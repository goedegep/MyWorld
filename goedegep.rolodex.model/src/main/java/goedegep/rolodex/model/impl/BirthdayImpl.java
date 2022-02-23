/**
 */
package goedegep.rolodex.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import goedegep.rolodex.model.Birthday;
import goedegep.rolodex.model.RolodexPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Birthday</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.impl.BirthdayImpl#getMonth <em>Month</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.BirthdayImpl#getDay <em>Day</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.BirthdayImpl#getYear <em>Year</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BirthdayImpl extends MinimalEObjectImpl.Container implements Birthday {
  /**
   * The default value of the '{@link #getMonth() <em>Month</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMonth()
   * @generated
   * @ordered
   */
  protected static final int MONTH_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getMonth() <em>Month</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMonth()
   * @generated
   * @ordered
   */
  protected int month = MONTH_EDEFAULT;

  /**
   * This is true if the Month attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean monthESet;

  /**
   * The default value of the '{@link #getDay() <em>Day</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDay()
   * @generated
   * @ordered
   */
  protected static final int DAY_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getDay() <em>Day</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDay()
   * @generated
   * @ordered
   */
  protected int day = DAY_EDEFAULT;

  /**
   * This is true if the Day attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean dayESet;

  /**
   * The default value of the '{@link #getYear() <em>Year</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getYear()
   * @generated
   * @ordered
   */
  protected static final int YEAR_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getYear() <em>Year</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getYear()
   * @generated
   * @ordered
   */
  protected int year = YEAR_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected BirthdayImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return RolodexPackage.Literals.BIRTHDAY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int getMonth() {
    return month;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setMonth(int newMonth) {
    int oldMonth = month;
    month = newMonth;
    boolean oldMonthESet = monthESet;
    monthESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.BIRTHDAY__MONTH, oldMonth, month,
          !oldMonthESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetMonth() {
    int oldMonth = month;
    boolean oldMonthESet = monthESet;
    month = MONTH_EDEFAULT;
    monthESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, RolodexPackage.BIRTHDAY__MONTH, oldMonth, MONTH_EDEFAULT,
          oldMonthESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetMonth() {
    return monthESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int getDay() {
    return day;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDay(int newDay) {
    int oldDay = day;
    day = newDay;
    boolean oldDayESet = dayESet;
    dayESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.BIRTHDAY__DAY, oldDay, day, !oldDayESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDay() {
    int oldDay = day;
    boolean oldDayESet = dayESet;
    day = DAY_EDEFAULT;
    dayESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, RolodexPackage.BIRTHDAY__DAY, oldDay, DAY_EDEFAULT,
          oldDayESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDay() {
    return dayESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int getYear() {
    return year;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setYear(int newYear) {
    int oldYear = year;
    year = newYear;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.BIRTHDAY__YEAR, oldYear, year));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case RolodexPackage.BIRTHDAY__MONTH:
      return getMonth();
    case RolodexPackage.BIRTHDAY__DAY:
      return getDay();
    case RolodexPackage.BIRTHDAY__YEAR:
      return getYear();
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
    case RolodexPackage.BIRTHDAY__MONTH:
      setMonth((Integer) newValue);
      return;
    case RolodexPackage.BIRTHDAY__DAY:
      setDay((Integer) newValue);
      return;
    case RolodexPackage.BIRTHDAY__YEAR:
      setYear((Integer) newValue);
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
    case RolodexPackage.BIRTHDAY__MONTH:
      unsetMonth();
      return;
    case RolodexPackage.BIRTHDAY__DAY:
      unsetDay();
      return;
    case RolodexPackage.BIRTHDAY__YEAR:
      setYear(YEAR_EDEFAULT);
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
    case RolodexPackage.BIRTHDAY__MONTH:
      return isSetMonth();
    case RolodexPackage.BIRTHDAY__DAY:
      return isSetDay();
    case RolodexPackage.BIRTHDAY__YEAR:
      return year != YEAR_EDEFAULT;
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
    result.append(" (month: ");
    if (monthESet)
      result.append(month);
    else
      result.append("<unset>");
    result.append(", day: ");
    if (dayESet)
      result.append(day);
    else
      result.append("<unset>");
    result.append(", year: ");
    result.append(year);
    result.append(')');
    return result.toString();
  }

} //BirthdayImpl
