/**
 */
package goedegep.finan.investmentinsurance.model;

import goedegep.rolodex.model.AddressList;
import goedegep.rolodex.model.CityList;
import goedegep.rolodex.model.PersonList;
import goedegep.rolodex.model.PhoneNumberList;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;
import java.time.LocalDate;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Beleggings Verzekeringen Data</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getInsuranceCompanies <em>Insurance Companies</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getInvestmentInsurances <em>Investment Insurances</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getPersonList <em>Person List</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getAddressList <em>Address List</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getCityList <em>City List</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getPhoneNumberList <em>Phone Number List</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentInsurancesData()
 * @model
 * @generated
 */
public interface InvestmentInsurancesData extends EObject {
	/**
   * Returns the value of the '<em><b>Insurance Companies</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.finan.investmentinsurance.model.InsuranceCompany}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Maatschappijen</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Insurance Companies</em>' containment reference list.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentInsurancesData_InsuranceCompanies()
   * @model containment="true"
   * @generated
   */
	EList<InsuranceCompany> getInsuranceCompanies();

	/**
   * Returns the value of the '<em><b>Investment Insurances</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.finan.investmentinsurance.model.InvestmentInsurance}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Beleggingsverzekeringen</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Investment Insurances</em>' containment reference list.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentInsurancesData_InvestmentInsurances()
   * @model containment="true"
   * @generated
   */
	EList<InvestmentInsurance> getInvestmentInsurances();

	/**
   * Returns the value of the '<em><b>Person List</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Personen Lijst</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Person List</em>' containment reference.
   * @see #isSetPersonList()
   * @see #unsetPersonList()
   * @see #setPersonList(PersonList)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentInsurancesData_PersonList()
   * @model containment="true" unsettable="true" required="true"
   * @generated
   */
	PersonList getPersonList();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getPersonList <em>Person List</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Person List</em>' containment reference.
   * @see #isSetPersonList()
   * @see #unsetPersonList()
   * @see #getPersonList()
   * @generated
   */
  void setPersonList(PersonList value);

  /**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getPersonList <em>Person List</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetPersonList()
   * @see #getPersonList()
   * @see #setPersonList(PersonList)
   * @generated
   */
  void unsetPersonList();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getPersonList <em>Person List</em>}' containment reference is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Person List</em>' containment reference is set.
   * @see #unsetPersonList()
   * @see #getPersonList()
   * @see #setPersonList(PersonList)
   * @generated
   */
	boolean isSetPersonList();

	/**
   * Returns the value of the '<em><b>Address List</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Adres Lijst</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Address List</em>' containment reference.
   * @see #setAddressList(AddressList)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentInsurancesData_AddressList()
   * @model containment="true"
   * @generated
   */
	AddressList getAddressList();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getAddressList <em>Address List</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Address List</em>' containment reference.
   * @see #getAddressList()
   * @generated
   */
  void setAddressList(AddressList value);

  /**
   * Returns the value of the '<em><b>City List</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>City List</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>City List</em>' containment reference.
   * @see #setCityList(CityList)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentInsurancesData_CityList()
   * @model containment="true"
   * @generated
   */
	CityList getCityList();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getCityList <em>City List</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>City List</em>' containment reference.
   * @see #getCityList()
   * @generated
   */
  void setCityList(CityList value);

  /**
   * Returns the value of the '<em><b>Phone Number List</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Phone Number List</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Phone Number List</em>' containment reference.
   * @see #setPhoneNumberList(PhoneNumberList)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentInsurancesData_PhoneNumberList()
   * @model containment="true"
   * @generated
   */
	PhoneNumberList getPhoneNumberList();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getPhoneNumberList <em>Phone Number List</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Phone Number List</em>' containment reference.
   * @see #getPhoneNumberList()
   * @generated
   */
  void setPhoneNumberList(PhoneNumberList value);

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @model kind="operation" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getLastKnownTotalValue();

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @model kind="operation" dataType="goedegep.types.model.ELocalDate"
   * @generated
   */
	LocalDate getDateForLastKnownTotalValue();

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @model kind="operation" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getTotalPremium();

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model kind="operation" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
  FixedPointValue getAverageTotalReturnOnInvestment();

} // BeleggingsVerzekeringenData
