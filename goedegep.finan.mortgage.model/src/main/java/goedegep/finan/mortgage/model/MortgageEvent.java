/**
 */
package goedegep.finan.mortgage.model;

import java.util.Date;

import org.eclipse.emf.ecore.EObject;

import goedegep.util.fixedpointvalue.FixedPointValue;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.MortgageEvent#getDate <em>Date</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.MortgageEvent#getComments <em>Comments</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.MortgageEvent#getNewInterestRate <em>New Interest Rate</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgageEvent()
 * @model abstract="true"
 * @generated
 */
public interface MortgageEvent extends EObject {
  /**
   * Returns the value of the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The date the event takes place.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Date</em>' attribute.
   * @see #isSetDate()
   * @see #unsetDate()
   * @see #setDate(Date)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgageEvent_Date()
   * @model unsettable="true"
   * @generated
   */
  Date getDate();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.MortgageEvent#getDate <em>Date</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.MortgageEvent#getDate <em>Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDate()
   * @see #getDate()
   * @see #setDate(Date)
   * @generated
   */
  void unsetDate();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.MortgageEvent#getDate <em>Date</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Comments</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Any remarks about this event.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Comments</em>' attribute.
   * @see #isSetComments()
   * @see #unsetComments()
   * @see #setComments(String)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgageEvent_Comments()
   * @model unsettable="true"
   * @generated
   */
  String getComments();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.MortgageEvent#getComments <em>Comments</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Comments</em>' attribute.
   * @see #isSetComments()
   * @see #unsetComments()
   * @see #getComments()
   * @generated
   */
  void setComments(String value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.MortgageEvent#getComments <em>Comments</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetComments()
   * @see #getComments()
   * @see #setComments(String)
   * @generated
   */
  void unsetComments();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.MortgageEvent#getComments <em>Comments</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Comments</em>' attribute is set.
   * @see #unsetComments()
   * @see #getComments()
   * @see #setComments(String)
   * @generated
   */
  boolean isSetComments();

  /**
   * Returns the value of the '<em><b>New Interest Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * New interest rate.
   * <!-- end-model-doc -->
   * @return the value of the '<em>New Interest Rate</em>' attribute.
   * @see #isSetNewInterestRate()
   * @see #unsetNewInterestRate()
   * @see #setNewInterestRate(FixedPointValue)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgageEvent_NewInterestRate()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
  FixedPointValue getNewInterestRate();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.MortgageEvent#getNewInterestRate <em>New Interest Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>New Interest Rate</em>' attribute.
   * @see #isSetNewInterestRate()
   * @see #unsetNewInterestRate()
   * @see #getNewInterestRate()
   * @generated
   */
  void setNewInterestRate(FixedPointValue value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.MortgageEvent#getNewInterestRate <em>New Interest Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetNewInterestRate()
   * @see #getNewInterestRate()
   * @see #setNewInterestRate(FixedPointValue)
   * @generated
   */
  void unsetNewInterestRate();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.MortgageEvent#getNewInterestRate <em>New Interest Rate</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>New Interest Rate</em>' attribute is set.
   * @see #unsetNewInterestRate()
   * @see #getNewInterestRate()
   * @see #setNewInterestRate(FixedPointValue)
   * @generated
   */
  boolean isSetNewInterestRate();

} // MortgageEvent
