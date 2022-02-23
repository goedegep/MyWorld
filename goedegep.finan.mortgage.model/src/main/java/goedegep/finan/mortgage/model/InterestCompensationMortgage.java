/**
 */
package goedegep.finan.mortgage.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import goedegep.util.fixedpointvalue.FixedPointValue;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Interest Compensation Mortgage</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.InterestCompensationMortgage#getCompensationPercentageBorrower <em>Compensation Percentage Borrower</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.InterestCompensationMortgage#getPercentageDecemberPayment <em>Percentage December Payment</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.InterestCompensationMortgage#getCompensationPaymentsPerYear <em>Compensation Payments Per Year</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.mortgage.model.MortgagePackage#getInterestCompensationMortgage()
 * @model
 * @generated
 */
public interface InterestCompensationMortgage extends Mortgage {
  /**
   * Returns the value of the '<em><b>Compensation Percentage Borrower</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Compensation Percentage Borrower</em>' attribute.
   * @see #isSetCompensationPercentageBorrower()
   * @see #unsetCompensationPercentageBorrower()
   * @see #setCompensationPercentageBorrower(FixedPointValue)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getInterestCompensationMortgage_CompensationPercentageBorrower()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
  FixedPointValue getCompensationPercentageBorrower();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.InterestCompensationMortgage#getCompensationPercentageBorrower <em>Compensation Percentage Borrower</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Compensation Percentage Borrower</em>' attribute.
   * @see #isSetCompensationPercentageBorrower()
   * @see #unsetCompensationPercentageBorrower()
   * @see #getCompensationPercentageBorrower()
   * @generated
   */
  void setCompensationPercentageBorrower(FixedPointValue value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.InterestCompensationMortgage#getCompensationPercentageBorrower <em>Compensation Percentage Borrower</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetCompensationPercentageBorrower()
   * @see #getCompensationPercentageBorrower()
   * @see #setCompensationPercentageBorrower(FixedPointValue)
   * @generated
   */
  void unsetCompensationPercentageBorrower();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.InterestCompensationMortgage#getCompensationPercentageBorrower <em>Compensation Percentage Borrower</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Compensation Percentage Borrower</em>' attribute is set.
   * @see #unsetCompensationPercentageBorrower()
   * @see #getCompensationPercentageBorrower()
   * @see #setCompensationPercentageBorrower(FixedPointValue)
   * @generated
   */
  boolean isSetCompensationPercentageBorrower();

  /**
   * Returns the value of the '<em><b>Percentage December Payment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Percentage December Payment</em>' attribute.
   * @see #isSetPercentageDecemberPayment()
   * @see #unsetPercentageDecemberPayment()
   * @see #setPercentageDecemberPayment(FixedPointValue)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getInterestCompensationMortgage_PercentageDecemberPayment()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
  FixedPointValue getPercentageDecemberPayment();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.InterestCompensationMortgage#getPercentageDecemberPayment <em>Percentage December Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Percentage December Payment</em>' attribute.
   * @see #isSetPercentageDecemberPayment()
   * @see #unsetPercentageDecemberPayment()
   * @see #getPercentageDecemberPayment()
   * @generated
   */
  void setPercentageDecemberPayment(FixedPointValue value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.InterestCompensationMortgage#getPercentageDecemberPayment <em>Percentage December Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetPercentageDecemberPayment()
   * @see #getPercentageDecemberPayment()
   * @see #setPercentageDecemberPayment(FixedPointValue)
   * @generated
   */
  void unsetPercentageDecemberPayment();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.InterestCompensationMortgage#getPercentageDecemberPayment <em>Percentage December Payment</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Percentage December Payment</em>' attribute is set.
   * @see #unsetPercentageDecemberPayment()
   * @see #getPercentageDecemberPayment()
   * @see #setPercentageDecemberPayment(FixedPointValue)
   * @generated
   */
  boolean isSetPercentageDecemberPayment();

  /**
   * Returns the value of the '<em><b>Compensation Payments Per Year</b></em>' map.
   * The key is of type {@link java.lang.Integer},
   * and the value is of type list of {@link goedegep.finan.mortgage.model.CompensationPayment},
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Compensation Payments Per Year</em>' map.
   * @see goedegep.finan.mortgage.model.MortgagePackage#getInterestCompensationMortgage_CompensationPaymentsPerYear()
   * @model mapType="goedegep.finan.mortgage.model.IntegerToEList&lt;org.eclipse.emf.ecore.EIntegerObject, goedegep.finan.mortgage.model.CompensationPayment&gt;"
   * @generated
   */
  EMap<Integer, EList<CompensationPayment>> getCompensationPaymentsPerYear();

} // InterestCompensationMortgage
