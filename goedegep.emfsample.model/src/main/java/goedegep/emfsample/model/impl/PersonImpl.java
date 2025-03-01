/**
 */
package goedegep.emfsample.model.impl;

import goedegep.emfsample.model.Birthday;
import goedegep.emfsample.model.EmfSamplePackage;
import goedegep.emfsample.model.Gender;
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
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Person</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.emfsample.model.impl.PersonImpl#getFirstnames <em>Firstnames</em>}</li>
 *   <li>{@link goedegep.emfsample.model.impl.PersonImpl#getSurname <em>Surname</em>}</li>
 *   <li>{@link goedegep.emfsample.model.impl.PersonImpl#getGender <em>Gender</em>}</li>
 *   <li>{@link goedegep.emfsample.model.impl.PersonImpl#getBirthday <em>Birthday</em>}</li>
 *   <li>{@link goedegep.emfsample.model.impl.PersonImpl#getRetirementDate <em>Retirement Date</em>}</li>
 *   <li>{@link goedegep.emfsample.model.impl.PersonImpl#isHasChildren <em>Has Children</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PersonImpl extends MinimalEObjectImpl.Container implements Person {
  /**
   * The cached value of the '{@link #getFirstnames() <em>Firstnames</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFirstnames()
   * @generated
   * @ordered
   */
  protected EList<String> firstnames;

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
   * The cached value of the '{@link #getBirthday() <em>Birthday</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBirthday()
   * @generated
   * @ordered
   */
  protected Birthday birthday;

  /**
   * The default value of the '{@link #getRetirementDate() <em>Retirement Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRetirementDate()
   * @generated
   * @ordered
   */
  protected static final Date RETIREMENT_DATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getRetirementDate() <em>Retirement Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRetirementDate()
   * @generated
   * @ordered
   */
  protected Date retirementDate = RETIREMENT_DATE_EDEFAULT;

  /**
   * This is true if the Retirement Date attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean retirementDateESet;

  /**
   * The default value of the '{@link #isHasChildren() <em>Has Children</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isHasChildren()
   * @generated
   * @ordered
   */
  protected static final boolean HAS_CHILDREN_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isHasChildren() <em>Has Children</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isHasChildren()
   * @generated
   * @ordered
   */
  protected boolean hasChildren = HAS_CHILDREN_EDEFAULT;

  /**
   * This is true if the Has Children attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean hasChildrenESet;

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
  public EList<String> getFirstnames() {
    if (firstnames == null) {
      firstnames = new EDataTypeUniqueEList.Unsettable<String>(String.class, this, EmfSamplePackage.PERSON__FIRSTNAMES);
    }
    return firstnames;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetFirstnames() {
    if (firstnames != null) ((InternalEList.Unsettable<?>)firstnames).unset();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetFirstnames() {
    return firstnames != null && ((InternalEList.Unsettable<?>)firstnames).isSet();
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
    return birthday;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetBirthday(Birthday newBirthday, NotificationChain msgs) {
    Birthday oldBirthday = birthday;
    birthday = newBirthday;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EmfSamplePackage.PERSON__BIRTHDAY, oldBirthday, newBirthday);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setBirthday(Birthday newBirthday) {
    if (newBirthday != birthday) {
      NotificationChain msgs = null;
      if (birthday != null)
        msgs = ((InternalEObject)birthday).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EmfSamplePackage.PERSON__BIRTHDAY, null, msgs);
      if (newBirthday != null)
        msgs = ((InternalEObject)newBirthday).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EmfSamplePackage.PERSON__BIRTHDAY, null, msgs);
      msgs = basicSetBirthday(newBirthday, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EmfSamplePackage.PERSON__BIRTHDAY, newBirthday, newBirthday));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Date getRetirementDate() {
    return retirementDate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setRetirementDate(Date newRetirementDate) {
    Date oldRetirementDate = retirementDate;
    retirementDate = newRetirementDate;
    boolean oldRetirementDateESet = retirementDateESet;
    retirementDateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EmfSamplePackage.PERSON__RETIREMENT_DATE, oldRetirementDate, retirementDate, !oldRetirementDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetRetirementDate() {
    Date oldRetirementDate = retirementDate;
    boolean oldRetirementDateESet = retirementDateESet;
    retirementDate = RETIREMENT_DATE_EDEFAULT;
    retirementDateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, EmfSamplePackage.PERSON__RETIREMENT_DATE, oldRetirementDate, RETIREMENT_DATE_EDEFAULT, oldRetirementDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetRetirementDate() {
    return retirementDateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isHasChildren() {
    return hasChildren;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setHasChildren(boolean newHasChildren) {
    boolean oldHasChildren = hasChildren;
    hasChildren = newHasChildren;
    boolean oldHasChildrenESet = hasChildrenESet;
    hasChildrenESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EmfSamplePackage.PERSON__HAS_CHILDREN, oldHasChildren, hasChildren, !oldHasChildrenESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetHasChildren() {
    boolean oldHasChildren = hasChildren;
    boolean oldHasChildrenESet = hasChildrenESet;
    hasChildren = HAS_CHILDREN_EDEFAULT;
    hasChildrenESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, EmfSamplePackage.PERSON__HAS_CHILDREN, oldHasChildren, HAS_CHILDREN_EDEFAULT, oldHasChildrenESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetHasChildren() {
    return hasChildrenESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case EmfSamplePackage.PERSON__BIRTHDAY:
        return basicSetBirthday(null, msgs);
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
      case EmfSamplePackage.PERSON__FIRSTNAMES:
        return getFirstnames();
      case EmfSamplePackage.PERSON__SURNAME:
        return getSurname();
      case EmfSamplePackage.PERSON__GENDER:
        return getGender();
      case EmfSamplePackage.PERSON__BIRTHDAY:
        return getBirthday();
      case EmfSamplePackage.PERSON__RETIREMENT_DATE:
        return getRetirementDate();
      case EmfSamplePackage.PERSON__HAS_CHILDREN:
        return isHasChildren();
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
      case EmfSamplePackage.PERSON__FIRSTNAMES:
        getFirstnames().clear();
        getFirstnames().addAll((Collection<? extends String>)newValue);
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
      case EmfSamplePackage.PERSON__RETIREMENT_DATE:
        setRetirementDate((Date)newValue);
        return;
      case EmfSamplePackage.PERSON__HAS_CHILDREN:
        setHasChildren((Boolean)newValue);
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
      case EmfSamplePackage.PERSON__FIRSTNAMES:
        unsetFirstnames();
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
      case EmfSamplePackage.PERSON__RETIREMENT_DATE:
        unsetRetirementDate();
        return;
      case EmfSamplePackage.PERSON__HAS_CHILDREN:
        unsetHasChildren();
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
      case EmfSamplePackage.PERSON__FIRSTNAMES:
        return isSetFirstnames();
      case EmfSamplePackage.PERSON__SURNAME:
        return isSetSurname();
      case EmfSamplePackage.PERSON__GENDER:
        return isSetGender();
      case EmfSamplePackage.PERSON__BIRTHDAY:
        return birthday != null;
      case EmfSamplePackage.PERSON__RETIREMENT_DATE:
        return isSetRetirementDate();
      case EmfSamplePackage.PERSON__HAS_CHILDREN:
        return isSetHasChildren();
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
    result.append(" (firstnames: ");
    result.append(firstnames);
    result.append(", surname: ");
    if (surnameESet) result.append(surname); else result.append("<unset>");
    result.append(", gender: ");
    if (genderESet) result.append(gender); else result.append("<unset>");
    result.append(", retirementDate: ");
    if (retirementDateESet) result.append(retirementDate); else result.append("<unset>");
    result.append(", hasChildren: ");
    if (hasChildrenESet) result.append(hasChildren); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //PersonImpl
