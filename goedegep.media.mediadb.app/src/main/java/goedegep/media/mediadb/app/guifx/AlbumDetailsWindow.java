package goedegep.media.mediadb.app.guifx;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.media.app.MediaRegistry;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.DiscAndTrackNrs;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MediumType;
import goedegep.media.mediadb.model.MyCompilation;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.Player;
import goedegep.media.mediadb.model.InformationType;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.util.MediaDbUtil;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.objectselector.ObjectSelectionListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// TODO handle collection per track
public class AlbumDetailsWindow extends AlbumDetailsAbstract {
  private static final Logger LOGGER = Logger.getLogger(AlbumDetailsWindow.class.getName());
  private static final FlexDateFormat FDF = new FlexDateFormat();
  
  private CustomizationFx customization;
  private MediaDb mediaDb;  
  private Map<Track, Path> trackDiscLocationMap;
  private AlbumsTable albumsTable;
  private ComponentFactoryFx componentFactory;
  
  /**
   * Shows the album title.
   */
  private TextField albumTitleTextField;
  
  /**
   * Shows the album artist name.
   */
  private TextField albumArtistTextField;
  
  /**
   * Shows the album artist name.
   */
  private TextField albumIssueDateTextField;
  
  /**
   * Shows the album id.
   */
  private TextField albumIdTextField;
  
  /**
   * Shows the album front, front inlay, back and label pictures
   */
  private HBox picturesHBox;
  
  /**
   * Shows information as HTML
   */
  private WebView webView;
  
  /**
   * Shows on which media the album was issued
   */
  private TextField issuedOnMediaTextField;
  
  /**
   * Shows whether the album is a compilation album or not.
   */
  private CheckBox isCompilationAlbumCheckBox;
  
  /**
   * Shows whether the album is 'my own' compilation or not.
   */
  private CheckBox isOwnCompilationCheckBox;
  
  /**
   * Shows whether the album is a movie soundtrack or not.
   */
  private CheckBox isSoundTrackCheckBox;
  
  /**
   * Shows the tracks per disc in tables
   */
  private VBox discTracksVBox;
 
  private Album album;
  
  private ObjectSelectionListener<Album> albumsTableListener = null;
  
  private Stage currentLargePictureStage;

  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   */
  public AlbumDetailsWindow(CustomizationFx customization, MediaDb mediaDb, Map<Track, Path> trackDiscLocationMap, AlbumsTable albumsTable) {
    super("Album details", customization);
    
    this.customization = customization;
    this.mediaDb = mediaDb;
    this.trackDiscLocationMap = trackDiscLocationMap;
    this.albumsTable = albumsTable;
    componentFactory = customization.getComponentFactoryFx();

    createGUI();
  }

  /**
   * Change the <code>Album</code> for which the information is shown.
   * 
   * @param album the <code>Album</code> for which the information is shown in this window.
   */
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
     * Center is a VBox; the ...
     */

    BorderPane mainPane = new BorderPane();
    
    VBox centerPane = componentFactory.createVBox(18);
    
    /*
     * General information
     */
    GridPane gridPane = componentFactory.createGridPane(10.0, 10.0, 10.0);
    
    // First row: 'Album: <album-title>'   +  'Edit button' + 'Sync to album table checkbox'
    Label label = componentFactory.createLabel("Title:");
    gridPane.add(label, 0, 0);
    
    albumTitleTextField = componentFactory.createTextField(600, null);
    albumTitleTextField.setEditable(false);
    gridPane.add(albumTitleTextField, 1, 0);
    
    Button editButton = componentFactory.createButton("Open in Album Editor", "Open edit window");
    editButton.setOnAction((e) -> {
      new AlbumDetailsEditor(getCustomization(), mediaDb, trackDiscLocationMap, album);
    });
    gridPane.add(editButton, 3, 0);
    
