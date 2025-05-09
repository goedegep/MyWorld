package goedegep.media.mediadb.app.guifx;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EStructuralFeature;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorChoiceBox;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorCustom;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.media.app.MediaRegistry;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Collection;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Player;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.util.datetime.DurationFormat;
import javafx.collections.FXCollections;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.StringConverter;

/**
 * This class provides a table which lists the tracks of a single disc of an album.
 */
public class DiscTracksTable extends EObjectTable<TrackReference> {
  private static final Logger LOGGER = Logger.getLogger(DiscTracksTable.class.getName());
  
  private Map<Track, Path> trackDiscLocationMap;
  
  /**
   * The media player process. If not null, currentlyPlayingTrack is also not null.
   */
  private Process mediaPlayerProcess = null;
  
  /**
   * The currently playing track. If not null, mediaPlayerProcess is also not null.
   */
  private Track currentlyPlayingTrack = null;

  /**
   * Constructor
   * 
   * @param trackReferences the items to be shown in the table.
   */
  public DiscTracksTable(CustomizationFx customization, Disc disc, Map<Track, Path> trackDiscLocationMap) {
    super(customization, MediadbPackage.eINSTANCE.getTrackReference(), new DiscTracksTableDescriptor(), disc, MediadbPackage.eINSTANCE.getDisc_TrackReferences());
    for (TableColumn<TrackReference, ?> column: getColumns()) {
      boolean hide = false;
      switch (column.getText()) {
      case "Artist":
        hide = allItemsNullOrEmpty(MediadbPackage.eINSTANCE.getTrack_Artist());
        break;
        
      case "Duration":
        hide = allItemsNullOrEmpty(MediadbPackage.eINSTANCE.getTrack_Duration());
        break;
        
      case "Players":
        hide = allItemsNullOrEmpty(MediadbPackage.eINSTANCE.getTrack_Players());
        break;
        
      case "Author":
        hide = allItemsNullOrEmpty(MediadbPackage.eINSTANCE.getTrack_Authors());
        break;
        
      case "Bonus track":
        hide = hideBonusTrack();
        break;
        
      case "Original track reference":
        hide = hideOriginalTrackReference();
        break;
      }
      if (hide) {
        column.setVisible(false);
      }
    }

    this.setEditable(true);
    
    this.trackDiscLocationMap = trackDiscLocationMap;
  }
    
  /**
   * Check whether the Bonus track column shall be hidden?
   * <p>
   * This is the fact if all values are null, or if all values are the same.
   * 
   * @return true if the Artist column shall be hidden, false otherwise.
   */
  private boolean hideBonusTrack() {
    for (TrackReference trackReference: getItems()) {
      if (trackReference.getBonusTrack() != null) {
        return false;
      }
    }
    
    return true;
  }
  
  /**
   * Check whether the Original track reference column shall be hidden?
   * <p>
   * This is the fact if all values are null, or if all values are the same.
   * 
   * @return true if the Artist column shall be hidden, false otherwise.
   */
  private boolean hideOriginalTrackReference() {
    for (TrackReference trackReference: getItems()) {
      if (trackReference.isOriginalAlbumTrackReference()) {
        return false;
      }
    }
    
    return true;
  }
  
  private boolean allItemsNullOrEmpty(EStructuralFeature attribute) {
    for (TrackReference trackReference: getItems()) {
      Track track = trackReference.getTrack();
      Object value = track.eGet(attribute);
      if (value != null) {
        if (value instanceof List<?> list) {
          if (!list.isEmpty()) {
            return false;
          }
        } else {
          return false;
        }
      }
    }
    
    return true;
  }

  @Override
  protected void handleRowClicked(TrackReference trackReference) {
    // A single click on a row 'opens' (plays) the track.
    openObject(trackReference);
  }

