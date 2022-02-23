/**
 */
package goedegep.finan.lynx2finan.model.impl;

import goedegep.finan.lynx2finan.model.LynxToFinanPackage;
import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import goedegep.finan.lynx2finan.model.LynxToFinanShareIdList;
import goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Lynx To Finan Share Id List</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.lynx2finan.model.impl.LynxToFinanShareIdListImpl#getEntries <em>Entries</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LynxToFinanShareIdListImpl extends MinimalEObjectImpl.Container implements LynxToFinanShareIdList {
  /**
   * The cached value of the '{@link #getEntries() <em>Entries</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEntries()
   * @generated
   * @ordered
   */
  protected EList<LynxToFinanShareIdListEntry> entries;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected LynxToFinanShareIdListImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return LynxToFinanPackage.Literals.LYNX_TO_FINAN_SHARE_ID_LIST;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<LynxToFinanShareIdListEntry> getEntries() {
    if (entries == null) {
      entries = new EObjectContainmentEList<LynxToFinanShareIdListEntry>(LynxToFinanShareIdListEntry.class, this, LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST__ENTRIES);
    }
    return entries;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST__ENTRIES:
        return ((InternalEList<?>)getEntries()).basicRemove(otherEnd, msgs);
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
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST__ENTRIES:
        return getEntries();
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
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST__ENTRIES:
        getEntries().clear();
        getEntries().addAll((Collection<? extends LynxToFinanShareIdListEntry>)newValue);
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
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST__ENTRIES:
        getEntries().clear();
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
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST__ENTRIES:
        return entries != null && !entries.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //LynxToFinanShareIdListImpl
