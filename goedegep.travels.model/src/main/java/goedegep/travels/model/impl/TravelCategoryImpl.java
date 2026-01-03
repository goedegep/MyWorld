/**
 */
package goedegep.travels.model.impl;

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

import goedegep.travels.model.Travel;
import goedegep.travels.model.TravelCategory;
import goedegep.travels.model.TravelsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Travel Category</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.travels.model.impl.TravelCategoryImpl#getTravels <em>Travels</em>}</li>
 *   <li>{@link goedegep.travels.model.impl.TravelCategoryImpl#getTravelCategoryName <em>Travel Category Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TravelCategoryImpl extends MinimalEObjectImpl.Container implements TravelCategory {
  /**
   * The cached value of the '{@link #getTravels() <em>Travels</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTravels()
   * @generated
   * @ordered
   */
  protected EList<Travel> travels;

  /**
   * The default value of the '{@link #getTravelCategoryName() <em>Travel Category Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTravelCategoryName()
   * @generated
   * @ordered
   */
  protected static final String TRAVEL_CATEGORY_NAME_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getTravelCategoryName() <em>Travel Category Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTravelCategoryName()
   * @generated
   * @ordered
   */
  protected String travelCategoryName = TRAVEL_CATEGORY_NAME_EDEFAULT;
  /**
   * This is true if the Travel Category Name attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean travelCategoryNameESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TravelCategoryImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return TravelsPackage.Literals.TRAVEL_CATEGORY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Travel> getTravels() {
    if (travels == null) {
      travels = new EObjectContainmentEList<Travel>(Travel.class, this, TravelsPackage.TRAVEL_CATEGORY__TRAVELS);
    }
    return travels;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getTravelCategoryName() {
    return travelCategoryName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTravelCategoryName(String newTravelCategoryName) {
    String oldTravelCategoryName = travelCategoryName;
    travelCategoryName = newTravelCategoryName;
    boolean oldTravelCategoryNameESet = travelCategoryNameESet;
    travelCategoryNameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TravelsPackage.TRAVEL_CATEGORY__TRAVEL_CATEGORY_NAME,
          oldTravelCategoryName, travelCategoryName, !oldTravelCategoryNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetTravelCategoryName() {
    String oldTravelCategoryName = travelCategoryName;
    boolean oldTravelCategoryNameESet = travelCategoryNameESet;
    travelCategoryName = TRAVEL_CATEGORY_NAME_EDEFAULT;
    travelCategoryNameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, TravelsPackage.TRAVEL_CATEGORY__TRAVEL_CATEGORY_NAME,
          oldTravelCategoryName, TRAVEL_CATEGORY_NAME_EDEFAULT, oldTravelCategoryNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetTravelCategoryName() {
    return travelCategoryNameESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case TravelsPackage.TRAVEL_CATEGORY__TRAVELS:
      return ((InternalEList<?>) getTravels()).basicRemove(otherEnd, msgs);
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
    case TravelsPackage.TRAVEL_CATEGORY__TRAVELS:
      return getTravels();
    case TravelsPackage.TRAVEL_CATEGORY__TRAVEL_CATEGORY_NAME:
      return getTravelCategoryName();
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
    case TravelsPackage.TRAVEL_CATEGORY__TRAVELS:
      getTravels().clear();
      getTravels().addAll((Collection<? extends Travel>) newValue);
      return;
    case TravelsPackage.TRAVEL_CATEGORY__TRAVEL_CATEGORY_NAME:
      setTravelCategoryName((String) newValue);
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
    case TravelsPackage.TRAVEL_CATEGORY__TRAVELS:
      getTravels().clear();
      return;
    case TravelsPackage.TRAVEL_CATEGORY__TRAVEL_CATEGORY_NAME:
      unsetTravelCategoryName();
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
    case TravelsPackage.TRAVEL_CATEGORY__TRAVELS:
      return travels != null && !travels.isEmpty();
    case TravelsPackage.TRAVEL_CATEGORY__TRAVEL_CATEGORY_NAME:
      return isSetTravelCategoryName();
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
    if (eIsProxy())
      return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (travelCategoryName: ");
    if (travelCategoryNameESet)
      result.append(travelCategoryName);
    else
      result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //TravelCategoryImpl
