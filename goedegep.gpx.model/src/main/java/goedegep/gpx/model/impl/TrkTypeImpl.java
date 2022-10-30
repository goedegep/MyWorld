/**
 */
package goedegep.gpx.model.impl;

import goedegep.gpx.model.ExtensionsType;
import goedegep.gpx.model.GPXPackage;
import goedegep.gpx.model.LinkType;
import goedegep.gpx.model.TrkType;
import goedegep.gpx.model.TrksegType;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;

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

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Trk Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.gpx.model.impl.TrkTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.gpx.model.impl.TrkTypeImpl#getCmt <em>Cmt</em>}</li>
 *   <li>{@link goedegep.gpx.model.impl.TrkTypeImpl#getDesc <em>Desc</em>}</li>
 *   <li>{@link goedegep.gpx.model.impl.TrkTypeImpl#getSrc <em>Src</em>}</li>
 *   <li>{@link goedegep.gpx.model.impl.TrkTypeImpl#getLink <em>Link</em>}</li>
 *   <li>{@link goedegep.gpx.model.impl.TrkTypeImpl#getNumber <em>Number</em>}</li>
 *   <li>{@link goedegep.gpx.model.impl.TrkTypeImpl#getType <em>Type</em>}</li>
 *   <li>{@link goedegep.gpx.model.impl.TrkTypeImpl#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link goedegep.gpx.model.impl.TrkTypeImpl#getTrkseg <em>Trkseg</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TrkTypeImpl extends MinimalEObjectImpl.Container implements TrkType {
  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getCmt() <em>Cmt</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCmt()
   * @generated
   * @ordered
   */
  protected static final String CMT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCmt() <em>Cmt</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCmt()
   * @generated
   * @ordered
   */
  protected String cmt = CMT_EDEFAULT;

  /**
   * The default value of the '{@link #getDesc() <em>Desc</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDesc()
   * @generated
   * @ordered
   */
  protected static final String DESC_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDesc() <em>Desc</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDesc()
   * @generated
   * @ordered
   */
  protected String desc = DESC_EDEFAULT;

  /**
   * The default value of the '{@link #getSrc() <em>Src</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSrc()
   * @generated
   * @ordered
   */
  protected static final String SRC_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getSrc() <em>Src</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSrc()
   * @generated
   * @ordered
   */
  protected String src = SRC_EDEFAULT;

  /**
   * The cached value of the '{@link #getLink() <em>Link</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLink()
   * @generated
   * @ordered
   */
  protected EList<LinkType> link;

  /**
   * The default value of the '{@link #getNumber() <em>Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNumber()
   * @generated
   * @ordered
   */
  protected static final BigInteger NUMBER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNumber() <em>Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNumber()
   * @generated
   * @ordered
   */
  protected BigInteger number = NUMBER_EDEFAULT;

  /**
   * The default value of the '{@link #getType() <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected static final String TYPE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected String type = TYPE_EDEFAULT;

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
   * The cached value of the '{@link #getTrkseg() <em>Trkseg</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTrkseg()
   * @generated
   * @ordered
   */
  protected EList<TrksegType> trkseg;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TrkTypeImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return GPXPackage.Literals.TRK_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName() {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName) {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GPXPackage.TRK_TYPE__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getCmt() {
    return cmt;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCmt(String newCmt) {
    String oldCmt = cmt;
    cmt = newCmt;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GPXPackage.TRK_TYPE__CMT, oldCmt, cmt));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getDesc() {
    return desc;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDesc(String newDesc) {
    String oldDesc = desc;
    desc = newDesc;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GPXPackage.TRK_TYPE__DESC, oldDesc, desc));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getSrc() {
    return src;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSrc(String newSrc) {
    String oldSrc = src;
    src = newSrc;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GPXPackage.TRK_TYPE__SRC, oldSrc, src));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<LinkType> getLink() {
    if (link == null) {
      link = new EObjectContainmentEList<LinkType>(LinkType.class, this, GPXPackage.TRK_TYPE__LINK);
    }
    return link;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BigInteger getNumber() {
    return number;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNumber(BigInteger newNumber) {
    BigInteger oldNumber = number;
    number = newNumber;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GPXPackage.TRK_TYPE__NUMBER, oldNumber, number));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getType() {
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setType(String newType) {
    String oldType = type;
    type = newType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GPXPackage.TRK_TYPE__TYPE, oldType, type));
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GPXPackage.TRK_TYPE__EXTENSIONS, oldExtensions, newExtensions);
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
        msgs = ((InternalEObject)extensions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GPXPackage.TRK_TYPE__EXTENSIONS, null, msgs);
      if (newExtensions != null)
        msgs = ((InternalEObject)newExtensions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GPXPackage.TRK_TYPE__EXTENSIONS, null, msgs);
      msgs = basicSetExtensions(newExtensions, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GPXPackage.TRK_TYPE__EXTENSIONS, newExtensions, newExtensions));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<TrksegType> getTrkseg() {
    if (trkseg == null) {
      trkseg = new EObjectContainmentEList<TrksegType>(TrksegType.class, this, GPXPackage.TRK_TYPE__TRKSEG);
    }
    return trkseg;
  }

  /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public double getLength() {
		double length = 0.0;
		
		for (TrksegType trackSegment: getTrkseg()) {
			length += trackSegment.getLength();
		}
		
		return length;
	}

		/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public double getCumulativeAscent() {
    double cumulativeAscent = 0.0;
    
    for (TrksegType trackSegment: getTrkseg()) {
      cumulativeAscent += trackSegment.getCumulativeAscent();
    }
    
    return cumulativeAscent;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public Double getCumulativeDescent() {
    double cumulativeDescent = 0.0;
    
    for (TrksegType trackSegment: getTrkseg()) {
      cumulativeDescent += trackSegment.getCumulativeDescent();
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
    
    for (TrksegType trackSegment: getTrkseg()) {
      Long trackSegmentDuration = trackSegment.getDuration();
      if (trackSegmentDuration != null) {
        if (duration == null) {
          duration = trackSegmentDuration;
        } else {
          duration += trackSegmentDuration;
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
    int numberOfSegments = getTrkseg().size();
    if (numberOfSegments == 0) {
      return null;
    }
    
    TrksegType firstSegment = getTrkseg().get(0);
    return firstSegment.getStartTime();
  }

    /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Date getEndTime() {
    // TODO: implement this method
    // Ensure that you remove @generated or mark it @generated NOT
    throw new UnsupportedOperationException();
  }

    /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public Double getStartElevation() {
    int numberOfSegments = getTrkseg().size();
    if (numberOfSegments == 0) {
      return null;
    }
    
    TrksegType firstSegment = getTrkseg().get(0);
    return firstSegment.getStartElevation();
  }

    /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public Double getEndElevation() {
    int numberOfSegments = getTrkseg().size();
    if (numberOfSegments == 0) {
      return null;
    }
    
    TrksegType lastSegment = getTrkseg().get(numberOfSegments - 1);
    return lastSegment.getEndElevation();
  }

    /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case GPXPackage.TRK_TYPE__LINK:
        return ((InternalEList<?>)getLink()).basicRemove(otherEnd, msgs);
      case GPXPackage.TRK_TYPE__EXTENSIONS:
        return basicSetExtensions(null, msgs);
      case GPXPackage.TRK_TYPE__TRKSEG:
        return ((InternalEList<?>)getTrkseg()).basicRemove(otherEnd, msgs);
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
      case GPXPackage.TRK_TYPE__NAME:
        return getName();
      case GPXPackage.TRK_TYPE__CMT:
        return getCmt();
      case GPXPackage.TRK_TYPE__DESC:
        return getDesc();
      case GPXPackage.TRK_TYPE__SRC:
        return getSrc();
      case GPXPackage.TRK_TYPE__LINK:
        return getLink();
      case GPXPackage.TRK_TYPE__NUMBER:
        return getNumber();
      case GPXPackage.TRK_TYPE__TYPE:
        return getType();
      case GPXPackage.TRK_TYPE__EXTENSIONS:
        return getExtensions();
      case GPXPackage.TRK_TYPE__TRKSEG:
        return getTrkseg();
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
      case GPXPackage.TRK_TYPE__NAME:
        setName((String)newValue);
        return;
      case GPXPackage.TRK_TYPE__CMT:
        setCmt((String)newValue);
        return;
      case GPXPackage.TRK_TYPE__DESC:
        setDesc((String)newValue);
        return;
      case GPXPackage.TRK_TYPE__SRC:
        setSrc((String)newValue);
        return;
      case GPXPackage.TRK_TYPE__LINK:
        getLink().clear();
        getLink().addAll((Collection<? extends LinkType>)newValue);
        return;
      case GPXPackage.TRK_TYPE__NUMBER:
        setNumber((BigInteger)newValue);
        return;
      case GPXPackage.TRK_TYPE__TYPE:
        setType((String)newValue);
        return;
      case GPXPackage.TRK_TYPE__EXTENSIONS:
        setExtensions((ExtensionsType)newValue);
        return;
      case GPXPackage.TRK_TYPE__TRKSEG:
        getTrkseg().clear();
        getTrkseg().addAll((Collection<? extends TrksegType>)newValue);
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
      case GPXPackage.TRK_TYPE__NAME:
        setName(NAME_EDEFAULT);
        return;
      case GPXPackage.TRK_TYPE__CMT:
        setCmt(CMT_EDEFAULT);
        return;
      case GPXPackage.TRK_TYPE__DESC:
        setDesc(DESC_EDEFAULT);
        return;
      case GPXPackage.TRK_TYPE__SRC:
        setSrc(SRC_EDEFAULT);
        return;
      case GPXPackage.TRK_TYPE__LINK:
        getLink().clear();
        return;
      case GPXPackage.TRK_TYPE__NUMBER:
        setNumber(NUMBER_EDEFAULT);
        return;
      case GPXPackage.TRK_TYPE__TYPE:
        setType(TYPE_EDEFAULT);
        return;
      case GPXPackage.TRK_TYPE__EXTENSIONS:
        setExtensions((ExtensionsType)null);
        return;
      case GPXPackage.TRK_TYPE__TRKSEG:
        getTrkseg().clear();
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
      case GPXPackage.TRK_TYPE__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case GPXPackage.TRK_TYPE__CMT:
        return CMT_EDEFAULT == null ? cmt != null : !CMT_EDEFAULT.equals(cmt);
      case GPXPackage.TRK_TYPE__DESC:
        return DESC_EDEFAULT == null ? desc != null : !DESC_EDEFAULT.equals(desc);
      case GPXPackage.TRK_TYPE__SRC:
        return SRC_EDEFAULT == null ? src != null : !SRC_EDEFAULT.equals(src);
      case GPXPackage.TRK_TYPE__LINK:
        return link != null && !link.isEmpty();
      case GPXPackage.TRK_TYPE__NUMBER:
        return NUMBER_EDEFAULT == null ? number != null : !NUMBER_EDEFAULT.equals(number);
      case GPXPackage.TRK_TYPE__TYPE:
        return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
      case GPXPackage.TRK_TYPE__EXTENSIONS:
        return extensions != null;
      case GPXPackage.TRK_TYPE__TRKSEG:
        return trkseg != null && !trkseg.isEmpty();
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
      case GPXPackage.TRK_TYPE___GET_LENGTH:
        return getLength();
      case GPXPackage.TRK_TYPE___GET_CUMULATIVE_ASCENT:
        return getCumulativeAscent();
      case GPXPackage.TRK_TYPE___GET_DURATION:
        return getDuration();
      case GPXPackage.TRK_TYPE___GET_START_TIME:
        return getStartTime();
      case GPXPackage.TRK_TYPE___GET_END_TIME:
        return getEndTime();
      case GPXPackage.TRK_TYPE___GET_CUMULATIVE_DESCENT:
        return getCumulativeDescent();
      case GPXPackage.TRK_TYPE___GET_START_ELEVATION:
        return getStartElevation();
      case GPXPackage.TRK_TYPE___GET_END_ELEVATION:
        return getEndElevation();
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
    result.append(" (name: ");
    result.append(name);
    result.append(", cmt: ");
    result.append(cmt);
    result.append(", desc: ");
    result.append(desc);
    result.append(", src: ");
    result.append(src);
    result.append(", number: ");
    result.append(number);
    result.append(", type: ");
    result.append(type);
    result.append(')');
    return result.toString();
  }

} //TrkTypeImpl
