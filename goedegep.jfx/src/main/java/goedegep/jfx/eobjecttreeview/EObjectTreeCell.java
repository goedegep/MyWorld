package goedegep.jfx.eobjecttreeview;

import java.nio.ByteBuffer;
import java.util.function.BiPredicate;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import goedegep.util.emf.EObjectPath;
import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;

/**
 * This class provides the {@link TreeCell} for an EObjectTreeView.
 * <p>
 * A TreeCell is used to render an item in a TreeView.</br>
 * Although the rendering largely depends on the type of the item, there is only a single type of TreeCell.
 * Therefore the actual work is done by type depend helpers. These helpers all implement the {@link EObjectTreeCellHelper} interface.<br/>
 * A TreeCell instance is also reused to render different items. Each time the TreeCell instance is assigned to an item, the method updateItem is called.
 * Therefore this method creates the right helper for the item type and then delegates the work to the helper by calling updateItem on the helper.<br/>
 * The following methods delegate all the work to the helper: startEdit(), commitEdit(), cancelEdit().
 * 
 */
public class EObjectTreeCell extends TreeCell<EObjectTreeItemContent> {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeCell.class.getName());
  private static final DataFormat EOBJECT_PATH = new DataFormat("EObjectPath");
    
  private EObjectTreeCellHelper eObjectTreeCellHelper = null;   // The type specific helper, to which most work is delegated.

  /**
   * Constructor.
   */
  public EObjectTreeCell() {
    LOGGER.info("=>");
    
    EObjectTreeCell thisCell = this;
    
    /*
     * Drag & Drop handling.
     * For now only moving (cut and paste) of EObjects is supported.
     * For an EObject the Data Format EOBJECT_PATH is used, in which case an EObjectPath is placed on the clipboard.
     * When dropped on a list, the source is moved to the end of the list.
     * When dropped on a list item, the source is moved to the list, before that item.
     */
    setOnDragDetected(this::handleDragDetected);
    
    /**
     * The dragenter event is fired when a dragged element or text selection enters a valid drop target.
     */
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
  
  /*
   * Handle the starting of a drag event.
   * <p>
   * Only dragging of an EObject is supported.<br/>
   * If the item is an EObject, an {@code EObjectPath} is created and serialized as bytes.
   * {@code ClipboardContent} is created with this data, with type {@code EOBJECT_PATH}.
   * A {@code Dragboard} is created for a MOVE and with the {@code ClipboardContent} as content.
   */
  private void handleDragDetected(MouseEvent event) {
    LOGGER.info("=>");
    
    EObjectTreeItem eObjectTreeItem = (EObjectTreeItem) getTreeItem();
    if (eObjectTreeItem == null) {
      return;
    }
    
    EObjectTreeItemContent eObjectTreeItemContent = eObjectTreeItem.getValue();
    Object object = eObjectTreeItemContent.getObject();
    if (object instanceof EObject eObject) {
      EObjectPath eObjectPath = new EObjectPath(eObject);
      ByteBuffer eObjectPathAsBytes = eObjectPath.getSerializedData();
      
      ClipboardContent clipboardContent = new ClipboardContent();
      clipboardContent.put(EOBJECT_PATH, eObjectPathAsBytes);
      
      Dragboard dragBoard = startDragAndDrop(TransferMode.MOVE);
      dragBoard.setContent(clipboardContent);
    }

    event.consume();
  }
  
  private void handleDragEntered(DragEvent dragEvent) {
    LOGGER.info("=>");
    /* the drag-and-drop gesture entered the target */
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

  private void handleDragDropped(DragEvent dragEvent) {
      LOGGER.info("=>");
      
      boolean success = false;
      
      // Get the EOBJECT_PATH from the drag board, and if it isn't null use it to reconstruct the EObjectPath.
      Dragboard dragboard = dragEvent.getDragboard();
      if (dragboard.hasContent(EOBJECT_PATH)) {
      ByteBuffer eObjectPathBytes = (ByteBuffer) dragboard.getContent(EOBJECT_PATH);
      if (eObjectPathBytes != null) {
        LOGGER.info("EObjectPath received!!");
        EObjectPath eObjectPath = new EObjectPath(eObjectPathBytes);
        
        // Get the root EObject, which is needed (together with the eObjectPath) to get the source object.
        EObjectTreeItem thisEObjectTreeItem = (EObjectTreeItem)  getTreeItem();
        EObject rootEObject = thisEObjectTreeItem.getRootEObject();
        EObject sourceEObject = eObjectPath.resolveEObjectPath(rootEObject);
        LOGGER.info("Resolved source EObject=" + sourceEObject.toString());
        
        EObjectTreeItemContent thisEObjectTreeItemContent = thisEObjectTreeItem.getValue();
        Object thisObject = thisEObjectTreeItemContent.getObject();
        LOGGER.info("thisObject=" + thisObject.toString());
        if (thisObject.equals(sourceEObject)) {
          throw new RuntimeException("Cannot cut and past object to itself: object=" + sourceEObject.toString());
        }
        EStructuralFeature thisStructuralFeature = thisEObjectTreeItemContent.getEStructuralFeature();
        if (thisStructuralFeature != null) {
          LOGGER.info("contentStructuralFeature=" + thisStructuralFeature.toString());
          if (thisStructuralFeature instanceof EReference) {
            EReference contentEReference = (EReference) thisStructuralFeature;
            EClass contentReferenceType = contentEReference.getEReferenceType();
            if (contentReferenceType.isSuperTypeOf(sourceEObject.eClass())) {
              LOGGER.info("Yes it is a supertype");
              
              handleCutForDragAndDrop(dragEvent, sourceEObject);
              
              @SuppressWarnings("unchecked")
              EList<Object> contentEList = (EList<Object>) thisObject;
              contentEList.add(sourceEObject);
              thisEObjectTreeItem.rebuildChildren();
              
              success = true;
            }
          }
        } else {
          LOGGER.info("Node has no StructuralFeature");
          if (sourceEObject.getClass().equals(thisObject.getClass())) {
            // this item is either a single value, which can be overwritten, or it is part of a list, so the source can be inserted.
            LOGGER.info("Object has same type");
            EObjectTreeItem eObjectParentTreeItem = (EObjectTreeItem)  thisEObjectTreeItem.getParent();
            EObjectTreeItemContent eObjectParentTreeItemContent = eObjectParentTreeItem.getValue();
            EStructuralFeature parentStructuralFeature = eObjectParentTreeItemContent.getEStructuralFeature();
            if (parentStructuralFeature != null) {
              LOGGER.info("parentStructuralFeature=" + parentStructuralFeature.toString());
              if (parentStructuralFeature instanceof EReference) {
                EReference parentEReference = (EReference) parentStructuralFeature;
                if (parentEReference.isMany()) {
                  
                  // It is a list, so insert before this item.
                  LOGGER.info("Item is part of a list; insert before");
                  @SuppressWarnings("unchecked")
                  EList<Object> contentEList = (EList<Object>) eObjectParentTreeItemContent.getObject();
                  LOGGER.info("contentEList=" + contentEList.toString());
                  LOGGER.info("contentObject=" + thisObject.toString());
                  int contentObjectIndex = contentEList.indexOf(thisObject);
                  LOGGER.info("contentObjectIndex=" + contentObjectIndex);
                  if (contentObjectIndex != -1) {
                    handleCutForDragAndDrop(dragEvent, sourceEObject);
                    contentEList.add(contentEList.indexOf(thisObject), sourceEObject);
                    eObjectParentTreeItem.rebuildChildren();

                    success = true;
                  }
               } else {
                  // It is a single value, which will be replaced.
                  LOGGER.info("Single item; replace");
                }
              }
            }
          }
        }
        LOGGER.info("contentObject=" + thisObject.toString());
        if (thisObject instanceof EObject) {
          EObject contentEObject = (EObject) thisObject;
          LOGGER.info("contentEObject=" + contentEObject.toString());
        } else if (thisObject instanceof EReference) {
          EReference contentEReference = (EReference) thisObject;
          LOGGER.info("contentEReference=" + contentEReference.toString());
        } else if (thisObject instanceof EList) {
          @SuppressWarnings("unchecked")
          EList<Object> contentEList = (EList<Object>) thisObject;
          LOGGER.info("contentEList=" + contentEList.toString());
        }
        
      }
      
      /* let the source know whether the item was successfully transferred and used */
      dragEvent.setDropCompleted(success);
      } else {
        BiPredicate<EObjectTreeItem, Dragboard> handleDropFunction = ((EObjectTreeView) getTreeView()).getHandleDropFunction();
        if (handleDropFunction != null) {
          if (handleDropFunction.test((EObjectTreeItem) getTreeItem(), dragboard)) {
            LOGGER.info("Drop successful");
          }
        }
      }

      dragEvent.consume();
  }
  
  /**
   * Delete the source object of a drag and drop event.
   * 
   * @param event the Drag and Drop event.
   * @param sourceEObject the source object of the Drag and Drop event.
   */
  private void handleCutForDragAndDrop(DragEvent event, EObject sourceEObject) {
    // Remove object from its current location
    Object eventSource = event.getGestureSource();
    LOGGER.info("event source=" + eventSource);
    EObjectTreeCell eventSourceCell = (EObjectTreeCell) eventSource;
    EObjectTreeItem eventSourceTreeItem = (EObjectTreeItem) eventSourceCell.getTreeItem();
    LOGGER.info("eventSourceTreeItem.value.structuralFeature=" + eventSourceTreeItem.getValue().getEStructuralFeature());
    LOGGER.info("eventSourceTreeItem.value.object=" + eventSourceTreeItem.getValue().getObject());
    EObjectTreeItem eventSourceParentsTreeItem = (EObjectTreeItem) eventSourceTreeItem.getParent();
    LOGGER.info("eventSourceParentsTreeItem.value.structuralFeature=" + eventSourceParentsTreeItem.getValue().getEStructuralFeature());
    LOGGER.info("eventSourceParentsTreeItem.value.object=" + eventSourceParentsTreeItem.getValue().getObject());
    EStructuralFeature eventSourceParentsStructuralFeature = eventSourceParentsTreeItem.getValue().getEStructuralFeature();
    if (eventSourceParentsStructuralFeature instanceof EReference) {
      EReference eventSourceParentsReference = (EReference) eventSourceParentsStructuralFeature;
      LOGGER.info("eventSourceParentsReference=" + eventSourceParentsReference);
      
      @SuppressWarnings("unchecked")
      EList<Object> sourceParentEList = (EList<Object>) eventSourceParentsTreeItem.getValue().getObject();
      sourceParentEList.remove(sourceEObject);
      eventSourceParentsTreeItem.rebuildChildren();
    }
  }
  
  /**
   * Check whether the information of the dragEvent can be dropped on this tree item.
   * 
   * @param dragEvent the Drag and Drop event.
   * @return true if the {@code dragEvent} can be handled.
   */
  private TransferMode isDropPossible(DragEvent dragEvent) {
    // Cannot drop on itself
    if (dragEvent.getGestureSource() == this) {
      return null;
    }
    
    Dragboard dragboard = dragEvent.getDragboard();
    if (dragboard.hasContent(EOBJECT_PATH)  &&
        isDropPossible((ByteBuffer) dragboard.getContent(EOBJECT_PATH))) {
      return TransferMode.MOVE;
    }
    
    BiPredicate<EObjectTreeItem, Dragboard> isDropPossibleFunction = ((EObjectTreeView) getTreeView()).getIsDropPossibleFunction();
    if (isDropPossibleFunction != null) {
      if (isDropPossibleFunction.test((EObjectTreeItem) getTreeItem(), dragboard)) {
        return TransferMode.COPY;
      }
    }
    
    return null;
  }
   
  /**
   * Check whether an EObject, represented by the binary representation of its EObjectPath, can be dropped on this tree item.
   * 
   * @param eObjectPathBytes binary representation of the EObjectPath of the dragged EObject.
   * @return true if the object can be dropped on this cell, false otherwise.
   */
  private boolean isDropPossible(ByteBuffer eObjectPathBytes) {
    LOGGER.info("=>");
    boolean returnValue = false;
    
    if (eObjectPathBytes != null) {
      LOGGER.info("EObjectPath received!!");
      EObjectPath eObjectPath = new EObjectPath(eObjectPathBytes);
      
      // Get the root EObject, which is needed (together with the eObjectPath) to get the source object.
      EObjectTreeItem eObjectTreeItem = (EObjectTreeItem)  getTreeItem();
      EObject rootEObject = eObjectTreeItem.getRootEObject();
      EObject sourceEObject = eObjectPath.resolveEObjectPath(rootEObject);
      LOGGER.info("Resolved source EObject=" + sourceEObject.toString());
     
      EObjectTreeItemContent eObjectTreeItemContent = eObjectTreeItem.getValue();
      EStructuralFeature contentStructuralFeature = eObjectTreeItemContent.getEStructuralFeature();
      if (contentStructuralFeature != null) {
        LOGGER.info("contentStructuralFeature=" + contentStructuralFeature.toString());
        if (contentStructuralFeature instanceof EReference) {
          EReference contentEReference = (EReference) contentStructuralFeature;
          EClass contentReferenceType = contentEReference.getEReferenceType();
          if (contentReferenceType.isSuperTypeOf(sourceEObject.eClass())) {
            LOGGER.info("Yes it is a supertype");
            returnValue = true;
          }
//          if (sourceEObject.eClass().equals(contentReferenceType)) {
//            LOGGER.severe("Type is OK");
//            returnValue = true;
//          }
        }
      } else {
        Object object = eObjectTreeItemContent.getObject();
        if (sourceEObject.getClass().equals(object.getClass())) {
          // this item is either a single value, which can be overwritten, or it is part of a list, so the source can be inserted.
          LOGGER.info("Object has same type");
          returnValue = true;
        }
      }
    }
    
    LOGGER.info("=> " + returnValue);
    return returnValue;
  }
  
  @Override
  public void updateItem(EObjectTreeItemContent eObjectTreeItemContent, boolean empty) {
    LOGGER.info("=> item=" + (eObjectTreeItemContent != null ? eObjectTreeItemContent.toString() : "(null)") + ", empty=" + empty);
    
//    // TEMP
//    TreeItem<EObjectTreeItemContent> treeItem = getTreeItem();
//    if (treeItem != null) {
//      EObjectTreeItemContent content = treeItem.getValue();
//      Object value = content.getObject();
//      if (!"goedegep.vacations.model.impl.VacationsImpl".equals(value.getClass().getName())) {
//        TreeItem<EObjectTreeItemContent> parentTreeItem = treeItem.getParent();
//        if (parentTreeItem == null) {
//          LOGGER.severe("parent is null for: " + treeItem.toString());
//        }
//      }
//    }

    super.updateItem(eObjectTreeItemContent, empty);
    
    if (empty) {
      setText(null);
      setGraphic(null);
      
      eObjectTreeCellHelper = null;
    } else {
      eObjectTreeCellHelper = createEObjectTreeCellHelper(eObjectTreeItemContent);
      
      eObjectTreeCellHelper.updateItem(eObjectTreeItemContent);
    }
        
    LOGGER.info("<=");
  }

  /**
   * Create a helper for the type of content of the tree item related to a tree cell.
   * <p>
   * The type of content is specified by the {@link EObjectItemType}, which is part of the EObjectTreeItemContent. Each content
   * type has its own helper class as follows:
   * <pre>
   * EObjectItemType        helper class
   * OBJECT                 EObjectTreeCellHelperForObject
   * OBJECT_LIST            EObjectTreeCellHelperForObjectList
   * ATTRIBUTE_SIMPLE       EObjectTreeCellHelperForAttributeSimple
   * ATTRIBUTE_LIST         EObjectTreeCellHelperForAttributeList
   * ATTRIBUTE_LIST_VALUE   EObjectTreeCellHelperForAttributeListValue
   * </pre>
   * 
   * @param eObjectTreeItemContent the content of the tree item to be rendered by a tree cell.
   * @return an EObjectTreeCellHelper a tree cell helper of the right type.
   */
  private EObjectTreeCellHelper createEObjectTreeCellHelper(EObjectTreeItemContent eObjectTreeItemContent) {
    LOGGER.info("=> EObjectTreeItemType=" + eObjectTreeItemContent.getEObjectTreeItemType());
    
    EObjectTreeCellHelper helper = null;
    
    switch (eObjectTreeItemContent.getEObjectTreeItemType()) {
    case OBJECT:
      helper = new EObjectTreeCellHelperForObject(this);
      break;
      
    case OBJECT_LIST:
      helper = new EObjectTreeCellHelperForObjectList(this);
      break;
      
    case ATTRIBUTE_SIMPLE:
      helper = new EObjectTreeCellHelperForAttributeSimple(this);
      break;
      
    case ATTRIBUTE_LIST:
      helper = new EObjectTreeCellHelperForAttributeList(this);
      break;
      
    case ATTRIBUTE_LIST_VALUE:
      helper = new EObjectTreeCellHelperForAttributeListValue(this);
      break;
    }
    
    LOGGER.info("<= " + helper.getClass().getName());
    return helper;
  }

  @Override
  public void startEdit() {
    LOGGER.info("=>");
    
    if (getItem().getEObjectTreeItemType().isEditable()) {
      super.startEdit();
      eObjectTreeCellHelper.startEdit(this);
    }
    
    LOGGER.info("<=");
  }
  
  @Override
  public void commitEdit(EObjectTreeItemContent newValue) {
    eObjectTreeCellHelper.commitEdit(getTreeItem(), newValue);
    
    super.commitEdit(newValue);
  }

  @Override
  public void cancelEdit() {
    LOGGER.info("=>");
    
    super.cancelEdit();
    
    eObjectTreeCellHelper.cancelEdit();

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
    return eObjectTreeCellHelper.getText();
  }
}
