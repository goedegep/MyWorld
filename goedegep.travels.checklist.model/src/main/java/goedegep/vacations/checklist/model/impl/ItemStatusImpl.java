/**
 */
package goedegep.vacations.checklist.model.impl;

import goedegep.vacations.checklist.model.ItemStatus;
import goedegep.vacations.checklist.model.PackStatus;
import goedegep.vacations.checklist.model.VacationChecklistItem;
import goedegep.vacations.checklist.model.VacationChecklistPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Item Status</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.checklist.model.impl.ItemStatusImpl#getVacationChecklistItem <em>Vacation Checklist Item</em>}</li>
 *   <li>{@link goedegep.vacations.checklist.model.impl.ItemStatusImpl#getPackStatus <em>Pack Status</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ItemStatusImpl extends MinimalEObjectImpl.Container implements ItemStatus {
  /**
   * The cached value of the '{@link #getVacationChecklistItem() <em>Vacation Checklist Item</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVacationChecklistItem()
   * @generated
   * @ordered
   */
  protected VacationChecklistItem vacationChecklistItem;

  /**
   * This is true if the Vacation Checklist Item reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean vacationChecklistItemESet;

  /**
   * The default value of the '{@link #getPackStatus() <em>Pack Status</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPackStatus()
   * @generated
   * @ordered
   */
  protected static final PackStatus PACK_STATUS_EDEFAULT = PackStatus.TODO;

  /**
   * The cached value of the '{@link #getPackStatus() <em>Pack Status</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPackStatus()
   * @generated
   * @ordered
   */
  protected PackStatus packStatus = PACK_STATUS_EDEFAULT;

  /**
   * This is true if the Pack Status attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean packStatusESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ItemStatusImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return VacationChecklistPackage.Literals.ITEM_STATUS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public VacationChecklistItem getVacationChecklistItem() {
    if (vacationChecklistItem != null && vacationChecklistItem.eIsProxy()) {
      InternalEObject oldVacationChecklistItem = (InternalEObject)vacationChecklistItem;
      vacationChecklistItem = (VacationChecklistItem)eResolveProxy(oldVacationChecklistItem);
      if (vacationChecklistItem != oldVacationChecklistItem) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, VacationChecklistPackage.ITEM_STATUS__VACATION_CHECKLIST_ITEM, oldVacationChecklistItem, vacationChecklistItem));
      }
    }
    return vacationChecklistItem;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VacationChecklistItem basicGetVacationChecklistItem() {
    return vacationChecklistItem;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setVacationChecklistItem(VacationChecklistItem newVacationChecklistItem) {
    VacationChecklistItem oldVacationChecklistItem = vacationChecklistItem;
    vacationChecklistItem = newVacationChecklistItem;
    boolean oldVacationChecklistItemESet = vacationChecklistItemESet;
    vacationChecklistItemESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationChecklistPackage.ITEM_STATUS__VACATION_CHECKLIST_ITEM, oldVacationChecklistItem, vacationChecklistItem, !oldVacationChecklistItemESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetVacationChecklistItem() {
    VacationChecklistItem oldVacationChecklistItem = vacationChecklistItem;
    boolean oldVacationChecklistItemESet = vacationChecklistItemESet;
    vacationChecklistItem = null;
    vacationChecklistItemESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, VacationChecklistPackage.ITEM_STATUS__VACATION_CHECKLIST_ITEM, oldVacationChecklistItem, null, oldVacationChecklistItemESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetVacationChecklistItem() {
    return vacationChecklistItemESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PackStatus getPackStatus() {
    return packStatus;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPackStatus(PackStatus newPackStatus) {
    PackStatus oldPackStatus = packStatus;
    packStatus = newPackStatus == null ? PACK_STATUS_EDEFAULT : newPackStatus;
    boolean oldPackStatusESet = packStatusESet;
    packStatusESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationChecklistPackage.ITEM_STATUS__PACK_STATUS, oldPackStatus, packStatus, !oldPackStatusESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetPackStatus() {
    PackStatus oldPackStatus = packStatus;
    boolean oldPackStatusESet = packStatusESet;
    packStatus = PACK_STATUS_EDEFAULT;
    packStatusESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, VacationChecklistPackage.ITEM_STATUS__PACK_STATUS, oldPackStatus, PACK_STATUS_EDEFAULT, oldPackStatusESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetPackStatus() {
    return packStatusESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case VacationChecklistPackage.ITEM_STATUS__VACATION_CHECKLIST_ITEM:
        if (resolve) return getVacationChecklistItem();
        return basicGetVacationChecklistItem();
      case VacationChecklistPackage.ITEM_STATUS__PACK_STATUS:
        return getPackStatus();
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
      case VacationChecklistPackage.ITEM_STATUS__VACATION_CHECKLIST_ITEM:
        setVacationChecklistItem((VacationChecklistItem)newValue);
        return;
      case VacationChecklistPackage.ITEM_STATUS__PACK_STATUS:
        setPackStatus((PackStatus)newValue);
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
      case VacationChecklistPackage.ITEM_STATUS__VACATION_CHECKLIST_ITEM:
        unsetVacationChecklistItem();
        return;
      case VacationChecklistPackage.ITEM_STATUS__PACK_STATUS:
        unsetPackStatus();
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
      case VacationChecklistPackage.ITEM_STATUS__VACATION_CHECKLIST_ITEM:
        return isSetVacationChecklistItem();
      case VacationChecklistPackage.ITEM_STATUS__PACK_STATUS:
        return isSetPackStatus();
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
    result.append(" (packStatus: ");
    if (packStatusESet) result.append(packStatus); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //ItemStatusImpl
