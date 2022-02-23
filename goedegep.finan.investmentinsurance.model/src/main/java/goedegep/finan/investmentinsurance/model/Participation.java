/**
 */
package goedegep.finan.investmentinsurance.model;

import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Participatie</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.investmentinsurance.model.Participation#getInvestmentFund <em>Investment Fund</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.Participation#getParticipationMutations <em>Participation Mutations</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentNetHistoric <em>Example Return On Investment Net Historic</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentGross <em>Example Return On Investment Gross</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentGrossCompanyOwn <em>Example Return On Investment Gross Company Own</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentPessimistic <em>Example Return On Investment Pessimistic</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.Participation#getTotalExpenseRatio <em>Total Expense Ratio</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalNetHistoric <em>Example Capital Net Historic</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalGross <em>Example Capital Gross</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalGrossCompanyOwn <em>Example Capital Gross Company Own</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalPessimistic <em>Example Capital Pessimistic</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.Participation#getReturnOnInvestmentReductionNetHistoric <em>Return On Investment Reduction Net Historic</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalAfterReduction <em>Example Capital After Reduction</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.Participation#getDistributionPercentage <em>Distribution Percentage</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.Participation#getStandardFundReturnOnInvestment <em>Standard Fund Return On Investment</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalStandardFundReturnOnInvestment <em>Example Capital Standard Fund Return On Investment</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.Participation#getNumberOfParticipationsEnd <em>Number Of Participations End</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.Participation#isParticipationMutationsComplete <em>Participation Mutations Complete</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.Participation#getAnnualStatement <em>Annual Statement</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getParticipation()
 * @model
 * @generated
 */
