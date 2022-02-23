/**
 */
package goedegep.icons.iconset.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Icon Data</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.icons.iconset.model.IconData#getData <em>Data</em>}</li>
 * </ul>
 *
 * @see goedegep.icons.iconset.model.ModelPackage#getIconData()
 * @model
 * @generated
 */
public interface IconData extends EObject {
  /**
   * Returns the value of the '<em><b>Data</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Data</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Data</em>' attribute.
   * @see #isSetData()
   * @see #unsetData()
   * @see #setData(byte[])
   * @see goedegep.icons.iconset.model.ModelPackage#getIconData_Data()
   * @model unsettable="true"
   * @generated
   */
  byte[] getData();

  /**
   * Sets the value of the '{@link goedegep.icons.iconset.model.IconData#getData <em>Data</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Data</em>' attribute.
   * @see #isSetData()
   * @see #unsetData()
   * @see #getData()
   * @generated
   */
  void setData(byte[] value);

  /**
   * Unsets the value of the '{@link goedegep.icons.iconset.model.IconData#getData <em>Data</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetData()
   * @see #getData()
   * @see #setData(byte[])
   * @generated
   */
  void unsetData();

  /**
   * Returns whether the value of the '{@link goedegep.icons.iconset.model.IconData#getData <em>Data</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Data</em>' attribute is set.
   * @see #unsetData()
   * @see #getData()
   * @see #setData(byte[])
   * @generated
   */
  boolean isSetData();

} // IconData
