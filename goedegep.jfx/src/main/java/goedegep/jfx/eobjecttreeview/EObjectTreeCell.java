package goedegep.jfx.eobjecttreeview;

import java.util.logging.Logger;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EStructuralFeature;

import goedegep.util.emf.EObjectPath;
import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
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
//  private static final DataFormat EOBJECT_PATH = new DataFormat("EObjectPath");
    
  /**
   * The {@code EObjectTreeItemType} of the item rendered by this cell.
   */
  private EObjectTreeItemType treeItemType = null;
  
  /**
   * The {@code EObjectTreeCellHelper} for the {@code treeItemType}.
   */
  private EObjectTreeCellHelper treeCellHelper = null;

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
  
  @Override
  public void updateItem(Object value, boolean empty) {
    LOGGER.info("=> item=" + (value != null ? value : "(null)") + ", empty=" + empty);
    
    super.updateItem(value, empty);
    
    if (empty) {
      setText(null);
      setGraphic(null);
      
      treeItemType = null;
      treeCellHelper = null;
    } else {
      updateTreeCellHelper(value);
      
      treeCellHelper.updateItem(value);
    }
        
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
    if (eObjectTreeItem == null) {
      return;
    } else {
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
      LOGGER.severe("=>");
      
      EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) getTreeItem();
      if (eObjectTreeItem == null) {
        return;
      } else {
        eObjectTreeItem.handleDragDropped(dragEvent);
        return;
      }
      
//      boolean success = false;
//      
//      EObject sourceEObject = getSourceObject(dragEvent.getDragboard());
//      
//      Dragboard dragboard = dragEvent.getDragboard();
//      dragboard.getContent(EOBJECT_PATH);
//      
//      if (sourceEObject != null) {        
//        // Check that this node isn't the sourceNode, as you cannot cut and paste an object to itself. If so throw an exception as this shouldn't happen, as it is checked on drag entered.
//        EObjectTreeItem thisEObjectTreeItem = (EObjectTreeItem)  getTreeItem();        
//        EObjectTreeItemContent thisEObjectTreeItemContent = thisEObjectTreeItem.getValue();
//        Object thisObject = thisEObjectTreeItemContent.getObject();
//        LOGGER.info("thisObject=" + thisObject.toString());
//        if (thisObject.equals(sourceEObject)) {
//          throw new RuntimeException("Cannot cut and past object to itself: object=" + sourceEObject.toString());
//        }
//        
//        treeCellHelper.handleDragDropped(dragEvent, sourceEObject, thisEObjectTreeItem);
//                
////        EStructuralFeature thisStructuralFeature = thisEObjectTreeItemContent.getEStructuralFeature();
////        if (thisStructuralFeature != null) {
////          LOGGER.info("contentStructuralFeature=" + thisStructuralFeature.toString());
////          if (thisStructuralFeature instanceof EReference) {
////            EReference contentEReference = (EReference) thisStructuralFeature;
////            EClass contentReferenceType = contentEReference.getEReferenceType();
////            if (contentReferenceType.isSuperTypeOf(sourceEObject.eClass())) {
////              LOGGER.info("Yes it is a supertype");
////              
////              handleCutForDragAndDrop(dragEvent, sourceEObject);
////              
////              @SuppressWarnings("unchecked")
////              EList<Object> contentEList = (EList<Object>) thisObject;
////              contentEList.add(sourceEObject);
////              thisEObjectTreeItem.rebuildChildren();
////              
////              success = true;
////            }
////          }
////        } else {
////          LOGGER.info("Node has no StructuralFeature");
////          if (sourceEObject.getClass().equals(thisObject.getClass())) {
////            // this item is either a single value, which can be overwritten, or it is part of a list, so the source can be inserted.
////            LOGGER.info("Object has same type");
////            EObjectTreeItem eObjectParentTreeItem = (EObjectTreeItem)  thisEObjectTreeItem.getParent();
////            EObjectTreeItemContent eObjectParentTreeItemContent = eObjectParentTreeItem.getValue();
////            EStructuralFeature parentStructuralFeature = eObjectParentTreeItemContent.getEStructuralFeature();
////            if (parentStructuralFeature != null) {
////              LOGGER.info("parentStructuralFeature=" + parentStructuralFeature.toString());
////              if (parentStructuralFeature instanceof EReference) {
////                EReference parentEReference = (EReference) parentStructuralFeature;
////                if (parentEReference.isMany()) {
////                  
////                  // It is a list, so insert before this item.
////                  LOGGER.info("Item is part of a list; insert before");
////                  @SuppressWarnings("unchecked")
////                  EList<Object> contentEList = (EList<Object>) eObjectParentTreeItemContent.getObject();
////                  LOGGER.info("contentEList=" + contentEList.toString());
////                  LOGGER.info("contentObject=" + thisObject.toString());
////                  int contentObjectIndex = contentEList.indexOf(thisObject);
////                  LOGGER.info("contentObjectIndex=" + contentObjectIndex);
////                  if (contentObjectIndex != -1) {
////                    handleCutForDragAndDrop(dragEvent, sourceEObject);
////                    contentEList.add(contentEList.indexOf(thisObject), sourceEObject);
////                    eObjectParentTreeItem.rebuildChildren();
////
////                    success = true;
////                  }
////               } else {
////                  // It is a single value, which will be replaced.
////                  LOGGER.info("Single item; replace");
////                }
////              }
////            }
////          }
////        }
////        LOGGER.info("contentObject=" + thisObject.toString());
////        if (thisObject instanceof EObject) {
////          EObject contentEObject = (EObject) thisObject;
////          LOGGER.info("contentEObject=" + contentEObject.toString());
////        } else if (thisObject instanceof EReference) {
////          EReference contentEReference = (EReference) thisObject;
////          LOGGER.info("contentEReference=" + contentEReference.toString());
////        } else if (thisObject instanceof EList) {
////          @SuppressWarnings("unchecked")
////          EList<Object> contentEList = (EList<Object>) thisObject;
////          LOGGER.info("contentEList=" + contentEList.toString());
////        }
////        
////      
//      /* let the source know whether the item was successfully transferred and used */
//      dragEvent.setDropCompleted(success);
//      } else {
//        BiPredicate<EObjectTreeItem, Dragboard> handleDropFunction = ((EObjectTreeView) getTreeView()).getHandleDropFunction();
//        if (handleDropFunction != null) {
//          if (handleDropFunction.test((EObjectTreeItem) getTreeItem(), dragEvent.getDragboard())) {
//            LOGGER.info("Drop successful");
//          }
//        }
//      }
//
//      dragEvent.consume();
  }
  
