/**
 */
package goedegep.pctools.filescontrolled.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Uncontrolled Folder Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.pctools.filescontrolled.model.UncontrolledFolderInfo#isAllContentsHasCopies <em>All Contents Has Copies</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.UncontrolledFolderInfo#getFileinfos <em>Fileinfos</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.UncontrolledFolderInfo#getSubFoldersInfos <em>Sub Folders Infos</em>}</li>
 * </ul>
 *
 * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getUncontrolledFolderInfo()
 * @model
 * @generated
 */
public interface UncontrolledFolderInfo extends FolderInfo {
  /**
   * Returns the value of the '<em><b>All Contents Has Copies</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>All Contents Has Copies</em>' attribute.
   * @see #setAllContentsHasCopies(boolean)
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getUncontrolledFolderInfo_AllContentsHasCopies()
   * @model
   * @generated
   */
  boolean isAllContentsHasCopies();

  /**
   * Sets the value of the '{@link goedegep.pctools.filescontrolled.model.UncontrolledFolderInfo#isAllContentsHasCopies <em>All Contents Has Copies</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>All Contents Has Copies</em>' attribute.
   * @see #isAllContentsHasCopies()
   * @generated
   */
  void setAllContentsHasCopies(boolean value);

  /**
   * Returns the value of the '<em><b>Fileinfos</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.pctools.filescontrolled.model.FileInfo}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fileinfos</em>' containment reference list.
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getUncontrolledFolderInfo_Fileinfos()
   * @model containment="true"
   * @generated
   */
  EList<FileInfo> getFileinfos();

  /**
   * Returns the value of the '<em><b>Sub Folders Infos</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.pctools.filescontrolled.model.UncontrolledFolderInfo}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Sub Folders Infos</em>' containment reference list.
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getUncontrolledFolderInfo_SubFoldersInfos()
   * @model containment="true"
   * @generated
   */
  EList<UncontrolledFolderInfo> getSubFoldersInfos();

} // UncontrolledFolderInfo
