package goedegep.media.mediadb.app.guifx;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.logging.Logger;

import goedegep.appgen.Operation;
import goedegep.appgen.TableRowOperationDescriptor;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultAppResourcesFx;
import goedegep.jfx.DoubleClickEventDispatcher;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorCheckBox;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorCustom;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.media.app.MediaRegistry;
import goedegep.media.app.base.MediaAppResourcesFx;
import goedegep.media.mediadb.albumeditor.guifx.AlbumEditor;
import goedegep.media.mediadb.app.AlbumDiscLocationInfo;
import goedegep.media.mediadb.app.MediaDbAppUtil;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.DiscAndTrackNrs;
import goedegep.media.mediadb.model.IWant;
import goedegep.media.mediadb.model.InformationType;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.media.mediadb.model.util.MediaDbUtil;
import goedegep.media.musicfolder.AlbumOnDiscInfo;
import goedegep.resources.ImageSize;
import goedegep.util.datetime.FlexDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * This class provides a table to list (music) Albums
 */
public class AlbumsTable extends EObjectTable<Album> {
  private static final Logger LOGGER = Logger.getLogger(AlbumsTable.class.getName());
  
  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;
  
  /**
   * The Media Database
   */
  private MediaDb mediaDb;
  
  /**
   * A reference to a MediaDbWindow, which provides functionality like opening an album details window.
   */
  private MediaDbWindow mediaDbWindow;
  
  /**
   * This map relates a single disc Album (as in the mediaDb) to its location on disc.
   */
  private Map<Album, AlbumOnDiscInfo> albumToMusicFolderLocationMap;
  
  /**
   * This map relates an Album disc (as in the mediaDb) to its location on disc.
   */
  private Map<Disc, AlbumOnDiscInfo> albumDiscToMusicFolderLocationMap;
  
  /**
   * This map relates a track (as in the mediaDb) to its location on disc.
   */
  private Map<Track, Path> trackDiscLocationMap;
  
  /**
   * The media player process. If not null, currentlyPlayingAlbum is also not null.
   */
  private Process mediaPlayerProcess = null;
  
  /**
   * The currently playing album. If not null, mediaPlayerProcess is also not null.
   */
  private Album currentlyPlayingAlbum = null;
  
  ObjectProperty<AlbumOnDiscInfo> discToPlay = new SimpleObjectProperty<>();

  /**
   * Constructor
   * 
   * @param customization  the GUI customization.
   * @param mediaDb the media database from which the albums are listed
   * @param albumDiscToMusicFolderLocationMap a map that relates the albums in the <code>mediaDb</code> to their locations on disc.
   * @param trackDiscLocationMap a map that relates tracks to their location on disc.
   */
  public AlbumsTable(CustomizationFx customization, MediaDbWindow mediaDbWindow, MediaDb mediaDb,
      Map<Album, AlbumOnDiscInfo> albumToMusicFolderLocationMap, Map<Disc, AlbumOnDiscInfo> albumDiscToMusicFolderLocationMap, Map<Track, Path> trackDiscLocationMap) {
    super(customization, MediadbPackage.eINSTANCE.getAlbum(), new AlbumsTableDescriptor(customization, mediaDb, trackDiscLocationMap), mediaDb, MediadbPackage.eINSTANCE.getMediaDb_Albums());
    
    this.customization = customization;
    this.mediaDb = mediaDb;
    this.mediaDbWindow = mediaDbWindow;
    this.albumToMusicFolderLocationMap = albumToMusicFolderLocationMap;
    this.albumDiscToMusicFolderLocationMap = albumDiscToMusicFolderLocationMap;
    this.trackDiscLocationMap = trackDiscLocationMap;
    componentFactory = customization.getComponentFactoryFx();
    
    AlbumsTableDescriptor albumsTableDescriptor = (AlbumsTableDescriptor) getTableDescriptor();
    albumsTableDescriptor.setAlbumsTable(this);
    
    setTableMenuButtonVisible(true);
    Background background = customization.getComponentFactoryFx().getPanelBackground();
    if (background != null) {
      setBackground(background);
    }
    
//    getColumns().get(1).setSortType(TableColumn.SortType.ASCENDING);
//    getColumns().get(2).setSortType(TableColumn.SortType.ASCENDING);
//    getColumns().get(3).setSortType(TableColumn.SortType.ASCENDING);
//    getSortOrder().addAll(getColumns().get(1), getColumns().get(2), getColumns().get(3));    
    
  }

