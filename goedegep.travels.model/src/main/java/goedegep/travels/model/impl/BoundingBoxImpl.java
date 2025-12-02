/**
 */
package goedegep.travels.model.impl;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import goedegep.travels.model.BoundingBox;
import goedegep.travels.model.TravelsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bounding Box</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.travels.model.impl.BoundingBoxImpl#getWest <em>West</em>}</li>
 *   <li>{@link goedegep.travels.model.impl.BoundingBoxImpl#getNorth <em>North</em>}</li>
 *   <li>{@link goedegep.travels.model.impl.BoundingBoxImpl#getEast <em>East</em>}</li>
 *   <li>{@link goedegep.travels.model.impl.BoundingBoxImpl#getSouth <em>South</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BoundingBoxImpl extends MinimalEObjectImpl.Container implements BoundingBox {
  /**
   * The default value of the '{@link #getWest() <em>West</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWest()
   * @generated
   * @ordered
   */
  protected static final Double WEST_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getWest() <em>West</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWest()
   * @generated
   * @ordered
   */
  protected Double west = WEST_EDEFAULT;

  /**
   * This is true if the West attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean westESet;

  /**
   * The default value of the '{@link #getNorth() <em>North</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNorth()
   * @generated
   * @ordered
   */
  protected static final Double NORTH_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNorth() <em>North</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNorth()
   * @generated
   * @ordered
   */
  protected Double north = NORTH_EDEFAULT;

  /**
   * This is true if the North attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean northESet;

  /**
   * The default value of the '{@link #getEast() <em>East</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEast()
   * @generated
   * @ordered
   */
  protected static final Double EAST_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getEast() <em>East</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEast()
   * @generated
   * @ordered
   */
  protected Double east = EAST_EDEFAULT;

  /**
   * This is true if the East attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean eastESet;

  /**
   * The default value of the '{@link #getSouth() <em>South</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSouth()
   * @generated
   * @ordered
   */
  protected static final Double SOUTH_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getSouth() <em>South</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSouth()
   * @generated
   * @ordered
   */
  protected Double south = SOUTH_EDEFAULT;

  /**
   * This is true if the South attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean southESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected BoundingBoxImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return TravelsPackage.Literals.BOUNDING_BOX;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Double getWest() {
    return west;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setWest(Double newWest) {
    Double oldWest = west;
    west = newWest;
    boolean oldWestESet = westESet;
    westESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TravelsPackage.BOUNDING_BOX__WEST, oldWest, west,
          !oldWestESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetWest() {
    Double oldWest = west;
    boolean oldWestESet = westESet;
    west = WEST_EDEFAULT;
    westESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, TravelsPackage.BOUNDING_BOX__WEST, oldWest, WEST_EDEFAULT,
          oldWestESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetWest() {
    return westESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Double getNorth() {
    return north;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setNorth(Double newNorth) {
    Double oldNorth = north;
    north = newNorth;
    boolean oldNorthESet = northESet;
    northESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TravelsPackage.BOUNDING_BOX__NORTH, oldNorth, north,
          !oldNorthESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetNorth() {
    Double oldNorth = north;
    boolean oldNorthESet = northESet;
    north = NORTH_EDEFAULT;
    northESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, TravelsPackage.BOUNDING_BOX__NORTH, oldNorth,
          NORTH_EDEFAULT, oldNorthESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetNorth() {
    return northESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Double getEast() {
    return east;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setEast(Double newEast) {
    Double oldEast = east;
    east = newEast;
    boolean oldEastESet = eastESet;
    eastESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TravelsPackage.BOUNDING_BOX__EAST, oldEast, east,
          !oldEastESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetEast() {
    Double oldEast = east;
    boolean oldEastESet = eastESet;
    east = EAST_EDEFAULT;
    eastESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, TravelsPackage.BOUNDING_BOX__EAST, oldEast, EAST_EDEFAULT,
          oldEastESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetEast() {
    return eastESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Double getSouth() {
    return south;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setSouth(Double newSouth) {
    Double oldSouth = south;
    south = newSouth;
    boolean oldSouthESet = southESet;
    southESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TravelsPackage.BOUNDING_BOX__SOUTH, oldSouth, south,
          !oldSouthESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetSouth() {
    Double oldSouth = south;
    boolean oldSouthESet = southESet;
    south = SOUTH_EDEFAULT;
    southESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, TravelsPackage.BOUNDING_BOX__SOUTH, oldSouth,
          SOUTH_EDEFAULT, oldSouthESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetSouth() {
    return southESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public boolean isValid() {
    if ((getNorth() != null) && (getEast() != null) && (getSouth() != null) && (getWest() != null)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case TravelsPackage.BOUNDING_BOX__WEST:
      return getWest();
    case TravelsPackage.BOUNDING_BOX__NORTH:
      return getNorth();
    case TravelsPackage.BOUNDING_BOX__EAST:
      return getEast();
    case TravelsPackage.BOUNDING_BOX__SOUTH:
      return getSouth();
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
    case TravelsPackage.BOUNDING_BOX__WEST:
      setWest((Double) newValue);
      return;
    case TravelsPackage.BOUNDING_BOX__NORTH:
      setNorth((Double) newValue);
      return;
    case TravelsPackage.BOUNDING_BOX__EAST:
      setEast((Double) newValue);
      return;
    case TravelsPackage.BOUNDING_BOX__SOUTH:
      setSouth((Double) newValue);
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
    case TravelsPackage.BOUNDING_BOX__WEST:
      unsetWest();
      return;
    case TravelsPackage.BOUNDING_BOX__NORTH:
      unsetNorth();
      return;
    case TravelsPackage.BOUNDING_BOX__EAST:
      unsetEast();
      return;
    case TravelsPackage.BOUNDING_BOX__SOUTH:
      unsetSouth();
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
    case TravelsPackage.BOUNDING_BOX__WEST:
      return isSetWest();
    case TravelsPackage.BOUNDING_BOX__NORTH:
      return isSetNorth();
    case TravelsPackage.BOUNDING_BOX__EAST:
      return isSetEast();
    case TravelsPackage.BOUNDING_BOX__SOUTH:
      return isSetSouth();
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
    case TravelsPackage.BOUNDING_BOX___IS_VALID:
      return isValid();
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
    if (eIsProxy())
      return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (west: ");
    if (westESet)
      result.append(west);
    else
      result.append("<unset>");
    result.append(", north: ");
    if (northESet)
      result.append(north);
    else
      result.append("<unset>");
    result.append(", east: ");
    if (eastESet)
      result.append(east);
    else
      result.append("<unset>");
    result.append(", south: ");
    if (southESet)
      result.append(south);
    else
      result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //BoundingBoxImpl
