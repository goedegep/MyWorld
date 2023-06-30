/**
 */
package goedegep.pctools.filescontrolled.model.impl;

import goedegep.pctools.filescontrolled.model.ControlledRootFolderInfo;
import goedegep.pctools.filescontrolled.model.PCToolsPackage;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Controlled Root Folder Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.ControlledRootFolderInfoImpl#getFolderBasePath <em>Folder Base Path</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ControlledRootFolderInfoImpl extends ControlledFolderInfoImpl implements ControlledRootFolderInfo {
  /**
   * The default value of the '{@link #getFolderBasePath() <em>Folder Base Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFolderBasePath()
   * @generated
   * @ordered
   */
  protected static final String FOLDER_BASE_PATH_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFolderBasePath() <em>Folder Base Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFolderBasePath()
   * @generated
   * @ordered
   */
  protected String folderBasePath = FOLDER_BASE_PATH_EDEFAULT;

  /**
   * This is true if the Folder Base Path attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean folderBasePathESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ControlledRootFolderInfoImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return PCToolsPackage.Literals.CONTROLLED_ROOT_FOLDER_INFO;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getFolderBasePath() {
    return folderBasePath;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFolderBasePath(String newFolderBasePath) {
    String oldFolderBasePath = folderBasePath;
    folderBasePath = newFolderBasePath;
    boolean oldFolderBasePathESet = folderBasePathESet;
    folderBasePathESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PCToolsPackage.CONTROLLED_ROOT_FOLDER_INFO__FOLDER_BASE_PATH, oldFolderBasePath, folderBasePath, !oldFolderBasePathESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetFolderBasePath() {
    String oldFolderBasePath = folderBasePath;
    boolean oldFolderBasePathESet = folderBasePathESet;
    folderBasePath = FOLDER_BASE_PATH_EDEFAULT;
    folderBasePathESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, PCToolsPackage.CONTROLLED_ROOT_FOLDER_INFO__FOLDER_BASE_PATH, oldFolderBasePath, FOLDER_BASE_PATH_EDEFAULT, oldFolderBasePathESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetFolderBasePath() {
    return folderBasePathESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public String getFullPathname() {
    return getFolderBasePath();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case PCToolsPackage.CONTROLLED_ROOT_FOLDER_INFO__FOLDER_BASE_PATH:
        return getFolderBasePath();
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
      case PCToolsPackage.CONTROLLED_ROOT_FOLDER_INFO__FOLDER_BASE_PATH:
        setFolderBasePath((String)newValue);
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
      case PCToolsPackage.CONTROLLED_ROOT_FOLDER_INFO__FOLDER_BASE_PATH:
        unsetFolderBasePath();
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
      case PCToolsPackage.CONTROLLED_ROOT_FOLDER_INFO__FOLDER_BASE_PATH:
        return isSetFolderBasePath();
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
    result.append(" (folderBasePath: ");
    if (folderBasePathESet) result.append(folderBasePath); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //ControlledRootFolderInfoImpl