  protected ContextMenu createContextMenu(TableRow<Album> row) {
    // Handle standard items.
    ContextMenu contextMenu = super.createContextMenu(row);
    
    // Add AlbumsTable specific items.
    MenuItem menuItem = componentFactory.createMenuItem("Importeer tracks");
    menuItem.setOnAction((ActionEvent event) -> {
      importTracks(row.getItem());
    });
    contextMenu.getItems().add(menuItem);
    
    return contextMenu;
  }

  @Override
  protected void handleRowClicked(Album album) {
    // A single click on a row 'opens' (plays) the album.
    openObject(album);
  }

  @Override
  protected void handleRowDoubleClicked(Album rowData) {
    LOGGER.info("=>");
    mediaDbWindow.openAlbumDetailsWindow();
  }

  /**
   * 'Open' an Album, or a disc of an album in case of a multi disc album.
   * <p>
   * Opening in this case means playing with MPC-HC.<br/>
   * Note: This table (and so this method) are not used to find problems in the provided information.
   *       So if something is missing or wrong, this method just returns.
   */
  @Override
  public void openObject(Album album) {
    LOGGER.severe("Opening: " + album.getTitle());
    
    // If we are currently playing this album, kill the player.
    if ((mediaPlayerProcess != null)  &&  mediaPlayerProcess.isAlive()  &&  (currentlyPlayingAlbum != null  &&  currentlyPlayingAlbum.equals(album))) {
      mediaPlayerProcess.destroy();
      mediaPlayerProcess = null;
      currentlyPlayingAlbum = null;
      return;
    }
    
//    // MyInfo is used to determine whether I have the album (partly) on disc or not, or that I have it as part of a compilation.
//    if (!album.isSetMyInfo()) {
//      return;
//    }
    
    // Can't play anything if the path to MPC-HC isn't set.
    if (MediaRegistry.mediaPlayerClassicExecutable == null) {
      return;
    }
    
//    MyInfo myInfo = album.getMyInfo();
    if (MediaDbUtil.haveAlbumOnDisc(album)) {
      // Complete album in MusicFolder, play complete album
      AlbumOnDiscInfo albumDiscLocationInfo;

      albumDiscLocationInfo = albumToMusicFolderLocationMap.get(album);
      
      if (albumDiscLocationInfo == null) {
        // If the album is available on disc, MPC-HC is started with the related disc folder.
        List<Disc> discsInMusicFolder = new ArrayList<>();
        List<AlbumOnDiscInfo> albumDiscLocationInfos = new ArrayList<>();

        for (Disc disc: album.getDiscs()) {
          AlbumOnDiscInfo thisAlbumDiscLocationInfo = albumDiscToMusicFolderLocationMap.get(disc);
          if (albumDiscLocationInfo != null) {
            discsInMusicFolder.add(disc);
            albumDiscLocationInfos.add(thisAlbumDiscLocationInfo);
          }
        }

        if (discsInMusicFolder.isEmpty()) {
          return;
        }

        // If more than one disc available for the album, let the user chose one.
        if (discsInMusicFolder.size() > 1) {
          discToPlay.addListener((e) -> {
            playDisc(album);
          });
          DiscSelectionDialog discSelectionDialog = new DiscSelectionDialog(customization, mediaDbWindow, discsInMusicFolder, albumDiscLocationInfos, discToPlay);
          discSelectionDialog.show();
          return;
        } else {
          albumDiscLocationInfo = albumDiscLocationInfos.get(0);
        }
      }
      
      // Build the command to start the media player.
      // First argument is the MPC-HC player executable. Then for each album folder a '/play' argument followed by the folder.
      List<String> commandArguments = new ArrayList<>();
      commandArguments.add(MediaRegistry.mediaPlayerClassicExecutable);
      LOGGER.info("albumDiscLocationInfo: " + albumDiscLocationInfo.toString());
      commandArguments.add("/play ");
      commandArguments.add(albumDiscLocationInfo.getAlbumFolderName());
      
      try {
        mediaPlayerProcess = new ProcessBuilder(commandArguments).start();
        currentlyPlayingAlbum = album;
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    } else if (MediaDbUtil.haveAlbumPartlyOnDisc(album)) {
      /*
       * If there are track of an album available, we have the following options:
       * - the album is an Excerpt album
       * - the tracks are part of a My Compilation album
       * - the tracks are part of a Collection
       * In all cases we play the available tracks.
       * If the tracks are available via the trackDiscLocationMap, they are played.
       */
      
      // Build the command to start the media player.
      // First argument is the MPC-HC player executable. Then for each track ....
      List<String> commandArguments = new ArrayList<>();
      commandArguments.add(MediaRegistry.mediaPlayerClassicExecutable);        

//      List<DiscAndTrackNrs> tracks = MediaDbUtil.getAlbumTracksIHave(album, true);
      
      for (Disc disc: album.getDiscs()) {
        for (TrackReference trackReference: disc.getTrackReferences()) {
          Track track = trackReference.getTrack();
          // TODO follow references
          if (track == null) {
            LOGGER.severe("track is null");
          }
          Path trackPath = trackDiscLocationMap.get(track);
          if (trackPath != null) {
            LOGGER.severe("file: " + trackPath.toAbsolutePath().toString());
            commandArguments.add("/play ");
            commandArguments.add(trackPath.toAbsolutePath().toString());
          }
        }
      }

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

      // Only start the player if at least one track is found.
      if (commandArguments.size() > 1) {
        try {
          mediaPlayerProcess = new ProcessBuilder(commandArguments).start();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }

    } else if (album.isSetMyInfo()  &&  (MediaDbAppUtil.getReferredAlbum(album.getMyInfo()) != null)) {
      // If there is an album reference, this usually refers to a compilation which contains the tracks that I have of this album.
      Album referredAlbum = MediaDbAppUtil.getReferredAlbum(album.getMyInfo());
      if (referredAlbum != null) {
        if (findObject(referredAlbum)) {
          openObject(referredAlbum);
        }
        return;
      }
    } else if (MediaDbUtil.haveAlbumOnCdda(album)) {
      componentFactory.createInformationDialog("Play CD in CD player", "You have this album on CD", "So put the CD in your CD player and press 'Play'").show();
    } else {
      return;
    }

  }
  
  private void playDisc(Album album) {
    AlbumOnDiscInfo albumDiscLocationInfo = discToPlay.getValue();
    
    // Build the command to start the media player.
    // First argument is the MPC-HC player executable. Then for each album folder a '/play' argument followed by the folder.
    List<String> commandArguments = new ArrayList<>();
    commandArguments.add(MediaRegistry.mediaPlayerClassicExecutable);
    LOGGER.severe("albumDiscLocationInfo: " + albumDiscLocationInfo.toString());
    commandArguments.add("/play ");
    commandArguments.add(albumDiscLocationInfo.getAlbumFolderName());
    
    try {
      mediaPlayerProcess = new ProcessBuilder(commandArguments).start();
      currentlyPlayingAlbum = album;
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    LOGGER.severe("Playing disc");
    
    discToPlay = new SimpleObjectProperty<>();

  }

  protected void importTracks(Album album) {
    
    LOGGER.severe("Importing album: " + album.getTitle());
    ImportAlbumTracksWindow importAlbumTracksWindow = new ImportAlbumTracksWindow(customization, mediaDb, album);
    importAlbumTracksWindow.show();
  }
}


/**
 * This class is the table descriptor for the AlbumsTable.
 */
class AlbumsTableDescriptor extends EObjectTableDescriptor<Album> {
  private static final int IMAGE_HEIGHT = 60;
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;

  /*
   * Column descriptors which have to be adapted in the constructor.
   */
  private EObjectTableColumnDescriptorCustom<Album> playColumnDescriptor = new EObjectTableColumnDescriptorCustom<Album>(null, "Play", null, false, true, null);
  private EObjectTableColumnDescriptorCustom<Album> frontImagesColumnDescriptor = new EObjectTableColumnDescriptorCustom<Album>(MEDIA_DB_PACKAGE.getAlbum_ImagesFront(), "Front", null, false, true, null);
  private EObjectTableColumnDescriptorCustom<Album> insideImagesColumnDescriptor = new EObjectTableColumnDescriptorCustom<Album>(MEDIA_DB_PACKAGE.getAlbum_ImagesFrontInside(), "Inside", null, false, true, null);
  private EObjectTableColumnDescriptorCustom<Album> backImagesColumnDescriptor = new EObjectTableColumnDescriptorCustom<Album>(MEDIA_DB_PACKAGE.getAlbum_ImagesBack(), "Back", null, false, true, null);
  private EObjectTableColumnDescriptorCustom<Album> labelImagesColumnDescriptor = new EObjectTableColumnDescriptorCustom<Album>(MEDIA_DB_PACKAGE.getAlbum_ImagesLabel(), "Label", null, false, true, null);
  private EObjectTableColumnDescriptorCustom<Album> albumReferenceColumnDescriptor = new EObjectTableColumnDescriptorCustom<Album>(MEDIA_DB_PACKAGE.getAlbum_MyInfo(), "Reference", null, false, true, null);

  
  /*
   * The complete list of column descriptors
   */
  private List<EObjectTableColumnDescriptorAbstract<Album>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<Album>>of(
      playColumnDescriptor,
      new EObjectTableColumnDescriptorBasic<Album>(MEDIA_DB_PACKAGE.getAlbum_Artist(), "Artist", false, true),
      new EObjectTableColumnDescriptorBasic<Album>(MEDIA_DB_PACKAGE.getAlbum_Title(), "Title", false, true),
      new EObjectTableColumnDescriptorBasic<Album>(MEDIA_DB_PACKAGE.getAlbum_ReleaseDate(), "Release date", false, true),
      frontImagesColumnDescriptor,
      insideImagesColumnDescriptor,
      backImagesColumnDescriptor,
      labelImagesColumnDescriptor,
      new EObjectTableColumnDescriptorCheckBox<Album>(MEDIA_DB_PACKAGE.getAlbum_Compilation(), "Compilation album", false, true),
      new EObjectTableColumnDescriptorCheckBox<Album>(MEDIA_DB_PACKAGE.getAlbum_Soundtrack(), "Soundtrack", false, true),
      albumReferenceColumnDescriptor,
      new EObjectTableColumnDescriptorCustom<>(null, "Source", 300, false, true, column -> {
            TableCell<Album, Object> cell = new TableCell<>() {

              @Override
              protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || (item == null)) {
                  setText(null);
                }
                else {
                  Album album = (Album) item;
                  StringBuilder buf = new StringBuilder();
                  boolean commaNeeded = false;
                  
                  MyInfo myInfo = album.getMyInfo();
                  List<MediumInfo> mediumInfos = myInfo.getIHaveOn();
                  for (MediumInfo mediumInfo: mediumInfos) {
                    List<InformationType> sourceTypes = mediumInfo.getSourceTypes();
                    for (InformationType sourceType: sourceTypes) {
                      if (commaNeeded) {
                        buf.append(", ");
                      }
                      buf.append(sourceType.getName());
                      commaNeeded = true;
                    }
                  }
                  
                  Set<InformationType> sourceTypesSet = new HashSet<>();
                  for (Disc disc: album.getDiscs()) {
                    for (TrackReference trackReference: disc.getTrackReferences()) {
                      MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
                      if (myTrackInfo != null) {
                        List<MediumInfo> mediumInfos2 = myTrackInfo.getIHaveOn();
                        for (MediumInfo mediumInfo: mediumInfos2) {
                          List<InformationType> sourceTypes2 = mediumInfo.getSourceTypes();
                          for (InformationType sourceType2: sourceTypes2) {
                            sourceTypesSet.add(sourceType2);
                          }
                        }
                      }
                    }
                  }
                  for (InformationType sourceType3: sourceTypesSet) {
                    if (commaNeeded) {
                      buf.append(", ");
                    }
                    buf.append("tr:" + sourceType3.getName());
                    commaNeeded = true;
                  }
                  

                  setText(buf.toString());
                }
              }
            };

            return cell;
          })
  );
  
  @SuppressWarnings("serial")
  private static Map<Operation, TableRowOperationDescriptor<Album>> rowOperations = new HashMap<>() {
    {
      put(Operation.OPEN, new TableRowOperationDescriptor<>("Play"));
      put(Operation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete album"));
    }
  };

  private CustomizationFx customization;
  private MediaDb mediaDb;
  private Map<Track, Path>  trackDiscLocationMap;
  private AlbumReferenceCellFactory albumReferenceCellFactory;

  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   */
  AlbumsTableDescriptor(CustomizationFx customization, MediaDb mediaDb, Map<Track, Path> trackDiscLocationMap) {
    super();
    this.customization = customization;
    this.mediaDb = mediaDb;
    this.trackDiscLocationMap = trackDiscLocationMap;
    
    MyInfoPlayCellFactory myInfoPlayCellFactory = new MyInfoPlayCellFactory(customization, IMAGE_HEIGHT);
    playColumnDescriptor.setCellFactory(myInfoPlayCellFactory);
    
    ImageListCellFactory imageListCellFactory = new ImageListCellFactory(customization, IMAGE_HEIGHT, MediaRegistry.albumInfoDirectory);
    frontImagesColumnDescriptor.setCellFactory(imageListCellFactory);
    insideImagesColumnDescriptor.setCellFactory(imageListCellFactory);
    backImagesColumnDescriptor.setCellFactory(imageListCellFactory);
    labelImagesColumnDescriptor.setCellFactory(imageListCellFactory);
    
    albumReferenceCellFactory = new AlbumReferenceCellFactory(customization, IMAGE_HEIGHT);
    albumReferenceColumnDescriptor.setCellFactory(albumReferenceCellFactory);
    
    setComparator(new AlbumComparator());
    setColumnDescriptors(columnDescriptors);
    
    rowOperations.put(Operation.EXTENDED_OPERATION, new TableRowOperationDescriptor<>("Edit album", (BiConsumer<List<Album>, Album>) this::editAlbum));
    setRowOperations(rowOperations);
  }
  
  public void setAlbumsTable(AlbumsTable albumsTable) {
    albumReferenceCellFactory.setAlbumsTable(albumsTable);
  }
  
  private void editAlbum(List<Album> albums, Album album) {
    new AlbumEditor(customization, mediaDb, trackDiscLocationMap).runEditor().setObject(album);
  }
}

/**
 * A Comparator to sort the albums.
 */
class AlbumComparator implements Comparator<Album> {

  @Override
  public int compare(Album album1, Album album2) {
    int compareResult = 0;
    
    if (album1.getArtist() == null  ||  !album1.getArtist().isSetName()) {
      compareResult = -1;
    } else if (album2.getArtist() == null || !album2.getArtist().isSetName()) {
      compareResult = 1;
    } else {
      compareResult = album1.getArtist().getName().compareTo(album2.getArtist().getName());
    }
    if (compareResult == 0) {
      FlexDate releaseDate1;
      if (MediaDbUtil.isOwnCompilationAlbum(album1)) {
        releaseDate1 = new FlexDate(9999);
      } else {
        releaseDate1 = album1.getReleaseDate();
      }
      FlexDate releaseDate2;
      if (MediaDbUtil.isOwnCompilationAlbum(album2)) {
        releaseDate2 = new FlexDate(9999);
      } else {
        releaseDate2 = album2.getReleaseDate();
      }
      if (releaseDate1 == null  &&  releaseDate2 != null) {
        compareResult = -1;
      } else if (releaseDate1 != null  &&  releaseDate2 == null) {
        compareResult = 1;
      } else {
        compareResult = releaseDate1.compareTo(releaseDate2);
      }
    }
    return compareResult;
  }
  
}

/**
 * A factory to create a 'Play' cell, based on the 'MyInfo' of the album.
 */
class MyInfoPlayCellFactory implements Callback<TableColumn<Album, Object>, TableCell<Album, Object>> {
  private CustomizationFx customization;
  private int imageHeight;
  
  public MyInfoPlayCellFactory(CustomizationFx customization, int imageHeight) {
    this.customization = customization;
    this.imageHeight = imageHeight;
  }

  @Override
  public TableCell<Album, Object> call(TableColumn<Album, Object> arg0) {
    return new MyInfoPlayCell(customization, imageHeight);
  }
  
}

/**
 * A 'Play' table cell
 */
class MyInfoPlayCell extends TableCell<Album, Object> {
  private static final Logger LOGGER = Logger.getLogger(MyInfoPlayCell.class.getName());

//  static DefaultAppResourcesFx m = new DefaultAppResourcesFx();
  
  private MediaAppResourcesFx mediaAppResources;
  private ImageView imageView;

  public MyInfoPlayCell(CustomizationFx customization, int imageHeight) {
    LOGGER.info("<=>");
    Background background = customization.getComponentFactoryFx().getPanelBackground();
    if (background != null) {
      setBackground(background);
    }
//    AppResourcesFx appResources = customization.getResources();
    mediaAppResources = (MediaAppResourcesFx) customization.getResources();
//    Image i = m.getAttentionImage(ImageSize.SIZE_0);
    imageView = new ImageView();
    imageView.setFitHeight(imageHeight);
    imageView.setPreserveRatio(true);
//    imageView.setImage(i);
    setGraphic(imageView);
    
    addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
      MyInfoPlayCell cell = (MyInfoPlayCell) e.getSource();
      Album album = (Album) cell.getItem();
      LOGGER.info("Mouse clicked on: " + album.toString());
      ((EObjectTable<Album>) getTableView()).openObject(album);
      e.consume();
    });
    
    addEventHandler(DoubleClickEventDispatcher.MOUSE_DOUBLE_CLICKED, e -> {
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
      if (!(item instanceof Album)) {
        throw new IllegalArgumentException("item shall be of type 'Album', but is of type '" + item.getClass().getName() + "'");
      }
      Album album = (Album) item;
      if ("Bigger, Better, Faster, More!".equals(album.getTitle())) {
        LOGGER.severe("STOP");
      }
      
      MyInfo myInfo = album.getMyInfo();
      String tooltipText = null;
      
      if (MediaDbAppUtil.iHaveToGetAlbumOrTracks(album)) {
        imageView.setImage(mediaAppResources.getIWantIcon());
        tooltipText = "I want to have this album (partly), so it cannot be played now";
      } else if (MediaDbUtil.haveAlbumOnDisc(album)) {
        imageView.setImage(mediaAppResources.getPlayIcon());
        tooltipText = "I have this album on disc, so it can be played";
      } else if (MediaDbUtil.haveAlbumPartlyOnDisc(album)) {
        imageView.setImage(mediaAppResources.getPlayPartlyIcon());
        tooltipText = "I have this album partly";
      } else if (MediaDbUtil.haveAlbumOnCdda(album)) {
        imageView.setImage(mediaAppResources.getCddaIcon());
        tooltipText = "I have this album on CD, so it cannot be played here.";
      } else if (MediaDbUtil.haveAlbumOnSacd(album)) {
        imageView.setImage(mediaAppResources.getSacdIcon());
        tooltipText = "I have this album on Super Audio CD, so it cannot be played here.";
      } else if (MediaDbUtil.getIWant(album) == IWant.NO) {
        LOGGER.fine("IWant is NO");
        imageView.setImage(mediaAppResources.getNotInMediaDbIcon());
        tooltipText = "I don't have this album and I don't want this album, so it cannot be played.";
      } else if (MediaDbUtil.getIWant(album) == IWant.DONT_KNOW) {
        imageView.setImage(mediaAppResources.getDontKnowIcon());
        tooltipText = "I don't have this album and I don't know whether I want to have it, so it cannot be played now.";
      } else if (MediaDbAppUtil.getReferredAlbum(myInfo) != null) {
        imageView.setImage(mediaAppResources.getReferenceIcon());
        tooltipText = "Referentie. TODO";
      } else {
        LOGGER.severe("Unknown play type for album: " + album.getArtistAndTitle() + ", myInfo=" + (myInfo != null ? myInfo.toString() : "null"));
      }
      
      setGraphic(imageView);
      setText(null);
      if (tooltipText != null) {
        Tooltip tooltip = new Tooltip(tooltipText);
        setTooltip(tooltip);
      } else {
        setTooltip(null);
      }
    }
    LOGGER.info("<=");
  }
}


/**
 * A factory to create a 'List of Images' cell.
 */
class ImageListCellFactory implements Callback<TableColumn<Album, Object>, TableCell<Album, Object>> {
  private CustomizationFx customization;
  private int imageHeight;
  private String albumInfoDirectory;
  
