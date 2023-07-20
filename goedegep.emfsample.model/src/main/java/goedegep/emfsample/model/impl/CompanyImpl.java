/**
 */
package goedegep.emfsample.model.impl;

import goedegep.emfsample.model.Birthday;
import goedegep.emfsample.model.Company;
import goedegep.emfsample.model.EmfSamplePackage;
import goedegep.emfsample.model.Person;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

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
    }
    return super.eIsSet(featureID);
  }

} //CompanyImpl
