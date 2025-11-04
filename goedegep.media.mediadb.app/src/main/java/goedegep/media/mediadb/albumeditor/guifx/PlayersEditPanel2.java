package goedegep.media.mediadb.albumeditor.guifx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditPanelTemplate;
import goedegep.jfx.editor.EditorException;
import goedegep.jfx.editor.ListEditPanelTemplate;
import goedegep.media.common.IMediaDbService;
import goedegep.media.mediadb.model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class is an edit panel (extension of {@link EditPanelTemplate} to
 * show/edit a list of players (a list of {@link Player}).
 * <p>
 * The panel (the Control) is a {@link TitledPane}.<br/>
 * The items can be reordered via drag and drop.
 */
public class PlayersEditPanel2 extends ListEditPanelTemplate<List<Player>, Player, PlayerMultiControl> {
  private static final Logger LOGGER = Logger.getLogger(PlayersEditPanel2.class.getName());
  
  /**
   * Clipboard content type used for drag and drop.
   */
  private static final DataFormat PLAYER = new DataFormat("Player");
  
  /**
   * the media database service.
   */
  private IMediaDbService mediaDbService;
  
  /**
   * Top level panel.
   * Left is VBox with 'Players' label and 'new player' button.
   * Right is GridPane with the players
   */
  protected HBox mainPanel;
  
  /**
   * Pane for the players.
   */
  protected GridPane playersGridPane;
  
//  /**
//   * The list of {@code PlayerMultiControl}s.
//   */
//  ObservableList<PlayerMultiControl> playerMultiControls;
  
  /**
   * Mapping from the {@code playerMultiControls} to the related {@code Player}.
   * FillControlsFromObject (via setObject): Clear the map. For each Player a PlayerMultiControl is created (and appended to the playerMultiControls) and a mapping is added.
   * Add: A new player is always added as the last player. So a PlayerMultiControl is created (and appended to the playerMultiControls). No mapping is added.
   * Delete: The mapping of the PlayerMultiControl is deleted and the item itself is deleted.
   * Reorder (via Drag & Drop): Only the order of the playerMultiControls is changed.
   * FillObjectFromControls (via accept):
   *     for each item in the playerMultiControls
   *        if there is a mapping, move the Player to the index of the item
   *        else insert the Player at the index of the panel
   *     Delete all Players with an index higher than the size of the playerMultiControls minus 1
   */
  Map<PlayerMultiControl, Player> multiControlToPlayerMap = new HashMap<>();

  
  
  /**
   * Create a new {@code PlayersEditPanel2}
   * 
   * @param customization The GUI customization.
   * @param mediaDbService the media database service.
   * @return a newly created {@code PlayersEditPanel2}
   */
  public static PlayersEditPanel2 newInstance(CustomizationFx customization, IMediaDbService mediaDbService) {
    PlayersEditPanel2 playersEditPanel = new PlayersEditPanel2(customization, mediaDbService);
    
    playersEditPanel.performInitialization();
    
    return playersEditPanel;
  }
  
  /**
   * Constructor.
   * 
   * @param customization The GUI customization.
   * @param mediaDbService the media database service.
   */
  private PlayersEditPanel2(CustomizationFx customization, IMediaDbService mediaDbService) {
    super(customization, true);
    
    this.mediaDbService = mediaDbService;
  }

  @Override
  public Node getControl() {
    return mainPanel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createControls() {
    super.createControls();
//    listItemPanels = FXCollections.observableArrayList();
//    listItemPanels.addListener(new ListChangeListener<PlayerMultiControl>() {
//
//      @Override
//      public void onChanged(Change<? extends PlayerMultiControl> c) {
//        while (c.next()) {
//          if (c.wasPermutated()) {  // NOPMD
//            // No action needed here
//          } else if (c.wasUpdated()) {
//            LOGGER.severe("Update not handled!!");
//          } else {
//            for (PlayerMultiControl playerEditPanel: c.getRemoved()) {
//              unregisterEditorComponents(playerEditPanel);
//              handleChanges();
//            }
//            for (PlayerMultiControl playerEditPanel: c.getAddedSubList()) {
//              registerEditorComponents(playerEditPanel);
//              handleChanges();
//            }
//          }
//        }
//        
//        updateListPresentation();
//        
//      }
//      
//    });
//    
//    this.addValueAndOrStatusChangeListener((_, _) -> handleChanges());
  }
  
  protected void handleChanges() {
    // TODO no action??
  }
  
  /**
   * Redraw the players panel ({@link invoiceAndPropertyItemsVBox}).
   */
  protected void updateListPresentation() {
    playersGridPane.getChildren().clear();
    
    int index = 0;
    
    Label label = componentFactory.createStrongLabel("Player");
    playersGridPane.add(label, 0, index);
    label = componentFactory.createStrongLabel("Instruments");
    playersGridPane.add(label, 1, index);
    index++;
    
    for (PlayerMultiControl playerMultiControl:  listItemPanels) {
      playersGridPane.add(playerMultiControl.getArtistEditorControl().getControl(), 0, index);
      playersGridPane.add(playerMultiControl.getInstrumentsEditorControl().getControl(), 1, index);
      playersGridPane.add(playerMultiControl.getStatusIndicator(), 2, index);
      Button deleteButton = componentFactory.createButton("Delete", "Remove this player");
      deleteButton.setOnAction((e) -> deletePlayer(e));
      playersGridPane.add(deleteButton, 3, index);
      index++;
    }
  }
  
  /**
   * Handle the action of one of the delete buttons.
   * 
   * @param actionEvent the {@code ActionEvent}.
   */
  private void deletePlayer(ActionEvent actionEvent) {
    /*
     * The source of the actionEvent is a delete button.
     * Find the row with this button, then the first node in this HBox is the control of one of the invoiceAndPropertyItemPanels.
     */
    Object eventSource = actionEvent.getSource();
    
    int rowIndex = -1;
    for (Node node: playersGridPane.getChildren()) {
      if (node == eventSource) {
        rowIndex = GridPane.getRowIndex(node);
        
        PlayerMultiControl playerMultiControlToDelete = listItemPanels.get(rowIndex);
        multiControlToPlayerMap.remove(playerMultiControlToDelete);
        listItemPanels.remove(rowIndex);
        break;
      }
    }
  }

  @Override
  public String getValueAsFormattedText() {
    // Not applicable
    return null;
  }

  @Override
  protected void createEditPanel() {
    mainPanel = componentFactory.createHBox(12.0);
    
    VBox leftPane = componentFactory.createVBox(12.0);
    
    HBox labelAndStatusBox = componentFactory.createHBox(12.0, 12.0);
    Label label = componentFactory.createStrongLabel("Players");
    labelAndStatusBox.getChildren().addAll(label, getStatusIndicator());
    
    Button newPlayerButton = componentFactory.createButton("+ Add a player", "Click to a new player");
    newPlayerButton.setOnAction(_ -> createNewPlayerMultiControl(null));
    leftPane.getChildren().addAll(labelAndStatusBox, newPlayerButton);
    
    playersGridPane = componentFactory.createGridPane(12.0, 12.0);
    
    mainPanel.getChildren().addAll(leftPane, playersGridPane);
    
  }
  
  /**
   * Create a new {@code PlayerMultiControl}.
   * 
   * @param player the optional value for the control
   */
  private void createNewPlayerMultiControl(Player player) {
    PlayerMultiControl playerMultiControl = PlayerMultiControl.newInstance(customization, mediaDbService);
    
    installDragAndDropHandling(playerMultiControl);
    listItemPanels.add(playerMultiControl);
    
    if (player != null) {
      multiControlToPlayerMap.put(playerMultiControl, player);
      playerMultiControl.setObject(player);
    }
  }
  
  /**
   * Install drag and drop handling.
   * <p>
   * The players can be reordered via drag and drop.
   */
  private void installDragAndDropHandling(PlayerMultiControl playerMultiControl) {
    
    /*
     * Handle the starting of a drag event.
     * This can be dragged.
     */
    playerMultiControl.getArtistEditorControl().getControl().setOnDragDetected(new EventHandler<MouseEvent>() {

      public void handle(MouseEvent event) {
        ClipboardContent clipboardContent = new ClipboardContent();
        Integer myIndex = listItemPanels.indexOf(playerMultiControl);
        clipboardContent.put(PLAYER, myIndex);

        Dragboard dragBoard = playersGridPane.startDragAndDrop(TransferMode.MOVE);
        dragBoard.setContent(clipboardContent);

        event.consume();
      }
    });
    
    /*
     * Check whether a drop event can be handled (upon a drag over).
     * Drop is supported for PLAYER.
     */
    playerMultiControl.getArtistEditorControl().getControl().setOnDragOver(new EventHandler<DragEvent>() {
      public void handle(DragEvent event) {
        /* data is dragged over a (possible) target */
        /* accept it only if it is not dragged from the same node 
         * and if it has a supported data format. */
        Dragboard dragboard = event.getDragboard();
        if (event.getGestureSource() != playerMultiControl  &&  dragboard.hasContent(PLAYER)) {
          event.acceptTransferModes(TransferMode.MOVE);
        }

        event.consume();
      }
    });
    
    /*
     * Handle the dropping on the target
     */
    playerMultiControl.getArtistEditorControl().getControl().setOnDragDropped(new EventHandler<DragEvent>() {
      public void handle(DragEvent event) {
        LOGGER.info("=>");

        boolean success = false;

        // Get the index from the drag board, and if it isn't null use it.
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasContent(PLAYER)) {
          Integer sourceIndex = (Integer) dragboard.getContent(PLAYER);
          if (sourceIndex != null) {
            PlayerMultiControl sourcePanel = listItemPanels.get(sourceIndex);
            int sourceIndexInt = sourceIndex;
            listItemPanels.remove(sourceIndexInt);
            
            Integer myIndex = listItemPanels.indexOf(playerMultiControl);
            listItemPanels.add(myIndex, sourcePanel);
          }

          /* let the source know whether the item was successfully transferred and used */
          event.setDropCompleted(success);
        }

        event.consume();
      }
    });
    
  }

  @Override
  protected void setErrorFeedback(boolean valid) {
    // Not applicable    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<Player> createObject() {
    return new ArrayList<Player>();
  }

  @Override
  protected void fillControlsFromObject() {
    if (value == null) {
      return;
    }
    
    // Clear the map. For each InvoiceAndPropertyItem an InvoiceAndPropertyItemPanel is created (and appended to the invoiceAndPropertyItemPanels) and a mapping is added.
    multiControlToPlayerMap.clear();
    for (Player player: value) {
      createNewPlayerMultiControl(player);
    }
  }

  @Override
  protected void fillObjectFromControls(List<Player> players, boolean getCurrentValue) throws EditorException {
    if (getCurrentValue) {
      players.clear();

      for (PlayerMultiControl playerMultiControl: listItemPanels) {
        Player player = playerMultiControl.getCurrentValue();
        players.add(player);
      }
    } else {
      /*     for each panel in the invoiceAndPropertyItemPanels
      *        if there is a mapping, move the InvoiceAndPropertyItem to the index of the panel
      *        else insert the InvoiceAndPropertyItem at the index of the panel
      *     Delete all InvoiceAndPropertyItems with an index higher than the size of the invoiceAndPropertyItemPanels minus 1
      */
      int index = 0;
      for (PlayerMultiControl playerMultiControl: listItemPanels) {
        Player playerFromPanel = playerMultiControl.accept();
        Player player = multiControlToPlayerMap.get(playerMultiControl);
        if (player != null) {
          int indexOfPlayerToMove = value.indexOf(player);
          Player playerToMove = value.get(indexOfPlayerToMove);
          value.remove(indexOfPlayerToMove);
          value.add(index, playerToMove);
        } else {
          value.add(index, playerFromPanel);
          multiControlToPlayerMap.put(playerMultiControl, playerFromPanel);
        }
        
        index++;
      }
      
      while (value.size() > listItemPanels.size()) {
        value.removeLast();
      }
    }

  }
  
}
