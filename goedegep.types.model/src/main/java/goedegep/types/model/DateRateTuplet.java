/**
 */
package goedegep.types.model;

import goedegep.util.money.PgCurrency;

import java.time.LocalDate;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Date Rate Tuplet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.types.model.DateRateTuplet#getDate <em>Date</em>}</li>
 *   <li>{@link goedegep.types.model.DateRateTuplet#getRate <em>Rate</em>}</li>
 * </ul>
 *
 * @see goedegep.types.model.TypesPackage#getDateRateTuplet()
 * @model
 * @generated
 */
public interface DateRateTuplet extends EObject {
  /**
   * Returns the value of the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Date</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Date</em>' attribute.
   * @see #isSetDate()
   * @see #unsetDate()
   * @see #setDate(LocalDate)
   * @see goedegep.types.model.TypesPackage#getDateRateTuplet_Date()
   * @model unsettable="true" dataType="goedegep.types.model.ELocalDate"
   * @generated
   */
  LocalDate getDate();

  /**
   * Sets the value of the '{@link goedegep.types.model.DateRateTuplet#getDate <em>Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Date</em>' attribute.
   * @see #isSetDate()
   * @see #unsetDate()
   * @see #getDate()
   * @generated
   */
  void setDate(LocalDate value);

  /**
   * Unsets the value of the '{@link goedegep.types.model.DateRateTuplet#getDate <em>Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDate()
   * @see #getDate()
   * @see #setDate(LocalDate)
   * @generated
   */
  void unsetDate();

  /**
   * Returns whether the value of the '{@link goedegep.types.model.DateRateTuplet#getDate <em>Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Date</em>' attribute is set.
   * @see #unsetDate()
   * @see #getDate()
   * @see #setDate(LocalDate)
   * @generated
   */
  boolean isSetDate();

  /**
   * Returns the value of the '<em><b>Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Rate</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Rate</em>' attribute.
   * @see #isSetRate()
   * @see #unsetRate()
   * @see #setRate(PgCurrency)
   * @see goedegep.types.model.TypesPackage#getDateRateTuplet_Rate()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getRate();

  /**
   * Sets the value of the '{@link goedegep.types.model.DateRateTuplet#getRate <em>Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Rate</em>' attribute.
   * @see #isSetRate()
   * @see #unsetRate()
   * @see #getRate()
   * @generated
   */
  void setRate(PgCurrency value);

  /**
   * Unsets the value of the '{@link goedegep.types.model.DateRateTuplet#getRate <em>Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetRate()
   * @see #getRate()
   * @see #setRate(PgCurrency)
   * @generated
   */
  void unsetRate();

  /**
   * Returns whether the value of the '{@link goedegep.types.model.DateRateTuplet#getRate <em>Rate</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Rate</em>' attribute is set.
   * @see #unsetRate()
   * @see #getRate()
   * @see #setRate(PgCurrency)
   * @generated
   */
  boolean isSetRate();

} // DateRateTuplet
