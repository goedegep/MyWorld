/**
 */
package goedegep.finan.lynx2finan.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Lynx To Finan Share Id List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.lynx2finan.model.LynxToFinanShareIdList#getEntries <em>Entries</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.lynx2finan.model.LynxToFinanPackage#getLynxToFinanShareIdList()
 * @model
 * @generated
 */
public interface LynxToFinanShareIdList extends EObject {
  /**
   * Returns the value of the '<em><b>Entries</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Entries</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Entries</em>' containment reference list.
   * @see goedegep.finan.lynx2finan.model.LynxToFinanPackage#getLynxToFinanShareIdList_Entries()
   * @model containment="true" ordered="false"
   * @generated
   */
  EList<LynxToFinanShareIdListEntry> getEntries();

} // LynxToFinanShareIdList
