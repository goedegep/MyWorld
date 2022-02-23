/**
 */
package goedegep.finan.investmentinsurance.model;

import goedegep.types.model.DateRateTuplet;
import goedegep.util.money.PgCurrency;

import java.time.LocalDate;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Beleggings Fonds</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InvestmentFund#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InvestmentFund#getFundInformation <em>Fund Information</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.InvestmentFund#getStockPrices <em>Stock Prices</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentFund()
 * @model
 * @generated
 */
public interface InvestmentFund extends EObject {
	/**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Naam</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #isSetName()
   * @see #unsetName()
   * @see #setName(String)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentFund_Name()
   * @model unsettable="true"
   * @generated
   */
	String getName();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentFund#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #isSetName()
   * @see #unsetName()
   * @see #getName()
   * @generated
   */
	void setName(String value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentFund#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetName()
   * @see #getName()
   * @see #setName(String)
   * @generated
   */
  void unsetName();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentFund#getName <em>Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Name</em>' attribute is set.
   * @see #unsetName()
   * @see #getName()
   * @see #setName(String)
   * @generated
   */
  boolean isSetName();

  /**
   * Returns the value of the '<em><b>Stock Prices</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.types.model.DateRateTuplet}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Koersen</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Stock Prices</em>' containment reference list.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentFund_StockPrices()
   * @model containment="true"
   * @generated
   */
	EList<DateRateTuplet> getStockPrices();

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model dataType="goedegep.types.model.EMoney" dateDataType="goedegep.types.model.ELocalDate"
   * @generated
   */
  PgCurrency getStockPrice(LocalDate date);

  /**
   * Returns the value of the '<em><b>Fund Information</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fonds Informatie</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Fund Information</em>' attribute.
   * @see #isSetFundInformation()
   * @see #unsetFundInformation()
   * @see #setFundInformation(String)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getInvestmentFund_FundInformation()
   * @model unsettable="true"
   * @generated
   */
	String getFundInformation();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentFund#getFundInformation <em>Fund Information</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Fund Information</em>' attribute.
   * @see #isSetFundInformation()
   * @see #unsetFundInformation()
   * @see #getFundInformation()
   * @generated
   */
	void setFundInformation(String value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentFund#getFundInformation <em>Fund Information</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetFundInformation()
   * @see #getFundInformation()
   * @see #setFundInformation(String)
   * @generated
   */
  void unsetFundInformation();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.InvestmentFund#getFundInformation <em>Fund Information</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Fund Information</em>' attribute is set.
   * @see #unsetFundInformation()
   * @see #getFundInformation()
   * @see #setFundInformation(String)
   * @generated
   */
	boolean isSetFundInformation();

}
