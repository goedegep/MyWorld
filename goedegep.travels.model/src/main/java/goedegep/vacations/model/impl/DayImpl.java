/**
 */
package goedegep.vacations.model.impl;

import goedegep.util.datetime.DateUtil;
import goedegep.util.datetime.FlexDate;
import goedegep.vacations.model.Day;
import goedegep.vacations.model.Vacation;
import goedegep.vacations.model.VacationsPackage;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Date;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Vacation Element Day</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.model.impl.DayImpl#getDays <em>Days</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.DayImpl#getTitle <em>Title</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DayImpl extends VacationElementImpl implements Day {
  /**
   * The default value of the '{@link #getDays() <em>Days</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDays()
   * @generated
   * @ordered
   */
  protected static final Integer DAYS_EDEFAULT = new Integer(1);
  /**
   * The cached value of the '{@link #getDays() <em>Days</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDays()
   * @generated
   * @ordered
   */
  protected Integer days = DAYS_EDEFAULT;
  /**
   * This is true if the Days attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean daysESet;

  /**
   * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTitle()
   * @generated
   * @ordered
   */
  protected static final String TITLE_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTitle()
   * @generated
   * @ordered
   */
  protected String title = TITLE_EDEFAULT;
  /**
   * This is true if the Title attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean titleESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DayImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return VacationsPackage.Literals.DAY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Integer getDays() {
    return days;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDays(Integer newDays) {
    Integer oldDays = days;
    days = newDays;
    boolean oldDaysESet = daysESet;
    daysESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.DAY__DAYS, oldDays, days, !oldDaysESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDays() {
    Integer oldDays = days;
    boolean oldDaysESet = daysESet;
    days = DAYS_EDEFAULT;
    daysESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, VacationsPackage.DAY__DAYS, oldDays, DAYS_EDEFAULT,
          oldDaysESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDays() {
    return daysESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getTitle() {
    return title;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTitle(String newTitle) {
    String oldTitle = title;
    title = newTitle;
    boolean oldTitleESet = titleESet;
    titleESet = true;
    if (eNotificationRequired())
      eNotify(
          new ENotificationImpl(this, Notification.SET, VacationsPackage.DAY__TITLE, oldTitle, title, !oldTitleESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetTitle() {
    String oldTitle = title;
    boolean oldTitleESet = titleESet;
    title = TITLE_EDEFAULT;
    titleESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, VacationsPackage.DAY__TITLE, oldTitle, TITLE_EDEFAULT,
          oldTitleESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetTitle() {
    return titleESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public Date getDate() {
    Vacation vacation = getVacation();
    FlexDate startDate = vacation.getDate();
    if ((startDate == null) || !startDate.isDaySet()) {
      return null;
    }
    int dayNr = getDayNr();
    LocalDate localStartDate = DateUtil.flexDateToLocalDate(startDate);
    localStartDate = localStartDate.plusDays(dayNr - 1);

    return DateUtil.localDateToDate(localStartDate);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case VacationsPackage.DAY__DAYS:
      return getDays();
    case VacationsPackage.DAY__TITLE:
      return getTitle();
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
    case VacationsPackage.DAY__DAYS:
      setDays((Integer) newValue);
      return;
    case VacationsPackage.DAY__TITLE:
      setTitle((String) newValue);
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
    case VacationsPackage.DAY__DAYS:
      unsetDays();
      return;
    case VacationsPackage.DAY__TITLE:
      unsetTitle();
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
    case VacationsPackage.DAY__DAYS:
      return isSetDays();
    case VacationsPackage.DAY__TITLE:
      return isSetTitle();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
    switch (operationID) {
    case VacationsPackage.DAY___GET_DATE:
      return getDate();
    }
    return super.eInvoke(operationID, arguments);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();

    buf.append("Title: ").append(getTitle() != null ? getTitle().toString() : "(null)");
    buf.append(", Nr. of days: ").append(getDays() != null ? getDays().toString() : "(null)");

    return buf.toString();
  }

} //VacationElementDayImpl
