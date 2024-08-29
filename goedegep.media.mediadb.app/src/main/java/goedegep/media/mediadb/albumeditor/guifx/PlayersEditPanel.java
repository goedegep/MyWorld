package goedegep.media.mediadb.albumeditor.guifx;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.util.EcoreUtil;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControl;
import goedegep.jfx.objectcontrols.ObjectControlTemplate;
import goedegep.jfx.objectcontrols.ObjectEditPanelTemplate;
import goedegep.jfx.objecteditor.ObjectEditorException;
import goedegep.jfx.objectcontrols.ObjectControlGroup;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.Player;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class is an {@link ObjectControl} for a list of {@code Player}s.
 */
public class PlayersEditPanel extends ObjectEditPanelTemplate<List<Player>> {
  private static final Logger LOGGER = Logger.getLogger(PlayersEditPanel.class.getName());
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
  private static final MediadbFactory MEDIA_DB_FACTORY = MediadbFactory.eINSTANCE;
  
  /**
   * The GUI customization.
   */
  private CustomizationFx customization;
  
  /**
   * The media database (containing the artists)
   */
  private MediaDb mediaDb;
  
  /**
   * Main panel
   */
  private HBox mainPanel;
  
  /**
   * Panel for the players.
   */
  private VBox playersPanel;
  
  /**
   * 
   */
  private List<ObjectControlForPlayer> objectControlsForPlayers = new ArrayList<>();
 
  
  /**
   * Constructor.
   * 
   * @param customization The GUI customization.
   * @param mediaDb The media database.
   */
  public PlayersEditPanel(CustomizationFx customization, MediaDb mediaDb) {
    super(customization);
    
    this.customization = customization;
    this.mediaDb = mediaDb;
    
    objectControlsGroup.setId("PlayersEditPanel");
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void createControls() {
    // players are created when needed.
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void createEditPanel() {
    mainPanel = componentFactory.createHBox(12.0, 12.0);
    
    VBox controlsBox = componentFactory.createVBox(10.0);
    Label label = componentFactory.createLabel("Players");
    controlsBox.getChildren().add(label);
    
    Button addPlayerButton = componentFactory.createButton("Add player", "click to add a player");
    controlsBox.getChildren().add(addPlayerButton);
    mainPanel.getChildren().add(controlsBox);
    
    playersPanel = componentFactory.createVBox();
    mainPanel.getChildren().add(playersPanel);
    
//    mainPanel.getChildren().add(getStatusIndicator());  // TODO per player
    
    addPlayerButton.setOnAction((e) -> {
      ObjectControlForPlayer objectControlForPlayer = new ObjectControlForPlayer(customization, mediaDb);
      playersPanel.getChildren().add(objectControlForPlayer.getControl());
      objectControlsGroup.addObjectControls(objectControlForPlayer);
     });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createObject() {
    object = new ArrayList<Player>();
  }

  /**
   * {@inheritDoc}
   * 
   * Default is an empty players list.
   */
  @Override
  protected void fillControlsWithDefaultValues() {
    if (object != null) {
      object.clear();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void fillControlsFromObject() {
    if (object != null) {
      for (Player player: object) {
        ObjectControlForPlayer objectControlForPlayer = new ObjectControlForPlayer(customization, mediaDb);
        objectControlsForPlayers.add(objectControlForPlayer);
        Player playerCopy = EcoreUtil.copy(player);
        objectControlForPlayer.setValue(playerCopy);
        playersPanel.getChildren().add(objectControlForPlayer.getControl());
        objectControlsGroup.addObjectControls(objectControlForPlayer);
      }
      
    }
  }

  @Override
  protected void updateObjectFromControls() throws ObjectEditorException {
    // just rebuild the players list.
    object.clear();
    
    for (ObjectControlForPlayer objectControlForPlayer: objectControlsForPlayers) {
      Player player = MEDIA_DB_FACTORY.createPlayer();
      player.setArtist(objectControlForPlayer.getValue().getArtist());
      player.getInstruments().addAll(objectControlForPlayer.getValue().getInstruments());
    }
  }

  @Override
  public Node getControl() {
    return mainPanel;
  }

}
