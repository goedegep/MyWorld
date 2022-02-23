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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.AddressForPeriod;
import goedegep.rolodex.model.AddressHolder;
import goedegep.rolodex.model.Archive;
import goedegep.rolodex.model.Birthday;
import goedegep.rolodex.model.Description;
import goedegep.rolodex.model.Family;
import goedegep.rolodex.model.Gender;
import goedegep.rolodex.model.Person;
import goedegep.rolodex.model.PhoneNumber;
import goedegep.rolodex.model.RolodexPackage;

import java.lang.reflect.InvocationTargetException;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Person</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.impl.PersonImpl#getPhoneNumbers <em>Phone Numbers</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.PersonImpl#getAddress <em>Address</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.PersonImpl#getPreviousAddresses <em>Previous Addresses</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.PersonImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.PersonImpl#isArchived <em>Archived</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.PersonImpl#getFirstname <em>Firstname</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.PersonImpl#getInfix <em>Infix</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.PersonImpl#getSurname <em>Surname</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.PersonImpl#getInitials <em>Initials</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.PersonImpl#getGender <em>Gender</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.PersonImpl#getId <em>Id</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.PersonImpl#getFamily <em>Family</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.PersonImpl#getBirthday <em>Birthday</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PersonImpl extends MinimalEObjectImpl.Container implements Person {

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
   * The cached value of the '{@link #getAddress() <em>Address</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAddress()
   * @generated
   * @ordered
   */
  protected Address address;

  /**
   * The cached value of the '{@link #getPreviousAddresses() <em>Previous Addresses</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPreviousAddresses()
   * @generated
   * @ordered
   */
  protected EList<AddressForPeriod> previousAddresses;

  /**
   * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected static final String DESCRIPTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected String description = DESCRIPTION_EDEFAULT;

  /**
   * This is true if the Description attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean descriptionESet;

  /**
   * The default value of the '{@link #isArchived() <em>Archived</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isArchived()
   * @generated
   * @ordered
   */
  protected static final boolean ARCHIVED_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isArchived() <em>Archived</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isArchived()
   * @generated
   * @ordered
   */
  protected boolean archived = ARCHIVED_EDEFAULT;

  /**
   * This is true if the Archived attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean archivedESet;

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
   * The default value of the '{@link #getInfix() <em>Infix</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInfix()
   * @generated
   * @ordered
   */
  protected static final String INFIX_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getInfix() <em>Infix</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInfix()
   * @generated
   * @ordered
   */
  protected String infix = INFIX_EDEFAULT;

  /**
   * This is true if the Infix attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean infixESet;

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
   * The default value of the '{@link #getInitials() <em>Initials</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInitials()
   * @generated
   * @ordered
   */
  protected static final String INITIALS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getInitials() <em>Initials</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInitials()
   * @generated
   * @ordered
   */
  protected String initials = INITIALS_EDEFAULT;

  /**
   * This is true if the Initials attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean initialsESet;

  /**
   * The default value of the '{@link #getGender() <em>Gender</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGender()
   * @generated
   * @ordered
   */
  protected static final Gender GENDER_EDEFAULT = Gender.MALE;

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
   * The default value of the '{@link #getId() <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getId()
   * @generated
   * @ordered
   */
  protected static final String ID_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getId()
   * @generated
   * @ordered
   */
  protected String id = ID_EDEFAULT;

  /**
   * This is true if the Id attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean idESet;

  /**
   * The cached value of the '{@link #getFamily() <em>Family</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFamily()
   * @generated
   * @ordered
   */
  protected Family family;

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
   * This is true if the Birthday containment reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean birthdayESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  protected PersonImpl() {
    super();
    setId(EcoreUtil.generateUUID());
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return RolodexPackage.Literals.PERSON;
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
          RolodexPackage.PERSON__PHONE_NUMBERS, RolodexPackage.PHONE_NUMBER__NUMBER_HOLDERS);
    }
    return phoneNumbers;
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
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.PERSON__FIRSTNAME, oldFirstname, firstname,
          !oldFirstnameESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, RolodexPackage.PERSON__FIRSTNAME, oldFirstname,
          FIRSTNAME_EDEFAULT, oldFirstnameESet));
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
  public String getInfix() {
    return infix;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setInfix(String newInfix) {
    String oldInfix = infix;
    infix = newInfix;
    boolean oldInfixESet = infixESet;
    infixESet = true;
    if (eNotificationRequired())
      eNotify(
          new ENotificationImpl(this, Notification.SET, RolodexPackage.PERSON__INFIX, oldInfix, infix, !oldInfixESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetInfix() {
    String oldInfix = infix;
    boolean oldInfixESet = infixESet;
    infix = INFIX_EDEFAULT;
    infixESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, RolodexPackage.PERSON__INFIX, oldInfix, INFIX_EDEFAULT,
          oldInfixESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetInfix() {
    return infixESet;
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
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.PERSON__SURNAME, oldSurname, surname,
          !oldSurnameESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, RolodexPackage.PERSON__SURNAME, oldSurname,
          SURNAME_EDEFAULT, oldSurnameESet));
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
  public String getInitials() {
    return initials;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setInitials(String newInitials) {
    String oldInitials = initials;
    initials = newInitials;
    boolean oldInitialsESet = initialsESet;
    initialsESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.PERSON__INITIALS, oldInitials, initials,
          !oldInitialsESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetInitials() {
    String oldInitials = initials;
    boolean oldInitialsESet = initialsESet;
    initials = INITIALS_EDEFAULT;
    initialsESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, RolodexPackage.PERSON__INITIALS, oldInitials,
          INITIALS_EDEFAULT, oldInitialsESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetInitials() {
    return initialsESet;
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
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.PERSON__GENDER, oldGender, gender,
          !oldGenderESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, RolodexPackage.PERSON__GENDER, oldGender, GENDER_EDEFAULT,
          oldGenderESet));
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
  public Address getAddress() {
    if (address != null && address.eIsProxy()) {
      InternalEObject oldAddress = (InternalEObject) address;
      address = (Address) eResolveProxy(oldAddress);
      if (address != oldAddress) {
        if (eNotificationRequired())
          eNotify(
              new ENotificationImpl(this, Notification.RESOLVE, RolodexPackage.PERSON__ADDRESS, oldAddress, address));
      }
    }
    return address;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Address basicGetAddress() {
    return address;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setAddress(Address newAddress) {
    Address oldAddress = address;
    address = newAddress;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.PERSON__ADDRESS, oldAddress, address));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<AddressForPeriod> getPreviousAddresses() {
    if (previousAddresses == null) {
      previousAddresses = new EObjectContainmentEList<AddressForPeriod>(AddressForPeriod.class, this,
          RolodexPackage.PERSON__PREVIOUS_ADDRESSES);
    }
    return previousAddresses;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDescription(String newDescription) {
    String oldDescription = description;
    description = newDescription;
    boolean oldDescriptionESet = descriptionESet;
    descriptionESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.PERSON__DESCRIPTION, oldDescription,
          description, !oldDescriptionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDescription() {
    String oldDescription = description;
    boolean oldDescriptionESet = descriptionESet;
    description = DESCRIPTION_EDEFAULT;
    descriptionESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, RolodexPackage.PERSON__DESCRIPTION, oldDescription,
          DESCRIPTION_EDEFAULT, oldDescriptionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDescription() {
    return descriptionESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isArchived() {
    return archived;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setArchived(boolean newArchived) {
    boolean oldArchived = archived;
    archived = newArchived;
    boolean oldArchivedESet = archivedESet;
    archivedESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.PERSON__ARCHIVED, oldArchived, archived,
          !oldArchivedESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetArchived() {
    boolean oldArchived = archived;
    boolean oldArchivedESet = archivedESet;
    archived = ARCHIVED_EDEFAULT;
    archivedESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, RolodexPackage.PERSON__ARCHIVED, oldArchived,
          ARCHIVED_EDEFAULT, oldArchivedESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetArchived() {
    return archivedESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public String getId() {
    if (!eIsSet(RolodexPackage.PERSON__ID)) {
      id = EcoreUtil.generateUUID();
    }

    return id;
    //    StringBuffer buf = new StringBuffer();
    //    // "Peter:::23-01-1957", "Naamloos:::11-08-2001"
    //    if (eIsSet(RolodexPackage.PERSON__FIRSTNAME)) {
    //      buf.append(firstname);
    //    }
    //    buf.append(":");
    //    if (eIsSet(RolodexPackage.PERSON__INFIX)) {
    //      buf.append(infix);
    //    }
    //    buf.append(":");
    //    if (eIsSet(RolodexPackage.PERSON__SURNAME)) {
    //      buf.append(surname);
    //    }
    //    buf.append(":");
    //    if (eIsSet(RolodexPackage.PERSON__DATE_OF_BIRTH)) {
    //      buf.append(DF.format(dateOfBirth));
    //    }
    //    
    //    return buf.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setId(String newId) {
    String oldId = id;
    id = newId;
    boolean oldIdESet = idESet;
    idESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.PERSON__ID, oldId, id, !oldIdESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetId() {
    String oldId = id;
    boolean oldIdESet = idESet;
    id = ID_EDEFAULT;
    idESet = false;
    if (eNotificationRequired())
      eNotify(
          new ENotificationImpl(this, Notification.UNSET, RolodexPackage.PERSON__ID, oldId, ID_EDEFAULT, oldIdESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetId() {
    return idESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Family getFamily() {
    if (family != null && family.eIsProxy()) {
      InternalEObject oldFamily = (InternalEObject) family;
      family = (Family) eResolveProxy(oldFamily);
      if (family != oldFamily) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, RolodexPackage.PERSON__FAMILY, oldFamily, family));
      }
    }
    return family;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Family basicGetFamily() {
    return family;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetFamily(Family newFamily, NotificationChain msgs) {
    Family oldFamily = family;
    family = newFamily;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RolodexPackage.PERSON__FAMILY,
          oldFamily, newFamily);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFamily(Family newFamily) {
    if (newFamily != family) {
      NotificationChain msgs = null;
      if (family != null)
        msgs = ((InternalEObject) family).eInverseRemove(this, RolodexPackage.FAMILY__MEMBERS, Family.class, msgs);
      if (newFamily != null)
        msgs = ((InternalEObject) newFamily).eInverseAdd(this, RolodexPackage.FAMILY__MEMBERS, Family.class, msgs);
      msgs = basicSetFamily(newFamily, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.PERSON__FAMILY, newFamily, newFamily));
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
    boolean oldBirthdayESet = birthdayESet;
    birthdayESet = true;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RolodexPackage.PERSON__BIRTHDAY,
          oldBirthday, newBirthday, !oldBirthdayESet);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
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
        msgs = ((InternalEObject) birthday).eInverseRemove(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.PERSON__BIRTHDAY, null, msgs);
      if (newBirthday != null)
        msgs = ((InternalEObject) newBirthday).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.PERSON__BIRTHDAY, null, msgs);
      msgs = basicSetBirthday(newBirthday, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else {
      boolean oldBirthdayESet = birthdayESet;
      birthdayESet = true;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.PERSON__BIRTHDAY, newBirthday, newBirthday,
            !oldBirthdayESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicUnsetBirthday(NotificationChain msgs) {
    Birthday oldBirthday = birthday;
    birthday = null;
    boolean oldBirthdayESet = birthdayESet;
    birthdayESet = false;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, RolodexPackage.PERSON__BIRTHDAY,
          oldBirthday, null, oldBirthdayESet);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetBirthday() {
    if (birthday != null) {
      NotificationChain msgs = null;
      msgs = ((InternalEObject) birthday).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RolodexPackage.PERSON__BIRTHDAY,
          null, msgs);
      msgs = basicUnsetBirthday(msgs);
      if (msgs != null)
        msgs.dispatch();
    } else {
      boolean oldBirthdayESet = birthdayESet;
      birthdayESet = false;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.UNSET, RolodexPackage.PERSON__BIRTHDAY, null, null,
            oldBirthdayESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetBirthday() {
    return birthdayESet;
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
    case RolodexPackage.PERSON__PHONE_NUMBERS:
      return ((InternalEList<InternalEObject>) (InternalEList<?>) getPhoneNumbers()).basicAdd(otherEnd, msgs);
    case RolodexPackage.PERSON__FAMILY:
      if (family != null)
        msgs = ((InternalEObject) family).eInverseRemove(this, RolodexPackage.FAMILY__MEMBERS, Family.class, msgs);
      return basicSetFamily((Family) otherEnd, msgs);
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
    case RolodexPackage.PERSON__PHONE_NUMBERS:
      return ((InternalEList<?>) getPhoneNumbers()).basicRemove(otherEnd, msgs);
    case RolodexPackage.PERSON__PREVIOUS_ADDRESSES:
      return ((InternalEList<?>) getPreviousAddresses()).basicRemove(otherEnd, msgs);
    case RolodexPackage.PERSON__FAMILY:
      return basicSetFamily(null, msgs);
    case RolodexPackage.PERSON__BIRTHDAY:
      return basicUnsetBirthday(msgs);
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
    case RolodexPackage.PERSON__PHONE_NUMBERS:
      return getPhoneNumbers();
    case RolodexPackage.PERSON__ADDRESS:
      if (resolve)
        return getAddress();
      return basicGetAddress();
    case RolodexPackage.PERSON__PREVIOUS_ADDRESSES:
      return getPreviousAddresses();
    case RolodexPackage.PERSON__DESCRIPTION:
      return getDescription();
    case RolodexPackage.PERSON__ARCHIVED:
      return isArchived();
    case RolodexPackage.PERSON__FIRSTNAME:
      return getFirstname();
    case RolodexPackage.PERSON__INFIX:
      return getInfix();
    case RolodexPackage.PERSON__SURNAME:
      return getSurname();
    case RolodexPackage.PERSON__INITIALS:
      return getInitials();
    case RolodexPackage.PERSON__GENDER:
      return getGender();
    case RolodexPackage.PERSON__ID:
      return getId();
    case RolodexPackage.PERSON__FAMILY:
      if (resolve)
        return getFamily();
      return basicGetFamily();
    case RolodexPackage.PERSON__BIRTHDAY:
      return getBirthday();
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
    case RolodexPackage.PERSON__PHONE_NUMBERS:
      getPhoneNumbers().clear();
      getPhoneNumbers().addAll((Collection<? extends PhoneNumber>) newValue);
      return;
    case RolodexPackage.PERSON__ADDRESS:
      setAddress((Address) newValue);
      return;
    case RolodexPackage.PERSON__PREVIOUS_ADDRESSES:
      getPreviousAddresses().clear();
      getPreviousAddresses().addAll((Collection<? extends AddressForPeriod>) newValue);
      return;
    case RolodexPackage.PERSON__DESCRIPTION:
      setDescription((String) newValue);
      return;
    case RolodexPackage.PERSON__ARCHIVED:
      setArchived((Boolean) newValue);
      return;
    case RolodexPackage.PERSON__FIRSTNAME:
      setFirstname((String) newValue);
      return;
    case RolodexPackage.PERSON__INFIX:
      setInfix((String) newValue);
      return;
    case RolodexPackage.PERSON__SURNAME:
      setSurname((String) newValue);
      return;
    case RolodexPackage.PERSON__INITIALS:
      setInitials((String) newValue);
      return;
    case RolodexPackage.PERSON__GENDER:
      setGender((Gender) newValue);
      return;
    case RolodexPackage.PERSON__ID:
      setId((String) newValue);
      return;
    case RolodexPackage.PERSON__FAMILY:
      setFamily((Family) newValue);
      return;
    case RolodexPackage.PERSON__BIRTHDAY:
      setBirthday((Birthday) newValue);
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
    case RolodexPackage.PERSON__PHONE_NUMBERS:
      getPhoneNumbers().clear();
      return;
    case RolodexPackage.PERSON__ADDRESS:
      setAddress((Address) null);
      return;
    case RolodexPackage.PERSON__PREVIOUS_ADDRESSES:
      getPreviousAddresses().clear();
      return;
    case RolodexPackage.PERSON__DESCRIPTION:
      unsetDescription();
      return;
    case RolodexPackage.PERSON__ARCHIVED:
      unsetArchived();
      return;
    case RolodexPackage.PERSON__FIRSTNAME:
      unsetFirstname();
      return;
    case RolodexPackage.PERSON__INFIX:
      unsetInfix();
      return;
    case RolodexPackage.PERSON__SURNAME:
      unsetSurname();
      return;
    case RolodexPackage.PERSON__INITIALS:
      unsetInitials();
      return;
    case RolodexPackage.PERSON__GENDER:
      unsetGender();
      return;
    case RolodexPackage.PERSON__ID:
      unsetId();
      return;
    case RolodexPackage.PERSON__FAMILY:
      setFamily((Family) null);
      return;
    case RolodexPackage.PERSON__BIRTHDAY:
      unsetBirthday();
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
    case RolodexPackage.PERSON__PHONE_NUMBERS:
      return phoneNumbers != null && !phoneNumbers.isEmpty();
    case RolodexPackage.PERSON__ADDRESS:
      return address != null;
    case RolodexPackage.PERSON__PREVIOUS_ADDRESSES:
      return previousAddresses != null && !previousAddresses.isEmpty();
    case RolodexPackage.PERSON__DESCRIPTION:
      return isSetDescription();
    case RolodexPackage.PERSON__ARCHIVED:
      return isSetArchived();
    case RolodexPackage.PERSON__FIRSTNAME:
      return isSetFirstname();
    case RolodexPackage.PERSON__INFIX:
      return isSetInfix();
    case RolodexPackage.PERSON__SURNAME:
      return isSetSurname();
    case RolodexPackage.PERSON__INITIALS:
      return isSetInitials();
    case RolodexPackage.PERSON__GENDER:
      return isSetGender();
    case RolodexPackage.PERSON__ID:
      return isSetId();
    case RolodexPackage.PERSON__FAMILY:
      return family != null;
    case RolodexPackage.PERSON__BIRTHDAY:
      return isSetBirthday();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
    if (baseClass == AddressHolder.class) {
      switch (derivedFeatureID) {
      case RolodexPackage.PERSON__ADDRESS:
        return RolodexPackage.ADDRESS_HOLDER__ADDRESS;
      case RolodexPackage.PERSON__PREVIOUS_ADDRESSES:
        return RolodexPackage.ADDRESS_HOLDER__PREVIOUS_ADDRESSES;
      default:
        return -1;
      }
    }
    if (baseClass == Description.class) {
      switch (derivedFeatureID) {
      case RolodexPackage.PERSON__DESCRIPTION:
        return RolodexPackage.DESCRIPTION__DESCRIPTION;
      default:
        return -1;
      }
    }
    if (baseClass == Archive.class) {
      switch (derivedFeatureID) {
      case RolodexPackage.PERSON__ARCHIVED:
        return RolodexPackage.ARCHIVE__ARCHIVED;
      default:
        return -1;
      }
    }
    return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
    if (baseClass == AddressHolder.class) {
      switch (baseFeatureID) {
      case RolodexPackage.ADDRESS_HOLDER__ADDRESS:
        return RolodexPackage.PERSON__ADDRESS;
      case RolodexPackage.ADDRESS_HOLDER__PREVIOUS_ADDRESSES:
        return RolodexPackage.PERSON__PREVIOUS_ADDRESSES;
      default:
        return -1;
      }
    }
    if (baseClass == Description.class) {
      switch (baseFeatureID) {
      case RolodexPackage.DESCRIPTION__DESCRIPTION:
        return RolodexPackage.PERSON__DESCRIPTION;
      default:
        return -1;
      }
    }
    if (baseClass == Archive.class) {
      switch (baseFeatureID) {
      case RolodexPackage.ARCHIVE__ARCHIVED:
        return RolodexPackage.PERSON__ARCHIVED;
      default:
        return -1;
      }
    }
    return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
    switch (operationID) {
    case RolodexPackage.PERSON___GET_NAME:
      return getName();
    case RolodexPackage.PERSON___RETRIEVE_ADDRESS:
      return retrieveAddress();
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

    StringBuffer result = new StringBuffer();

    boolean spaceNeeded = false;
    if (firstname != null) {
      result.append(firstname);
      spaceNeeded = true;
    }
    if (infix != null) {
      if (spaceNeeded) {
        result.append(" ");
      }
      result.append(infix);
    }
    if (surname != null) {
      if (spaceNeeded) {
        result.append(" ");
      }
      result.append(surname);
    }
    return result.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public String getName() {
    StringBuilder buf = new StringBuilder();
    boolean spaceRequired = false;

    if (eIsSet(RolodexPackage.PERSON__FIRSTNAME)) {
      buf.append(getFirstname());
      spaceRequired = true;
    }
    if (eIsSet(RolodexPackage.PERSON__SURNAME)) {
      // It only makes sense to show the infix if also the surname is known.
      if (eIsSet(RolodexPackage.PERSON__INFIX)) {
        if (spaceRequired) {
          buf.append(" ");
        }
        buf.append(getInfix());
      }

      if (spaceRequired) {
        buf.append(" ");
      }
      buf.append(getSurname());
    }

    return buf.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public Address retrieveAddress() {
    Address address = getAddress();

    if (address == null) {
      Family family = getFamily();
      if (family != null) {
        address = family.getAddress();
      }
    }

    return address;
  }

} //PersonImpl