  public ImageListCellFactory(CustomizationFx customization, int imageHeight, String albumInfoDirectory) {
    this.customization = customization;
    this.imageHeight = imageHeight;
    this.albumInfoDirectory = albumInfoDirectory;
  }

  @Override
  public TableCell<Album, Object> call(TableColumn<Album, Object> arg0) {
    return new ImageListCell(customization, imageHeight, albumInfoDirectory);
  }
  
}

/**
 * A table cell for a list of Images.
 */
class ImageListCell extends TableCell<Album, Object> {
  private static final Logger LOGGER = Logger.getLogger(ImageListCell.class.getName());
  
  private int imageHeight;
  private String albumInfoDirectory;

  public ImageListCell(CustomizationFx customization, int imageHeight, String albumInfoDirectory) {
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
      if (!(item instanceof List)) {
        throw new IllegalArgumentException("item shall be List of Strings, but is of type '" + item.getClass().getName() + "'");
      }
      @SuppressWarnings("unchecked")
      List<String>  imageFileNames = (List<String>) item;
      
      HBox hbox = new HBox(15);
      for (String imageFileName: imageFileNames) {
        Path imagePath = Paths.get(albumInfoDirectory, imageFileName);
        Image image = new Image("file:" + imagePath.toAbsolutePath().toString(), -1, imageHeight,  true, true);
                
        ImageView imageView = new ImageView(image);
        hbox.getChildren().add(imageView);
      }
            
      setGraphic(hbox);
      setText(null);
    }
    LOGGER.info("<=");
  }
}

