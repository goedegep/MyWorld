/**
 */
package goedegep.media.photoshow.model.impl;

import goedegep.media.photoshow.model.FolderTimeOffsetSpecification;
import goedegep.media.photoshow.model.PhotoShowPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Folder Time Offset Specification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.photoshow.model.impl.FolderTimeOffsetSpecificationImpl#getFolderName <em>Folder Name</em>}</li>
 *   <li>{@link goedegep.media.photoshow.model.impl.FolderTimeOffsetSpecificationImpl#getTimeOffset <em>Time Offset</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FolderTimeOffsetSpecificationImpl extends MinimalEObjectImpl.Container implements FolderTimeOffsetSpecification {
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
   * The default value of the '{@link #getTimeOffset() <em>Time Offset</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTimeOffset()
   * @generated
   * @ordered
   */
  protected static final String TIME_OFFSET_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getTimeOffset() <em>Time Offset</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTimeOffset()
   * @generated
   * @ordered
   */
  protected String timeOffset = TIME_OFFSET_EDEFAULT;

  /**
   * This is true if the Time Offset attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean timeOffsetESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FolderTimeOffsetSpecificationImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return PhotoShowPackage.Literals.FOLDER_TIME_OFFSET_SPECIFICATION;
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
      eNotify(new ENotificationImpl(this, Notification.SET, PhotoShowPackage.FOLDER_TIME_OFFSET_SPECIFICATION__FOLDER_NAME, oldFolderName, folderName, !oldFolderNameESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, PhotoShowPackage.FOLDER_TIME_OFFSET_SPECIFICATION__FOLDER_NAME, oldFolderName, FOLDER_NAME_EDEFAULT, oldFolderNameESet));
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
   * @generated
   */
  @Override
  public String getTimeOffset() {
    return timeOffset;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTimeOffset(String newTimeOffset) {
    String oldTimeOffset = timeOffset;
    timeOffset = newTimeOffset;
    boolean oldTimeOffsetESet = timeOffsetESet;
    timeOffsetESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PhotoShowPackage.FOLDER_TIME_OFFSET_SPECIFICATION__TIME_OFFSET, oldTimeOffset, timeOffset, !oldTimeOffsetESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetTimeOffset() {
    String oldTimeOffset = timeOffset;
    boolean oldTimeOffsetESet = timeOffsetESet;
    timeOffset = TIME_OFFSET_EDEFAULT;
    timeOffsetESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, PhotoShowPackage.FOLDER_TIME_OFFSET_SPECIFICATION__TIME_OFFSET, oldTimeOffset, TIME_OFFSET_EDEFAULT, oldTimeOffsetESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetTimeOffset() {
    return timeOffsetESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case PhotoShowPackage.FOLDER_TIME_OFFSET_SPECIFICATION__FOLDER_NAME:
        return getFolderName();
      case PhotoShowPackage.FOLDER_TIME_OFFSET_SPECIFICATION__TIME_OFFSET:
        return getTimeOffset();
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
      case PhotoShowPackage.FOLDER_TIME_OFFSET_SPECIFICATION__FOLDER_NAME:
        setFolderName((String)newValue);
        return;
      case PhotoShowPackage.FOLDER_TIME_OFFSET_SPECIFICATION__TIME_OFFSET:
        setTimeOffset((String)newValue);
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
      case PhotoShowPackage.FOLDER_TIME_OFFSET_SPECIFICATION__FOLDER_NAME:
        unsetFolderName();
        return;
      case PhotoShowPackage.FOLDER_TIME_OFFSET_SPECIFICATION__TIME_OFFSET:
        unsetTimeOffset();
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
      case PhotoShowPackage.FOLDER_TIME_OFFSET_SPECIFICATION__FOLDER_NAME:
        return isSetFolderName();
      case PhotoShowPackage.FOLDER_TIME_OFFSET_SPECIFICATION__TIME_OFFSET:
        return isSetTimeOffset();
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
    result.append(" (folderName: ");
    if (folderNameESet) result.append(folderName); else result.append("<unset>");
    result.append(", timeOffset: ");
    if (timeOffsetESet) result.append(timeOffset); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //FolderTimeOffsetSpecificationImpl
