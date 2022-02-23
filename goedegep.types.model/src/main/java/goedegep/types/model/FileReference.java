/**
 */
package goedegep.types.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bestand Referentie</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.types.model.FileReference#getFile <em>File</em>}</li>
 *   <li>{@link goedegep.types.model.FileReference#getTitle <em>Title</em>}</li>
 * </ul>
 *
 * @see goedegep.types.model.TypesPackage#getFileReference()
 * @model
 * @generated
 */
public interface FileReference extends EObject {
  /**
   * Returns the value of the '<em><b>File</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Bestand</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>File</em>' attribute.
   * @see #isSetFile()
   * @see #unsetFile()
   * @see #setFile(String)
   * @see goedegep.types.model.TypesPackage#getFileReference_File()
   * @model unsettable="true" required="true"
   * @generated
   */
  String getFile();

  /**
   * Sets the value of the '{@link goedegep.types.model.FileReference#getFile <em>File</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>File</em>' attribute.
   * @see #isSetFile()
   * @see #unsetFile()
   * @see #getFile()
   * @generated
   */
  void setFile(String value);

  /**
   * Unsets the value of the '{@link goedegep.types.model.FileReference#getFile <em>File</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetFile()
   * @see #getFile()
   * @see #setFile(String)
   * @generated
   */
  void unsetFile();

  /**
   * Returns whether the value of the '{@link goedegep.types.model.FileReference#getFile <em>File</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>File</em>' attribute is set.
   * @see #unsetFile()
   * @see #getFile()
   * @see #setFile(String)
   * @generated
   */
  boolean isSetFile();

  /**
   * Returns the value of the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Titel</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Title</em>' attribute.
   * @see #isSetTitle()
   * @see #unsetTitle()
   * @see #setTitle(String)
   * @see goedegep.types.model.TypesPackage#getFileReference_Title()
   * @model unsettable="true"
   * @generated
   */
  String getTitle();

  /**
   * Sets the value of the '{@link goedegep.types.model.FileReference#getTitle <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Title</em>' attribute.
   * @see #isSetTitle()
   * @see #unsetTitle()
   * @see #getTitle()
   * @generated
   */
  void setTitle(String value);

  /**
   * Unsets the value of the '{@link goedegep.types.model.FileReference#getTitle <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetTitle()
   * @see #getTitle()
   * @see #setTitle(String)
   * @generated
   */
  void unsetTitle();

  /**
   * Returns whether the value of the '{@link goedegep.types.model.FileReference#getTitle <em>Title</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Title</em>' attribute is set.
   * @see #unsetTitle()
   * @see #getTitle()
   * @see #setTitle(String)
   * @generated
   */
  boolean isSetTitle();

} // BestandReferentie
