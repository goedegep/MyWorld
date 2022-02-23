/**
 */
package goedegep.finan.investmentinsurance.model;

import goedegep.types.model.Event;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;

import java.time.LocalDate;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Jaar Opgave</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPeriodFrom <em>Period From</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPeriodUntil <em>Period Until</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPremiumPaid <em>Premium Paid</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPremiumDeathRiskCoverage <em>Premium Death Risk Coverage</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPremiumOccupationalDisabilityRiskCoverage <em>Premium Occupational Disability Risk Coverage</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getUpkeepPremium <em>Upkeep Premium</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getFirstCostsInsuranceCompany <em>First Costs Insurance Company</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getContinuingCostsInsuranceCompany <em>Continuing Costs Insurance Company</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getManagementCosts <em>Management Costs</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getBuyAndSellCosts <em>Buy And Sell Costs</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getMutationCosts <em>Mutation Costs</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getCostsRestitution <em>Costs Restitution</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getCorrections <em>Corrections</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getEarnedOnTheParticipations <em>Earned On The Participations</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getParticipations <em>Participations</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getExampleCapitalOnEndDate <em>Example Capital On End Date</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getExpectedYearlyCostsIncrease <em>Expected Yearly Costs Increase</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getAnnualStatement()
 * @model
 * @generated
 */
public interface AnnualStatement extends Event {
	/**
   * Returns the value of the '<em><b>Period From</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Periode Van</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Period From</em>' attribute.
   * @see #isSetPeriodFrom()
   * @see #unsetPeriodFrom()
   * @see #setPeriodFrom(LocalDate)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getAnnualStatement_PeriodFrom()
   * @model unsettable="true" dataType="goedegep.types.model.ELocalDate"
   * @generated
   */
	LocalDate getPeriodFrom();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPeriodFrom <em>Period From</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Period From</em>' attribute.
   * @see #isSetPeriodFrom()
   * @see #unsetPeriodFrom()
   * @see #getPeriodFrom()
   * @generated
   */
  void setPeriodFrom(LocalDate value);

  /**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPeriodFrom <em>Period From</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #isSetPeriodFrom()
   * @see #getPeriodFrom()
   * @see #setPeriodFrom(LocalDate)
   * @generated
   */
	void unsetPeriodFrom();

	/**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPeriodFrom <em>Period From</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Period From</em>' attribute is set.
   * @see #unsetPeriodFrom()
   * @see #getPeriodFrom()
   * @see #setPeriodFrom(LocalDate)
   * @generated
   */
	boolean isSetPeriodFrom();

	/**
   * Returns the value of the '<em><b>Period Until</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Periode Tot</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Period Until</em>' attribute.
   * @see #isSetPeriodUntil()
   * @see #unsetPeriodUntil()
   * @see #setPeriodUntil(LocalDate)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getAnnualStatement_PeriodUntil()
   * @model unsettable="true" dataType="goedegep.types.model.ELocalDate"
   * @generated
   */
	LocalDate getPeriodUntil();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPeriodUntil <em>Period Until</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Period Until</em>' attribute.
   * @see #isSetPeriodUntil()
   * @see #unsetPeriodUntil()
   * @see #getPeriodUntil()
   * @generated
   */
  void setPeriodUntil(LocalDate value);

  /**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPeriodUntil <em>Period Until</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #isSetPeriodUntil()
   * @see #getPeriodUntil()
   * @see #setPeriodUntil(LocalDate)
   * @generated
   */
	void unsetPeriodUntil();

	/**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPeriodUntil <em>Period Until</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Period Until</em>' attribute is set.
   * @see #unsetPeriodUntil()
   * @see #getPeriodUntil()
   * @see #setPeriodUntil(LocalDate)
   * @generated
   */
	boolean isSetPeriodUntil();

