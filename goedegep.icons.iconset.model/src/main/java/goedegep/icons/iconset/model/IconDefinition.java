/**
 */
package goedegep.icons.iconset.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Icon Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.icons.iconset.model.IconDefinition#getIconInfo <em>Icon Info</em>}</li>
 *   <li>{@link goedegep.icons.iconset.model.IconDefinition#getIconData <em>Icon Data</em>}</li>
 * </ul>
 *
 * @see goedegep.icons.iconset.model.ModelPackage#getIconDefinition()
 * @model
 * @generated
 */
public interface IconDefinition extends EObject {
  /**
   * Returns the value of the '<em><b>Icon Info</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Icon Info</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Icon Info</em>' containment reference.
   * @see #isSetIconInfo()
   * @see #unsetIconInfo()
   * @see #setIconInfo(IconInfo)
   * @see goedegep.icons.iconset.model.ModelPackage#getIconDefinition_IconInfo()
   * @model containment="true" unsettable="true" required="true"
   * @generated
   */
  IconInfo getIconInfo();

  /**
   * Sets the value of the '{@link goedegep.icons.iconset.model.IconDefinition#getIconInfo <em>Icon Info</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Icon Info</em>' containment reference.
   * @see #isSetIconInfo()
   * @see #unsetIconInfo()
   * @see #getIconInfo()
   * @generated
   */
  void setIconInfo(IconInfo value);

  /**
   * Unsets the value of the '{@link goedegep.icons.iconset.model.IconDefinition#getIconInfo <em>Icon Info</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetIconInfo()
   * @see #getIconInfo()
   * @see #setIconInfo(IconInfo)
   * @generated
   */
  void unsetIconInfo();

  /**
   * Returns whether the value of the '{@link goedegep.icons.iconset.model.IconDefinition#getIconInfo <em>Icon Info</em>}' containment reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Icon Info</em>' containment reference is set.
   * @see #unsetIconInfo()
   * @see #getIconInfo()
   * @see #setIconInfo(IconInfo)
   * @generated
   */
  boolean isSetIconInfo();

  /**
   * Returns the value of the '<em><b>Icon Data</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Icon Data</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Icon Data</em>' containment reference.
   * @see #isSetIconData()
   * @see #unsetIconData()
   * @see #setIconData(IconData)
   * @see goedegep.icons.iconset.model.ModelPackage#getIconDefinition_IconData()
   * @model containment="true" unsettable="true" required="true"
   * @generated
   */
  IconData getIconData();

  /**
   * Sets the value of the '{@link goedegep.icons.iconset.model.IconDefinition#getIconData <em>Icon Data</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Icon Data</em>' containment reference.
   * @see #isSetIconData()
   * @see #unsetIconData()
   * @see #getIconData()
   * @generated
   */
  void setIconData(IconData value);

  /**
   * Unsets the value of the '{@link goedegep.icons.iconset.model.IconDefinition#getIconData <em>Icon Data</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetIconData()
   * @see #getIconData()
   * @see #setIconData(IconData)
   * @generated
   */
  void unsetIconData();

  /**
   * Returns whether the value of the '{@link goedegep.icons.iconset.model.IconDefinition#getIconData <em>Icon Data</em>}' containment reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Icon Data</em>' containment reference is set.
   * @see #unsetIconData()
   * @see #getIconData()
   * @see #setIconData(IconData)
   * @generated
   */
  boolean isSetIconData();

} // IconDefinition
