/**
 */
package goedegep.pctools.filescontrolled.model.impl;

import goedegep.pctools.filescontrolled.model.FolderInfo;
import goedegep.pctools.filescontrolled.model.PCToolsPackage;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Folder Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.FolderInfoImpl#getFolderName <em>Folder Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FolderInfoImpl extends MinimalEObjectImpl.Container implements FolderInfo {
  /**
   * The default value of the '{@link #getFolderName() <em>Folder Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFolderName()
   * @generated
   * @ordered
   */
  protected static final String FOLDER_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFolderName() <em>Folder Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFolderName()
   * @generated
   * @ordered
   */
  protected String folderName = FOLDER_NAME_EDEFAULT;

  /**
   * This is true if the Folder Name attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean folderNameESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FolderInfoImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return PCToolsPackage.Literals.FOLDER_INFO;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getFolderName() {
    return folderName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFolderName(String newFolderName) {
    String oldFolderName = folderName;
    folderName = newFolderName;
    boolean oldFolderNameESet = folderNameESet;
    folderNameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PCToolsPackage.FOLDER_INFO__FOLDER_NAME, oldFolderName, folderName, !oldFolderNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetFolderName() {
    String oldFolderName = folderName;
    boolean oldFolderNameESet = folderNameESet;
    folderName = FOLDER_NAME_EDEFAULT;
    folderNameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, PCToolsPackage.FOLDER_INFO__FOLDER_NAME, oldFolderName, FOLDER_NAME_EDEFAULT, oldFolderNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetFolderName() {
    return folderNameESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public String getFullPathname() {
    EObject container = this.eContainer();
    if (container == null) {
      throw new RuntimeException("This object isn't contained in a container yet");
    } else {
      if (container instanceof FolderInfo) {
      	FolderInfo folderInfo = (FolderInfo) container;
        String parentPathname = folderInfo.getFullPathname();
        Path path = Paths.get(parentPathname, getFolderName());
        String pathName = path.toAbsolutePath().toString();
        
        return pathName;
      } else {
        throw new RuntimeException("This object is contained in a container which isn't a FolderInfo");      
      }
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case PCToolsPackage.FOLDER_INFO__FOLDER_NAME:
        return getFolderName();
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
      case PCToolsPackage.FOLDER_INFO__FOLDER_NAME:
        setFolderName((String)newValue);
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
      case PCToolsPackage.FOLDER_INFO__FOLDER_NAME:
        unsetFolderName();
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
      case PCToolsPackage.FOLDER_INFO__FOLDER_NAME:
        return isSetFolderName();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
    switch (operationID) {
      case PCToolsPackage.FOLDER_INFO___GET_FULL_PATHNAME:
        return getFullPathname();
    }
    return super.eInvoke(operationID, arguments);
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
    result.append(" (folderName: ");
    if (folderNameESet) result.append(folderName); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //FolderInfoImpl
