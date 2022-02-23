/**
 */
package goedegep.media.mediadb.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Disc And Track Nrs</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.DiscAndTrackNrs#getDiscNr <em>Disc Nr</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.DiscAndTrackNrs#getTrackNrs <em>Track Nrs</em>}</li>
 * </ul>
 *
 * @see goedegep.media.mediadb.model.MediadbPackage#getDiscAndTrackNrs()
 * @model
 * @generated
 */
public interface DiscAndTrackNrs extends EObject {
  /**
   * Returns the value of the '<em><b>Disc Nr</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Disc Nr</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Disc Nr</em>' attribute.
   * @see #isSetDiscNr()
   * @see #unsetDiscNr()
   * @see #setDiscNr(int)
   * @see goedegep.media.mediadb.model.MediadbPackage#getDiscAndTrackNrs_DiscNr()
   * @model unsettable="true"
   * @generated
   */
  int getDiscNr();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.DiscAndTrackNrs#getDiscNr <em>Disc Nr</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Disc Nr</em>' attribute.
   * @see #isSetDiscNr()
   * @see #unsetDiscNr()
   * @see #getDiscNr()
   * @generated
   */
  void setDiscNr(int value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.DiscAndTrackNrs#getDiscNr <em>Disc Nr</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDiscNr()
   * @see #getDiscNr()
   * @see #setDiscNr(int)
   * @generated
   */
  void unsetDiscNr();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.DiscAndTrackNrs#getDiscNr <em>Disc Nr</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Disc Nr</em>' attribute is set.
   * @see #unsetDiscNr()
   * @see #getDiscNr()
   * @see #setDiscNr(int)
   * @generated
   */
  boolean isSetDiscNr();

  /**
   * Returns the value of the '<em><b>Track Nrs</b></em>' attribute list.
   * The list contents are of type {@link java.lang.Integer}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Track Nrs</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Track Nrs</em>' attribute list.
   * @see #isSetTrackNrs()
   * @see #unsetTrackNrs()
   * @see goedegep.media.mediadb.model.MediadbPackage#getDiscAndTrackNrs_TrackNrs()
   * @model unsettable="true" required="true"
   * @generated
   */
  EList<Integer> getTrackNrs();

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.DiscAndTrackNrs#getTrackNrs <em>Track Nrs</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetTrackNrs()
   * @see #getTrackNrs()
   * @generated
   */
  void unsetTrackNrs();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.DiscAndTrackNrs#getTrackNrs <em>Track Nrs</em>}' attribute list is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Track Nrs</em>' attribute list is set.
   * @see #unsetTrackNrs()
   * @see #getTrackNrs()
   * @generated
   */
  boolean isSetTrackNrs();

} // DiscAndTrackNrs
