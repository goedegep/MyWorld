/**
 */
package goedegep.finan.jobappointment.model.util;

import goedegep.finan.jobappointment.model.*;
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
 * @see goedegep.finan.jobappointment.model.JobAppointmentPackage
 * @generated
 */
public class JobAppointmentSwitch<T> extends Switch<T> {
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static JobAppointmentPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JobAppointmentSwitch() {
    if (modelPackage == null) {
      modelPackage = JobAppointmentPackage.eINSTANCE;
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
      case JobAppointmentPackage.SALARY_EVENT: {
        SalaryEvent salaryEvent = (SalaryEvent)theEObject;
        T result = caseSalaryEvent(salaryEvent);
        if (result == null) result = caseEvent(salaryEvent);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JobAppointmentPackage.COLLECTIVE_RAISE: {
        CollectiveRaise collectiveRaise = (CollectiveRaise)theEObject;
        T result = caseCollectiveRaise(collectiveRaise);
        if (result == null) result = caseSalaryEvent(collectiveRaise);
        if (result == null) result = caseEvent(collectiveRaise);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JobAppointmentPackage.PARTTIME_PERCENTAGE: {
        ParttimePercentage parttimePercentage = (ParttimePercentage)theEObject;
        T result = caseParttimePercentage(parttimePercentage);
        if (result == null) result = caseSalaryEvent(parttimePercentage);
        if (result == null) result = caseEvent(parttimePercentage);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JobAppointmentPackage.SALARY_NOTICE: {
        SalaryNotice salaryNotice = (SalaryNotice)theEObject;
        T result = caseSalaryNotice(salaryNotice);
        if (result == null) result = caseSalaryEvent(salaryNotice);
        if (result == null) result = caseEvent(salaryNotice);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JobAppointmentPackage.SALARY_PAYMENT: {
        SalaryPayment salaryPayment = (SalaryPayment)theEObject;
        T result = caseSalaryPayment(salaryPayment);
        if (result == null) result = caseSalaryEvent(salaryPayment);
        if (result == null) result = caseEvent(salaryPayment);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JobAppointmentPackage.JOB_APPOINTMENT: {
        JobAppointment jobAppointment = (JobAppointment)theEObject;
        T result = caseJobAppointment(jobAppointment);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Salary Event</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Salary Event</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSalaryEvent(SalaryEvent object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Collective Raise</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Collective Raise</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCollectiveRaise(CollectiveRaise object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Parttime Percentage</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Parttime Percentage</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseParttimePercentage(ParttimePercentage object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Salary Notice</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Salary Notice</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSalaryNotice(SalaryNotice object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Salary Payment</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Salary Payment</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSalaryPayment(SalaryPayment object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Job Appointment</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Job Appointment</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJobAppointment(JobAppointment object) {
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

} //JobAppointmentSwitch
