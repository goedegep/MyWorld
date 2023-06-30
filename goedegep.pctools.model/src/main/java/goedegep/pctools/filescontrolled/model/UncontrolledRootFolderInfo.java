/**
 */
package goedegep.pctools.filescontrolled.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Uncontrolled Root Folder Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.pctools.filescontrolled.model.UncontrolledRootFolderInfo#getFolderBasePath <em>Folder Base Path</em>}</li>
 * </ul>
 *
 * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getUncontrolledRootFolderInfo()
 * @model
 * @generated
 */
public interface UncontrolledRootFolderInfo extends UncontrolledFolderInfo {

  /**
   * Returns the value of the '<em><b>Folder Base Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Folder Base Path</em>' attribute.
   * @see #isSetFolderBasePath()
   * @see #unsetFolderBasePath()
   * @see #setFolderBasePath(String)
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getUncontrolledRootFolderInfo_FolderBasePath()
   * @model unsettable="true" required="true"
   * @generated
   */
  String getFolderBasePath();

  /**
   * Sets the value of the '{@link goedegep.pctools.filescontrolled.model.UncontrolledRootFolderInfo#getFolderBasePath <em>Folder Base Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Folder Base Path</em>' attribute.
   * @see #isSetFolderBasePath()
   * @see #unsetFolderBasePath()
   * @see #getFolderBasePath()
   * @generated
   */
  void setFolderBasePath(String value);

  /**
   * Unsets the value of the '{@link goedegep.pctools.filescontrolled.model.UncontrolledRootFolderInfo#getFolderBasePath <em>Folder Base Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetFolderBasePath()
   * @see #getFolderBasePath()
   * @see #setFolderBasePath(String)
   * @generated
   */
  void unsetFolderBasePath();

  /**
   * Returns whether the value of the '{@link goedegep.pctools.filescontrolled.model.UncontrolledRootFolderInfo#getFolderBasePath <em>Folder Base Path</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Folder Base Path</em>' attribute is set.
   * @see #unsetFolderBasePath()
   * @see #getFolderBasePath()
   * @see #setFolderBasePath(String)
   * @generated
   */
  boolean isSetFolderBasePath();
} // UncontrolledRootFolderInfo
