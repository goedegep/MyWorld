package goedegep.media.mediadb.app.guifx;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.appgen.Operation;
import goedegep.appgen.TableRowOperationDescriptor;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultAppResourcesFx;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorCustom;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.media.common.MediaAppResourcesFx;
import goedegep.media.common.MediaRegistry;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.Subject;
import goedegep.media.mediadb.model.Video;
import goedegep.resources.ImageSize;
import goedegep.util.datetime.FlexDateFormat;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * This class provides a table to list (music) Albums
 */
public class VideosTable extends EObjectTable<Video> {
  private static final Logger LOGGER = Logger.getLogger(VideosTable.class.getName());
  
  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;
  
  /**
   * The Media Database
   */
  private MediaDb mediaDb;
  
  /**
   * A reference to a FilmDbWindow, which provides functionality like opening a film details window.
   */
  private VideoDbWindow videoDbWindow;
  
  /**
   * The film player process. If not null, currentlyPlayingFilm is also not null.
   */
  private Process videoPlayerProcess = null;
  
  /**
   * The currently playing film. If not null, filmPlayerProcess is also not null.
   */
  private Video currentlyPlayingVideo = null;
  
  ObjectProperty<Video> videoToPlay = new SimpleObjectProperty<>();

  /**
   * Constructor
   * 
   * @param customization  the GUI customization.
   * @param filmDbWindow a reference to the film database window
   * @param mediaDb the media database from which the albums are listed
   */
  public VideosTable(CustomizationFx customization, VideoDbWindow filmDbWindow, MediaDb mediaDb) {
    super(customization, MediadbPackage.eINSTANCE.getVideo(), new VideosTableDescriptor(customization), mediaDb, MediadbPackage.eINSTANCE.getMediaDb_Videos());
    
    this.customization = customization;
    this.mediaDb = mediaDb;
    this.videoDbWindow = filmDbWindow;
    componentFactory = customization.getComponentFactoryFx();
    
//    FilmsTableDescriptor filmsTableDescriptor = (FilmsTableDescriptor) getTableDescriptor();
//    filmsTableDescriptor.setFilmsTable(this);
    
    setTableMenuButtonVisible(true);
    Background background = customization.getComponentFactoryFx().getPanelBackground();
    if (background != null) {
      setBackground(background);
    }
    
  }

  @Override
  protected void handleRowClicked(Video film) {
    // A single click on a row 'opens' (plays) the film.
    openObject(film);
  }

  @Override
  protected void handleRowDoubleClicked(Video rowData) {
    LOGGER.severe("=>");
    videoDbWindow.openVideoDetailsEditor(getSelectedObject());
  }

