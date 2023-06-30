/**
 */
package goedegep.pctools.filescontrolled.model.impl;

import goedegep.pctools.filescontrolled.model.FileInfo;
import goedegep.pctools.filescontrolled.model.PCToolsPackage;
import goedegep.pctools.filescontrolled.model.UncontrolledFolderInfo;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Uncontrolled Folder Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.UncontrolledFolderInfoImpl#isAllContentsHasCopies <em>All Contents Has Copies</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.UncontrolledFolderInfoImpl#getFileinfos <em>Fileinfos</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.UncontrolledFolderInfoImpl#getSubFoldersInfos <em>Sub Folders Infos</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UncontrolledFolderInfoImpl extends FolderInfoImpl implements UncontrolledFolderInfo {
  /**
   * The default value of the '{@link #isAllContentsHasCopies() <em>All Contents Has Copies</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isAllContentsHasCopies()
   * @generated
   * @ordered
   */
  protected static final boolean ALL_CONTENTS_HAS_COPIES_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isAllContentsHasCopies() <em>All Contents Has Copies</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isAllContentsHasCopies()
   * @generated
   * @ordered
   */
  protected boolean allContentsHasCopies = ALL_CONTENTS_HAS_COPIES_EDEFAULT;

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
   * The cached value of the '{@link #getSubFoldersInfos() <em>Sub Folders Infos</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSubFoldersInfos()
   * @generated
   * @ordered
   */
  protected EList<UncontrolledFolderInfo> subFoldersInfos;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected UncontrolledFolderInfoImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return PCToolsPackage.Literals.UNCONTROLLED_FOLDER_INFO;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isAllContentsHasCopies() {
    return allContentsHasCopies;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setAllContentsHasCopies(boolean newAllContentsHasCopies) {
    boolean oldAllContentsHasCopies = allContentsHasCopies;
    allContentsHasCopies = newAllContentsHasCopies;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PCToolsPackage.UNCONTROLLED_FOLDER_INFO__ALL_CONTENTS_HAS_COPIES, oldAllContentsHasCopies, allContentsHasCopies));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<FileInfo> getFileinfos() {
    if (fileinfos == null) {
      fileinfos = new EObjectContainmentEList<FileInfo>(FileInfo.class, this, PCToolsPackage.UNCONTROLLED_FOLDER_INFO__FILEINFOS);
    }
    return fileinfos;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<UncontrolledFolderInfo> getSubFoldersInfos() {
    if (subFoldersInfos == null) {
      subFoldersInfos = new EObjectContainmentEList<UncontrolledFolderInfo>(UncontrolledFolderInfo.class, this, PCToolsPackage.UNCONTROLLED_FOLDER_INFO__SUB_FOLDERS_INFOS);
    }
    return subFoldersInfos;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case PCToolsPackage.UNCONTROLLED_FOLDER_INFO__FILEINFOS:
        return ((InternalEList<?>)getFileinfos()).basicRemove(otherEnd, msgs);
      case PCToolsPackage.UNCONTROLLED_FOLDER_INFO__SUB_FOLDERS_INFOS:
        return ((InternalEList<?>)getSubFoldersInfos()).basicRemove(otherEnd, msgs);
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
      case PCToolsPackage.UNCONTROLLED_FOLDER_INFO__ALL_CONTENTS_HAS_COPIES:
        return isAllContentsHasCopies();
      case PCToolsPackage.UNCONTROLLED_FOLDER_INFO__FILEINFOS:
        return getFileinfos();
      case PCToolsPackage.UNCONTROLLED_FOLDER_INFO__SUB_FOLDERS_INFOS:
        return getSubFoldersInfos();
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
      case PCToolsPackage.UNCONTROLLED_FOLDER_INFO__ALL_CONTENTS_HAS_COPIES:
        setAllContentsHasCopies((Boolean)newValue);
        return;
      case PCToolsPackage.UNCONTROLLED_FOLDER_INFO__FILEINFOS:
        getFileinfos().clear();
        getFileinfos().addAll((Collection<? extends FileInfo>)newValue);
        return;
      case PCToolsPackage.UNCONTROLLED_FOLDER_INFO__SUB_FOLDERS_INFOS:
        getSubFoldersInfos().clear();
        getSubFoldersInfos().addAll((Collection<? extends UncontrolledFolderInfo>)newValue);
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
      case PCToolsPackage.UNCONTROLLED_FOLDER_INFO__ALL_CONTENTS_HAS_COPIES:
        setAllContentsHasCopies(ALL_CONTENTS_HAS_COPIES_EDEFAULT);
        return;
      case PCToolsPackage.UNCONTROLLED_FOLDER_INFO__FILEINFOS:
        getFileinfos().clear();
        return;
      case PCToolsPackage.UNCONTROLLED_FOLDER_INFO__SUB_FOLDERS_INFOS:
        getSubFoldersInfos().clear();
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
      case PCToolsPackage.UNCONTROLLED_FOLDER_INFO__ALL_CONTENTS_HAS_COPIES:
        return allContentsHasCopies != ALL_CONTENTS_HAS_COPIES_EDEFAULT;
      case PCToolsPackage.UNCONTROLLED_FOLDER_INFO__FILEINFOS:
        return fileinfos != null && !fileinfos.isEmpty();
      case PCToolsPackage.UNCONTROLLED_FOLDER_INFO__SUB_FOLDERS_INFOS:
        return subFoldersInfos != null && !subFoldersInfos.isEmpty();
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
    result.append(" (allContentsHasCopies: ");
    result.append(allContentsHasCopies);
    result.append(')');
    return result.toString();
  }

} //UncontrolledFolderInfoImpl
