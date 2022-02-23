package goedegep.jfx.eobjecttreeview;

import java.util.List;
import java.util.function.Function;

import goedegep.util.text.Indent;
import javafx.scene.image.Image;

/**
 * This class forms the common part for descriptors for an item in an EObjectTreeView.
 * <p>
 * A tree (represented as TreeNode) with these descriptors is
 * part of an {@link EObjectTreeDescriptor}.<br/>
 * The following properties are provided:
 * <ul>
 * <li>
 * expandOnCreation<br/>
 * If true, the tree item is expanded (i.e. its children are also displayed) when it is created.
 * </li>
 * <li>
 * nodeOperationDescriptors<br/>
 * A list of {@link NodeOperationDescriptor} which specifies the available operations for the tree item.
 * </li>
 * </ul>
 * The item descriptors for the different types of items have to extend this class.
 */
public abstract class EObjectTreeItemDescriptor {
  private EObjectTreeItemDescriptorType descriptorType;
  private boolean expandOnCreation = false;                          // Expand the node upon creation of the tree.
  private List<NodeOperationDescriptor> nodeOperationDescriptors;    // Defines the available operations for the node.
  private Function<Object, Image> nodeIconFunction;                  // A function to provide the node icon.

  public EObjectTreeItemDescriptor(EObjectTreeItemDescriptorType descriptorType, boolean expandOnCreation, List<NodeOperationDescriptor> nodeOperationDescriptors) {
    this(descriptorType, expandOnCreation, nodeOperationDescriptors, null);
  }

  public EObjectTreeItemDescriptor(EObjectTreeItemDescriptorType descriptorType, boolean expandOnCreation, List<NodeOperationDescriptor> nodeOperationDescriptors,
      Function<Object, Image> nodeIconFunction) {
    this.descriptorType = descriptorType;
    this.expandOnCreation = expandOnCreation;
    this.nodeOperationDescriptors = nodeOperationDescriptors;
    this.nodeIconFunction = nodeIconFunction;
  }

  public EObjectTreeItemDescriptorType getDescriptorType() {
    return descriptorType;
  }

  protected void setDescriptorType(EObjectTreeItemDescriptorType descriptorType) {
    this.descriptorType = descriptorType;
  }

  public boolean isExpandOnCreation() {
    return expandOnCreation;
  }

  public List<NodeOperationDescriptor> getNodeOperationDescriptors() {
    return nodeOperationDescriptors;
  }

  public java.util.function.Function<Object, Image> getNodeIconFunction() {
    return nodeIconFunction;
  }


  public enum EObjectTreeItemDescriptorType {
    CLASS,
    ATTRIBUTE,
    ATTRIBUTE_LIST,
    ATTRIBUTE_LIST_VALUE,
    CLASS_LIST,
    CLASS_REFERENCE
  }


  protected abstract Object toString(Indent indent);
}

