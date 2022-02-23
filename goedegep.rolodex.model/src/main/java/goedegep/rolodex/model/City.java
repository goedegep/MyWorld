/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>City</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link goedegep.rolodex.model.City#getCountry <em>Country</em>}</li>
 *   <li>{@link goedegep.rolodex.model.City#getCityName <em>City Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getCity()
 * @model
 */
@SuppressWarnings("rawtypes")
public interface City extends EObject, Comparable {
  /**
   * Returns the value of the '<em><b>Country</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Country</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Country</em>' reference.
   * @see #setCountry(Country)
   * @see goedegep.rolodex.model.RolodexPackage#getCity_Country()
   * @model required="true"
   * @generated
   */
  Country getCountry();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.City#getCountry <em>Country</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Country</em>' reference.
   * @see #getCountry()
   * @generated
   */
  void setCountry(Country value);

  /**
   * Returns the value of the '<em><b>City Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>City Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>City Name</em>' attribute.
   * @see #setCityName(String)
   * @see goedegep.rolodex.model.RolodexPackage#getCity_CityName()
   * @model required="true"
   * @generated
   */
  String getCityName();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.City#getCityName <em>City Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>City Name</em>' attribute.
   * @see #getCityName()
   * @generated
   */
  void setCityName(String value);

  /**
   * Returns the value of the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Id</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Id</em>' attribute.
   * @see goedegep.rolodex.model.RolodexPackage#getCity_Id()
   * @model changeable="false" derived="true"
   * @generated
   */
  String getId();

} // City
