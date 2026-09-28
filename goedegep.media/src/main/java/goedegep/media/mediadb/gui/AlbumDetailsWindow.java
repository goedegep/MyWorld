package goedegep.media.mediadb.gui;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.media.common.IAlbumDetailsWindow;
import goedegep.media.common.IMediaDbService;
import goedegep.media.common.MediaRegistry;
import goedegep.media.mediadb.gui.albumeditor.AlbumEditor;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.AlbumType;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.DiscAndTrackNrs;
import goedegep.media.mediadb.model.InformationType;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Player;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.media.mediadb.model.util.MediaDbUtil;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.objectselector.ObjectSelectionListener;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// TODO handle collection per track
public class AlbumDetailsWindow extends JfxStage implements IAlbumDetailsWindow {
  private static final Logger LOGGER = Logger.getLogger(AlbumDetailsWindow.class.getName());
  private static final FlexDateFormat FDF = new FlexDateFormat();
  
  /**
   * The media registry.
   */
  private MediaRegistry mediaRegistry;
  
  /**
   * The media database service.
   */
  private IMediaDbService mediaDbService;
  
  /**
   * A map with the locations of the tracks on disc.
   */
  private Map<Track, Path> trackDiscLocationMap;
  
  /**
   * The albums table, used to synchronize the selection of the album in this window with the selection in the albums table.
   */
  private AlbumsTable albumsTable;
  
  /**
   * Shows the album title.
   */
  private TextField albumTitleTextField;
  
  /**
   * Shows the album artist name.
   */
  private TextField albumArtistTextField;
  
  /**
   * Shows the album issue date (release date).
   */
  private TextField albumIssueDateTextField;
  
  /**
   * Shows the album id.
   */
  private TextField albumIdTextField;
  
  /**
   * Shows the album type.
   */
  private TextField albumTypeTextField;
  
  /**
   * Shows the album front, front inlay, back and label pictures
   */
  private HBox picturesHBox;
  
  /**
   * Shows information as HTML
   */
  private WebView webView;
    
  /**
   * Shows the tracks per disc in tables
   */
  private VBox discTracksVBox;
 
  /**
   * The album for which the information is shown in this window.
   */
  private Album album;
  
  /**
   * Listener for the albums table, to synchronize the selection of the album in this window with the selection in the albums table.
   */
  private ObjectSelectionListener<Album> albumsTableListener = null;
  
  /**
   * The stage for showing a large (full size) picture.
   */
  private Stage currentLargePictureStage;

  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   * @param mediaDbService the media service.
   * @param trackDiscLocationMap a map with the locations of the tracks on disc.
   * 
   */
  public AlbumDetailsWindow(CustomizationFx customization, IMediaDbService mediaDbService, Map<Track, Path> trackDiscLocationMap, EObjectTable<Album> albumsTable) {
    super(customization, "Album details");
    
    this.mediaDbService = mediaDbService;
    this.trackDiscLocationMap = trackDiscLocationMap;
    this.albumsTable = (AlbumsTable) albumsTable;
    mediaRegistry = MediaRegistry.getInstance();

    createGUI();
  }

  @Override
  public void setAlbum(Album album) {
    this.album = album;
    
    updateAlbumDetails();
  }
  
