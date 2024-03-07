package goedegep.jfx.eobjecttreeview;

import java.text.Format;

import goedegep.util.text.Indent;

/**
 * This class is an {@link EObjectTreeItemDescriptor} for an item of type {@link EObjectTreeItemType.ATTRIBUTE_LIST_VALUE}.
 */
public class EObjectTreeItemAttributeListValueDescriptor extends EObjectTreeItemDescriptor {
  /**
   * If true the text value can span multiple lines.
   */
  private boolean isMultiLineText = false;
  
  /**
   * The formatter used to format and parse the object (optional).
   */
  private Format format = null;
  
  /**
   * Constructor
   */
  public EObjectTreeItemAttributeListValueDescriptor() {
    super(EObjectTreeItemDescriptorType.ATTRIBUTE_LIST_VALUE);
  }

  /**
   * Check whether it is a multi-line text.
   * 
   * @return true if it is a multi-line text, false otherwise.
   */
  public boolean isMultiLineText() {
    return isMultiLineText;
  }
  
  /**
   * Set whether it is a multi-line text or not.
   * 
   * @param isMultiLineText if true it is a multi-line text.
   * @return this
   */
  public EObjectTreeItemAttributeListValueDescriptor setMultiLineText(boolean isMultiLineText) {
    this.isMultiLineText = isMultiLineText;
    
    return this;
  }

  /**
   * Get the formatter used to format and parse the object.
   * 
   * @return the formatter used to format and parse the object.
   */
  public Format getFormat() {
    return format;
  }
  
  /**
   * Set the formatter used to format and parse the object.
   * 
   * @param format the formatter used to format and parse the object.
   * @return this
   */
  public EObjectTreeItemAttributeListValueDescriptor setFormat(Format format) {
    this.format = format;
    
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
  public EObjectTreeItemAttributeListValueDescriptor addNodeOperationDescriptor(NodeOperationDescriptor nodeOperationDescriptor) {
    super.addNodeOperationDescriptor(nodeOperationDescriptor);
    
    return this;
  }

  @Override
  public Object toString(Indent indent) {
    StringBuilder buf = new StringBuilder();
    
    buf.append(indent.toString()).append(getClass().getSimpleName())
    .append(": isMultiLineText=").append(isMultiLineText)
    .append(", format=").append(format);
    
    return buf.toString();
  }
}
