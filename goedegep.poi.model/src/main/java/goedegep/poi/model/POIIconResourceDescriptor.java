/**
 */
package goedegep.poi.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Icon Resource Descriptor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.poi.model.POIIconResourceDescriptor#getCategory <em>Category</em>}</li>
 *   <li>{@link goedegep.poi.model.POIIconResourceDescriptor#getIconFileName <em>Icon File Name</em>}</li>
 * </ul>
 *
 * @see goedegep.poi.model.POIPackage#getPOIIconResourceDescriptor()
 * @model
 * @generated
 */
public interface POIIconResourceDescriptor extends EObject {
  /**
   * Returns the value of the '<em><b>Category</b></em>' attribute.
   * The default value is <code>"Default POI"</code>.
   * The literals are from the enumeration {@link goedegep.poi.model.POICategoryId}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Category</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Category</em>' attribute.
   * @see goedegep.poi.model.POICategoryId
   * @see #isSetCategory()
   * @see #unsetCategory()
   * @see #setCategory(POICategoryId)
   * @see goedegep.poi.model.POIPackage#getPOIIconResourceDescriptor_Category()
   * @model default="Default POI" unsettable="true"
   * @generated
   */
  POICategoryId getCategory();

  /**
   * Sets the value of the '{@link goedegep.poi.model.POIIconResourceDescriptor#getCategory <em>Category</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Category</em>' attribute.
   * @see goedegep.poi.model.POICategoryId
   * @see #isSetCategory()
   * @see #unsetCategory()
   * @see #getCategory()
   * @generated
   */
  void setCategory(POICategoryId value);

  /**
   * Unsets the value of the '{@link goedegep.poi.model.POIIconResourceDescriptor#getCategory <em>Category</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetCategory()
   * @see #getCategory()
   * @see #setCategory(POICategoryId)
   * @generated
   */
  void unsetCategory();

  /**
   * Returns whether the value of the '{@link goedegep.poi.model.POIIconResourceDescriptor#getCategory <em>Category</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Category</em>' attribute is set.
   * @see #unsetCategory()
   * @see #getCategory()
   * @see #setCategory(POICategoryId)
   * @generated
   */
  boolean isSetCategory();

  /**
   * Returns the value of the '<em><b>Icon File Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Icon File Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Icon File Name</em>' attribute.
   * @see #isSetIconFileName()
   * @see #unsetIconFileName()
   * @see #setIconFileName(String)
   * @see goedegep.poi.model.POIPackage#getPOIIconResourceDescriptor_IconFileName()
   * @model unsettable="true"
   * @generated
   */
  String getIconFileName();

  /**
   * Sets the value of the '{@link goedegep.poi.model.POIIconResourceDescriptor#getIconFileName <em>Icon File Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Icon File Name</em>' attribute.
   * @see #isSetIconFileName()
   * @see #unsetIconFileName()
   * @see #getIconFileName()
   * @generated
   */
  void setIconFileName(String value);

  /**
   * Unsets the value of the '{@link goedegep.poi.model.POIIconResourceDescriptor#getIconFileName <em>Icon File Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetIconFileName()
   * @see #getIconFileName()
   * @see #setIconFileName(String)
   * @generated
   */
  void unsetIconFileName();

  /**
   * Returns whether the value of the '{@link goedegep.poi.model.POIIconResourceDescriptor#getIconFileName <em>Icon File Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Icon File Name</em>' attribute is set.
   * @see #unsetIconFileName()
   * @see #getIconFileName()
   * @see #setIconFileName(String)
   * @generated
   */
  boolean isSetIconFileName();

} // POIIconResourceDescriptor
