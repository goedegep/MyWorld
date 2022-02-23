/**
 */
package goedegep.finan.investmentinsurance.model;

import goedegep.types.model.Event;
import goedegep.util.money.PgCurrency;

import java.time.LocalDate;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Extra Inleg</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.investmentinsurance.model.ExtraInvestment#getPremium <em>Premium</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.ExtraInvestment#getDepositDate <em>Deposit Date</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.ExtraInvestment#getInvestmentParts <em>Investment Parts</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.ExtraInvestment#getOverviewDate <em>Overview Date</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.ExtraInvestment#getParticipations <em>Participations</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getExtraInvestment()
 * @model
 * @generated
 */
public interface ExtraInvestment extends Event {
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
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getExtraInvestment_Premium()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getPremium();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.ExtraInvestment#getPremium <em>Premium</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.ExtraInvestment#getPremium <em>Premium</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetPremium()
   * @see #getPremium()
   * @see #setPremium(PgCurrency)
   * @generated
   */
  void unsetPremium();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.ExtraInvestment#getPremium <em>Premium</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Deposit Date</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inleg Datum</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Deposit Date</em>' attribute.
   * @see #isSetDepositDate()
   * @see #unsetDepositDate()
   * @see #setDepositDate(LocalDate)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getExtraInvestment_DepositDate()
   * @model unsettable="true" dataType="goedegep.types.model.ELocalDate"
   * @generated
   */
	LocalDate getDepositDate();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.ExtraInvestment#getDepositDate <em>Deposit Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Deposit Date</em>' attribute.
   * @see #isSetDepositDate()
   * @see #unsetDepositDate()
   * @see #getDepositDate()
   * @generated
   */
  void setDepositDate(LocalDate value);

  /**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.ExtraInvestment#getDepositDate <em>Deposit Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDepositDate()
   * @see #getDepositDate()
   * @see #setDepositDate(LocalDate)
   * @generated
   */
  void unsetDepositDate();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.ExtraInvestment#getDepositDate <em>Deposit Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Deposit Date</em>' attribute is set.
   * @see #unsetDepositDate()
   * @see #getDepositDate()
   * @see #setDepositDate(LocalDate)
   * @generated
   */
	boolean isSetDepositDate();

	/**
   * Returns the value of the '<em><b>Investment Parts</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.finan.investmentinsurance.model.InvestmentPart}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Investment Parts</em>' containment reference list.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getExtraInvestment_InvestmentParts()
   * @model containment="true"
   * @generated
   */
  EList<InvestmentPart> getInvestmentParts();

  /**
   * Returns the value of the '<em><b>Overview Date</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Overzicht Datum</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Overview Date</em>' attribute.
   * @see #isSetOverviewDate()
   * @see #unsetOverviewDate()
   * @see #setOverviewDate(LocalDate)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getExtraInvestment_OverviewDate()
   * @model unsettable="true" dataType="goedegep.types.model.ELocalDate"
   * @generated
   */
	LocalDate getOverviewDate();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.ExtraInvestment#getOverviewDate <em>Overview Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Overview Date</em>' attribute.
   * @see #isSetOverviewDate()
   * @see #unsetOverviewDate()
   * @see #getOverviewDate()
   * @generated
   */
  void setOverviewDate(LocalDate value);

  /**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.ExtraInvestment#getOverviewDate <em>Overview Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetOverviewDate()
   * @see #getOverviewDate()
   * @see #setOverviewDate(LocalDate)
   * @generated
   */
  void unsetOverviewDate();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.ExtraInvestment#getOverviewDate <em>Overview Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Overview Date</em>' attribute is set.
   * @see #unsetOverviewDate()
   * @see #getOverviewDate()
   * @see #setOverviewDate(LocalDate)
   * @generated
   */
	boolean isSetOverviewDate();

	/**
   * Returns the value of the '<em><b>Participations</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.finan.investmentinsurance.model.Participation}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Participations</em>' containment reference list.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getExtraInvestment_Participations()
   * @model containment="true"
   * @generated
   */
  EList<Participation> getParticipations();

} // ExtraInleg
