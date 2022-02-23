/**
 */
package goedegep.finan.mortgage.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compensation Payments</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.CompensationPayments#getCompensationpayments <em>Compensationpayments</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.mortgage.model.MortgagePackage#getCompensationPayments()
 * @model
 * @generated
 */
public interface CompensationPayments extends EObject {
  /**
   * Returns the value of the '<em><b>Compensationpayments</b></em>' reference list.
   * The list contents are of type {@link goedegep.finan.mortgage.model.CompensationPayment}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Compensationpayments</em>' reference list.
   * @see goedegep.finan.mortgage.model.MortgagePackage#getCompensationPayments_Compensationpayments()
   * @model
   * @generated
   */
  EList<CompensationPayment> getCompensationpayments();

} // CompensationPayments
