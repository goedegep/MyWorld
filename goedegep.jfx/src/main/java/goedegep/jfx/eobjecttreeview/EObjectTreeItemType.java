package goedegep.jfx.eobjecttreeview;

/**
 * This enumeration defines the types of the items in an EObjectTreeView.
 */
public enum EObjectTreeItemType {
  /**
   * The item is an EObject referred to by a single reference.<br/>
   * Show the class name, or the name specified via the descriptor. The children are the attributes and references of the EObject.<br/>
   * Currently EObjects without any attributes or references are not supported, so this type is not a leaf.<br/>
   * As it is not possible to add, remove or change the attributes or references, this type is not editable.
   */
  OBJECT(false),
  /**
   * The item is an EObject in a list of EObjects (referred to be a many reference).<br/>
   * Show the class name, or the name specified via the descriptor. The children are the attributes and references of the EObject.<br/>
   * As it is not possible to add, remove or change the attributes or references, this type is not editable.
   */
  OBJECT_LIST(false),
  /**
   * The item is a simple attribute value (as opposite to a list of attribute values).<br/>
   * Show the attribute name, or the name specified via the descriptor. Plus the value.<br/>
   * This item is by definition a leaf, of which the value can be changed.
   */
  ATTRIBUTE_SIMPLE(true),
  /**
   * The item is a list of attribute values.<br/>
   * Show the attribute name, or the name specified via the descriptor. Children are the values.<br/>
   *  The list itself cannot be changed.
   */
  ATTRIBUTE_LIST(false),
  /**
   * The item is one value of a list of attribute values.<br/>
   * Show the value.
   * This item is by definition a leaf, of which the value can be changed.
   */
  ATTRIBUTE_LIST_VALUE(true);   // show value.
  
  private boolean isEditable;  // indicates whether the value of this item can be changed.

  /**
   * Constructor.
   * 
   * @param leaf indicates whether this node has children or not
   * @param isEditable indicates whether the value of this item can be changed.
   */
  private EObjectTreeItemType(boolean isEditable) {
    this.isEditable = isEditable;
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
