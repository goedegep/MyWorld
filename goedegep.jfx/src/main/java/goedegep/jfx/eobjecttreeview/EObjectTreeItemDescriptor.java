package goedegep.jfx.eobjecttreeview;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
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
  static final String NEWLINE = System.getProperty("line.separator");

  /**
   * Indication of the kind of node that this descriptor is for (mandatory).
   */
  private EObjectTreeItemDescriptorType descriptorType = null;
  
  /**
   * If true, the node will be expanded upon creation.
   */
  private boolean expandOnCreation = false;
  
  /**
   * If true, the text is in a strong font (bold).
   */
  private boolean strongText = false;
  
  /**
   * Defines the available operations for the node (optional).
   */
  private List<NodeOperationDescriptor> nodeOperationDescriptors = null;
  
  
  /**
   * Function to provide the node text (optional).
   */
  private Function<EObject, String> nodeTextFunction = null;
  
  /**
   * A function to provide the node icon (optional).
   */
  private Function<Object, Image> nodeIconFunction = null;
  

  /**
   * Constructor.
   * 
   * @param descriptorType Indication of the kind of node that this descriptor is for. This value may not be null.
   */
  public EObjectTreeItemDescriptor(EObjectTreeItemDescriptorType descriptorType) {
    java.util.Objects.requireNonNull(descriptorType, "The descriptorType may not be null");
    
    this.descriptorType = descriptorType;
  }
  
  /**
   * Get the descriptor type.
   * 
   * @return the descriptor type.
   */
  public EObjectTreeItemDescriptorType getDescriptorType() {
    return descriptorType;
  }

  /**
   * Check whether the node shall be expanded on creation or not.
   * 
   * @return whether the node shall be expanded on creation or not.
   */
  public boolean isExpandOnCreation() {
    return expandOnCreation;
  }
  
  /**
   * Set whether the node shall be expanded upon creation or not
   * 
   * @param expandOnCreation if true, the node will be expanded upon creation.
   * @return this
   */
  public EObjectTreeItemDescriptor setExpandOnCreation(boolean expandOnCreation) {
    this.expandOnCreation = expandOnCreation;
    
    return this;
  }

  /**
   * Check whether the text shall be in a strong font (bold).
   * 
   * @return true if the text shall be in a strong font (bold), false otherwise.
   */
  public boolean isStrongText() {
    return strongText;
  }

  /**
   * Set whether the text shall be in a strong font (bold) or not.
   * 
   * @param strongText if true the text shall be in a strong font (bold).
   * @return this
   */
  public EObjectTreeItemDescriptor setStrongText(boolean strongText) {
    this.strongText = strongText;
    
    return this;
  }

  /**
   * Get the available operations for the node.
   * 
   * @return the available operations for the node.
   */
  public List<NodeOperationDescriptor> getNodeOperationDescriptors() {
    return nodeOperationDescriptors;
  }
  
  /**
   * Add a node operation.
   * <p>
   * This operation will be added to the end of the list of operations.
   * 
   * @param nodeOperationDescriptor descriptor for the operation to be added.
   * @return this
   */
  public EObjectTreeItemDescriptor addNodeOperationDescriptor(NodeOperationDescriptor nodeOperationDescriptor) {
    if (nodeOperationDescriptors == null) {
      nodeOperationDescriptors = new ArrayList<>();
    }
    
    nodeOperationDescriptors.add(nodeOperationDescriptor);
    
    return this;
  }

  /**
   * Get the function to provide the node text.
   * 
   * @return the function to provide the node text.
   */
  public Function<EObject, String> getNodeTextFunction() {
    return nodeTextFunction;
  }
  
  /**
   * Set the function to provide the node text.
   * 
   * @param nodeTextFunction the function to provide the node text.
   * @return this
   */
  public EObjectTreeItemDescriptor setNodeTextFunction(Function<EObject, String> nodeTextFunction) {
    this.nodeTextFunction = nodeTextFunction;
    
    return this;
  }

  /**
   * Get the function to provide the node icon.
   * 
   * @return the function to provide the node icon.
   */
  public Function<Object, Image> getNodeIconFunction() {
    return nodeIconFunction;
  }
  
  /**
   * Set the function to provide the node icon.
   * @param nodeIconFunction the function to provide the node icon.
   * @return this
   */
  public EObjectTreeItemDescriptor setNodeIconFunction(Function<Object, Image> nodeIconFunction) {
    this.nodeIconFunction = nodeIconFunction;
    
    return this;
  }

  /**
   * Indication of the node type for which this is a descriptor.
   */
  public enum EObjectTreeItemDescriptorType {
    CLASS,
    ATTRIBUTE,
    ATTRIBUTE_LIST,
    ATTRIBUTE_LIST_VALUE,
    CLASS_LIST,
    CLASS_REFERENCE
  }

  protected Object toString(Indent indent) {
    StringBuilder buf = new StringBuilder();
    
    buf.append(indent.toString()).append("descriptorType: ").append(descriptorType).append(NEWLINE);
    buf.append(indent.toString()).append("expandOnCreation: ").append(expandOnCreation).append(NEWLINE);
    buf.append(indent.toString()).append("strongText: ").append(strongText).append(NEWLINE);
    buf.append(indent.toString()).append("nodeOperationDescriptors: ");
    if (nodeOperationDescriptors != null) {
      for (NodeOperationDescriptor nodeOperationDescriptor: nodeOperationDescriptors) {
        buf.append(nodeOperationDescriptor.getOperation().name());
      }
    } else {
      buf.append("<not set>");
    }
    buf.append(NEWLINE);
    buf.append(indent.toString()).append("buildText: ").append(nodeTextFunction != null ? "Set" : "Not set").append(NEWLINE);
    buf.append(indent.toString()).append("nodeIconFunction: ").append(nodeIconFunction != null ? "Set" : "Not set").append(NEWLINE);
    
    return buf.toString();
  }
}

