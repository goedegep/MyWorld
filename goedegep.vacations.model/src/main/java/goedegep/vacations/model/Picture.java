/**
 */
package goedegep.vacations.model;

import goedegep.types.model.FileReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Vacation Element Picture</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.model.Picture#getPictureReference <em>Picture Reference</em>}</li>
 * </ul>
 *
 * @see goedegep.vacations.model.VacationsPackage#getPicture()
 * @model
 * @generated
 */
public interface Picture extends VacationElement {
  /**
   * Returns the value of the '<em><b>Picture Reference</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Picture Reference</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Picture Reference</em>' containment reference.
   * @see #isSetPictureReference()
   * @see #unsetPictureReference()
   * @see #setPictureReference(FileReference)
   * @see goedegep.vacations.model.VacationsPackage#getPicture_PictureReference()
   * @model containment="true" unsettable="true"
   * @generated
   */
  FileReference getPictureReference();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Picture#getPictureReference <em>Picture Reference</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Picture Reference</em>' containment reference.
   * @see #isSetPictureReference()
   * @see #unsetPictureReference()
   * @see #getPictureReference()
   * @generated
   */
  void setPictureReference(FileReference value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.Picture#getPictureReference <em>Picture Reference</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetPictureReference()
   * @see #getPictureReference()
   * @see #setPictureReference(FileReference)
   * @generated
   */
  void unsetPictureReference();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Picture#getPictureReference <em>Picture Reference</em>}' containment reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Picture Reference</em>' containment reference is set.
   * @see #unsetPictureReference()
   * @see #getPictureReference()
   * @see #setPictureReference(FileReference)
   * @generated
   */
  boolean isSetPictureReference();

} // VacationElementPicture
