package goedegep.jfx.eobjecttreeview;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EStructuralFeature;

import goedegep.util.emf.EObjectPath;
import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;

/**
 * This class provides the {@link TreeCell} for an EObjectTreeView.
 * <p>
 * A TreeCell is used to render an item in a TreeView.</br>
 * Although the rendering largely depends on the type of the item, there is only a single type of TreeCell (which may be reused for any type of item in the tree).
 * Therefore the actual work is done by type specific helpers. These helpers all implement the {@link EObjectTreeCellHelper} interface.<br/>
 * A TreeCell instance is also reused to render different items. Each time the TreeCell instance is assigned to an item, the method updateItem is called.
 * Therefore this method creates the right helper for the item type and then delegates the work to the helper by calling updateItem on the helper.<br/>
 * 
 */
public class EObjectTreeCell extends TreeCell<Object> {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCell.class.getName());
  
  /**
   * Save the current {@code TreeItem} associated to this cell, to check whether a different {@code TreeItem} is associated.
   */
  private TreeItem<?> currentTreeItem = null;
  
  /**
   * The {@code EObjectTreeCellHelper} for the {@code treeItemType}.
   */
  private EObjectTreeCellHelper treeCellHelper = null;
  
  /**
   * Queues with free tree cell helpers per helper class.
   */
  private HashMap<Class<?>, Queue<EObjectTreeCellHelper>> helpers = new HashMap<>();

  /**
   * Constructor.
   */
  public EObjectTreeCell() {
    LOGGER.info("=> treeItem: " + (getTreeItem() != null ? getTreeItem().toString() : "<null>"));
    
    EObjectTreeCell thisCell = this;
    
    
    /*
     * Drag & Drop handling.
     * For now only moving (cut and paste) of EObjects is supported.
     * For an EObject the Data Format EOBJECT_PATH is used, in which case an EObjectPath is placed on the clipboard.
     * When dropped on a list, the source is moved to the end of the list.
     * When dropped on a list item, the source is moved to the list, before that item.
     * When dropped on a single reference, the object is replaced.
     */
    // The drag detected event is fired when the mouse is pressed and moved on a node.
    setOnDragDetected(this::handleDragDetected);
    
    // The dragenter event is fired when a dragged element or text selection enters a possible target.
    setOnDragEntered(this::handleDragEntered);
    
    /**
     * The dragover event is fired when an element or text selection is being dragged over a valid drop target (every few hundred milliseconds).
     */
    setOnDragOver(this::handleDragOver);
    
    /**
     * The dragexit event is fired when a dragged element leaves a possible drop target.
     */
    setOnDragExited(new EventHandler<DragEvent>() {
      public void handle(DragEvent event) {
        LOGGER.info("=>");
        thisCell.getGraphic().setEffect(null);

        event.consume();
      }
    });

    /*
     * Handle the dropping on the target
     */
    setOnDragDropped(this::handleDragDropped);
        
    LOGGER.info("<=");
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void updateItem(Object value, boolean empty) {
    LOGGER.info("=> item=" + (value != null ? value : "(null)") + ", empty=" + empty);
    
    super.updateItem(value, empty);

    setText(null);

    if (empty) {
      setGraphic(null);
      return;
    }

    // Update the helper if this cell is associated to a different tree item.
    TreeItem<?> newTreeItem = getTreeItem();
    if (currentTreeItem == null  ||  currentTreeItem != newTreeItem) {
      updateTreeCellHelper();
    }
    currentTreeItem = newTreeItem;

    treeCellHelper.updateItem(value);

    LOGGER.info("<=");
  }

  /**
   * Handle the starting of a drag event.
   * <p>
   * Drag and Drop is more related to the tree item, so this method just call the same method on the tree item.
   * Only dragging of an EObject is supported.<br/>
   * If the item is an EObject, an {@link EObjectPath} is created and serialized as bytes.
   * {@code ClipboardContent} is created with this data, with type {@link EOBJECT_PATH}.
   * A {@code Dragboard} is created for a MOVE and with the {@code ClipboardContent} as content.
   * 
   * @param event the {@code MouseEvent} which detected the drag start.
   */
  private void handleDragDetected(MouseEvent event) {
    LOGGER.info("=>");
    
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) getTreeItem();
    if (eObjectTreeItem != null) {
      eObjectTreeItem.handleDragDetected(event, this);
    }
  }
  
  /**
   * Handle a 'drag entered' event.
   * <p>
   * This is handled as follows:
   * <ul>
   * <li>Check whether a drop is possible</li>
   * </ul>
   * 
   * @param dragEvent
   */
  private void handleDragEntered(DragEvent dragEvent) {
    LOGGER.info("=>");
    /* the drag-and-drop gesture entered a possible target */
    /* show to the user that it is an actual gesture target */
    TransferMode transferMode = isDropPossible(dragEvent);
    if (transferMode != null) {
      DropShadow ds1 = new DropShadow();
      ds1.setOffsetY(4.0f);
      ds1.setOffsetX(4.0f);
      ds1.setColor(Color.CORAL);
      LOGGER.info("Highlight set");
      getGraphic().setEffect(ds1);
      
      dragEvent.acceptTransferModes(transferMode);      
    }

    dragEvent.consume();    
  }
  
  /**
   * Check whether a drop event can be handled (upon a drag over).
   * <p>
   * Drop is standard supported for {@code EObject}s.<br/>
   * If it isn't an {@code EObject} and an {@code isDropPossibleFunction} is specified on the TreeView,
   * then this function is called to check whether a drop is supported.
   */
  private void handleDragOver(DragEvent dragEvent) {
    LOGGER.info("=>");
    
    /* data is dragged over a (possible) target */
    /* accept it only if it is not dragged from the same node 
     * and if it has a supported data format. */
    TransferMode transferMode = isDropPossible(dragEvent);
    
    if (transferMode != null) {      
      dragEvent.acceptTransferModes(transferMode);      
    }

    dragEvent.consume();
  }

  /**
   * Handle a 'drag dropped' event.
   * <p>
   * Most of the work depends on the type of tree item, so this part is delegated to the tree cell helper.
   * 
   * @param dragEvent a 'drag dropped' event.
   */
  private void handleDragDropped(DragEvent dragEvent) {
      LOGGER.info("=>");
      
      EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) getTreeItem();
      if (eObjectTreeItem != null) {
        eObjectTreeItem.handleDragDropped(dragEvent);
      }
      
  }
    
  /**
   * Check whether the information of the dragEvent can be dropped on this tree item and if so return the TransferMode.
   * <p>
   * An item cannot be dropped on itself, so if the gesture source is this item, null is returned.</br>
   * If the dragboard content is an EOBJECT_PATH, the isDropPossible() of the helper is called. If this has a positive reply, the TransferMode is MOVE.</br>
   * If the dragboard content isn't an EOBJECT_PATH, or a drop isn't possible, the isDropPossibleFunction (when set on the TreeView) is called.
   * 
   * @param dragEvent the Drag and Drop event.
   * @return true if the {@code dragEvent} can be handled.
   */
  private TransferMode isDropPossible(DragEvent dragEvent) {
    // Cannot drop on itself
    if (dragEvent.getGestureSource() == this) {
      return null;
    }
//    LOGGER.severe("=> dragEvent=" + EObjectTreeItem.dragEventToString(dragEvent));
    
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) getTreeItem();
    if (eObjectTreeItem == null) {
      return null;
    } else {
      return eObjectTreeItem.getTransferModeForDrop(dragEvent);
    }
    
  }

  /**
   * Update the {@code treeCellHelper} for the current content.
   * <p>
   * If a {@code PresentationType} is specified (only possible for nodes of type {@code EObjectTreeItemForAttributeSimple}), this defines the helper class.<br/>
   * Otherwise the best helper is selected based on the content type.
   * The type of content is specified by the {@link EObjectItemType} of the tree item. Each content
   * type has its own helper class as follows:
   * <pre>
   * EObjectItemType        helper class
   * OBJECT                 EObjectTreeCellHelperForObject
   * OBJECT_IN_LIST         EObjectTreeCellHelperForObjectInList
   * OBJECT_LIST            EObjectTreeCellHelperForObjectList
   * ATTRIBUTE_SIMPLE       EObjectTreeCellHelperForAttributeBoolean for data type Boolean, else EObjectTreeCellHelperForAttributeSimple
   * ATTRIBUTE_LIST         EObjectTreeCellHelperForAttributeList
   * ATTRIBUTE_LIST_VALUE   EObjectTreeCellHelperForAttributeListValue
   * </pre>
   * 
   * @param eObjectTreeItemContent the content of the tree item to be rendered by a tree cell.
   */
  private void updateTreeCellHelper() {
    LOGGER.info("=> EObjectTreeItemType=" + ((EObjectTreeItem) getTreeItem()).getEObjectTreeItemType());
    
    Class<? extends EObjectTreeCellHelper> helperClass = null;
    
    PresentationType presentationType = null;
    EObjectTreeItem treeItem = (EObjectTreeItem) getTreeItem();
    if (treeItem instanceof EObjectTreeItemForAttributeSimple eObjectTreeItemForAttributeSimple) {
      presentationType = eObjectTreeItemForAttributeSimple.getEObjectTreeItemAttributeDescriptor().getPresentationType();
    }
    
    if (presentationType != null) {
      switch(presentationType) {
      case BOOLEAN:
        helperClass = EObjectTreeCellHelperForAttributeBoolean.class;
        break;

      case SINGLE_LINE_TEXT:
      case FORMAT:
        helperClass = EObjectTreeCellHelperForAttributeSimple.class;
        break;

      case MULTI_LINE_TEXT:
        helperClass = EObjectTreeCellHelperForAttributeMultiLineText.class;
        break;

      case ENUMERATION:
        helperClass = EObjectTreeCellHelperForAttributeEnumeration.class;
        break;

      case FILE:
        helperClass = EObjectTreeCellHelperForAttributeFile.class;
        break;

      case FOLDER:
        helperClass = EObjectTreeCellHelperForAttributeFolder.class;
        break;
      }
    }

    if (helperClass == null) {
      EObjectTreeItemType newTreeItemType = ((EObjectTreeItem) getTreeItem()).getEObjectTreeItemType();
      
      switch (newTreeItemType) {
      case OBJECT:
        helperClass = EObjectTreeCellHelperForObject.class;
        break;

      case OBJECT_LIST:
        helperClass = EObjectTreeCellHelperForObjectList.class;
        break;

      case ATTRIBUTE_SIMPLE:
        EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) getTreeItem();
        EStructuralFeature eStructuralFeature = eObjectTreeItem.getEStructuralFeature();
        if (eStructuralFeature instanceof EAttribute eAttribute) {
          EDataType eDataType = eAttribute.getEAttributeType();
          if (eDataType instanceof EEnum) {
            helperClass = EObjectTreeCellHelperForAttributeEnumeration.class;
          } else {
            switch (eDataType.getName()) {
            case "Boolean":
            case "EBoolean":
              helperClass = EObjectTreeCellHelperForAttributeBoolean.class;
              break;

            default:
              helperClass = EObjectTreeCellHelperForAttributeSimple.class;
              break;
            }
          }
        } else {
          throw new RuntimeException("eStructuralFeature is not an eAttribute");
        }
        
        break;

      case ATTRIBUTE_LIST:
        helperClass = EObjectTreeCellHelperForAttributeList.class;
        break;

      case ATTRIBUTE_LIST_VALUE:
        helperClass = EObjectTreeCellHelperForAttributeListValue.class;
        break;

      default:
        LOGGER.severe("Error: reaching default clause for treeItemType: " + newTreeItemType);
        throw new RuntimeException("Error: reaching default clause for treeItemType: " + newTreeItemType);
      }
    }

    if (treeCellHelper == null  ||  treeCellHelper.getClass() != helperClass) {
      
      // store the current helper, if there is one, in the helpers map.
      if (treeCellHelper != null) {
        Queue<EObjectTreeCellHelper> queue = helpers.get(treeCellHelper.getClass());
        if (queue == null) {
          queue = new LinkedList<EObjectTreeCellHelper>();
          helpers.put(treeCellHelper.getClass(), queue);
        }
        queue.add(treeCellHelper);
      }

      // get new helper from helpers map, or create it if not available
      Queue<EObjectTreeCellHelper> queue = helpers.get(helperClass);
      if (queue != null  &&  !queue.isEmpty()) {
        treeCellHelper = queue.remove();
      } else {
        try {
          treeCellHelper = helperClass.getConstructor(this.getClass()).newInstance(this);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
          e.printStackTrace();
        }
        
      }
    }
    
    LOGGER.info("<= " + (treeCellHelper != null ? treeCellHelper.getClass().getName() : "<null>"));
  }

  @Override
  public void startEdit() {
    LOGGER.info("=>");
    
    if (((EObjectTreeItem)getTreeItem()).getEObjectTreeItemType().isEditable()) {
      super.startEdit();
      treeCellHelper.startEdit();
    }
    
    LOGGER.info("<=");
  }
  
  @Override
  public void commitEdit(Object newValue) {
    treeCellHelper.commitEdit(getTreeItem(), newValue);
    
    super.commitEdit(newValue);
  }

  @Override
  public void cancelEdit() {
    LOGGER.info("=>");
    
    super.cancelEdit();
    
    treeCellHelper.cancelEdit();

    LOGGER.info("<=");
  }
  
  /**
   * Get an, as good as possible, textual representation of a cell.
   * <p>
   * This method is a kind of a toString method, which can e.g. be used to print a textual representation of a tree view.
   * 
   * @return an, as good as possible, textual representation of the cell.
   */
  public String getCellText() {
    return treeCellHelper.getText();
  }
}