  /**
   * Create the GUI.
   */
  private void createGUI() {

    /*
     * Main pane is a BorderPane.
     * Center is a VBox with:
     *   a GridPane with general information (title, artist, issue date, album id, album type)
     *   an HBox with the album pictures (front, front inlay, back, label)
     *   an HBox with a WebView with HTML information showing the description, players, my info, etc.
     *   a GridPane with the issued on media information
     *   a VBox with the disc track tables
     */

    BorderPane mainPane = new BorderPane();
    
    VBox centerPane = componentFactory.createVBox(18);
    
    /*
     * General information
     */
    GridPane gridPane = componentFactory.createGridPane(10.0, 10.0, 10.0);
    
    // First row: 'Album: <album-title>'   +  'Open in Album Editor' button + 'Sync to album table checkbox'
    Label label = componentFactory.createLabel("Title:");
    gridPane.add(label, 0, 0);
    
    albumTitleTextField = componentFactory.createTextField(600, null);
    albumTitleTextField.setEditable(false);
    gridPane.add(albumTitleTextField, 1, 0);
    
    Button editButton = componentFactory.createButton("Open in Album Editor", "Open edit window");
    editButton.setOnAction((_) -> {
      AlbumEditor albumEditor = AlbumEditor.newInstance(customization, mediaDbService);
      albumEditor.setObject(album);
      albumEditor.show();
    });
    gridPane.add(editButton, 3, 0);
    
    CheckBox syncToAlbumsTableCheckBox = componentFactory.createCheckBox("Synchronize to Albums Table", false);
    syncToAlbumsTableCheckBox.setOnAction((_) -> {
      setSyncToAlbumsTable(syncToAlbumsTableCheckBox.isSelected());
    });
    gridPane.add(syncToAlbumsTableCheckBox, 4, 0);

    // Second row: 'Artist: <artist-name>'
    label = componentFactory.createLabel("Artist:");
    gridPane.add(label, 0, 1);
    
    albumArtistTextField = componentFactory.createTextField(600, null);
    albumArtistTextField.setEditable(false);
    gridPane.add(albumArtistTextField, 1, 1);
    
    // Third row: 'Issue date: <album-issue-date>'
    label = componentFactory.createLabel("Issued:");
    gridPane.add(label, 0, 2);
    
    albumIssueDateTextField = componentFactory.createTextField(600, null);
    albumIssueDateTextField.setEditable(false);
    gridPane.add(albumIssueDateTextField, 1, 2);
    
    // Fourth row: 'Album id: <album-id>'
    label = componentFactory.createLabel("Album id:");
    gridPane.add(label, 0, 3);
    
    albumIdTextField = componentFactory.createTextField(600, null);
    albumIdTextField.setEditable(false);
    gridPane.add(albumIdTextField, 1, 3);
    
    // Fifth row: 'Album type: <album-type>'
    label = componentFactory.createLabel("Album tyoe");
    gridPane.add(label, 0, 4);
    
    albumTypeTextField = componentFactory.createTextField(600, null);
    albumTypeTextField.setEditable(false);
    gridPane.add(albumTypeTextField, 1, 4);
    
    centerPane.getChildren().add(gridPane);
    
    /*
     * pictures
     */
    picturesHBox = componentFactory.createHBox(10.0, 10.0);
    picturesHBox.setMinHeight(300);
    picturesHBox.setPrefHeight(300);
    centerPane.getChildren().add(picturesHBox);
    
    /**
     * HTML Information
     */
    HBox hBox = componentFactory.createHBox();
    hBox.setPadding(new Insets(10.0));
    hBox.setMaxHeight(200);
    webView = new WebView();
    hBox.getChildren().add(webView);
    centerPane.getChildren().add(hBox);
    
    /*
     * Issued on media
     */
    gridPane = componentFactory.createGridPane(10.0, 10.0, 10.0);
    
    label = componentFactory.createLabel("Issued on:");
    gridPane.add(label, 0, 0);
    
    centerPane.getChildren().add(gridPane);
    
    /*
     * Disc tracks tables.
     */
    discTracksVBox = componentFactory.createVBox(10.0, 10.0);
    centerPane.getChildren().add(discTracksVBox);
    
    mainPane.setCenter(centerPane);
   

    Scene scene = new Scene(mainPane, 1200, 1200);
    setScene(scene);
    show();
  }
  
  /**
   * Start/stop coupling this window to the selected album in the albums table.
   * 
   * @param syncToAlbumsTable if true, synchronize with the albums table, if false stop synchronization (if active).
   */
  private void setSyncToAlbumsTable(boolean syncToAlbumsTable) {
    if (syncToAlbumsTable) {
      if (albumsTableListener == null) {
        albumsTableListener = (_, album) -> setAlbum(album);
      }
      albumsTable.addObjectSelectionListener(albumsTableListener);
      setAlbum(albumsTable.getSelectedObject());
    } else {
      if (albumsTableListener != null) {
        albumsTable.removeObjectSelectionListener(albumsTableListener);
      }
    }
  }
  
  /**
   * Update all album details, for a new value of {@code album}
   */
  private void updateAlbumDetails() {
    updateGeneralInformation();
    updatePicturesPanel();
    updateHtmlInformation();
    updateIssuedOnInformation();
    updateDiscsPanel();
  }
  
  /**
   * Update the general information of the {@code album}.
   */
  private void updateGeneralInformation() {
    if (album != null) {
      if (album.isSetTitle()) {
        albumTitleTextField.setText(album.getTitle());
      } else {
        albumTitleTextField.setText("");
      }
      
      if (album.isSetArtist()  &&  album.getArtist() != null) {
        albumArtistTextField.setText(album.getArtist().getName());
      } else {
        albumArtistTextField.setText("");
      }
      
      if (album.isSetReleaseDate()) {
        albumIssueDateTextField.setText(FDF.format(album.getReleaseDate()));
      } else {
        albumIssueDateTextField.setText("");
      }
      
      if (album.isSetId()) {
        albumIdTextField.setText(album.getId());
      } else {
        albumIdTextField.setText("");
      }
      
      String albumType = "";
      MyInfo myInfo = album.getMyInfo();
      if (myInfo != null) {
        if (myInfo.isSetAlbumType()) {
          switch (myInfo.getAlbumType()) {
          case NORMAL_ALBUM:
            albumType = "Standard album";
            break;
            
          case COMPILATION_ALBUM:
            albumType = "Compilation album";
            break;
            
          case OWN_COMPILATION_ALBUM:
            albumType = "Own compilation album";
            break;
            
          case VARIOUS_ARTISTS_ALBUM:
            albumType = "Various artists album";
            break;
          }
        };
      }
      albumTypeTextField.setText(albumType);
    } else {
      albumTitleTextField.setText("");
      albumArtistTextField.setText("");
      albumIssueDateTextField.setText("");
      albumIdTextField.setText("");
      albumTypeTextField.setText("");
    }
  }
  
