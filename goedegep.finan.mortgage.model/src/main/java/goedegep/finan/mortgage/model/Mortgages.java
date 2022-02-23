/**
 */
package goedegep.finan.mortgage.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mortgages</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.Mortgages#getMortgages <em>Mortgages</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.Mortgages#getInterestRateSets <em>Interest Rate Sets</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgages()
 * @model
 * @generated
 */
public interface Mortgages extends EObject {
  /**
   * Returns the value of the '<em><b>Mortgages</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.finan.mortgage.model.Mortgage}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Mortgages</em>' containment reference list.
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgages_Mortgages()
   * @model containment="true"
   * @generated
   */
  EList<Mortgage> getMortgages();

  /**
   * Returns the value of the '<em><b>Interest Rate Sets</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.finan.mortgage.model.InterestRateSet}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Interest Rate Sets</em>' containment reference list.
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgages_InterestRateSets()
   * @model containment="true"
   * @generated
   */
  EList<InterestRateSet> getInterestRateSets();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model
   * @generated
   */
  Mortgage getMortgage(String mortgageNumber);

} // Mortgages
