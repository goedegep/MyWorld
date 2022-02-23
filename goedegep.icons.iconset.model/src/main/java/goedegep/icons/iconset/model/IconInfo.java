/**
 */
package goedegep.icons.iconset.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Icon Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.icons.iconset.model.IconInfo#getIconSetId <em>Icon Set Id</em>}</li>
 *   <li>{@link goedegep.icons.iconset.model.IconInfo#getIconId <em>Icon Id</em>}</li>
 * </ul>
 *
 * @see goedegep.icons.iconset.model.ModelPackage#getIconInfo()
 * @model
 * @generated
 */
public interface IconInfo extends EObject {
  /**
   * Returns the value of the '<em><b>Icon Set Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Icon Set Id</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Icon Set Id</em>' attribute.
   * @see #isSetIconSetId()
   * @see #unsetIconSetId()
   * @see #setIconSetId(int)
   * @see goedegep.icons.iconset.model.ModelPackage#getIconInfo_IconSetId()
   * @model unsettable="true" required="true"
   * @generated
   */
  int getIconSetId();

  /**
   * Sets the value of the '{@link goedegep.icons.iconset.model.IconInfo#getIconSetId <em>Icon Set Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Icon Set Id</em>' attribute.
   * @see #isSetIconSetId()
   * @see #unsetIconSetId()
   * @see #getIconSetId()
   * @generated
   */
  void setIconSetId(int value);

  /**
   * Unsets the value of the '{@link goedegep.icons.iconset.model.IconInfo#getIconSetId <em>Icon Set Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetIconSetId()
   * @see #getIconSetId()
   * @see #setIconSetId(int)
   * @generated
   */
  void unsetIconSetId();

  /**
   * Returns whether the value of the '{@link goedegep.icons.iconset.model.IconInfo#getIconSetId <em>Icon Set Id</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Icon Set Id</em>' attribute is set.
   * @see #unsetIconSetId()
   * @see #getIconSetId()
   * @see #setIconSetId(int)
   * @generated
   */
  boolean isSetIconSetId();

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
   * @see goedegep.icons.iconset.model.ModelPackage#getIconInfo_IconId()
   * @model unsettable="true" required="true"
   * @generated
   */
  int getIconId();

  /**
   * Sets the value of the '{@link goedegep.icons.iconset.model.IconInfo#getIconId <em>Icon Id</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.icons.iconset.model.IconInfo#getIconId <em>Icon Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetIconId()
   * @see #getIconId()
   * @see #setIconId(int)
   * @generated
   */
  void unsetIconId();

  /**
   * Returns whether the value of the '{@link goedegep.icons.iconset.model.IconInfo#getIconId <em>Icon Id</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Icon Id</em>' attribute is set.
   * @see #unsetIconId()
   * @see #getIconId()
   * @see #setIconId(int)
   * @generated
   */
  boolean isSetIconId();

} // IconInfo
