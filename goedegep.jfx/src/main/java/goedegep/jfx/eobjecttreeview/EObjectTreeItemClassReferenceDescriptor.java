package goedegep.jfx.eobjecttreeview;

import java.util.List;
import java.util.function.Function;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import goedegep.util.text.Indent;

/**
 * This class is an {@link EObjectTreeItemDescriptor} for an item of type {@link EObjectTreeItemType.CLASS_REFERENCE}.
 */
public class EObjectTreeItemClassReferenceDescriptor extends EObjectTreeItemDescriptor {
  private EClass eClass;
  private EReference eReference;    // Identifies the reference to which this descriptor applies
    
  public EObjectTreeItemClassReferenceDescriptor(EReference eReference, EClass eClass, Function<EObject, String> buildText, boolean expandOnCreation,
      List<NodeOperationDescriptor> nodeOperationDescriptors) {
    super(EObjectTreeItemDescriptorType.CLASS_REFERENCE, expandOnCreation, nodeOperationDescriptors, buildText, null);
//    super(eClass, buildText, expandOnCreation, nodeOperationDescriptors);
//    setDescriptorType(EObjectTreeItemDescriptorType.CLASS_REFERENCE);
    this.eReference = eReference;
    this.eClass = eClass;
  }

  public EReference getEReference() {
    return eReference;
  }
  
  public EClass getEClass() {
    return eClass;
  }
  
  @Override
  public String toString() {
    return toString(new Indent(2));
  }
  
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
