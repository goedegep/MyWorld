/**
 */
package goedegep.finan.mortgage.model;

import java.util.Date;

import org.eclipse.emf.ecore.EObject;

import goedegep.util.fixedpointvalue.FixedPointValue;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rate</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.Rate#getDate <em>Date</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.Rate#getRate <em>Rate</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.mortgage.model.MortgagePackage#getRate()
 * @model
 * @generated
 */
public interface Rate extends EObject {
  /**
   * Returns the value of the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Date</em>' attribute.
   * @see #isSetDate()
   * @see #unsetDate()
   * @see #setDate(Date)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getRate_Date()
   * @model unsettable="true"
   * @generated
   */
  Date getDate();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.Rate#getDate <em>Date</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.Rate#getDate <em>Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDate()
   * @see #getDate()
   * @see #setDate(Date)
   * @generated
   */
  void unsetDate();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.Rate#getDate <em>Date</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Rate</em>' attribute.
   * @see #isSetRate()
   * @see #unsetRate()
   * @see #setRate(FixedPointValue)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getRate_Rate()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
  FixedPointValue getRate();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.Rate#getRate <em>Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Rate</em>' attribute.
   * @see #isSetRate()
   * @see #unsetRate()
   * @see #getRate()
   * @generated
   */
  void setRate(FixedPointValue value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.Rate#getRate <em>Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetRate()
   * @see #getRate()
   * @see #setRate(FixedPointValue)
   * @generated
   */
  void unsetRate();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.Rate#getRate <em>Rate</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Rate</em>' attribute is set.
   * @see #unsetRate()
   * @see #getRate()
   * @see #setRate(FixedPointValue)
   * @generated
   */
  boolean isSetRate();

} // Rate
