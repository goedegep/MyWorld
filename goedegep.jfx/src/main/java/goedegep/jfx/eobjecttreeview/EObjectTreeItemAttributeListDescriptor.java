package goedegep.jfx.eobjecttreeview;

import java.util.Objects;

import org.eclipse.emf.ecore.EAttribute;

import goedegep.util.text.Indent;

/**
 * This class is an {@link EObjectTreeItemDescriptor} for an item of type {@link EObjectTreeItemType.ATTRIBUTE_LIST}.
 */
public class EObjectTreeItemAttributeListDescriptor extends EObjectTreeItemDescriptor {
  /**
   * Identifies the attribute to which this descriptor applies
   */
  private EAttribute eAttribute = null;
  
  /**
   * Text to display instead of the attribute name (optional).
   */
  private String labelText = null;
  
  /**
   * Descriptor for the values in the list (optional).
   */
  private EObjectTreeItemAttributeListValueDescriptor listValuesDescriptor = null;
  
  /**
   * Constructor
   * 
   * @param eAttribute identifies the attribute to which this descriptor applies. This value may not be null.
   */
  public EObjectTreeItemAttributeListDescriptor(EAttribute eAttribute) {
    super(EObjectTreeItemDescriptorType.ATTRIBUTE_LIST);
    
    Objects.requireNonNull(eAttribute, "The value of eAttribute may not be null");
    
    this.eAttribute = eAttribute;
    this.labelText = eAttribute.getName();
  }
  
  /**
   * Get the attribute to which this descriptor applies.
   * 
   * @return the attribute to which this descriptor applies.
   */
  public EAttribute getEAttribute() {
    return eAttribute;
  }

  /**
   * Get the text to display.
   * 
   * @return the text to display.
   */
  public String getLabelText() {
    return labelText;
  }
  
  /**
   * Set the text to display instead of the attribute name.
   * 
   * @param labelText the text to display instead of the attribute name.
   * @return this
   */
  public EObjectTreeItemAttributeListDescriptor setLabelText(String labelText) {
    this.labelText = labelText;
    
    return this;
  }

  /**
   * Get the descriptor for the values in the list.
   * 
   * @return the descriptor for the values in the list.
   */
  public EObjectTreeItemAttributeListValueDescriptor geteObjectTreeItemAttributeListValueDescriptor() {
    return listValuesDescriptor;
  }
  
  /**
   * Set the descriptor for the values in the list.
   * 
   * @param listValuesDescriptor the descriptor for the values in the list.
   * @return this
   */
  public EObjectTreeItemAttributeListDescriptor setListValuesDescriptor(EObjectTreeItemAttributeListValueDescriptor listValuesDescriptor) {
    this.listValuesDescriptor = listValuesDescriptor;
    
    return this;
  }
  
  /**
   * Add a node operation.
   * <p>
   * This operation will be added to the end of the list of operations.
   * TODO instead of overwriting this method implement a solution 
   * 
   * @param nodeOperationDescriptor descriptor for the operation to be added.
   * @return this
   */
  public EObjectTreeItemAttributeListDescriptor addNodeOperationDescriptor(NodeOperationDescriptor nodeOperationDescriptor) {
    super.addNodeOperationDescriptor(nodeOperationDescriptor);
    
    return this;
  }

  @Override
  public Object toString(Indent indent) {
    StringBuilder buf = new StringBuilder();
    
    buf.append(indent.toString()).append(getClass().getSimpleName());
    buf.append(": eAttribute=");
    buf.append(eAttribute != null ? eAttribute.getName() : "<null>");
    buf.append(", labelText=");
    buf.append(labelText);
    buf.append("eObjectTreeItemAttributeListValueDescriptor=");
    if (listValuesDescriptor != null) {
      buf.append(listValuesDescriptor);
    } else {
      buf.append("<null>");
    }
    
    return buf.toString();
  }
}
