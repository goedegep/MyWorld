/**
 */
package goedegep.finan.jobappointment.model.util;

import goedegep.finan.jobappointment.model.*;
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
 * @see goedegep.finan.jobappointment.model.JobAppointmentPackage
 * @generated
 */
public class JobAppointmentAdapterFactory extends AdapterFactoryImpl {
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static JobAppointmentPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JobAppointmentAdapterFactory() {
    if (modelPackage == null) {
      modelPackage = JobAppointmentPackage.eINSTANCE;
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
  protected JobAppointmentSwitch<Adapter> modelSwitch =
    new JobAppointmentSwitch<Adapter>() {
      @Override
      public Adapter caseSalaryEvent(SalaryEvent object) {
        return createSalaryEventAdapter();
      }
      @Override
      public Adapter caseCollectiveRaise(CollectiveRaise object) {
        return createCollectiveRaiseAdapter();
      }
      @Override
      public Adapter caseParttimePercentage(ParttimePercentage object) {
        return createParttimePercentageAdapter();
      }
      @Override
      public Adapter caseSalaryNotice(SalaryNotice object) {
        return createSalaryNoticeAdapter();
      }
      @Override
      public Adapter caseSalaryPayment(SalaryPayment object) {
        return createSalaryPaymentAdapter();
      }
      @Override
      public Adapter caseJobAppointment(JobAppointment object) {
        return createJobAppointmentAdapter();
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
   * Creates a new adapter for an object of class '{@link goedegep.finan.jobappointment.model.SalaryEvent <em>Salary Event</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.jobappointment.model.SalaryEvent
   * @generated
   */
  public Adapter createSalaryEventAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.jobappointment.model.CollectiveRaise <em>Collective Raise</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.jobappointment.model.CollectiveRaise
   * @generated
   */
  public Adapter createCollectiveRaiseAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.jobappointment.model.ParttimePercentage <em>Parttime Percentage</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.jobappointment.model.ParttimePercentage
   * @generated
   */
  public Adapter createParttimePercentageAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.jobappointment.model.SalaryNotice <em>Salary Notice</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.jobappointment.model.SalaryNotice
   * @generated
   */
  public Adapter createSalaryNoticeAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.jobappointment.model.SalaryPayment <em>Salary Payment</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.jobappointment.model.SalaryPayment
   * @generated
   */
  public Adapter createSalaryPaymentAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.jobappointment.model.JobAppointment <em>Job Appointment</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.jobappointment.model.JobAppointment
   * @generated
   */
  public Adapter createJobAppointmentAdapter() {
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

} //JobAppointmentAdapterFactory
