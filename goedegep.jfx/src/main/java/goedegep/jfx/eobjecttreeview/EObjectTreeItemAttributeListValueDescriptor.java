package goedegep.jfx.eobjecttreeview;

import java.text.Format;
import java.util.List;

import goedegep.util.text.Indent;

/**
 * This class is an {@link EObjectTreeItemDescriptor} for an item of type {@link EObjectTreeItemType.ATTRIBUTE_LIST_VALUE}.
 */
public class EObjectTreeItemAttributeListValueDescriptor extends EObjectTreeItemDescriptor {
  private boolean isMultiLineText;  // If true the text value can span multiple lines.
  private Format format;            // The formatter used to format and parse the object.
  
  public EObjectTreeItemAttributeListValueDescriptor(boolean isMultiLineText, List<NodeOperationDescriptor> nodeOperationDescriptors) {
    super(EObjectTreeItemDescriptorType.ATTRIBUTE_LIST_VALUE, false, nodeOperationDescriptors);
    
    this.isMultiLineText = isMultiLineText;
    format = null;
  }
  
  public EObjectTreeItemAttributeListValueDescriptor(Format format, List<NodeOperationDescriptor> nodeOperationDescriptors) {
    super(EObjectTreeItemDescriptorType.ATTRIBUTE_LIST_VALUE, false, nodeOperationDescriptors);
    
    isMultiLineText = false;
    this.format = format;
  }

  protected boolean isMultiLineText() {
    return isMultiLineText;
  }

  protected Format getFormat() {
    return format;
  }

  @Override
  protected Object toString(Indent indent) {
    StringBuilder buf = new StringBuilder();
    
    buf.append(indent.toString()).append(getClass().getSimpleName());
    buf.append(": isMultiLineText=");
    buf.append(isMultiLineText);
    buf.append(", format=");
    buf.append(format);
    
    return buf.toString();
  }
}
