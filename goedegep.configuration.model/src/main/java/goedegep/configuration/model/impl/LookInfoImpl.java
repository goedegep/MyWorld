/**
 */
package goedegep.configuration.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.configuration.model.ConfigurationPackage;
import goedegep.configuration.model.LookInfo;
import goedegep.configuration.model.ModuleLook;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Look Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.configuration.model.impl.LookInfoImpl#getModuleLooks <em>Module Looks</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LookInfoImpl extends MinimalEObjectImpl.Container implements LookInfo {
  /**
   * The cached value of the '{@link #getModuleLooks() <em>Module Looks</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getModuleLooks()
   * @generated
   * @ordered
   */
  protected EList<ModuleLook> moduleLooks;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected LookInfoImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ConfigurationPackage.Literals.LOOK_INFO;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<ModuleLook> getModuleLooks() {
    if (moduleLooks == null) {
      moduleLooks = new EObjectContainmentEList<ModuleLook>(ModuleLook.class, this,
          ConfigurationPackage.LOOK_INFO__MODULE_LOOKS);
    }
    return moduleLooks;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case ConfigurationPackage.LOOK_INFO__MODULE_LOOKS:
      return ((InternalEList<?>) getModuleLooks()).basicRemove(otherEnd, msgs);
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
    case ConfigurationPackage.LOOK_INFO__MODULE_LOOKS:
      return getModuleLooks();
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
    case ConfigurationPackage.LOOK_INFO__MODULE_LOOKS:
      getModuleLooks().clear();
      getModuleLooks().addAll((Collection<? extends ModuleLook>) newValue);
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
    case ConfigurationPackage.LOOK_INFO__MODULE_LOOKS:
      getModuleLooks().clear();
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
    case ConfigurationPackage.LOOK_INFO__MODULE_LOOKS:
      return moduleLooks != null && !moduleLooks.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //LookInfoImpl
