/**
 */
package goedegep.pctools.filescontrolled.model.impl;

import goedegep.pctools.filescontrolled.model.EqualityType;
import goedegep.pctools.filescontrolled.model.FileInfo;
import goedegep.pctools.filescontrolled.model.FolderInfo;
import goedegep.pctools.filescontrolled.model.PCToolsPackage;
import goedegep.util.file.FileUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>File Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.FileInfoImpl#getFileName <em>File Name</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.FileInfoImpl#getCopyOf <em>Copy Of</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.FileInfoImpl#getEqualityType <em>Equality Type</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.impl.FileInfoImpl#getMd5String <em>Md5 String</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FileInfoImpl extends MinimalEObjectImpl.Container implements FileInfo {
  /**
   * The default value of the '{@link #getFileName() <em>File Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFileName()
   * @generated
   * @ordered
   */
  protected static final String FILE_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFileName() <em>File Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFileName()
   * @generated
   * @ordered
   */
  protected String fileName = FILE_NAME_EDEFAULT;

  /**
   * This is true if the File Name attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean fileNameESet;

  /**
   * The cached value of the '{@link #getCopyOf() <em>Copy Of</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCopyOf()
   * @generated
   * @ordered
   */
  protected FileInfo copyOf;

  /**
   * This is true if the Copy Of reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean copyOfESet;

  /**
   * The default value of the '{@link #getEqualityType() <em>Equality Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEqualityType()
   * @generated
   * @ordered
   */
  protected static final EqualityType EQUALITY_TYPE_EDEFAULT = EqualityType.SIZE;

  /**
   * The cached value of the '{@link #getEqualityType() <em>Equality Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEqualityType()
   * @generated
   * @ordered
   */
  protected EqualityType equalityType = EQUALITY_TYPE_EDEFAULT;

  /**
   * The default value of the '{@link #getMd5String() <em>Md5 String</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMd5String()
   * @generated
   * @ordered
   */
  protected static final String MD5_STRING_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMd5String() <em>Md5 String</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMd5String()
   * @generated
   * @ordered
   */
  protected String md5String = MD5_STRING_EDEFAULT;

  /**
   * This is true if the Md5 String attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean md5StringESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FileInfoImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return PCToolsPackage.Literals.FILE_INFO;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getFileName() {
    return fileName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFileName(String newFileName) {
    String oldFileName = fileName;
    fileName = newFileName;
    boolean oldFileNameESet = fileNameESet;
    fileNameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PCToolsPackage.FILE_INFO__FILE_NAME, oldFileName, fileName, !oldFileNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetFileName() {
    String oldFileName = fileName;
    boolean oldFileNameESet = fileNameESet;
    fileName = FILE_NAME_EDEFAULT;
    fileNameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, PCToolsPackage.FILE_INFO__FILE_NAME, oldFileName, FILE_NAME_EDEFAULT, oldFileNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetFileName() {
    return fileNameESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FileInfo getCopyOf() {
    if (copyOf != null && copyOf.eIsProxy()) {
      InternalEObject oldCopyOf = (InternalEObject)copyOf;
      copyOf = (FileInfo)eResolveProxy(oldCopyOf);
      if (copyOf != oldCopyOf) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, PCToolsPackage.FILE_INFO__COPY_OF, oldCopyOf, copyOf));
      }
    }
    return copyOf;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FileInfo basicGetCopyOf() {
    return copyOf;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCopyOf(FileInfo newCopyOf) {
    FileInfo oldCopyOf = copyOf;
    copyOf = newCopyOf;
    boolean oldCopyOfESet = copyOfESet;
    copyOfESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PCToolsPackage.FILE_INFO__COPY_OF, oldCopyOf, copyOf, !oldCopyOfESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetCopyOf() {
    FileInfo oldCopyOf = copyOf;
    boolean oldCopyOfESet = copyOfESet;
    copyOf = null;
    copyOfESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, PCToolsPackage.FILE_INFO__COPY_OF, oldCopyOf, null, oldCopyOfESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetCopyOf() {
    return copyOfESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EqualityType getEqualityType() {
    return equalityType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setEqualityType(EqualityType newEqualityType) {
    EqualityType oldEqualityType = equalityType;
    equalityType = newEqualityType == null ? EQUALITY_TYPE_EDEFAULT : newEqualityType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PCToolsPackage.FILE_INFO__EQUALITY_TYPE, oldEqualityType, equalityType));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public String getMd5String() {
    if (md5String == null) {
      try {
        md5String = FileUtils.generateMD5String(getFullPathname());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    return md5String;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setMd5String(String newMd5String) {
    String oldMd5String = md5String;
    md5String = newMd5String;
    boolean oldMd5StringESet = md5StringESet;
    md5StringESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PCToolsPackage.FILE_INFO__MD5_STRING, oldMd5String, md5String, !oldMd5StringESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetMd5String() {
    String oldMd5String = md5String;
    boolean oldMd5StringESet = md5StringESet;
    md5String = MD5_STRING_EDEFAULT;
    md5StringESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, PCToolsPackage.FILE_INFO__MD5_STRING, oldMd5String, MD5_STRING_EDEFAULT, oldMd5StringESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetMd5String() {
    return md5StringESet;
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
        Path path = Paths.get(parentPathname, getFileName());
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
      case PCToolsPackage.FILE_INFO__FILE_NAME:
        return getFileName();
      case PCToolsPackage.FILE_INFO__COPY_OF:
        if (resolve) return getCopyOf();
        return basicGetCopyOf();
      case PCToolsPackage.FILE_INFO__EQUALITY_TYPE:
        return getEqualityType();
      case PCToolsPackage.FILE_INFO__MD5_STRING:
        return getMd5String();
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
      case PCToolsPackage.FILE_INFO__FILE_NAME:
        setFileName((String)newValue);
        return;
      case PCToolsPackage.FILE_INFO__COPY_OF:
        setCopyOf((FileInfo)newValue);
        return;
      case PCToolsPackage.FILE_INFO__EQUALITY_TYPE:
        setEqualityType((EqualityType)newValue);
        return;
      case PCToolsPackage.FILE_INFO__MD5_STRING:
        setMd5String((String)newValue);
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
      case PCToolsPackage.FILE_INFO__FILE_NAME:
        unsetFileName();
        return;
      case PCToolsPackage.FILE_INFO__COPY_OF:
        unsetCopyOf();
        return;
      case PCToolsPackage.FILE_INFO__EQUALITY_TYPE:
        setEqualityType(EQUALITY_TYPE_EDEFAULT);
        return;
      case PCToolsPackage.FILE_INFO__MD5_STRING:
        unsetMd5String();
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
      case PCToolsPackage.FILE_INFO__FILE_NAME:
        return isSetFileName();
      case PCToolsPackage.FILE_INFO__COPY_OF:
        return isSetCopyOf();
      case PCToolsPackage.FILE_INFO__EQUALITY_TYPE:
        return equalityType != EQUALITY_TYPE_EDEFAULT;
      case PCToolsPackage.FILE_INFO__MD5_STRING:
        return isSetMd5String();
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
      case PCToolsPackage.FILE_INFO___GET_FULL_PATHNAME:
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
    result.append(" (fileName: ");
    if (fileNameESet) result.append(fileName); else result.append("<unset>");
    result.append(", equalityType: ");
    result.append(equalityType);
    result.append(", md5String: ");
    if (md5StringESet) result.append(md5String); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //FileInfoImpl
