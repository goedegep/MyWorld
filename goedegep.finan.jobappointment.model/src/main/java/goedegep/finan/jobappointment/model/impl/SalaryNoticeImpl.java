/**
 */
package goedegep.finan.jobappointment.model.impl;

import goedegep.finan.jobappointment.model.JobAppointmentPackage;
import goedegep.finan.jobappointment.model.SalaryNotice;

import goedegep.util.money.PgCurrency;

import java.time.LocalDate;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Salary Notice</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.jobappointment.model.impl.SalaryNoticeImpl#getStartingDate <em>Starting Date</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.impl.SalaryNoticeImpl#getCurrentSalaryFulltime <em>Current Salary Fulltime</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.impl.SalaryNoticeImpl#getCurrentSalaryParttime <em>Current Salary Parttime</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.impl.SalaryNoticeImpl#getCurrentParttimePercentage <em>Current Parttime Percentage</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.impl.SalaryNoticeImpl#getNewSalaryFulltime <em>New Salary Fulltime</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.impl.SalaryNoticeImpl#getNewSalaryParttime <em>New Salary Parttime</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.impl.SalaryNoticeImpl#getNewParttimePercentage <em>New Parttime Percentage</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.impl.SalaryNoticeImpl#getFunctionGroup <em>Function Group</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SalaryNoticeImpl extends SalaryEventImpl implements SalaryNotice {
  /**
   * The default value of the '{@link #getStartingDate() <em>Starting Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStartingDate()
   * @generated
   * @ordered
   */
  protected static final LocalDate STARTING_DATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getStartingDate() <em>Starting Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStartingDate()
   * @generated
   * @ordered
   */
  protected LocalDate startingDate = STARTING_DATE_EDEFAULT;

  /**
   * The default value of the '{@link #getCurrentSalaryFulltime() <em>Current Salary Fulltime</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCurrentSalaryFulltime()
   * @generated
   * @ordered
   */
  protected static final PgCurrency CURRENT_SALARY_FULLTIME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCurrentSalaryFulltime() <em>Current Salary Fulltime</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCurrentSalaryFulltime()
   * @generated
   * @ordered
   */
  protected PgCurrency currentSalaryFulltime = CURRENT_SALARY_FULLTIME_EDEFAULT;

  /**
   * The default value of the '{@link #getCurrentSalaryParttime() <em>Current Salary Parttime</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCurrentSalaryParttime()
   * @generated
   * @ordered
   */
  protected static final PgCurrency CURRENT_SALARY_PARTTIME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCurrentSalaryParttime() <em>Current Salary Parttime</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCurrentSalaryParttime()
   * @generated
   * @ordered
   */
  protected PgCurrency currentSalaryParttime = CURRENT_SALARY_PARTTIME_EDEFAULT;

  /**
   * The default value of the '{@link #getCurrentParttimePercentage() <em>Current Parttime Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCurrentParttimePercentage()
   * @generated
   * @ordered
   */
  protected static final Integer CURRENT_PARTTIME_PERCENTAGE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCurrentParttimePercentage() <em>Current Parttime Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCurrentParttimePercentage()
   * @generated
   * @ordered
   */
  protected Integer currentParttimePercentage = CURRENT_PARTTIME_PERCENTAGE_EDEFAULT;

  /**
   * The default value of the '{@link #getNewSalaryFulltime() <em>New Salary Fulltime</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNewSalaryFulltime()
   * @generated
   * @ordered
   */
  protected static final PgCurrency NEW_SALARY_FULLTIME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNewSalaryFulltime() <em>New Salary Fulltime</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNewSalaryFulltime()
   * @generated
   * @ordered
   */
  protected PgCurrency newSalaryFulltime = NEW_SALARY_FULLTIME_EDEFAULT;

  /**
   * The default value of the '{@link #getNewSalaryParttime() <em>New Salary Parttime</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNewSalaryParttime()
   * @generated
   * @ordered
   */
  protected static final PgCurrency NEW_SALARY_PARTTIME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNewSalaryParttime() <em>New Salary Parttime</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNewSalaryParttime()
   * @generated
   * @ordered
   */
  protected PgCurrency newSalaryParttime = NEW_SALARY_PARTTIME_EDEFAULT;

  /**
   * The default value of the '{@link #getNewParttimePercentage() <em>New Parttime Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNewParttimePercentage()
   * @generated
   * @ordered
   */
  protected static final Integer NEW_PARTTIME_PERCENTAGE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNewParttimePercentage() <em>New Parttime Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNewParttimePercentage()
   * @generated
   * @ordered
   */
  protected Integer newParttimePercentage = NEW_PARTTIME_PERCENTAGE_EDEFAULT;

  /**
   * The default value of the '{@link #getFunctionGroup() <em>Function Group</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFunctionGroup()
   * @generated
   * @ordered
   */
  protected static final String FUNCTION_GROUP_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFunctionGroup() <em>Function Group</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFunctionGroup()
   * @generated
   * @ordered
   */
  protected String functionGroup = FUNCTION_GROUP_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected SalaryNoticeImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return JobAppointmentPackage.Literals.SALARY_NOTICE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public LocalDate getStartingDate() {
    return startingDate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setStartingDate(LocalDate newStartingDate) {
    LocalDate oldStartingDate = startingDate;
    startingDate = newStartingDate;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JobAppointmentPackage.SALARY_NOTICE__STARTING_DATE, oldStartingDate, startingDate));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getCurrentSalaryFulltime() {
    return currentSalaryFulltime;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCurrentSalaryFulltime(PgCurrency newCurrentSalaryFulltime) {
    PgCurrency oldCurrentSalaryFulltime = currentSalaryFulltime;
    currentSalaryFulltime = newCurrentSalaryFulltime;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JobAppointmentPackage.SALARY_NOTICE__CURRENT_SALARY_FULLTIME, oldCurrentSalaryFulltime, currentSalaryFulltime));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getCurrentSalaryParttime() {
    return currentSalaryParttime;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCurrentSalaryParttime(PgCurrency newCurrentSalaryParttime) {
    PgCurrency oldCurrentSalaryParttime = currentSalaryParttime;
    currentSalaryParttime = newCurrentSalaryParttime;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JobAppointmentPackage.SALARY_NOTICE__CURRENT_SALARY_PARTTIME, oldCurrentSalaryParttime, currentSalaryParttime));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Integer getCurrentParttimePercentage() {
    return currentParttimePercentage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCurrentParttimePercentage(Integer newCurrentParttimePercentage) {
    Integer oldCurrentParttimePercentage = currentParttimePercentage;
    currentParttimePercentage = newCurrentParttimePercentage;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JobAppointmentPackage.SALARY_NOTICE__CURRENT_PARTTIME_PERCENTAGE, oldCurrentParttimePercentage, currentParttimePercentage));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getNewSalaryFulltime() {
    return newSalaryFulltime;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setNewSalaryFulltime(PgCurrency newNewSalaryFulltime) {
    PgCurrency oldNewSalaryFulltime = newSalaryFulltime;
    newSalaryFulltime = newNewSalaryFulltime;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JobAppointmentPackage.SALARY_NOTICE__NEW_SALARY_FULLTIME, oldNewSalaryFulltime, newSalaryFulltime));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getNewSalaryParttime() {
    return newSalaryParttime;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setNewSalaryParttime(PgCurrency newNewSalaryParttime) {
    PgCurrency oldNewSalaryParttime = newSalaryParttime;
    newSalaryParttime = newNewSalaryParttime;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JobAppointmentPackage.SALARY_NOTICE__NEW_SALARY_PARTTIME, oldNewSalaryParttime, newSalaryParttime));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Integer getNewParttimePercentage() {
    return newParttimePercentage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setNewParttimePercentage(Integer newNewParttimePercentage) {
    Integer oldNewParttimePercentage = newParttimePercentage;
    newParttimePercentage = newNewParttimePercentage;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JobAppointmentPackage.SALARY_NOTICE__NEW_PARTTIME_PERCENTAGE, oldNewParttimePercentage, newParttimePercentage));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getFunctionGroup() {
    return functionGroup;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFunctionGroup(String newFunctionGroup) {
    String oldFunctionGroup = functionGroup;
    functionGroup = newFunctionGroup;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JobAppointmentPackage.SALARY_NOTICE__FUNCTION_GROUP, oldFunctionGroup, functionGroup));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case JobAppointmentPackage.SALARY_NOTICE__STARTING_DATE:
        return getStartingDate();
      case JobAppointmentPackage.SALARY_NOTICE__CURRENT_SALARY_FULLTIME:
        return getCurrentSalaryFulltime();
      case JobAppointmentPackage.SALARY_NOTICE__CURRENT_SALARY_PARTTIME:
        return getCurrentSalaryParttime();
      case JobAppointmentPackage.SALARY_NOTICE__CURRENT_PARTTIME_PERCENTAGE:
        return getCurrentParttimePercentage();
      case JobAppointmentPackage.SALARY_NOTICE__NEW_SALARY_FULLTIME:
        return getNewSalaryFulltime();
      case JobAppointmentPackage.SALARY_NOTICE__NEW_SALARY_PARTTIME:
        return getNewSalaryParttime();
      case JobAppointmentPackage.SALARY_NOTICE__NEW_PARTTIME_PERCENTAGE:
        return getNewParttimePercentage();
      case JobAppointmentPackage.SALARY_NOTICE__FUNCTION_GROUP:
        return getFunctionGroup();
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
      case JobAppointmentPackage.SALARY_NOTICE__STARTING_DATE:
        setStartingDate((LocalDate)newValue);
        return;
      case JobAppointmentPackage.SALARY_NOTICE__CURRENT_SALARY_FULLTIME:
        setCurrentSalaryFulltime((PgCurrency)newValue);
        return;
      case JobAppointmentPackage.SALARY_NOTICE__CURRENT_SALARY_PARTTIME:
        setCurrentSalaryParttime((PgCurrency)newValue);
        return;
      case JobAppointmentPackage.SALARY_NOTICE__CURRENT_PARTTIME_PERCENTAGE:
        setCurrentParttimePercentage((Integer)newValue);
        return;
      case JobAppointmentPackage.SALARY_NOTICE__NEW_SALARY_FULLTIME:
        setNewSalaryFulltime((PgCurrency)newValue);
        return;
      case JobAppointmentPackage.SALARY_NOTICE__NEW_SALARY_PARTTIME:
        setNewSalaryParttime((PgCurrency)newValue);
        return;
      case JobAppointmentPackage.SALARY_NOTICE__NEW_PARTTIME_PERCENTAGE:
        setNewParttimePercentage((Integer)newValue);
        return;
      case JobAppointmentPackage.SALARY_NOTICE__FUNCTION_GROUP:
        setFunctionGroup((String)newValue);
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
      case JobAppointmentPackage.SALARY_NOTICE__STARTING_DATE:
        setStartingDate(STARTING_DATE_EDEFAULT);
        return;
      case JobAppointmentPackage.SALARY_NOTICE__CURRENT_SALARY_FULLTIME:
        setCurrentSalaryFulltime(CURRENT_SALARY_FULLTIME_EDEFAULT);
        return;
      case JobAppointmentPackage.SALARY_NOTICE__CURRENT_SALARY_PARTTIME:
        setCurrentSalaryParttime(CURRENT_SALARY_PARTTIME_EDEFAULT);
        return;
      case JobAppointmentPackage.SALARY_NOTICE__CURRENT_PARTTIME_PERCENTAGE:
        setCurrentParttimePercentage(CURRENT_PARTTIME_PERCENTAGE_EDEFAULT);
        return;
      case JobAppointmentPackage.SALARY_NOTICE__NEW_SALARY_FULLTIME:
        setNewSalaryFulltime(NEW_SALARY_FULLTIME_EDEFAULT);
        return;
      case JobAppointmentPackage.SALARY_NOTICE__NEW_SALARY_PARTTIME:
        setNewSalaryParttime(NEW_SALARY_PARTTIME_EDEFAULT);
        return;
      case JobAppointmentPackage.SALARY_NOTICE__NEW_PARTTIME_PERCENTAGE:
        setNewParttimePercentage(NEW_PARTTIME_PERCENTAGE_EDEFAULT);
        return;
      case JobAppointmentPackage.SALARY_NOTICE__FUNCTION_GROUP:
        setFunctionGroup(FUNCTION_GROUP_EDEFAULT);
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
      case JobAppointmentPackage.SALARY_NOTICE__STARTING_DATE:
        return STARTING_DATE_EDEFAULT == null ? startingDate != null : !STARTING_DATE_EDEFAULT.equals(startingDate);
      case JobAppointmentPackage.SALARY_NOTICE__CURRENT_SALARY_FULLTIME:
        return CURRENT_SALARY_FULLTIME_EDEFAULT == null ? currentSalaryFulltime != null : !CURRENT_SALARY_FULLTIME_EDEFAULT.equals(currentSalaryFulltime);
      case JobAppointmentPackage.SALARY_NOTICE__CURRENT_SALARY_PARTTIME:
        return CURRENT_SALARY_PARTTIME_EDEFAULT == null ? currentSalaryParttime != null : !CURRENT_SALARY_PARTTIME_EDEFAULT.equals(currentSalaryParttime);
      case JobAppointmentPackage.SALARY_NOTICE__CURRENT_PARTTIME_PERCENTAGE:
        return CURRENT_PARTTIME_PERCENTAGE_EDEFAULT == null ? currentParttimePercentage != null : !CURRENT_PARTTIME_PERCENTAGE_EDEFAULT.equals(currentParttimePercentage);
      case JobAppointmentPackage.SALARY_NOTICE__NEW_SALARY_FULLTIME:
        return NEW_SALARY_FULLTIME_EDEFAULT == null ? newSalaryFulltime != null : !NEW_SALARY_FULLTIME_EDEFAULT.equals(newSalaryFulltime);
      case JobAppointmentPackage.SALARY_NOTICE__NEW_SALARY_PARTTIME:
        return NEW_SALARY_PARTTIME_EDEFAULT == null ? newSalaryParttime != null : !NEW_SALARY_PARTTIME_EDEFAULT.equals(newSalaryParttime);
      case JobAppointmentPackage.SALARY_NOTICE__NEW_PARTTIME_PERCENTAGE:
        return NEW_PARTTIME_PERCENTAGE_EDEFAULT == null ? newParttimePercentage != null : !NEW_PARTTIME_PERCENTAGE_EDEFAULT.equals(newParttimePercentage);
      case JobAppointmentPackage.SALARY_NOTICE__FUNCTION_GROUP:
        return FUNCTION_GROUP_EDEFAULT == null ? functionGroup != null : !FUNCTION_GROUP_EDEFAULT.equals(functionGroup);
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
    result.append(" (startingDate: ");
    result.append(startingDate);
    result.append(", currentSalaryFulltime: ");
    result.append(currentSalaryFulltime);
    result.append(", currentSalaryParttime: ");
    result.append(currentSalaryParttime);
    result.append(", currentParttimePercentage: ");
    result.append(currentParttimePercentage);
    result.append(", newSalaryFulltime: ");
    result.append(newSalaryFulltime);
    result.append(", newSalaryParttime: ");
    result.append(newSalaryParttime);
    result.append(", newParttimePercentage: ");
    result.append(newParttimePercentage);
    result.append(", functionGroup: ");
    result.append(functionGroup);
    result.append(')');
    return result.toString();
  }

} //SalaryNoticeImpl
