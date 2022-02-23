/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.rolodex.model.Employee;
import goedegep.rolodex.model.Institution;
import goedegep.rolodex.model.Person;
import goedegep.rolodex.model.PhoneNumber;
import goedegep.rolodex.model.RolodexPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Employee</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.impl.EmployeeImpl#getPhoneNumbers <em>Phone Numbers</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.EmployeeImpl#getInstitution <em>Institution</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.EmployeeImpl#getPerson <em>Person</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EmployeeImpl extends MinimalEObjectImpl.Container implements Employee {
  /**
   * The cached value of the '{@link #getPhoneNumbers() <em>Phone Numbers</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPhoneNumbers()
   * @generated
   * @ordered
   */
  protected EList<PhoneNumber> phoneNumbers;

  /**
   * The cached value of the '{@link #getInstitution() <em>Institution</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInstitution()
   * @generated
   * @ordered
   */
  protected Institution institution;

  /**
   * The cached value of the '{@link #getPerson() <em>Person</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPerson()
   * @generated
   * @ordered
   */
  protected Person person;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected EmployeeImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return RolodexPackage.Literals.EMPLOYEE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<PhoneNumber> getPhoneNumbers() {
    if (phoneNumbers == null) {
      phoneNumbers = new EObjectWithInverseResolvingEList.ManyInverse<PhoneNumber>(PhoneNumber.class, this,
          RolodexPackage.EMPLOYEE__PHONE_NUMBERS, RolodexPackage.PHONE_NUMBER__NUMBER_HOLDERS);
    }
    return phoneNumbers;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Institution getInstitution() {
    if (institution != null && institution.eIsProxy()) {
      InternalEObject oldInstitution = (InternalEObject) institution;
      institution = (Institution) eResolveProxy(oldInstitution);
      if (institution != oldInstitution) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, RolodexPackage.EMPLOYEE__INSTITUTION,
              oldInstitution, institution));
      }
    }
    return institution;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Institution basicGetInstitution() {
    return institution;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setInstitution(Institution newInstitution) {
    Institution oldInstitution = institution;
    institution = newInstitution;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.EMPLOYEE__INSTITUTION, oldInstitution,
          institution));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Person basicGetPerson() {
    return person;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Person getPerson() {
    if (person != null && person.eIsProxy()) {
      InternalEObject oldPerson = (InternalEObject) person;
      person = (Person) eResolveProxy(oldPerson);
      if (person != oldPerson) {
        if (eNotificationRequired())
          eNotify(
              new ENotificationImpl(this, Notification.RESOLVE, RolodexPackage.EMPLOYEE__PERSON, oldPerson, person));
      }
    }
    return person;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPerson(Person newPerson) {
    Person oldPerson = person;
    person = newPerson;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.EMPLOYEE__PERSON, oldPerson, person));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case RolodexPackage.EMPLOYEE__PHONE_NUMBERS:
      return ((InternalEList<InternalEObject>) (InternalEList<?>) getPhoneNumbers()).basicAdd(otherEnd, msgs);
    }
    return super.eInverseAdd(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case RolodexPackage.EMPLOYEE__PHONE_NUMBERS:
      return ((InternalEList<?>) getPhoneNumbers()).basicRemove(otherEnd, msgs);
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
    case RolodexPackage.EMPLOYEE__PHONE_NUMBERS:
      return getPhoneNumbers();
    case RolodexPackage.EMPLOYEE__INSTITUTION:
      if (resolve)
        return getInstitution();
      return basicGetInstitution();
    case RolodexPackage.EMPLOYEE__PERSON:
      if (resolve)
        return getPerson();
      return basicGetPerson();
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
    case RolodexPackage.EMPLOYEE__PHONE_NUMBERS:
      getPhoneNumbers().clear();
      getPhoneNumbers().addAll((Collection<? extends PhoneNumber>) newValue);
      return;
    case RolodexPackage.EMPLOYEE__INSTITUTION:
      setInstitution((Institution) newValue);
      return;
    case RolodexPackage.EMPLOYEE__PERSON:
      setPerson((Person) newValue);
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
    case RolodexPackage.EMPLOYEE__PHONE_NUMBERS:
      getPhoneNumbers().clear();
      return;
    case RolodexPackage.EMPLOYEE__INSTITUTION:
      setInstitution((Institution) null);
      return;
    case RolodexPackage.EMPLOYEE__PERSON:
      setPerson((Person) null);
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
    case RolodexPackage.EMPLOYEE__PHONE_NUMBERS:
      return phoneNumbers != null && !phoneNumbers.isEmpty();
    case RolodexPackage.EMPLOYEE__INSTITUTION:
      return institution != null;
    case RolodexPackage.EMPLOYEE__PERSON:
      return person != null;
    }
    return super.eIsSet(featureID);
  }

  public String toString() {
    StringBuffer result = new StringBuffer();

    if (eIsSet(RolodexPackage.EMPLOYEE__PERSON)) {
      result.append(getPerson().getName());
    } else {
      result.append("-");
    }
    result.append(" bij ");
    result.append(getInstitution().getName());

    return result.toString();
  }

} //EmployeeImpl