	/**
   * Returns the value of the '<em><b>Premium Paid</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Betaalde Premie</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Premium Paid</em>' attribute.
   * @see #isSetPremiumPaid()
   * @see #unsetPremiumPaid()
   * @see #setPremiumPaid(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getAnnualStatement_PremiumPaid()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getPremiumPaid();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPremiumPaid <em>Premium Paid</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Premium Paid</em>' attribute.
   * @see #isSetPremiumPaid()
   * @see #unsetPremiumPaid()
   * @see #getPremiumPaid()
   * @generated
   */
  void setPremiumPaid(PgCurrency value);

  /**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPremiumPaid <em>Premium Paid</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetPremiumPaid()
   * @see #getPremiumPaid()
   * @see #setPremiumPaid(PgCurrency)
   * @generated
   */
  void unsetPremiumPaid();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPremiumPaid <em>Premium Paid</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Premium Paid</em>' attribute is set.
   * @see #unsetPremiumPaid()
   * @see #getPremiumPaid()
   * @see #setPremiumPaid(PgCurrency)
   * @generated
   */
  boolean isSetPremiumPaid();

  /**
   * Returns the value of the '<em><b>Premium Death Risk Coverage</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Premium Death Risk Coverage</em>' attribute.
   * @see #isSetPremiumDeathRiskCoverage()
   * @see #unsetPremiumDeathRiskCoverage()
   * @see #setPremiumDeathRiskCoverage(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getAnnualStatement_PremiumDeathRiskCoverage()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getPremiumDeathRiskCoverage();

  /**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPremiumDeathRiskCoverage <em>Premium Death Risk Coverage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Premium Death Risk Coverage</em>' attribute.
   * @see #isSetPremiumDeathRiskCoverage()
   * @see #unsetPremiumDeathRiskCoverage()
   * @see #getPremiumDeathRiskCoverage()
   * @generated
   */
  void setPremiumDeathRiskCoverage(PgCurrency value);

  /**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPremiumDeathRiskCoverage <em>Premium Death Risk Coverage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetPremiumDeathRiskCoverage()
   * @see #getPremiumDeathRiskCoverage()
   * @see #setPremiumDeathRiskCoverage(PgCurrency)
   * @generated
   */
  void unsetPremiumDeathRiskCoverage();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPremiumDeathRiskCoverage <em>Premium Death Risk Coverage</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Premium Death Risk Coverage</em>' attribute is set.
   * @see #unsetPremiumDeathRiskCoverage()
   * @see #getPremiumDeathRiskCoverage()
   * @see #setPremiumDeathRiskCoverage(PgCurrency)
   * @generated
   */
  boolean isSetPremiumDeathRiskCoverage();