  /**
   * 'Open' an film.
   * <p>
   * Opening in this case means playing with ....<br/>
   * Note: This table (and so this method) are not used to find problems in the provided information.
   *       So if something is missing or wrong, this method just returns.
   */
  @Override
  public void openObject(Video film) {
    LOGGER.severe("Opening: " + film.getTitle());
    
//    // If we are currently playing this album, kill the player.
//    if ((mediaPlayerProcess != null)  &&  mediaPlayerProcess.isAlive()  &&  (currentlyPlayingAlbum != null  &&  currentlyPlayingAlbum.equals(album))) {
//      mediaPlayerProcess.destroy();
//      mediaPlayerProcess = null;
//      currentlyPlayingAlbum = null;
//      return;
//    }
//    
//    // Can't play anything if the path to MPC-HC isn't set.
//    if (MediaRegistry.mediaPlayerClassicExecutable == null) {
//      return;
//    }
//    
//    if (MediaDbUtil.haveAlbumOnDisc(album, null)) {
//      // Complete album in MusicFolder, play complete album
//      AlbumDiscLocationInfo albumDiscLocationInfo;
//
//      albumDiscLocationInfo = albumToMusicFolderLocationMap.get(album);
//      
//      if (albumDiscLocationInfo == null) {
//        // If the album is available on disc, MPC-HC is started with the related disc folder.
//        List<Disc> discsInMusicFolder = new ArrayList<>();
//        List<AlbumDiscLocationInfo> albumDiscLocationInfos = new ArrayList<>();
//
//        for (Disc disc: album.getDiscs()) {
//          AlbumDiscLocationInfo thisAlbumDiscLocationInfo = albumDiscToMusicFolderLocationMap.get(disc);
//          if (albumDiscLocationInfo != null) {
//            discsInMusicFolder.add(disc);
//            albumDiscLocationInfos.add(thisAlbumDiscLocationInfo);
//          }
//        }
//
//        if (discsInMusicFolder.isEmpty()) {
//          return;
//        }
//
//        // If more than one disc available for the album, let the user chose one.
//        if (discsInMusicFolder.size() > 1) {
//          discToPlay.addListener((e) -> {
//            playDisc(album);
//          });
//          DiscSelectionDialog discSelectionDialog = new DiscSelectionDialog(customization, mediaDbWindow, discsInMusicFolder, albumDiscLocationInfos, discToPlay);
//          discSelectionDialog.show();
//          return;
//        } else {
//          albumDiscLocationInfo = albumDiscLocationInfos.get(0);
//        }
//      }
//      
//      // Build the command to start the media player.
//      // First argument is the MPC-HC player executable. Then for each album folder a '/play' argument followed by the folder.
//      List<String> commandArguments = new ArrayList<>();
//      commandArguments.add(MediaRegistry.mediaPlayerClassicExecutable);
//      LOGGER.severe("albumDiscLocationInfo: " + albumDiscLocationInfo.toString());
//      commandArguments.add("/play ");
//      commandArguments.add(albumDiscLocationInfo.getAbsAlbumFolderPathname());
//      
//      try {
//        mediaPlayerProcess = new ProcessBuilder(commandArguments).start();
//        currentlyPlayingAlbum = album;
//      } catch (IOException e1) {
//        e1.printStackTrace();
//      }
//    } else if (MediaDbUtil.haveAlbumPartlyOnDisc(album)) {
//      /*
//       * In this case the tracks I have are not part of a compilation, but are e.g. available in a Track Folder.
//       * If the tracks are available via the trackDiscLocationMap, they are played.
//       */
//      
//      // Build the command to start the media player.
//      // First argument is the MPC-HC player executable. Then for each track ....
//      List<String> commandArguments = new ArrayList<>();
//      commandArguments.add(MediaRegistry.mediaPlayerClassicExecutable);        
//
//      List<DiscAndTrackNrs> tracks = MediaDbUtil.getAlbumTracksIHave(album, true);
//
//      for (DiscAndTrackNrs discAndTrackNr: tracks) {
//        int discNr = 1;
//        if (discAndTrackNr.isSetDiscNr()) {
//          discNr = discAndTrackNr.getDiscNr();
//        }
//        for (int trackNr: discAndTrackNr.getTrackNrs()) {
//          Track track = album.getTrackReference(discNr, trackNr).getTrack();
//          Path trackPath = trackDiscLocationMap.get(track);
//          if (trackPath != null) {
//            LOGGER.severe("file: " + trackPath.toAbsolutePath().toString());
//            commandArguments.add("/play ");
//            commandArguments.add(trackPath.toAbsolutePath().toString());
//          } else {
//            LOGGER.severe("Path not found for: " + album.getArtistAndTitle() + ", track: " + discNr + "." + trackNr);
//          }
//        }
//      }
//
//      // Only start the player if at least one track is found.
//      if (commandArguments.size() > 1) {
//        try {
//          mediaPlayerProcess = new ProcessBuilder(commandArguments).start();
//        } catch (IOException e1) {
//          e1.printStackTrace();
//        }
//      }
//
//    } else if (album.isSetMyInfo()  &&  (MediaDbAppUtil.getReferredAlbum(album.getMyInfo()) != null)) {
//      // If there is an album reference, this usually refers to a compilation which contains the tracks that I have of this album.
//      Album referredAlbum = MediaDbAppUtil.getReferredAlbum(album.getMyInfo());
//      if (referredAlbum != null) {
//        if (findObject(referredAlbum)) {
//          openObject(referredAlbum);
//        }
//        return;
//      }
//    } else {
//      return;
//    }

  }
  
