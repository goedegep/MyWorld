/**
 */
package goedegep.pctools.filescontrolled.model.impl;

import goedegep.pctools.filescontrolled.model.ControlledFolderInfo;
import goedegep.pctools.filescontrolled.model.FileInfo;
import goedegep.pctools.filescontrolled.model.PCToolsPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Controlled Folder Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.ControlledFolderInfoImpl#getSubFolderInfos <em>Sub Folder Infos</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.ControlledFolderInfoImpl#getFileinfos <em>Fileinfos</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ControlledFolderInfoImpl extends FolderInfoImpl implements ControlledFolderInfo {
  /**
   * The cached value of the '{@link #getSubFolderInfos() <em>Sub Folder Infos</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSubFolderInfos()
   * @generated
   * @ordered
   */
  protected EList<ControlledFolderInfo> subFolderInfos;

  /**
   * The cached value of the '{@link #getFileinfos() <em>Fileinfos</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFileinfos()
   * @generated
   * @ordered
   */
  protected EList<FileInfo> fileinfos;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ControlledFolderInfoImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return PCToolsPackage.Literals.CONTROLLED_FOLDER_INFO;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<ControlledFolderInfo> getSubFolderInfos() {
    if (subFolderInfos == null) {
      subFolderInfos = new EObjectContainmentEList<ControlledFolderInfo>(ControlledFolderInfo.class, this, PCToolsPackage.CONTROLLED_FOLDER_INFO__SUB_FOLDER_INFOS);
    }
    return subFolderInfos;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<FileInfo> getFileinfos() {
    if (fileinfos == null) {
      fileinfos = new EObjectContainmentEList<FileInfo>(FileInfo.class, this, PCToolsPackage.CONTROLLED_FOLDER_INFO__FILEINFOS);
    }
    return fileinfos;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case PCToolsPackage.CONTROLLED_FOLDER_INFO__SUB_FOLDER_INFOS:
        return ((InternalEList<?>)getSubFolderInfos()).basicRemove(otherEnd, msgs);
      case PCToolsPackage.CONTROLLED_FOLDER_INFO__FILEINFOS:
        return ((InternalEList<?>)getFileinfos()).basicRemove(otherEnd, msgs);
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
      case PCToolsPackage.CONTROLLED_FOLDER_INFO__SUB_FOLDER_INFOS:
        return getSubFolderInfos();
      case PCToolsPackage.CONTROLLED_FOLDER_INFO__FILEINFOS:
        return getFileinfos();
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
      case PCToolsPackage.CONTROLLED_FOLDER_INFO__SUB_FOLDER_INFOS:
        getSubFolderInfos().clear();
        getSubFolderInfos().addAll((Collection<? extends ControlledFolderInfo>)newValue);
        return;
      case PCToolsPackage.CONTROLLED_FOLDER_INFO__FILEINFOS:
        getFileinfos().clear();
        getFileinfos().addAll((Collection<? extends FileInfo>)newValue);
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
      case PCToolsPackage.CONTROLLED_FOLDER_INFO__SUB_FOLDER_INFOS:
        getSubFolderInfos().clear();
        return;
      case PCToolsPackage.CONTROLLED_FOLDER_INFO__FILEINFOS:
        getFileinfos().clear();
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
      case PCToolsPackage.CONTROLLED_FOLDER_INFO__SUB_FOLDER_INFOS:
        return subFolderInfos != null && !subFolderInfos.isEmpty();
      case PCToolsPackage.CONTROLLED_FOLDER_INFO__FILEINFOS:
        return fileinfos != null && !fileinfos.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //ControlledFolderInfoImpl
