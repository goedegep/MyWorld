/**
 */
package goedegep.pctools.filescontrolled.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Folder Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.pctools.filescontrolled.model.FolderInfo#getFolderName <em>Folder Name</em>}</li>
 * </ul>
 *
 * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getFolderInfo()
 * @model
 * @generated
 */
public interface FolderInfo extends EObject {
  /**
   * Returns the value of the '<em><b>Folder Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Folder Name</em>' attribute.
   * @see #isSetFolderName()
   * @see #unsetFolderName()
   * @see #setFolderName(String)
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getFolderInfo_FolderName()
   * @model unsettable="true" required="true"
   * @generated
   */
  String getFolderName();

  /**
   * Sets the value of the '{@link goedegep.pctools.filescontrolled.model.FolderInfo#getFolderName <em>Folder Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Folder Name</em>' attribute.
   * @see #isSetFolderName()
   * @see #unsetFolderName()
   * @see #getFolderName()
   * @generated
   */
  void setFolderName(String value);

  /**
   * Unsets the value of the '{@link goedegep.pctools.filescontrolled.model.FolderInfo#getFolderName <em>Folder Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetFolderName()
   * @see #getFolderName()
   * @see #setFolderName(String)
   * @generated
   */
  void unsetFolderName();

  /**
   * Returns whether the value of the '{@link goedegep.pctools.filescontrolled.model.FolderInfo#getFolderName <em>Folder Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Folder Name</em>' attribute is set.
   * @see #unsetFolderName()
   * @see #getFolderName()
   * @see #setFolderName(String)
   * @generated
   */
  boolean isSetFolderName();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model kind="operation" required="true"
   * @generated
   */
  String getFullPathname();

} // FolderInfo