  private void playVideo(Video film) {
//    AlbumDiscLocationInfo albumDiscLocationInfo = discToPlay.getValue();
//    
//    // Build the command to start the media player.
//    // First argument is the MPC-HC player executable. Then for each album folder a '/play' argument followed by the folder.
//    List<String> commandArguments = new ArrayList<>();
//    commandArguments.add(MediaRegistry.mediaPlayerClassicExecutable);
//    LOGGER.severe("albumDiscLocationInfo: " + albumDiscLocationInfo.toString());
//    commandArguments.add("/play ");
//    commandArguments.add(albumDiscLocationInfo.getAbsAlbumFolderPathname());
//    
//    try {
//      mediaPlayerProcess = new ProcessBuilder(commandArguments).start();
//      currentlyPlayingAlbum = album;
//    } catch (IOException e1) {
//      e1.printStackTrace();
//    }
//    LOGGER.severe("Playing disc");
//    
//    discToPlay = new SimpleObjectProperty<>();
//
  }

}


/**
 * This class is the table descriptor for the VideosTable.
 */
class VideosTableDescriptor extends EObjectTableDescriptor<Video> {
  private static final int IMAGE_HEIGHT = 60;
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
  private static final FlexDateFormat FDF = new FlexDateFormat();

  /*
   * Column descriptors which have to be adapted in the constructor.
   */
  private EObjectTableColumnDescriptorCustom<Video> playColumnDescriptor = new EObjectTableColumnDescriptorCustom<>(null, "Play", null, true, true, null);
  private EObjectTableColumnDescriptorCustom<Video> imageColumnDescriptor = new EObjectTableColumnDescriptorCustom<>(MEDIA_DB_PACKAGE.getVideo_Image(), "Image", null, true, true, null);
  
  /*
   * The complete list of column descriptors
   */
  private List<EObjectTableColumnDescriptorAbstract<Video>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<Video>>of(
      playColumnDescriptor,
      new EObjectTableColumnDescriptorBasic<Video>(MEDIA_DB_PACKAGE.getVideo_Date(), "Date", true, true),
      new EObjectTableColumnDescriptorBasic<Video>(MEDIA_DB_PACKAGE.getVideo_Title(), "Title", true, true),
      imageColumnDescriptor,
      new EObjectTableColumnDescriptorBasic<Video>(MEDIA_DB_PACKAGE.getVideo_Subjects(), "Subjects", 300, true, true, new StringConverter<List<Subject>>() {

        @Override
        public String toString(List<Subject> subjects) {
          StringBuilder buf = new StringBuilder();
          boolean first = true;
          
          for (Subject subject: subjects) {
            if (first) {
              first = false;
            } else {
              buf.append(", ");
            }
            if (subject.isSetDate()) {
              buf.append(FDF.format(subject.getDate()));
              if (subject.isSetTitle()) {
                buf.append(": ");
              }
            }
            if (subject.isSetTitle()) {
              buf.append(subject.getTitle());
            }
            if (!subject.getTags().isEmpty()) {
              buf.append(": ");
            }
            boolean firstTag = true;
            for (String tag: subject.getTags()) {
              if (firstTag) {
                firstTag = false;
              } else {
                buf.append("; ");
              }
              buf.append(tag);
            }
          }
          
          return buf.toString();
        }

        @Override
        public List<Subject> fromString(String string) {
          // Not needed here
          return null;
        }
        
      })      
  );
  
  @SuppressWarnings("serial")
  private static Map<Operation, TableRowOperationDescriptor<Video>> rowOperations = new HashMap<>() {
    {
      put(Operation.OPEN, new TableRowOperationDescriptor<>("Play"));
      put(Operation.NEW_OBJECT, new TableRowOperationDescriptor<>("New film"));
      put(Operation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete film"));
    }
  };
    
  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   */
  VideosTableDescriptor(CustomizationFx customization) {
    super();
    
    VideoPlayCellFactory myInfoPlayCellFactory = new VideoPlayCellFactory(customization, IMAGE_HEIGHT);
    playColumnDescriptor.setCellFactory(myInfoPlayCellFactory);
    
    VideoImageCellFactory imageListCellFactory = new VideoImageCellFactory(customization, IMAGE_HEIGHT, MediaRegistry.musicDataDirectory);
    imageColumnDescriptor.setCellFactory(imageListCellFactory);
    
    setComparator(new VideoComparator());
    setColumnDescriptors(columnDescriptors);
    setRowOperations(rowOperations);
  }
  
}

/**
 * A Comparator to sort the films.
 */
class VideoComparator implements Comparator<Video> {

