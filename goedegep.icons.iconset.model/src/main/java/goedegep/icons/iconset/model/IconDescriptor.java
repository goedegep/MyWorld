/**
 */
package goedegep.icons.iconset.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Icon Descriptor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.icons.iconset.model.IconDescriptor#getUrl <em>Url</em>}</li>
 *   <li>{@link goedegep.icons.iconset.model.IconDescriptor#getIconId <em>Icon Id</em>}</li>
 * </ul>
 *
 * @see goedegep.icons.iconset.model.ModelPackage#getIconDescriptor()
 * @model
 * @generated
 */
public interface IconDescriptor extends EObject {
  /**
   * Returns the value of the '<em><b>Url</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Url</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Url</em>' attribute.
   * @see #isSetUrl()
   * @see #unsetUrl()
   * @see #setUrl(String)
   * @see goedegep.icons.iconset.model.ModelPackage#getIconDescriptor_Url()
   * @model unsettable="true"
   * @generated
   */
  String getUrl();

  /**
   * Sets the value of the '{@link goedegep.icons.iconset.model.IconDescriptor#getUrl <em>Url</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Url</em>' attribute.
   * @see #isSetUrl()
   * @see #unsetUrl()
   * @see #getUrl()
   * @generated
   */
  void setUrl(String value);

  /**
   * Unsets the value of the '{@link goedegep.icons.iconset.model.IconDescriptor#getUrl <em>Url</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetUrl()
   * @see #getUrl()
   * @see #setUrl(String)
   * @generated
   */
  void unsetUrl();

  /**
   * Returns whether the value of the '{@link goedegep.icons.iconset.model.IconDescriptor#getUrl <em>Url</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Url</em>' attribute is set.
   * @see #unsetUrl()
   * @see #getUrl()
   * @see #setUrl(String)
   * @generated
   */
  boolean isSetUrl();

  /**
   * Returns the value of the '<em><b>Icon Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Icon Id</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Icon Id</em>' attribute.
   * @see #isSetIconId()
   * @see #unsetIconId()
   * @see #setIconId(int)
   * @see goedegep.icons.iconset.model.ModelPackage#getIconDescriptor_IconId()
   * @model unsettable="true" required="true"
   * @generated
   */
  int getIconId();

  /**
   * Sets the value of the '{@link goedegep.icons.iconset.model.IconDescriptor#getIconId <em>Icon Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Icon Id</em>' attribute.
   * @see #isSetIconId()
   * @see #unsetIconId()
   * @see #getIconId()
   * @generated
   */
  void setIconId(int value);

  /**
   * Unsets the value of the '{@link goedegep.icons.iconset.model.IconDescriptor#getIconId <em>Icon Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetIconId()
   * @see #getIconId()
   * @see #setIconId(int)
   * @generated
   */
  void unsetIconId();

  /**
   * Returns whether the value of the '{@link goedegep.icons.iconset.model.IconDescriptor#getIconId <em>Icon Id</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Icon Id</em>' attribute is set.
   * @see #unsetIconId()
   * @see #getIconId()
   * @see #setIconId(int)
   * @generated
   */
  boolean isSetIconId();

} // IconDescriptor
