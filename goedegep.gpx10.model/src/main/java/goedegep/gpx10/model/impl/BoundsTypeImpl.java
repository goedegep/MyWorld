/**
 */
package goedegep.gpx10.model.impl;

import goedegep.gpx10.model.BoundsType;
import goedegep.gpx10.model.GPX10Package;

import java.math.BigDecimal;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bounds Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.gpx10.model.impl.BoundsTypeImpl#getMinlat <em>Minlat</em>}</li>
 *   <li>{@link goedegep.gpx10.model.impl.BoundsTypeImpl#getMinlon <em>Minlon</em>}</li>
 *   <li>{@link goedegep.gpx10.model.impl.BoundsTypeImpl#getMaxlat <em>Maxlat</em>}</li>
 *   <li>{@link goedegep.gpx10.model.impl.BoundsTypeImpl#getMaxlon <em>Maxlon</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BoundsTypeImpl extends MinimalEObjectImpl.Container implements BoundsType {
  /**
   * The default value of the '{@link #getMinlat() <em>Minlat</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMinlat()
   * @generated
   * @ordered
   */
  protected static final BigDecimal MINLAT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMinlat() <em>Minlat</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMinlat()
   * @generated
   * @ordered
   */
  protected BigDecimal minlat = MINLAT_EDEFAULT;

  /**
   * The default value of the '{@link #getMinlon() <em>Minlon</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMinlon()
   * @generated
   * @ordered
   */
  protected static final BigDecimal MINLON_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMinlon() <em>Minlon</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMinlon()
   * @generated
   * @ordered
   */
  protected BigDecimal minlon = MINLON_EDEFAULT;

  /**
   * The default value of the '{@link #getMaxlat() <em>Maxlat</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMaxlat()
   * @generated
   * @ordered
   */
  protected static final BigDecimal MAXLAT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMaxlat() <em>Maxlat</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMaxlat()
   * @generated
   * @ordered
   */
  protected BigDecimal maxlat = MAXLAT_EDEFAULT;

  /**
   * The default value of the '{@link #getMaxlon() <em>Maxlon</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMaxlon()
   * @generated
   * @ordered
   */
  protected static final BigDecimal MAXLON_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMaxlon() <em>Maxlon</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMaxlon()
   * @generated
   * @ordered
   */
  protected BigDecimal maxlon = MAXLON_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected BoundsTypeImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return GPX10Package.Literals.BOUNDS_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BigDecimal getMinlat() {
    return minlat;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMinlat(BigDecimal newMinlat) {
    BigDecimal oldMinlat = minlat;
    minlat = newMinlat;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GPX10Package.BOUNDS_TYPE__MINLAT, oldMinlat, minlat));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BigDecimal getMinlon() {
    return minlon;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMinlon(BigDecimal newMinlon) {
    BigDecimal oldMinlon = minlon;
    minlon = newMinlon;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GPX10Package.BOUNDS_TYPE__MINLON, oldMinlon, minlon));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BigDecimal getMaxlat() {
    return maxlat;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMaxlat(BigDecimal newMaxlat) {
    BigDecimal oldMaxlat = maxlat;
    maxlat = newMaxlat;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GPX10Package.BOUNDS_TYPE__MAXLAT, oldMaxlat, maxlat));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BigDecimal getMaxlon() {
    return maxlon;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMaxlon(BigDecimal newMaxlon) {
    BigDecimal oldMaxlon = maxlon;
    maxlon = newMaxlon;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GPX10Package.BOUNDS_TYPE__MAXLON, oldMaxlon, maxlon));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case GPX10Package.BOUNDS_TYPE__MINLAT:
        return getMinlat();
      case GPX10Package.BOUNDS_TYPE__MINLON:
        return getMinlon();
      case GPX10Package.BOUNDS_TYPE__MAXLAT:
        return getMaxlat();
      case GPX10Package.BOUNDS_TYPE__MAXLON:
        return getMaxlon();
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
      case GPX10Package.BOUNDS_TYPE__MINLAT:
        setMinlat((BigDecimal)newValue);
        return;
      case GPX10Package.BOUNDS_TYPE__MINLON:
        setMinlon((BigDecimal)newValue);
        return;
      case GPX10Package.BOUNDS_TYPE__MAXLAT:
        setMaxlat((BigDecimal)newValue);
        return;
      case GPX10Package.BOUNDS_TYPE__MAXLON:
        setMaxlon((BigDecimal)newValue);
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
      case GPX10Package.BOUNDS_TYPE__MINLAT:
        setMinlat(MINLAT_EDEFAULT);
        return;
      case GPX10Package.BOUNDS_TYPE__MINLON:
        setMinlon(MINLON_EDEFAULT);
        return;
      case GPX10Package.BOUNDS_TYPE__MAXLAT:
        setMaxlat(MAXLAT_EDEFAULT);
        return;
      case GPX10Package.BOUNDS_TYPE__MAXLON:
        setMaxlon(MAXLON_EDEFAULT);
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
      case GPX10Package.BOUNDS_TYPE__MINLAT:
        return MINLAT_EDEFAULT == null ? minlat != null : !MINLAT_EDEFAULT.equals(minlat);
      case GPX10Package.BOUNDS_TYPE__MINLON:
        return MINLON_EDEFAULT == null ? minlon != null : !MINLON_EDEFAULT.equals(minlon);
      case GPX10Package.BOUNDS_TYPE__MAXLAT:
        return MAXLAT_EDEFAULT == null ? maxlat != null : !MAXLAT_EDEFAULT.equals(maxlat);
      case GPX10Package.BOUNDS_TYPE__MAXLON:
        return MAXLON_EDEFAULT == null ? maxlon != null : !MAXLON_EDEFAULT.equals(maxlon);
    }
    return super.eIsSet(featureID);
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
    result.append(" (minlat: ");
    result.append(minlat);
    result.append(", minlon: ");
    result.append(minlon);
    result.append(", maxlat: ");
    result.append(maxlat);
    result.append(", maxlon: ");
    result.append(maxlon);
    result.append(')');
    return result.toString();
  }

} //BoundsTypeImpl