  /**
   * Update the pictures in the picturesHBox for the current Album.
   * <p>
   * The panel is cleared and if there is a current album, the front, front inside, back and label
   * pictures are added to the panel.
   */
  private void updatePicturesPanel() {
    picturesHBox.getChildren().clear();
    
    if (album != null) {
      List<String> imageFileNames = new ArrayList<>();
      imageFileNames.addAll(album.getImagesFront());
      imageFileNames.addAll(album.getImagesFrontInside());
      imageFileNames.addAll(album.getImagesBack());
      imageFileNames.addAll(album.getImagesLabel());

      for (String imageFileName: imageFileNames) {
        String imageURL = "file:" + mediaRegistry.getMusicDataDirectory() + "\\Albums\\" + imageFileName;
        LOGGER.info("Going to read image from URL: " + imageURL);
        Image image = new Image(imageURL, 200.0, 200.0, true, true);
        ImageView imageView = new ImageView(image);
        imageView.setOnMouseEntered(e -> showLargePicture(e, imageURL));
        imageView.setOnMouseExited(_ -> {
          if (currentLargePictureStage != null) {
            currentLargePictureStage.close();
          }
        });
        picturesHBox.getChildren().add(imageView);
      }
    }
  }
  
  /**
   * Update the information about the album that is presented as Html.
   * <p>
   * The following in formation is shown (if available):
   * <ul>
   * <li>the description title (a header)</li>
   * <li>the description</li>
   * <li>the players (with a header "Meewerkende artiesten:").<br/>
   *     For each player the name is shown followed by the instruments played.</li>
   * <li>My Information header</li>
   *   <ul>
   *   <li>I want information</li>
   *   <li>I have on media information</li>
   *   <li>I want information</li>
   *   </ul>
   * </ul>
   * 
   * This information is show in the {@code webView}.
   */
  private void updateHtmlInformation() {
    if (album == null) {
      webView.getEngine().loadContent("");
      return;
    }

    StringBuilder buf = new StringBuilder();
    
    // Description title
    if (album.isSetDescriptionTitle()) {
      buf.append("<h2>");
      buf.append(album.getDescriptionTitle());
      buf.append("</h2>");
    }
    
    // Description
    if (album.isSetDescription()) {
      buf.append(album.getDescription());
    }
    
    // Players
    List<Player> players = album.getPlayers();
    if (players.size() > 0) {
      buf.append("<h3>");
      buf.append("Meewerkende artiesten:");
      buf.append("</h3>");

      for (Player player: players) {
        Artist artist = player.getArtist();
        if (artist != null) {
          buf.append("<b>");
          buf.append(artist.getName());
          buf.append("</b>");
          boolean first = true;
          for (String instrument: player.getInstruments()) {
            if (first) {
              buf.append(": ");
              first = false;
            } else {
              buf.append(", ");
            }
            buf.append(instrument);
          }
        }
        buf.append("<br/>");
      }
    }

    //
    // My Information
    //
    buf.append("<h2>My Information</h2>");

    // IWant
    switch (MediaDbUtil.getIWant(album)) {
    case DONT_KNOW:
      buf.append("I don't know yet whether I want to have this album.");
      buf.append("<br/>");
      break;

    case NO:
      buf.append("I don't want to have this album.");
      buf.append("<br/>");
      break;

    case YES:
      buf.append("I want to have this album (so I have to download it).");
      buf.append("<br/>");
      break;

    case NOT_SET:
      // no action
      break;
    }

    // I have on media
    Set<MediumInfo> iHaveOnMedia = MediaDbUtil.haveAlbumOnMediumTypes(album);
    if (!iHaveOnMedia.isEmpty()) {
      boolean first = true;
      buf.append("I have this album on: ");
      for (MediumInfo iHaveOnMedium: iHaveOnMedia) {
        if (first) {
          first = false;
        } else {
          buf.append(", ");
        }
        buf.append(GuiUtils.createMediumText(iHaveOnMedium.getMediumType()));
        if (iHaveOnMedium.isSetSourceType()) {
          buf.append(" (from ");
          InformationType sourceType = iHaveOnMedium.getSourceType();
          if (sourceType != InformationType.NOT_SET) {
            buf.append(sourceType.getLiteral());
          } else {
            buf.append("??");
          }
          buf.append(")");
        }
        if (iHaveOnMedium.isSetSourceBitRate()) {
          buf.append(" {bit rate ");
          buf.append(iHaveOnMedium.getSourceBitRate());
          buf.append("}");
        }
      }
      buf.append("<br/>");
    }

    // I want
//    if (iHaveOnMedia.isEmpty()) {
      List<DiscAndTrackNrs> discAndTrackNrsList = MediaDbUtil.getAlbumTracksIWant(album);
      if (!discAndTrackNrsList.isEmpty()) {
        buf.append("Tracks/discs I want to have: ");
        boolean first = true;
        for (DiscAndTrackNrs discAndTrackNrs: discAndTrackNrsList) {
          Integer discNr = null;
          if (discAndTrackNrs.isSetDiscNr()) {
            discNr = discAndTrackNrs.getDiscNr();
          }
          for (int trackNr: discAndTrackNrs.getTrackNrs()) {
            if (first == true) {
              first = false;
            } else {
              buf.append(", ");
            }
            if ((discNr != null)  && (discNr != -1)) {
              buf.append(discNr);
              buf.append(".");
            }
            buf.append(trackNr);
          }
        }
        buf.append("<br/>");
      }

      discAndTrackNrsList = MediaDbUtil.getAlbumTracksIHave(album, false);
      if (!discAndTrackNrsList.isEmpty()) {
        buf.append("Track/discs I have: ");
        boolean first = true;
        for (DiscAndTrackNrs discAndTrackNrs: discAndTrackNrsList) {
          Integer discNr = null;
          if (discAndTrackNrs.isSetDiscNr()) {
            discNr = discAndTrackNrs.getDiscNr();
          }
          if (discNr != null  &&  discAndTrackNrs.getTrackNrs().size() == 0) {
            if (first == true) {
              first = false;
            } else {
              buf.append(", ");
            }
            buf.append("disc ");
            buf.append(discNr);
          } else {
            for (int trackNr: discAndTrackNrs.getTrackNrs()) {
              if (first == true) {
                first = false;
              } else {
                buf.append(", ");
              }
              if ((discNr != null)  && (discNr != -1)) {
                buf.append(discNr);
                buf.append(".");
              }
              buf.append(trackNr);
            }
          }
        }
        buf.append("<br/>");
      }
//    }

    if (album.isSetMyInfo()) {

      MyInfo myInfo = album.getMyInfo();
      if (myInfo.isSetMyComments()) {
        buf.append("Remarks: ");
        buf.append(myInfo.getMyComments());
        buf.append("<br/>");
      }
      if (myInfo.isIveHadOnLP()) {
        buf.append("I've had the LP.");
        buf.append("<br/>");
      }
    }
    webView.getEngine().loadContent(buf.toString());
  }
  