    CheckBox syncToAlbumsTableCheckBox = componentFactory.createCheckBox("Synchronize to Albums Table", false);
    syncToAlbumsTableCheckBox.setOnAction((e) -> {
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
    
    centerPane.getChildren().add(gridPane);
    
    /*
     * pictures
     */
    picturesHBox = componentFactory.createHBox(10.0, 10.0);
    centerPane.getChildren().add(picturesHBox);
    
    /**
     * HTML Information
     */
    HBox hBox = componentFactory.createHBox();
    hBox.setPadding(new Insets(10.0));
    webView = new WebView();
    hBox.getChildren().add(webView);
    centerPane.getChildren().add(hBox);
    
    /*
     * Issued on media
     */
    gridPane = componentFactory.createGridPane(10.0, 10.0, 10.0);
    
    label = componentFactory.createLabel("Issued on:");
    gridPane.add(label, 0, 0);
    
    issuedOnMediaTextField = componentFactory.createTextField(600, null);
    gridPane.add(issuedOnMediaTextField, 1, 0);
    
    centerPane.getChildren().add(gridPane);
    
    /*
     * Checkboxes for 'compilation album', 'own compilation' and 'soundtrack'.
     */
    hBox = componentFactory.createHBox(10.0, 10.0);
    isCompilationAlbumCheckBox = componentFactory.createCheckBox("compilation album", false);
    isOwnCompilationCheckBox = componentFactory.createCheckBox("own compilation", false);
    isSoundTrackCheckBox = componentFactory.createCheckBox("soundtrack", false);
    hBox.getChildren().addAll(isCompilationAlbumCheckBox, isOwnCompilationCheckBox, isSoundTrackCheckBox);
    
    centerPane.getChildren().add(hBox);
    
    mainPane.setCenter(centerPane);
    
    /*
     * Disc tracks tables.
     */
    discTracksVBox = componentFactory.createVBox(10.0, 10.0);
    centerPane.getChildren().add(discTracksVBox);
    

    Scene scene = new Scene(mainPane, 1200, 1200);
    setScene(scene);
    show();
  }
  
  private void setSyncToAlbumsTable(boolean syncToAlbumsTable) {
    if (syncToAlbumsTable) {
      if (albumsTableListener == null) {
        albumsTableListener = new ObjectSelectionListener<Album>() {

          @Override
          public void objectSelected(Object source, Album album) {
            setAlbum(album);
          }

        };
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
   * Update all album details, for a new value of <code>album</code>
   */
  private void updateAlbumDetails() {
    updateGeneralInformation();
    updatePicturesPanel();
    updateHtmlInformation();
    updateIssuedOnPanel();
    updateCheckBoxes();
    updateDiscsPanel();
  }
  
  /**
   * Update the general information of the <code>album</code>.
   */
  private void updateGeneralInformation() {
    if (album != null) {
      if (album.isSetTitle()) {
        albumTitleTextField.setText(album.getTitle());
      } else {
        albumTitleTextField.setText("");
      }
      
      if (album.isSetArtist()) {
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
      
    } else {
      albumTitleTextField.setText("");
      albumArtistTextField.setText("");
      albumIssueDateTextField.setText("");
      albumIdTextField.setText("");
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
        String imagePathName = MediaRegistry.albumInfoDirectory + "\\" + imageFileName;
        LOGGER.info("Going to read image from file: " + imagePathName);
        Image image = new Image("file:" + imagePathName, 0.0, 200.0, true, true);
        ImageView imageView = new ImageView(image);
        imageView.setOnMouseEntered(e -> showLargePicture("file:" + imagePathName));
        imageView.setOnMouseExited(e -> {
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
   * This information is show in the <code>webView</code>.
   */
  private void updateHtmlInformation() {
    if (album != null) {
      StringBuilder buf = new StringBuilder();
      if (album.isSetDescriptionTitle()) {
        buf.append("<h2>");
        buf.append(album.getDescriptionTitle());
        buf.append("</h2>");
      }
      if (album.isSetDescription()) {
        LOGGER.severe("Description: " + album.getDescription());
        buf.append(album.getDescription());
      }
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
            buf.append(": ");
            boolean first = true;
            for (String instrument: player.getInstruments()) {
              if (first) {
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

      buf.append("<h2>Mijn informatie:</h2>");

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
          if (iHaveOnMedium.isSetSourceTypes()) {
            buf.append(" (van ");
            boolean firstSourceType = true;
            for (InformationType sourceType: iHaveOnMedium.getSourceTypes()) {
              if (firstSourceType) {
                firstSourceType = false;
              } else {
                buf.append(", ");
              }
              if (sourceType != InformationType.NOT_SET) {
                buf.append(sourceType.getLiteral());
              } else {
                buf.append("??");
              }
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
      
      if (iHaveOnMedia.isEmpty()) {
        List<DiscAndTrackNrs> discAndTrackNrsList = MediaDbUtil.getAlbumTracksIWant(album);
        if (!discAndTrackNrsList.isEmpty()) {
          buf.append("Nummers/discs die ik wil hebben: ");
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
          buf.append("Nummers/discs die ik heb: ");
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
      }

      if (album.isSetMyInfo()) {

        MyInfo myInfo = album.getMyInfo();
        if (myInfo.isSetMyComments()) {
          buf.append("Opmerkingen: ");
          buf.append(myInfo.getMyComments());
          buf.append("<br/>");
        }
        if (myInfo.isIveHadOnLP()) {
          buf.append("Ik heb de lp gehad.");
          buf.append("<br/>");
        }
      }
      webView.getEngine().loadContent(buf.toString());
    } else {
      webView.getEngine().loadContent("");
    }
  }

  /**
   * Update the Issued On information panel
   */
  private void updateIssuedOnPanel() {
    if (album != null) {
      StringBuilder buf = new StringBuilder();
      
      boolean first = true;
      for (MediumType mediumType: album.getIssuedOnMediums()) {
        if (first) {
          first = false;
        } else {
          buf.append(", ");
        }
        buf.append(mediumType.getLiteral());
      }
      issuedOnMediaTextField.setText(buf.toString());
    } else {
      issuedOnMediaTextField.setText("");
    }
  }
  
  /**
   * Update the checkboxes.
   */
  private void updateCheckBoxes() {
    if (album != null) {      
      isCompilationAlbumCheckBox.setSelected(album.isCompilation());
      isOwnCompilationCheckBox.setSelected(album instanceof MyCompilation);
      isSoundTrackCheckBox.setSelected(album.isSoundtrack());

    } else {
      isCompilationAlbumCheckBox.setSelected(false);
      isOwnCompilationCheckBox.setSelected(false);
      isSoundTrackCheckBox.setSelected(false);
    }
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
        DiscTracksTable discTracksTable = new DiscTracksTable(customization, disc.getTrackReferences(), trackDiscLocationMap);
        discTracksVBox.getChildren().add(discTracksTable);
      }
    }
  }
  
  /**
   * Show a picture, full size, in a separate <code>Stage</code> (without any header or border).
   * 
   * @param fileName file name of the picture to be shown.
   */
  private void showLargePicture(String fileName) {
    Image image = new Image(fileName);
    ImageView currentLargePicture = new ImageView(image);
    
    currentLargePictureStage = new Stage();
    currentLargePictureStage.initStyle(StageStyle.UNDECORATED);
    BorderPane pane = new BorderPane();
    pane.setCenter(currentLargePicture);
    currentLargePictureStage.setScene(new Scene(pane));
    currentLargePictureStage.show();
  }
}
