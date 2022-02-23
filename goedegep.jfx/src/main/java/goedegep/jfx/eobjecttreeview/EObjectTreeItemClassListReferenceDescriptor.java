package goedegep.jfx.eobjecttreeview;

import java.util.List;
import java.util.function.Function;

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
 * <li>
 * expandOnCreation - if set, the node is expanded upon creation of the tree. Default is false.<br/>
 * (inherited from EObjectTreeItemDescriptor)
 * </li>
 * </ul>
 */
public class EObjectTreeItemClassListReferenceDescriptor extends EObjectTreeItemDescriptor {
  private EReference eReference;    // Identifies the reference to which this descriptor applies
  private String labelText;         // Text to display instead of the attribute name.
  
  
  /**
   * Constructor where all attributes of the descriptor are specified.
   * 
   * @param eReference identifies the attribute (reference) to which this descriptor applies.
   * @param labelText the text to be shown for this reference.
   * @param expandOnCreation expand the node upon creation of the tree.
   */
  public EObjectTreeItemClassListReferenceDescriptor(EReference eReference, String labelText, boolean expandOnCreation,
      List<NodeOperationDescriptor> nodeOperationDescriptors) {
    this(eReference, labelText, expandOnCreation, nodeOperationDescriptors, null);
  }

  /**
   * Constructor where all attributes of the descriptor are specified.
   * 
   * @param eReference identifies the attribute (reference) to which this descriptor applies.
   * @param labelText the text to be shown for this reference.
   * @param expandOnCreation expand the node upon creation of the tree.
   */
  public EObjectTreeItemClassListReferenceDescriptor(EReference eReference, String labelText, boolean expandOnCreation,
      List<NodeOperationDescriptor> nodeOperationDescriptors, Function<Object, Image> nodeIconFunction) {
    super(EObjectTreeItemDescriptorType.CLASS_LIST, expandOnCreation, nodeOperationDescriptors, nodeIconFunction);
    this.eReference = eReference;
    this.labelText = labelText;
  }
  
  /**
   * Constructor where only the eReference is specified.
   * <p>
   * The other attributes are set to their default values:
   * <ul>
   * <li>
   * labelText is set to the name of the reference.
   * </li>
   * <li>
   * expandOnCreation is set to false.
   * </li>
   * </ul>
   * 
   * @param eReference identifies the attribute (reference) to which this descriptor applies.
   * @param labelText the text to be shown for this reference.
   * @param expandOnCreation expand the node upon creation of the tree.
   */
  public EObjectTreeItemClassListReferenceDescriptor(EReference eReference) {
    this(eReference, eReference.getName(), false, null);
  }

  public EReference getEReference() {
    return eReference;
  }

  public String getLabelText() {
    return labelText;
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
