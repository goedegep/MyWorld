package goedegep.jfx.eobjecttreeview;

/**
 * This enumeration defines the types of the items in an EObjectTreeView.
 */
public enum EObjectTreeItemType {
  /**
   * The item is an EObject.<br/>
   * Show the class name, or the name specified via the descriptor. The children are the attributes and references of the EObject.<br/>
   * Currently EObjects without any attributes or references are not supported, so this type is not a leaf.<br/>
   * As it is not possible to add, remove or change the attributes or references, this type is not editable.
   */
  OBJECT(false, false),
  /**
   *  The item is a list of EObjects (an 'isMany' Reference)<br/>
   *  Show the reference name, or the name specified via descriptor. The children are the objects in the list.<br/>
   *  As this item can have children, it is not a leaf. TODO Why is it also a leaf if the list is empty? <br/>
   *  The list (the reference) cannot be changed.
   *  
   */
  OBJECT_LIST(false, false),
  /**
   * The item is a simple attribute value (as opposite to a list of attribute values).<br/>
   * Show the attribute name, or the name specified via the descriptor. Plus the value.<br/>
   * This item is by definition a leaf, of which the value can be changed.
   */
  ATTRIBUTE_SIMPLE(true, true),
  /**
   * The item is a list of attribute values.<br/>
   * Show the attribute name, or the name specified via the descriptor. Children are the values.<br/>
   * As this item can have children, it is not a leaf. TODO Why is it also a leaf if the list is empty?<br/>
   *  The list itself cannot be changed.
   */
  ATTRIBUTE_LIST(false, false),
  /**
   * The item is one value of a list of attribute values.<br/>
   * Show the value.
   * This item is by definition a leaf, of which the value can be changed.
   */
  ATTRIBUTE_LIST_VALUE(true, true);   // show value.
  
  private boolean leaf;        // indicates whether this item has (or can have) children. TODO which one is it.
  private boolean isEditable;  // indicates whether the value of this item can be changed.

  /**
   * Constructor.
   * 
   * @param leaf indicates whether this node has children or not
   * @param isEditable indicates whether the value of this item can be changed.
   */
  private EObjectTreeItemType(boolean leaf, boolean isEditable) {
    this.leaf = leaf;
    this.isEditable = isEditable;
  }

  /**
   * Check whether this item type is a leaf, which is the case for the types: {@link #ATTRIBUTE_SIMPLE} and {@link #ATTRIBUTE_LIST_VALUE}.
   * 
   * @return true if this item type is a leaf, false otherwise.
   */
  public boolean isLeaf() {
    return leaf;
  }

  /**
   * Check whether this item type can be edited, which is the case for the types: {@link #ATTRIBUTE_SIMPLE} and {@link #ATTRIBUTE_LIST_VALUE}.
   * 
   * @return true is this type can be edited, false otherwise.
   */
  public boolean isEditable() {
    return isEditable;
  }

}
