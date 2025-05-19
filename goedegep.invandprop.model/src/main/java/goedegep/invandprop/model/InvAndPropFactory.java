/**
 */
package goedegep.invandprop.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.invandprop.model.InvAndPropPackage
 * @generated
 */
public interface InvAndPropFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  InvAndPropFactory eINSTANCE = goedegep.invandprop.model.impl.InvAndPropFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Invoices And Properties</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Invoices And Properties</em>'.
   * @generated
   */
  InvoicesAndProperties createInvoicesAndProperties();

  /**
   * Returns a new object of class '<em>Invoice And Property Item</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Invoice And Property Item</em>'.
   * @generated
   */
  InvoiceAndPropertyItem createInvoiceAndPropertyItem();

  /**
   * Returns a new object of class '<em>Invoice And Property</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Invoice And Property</em>'.
   * @generated
   */
  InvoiceAndProperty createInvoiceAndProperty();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  InvAndPropPackage getInvAndPropPackage();

} //InvAndPropFactory
