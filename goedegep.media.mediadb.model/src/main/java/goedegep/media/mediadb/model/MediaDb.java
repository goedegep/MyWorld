/**
 */
package goedegep.media.mediadb.model;

import goedegep.util.datetime.FlexDate;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Media Db</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.MediaDb#getArtists <em>Artists</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.MediaDb#getAlbums <em>Albums</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.MediaDb#getTracks <em>Tracks</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.MediaDb#getTrackcollections <em>Trackcollections</em>}</li>
 * </ul>
 *
 * @see goedegep.media.mediadb.model.MediadbPackage#getMediaDb()
 * @model
 * @generated
 */
public interface MediaDb extends EObject {
  /**
   * Returns the value of the '<em><b>Artists</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.media.mediadb.model.Artist}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Artists</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Artists</em>' containment reference list.
   * @see goedegep.media.mediadb.model.MediadbPackage#getMediaDb_Artists()
   * @model containment="true"
   * @generated
   */
  EList<Artist> getArtists();

  /**
   * Returns the value of the '<em><b>Albums</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.media.mediadb.model.Album}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Albums</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Albums</em>' containment reference list.
   * @see goedegep.media.mediadb.model.MediadbPackage#getMediaDb_Albums()
   * @model containment="true"
   * @generated
   */
  EList<Album> getAlbums();

  /**
   * Returns the value of the '<em><b>Tracks</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.media.mediadb.model.Track}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Tracks</em>' containment reference list.
   * @see goedegep.media.mediadb.model.MediadbPackage#getMediaDb_Tracks()
   * @model containment="true"
   * @generated
   */
  EList<Track> getTracks();

  /**
   * Returns the value of the '<em><b>Trackcollections</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.media.mediadb.model.TrackCollection}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Trackcollections</em>' containment reference list.
   * @see goedegep.media.mediadb.model.MediadbPackage#getMediaDb_Trackcollections()
   * @model containment="true"
   * @generated
   */
  EList<TrackCollection> getTrackcollections();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model
   * @generated
   */
  Artist getArtist(String artistName);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model releaseDateDataType="goedegep.types.model.EFlexDate"
   * @generated
   */
  Album getAlbum(FlexDate releaseDate, Artist artist, String title);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model ordered="false" releaseDateDataType="goedegep.types.model.EFlexDate"
   * @generated
   */
  EList<Album> getAlbums(FlexDate releaseDate, Artist artist, String title);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model
   * @generated
   */
  TrackCollection getTrackCollection(Collection collection);

} // MediaDb
