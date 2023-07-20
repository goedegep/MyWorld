/**
 */
package goedegep.vacations.model;

import goedegep.types.model.FileReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.model.Document#getDocumentReference <em>Document Reference</em>}</li>
 * </ul>
 *
 * @see goedegep.vacations.model.VacationsPackage#getDocument()
 * @model
 * @generated
 */
public interface Document extends VacationElement {
  /**
   * Returns the value of the '<em><b>Document Reference</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Document Reference</em>' containment reference.
   * @see #isSetDocumentReference()
   * @see #unsetDocumentReference()
   * @see #setDocumentReference(FileReference)
   * @see goedegep.vacations.model.VacationsPackage#getDocument_DocumentReference()
   * @model containment="true" unsettable="true"
   * @generated
   */
  FileReference getDocumentReference();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Document#getDocumentReference <em>Document Reference</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Document Reference</em>' containment reference.
   * @see #isSetDocumentReference()
   * @see #unsetDocumentReference()
   * @see #getDocumentReference()
   * @generated
   */
  void setDocumentReference(FileReference value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.Document#getDocumentReference <em>Document Reference</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDocumentReference()
   * @see #getDocumentReference()
   * @see #setDocumentReference(FileReference)
   * @generated
   */
  void unsetDocumentReference();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Document#getDocumentReference <em>Document Reference</em>}' containment reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Document Reference</em>' containment reference is set.
   * @see #unsetDocumentReference()
   * @see #getDocumentReference()
   * @see #setDocumentReference(FileReference)
   * @generated
   */
  boolean isSetDocumentReference();

} // Document
