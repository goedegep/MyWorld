/**
 */
package goedegep.media.photoshow.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see goedegep.media.photoshow.model.PhotoShowFactory
 * @model kind="package"
 * @generated
 */
public interface PhotoShowPackage extends EPackage {
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "model";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.goedegep.org/solvation";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "photoshow";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  PhotoShowPackage eINSTANCE = goedegep.media.photoshow.model.impl.PhotoShowPackageImpl.init();

  /**
   * The meta object id for the '{@link goedegep.media.photoshow.model.impl.PhotoShowSpecificationImpl <em>Specification</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.photoshow.model.impl.PhotoShowSpecificationImpl
   * @see goedegep.media.photoshow.model.impl.PhotoShowPackageImpl#getPhotoShowSpecification()
   * @generated
   */
  int PHOTO_SHOW_SPECIFICATION = 0;

  /**
   * The feature id for the '<em><b>Photo Folders</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHOTO_SHOW_SPECIFICATION__PHOTO_FOLDERS = 0;

  /**
   * The feature id for the '<em><b>Timeoffsetspecifications</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHOTO_SHOW_SPECIFICATION__TIMEOFFSETSPECIFICATIONS = 1;

  /**
   * The feature id for the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHOTO_SHOW_SPECIFICATION__TITLE = 2;

  /**
   * The feature id for the '<em><b>Folder Time Offset Specifications</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHOTO_SHOW_SPECIFICATION__FOLDER_TIME_OFFSET_SPECIFICATIONS = 3;

  /**
   * The feature id for the '<em><b>Photos To Show</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHOTO_SHOW_SPECIFICATION__PHOTOS_TO_SHOW = 4;

  /**
   * The number of structural features of the '<em>Specification</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHOTO_SHOW_SPECIFICATION_FEATURE_COUNT = 5;

  /**
   * The number of operations of the '<em>Specification</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHOTO_SHOW_SPECIFICATION_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.media.photoshow.model.impl.TimeOffsetSpecificationImpl <em>Time Offset Specification</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.photoshow.model.impl.TimeOffsetSpecificationImpl
   * @see goedegep.media.photoshow.model.impl.PhotoShowPackageImpl#getTimeOffsetSpecification()
   * @generated
   */
  int TIME_OFFSET_SPECIFICATION = 1;

  /**
   * The feature id for the '<em><b>Photo</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_OFFSET_SPECIFICATION__PHOTO = 0;

  /**
   * The feature id for the '<em><b>Time Offset</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_OFFSET_SPECIFICATION__TIME_OFFSET = 1;

  /**
   * The number of structural features of the '<em>Time Offset Specification</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_OFFSET_SPECIFICATION_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Time Offset Specification</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_OFFSET_SPECIFICATION_OPERATION_COUNT = 0;


  /**
   * The meta object id for the '{@link goedegep.media.photoshow.model.impl.FolderTimeOffsetSpecificationImpl <em>Folder Time Offset Specification</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.media.photoshow.model.impl.FolderTimeOffsetSpecificationImpl
   * @see goedegep.media.photoshow.model.impl.PhotoShowPackageImpl#getFolderTimeOffsetSpecification()
   * @generated
   */
  int FOLDER_TIME_OFFSET_SPECIFICATION = 2;

  /**
   * The feature id for the '<em><b>Folder Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOLDER_TIME_OFFSET_SPECIFICATION__FOLDER_NAME = 0;

  /**
   * The feature id for the '<em><b>Time Offset</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOLDER_TIME_OFFSET_SPECIFICATION__TIME_OFFSET = 1;

  /**
   * The number of structural features of the '<em>Folder Time Offset Specification</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOLDER_TIME_OFFSET_SPECIFICATION_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Folder Time Offset Specification</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOLDER_TIME_OFFSET_SPECIFICATION_OPERATION_COUNT = 0;


  /**
   * Returns the meta object for class '{@link goedegep.media.photoshow.model.PhotoShowSpecification <em>Specification</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Specification</em>'.
   * @see goedegep.media.photoshow.model.PhotoShowSpecification
   * @generated
   */
  EClass getPhotoShowSpecification();

