/**
 */
package goedegep.finan.investmentinsurance.model;

import goedegep.rolodex.model.Person;
import goedegep.types.model.Event;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;

import java.time.LocalDate;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Beleggings Verzekering</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getInsuranceCompany <em>Insurance Company</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getPolicyNumber <em>Policy Number</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getStartingDate <em>Starting Date</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getPolicyHolder <em>Policy Holder</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getPremium <em>Premium</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getInsuredBenefitOnDeath <em>Insured Benefit On Death</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getInvestmentParts <em>Investment Parts</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getEvents <em>Events</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentInsurance()
 * @model
 * @generated
 */
public interface InvestmentInsurance extends EObject {
	/**
   * Returns the value of the '<em><b>Insurance Company</b></em>' reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Maatschappij</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Insurance Company</em>' reference.
   * @see #setInsuranceCompany(InsuranceCompany)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentInsurance_InsuranceCompany()
   * @model
   * @generated
   */
	InsuranceCompany getInsuranceCompany();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getInsuranceCompany <em>Insurance Company</em>}' reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Insurance Company</em>' reference.
   * @see #getInsuranceCompany()
   * @generated
   */
	void setInsuranceCompany(InsuranceCompany value);

	/**
   * Returns the value of the '<em><b>Policy Number</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Polisnummer</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Policy Number</em>' attribute.
   * @see #isSetPolicyNumber()
   * @see #unsetPolicyNumber()
   * @see #setPolicyNumber(String)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentInsurance_PolicyNumber()
   * @model unsettable="true" required="true"
   * @generated
   */
	String getPolicyNumber();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getPolicyNumber <em>Policy Number</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Policy Number</em>' attribute.
   * @see #isSetPolicyNumber()
   * @see #unsetPolicyNumber()
   * @see #getPolicyNumber()
   * @generated
   */
	void setPolicyNumber(String value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getPolicyNumber <em>Policy Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetPolicyNumber()
   * @see #getPolicyNumber()
   * @see #setPolicyNumber(String)
   * @generated
   */
  void unsetPolicyNumber();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getPolicyNumber <em>Policy Number</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Policy Number</em>' attribute is set.
   * @see #unsetPolicyNumber()
   * @see #getPolicyNumber()
   * @see #setPolicyNumber(String)
   * @generated
   */
	boolean isSetPolicyNumber();

	/**
   * Returns the value of the '<em><b>Starting Date</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ingangs Datum</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Starting Date</em>' attribute.
   * @see #isSetStartingDate()
   * @see #unsetStartingDate()
   * @see #setStartingDate(LocalDate)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentInsurance_StartingDate()
   * @model unsettable="true" dataType="goedegep.types.model.ELocalDate"
   * @generated
   */
	LocalDate getStartingDate();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getStartingDate <em>Starting Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Starting Date</em>' attribute.
   * @see #isSetStartingDate()
   * @see #unsetStartingDate()
   * @see #getStartingDate()
   * @generated
   */
  void setStartingDate(LocalDate value);

  /**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getStartingDate <em>Starting Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetStartingDate()
   * @see #getStartingDate()
   * @see #setStartingDate(LocalDate)
   * @generated
   */
  void unsetStartingDate();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getStartingDate <em>Starting Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Starting Date</em>' attribute is set.
   * @see #unsetStartingDate()
   * @see #getStartingDate()
   * @see #setStartingDate(LocalDate)
   * @generated
   */
	boolean isSetStartingDate();

	/**
   * Returns the value of the '<em><b>Policy Holder</b></em>' reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Verzekering Nemer</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Policy Holder</em>' reference.
   * @see #isSetPolicyHolder()
   * @see #unsetPolicyHolder()
   * @see #setPolicyHolder(Person)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentInsurance_PolicyHolder()
   * @model unsettable="true"
   * @generated
   */
	Person getPolicyHolder();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getPolicyHolder <em>Policy Holder</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Policy Holder</em>' reference.
   * @see #isSetPolicyHolder()
   * @see #unsetPolicyHolder()
   * @see #getPolicyHolder()
   * @generated
   */
  void setPolicyHolder(Person value);

  /**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getPolicyHolder <em>Policy Holder</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetPolicyHolder()
   * @see #getPolicyHolder()
   * @see #setPolicyHolder(Person)
   * @generated
   */
  void unsetPolicyHolder();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getPolicyHolder <em>Policy Holder</em>}' reference is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Policy Holder</em>' reference is set.
   * @see #unsetPolicyHolder()
   * @see #getPolicyHolder()
   * @see #setPolicyHolder(Person)
   * @generated
   */
	boolean isSetPolicyHolder();

	/**
   * Returns the value of the '<em><b>Premium</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Premie</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Premium</em>' attribute.
   * @see #isSetPremium()
   * @see #unsetPremium()
   * @see #setPremium(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentInsurance_Premium()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getPremium();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getPremium <em>Premium</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Premium</em>' attribute.
   * @see #isSetPremium()
   * @see #unsetPremium()
   * @see #getPremium()
   * @generated
   */
	void setPremium(PgCurrency value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getPremium <em>Premium</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetPremium()
   * @see #getPremium()
   * @see #setPremium(PgCurrency)
   * @generated
   */
  void unsetPremium();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getPremium <em>Premium</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Premium</em>' attribute is set.
   * @see #unsetPremium()
   * @see #getPremium()
   * @see #setPremium(PgCurrency)
   * @generated
   */
	boolean isSetPremium();

	/**
   * Returns the value of the '<em><b>Insured Benefit On Death</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Verzekerde Uitkering Bij Overlijden</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Insured Benefit On Death</em>' attribute.
   * @see #isSetInsuredBenefitOnDeath()
   * @see #unsetInsuredBenefitOnDeath()
   * @see #setInsuredBenefitOnDeath(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentInsurance_InsuredBenefitOnDeath()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getInsuredBenefitOnDeath();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getInsuredBenefitOnDeath <em>Insured Benefit On Death</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Insured Benefit On Death</em>' attribute.
   * @see #isSetInsuredBenefitOnDeath()
   * @see #unsetInsuredBenefitOnDeath()
   * @see #getInsuredBenefitOnDeath()
   * @generated
   */
	void setInsuredBenefitOnDeath(PgCurrency value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getInsuredBenefitOnDeath <em>Insured Benefit On Death</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetInsuredBenefitOnDeath()
   * @see #getInsuredBenefitOnDeath()
   * @see #setInsuredBenefitOnDeath(PgCurrency)
   * @generated
   */
  void unsetInsuredBenefitOnDeath();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getInsuredBenefitOnDeath <em>Insured Benefit On Death</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Insured Benefit On Death</em>' attribute is set.
   * @see #unsetInsuredBenefitOnDeath()
   * @see #getInsuredBenefitOnDeath()
   * @see #setInsuredBenefitOnDeath(PgCurrency)
   * @generated
   */
	boolean isSetInsuredBenefitOnDeath();

	/**
   * Returns the value of the '<em><b>Investment Parts</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.finan.investmentinsurance.model.InvestmentPart}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Beleggingsdelen</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Investment Parts</em>' containment reference list.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentInsurance_InvestmentParts()
   * @model containment="true"
   * @generated
   */
	EList<InvestmentPart> getInvestmentParts();

	/**
   * Returns the value of the '<em><b>Events</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.types.model.Event}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Events</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Events</em>' containment reference list.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentInsurance_Events()
   * @model containment="true"
   * @generated
   */
	EList<Event> getEvents();

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @model kind="operation" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getLastKnownValue();

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @model kind="operation" dataType="goedegep.types.model.ELocalDate"
   * @generated
   */
	LocalDate getDateForLastKnownValue();

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model kind="operation"
   * @generated
   */
  AnnualStatement getLastAnnualStatement();

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @model kind="operation" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
	FixedPointValue getAverageReturnOnInvestment();

} // BeleggingsVerzekering
