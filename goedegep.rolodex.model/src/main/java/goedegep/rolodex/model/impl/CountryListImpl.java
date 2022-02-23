/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.rolodex.model.Country;
import goedegep.rolodex.model.CountryList;
import goedegep.rolodex.model.RolodexFactory;
import goedegep.rolodex.model.RolodexPackage;

import java.lang.reflect.InvocationTargetException;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Country List</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.impl.CountryListImpl#getCountries <em>Countries</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CountryListImpl extends MinimalEObjectImpl.Container implements CountryList {
  /**
   * The cached value of the '{@link #getCountries() <em>Countries</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCountries()
   * @generated
   * @ordered
   */
  protected EList<Country> countries;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CountryListImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return RolodexPackage.Literals.COUNTRY_LIST;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Country> getCountries() {
    if (countries == null) {
      countries = new EObjectContainmentEList<Country>(Country.class, this, RolodexPackage.COUNTRY_LIST__COUNTRIES);
    }
    return countries;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public Country addCountry(String countryName) {
    if (countryName.isEmpty()) {
      return null;
    }

    Country country = getCountry(countryName);
    if (country != null) {
      return null;
    } else {
      RolodexFactory rolodexFactory = RolodexFactory.eINSTANCE;
      country = rolodexFactory.createCountry();
      country.setCountryName(countryName);
      getCountries().add(country);
      return country;
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case RolodexPackage.COUNTRY_LIST__COUNTRIES:
      return ((InternalEList<?>) getCountries()).basicRemove(otherEnd, msgs);
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
    case RolodexPackage.COUNTRY_LIST__COUNTRIES:
      return getCountries();
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
    case RolodexPackage.COUNTRY_LIST__COUNTRIES:
      getCountries().clear();
      getCountries().addAll((Collection<? extends Country>) newValue);
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
    case RolodexPackage.COUNTRY_LIST__COUNTRIES:
      getCountries().clear();
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
    case RolodexPackage.COUNTRY_LIST__COUNTRIES:
      return countries != null && !countries.isEmpty();
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
    case RolodexPackage.COUNTRY_LIST___ADD_COUNTRY__STRING:
      return addCountry((String) arguments.get(0));
    case RolodexPackage.COUNTRY_LIST___GET_COUNTRY__STRING:
      return getCountry((String) arguments.get(0));
    case RolodexPackage.COUNTRY_LIST___REMOVE_COUNTRY__COUNTRY:
      removeCountry((Country) arguments.get(0));
      return null;
    }
    return super.eInvoke(operationID, arguments);
  }

  @Override
  public Country getCountry(String countryName) {
    for (Country country : countries) {
      if (country.getCountryName().equalsIgnoreCase(countryName)) {
        return country;
      }
    }

    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void removeCountry(Country country) {
    // TODO: implement this method
    // Ensure that you remove @generated or mark it @generated NOT
    throw new UnsupportedOperationException();
  }

} //CountryListImpl
