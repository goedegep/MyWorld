package goedegep.jfx.eobjecttreeview;

import java.util.Objects;
import java.util.function.Function;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import goedegep.util.text.Indent;

/**
 * This class is an {@link EObjectTreeItemDescriptor} for an item of type {@link EObjectTreeItemType.CLASS_REFERENCE}.
 */
public class EObjectTreeItemClassReferenceDescriptor extends EObjectTreeItemDescriptor {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeItemClassReferenceDescriptor.class.getName());
  
  /**
   * The {@code EReference} to which this descriptor applies (mandatory).
   */
  private EReference eReference = null;

  /**
   * The {@code EClass} that contains the {@code eReference}.
   * <p>
   * This value is always set via a constructor.<br/>
   * If the {@code eReference} is a containment reference, you have to use the constructor with only the {@code eReference} argument.<br/>
   * Else you have to use the constructor where you also have to provide the containing {@code EClass}.
   */
  private EClass eClass = null;
  
  
  /**
   * Constructor for when the {@code EReference} is a containment reference.
   * 
   * @param eReference the {@code EReference} to which this descriptor applies. This value may not be null.
   */
  public EObjectTreeItemClassReferenceDescriptor(EReference eReference) {
    super(EObjectTreeItemDescriptorType.CLASS_REFERENCE);
    
    Objects.requireNonNull(eReference, "The value of eReference may not be null.");
    
    if (!eReference.isContainment()) {
      throw new IllegalArgumentException("In this constructor only a containment eReference is allowed");
    }
    
    this.eReference = eReference;
    eClass = (EClass) eReference.eContainer();
    
    setNodeTextFunction(object -> eReference.getName());
  }
  
  /**
   * Constructor for when the {@code EReference} is NOT a containment reference.
   * 
   * @param eReference the {@code EReference} to which this descriptor applies. This value may not be null.
   * @param eClass the {@code EClass} which contains the {@code eReference}.
   */
  public EObjectTreeItemClassReferenceDescriptor(EReference eReference, EClass eClass) {
    super(EObjectTreeItemDescriptorType.CLASS_REFERENCE);
    
    Objects.requireNonNull(eReference, "The value of eReference may not be null.");
    Objects.requireNonNull(eClass, "The value of eClass may not be null.");
    
    if (eReference.isContainment()) {
      throw new IllegalArgumentException("In this constructor only a non-containment eReference is allowed");
    }
    
    this.eReference = eReference;
    this.eClass = eClass;
  }
    
  /**
   * Get the {@code EReference} to which this descriptor applies.
   * 
   * @return the {@code EReference} to which this descriptor applies.
   */
  public EReference getEReference() {
    return eReference;
  }
  
  /**
   * Get the {@code EClass} that contains the {@code eReference}.
   * @return the {@code EClass} that contains the {@code eReference}.
   */
  public EClass getEClass() {
    return eClass;
  }
  
  /**
   * Set the function to provide the node text.
   * TODO instead of overwriting this method implement a solution 
   * 
   * @param nodeTextFunction the function to provide the node text.
   * @return this
   */
  public EObjectTreeItemClassReferenceDescriptor setNodeTextFunction(Function<EObject, String> nodeTextFunction) {
    super.setNodeTextFunction(nodeTextFunction);
    
    return this;
  }

  /**
   * Set whether the text shall be in a strong font (bold) or not.
   * TODO instead of overwriting this method implement a solution 
   * 
   * @param strongText if true the text shall be in a strong font (bold).
   * @return this
   */
  public EObjectTreeItemClassReferenceDescriptor setStrongText(boolean strongText) {
    super.setStrongText(strongText);
    
    return this;
  }
  
  /**
   * Set whether the node shall be expanded upon creation or not
   * 
   * @param expandOnCreation if true, the node will be expanded upon creation.
   * @return this
   */
  public EObjectTreeItemClassReferenceDescriptor setExpandOnCreation(boolean expandOnCreation) {
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
  public EObjectTreeItemClassReferenceDescriptor addNodeOperationDescriptor(NodeOperationDescriptor nodeOperationDescriptor) {
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
    
    buf.append(indent.toString()).append(getClass().getSimpleName());
    buf.append(": className=");
    buf.append(getEClass().getName());
    if (getNodeTextFunction() != null) {
      buf.append(", buildText specified");
    } else {
      buf.append(", buildText not specified");
    }
    buf.append(", eReference=");
    buf.append(eReference.getName());
    
    return buf.toString();
  }
}
