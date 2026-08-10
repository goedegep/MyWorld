/**
 */
package goedegep.media.mediadb.model.impl;

import java.util.Collection;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackCollection;
import goedegep.media.mediadb.model.Video;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Media Db</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.impl.MediaDbImpl#getArtists <em>Artists</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.MediaDbImpl#getAlbums <em>Albums</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.MediaDbImpl#getTracks <em>Tracks</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.MediaDbImpl#getTrackcollections <em>Trackcollections</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.MediaDbImpl#getVideos <em>Videos</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MediaDbImpl extends MinimalEObjectImpl.Container implements MediaDb {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(MediaDbImpl.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");

  /**
   * The cached value of the '{@link #getArtists() <em>Artists</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getArtists()
   * @generated
   * @ordered
   */
  protected EList<Artist> artists;

  /**
   * The cached value of the '{@link #getAlbums() <em>Albums</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAlbums()
   * @generated
   * @ordered
   */
  protected EList<Album> albums;

  /**
   * The cached value of the '{@link #getTracks() <em>Tracks</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTracks()
   * @generated
   * @ordered
   */
  protected EList<Track> tracks;

  /**
   * The cached value of the '{@link #getTrackcollections() <em>Trackcollections</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTrackcollections()
   * @generated
   * @ordered
   */
  protected EList<TrackCollection> trackcollections;

  /**
   * The cached value of the '{@link #getVideos() <em>Videos</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVideos()
   * @generated
   * @ordered
   */
  protected EList<Video> videos;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MediaDbImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MediadbPackage.Literals.MEDIA_DB;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Artist> getArtists() {
    if (artists == null) {
      artists = new EObjectContainmentEList<Artist>(Artist.class, this, MediadbPackage.MEDIA_DB__ARTISTS);
    }
    return artists;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Album> getAlbums() {
    if (albums == null) {
      albums = new EObjectContainmentEList<Album>(Album.class, this, MediadbPackage.MEDIA_DB__ALBUMS);
    }
    return albums;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Track> getTracks() {
    if (tracks == null) {
      tracks = new EObjectContainmentEList<Track>(Track.class, this, MediadbPackage.MEDIA_DB__TRACKS);
    }
    return tracks;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<TrackCollection> getTrackcollections() {
    if (trackcollections == null) {
      trackcollections = new EObjectContainmentEList<TrackCollection>(TrackCollection.class, this,
          MediadbPackage.MEDIA_DB__TRACKCOLLECTIONS);
    }
    return trackcollections;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Video> getVideos() {
    if (videos == null) {
      videos = new EObjectContainmentEList<Video>(Video.class, this, MediadbPackage.MEDIA_DB__VIDEOS);
    }
    return videos;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case MediadbPackage.MEDIA_DB__ARTISTS:
      return ((InternalEList<?>) getArtists()).basicRemove(otherEnd, msgs);
    case MediadbPackage.MEDIA_DB__ALBUMS:
      return ((InternalEList<?>) getAlbums()).basicRemove(otherEnd, msgs);
    case MediadbPackage.MEDIA_DB__TRACKS:
      return ((InternalEList<?>) getTracks()).basicRemove(otherEnd, msgs);
    case MediadbPackage.MEDIA_DB__TRACKCOLLECTIONS:
      return ((InternalEList<?>) getTrackcollections()).basicRemove(otherEnd, msgs);
    case MediadbPackage.MEDIA_DB__VIDEOS:
      return ((InternalEList<?>) getVideos()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case MediadbPackage.MEDIA_DB__ARTISTS:
      return getArtists();
    case MediadbPackage.MEDIA_DB__ALBUMS:
      return getAlbums();
    case MediadbPackage.MEDIA_DB__TRACKS:
      return getTracks();
    case MediadbPackage.MEDIA_DB__TRACKCOLLECTIONS:
      return getTrackcollections();
    case MediadbPackage.MEDIA_DB__VIDEOS:
      return getVideos();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
    case MediadbPackage.MEDIA_DB__ARTISTS:
      getArtists().clear();
      getArtists().addAll((Collection<? extends Artist>) newValue);
      return;
    case MediadbPackage.MEDIA_DB__ALBUMS:
      getAlbums().clear();
      getAlbums().addAll((Collection<? extends Album>) newValue);
      return;
    case MediadbPackage.MEDIA_DB__TRACKS:
      getTracks().clear();
      getTracks().addAll((Collection<? extends Track>) newValue);
      return;
    case MediadbPackage.MEDIA_DB__TRACKCOLLECTIONS:
      getTrackcollections().clear();
      getTrackcollections().addAll((Collection<? extends TrackCollection>) newValue);
      return;
    case MediadbPackage.MEDIA_DB__VIDEOS:
      getVideos().clear();
      getVideos().addAll((Collection<? extends Video>) newValue);
      return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID) {
    switch (featureID) {
    case MediadbPackage.MEDIA_DB__ARTISTS:
      getArtists().clear();
      return;
    case MediadbPackage.MEDIA_DB__ALBUMS:
      getAlbums().clear();
      return;
    case MediadbPackage.MEDIA_DB__TRACKS:
      getTracks().clear();
      return;
    case MediadbPackage.MEDIA_DB__TRACKCOLLECTIONS:
      getTrackcollections().clear();
      return;
    case MediadbPackage.MEDIA_DB__VIDEOS:
      getVideos().clear();
      return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID) {
    switch (featureID) {
    case MediadbPackage.MEDIA_DB__ARTISTS:
      return artists != null && !artists.isEmpty();
    case MediadbPackage.MEDIA_DB__ALBUMS:
      return albums != null && !albums.isEmpty();
    case MediadbPackage.MEDIA_DB__TRACKS:
      return tracks != null && !tracks.isEmpty();
    case MediadbPackage.MEDIA_DB__TRACKCOLLECTIONS:
      return trackcollections != null && !trackcollections.isEmpty();
    case MediadbPackage.MEDIA_DB__VIDEOS:
      return videos != null && !videos.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
     * @generated NOT
     */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();

    buf.append(NEWLINE);

    buf.append("Artists:");
    buf.append(NEWLINE);
    for (Artist artist : getArtists()) {
      buf.append(artist.toString());
    }

    buf.append(NEWLINE);

    buf.append("Albums:");
    buf.append(NEWLINE);
    for (Album album : getAlbums()) {
      buf.append(album.toString());
    }

    buf.append(NEWLINE);

    buf.append("Songs:");
    buf.append(NEWLINE);

    return buf.toString();
  }

} //MediaDbImpl
