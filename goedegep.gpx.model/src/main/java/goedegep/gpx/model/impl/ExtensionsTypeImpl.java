/**
 */
package goedegep.gpx.model.impl;

import goedegep.gpx.model.ExtensionsType;
import goedegep.gpx.model.GPXPackage;

import java.math.BigDecimal;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Extensions Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.gpx.model.impl.ExtensionsTypeImpl#getAny <em>Any</em>}</li>
 *   <li>{@link goedegep.gpx.model.impl.ExtensionsTypeImpl#getSpeed <em>Speed</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExtensionsTypeImpl extends MinimalEObjectImpl.Container implements ExtensionsType {
  /**
   * The cached value of the '{@link #getAny() <em>Any</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAny()
   * @generated
   * @ordered
   */
  protected FeatureMap any;

  /**
   * The default value of the '{@link #getSpeed() <em>Speed</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSpeed()
   * @generated
   * @ordered
   */
  protected static final BigDecimal SPEED_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getSpeed() <em>Speed</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSpeed()
   * @generated
   * @ordered
   */
  protected BigDecimal speed = SPEED_EDEFAULT;
  /**
   * This is true if the Speed attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean speedESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ExtensionsTypeImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return GPXPackage.Literals.EXTENSIONS_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureMap getAny() {
    if (any == null) {
      any = new BasicFeatureMap(this, GPXPackage.EXTENSIONS_TYPE__ANY);
    }
    return any;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BigDecimal getSpeed() {
    return speed;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSpeed(BigDecimal newSpeed) {
    BigDecimal oldSpeed = speed;
    speed = newSpeed;
    boolean oldSpeedESet = speedESet;
    speedESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GPXPackage.EXTENSIONS_TYPE__SPEED, oldSpeed, speed, !oldSpeedESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetSpeed() {
    BigDecimal oldSpeed = speed;
    boolean oldSpeedESet = speedESet;
    speed = SPEED_EDEFAULT;
    speedESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, GPXPackage.EXTENSIONS_TYPE__SPEED, oldSpeed, SPEED_EDEFAULT, oldSpeedESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetSpeed() {
    return speedESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case GPXPackage.EXTENSIONS_TYPE__ANY:
        return ((InternalEList<?>)getAny()).basicRemove(otherEnd, msgs);
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
      case GPXPackage.EXTENSIONS_TYPE__ANY:
        if (coreType) return getAny();
        return ((FeatureMap.Internal)getAny()).getWrapper();
      case GPXPackage.EXTENSIONS_TYPE__SPEED:
        return getSpeed();
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
      case GPXPackage.EXTENSIONS_TYPE__ANY:
        ((FeatureMap.Internal)getAny()).set(newValue);
        return;
      case GPXPackage.EXTENSIONS_TYPE__SPEED:
        setSpeed((BigDecimal)newValue);
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
      case GPXPackage.EXTENSIONS_TYPE__ANY:
        getAny().clear();
        return;
      case GPXPackage.EXTENSIONS_TYPE__SPEED:
        unsetSpeed();
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
      case GPXPackage.EXTENSIONS_TYPE__ANY:
        return any != null && !any.isEmpty();
      case GPXPackage.EXTENSIONS_TYPE__SPEED:
        return isSetSpeed();
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
    result.append(" (any: ");
    result.append(any);
    result.append(", speed: ");
    if (speedESet) result.append(speed); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //ExtensionsTypeImpl
