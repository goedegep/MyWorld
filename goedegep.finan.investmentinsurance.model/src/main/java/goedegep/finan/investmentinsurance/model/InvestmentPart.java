/**
 */
package goedegep.finan.investmentinsurance.model;

import org.eclipse.emf.ecore.EObject;

import goedegep.util.fixedpointvalue.FixedPointValue;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Beleggingsdeel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InvestmentPart#getInvestmentFund <em>Investment Fund</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InvestmentPart#getPercentage <em>Percentage</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentPart()
 * @model
 * @generated
 */
public interface InvestmentPart extends EObject {
	/**
   * Returns the value of the '<em><b>Investment Fund</b></em>' reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fonds</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Investment Fund</em>' reference.
   * @see #setInvestmentFund(InvestmentFund)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentPart_InvestmentFund()
   * @model
   * @generated
   */
	InvestmentFund getInvestmentFund();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentPart#getInvestmentFund <em>Investment Fund</em>}' reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Investment Fund</em>' reference.
   * @see #getInvestmentFund()
   * @generated
   */
	void setInvestmentFund(InvestmentFund value);

	/**
   * Returns the value of the '<em><b>Percentage</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Percentage</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Percentage</em>' attribute.
   * @see #isSetPercentage()
   * @see #unsetPercentage()
   * @see #setPercentage(FixedPointValue)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentPart_Percentage()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
	FixedPointValue getPercentage();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentPart#getPercentage <em>Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Percentage</em>' attribute.
   * @see #isSetPercentage()
   * @see #unsetPercentage()
   * @see #getPercentage()
   * @generated
   */
	void setPercentage(FixedPointValue value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentPart#getPercentage <em>Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #isSetPercentage()
   * @see #getPercentage()
   * @see #setPercentage(FixedPointValue)
   * @generated
   */
	void unsetPercentage();

	/**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentPart#getPercentage <em>Percentage</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Percentage</em>' attribute is set.
   * @see #unsetPercentage()
   * @see #getPercentage()
   * @see #setPercentage(FixedPointValue)
   * @generated
   */
	boolean isSetPercentage();

} // Beleggingsdeel