  /**
   * Returns the value of the '<em><b>Premium Occupational Disability Risk Coverage</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Premies Arbeids Ongeschiktheids Risico Dekking</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Premium Occupational Disability Risk Coverage</em>' attribute.
   * @see #isSetPremiumOccupationalDisabilityRiskCoverage()
   * @see #unsetPremiumOccupationalDisabilityRiskCoverage()
   * @see #setPremiumOccupationalDisabilityRiskCoverage(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getAnnualStatement_PremiumOccupationalDisabilityRiskCoverage()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getPremiumOccupationalDisabilityRiskCoverage();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPremiumOccupationalDisabilityRiskCoverage <em>Premium Occupational Disability Risk Coverage</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Premium Occupational Disability Risk Coverage</em>' attribute.
   * @see #isSetPremiumOccupationalDisabilityRiskCoverage()
   * @see #unsetPremiumOccupationalDisabilityRiskCoverage()
   * @see #getPremiumOccupationalDisabilityRiskCoverage()
   * @generated
   */
	void setPremiumOccupationalDisabilityRiskCoverage(PgCurrency value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPremiumOccupationalDisabilityRiskCoverage <em>Premium Occupational Disability Risk Coverage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetPremiumOccupationalDisabilityRiskCoverage()
   * @see #getPremiumOccupationalDisabilityRiskCoverage()
   * @see #setPremiumOccupationalDisabilityRiskCoverage(PgCurrency)
   * @generated
   */
  void unsetPremiumOccupationalDisabilityRiskCoverage();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPremiumOccupationalDisabilityRiskCoverage <em>Premium Occupational Disability Risk Coverage</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Premium Occupational Disability Risk Coverage</em>' attribute is set.
   * @see #unsetPremiumOccupationalDisabilityRiskCoverage()
   * @see #getPremiumOccupationalDisabilityRiskCoverage()
   * @see #setPremiumOccupationalDisabilityRiskCoverage(PgCurrency)
   * @generated
   */
	boolean isSetPremiumOccupationalDisabilityRiskCoverage();

	/**
   * Returns the value of the '<em><b>Upkeep Premium</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Premie Verzorging</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Upkeep Premium</em>' attribute.
   * @see #isSetUpkeepPremium()
   * @see #unsetUpkeepPremium()
   * @see #setUpkeepPremium(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getAnnualStatement_UpkeepPremium()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getUpkeepPremium();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getUpkeepPremium <em>Upkeep Premium</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Upkeep Premium</em>' attribute.
   * @see #isSetUpkeepPremium()
   * @see #unsetUpkeepPremium()
   * @see #getUpkeepPremium()
   * @generated
   */
	void setUpkeepPremium(PgCurrency value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getUpkeepPremium <em>Upkeep Premium</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetUpkeepPremium()
   * @see #getUpkeepPremium()
   * @see #setUpkeepPremium(PgCurrency)
   * @generated
   */
  void unsetUpkeepPremium();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getUpkeepPremium <em>Upkeep Premium</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Upkeep Premium</em>' attribute is set.
   * @see #unsetUpkeepPremium()
   * @see #getUpkeepPremium()
   * @see #setUpkeepPremium(PgCurrency)
   * @generated
   */
	boolean isSetUpkeepPremium();

	/**
   * Returns the value of the '<em><b>First Costs Insurance Company</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kosten Verzekerings Maatschappij Eerste</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>First Costs Insurance Company</em>' attribute.
   * @see #isSetFirstCostsInsuranceCompany()
   * @see #unsetFirstCostsInsuranceCompany()
   * @see #setFirstCostsInsuranceCompany(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getAnnualStatement_FirstCostsInsuranceCompany()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getFirstCostsInsuranceCompany();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getFirstCostsInsuranceCompany <em>First Costs Insurance Company</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>First Costs Insurance Company</em>' attribute.
   * @see #isSetFirstCostsInsuranceCompany()
   * @see #unsetFirstCostsInsuranceCompany()
   * @see #getFirstCostsInsuranceCompany()
   * @generated
   */
	void setFirstCostsInsuranceCompany(PgCurrency value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getFirstCostsInsuranceCompany <em>First Costs Insurance Company</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetFirstCostsInsuranceCompany()
   * @see #getFirstCostsInsuranceCompany()
   * @see #setFirstCostsInsuranceCompany(PgCurrency)
   * @generated
   */
  void unsetFirstCostsInsuranceCompany();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getFirstCostsInsuranceCompany <em>First Costs Insurance Company</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>First Costs Insurance Company</em>' attribute is set.
   * @see #unsetFirstCostsInsuranceCompany()
   * @see #getFirstCostsInsuranceCompany()
   * @see #setFirstCostsInsuranceCompany(PgCurrency)
   * @generated
   */
	boolean isSetFirstCostsInsuranceCompany();

	/**
   * Returns the value of the '<em><b>Continuing Costs Insurance Company</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kosten Verzekerings Maatschappij Doorlopend</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Continuing Costs Insurance Company</em>' attribute.
   * @see #isSetContinuingCostsInsuranceCompany()
   * @see #unsetContinuingCostsInsuranceCompany()
   * @see #setContinuingCostsInsuranceCompany(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getAnnualStatement_ContinuingCostsInsuranceCompany()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getContinuingCostsInsuranceCompany();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getContinuingCostsInsuranceCompany <em>Continuing Costs Insurance Company</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Continuing Costs Insurance Company</em>' attribute.
   * @see #isSetContinuingCostsInsuranceCompany()
   * @see #unsetContinuingCostsInsuranceCompany()
   * @see #getContinuingCostsInsuranceCompany()
   * @generated
   */
	void setContinuingCostsInsuranceCompany(PgCurrency value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getContinuingCostsInsuranceCompany <em>Continuing Costs Insurance Company</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetContinuingCostsInsuranceCompany()
   * @see #getContinuingCostsInsuranceCompany()
   * @see #setContinuingCostsInsuranceCompany(PgCurrency)
   * @generated
   */
  void unsetContinuingCostsInsuranceCompany();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getContinuingCostsInsuranceCompany <em>Continuing Costs Insurance Company</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Continuing Costs Insurance Company</em>' attribute is set.
   * @see #unsetContinuingCostsInsuranceCompany()
   * @see #getContinuingCostsInsuranceCompany()
   * @see #setContinuingCostsInsuranceCompany(PgCurrency)
   * @generated
   */
	boolean isSetContinuingCostsInsuranceCompany();

	/**
   * Returns the value of the '<em><b>Management Costs</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Beheerkosten</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Management Costs</em>' attribute.
   * @see #isSetManagementCosts()
   * @see #unsetManagementCosts()
   * @see #setManagementCosts(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getAnnualStatement_ManagementCosts()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getManagementCosts();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getManagementCosts <em>Management Costs</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Management Costs</em>' attribute.
   * @see #isSetManagementCosts()
   * @see #unsetManagementCosts()
   * @see #getManagementCosts()
   * @generated
   */
	void setManagementCosts(PgCurrency value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getManagementCosts <em>Management Costs</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetManagementCosts()
   * @see #getManagementCosts()
   * @see #setManagementCosts(PgCurrency)
   * @generated
   */
  void unsetManagementCosts();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getManagementCosts <em>Management Costs</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Management Costs</em>' attribute is set.
   * @see #unsetManagementCosts()
   * @see #getManagementCosts()
   * @see #setManagementCosts(PgCurrency)
   * @generated
   */
	boolean isSetManagementCosts();

	/**
   * Returns the value of the '<em><b>Buy And Sell Costs</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Aan En Verkoop Kosten</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Buy And Sell Costs</em>' attribute.
   * @see #isSetBuyAndSellCosts()
   * @see #unsetBuyAndSellCosts()
   * @see #setBuyAndSellCosts(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getAnnualStatement_BuyAndSellCosts()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getBuyAndSellCosts();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getBuyAndSellCosts <em>Buy And Sell Costs</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Buy And Sell Costs</em>' attribute.
   * @see #isSetBuyAndSellCosts()
   * @see #unsetBuyAndSellCosts()
   * @see #getBuyAndSellCosts()
   * @generated
   */
	void setBuyAndSellCosts(PgCurrency value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getBuyAndSellCosts <em>Buy And Sell Costs</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetBuyAndSellCosts()
   * @see #getBuyAndSellCosts()
   * @see #setBuyAndSellCosts(PgCurrency)
   * @generated
   */
  void unsetBuyAndSellCosts();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getBuyAndSellCosts <em>Buy And Sell Costs</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Buy And Sell Costs</em>' attribute is set.
   * @see #unsetBuyAndSellCosts()
   * @see #getBuyAndSellCosts()
   * @see #setBuyAndSellCosts(PgCurrency)
   * @generated
   */
	boolean isSetBuyAndSellCosts();

	/**
   * Returns the value of the '<em><b>Mutation Costs</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mutatie Kosten</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Mutation Costs</em>' attribute.
   * @see #isSetMutationCosts()
   * @see #unsetMutationCosts()
   * @see #setMutationCosts(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getAnnualStatement_MutationCosts()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getMutationCosts();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getMutationCosts <em>Mutation Costs</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Mutation Costs</em>' attribute.
   * @see #isSetMutationCosts()
   * @see #unsetMutationCosts()
   * @see #getMutationCosts()
   * @generated
   */
	void setMutationCosts(PgCurrency value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getMutationCosts <em>Mutation Costs</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetMutationCosts()
   * @see #getMutationCosts()
   * @see #setMutationCosts(PgCurrency)
   * @generated
   */
  void unsetMutationCosts();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getMutationCosts <em>Mutation Costs</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Mutation Costs</em>' attribute is set.
   * @see #unsetMutationCosts()
   * @see #getMutationCosts()
   * @see #setMutationCosts(PgCurrency)
   * @generated
   */
	boolean isSetMutationCosts();

	/**
   * Returns the value of the '<em><b>Costs Restitution</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Teruggave Kosten</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Costs Restitution</em>' attribute.
   * @see #isSetCostsRestitution()
   * @see #unsetCostsRestitution()
   * @see #setCostsRestitution(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getAnnualStatement_CostsRestitution()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getCostsRestitution();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getCostsRestitution <em>Costs Restitution</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Costs Restitution</em>' attribute.
   * @see #isSetCostsRestitution()
   * @see #unsetCostsRestitution()
   * @see #getCostsRestitution()
   * @generated
   */
	void setCostsRestitution(PgCurrency value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getCostsRestitution <em>Costs Restitution</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetCostsRestitution()
   * @see #getCostsRestitution()
   * @see #setCostsRestitution(PgCurrency)
   * @generated
   */
  void unsetCostsRestitution();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getCostsRestitution <em>Costs Restitution</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Costs Restitution</em>' attribute is set.
   * @see #unsetCostsRestitution()
   * @see #getCostsRestitution()
   * @see #setCostsRestitution(PgCurrency)
   * @generated
   */
	boolean isSetCostsRestitution();

	/**
   * Returns the value of the '<em><b>Corrections</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Correcties</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Corrections</em>' attribute.
   * @see #isSetCorrections()
   * @see #unsetCorrections()
   * @see #setCorrections(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getAnnualStatement_Corrections()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getCorrections();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getCorrections <em>Corrections</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Corrections</em>' attribute.
   * @see #isSetCorrections()
   * @see #unsetCorrections()
   * @see #getCorrections()
   * @generated
   */
	void setCorrections(PgCurrency value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getCorrections <em>Corrections</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetCorrections()
   * @see #getCorrections()
   * @see #setCorrections(PgCurrency)
   * @generated
   */
  void unsetCorrections();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getCorrections <em>Corrections</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Corrections</em>' attribute is set.
   * @see #unsetCorrections()
   * @see #getCorrections()
   * @see #setCorrections(PgCurrency)
   * @generated
   */
	boolean isSetCorrections();

	/**
   * Returns the value of the '<em><b>Earned On The Participations</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Verdiend Op De Participaties</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Earned On The Participations</em>' attribute.
   * @see #isSetEarnedOnTheParticipations()
   * @see #unsetEarnedOnTheParticipations()
   * @see #setEarnedOnTheParticipations(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getAnnualStatement_EarnedOnTheParticipations()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getEarnedOnTheParticipations();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getEarnedOnTheParticipations <em>Earned On The Participations</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Earned On The Participations</em>' attribute.
   * @see #isSetEarnedOnTheParticipations()
   * @see #unsetEarnedOnTheParticipations()
   * @see #getEarnedOnTheParticipations()
   * @generated
   */
	void setEarnedOnTheParticipations(PgCurrency value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getEarnedOnTheParticipations <em>Earned On The Participations</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetEarnedOnTheParticipations()
   * @see #getEarnedOnTheParticipations()
   * @see #setEarnedOnTheParticipations(PgCurrency)
   * @generated
   */
  void unsetEarnedOnTheParticipations();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getEarnedOnTheParticipations <em>Earned On The Participations</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Earned On The Participations</em>' attribute is set.
   * @see #unsetEarnedOnTheParticipations()
   * @see #getEarnedOnTheParticipations()
   * @see #setEarnedOnTheParticipations(PgCurrency)
   * @generated
   */
	boolean isSetEarnedOnTheParticipations();

	/**
   * Returns the value of the '<em><b>Participations</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.finan.investmentinsurance.model.Participation}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Participaties</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Participations</em>' containment reference list.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getAnnualStatement_Participations()
   * @model containment="true"
   * @generated
   */
	EList<Participation> getParticipations();

	/**
   * Returns the value of the '<em><b>Example Capital On End Date</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Voorbeeld Kapitaal Eind Datum</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Example Capital On End Date</em>' attribute.
   * @see #isSetExampleCapitalOnEndDate()
   * @see #unsetExampleCapitalOnEndDate()
   * @see #setExampleCapitalOnEndDate(LocalDate)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getAnnualStatement_ExampleCapitalOnEndDate()
   * @model unsettable="true" dataType="goedegep.types.model.ELocalDate"
   * @generated
   */
	LocalDate getExampleCapitalOnEndDate();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getExampleCapitalOnEndDate <em>Example Capital On End Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Example Capital On End Date</em>' attribute.
   * @see #isSetExampleCapitalOnEndDate()
   * @see #unsetExampleCapitalOnEndDate()
   * @see #getExampleCapitalOnEndDate()
   * @generated
   */
  void setExampleCapitalOnEndDate(LocalDate value);

  /**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getExampleCapitalOnEndDate <em>Example Capital On End Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetExampleCapitalOnEndDate()
   * @see #getExampleCapitalOnEndDate()
   * @see #setExampleCapitalOnEndDate(LocalDate)
   * @generated
   */
  void unsetExampleCapitalOnEndDate();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getExampleCapitalOnEndDate <em>Example Capital On End Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Example Capital On End Date</em>' attribute is set.
   * @see #unsetExampleCapitalOnEndDate()
   * @see #getExampleCapitalOnEndDate()
   * @see #setExampleCapitalOnEndDate(LocalDate)
   * @generated
   */
	boolean isSetExampleCapitalOnEndDate();

	/**
   * Returns the value of the '<em><b>Expected Yearly Costs Increase</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Verwachtte Kosten Stijging Per Jaar</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Expected Yearly Costs Increase</em>' attribute.
   * @see #isSetExpectedYearlyCostsIncrease()
   * @see #unsetExpectedYearlyCostsIncrease()
   * @see #setExpectedYearlyCostsIncrease(FixedPointValue)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getAnnualStatement_ExpectedYearlyCostsIncrease()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
	FixedPointValue getExpectedYearlyCostsIncrease();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getExpectedYearlyCostsIncrease <em>Expected Yearly Costs Increase</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expected Yearly Costs Increase</em>' attribute.
   * @see #isSetExpectedYearlyCostsIncrease()
   * @see #unsetExpectedYearlyCostsIncrease()
   * @see #getExpectedYearlyCostsIncrease()
   * @generated
   */
	void setExpectedYearlyCostsIncrease(FixedPointValue value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getExpectedYearlyCostsIncrease <em>Expected Yearly Costs Increase</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetExpectedYearlyCostsIncrease()
   * @see #getExpectedYearlyCostsIncrease()
   * @see #setExpectedYearlyCostsIncrease(FixedPointValue)
   * @generated
   */
  void unsetExpectedYearlyCostsIncrease();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getExpectedYearlyCostsIncrease <em>Expected Yearly Costs Increase</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Expected Yearly Costs Increase</em>' attribute is set.
   * @see #unsetExpectedYearlyCostsIncrease()
   * @see #getExpectedYearlyCostsIncrease()
   * @see #setExpectedYearlyCostsIncrease(FixedPointValue)
   * @generated
   */
	boolean isSetExpectedYearlyCostsIncrease();

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @model dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency totalCosts();

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @model dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency valueAtYearStart();

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @model dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency percentageCosts();

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @model dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency fixedCosts();

} // JaarOpgave
