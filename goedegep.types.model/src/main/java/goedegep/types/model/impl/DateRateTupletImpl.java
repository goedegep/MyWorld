/**
 */
package goedegep.types.model.impl;

import goedegep.types.model.DateRateTuplet;
import goedegep.types.model.TypesPackage;
import goedegep.util.money.PgCurrency;
import java.time.LocalDate;
import java.util.Objects;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Date Rate Tuplet</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.types.model.impl.DateRateTupletImpl#getDate <em>Date</em>}</li>
 *   <li>{@link goedegep.types.model.impl.DateRateTupletImpl#getRate <em>Rate</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DateRateTupletImpl extends MinimalEObjectImpl.Container implements DateRateTuplet {
  /**
   * The default value of the '{@link #getDate() <em>Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDate()
   * @generated
   * @ordered
   */
  protected static final LocalDate DATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDate() <em>Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDate()
   * @generated
   * @ordered
   */
  protected LocalDate date = DATE_EDEFAULT;

  /**
   * This is true if the Date attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean dateESet;

  /**
   * The default value of the '{@link #getRate() <em>Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRate()
   * @generated
   * @ordered
   */
  protected static final PgCurrency RATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getRate() <em>Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRate()
   * @generated
   * @ordered
   */
  protected PgCurrency rate = RATE_EDEFAULT;

  /**
   * This is true if the Rate attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean rateESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DateRateTupletImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return TypesPackage.Literals.DATE_RATE_TUPLET;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public LocalDate getDate() {
    return date;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDate(LocalDate newDate) {
    LocalDate oldDate = date;
    date = newDate;
    boolean oldDateESet = dateESet;
    dateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.DATE_RATE_TUPLET__DATE, oldDate, date,
          !oldDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDate() {
    LocalDate oldDate = date;
    boolean oldDateESet = dateESet;
    date = DATE_EDEFAULT;
    dateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, TypesPackage.DATE_RATE_TUPLET__DATE, oldDate,
          DATE_EDEFAULT, oldDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDate() {
    return dateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getRate() {
    return rate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setRate(PgCurrency newRate) {
    PgCurrency oldRate = rate;
    rate = newRate;
    boolean oldRateESet = rateESet;
    rateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.DATE_RATE_TUPLET__RATE, oldRate, rate,
          !oldRateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetRate() {
    PgCurrency oldRate = rate;
    boolean oldRateESet = rateESet;
    rate = RATE_EDEFAULT;
    rateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, TypesPackage.DATE_RATE_TUPLET__RATE, oldRate,
          RATE_EDEFAULT, oldRateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetRate() {
    return rateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case TypesPackage.DATE_RATE_TUPLET__DATE:
      return getDate();
    case TypesPackage.DATE_RATE_TUPLET__RATE:
      return getRate();
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
    case TypesPackage.DATE_RATE_TUPLET__DATE:
      setDate((LocalDate) newValue);
      return;
    case TypesPackage.DATE_RATE_TUPLET__RATE:
      setRate((PgCurrency) newValue);
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
    case TypesPackage.DATE_RATE_TUPLET__DATE:
      unsetDate();
      return;
    case TypesPackage.DATE_RATE_TUPLET__RATE:
      unsetRate();
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
    case TypesPackage.DATE_RATE_TUPLET__DATE:
      return isSetDate();
    case TypesPackage.DATE_RATE_TUPLET__RATE:
      return isSetRate();
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
    if (eIsProxy())
      return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (date: ");
    if (dateESet)
      result.append(date);
    else
      result.append("<unset>");
    result.append(", rate: ");
    if (rateESet)
      result.append(rate);
    else
      result.append("<unset>");
    result.append(')');
    return result.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public boolean equals(Object object) {
    if (object == null) {
      return false;
    }

    if (this == object) {
      return true;
    }

    if (getClass() != object.getClass()) {
      return false;
    }

    DateRateTuplet tuplet = (DateRateTuplet) object;

    return date.equals(tuplet.getDate()) && rate.equals(tuplet.getRate());
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public int hashCode() {
    return Objects.hash(date, rate);
  }

} //DateRateTupletImpl
