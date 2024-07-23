package goedegep.media.mediadb.albumeditor.guifx;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControl;
import goedegep.jfx.objectcontrols.ObjectControlTemplate;
import goedegep.jfx.objectcontrols.ObjectControlGroup;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.Player;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class is an {@link ObjectControl} for a list of {@code Player}s.
 */
public class ObjectControlForPlayers extends ObjectControlTemplate<List<Player>> {
  
  /**
   * The GUI customization.
   */
  private CustomizationFx customization;
  
  /**
   * The media database (containing the artists)
   */
  private MediaDb mediaDb;
  
 /**
   * Factory to create the GUI components.
   */
  private ComponentFactoryFx componentFactory;
  
  /**
   * An {@code ObjectControlGroup} to contain all Object Controls.
   */
  private ObjectControlGroup objectControlsGroup;
  
  /**
   * Main panel
   */
  private HBox mainPanel;
  
  /**
   * Panel for the players.
   */
  private VBox playersPanel;
  
 
  
  /**
   * Constructor.
   * 
   * @param customization The GUI customization.
   */
  public ObjectControlForPlayers(CustomizationFx customization, MediaDb mediaDb) {
    super(true);
    
    this.customization = customization;
    this.mediaDb = mediaDb;
    
    componentFactory = customization.getComponentFactoryFx();
    
    createControls();
    
    createGUI();
    
    ociHandleNewUserInput(null);
  }
  
  private void createControls() {
    objectControlsGroup = new ObjectControlGroup();
  }
  
  private void createGUI() {
    mainPanel = componentFactory.createHBox(12.0, 12.0);
    
    VBox controlsBox = componentFactory.createVBox(10.0);
    Label label = componentFactory.createLabel("Players");
    controlsBox.getChildren().add(label);
    
    Button addPlayerButton = componentFactory.createButton("Add player", "click to add a player");
    controlsBox.getChildren().add(addPlayerButton);
    mainPanel.getChildren().add(controlsBox);
    
    playersPanel = componentFactory.createVBox();
    mainPanel.getChildren().add(playersPanel);
    
    mainPanel.getChildren().add(getStatusIndicator());
    
    addPlayerButton.setOnAction((e) -> {
      ObjectControlForPlayer objectControlForPlayer = new ObjectControlForPlayer(customization, mediaDb);
      playersPanel.getChildren().add(objectControlForPlayer.getControl());
      objectControlsGroup.addObjectControl(objectControlForPlayer);
     });
  }

  @Override
  public void setValue(List<Player> players) {
    playersPanel.getChildren().clear();
    
    if (players != null) {
      for (Player player: players) {
        ObjectControlForPlayer objectControlForPlayer = new ObjectControlForPlayer(customization, mediaDb);
        Player playerCopy = EcoreUtil.copy(player); 
        objectControlForPlayer.setValue(playerCopy);
        playersPanel.getChildren().add(objectControlForPlayer.getControl());
        objectControlsGroup.addObjectControl(objectControlForPlayer);
      }
    }
  }

  @Override
  public Node getControl() {
    return mainPanel;
  }

  @Override
  public String getValueAsFormattedText() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected boolean ociDetermineFilledIn(Object source) {
    return objectControlsGroup.isAnyObjectInputFilledIn();
  }

  @Override
  protected List<Player> ociDetermineValue(Object source) {
    List<Player> players = new ArrayList<>();
    for (ObjectControl<?> objectControlForPlayer: objectControlsGroup.getObjectControls()) {
      players.add(((ObjectControlForPlayer) objectControlForPlayer).getValue());
    }
    
    return players;
  }

  @Override
  protected void ociSetErrorFeedback(boolean valid) {
    // no action
    
  }

  @Override
  protected void ociRedrawValue() {
    // no action
    
  }

  @Override
  protected void ociUpdateNonSourceControls(Object source) {
    // TODO Auto-generated method stub
    
  }

}