/**
 * A factory to create a 'AlbumReference' cell, based on the 'MyInfo' of the album.
 */
class AlbumReferenceCellFactory implements Callback<TableColumn<Album, Object>, TableCell<Album, Object>> {
  private CustomizationFx customization;
  private AlbumsTable albumsTable;
  private int imageHeight;
  
  public AlbumReferenceCellFactory(CustomizationFx customization, int imageHeight) {
    this.customization = customization;
    this.imageHeight = imageHeight;
  }

  @Override
  public TableCell<Album, Object> call(TableColumn<Album, Object> arg0) {
    return new AlbumReferenceCell(customization, this, imageHeight);
  }
  
  public void setAlbumsTable(AlbumsTable albumsTable) {
    this.albumsTable = albumsTable;
  }
  
  public AlbumsTable getAlbumsTable() {
    return albumsTable;
  }
}

/**
 * A 'Reference' table cell
 */
class AlbumReferenceCell extends TableCell<Album, Object> {
  private static final Logger LOGGER = Logger.getLogger(AlbumReferenceCell.class.getName());
  
  private MediaAppResourcesFx mediaAppResources;
  private ImageView imageView;
  private AlbumReferenceCellFactory albumReferenceCellFactory;
  private Album album;

  public AlbumReferenceCell(CustomizationFx customization, AlbumReferenceCellFactory albumReferenceCellFactory, int imageHeight) {
    LOGGER.info("<=>");
    AppResourcesFx appResources = customization.getResources();
    this.albumReferenceCellFactory = albumReferenceCellFactory;
    mediaAppResources = (MediaAppResourcesFx) appResources;
    imageView = new ImageView();
    imageView.setImage(mediaAppResources.getReferenceIcon());
    imageView.setFitHeight(24);
    imageView.setPreserveRatio(true);
    setGraphic(imageView);
    addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleMouseClickedEvent(this, e));
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
      if (!(item instanceof MyInfo)) {
        throw new IllegalArgumentException("item shall be of type 'MyInfo', but is of type '" + item.getClass().getName() + "'");
      }
      MyInfo myInfo = (MyInfo) item;
      
