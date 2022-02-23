/**
 */
package goedegep.finan.jobappointment.model.impl;

import goedegep.finan.jobappointment.model.JobAppointmentPackage;
import goedegep.finan.jobappointment.model.SalaryEvent;

import goedegep.finan.jobappointment.model.SalaryEventType;
import goedegep.types.model.impl.EventImpl;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Salary Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.jobappointment.model.impl.SalaryEventImpl#getSalaryEventType <em>Salary Event Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class SalaryEventImpl extends EventImpl implements SalaryEvent {
  /**
   * The default value of the '{@link #getSalaryEventType() <em>Salary Event Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSalaryEventType()
   * @generated
   * @ordered
   */
  protected static final SalaryEventType SALARY_EVENT_TYPE_EDEFAULT = SalaryEventType.COLLECTIVE_RAISE;
  /**
   * The cached value of the '{@link #getSalaryEventType() <em>Salary Event Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSalaryEventType()
   * @generated
   * @ordered
   */
  protected SalaryEventType salaryEventType = SALARY_EVENT_TYPE_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected SalaryEventImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return JobAppointmentPackage.Literals.SALARY_EVENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public SalaryEventType getSalaryEventType() {
    return salaryEventType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setSalaryEventType(SalaryEventType newSalaryEventType) {
    SalaryEventType oldSalaryEventType = salaryEventType;
    salaryEventType = newSalaryEventType == null ? SALARY_EVENT_TYPE_EDEFAULT : newSalaryEventType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JobAppointmentPackage.SALARY_EVENT__SALARY_EVENT_TYPE, oldSalaryEventType, salaryEventType));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case JobAppointmentPackage.SALARY_EVENT__SALARY_EVENT_TYPE:
        return getSalaryEventType();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case JobAppointmentPackage.SALARY_EVENT__SALARY_EVENT_TYPE:
        setSalaryEventType((SalaryEventType)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID) {
    switch (featureID) {
      case JobAppointmentPackage.SALARY_EVENT__SALARY_EVENT_TYPE:
        setSalaryEventType(SALARY_EVENT_TYPE_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID) {
    switch (featureID) {
      case JobAppointmentPackage.SALARY_EVENT__SALARY_EVENT_TYPE:
        return salaryEventType != SALARY_EVENT_TYPE_EDEFAULT;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (salaryEventType: ");
    result.append(salaryEventType);
    result.append(')');
    return result.toString();
  }

} //SalaryEventImpl