  /**
   * Returns the meta object for the attribute list '{@link goedegep.media.photoshow.model.PhotoShowSpecification#getPhotoFolders <em>Photo Folders</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Photo Folders</em>'.
   * @see goedegep.media.photoshow.model.PhotoShowSpecification#getPhotoFolders()
   * @see #getPhotoShowSpecification()
   * @generated
   */
  EAttribute getPhotoShowSpecification_PhotoFolders();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.media.photoshow.model.PhotoShowSpecification#getTimeoffsetspecifications <em>Timeoffsetspecifications</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Timeoffsetspecifications</em>'.
   * @see goedegep.media.photoshow.model.PhotoShowSpecification#getTimeoffsetspecifications()
   * @see #getPhotoShowSpecification()
   * @generated
   */
  EReference getPhotoShowSpecification_Timeoffsetspecifications();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.photoshow.model.PhotoShowSpecification#getTitle <em>Title</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Title</em>'.
   * @see goedegep.media.photoshow.model.PhotoShowSpecification#getTitle()
   * @see #getPhotoShowSpecification()
   * @generated
   */
  EAttribute getPhotoShowSpecification_Title();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.media.photoshow.model.PhotoShowSpecification#getFolderTimeOffsetSpecifications <em>Folder Time Offset Specifications</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Folder Time Offset Specifications</em>'.
   * @see goedegep.media.photoshow.model.PhotoShowSpecification#getFolderTimeOffsetSpecifications()
   * @see #getPhotoShowSpecification()
   * @generated
   */
  EReference getPhotoShowSpecification_FolderTimeOffsetSpecifications();

  /**
   * Returns the meta object for the attribute list '{@link goedegep.media.photoshow.model.PhotoShowSpecification#getPhotosToShow <em>Photos To Show</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Photos To Show</em>'.
   * @see goedegep.media.photoshow.model.PhotoShowSpecification#getPhotosToShow()
   * @see #getPhotoShowSpecification()
   * @generated
   */
  EAttribute getPhotoShowSpecification_PhotosToShow();

  /**
   * Returns the meta object for class '{@link goedegep.media.photoshow.model.TimeOffsetSpecification <em>Time Offset Specification</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Time Offset Specification</em>'.
   * @see goedegep.media.photoshow.model.TimeOffsetSpecification
   * @generated
   */
  EClass getTimeOffsetSpecification();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.photoshow.model.TimeOffsetSpecification#getPhoto <em>Photo</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Photo</em>'.
   * @see goedegep.media.photoshow.model.TimeOffsetSpecification#getPhoto()
   * @see #getTimeOffsetSpecification()
   * @generated
   */
  EAttribute getTimeOffsetSpecification_Photo();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.photoshow.model.TimeOffsetSpecification#getTimeOffset <em>Time Offset</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Time Offset</em>'.
   * @see goedegep.media.photoshow.model.TimeOffsetSpecification#getTimeOffset()
   * @see #getTimeOffsetSpecification()
   * @generated
   */
  EAttribute getTimeOffsetSpecification_TimeOffset();

  /**
   * Returns the meta object for class '{@link goedegep.media.photoshow.model.FolderTimeOffsetSpecification <em>Folder Time Offset Specification</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Folder Time Offset Specification</em>'.
   * @see goedegep.media.photoshow.model.FolderTimeOffsetSpecification
   * @generated
   */
  EClass getFolderTimeOffsetSpecification();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.photoshow.model.FolderTimeOffsetSpecification#getFolderName <em>Folder Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Folder Name</em>'.
   * @see goedegep.media.photoshow.model.FolderTimeOffsetSpecification#getFolderName()
   * @see #getFolderTimeOffsetSpecification()
   * @generated
   */
  EAttribute getFolderTimeOffsetSpecification_FolderName();

