/**
 */
package goedegep.vacations.checklist.model.impl;

import goedegep.vacations.checklist.model.VacationChecklistCategory;
import goedegep.vacations.checklist.model.VacationChecklistItem;
import goedegep.vacations.checklist.model.VacationChecklistPackage;

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
 * An implementation of the model object '<em><b>Category</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.checklist.model.impl.VacationChecklistCategoryImpl#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.vacations.checklist.model.impl.VacationChecklistCategoryImpl#getVacationChecklistItems <em>Vacation Checklist Items</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VacationChecklistCategoryImpl extends MinimalEObjectImpl.Container implements VacationChecklistCategory {
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
   * This is true if the Name attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean nameESet;

  /**
   * The cached value of the '{@link #getVacationChecklistItems() <em>Vacation Checklist Items</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVacationChecklistItems()
   * @generated
   * @ordered
   */
  protected EList<VacationChecklistItem> vacationChecklistItems;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected VacationChecklistCategoryImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return VacationChecklistPackage.Literals.VACATION_CHECKLIST_CATEGORY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setName(String newName) {
    String oldName = name;
    name = newName;
    boolean oldNameESet = nameESet;
    nameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationChecklistPackage.VACATION_CHECKLIST_CATEGORY__NAME, oldName, name, !oldNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetName() {
    String oldName = name;
    boolean oldNameESet = nameESet;
    name = NAME_EDEFAULT;
    nameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, VacationChecklistPackage.VACATION_CHECKLIST_CATEGORY__NAME, oldName, NAME_EDEFAULT, oldNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetName() {
    return nameESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<VacationChecklistItem> getVacationChecklistItems() {
    if (vacationChecklistItems == null) {
      vacationChecklistItems = new EObjectContainmentEList<VacationChecklistItem>(VacationChecklistItem.class, this, VacationChecklistPackage.VACATION_CHECKLIST_CATEGORY__VACATION_CHECKLIST_ITEMS);
    }
    return vacationChecklistItems;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case VacationChecklistPackage.VACATION_CHECKLIST_CATEGORY__VACATION_CHECKLIST_ITEMS:
        return ((InternalEList<?>)getVacationChecklistItems()).basicRemove(otherEnd, msgs);
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
      case VacationChecklistPackage.VACATION_CHECKLIST_CATEGORY__NAME:
        return getName();
      case VacationChecklistPackage.VACATION_CHECKLIST_CATEGORY__VACATION_CHECKLIST_ITEMS:
        return getVacationChecklistItems();
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
      case VacationChecklistPackage.VACATION_CHECKLIST_CATEGORY__NAME:
        setName((String)newValue);
        return;
      case VacationChecklistPackage.VACATION_CHECKLIST_CATEGORY__VACATION_CHECKLIST_ITEMS:
        getVacationChecklistItems().clear();
        getVacationChecklistItems().addAll((Collection<? extends VacationChecklistItem>)newValue);
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
      case VacationChecklistPackage.VACATION_CHECKLIST_CATEGORY__NAME:
        unsetName();
        return;
      case VacationChecklistPackage.VACATION_CHECKLIST_CATEGORY__VACATION_CHECKLIST_ITEMS:
        getVacationChecklistItems().clear();
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
      case VacationChecklistPackage.VACATION_CHECKLIST_CATEGORY__NAME:
        return isSetName();
      case VacationChecklistPackage.VACATION_CHECKLIST_CATEGORY__VACATION_CHECKLIST_ITEMS:
        return vacationChecklistItems != null && !vacationChecklistItems.isEmpty();
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
    result.append(" (name: ");
    if (nameESet) result.append(name); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //VacationChecklistCategoryImpl
