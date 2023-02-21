/**
 */
package goedegep.finan.mortgage.model.util;

import goedegep.finan.mortgage.model.*;

import java.util.Map;
import org.eclipse.emf.common.util.EList;
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
 * @see goedegep.finan.mortgage.model.MortgagePackage
 * @generated
 */
public class MortgageSwitch<T> extends Switch<T> {
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static MortgagePackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MortgageSwitch() {
    if (modelPackage == null) {
      modelPackage = MortgagePackage.eINSTANCE;
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
      case MortgagePackage.MORTGAGE: {
        Mortgage mortgage = (Mortgage)theEObject;
        T result = caseMortgage(mortgage);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MortgagePackage.MORTGAGE_EVENT: {
        MortgageEvent mortgageEvent = (MortgageEvent)theEObject;
        T result = caseMortgageEvent(mortgageEvent);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MortgagePackage.FINAL_PAYMENT: {
        FinalPayment finalPayment = (FinalPayment)theEObject;
        T result = caseFinalPayment(finalPayment);
        if (result == null) result = caseMortgageEvent(finalPayment);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MortgagePackage.PERIODIC_PAYMENT: {
        PeriodicPayment periodicPayment = (PeriodicPayment)theEObject;
        T result = casePeriodicPayment(periodicPayment);
        if (result == null) result = caseMortgageEvent(periodicPayment);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MortgagePackage.NEW_INTEREST_RATE: {
        NewInterestRate newInterestRate = (NewInterestRate)theEObject;
        T result = caseNewInterestRate(newInterestRate);
        if (result == null) result = caseMortgageEvent(newInterestRate);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MortgagePackage.MORTGAGES: {
        Mortgages mortgages = (Mortgages)theEObject;
        T result = caseMortgages(mortgages);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE: {
        InterestCompensationMortgage interestCompensationMortgage = (InterestCompensationMortgage)theEObject;
        T result = caseInterestCompensationMortgage(interestCompensationMortgage);
        if (result == null) result = caseMortgage(interestCompensationMortgage);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MortgagePackage.INTEGER_TO_ELIST: {
        @SuppressWarnings("unchecked") Map.Entry<Integer, EList<CompensationPayment>> integerToEList = (Map.Entry<Integer, EList<CompensationPayment>>)theEObject;
        T result = caseIntegerToEList(integerToEList);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MortgagePackage.COMPENSATION_PAYMENT: {
        CompensationPayment compensationPayment = (CompensationPayment)theEObject;
        T result = caseCompensationPayment(compensationPayment);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MortgagePackage.COMPENSATION_PAYMENTS: {
        CompensationPayments compensationPayments = (CompensationPayments)theEObject;
        T result = caseCompensationPayments(compensationPayments);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MortgagePackage.PERIODIC_PAYMENT_WITH_COMPENSATION: {
        PeriodicPaymentWithCompensation periodicPaymentWithCompensation = (PeriodicPaymentWithCompensation)theEObject;
        T result = casePeriodicPaymentWithCompensation(periodicPaymentWithCompensation);
        if (result == null) result = casePeriodicPayment(periodicPaymentWithCompensation);
        if (result == null) result = caseMortgageEvent(periodicPaymentWithCompensation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MortgagePackage.NEW_INTEREST_RATE_WITH_COMPENSATION: {
        NewInterestRateWithCompensation newInterestRateWithCompensation = (NewInterestRateWithCompensation)theEObject;
        T result = caseNewInterestRateWithCompensation(newInterestRateWithCompensation);
        if (result == null) result = caseNewInterestRate(newInterestRateWithCompensation);
        if (result == null) result = caseMortgageEvent(newInterestRateWithCompensation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW: {
        MortgageYearlyOverview mortgageYearlyOverview = (MortgageYearlyOverview)theEObject;
        T result = caseMortgageYearlyOverview(mortgageYearlyOverview);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW: {
        InterestCompensationMortgageYearlyOverview interestCompensationMortgageYearlyOverview = (InterestCompensationMortgageYearlyOverview)theEObject;
        T result = caseInterestCompensationMortgageYearlyOverview(interestCompensationMortgageYearlyOverview);
        if (result == null) result = caseMortgageYearlyOverview(interestCompensationMortgageYearlyOverview);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MortgagePackage.INTEREST_RATE_SET: {
        InterestRateSet interestRateSet = (InterestRateSet)theEObject;
        T result = caseInterestRateSet(interestRateSet);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MortgagePackage.RATE: {
        Rate rate = (Rate)theEObject;
        T result = caseRate(rate);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEWS: {
        MortgageYearlyOverviews mortgageYearlyOverviews = (MortgageYearlyOverviews)theEObject;
        T result = caseMortgageYearlyOverviews(mortgageYearlyOverviews);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Mortgage</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Mortgage</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMortgage(Mortgage object) {
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
  public T caseMortgageEvent(MortgageEvent object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Final Payment</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Final Payment</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFinalPayment(FinalPayment object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Periodic Payment</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Periodic Payment</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePeriodicPayment(PeriodicPayment object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>New Interest Rate</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>New Interest Rate</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseNewInterestRate(NewInterestRate object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Mortgages</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Mortgages</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMortgages(Mortgages object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Interest Compensation Mortgage</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Interest Compensation Mortgage</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInterestCompensationMortgage(InterestCompensationMortgage object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Integer To EList</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Integer To EList</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseIntegerToEList(Map.Entry<Integer, EList<CompensationPayment>> object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Compensation Payment</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Compensation Payment</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCompensationPayment(CompensationPayment object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Compensation Payments</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Compensation Payments</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCompensationPayments(CompensationPayments object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Periodic Payment With Compensation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Periodic Payment With Compensation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePeriodicPaymentWithCompensation(PeriodicPaymentWithCompensation object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>New Interest Rate With Compensation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>New Interest Rate With Compensation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseNewInterestRateWithCompensation(NewInterestRateWithCompensation object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Yearly Overview</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Yearly Overview</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMortgageYearlyOverview(MortgageYearlyOverview object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Interest Compensation Mortgage Yearly Overview</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Interest Compensation Mortgage Yearly Overview</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInterestCompensationMortgageYearlyOverview(InterestCompensationMortgageYearlyOverview object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Interest Rate Set</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Interest Rate Set</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInterestRateSet(InterestRateSet object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Rate</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Rate</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRate(Rate object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Yearly Overviews</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Yearly Overviews</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMortgageYearlyOverviews(MortgageYearlyOverviews object) {
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

} //MortgageSwitch
