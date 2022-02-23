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
 * A representation of the model object '<em><b>Country</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link goedegep.rolodex.model.Country#getCountryName <em>Country Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getCountry()
 * @model
 */
@SuppressWarnings("rawtypes")
public interface Country extends EObject, Comparable {
  /**
   * Returns the value of the '<em><b>Country Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Country Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Country Name</em>' attribute.
   * @see #setCountryName(String)
   * @see goedegep.rolodex.model.RolodexPackage#getCountry_CountryName()
   * @model required="true"
   * @generated
   */
  String getCountryName();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Country#getCountryName <em>Country Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Country Name</em>' attribute.
   * @see #getCountryName()
   * @generated
   */
  void setCountryName(String value);

} // Country
