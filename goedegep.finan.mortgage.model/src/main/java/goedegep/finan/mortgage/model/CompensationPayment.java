/**
 */
package goedegep.finan.mortgage.model;

import goedegep.util.money.PgCurrency;
import java.util.Date;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compensation Payment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.CompensationPayment#getDate <em>Date</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.CompensationPayment#getAmount <em>Amount</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.CompensationPayment#getDescription <em>Description</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.mortgage.model.MortgagePackage#getCompensationPayment()
 * @model
 * @generated
 */
public interface CompensationPayment extends EObject {
  /**
   * Returns the value of the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Date</em>' attribute.
   * @see #isSetDate()
   * @see #unsetDate()
   * @see #setDate(Date)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getCompensationPayment_Date()
   * @model unsettable="true"
   * @generated
   */
  Date getDate();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.CompensationPayment#getDate <em>Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Date</em>' attribute.
   * @see #isSetDate()
   * @see #unsetDate()
   * @see #getDate()
   * @generated
   */
  void setDate(Date value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.CompensationPayment#getDate <em>Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDate()
   * @see #getDate()
   * @see #setDate(Date)
   * @generated
   */
  void unsetDate();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.CompensationPayment#getDate <em>Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Date</em>' attribute is set.
   * @see #unsetDate()
   * @see #getDate()
   * @see #setDate(Date)
   * @generated
   */
  boolean isSetDate();

  /**
   * Returns the value of the '<em><b>Amount</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Amount</em>' attribute.
   * @see #isSetAmount()
   * @see #unsetAmount()
   * @see #setAmount(PgCurrency)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getCompensationPayment_Amount()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getAmount();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.CompensationPayment#getAmount <em>Amount</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.CompensationPayment#getAmount <em>Amount</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetAmount()
   * @see #getAmount()
   * @see #setAmount(PgCurrency)
   * @generated
   */
  void unsetAmount();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.CompensationPayment#getAmount <em>Amount</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Description</em>' attribute.
   * @see #isSetDescription()
   * @see #unsetDescription()
   * @see #setDescription(String)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getCompensationPayment_Description()
   * @model unsettable="true"
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.CompensationPayment#getDescription <em>Description</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.CompensationPayment#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDescription()
   * @see #getDescription()
   * @see #setDescription(String)
   * @generated
   */
  void unsetDescription();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.CompensationPayment#getDescription <em>Description</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Description</em>' attribute is set.
   * @see #unsetDescription()
   * @see #getDescription()
   * @see #setDescription(String)
   * @generated
   */
  boolean isSetDescription();

} // CompensationPayment
