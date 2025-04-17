/**
 */
package goedegep.invandprop.model;

import goedegep.types.model.FileReference;
import goedegep.util.datetime.FlexDate;

import goedegep.util.money.PgCurrency;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Invoice And Property Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getType <em>Type</em>}</li>
 *   <li>{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getSerialNumber <em>Serial Number</em>}</li>
 *   <li>{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getBrand <em>Brand</em>}</li>
 *   <li>{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getAmount <em>Amount</em>}</li>
 *   <li>{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getFromDate <em>From Date</em>}</li>
 *   <li>{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getUntilDate <em>Until Date</em>}</li>
 *   <li>{@link goedegep.invandprop.model.InvoiceAndPropertyItem#isArchive <em>Archive</em>}</li>
 *   <li>{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getRemarks <em>Remarks</em>}</li>
 *   <li>{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getNumberOfItems <em>Number Of Items</em>}</li>
 *   <li>{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getPictures <em>Pictures</em>}</li>
 *   <li>{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getDocuments <em>Documents</em>}</li>
 * </ul>
 *
 * @see goedegep.invandprop.model.InvAndPropPackage#getInvoiceAndPropertyItem()
 * @model
 * @generated
 */
public interface InvoiceAndPropertyItem extends EObject {
  /**
   * Returns the value of the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' attribute.
   * @see #isSetType()
   * @see #unsetType()
   * @see #setType(String)
   * @see goedegep.invandprop.model.InvAndPropPackage#getInvoiceAndPropertyItem_Type()
   * @model unsettable="true"
   * @generated
   */
  String getType();

  /**
   * Sets the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getType <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' attribute.
   * @see #isSetType()
   * @see #unsetType()
   * @see #getType()
   * @generated
   */
  void setType(String value);

  /**
   * Unsets the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getType <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetType()
   * @see #getType()
   * @see #setType(String)
   * @generated
   */
  void unsetType();

  /**
   * Returns whether the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getType <em>Type</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Type</em>' attribute is set.
   * @see #unsetType()
   * @see #getType()
   * @see #setType(String)
   * @generated
   */
  boolean isSetType();

  /**
   * Returns the value of the '<em><b>Serial Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Serial Number</em>' attribute.
   * @see #isSetSerialNumber()
   * @see #unsetSerialNumber()
   * @see #setSerialNumber(String)
   * @see goedegep.invandprop.model.InvAndPropPackage#getInvoiceAndPropertyItem_SerialNumber()
   * @model unsettable="true"
   * @generated
   */
  String getSerialNumber();

  /**
   * Sets the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getSerialNumber <em>Serial Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Serial Number</em>' attribute.
   * @see #isSetSerialNumber()
   * @see #unsetSerialNumber()
   * @see #getSerialNumber()
   * @generated
   */
  void setSerialNumber(String value);

  /**
   * Unsets the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getSerialNumber <em>Serial Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetSerialNumber()
   * @see #getSerialNumber()
   * @see #setSerialNumber(String)
   * @generated
   */
  void unsetSerialNumber();

  /**
   * Returns whether the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getSerialNumber <em>Serial Number</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Serial Number</em>' attribute is set.
   * @see #unsetSerialNumber()
   * @see #getSerialNumber()
   * @see #setSerialNumber(String)
   * @generated
   */
  boolean isSetSerialNumber();

  /**
   * Returns the value of the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Description</em>' attribute.
   * @see #isSetDescription()
   * @see #unsetDescription()
   * @see #setDescription(String)
   * @see goedegep.invandprop.model.InvAndPropPackage#getInvoiceAndPropertyItem_Description()
   * @model unsettable="true"
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #isSetDescription()
   * @see #unsetDescription()
   * @see #getDescription()
   * @generated
   */
  void setDescription(String value);

  /**
   * Unsets the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDescription()
   * @see #getDescription()
   * @see #setDescription(String)
   * @generated
   */
  void unsetDescription();

  /**
   * Returns whether the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getDescription <em>Description</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Description</em>' attribute is set.
   * @see #unsetDescription()
   * @see #getDescription()
   * @see #setDescription(String)
   * @generated
   */
  boolean isSetDescription();

  /**
   * Returns the value of the '<em><b>Brand</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Brand</em>' attribute.
   * @see #isSetBrand()
   * @see #unsetBrand()
   * @see #setBrand(String)
   * @see goedegep.invandprop.model.InvAndPropPackage#getInvoiceAndPropertyItem_Brand()
   * @model unsettable="true"
   * @generated
   */
  String getBrand();

  /**
   * Sets the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getBrand <em>Brand</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Brand</em>' attribute.
   * @see #isSetBrand()
   * @see #unsetBrand()
   * @see #getBrand()
   * @generated
   */
  void setBrand(String value);

  /**
   * Unsets the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getBrand <em>Brand</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetBrand()
   * @see #getBrand()
   * @see #setBrand(String)
   * @generated
   */
  void unsetBrand();

  /**
   * Returns whether the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getBrand <em>Brand</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Brand</em>' attribute is set.
   * @see #unsetBrand()
   * @see #getBrand()
   * @see #setBrand(String)
   * @generated
   */
  boolean isSetBrand();

  /**
   * Returns the value of the '<em><b>Amount</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Amount</em>' attribute.
   * @see #isSetAmount()
   * @see #unsetAmount()
   * @see #setAmount(PgCurrency)
   * @see goedegep.invandprop.model.InvAndPropPackage#getInvoiceAndPropertyItem_Amount()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getAmount();

  /**
   * Sets the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getAmount <em>Amount</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Amount</em>' attribute.
   * @see #isSetAmount()
   * @see #unsetAmount()
   * @see #getAmount()
   * @generated
   */
  void setAmount(PgCurrency value);

  /**
   * Unsets the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getAmount <em>Amount</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetAmount()
   * @see #getAmount()
   * @see #setAmount(PgCurrency)
   * @generated
   */
  void unsetAmount();

  /**
   * Returns whether the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getAmount <em>Amount</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Amount</em>' attribute is set.
   * @see #unsetAmount()
   * @see #getAmount()
   * @see #setAmount(PgCurrency)
   * @generated
   */
  boolean isSetAmount();

  /**
   * Returns the value of the '<em><b>From Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>From Date</em>' attribute.
   * @see #isSetFromDate()
   * @see #unsetFromDate()
   * @see #setFromDate(FlexDate)
   * @see goedegep.invandprop.model.InvAndPropPackage#getInvoiceAndPropertyItem_FromDate()
   * @model unsettable="true" dataType="goedegep.types.model.EFlexDate"
   * @generated
   */
  FlexDate getFromDate();

  /**
   * Sets the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getFromDate <em>From Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>From Date</em>' attribute.
   * @see #isSetFromDate()
   * @see #unsetFromDate()
   * @see #getFromDate()
   * @generated
   */
  void setFromDate(FlexDate value);

  /**
   * Unsets the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getFromDate <em>From Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetFromDate()
   * @see #getFromDate()
   * @see #setFromDate(FlexDate)
   * @generated
   */
  void unsetFromDate();

  /**
   * Returns whether the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getFromDate <em>From Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>From Date</em>' attribute is set.
   * @see #unsetFromDate()
   * @see #getFromDate()
   * @see #setFromDate(FlexDate)
   * @generated
   */
  boolean isSetFromDate();

  /**
   * Returns the value of the '<em><b>Until Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Until Date</em>' attribute.
   * @see #isSetUntilDate()
   * @see #unsetUntilDate()
   * @see #setUntilDate(FlexDate)
   * @see goedegep.invandprop.model.InvAndPropPackage#getInvoiceAndPropertyItem_UntilDate()
   * @model unsettable="true" dataType="goedegep.types.model.EFlexDate"
   * @generated
   */
  FlexDate getUntilDate();

  /**
   * Sets the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getUntilDate <em>Until Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Until Date</em>' attribute.
   * @see #isSetUntilDate()
   * @see #unsetUntilDate()
   * @see #getUntilDate()
   * @generated
   */
  void setUntilDate(FlexDate value);

  /**
   * Unsets the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getUntilDate <em>Until Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetUntilDate()
   * @see #getUntilDate()
   * @see #setUntilDate(FlexDate)
   * @generated
   */
  void unsetUntilDate();

  /**
   * Returns whether the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getUntilDate <em>Until Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Until Date</em>' attribute is set.
   * @see #unsetUntilDate()
   * @see #getUntilDate()
   * @see #setUntilDate(FlexDate)
   * @generated
   */
  boolean isSetUntilDate();

  /**
   * Returns the value of the '<em><b>Archive</b></em>' attribute.
   * The default value is <code>"false"</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Archive</em>' attribute.
   * @see #setArchive(boolean)
   * @see goedegep.invandprop.model.InvAndPropPackage#getInvoiceAndPropertyItem_Archive()
   * @model default="false" required="true"
   * @generated
   */
  boolean isArchive();

  /**
   * Sets the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#isArchive <em>Archive</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Archive</em>' attribute.
   * @see #isArchive()
   * @generated
   */
  void setArchive(boolean value);

  /**
   * Returns the value of the '<em><b>Remarks</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Remarks</em>' attribute.
   * @see #isSetRemarks()
   * @see #unsetRemarks()
   * @see #setRemarks(String)
   * @see goedegep.invandprop.model.InvAndPropPackage#getInvoiceAndPropertyItem_Remarks()
   * @model unsettable="true"
   * @generated
   */
  String getRemarks();

  /**
   * Sets the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getRemarks <em>Remarks</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Remarks</em>' attribute.
   * @see #isSetRemarks()
   * @see #unsetRemarks()
   * @see #getRemarks()
   * @generated
   */
  void setRemarks(String value);

  /**
   * Unsets the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getRemarks <em>Remarks</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetRemarks()
   * @see #getRemarks()
   * @see #setRemarks(String)
   * @generated
   */
  void unsetRemarks();

  /**
   * Returns whether the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getRemarks <em>Remarks</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Remarks</em>' attribute is set.
   * @see #unsetRemarks()
   * @see #getRemarks()
   * @see #setRemarks(String)
   * @generated
   */
  boolean isSetRemarks();

  /**
   * Returns the value of the '<em><b>Number Of Items</b></em>' attribute.
   * The default value is <code>"1"</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Number Of Items</em>' attribute.
   * @see #setNumberOfItems(int)
   * @see goedegep.invandprop.model.InvAndPropPackage#getInvoiceAndPropertyItem_NumberOfItems()
   * @model default="1" required="true"
   * @generated
   */
  int getNumberOfItems();

  /**
   * Sets the value of the '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getNumberOfItems <em>Number Of Items</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Number Of Items</em>' attribute.
   * @see #getNumberOfItems()
   * @generated
   */
  void setNumberOfItems(int value);

  /**
   * Returns the value of the '<em><b>Pictures</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.types.model.FileReference}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Pictures</em>' containment reference list.
   * @see goedegep.invandprop.model.InvAndPropPackage#getInvoiceAndPropertyItem_Pictures()
   * @model containment="true"
   * @generated
   */
  EList<FileReference> getPictures();

  /**
   * Returns the value of the '<em><b>Documents</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.types.model.FileReference}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Documents</em>' containment reference list.
   * @see goedegep.invandprop.model.InvAndPropPackage#getInvoiceAndPropertyItem_Documents()
   * @model containment="true"
   * @generated
   */
  EList<FileReference> getDocuments();

} // InvoiceAndPropertyItem
