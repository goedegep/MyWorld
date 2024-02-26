/**
 */
package goedegep.gpx.model.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.gpx.model.ExtensionsType;
import goedegep.gpx.model.GPXPackage;
import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.MetadataType;
import goedegep.gpx.model.RteType;
import goedegep.gpx.model.TrkType;
import goedegep.gpx.model.WptType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gpx Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.gpx.model.impl.GpxTypeImpl#getMetadata <em>Metadata</em>}</li>
 *   <li>{@link goedegep.gpx.model.impl.GpxTypeImpl#getWpt <em>Wpt</em>}</li>
 *   <li>{@link goedegep.gpx.model.impl.GpxTypeImpl#getRte <em>Rte</em>}</li>
 *   <li>{@link goedegep.gpx.model.impl.GpxTypeImpl#getTrk <em>Trk</em>}</li>
 *   <li>{@link goedegep.gpx.model.impl.GpxTypeImpl#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link goedegep.gpx.model.impl.GpxTypeImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link goedegep.gpx.model.impl.GpxTypeImpl#getCreator <em>Creator</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GpxTypeImpl extends MinimalEObjectImpl.Container implements GpxType {
  /**
   * The cached value of the '{@link #getMetadata() <em>Metadata</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMetadata()
   * @generated
   * @ordered
   */
  protected MetadataType metadata;

  /**
   * The cached value of the '{@link #getWpt() <em>Wpt</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWpt()
   * @generated
   * @ordered
   */
  protected EList<WptType> wpt;

  /**
   * The cached value of the '{@link #getRte() <em>Rte</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRte()
   * @generated
   * @ordered
   */
  protected EList<RteType> rte;

  /**
   * The cached value of the '{@link #getTrk() <em>Trk</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTrk()
   * @generated
   * @ordered
   */
  protected EList<TrkType> trk;

  /**
   * The cached value of the '{@link #getExtensions() <em>Extensions</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExtensions()
   * @generated
   * @ordered
   */
  protected ExtensionsType extensions;

  /**
   * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVersion()
   * @generated
   * @ordered
   */
  protected static final String VERSION_EDEFAULT = "1.1";

  /**
   * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVersion()
   * @generated
   * @ordered
   */
  protected String version = VERSION_EDEFAULT;

  /**
   * This is true if the Version attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean versionESet;

  /**
   * The default value of the '{@link #getCreator() <em>Creator</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCreator()
   * @generated
   * @ordered
   */
  protected static final String CREATOR_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCreator() <em>Creator</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCreator()
   * @generated
   * @ordered
   */
  protected String creator = CREATOR_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected GpxTypeImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return GPXPackage.Literals.GPX_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MetadataType getMetadata() {
    return metadata;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetMetadata(MetadataType newMetadata, NotificationChain msgs) {
    MetadataType oldMetadata = metadata;
    metadata = newMetadata;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GPXPackage.GPX_TYPE__METADATA, oldMetadata, newMetadata);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMetadata(MetadataType newMetadata) {
    if (newMetadata != metadata) {
      NotificationChain msgs = null;
      if (metadata != null)
        msgs = ((InternalEObject)metadata).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GPXPackage.GPX_TYPE__METADATA, null, msgs);
      if (newMetadata != null)
        msgs = ((InternalEObject)newMetadata).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GPXPackage.GPX_TYPE__METADATA, null, msgs);
      msgs = basicSetMetadata(newMetadata, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GPXPackage.GPX_TYPE__METADATA, newMetadata, newMetadata));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<WptType> getWpt() {
    if (wpt == null) {
      wpt = new EObjectContainmentEList<WptType>(WptType.class, this, GPXPackage.GPX_TYPE__WPT);
    }
    return wpt;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<RteType> getRte() {
    if (rte == null) {
      rte = new EObjectContainmentEList<RteType>(RteType.class, this, GPXPackage.GPX_TYPE__RTE);
    }
    return rte;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<TrkType> getTrk() {
    if (trk == null) {
      trk = new EObjectContainmentEList<TrkType>(TrkType.class, this, GPXPackage.GPX_TYPE__TRK);
    }
    return trk;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExtensionsType getExtensions() {
    return extensions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetExtensions(ExtensionsType newExtensions, NotificationChain msgs) {
    ExtensionsType oldExtensions = extensions;
    extensions = newExtensions;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GPXPackage.GPX_TYPE__EXTENSIONS, oldExtensions, newExtensions);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setExtensions(ExtensionsType newExtensions) {
    if (newExtensions != extensions) {
      NotificationChain msgs = null;
      if (extensions != null)
        msgs = ((InternalEObject)extensions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GPXPackage.GPX_TYPE__EXTENSIONS, null, msgs);
      if (newExtensions != null)
        msgs = ((InternalEObject)newExtensions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GPXPackage.GPX_TYPE__EXTENSIONS, null, msgs);
      msgs = basicSetExtensions(newExtensions, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GPXPackage.GPX_TYPE__EXTENSIONS, newExtensions, newExtensions));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getVersion() {
    return version;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setVersion(String newVersion) {
    String oldVersion = version;
    version = newVersion;
    boolean oldVersionESet = versionESet;
    versionESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GPXPackage.GPX_TYPE__VERSION, oldVersion, version, !oldVersionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetVersion() {
    String oldVersion = version;
    boolean oldVersionESet = versionESet;
    version = VERSION_EDEFAULT;
    versionESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, GPXPackage.GPX_TYPE__VERSION, oldVersion, VERSION_EDEFAULT, oldVersionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetVersion() {
    return versionESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getCreator() {
    return creator;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCreator(String newCreator) {
    String oldCreator = creator;
    creator = newCreator;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GPXPackage.GPX_TYPE__CREATOR, oldCreator, creator));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public double getLength() {
    double length = 0d;
    
    for (TrkType track: getTrk()) {
      length += track.getLength();
    }
    
    return length;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public double getCumulativeAscent() {
    double cumulativeAscent = 0d;
    
    for (TrkType track: getTrk()) {
      cumulativeAscent += track.getCumulativeAscent();
    }
    
    return cumulativeAscent;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public Double getCumulativeDescent() {
    double cumulativeDescent = 0d;
    
    for (TrkType track: getTrk()) {
      cumulativeDescent += track.getCumulativeDescent();
    }
    
    return cumulativeDescent;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public Long getDuration() {
    Long duration = null;
    
    for (TrkType track: getTrk()) {
      Long trackDuration = track.getDuration();
      if (trackDuration != null) {
        if (duration == null) {
          duration = trackDuration;
        } else {
          duration += trackDuration;
        }
      } else {
        return null;
      }
    }
    
    return duration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public Date getStartTime() {
    int numberOfTracks = getTrk().size();
    if (numberOfTracks == 0) {
      return null;
    }
    
    TrkType firstTrack = getTrk().get(0);
    return firstTrack.getStartTime();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public void setStartTime(Date startTime) {
    int numberOfTracks = getTrk().size();
    if (numberOfTracks == 0) {
      return;
    }
    
    TrkType firstTrack = getTrk().get(0);
    firstTrack.setStartTime(startTime);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public Date getEndTime() {
    int numberOfTracks = getTrk().size();
    if (numberOfTracks == 0) {
      return null;
    }
    
    TrkType lastTrack = getTrk().get(numberOfTracks - 1);
    return lastTrack.getEndTime();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public void setEndTime(Date endTime) {
    int numberOfTracks = getTrk().size();
    if (numberOfTracks == 0) {
      return;
    }
    
    TrkType lastTrack = getTrk().get(numberOfTracks - 1);
    lastTrack.setEndTime(endTime);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public Double getStartElevation() {
    int numberOfTracks = getTrk().size();
    if (numberOfTracks == 0) {
      return null;
    }
    
    TrkType firstTrack = getTrk().get(0);
    return firstTrack.getStartElevation();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public Double getEndElevation() {
    int numberOfTracks = getTrk().size();
    if (numberOfTracks == 0) {
      return null;
    }
    
    TrkType lastTrack = getTrk().get(numberOfTracks - 1);
    return lastTrack.getEndElevation();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case GPXPackage.GPX_TYPE__METADATA:
        return basicSetMetadata(null, msgs);
      case GPXPackage.GPX_TYPE__WPT:
        return ((InternalEList<?>)getWpt()).basicRemove(otherEnd, msgs);
      case GPXPackage.GPX_TYPE__RTE:
        return ((InternalEList<?>)getRte()).basicRemove(otherEnd, msgs);
      case GPXPackage.GPX_TYPE__TRK:
        return ((InternalEList<?>)getTrk()).basicRemove(otherEnd, msgs);
      case GPXPackage.GPX_TYPE__EXTENSIONS:
        return basicSetExtensions(null, msgs);
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
      case GPXPackage.GPX_TYPE__METADATA:
        return getMetadata();
      case GPXPackage.GPX_TYPE__WPT:
        return getWpt();
      case GPXPackage.GPX_TYPE__RTE:
        return getRte();
      case GPXPackage.GPX_TYPE__TRK:
        return getTrk();
      case GPXPackage.GPX_TYPE__EXTENSIONS:
        return getExtensions();
      case GPXPackage.GPX_TYPE__VERSION:
        return getVersion();
      case GPXPackage.GPX_TYPE__CREATOR:
        return getCreator();
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
      case GPXPackage.GPX_TYPE__METADATA:
        setMetadata((MetadataType)newValue);
        return;
      case GPXPackage.GPX_TYPE__WPT:
        getWpt().clear();
        getWpt().addAll((Collection<? extends WptType>)newValue);
        return;
      case GPXPackage.GPX_TYPE__RTE:
        getRte().clear();
        getRte().addAll((Collection<? extends RteType>)newValue);
        return;
      case GPXPackage.GPX_TYPE__TRK:
        getTrk().clear();
        getTrk().addAll((Collection<? extends TrkType>)newValue);
        return;
      case GPXPackage.GPX_TYPE__EXTENSIONS:
        setExtensions((ExtensionsType)newValue);
        return;
      case GPXPackage.GPX_TYPE__VERSION:
        setVersion((String)newValue);
        return;
      case GPXPackage.GPX_TYPE__CREATOR:
        setCreator((String)newValue);
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
      case GPXPackage.GPX_TYPE__METADATA:
        setMetadata((MetadataType)null);
        return;
      case GPXPackage.GPX_TYPE__WPT:
        getWpt().clear();
        return;
      case GPXPackage.GPX_TYPE__RTE:
        getRte().clear();
        return;
      case GPXPackage.GPX_TYPE__TRK:
        getTrk().clear();
        return;
      case GPXPackage.GPX_TYPE__EXTENSIONS:
        setExtensions((ExtensionsType)null);
        return;
      case GPXPackage.GPX_TYPE__VERSION:
        unsetVersion();
        return;
      case GPXPackage.GPX_TYPE__CREATOR:
        setCreator(CREATOR_EDEFAULT);
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
      case GPXPackage.GPX_TYPE__METADATA:
        return metadata != null;
      case GPXPackage.GPX_TYPE__WPT:
        return wpt != null && !wpt.isEmpty();
      case GPXPackage.GPX_TYPE__RTE:
        return rte != null && !rte.isEmpty();
      case GPXPackage.GPX_TYPE__TRK:
        return trk != null && !trk.isEmpty();
      case GPXPackage.GPX_TYPE__EXTENSIONS:
        return extensions != null;
      case GPXPackage.GPX_TYPE__VERSION:
        return isSetVersion();
      case GPXPackage.GPX_TYPE__CREATOR:
        return CREATOR_EDEFAULT == null ? creator != null : !CREATOR_EDEFAULT.equals(creator);
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
      case GPXPackage.GPX_TYPE___GET_LENGTH:
        return getLength();
      case GPXPackage.GPX_TYPE___GET_CUMULATIVE_ASCENT:
        return getCumulativeAscent();
      case GPXPackage.GPX_TYPE___GET_DURATION:
        return getDuration();
      case GPXPackage.GPX_TYPE___GET_START_TIME:
        return getStartTime();
      case GPXPackage.GPX_TYPE___GET_END_TIME:
        return getEndTime();
      case GPXPackage.GPX_TYPE___GET_CUMULATIVE_DESCENT:
        return getCumulativeDescent();
      case GPXPackage.GPX_TYPE___GET_START_ELEVATION:
        return getStartElevation();
      case GPXPackage.GPX_TYPE___GET_END_ELEVATION:
        return getEndElevation();
      case GPXPackage.GPX_TYPE___SET_START_TIME__DATE:
        setStartTime((Date)arguments.get(0));
        return null;
      case GPXPackage.GPX_TYPE___SET_END_TIME__DATE:
        setEndTime((Date)arguments.get(0));
        return null;
    }
    return super.eInvoke(operationID, arguments);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (version: ");
    if (versionESet) result.append(version); else result.append("<unset>");
    result.append(", creator: ");
    result.append(creator);
    result.append(')');
    return result.toString();
  }

} //GpxTypeImpl
