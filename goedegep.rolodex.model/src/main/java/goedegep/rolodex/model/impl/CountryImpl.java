/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model.impl;

import java.util.Objects;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import goedegep.rolodex.model.Country;
import goedegep.rolodex.model.RolodexPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Country</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.impl.CountryImpl#getCountryName <em>Country Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CountryImpl extends MinimalEObjectImpl.Container implements Country {
  /**
   * The default value of the '{@link #getCountryName() <em>Country Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCountryName()
   * @generated
   * @ordered
   */
  protected static final String COUNTRY_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCountryName() <em>Country Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCountryName()
   * @generated
   * @ordered
   */
  protected String countryName = COUNTRY_NAME_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CountryImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return RolodexPackage.Literals.COUNTRY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getCountryName() {
    return countryName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCountryName(String newCountryName) {
    String oldCountryName = countryName;
    countryName = newCountryName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.COUNTRY__COUNTRY_NAME, oldCountryName,
          countryName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case RolodexPackage.COUNTRY__COUNTRY_NAME:
      return getCountryName();
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
    case RolodexPackage.COUNTRY__COUNTRY_NAME:
      setCountryName((String) newValue);
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
    case RolodexPackage.COUNTRY__COUNTRY_NAME:
      setCountryName(COUNTRY_NAME_EDEFAULT);
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
    case RolodexPackage.COUNTRY__COUNTRY_NAME:
      return COUNTRY_NAME_EDEFAULT == null ? countryName != null : !COUNTRY_NAME_EDEFAULT.equals(countryName);
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

    //    StringBuffer result = new StringBuffer(super.toString());
    //    result.append(" (countryName: ");
    //    result.append(countryName);
    //    result.append(')');
    //    return result.toString();
    return countryName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public int compareTo(Object o) {
    if (o instanceof Country) {
      Country country = (Country) o;
      return countryName.compareTo(country.getCountryName());
    } else {
      return -1;
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public boolean equals(Object object) {
    if (object == null) {
      return false;
    }

    if (this == object) {
      return true;
    }

    if (getClass() != object.getClass()) {
      return false;
    }

    return countryName.equals(((Country) object).getCountryName());
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public int hashCode() {
    return Objects.hash(countryName);
  }

} //CountryImpl
