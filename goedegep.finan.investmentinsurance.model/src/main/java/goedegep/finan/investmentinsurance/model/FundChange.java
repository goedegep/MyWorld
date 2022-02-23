/**
 */
package goedegep.finan.investmentinsurance.model;

import goedegep.types.model.Event;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Fonds Wijziging</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.investmentinsurance.model.FundChange#getFromFund <em>From Fund</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.FundChange#getToFund <em>To Fund</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.FundChange#getFromNumberOfParticipations <em>From Number Of Participations</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.FundChange#getToNumberOfParticipations <em>To Number Of Participations</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.FundChange#getFromStockPrice <em>From Stock Price</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.FundChange#getToStockPrice <em>To Stock Price</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.FundChange#getFromAverageHistoricReturnOnInvestment <em>From Average Historic Return On Investment</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.FundChange#getFromTER <em>From TER</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.FundChange#getToAverageHistoricReturnOnInvestment <em>To Average Historic Return On Investment</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.FundChange#getToTER <em>To TER</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getFundChange()
 * @model
 * @generated
 */
public interface FundChange extends Event {
	/**
   * Returns the value of the '<em><b>From Fund</b></em>' reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Van Fonds</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>From Fund</em>' reference.
   * @see #isSetFromFund()
   * @see #unsetFromFund()
   * @see #setFromFund(InvestmentFund)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getFundChange_FromFund()
   * @model unsettable="true" required="true"
   * @generated
   */
	InvestmentFund getFromFund();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getFromFund <em>From Fund</em>}' reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>From Fund</em>' reference.
   * @see #isSetFromFund()
   * @see #unsetFromFund()
   * @see #getFromFund()
   * @generated
   */
	void setFromFund(InvestmentFund value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getFromFund <em>From Fund</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetFromFund()
   * @see #getFromFund()
   * @see #setFromFund(InvestmentFund)
   * @generated
   */
  void unsetFromFund();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getFromFund <em>From Fund</em>}' reference is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>From Fund</em>' reference is set.
   * @see #unsetFromFund()
   * @see #getFromFund()
   * @see #setFromFund(InvestmentFund)
   * @generated
   */
	boolean isSetFromFund();

	/**
   * Returns the value of the '<em><b>To Fund</b></em>' reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Naar Fonds</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>To Fund</em>' reference.
   * @see #isSetToFund()
   * @see #unsetToFund()
   * @see #setToFund(InvestmentFund)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getFundChange_ToFund()
   * @model unsettable="true" required="true"
   * @generated
   */
	InvestmentFund getToFund();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getToFund <em>To Fund</em>}' reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>To Fund</em>' reference.
   * @see #isSetToFund()
   * @see #unsetToFund()
   * @see #getToFund()
   * @generated
   */
	void setToFund(InvestmentFund value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getToFund <em>To Fund</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetToFund()
   * @see #getToFund()
   * @see #setToFund(InvestmentFund)
   * @generated
   */
  void unsetToFund();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getToFund <em>To Fund</em>}' reference is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>To Fund</em>' reference is set.
   * @see #unsetToFund()
   * @see #getToFund()
   * @see #setToFund(InvestmentFund)
   * @generated
   */
	boolean isSetToFund();

	/**
   * Returns the value of the '<em><b>From Number Of Participations</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Van Aantal Participaties</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>From Number Of Participations</em>' attribute.
   * @see #isSetFromNumberOfParticipations()
   * @see #unsetFromNumberOfParticipations()
   * @see #setFromNumberOfParticipations(FixedPointValue)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getFundChange_FromNumberOfParticipations()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue" required="true"
   * @generated
   */
	FixedPointValue getFromNumberOfParticipations();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getFromNumberOfParticipations <em>From Number Of Participations</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>From Number Of Participations</em>' attribute.
   * @see #isSetFromNumberOfParticipations()
   * @see #unsetFromNumberOfParticipations()
   * @see #getFromNumberOfParticipations()
   * @generated
   */
	void setFromNumberOfParticipations(FixedPointValue value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getFromNumberOfParticipations <em>From Number Of Participations</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetFromNumberOfParticipations()
   * @see #getFromNumberOfParticipations()
   * @see #setFromNumberOfParticipations(FixedPointValue)
   * @generated
   */
  void unsetFromNumberOfParticipations();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getFromNumberOfParticipations <em>From Number Of Participations</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>From Number Of Participations</em>' attribute is set.
   * @see #unsetFromNumberOfParticipations()
   * @see #getFromNumberOfParticipations()
   * @see #setFromNumberOfParticipations(FixedPointValue)
   * @generated
   */
	boolean isSetFromNumberOfParticipations();

	/**
   * Returns the value of the '<em><b>To Number Of Participations</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Naar Aantal Participaties</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>To Number Of Participations</em>' attribute.
   * @see #isSetToNumberOfParticipations()
   * @see #unsetToNumberOfParticipations()
   * @see #setToNumberOfParticipations(FixedPointValue)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getFundChange_ToNumberOfParticipations()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue" required="true"
   * @generated
   */
	FixedPointValue getToNumberOfParticipations();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getToNumberOfParticipations <em>To Number Of Participations</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>To Number Of Participations</em>' attribute.
   * @see #isSetToNumberOfParticipations()
   * @see #unsetToNumberOfParticipations()
   * @see #getToNumberOfParticipations()
   * @generated
   */
	void setToNumberOfParticipations(FixedPointValue value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getToNumberOfParticipations <em>To Number Of Participations</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetToNumberOfParticipations()
   * @see #getToNumberOfParticipations()
   * @see #setToNumberOfParticipations(FixedPointValue)
   * @generated
   */
  void unsetToNumberOfParticipations();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getToNumberOfParticipations <em>To Number Of Participations</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>To Number Of Participations</em>' attribute is set.
   * @see #unsetToNumberOfParticipations()
   * @see #getToNumberOfParticipations()
   * @see #setToNumberOfParticipations(FixedPointValue)
   * @generated
   */
	boolean isSetToNumberOfParticipations();

	/**
   * Returns the value of the '<em><b>From Stock Price</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Van Koers</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>From Stock Price</em>' attribute.
   * @see #isSetFromStockPrice()
   * @see #unsetFromStockPrice()
   * @see #setFromStockPrice(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getFundChange_FromStockPrice()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getFromStockPrice();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getFromStockPrice <em>From Stock Price</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>From Stock Price</em>' attribute.
   * @see #isSetFromStockPrice()
   * @see #unsetFromStockPrice()
   * @see #getFromStockPrice()
   * @generated
   */
	void setFromStockPrice(PgCurrency value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getFromStockPrice <em>From Stock Price</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetFromStockPrice()
   * @see #getFromStockPrice()
   * @see #setFromStockPrice(PgCurrency)
   * @generated
   */
  void unsetFromStockPrice();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getFromStockPrice <em>From Stock Price</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>From Stock Price</em>' attribute is set.
   * @see #unsetFromStockPrice()
   * @see #getFromStockPrice()
   * @see #setFromStockPrice(PgCurrency)
   * @generated
   */
	boolean isSetFromStockPrice();

	/**
   * Returns the value of the '<em><b>To Stock Price</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Naar Koers</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>To Stock Price</em>' attribute.
   * @see #isSetToStockPrice()
   * @see #unsetToStockPrice()
   * @see #setToStockPrice(PgCurrency)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getFundChange_ToStockPrice()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
	PgCurrency getToStockPrice();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getToStockPrice <em>To Stock Price</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>To Stock Price</em>' attribute.
   * @see #isSetToStockPrice()
   * @see #unsetToStockPrice()
   * @see #getToStockPrice()
   * @generated
   */
	void setToStockPrice(PgCurrency value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getToStockPrice <em>To Stock Price</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetToStockPrice()
   * @see #getToStockPrice()
   * @see #setToStockPrice(PgCurrency)
   * @generated
   */
  void unsetToStockPrice();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getToStockPrice <em>To Stock Price</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>To Stock Price</em>' attribute is set.
   * @see #unsetToStockPrice()
   * @see #getToStockPrice()
   * @see #setToStockPrice(PgCurrency)
   * @generated
   */
	boolean isSetToStockPrice();

	/**
   * Returns the value of the '<em><b>From Average Historic Return On Investment</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Van Gemiddeld Historisch Rendement</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>From Average Historic Return On Investment</em>' attribute.
   * @see #isSetFromAverageHistoricReturnOnInvestment()
   * @see #unsetFromAverageHistoricReturnOnInvestment()
   * @see #setFromAverageHistoricReturnOnInvestment(FixedPointValue)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getFundChange_FromAverageHistoricReturnOnInvestment()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
	FixedPointValue getFromAverageHistoricReturnOnInvestment();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getFromAverageHistoricReturnOnInvestment <em>From Average Historic Return On Investment</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>From Average Historic Return On Investment</em>' attribute.
   * @see #isSetFromAverageHistoricReturnOnInvestment()
   * @see #unsetFromAverageHistoricReturnOnInvestment()
   * @see #getFromAverageHistoricReturnOnInvestment()
   * @generated
   */
	void setFromAverageHistoricReturnOnInvestment(FixedPointValue value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getFromAverageHistoricReturnOnInvestment <em>From Average Historic Return On Investment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetFromAverageHistoricReturnOnInvestment()
   * @see #getFromAverageHistoricReturnOnInvestment()
   * @see #setFromAverageHistoricReturnOnInvestment(FixedPointValue)
   * @generated
   */
  void unsetFromAverageHistoricReturnOnInvestment();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getFromAverageHistoricReturnOnInvestment <em>From Average Historic Return On Investment</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>From Average Historic Return On Investment</em>' attribute is set.
   * @see #unsetFromAverageHistoricReturnOnInvestment()
   * @see #getFromAverageHistoricReturnOnInvestment()
   * @see #setFromAverageHistoricReturnOnInvestment(FixedPointValue)
   * @generated
   */
	boolean isSetFromAverageHistoricReturnOnInvestment();

	/**
   * Returns the value of the '<em><b>From TER</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Van TER</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>From TER</em>' attribute.
   * @see #isSetFromTER()
   * @see #unsetFromTER()
   * @see #setFromTER(FixedPointValue)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getFundChange_FromTER()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
	FixedPointValue getFromTER();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getFromTER <em>From TER</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>From TER</em>' attribute.
   * @see #isSetFromTER()
   * @see #unsetFromTER()
   * @see #getFromTER()
   * @generated
   */
	void setFromTER(FixedPointValue value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getFromTER <em>From TER</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetFromTER()
   * @see #getFromTER()
   * @see #setFromTER(FixedPointValue)
   * @generated
   */
  void unsetFromTER();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getFromTER <em>From TER</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>From TER</em>' attribute is set.
   * @see #unsetFromTER()
   * @see #getFromTER()
   * @see #setFromTER(FixedPointValue)
   * @generated
   */
	boolean isSetFromTER();

	/**
   * Returns the value of the '<em><b>To Average Historic Return On Investment</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Naar Gemiddeld Historisch Rendement</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>To Average Historic Return On Investment</em>' attribute.
   * @see #isSetToAverageHistoricReturnOnInvestment()
   * @see #unsetToAverageHistoricReturnOnInvestment()
   * @see #setToAverageHistoricReturnOnInvestment(FixedPointValue)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getFundChange_ToAverageHistoricReturnOnInvestment()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
	FixedPointValue getToAverageHistoricReturnOnInvestment();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getToAverageHistoricReturnOnInvestment <em>To Average Historic Return On Investment</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>To Average Historic Return On Investment</em>' attribute.
   * @see #isSetToAverageHistoricReturnOnInvestment()
   * @see #unsetToAverageHistoricReturnOnInvestment()
   * @see #getToAverageHistoricReturnOnInvestment()
   * @generated
   */
	void setToAverageHistoricReturnOnInvestment(FixedPointValue value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getToAverageHistoricReturnOnInvestment <em>To Average Historic Return On Investment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetToAverageHistoricReturnOnInvestment()
   * @see #getToAverageHistoricReturnOnInvestment()
   * @see #setToAverageHistoricReturnOnInvestment(FixedPointValue)
   * @generated
   */
  void unsetToAverageHistoricReturnOnInvestment();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getToAverageHistoricReturnOnInvestment <em>To Average Historic Return On Investment</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>To Average Historic Return On Investment</em>' attribute is set.
   * @see #unsetToAverageHistoricReturnOnInvestment()
   * @see #getToAverageHistoricReturnOnInvestment()
   * @see #setToAverageHistoricReturnOnInvestment(FixedPointValue)
   * @generated
   */
	boolean isSetToAverageHistoricReturnOnInvestment();

	/**
   * Returns the value of the '<em><b>To TER</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Naar TER</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>To TER</em>' attribute.
   * @see #isSetToTER()
   * @see #unsetToTER()
   * @see #setToTER(FixedPointValue)
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#getFundChange_ToTER()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
	FixedPointValue getToTER();

	/**
   * Sets the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getToTER <em>To TER</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>To TER</em>' attribute.
   * @see #isSetToTER()
   * @see #unsetToTER()
   * @see #getToTER()
   * @generated
   */
	void setToTER(FixedPointValue value);

	/**
   * Unsets the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getToTER <em>To TER</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetToTER()
   * @see #getToTER()
   * @see #setToTER(FixedPointValue)
   * @generated
   */
  void unsetToTER();

  /**
   * Returns whether the value of the '{@link goedegep.finan.investmentinsurance.model.FundChange#getToTER <em>To TER</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>To TER</em>' attribute is set.
   * @see #unsetToTER()
   * @see #getToTER()
   * @see #setToTER(FixedPointValue)
   * @generated
   */
	boolean isSetToTER();

} // FondsWijziging
