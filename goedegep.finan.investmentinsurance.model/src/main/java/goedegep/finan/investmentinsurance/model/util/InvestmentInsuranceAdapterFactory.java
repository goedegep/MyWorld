/**
 */
package goedegep.finan.investmentinsurance.model.util;

import goedegep.finan.investmentinsurance.model.*;
import goedegep.rolodex.model.Institution;
import goedegep.rolodex.model.PhoneNumberHolder;
import goedegep.types.model.Event;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage
 * @generated
 */
public class InvestmentInsuranceAdapterFactory extends AdapterFactoryImpl {
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static InvestmentInsurancePackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InvestmentInsuranceAdapterFactory() {
    if (modelPackage == null) {
      modelPackage = InvestmentInsurancePackage.eINSTANCE;
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
  protected InvestmentInsuranceSwitch<Adapter> modelSwitch =
    new InvestmentInsuranceSwitch<Adapter>() {
      @Override
      public Adapter caseInvestmentInsurance(InvestmentInsurance object) {
        return createInvestmentInsuranceAdapter();
      }
      @Override
      public Adapter caseInvestmentInsurancesData(InvestmentInsurancesData object) {
        return createInvestmentInsurancesDataAdapter();
      }
      @Override
      public Adapter caseInsuranceCompany(InsuranceCompany object) {
        return createInsuranceCompanyAdapter();
      }
      @Override
      public Adapter caseAnnualStatement(AnnualStatement object) {
        return createAnnualStatementAdapter();
      }
      @Override
      public Adapter caseInvestmentFund(InvestmentFund object) {
        return createInvestmentFundAdapter();
      }
      @Override
      public Adapter caseParticipation(Participation object) {
        return createParticipationAdapter();
      }
      @Override
      public Adapter caseInvestmentPart(InvestmentPart object) {
        return createInvestmentPartAdapter();
      }
      @Override
      public Adapter caseFundChange(FundChange object) {
        return createFundChangeAdapter();
      }
      @Override
      public Adapter caseExtraInvestment(ExtraInvestment object) {
        return createExtraInvestmentAdapter();
      }
      @Override
      public Adapter casePhoneNumberHolder(PhoneNumberHolder object) {
        return createPhoneNumberHolderAdapter();
      }
      @Override
      public Adapter caseAddressHolder(goedegep.rolodex.model.AddressHolder object) {
        return createAddressHolderAdapter();
      }
      @Override
      public Adapter caseInstitution(Institution object) {
        return createInstitutionAdapter();
      }
      @Override
      public Adapter caseEvent(Event object) {
        return createEventAdapter();
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
   * Creates a new adapter for an object of class '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance <em>Investment Insurance</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurance
   * @generated
   */
  public Adapter createInvestmentInsuranceAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData <em>Investment Insurances Data</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancesData
   * @generated
   */
  public Adapter createInvestmentInsurancesDataAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.investmentinsurance.model.InsuranceCompany <em>Insurance Company</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.investmentinsurance.model.InsuranceCompany
   * @generated
   */
  public Adapter createInsuranceCompanyAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.investmentinsurance.model.AnnualStatement <em>Annual Statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement
   * @generated
   */
  public Adapter createAnnualStatementAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.investmentinsurance.model.InvestmentFund <em>Investment Fund</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.investmentinsurance.model.InvestmentFund
   * @generated
   */
  public Adapter createInvestmentFundAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.investmentinsurance.model.Participation <em>Participation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.investmentinsurance.model.Participation
   * @generated
   */
  public Adapter createParticipationAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.investmentinsurance.model.InvestmentPart <em>Investment Part</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.investmentinsurance.model.InvestmentPart
   * @generated
   */
  public Adapter createInvestmentPartAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.investmentinsurance.model.FundChange <em>Fund Change</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.investmentinsurance.model.FundChange
   * @generated
   */
  public Adapter createFundChangeAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.investmentinsurance.model.ExtraInvestment <em>Extra Investment</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.investmentinsurance.model.ExtraInvestment
   * @generated
   */
  public Adapter createExtraInvestmentAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.PhoneNumberHolder <em>Phone Number Holder</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.PhoneNumberHolder
   * @generated
   */
  public Adapter createPhoneNumberHolderAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.AddressHolder <em>Address Holder</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.AddressHolder
   * @generated
   */
  public Adapter createAddressHolderAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.Institution <em>Institution</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.Institution
   * @generated
   */
  public Adapter createInstitutionAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.types.model.Event <em>Event</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.types.model.Event
   * @generated
   */
  public Adapter createEventAdapter() {
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

} //InvestmentInsuranceAdapterFactory
