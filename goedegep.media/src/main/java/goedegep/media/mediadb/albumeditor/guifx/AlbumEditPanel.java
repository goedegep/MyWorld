package goedegep.media.mediadb.albumeditor.guifx;

import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditPanelTemplate;
import goedegep.jfx.editor.EditorException;
import goedegep.jfx.editor.controls.EditorControlEnumComboBox;
import goedegep.jfx.editor.controls.EditorControlFlexDate;
import goedegep.jfx.editor.controls.EditorControlString;
import goedegep.media.common.IMediaDbService;
import goedegep.media.mediadb.guifx.ArtistDetailsEditor;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.AlbumType;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.Player;
import goedegep.util.emf.EmfUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * This class is the main edit panel for the {@link AlbumEditor}.
 */
public class AlbumEditPanel extends EditPanelTemplate<Album> {
  private static final Logger LOGGER = Logger.getLogger(AlbumEditPanel.class.getName());
  private static final MediadbFactory MEDIA_DB_FACTORY = MediadbFactory.eINSTANCE;
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
  
  
  /*
   * EditorControls
   */
  
  /**
   * Album type control
   */
  private EditorControlEnumComboBox<AlbumType> albumTypeControl;

  /**
   * Control for the album title.
   * The title is plain text.
   */
  private EditorControlString albumTitleControl;
  
  /**
   * Control for the album artist.
   */
  private EditorControlArtist albumArtistControl;
  
  /**
   * Control for the album release date.
   * A <code>FlexDateFormat</code> is used to fill and parse this value.
   */
  private EditorControlFlexDate albumIssueDateControl;
  
  /**
   * Control for the album Id.<br/>
   * The album Id is plain text.
   */
  private EditorControlString albumIdControl;
  
  /**
   * Edit panel for the players
   */
  private PlayersEditPanel2 playersEditPanel;
  
  
  /*
   * Other GUI items
   */
  
  /**
   * Main edit panel
   */
  private VBox mainPane;
  
  /**
   * Other
   */
  
  /**
   * the media database service.
   */
  private IMediaDbService mediaDbService;