  /**
   * 'Open' a TrackReference.
   * <p>
   * Opening in this case means playing it (if available) with MPC-HC.<br/>
   * Note: This table (and so this method) are not used to find problems in the provided information.
   *       So if something is missing or wrong, this method just returns.
   */
  @Override
  public void openObject(TrackReference trackReference) {
    Track track = trackReference.getTrack();
    LOGGER.severe("Opening: " + track.getTitle());
    
    // If we are currently playing this track, kill the player.
    if ((mediaPlayerProcess != null)  &&  mediaPlayerProcess.isAlive()  &&  (currentlyPlayingTrack.equals(track))) {
      mediaPlayerProcess.destroy();
      mediaPlayerProcess = null;
      currentlyPlayingTrack = null;
      return;
    }
        
    // Can't play anything if the path to MPC-HC isn't set.
    if (MediaRegistry.mediaPlayerClassicExecutable == null) {
      return;
    }
    
    // If the track is available via the trackDiscLocationMap, it is played.
    Path trackPath = trackDiscLocationMap.get(track);
    if (trackPath != null) {
      // Build the command to start the media player.
      // First argument is the MPC-HC player executable.
      List<String> commandArguments = new ArrayList<>();
      commandArguments.add(MediaRegistry.mediaPlayerClassicExecutable);        

      LOGGER.severe("file: " + trackPath.toAbsolutePath().toString());
      commandArguments.add("/play ");
      commandArguments.add(trackPath.toAbsolutePath().toString());

      try {
        currentlyPlayingTrack = track;
        mediaPlayerProcess = new ProcessBuilder(commandArguments).start();
      } catch (IOException e1) {
        e1.printStackTrace();
      }

    } else {
      LOGGER.severe("Path not found for: " + track.getTitle());
    }

  }
}

class DiscTracksTableDescriptor extends EObjectTableDescriptor<TrackReference> {
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;

