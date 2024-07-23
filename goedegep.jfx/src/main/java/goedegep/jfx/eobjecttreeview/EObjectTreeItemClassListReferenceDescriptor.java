package goedegep.jfx.eobjecttreeview;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

import goedegep.util.text.Indent;
import javafx.scene.image.Image;


/**
 * This class is an EReference specific descriptor for an item in an EObjectTreeView.
 * <p>
 * This class has the following attributes:
 * <ul>
 * <li>
 * eReference - identifies the attribute (reference) to which this descriptor applies.
 * </li>
 * <li>
 * labelText - the text to be shown for this reference. Default is the name of the reference.
 * </li>
 * </ul>
 */
public class EObjectTreeItemClassListReferenceDescriptor extends EObjectTreeItemDescriptor {
  
  /**
   * Identifies the reference to which this descriptor applies (mandatory).
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
   * Text to display. Default is the name of the {@code eReference}.
   */
  private String labelText = null;
  

  /**
   * Constructor for when the {@code EReference} is a containment reference.
   * 
   * @param eReference the {@code EReference} to which this descriptor applies. This value may not be null.
   */
  public EObjectTreeItemClassListReferenceDescriptor(EReference eReference) {
    super(EObjectTreeItemDescriptorType.CLASS_LIST);
    
    Objects.requireNonNull(eReference, "The value of eReference may not be null.");
    
//    if (!eReference.isContainment()) {
//      throw new IllegalArgumentException("In this constructor only a containment eReference is allowed");
//    }
    
    this.eReference = eReference;
    eClass = (EClass) eReference.eContainer();
    
    setNodeTextFunction(object -> eReference.getName());
  }
  
  /**
   * Constructor for when the {@code EReference} is NOT a containment reference.
   * 
   * @param eReference the {@code EReference} to which this descriptor applies. This value may not be null.
   * @param eClass the {@code EClass} which contains the {@code eReference}. This value may not be null.
   */
  public EObjectTreeItemClassListReferenceDescriptor(EReference eReference, EClass eClass) {
    super(EObjectTreeItemDescriptorType.CLASS_LIST);
    
    Objects.requireNonNull(eReference, "The value of eReference may not be null.");
    Objects.requireNonNull(eClass, "The value of eClass may not be null.");
    
    if (eReference.isContainment()) {
      throw new IllegalArgumentException("In this constructor only a non-containment eReference is allowed");
    }
    
    this.eReference = eReference;
    this.eClass = eClass;
  }
  
  /**
   * Get the reference to which this descriptor applies.
   * 
   * @return the reference to which this descriptor applies.
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
   * Get the text to display.
   * 
   * @return the text to display.
   */
  public String getLabelText() {
    return labelText;
  }
  
  /**
   * Set the text to display.
   * 
   * @param labelText the text to display.
   * @return this
   */
  public EObjectTreeItemClassListReferenceDescriptor setLabelText(String labelText) {
    this.labelText = labelText;
    
    return this;
  }

  /**
   * Set whether the text shall be in a strong font (bold) or not.
   * 
   * @param strongText if true the text shall be in a strong font (bold).
   * @return this
   */
  public EObjectTreeItemClassListReferenceDescriptor setStrongText(boolean strongText) {
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
  public EObjectTreeItemClassListReferenceDescriptor setNodeIconFunction(Function<Object, Image> nodeIconFunction) {
    super.setNodeIconFunction(nodeIconFunction);
    
    return this;
  }
  
  /**
   * Set whether the node shall be expanded upon creation or not
   * 
   * @param expandOnCreation if true, the node will be expanded upon creation.
   * @return this
   */
  public EObjectTreeItemClassListReferenceDescriptor setExpandOnCreation(boolean expandOnCreation) {
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
  public EObjectTreeItemClassListReferenceDescriptor addNodeOperationDescriptor(NodeOperationDescriptor nodeOperationDescriptor) {
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
    buf.append(": eReference=");
    buf.append(eReference.getName());
    buf.append(", labelText=");
    buf.append(labelText);
    buf.append(", expandOnCreation=");
    buf.append(isExpandOnCreation());
    
    return buf.toString();
  }
}
