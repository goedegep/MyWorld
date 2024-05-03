package goedegep.media.mediadb.app.guifx;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.appgen.Operation;
import goedegep.appgen.TableRowOperationDescriptor;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultAppResourcesFx;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorCustom;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.media.app.MediaRegistry;
import goedegep.media.app.base.MediaAppResourcesFx;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.Track;
import goedegep.resources.ImageSize;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.util.Callback;

/**
 * This class provides a table to list (music) Tracks.
 */
public class TracksTable extends EObjectTable<Track> {
  private static final Logger LOGGER = Logger.getLogger(TracksTable.class.getName());
  
//  /**
//   * The Media Database
//   */
//  private MediaDb mediaDb;
  
//  /**
//   * A reference to a MediaDbWindow, which provides functionality like opening an album details window.
//   */
//  private MediaDbWindow mediaDbWindow;
  
  /**
   * This map relates a track (as in the mediaDb) to its location on disc.
   */
  private Map<Track, Path> trackDiscLocationMap;
  
  /**
   * The media player process. If not null, currentlyPlayingAlbum is also not null.
   */
  private Process mediaPlayerProcess = null;
  
  /**
   * The currently playing track. If not null, mediaPlayerProcess is also not null.
   */
  private Track currentlyPlayingTrack = null;
  
//  ObjectProperty<AlbumDiscLocationInfo> discToPlay = new SimpleObjectProperty<>();

  /**
   * Constructor
   * 
   * @param customization  the GUI customization.
   * @param mediaDb the media database from which the albums are listed
   * @param albumDiscToMusicFolderLocationMap a map that relates the albums in the <code>mediaDb</code> to their locations on disc.
   * @param trackDiscLocationMap a map that relates tracks to their location on disc.
   */
  public TracksTable(CustomizationFx customization, MediaDbWindow mediaDbWindow, MediaDb mediaDb, Map<Track, Path> trackDiscLocationMap) {
    super(customization, MediadbPackage.eINSTANCE.getTrack(), new TracksTableDescriptor(customization, trackDiscLocationMap), mediaDb, MediadbPackage.eINSTANCE.getMediaDb_Tracks());
    
//    this.mediaDbWindow = mediaDbWindow;
    this.trackDiscLocationMap = trackDiscLocationMap;
    
    setTableMenuButtonVisible(true);
    Background background = customization.getComponentFactoryFx().getPanelBackground();
    if (background != null) {
      setBackground(background);
    }
  }

  @Override
  protected void handleRowClicked(Track track) {
    // A single click on a row 'opens' (plays) the track.
    openObject(track);
  }

