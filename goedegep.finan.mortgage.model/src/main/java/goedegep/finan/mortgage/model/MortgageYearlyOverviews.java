/**
 */
package goedegep.finan.mortgage.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Yearly Overviews</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.MortgageYearlyOverviews#getYearlyOverviews <em>Yearly Overviews</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgageYearlyOverviews()
 * @model
 * @generated
 */
public interface MortgageYearlyOverviews extends EObject {
  /**
   * Returns the value of the '<em><b>Yearly Overviews</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.finan.mortgage.model.MortgageYearlyOverview}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Yearly Overviews</em>' containment reference list.
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgageYearlyOverviews_YearlyOverviews()
   * @model containment="true"
   * @generated
   */
  EList<MortgageYearlyOverview> getYearlyOverviews();

} // MortgageYearlyOverviews
