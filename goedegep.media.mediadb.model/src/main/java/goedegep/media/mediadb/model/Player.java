/**
 */
package goedegep.media.mediadb.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Player</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This interface provides information on someone who plays/performs on an album or track. It consists of an {@code Artist} combined with the instrument played by that artist (where vocals are also seen as an instrument).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.Player#getArtist <em>Artist</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Player#getInstruments <em>Instruments</em>}</li>
 * </ul>
 *
 * @see goedegep.media.mediadb.model.MediadbPackage#getPlayer()
 * @model
 * @generated
 */
public interface Player extends EObject {
  /**
   * Returns the value of the '<em><b>Artist</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Artist</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The player.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Artist</em>' reference.
   * @see #isSetArtist()
   * @see #unsetArtist()
   * @see #setArtist(Artist)
   * @see goedegep.media.mediadb.model.MediadbPackage#getPlayer_Artist()
   * @model unsettable="true"
   * @generated
   */
  Artist getArtist();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.Player#getArtist <em>Artist</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Artist</em>' reference.
   * @see #isSetArtist()
   * @see #unsetArtist()
   * @see #getArtist()
   * @generated
   */
  void setArtist(Artist value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Player#getArtist <em>Artist</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetArtist()
   * @see #getArtist()
   * @see #setArtist(Artist)
   * @generated
   */
  void unsetArtist();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Player#getArtist <em>Artist</em>}' reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Artist</em>' reference is set.
   * @see #unsetArtist()
   * @see #getArtist()
   * @see #setArtist(Artist)
   * @generated
   */
  boolean isSetArtist();

  /**
   * Returns the value of the '<em><b>Instruments</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Instruments</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * A list of instruments played by this player. Vocals is also seen as an instrument. An instrument is a free format text.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Instruments</em>' attribute list.
   * @see goedegep.media.mediadb.model.MediadbPackage#getPlayer_Instruments()
   * @model
   * @generated
   */
  EList<String> getInstruments();

} // Player
