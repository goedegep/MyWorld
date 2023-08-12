package goedegep.jfx.eobjecttreeview;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import goedegep.util.text.Indent;
import javafx.scene.image.Image;

/**
 * This class is an {@link EObjectTreeItemDescriptor} for an item of type {@link EObjectTreeItemType.OBJECT}.
 */
public class EObjectTreeItemClassDescriptor extends EObjectTreeItemDescriptor {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeItemClassDescriptor.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  
  private EClass eClass;                       // The actual eClass
//  private Function<EObject, String> buildText; // Function to generate the class text from the class content.
  private List<EObjectTreeItemDescriptor> structuralFeatureDescriptors = new ArrayList<>();


  public EObjectTreeItemClassDescriptor(EClass eClass, Function<EObject, String> buildText, boolean expandOnCreation,
      List<NodeOperationDescriptor> nodeOperationDescriptors) {
    this(eClass, buildText, expandOnCreation, nodeOperationDescriptors, null);
  }

  public EObjectTreeItemClassDescriptor(EClass eClass, Function<EObject, String> buildText, boolean expandOnCreation,
      List<NodeOperationDescriptor> nodeOperationDescriptors, Function<Object, Image> nodeIconFunction) {
    super(EObjectTreeItemDescriptorType.CLASS, expandOnCreation, nodeOperationDescriptors, buildText, nodeIconFunction);
    
    if (eClass == null) {
      throw new IllegalArgumentException("Argument eClass cannot be null");
    }
    
    this.eClass = eClass;
//    this.buildText = buildText;
  }

  public EObjectTreeItemClassDescriptor(EClass eClass) {
    this(eClass, (eObject) -> eClass.getName(), false, null, null);
    LOGGER.info("eClass=" + eClass.getName());
  }
  
  public void addStructuralFeatureDescriptor(EObjectTreeItemDescriptor eObjectTreeItemDescriptor) {
    structuralFeatureDescriptors.add(eObjectTreeItemDescriptor);
  }

  public List<EObjectTreeItemDescriptor> getStructuralFeatureDescriptors() {
    return structuralFeatureDescriptors;
  }

  public EClass getEClass() {
    return eClass;
  }

//  public Function<EObject, String> getBuildText() {
//    return buildText;
//  }
  
  @Override
  public String toString(Indent indent) {
    StringBuilder buf = new StringBuilder();
    
    buf.append(indent.toString()).append(getClass().getSimpleName());
    buf.append(": className=");
    buf.append(eClass.getName());
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
