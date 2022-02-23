/**
 */
package goedegep.gpx.model.impl;

import goedegep.geo.dbl.WGS84Coordinates;
import goedegep.gpx.model.ExtensionsType;
import goedegep.gpx.model.GPXPackage;
import goedegep.gpx.model.TrksegType;
import goedegep.gpx.model.WptType;

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

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Trkseg Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.gpx.model.impl.TrksegTypeImpl#getTrkpt <em>Trkpt</em>}</li>
 *   <li>{@link goedegep.gpx.model.impl.TrksegTypeImpl#getExtensions <em>Extensions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TrksegTypeImpl extends MinimalEObjectImpl.Container implements TrksegType {
  /**
   * The cached value of the '{@link #getTrkpt() <em>Trkpt</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTrkpt()
   * @generated
   * @ordered
   */
  protected EList<WptType> trkpt;

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
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TrksegTypeImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return GPXPackage.Literals.TRKSEG_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<WptType> getTrkpt() {
    if (trkpt == null) {
      trkpt = new EObjectContainmentEList<WptType>(WptType.class, this, GPXPackage.TRKSEG_TYPE__TRKPT);
    }
    return trkpt;
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GPXPackage.TRKSEG_TYPE__EXTENSIONS, oldExtensions, newExtensions);
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
        msgs = ((InternalEObject)extensions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GPXPackage.TRKSEG_TYPE__EXTENSIONS, null, msgs);
      if (newExtensions != null)
        msgs = ((InternalEObject)newExtensions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GPXPackage.TRKSEG_TYPE__EXTENSIONS, null, msgs);
      msgs = basicSetExtensions(newExtensions, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GPXPackage.TRKSEG_TYPE__EXTENSIONS, newExtensions, newExtensions));
  }

  /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public double getLength() {
        
        double length = 0.0;
        WptType previousWaypoint = null;
        WGS84Coordinates previousPoint = null;
        WGS84Coordinates currentPoint = null;

        for (WptType currentWaypoint: getTrkpt()) {
            double elevation = currentWaypoint.getEle() != null ? currentWaypoint.getEle().doubleValue() : 0.0;
            currentPoint = new WGS84Coordinates(currentWaypoint.getLat().doubleValue(), currentWaypoint.getLon().doubleValue(), elevation);
        	if (previousWaypoint != null) {
        		length += currentPoint.getDistance(previousPoint);
        	}
            previousWaypoint = currentWaypoint;
            previousPoint = currentPoint;
        }

        return length;
	}

		/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case GPXPackage.TRKSEG_TYPE__TRKPT:
        return ((InternalEList<?>)getTrkpt()).basicRemove(otherEnd, msgs);
      case GPXPackage.TRKSEG_TYPE__EXTENSIONS:
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
      case GPXPackage.TRKSEG_TYPE__TRKPT:
        return getTrkpt();
      case GPXPackage.TRKSEG_TYPE__EXTENSIONS:
        return getExtensions();
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
      case GPXPackage.TRKSEG_TYPE__TRKPT:
        getTrkpt().clear();
        getTrkpt().addAll((Collection<? extends WptType>)newValue);
        return;
      case GPXPackage.TRKSEG_TYPE__EXTENSIONS:
        setExtensions((ExtensionsType)newValue);
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
      case GPXPackage.TRKSEG_TYPE__TRKPT:
        getTrkpt().clear();
        return;
      case GPXPackage.TRKSEG_TYPE__EXTENSIONS:
        setExtensions((ExtensionsType)null);
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
      case GPXPackage.TRKSEG_TYPE__TRKPT:
        return trkpt != null && !trkpt.isEmpty();
      case GPXPackage.TRKSEG_TYPE__EXTENSIONS:
        return extensions != null;
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
      case GPXPackage.TRKSEG_TYPE___GET_LENGTH:
        return getLength();
    }
    return super.eInvoke(operationID, arguments);
  }

} //TrksegTypeImpl
