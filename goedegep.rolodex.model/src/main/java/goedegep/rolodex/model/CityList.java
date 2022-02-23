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
 * A representation of the model object '<em><b>City List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A list of cities. Each <code>Address</code> has a reference to a <code>City</code>.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.CityList#getCities <em>Cities</em>}</li>
 * </ul>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getCityList()
 * @model
 * @generated
 */
public interface CityList extends EObject {
  /**
   * Returns the value of the '<em><b>Cities</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.rolodex.model.City}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Cities</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Cities</em>' containment reference list.
   * @see goedegep.rolodex.model.RolodexPackage#getCityList_Cities()
   * @model containment="true"
   * @generated
   */
  EList<City> getCities();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get the city for a specified Id.
   * 
   * @param id the identifying string of a <code>City</code>
   * @return the <code>City</code> with the specified <code>Id</code>, or <code>null</code> if there is no <code>City</code> with the given <code>Id</code>.
   * <!-- end-model-doc -->
   * @model
   * @generated
   */
  City findCityById(String id);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Create a <code>City</code> with the specified name in a specific <code>Country</code>, and add it to the <code>cities</doc>.
   * <p>
   * The given name cannot be an empty string. Also there may not yet already exist a <code>City</code>, in the specified <code>Country</code>, with the specified name.
   * 
   * @param cityName the name of the city to be added.
   * @param country the <code>Country</code> in which the city is located
   * @return The newly created and added <code>City</code>, or null if the <code>countryName</code>/<code>Country</code> combination isn't valid.
   * <!-- end-model-doc -->
   * @model
   * @generated
   */
  City addCity(String cityName, Country country);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get the <code>City</code> with the specified name and in the given <code>Country</code>. 
   * 
   * @param cityName the name of the <code>City</code> to be returned.
   * @param country the <code>Country</code> in which the city shall be located.
   * @returns the <code>City</code> with the name <code>cityName</code> in the specified <code>Country</code>, or null if no such <code>City</code> exists.
   * <!-- end-model-doc -->
   * @model
   * @generated
   */
  City getCity(String cityName, Country country);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model
   * @generated
   */
  EList<City> getCity(String cityName);

  City getCity(String cityName, String countryName);

} // CityList
