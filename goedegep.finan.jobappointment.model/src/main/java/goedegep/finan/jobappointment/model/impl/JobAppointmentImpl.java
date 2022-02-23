/**
 */
package goedegep.finan.jobappointment.model.impl;

import goedegep.finan.jobappointment.model.JobAppointment;
import goedegep.finan.jobappointment.model.JobAppointmentPackage;
import goedegep.finan.jobappointment.model.SalaryEvent;

import goedegep.util.money.PgCurrency;

import java.time.LocalDate;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Job Appointment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.jobappointment.model.impl.JobAppointmentImpl#getSalaryevents <em>Salaryevents</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.impl.JobAppointmentImpl#getCommencementOfEmploymentDate <em>Commencement Of Employment Date</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.impl.JobAppointmentImpl#getStartingSalary <em>Starting Salary</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.impl.JobAppointmentImpl#getStartingParttimePercentage <em>Starting Parttime Percentage</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JobAppointmentImpl extends MinimalEObjectImpl.Container implements JobAppointment {
  /**
   * The cached value of the '{@link #getSalaryevents() <em>Salaryevents</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSalaryevents()
   * @generated
   * @ordered
   */
  protected EList<SalaryEvent> salaryevents;

  /**
   * The default value of the '{@link #getCommencementOfEmploymentDate() <em>Commencement Of Employment Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCommencementOfEmploymentDate()
   * @generated
   * @ordered
   */
  protected static final LocalDate COMMENCEMENT_OF_EMPLOYMENT_DATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCommencementOfEmploymentDate() <em>Commencement Of Employment Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCommencementOfEmploymentDate()
   * @generated
   * @ordered
   */
  protected LocalDate commencementOfEmploymentDate = COMMENCEMENT_OF_EMPLOYMENT_DATE_EDEFAULT;

  /**
   * This is true if the Commencement Of Employment Date attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean commencementOfEmploymentDateESet;

  /**
   * The default value of the '{@link #getStartingSalary() <em>Starting Salary</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStartingSalary()
   * @generated
   * @ordered
   */
  protected static final PgCurrency STARTING_SALARY_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getStartingSalary() <em>Starting Salary</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStartingSalary()
   * @generated
   * @ordered
   */
  protected PgCurrency startingSalary = STARTING_SALARY_EDEFAULT;

  /**
   * This is true if the Starting Salary attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean startingSalaryESet;

  /**
   * The default value of the '{@link #getStartingParttimePercentage() <em>Starting Parttime Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStartingParttimePercentage()
   * @generated
   * @ordered
   */
  protected static final Integer STARTING_PARTTIME_PERCENTAGE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getStartingParttimePercentage() <em>Starting Parttime Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStartingParttimePercentage()
   * @generated
   * @ordered
   */
  protected Integer startingParttimePercentage = STARTING_PARTTIME_PERCENTAGE_EDEFAULT;

  /**
   * This is true if the Starting Parttime Percentage attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean startingParttimePercentageESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected JobAppointmentImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return JobAppointmentPackage.Literals.JOB_APPOINTMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<SalaryEvent> getSalaryevents() {
    if (salaryevents == null) {
      salaryevents = new EObjectContainmentEList<SalaryEvent>(SalaryEvent.class, this, JobAppointmentPackage.JOB_APPOINTMENT__SALARYEVENTS);
    }
    return salaryevents;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public LocalDate getCommencementOfEmploymentDate() {
    return commencementOfEmploymentDate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCommencementOfEmploymentDate(LocalDate newCommencementOfEmploymentDate) {
    LocalDate oldCommencementOfEmploymentDate = commencementOfEmploymentDate;
    commencementOfEmploymentDate = newCommencementOfEmploymentDate;
    boolean oldCommencementOfEmploymentDateESet = commencementOfEmploymentDateESet;
    commencementOfEmploymentDateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JobAppointmentPackage.JOB_APPOINTMENT__COMMENCEMENT_OF_EMPLOYMENT_DATE, oldCommencementOfEmploymentDate, commencementOfEmploymentDate, !oldCommencementOfEmploymentDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetCommencementOfEmploymentDate() {
    LocalDate oldCommencementOfEmploymentDate = commencementOfEmploymentDate;
    boolean oldCommencementOfEmploymentDateESet = commencementOfEmploymentDateESet;
    commencementOfEmploymentDate = COMMENCEMENT_OF_EMPLOYMENT_DATE_EDEFAULT;
    commencementOfEmploymentDateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, JobAppointmentPackage.JOB_APPOINTMENT__COMMENCEMENT_OF_EMPLOYMENT_DATE, oldCommencementOfEmploymentDate, COMMENCEMENT_OF_EMPLOYMENT_DATE_EDEFAULT, oldCommencementOfEmploymentDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetCommencementOfEmploymentDate() {
    return commencementOfEmploymentDateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getStartingSalary() {
    return startingSalary;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setStartingSalary(PgCurrency newStartingSalary) {
    PgCurrency oldStartingSalary = startingSalary;
    startingSalary = newStartingSalary;
    boolean oldStartingSalaryESet = startingSalaryESet;
    startingSalaryESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JobAppointmentPackage.JOB_APPOINTMENT__STARTING_SALARY, oldStartingSalary, startingSalary, !oldStartingSalaryESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetStartingSalary() {
    PgCurrency oldStartingSalary = startingSalary;
    boolean oldStartingSalaryESet = startingSalaryESet;
    startingSalary = STARTING_SALARY_EDEFAULT;
    startingSalaryESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, JobAppointmentPackage.JOB_APPOINTMENT__STARTING_SALARY, oldStartingSalary, STARTING_SALARY_EDEFAULT, oldStartingSalaryESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetStartingSalary() {
    return startingSalaryESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Integer getStartingParttimePercentage() {
    return startingParttimePercentage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setStartingParttimePercentage(Integer newStartingParttimePercentage) {
    Integer oldStartingParttimePercentage = startingParttimePercentage;
    startingParttimePercentage = newStartingParttimePercentage;
    boolean oldStartingParttimePercentageESet = startingParttimePercentageESet;
    startingParttimePercentageESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JobAppointmentPackage.JOB_APPOINTMENT__STARTING_PARTTIME_PERCENTAGE, oldStartingParttimePercentage, startingParttimePercentage, !oldStartingParttimePercentageESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetStartingParttimePercentage() {
    Integer oldStartingParttimePercentage = startingParttimePercentage;
    boolean oldStartingParttimePercentageESet = startingParttimePercentageESet;
    startingParttimePercentage = STARTING_PARTTIME_PERCENTAGE_EDEFAULT;
    startingParttimePercentageESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, JobAppointmentPackage.JOB_APPOINTMENT__STARTING_PARTTIME_PERCENTAGE, oldStartingParttimePercentage, STARTING_PARTTIME_PERCENTAGE_EDEFAULT, oldStartingParttimePercentageESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetStartingParttimePercentage() {
    return startingParttimePercentageESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case JobAppointmentPackage.JOB_APPOINTMENT__SALARYEVENTS:
        return ((InternalEList<?>)getSalaryevents()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case JobAppointmentPackage.JOB_APPOINTMENT__SALARYEVENTS:
        return getSalaryevents();
      case JobAppointmentPackage.JOB_APPOINTMENT__COMMENCEMENT_OF_EMPLOYMENT_DATE:
        return getCommencementOfEmploymentDate();
      case JobAppointmentPackage.JOB_APPOINTMENT__STARTING_SALARY:
        return getStartingSalary();
      case JobAppointmentPackage.JOB_APPOINTMENT__STARTING_PARTTIME_PERCENTAGE:
        return getStartingParttimePercentage();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case JobAppointmentPackage.JOB_APPOINTMENT__SALARYEVENTS:
        getSalaryevents().clear();
        getSalaryevents().addAll((Collection<? extends SalaryEvent>)newValue);
        return;
      case JobAppointmentPackage.JOB_APPOINTMENT__COMMENCEMENT_OF_EMPLOYMENT_DATE:
        setCommencementOfEmploymentDate((LocalDate)newValue);
        return;
      case JobAppointmentPackage.JOB_APPOINTMENT__STARTING_SALARY:
        setStartingSalary((PgCurrency)newValue);
        return;
      case JobAppointmentPackage.JOB_APPOINTMENT__STARTING_PARTTIME_PERCENTAGE:
        setStartingParttimePercentage((Integer)newValue);
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
      case JobAppointmentPackage.JOB_APPOINTMENT__SALARYEVENTS:
        getSalaryevents().clear();
        return;
      case JobAppointmentPackage.JOB_APPOINTMENT__COMMENCEMENT_OF_EMPLOYMENT_DATE:
        unsetCommencementOfEmploymentDate();
        return;
      case JobAppointmentPackage.JOB_APPOINTMENT__STARTING_SALARY:
        unsetStartingSalary();
        return;
      case JobAppointmentPackage.JOB_APPOINTMENT__STARTING_PARTTIME_PERCENTAGE:
        unsetStartingParttimePercentage();
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
      case JobAppointmentPackage.JOB_APPOINTMENT__SALARYEVENTS:
        return salaryevents != null && !salaryevents.isEmpty();
      case JobAppointmentPackage.JOB_APPOINTMENT__COMMENCEMENT_OF_EMPLOYMENT_DATE:
        return isSetCommencementOfEmploymentDate();
      case JobAppointmentPackage.JOB_APPOINTMENT__STARTING_SALARY:
        return isSetStartingSalary();
      case JobAppointmentPackage.JOB_APPOINTMENT__STARTING_PARTTIME_PERCENTAGE:
        return isSetStartingParttimePercentage();
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
    result.append(" (commencementOfEmploymentDate: ");
    if (commencementOfEmploymentDateESet) result.append(commencementOfEmploymentDate); else result.append("<unset>");
    result.append(", startingSalary: ");
    if (startingSalaryESet) result.append(startingSalary); else result.append("<unset>");
    result.append(", startingParttimePercentage: ");
    if (startingParttimePercentageESet) result.append(startingParttimePercentage); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //JobAppointmentImpl
