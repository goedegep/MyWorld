/**
 */
package goedegep.pctools.filescontrolled.model.impl;

import goedegep.pctools.filescontrolled.model.ControlledRootFolderInfo;
import goedegep.pctools.filescontrolled.model.PCToolsPackage;
import goedegep.pctools.filescontrolled.model.Result;

import goedegep.pctools.filescontrolled.model.UncontrolledRootFolderInfo;
import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Result</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.ResultImpl#getControlledrootfolderinfos <em>Controlledrootfolderinfos</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.ResultImpl#getUncontrolledRootFolderInfos <em>Uncontrolled Root Folder Infos</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ResultImpl extends MinimalEObjectImpl.Container implements Result {
  /**
   * The cached value of the '{@link #getControlledrootfolderinfos() <em>Controlledrootfolderinfos</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getControlledrootfolderinfos()
   * @generated
   * @ordered
   */
  protected EList<ControlledRootFolderInfo> controlledrootfolderinfos;

  /**
   * The cached value of the '{@link #getUncontrolledRootFolderInfos() <em>Uncontrolled Root Folder Infos</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUncontrolledRootFolderInfos()
   * @generated
   * @ordered
   */
  protected EList<UncontrolledRootFolderInfo> uncontrolledRootFolderInfos;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ResultImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return PCToolsPackage.Literals.RESULT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<ControlledRootFolderInfo> getControlledrootfolderinfos() {
    if (controlledrootfolderinfos == null) {
      controlledrootfolderinfos = new EObjectContainmentEList<ControlledRootFolderInfo>(ControlledRootFolderInfo.class, this, PCToolsPackage.RESULT__CONTROLLEDROOTFOLDERINFOS);
    }
    return controlledrootfolderinfos;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<UncontrolledRootFolderInfo> getUncontrolledRootFolderInfos() {
    if (uncontrolledRootFolderInfos == null) {
      uncontrolledRootFolderInfos = new EObjectContainmentEList<UncontrolledRootFolderInfo>(UncontrolledRootFolderInfo.class, this, PCToolsPackage.RESULT__UNCONTROLLED_ROOT_FOLDER_INFOS);
    }
    return uncontrolledRootFolderInfos;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case PCToolsPackage.RESULT__CONTROLLEDROOTFOLDERINFOS:
        return ((InternalEList<?>)getControlledrootfolderinfos()).basicRemove(otherEnd, msgs);
      case PCToolsPackage.RESULT__UNCONTROLLED_ROOT_FOLDER_INFOS:
        return ((InternalEList<?>)getUncontrolledRootFolderInfos()).basicRemove(otherEnd, msgs);
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
      case PCToolsPackage.RESULT__CONTROLLEDROOTFOLDERINFOS:
        return getControlledrootfolderinfos();
      case PCToolsPackage.RESULT__UNCONTROLLED_ROOT_FOLDER_INFOS:
        return getUncontrolledRootFolderInfos();
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
      case PCToolsPackage.RESULT__CONTROLLEDROOTFOLDERINFOS:
        getControlledrootfolderinfos().clear();
        getControlledrootfolderinfos().addAll((Collection<? extends ControlledRootFolderInfo>)newValue);
        return;
      case PCToolsPackage.RESULT__UNCONTROLLED_ROOT_FOLDER_INFOS:
        getUncontrolledRootFolderInfos().clear();
        getUncontrolledRootFolderInfos().addAll((Collection<? extends UncontrolledRootFolderInfo>)newValue);
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
      case PCToolsPackage.RESULT__CONTROLLEDROOTFOLDERINFOS:
        getControlledrootfolderinfos().clear();
        return;
      case PCToolsPackage.RESULT__UNCONTROLLED_ROOT_FOLDER_INFOS:
        getUncontrolledRootFolderInfos().clear();
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
      case PCToolsPackage.RESULT__CONTROLLEDROOTFOLDERINFOS:
        return controlledrootfolderinfos != null && !controlledrootfolderinfos.isEmpty();
      case PCToolsPackage.RESULT__UNCONTROLLED_ROOT_FOLDER_INFOS:
        return uncontrolledRootFolderInfos != null && !uncontrolledRootFolderInfos.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //ResultImpl