  /**
   * Returns the meta object for the attribute '{@link goedegep.media.photoshow.model.FolderTimeOffsetSpecification#getTimeOffset <em>Time Offset</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Time Offset</em>'.
   * @see goedegep.media.photoshow.model.FolderTimeOffsetSpecification#getTimeOffset()
   * @see #getFolderTimeOffsetSpecification()
   * @generated
   */
  EAttribute getFolderTimeOffsetSpecification_TimeOffset();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  PhotoShowFactory getPhotoShowFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each operation of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals {
    /**
     * The meta object literal for the '{@link goedegep.media.photoshow.model.impl.PhotoShowSpecificationImpl <em>Specification</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.media.photoshow.model.impl.PhotoShowSpecificationImpl
     * @see goedegep.media.photoshow.model.impl.PhotoShowPackageImpl#getPhotoShowSpecification()
     * @generated
     */
    EClass PHOTO_SHOW_SPECIFICATION = eINSTANCE.getPhotoShowSpecification();

    /**
     * The meta object literal for the '<em><b>Photo Folders</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PHOTO_SHOW_SPECIFICATION__PHOTO_FOLDERS = eINSTANCE.getPhotoShowSpecification_PhotoFolders();

    /**
     * The meta object literal for the '<em><b>Timeoffsetspecifications</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PHOTO_SHOW_SPECIFICATION__TIMEOFFSETSPECIFICATIONS = eINSTANCE.getPhotoShowSpecification_Timeoffsetspecifications();

    /**
     * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PHOTO_SHOW_SPECIFICATION__TITLE = eINSTANCE.getPhotoShowSpecification_Title();

    /**
     * The meta object literal for the '<em><b>Folder Time Offset Specifications</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PHOTO_SHOW_SPECIFICATION__FOLDER_TIME_OFFSET_SPECIFICATIONS = eINSTANCE.getPhotoShowSpecification_FolderTimeOffsetSpecifications();

    /**
     * The meta object literal for the '<em><b>Photos To Show</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PHOTO_SHOW_SPECIFICATION__PHOTOS_TO_SHOW = eINSTANCE.getPhotoShowSpecification_PhotosToShow();

    /**
     * The meta object literal for the '{@link goedegep.media.photoshow.model.impl.TimeOffsetSpecificationImpl <em>Time Offset Specification</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.media.photoshow.model.impl.TimeOffsetSpecificationImpl
     * @see goedegep.media.photoshow.model.impl.PhotoShowPackageImpl#getTimeOffsetSpecification()
     * @generated
     */
    EClass TIME_OFFSET_SPECIFICATION = eINSTANCE.getTimeOffsetSpecification();

    /**
     * The meta object literal for the '<em><b>Photo</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TIME_OFFSET_SPECIFICATION__PHOTO = eINSTANCE.getTimeOffsetSpecification_Photo();

    /**
     * The meta object literal for the '<em><b>Time Offset</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TIME_OFFSET_SPECIFICATION__TIME_OFFSET = eINSTANCE.getTimeOffsetSpecification_TimeOffset();

    /**
     * The meta object literal for the '{@link goedegep.media.photoshow.model.impl.FolderTimeOffsetSpecificationImpl <em>Folder Time Offset Specification</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.media.photoshow.model.impl.FolderTimeOffsetSpecificationImpl
     * @see goedegep.media.photoshow.model.impl.PhotoShowPackageImpl#getFolderTimeOffsetSpecification()
     * @generated
     */
    EClass FOLDER_TIME_OFFSET_SPECIFICATION = eINSTANCE.getFolderTimeOffsetSpecification();

    /**
     * The meta object literal for the '<em><b>Folder Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FOLDER_TIME_OFFSET_SPECIFICATION__FOLDER_NAME = eINSTANCE.getFolderTimeOffsetSpecification_FolderName();

    /**
     * The meta object literal for the '<em><b>Time Offset</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FOLDER_TIME_OFFSET_SPECIFICATION__TIME_OFFSET = eINSTANCE.getFolderTimeOffsetSpecification_TimeOffset();

  }

} //PhotoShowPackage
