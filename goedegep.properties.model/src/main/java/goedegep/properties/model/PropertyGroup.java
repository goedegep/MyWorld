/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.properties.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.properties.model.PropertyGroup#getProperties <em>Properties</em>}</li>
 *   <li>{@link goedegep.properties.model.PropertyGroup#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.properties.model.PropertyGroup#getPropertyGroups <em>Property Groups</em>}</li>
 * </ul>
 *
 * @see goedegep.properties.model.PropertiesPackage#getPropertyGroup()
 * @model
 * @generated
 */
public interface PropertyGroup extends EObject {
  /**
   * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.properties.model.Property}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Properties</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Properties</em>' containment reference list.
   * @see goedegep.properties.model.PropertiesPackage#getPropertyGroup_Properties()
   * @model containment="true"
   * @generated
   */
  EList<Property> getProperties();

  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #isSetName()
   * @see #unsetName()
   * @see #setName(String)
   * @see goedegep.properties.model.PropertiesPackage#getPropertyGroup_Name()
   * @model unsettable="true" required="true"
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link goedegep.properties.model.PropertyGroup#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #isSetName()
   * @see #unsetName()
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Unsets the value of the '{@link goedegep.properties.model.PropertyGroup#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetName()
   * @see #getName()
   * @see #setName(String)
   * @generated
   */
  void unsetName();

  /**
   * Returns whether the value of the '{@link goedegep.properties.model.PropertyGroup#getName <em>Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Name</em>' attribute is set.
   * @see #unsetName()
   * @see #getName()
   * @see #setName(String)
   * @generated
   */
  boolean isSetName();

  /**
   * Returns the value of the '<em><b>Property Groups</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.properties.model.PropertyGroup}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Property Groups</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Property Groups</em>' containment reference list.
   * @see goedegep.properties.model.PropertiesPackage#getPropertyGroup_PropertyGroups()
   * @model containment="true"
   * @generated
   */
  EList<PropertyGroup> getPropertyGroups();

} // PropertyGroup
