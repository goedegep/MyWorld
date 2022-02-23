/**
 */
package goedegep.finan.investmentinsurance.model;

import org.eclipse.emf.common.util.EList;

import goedegep.rolodex.model.Institution;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Maatschappij</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InsuranceCompany#getInvestmentFunds <em>Investment Funds</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InsuranceCompany#getDepartment <em>Department</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InsuranceCompany#getWebsite <em>Website</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInsuranceCompany()
 * @model
 * @generated
 */
public interface InsuranceCompany extends Institution {
	/**
   * Returns the value of the '<em><b>Investment Funds</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.finan.investmentinsurance.model.InvestmentFund}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fondsen</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Investment Funds</em>' containment reference list.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInsuranceCompany_InvestmentFunds()
   * @model containment="true"
   * @generated
   */
	EList<InvestmentFund> getInvestmentFunds();

	/**
   * Returns the value of the '<em><b>Department</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Afdeling</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Department</em>' attribute.
   * @see #isSetDepartment()
   * @see #unsetDepartment()
   * @see #setDepartment(String)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInsuranceCompany_Department()
   * @model unsettable="true"
   * @generated
   */
	String getDepartment();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.InsuranceCompany#getDepartment <em>Department</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Department</em>' attribute.
   * @see #isSetDepartment()
   * @see #unsetDepartment()
   * @see #getDepartment()
   * @generated
   */
	void setDepartment(String value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.InsuranceCompany#getDepartment <em>Department</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDepartment()
   * @see #getDepartment()
   * @see #setDepartment(String)
   * @generated
   */
  void unsetDepartment();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.InsuranceCompany#getDepartment <em>Department</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Department</em>' attribute is set.
   * @see #unsetDepartment()
   * @see #getDepartment()
   * @see #setDepartment(String)
   * @generated
   */
	boolean isSetDepartment();

	/**
   * Returns the value of the '<em><b>Website</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Website</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Website</em>' attribute.
   * @see #isSetWebsite()
   * @see #unsetWebsite()
   * @see #setWebsite(String)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInsuranceCompany_Website()
   * @model unsettable="true"
   * @generated
   */
	String getWebsite();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.InsuranceCompany#getWebsite <em>Website</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Website</em>' attribute.
   * @see #isSetWebsite()
   * @see #unsetWebsite()
   * @see #getWebsite()
   * @generated
   */
	void setWebsite(String value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.InsuranceCompany#getWebsite <em>Website</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #isSetWebsite()
   * @see #getWebsite()
   * @see #setWebsite(String)
   * @generated
   */
	void unsetWebsite();

	/**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.InsuranceCompany#getWebsite <em>Website</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Website</em>' attribute is set.
   * @see #unsetWebsite()
   * @see #getWebsite()
   * @see #setWebsite(String)
   * @generated
   */
	boolean isSetWebsite();

} // Maatschappij
