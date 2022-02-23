/**
 */
package goedegep.vacations.model;

import goedegep.types.model.FileReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Vacation Element GPX</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.model.GPXTrack#getTrackReference <em>Track Reference</em>}</li>
 * </ul>
 *
 * @see goedegep.vacations.model.VacationsPackage#getGPXTrack()
 * @model
 * @generated
 */
public interface GPXTrack extends VacationElement {
  /**
   * Returns the value of the '<em><b>Track Reference</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Track Reference</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Track Reference</em>' containment reference.
   * @see #isSetTrackReference()
   * @see #unsetTrackReference()
   * @see #setTrackReference(FileReference)
   * @see goedegep.vacations.model.VacationsPackage#getGPXTrack_TrackReference()
   * @model containment="true" unsettable="true"
   * @generated
   */
  FileReference getTrackReference();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.GPXTrack#getTrackReference <em>Track Reference</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Track Reference</em>' containment reference.
   * @see #isSetTrackReference()
   * @see #unsetTrackReference()
   * @see #getTrackReference()
   * @generated
   */
  void setTrackReference(FileReference value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.GPXTrack#getTrackReference <em>Track Reference</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetTrackReference()
   * @see #getTrackReference()
   * @see #setTrackReference(FileReference)
   * @generated
   */
  void unsetTrackReference();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.GPXTrack#getTrackReference <em>Track Reference</em>}' containment reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Track Reference</em>' containment reference is set.
   * @see #unsetTrackReference()
   * @see #getTrackReference()
   * @see #setTrackReference(FileReference)
   * @generated
   */
  boolean isSetTrackReference();

} // VacationElementGPX
