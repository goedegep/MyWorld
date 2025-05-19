/**
 */
package goedegep.emfsample.model.impl;

import goedegep.emfsample.model.Birthday;
import goedegep.emfsample.model.Company;
import goedegep.emfsample.model.EmfSamplePackage;
import goedegep.emfsample.model.Person;

import java.util.Collection;

import java.util.Date;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Company</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.emfsample.model.impl.CompanyImpl#getEmployees <em>Employees</em>}</li>
 *   <li>{@link goedegep.emfsample.model.impl.CompanyImpl#getBirthdays <em>Birthdays</em>}</li>
 *   <li>{@link goedegep.emfsample.model.impl.CompanyImpl#getFormerEmployees <em>Former Employees</em>}</li>
 *   <li>{@link goedegep.emfsample.model.impl.CompanyImpl#getCompanyName <em>Company Name</em>}</li>
 *   <li>{@link goedegep.emfsample.model.impl.CompanyImpl#getDateOfEstablishment <em>Date Of Establishment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompanyImpl extends MinimalEObjectImpl.Container implements Company {
  /**
   * The cached value of the '{@link #getEmployees() <em>Employees</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEmployees()
   * @generated
   * @ordered
   */
  protected EList<Person> employees;

  /**
   * The cached value of the '{@link #getBirthdays() <em>Birthdays</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBirthdays()
   * @generated
   * @ordered
   */
  protected EList<Birthday> birthdays;

  /**
   * The cached value of the '{@link #getFormerEmployees() <em>Former Employees</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFormerEmployees()
   * @generated
   * @ordered
   */
  protected EList<Person> formerEmployees;

  /**
   * The default value of the '{@link #getCompanyName() <em>Company Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompanyName()
   * @generated
   * @ordered
   */
  protected static final String COMPANY_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCompanyName() <em>Company Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompanyName()
   * @generated
   * @ordered
   */
  protected String companyName = COMPANY_NAME_EDEFAULT;

  /**
   * This is true if the Company Name attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean companyNameESet;

  /**
   * The default value of the '{@link #getDateOfEstablishment() <em>Date Of Establishment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDateOfEstablishment()
   * @generated
   * @ordered
   */
  protected static final Date DATE_OF_ESTABLISHMENT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDateOfEstablishment() <em>Date Of Establishment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDateOfEstablishment()
   * @generated
   * @ordered
   */
  protected Date dateOfEstablishment = DATE_OF_ESTABLISHMENT_EDEFAULT;

  /**
   * This is true if the Date Of Establishment attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean dateOfEstablishmentESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CompanyImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return EmfSamplePackage.Literals.COMPANY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Person> getEmployees() {
    if (employees == null) {
      employees = new EObjectContainmentEList<Person>(Person.class, this, EmfSamplePackage.COMPANY__EMPLOYEES);
    }
    return employees;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Birthday> getBirthdays() {
    if (birthdays == null) {
      birthdays = new EObjectResolvingEList<Birthday>(Birthday.class, this, EmfSamplePackage.COMPANY__BIRTHDAYS);
    }
    return birthdays;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Person> getFormerEmployees() {
    if (formerEmployees == null) {
      formerEmployees = new EObjectContainmentEList<Person>(Person.class, this, EmfSamplePackage.COMPANY__FORMER_EMPLOYEES);
    }
    return formerEmployees;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getCompanyName() {
    return companyName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCompanyName(String newCompanyName) {
    String oldCompanyName = companyName;
    companyName = newCompanyName;
    boolean oldCompanyNameESet = companyNameESet;
    companyNameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EmfSamplePackage.COMPANY__COMPANY_NAME, oldCompanyName, companyName, !oldCompanyNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetCompanyName() {
    String oldCompanyName = companyName;
    boolean oldCompanyNameESet = companyNameESet;
    companyName = COMPANY_NAME_EDEFAULT;
    companyNameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, EmfSamplePackage.COMPANY__COMPANY_NAME, oldCompanyName, COMPANY_NAME_EDEFAULT, oldCompanyNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetCompanyName() {
    return companyNameESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Date getDateOfEstablishment() {
    return dateOfEstablishment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDateOfEstablishment(Date newDateOfEstablishment) {
    Date oldDateOfEstablishment = dateOfEstablishment;
    dateOfEstablishment = newDateOfEstablishment;
    boolean oldDateOfEstablishmentESet = dateOfEstablishmentESet;
    dateOfEstablishmentESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EmfSamplePackage.COMPANY__DATE_OF_ESTABLISHMENT, oldDateOfEstablishment, dateOfEstablishment, !oldDateOfEstablishmentESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDateOfEstablishment() {
    Date oldDateOfEstablishment = dateOfEstablishment;
    boolean oldDateOfEstablishmentESet = dateOfEstablishmentESet;
    dateOfEstablishment = DATE_OF_ESTABLISHMENT_EDEFAULT;
    dateOfEstablishmentESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, EmfSamplePackage.COMPANY__DATE_OF_ESTABLISHMENT, oldDateOfEstablishment, DATE_OF_ESTABLISHMENT_EDEFAULT, oldDateOfEstablishmentESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDateOfEstablishment() {
    return dateOfEstablishmentESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case EmfSamplePackage.COMPANY__EMPLOYEES:
        return ((InternalEList<?>)getEmployees()).basicRemove(otherEnd, msgs);
      case EmfSamplePackage.COMPANY__FORMER_EMPLOYEES:
        return ((InternalEList<?>)getFormerEmployees()).basicRemove(otherEnd, msgs);
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
      case EmfSamplePackage.COMPANY__EMPLOYEES:
        return getEmployees();
      case EmfSamplePackage.COMPANY__BIRTHDAYS:
        return getBirthdays();
      case EmfSamplePackage.COMPANY__FORMER_EMPLOYEES:
        return getFormerEmployees();
      case EmfSamplePackage.COMPANY__COMPANY_NAME:
        return getCompanyName();
      case EmfSamplePackage.COMPANY__DATE_OF_ESTABLISHMENT:
        return getDateOfEstablishment();
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
      case EmfSamplePackage.COMPANY__EMPLOYEES:
        getEmployees().clear();
        getEmployees().addAll((Collection<? extends Person>)newValue);
        return;
      case EmfSamplePackage.COMPANY__BIRTHDAYS:
        getBirthdays().clear();
        getBirthdays().addAll((Collection<? extends Birthday>)newValue);
        return;
      case EmfSamplePackage.COMPANY__FORMER_EMPLOYEES:
        getFormerEmployees().clear();
        getFormerEmployees().addAll((Collection<? extends Person>)newValue);
        return;
      case EmfSamplePackage.COMPANY__COMPANY_NAME:
        setCompanyName((String)newValue);
        return;
      case EmfSamplePackage.COMPANY__DATE_OF_ESTABLISHMENT:
        setDateOfEstablishment((Date)newValue);
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
      case EmfSamplePackage.COMPANY__EMPLOYEES:
        getEmployees().clear();
        return;
      case EmfSamplePackage.COMPANY__BIRTHDAYS:
        getBirthdays().clear();
        return;
      case EmfSamplePackage.COMPANY__FORMER_EMPLOYEES:
        getFormerEmployees().clear();
        return;
      case EmfSamplePackage.COMPANY__COMPANY_NAME:
        unsetCompanyName();
        return;
      case EmfSamplePackage.COMPANY__DATE_OF_ESTABLISHMENT:
        unsetDateOfEstablishment();
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
      case EmfSamplePackage.COMPANY__EMPLOYEES:
        return employees != null && !employees.isEmpty();
      case EmfSamplePackage.COMPANY__BIRTHDAYS:
        return birthdays != null && !birthdays.isEmpty();
      case EmfSamplePackage.COMPANY__FORMER_EMPLOYEES:
        return formerEmployees != null && !formerEmployees.isEmpty();
      case EmfSamplePackage.COMPANY__COMPANY_NAME:
        return isSetCompanyName();
      case EmfSamplePackage.COMPANY__DATE_OF_ESTABLISHMENT:
        return isSetDateOfEstablishment();
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
    result.append(" (companyName: ");
    if (companyNameESet) result.append(companyName); else result.append("<unset>");
    result.append(", dateOfEstablishment: ");
    if (dateOfEstablishmentESet) result.append(dateOfEstablishment); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //CompanyImpl
