/**
 */
package goedegep.pctools.filescontrolled.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Result</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.pctools.filescontrolled.model.Result#getControlledrootfolderinfos <em>Controlledrootfolderinfos</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.Result#getUncontrolledRootFolderInfos <em>Uncontrolled Root Folder Infos</em>}</li>
 * </ul>
 *
 * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getResult()
 * @model
 * @generated
 */
public interface Result extends EObject {
  /**
   * Returns the value of the '<em><b>Controlledrootfolderinfos</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.pctools.filescontrolled.model.ControlledRootFolderInfo}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Controlledrootfolderinfos</em>' containment reference list.
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getResult_Controlledrootfolderinfos()
   * @model containment="true"
   * @generated
   */
  EList<ControlledRootFolderInfo> getControlledrootfolderinfos();

  /**
   * Returns the value of the '<em><b>Uncontrolled Root Folder Infos</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.pctools.filescontrolled.model.UncontrolledRootFolderInfo}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Uncontrolled Root Folder Infos</em>' containment reference list.
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getResult_UncontrolledRootFolderInfos()
   * @model containment="true"
   * @generated
   */
  EList<UncontrolledRootFolderInfo> getUncontrolledRootFolderInfos();

} // Result
