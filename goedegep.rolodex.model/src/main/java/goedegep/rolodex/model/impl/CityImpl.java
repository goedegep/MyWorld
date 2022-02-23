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

import goedegep.rolodex.model.City;
import goedegep.rolodex.model.Country;
import goedegep.rolodex.model.RolodexPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>City</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.impl.CityImpl#getCountry <em>Country</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.CityImpl#getCityName <em>City Name</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.CityImpl#getId <em>Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CityImpl extends MinimalEObjectImpl.Container implements City {
  /**
   * The cached value of the '{@link #getCountry() <em>Country</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCountry()
   * @generated
   * @ordered
   */
  protected Country country;

  /**
   * The default value of the '{@link #getCityName() <em>City Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCityName()
   * @generated
   * @ordered
   */
  protected static final String CITY_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCityName() <em>City Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCityName()
   * @generated
   * @ordered
   */
  protected String cityName = CITY_NAME_EDEFAULT;

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
   * @generated
   */
  protected CityImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return RolodexPackage.Literals.CITY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Country getCountry() {
    if (country != null && country.eIsProxy()) {
      InternalEObject oldCountry = (InternalEObject) country;
      country = (Country) eResolveProxy(oldCountry);
      if (country != oldCountry) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, RolodexPackage.CITY__COUNTRY, oldCountry, country));
      }
    }
    return country;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Country basicGetCountry() {
    return country;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCountry(Country newCountry) {
    Country oldCountry = country;
    country = newCountry;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.CITY__COUNTRY, oldCountry, country));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getCityName() {
    return cityName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCityName(String newCityName) {
    String oldCityName = cityName;
    cityName = newCityName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.CITY__CITY_NAME, oldCityName, cityName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public String getId() {
    return cityName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case RolodexPackage.CITY__COUNTRY:
      if (resolve)
        return getCountry();
      return basicGetCountry();
    case RolodexPackage.CITY__CITY_NAME:
      return getCityName();
    case RolodexPackage.CITY__ID:
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
    case RolodexPackage.CITY__COUNTRY:
      setCountry((Country) newValue);
      return;
    case RolodexPackage.CITY__CITY_NAME:
      setCityName((String) newValue);
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
    case RolodexPackage.CITY__COUNTRY:
      setCountry((Country) null);
      return;
    case RolodexPackage.CITY__CITY_NAME:
      setCityName(CITY_NAME_EDEFAULT);
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
    case RolodexPackage.CITY__COUNTRY:
      return country != null;
    case RolodexPackage.CITY__CITY_NAME:
      return CITY_NAME_EDEFAULT == null ? cityName != null : !CITY_NAME_EDEFAULT.equals(cityName);
    case RolodexPackage.CITY__ID:
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

    return cityName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @SuppressWarnings("unchecked")
  @Override
  public int compareTo(Object o) {
    if (o instanceof City) {
      City city = (City) o;
      int result = cityName.compareTo(city.getCityName());
      if (result != 0) {
        return result;
      } else {
        return getCountry().compareTo(city.getCountry());
      }
    } else {
      return -1;
    }
  }
} //CityImpl
