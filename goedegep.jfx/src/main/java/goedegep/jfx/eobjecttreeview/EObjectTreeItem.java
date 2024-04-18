package goedegep.jfx.eobjecttreeview;

import java.nio.ByteBuffer;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EStructuralFeature;

import goedegep.util.emf.EObjectPath;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

/**
 * This class represents the common part of EObject tree items.
 * <p>
 * There is a subclass (EObjectTreeItemFor<type>) for each EObjectTreeItemType.</br>
 * The children of an item are created when needed (i.e. upon the first call to getChildren()).<br/>
 * 
 * Note: We cannot make the value item specific, because if you use 'extends TreeItem<T>' then getChildren shall return a list of TreeItem<T>.
 * So this means that all items in the tree shall have the same type (which can thus only be Object).
 */
public abstract class EObjectTreeItem extends TreeItem<Object> {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeItem.class.getName());
  static final String NEWLINE = System.getProperty("line.separator");
  
  /**
   * This {@link DataFormat} is used to identify an EObject in a drag and drop event.
   */
  protected static final DataFormat EOBJECT_PATH = new DataFormat("EObjectPath");
  
  /**
   * Caches the result for method isLeaf().
   */
  protected Boolean isLeaf = null;
  
  /**
   * If true, the children of this item still have to be created.
   */
  protected boolean isFirstTimeChildren = true;
  
  /**
   * Reference to the tree view of which this item is part.
   */
  private EObjectTreeView eObjectTreeView = null;
  
  /**
   * The type of information stored in the item. This value determines what is stored in the other fields.
   */
  private EObjectTreeItemType eObjectTreeItemType = null;


  /**
   * Constructor.
   * 
   * @param value the value for this tree item (a value can be null)
   * @param eObjectTreeItemType the {@code EObjectTreeItemType} for this tree item (mandatory)
   * @param presentationDescriptor a TreeNode containing the specification for the representation of this item (mandatory)
   */
  public EObjectTreeItem(Object value, EObjectTreeItemType eObjectTreeItemType, EObjectTreeView eObjectTreeView) {
    super(value);
    
    this.eObjectTreeItemType = eObjectTreeItemType;
    this.eObjectTreeView = eObjectTreeView;
        
    LOGGER.info("<=>");
  }

  /**
   * Get the reference to the {@code EObjectTreeView} of which this item is part.
   * @return the reference to the tree view of which this item is part.
   */
  public EObjectTreeView getEObjectTreeView() {
    return eObjectTreeView;
  }
  
  /**
   * Get the {@code EObjectTreeItemType} of this item.
   * 
   * @return the {@code EObjectTreeItemType} of this item.
   */
  public EObjectTreeItemType getEObjectTreeItemType() {
    return eObjectTreeItemType;
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean isLeaf() {
    LOGGER.fine("=> eObjectTreeItemContent=" + (getValue() != null ? getValue().toString() : "<null>"));
    
    if (isLeaf == null) {
      isLeaf = isItemALeafItem();
    }
    
    LOGGER.info("<= " + isLeaf);
    return isLeaf;
  }
  
  /**
   * Determine whether this tree item is a leaf item.
   */
  abstract boolean isItemALeafItem();

  /**
   * @inheritDoc
   */
  @Override
  public ObservableList<TreeItem<Object>> getChildren() {
    LOGGER.info("=> " + toString());
    
    if (isFirstTimeChildren) {
      isFirstTimeChildren = false;
      ObservableList<TreeItem<Object>> children = buildChildren();
      if (children != null) {
        super.getChildren().setAll(children);
      }
    }
    
    LOGGER.info("<= via super.getChildren()");
    return super.getChildren();
  }
  
  /**
   * Rebuild the children of this node.
   * <p>
   * This method can be called after changes to this node.
   * TODO This should only be called for ...
   */
  public void rebuildChildren() {
    super.getChildren().clear();
    isLeaf = null;
    ObservableList<TreeItem<Object>> children = buildChildren();
    if (children != null) {
      super.getChildren().addAll(children);
    }
    
  }
  
  /**
   * Create the list of children of this tree item.
   * <p>
   * The items to be shown are defined by the presentation descriptors.<br/>

   * @return the list of children, or null if there are no children.
   */
  abstract ObservableList<TreeItem<Object>> buildChildren();
  

  /**
   * Get the index of this child within the list of children of the parent.
   * 
   * @return the index of this child.
   */
  public int getChildIndex() {
    int childIndex = 0;
    
    // move back through the 'list' via previousSibling() and count the
    // number of steps.
    TreeItem<Object> previousSibling = previousSibling();
    
    while(previousSibling != null) {
      childIndex++;
      previousSibling = previousSibling.previousSibling();
    }
    
    return childIndex;
  }
  
  /**
   * Find a child item of a specified item, which contains a specific EStructuralFeature.
   * <p>
   * This is used to handle a changed value, where we get information that a feature has changed for an object.<br/>
   * Note: this method only applies to EObjects, but we have it here so no casting is needed to call this method.
   * 
   * @param eStructuralFeature the EStructuralFeature of the child to be found. This value may not be null.
   * @return the specified child, or null if no such child exists.
   */
  EObjectTreeItem findChildTreeItem(EStructuralFeature eStructuralFeature) {
    throw new UnsupportedOperationException("This method cannot be called on type: " + this.getClass().getName());
  }
  
  /**
   * Get the {@code EStructuralFeature} of this tree item.
   * 
   * @param treeItem a {@code TreeItem<Object>}.
   * @return the {@code EStructuralFeature} of {@code treeItem}.
   */
  public abstract EStructuralFeature getEStructuralFeature();

  /**
   * Change the edit mode.
   * 
   * @param editMode
   */
  public void setEditMode(boolean editMode) {
    // If the children of this item aren't created yet, nothing has to be done.
    if (isFirstTimeChildren) {
      return;
    }
    
    if (editMode) {
      switchToEditMode();
    } else {
      switchToViewMode();
    }
  }
  
  /**
   * Switch to edit mode.
   * <p>
   * Add the 'empty' value nodes, which aren't shown in view mode.
   */
  abstract void switchToEditMode();
    
  /**
   * Switch to view mode.
   * <p>
   * The 'empty' nodes are removed.
   */
  abstract void switchToViewMode();
  
  /**
   * Check whether this item is in edit mode or not.
   * 
   * @return true if the tree is in edit mode, false otherwise.
   */
  public boolean isInEditMode() {
    return eObjectTreeView.isEditable();
  }
  
  /*
   * Start of Drag and Drop handling.
   */

  /**
   * Handle the starting of a drag event.
   * <p>
   * Override this method for tree items that support dragging.
   * 
   * @param event the {@code MouseEvent} which detected the drag start.
   */
  public void handleDragDetected(MouseEvent event, Node node) {
    event.consume();
  }

  /**
   * Check whether the information of the dragEvent can be dropped on this tree item and if so return the TransferMode.
   * <p>
   * Override this method for tree items that support dropping items.
   * 
   * @param dragEvent the Drag and Drop event.
   * @return true if the {@code dragEvent} can be handled.
   */
  public TransferMode isDropPossible(DragEvent dragEvent) {
    return null;
  }

  /**
   * Handle a drop.
   * <p>
   * Override this method for tree items that support dropping items.
   * 
   * @param dragEvent the Drag and Drop event.
   */
  public void handleDragDropped(DragEvent dragEvent) {
  }
  
  public static String dragEventToString(DragEvent dragEvent) {
    StringBuilder buf = new StringBuilder();
    
    buf.append(NEWLINE);
    
    Dragboard dragboard = dragEvent.getDragboard();
    buf.append("DataFormats: ");
    boolean first = true;
    for (DataFormat dataFormat: dragboard.getContentTypes()) {
      if (first) {
        first = false;
      } else {
        buf.append(", ");
      }
      buf.append(dataFormat);
    }
    buf.append(NEWLINE);
    
    if (dragboard.hasContent(EOBJECT_PATH)) {
      ByteBuffer eObjectPathBytes = (ByteBuffer) dragboard.getContent(EOBJECT_PATH);
      EObjectPath eObjectPath = new EObjectPath(eObjectPathBytes);
      buf.append("EObjectPath: ").append(NEWLINE).append(eObjectPath.toString());
    }
    
    Object gestureSourceObject = dragEvent.getGestureSource();
    buf.append("Gesture source class: ").append(gestureSourceObject != null ? gestureSourceObject.getClass().getSimpleName() : "<null>").append(NEWLINE);
    buf.append("Gesture source value: ").append(gestureSourceObject != null ? gestureSourceObject.toString() : "<null>").append(NEWLINE);
    Object gestureTargetObject = dragEvent.getGestureTarget();
    buf.append("Gesture target class: ").append(gestureTargetObject != null ? gestureTargetObject.getClass().getSimpleName() : "<null>").append(NEWLINE);
    buf.append("Gesture target value: ").append(gestureTargetObject != null ? gestureTargetObject.toString() : "<null>").append(NEWLINE);
    
    return buf.toString();
  }
  
  /*
   * End of Drag and Drop handling
   */
  
  public int getIndexInParentTreeItem() {
    TreeItem<Object> parentTreeItem = getParent();
    int index = 0;
    for (TreeItem<Object> child: parentTreeItem.getChildren()) {
      if (child == this) {
        break;
      }
      index++;
    }
    
    return index;
  }
  
  /**
   * This method shall return a text which represents as much as possible what is shown in the node.
   * <p>
   * This method is a kind of a toString method, which can e.g. be used to print a textual representation of a tree view.
   * 
   * @return an, as good as possible, textual representation of the cell.
   */
  public abstract String getText();
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(NEWLINE);
    buf.append("isFirstTimeChildren: ").append(isFirstTimeChildren).append(NEWLINE);
    buf.append("isLeaf: ").append(isLeaf).append(NEWLINE);
    buf.append("value: ").append(getValue() != null ? getValue().toString() : "<null>").append(NEWLINE);
    buf.append("type: ").append(eObjectTreeItemType.name()).append(NEWLINE);
    
    return buf.toString();
  }
}
