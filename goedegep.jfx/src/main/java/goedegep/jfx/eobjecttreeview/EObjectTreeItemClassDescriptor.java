package goedegep.jfx.eobjecttreeview;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;

import goedegep.util.text.Indent;
import javafx.scene.image.Image;

/**
 * This class is an {@link EObjectTreeItemDescriptor} for an item of type {@link EObjectTreeItemType.OBJECT}.
 */
public class EObjectTreeItemClassDescriptor extends EObjectTreeItemDescriptor {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeItemClassDescriptor.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  
  /**
   * Descriptors for the structural features of the EClass.
   */
  private List<EObjectTreeItemDescriptor> structuralFeatureDescriptors = new ArrayList<>();
  
  /**
   * Constructor.
   */
  public EObjectTreeItemClassDescriptor() {
    super(EObjectTreeItemDescriptorType.CLASS);
  }

  /**
   * Add a structural feature descriptor.
   * 
   * @param eObjectTreeItemDescriptor the {@code EObjectTreeItemDescriptor} to be added.
   */
  public void addStructuralFeatureDescriptor(EObjectTreeItemDescriptor eObjectTreeItemDescriptor) {
    structuralFeatureDescriptors.add(eObjectTreeItemDescriptor);
  }

  /**
   * Get the structural feature descriptors.
   * 
   * @return the structural feature descriptors.
   */
  public List<EObjectTreeItemDescriptor> getStructuralFeatureDescriptors() {
    return structuralFeatureDescriptors;
  }

  /**
   * Set whether the text shall be in a strong font (bold) or not.
   * TODO instead of overwriting this method implement a solution 
   * 
   * @param strongText if true the text shall be in a strong font (bold).
   * @return this
   */
  public EObjectTreeItemClassDescriptor setStrongText(boolean strongText) {
    super.setStrongText(strongText);
    
    return this;
  }
  
  /**
   * Set the function to provide the node icon.
   * TODO instead of overwriting this method implement a solution 
   * 
   * @param nodeIconFunction the function to provide the node icon.
   * @return this
   */
  public EObjectTreeItemClassDescriptor setNodeIconFunction(Function<Object, Image> nodeIconFunction) {
    super.setNodeIconFunction(nodeIconFunction);
    
    return this;
  }
  
  /**
   * Set the function to provide the node text.
   * TODO instead of overwriting this method implement a solution 
   * 
   * @param nodeTextFunction the function to provide the node text.
   * @return this
   */
  public EObjectTreeItemClassDescriptor setNodeTextFunction(Function<EObject, String> nodeTextFunction) {
    super.setNodeTextFunction(nodeTextFunction);
    
    return this;
  }
  
  /**
   * Set whether the node shall be expanded upon creation or not
   * 
   * @param expandOnCreation if true, the node will be expanded upon creation.
   * @return this
   */
  public EObjectTreeItemClassDescriptor setExpandOnCreation(boolean expandOnCreation) {
    super.setExpandOnCreation(expandOnCreation);
    
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
  public EObjectTreeItemClassDescriptor addNodeOperationDescriptor(NodeOperationDescriptor nodeOperationDescriptor) {
    super.addNodeOperationDescriptor(nodeOperationDescriptor);
    
    return this;
  }

  @Override
  public String toString(Indent indent) {
    StringBuilder buf = new StringBuilder();
    
    buf.append(indent.toString()).append(getClass().getSimpleName());
    buf.append(": className=");
    if (getNodeTextFunction() != null) {
      buf.append(", buildText specified");
    } else {
      buf.append(", buildText not specified");
    }
    
    buf.append(NEWLINE).append(indent.toString()).append("Attribute Descriptors:").append(NEWLINE);
    
    indent.increment();
    for (EObjectTreeItemDescriptor eObjectTreeItemDescriptor: structuralFeatureDescriptors) {
      buf.append(eObjectTreeItemDescriptor.toString(indent)).append(NEWLINE);
    }
    indent.decrement();
    
    LOGGER.severe(buf.toString());
    return buf.toString();
  }

}
