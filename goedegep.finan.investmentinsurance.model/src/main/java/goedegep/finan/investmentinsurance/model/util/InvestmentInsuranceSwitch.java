/**
 */
package goedegep.finan.investmentinsurance.model.util;

import goedegep.finan.investmentinsurance.model.*;
import goedegep.rolodex.model.Institution;
import goedegep.rolodex.model.PhoneNumberHolder;
import goedegep.types.model.Event;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage
 * @generated
 */
public class InvestmentInsuranceSwitch<T> extends Switch<T> {
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static InvestmentInsurancePackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InvestmentInsuranceSwitch() {
    if (modelPackage == null) {
      modelPackage = InvestmentInsurancePackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage) {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject) {
    switch (classifierID) {
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE: {
        InvestmentInsurance investmentInsurance = (InvestmentInsurance)theEObject;
        T result = caseInvestmentInsurance(investmentInsurance);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA: {
        InvestmentInsurancesData investmentInsurancesData = (InvestmentInsurancesData)theEObject;
        T result = caseInvestmentInsurancesData(investmentInsurancesData);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case InvestmentInsurancePackage.INSURANCE_COMPANY: {
        InsuranceCompany insuranceCompany = (InsuranceCompany)theEObject;
        T result = caseInsuranceCompany(insuranceCompany);
        if (result == null) result = caseInstitution(insuranceCompany);
        if (result == null) result = casePhoneNumberHolder(insuranceCompany);
        if (result == null) result = caseAddressHolder(insuranceCompany);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case InvestmentInsurancePackage.ANNUAL_STATEMENT: {
        AnnualStatement annualStatement = (AnnualStatement)theEObject;
        T result = caseAnnualStatement(annualStatement);
        if (result == null) result = caseEvent(annualStatement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case InvestmentInsurancePackage.INVESTMENT_FUND: {
        InvestmentFund investmentFund = (InvestmentFund)theEObject;
        T result = caseInvestmentFund(investmentFund);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case InvestmentInsurancePackage.PARTICIPATION: {
        Participation participation = (Participation)theEObject;
        T result = caseParticipation(participation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case InvestmentInsurancePackage.INVESTMENT_PART: {
        InvestmentPart investmentPart = (InvestmentPart)theEObject;
        T result = caseInvestmentPart(investmentPart);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case InvestmentInsurancePackage.FUND_CHANGE: {
        FundChange fundChange = (FundChange)theEObject;
        T result = caseFundChange(fundChange);
        if (result == null) result = caseEvent(fundChange);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case InvestmentInsurancePackage.EXTRA_INVESTMENT: {
        ExtraInvestment extraInvestment = (ExtraInvestment)theEObject;
        T result = caseExtraInvestment(extraInvestment);
        if (result == null) result = caseEvent(extraInvestment);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Investment Insurance</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Investment Insurance</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInvestmentInsurance(InvestmentInsurance object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Investment Insurances Data</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Investment Insurances Data</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInvestmentInsurancesData(InvestmentInsurancesData object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Insurance Company</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Insurance Company</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInsuranceCompany(InsuranceCompany object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Annual Statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Annual Statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAnnualStatement(AnnualStatement object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Investment Fund</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Investment Fund</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInvestmentFund(InvestmentFund object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Participation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Participation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseParticipation(Participation object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Investment Part</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Investment Part</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInvestmentPart(InvestmentPart object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Fund Change</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Fund Change</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFundChange(FundChange object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Extra Investment</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Extra Investment</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseExtraInvestment(ExtraInvestment object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Phone Number Holder</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Phone Number Holder</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePhoneNumberHolder(PhoneNumberHolder object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Address Holder</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Address Holder</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAddressHolder(goedegep.rolodex.model.AddressHolder object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Institution</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Institution</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInstitution(Institution object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Event</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Event</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEvent(Event object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object) {
    return null;
  }

} //InvestmentInsuranceSwitch
