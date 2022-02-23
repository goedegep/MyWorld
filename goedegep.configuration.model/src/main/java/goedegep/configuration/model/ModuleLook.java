/**
 */
package goedegep.configuration.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Module Look</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.configuration.model.ModuleLook#getLook <em>Look</em>}</li>
 *   <li>{@link goedegep.configuration.model.ModuleLook#getModuleName <em>Module Name</em>}</li>
 *   <li>{@link goedegep.configuration.model.ModuleLook#getModuleLooks <em>Module Looks</em>}</li>
 *   <li>{@link goedegep.configuration.model.ModuleLook#getResourcesClassName <em>Resources Class Name</em>}</li>
 *   <li>{@link goedegep.configuration.model.ModuleLook#getParentModuleLook <em>Parent Module Look</em>}</li>
 * </ul>
 *
 * @see goedegep.configuration.model.ConfigurationPackage#getModuleLook()
 * @model
 * @generated
 */
public interface ModuleLook extends EObject {

  /**
   * Returns the value of the '<em><b>Look</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Look</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Look</em>' containment reference.
   * @see #setLook(Look)
   * @see goedegep.configuration.model.ConfigurationPackage#getModuleLook_Look()
   * @model containment="true" required="true"
   * @generated
   */
  Look getLook();

  /**
   * Sets the value of the '{@link goedegep.configuration.model.ModuleLook#getLook <em>Look</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Look</em>' containment reference.
   * @see #getLook()
   * @generated
   */
  void setLook(Look value);

  /**
   * Returns the value of the '<em><b>Module Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Module Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Module Name</em>' attribute.
   * @see #setModuleName(String)
   * @see goedegep.configuration.model.ConfigurationPackage#getModuleLook_ModuleName()
   * @model
   * @generated
   */
  String getModuleName();

  /**
   * Sets the value of the '{@link goedegep.configuration.model.ModuleLook#getModuleName <em>Module Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Module Name</em>' attribute.
   * @see #getModuleName()
   * @generated
   */
  void setModuleName(String value);

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
   * @see #isSetModuleLooks()
   * @see #unsetModuleLooks()
   * @see goedegep.configuration.model.ConfigurationPackage#getModuleLook_ModuleLooks()
   * @model containment="true" unsettable="true"
   * @generated
   */
  EList<ModuleLook> getModuleLooks();

  /**
   * Unsets the value of the '{@link goedegep.configuration.model.ModuleLook#getModuleLooks <em>Module Looks</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetModuleLooks()
   * @see #getModuleLooks()
   * @generated
   */
  void unsetModuleLooks();

  /**
   * Returns whether the value of the '{@link goedegep.configuration.model.ModuleLook#getModuleLooks <em>Module Looks</em>}' containment reference list is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Module Looks</em>' containment reference list is set.
   * @see #unsetModuleLooks()
   * @see #getModuleLooks()
   * @generated
   */
  boolean isSetModuleLooks();

  /**
   * Returns the value of the '<em><b>Resources Class Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Resources Class Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Resources Class Name</em>' attribute.
   * @see #isSetResourcesClassName()
   * @see #unsetResourcesClassName()
   * @see #setResourcesClassName(String)
   * @see goedegep.configuration.model.ConfigurationPackage#getModuleLook_ResourcesClassName()
   * @model unsettable="true"
   * @generated
   */
  String getResourcesClassName();

  /**
   * Sets the value of the '{@link goedegep.configuration.model.ModuleLook#getResourcesClassName <em>Resources Class Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Resources Class Name</em>' attribute.
   * @see #isSetResourcesClassName()
   * @see #unsetResourcesClassName()
   * @see #getResourcesClassName()
   * @generated
   */
  void setResourcesClassName(String value);

  /**
   * Unsets the value of the '{@link goedegep.configuration.model.ModuleLook#getResourcesClassName <em>Resources Class Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetResourcesClassName()
   * @see #getResourcesClassName()
   * @see #setResourcesClassName(String)
   * @generated
   */
  void unsetResourcesClassName();

  /**
   * Returns whether the value of the '{@link goedegep.configuration.model.ModuleLook#getResourcesClassName <em>Resources Class Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Resources Class Name</em>' attribute is set.
   * @see #unsetResourcesClassName()
   * @see #getResourcesClassName()
   * @see #setResourcesClassName(String)
   * @generated
   */
  boolean isSetResourcesClassName();

  /**
   * Returns the value of the '<em><b>Parent Module Look</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Parent Module Look</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Parent Module Look</em>' reference.
   * @see #setParentModuleLook(ModuleLook)
   * @see goedegep.configuration.model.ConfigurationPackage#getModuleLook_ParentModuleLook()
   * @model required="true"
   * @generated
   */
  ModuleLook getParentModuleLook();

  /**
   * Sets the value of the '{@link goedegep.configuration.model.ModuleLook#getParentModuleLook <em>Parent Module Look</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Parent Module Look</em>' reference.
   * @see #getParentModuleLook()
   * @generated
   */
  void setParentModuleLook(ModuleLook value);
} // ModuleLook
