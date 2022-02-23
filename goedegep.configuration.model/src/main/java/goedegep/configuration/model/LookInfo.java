/**
 */
package goedegep.configuration.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Look Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.configuration.model.LookInfo#getModuleLooks <em>Module Looks</em>}</li>
 * </ul>
 *
 * @see goedegep.configuration.model.ConfigurationPackage#getLookInfo()
 * @model
 * @generated
 */
public interface LookInfo extends EObject {
  /**
   * Returns the value of the '<em><b>Module Looks</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.configuration.model.ModuleLook}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Module Looks</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Module Looks</em>' containment reference list.
   * @see goedegep.configuration.model.ConfigurationPackage#getLookInfo_ModuleLooks()
   * @model containment="true"
   * @generated
   */
  EList<ModuleLook> getModuleLooks();

} // LookInfo
