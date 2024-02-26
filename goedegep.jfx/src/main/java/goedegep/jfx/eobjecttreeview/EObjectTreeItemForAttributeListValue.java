package goedegep.jfx.eobjecttreeview;

import java.util.logging.Logger;

import org.eclipse.emf.ecore.EStructuralFeature;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 * This class is a TreeItem for single item of an attribute list.
 * <p>
 * The item typically only shows the value.<br/>
 * This class has no EAttribute. Instead the attribute is obtained from the parent item.
 *
 */
public class EObjectTreeItemForAttributeListValue extends EObjectTreeItem {
  private static final Logger LOGGER = Logger.getLogger(EObjectTreeItemForAttributeListValue.class.getName());
  
  private EObjectTreeItemAttributeListValueDescriptor eObjectTreeItemAttributeListValueDescriptor;

  /**
   * Constructor.
   * 
   * @param object
   * @param eObjectTreeItemAttributeListValueDescriptor
   * @param eObjectTreeView
   */
  public EObjectTreeItemForAttributeListValue(Object object, EObjectTreeItemAttributeListValueDescriptor eObjectTreeItemAttributeListValueDescriptor, EObjectTreeView eObjectTreeView) {
    
    super(object, EObjectTreeItemType.ATTRIBUTE_LIST_VALUE, eObjectTreeView);
    
    this.eObjectTreeItemAttributeListValueDescriptor = eObjectTreeItemAttributeListValueDescriptor;
  }

  /**
   * {@inheritDoc}
   * 
   * An attribute list value has no structural feature.
   */
  @Override
  public EStructuralFeature getEStructuralFeature() {
    return null;
  }
  
  /**
   * {@inheritDoc}
   * A list value is always a leaf.
   */
  @Override
  boolean isItemALeafItem() {
    return true;
  }
  
  /**
   * {@inheritDoc}
   * This item is a leaf, it doesn't have any children.
   */
  @Override
  ObservableList<TreeItem<Object>> buildChildren() {
    return null;
  }  

  public EObjectTreeItemAttributeListValueDescriptor getEObjectTreeItemAttributeListValueDescriptor() {
    return eObjectTreeItemAttributeListValueDescriptor;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  void switchToEditMode() {
    // No action as this item is a leaf.
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  void switchToViewMode() {
    // No action as this item is a leaf.
  }
  
  /**
   * Handle the fact that the value has changed.
   * 
   * @param eStructuralFeature the changed feature
   * @param newValue the new value
   */
  public void handleValueChanged(EStructuralFeature eStructuralFeature, Object newValue) {
    LOGGER.info("=> " + toString());
    setValue(newValue);    
  }
  
  /**
   *{@inheritDoc}
   */
  public String getText() {
    return getValue() != null ? getValue().toString() : "";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(super.toString());
    buf.append("descriptor: ").append(eObjectTreeItemAttributeListValueDescriptor != null ? eObjectTreeItemAttributeListValueDescriptor : "<null>").append(NEWLINE);
    
    return buf.toString();    
  }
}
