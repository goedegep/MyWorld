package goedegep.jfx.eobjecttreeview;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;

import goedegep.util.text.Indent;

public class EObjectTreeItemAttributeListDescriptor extends EObjectTreeItemDescriptor {
  private EAttribute eAttribute;    // identifies the attribute to which this descriptor applies
  private String labelText;         // Text to display instead of the attribute name.
  private EObjectTreeItemAttributeListValueDescriptor eObjectTreeItemAttributeListValueDescriptor;
  
  public EObjectTreeItemAttributeListDescriptor(
      EAttribute eAttribute,
      String labelText,
      boolean isMultiLineText,
      List<NodeOperationDescriptor> nodeOperationDescriptors,
      EObjectTreeItemAttributeListValueDescriptor eObjectTreeItemAttributeListValueDescriptor) {
    super(EObjectTreeItemDescriptorType.ATTRIBUTE_LIST, false, nodeOperationDescriptors);
    
    this.eAttribute = eAttribute;
    this.labelText = labelText;
    this.eObjectTreeItemAttributeListValueDescriptor = eObjectTreeItemAttributeListValueDescriptor;
  }

  protected EAttribute getEAttribute() {
    return eAttribute;
  }

  protected String getLabelText() {
    return labelText;
  }

  protected EObjectTreeItemAttributeListValueDescriptor geteObjectTreeItemAttributeListValueDescriptor() {
    return eObjectTreeItemAttributeListValueDescriptor;
  }

  @Override
  protected Object toString(Indent indent) {
    StringBuilder buf = new StringBuilder();
    
    buf.append(indent.toString()).append(getClass().getSimpleName());
    buf.append(": eAttribute=");
    buf.append(eAttribute != null ? eAttribute.getName() : "<null>");
    buf.append(", labelText=");
    buf.append(labelText);
    buf.append("eObjectTreeItemAttributeListValueDescriptor=");
    if (eObjectTreeItemAttributeListValueDescriptor != null) {
      buf.append(eObjectTreeItemAttributeListValueDescriptor);
    } else {
      buf.append("<null>");
    }
    
    return buf.toString();
  }
}
