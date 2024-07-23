/**
 */
package goedegep.media.mediadb.model.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Track Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.impl.TrackReferenceImpl#getTrack <em>Track</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.TrackReferenceImpl#getBonusTrack <em>Bonus Track</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.TrackReferenceImpl#getMyTrackInfo <em>My Track Info</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.TrackReferenceImpl#getOriginalAlbumTrackReference <em>Original Album Track Reference</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TrackReferenceImpl extends MinimalEObjectImpl.Container implements TrackReference {
  private static final String NEW_LINE = System.getProperty("line.separator");

  /**
   * The cached value of the '{@link #getTrack() <em>Track</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTrack()
   * @generated
   * @ordered
   */
  protected Track track;

  /**
   * This is true if the Track reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean trackESet;

  /**
   * The default value of the '{@link #getBonusTrack() <em>Bonus Track</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBonusTrack()
   * @generated
   * @ordered
   */
  protected static final String BONUS_TRACK_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getBonusTrack() <em>Bonus Track</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBonusTrack()
   * @generated
   * @ordered
   */
  protected String bonusTrack = BONUS_TRACK_EDEFAULT;

  /**
   * This is true if the Bonus Track attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean bonusTrackESet;

  /**
   * The cached value of the '{@link #getMyTrackInfo() <em>My Track Info</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMyTrackInfo()
   * @generated
   * @ordered
   */
  protected MyTrackInfo myTrackInfo;

  /**
   * This is true if the My Track Info containment reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean myTrackInfoESet;

  /**
   * The cached value of the '{@link #getOriginalAlbumTrackReference() <em>Original Album Track Reference</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOriginalAlbumTrackReference()
   * @generated
   * @ordered
   */
  protected TrackReference originalAlbumTrackReference;

  /**
   * This is true if the Original Album Track Reference reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean originalAlbumTrackReferenceESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TrackReferenceImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MediadbPackage.Literals.TRACK_REFERENCE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Track getTrack() {
    if (track != null && track.eIsProxy()) {
      InternalEObject oldTrack = (InternalEObject) track;
      track = (Track) eResolveProxy(oldTrack);
      if (track != oldTrack) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, MediadbPackage.TRACK_REFERENCE__TRACK, oldTrack,
              track));
      }
    }
    return track;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Track basicGetTrack() {
    return track;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetTrack(Track newTrack, NotificationChain msgs) {
    Track oldTrack = track;
    track = newTrack;
    boolean oldTrackESet = trackESet;
    trackESet = true;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
          MediadbPackage.TRACK_REFERENCE__TRACK, oldTrack, newTrack, !oldTrackESet);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTrack(Track newTrack) {
    if (newTrack != track) {
      NotificationChain msgs = null;
      if (track != null)
        msgs = ((InternalEObject) track).eInverseRemove(this, MediadbPackage.TRACK__REFERRED_BY, Track.class, msgs);
      if (newTrack != null)
        msgs = ((InternalEObject) newTrack).eInverseAdd(this, MediadbPackage.TRACK__REFERRED_BY, Track.class, msgs);
      msgs = basicSetTrack(newTrack, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else {
      boolean oldTrackESet = trackESet;
      trackESet = true;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.TRACK_REFERENCE__TRACK, newTrack, newTrack,
            !oldTrackESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicUnsetTrack(NotificationChain msgs) {
    Track oldTrack = track;
    track = null;
    boolean oldTrackESet = trackESet;
    trackESet = false;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET,
          MediadbPackage.TRACK_REFERENCE__TRACK, oldTrack, null, oldTrackESet);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetTrack() {
    if (track != null) {
      NotificationChain msgs = null;
      msgs = ((InternalEObject) track).eInverseRemove(this, MediadbPackage.TRACK__REFERRED_BY, Track.class, msgs);
      msgs = basicUnsetTrack(msgs);
      if (msgs != null)
        msgs.dispatch();
    } else {
      boolean oldTrackESet = trackESet;
      trackESet = false;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.TRACK_REFERENCE__TRACK, null, null,
            oldTrackESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetTrack() {
    return trackESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getBonusTrack() {
    return bonusTrack;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setBonusTrack(String newBonusTrack) {
    String oldBonusTrack = bonusTrack;
    bonusTrack = newBonusTrack;
    boolean oldBonusTrackESet = bonusTrackESet;
    bonusTrackESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.TRACK_REFERENCE__BONUS_TRACK, oldBonusTrack,
          bonusTrack, !oldBonusTrackESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetBonusTrack() {
    String oldBonusTrack = bonusTrack;
    boolean oldBonusTrackESet = bonusTrackESet;
    bonusTrack = BONUS_TRACK_EDEFAULT;
    bonusTrackESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.TRACK_REFERENCE__BONUS_TRACK,
          oldBonusTrack, BONUS_TRACK_EDEFAULT, oldBonusTrackESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetBonusTrack() {
    return bonusTrackESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public MyTrackInfo getMyTrackInfo() {
    return myTrackInfo;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetMyTrackInfo(MyTrackInfo newMyTrackInfo, NotificationChain msgs) {
    MyTrackInfo oldMyTrackInfo = myTrackInfo;
    myTrackInfo = newMyTrackInfo;
    boolean oldMyTrackInfoESet = myTrackInfoESet;
    myTrackInfoESet = true;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
          MediadbPackage.TRACK_REFERENCE__MY_TRACK_INFO, oldMyTrackInfo, newMyTrackInfo, !oldMyTrackInfoESet);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setMyTrackInfo(MyTrackInfo newMyTrackInfo) {
    if (newMyTrackInfo != myTrackInfo) {
      NotificationChain msgs = null;
      if (myTrackInfo != null)
        msgs = ((InternalEObject) myTrackInfo).eInverseRemove(this,
            EOPPOSITE_FEATURE_BASE - MediadbPackage.TRACK_REFERENCE__MY_TRACK_INFO, null, msgs);
      if (newMyTrackInfo != null)
        msgs = ((InternalEObject) newMyTrackInfo).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - MediadbPackage.TRACK_REFERENCE__MY_TRACK_INFO, null, msgs);
      msgs = basicSetMyTrackInfo(newMyTrackInfo, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else {
      boolean oldMyTrackInfoESet = myTrackInfoESet;
      myTrackInfoESet = true;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.TRACK_REFERENCE__MY_TRACK_INFO,
            newMyTrackInfo, newMyTrackInfo, !oldMyTrackInfoESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicUnsetMyTrackInfo(NotificationChain msgs) {
    MyTrackInfo oldMyTrackInfo = myTrackInfo;
    myTrackInfo = null;
    boolean oldMyTrackInfoESet = myTrackInfoESet;
    myTrackInfoESet = false;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET,
          MediadbPackage.TRACK_REFERENCE__MY_TRACK_INFO, oldMyTrackInfo, null, oldMyTrackInfoESet);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetMyTrackInfo() {
    if (myTrackInfo != null) {
      NotificationChain msgs = null;
      msgs = ((InternalEObject) myTrackInfo).eInverseRemove(this,
          EOPPOSITE_FEATURE_BASE - MediadbPackage.TRACK_REFERENCE__MY_TRACK_INFO, null, msgs);
      msgs = basicUnsetMyTrackInfo(msgs);
      if (msgs != null)
        msgs.dispatch();
    } else {
      boolean oldMyTrackInfoESet = myTrackInfoESet;
      myTrackInfoESet = false;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.TRACK_REFERENCE__MY_TRACK_INFO, null,
            null, oldMyTrackInfoESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetMyTrackInfo() {
    return myTrackInfoESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TrackReference getOriginalAlbumTrackReference() {
    if (originalAlbumTrackReference != null && originalAlbumTrackReference.eIsProxy()) {
      InternalEObject oldOriginalAlbumTrackReference = (InternalEObject) originalAlbumTrackReference;
      originalAlbumTrackReference = (TrackReference) eResolveProxy(oldOriginalAlbumTrackReference);
      if (originalAlbumTrackReference != oldOriginalAlbumTrackReference) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE,
              MediadbPackage.TRACK_REFERENCE__ORIGINAL_ALBUM_TRACK_REFERENCE, oldOriginalAlbumTrackReference,
              originalAlbumTrackReference));
      }
    }
    return originalAlbumTrackReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TrackReference basicGetOriginalAlbumTrackReference() {
    return originalAlbumTrackReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setOriginalAlbumTrackReference(TrackReference newOriginalAlbumTrackReference) {
    TrackReference oldOriginalAlbumTrackReference = originalAlbumTrackReference;
    originalAlbumTrackReference = newOriginalAlbumTrackReference;
    boolean oldOriginalAlbumTrackReferenceESet = originalAlbumTrackReferenceESet;
    originalAlbumTrackReferenceESet = true;
    if (eNotificationRequired())
      eNotify(
          new ENotificationImpl(this, Notification.SET, MediadbPackage.TRACK_REFERENCE__ORIGINAL_ALBUM_TRACK_REFERENCE,
              oldOriginalAlbumTrackReference, originalAlbumTrackReference, !oldOriginalAlbumTrackReferenceESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetOriginalAlbumTrackReference() {
    TrackReference oldOriginalAlbumTrackReference = originalAlbumTrackReference;
    boolean oldOriginalAlbumTrackReferenceESet = originalAlbumTrackReferenceESet;
    originalAlbumTrackReference = null;
    originalAlbumTrackReferenceESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET,
          MediadbPackage.TRACK_REFERENCE__ORIGINAL_ALBUM_TRACK_REFERENCE, oldOriginalAlbumTrackReference, null,
          oldOriginalAlbumTrackReferenceESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetOriginalAlbumTrackReference() {
    return originalAlbumTrackReferenceESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public int getTrackNr() {
    EObject container = eContainer();

    if (container instanceof Disc) {
      Disc disc = (Disc) container;
      EList<TrackReference> tracks = disc.getTrackReferences();
      int myIndex = tracks.indexOf(this);

      return myIndex + 1;
    } else {
      return -1;
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public Disc getDisc() {
    EObject eContainer = eContainer();
    if (eContainer instanceof Disc) {
      return (Disc) eContainer;
    } else {
      return null;
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case MediadbPackage.TRACK_REFERENCE__TRACK:
      if (track != null)
        msgs = ((InternalEObject) track).eInverseRemove(this, MediadbPackage.TRACK__REFERRED_BY, Track.class, msgs);
      return basicSetTrack((Track) otherEnd, msgs);
    }
    return super.eInverseAdd(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case MediadbPackage.TRACK_REFERENCE__TRACK:
      return basicUnsetTrack(msgs);
    case MediadbPackage.TRACK_REFERENCE__MY_TRACK_INFO:
      return basicUnsetMyTrackInfo(msgs);
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
    case MediadbPackage.TRACK_REFERENCE__TRACK:
      if (resolve)
        return getTrack();
      return basicGetTrack();
    case MediadbPackage.TRACK_REFERENCE__BONUS_TRACK:
      return getBonusTrack();
    case MediadbPackage.TRACK_REFERENCE__MY_TRACK_INFO:
      return getMyTrackInfo();
    case MediadbPackage.TRACK_REFERENCE__ORIGINAL_ALBUM_TRACK_REFERENCE:
      if (resolve)
        return getOriginalAlbumTrackReference();
      return basicGetOriginalAlbumTrackReference();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
    case MediadbPackage.TRACK_REFERENCE__TRACK:
      setTrack((Track) newValue);
      return;
    case MediadbPackage.TRACK_REFERENCE__BONUS_TRACK:
      setBonusTrack((String) newValue);
      return;
    case MediadbPackage.TRACK_REFERENCE__MY_TRACK_INFO:
      setMyTrackInfo((MyTrackInfo) newValue);
      return;
    case MediadbPackage.TRACK_REFERENCE__ORIGINAL_ALBUM_TRACK_REFERENCE:
      setOriginalAlbumTrackReference((TrackReference) newValue);
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
    case MediadbPackage.TRACK_REFERENCE__TRACK:
      unsetTrack();
      return;
    case MediadbPackage.TRACK_REFERENCE__BONUS_TRACK:
      unsetBonusTrack();
      return;
    case MediadbPackage.TRACK_REFERENCE__MY_TRACK_INFO:
      unsetMyTrackInfo();
      return;
    case MediadbPackage.TRACK_REFERENCE__ORIGINAL_ALBUM_TRACK_REFERENCE:
      unsetOriginalAlbumTrackReference();
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
    case MediadbPackage.TRACK_REFERENCE__TRACK:
      return isSetTrack();
    case MediadbPackage.TRACK_REFERENCE__BONUS_TRACK:
      return isSetBonusTrack();
    case MediadbPackage.TRACK_REFERENCE__MY_TRACK_INFO:
      return isSetMyTrackInfo();
    case MediadbPackage.TRACK_REFERENCE__ORIGINAL_ALBUM_TRACK_REFERENCE:
      return isSetOriginalAlbumTrackReference();
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
    case MediadbPackage.TRACK_REFERENCE___GET_TRACK_NR:
      return getTrackNr();
    case MediadbPackage.TRACK_REFERENCE___GET_DISC:
      return getDisc();
    }
    return super.eInvoke(operationID, arguments);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();

    buf.append("track nr: ").append(getTrackNr()).append(NEW_LINE);
    Track track = getTrack();
    buf.append("track: ").append(track != null ? track.getTitle() : "<no-track>").append(NEW_LINE);
    buf.append("bonus track: ").append(getBonusTrack() != null ? getBonusTrack() : "No").append(NEW_LINE);
    buf.append("myTrackInfo: ").append(getMyTrackInfo() != null ? myTrackInfo.toString() : "<no myTrackInfo>")
    .append(NEW_LINE);
    buf.append("originalAlbumTrackReference: ");
    if (originalAlbumTrackReference != null) {
      buf.append("track nr ").append(originalAlbumTrackReference.getTrackNr());
      Disc disc = originalAlbumTrackReference.getDisc();
      if (disc == null) {
        buf.append("  ==Disc is null==  ");
        System.out.println("Disc is null");
      } else {
        Album album = disc.getAlbum();
        if (album.isMultiDiscAlbum()) {
          buf.append(" on disc ").append(disc.getTitle());
        }
        buf.append(" of album " + album.getArtistAndTitle());
      }
    } else {
      buf.append("<no-originalAlbumTrackReference>");
    }
    buf.append(NEW_LINE);

    return buf.toString();
  }

} //TrackReferenceImpl
