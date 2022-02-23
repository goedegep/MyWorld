/**
 */
package goedegep.invandprop.model;

import goedegep.types.model.FileReference;
import goedegep.util.datetime.FlexDate;
import goedegep.util.money.PgCurrency;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Invoice</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.invandprop.model.Invoice#getDate <em>Date</em>}</li>
 *   <li>{@link goedegep.invandprop.model.Invoice#getCompany <em>Company</em>}</li>
 *   <li>{@link goedegep.invandprop.model.Invoice#getInvoiceItems <em>Invoice Items</em>}</li>
 *   <li>{@link goedegep.invandprop.model.Invoice#getDocuments <em>Documents</em>}</li>
 * </ul>
 *
 * @see goedegep.invandprop.model.InvAndPropPackage#getInvoice()
 * @model
 * @generated
 */
public interface Invoice extends Expenditure {
  /**
   * Returns the value of the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Date</em>' attribute.
   * @see #isSetDate()
   * @see #unsetDate()
   * @see #setDate(FlexDate)
   * @see goedegep.invandprop.model.InvAndPropPackage#getInvoice_Date()
   * @model unsettable="true" dataType="goedegep.types.model.EFlexDate"
   * @generated
   */
  FlexDate getDate();

  /**
   * Sets the value of the '{@link goedegep.invandprop.model.Invoice#getDate <em>Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Date</em>' attribute.
   * @see #isSetDate()
   * @see #unsetDate()
   * @see #getDate()
   * @generated
   */
  void setDate(FlexDate value);

  /**
   * Unsets the value of the '{@link goedegep.invandprop.model.Invoice#getDate <em>Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDate()
   * @see #getDate()
   * @see #setDate(FlexDate)
   * @generated
   */
  void unsetDate();

  /**
   * Returns whether the value of the '{@link goedegep.invandprop.model.Invoice#getDate <em>Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Date</em>' attribute is set.
   * @see #unsetDate()
   * @see #getDate()
   * @see #setDate(FlexDate)
   * @generated
   */
  boolean isSetDate();

  /**
   * Returns the value of the '<em><b>Company</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Company</em>' attribute.
   * @see #isSetCompany()
   * @see #unsetCompany()
   * @see #setCompany(String)
   * @see goedegep.invandprop.model.InvAndPropPackage#getInvoice_Company()
   * @model unsettable="true"
   * @generated
   */
  String getCompany();

  /**
   * Sets the value of the '{@link goedegep.invandprop.model.Invoice#getCompany <em>Company</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Company</em>' attribute.
   * @see #isSetCompany()
   * @see #unsetCompany()
   * @see #getCompany()
   * @generated
   */
  void setCompany(String value);

  /**
   * Unsets the value of the '{@link goedegep.invandprop.model.Invoice#getCompany <em>Company</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetCompany()
   * @see #getCompany()
   * @see #setCompany(String)
   * @generated
   */
  void unsetCompany();

  /**
   * Returns whether the value of the '{@link goedegep.invandprop.model.Invoice#getCompany <em>Company</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Company</em>' attribute is set.
   * @see #unsetCompany()
   * @see #getCompany()
   * @see #setCompany(String)
   * @generated
   */
  boolean isSetCompany();

  /**
   * Returns the value of the '<em><b>Invoice Items</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.invandprop.model.InvoiceItem}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Invoice Items</em>' containment reference list.
   * @see goedegep.invandprop.model.InvAndPropPackage#getInvoice_InvoiceItems()
   * @model containment="true"
   * @generated
   */
  EList<InvoiceItem> getInvoiceItems();

  /**
   * Returns the value of the '<em><b>Documents</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.types.model.FileReference}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Documents</em>' containment reference list.
   * @see goedegep.invandprop.model.InvAndPropPackage#getInvoice_Documents()
   * @model containment="true"
   * @generated
   */
  EList<FileReference> getDocuments();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model kind="operation" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getTotalAmountInvoiceItems();

} // Invoice
