/**
 */
package goedegep.finan.mortgage.model.util;

import goedegep.finan.mortgage.model.*;

import java.util.Map;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.finan.mortgage.model.MortgagePackage
 * @generated
 */
public class MortgageAdapterFactory extends AdapterFactoryImpl {
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static MortgagePackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MortgageAdapterFactory() {
    if (modelPackage == null) {
      modelPackage = MortgagePackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object) {
    if (object == modelPackage) {
      return true;
    }
    if (object instanceof EObject) {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MortgageSwitch<Adapter> modelSwitch =
    new MortgageSwitch<Adapter>() {
      @Override
      public Adapter caseMortgage(Mortgage object) {
        return createMortgageAdapter();
      }
      @Override
      public Adapter caseMortgageEvent(MortgageEvent object) {
        return createMortgageEventAdapter();
      }
      @Override
      public Adapter caseFinalPayment(FinalPayment object) {
        return createFinalPaymentAdapter();
      }
      @Override
      public Adapter casePeriodicPayment(PeriodicPayment object) {
        return createPeriodicPaymentAdapter();
      }
      @Override
      public Adapter caseNewInterestRate(NewInterestRate object) {
        return createNewInterestRateAdapter();
      }
      @Override
      public Adapter caseMortgages(Mortgages object) {
        return createMortgagesAdapter();
      }
      @Override
      public Adapter caseInterestCompensationMortgage(InterestCompensationMortgage object) {
        return createInterestCompensationMortgageAdapter();
      }
      @Override
      public Adapter caseIntegerToEList(Map.Entry<Integer, EList<CompensationPayment>> object) {
        return createIntegerToEListAdapter();
      }
      @Override
      public Adapter caseCompensationPayment(CompensationPayment object) {
        return createCompensationPaymentAdapter();
      }
      @Override
      public Adapter caseCompensationPayments(CompensationPayments object) {
        return createCompensationPaymentsAdapter();
      }
      @Override
      public Adapter casePeriodicPaymentWithCompensation(PeriodicPaymentWithCompensation object) {
        return createPeriodicPaymentWithCompensationAdapter();
      }
      @Override
      public Adapter caseNewInterestRateWithCompensation(NewInterestRateWithCompensation object) {
        return createNewInterestRateWithCompensationAdapter();
      }
      @Override
      public Adapter caseMortgageYearlyOverview(MortgageYearlyOverview object) {
        return createMortgageYearlyOverviewAdapter();
      }
      @Override
      public Adapter caseInterestCompensationMortgageYearlyOverview(InterestCompensationMortgageYearlyOverview object) {
        return createInterestCompensationMortgageYearlyOverviewAdapter();
      }
      @Override
      public Adapter caseInterestRateSet(InterestRateSet object) {
        return createInterestRateSetAdapter();
      }
      @Override
      public Adapter caseRate(Rate object) {
        return createRateAdapter();
      }
      @Override
      public Adapter caseMortgageYearlyOverviews(MortgageYearlyOverviews object) {
        return createMortgageYearlyOverviewsAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object) {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target) {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.mortgage.model.Mortgage <em>Mortgage</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.mortgage.model.Mortgage
   * @generated
   */
  public Adapter createMortgageAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.mortgage.model.MortgageEvent <em>Event</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.mortgage.model.MortgageEvent
   * @generated
   */
  public Adapter createMortgageEventAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.mortgage.model.FinalPayment <em>Final Payment</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.mortgage.model.FinalPayment
   * @generated
   */
  public Adapter createFinalPaymentAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.mortgage.model.PeriodicPayment <em>Periodic Payment</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.mortgage.model.PeriodicPayment
   * @generated
   */
  public Adapter createPeriodicPaymentAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.mortgage.model.NewInterestRate <em>New Interest Rate</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.mortgage.model.NewInterestRate
   * @generated
   */
  public Adapter createNewInterestRateAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.mortgage.model.Mortgages <em>Mortgages</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.mortgage.model.Mortgages
   * @generated
   */
  public Adapter createMortgagesAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.mortgage.model.InterestCompensationMortgage <em>Interest Compensation Mortgage</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.mortgage.model.InterestCompensationMortgage
   * @generated
   */
  public Adapter createInterestCompensationMortgageAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>Integer To EList</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see java.util.Map.Entry
   * @generated
   */
  public Adapter createIntegerToEListAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.mortgage.model.CompensationPayment <em>Compensation Payment</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.mortgage.model.CompensationPayment
   * @generated
   */
  public Adapter createCompensationPaymentAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.mortgage.model.CompensationPayments <em>Compensation Payments</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.mortgage.model.CompensationPayments
   * @generated
   */
  public Adapter createCompensationPaymentsAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.mortgage.model.PeriodicPaymentWithCompensation <em>Periodic Payment With Compensation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.mortgage.model.PeriodicPaymentWithCompensation
   * @generated
   */
  public Adapter createPeriodicPaymentWithCompensationAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.mortgage.model.NewInterestRateWithCompensation <em>New Interest Rate With Compensation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.mortgage.model.NewInterestRateWithCompensation
   * @generated
   */
  public Adapter createNewInterestRateWithCompensationAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview <em>Yearly Overview</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.mortgage.model.MortgageYearlyOverview
   * @generated
   */
  public Adapter createMortgageYearlyOverviewAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview <em>Interest Compensation Mortgage Yearly Overview</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview
   * @generated
   */
  public Adapter createInterestCompensationMortgageYearlyOverviewAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.mortgage.model.InterestRateSet <em>Interest Rate Set</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.mortgage.model.InterestRateSet
   * @generated
   */
  public Adapter createInterestRateSetAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.mortgage.model.Rate <em>Rate</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.mortgage.model.Rate
   * @generated
   */
  public Adapter createRateAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.mortgage.model.MortgageYearlyOverviews <em>Yearly Overviews</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.mortgage.model.MortgageYearlyOverviews
   * @generated
   */
  public Adapter createMortgageYearlyOverviewsAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter() {
    return null;
  }

} //MortgageAdapterFactory
