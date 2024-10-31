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
 * <!-- begin-model-doc -->
 * This interface is the top level interface for the media (music) database.<br>
 * It contains both general information like information on albums and artists, and personal information like whether I want to have an album and on what media I have it.</br>
 * The interface names for personal information start with 'My', being {@code MyInfo} and {@code MyTrackInfo}.<br/>
 * <h2>Overview of the interfaces</h2>
 * <h3>{@link Album}</h3><
 * The database contains a list of albums, where an album can be any collection of tracks.<br/>
 * An album consists of one or more discs.
 * <h3>{@link Disc}</h3>
 * This interface represents a physical disc like a CD or SACD, but can also represent any collection of tracks.<br/>
 * A disc contains tracks, represented by track references ({@code TrackReference}).
 * <h3>{@link TrackReference}</h3>
 * The tracks of a disc are specified by track references and not directly by tracks. Main reason for this is that the same track (the same recording) can appear on more than one album.
 * <h3>{@link Track}</h3>
 * The database contains a list of tracks, where a track represents a single song/recording.
 * The tracks are referred to (via track references) by the tracks of the discs of the albums and by the tracks in the track collections.
 * <h3>{@link TrackCollection}</h3>
 * The database also contains track collections, which are collections of tracks of a specific genre and for which you don't have albums details.
 * <h3>{@link Artist}</h3>
 * The database has a list of artists, which may be an artist of an album or track, or a player on an album or track, or an author of a track.
 * <h3>{@link Video}</h3>
 * The database has a list of videos.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.MediaDb#getArtists <em>Artists</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.MediaDb#getAlbums <em>Albums</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.MediaDb#getTracks <em>Tracks</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.MediaDb#getTrackcollections <em>Trackcollections</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.MediaDb#getVideos <em>Videos</em>}</li>
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
   * <!-- begin-model-doc -->
   * The collection of artists in the database.
   * <!-- end-model-doc -->
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
   * <!-- begin-model-doc -->
   * The collection of albums in the database.
   * <!-- end-model-doc -->
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
   * <!-- begin-model-doc -->
   * The collection of track collections in the database.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Trackcollections</em>' containment reference list.
   * @see goedegep.media.mediadb.model.MediadbPackage#getMediaDb_Trackcollections()
   * @model containment="true"
   * @generated
   */
  EList<TrackCollection> getTrackcollections();

  /**
   * Returns the value of the '<em><b>Videos</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.media.mediadb.model.Video}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The collection of videos in the database.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Videos</em>' containment reference list.
   * @see goedegep.media.mediadb.model.MediadbPackage#getMediaDb_Videos()
   * @model containment="true"
   * @generated
   */
  EList<Video> getVideos();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get an {@code Artist} from the media database, based on its name.
   * 
   * @param artistName the name of the artist
   * @return the  {@code Artist} with the name {@code artistName}, or null if no such artist exists in the media database.
   * <!-- end-model-doc -->
   * @model
   * @generated
   */
  Artist getArtist(String artistName);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get an album based on release date (optional), artist and title.
   * 
   * @param releaseDate The release date (issue date) of the album (optional)
   * @param artist The {@code Artist} of the album (mandatory)
   * @param title The {@code Title} of the album (mandatory)
   * @return the spefied album, or null if no such album exists in the media database
   * <!-- end-model-doc -->
   * @model releaseDateDataType="goedegep.types.model.EFlexDate"
   * @generated
   */
  Album getAlbum(FlexDate releaseDate, Artist artist, String title);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get all albums with specified release date (optional), artist and title.
   * 
   * @param releaseDate The release date (issue date) of the album (optional)
   * @param artist The {@code Artist} of the album (optional). If the artist has a container artist, then this will be used.
   * @param title The {@code Title} of the album (optional)
   * @return all albums that match the specified values. The returned list may be empty, but it's never null.
   * <!-- end-model-doc -->
   * @model ordered="false" releaseDateDataType="goedegep.types.model.EFlexDate"
   * @generated
   */
  EList<Album> getAlbums(FlexDate releaseDate, Artist artist, String title);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get a specific track collection.
   * 
   * @param collection The {@code Collection} for which the track collection is to be returned.
   * @return the {@code TrackCollection} for the {@collection}.
   * <!-- end-model-doc -->
   * @model
   * @generated
   */
  TrackCollection getTrackCollection(Collection collection);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get a track specified by its artist and title.
   * 
   * @param artist the track {@code Artist} (mandatory).
   * @param titlte the track title (mandatory).
   * @return the {@code Track} with given {@code artist} and {@code title}, or null if such a track doesn't exist. 
   * <!-- end-model-doc -->
   * @model
   * @generated
   */
  Track getTrack(Artist artist, String title);

} // MediaDb
