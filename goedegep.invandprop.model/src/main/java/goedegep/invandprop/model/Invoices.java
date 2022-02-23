/**
 */
package goedegep.invandprop.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Invoices</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.invandprop.model.Invoices#getInvoices <em>Invoices</em>}</li>
 * </ul>
 *
 * @see goedegep.invandprop.model.InvAndPropPackage#getInvoices()
 * @model
 * @generated
 */
public interface Invoices extends EObject {
  /**
   * Returns the value of the '<em><b>Invoices</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.invandprop.model.Invoice}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Invoices</em>' containment reference list.
   * @see goedegep.invandprop.model.InvAndPropPackage#getInvoices_Invoices()
   * @model containment="true" ordered="false"
   * @generated
   */
  EList<Invoice> getInvoices();

} // Invoices
