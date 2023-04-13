/**
 */
package goedegep.vacations.checklist.model.impl;

import goedegep.vacations.checklist.model.CurrentVacation;
import goedegep.vacations.checklist.model.VacationChecklist;
import goedegep.vacations.checklist.model.VacationChecklistCategoriesList;
import goedegep.vacations.checklist.model.VacationChecklistLabelsList;
import goedegep.vacations.checklist.model.VacationChecklistPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Vacation Checklist</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.checklist.model.impl.VacationChecklistImpl#getVacationChecklistLabelsList <em>Vacation Checklist Labels List</em>}</li>
 *   <li>{@link goedegep.vacations.checklist.model.impl.VacationChecklistImpl#getVacationChecklistCategoriesList <em>Vacation Checklist Categories List</em>}</li>
 *   <li>{@link goedegep.vacations.checklist.model.impl.VacationChecklistImpl#getCurrentVacation <em>Current Vacation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VacationChecklistImpl extends MinimalEObjectImpl.Container implements VacationChecklist {
  /**
   * The cached value of the '{@link #getVacationChecklistLabelsList() <em>Vacation Checklist Labels List</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVacationChecklistLabelsList()
   * @generated
   * @ordered
   */
  protected VacationChecklistLabelsList vacationChecklistLabelsList;

  /**
   * The cached value of the '{@link #getVacationChecklistCategoriesList() <em>Vacation Checklist Categories List</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVacationChecklistCategoriesList()
   * @generated
   * @ordered
   */
  protected VacationChecklistCategoriesList vacationChecklistCategoriesList;

  /**
   * The cached value of the '{@link #getCurrentVacation() <em>Current Vacation</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCurrentVacation()
   * @generated
   * @ordered
   */
  protected CurrentVacation currentVacation;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected VacationChecklistImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return VacationChecklistPackage.Literals.VACATION_CHECKLIST;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public VacationChecklistLabelsList getVacationChecklistLabelsList() {
    return vacationChecklistLabelsList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetVacationChecklistLabelsList(VacationChecklistLabelsList newVacationChecklistLabelsList, NotificationChain msgs) {
    VacationChecklistLabelsList oldVacationChecklistLabelsList = vacationChecklistLabelsList;
    vacationChecklistLabelsList = newVacationChecklistLabelsList;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, VacationChecklistPackage.VACATION_CHECKLIST__VACATION_CHECKLIST_LABELS_LIST, oldVacationChecklistLabelsList, newVacationChecklistLabelsList);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setVacationChecklistLabelsList(VacationChecklistLabelsList newVacationChecklistLabelsList) {
    if (newVacationChecklistLabelsList != vacationChecklistLabelsList) {
      NotificationChain msgs = null;
      if (vacationChecklistLabelsList != null)
        msgs = ((InternalEObject)vacationChecklistLabelsList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - VacationChecklistPackage.VACATION_CHECKLIST__VACATION_CHECKLIST_LABELS_LIST, null, msgs);
      if (newVacationChecklistLabelsList != null)
        msgs = ((InternalEObject)newVacationChecklistLabelsList).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - VacationChecklistPackage.VACATION_CHECKLIST__VACATION_CHECKLIST_LABELS_LIST, null, msgs);
      msgs = basicSetVacationChecklistLabelsList(newVacationChecklistLabelsList, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationChecklistPackage.VACATION_CHECKLIST__VACATION_CHECKLIST_LABELS_LIST, newVacationChecklistLabelsList, newVacationChecklistLabelsList));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public VacationChecklistCategoriesList getVacationChecklistCategoriesList() {
    return vacationChecklistCategoriesList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetVacationChecklistCategoriesList(VacationChecklistCategoriesList newVacationChecklistCategoriesList, NotificationChain msgs) {
    VacationChecklistCategoriesList oldVacationChecklistCategoriesList = vacationChecklistCategoriesList;
    vacationChecklistCategoriesList = newVacationChecklistCategoriesList;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, VacationChecklistPackage.VACATION_CHECKLIST__VACATION_CHECKLIST_CATEGORIES_LIST, oldVacationChecklistCategoriesList, newVacationChecklistCategoriesList);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setVacationChecklistCategoriesList(VacationChecklistCategoriesList newVacationChecklistCategoriesList) {
    if (newVacationChecklistCategoriesList != vacationChecklistCategoriesList) {
      NotificationChain msgs = null;
      if (vacationChecklistCategoriesList != null)
        msgs = ((InternalEObject)vacationChecklistCategoriesList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - VacationChecklistPackage.VACATION_CHECKLIST__VACATION_CHECKLIST_CATEGORIES_LIST, null, msgs);
      if (newVacationChecklistCategoriesList != null)
        msgs = ((InternalEObject)newVacationChecklistCategoriesList).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - VacationChecklistPackage.VACATION_CHECKLIST__VACATION_CHECKLIST_CATEGORIES_LIST, null, msgs);
      msgs = basicSetVacationChecklistCategoriesList(newVacationChecklistCategoriesList, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationChecklistPackage.VACATION_CHECKLIST__VACATION_CHECKLIST_CATEGORIES_LIST, newVacationChecklistCategoriesList, newVacationChecklistCategoriesList));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public CurrentVacation getCurrentVacation() {
    return currentVacation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetCurrentVacation(CurrentVacation newCurrentVacation, NotificationChain msgs) {
    CurrentVacation oldCurrentVacation = currentVacation;
    currentVacation = newCurrentVacation;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, VacationChecklistPackage.VACATION_CHECKLIST__CURRENT_VACATION, oldCurrentVacation, newCurrentVacation);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCurrentVacation(CurrentVacation newCurrentVacation) {
    if (newCurrentVacation != currentVacation) {
      NotificationChain msgs = null;
      if (currentVacation != null)
        msgs = ((InternalEObject)currentVacation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - VacationChecklistPackage.VACATION_CHECKLIST__CURRENT_VACATION, null, msgs);
      if (newCurrentVacation != null)
        msgs = ((InternalEObject)newCurrentVacation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - VacationChecklistPackage.VACATION_CHECKLIST__CURRENT_VACATION, null, msgs);
      msgs = basicSetCurrentVacation(newCurrentVacation, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationChecklistPackage.VACATION_CHECKLIST__CURRENT_VACATION, newCurrentVacation, newCurrentVacation));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case VacationChecklistPackage.VACATION_CHECKLIST__VACATION_CHECKLIST_LABELS_LIST:
        return basicSetVacationChecklistLabelsList(null, msgs);
      case VacationChecklistPackage.VACATION_CHECKLIST__VACATION_CHECKLIST_CATEGORIES_LIST:
        return basicSetVacationChecklistCategoriesList(null, msgs);
      case VacationChecklistPackage.VACATION_CHECKLIST__CURRENT_VACATION:
        return basicSetCurrentVacation(null, msgs);
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
      case VacationChecklistPackage.VACATION_CHECKLIST__VACATION_CHECKLIST_LABELS_LIST:
        return getVacationChecklistLabelsList();
      case VacationChecklistPackage.VACATION_CHECKLIST__VACATION_CHECKLIST_CATEGORIES_LIST:
        return getVacationChecklistCategoriesList();
      case VacationChecklistPackage.VACATION_CHECKLIST__CURRENT_VACATION:
        return getCurrentVacation();
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
      case VacationChecklistPackage.VACATION_CHECKLIST__VACATION_CHECKLIST_LABELS_LIST:
        setVacationChecklistLabelsList((VacationChecklistLabelsList)newValue);
        return;
      case VacationChecklistPackage.VACATION_CHECKLIST__VACATION_CHECKLIST_CATEGORIES_LIST:
        setVacationChecklistCategoriesList((VacationChecklistCategoriesList)newValue);
        return;
      case VacationChecklistPackage.VACATION_CHECKLIST__CURRENT_VACATION:
        setCurrentVacation((CurrentVacation)newValue);
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
      case VacationChecklistPackage.VACATION_CHECKLIST__VACATION_CHECKLIST_LABELS_LIST:
        setVacationChecklistLabelsList((VacationChecklistLabelsList)null);
        return;
      case VacationChecklistPackage.VACATION_CHECKLIST__VACATION_CHECKLIST_CATEGORIES_LIST:
        setVacationChecklistCategoriesList((VacationChecklistCategoriesList)null);
        return;
      case VacationChecklistPackage.VACATION_CHECKLIST__CURRENT_VACATION:
        setCurrentVacation((CurrentVacation)null);
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
      case VacationChecklistPackage.VACATION_CHECKLIST__VACATION_CHECKLIST_LABELS_LIST:
        return vacationChecklistLabelsList != null;
      case VacationChecklistPackage.VACATION_CHECKLIST__VACATION_CHECKLIST_CATEGORIES_LIST:
        return vacationChecklistCategoriesList != null;
      case VacationChecklistPackage.VACATION_CHECKLIST__CURRENT_VACATION:
        return currentVacation != null;
    }
    return super.eIsSet(featureID);
  }

} //VacationChecklistImpl
