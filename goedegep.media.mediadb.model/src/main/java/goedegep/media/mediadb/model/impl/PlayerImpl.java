/**
 */
package goedegep.media.mediadb.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.Player;
import goedegep.util.string.StringUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Player</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.impl.PlayerImpl#getArtist <em>Artist</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.PlayerImpl#getInstruments <em>Instruments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PlayerImpl extends MinimalEObjectImpl.Container implements Player {
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
   * The cached value of the '{@link #getInstruments() <em>Instruments</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInstruments()
   * @generated
   * @ordered
   */
  protected EList<String> instruments;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PlayerImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MediadbPackage.Literals.PLAYER;
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
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, MediadbPackage.PLAYER__ARTIST, oldArtist, artist));
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
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.PLAYER__ARTIST, oldArtist, artist,
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.PLAYER__ARTIST, oldArtist, null,
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
  public EList<String> getInstruments() {
    if (instruments == null) {
      instruments = new EDataTypeUniqueEList<String>(String.class, this, MediadbPackage.PLAYER__INSTRUMENTS);
    }
    return instruments;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case MediadbPackage.PLAYER__ARTIST:
      if (resolve)
        return getArtist();
      return basicGetArtist();
    case MediadbPackage.PLAYER__INSTRUMENTS:
      return getInstruments();
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
    case MediadbPackage.PLAYER__ARTIST:
      setArtist((Artist) newValue);
      return;
    case MediadbPackage.PLAYER__INSTRUMENTS:
      getInstruments().clear();
      getInstruments().addAll((Collection<? extends String>) newValue);
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
    case MediadbPackage.PLAYER__ARTIST:
      unsetArtist();
      return;
    case MediadbPackage.PLAYER__INSTRUMENTS:
      getInstruments().clear();
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
    case MediadbPackage.PLAYER__ARTIST:
      return isSetArtist();
    case MediadbPackage.PLAYER__INSTRUMENTS:
      return instruments != null && !instruments.isEmpty();
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
    buf.append(isSetArtist() ? getArtist() : "<no-artist>");
    buf.append(" (instruments: ");
    buf.append(StringUtil.objectCollectionToCommaSeparatedStrings(getInstruments()));
    buf.append(')');
    return buf.toString();
  }

} //PlayerImpl
