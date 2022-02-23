package goedegep.jfx.eobjecttreeview;

import java.util.List;
import java.util.function.Function;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import goedegep.util.text.Indent;

public class EObjectTreeItemClassReferenceDescriptor extends EObjectTreeItemClassDescriptor {
  private EReference eReference;    // Identifies the reference to which this descriptor applies
    
  public EObjectTreeItemClassReferenceDescriptor(EReference eReference, EClass eClass, Function<EObject, String> buildText, boolean expandOnCreation,
      List<NodeOperationDescriptor> nodeOperationDescriptors) {
    super(eClass, buildText, expandOnCreation, nodeOperationDescriptors);
    setDescriptorType(EObjectTreeItemDescriptorType.CLASS_REFERENCE);
    this.eReference = eReference;
  }

  public EReference getEReference() {
    return eReference;
  }
  
//  /**
//   * Check whether the structural features of this class are defined as children of this node.
//   * <p>
//   * If the structural features of this class are not defined as children of this node, there shall
//   * be a separate descriptor for each class which is referred to.
//   * 
//   * @return true if the structural features of this class are defined as children of this node, false otherwise.
//   */
//  public boolean areStructuralFeaturesDefinedAsChildren() {
//    return structuralFeaturesDefinedAsChildren;
//  }
  
  @Override
  public String toString(Indent indent) {
    StringBuilder buf = new StringBuilder();
    
    buf.append(indent.toString()).append(getClass().getSimpleName());
    buf.append(": className=");
    buf.append(getEClass().getName());
    if (getBuildText() != null) {
      buf.append(", buildText specified");
    } else {
      buf.append(", buildText not specified");
    }
    buf.append(", eReference=");
    buf.append(eReference.getName());
    
    return buf.toString();
  }
}
