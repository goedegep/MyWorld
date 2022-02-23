/**
 */
package goedegep.media.photoshow.model.impl;

import goedegep.media.photoshow.model.FolderTimeOffsetSpecification;
import goedegep.media.photoshow.model.PhotoShowPackage;
import goedegep.media.photoshow.model.PhotoShowSpecification;
import goedegep.media.photoshow.model.TimeOffsetSpecification;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Specification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.photoshow.model.impl.PhotoShowSpecificationImpl#getPhotoFolders <em>Photo Folders</em>}</li>
 *   <li>{@link goedegep.media.photoshow.model.impl.PhotoShowSpecificationImpl#getTimeoffsetspecifications <em>Timeoffsetspecifications</em>}</li>
 *   <li>{@link goedegep.media.photoshow.model.impl.PhotoShowSpecificationImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link goedegep.media.photoshow.model.impl.PhotoShowSpecificationImpl#getFolderTimeOffsetSpecifications <em>Folder Time Offset Specifications</em>}</li>
 *   <li>{@link goedegep.media.photoshow.model.impl.PhotoShowSpecificationImpl#getPhotosToShow <em>Photos To Show</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PhotoShowSpecificationImpl extends MinimalEObjectImpl.Container implements PhotoShowSpecification {
  /**
   * The cached value of the '{@link #getPhotoFolders() <em>Photo Folders</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPhotoFolders()
   * @generated
   * @ordered
   */
  protected EList<String> photoFolders;

  /**
   * The cached value of the '{@link #getTimeoffsetspecifications() <em>Timeoffsetspecifications</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTimeoffsetspecifications()
   * @generated
   * @ordered
   */
  protected EList<TimeOffsetSpecification> timeoffsetspecifications;

  /**
   * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTitle()
   * @generated
   * @ordered
   */
  protected static final String TITLE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTitle()
   * @generated
   * @ordered
   */
  protected String title = TITLE_EDEFAULT;

  /**
   * This is true if the Title attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean titleESet;

  /**
   * The cached value of the '{@link #getFolderTimeOffsetSpecifications() <em>Folder Time Offset Specifications</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFolderTimeOffsetSpecifications()
   * @generated
   * @ordered
   */
  protected EList<FolderTimeOffsetSpecification> folderTimeOffsetSpecifications;

  /**
   * The cached value of the '{@link #getPhotosToShow() <em>Photos To Show</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPhotosToShow()
   * @generated
   * @ordered
   */
  protected EList<String> photosToShow;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PhotoShowSpecificationImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return PhotoShowPackage.Literals.PHOTO_SHOW_SPECIFICATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<String> getPhotoFolders() {
    if (photoFolders == null) {
      photoFolders = new EDataTypeUniqueEList<String>(String.class, this, PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__PHOTO_FOLDERS);
    }
    return photoFolders;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<TimeOffsetSpecification> getTimeoffsetspecifications() {
    if (timeoffsetspecifications == null) {
      timeoffsetspecifications = new EObjectContainmentEList<TimeOffsetSpecification>(TimeOffsetSpecification.class, this, PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__TIMEOFFSETSPECIFICATIONS);
    }
    return timeoffsetspecifications;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getTitle() {
    return title;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTitle(String newTitle) {
    String oldTitle = title;
    title = newTitle;
    boolean oldTitleESet = titleESet;
    titleESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__TITLE, oldTitle, title, !oldTitleESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetTitle() {
    String oldTitle = title;
    boolean oldTitleESet = titleESet;
    title = TITLE_EDEFAULT;
    titleESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__TITLE, oldTitle, TITLE_EDEFAULT, oldTitleESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetTitle() {
    return titleESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<FolderTimeOffsetSpecification> getFolderTimeOffsetSpecifications() {
    if (folderTimeOffsetSpecifications == null) {
      folderTimeOffsetSpecifications = new EObjectContainmentEList<FolderTimeOffsetSpecification>(FolderTimeOffsetSpecification.class, this, PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__FOLDER_TIME_OFFSET_SPECIFICATIONS);
    }
    return folderTimeOffsetSpecifications;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<String> getPhotosToShow() {
    if (photosToShow == null) {
      photosToShow = new EDataTypeUniqueEList<String>(String.class, this, PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__PHOTOS_TO_SHOW);
    }
    return photosToShow;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__TIMEOFFSETSPECIFICATIONS:
        return ((InternalEList<?>)getTimeoffsetspecifications()).basicRemove(otherEnd, msgs);
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__FOLDER_TIME_OFFSET_SPECIFICATIONS:
        return ((InternalEList<?>)getFolderTimeOffsetSpecifications()).basicRemove(otherEnd, msgs);
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
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__PHOTO_FOLDERS:
        return getPhotoFolders();
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__TIMEOFFSETSPECIFICATIONS:
        return getTimeoffsetspecifications();
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__TITLE:
        return getTitle();
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__FOLDER_TIME_OFFSET_SPECIFICATIONS:
        return getFolderTimeOffsetSpecifications();
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__PHOTOS_TO_SHOW:
        return getPhotosToShow();
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
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__PHOTO_FOLDERS:
        getPhotoFolders().clear();
        getPhotoFolders().addAll((Collection<? extends String>)newValue);
        return;
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__TIMEOFFSETSPECIFICATIONS:
        getTimeoffsetspecifications().clear();
        getTimeoffsetspecifications().addAll((Collection<? extends TimeOffsetSpecification>)newValue);
        return;
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__TITLE:
        setTitle((String)newValue);
        return;
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__FOLDER_TIME_OFFSET_SPECIFICATIONS:
        getFolderTimeOffsetSpecifications().clear();
        getFolderTimeOffsetSpecifications().addAll((Collection<? extends FolderTimeOffsetSpecification>)newValue);
        return;
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__PHOTOS_TO_SHOW:
        getPhotosToShow().clear();
        getPhotosToShow().addAll((Collection<? extends String>)newValue);
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
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__PHOTO_FOLDERS:
        getPhotoFolders().clear();
        return;
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__TIMEOFFSETSPECIFICATIONS:
        getTimeoffsetspecifications().clear();
        return;
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__TITLE:
        unsetTitle();
        return;
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__FOLDER_TIME_OFFSET_SPECIFICATIONS:
        getFolderTimeOffsetSpecifications().clear();
        return;
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__PHOTOS_TO_SHOW:
        getPhotosToShow().clear();
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
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__PHOTO_FOLDERS:
        return photoFolders != null && !photoFolders.isEmpty();
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__TIMEOFFSETSPECIFICATIONS:
        return timeoffsetspecifications != null && !timeoffsetspecifications.isEmpty();
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__TITLE:
        return isSetTitle();
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__FOLDER_TIME_OFFSET_SPECIFICATIONS:
        return folderTimeOffsetSpecifications != null && !folderTimeOffsetSpecifications.isEmpty();
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__PHOTOS_TO_SHOW:
        return photosToShow != null && !photosToShow.isEmpty();
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
    result.append(" (photoFolders: ");
    result.append(photoFolders);
    result.append(", title: ");
    if (titleESet) result.append(title); else result.append("<unset>");
    result.append(", photosToShow: ");
    result.append(photosToShow);
    result.append(')');
    return result.toString();
  }

} //PhotoShowSpecificationImpl