//  /**
//   * Get the source EObject from the dragboard of a DragEvent.
//   * <p>
//   * If the specified dragboard has content of type EOBJECT_PATH. This content is used to retrieve the related
//   * EObject in the tree in which this node is contained.
//   * 
//   * @param dragboard a Dragboard
//   * @return the EObject specified in the EOBJECT_PATH of the content of the <code>dragboard</code>, or null if this content isn't available or doesn't specify a node.
//   */
//  private EObject getSourceObject(Dragboard dragboard) {
//    // Get the EOBJECT_PATH from the drag board, and if it isn't null use it to reconstruct the EObjectPath.
//    if (dragboard.hasContent(EOBJECT_PATH)) {
//      ByteBuffer eObjectPathBytes = (ByteBuffer) dragboard.getContent(EOBJECT_PATH);
//      if (eObjectPathBytes != null) {
//        LOGGER.info("EObjectPath received!!");
//        EObjectPath eObjectPath = new EObjectPath(eObjectPathBytes);
//
//        // Get the root EObject, which is needed (together with the eObjectPath) to get the source object.
//        EObjectTreeItem thisEObjectTreeItem = (EObjectTreeItem)  getTreeItem();
//        EObject rootEObject = thisEObjectTreeItem.getRootEObject();
//        EObject sourceEObject = eObjectPath.resolveEObjectPath(rootEObject);
//        
//        LOGGER.info("Resolved source EObject=" + sourceEObject.toString());
//        return sourceEObject;
//      }
//
//    }
//    return null;
//  }
  