  /**
   * 'Open' a track.
   * <p>
   * Opening in this case means playing with MPC-HC.<br/>
   * Note: This table (and so this method) are not used to find problems in the provided information.
   *       So if something is missing or wrong, this method just returns.
   */
  @Override
  public void openObject(Track track) {
    LOGGER.severe("Opening: " + track.getTitle());
    
    // If we are currently playing this track, kill the player. TODO does this mean close if open?
    if ((mediaPlayerProcess != null)  &&  (currentlyPlayingTrack.equals(track))) {
      mediaPlayerProcess.destroy();
      mediaPlayerProcess = null;
      currentlyPlayingTrack = null;
      return;
    }
    
    // MyInfo is used to determine whether I have the track on disc or not, or that I have it as part of a compilation.
    // TODO I think I hava all tracks on disc.
//    if (!track.isSetMyInfo()) {
//      return;
//    }
    
    // Can't play anything if the path to MPC-HC isn't set.
    if (MediaRegistry.mediaPlayerClassicExecutable == null) {
      return;
    }
    

    // Build the command to start the media player.
    // First argument is the MPC-HC player executable. Then for each track ....
    List<String> commandArguments = new ArrayList<>();
    commandArguments.add(MediaRegistry.mediaPlayerClassicExecutable);        

    Path trackPath = trackDiscLocationMap.get(track);
    if (trackPath != null) {
      LOGGER.severe("file: " + trackPath.toAbsolutePath().toString());
      commandArguments.add("/play ");
      commandArguments.add(trackPath.toAbsolutePath().toString());
    } else {
      LOGGER.severe("Path not found for track: " + track.toString());
    }

    // Only start the player if at least one track is found.
    if (commandArguments.size() > 1) {
      try {
        new ProcessBuilder(commandArguments).start();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
  }
  
}


/**
 * This class is the table descriptor for the TracksTable.
 */
class TracksTableDescriptor extends EObjectTableDescriptor<Track> {
  private static final int IMAGE_HEIGHT = 60;
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
  
  /**
   * This map relates a track (as in the mediaDb) to its location on disc.
   */
  private Map<Track, Path> trackDiscLocationMap;

  /*
   * Column descriptors which have to be adapted in the constructor.
   */
  private EObjectTableColumnDescriptorCustom<Track> playColumnDescriptor = new EObjectTableColumnDescriptorCustom<>(null, "Play", null, true, true, null);
  
  /*
   * The complete list of column descriptors
   */
  private List<EObjectTableColumnDescriptorAbstract<Track>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<Track>>of(
      playColumnDescriptor,
      new EObjectTableColumnDescriptorBasic<Track>(MEDIA_DB_PACKAGE.getTrack_Artist(), "Artist", true, true),
      new EObjectTableColumnDescriptorBasic<Track>(MEDIA_DB_PACKAGE.getTrack_Title(), "Title", true, true),
      new EObjectTableColumnDescriptorCustom<Track>(MEDIA_DB_PACKAGE.getTrack_OriginalDisc(), "Album", null, false, true, column -> {
        TableCell<Track, Object> cell = new TableCell<>() {

          @Override
          protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            if(empty || (item == null)) {
              setText(null);
            }
            else {
              StringBuilder buf = new StringBuilder();
              Disc disc = (Disc) item;
              Album album = disc.getAlbum();
              Artist artist = album.getArtist();
              if (artist != null) {
                buf.append(artist.getName());
              } else {
                buf.append("<no artist>");
              }
              buf.append(" - ");
              buf.append(album.getTitle());
              
              setText(buf.toString());
            }
          }
        };

        return cell;
      }),
      new EObjectTableColumnDescriptorCustom<Track>(null, "File", null, false, true, column -> {
        TableCell<Track, Object> cell = new TableCell<>() {

          @Override
          protected void updateItem(Object item, boolean empty) {            
            super.updateItem(item, empty);
            if(empty || (item == null)) {
              setText(null);
            }
            else {
              Track track = (Track) item;
              Path path = trackDiscLocationMap.get(track);
              if (path != null) {
                setText(path.toString());
              } else {
                setText(null);
              }
            }
          }
        };

        return cell;
      })
  );
  
  @SuppressWarnings("serial")
  private static Map<Operation, TableRowOperationDescriptor<Track>> rowOperations = new HashMap<>() {
    {
      put(Operation.OPEN, new TableRowOperationDescriptor<>("Play"));
      put(Operation.NEW_OBJECT, new TableRowOperationDescriptor<>("New track"));
      put(Operation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete track"));
    }
  };
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   */
  TracksTableDescriptor(CustomizationFx customization, Map<Track, Path> trackDiscLocationMap) {
    super();
    
    this.trackDiscLocationMap = trackDiscLocationMap;
    
    MyTrackInfoPlayCellFactory myTrackInfoPlayCellFactory = new MyTrackInfoPlayCellFactory(customization, IMAGE_HEIGHT);
    playColumnDescriptor.setCellFactory(myTrackInfoPlayCellFactory);
        
    setComparator(new TrackComparator());
    setColumnDescriptors(columnDescriptors);
    setRowOperations(rowOperations);
  }
}

/**
 * A Comparator to sort the tracks.
 */
class TrackComparator implements Comparator<Track> {

  @Override
  public int compare(Track track1, Track track2) {
    int compareResult = 0;
    
    if (!track1.isSetArtist() || !track1.getArtist().isSetName()) {
      compareResult = -1;
    } else if (!track2.isSetArtist() || !track2.getArtist().isSetName()) {
      compareResult = 1;
    } else {
      compareResult = track1.getArtist().getName().compareTo(track2.getArtist().getName());
    }
    if (compareResult == 0) {
      if (track1.getTitle() == null) {
        compareResult = -1;
      } else if (track2.getTitle() == null) {
        compareResult = 1;
      } else {
        compareResult = track1.getTitle().compareTo(track2.getTitle());
      }
    }
    return compareResult;
  }
  
}

/**
 * A factory to create a 'Play' cell, based on the 'MyInfo' of the album.
 */
class MyTrackInfoPlayCellFactory implements Callback<TableColumn<Track, Object>, TableCell<Track, Object>> {
  private CustomizationFx customization;
  private int imageHeight;

  public MyTrackInfoPlayCellFactory(CustomizationFx customization, int imageHeight) {
    this.customization = customization;
    this.imageHeight = imageHeight;
  }

  @Override
  public TableCell<Track, Object> call(TableColumn<Track, Object> arg0) {
    return new MyTrackInfoPlayCell(customization, imageHeight);
  }

}

/**
 * A 'Play' table cell
 */
class MyTrackInfoPlayCell extends TableCell<Track, Object> {
  private static final Logger LOGGER = Logger.getLogger(MyInfoPlayCell.class.getName());

  static DefaultAppResourcesFx m = new DefaultAppResourcesFx();

  private MediaAppResourcesFx mediaAppResources;
  private ImageView imageView;

  public MyTrackInfoPlayCell(CustomizationFx customization, int imageHeight) {
    LOGGER.info("<=>");
    Background background = customization.getComponentFactoryFx().getPanelBackground();
    if (background != null) {
      setBackground(background);
    }
    AppResourcesFx appResources = customization.getResources();
    mediaAppResources = (MediaAppResourcesFx) appResources;
    Image i = m.getAttentionImage(ImageSize.SIZE_0);
    imageView = new ImageView();
    imageView.setFitHeight(imageHeight);
    imageView.setPreserveRatio(true);
    imageView.setImage(i);
    setGraphic(imageView);
    
    addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
      MyTrackInfoPlayCell cell = (MyTrackInfoPlayCell) e.getSource();
      Track track = (Track) cell.getItem();
      LOGGER.info("Mouse clicked on: " + track.toString());
      ((EObjectTable<Track>) getTableView()).openObject(track);
      e.consume();
    });
  }

  @Override
  protected void updateItem(Object item, boolean empty) {
    LOGGER.info("=> item=" + (item != null ? item.toString() : "(null)") + ", empty=" + empty);
    super.updateItem(item, empty);
    if (empty  ||  (item == null)) {
      setText(null);
      setGraphic(null);
      setTooltip(null);
    } else {
//      if (!(item instanceof MyTrackInfo)) {
//        LOGGER.severe("item: " + item.toString());
//        throw new IllegalArgumentException("item shall be of type 'MyTrackInfo', but is of type '" + item.getClass().getName() + "'");
//      }

      imageView.setImage(mediaAppResources.getPlayIcon());
      
      setGraphic(imageView);
      setText(null);
      Tooltip tooltip = new Tooltip("I have this album on disc, so it can be played");
      setTooltip(tooltip);
    }
    LOGGER.info("<=");
  }
}
