/**
 */
package goedegep.media.mediadb.model.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.TrackReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Disc</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.impl.DiscImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.DiscImpl#getTrackReferences <em>Track References</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DiscImpl extends MinimalEObjectImpl.Container implements Disc {
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
   * The cached value of the '{@link #getTrackReferences() <em>Track References</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTrackReferences()
   * @generated
   * @ordered
   */
  protected EList<TrackReference> trackReferences;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DiscImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MediadbPackage.Literals.DISC;
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
          new ENotificationImpl(this, Notification.SET, MediadbPackage.DISC__TITLE, oldTitle, title, !oldTitleESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.DISC__TITLE, oldTitle, TITLE_EDEFAULT,
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
  public EList<TrackReference> getTrackReferences() {
    if (trackReferences == null) {
      trackReferences = new EObjectContainmentEList<TrackReference>(TrackReference.class, this,
          MediadbPackage.DISC__TRACK_REFERENCES);
    }
    return trackReferences;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public Album getAlbum() {
    return (Album) eContainer();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public int getDiscNr() {
    return getAlbum().getDiscs().indexOf(this) + 1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case MediadbPackage.DISC__TRACK_REFERENCES:
      return ((InternalEList<?>) getTrackReferences()).basicRemove(otherEnd, msgs);
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
    case MediadbPackage.DISC__TITLE:
      return getTitle();
    case MediadbPackage.DISC__TRACK_REFERENCES:
      return getTrackReferences();
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
    case MediadbPackage.DISC__TITLE:
      setTitle((String) newValue);
      return;
    case MediadbPackage.DISC__TRACK_REFERENCES:
      getTrackReferences().clear();
      getTrackReferences().addAll((Collection<? extends TrackReference>) newValue);
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
    case MediadbPackage.DISC__TITLE:
      unsetTitle();
      return;
    case MediadbPackage.DISC__TRACK_REFERENCES:
      getTrackReferences().clear();
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
    case MediadbPackage.DISC__TITLE:
      return isSetTitle();
    case MediadbPackage.DISC__TRACK_REFERENCES:
      return trackReferences != null && !trackReferences.isEmpty();
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
    case MediadbPackage.DISC___GET_ALBUM:
      return getAlbum();
    case MediadbPackage.DISC___GET_DISC_NR:
      return getDiscNr();
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

    buf.append("Title: ").append(isSetTitle() ? getTitle() : "<no-title>").append(NEWLINE);

    buf.append("Tracks:");
    buf.append(NEWLINE);

    int i = 1;
    for (TrackReference trackReference : getTrackReferences()) {
      buf.append("Track ").append(i++).append(":").append(NEWLINE);
      buf.append(trackReference.toString());
      //      buf.append(NEWLINE);
    }

    return buf.toString();
  }

} //DiscImpl
