/**
 */
package goedegep.media.mediadb.model.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Video;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackCollection;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.text.FuzzyStringCompare;

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
  private static final Logger LOGGER = Logger.getLogger(MediaDbImpl.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final FlexDateFormat FDF = new FlexDateFormat();

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
   * @generated NOT
   */
  public Artist getArtist(String artistName) {
    for (Artist artist : getArtists()) {
      if (artist.getName().equals(artistName)) {
        return artist;
      }
    }

    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public Album getAlbum(FlexDate releaseDate, Artist artist, String title) {
    assert artist != null;
    assert title != null;

    for (Album album : getAlbums()) {
      boolean compareOkSoFar = true;

      // Check release date
      if (releaseDate == null) {
        LOGGER.fine("date OK, not specified");
      } else if (album.getReleaseDate() == null) {
        LOGGER.fine("date not OK; date specified, but not available in album");
        compareOkSoFar = false;
      } else {
        LOGGER.fine("Real date check");
        LOGGER.fine("releaseDate: " + FDF.format(releaseDate) + ", this album: " + FDF.format(album.getReleaseDate()));
        if (album.getReleaseDate().compareTo(releaseDate) == 0) {
          LOGGER.fine("date OK!!!");
        } else {
          LOGGER.fine("date not OK");
          compareOkSoFar = false;
        }
      }

      if (compareOkSoFar && FuzzyStringCompare.fuzzyStringCompare(album.getArtist().getName(), artist.getName())
          && FuzzyStringCompare.fuzzyStringCompare(album.getTitle(), title)) {
        return album;
      }
    }

    // No album found, go through the albums again but now check on the containerArtist.
    for (Album album : getAlbums()) {
      boolean compareOkSoFar = true;

      // Check release date
      if (releaseDate == null) {
        LOGGER.fine("date OK, not specified");
      } else if (album.getReleaseDate() == null) {
        LOGGER.fine("date not OK; date specified, but not available in album");
        compareOkSoFar = false;
      } else {
        LOGGER.fine("Real date check");
        LOGGER.fine("releaseDate: " + FDF.format(releaseDate) + ", this album: " + FDF.format(album.getReleaseDate()));
        if (album.getReleaseDate().compareTo(releaseDate) == 0) {
          LOGGER.fine("date OK!!!");
        } else {
          LOGGER.fine("date not OK");
          compareOkSoFar = false;
        }
      }

      if (compareOkSoFar && album.getArtist().isSetContainerArtist()
          && FuzzyStringCompare.fuzzyStringCompare(album.getArtist().getContainerArtist().getName(), artist.getName())
          && FuzzyStringCompare.fuzzyStringCompare(album.getTitle(), title)) {
        return album;
      }
    }

    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public EList<Album> getAlbums(FlexDate releaseDate, Artist artist, String title) {
    EList<Album> albums = new BasicEList<Album>();
    if ((artist != null) && artist.isSetContainerArtist()) {
      Artist containerArtist = artist.getContainerArtist();
      if (containerArtist == null) {
        throw new RuntimeException("container artist is null for artist: " + artist.getName());
      }
      artist = containerArtist;
    }

    for (Album album : getAlbums()) {
      boolean compareOkSoFar = true;

      // Check release date
      if (releaseDate == null) {
        // no release date check needed
        LOGGER.fine("date OK, not specified");
      } else if (!album.isSetReleaseDate()) {
        LOGGER.fine("date not OK; date specified, but not available in album");
        compareOkSoFar = false;
      } else {
        LOGGER.fine("Real date check");
        LOGGER.fine("releaseDate: " + FDF.format(releaseDate) + ", this album: " + FDF.format(album.getReleaseDate()));
        if (album.getReleaseDate().getYear() == null) {
          LOGGER.severe("Year is null for album: " + album.toString());
        }
        if (album.getReleaseDate().isSpecializationOf(releaseDate)) {
          LOGGER.fine("date OK!!!");
        } else {
          LOGGER.fine("date not OK");
          compareOkSoFar = false;
        }
      }

      Artist albumArtist = album.getArtist();
      if ((albumArtist != null) && albumArtist.isSetContainerArtist()) {
        albumArtist = albumArtist.getContainerArtist();
      }
      if (albumArtist == null) {
        LOGGER.info("albumArtist is null for album: " + album.getTitle());
      }
      if (compareOkSoFar) {
        if ((artist != null) && (albumArtist == null) || (artist == null) && (albumArtist != null)) {
          compareOkSoFar = false;
        } else if ((artist != null) && (albumArtist != null)) {
          compareOkSoFar = artist.getName().equals(albumArtist.getName());
        }
      }

      if (compareOkSoFar && (title != null)) {
        if (!title.equals(album.getTitle())) {
          compareOkSoFar = false;
        }
      }

      if (compareOkSoFar) {
        albums.add(album);
      }
    }

    return albums;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public TrackCollection getTrackCollection(goedegep.media.mediadb.model.Collection collection) {
    TrackCollection trackCollection = null;

    for (TrackCollection aTrackCollection : getTrackcollections()) {
      if (aTrackCollection.getCollection().equals(collection)) {
        trackCollection = aTrackCollection;
        break;
      }
    }

    if (trackCollection == null) {
      trackCollection = MediadbFactory.eINSTANCE.createTrackCollection();
      trackCollection.setCollection(collection);
      getTrackcollections().add(trackCollection);
    }

    return trackCollection;
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
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
    switch (operationID) {
    case MediadbPackage.MEDIA_DB___GET_ARTIST__STRING:
      return getArtist((String) arguments.get(0));
    case MediadbPackage.MEDIA_DB___GET_ALBUM__FLEXDATE_ARTIST_STRING:
      return getAlbum((FlexDate) arguments.get(0), (Artist) arguments.get(1), (String) arguments.get(2));
    case MediadbPackage.MEDIA_DB___GET_ALBUMS__FLEXDATE_ARTIST_STRING:
      return getAlbums((FlexDate) arguments.get(0), (Artist) arguments.get(1), (String) arguments.get(2));
    case MediadbPackage.MEDIA_DB___GET_TRACK_COLLECTION__COLLECTION:
      return getTrackCollection((goedegep.media.mediadb.model.Collection) arguments.get(0));
    }
    return super.eInvoke(operationID, arguments);
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
