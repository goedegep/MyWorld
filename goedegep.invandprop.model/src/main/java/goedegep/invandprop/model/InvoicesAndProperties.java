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
 *   <li>{@link goedegep.invandprop.model.InvoicesAndProperties#getInvoices <em>Invoices</em>}</li>
 *   <li>{@link goedegep.invandprop.model.InvoicesAndProperties#getProperties <em>Properties</em>}</li>
 *   <li>{@link goedegep.invandprop.model.InvoicesAndProperties#getInvoicseandpropertys <em>Invoicseandpropertys</em>}</li>
 * </ul>
 *
 * @see goedegep.invandprop.model.InvAndPropPackage#getInvoicesAndProperties()
 * @model
 * @generated
 */
public interface InvoicesAndProperties extends EObject {
  /**
   * Returns the value of the '<em><b>Invoices</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Invoices</em>' containment reference.
   * @see #setInvoices(Invoices)
   * @see goedegep.invandprop.model.InvAndPropPackage#getInvoicesAndProperties_Invoices()
   * @model containment="true" required="true"
   * @generated
   */
  Invoices getInvoices();

  /**
   * Sets the value of the '{@link goedegep.invandprop.model.InvoicesAndProperties#getInvoices <em>Invoices</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Invoices</em>' containment reference.
   * @see #getInvoices()
   * @generated
   */
  void setInvoices(Invoices value);

  /**
   * Returns the value of the '<em><b>Properties</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Properties</em>' containment reference.
   * @see #setProperties(Properties)
   * @see goedegep.invandprop.model.InvAndPropPackage#getInvoicesAndProperties_Properties()
   * @model containment="true" required="true"
   * @generated
   */
  Properties getProperties();

  /**
   * Sets the value of the '{@link goedegep.invandprop.model.InvoicesAndProperties#getProperties <em>Properties</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Properties</em>' containment reference.
   * @see #getProperties()
   * @generated
   */
  void setProperties(Properties value);

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
