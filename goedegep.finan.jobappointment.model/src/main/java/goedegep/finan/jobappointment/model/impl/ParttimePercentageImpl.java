/**
 */
package goedegep.finan.jobappointment.model.impl;

import goedegep.finan.jobappointment.model.JobAppointmentPackage;
import goedegep.finan.jobappointment.model.ParttimePercentage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parttime Percentage</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.jobappointment.model.impl.ParttimePercentageImpl#getParttimePercentage <em>Parttime Percentage</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ParttimePercentageImpl extends SalaryEventImpl implements ParttimePercentage {
  /**
   * The default value of the '{@link #getParttimePercentage() <em>Parttime Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParttimePercentage()
   * @generated
   * @ordered
   */
  protected static final Integer PARTTIME_PERCENTAGE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getParttimePercentage() <em>Parttime Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParttimePercentage()
   * @generated
   * @ordered
   */
  protected Integer parttimePercentage = PARTTIME_PERCENTAGE_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ParttimePercentageImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return JobAppointmentPackage.Literals.PARTTIME_PERCENTAGE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Integer getParttimePercentage() {
    return parttimePercentage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setParttimePercentage(Integer newParttimePercentage) {
    Integer oldParttimePercentage = parttimePercentage;
    parttimePercentage = newParttimePercentage;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JobAppointmentPackage.PARTTIME_PERCENTAGE__PARTTIME_PERCENTAGE, oldParttimePercentage, parttimePercentage));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case JobAppointmentPackage.PARTTIME_PERCENTAGE__PARTTIME_PERCENTAGE:
        return getParttimePercentage();
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
      case JobAppointmentPackage.PARTTIME_PERCENTAGE__PARTTIME_PERCENTAGE:
        setParttimePercentage((Integer)newValue);
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
      case JobAppointmentPackage.PARTTIME_PERCENTAGE__PARTTIME_PERCENTAGE:
        setParttimePercentage(PARTTIME_PERCENTAGE_EDEFAULT);
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
      case JobAppointmentPackage.PARTTIME_PERCENTAGE__PARTTIME_PERCENTAGE:
        return PARTTIME_PERCENTAGE_EDEFAULT == null ? parttimePercentage != null : !PARTTIME_PERCENTAGE_EDEFAULT.equals(parttimePercentage);
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
    result.append(" (parttimePercentage: ");
    result.append(parttimePercentage);
    result.append(')');
    return result.toString();
  }

} //ParttimePercentageImpl
