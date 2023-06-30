/**
 */
package goedegep.pctools.filescontrolled.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Controlled Folder Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.pctools.filescontrolled.model.ControlledFolderInfo#getSubFolderInfos <em>Sub Folder Infos</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.ControlledFolderInfo#getFileinfos <em>Fileinfos</em>}</li>
 * </ul>
 *
 * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getControlledFolderInfo()
 * @model
 * @generated
 */
public interface ControlledFolderInfo extends FolderInfo {
  /**
   * Returns the value of the '<em><b>Sub Folder Infos</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.pctools.filescontrolled.model.ControlledFolderInfo}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Sub Folder Infos</em>' containment reference list.
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getControlledFolderInfo_SubFolderInfos()
   * @model containment="true"
   * @generated
   */
  EList<ControlledFolderInfo> getSubFolderInfos();

  /**
   * Returns the value of the '<em><b>Fileinfos</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.pctools.filescontrolled.model.FileInfo}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fileinfos</em>' containment reference list.
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getControlledFolderInfo_Fileinfos()
   * @model containment="true"
   * @generated
   */
  EList<FileInfo> getFileinfos();

} // ControlledFolderInfo
