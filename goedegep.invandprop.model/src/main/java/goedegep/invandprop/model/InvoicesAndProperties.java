/**
 */
package goedegep.invandprop.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Invoices And Properties</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.invandprop.model.InvoicesAndProperties#getInvoicseandpropertys <em>Invoicseandpropertys</em>}</li>
 * </ul>
 *
 * @see goedegep.invandprop.model.InvAndPropPackage#getInvoicesAndProperties()
 * @model
 * @generated
 */
public interface InvoicesAndProperties extends EObject {
  /**
   * Returns the value of the '<em><b>Invoicseandpropertys</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.invandprop.model.InvoiceAndProperty}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Invoicseandpropertys</em>' containment reference list.
   * @see goedegep.invandprop.model.InvAndPropPackage#getInvoicesAndProperties_Invoicseandpropertys()
   * @model containment="true"
   * @generated
   */
  EList<InvoiceAndProperty> getInvoicseandpropertys();

} // InvoicesAndProperties