  @Override
  public int compare(Video film1, Video film2) {
    int compareResult = 0;
    
    if (film1.getDate() != null  &&  film2.getDate() == null) {
      return 1;
    }
    
    if (film1.getDate() == null  &&  film2.getDate() != null) {
      return -1;
    }
    
    if (film1.getDate() != null  &&  film2.getDate() != null) {
      compareResult = film1.getDate().compareTo(film2.getDate());
    }
    
    if (compareResult == 0) {
      if (film1.getTitle() != null  &&  film2.getTitle() == null) {
        return 1;
      }
      
      if (film1.getTitle() == null  &&  film2.getTitle() != null) {
        return -1;
      }
      
      if (film1.getTitle() != null  &&  film2.getTitle() != null) {
        compareResult = film1.getTitle().compareTo(film2.getTitle());
      }
    }
    
    return compareResult;
  }
  
}

/**
 * A factory to create a 'Play' cell.
 */
class VideoPlayCellFactory implements Callback<TableColumn<Video, Object>, TableCell<Video, Object>> {
  private CustomizationFx customization;
  private int imageHeight;
  
  public VideoPlayCellFactory(CustomizationFx customization, int imageHeight) {
    this.customization = customization;
    this.imageHeight = imageHeight;
  }

  @Override
  public TableCell<Video, Object> call(TableColumn<Video, Object> arg0) {
    return new VideoPlayCell(customization, imageHeight);
  }
  
}

/**
 * A 'Play' table cell
 */
class VideoPlayCell extends TableCell<Video, Object> {
  private static final Logger LOGGER = Logger.getLogger(MyInfoPlayCell.class.getName());

  static DefaultAppResourcesFx m = new DefaultAppResourcesFx();
  
  private MediaAppResourcesFx mediaAppResources;
  private ImageView imageView;

  public VideoPlayCell(CustomizationFx customization, int imageHeight) {
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
      if (!(item instanceof Video)) {
        throw new IllegalArgumentException("item shall be of type 'Film', but is of type '" + item.getClass().getName() + "'");
      }
      Video film = (Video) item;
      
      String tooltipText = "Play this video";
      imageView.setImage(mediaAppResources.getPlayIcon());
      
      setGraphic(imageView);
      setText(null);
        Tooltip tooltip = new Tooltip(tooltipText);
        setTooltip(tooltip);
    }
    LOGGER.info("<=");
  }
}


/**
 * A factory to create a 'List of Images' cell.
 */
class VideoImageCellFactory implements Callback<TableColumn<Video, Object>, TableCell<Video, Object>> {
  private CustomizationFx customization;
  private int imageHeight;
  private String albumInfoDirectory;
  
  public VideoImageCellFactory(CustomizationFx customization, int imageHeight, String albumInfoDirectory) {
    this.customization = customization;
    this.imageHeight = imageHeight;
    this.albumInfoDirectory = albumInfoDirectory;
  }

  @Override
  public TableCell<Video, Object> call(TableColumn<Video, Object> arg0) {
    return new VideoImageCell(customization, imageHeight, albumInfoDirectory);
  }
  
}

/**
 * A table cell for an Image.
 */
class VideoImageCell extends TableCell<Video, Object> {
  private static final Logger LOGGER = Logger.getLogger(ImageListCell.class.getName());
  
  private int imageHeight;
  private String albumInfoDirectory;

  public VideoImageCell(CustomizationFx customization, int imageHeight, String albumInfoDirectory) {
    LOGGER.info("<=>");
    this.imageHeight = imageHeight;
    this.albumInfoDirectory = albumInfoDirectory;
    Background background = customization.getComponentFactoryFx().getPanelBackground();
    if (background != null) {
      setBackground(background);
    }
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
      if (!(item instanceof String)) {
        throw new IllegalArgumentException("item shall be a String, but is of type '" + item.getClass().getName() + "'");
      }
      
      String  imageFileName = (String) item;
      Image image = new Image("file:" + imageFileName, -1, imageHeight,  true, true);
      ImageView imageView = new ImageView(image);
      setGraphic(imageView);
      setText(null);
    }
    LOGGER.info("<=");
  }
}



/**
 * Film player
 */
class VideoPlayer implements Runnable {
//  private Film film;
  
  public VideoPlayer(Video film) {
//    this.film = film;
  }

  @Override
  public void run() {
    // TODO Auto-generated method stub
    
  }
  
}

