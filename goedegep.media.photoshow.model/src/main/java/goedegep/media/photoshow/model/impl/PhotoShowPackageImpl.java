/**
 */
package goedegep.media.photoshow.model.impl;

import goedegep.media.photoshow.model.FolderTimeOffsetSpecification;
import goedegep.media.photoshow.model.PhotoShowFactory;
import goedegep.media.photoshow.model.PhotoShowPackage;
import goedegep.media.photoshow.model.PhotoShowSpecification;
import goedegep.media.photoshow.model.TimeOffsetSpecification;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PhotoShowPackageImpl extends EPackageImpl implements PhotoShowPackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass photoShowSpecificationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass timeOffsetSpecificationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass folderTimeOffsetSpecificationEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see goedegep.media.photoshow.model.PhotoShowPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private PhotoShowPackageImpl() {
    super(eNS_URI, PhotoShowFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   *
   * <p>This method is used to initialize {@link PhotoShowPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static PhotoShowPackage init() {
    if (isInited) return (PhotoShowPackage)EPackage.Registry.INSTANCE.getEPackage(PhotoShowPackage.eNS_URI);

    // Obtain or create and register package
    Object registeredPhotoShowPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
    PhotoShowPackageImpl thePhotoShowPackage = registeredPhotoShowPackage instanceof PhotoShowPackageImpl ? (PhotoShowPackageImpl)registeredPhotoShowPackage : new PhotoShowPackageImpl();

    isInited = true;

    // Create package meta-data objects
    thePhotoShowPackage.createPackageContents();

    // Initialize created meta-data
    thePhotoShowPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    thePhotoShowPackage.freeze();

    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(PhotoShowPackage.eNS_URI, thePhotoShowPackage);
    return thePhotoShowPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getPhotoShowSpecification() {
    return photoShowSpecificationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPhotoShowSpecification_PhotoFolders() {
    return (EAttribute)photoShowSpecificationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getPhotoShowSpecification_Timeoffsetspecifications() {
    return (EReference)photoShowSpecificationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPhotoShowSpecification_Title() {
    return (EAttribute)photoShowSpecificationEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getPhotoShowSpecification_FolderTimeOffsetSpecifications() {
    return (EReference)photoShowSpecificationEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPhotoShowSpecification_PhotosToShow() {
    return (EAttribute)photoShowSpecificationEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getTimeOffsetSpecification() {
    return timeOffsetSpecificationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getTimeOffsetSpecification_Photo() {
    return (EAttribute)timeOffsetSpecificationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getTimeOffsetSpecification_TimeOffset() {
    return (EAttribute)timeOffsetSpecificationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getFolderTimeOffsetSpecification() {
    return folderTimeOffsetSpecificationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getFolderTimeOffsetSpecification_FolderName() {
    return (EAttribute)folderTimeOffsetSpecificationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getFolderTimeOffsetSpecification_TimeOffset() {
    return (EAttribute)folderTimeOffsetSpecificationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PhotoShowFactory getPhotoShowFactory() {
    return (PhotoShowFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents() {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    photoShowSpecificationEClass = createEClass(PHOTO_SHOW_SPECIFICATION);
    createEAttribute(photoShowSpecificationEClass, PHOTO_SHOW_SPECIFICATION__PHOTO_FOLDERS);
    createEReference(photoShowSpecificationEClass, PHOTO_SHOW_SPECIFICATION__TIMEOFFSETSPECIFICATIONS);
    createEAttribute(photoShowSpecificationEClass, PHOTO_SHOW_SPECIFICATION__TITLE);
    createEReference(photoShowSpecificationEClass, PHOTO_SHOW_SPECIFICATION__FOLDER_TIME_OFFSET_SPECIFICATIONS);
    createEAttribute(photoShowSpecificationEClass, PHOTO_SHOW_SPECIFICATION__PHOTOS_TO_SHOW);

    timeOffsetSpecificationEClass = createEClass(TIME_OFFSET_SPECIFICATION);
    createEAttribute(timeOffsetSpecificationEClass, TIME_OFFSET_SPECIFICATION__PHOTO);
    createEAttribute(timeOffsetSpecificationEClass, TIME_OFFSET_SPECIFICATION__TIME_OFFSET);

    folderTimeOffsetSpecificationEClass = createEClass(FOLDER_TIME_OFFSET_SPECIFICATION);
    createEAttribute(folderTimeOffsetSpecificationEClass, FOLDER_TIME_OFFSET_SPECIFICATION__FOLDER_NAME);
    createEAttribute(folderTimeOffsetSpecificationEClass, FOLDER_TIME_OFFSET_SPECIFICATION__TIME_OFFSET);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents() {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes

    // Initialize classes, features, and operations; add parameters
    initEClass(photoShowSpecificationEClass, PhotoShowSpecification.class, "PhotoShowSpecification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getPhotoShowSpecification_PhotoFolders(), ecorePackage.getEString(), "photoFolders", null, 0, -1, PhotoShowSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPhotoShowSpecification_Timeoffsetspecifications(), this.getTimeOffsetSpecification(), null, "timeoffsetspecifications", null, 0, -1, PhotoShowSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPhotoShowSpecification_Title(), ecorePackage.getEString(), "title", null, 0, 1, PhotoShowSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPhotoShowSpecification_FolderTimeOffsetSpecifications(), this.getFolderTimeOffsetSpecification(), null, "folderTimeOffsetSpecifications", null, 0, -1, PhotoShowSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPhotoShowSpecification_PhotosToShow(), ecorePackage.getEString(), "photosToShow", null, 0, -1, PhotoShowSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(timeOffsetSpecificationEClass, TimeOffsetSpecification.class, "TimeOffsetSpecification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getTimeOffsetSpecification_Photo(), ecorePackage.getEString(), "photo", null, 0, 1, TimeOffsetSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getTimeOffsetSpecification_TimeOffset(), ecorePackage.getEString(), "timeOffset", null, 0, 1, TimeOffsetSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(folderTimeOffsetSpecificationEClass, FolderTimeOffsetSpecification.class, "FolderTimeOffsetSpecification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getFolderTimeOffsetSpecification_FolderName(), ecorePackage.getEString(), "folderName", null, 0, 1, FolderTimeOffsetSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFolderTimeOffsetSpecification_TimeOffset(), ecorePackage.getEString(), "timeOffset", null, 0, 1, FolderTimeOffsetSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //PhotoShowPackageImpl