  /**
   * Update the issued on information for the current {@code album}
   */
  private void updateIssuedOnInformation() {
    // TODO
  }
  
  /**
   * Update the discs panel (<code>discTracksVBox</code>).
   * <p>
   * The panel is cleared and then for each disc of the <code>Album</code> a {@link DiscTracksTable} is added to the panel.
   */
  private void updateDiscsPanel() {
    discTracksVBox.getChildren().clear();

    if (album != null) {
      List<Disc> discs = album.getDiscs();
      for (Disc disc: discs) {
        DiscTracksTable discTracksTable = new DiscTracksTable(customization, disc, trackDiscLocationMap);
        discTracksTable.setEditable(false);
        discTracksVBox.getChildren().add(discTracksTable);
      }
    }
  }
  
  /**
   * Show a picture, full size, in a separate <code>Stage</code> (without any header or border).
   * 
   * @param e the mouseEvent leading to showing the large size picture being shown
   * @param fileName file name of the picture to be shown.
   */
  private void showLargePicture(MouseEvent e, String fileName) {
    // determine where to show the picture: not where the mouse is now, but to the right or left, wherever there's the most space.
    double mouseX = e.getScreenX();
    
    Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    double screenWidth = screenBounds.getWidth();
    
    double largePictureStageX;
    if (mouseX < screenWidth / 2) {
      largePictureStageX = screenWidth / 2 + 20;
    } else {
      largePictureStageX = 20;
    }
    
    Image image = new Image(fileName);
    ImageView currentLargePicture = new ImageView(image);
    
    currentLargePictureStage = new Stage();
    currentLargePictureStage.initStyle(StageStyle.UNDECORATED);
    BorderPane pane = new BorderPane();
    pane.setCenter(currentLargePicture);
    currentLargePictureStage.setScene(new Scene(pane));
    currentLargePictureStage.setX(largePictureStageX);
    currentLargePictureStage.show();
  }
}