public interface Participation extends EObject {
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
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getParticipation_InvestmentFund()
   * @model required="true"
   * @generated
   */
	InvestmentFund getInvestmentFund();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getInvestmentFund <em>Investment Fund</em>}' reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Investment Fund</em>' reference.
   * @see #getInvestmentFund()
   * @generated
   */
	void setInvestmentFund(InvestmentFund value);

	/**
   * Returns the value of the '<em><b>Participation Mutations</b></em>' attribute list.
   * The list contents are of type {@link goedegep.util.fixedpointvalue.FixedPointValue}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Participatie Mutaties</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Participation Mutations</em>' attribute list.
   * @see #isSetParticipationMutations()
   * @see #unsetParticipationMutations()
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getParticipation_ParticipationMutations()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
	EList<FixedPointValue> getParticipationMutations();

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getParticipationMutations <em>Participation Mutations</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetParticipationMutations()
   * @see #getParticipationMutations()
   * @generated
   */
  void unsetParticipationMutations();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getParticipationMutations <em>Participation Mutations</em>}' attribute list is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Participation Mutations</em>' attribute list is set.
   * @see #unsetParticipationMutations()
   * @see #getParticipationMutations()
   * @generated
   */
	boolean isSetParticipationMutations();

	/**
   * Returns the value of the '<em><b>Example Return On Investment Net Historic</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Voorbeeld Rendement Netto Historisch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Example Return On Investment Net Historic</em>' attribute.
   * @see #isSetExampleReturnOnInvestmentNetHistoric()
   * @see #unsetExampleReturnOnInvestmentNetHistoric()
   * @see #setExampleReturnOnInvestmentNetHistoric(FixedPointValue)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getParticipation_ExampleReturnOnInvestmentNetHistoric()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
	FixedPointValue getExampleReturnOnInvestmentNetHistoric();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentNetHistoric <em>Example Return On Investment Net Historic</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Example Return On Investment Net Historic</em>' attribute.
   * @see #isSetExampleReturnOnInvestmentNetHistoric()
   * @see #unsetExampleReturnOnInvestmentNetHistoric()
   * @see #getExampleReturnOnInvestmentNetHistoric()
   * @generated
   */
	void setExampleReturnOnInvestmentNetHistoric(FixedPointValue value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentNetHistoric <em>Example Return On Investment Net Historic</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetExampleReturnOnInvestmentNetHistoric()
   * @see #getExampleReturnOnInvestmentNetHistoric()
   * @see #setExampleReturnOnInvestmentNetHistoric(FixedPointValue)
   * @generated
   */
  void unsetExampleReturnOnInvestmentNetHistoric();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentNetHistoric <em>Example Return On Investment Net Historic</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Example Return On Investment Net Historic</em>' attribute is set.
   * @see #unsetExampleReturnOnInvestmentNetHistoric()
   * @see #getExampleReturnOnInvestmentNetHistoric()
   * @see #setExampleReturnOnInvestmentNetHistoric(FixedPointValue)
   * @generated
   */
  boolean isSetExampleReturnOnInvestmentNetHistoric();

  /**
   * Returns the value of the '<em><b>Example Return On Investment Gross</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Voorbeeld Rendement Bruto</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Example Return On Investment Gross</em>' attribute.
   * @see #isSetExampleReturnOnInvestmentGross()
   * @see #unsetExampleReturnOnInvestmentGross()
   * @see #setExampleReturnOnInvestmentGross(FixedPointValue)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getParticipation_ExampleReturnOnInvestmentGross()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
	FixedPointValue getExampleReturnOnInvestmentGross();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentGross <em>Example Return On Investment Gross</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Example Return On Investment Gross</em>' attribute.
   * @see #isSetExampleReturnOnInvestmentGross()
   * @see #unsetExampleReturnOnInvestmentGross()
   * @see #getExampleReturnOnInvestmentGross()
   * @generated
   */
	void setExampleReturnOnInvestmentGross(FixedPointValue value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentGross <em>Example Return On Investment Gross</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetExampleReturnOnInvestmentGross()
   * @see #getExampleReturnOnInvestmentGross()
   * @see #setExampleReturnOnInvestmentGross(FixedPointValue)
   * @generated
   */
  void unsetExampleReturnOnInvestmentGross();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentGross <em>Example Return On Investment Gross</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Example Return On Investment Gross</em>' attribute is set.
   * @see #unsetExampleReturnOnInvestmentGross()
   * @see #getExampleReturnOnInvestmentGross()
   * @see #setExampleReturnOnInvestmentGross(FixedPointValue)
   * @generated
   */
	boolean isSetExampleReturnOnInvestmentGross();

	/**
   * Returns the value of the '<em><b>Example Return On Investment Gross Company Own</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Voorbeeld Rendement Bruto Eigen</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Example Return On Investment Gross Company Own</em>' attribute.
   * @see #isSetExampleReturnOnInvestmentGrossCompanyOwn()
   * @see #unsetExampleReturnOnInvestmentGrossCompanyOwn()
   * @see #setExampleReturnOnInvestmentGrossCompanyOwn(FixedPointValue)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getParticipation_ExampleReturnOnInvestmentGrossCompanyOwn()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
	FixedPointValue getExampleReturnOnInvestmentGrossCompanyOwn();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentGrossCompanyOwn <em>Example Return On Investment Gross Company Own</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Example Return On Investment Gross Company Own</em>' attribute.
   * @see #isSetExampleReturnOnInvestmentGrossCompanyOwn()
   * @see #unsetExampleReturnOnInvestmentGrossCompanyOwn()
   * @see #getExampleReturnOnInvestmentGrossCompanyOwn()
   * @generated
   */
	void setExampleReturnOnInvestmentGrossCompanyOwn(FixedPointValue value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentGrossCompanyOwn <em>Example Return On Investment Gross Company Own</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetExampleReturnOnInvestmentGrossCompanyOwn()
   * @see #getExampleReturnOnInvestmentGrossCompanyOwn()
   * @see #setExampleReturnOnInvestmentGrossCompanyOwn(FixedPointValue)
   * @generated
   */
  void unsetExampleReturnOnInvestmentGrossCompanyOwn();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentGrossCompanyOwn <em>Example Return On Investment Gross Company Own</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Example Return On Investment Gross Company Own</em>' attribute is set.
   * @see #unsetExampleReturnOnInvestmentGrossCompanyOwn()
   * @see #getExampleReturnOnInvestmentGrossCompanyOwn()
   * @see #setExampleReturnOnInvestmentGrossCompanyOwn(FixedPointValue)
   * @generated
   */
	boolean isSetExampleReturnOnInvestmentGrossCompanyOwn();

	/**
   * Returns the value of the '<em><b>Example Return On Investment Pessimistic</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Voorbeeld Rendement Pessimistisch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Example Return On Investment Pessimistic</em>' attribute.
   * @see #isSetExampleReturnOnInvestmentPessimistic()
   * @see #unsetExampleReturnOnInvestmentPessimistic()
   * @see #setExampleReturnOnInvestmentPessimistic(FixedPointValue)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getParticipation_ExampleReturnOnInvestmentPessimistic()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
	FixedPointValue getExampleReturnOnInvestmentPessimistic();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentPessimistic <em>Example Return On Investment Pessimistic</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Example Return On Investment Pessimistic</em>' attribute.
   * @see #isSetExampleReturnOnInvestmentPessimistic()
   * @see #unsetExampleReturnOnInvestmentPessimistic()
   * @see #getExampleReturnOnInvestmentPessimistic()
   * @generated
   */
	void setExampleReturnOnInvestmentPessimistic(FixedPointValue value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentPessimistic <em>Example Return On Investment Pessimistic</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetExampleReturnOnInvestmentPessimistic()
   * @see #getExampleReturnOnInvestmentPessimistic()
   * @see #setExampleReturnOnInvestmentPessimistic(FixedPointValue)
   * @generated
   */
  void unsetExampleReturnOnInvestmentPessimistic();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentPessimistic <em>Example Return On Investment Pessimistic</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Example Return On Investment Pessimistic</em>' attribute is set.
   * @see #unsetExampleReturnOnInvestmentPessimistic()
   * @see #getExampleReturnOnInvestmentPessimistic()
   * @see #setExampleReturnOnInvestmentPessimistic(FixedPointValue)
   * @generated
   */
	boolean isSetExampleReturnOnInvestmentPessimistic();

	/**
   * Returns the value of the '<em><b>Total Expense Ratio</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total Expense Ratio</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Total Expense Ratio</em>' attribute.
   * @see #isSetTotalExpenseRatio()
   * @see #unsetTotalExpenseRatio()
   * @see #setTotalExpenseRatio(FixedPointValue)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getParticipation_TotalExpenseRatio()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
	FixedPointValue getTotalExpenseRatio();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getTotalExpenseRatio <em>Total Expense Ratio</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Total Expense Ratio</em>' attribute.
   * @see #isSetTotalExpenseRatio()
   * @see #unsetTotalExpenseRatio()
   * @see #getTotalExpenseRatio()
   * @generated
   */
	void setTotalExpenseRatio(FixedPointValue value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getTotalExpenseRatio <em>Total Expense Ratio</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #isSetTotalExpenseRatio()
   * @see #getTotalExpenseRatio()
   * @see #setTotalExpenseRatio(FixedPointValue)
   * @generated
   */
	void unsetTotalExpenseRatio();

	/**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getTotalExpenseRatio <em>Total Expense Ratio</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Total Expense Ratio</em>' attribute is set.
   * @see #unsetTotalExpenseRatio()
   * @see #getTotalExpenseRatio()
   * @see #setTotalExpenseRatio(FixedPointValue)
   * @generated
   */
	boolean isSetTotalExpenseRatio();

	/**
   * Returns the value of the '<em><b>Example Capital Net Historic</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Voorbeeld Kapitaal Netto Historisch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Example Capital Net Historic</em>' attribute.
   * @see #isSetExampleCapitalNetHistoric()
   * @see #unsetExampleCapitalNetHistoric()
   * @see #setExampleCapitalNetHistoric(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getParticipation_ExampleCapitalNetHistoric()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getExampleCapitalNetHistoric();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalNetHistoric <em>Example Capital Net Historic</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Example Capital Net Historic</em>' attribute.
   * @see #isSetExampleCapitalNetHistoric()
   * @see #unsetExampleCapitalNetHistoric()
   * @see #getExampleCapitalNetHistoric()
   * @generated
   */
	void setExampleCapitalNetHistoric(PgCurrency value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalNetHistoric <em>Example Capital Net Historic</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetExampleCapitalNetHistoric()
   * @see #getExampleCapitalNetHistoric()
   * @see #setExampleCapitalNetHistoric(PgCurrency)
   * @generated
   */
  void unsetExampleCapitalNetHistoric();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalNetHistoric <em>Example Capital Net Historic</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Example Capital Net Historic</em>' attribute is set.
   * @see #unsetExampleCapitalNetHistoric()
   * @see #getExampleCapitalNetHistoric()
   * @see #setExampleCapitalNetHistoric(PgCurrency)
   * @generated
   */
	boolean isSetExampleCapitalNetHistoric();

	/**
   * Returns the value of the '<em><b>Example Capital Gross</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Voorbeeld Kapitaal Bruto</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Example Capital Gross</em>' attribute.
   * @see #isSetExampleCapitalGross()
   * @see #unsetExampleCapitalGross()
   * @see #setExampleCapitalGross(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getParticipation_ExampleCapitalGross()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getExampleCapitalGross();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalGross <em>Example Capital Gross</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Example Capital Gross</em>' attribute.
   * @see #isSetExampleCapitalGross()
   * @see #unsetExampleCapitalGross()
   * @see #getExampleCapitalGross()
   * @generated
   */
	void setExampleCapitalGross(PgCurrency value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalGross <em>Example Capital Gross</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetExampleCapitalGross()
   * @see #getExampleCapitalGross()
   * @see #setExampleCapitalGross(PgCurrency)
   * @generated
   */
  void unsetExampleCapitalGross();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalGross <em>Example Capital Gross</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Example Capital Gross</em>' attribute is set.
   * @see #unsetExampleCapitalGross()
   * @see #getExampleCapitalGross()
   * @see #setExampleCapitalGross(PgCurrency)
   * @generated
   */
	boolean isSetExampleCapitalGross();

	/**
   * Returns the value of the '<em><b>Example Capital Gross Company Own</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Voorbeeld Kapitaal Bruto Eigen</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Example Capital Gross Company Own</em>' attribute.
   * @see #isSetExampleCapitalGrossCompanyOwn()
   * @see #unsetExampleCapitalGrossCompanyOwn()
   * @see #setExampleCapitalGrossCompanyOwn(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getParticipation_ExampleCapitalGrossCompanyOwn()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getExampleCapitalGrossCompanyOwn();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalGrossCompanyOwn <em>Example Capital Gross Company Own</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Example Capital Gross Company Own</em>' attribute.
   * @see #isSetExampleCapitalGrossCompanyOwn()
   * @see #unsetExampleCapitalGrossCompanyOwn()
   * @see #getExampleCapitalGrossCompanyOwn()
   * @generated
   */
	void setExampleCapitalGrossCompanyOwn(PgCurrency value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalGrossCompanyOwn <em>Example Capital Gross Company Own</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetExampleCapitalGrossCompanyOwn()
   * @see #getExampleCapitalGrossCompanyOwn()
   * @see #setExampleCapitalGrossCompanyOwn(PgCurrency)
   * @generated
   */
  void unsetExampleCapitalGrossCompanyOwn();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalGrossCompanyOwn <em>Example Capital Gross Company Own</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Example Capital Gross Company Own</em>' attribute is set.
   * @see #unsetExampleCapitalGrossCompanyOwn()
   * @see #getExampleCapitalGrossCompanyOwn()
   * @see #setExampleCapitalGrossCompanyOwn(PgCurrency)
   * @generated
   */
	boolean isSetExampleCapitalGrossCompanyOwn();

	/**
   * Returns the value of the '<em><b>Example Capital Pessimistic</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Voorbeeld Kapitaal Pessimistisch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Example Capital Pessimistic</em>' attribute.
   * @see #isSetExampleCapitalPessimistic()
   * @see #unsetExampleCapitalPessimistic()
   * @see #setExampleCapitalPessimistic(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getParticipation_ExampleCapitalPessimistic()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getExampleCapitalPessimistic();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalPessimistic <em>Example Capital Pessimistic</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Example Capital Pessimistic</em>' attribute.
   * @see #isSetExampleCapitalPessimistic()
   * @see #unsetExampleCapitalPessimistic()
   * @see #getExampleCapitalPessimistic()
   * @generated
   */
	void setExampleCapitalPessimistic(PgCurrency value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalPessimistic <em>Example Capital Pessimistic</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetExampleCapitalPessimistic()
   * @see #getExampleCapitalPessimistic()
   * @see #setExampleCapitalPessimistic(PgCurrency)
   * @generated
   */
  void unsetExampleCapitalPessimistic();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalPessimistic <em>Example Capital Pessimistic</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Example Capital Pessimistic</em>' attribute is set.
   * @see #unsetExampleCapitalPessimistic()
   * @see #getExampleCapitalPessimistic()
   * @see #setExampleCapitalPessimistic(PgCurrency)
   * @generated
   */
	boolean isSetExampleCapitalPessimistic();

	/**
   * Returns the value of the '<em><b>Return On Investment Reduction Net Historic</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Afslag Rendement Netto Historisch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Return On Investment Reduction Net Historic</em>' attribute.
   * @see #isSetReturnOnInvestmentReductionNetHistoric()
   * @see #unsetReturnOnInvestmentReductionNetHistoric()
   * @see #setReturnOnInvestmentReductionNetHistoric(FixedPointValue)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getParticipation_ReturnOnInvestmentReductionNetHistoric()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
	FixedPointValue getReturnOnInvestmentReductionNetHistoric();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getReturnOnInvestmentReductionNetHistoric <em>Return On Investment Reduction Net Historic</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Return On Investment Reduction Net Historic</em>' attribute.
   * @see #isSetReturnOnInvestmentReductionNetHistoric()
   * @see #unsetReturnOnInvestmentReductionNetHistoric()
   * @see #getReturnOnInvestmentReductionNetHistoric()
   * @generated
   */
	void setReturnOnInvestmentReductionNetHistoric(FixedPointValue value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getReturnOnInvestmentReductionNetHistoric <em>Return On Investment Reduction Net Historic</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetReturnOnInvestmentReductionNetHistoric()
   * @see #getReturnOnInvestmentReductionNetHistoric()
   * @see #setReturnOnInvestmentReductionNetHistoric(FixedPointValue)
   * @generated
   */
  void unsetReturnOnInvestmentReductionNetHistoric();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getReturnOnInvestmentReductionNetHistoric <em>Return On Investment Reduction Net Historic</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Return On Investment Reduction Net Historic</em>' attribute is set.
   * @see #unsetReturnOnInvestmentReductionNetHistoric()
   * @see #getReturnOnInvestmentReductionNetHistoric()
   * @see #setReturnOnInvestmentReductionNetHistoric(FixedPointValue)
   * @generated
   */
	boolean isSetReturnOnInvestmentReductionNetHistoric();

	/**
   * Returns the value of the '<em><b>Example Capital After Reduction</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Voorbeeld Kapitaal Netto Historisch Na Afslag</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Example Capital After Reduction</em>' attribute.
   * @see #isSetExampleCapitalAfterReduction()
   * @see #unsetExampleCapitalAfterReduction()
   * @see #setExampleCapitalAfterReduction(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getParticipation_ExampleCapitalAfterReduction()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getExampleCapitalAfterReduction();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalAfterReduction <em>Example Capital After Reduction</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Example Capital After Reduction</em>' attribute.
   * @see #isSetExampleCapitalAfterReduction()
   * @see #unsetExampleCapitalAfterReduction()
   * @see #getExampleCapitalAfterReduction()
   * @generated
   */
	void setExampleCapitalAfterReduction(PgCurrency value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalAfterReduction <em>Example Capital After Reduction</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetExampleCapitalAfterReduction()
   * @see #getExampleCapitalAfterReduction()
   * @see #setExampleCapitalAfterReduction(PgCurrency)
   * @generated
   */
  void unsetExampleCapitalAfterReduction();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalAfterReduction <em>Example Capital After Reduction</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Example Capital After Reduction</em>' attribute is set.
   * @see #unsetExampleCapitalAfterReduction()
   * @see #getExampleCapitalAfterReduction()
   * @see #setExampleCapitalAfterReduction(PgCurrency)
   * @generated
   */
	boolean isSetExampleCapitalAfterReduction();

	/**
   * Returns the value of the '<em><b>Distribution Percentage</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Verdelings Percentage</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Distribution Percentage</em>' attribute.
   * @see #isSetDistributionPercentage()
   * @see #unsetDistributionPercentage()
   * @see #setDistributionPercentage(FixedPointValue)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getParticipation_DistributionPercentage()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
	FixedPointValue getDistributionPercentage();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getDistributionPercentage <em>Distribution Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Distribution Percentage</em>' attribute.
   * @see #isSetDistributionPercentage()
   * @see #unsetDistributionPercentage()
   * @see #getDistributionPercentage()
   * @generated
   */
	void setDistributionPercentage(FixedPointValue value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getDistributionPercentage <em>Distribution Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDistributionPercentage()
   * @see #getDistributionPercentage()
   * @see #setDistributionPercentage(FixedPointValue)
   * @generated
   */
  void unsetDistributionPercentage();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getDistributionPercentage <em>Distribution Percentage</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Distribution Percentage</em>' attribute is set.
   * @see #unsetDistributionPercentage()
   * @see #getDistributionPercentage()
   * @see #setDistributionPercentage(FixedPointValue)
   * @generated
   */
	boolean isSetDistributionPercentage();

	/**
   * Returns the value of the '<em><b>Standard Fund Return On Investment</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Standaard Fonds Rendement</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Standard Fund Return On Investment</em>' attribute.
   * @see #isSetStandardFundReturnOnInvestment()
   * @see #unsetStandardFundReturnOnInvestment()
   * @see #setStandardFundReturnOnInvestment(FixedPointValue)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getParticipation_StandardFundReturnOnInvestment()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
	FixedPointValue getStandardFundReturnOnInvestment();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getStandardFundReturnOnInvestment <em>Standard Fund Return On Investment</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Standard Fund Return On Investment</em>' attribute.
   * @see #isSetStandardFundReturnOnInvestment()
   * @see #unsetStandardFundReturnOnInvestment()
   * @see #getStandardFundReturnOnInvestment()
   * @generated
   */
	void setStandardFundReturnOnInvestment(FixedPointValue value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getStandardFundReturnOnInvestment <em>Standard Fund Return On Investment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetStandardFundReturnOnInvestment()
   * @see #getStandardFundReturnOnInvestment()
   * @see #setStandardFundReturnOnInvestment(FixedPointValue)
   * @generated
   */
  void unsetStandardFundReturnOnInvestment();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getStandardFundReturnOnInvestment <em>Standard Fund Return On Investment</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Standard Fund Return On Investment</em>' attribute is set.
   * @see #unsetStandardFundReturnOnInvestment()
   * @see #getStandardFundReturnOnInvestment()
   * @see #setStandardFundReturnOnInvestment(FixedPointValue)
   * @generated
   */
	boolean isSetStandardFundReturnOnInvestment();

	/**
   * Returns the value of the '<em><b>Example Capital Standard Fund Return On Investment</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Voorbeeld Kapitaal Standaard Fonds Rendement</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Example Capital Standard Fund Return On Investment</em>' attribute.
   * @see #isSetExampleCapitalStandardFundReturnOnInvestment()
   * @see #unsetExampleCapitalStandardFundReturnOnInvestment()
   * @see #setExampleCapitalStandardFundReturnOnInvestment(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getParticipation_ExampleCapitalStandardFundReturnOnInvestment()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getExampleCapitalStandardFundReturnOnInvestment();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalStandardFundReturnOnInvestment <em>Example Capital Standard Fund Return On Investment</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Example Capital Standard Fund Return On Investment</em>' attribute.
   * @see #isSetExampleCapitalStandardFundReturnOnInvestment()
   * @see #unsetExampleCapitalStandardFundReturnOnInvestment()
   * @see #getExampleCapitalStandardFundReturnOnInvestment()
   * @generated
   */
	void setExampleCapitalStandardFundReturnOnInvestment(PgCurrency value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalStandardFundReturnOnInvestment <em>Example Capital Standard Fund Return On Investment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetExampleCapitalStandardFundReturnOnInvestment()
   * @see #getExampleCapitalStandardFundReturnOnInvestment()
   * @see #setExampleCapitalStandardFundReturnOnInvestment(PgCurrency)
   * @generated
   */
  void unsetExampleCapitalStandardFundReturnOnInvestment();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalStandardFundReturnOnInvestment <em>Example Capital Standard Fund Return On Investment</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Example Capital Standard Fund Return On Investment</em>' attribute is set.
   * @see #unsetExampleCapitalStandardFundReturnOnInvestment()
   * @see #getExampleCapitalStandardFundReturnOnInvestment()
   * @see #setExampleCapitalStandardFundReturnOnInvestment(PgCurrency)
   * @generated
   */
	boolean isSetExampleCapitalStandardFundReturnOnInvestment();

	/**
   * Returns the value of the '<em><b>Number Of Participations End</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Aantal Participaties Eind</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Number Of Participations End</em>' attribute.
   * @see #isSetNumberOfParticipationsEnd()
   * @see #unsetNumberOfParticipationsEnd()
   * @see #setNumberOfParticipationsEnd(FixedPointValue)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getParticipation_NumberOfParticipationsEnd()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
	FixedPointValue getNumberOfParticipationsEnd();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getNumberOfParticipationsEnd <em>Number Of Participations End</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Number Of Participations End</em>' attribute.
   * @see #isSetNumberOfParticipationsEnd()
   * @see #unsetNumberOfParticipationsEnd()
   * @see #getNumberOfParticipationsEnd()
   * @generated
   */
	void setNumberOfParticipationsEnd(FixedPointValue value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getNumberOfParticipationsEnd <em>Number Of Participations End</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetNumberOfParticipationsEnd()
   * @see #getNumberOfParticipationsEnd()
   * @see #setNumberOfParticipationsEnd(FixedPointValue)
   * @generated
   */
  void unsetNumberOfParticipationsEnd();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getNumberOfParticipationsEnd <em>Number Of Participations End</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Number Of Participations End</em>' attribute is set.
   * @see #unsetNumberOfParticipationsEnd()
   * @see #getNumberOfParticipationsEnd()
   * @see #setNumberOfParticipationsEnd(FixedPointValue)
   * @generated
   */
	boolean isSetNumberOfParticipationsEnd();

	/**
   * Returns the value of the '<em><b>Participation Mutations Complete</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Participatie Mutaties Volledig</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Participation Mutations Complete</em>' attribute.
   * @see #setParticipationMutationsComplete(boolean)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getParticipation_ParticipationMutationsComplete()
   * @model required="true"
   * @generated
   */
	boolean isParticipationMutationsComplete();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#isParticipationMutationsComplete <em>Participation Mutations Complete</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Participation Mutations Complete</em>' attribute.
   * @see #isParticipationMutationsComplete()
   * @generated
   */
	void setParticipationMutationsComplete(boolean value);

	/**
   * Returns the value of the '<em><b>Annual Statement</b></em>' reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Jaar Opgave</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Annual Statement</em>' reference.
   * @see #setAnnualStatement(AnnualStatement)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getParticipation_AnnualStatement()
   * @model required="true"
   * @generated
   */
	AnnualStatement getAnnualStatement();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.Participation#getAnnualStatement <em>Annual Statement</em>}' reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Annual Statement</em>' reference.
   * @see #getAnnualStatement()
   * @generated
   */
	void setAnnualStatement(AnnualStatement value);

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @model dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
	FixedPointValue numberOfParticipationsStart();

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @model dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency endValue();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
  FixedPointValue numberOfParticipationsBought();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
  FixedPointValue numberOfParticipationsSold();

} // Participatie
