/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.City;
import goedegep.rolodex.model.RolodexPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Address</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.impl.AddressImpl#getStreetName <em>Street Name</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.AddressImpl#getHouseNumber <em>House Number</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.AddressImpl#getHouseNumberExtension <em>House Number Extension</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.AddressImpl#getCity <em>City</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.AddressImpl#getPOBox <em>PO Box</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.AddressImpl#getPostalCode <em>Postal Code</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.AddressImpl#getId <em>Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AddressImpl extends MinimalEObjectImpl.Container implements Address {
  //  private static final Logger         LOGGER = Logger.getLogger(AddressImpl.class.getName());

  /**
   * The default value of the '{@link #getStreetName() <em>Street Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStreetName()
   * @generated
   * @ordered
   */
  protected static final String STREET_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getStreetName() <em>Street Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStreetName()
   * @generated
   * @ordered
   */
  protected String streetName = STREET_NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getHouseNumber() <em>House Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHouseNumber()
   * @generated
   * @ordered
   */
  protected static final Integer HOUSE_NUMBER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getHouseNumber() <em>House Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHouseNumber()
   * @generated
   * @ordered
   */
  protected Integer houseNumber = HOUSE_NUMBER_EDEFAULT;

  /**
   * The default value of the '{@link #getHouseNumberExtension() <em>House Number Extension</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHouseNumberExtension()
   * @generated
   * @ordered
   */
  protected static final String HOUSE_NUMBER_EXTENSION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getHouseNumberExtension() <em>House Number Extension</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHouseNumberExtension()
   * @generated
   * @ordered
   */
  protected String houseNumberExtension = HOUSE_NUMBER_EXTENSION_EDEFAULT;

  /**
   * The cached value of the '{@link #getCity() <em>City</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCity()
   * @generated
   * @ordered
   */
  protected City city;

  /**
   * The default value of the '{@link #getPOBox() <em>PO Box</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPOBox()
   * @generated
   * @ordered
   */
  protected static final String PO_BOX_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPOBox() <em>PO Box</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPOBox()
   * @generated
   * @ordered
   */
  protected String pOBox = PO_BOX_EDEFAULT;

  /**
   * The default value of the '{@link #getPostalCode() <em>Postal Code</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPostalCode()
   * @generated
   * @ordered
   */
  protected static final String POSTAL_CODE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPostalCode() <em>Postal Code</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPostalCode()
   * @generated
   * @ordered
   */
  protected String postalCode = POSTAL_CODE_EDEFAULT;

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
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  protected AddressImpl() {
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
    return RolodexPackage.Literals.ADDRESS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getStreetName() {
    return streetName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setStreetName(String newStreetName) {
    String oldStreetName = streetName;
    streetName = newStreetName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.ADDRESS__STREET_NAME, oldStreetName,
          streetName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Integer getHouseNumber() {
    return houseNumber;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setHouseNumber(Integer newHouseNumber) {
    Integer oldHouseNumber = houseNumber;
    houseNumber = newHouseNumber;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.ADDRESS__HOUSE_NUMBER, oldHouseNumber,
          houseNumber));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getHouseNumberExtension() {
    return houseNumberExtension;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setHouseNumberExtension(String newHouseNumberExtension) {
    String oldHouseNumberExtension = houseNumberExtension;
    houseNumberExtension = newHouseNumberExtension;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.ADDRESS__HOUSE_NUMBER_EXTENSION,
          oldHouseNumberExtension, houseNumberExtension));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public City getCity() {
    if (city != null && city.eIsProxy()) {
      InternalEObject oldCity = (InternalEObject) city;
      city = (City) eResolveProxy(oldCity);
      if (city != oldCity) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, RolodexPackage.ADDRESS__CITY, oldCity, city));
      }
    }
    return city;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public City basicGetCity() {
    return city;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCity(City newCity) {
    City oldCity = city;
    city = newCity;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.ADDRESS__CITY, oldCity, city));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getPOBox() {
    return pOBox;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPOBox(String newPOBox) {
    String oldPOBox = pOBox;
    pOBox = newPOBox;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.ADDRESS__PO_BOX, oldPOBox, pOBox));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPostalCode(String newPostalCode) {
    String oldPostalCode = postalCode;
    postalCode = newPostalCode;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.ADDRESS__POSTAL_CODE, oldPostalCode,
          postalCode));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public String getId() {
    if (!eIsSet(RolodexPackage.ADDRESS__ID)) {
      id = EcoreUtil.generateUUID();
    }

    return id;
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
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.ADDRESS__ID, oldId, id));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case RolodexPackage.ADDRESS__STREET_NAME:
      return getStreetName();
    case RolodexPackage.ADDRESS__HOUSE_NUMBER:
      return getHouseNumber();
    case RolodexPackage.ADDRESS__HOUSE_NUMBER_EXTENSION:
      return getHouseNumberExtension();
    case RolodexPackage.ADDRESS__CITY:
      if (resolve)
        return getCity();
      return basicGetCity();
    case RolodexPackage.ADDRESS__PO_BOX:
      return getPOBox();
    case RolodexPackage.ADDRESS__POSTAL_CODE:
      return getPostalCode();
    case RolodexPackage.ADDRESS__ID:
      return getId();
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
    case RolodexPackage.ADDRESS__STREET_NAME:
      setStreetName((String) newValue);
      return;
    case RolodexPackage.ADDRESS__HOUSE_NUMBER:
      setHouseNumber((Integer) newValue);
      return;
    case RolodexPackage.ADDRESS__HOUSE_NUMBER_EXTENSION:
      setHouseNumberExtension((String) newValue);
      return;
    case RolodexPackage.ADDRESS__CITY:
      setCity((City) newValue);
      return;
    case RolodexPackage.ADDRESS__PO_BOX:
      setPOBox((String) newValue);
      return;
    case RolodexPackage.ADDRESS__POSTAL_CODE:
      setPostalCode((String) newValue);
      return;
    case RolodexPackage.ADDRESS__ID:
      setId((String) newValue);
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
    case RolodexPackage.ADDRESS__STREET_NAME:
      setStreetName(STREET_NAME_EDEFAULT);
      return;
    case RolodexPackage.ADDRESS__HOUSE_NUMBER:
      setHouseNumber(HOUSE_NUMBER_EDEFAULT);
      return;
    case RolodexPackage.ADDRESS__HOUSE_NUMBER_EXTENSION:
      setHouseNumberExtension(HOUSE_NUMBER_EXTENSION_EDEFAULT);
      return;
    case RolodexPackage.ADDRESS__CITY:
      setCity((City) null);
      return;
    case RolodexPackage.ADDRESS__PO_BOX:
      setPOBox(PO_BOX_EDEFAULT);
      return;
    case RolodexPackage.ADDRESS__POSTAL_CODE:
      setPostalCode(POSTAL_CODE_EDEFAULT);
      return;
    case RolodexPackage.ADDRESS__ID:
      setId(ID_EDEFAULT);
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
    case RolodexPackage.ADDRESS__STREET_NAME:
      return STREET_NAME_EDEFAULT == null ? streetName != null : !STREET_NAME_EDEFAULT.equals(streetName);
    case RolodexPackage.ADDRESS__HOUSE_NUMBER:
      return HOUSE_NUMBER_EDEFAULT == null ? houseNumber != null : !HOUSE_NUMBER_EDEFAULT.equals(houseNumber);
    case RolodexPackage.ADDRESS__HOUSE_NUMBER_EXTENSION:
      return HOUSE_NUMBER_EXTENSION_EDEFAULT == null ? houseNumberExtension != null
          : !HOUSE_NUMBER_EXTENSION_EDEFAULT.equals(houseNumberExtension);
    case RolodexPackage.ADDRESS__CITY:
      return city != null;
    case RolodexPackage.ADDRESS__PO_BOX:
      return PO_BOX_EDEFAULT == null ? pOBox != null : !PO_BOX_EDEFAULT.equals(pOBox);
    case RolodexPackage.ADDRESS__POSTAL_CODE:
      return POSTAL_CODE_EDEFAULT == null ? postalCode != null : !POSTAL_CODE_EDEFAULT.equals(postalCode);
    case RolodexPackage.ADDRESS__ID:
      return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   */
  @Override
  public String toString() {
    if (eIsProxy())
      return super.toString();

    boolean spaceNeeded = false;
    StringBuffer result = new StringBuffer();
    if (pOBox == null) {
      if (streetName != null) {
        result.append(streetName);
        spaceNeeded = true;
      }

      if (houseNumber != null) {
        if (spaceNeeded) {
          result.append(" ");
        }
        result.append(houseNumber);
        spaceNeeded = true;
      }

      if (houseNumberExtension != null) {
        if (spaceNeeded) {
          result.append(" ");
        }
        result.append(houseNumberExtension);
        spaceNeeded = true;
      }
    } else {
      result.append("Postbus ");
      result.append(pOBox);
    }

    result.append(", ");
    spaceNeeded = false;

    if (postalCode != null) {
      result.append(postalCode);
      spaceNeeded = true;
    }

    if (city != null) {
      if (spaceNeeded) {
        result.append(" ");
      }
      result.append(city.getCityName());
    }

    return result.toString();
  }

} //AddressImpl
