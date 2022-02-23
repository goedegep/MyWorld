/**
 */
package goedegep.vacations.model;

import goedegep.types.model.FileReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Map Image</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.model.MapImage#getImageReference <em>Image Reference</em>}</li>
 * </ul>
 *
 * @see goedegep.vacations.model.VacationsPackage#getMapImage()
 * @model
 * @generated
 */
public interface MapImage extends VacationElement {
  /**
   * Returns the value of the '<em><b>Image Reference</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Image Reference</em>' containment reference.
   * @see #setImageReference(FileReference)
   * @see goedegep.vacations.model.VacationsPackage#getMapImage_ImageReference()
   * @model containment="true"
   * @generated
   */
  FileReference getImageReference();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.MapImage#getImageReference <em>Image Reference</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Image Reference</em>' containment reference.
   * @see #getImageReference()
   * @generated
   */
  void setImageReference(FileReference value);

} // MapImage
