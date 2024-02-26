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
   * 
   * @param buildText function for creating the text
   * @param expandOnCreation if true the tree item will be expanded on creation
   * @param nodeOperationDescriptors specification for the node operations
   */
  public EObjectTreeItemClassDescriptor(Function<EObject, String> buildText, boolean expandOnCreation,
      List<NodeOperationDescriptor> nodeOperationDescriptors) {
    this(buildText, expandOnCreation, nodeOperationDescriptors, null);
  }

  /**
   * Constructor.
   * 
   * @param buildText function for creating the text
   * @param expandOnCreation if true the tree item will be expanded on creation
   * @param nodeOperationDescriptors specification for the node operations
   * @param nodeIconFunction function to provide an icon 
   */
  public EObjectTreeItemClassDescriptor(Function<EObject, String> buildText, boolean expandOnCreation,
      List<NodeOperationDescriptor> nodeOperationDescriptors, Function<Object, Image> nodeIconFunction) {
    super(EObjectTreeItemDescriptorType.CLASS, expandOnCreation, nodeOperationDescriptors, buildText, nodeIconFunction);
    
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

  @Override
  public String toString(Indent indent) {
    StringBuilder buf = new StringBuilder();
    
    buf.append(indent.toString()).append(getClass().getSimpleName());
    buf.append(": className=");
    if (getBuildText() != null) {
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
