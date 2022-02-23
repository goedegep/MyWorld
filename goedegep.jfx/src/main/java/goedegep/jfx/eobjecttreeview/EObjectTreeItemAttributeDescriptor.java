package goedegep.jfx.eobjecttreeview;

import java.text.Format;
import java.util.List;
import java.util.function.Function;

import org.eclipse.emf.ecore.EAttribute;

import goedegep.util.text.Indent;

/**
 * This class is an {@link EObjectTreeItemDescriptor} for an item of type {@link EObjectTreeItemType.ATTRIBUTE_SIMPLE}.
 */
public class EObjectTreeItemAttributeDescriptor extends EObjectTreeItemDescriptor {
  private EAttribute eAttribute;              // identifies the attribute to which this descriptor applies
  private String labelText;                   // Text to display instead of the attribute name.
  private Format format;                      // The formatter used to format and parse the object.
  private PresentationType presentationType;  // Hint for rendering and editing
  private Function<EObjectTreeCell, String> initialDirectoryNameFunction = null; // Directory to start FileChooser or DirectoryChooser.
  
  
  /**
   * Constructor for attributes with a multi-line text
   * 
   * @param eAttribute the EAttribute to which this descriptor applies (mandatory)
   * @param labelText the text to be shown as a label for 
   * @param isMultiLineText
   * @param presentationType indicates how the value is to be presented/edited
   * @param nodeOperationDescriptors
   */
  public EObjectTreeItemAttributeDescriptor(EAttribute eAttribute, String labelText, List<NodeOperationDescriptor> nodeOperationDescriptors) {
    super(EObjectTreeItemDescriptorType.ATTRIBUTE, false, nodeOperationDescriptors);
    
    if (eAttribute == null) {
      throw new RuntimeException("eAttribute may not be null");
    }
    
    this.eAttribute = eAttribute;
    this.labelText = labelText;
    if (eAttribute.getEType().getInstanceClass().isEnum()) {
      presentationType = PresentationType.ENUMERATION;
    } else {
      presentationType = PresentationType.SINGLE_LINE_TEXT;
    }
    format = null;
  }
  
  /**
   * Constructor for attributes with a multi-line text
   * 
   * @param eAttribute the EAttribute to which this descriptor applies (mandatory)
   * @param labelText the text to be shown as a label for 
   * @param isMultiLineText
   * @param presentationType indicates how the value is to be presented/edited
   * @param nodeOperationDescriptors
   */
  public EObjectTreeItemAttributeDescriptor(EAttribute eAttribute, String labelText, PresentationType presentationType, List<NodeOperationDescriptor> nodeOperationDescriptors) {
    super(EObjectTreeItemDescriptorType.ATTRIBUTE, false, nodeOperationDescriptors);
    
    if (eAttribute == null) {
      throw new RuntimeException("eAttribute may not be null");
    }
    
    this.eAttribute = eAttribute;
    this.labelText = labelText;
    this.presentationType = presentationType;
    format = null;
  }
  
  public EObjectTreeItemAttributeDescriptor(EAttribute eAttribute, String labelText, Format format, List<NodeOperationDescriptor> nodeOperationDescriptors) {
    super(EObjectTreeItemDescriptorType.ATTRIBUTE, false, nodeOperationDescriptors);
    
    if (eAttribute == null) {
      throw new RuntimeException("eAttribute may not be null");
    }
    
    this.eAttribute = eAttribute;
    this.labelText = labelText;
    presentationType = PresentationType.FORMAT;
    this.format = format;
  }
  
  public EObjectTreeItemAttributeDescriptor(EAttribute eAttribute) {
    this(eAttribute, eAttribute.getName(), null);
  }

  public EAttribute getEAttribute() {
    return eAttribute;
  }

  public String getLabelText() {
    return labelText;
  }

  public Format getFormat() {
    return format;
  }
  
  public PresentationType getPresentationType() {
    return presentationType;
  }

  public void setPresentationType(PresentationType presentationType) {
    this.presentationType = presentationType;
  }
    
  public Function<EObjectTreeCell, String> getInitialDirectoryNameFunction() {
    return initialDirectoryNameFunction;
  }

  public void setInitialDirectoryNameFunction(Function<EObjectTreeCell, String> initialDirectoryNameFunction) {
    this.initialDirectoryNameFunction = initialDirectoryNameFunction;
  }

  @Override
  public String toString(Indent indent) {
    StringBuilder buf = new StringBuilder();
    
    buf.append(indent.toString()).append(getClass().getSimpleName());
    buf.append(": eAttribute=");
    buf.append(eAttribute != null ? eAttribute.getName() : "<null>");
    buf.append(", labelText=");
    buf.append(labelText);
    buf.append(", presentationType=");
    if (presentationType != null) {
      buf.append(presentationType);
    } else {
      buf.append("<null>");
    }
    
    return buf.toString();
  }
  
}
