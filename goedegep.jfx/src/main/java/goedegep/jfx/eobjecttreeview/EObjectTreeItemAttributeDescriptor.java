package goedegep.jfx.eobjecttreeview;

import java.text.Format;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.emf.ecore.EAttribute;

import goedegep.util.text.Indent;

/**
 * This class is an {@link EObjectTreeItemDescriptor} for an item of type {@link EObjectTreeItemType.ATTRIBUTE_SIMPLE}.
 */
public class EObjectTreeItemAttributeDescriptor extends EObjectTreeItemDescriptor {
  static final String NEWLINE = System.getProperty("line.separator");
  
  /**
   * The attribute to which this descriptor applies (mandatory).
   */
  private EAttribute eAttribute = null;
  
  /**
   * Text to display instead of the attribute name (optional).
   */
  private String labelText = null;
  
  /**
   * A {@link Format} to parse and format the object value (optional).
   */
  private Format format = null;
  
  /**
   * Hint for rendering and editing (optional).
   * TODO Check in which cases this can be omitted.
   */
  private PresentationType presentationType = null;
  
  /**
   * If true (default value) use an 'open' dialog, else a 'save' dialog.
   * TODO this is only for files, so create EObjectTreeItemDescriptorForFile as subclass of EObjectTreeItemDescriptorForFile.
   */
  private boolean isOpenDialog = true;
  
  /**
   * A function to provide the initial directory for a FileChooser or DirectoryChooser.
   * TODO Move to EObjectTreeItemDescriptorForFile
   */
  private Function<EObjectTreeCell, String> initialDirectoryNameFunction = null;
  
  /**
   * A function to provide the initial file name for a FileChooser.
   * TODO this is only for files, so create EObjectTreeItemDescriptorForFile as subclass of EObjectTreeItemDescriptorForFile.
   */
  private Function<EObjectTreeCell, String> initialFileNameFunction = null;
  
  
  
  /**
   * Constructor
   * 
   * @param eAttribute the {@code EAttribute} to which this descriptor applies (mandatory)
   */
  public EObjectTreeItemAttributeDescriptor(EAttribute eAttribute) {
    super(EObjectTreeItemDescriptorType.ATTRIBUTE);
    
    Objects.requireNonNull(eAttribute, "eAttribute may not be null");
    
    this.eAttribute = eAttribute;
    labelText = eAttribute.getName();
  }
      
  /**
   * Get the {@code EAttribute} to which this descriptor applies.
   * 
   * @return the {@code EAttribute} to which this descriptor applies
   */
  public EAttribute getEAttribute() {
    return eAttribute;
  }

  /**
   * Get the text to display instead of the attribute name.
   * 
   * @return the text to display instead of the attribute name
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
  public EObjectTreeItemAttributeDescriptor setLabelText(String labelText) {
    this.labelText = labelText;
    
    return this;
  }

  /**
   * Get the {@link Format} to parse and format the object value.
   * 
   * @return the {@link Format} to parse and format the object value
   */
  public Format getFormat() {
    return format;
  }
  
  /**
   * Set the {@link Format} to parse and format the object value.
   * 
   * @param format the {@link Format} to parse and format the object value.
   * @return this
   */
  public EObjectTreeItemAttributeDescriptor setFormat(Format format) {
    this.format = format;
    
    return this;
  }
  
  /**
   * Get the hint ({@code PresentationType}) for rendering and editing.
   * 
   * @return the hint ({@code PresentationType}) for rendering and editing.
   */
  public PresentationType getPresentationType() {
    return presentationType;
  }

  /**
   * Set the hint ({@code PresentationType}) for rendering and editing.
   * 
   * @param presentationType the hint ({@code PresentationType}) for rendering and editing.
   * @return this
   */
  public EObjectTreeItemAttributeDescriptor setPresentationType(PresentationType presentationType) {
    this.presentationType = presentationType;
    
    return this;
  }
  
  /**
   * Check whether this is an open or save dialog.
   * 
   * @return true for an open dialog and false for a save dialog.
   */
  public boolean isOpenDialog() {
    return isOpenDialog;
  }

  /**
   * Specify whether this is an open or save dialog.
   * 
   * @param isOpenDialog true for an open dialog and false for a save dialog.
   * @return this
   */
  public EObjectTreeItemAttributeDescriptor setOpenDialog(boolean isOpenDialog) {
    this.isOpenDialog = isOpenDialog;
    
    return this;
  }
    
  /**
   * Get the function to provide the initial directory for a FileChooser or DirectoryChooser.
   * 
   * @return the function to provide the initial directory for a FileChooser or DirectoryChooser.
   */
  public Function<EObjectTreeCell, String> getInitialDirectoryNameFunction() {
    return initialDirectoryNameFunction;
  }

  /**
   * Set the function to provide the initial directory for a FileChooser or DirectoryChooser.
   * 
   * @param initialDirectoryNameFunction the function to provide the initial directory for a FileChooser or DirectoryChooser.
   * @return this
   */
  public EObjectTreeItemAttributeDescriptor setInitialDirectoryNameFunction(Function<EObjectTreeCell, String> initialDirectoryNameFunction) {
    this.initialDirectoryNameFunction = initialDirectoryNameFunction;
    
    return this;
  }

  /**
   * Get the function to provide the initial file name for a FileChooser.
   * 
   * @return the function to provide the initial file name for a FileChooser.
   */
  public Function<EObjectTreeCell, String> getInitialFileNameFunction() {
    return initialFileNameFunction;
  }

  /**
   * Set  the function to provide the initial file name for a FileChooser.
   * 
   * @param initialFileNameFunction the function to provide the initial file name for a FileChooser.
   * @return this
   */
  public EObjectTreeItemAttributeDescriptor setInitialFileNameFunction(Function<EObjectTreeCell, String> initialFileNameFunction) {
    this.initialFileNameFunction = initialFileNameFunction;
    
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
  public EObjectTreeItemAttributeDescriptor addNodeOperationDescriptor(NodeOperationDescriptor nodeOperationDescriptor) {
    super.addNodeOperationDescriptor(nodeOperationDescriptor);
    
    return this;
  }

  @Override
  public String toString() {
    return toString(new Indent(2));
  }

  @Override
  public String toString(Indent indent) {
    StringBuilder buf = new StringBuilder();
    
    buf.append(indent.toString()).append("class: ").append(getClass().getSimpleName()).append(NEWLINE);
    buf.append(super.toString(indent));
    buf.append(indent.toString()).append("eAttribute: ").append(eAttribute != null ? eAttribute.getName() : "<null>");
    buf.append(", labelText:").append(labelText);
    buf.append(", presentationType:").append(presentationType != null ? presentationType : "<null>");
    buf.append(", format:").append(format != null ? format.getClass().getSimpleName() : "<null>").append(NEWLINE);
    buf.append(indent.toString()).append("initialDirectoryNameFunction: ").append(initialDirectoryNameFunction != null ? "Set" : "Not set").append(NEWLINE);

    return buf.toString();
  }
  
}
