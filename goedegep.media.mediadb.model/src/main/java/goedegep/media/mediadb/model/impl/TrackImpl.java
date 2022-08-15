/**
 */
package goedegep.media.mediadb.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.Player;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackPart;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.util.string.StringUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Track</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.impl.TrackImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.TrackImpl#getDuration <em>Duration</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.TrackImpl#getPlayers <em>Players</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.TrackImpl#getArtist <em>Artist</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.TrackImpl#getParts <em>Parts</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.TrackImpl#getAuthors <em>Authors</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.TrackImpl#getReferredBy <em>Referred By</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.TrackImpl#getOriginalDisc <em>Original Disc</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TrackImpl extends MinimalEObjectImpl.Container implements Track {
  private static final String NEWLINE = System.getProperty("line.separator");

  /**
   * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTitle()
   * @generated
   * @ordered
   */
  protected static final String TITLE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTitle()
   * @generated
   * @ordered
   */
  protected String title = TITLE_EDEFAULT;

  /**
   * This is true if the Title attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean titleESet;

  /**
   * The default value of the '{@link #getDuration() <em>Duration</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDuration()
   * @generated
   * @ordered
   */
  protected static final Integer DURATION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDuration() <em>Duration</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDuration()
   * @generated
   * @ordered
   */
  protected Integer duration = DURATION_EDEFAULT;

  /**
   * This is true if the Duration attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean durationESet;

  /**
   * The cached value of the '{@link #getPlayers() <em>Players</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPlayers()
   * @generated
   * @ordered
   */
  protected EList<Player> players;

  /**
   * The cached value of the '{@link #getArtist() <em>Artist</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getArtist()
   * @generated
   * @ordered
   */
  protected Artist artist;

  /**
   * This is true if the Artist reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean artistESet;

  /**
   * The cached value of the '{@link #getParts() <em>Parts</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParts()
   * @generated
   * @ordered
   */
  protected EList<TrackPart> parts;

  /**
   * The cached value of the '{@link #getAuthors() <em>Authors</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAuthors()
   * @generated
   * @ordered
   */
  protected EList<Artist> authors;

  /**
   * The cached value of the '{@link #getReferredBy() <em>Referred By</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getReferredBy()
   * @generated
   * @ordered
   */
  protected EList<TrackReference> referredBy;

  /**
   * The cached value of the '{@link #getOriginalDisc() <em>Original Disc</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOriginalDisc()
   * @generated
   * @ordered
   */
  protected Disc originalDisc;

  /**
   * This is true if the Original Disc reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean originalDiscESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TrackImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MediadbPackage.Literals.TRACK;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getTitle() {
    return title;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTitle(String newTitle) {
    String oldTitle = title;
    title = newTitle;
    boolean oldTitleESet = titleESet;
    titleESet = true;
    if (eNotificationRequired())
      eNotify(
          new ENotificationImpl(this, Notification.SET, MediadbPackage.TRACK__TITLE, oldTitle, title, !oldTitleESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetTitle() {
    String oldTitle = title;
    boolean oldTitleESet = titleESet;
    title = TITLE_EDEFAULT;
    titleESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.TRACK__TITLE, oldTitle, TITLE_EDEFAULT,
          oldTitleESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetTitle() {
    return titleESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Integer getDuration() {
    return duration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDuration(Integer newDuration) {
    Integer oldDuration = duration;
    duration = newDuration;
    boolean oldDurationESet = durationESet;
    durationESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.TRACK__DURATION, oldDuration, duration,
          !oldDurationESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDuration() {
    Integer oldDuration = duration;
    boolean oldDurationESet = durationESet;
    duration = DURATION_EDEFAULT;
    durationESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.TRACK__DURATION, oldDuration,
          DURATION_EDEFAULT, oldDurationESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDuration() {
    return durationESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Player> getPlayers() {
    if (players == null) {
      players = new EObjectResolvingEList.Unsettable<Player>(Player.class, this, MediadbPackage.TRACK__PLAYERS);
    }
    return players;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetPlayers() {
    if (players != null)
      ((InternalEList.Unsettable<?>) players).unset();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetPlayers() {
    return players != null && ((InternalEList.Unsettable<?>) players).isSet();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Artist getArtist() {
    if (artist != null && artist.eIsProxy()) {
      InternalEObject oldArtist = (InternalEObject) artist;
      artist = (Artist) eResolveProxy(oldArtist);
      if (artist != oldArtist) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, MediadbPackage.TRACK__ARTIST, oldArtist, artist));
      }
    }
    return artist;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Artist basicGetArtist() {
    return artist;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setArtist(Artist newArtist) {
    Artist oldArtist = artist;
    artist = newArtist;
    boolean oldArtistESet = artistESet;
    artistESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.TRACK__ARTIST, oldArtist, artist,
          !oldArtistESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetArtist() {
    Artist oldArtist = artist;
    boolean oldArtistESet = artistESet;
    artist = null;
    artistESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.TRACK__ARTIST, oldArtist, null,
          oldArtistESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetArtist() {
    return artistESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<TrackPart> getParts() {
    if (parts == null) {
      parts = new EObjectContainmentEList<TrackPart>(TrackPart.class, this, MediadbPackage.TRACK__PARTS);
    }
    return parts;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Artist> getAuthors() {
    if (authors == null) {
      authors = new EObjectResolvingEList.Unsettable<Artist>(Artist.class, this, MediadbPackage.TRACK__AUTHORS);
    }
    return authors;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetAuthors() {
    if (authors != null)
      ((InternalEList.Unsettable<?>) authors).unset();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetAuthors() {
    return authors != null && ((InternalEList.Unsettable<?>) authors).isSet();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<TrackReference> getReferredBy() {
    if (referredBy == null) {
      referredBy = new EObjectResolvingEList<TrackReference>(TrackReference.class, this,
          MediadbPackage.TRACK__REFERRED_BY);
    }
    return referredBy;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Disc getOriginalDisc() {
    if (originalDisc != null && originalDisc.eIsProxy()) {
      InternalEObject oldOriginalDisc = (InternalEObject) originalDisc;
      originalDisc = (Disc) eResolveProxy(oldOriginalDisc);
      if (originalDisc != oldOriginalDisc) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, MediadbPackage.TRACK__ORIGINAL_DISC,
              oldOriginalDisc, originalDisc));
      }
    }
    return originalDisc;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Disc basicGetOriginalDisc() {
    return originalDisc;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setOriginalDisc(Disc newOriginalDisc) {
    Disc oldOriginalDisc = originalDisc;
    originalDisc = newOriginalDisc;
    boolean oldOriginalDiscESet = originalDiscESet;
    originalDiscESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.TRACK__ORIGINAL_DISC, oldOriginalDisc,
          originalDisc, !oldOriginalDiscESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetOriginalDisc() {
    Disc oldOriginalDisc = originalDisc;
    boolean oldOriginalDiscESet = originalDiscESet;
    originalDisc = null;
    originalDiscESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.TRACK__ORIGINAL_DISC, oldOriginalDisc,
          null, oldOriginalDiscESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetOriginalDisc() {
    return originalDiscESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case MediadbPackage.TRACK__PARTS:
      return ((InternalEList<?>) getParts()).basicRemove(otherEnd, msgs);
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
    case MediadbPackage.TRACK__TITLE:
      return getTitle();
    case MediadbPackage.TRACK__DURATION:
      return getDuration();
    case MediadbPackage.TRACK__PLAYERS:
      return getPlayers();
    case MediadbPackage.TRACK__ARTIST:
      if (resolve)
        return getArtist();
      return basicGetArtist();
    case MediadbPackage.TRACK__PARTS:
      return getParts();
    case MediadbPackage.TRACK__AUTHORS:
      return getAuthors();
    case MediadbPackage.TRACK__REFERRED_BY:
      return getReferredBy();
    case MediadbPackage.TRACK__ORIGINAL_DISC:
      if (resolve)
        return getOriginalDisc();
      return basicGetOriginalDisc();
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
    case MediadbPackage.TRACK__TITLE:
      setTitle((String) newValue);
      return;
    case MediadbPackage.TRACK__DURATION:
      setDuration((Integer) newValue);
      return;
    case MediadbPackage.TRACK__PLAYERS:
      getPlayers().clear();
      getPlayers().addAll((Collection<? extends Player>) newValue);
      return;
    case MediadbPackage.TRACK__ARTIST:
      setArtist((Artist) newValue);
      return;
    case MediadbPackage.TRACK__PARTS:
      getParts().clear();
      getParts().addAll((Collection<? extends TrackPart>) newValue);
      return;
    case MediadbPackage.TRACK__AUTHORS:
      getAuthors().clear();
      getAuthors().addAll((Collection<? extends Artist>) newValue);
      return;
    case MediadbPackage.TRACK__REFERRED_BY:
      getReferredBy().clear();
      getReferredBy().addAll((Collection<? extends TrackReference>) newValue);
      return;
    case MediadbPackage.TRACK__ORIGINAL_DISC:
      setOriginalDisc((Disc) newValue);
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
    case MediadbPackage.TRACK__TITLE:
      unsetTitle();
      return;
    case MediadbPackage.TRACK__DURATION:
      unsetDuration();
      return;
    case MediadbPackage.TRACK__PLAYERS:
      unsetPlayers();
      return;
    case MediadbPackage.TRACK__ARTIST:
      unsetArtist();
      return;
    case MediadbPackage.TRACK__PARTS:
      getParts().clear();
      return;
    case MediadbPackage.TRACK__AUTHORS:
      unsetAuthors();
      return;
    case MediadbPackage.TRACK__REFERRED_BY:
      getReferredBy().clear();
      return;
    case MediadbPackage.TRACK__ORIGINAL_DISC:
      unsetOriginalDisc();
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
    case MediadbPackage.TRACK__TITLE:
      return isSetTitle();
    case MediadbPackage.TRACK__DURATION:
      return isSetDuration();
    case MediadbPackage.TRACK__PLAYERS:
      return isSetPlayers();
    case MediadbPackage.TRACK__ARTIST:
      return isSetArtist();
    case MediadbPackage.TRACK__PARTS:
      return parts != null && !parts.isEmpty();
    case MediadbPackage.TRACK__AUTHORS:
      return isSetAuthors();
    case MediadbPackage.TRACK__REFERRED_BY:
      return referredBy != null && !referredBy.isEmpty();
    case MediadbPackage.TRACK__ORIGINAL_DISC:
      return isSetOriginalDisc();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append(isSetTitle() ? getTitle() : "<no-title>").append(NEWLINE);

    buf.append("duration:");
    buf.append(isSetDuration() ? getDuration() : "<no-duration>").append(NEWLINE);

    buf.append("artist:");
    buf.append(isSetArtist() ? getArtist().getName() : "<no-artist>").append(NEWLINE);

    buf.append("authors:");
    buf.append(StringUtil.objectCollectionToCommaSeparatedStrings(getAuthors())).append(NEWLINE);

    buf.append("players;");
    buf.append(StringUtil.objectCollectionToCommaSeparatedStrings(getPlayers())).append(NEWLINE);

    buf.append("Parts:");
    buf.append(StringUtil.objectCollectionToCommaSeparatedStrings(getParts())).append(NEWLINE);

    buf.append("originalDisc:");
    if (isSetOriginalDisc()) {
      Disc originalDisc = getOriginalDisc();
      Album originalAlbum = originalDisc.getAlbum();
      buf.append(originalAlbum != null ? originalAlbum.getTitle() : "<no title>").append(" - ");
      if (originalDisc.isSetTitle()) {
        buf.append(originalDisc.getTitle());
      } else {
        if (originalAlbum != null) {
          int discNr = originalAlbum.getDiscs().indexOf(originalDisc);
          buf.append(discNr);
        }
      }
    } else {
      buf.append("<no-original-disc>");
    }
    buf.append(NEWLINE);

    boolean first = true;
    buf.append("referredBy:");
    for (TrackReference trackReference : getReferredBy()) {
      if (first) {
        first = false;
      } else {
        buf.append(", ");
      }
      Disc disc = trackReference.getDisc();
      if (disc != null) {
        Album album = disc.getAlbum();
        if (album != null) {
          buf.append(album.getTitle()).append(":").append(trackReference.getTrackNr());
        }
      }
    }
    buf.append(NEWLINE);

    return buf.toString();
  }

} //TrackImpl