      album = null;
      StringBuilder buf = new StringBuilder();
      if (myInfo != null) {
        boolean first = true;
        for (Album referencedAlbum: myInfo.getAlbumReferences()) {
          if (first) {
            first = false;
            album = referencedAlbum;
          } else {
            buf.append(", ");
          }
          buf.append(referencedAlbum.getTitle());
        }
      }
      
      if (album != null) {
        setGraphic(imageView);
      } else {
        setGraphic(null);
      }
      setText(buf.toString());
      Tooltip tooltip = new Tooltip("Album references");
      setTooltip(tooltip);
    }
    LOGGER.info("<=");
  }
  
  private void handleMouseClickedEvent(AlbumReferenceCell cell, MouseEvent e) {
    LOGGER.severe("=>");
    if (album != null) {
      AlbumsTable albumsTable = albumReferenceCellFactory.getAlbumsTable();
      if (albumsTable != null) {
        boolean result = albumsTable.findObject(album);
        LOGGER.severe("album found = " + result);
      }
    }
  }
}


/**
 * Album disc player
 */
class AlbumDiscPlayer implements Runnable {
//  private Album album;
  
  public AlbumDiscPlayer(Album album) {
//    this.album = album;
  }

  @Override
  public void run() {
    // TODO Auto-generated method stub
    
  }
  
}

