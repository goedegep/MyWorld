/**
 */
package goedegep.media.photoshow.model.impl;

import goedegep.media.photoshow.model.PhotoShowPackage;
import goedegep.media.photoshow.model.TimeOffsetSpecification;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Time Offset Specification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.photoshow.model.impl.TimeOffsetSpecificationImpl#getPhoto <em>Photo</em>}</li>
 *   <li>{@link goedegep.media.photoshow.model.impl.TimeOffsetSpecificationImpl#getTimeOffset <em>Time Offset</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TimeOffsetSpecificationImpl extends MinimalEObjectImpl.Container implements TimeOffsetSpecification {
  /**
   * The default value of the '{@link #getPhoto() <em>Photo</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPhoto()
   * @generated
   * @ordered
   */
  protected static final String PHOTO_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPhoto() <em>Photo</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPhoto()
   * @generated
   * @ordered
   */
  protected String photo = PHOTO_EDEFAULT;

  /**
   * This is true if the Photo attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean photoESet;

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
  protected TimeOffsetSpecificationImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return PhotoShowPackage.Literals.TIME_OFFSET_SPECIFICATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getPhoto() {
    return photo;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPhoto(String newPhoto) {
    String oldPhoto = photo;
    photo = newPhoto;
    boolean oldPhotoESet = photoESet;
    photoESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PhotoShowPackage.TIME_OFFSET_SPECIFICATION__PHOTO, oldPhoto, photo, !oldPhotoESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetPhoto() {
    String oldPhoto = photo;
    boolean oldPhotoESet = photoESet;
    photo = PHOTO_EDEFAULT;
    photoESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, PhotoShowPackage.TIME_OFFSET_SPECIFICATION__PHOTO, oldPhoto, PHOTO_EDEFAULT, oldPhotoESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetPhoto() {
    return photoESet;
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
      eNotify(new ENotificationImpl(this, Notification.SET, PhotoShowPackage.TIME_OFFSET_SPECIFICATION__TIME_OFFSET, oldTimeOffset, timeOffset, !oldTimeOffsetESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, PhotoShowPackage.TIME_OFFSET_SPECIFICATION__TIME_OFFSET, oldTimeOffset, TIME_OFFSET_EDEFAULT, oldTimeOffsetESet));
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
      case PhotoShowPackage.TIME_OFFSET_SPECIFICATION__PHOTO:
        return getPhoto();
      case PhotoShowPackage.TIME_OFFSET_SPECIFICATION__TIME_OFFSET:
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
      case PhotoShowPackage.TIME_OFFSET_SPECIFICATION__PHOTO:
        setPhoto((String)newValue);
        return;
      case PhotoShowPackage.TIME_OFFSET_SPECIFICATION__TIME_OFFSET:
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
      case PhotoShowPackage.TIME_OFFSET_SPECIFICATION__PHOTO:
        unsetPhoto();
        return;
      case PhotoShowPackage.TIME_OFFSET_SPECIFICATION__TIME_OFFSET:
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
      case PhotoShowPackage.TIME_OFFSET_SPECIFICATION__PHOTO:
        return isSetPhoto();
      case PhotoShowPackage.TIME_OFFSET_SPECIFICATION__TIME_OFFSET:
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
    result.append(" (photo: ");
    if (photoESet) result.append(photo); else result.append("<unset>");
    result.append(", timeOffset: ");
    if (timeOffsetESet) result.append(timeOffset); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //TimeOffsetSpecificationImpl
