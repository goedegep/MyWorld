/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A list of countries. Each City has a reference to a country.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.CountryList#getCountries <em>Countries</em>}</li>
 * </ul>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getCountryList()
 * @model
 * @generated
 */
public interface CountryList extends EObject {
  /**
   * Returns the value of the '<em><b>Countries</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.rolodex.model.Country}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Countries</em>' containment reference list.
   * @see goedegep.rolodex.model.RolodexPackage#getCountryList_Countries()
   * @model containment="true"
   * @generated
   */
  EList<Country> getCountries();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Create a <code>Country</code> with the specified name and add it to the <code>countries</doc>.
   * <p>
   * The given name cannot be an empty string. Also there may not yet already exist a <code>Country</code> with the specified name.
   * 
   * @param countryName the name of the country to be added.
   * @return The newly created and added <code>Country</code>, or null if the <code>countryName</code> isn't valid.
   * <!-- end-model-doc -->
   * @model
   * @generated
   */
  Country addCountry(String countryName);

  Country getCountry(String countryName);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Remove a <code>Country</code> from the <code>countries</code>.
   * 
   * @param country the <code>Country</code> to be removed.
   * <!-- end-model-doc -->
   * @model
   * @generated
   */
  void removeCountry(Country country);

} // CountryList
