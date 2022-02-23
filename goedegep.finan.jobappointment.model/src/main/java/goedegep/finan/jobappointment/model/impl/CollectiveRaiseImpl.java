/**
 */
package goedegep.finan.jobappointment.model.impl;

import goedegep.finan.jobappointment.model.CollectiveRaise;
import goedegep.finan.jobappointment.model.JobAppointmentPackage;

import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Collective Raise</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.jobappointment.model.impl.CollectiveRaiseImpl#getPercentage <em>Percentage</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.impl.CollectiveRaiseImpl#getAmount <em>Amount</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CollectiveRaiseImpl extends SalaryEventImpl implements CollectiveRaise {
  /**
   * The default value of the '{@link #getPercentage() <em>Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPercentage()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue PERCENTAGE_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getPercentage() <em>Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPercentage()
   * @generated
   * @ordered
   */
  protected FixedPointValue percentage = PERCENTAGE_EDEFAULT;
  /**
   * The default value of the '{@link #getAmount() <em>Amount</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAmount()
   * @generated
   * @ordered
   */
  protected static final PgCurrency AMOUNT_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getAmount() <em>Amount</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAmount()
   * @generated
   * @ordered
   */
  protected PgCurrency amount = AMOUNT_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CollectiveRaiseImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return JobAppointmentPackage.Literals.COLLECTIVE_RAISE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FixedPointValue getPercentage() {
    return percentage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPercentage(FixedPointValue newPercentage) {
    FixedPointValue oldPercentage = percentage;
    percentage = newPercentage;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JobAppointmentPackage.COLLECTIVE_RAISE__PERCENTAGE, oldPercentage, percentage));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getAmount() {
    return amount;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setAmount(PgCurrency newAmount) {
    PgCurrency oldAmount = amount;
    amount = newAmount;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JobAppointmentPackage.COLLECTIVE_RAISE__AMOUNT, oldAmount, amount));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case JobAppointmentPackage.COLLECTIVE_RAISE__PERCENTAGE:
        return getPercentage();
      case JobAppointmentPackage.COLLECTIVE_RAISE__AMOUNT:
        return getAmount();
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
      case JobAppointmentPackage.COLLECTIVE_RAISE__PERCENTAGE:
        setPercentage((FixedPointValue)newValue);
        return;
      case JobAppointmentPackage.COLLECTIVE_RAISE__AMOUNT:
        setAmount((PgCurrency)newValue);
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
      case JobAppointmentPackage.COLLECTIVE_RAISE__PERCENTAGE:
        setPercentage(PERCENTAGE_EDEFAULT);
        return;
      case JobAppointmentPackage.COLLECTIVE_RAISE__AMOUNT:
        setAmount(AMOUNT_EDEFAULT);
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
      case JobAppointmentPackage.COLLECTIVE_RAISE__PERCENTAGE:
        return PERCENTAGE_EDEFAULT == null ? percentage != null : !PERCENTAGE_EDEFAULT.equals(percentage);
      case JobAppointmentPackage.COLLECTIVE_RAISE__AMOUNT:
        return AMOUNT_EDEFAULT == null ? amount != null : !AMOUNT_EDEFAULT.equals(amount);
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
    result.append(" (percentage: ");
    result.append(percentage);
    result.append(", amount: ");
    result.append(amount);
    result.append(')');
    return result.toString();
  }

} //CollectiveRaiseImpl
