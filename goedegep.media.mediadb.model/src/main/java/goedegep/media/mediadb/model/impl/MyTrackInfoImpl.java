/**
 */
package goedegep.media.mediadb.model.impl;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.IWant;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.TrackCollection;
import goedegep.media.mediadb.model.TrackReference;
import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>My Track Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.impl.MyTrackInfoImpl#getIHaveOn <em>IHave On</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.MyTrackInfoImpl#getIWant <em>IWant</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.impl.MyTrackInfoImpl#getTrackReference <em>Track Reference</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MyTrackInfoImpl extends MinimalEObjectImpl.Container implements MyTrackInfo {
  private static final Logger LOGGER = Logger.getLogger(MyTrackInfoImpl.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");

  /**
   * The cached value of the '{@link #getIHaveOn() <em>IHave On</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIHaveOn()
   * @generated
   * @ordered
   */
  protected EList<MediumInfo> iHaveOn;

  /**
   * The default value of the '{@link #getIWant() <em>IWant</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIWant()
   * @generated
   * @ordered
   */
  protected static final IWant IWANT_EDEFAULT = IWant.NOT_SET;

  /**
   * The cached value of the '{@link #getIWant() <em>IWant</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIWant()
   * @generated
   * @ordered
   */
  protected IWant iWant = IWANT_EDEFAULT;

  /**
   * This is true if the IWant attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean iWantESet;

  /**
   * The cached value of the '{@link #getTrackReference() <em>Track Reference</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTrackReference()
   * @generated
   * @ordered
   */
  protected TrackReference trackReference;
  /**
   * This is true if the Track Reference reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean trackReferenceESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MyTrackInfoImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MediadbPackage.Literals.MY_TRACK_INFO;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<MediumInfo> getIHaveOn() {
    if (iHaveOn == null) {
      iHaveOn = new EObjectContainmentEList.Unsettable<MediumInfo>(MediumInfo.class, this,
          MediadbPackage.MY_TRACK_INFO__IHAVE_ON);
    }
    return iHaveOn;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetIHaveOn() {
    if (iHaveOn != null)
      ((InternalEList.Unsettable<?>) iHaveOn).unset();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetIHaveOn() {
    return iHaveOn != null && ((InternalEList.Unsettable<?>) iHaveOn).isSet();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public IWant getIWant() {
    return iWant;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setIWant(IWant newIWant) {
    IWant oldIWant = iWant;
    iWant = newIWant == null ? IWANT_EDEFAULT : newIWant;
    boolean oldIWantESet = iWantESet;
    iWantESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.MY_TRACK_INFO__IWANT, oldIWant, iWant,
          !oldIWantESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetIWant() {
    IWant oldIWant = iWant;
    boolean oldIWantESet = iWantESet;
    iWant = IWANT_EDEFAULT;
    iWantESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.MY_TRACK_INFO__IWANT, oldIWant,
          IWANT_EDEFAULT, oldIWantESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetIWant() {
    return iWantESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TrackReference getTrackReference() {
    if (trackReference != null && trackReference.eIsProxy()) {
      InternalEObject oldTrackReference = (InternalEObject) trackReference;
      trackReference = (TrackReference) eResolveProxy(oldTrackReference);
      if (trackReference != oldTrackReference) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, MediadbPackage.MY_TRACK_INFO__TRACK_REFERENCE,
              oldTrackReference, trackReference));
      }
    }
    return trackReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TrackReference basicGetTrackReference() {
    return trackReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTrackReference(TrackReference newTrackReference) {
    TrackReference oldTrackReference = trackReference;
    trackReference = newTrackReference;
    boolean oldTrackReferenceESet = trackReferenceESet;
    trackReferenceESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MediadbPackage.MY_TRACK_INFO__TRACK_REFERENCE,
          oldTrackReference, trackReference, !oldTrackReferenceESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetTrackReference() {
    TrackReference oldTrackReference = trackReference;
    boolean oldTrackReferenceESet = trackReferenceESet;
    trackReference = null;
    trackReferenceESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MediadbPackage.MY_TRACK_INFO__TRACK_REFERENCE,
          oldTrackReference, null, oldTrackReferenceESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetTrackReference() {
    return trackReferenceESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case MediadbPackage.MY_TRACK_INFO__IHAVE_ON:
      return ((InternalEList<?>) getIHaveOn()).basicRemove(otherEnd, msgs);
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
    case MediadbPackage.MY_TRACK_INFO__IHAVE_ON:
      return getIHaveOn();
    case MediadbPackage.MY_TRACK_INFO__IWANT:
      return getIWant();
    case MediadbPackage.MY_TRACK_INFO__TRACK_REFERENCE:
      if (resolve)
        return getTrackReference();
      return basicGetTrackReference();
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
    case MediadbPackage.MY_TRACK_INFO__IHAVE_ON:
      getIHaveOn().clear();
      getIHaveOn().addAll((Collection<? extends MediumInfo>) newValue);
      return;
    case MediadbPackage.MY_TRACK_INFO__IWANT:
      setIWant((IWant) newValue);
      return;
    case MediadbPackage.MY_TRACK_INFO__TRACK_REFERENCE:
      setTrackReference((TrackReference) newValue);
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
    case MediadbPackage.MY_TRACK_INFO__IHAVE_ON:
      unsetIHaveOn();
      return;
    case MediadbPackage.MY_TRACK_INFO__IWANT:
      unsetIWant();
      return;
    case MediadbPackage.MY_TRACK_INFO__TRACK_REFERENCE:
      unsetTrackReference();
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
    case MediadbPackage.MY_TRACK_INFO__IHAVE_ON:
      return isSetIHaveOn();
    case MediadbPackage.MY_TRACK_INFO__IWANT:
      return isSetIWant();
    case MediadbPackage.MY_TRACK_INFO__TRACK_REFERENCE:
      return isSetTrackReference();
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

    buf.append("  iWant: ");
    buf.append(isSetIWant() ? getIWant().toString() : "<no-iwant>").append(NEWLINE);

    buf.append("  iHaveOn: ");
    buf.append(isSetIHaveOn() ? getIHaveOn().toString() : "<no-ihave-on>").append(NEWLINE);

    buf.append("  trackReference: ");
    if (isSetTrackReference()) {
      TrackReference trackReference = getTrackReference();
      EObject eContainer = trackReference.eContainer();
      if (eContainer instanceof Disc) {
        Disc disc = (Disc) eContainer;
        Album compilationAlbum = disc.getAlbum();
        buf.append("track '").append(trackReference.getTrack().getTitle()).append("' on '")
            .append(compilationAlbum.getTitle()).append("'");
      } else if (eContainer instanceof TrackCollection) {
        TrackCollection trackCollection = (TrackCollection) eContainer;
        buf.append("track '").append(trackReference.getTrack().getTitle()).append("' in collection '")
            .append(trackCollection.getCollection().getName()).append("'");
      }

    } else {
      buf.append("<no-track-reference>");
    }
    buf.append(NEWLINE);

    return buf.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean iHaveOnEquals(List<MediumInfo> mediumInfos1, List<MediumInfo> mediumInfos2) {
    if (mediumInfos1.size() != mediumInfos2.size()) {
      return false;
    }

    for (int i = 0; i < mediumInfos1.size(); i++) {
      MediumInfo mediumInfo1 = mediumInfos1.get(i);
      MediumInfo mediomInfo2 = mediumInfos2.get(i);
      if (!mediumInfo1.equals(mediomInfo2)) {
        LOGGER.severe("Medium info differs: mediumInfo1=" + mediumInfo1 + ", mediomInfo2=" + mediomInfo2);
        return false;
      }
    }

    return true;
  }

} //MyTrackInfoImpl