  /**
   * Create an instance of the {@code AlbumEditPanel}.
   * 
   * @param customization the GUI customization
   * @param mediaDbService the media database service.
   * @return the newly created {@code EventEditPanel2}.
   */
  public static AlbumEditPanel newInstance(CustomizationFx customization, IMediaDbService mediaDbService) {
    AlbumEditPanel albumEditPanel = new AlbumEditPanel(customization, mediaDbService);
    albumEditPanel.performInitialization();
    
    return albumEditPanel;
  }
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization
   * @param mediaDbService the media database service.
   */
  private AlbumEditPanel(CustomizationFx customization, IMediaDbService mediaDbService) {
    super(customization, false);
    
    this.mediaDbService = mediaDbService;
    setId("AlbumEditPanel");
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void createControls() {
    albumTypeControl = new EditorControlEnumComboBox.Builder<AlbumType>(AlbumType.NORMAL_ALBUM, "album type")
        .setCustomization(customization)
        .setLabelBaseText("Album T_ype")
        .setToolTipText("Select the type of album. This value determines how the discs/tracks are to be filled in")
        .build();
    
    albumTitleControl = new EditorControlString.Builder("album title")
        .setWidth(300d)
        .setLabelBaseText("_Title")
        .setToolTipText("The album title is a mandatory field")
        .setErrorTextSupplier(() -> "The album title is not filled in")
        .build();
        
    albumArtistControl = EditorControlArtist.newInstance(customization, mediaDbService);
    albumArtistControl.setId("album artist");
    albumArtistControl.setLabelBaseText("_Artist");
    
    albumIssueDateControl = new EditorControlFlexDate.FlexDateBuilder("issueDate")
        .setWidth(600d)
        .setLabelBaseText("_Issued")
        .setToolTipText("Optional first issue date of the album")
        .setErrorTextSupplier(() -> "The album title is not filled in")
        .setOptional(true)
        .build();
    
    albumIdControl = new EditorControlString.Builder("albumId")
        .setWidth(600d)
        .setLabelBaseText("Album I_d")
        .setToolTipText("Enter the optional Id of the album")
        .setOptional(true)
        .build();
    
    playersEditPanel = PlayersEditPanel2.newInstance(customization, mediaDbService);
            
    registerEditorComponents(albumTypeControl, albumTitleControl, albumArtistControl, albumIssueDateControl, albumIdControl, playersEditPanel);
    
    // Install change listener on the album type, to .... 
    ChangeListener<AlbumType> cl = new ChangeListener<>() {

      @Override
      public void changed(ObservableValue<? extends AlbumType> observable, AlbumType oldValue, AlbumType newValue) {
        LOGGER.severe("AlbumType: " + newValue);
        
      }
      
    };
    albumTypeControl.getControl().getSelectionModel().selectedItemProperty().addListener(cl);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Node getControl() {
    return mainPane;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void fillControlsFromObject() {
    if (value == null) {
      return;
    }
    albumTitleControl.setObject(value.getTitle());
    albumArtistControl.setObject(value.getArtist());
    albumIssueDateControl.setObject(value.getReleaseDate());
    albumIdControl.setObject(value.getId());
    
    playersEditPanel.setObject(value.getPlayers());
    
    MyInfo myInfo = value.getMyInfo();
    albumTypeControl.setObject(myInfo.getAlbumType());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void fillObjectFromControls(Album object, boolean getCurrentValue) throws EditorException {
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getAlbum_Title(), albumTitleControl.getValue());
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getAlbum_Artist(), albumArtistControl.getValue());
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getAlbum_ReleaseDate(), albumIssueDateControl.getValue());
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getAlbum_Id(), albumIdControl.getValue());
    
    if (getCurrentValue) {
      List<Player> players = playersEditPanel.getCurrentValue();
      object.getPlayers().clear();
      object.getPlayers().addAll(players);
    } else {
      playersEditPanel.accept();
    }

    /*
     * MyInfo
     */
    // MyInfo shall always be there, because albumType always has to be set.
    MyInfo myInfo = object.getMyInfo();
    
    EmfUtil.setFeatureValue(myInfo, MEDIA_DB_PACKAGE.getMyInfo_AlbumType(), albumTypeControl.getValue());
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected void createEditPanel() {
    mainPane = componentFactory.createVBox();

    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    
    // First row: 'Album type: <album-type>'
    gridPane.add(albumTypeControl.getLabel(), 0, 0);
    gridPane.add(albumTypeControl.getControl(), 1, 0);
    gridPane.add(albumTypeControl.getStatusIndicator(), 2, 0);
    
    // Second row: 'Album: <album-title>'
    gridPane.add(albumTitleControl.getLabel(), 0, 1);
    gridPane.add(albumTitleControl.getControl(), 1, 1);
    gridPane.add(albumTitleControl.getStatusIndicator(), 2, 1);
    
    // Third row: 'Artist: <artist>'   'New artist' button
    gridPane.add(albumArtistControl.getLabel(), 0, 2);
    gridPane.add(albumArtistControl.getControl(), 1, 2);
    gridPane.add(albumArtistControl.getStatusIndicator(), 2, 2);
    Button newArtistButton = componentFactory.createButton("New artist", "The artist of the album has to be selected from the list of known artists. With this button you can add a new artist to the database");
    newArtistButton.setOnAction(_ -> ArtistDetailsEditor.newInstance(customization, "New artist", mediaDbService).show());
    gridPane.add(newArtistButton, 3, 2);
    
    // Fourth row: 'Issue date: <album-issue-date>'
    gridPane.add(albumIssueDateControl.getLabel(), 0, 3);
    gridPane.add(albumIssueDateControl.getControl(), 1, 3);
    gridPane.add(albumIssueDateControl.getStatusIndicator(), 2, 3);
    
    // Fifth row: 'Album Id: <album-id>'
    gridPane.add(albumIdControl.getLabel(), 0, 4);
    gridPane.add(albumIdControl.getControl(), 1, 4);
    gridPane.add(albumIdControl.getStatusIndicator(), 2, 4);

    mainPane.getChildren().add(gridPane);
    
    // Players
    mainPane.getChildren().add(playersEditPanel.getControl());
  }
  

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setErrorFeedback(boolean valid) {
    // No action at this level.
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Album createObject() {
    return MEDIA_DB_FACTORY.createAlbum();
  }


  @Override
  public String getValueAsFormattedText() {
    return null;
  }
  
}
