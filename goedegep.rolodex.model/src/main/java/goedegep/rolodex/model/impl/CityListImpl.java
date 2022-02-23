/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.rolodex.model.City;
import goedegep.rolodex.model.CityList;
import goedegep.rolodex.model.Country;
import goedegep.rolodex.model.RolodexFactory;
import goedegep.rolodex.model.RolodexPackage;

import java.lang.reflect.InvocationTargetException;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>City List</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.impl.CityListImpl#getCities <em>Cities</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CityListImpl extends MinimalEObjectImpl.Container implements CityList {
  /**
   * The cached value of the '{@link #getCities() <em>Cities</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCities()
   * @generated
   * @ordered
   */
  protected EList<City> cities;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CityListImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return RolodexPackage.Literals.CITY_LIST;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<City> getCities() {
    if (cities == null) {
      cities = new EObjectContainmentEList<City>(City.class, this, RolodexPackage.CITY_LIST__CITIES);
    }
    return cities;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public City findCityById(String id) {
    for (City city : getCities()) {
      String cityId = city.getId();
      if (cityId.equals(id)) {
        return city;
      }
    }

    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public City addCity(String cityName, Country country) {
    if (cityName.isEmpty()) {
      return null;
    }

    City city = getCity(cityName, country);
    if (city != null) {
      return null;
    } else {
      RolodexFactory rolodexFactory = RolodexFactory.eINSTANCE;

      City newCity = rolodexFactory.createCity();
      newCity.setCityName(cityName);
      newCity.setCountry(country);
      cities.add(newCity);

      return newCity;
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public City getCity(String cityName, Country country) {
    for (City city : cities) {
      if (city.getCityName().equalsIgnoreCase(cityName) && city.getCountry().equals(country)) {
        return city;
      }
    }

    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public EList<City> getCity(String cityName) {
    EList<City> matchingCities = new BasicEList<>();

    for (City city : cities) {
      if (city.getCityName().equalsIgnoreCase(cityName)) {
        matchingCities.add(city);
      }
    }

    return matchingCities;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case RolodexPackage.CITY_LIST__CITIES:
      return ((InternalEList<?>) getCities()).basicRemove(otherEnd, msgs);
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
    case RolodexPackage.CITY_LIST__CITIES:
      return getCities();
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
    case RolodexPackage.CITY_LIST__CITIES:
      getCities().clear();
      getCities().addAll((Collection<? extends City>) newValue);
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
    case RolodexPackage.CITY_LIST__CITIES:
      getCities().clear();
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
    case RolodexPackage.CITY_LIST__CITIES:
      return cities != null && !cities.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
    switch (operationID) {
    case RolodexPackage.CITY_LIST___FIND_CITY_BY_ID__STRING:
      return findCityById((String) arguments.get(0));
    case RolodexPackage.CITY_LIST___ADD_CITY__STRING_COUNTRY:
      return addCity((String) arguments.get(0), (Country) arguments.get(1));
    case RolodexPackage.CITY_LIST___GET_CITY__STRING_COUNTRY:
      return getCity((String) arguments.get(0), (Country) arguments.get(1));
    case RolodexPackage.CITY_LIST___GET_CITY__STRING:
      return getCity((String) arguments.get(0));
    }
    return super.eInvoke(operationID, arguments);
  }

  @Override
  public City getCity(String cityName, String countryName) {
    for (City city : cities) {
      if (city.getCityName().equals(cityName)) {
        Country country = city.getCountry();
        if (country != null) {
          if (country.getCountryName().equals(countryName)) {
            return city;
          }
        } else {
          // Ignore countryName if there's no country.
          return city;
        }
      }
    }

    return null;
  }

} //CityListImpl