  private static List<EObjectTableColumnDescriptorAbstract<TrackReference>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<TrackReference>>of(
      new EObjectTableColumnDescriptorBasic<TrackReference>(MEDIA_DB_PACKAGE.getTrackReference__GetTrackNr(), "Nr.", false, true),
      new EObjectTableColumnDescriptorCustom<TrackReference>(null, "Title", null, true, true, column -> {
        TableCell<TrackReference, Object> cell = new TableCell<>() {

          @Override
          protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            if(empty || (item == null)) {
              setText(null);
            }
            else {
              TrackReference trackReference = (TrackReference) item;
              Track track = trackReference.getTrack();
              setText(track.getTitle());
            }
          }
        };

        return cell;
      }),
      new EObjectTableColumnDescriptorCustom<TrackReference>(null, "Artist", null, true, true, column -> {
        TableCell<TrackReference, Object> cell = new TableCell<>() {

          @Override
          protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            if(empty || (item == null)) {
              setText(null);
            }
            else {
              setText(null);
              TrackReference trackReference = (TrackReference) item;
              Track track = trackReference.getTrack();
              Artist trackArtist = track.getArtist();
              if (trackArtist != null) {
                setText(trackArtist.getName());
              }
            }
          }
        };

        return cell;
      }),
      new EObjectTableColumnDescriptorCustom<TrackReference>(null, "Duration", null, true, true, column -> {
        TableCell<TrackReference, Object> cell = new TableCell<>() {
          private DurationFormat format = new DurationFormat();

          @Override
          protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            if(empty || (item == null)) {
              setText(null);
            }
            else {
              TrackReference trackReference = (TrackReference) item;
              Track track = trackReference.getTrack();
              if (track == null) {
                System.out.println("track is null");
                setText(null);
              } else {
                if (track.isSetDuration()) {
                  Duration clockTime = Duration.ofSeconds(track.getDuration());
                  setText(format.format(clockTime));
                } else {
                  setText(null);
                }
              }
            }
          }
        };

        return cell;
      }),
      new EObjectTableColumnDescriptorCustom<TrackReference>(null, "Players", null, true, true, column -> {
        TableCell<TrackReference, Object> cell = new TableCell<>() {

          @Override
          protected void updateItem(Object item, boolean empty) {            
            super.updateItem(item, empty);
            if(empty || (item == null)) {
              setText(null);
            }
            else {
              TrackReference trackReference = (TrackReference) item;
              Track track = trackReference.getTrack();
              StringBuilder buf = new StringBuilder();
              List<Player> players = track.getPlayers();
              boolean first = true;
              for (Player player: players) {
                if (first == true) {
                  first = false;
                } else {
                  buf.append(", ");
                }
                Artist artist = player.getArtist();
                if (artist != null) {
                  buf.append(artist.getName());
                  buf.append(": ");
                  boolean firstInstrument = true;
                  for (String instrument: player.getInstruments()) {
                    if (firstInstrument == true) {
                      firstInstrument = false;
                    } else {
                      buf.append(", ");
                    }
                    buf.append(instrument);
                  }
                }
              }
              
              setText(buf.toString());
            }
          }
        };

        return cell;
      }),
      new EObjectTableColumnDescriptorCustom<TrackReference>(null, "Author", null, true, true, column -> {
        TableCell<TrackReference, Object> cell = new TableCell<>() {

          @Override
          protected void updateItem(Object item, boolean empty) {            
            super.updateItem(item, empty);
            if(empty || (item == null)) {
              setText(null);
            }
            else {
              TrackReference trackReference = (TrackReference) item;
              Track track = trackReference.getTrack();
              StringBuilder buf = new StringBuilder();
              List<Artist> authors = track.getAuthors();
              boolean first = true;
              for (Artist author: authors) {
                if (first == true) {
                  first = false;
                } else {
                  buf.append(", ");
                }
                buf.append(author.getName());
              }
              
              setText(buf.toString());
            }
          }
        };

        return cell;
      }),
      new EObjectTableColumnDescriptorCustom<TrackReference>(null, "Bonus track", null, true, true, column -> {
        TableCell<TrackReference, Object> cell = new TableCell<>() {

          @Override
          protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            if(empty || (item == null)) {
              setText(null);
            }
            else {
              TrackReference trackReference = (TrackReference) item;
              String text = trackReference.getBonusTrack();
              setText(text);
            }
          }
        };

        return cell;
      }),
      new EObjectTableColumnDescriptorCustom<TrackReference>(null, "Have on", null, true, true, column -> {
        TableCell<TrackReference, Object> cell = new TableCell<>() {

          @Override
          protected void updateItem(Object item, boolean empty) {            
            super.updateItem(item, empty);
            if(empty || (item == null)) {
              setText(null);
            }
            else {
              setText(null);
              TrackReference trackReference = (TrackReference) item;
              if (trackReference.isSetMyTrackInfo()) {
                MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
                if (myTrackInfo.isSetIHaveOn()) {
                  List<MediumInfo> mediumInfos = myTrackInfo.getIHaveOn();
                  boolean first = true;
                  StringBuilder buf = new StringBuilder();
                  for (MediumInfo mediumInfo: mediumInfos) {
                    if (first) {
                      first = false;
                    } else {
                      buf.append(", ");
                    }
                    buf.append(GuiUtils.createMediumText(mediumInfo.getMediumType()));
                  }
                  setText(buf.toString());
                }
              }
            }
          }
        };

        return cell;
      }),
      new EObjectTableColumnDescriptorCustom<TrackReference>(null, "Original track reference", null, true, true, column -> {
        TableCell<TrackReference, Object> cell = new TableCell<>() {

          @Override
          protected void updateItem(Object item, boolean empty) {            
            super.updateItem(item, empty);
            if(empty || (item == null)) {
              setText(null);
            }
            else {
              setText(null);
              TrackReference trackReference = (TrackReference) item;
              TrackReference originalTrackReference = trackReference.getTrack().getOriginalDiscTrackReference();
              if (originalTrackReference != null) {
                setText(GuiUtils.createTrackReferenceText(originalTrackReference));
              }
            }
          }
        };

        return cell;
      }),
      new EObjectTableColumnDescriptorCustom<TrackReference>(null, "Track reference", null, true, true, column -> {
        TableCell<TrackReference, Object> cell = new TableCell<>() {

          @Override
          protected void updateItem(Object item, boolean empty) {            
            super.updateItem(item, empty);
            if(empty || (item == null)) {
              setText(null);
            }
            else {
              setText(null);
              TrackReference trackReference = (TrackReference) item;
              MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
              if (myTrackInfo != null) {
                TrackReference compilationTrackReference = myTrackInfo.getTrackReference();
                if (compilationTrackReference != null) {
                  setText(GuiUtils.createTrackReferenceText(compilationTrackReference));
                }
              }
            }
          }
        };

        return cell;
      })
      );
  
  DiscTracksTableDescriptor() {
    super(columnDescriptors, null);
  }
}
