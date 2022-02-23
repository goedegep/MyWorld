/**
 */
package goedegep.invandprop.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Invoice Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.invandprop.model.InvoiceItem#getNumberOfItems <em>Number Of Items</em>}</li>
 * </ul>
 *
 * @see goedegep.invandprop.model.InvAndPropPackage#getInvoiceItem()
 * @model
 * @generated
 */
public interface InvoiceItem extends Expenditure {
  /**
   * Returns the value of the '<em><b>Number Of Items</b></em>' attribute.
   * The default value is <code>"1"</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Number Of Items</em>' attribute.
   * @see #setNumberOfItems(int)
   * @see goedegep.invandprop.model.InvAndPropPackage#getInvoiceItem_NumberOfItems()
   * @model default="1" required="true"
   * @generated
   */
  int getNumberOfItems();

  /**
   * Sets the value of the '{@link goedegep.invandprop.model.InvoiceItem#getNumberOfItems <em>Number Of Items</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Number Of Items</em>' attribute.
   * @see #getNumberOfItems()
   * @generated
   */
  void setNumberOfItems(int value);

} // InvoiceItem
