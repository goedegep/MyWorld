/**
 */
package goedegep.rolodex.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Archive</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.Archive#isArchived <em>Archived</em>}</li>
 * </ul>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getArchive()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Archive extends EObject {
  /**
   * Returns the value of the '<em><b>Archived</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Archived</em>' attribute.
   * @see #isSetArchived()
   * @see #unsetArchived()
   * @see #setArchived(boolean)
   * @see goedegep.rolodex.model.RolodexPackage#getArchive_Archived()
   * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
   * @generated
   */
  boolean isArchived();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Archive#isArchived <em>Archived</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Archived</em>' attribute.
   * @see #isSetArchived()
   * @see #unsetArchived()
   * @see #isArchived()
   * @generated
   */
  void setArchived(boolean value);

  /**
   * Unsets the value of the '{@link goedegep.rolodex.model.Archive#isArchived <em>Archived</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetArchived()
   * @see #isArchived()
   * @see #setArchived(boolean)
   * @generated
   */
  void unsetArchived();

  /**
   * Returns whether the value of the '{@link goedegep.rolodex.model.Archive#isArchived <em>Archived</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Archived</em>' attribute is set.
   * @see #unsetArchived()
   * @see #isArchived()
   * @see #setArchived(boolean)
   * @generated
   */
  boolean isSetArchived();

} // Archive
