/**
 */
package goedegep.invandprop.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Properties</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.invandprop.model.Properties#getProperties <em>Properties</em>}</li>
 * </ul>
 *
 * @see goedegep.invandprop.model.InvAndPropPackage#getProperties()
 * @model
 * @generated
 */
public interface Properties extends EObject {
  /**
   * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.invandprop.model.Property}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Properties</em>' containment reference list.
   * @see goedegep.invandprop.model.InvAndPropPackage#getProperties_Properties()
   * @model containment="true"
   * @generated
   */
  EList<Property> getProperties();

} // Properties