//  /**
//   * Delete the source object of a drag and drop event.
//   * 
//   * @param event the Drag and Drop event.
//   * @param sourceEObject the source object of the Drag and Drop event.
//   */
//  private void handleCutForDragAndDrop(DragEvent event, EObject sourceEObject) {
//    // Remove object from its current location
//    Object eventSource = event.getGestureSource();
//    LOGGER.info("event source=" + eventSource);
//    EObjectTreeCell eventSourceCell = (EObjectTreeCell) eventSource;
//    EObjectTreeItem eventSourceTreeItem = (EObjectTreeItem) eventSourceCell.getTreeItem();
//    LOGGER.info("eventSourceTreeItem.value.structuralFeature=" + eventSourceTreeItem.getValue().getEStructuralFeature());
//    LOGGER.info("eventSourceTreeItem.value.object=" + eventSourceTreeItem.getValue().getObject());
//    EObjectTreeItem eventSourceParentsTreeItem = (EObjectTreeItem) eventSourceTreeItem.getParent();
//    LOGGER.info("eventSourceParentsTreeItem.value.structuralFeature=" + eventSourceParentsTreeItem.getValue().getEStructuralFeature());
//    LOGGER.info("eventSourceParentsTreeItem.value.object=" + eventSourceParentsTreeItem.getValue().getObject());
//    EStructuralFeature eventSourceParentsStructuralFeature = eventSourceParentsTreeItem.getValue().getEStructuralFeature();
//    if (eventSourceParentsStructuralFeature instanceof EReference) {
//      EReference eventSourceParentsReference = (EReference) eventSourceParentsStructuralFeature;
//      LOGGER.info("eventSourceParentsReference=" + eventSourceParentsReference);
//      
//      @SuppressWarnings("unchecked")
//      EList<Object> sourceParentEList = (EList<Object>) eventSourceParentsTreeItem.getValue().getObject();
//      sourceParentEList.remove(sourceEObject);
//      eventSourceParentsTreeItem.rebuildChildren();
//    }
//  }
  
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
    LOGGER.severe("=> dragEvent=" + EObjectTreeItem.dragEventToString(dragEvent));
    
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) getTreeItem();
    if (eObjectTreeItem == null) {
      return null;
    } else {
      return eObjectTreeItem.isDropPossible(dragEvent);
    }
    
  }

  /**
   * Update the {@code treeCellHelper} for the current content.
   * <p>
   * The type of content is specified by the {@link EObjectItemType} of the tree item. Each content
   * type has its own helper class as follows:
   * <pre>
   * EObjectItemType        helper class
   * OBJECT                 EObjectTreeCellHelperForObject
   * OBJECT_IN_LIST         EObjectTreeCellHelperForObjectInList
   * OBJECT_LIST            EObjectTreeCellHelperForObjectList
   * ATTRIBUTE_SIMPLE       EObjectTreeCellHelperForAttributeSimple
   * ATTRIBUTE_LIST         EObjectTreeCellHelperForAttributeList
   * ATTRIBUTE_LIST_VALUE   EObjectTreeCellHelperForAttributeListValue
   * </pre>
   * 
   * @param eObjectTreeItemContent the content of the tree item to be rendered by a tree cell.
   */
  private void updateTreeCellHelper(Object value) {
    LOGGER.info("=> EObjectTreeItemType=" + ((EObjectTreeItem) getTreeItem()).getEObjectTreeItemType());
    
    
    EObjectTreeItemType newTreeItemType = ((EObjectTreeItem) getTreeItem()).getEObjectTreeItemType();
    
    if (treeItemType == null  ||  treeItemType != newTreeItemType) {
      switch (newTreeItemType) {
      case OBJECT:
        treeCellHelper = new EObjectTreeCellHelperForObject(this);
        break;

      case OBJECT_LIST:
        treeCellHelper = new EObjectTreeCellHelperForObjectList(this);
        break;

      case ATTRIBUTE_SIMPLE:
        EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) getTreeItem();
        EStructuralFeature eStructuralFeature = eObjectTreeItem.getEStructuralFeature();
        if (eStructuralFeature instanceof EAttribute eAttribute) {
          EDataType eDataType = eAttribute.getEAttributeType();
          switch (eDataType.getName()) {
          case "EBoolean":
            treeCellHelper = new EObjectTreeCellHelperForAttributeBoolean(this);
            break;
            
          default:
            treeCellHelper = new EObjectTreeCellHelperForAttributeSimple(this);
            break;
          }
        } else {
          throw new RuntimeException("eStructuralFeature is not an eAttribute");
        }
        break;

      case ATTRIBUTE_LIST:
        treeCellHelper = new EObjectTreeCellHelperForAttributeList(this);
        break;

      case ATTRIBUTE_LIST_VALUE:
        treeCellHelper = new EObjectTreeCellHelperForAttributeListValue(this);
        break;
      }
      
      if (treeCellHelper == null) {
        LOGGER.severe("Null after switch");
      }
      
      treeItemType = newTreeItemType;
    }
    
    if (treeCellHelper == null) {
      LOGGER.severe("Null after if");
    }
    
    LOGGER.info("<= " + treeCellHelper.getClass().getName());
  }

  @Override
  public void startEdit() {
    LOGGER.info("=>");
    
    if (((EObjectTreeItem)getTreeItem()).getEObjectTreeItemType().isEditable()) {
      super.startEdit();
      treeCellHelper.startEdit(this);
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
