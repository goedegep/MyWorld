package goedegep.jfx.xtreetreeview;

import java.util.logging.Logger;

import goedegep.util.xtree.XTree;
import goedegep.util.xtree.mutable.MutableXTreeNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class XTreeTreeItem extends TreeItem<MutableXTreeNode> {
  private static final Logger LOGGER = Logger.getLogger(XTreeTreeItem.class.getName());
  
  /**
   * If true, the children of this item still have to be created.
   */
  private boolean isFirstTimeChildren = true;

  /**
   * Constructor
   * 
   * @param xTreeNode
   */
  public XTreeTreeItem(MutableXTreeNode xTreeNode) {
    super(xTreeNode);
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean isLeaf() {
    LOGGER.severe("=> MutableXTreeNode=" + XTree.nodeToString(getValue().getDataType(), getValue().getData()));
    
    MutableXTreeNode xTreeNode = getValue();
    boolean result = !xTreeNode.hasChild();
        
    LOGGER.severe("<= " + result);
    return result;
  }


  /**
   * @inheritDoc
   */
  @Override
  public ObservableList<TreeItem<MutableXTreeNode>> getChildren() {
    LOGGER.severe("=>");
    
    if (isFirstTimeChildren) {
      isFirstTimeChildren = false;
      ObservableList<TreeItem<MutableXTreeNode>> children = buildChildren();
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
   */
  public void rebuildChildren() {
    super.getChildren().clear();
    ObservableList<TreeItem<MutableXTreeNode>> children = buildChildren();
    if (children != null) {
      super.getChildren().addAll(children);
    }
  }
  
  /**
   * Create the list of children for a tree item.
   * <p>
   * The items to be shown are defined by the presentation descriptors.<br/>
   * A tree item is one of the following:
   * <ul>
   *   <li>
   *     type OBJECT - for an EObject<br/>
   *     Content:
   *     <ul>
   *       <li>
   *         eObjectTreeItemType: OBJECT
   *       </li>
   *       <li>
   *         object: the eObject
   *       </li>
   *       <li>
   *         eStructuralFeature: null
   *       </li>
   *       <li>
   *         presentationDescriptorTreeNode: the corresponding descriptor of type EObjectTreeItemClassDescriptor
   *       </li>
   *     </ul>
   *     There's one child for each EStructuralFeature of this EObject, which is either an EAttribute or an EReference.<br/>
   *     For a single value EAttribute the child is of type ATTRIBUTE_SIMPLE.<br/>
   *     For a list value EAttribute the child is of type ATTRIBUTE_LIST.<br/>
   *     For an EReference to a single value EObject the child is of type OBJECT.<br/>
   *     For an EReference to a list value EObject the child is of type OBJECT_LIST.<br/>
   *   </li>
   *   <li>
   *     type ATTRIBUTE_SIMPLE - for a single value EAttribute (i.e. not a list).
   *     Content:
   *     <ul>
   *       <li>
   *         eObjectTreeItemType: ATTRIBUTE_SIMPLE
   *       </li>
   *       <li>
   *         object: the attribute value
   *       </li>
   *       <li>
   *         eStructuralFeature: the EAttribute for this attribute
   *       </li>
   *       <li>
   *         presentationDescriptorTreeNode: the corresponding descriptor of type EObjectTreeItemAttributeDescriptor
   *       </li>
   *     </ul>
   *     This is a leaf node.
   *   </li>
   *   <li>
   *     type ATTRIBUTE_LIST - for a list type EAttribute.
   *     Content:
   *     <ul>
   *       <li>
   *         eObjectTreeItemType: ATTRIBUTE_LIST
   *       </li>
   *       <li>
   *         object: the EList with the attribute values
   *       </li>
   *       <li>
   *         eStructuralFeature: the EAttribute for this attribute
   *       </li>
   *       <li>
   *         presentationDescriptorTreeNode: the corresponding descriptor of type EObjectTreeItemAttributeDescriptor
   *       </li>
   *     </ul>
   *     There's one child for each element in the EList.
   *   </li>
   *   <li>
   *     type OBJECT_LIST - for a list of EObjects
   *     Content:
   *     <ul>
   *       <li>
   *         eObjectTreeItemType: OBJECT_LIST
   *       </li>
   *       <li>
   *         object: the EList with the eObjects
   *       </li>
   *       <li>
   *         eStructuralFeature: the EReference for this list of EObjects
   *       </li>
   *       <li>
   *         presentationDescriptor: the corresponding descriptor of type EObjectTreeItemClassListReferenceDescriptor
   *       </li>
   *     </ul>
   *     There's one child for each element in the EList.
   *   </li>
   * </ul>
   * 
   * @param eObjectTreeItem the EObjectTreeItem for which the children are to be created.
   * @return the list of children for eObjectTreeItem, or null if there are no children.
   */
  private ObservableList<TreeItem<MutableXTreeNode>> buildChildren() {
    LOGGER.severe("=> MutableXTreeNode=" + toString());
    
    ObservableList<TreeItem<MutableXTreeNode>> children = FXCollections.observableArrayList();;
    
    MutableXTreeNode xTreeNode = getValue();
    MutableXTreeNode childXTreeNode = xTreeNode.getFirstChild();
    while (childXTreeNode != null) {
      // create and add node
      children.add(new XTreeTreeItem(childXTreeNode));
//      switch (childXTreeNode.getDataType()) {
//      case BLOB:
//        
//        break;
//        
//      case BOOLEAN:
//        break;
//        
//      case INTEGER:
//        break;
//        
//      case STRING:
//        break;
//        
//      case TAG:
//        break;
//      }
      
      childXTreeNode = childXTreeNode.getNextSibling();
    }
        
    LOGGER.severe("<=");
    return children;
  }
}