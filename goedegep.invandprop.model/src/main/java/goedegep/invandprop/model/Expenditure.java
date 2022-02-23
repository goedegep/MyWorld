/**
 */
package goedegep.invandprop.model;

import goedegep.util.money.PgCurrency;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Expenditure</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.invandprop.model.Expenditure#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.invandprop.model.Expenditure#getAmount <em>Amount</em>}</li>
 *   <li>{@link goedegep.invandprop.model.Expenditure#getRemarks <em>Remarks</em>}</li>
 *   <li>{@link goedegep.invandprop.model.Expenditure#isDescriptionFromProperty <em>Description From Property</em>}</li>
 *   <li>{@link goedegep.invandprop.model.Expenditure#getPurchase <em>Purchase</em>}</li>
 * </ul>
 *
 * @see goedegep.invandprop.model.InvAndPropPackage#getExpenditure()
 * @model
 * @generated
 */
public interface Expenditure extends EObject {
  /**
   * Returns the value of the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Description</em>' attribute.
   * @see #isSetDescription()
   * @see #unsetDescription()
   * @see #setDescription(String)
   * @see goedegep.invandprop.model.InvAndPropPackage#getExpenditure_Description()
   * @model unsettable="true"
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link goedegep.invandprop.model.Expenditure#getDescription <em>Description</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.invandprop.model.Expenditure#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDescription()
   * @see #getDescription()
   * @see #setDescription(String)
   * @generated
   */
  void unsetDescription();

  /**
   * Returns whether the value of the '{@link goedegep.invandprop.model.Expenditure#getDescription <em>Description</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Amount</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Amount</em>' attribute.
   * @see #isSetAmount()
   * @see #unsetAmount()
   * @see #setAmount(PgCurrency)
   * @see goedegep.invandprop.model.InvAndPropPackage#getExpenditure_Amount()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getAmount();

  /**
   * Sets the value of the '{@link goedegep.invandprop.model.Expenditure#getAmount <em>Amount</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.invandprop.model.Expenditure#getAmount <em>Amount</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetAmount()
   * @see #getAmount()
   * @see #setAmount(PgCurrency)
   * @generated
   */
  void unsetAmount();

  /**
   * Returns whether the value of the '{@link goedegep.invandprop.model.Expenditure#getAmount <em>Amount</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Remarks</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Remarks</em>' attribute.
   * @see #isSetRemarks()
   * @see #unsetRemarks()
   * @see #setRemarks(String)
   * @see goedegep.invandprop.model.InvAndPropPackage#getExpenditure_Remarks()
   * @model unsettable="true"
   * @generated
   */
  String getRemarks();

  /**
   * Sets the value of the '{@link goedegep.invandprop.model.Expenditure#getRemarks <em>Remarks</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.invandprop.model.Expenditure#getRemarks <em>Remarks</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetRemarks()
   * @see #getRemarks()
   * @see #setRemarks(String)
   * @generated
   */
  void unsetRemarks();

  /**
   * Returns whether the value of the '{@link goedegep.invandprop.model.Expenditure#getRemarks <em>Remarks</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Description From Property</b></em>' attribute.
   * The default value is <code>"true"</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Description From Property</em>' attribute.
   * @see #setDescriptionFromProperty(boolean)
   * @see goedegep.invandprop.model.InvAndPropPackage#getExpenditure_DescriptionFromProperty()
   * @model default="true"
   * @generated
   */
  boolean isDescriptionFromProperty();

  /**
   * Sets the value of the '{@link goedegep.invandprop.model.Expenditure#isDescriptionFromProperty <em>Description From Property</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description From Property</em>' attribute.
   * @see #isDescriptionFromProperty()
   * @generated
   */
  void setDescriptionFromProperty(boolean value);

  /**
   * Returns the value of the '<em><b>Purchase</b></em>' reference.
   * It is bidirectional and its opposite is '{@link goedegep.invandprop.model.Property#getExpenditure <em>Expenditure</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Purchase</em>' reference.
   * @see #isSetPurchase()
   * @see #unsetPurchase()
   * @see #setPurchase(Property)
   * @see goedegep.invandprop.model.InvAndPropPackage#getExpenditure_Purchase()
   * @see goedegep.invandprop.model.Property#getExpenditure
   * @model opposite="expenditure" unsettable="true"
   * @generated
   */
  Property getPurchase();

  /**
   * Sets the value of the '{@link goedegep.invandprop.model.Expenditure#getPurchase <em>Purchase</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Purchase</em>' reference.
   * @see #isSetPurchase()
   * @see #unsetPurchase()
   * @see #getPurchase()
   * @generated
   */
  void setPurchase(Property value);

  /**
   * Unsets the value of the '{@link goedegep.invandprop.model.Expenditure#getPurchase <em>Purchase</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetPurchase()
   * @see #getPurchase()
   * @see #setPurchase(Property)
   * @generated
   */
  void unsetPurchase();

  /**
   * Returns whether the value of the '{@link goedegep.invandprop.model.Expenditure#getPurchase <em>Purchase</em>}' reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Purchase</em>' reference is set.
   * @see #unsetPurchase()
   * @see #getPurchase()
   * @see #setPurchase(Property)
   * @generated
   */
  boolean isSetPurchase();

} // Expenditure
