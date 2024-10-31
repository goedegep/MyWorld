/**
 */
package goedegep.media.mediadb.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Track</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A music track that may appear on one or more albums.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.Track#getTitle <em>Title</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Track#getDuration <em>Duration</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Track#getPlayers <em>Players</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Track#getArtist <em>Artist</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Track#getParts <em>Parts</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Track#getAuthors <em>Authors</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Track#getReferredBy <em>Referred By</em>}</li>
 * </ul>
 *
 * @see goedegep.media.mediadb.model.MediadbPackage#getTrack()
 * @model
 * @generated
 */
public interface Track extends EObject {
  /**
   * Returns the value of the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Title</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Title</em>' attribute.
   * @see #isSetTitle()
   * @see #unsetTitle()
   * @see #setTitle(String)
   * @see goedegep.media.mediadb.model.MediadbPackage#getTrack_Title()
   * @model unsettable="true"
   * @generated
   */
  String getTitle();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.Track#getTitle <em>Title</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Track#getTitle <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetTitle()
   * @see #getTitle()
   * @see #setTitle(String)
   * @generated
   */
  void unsetTitle();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Track#getTitle <em>Title</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Title</em>' attribute is set.
   * @see #unsetTitle()
   * @see #getTitle()
   * @see #setTitle(String)
   * @generated
   */
  boolean isSetTitle();

  /**
   * Returns the value of the '<em><b>Duration</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Duration</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Duration</em>' attribute.
   * @see #isSetDuration()
   * @see #unsetDuration()
   * @see #setDuration(Integer)
   * @see goedegep.media.mediadb.model.MediadbPackage#getTrack_Duration()
   * @model unsettable="true"
   * @generated
   */
  Integer getDuration();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.Track#getDuration <em>Duration</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Duration</em>' attribute.
   * @see #isSetDuration()
   * @see #unsetDuration()
   * @see #getDuration()
   * @generated
   */
  void setDuration(Integer value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Track#getDuration <em>Duration</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDuration()
   * @see #getDuration()
   * @see #setDuration(Integer)
   * @generated
   */
  void unsetDuration();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Track#getDuration <em>Duration</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Duration</em>' attribute is set.
   * @see #unsetDuration()
   * @see #getDuration()
   * @see #setDuration(Integer)
   * @generated
   */
  boolean isSetDuration();

  /**
   * Returns the value of the '<em><b>Players</b></em>' reference list.
   * The list contents are of type {@link goedegep.media.mediadb.model.Player}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Players</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Players</em>' reference list.
   * @see #isSetPlayers()
   * @see #unsetPlayers()
   * @see goedegep.media.mediadb.model.MediadbPackage#getTrack_Players()
   * @model unsettable="true"
   * @generated
   */
  EList<Player> getPlayers();

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Track#getPlayers <em>Players</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetPlayers()
   * @see #getPlayers()
   * @generated
   */
  void unsetPlayers();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Track#getPlayers <em>Players</em>}' reference list is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Players</em>' reference list is set.
   * @see #unsetPlayers()
   * @see #getPlayers()
   * @generated
   */
  boolean isSetPlayers();

  /**
   * Returns the value of the '<em><b>Artist</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Artist</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Artist</em>' reference.
   * @see #isSetArtist()
   * @see #unsetArtist()
   * @see #setArtist(Artist)
   * @see goedegep.media.mediadb.model.MediadbPackage#getTrack_Artist()
   * @model unsettable="true"
   * @generated
   */
  Artist getArtist();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.Track#getArtist <em>Artist</em>}' reference.
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
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Track#getArtist <em>Artist</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetArtist()
   * @see #getArtist()
   * @see #setArtist(Artist)
   * @generated
   */
  void unsetArtist();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Track#getArtist <em>Artist</em>}' reference is set.
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
   * Returns the value of the '<em><b>Parts</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.media.mediadb.model.TrackPart}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Parts</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Parts</em>' containment reference list.
   * @see goedegep.media.mediadb.model.MediadbPackage#getTrack_Parts()
   * @model containment="true"
   * @generated
   */
  EList<TrackPart> getParts();

  /**
   * Returns the value of the '<em><b>Authors</b></em>' reference list.
   * The list contents are of type {@link goedegep.media.mediadb.model.Artist}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Authors</em>' reference list.
   * @see #isSetAuthors()
   * @see #unsetAuthors()
   * @see goedegep.media.mediadb.model.MediadbPackage#getTrack_Authors()
   * @model unsettable="true"
   * @generated
   */
  EList<Artist> getAuthors();

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Track#getAuthors <em>Authors</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetAuthors()
   * @see #getAuthors()
   * @generated
   */
  void unsetAuthors();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Track#getAuthors <em>Authors</em>}' reference list is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Authors</em>' reference list is set.
   * @see #unsetAuthors()
   * @see #getAuthors()
   * @generated
   */
  boolean isSetAuthors();

  /**
   * Returns the value of the '<em><b>Referred By</b></em>' reference list.
   * The list contents are of type {@link goedegep.media.mediadb.model.TrackReference}.
   * It is bidirectional and its opposite is '{@link goedegep.media.mediadb.model.TrackReference#getTrack <em>Track</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Referred By</em>' reference list.
   * @see goedegep.media.mediadb.model.MediadbPackage#getTrack_ReferredBy()
   * @see goedegep.media.mediadb.model.TrackReference#getTrack
   * @model opposite="track"
   * @generated
   */
  EList<TrackReference> getReferredBy();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get the {@code TrackReference} of the original album.
   * <p>
   * This is the 'referred by' {@code TrackReference} which has its {@code originalAlbumTrackReference} set.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  TrackReference getOriginalDiscTrackReference();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get the track artist. If {@code artist} is set, this is returned. Else, if {@code originalDisc} is set, the artist of the album of that disc is returned.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  Artist getTrackArtist();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get the {@code Disc} of the original album.
   * <p>
   * This is equal to calling {@code getOriginalDiscTrackReference()} followed by calling {@code getDisc()} on the obtained {@code TrackReference} (if it isn't null).
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  Disc getOriginalDisc();

} // Track
