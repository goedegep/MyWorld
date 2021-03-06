/**
 */
package goedegep.emfsample.model.impl;

import goedegep.emfsample.model.Birthday;
import goedegep.emfsample.model.EmfSamplePackage;
import goedegep.emfsample.model.Gender;
import goedegep.emfsample.model.Person;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Person</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.emfsample.model.impl.PersonImpl#getFirstname <em>Firstname</em>}</li>
 *   <li>{@link goedegep.emfsample.model.impl.PersonImpl#getSurname <em>Surname</em>}</li>
 *   <li>{@link goedegep.emfsample.model.impl.PersonImpl#getGender <em>Gender</em>}</li>
 *   <li>{@link goedegep.emfsample.model.impl.PersonImpl#getBirthday <em>Birthday</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PersonImpl extends MinimalEObjectImpl.Container implements Person {
  /**
   * The default value of the '{@link #getFirstname() <em>Firstname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFirstname()
   * @generated
   * @ordered
   */
  protected static final String FIRSTNAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFirstname() <em>Firstname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFirstname()
   * @generated
   * @ordered
   */
  protected String firstname = FIRSTNAME_EDEFAULT;

  /**
   * This is true if the Firstname attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean firstnameESet;

  /**
   * The default value of the '{@link #getSurname() <em>Surname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSurname()
   * @generated
   * @ordered
   */
  protected static final String SURNAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getSurname() <em>Surname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSurname()
   * @generated
   * @ordered
   */
  protected String surname = SURNAME_EDEFAULT;

  /**
   * This is true if the Surname attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean surnameESet;

  /**
   * The default value of the '{@link #getGender() <em>Gender</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGender()
   * @generated
   * @ordered
   */
  protected static final Gender GENDER_EDEFAULT = Gender.FEMALE;

  /**
   * The cached value of the '{@link #getGender() <em>Gender</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGender()
   * @generated
   * @ordered
   */
  protected Gender gender = GENDER_EDEFAULT;

  /**
   * This is true if the Gender attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean genderESet;

  /**
   * The cached value of the '{@link #getBirthday() <em>Birthday</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBirthday()
   * @generated
   * @ordered
   */
  protected Birthday birthday;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PersonImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return EmfSamplePackage.Literals.PERSON;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getFirstname() {
    return firstname;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFirstname(String newFirstname) {
    String oldFirstname = firstname;
    firstname = newFirstname;
    boolean oldFirstnameESet = firstnameESet;
    firstnameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EmfSamplePackage.PERSON__FIRSTNAME, oldFirstname, firstname, !oldFirstnameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetFirstname() {
    String oldFirstname = firstname;
    boolean oldFirstnameESet = firstnameESet;
    firstname = FIRSTNAME_EDEFAULT;
    firstnameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, EmfSamplePackage.PERSON__FIRSTNAME, oldFirstname, FIRSTNAME_EDEFAULT, oldFirstnameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetFirstname() {
    return firstnameESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getSurname() {
    return surname;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setSurname(String newSurname) {
    String oldSurname = surname;
    surname = newSurname;
    boolean oldSurnameESet = surnameESet;
    surnameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EmfSamplePackage.PERSON__SURNAME, oldSurname, surname, !oldSurnameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetSurname() {
    String oldSurname = surname;
    boolean oldSurnameESet = surnameESet;
    surname = SURNAME_EDEFAULT;
    surnameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, EmfSamplePackage.PERSON__SURNAME, oldSurname, SURNAME_EDEFAULT, oldSurnameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetSurname() {
    return surnameESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Gender getGender() {
    return gender;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setGender(Gender newGender) {
    Gender oldGender = gender;
    gender = newGender == null ? GENDER_EDEFAULT : newGender;
    boolean oldGenderESet = genderESet;
    genderESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EmfSamplePackage.PERSON__GENDER, oldGender, gender, !oldGenderESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetGender() {
    Gender oldGender = gender;
    boolean oldGenderESet = genderESet;
    gender = GENDER_EDEFAULT;
    genderESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, EmfSamplePackage.PERSON__GENDER, oldGender, GENDER_EDEFAULT, oldGenderESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetGender() {
    return genderESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Birthday getBirthday() {
    if (birthday != null && birthday.eIsProxy()) {
      InternalEObject oldBirthday = (InternalEObject)birthday;
      birthday = (Birthday)eResolveProxy(oldBirthday);
      if (birthday != oldBirthday) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, EmfSamplePackage.PERSON__BIRTHDAY, oldBirthday, birthday));
      }
    }
    return birthday;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Birthday basicGetBirthday() {
    return birthday;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setBirthday(Birthday newBirthday) {
    Birthday oldBirthday = birthday;
    birthday = newBirthday;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EmfSamplePackage.PERSON__BIRTHDAY, oldBirthday, birthday));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case EmfSamplePackage.PERSON__FIRSTNAME:
        return getFirstname();
      case EmfSamplePackage.PERSON__SURNAME:
        return getSurname();
      case EmfSamplePackage.PERSON__GENDER:
        return getGender();
      case EmfSamplePackage.PERSON__BIRTHDAY:
        if (resolve) return getBirthday();
        return basicGetBirthday();
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
      case EmfSamplePackage.PERSON__FIRSTNAME:
        setFirstname((String)newValue);
        return;
      case EmfSamplePackage.PERSON__SURNAME:
        setSurname((String)newValue);
        return;
      case EmfSamplePackage.PERSON__GENDER:
        setGender((Gender)newValue);
        return;
      case EmfSamplePackage.PERSON__BIRTHDAY:
        setBirthday((Birthday)newValue);
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
      case EmfSamplePackage.PERSON__FIRSTNAME:
        unsetFirstname();
        return;
      case EmfSamplePackage.PERSON__SURNAME:
        unsetSurname();
        return;
      case EmfSamplePackage.PERSON__GENDER:
        unsetGender();
        return;
      case EmfSamplePackage.PERSON__BIRTHDAY:
        setBirthday((Birthday)null);
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
      case EmfSamplePackage.PERSON__FIRSTNAME:
        return isSetFirstname();
      case EmfSamplePackage.PERSON__SURNAME:
        return isSetSurname();
      case EmfSamplePackage.PERSON__GENDER:
        return isSetGender();
      case EmfSamplePackage.PERSON__BIRTHDAY:
        return birthday != null;
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
    result.append(" (firstname: ");
    if (firstnameESet) result.append(firstname); else result.append("<unset>");
    result.append(", surname: ");
    if (surnameESet) result.append(surname); else result.append("<unset>");
    result.append(", gender: ");
    if (genderESet) result.append(gender); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //PersonImpl
