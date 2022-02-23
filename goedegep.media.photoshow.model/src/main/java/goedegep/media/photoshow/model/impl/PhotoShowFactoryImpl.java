/**
 */
package goedegep.media.photoshow.model.impl;

import goedegep.media.photoshow.model.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PhotoShowFactoryImpl extends EFactoryImpl implements PhotoShowFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static PhotoShowFactory init() {
    try {
      PhotoShowFactory thePhotoShowFactory = (PhotoShowFactory)EPackage.Registry.INSTANCE.getEFactory(PhotoShowPackage.eNS_URI);
      if (thePhotoShowFactory != null) {
        return thePhotoShowFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new PhotoShowFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PhotoShowFactoryImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass) {
    switch (eClass.getClassifierID()) {
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION: return createPhotoShowSpecification();
      case PhotoShowPackage.TIME_OFFSET_SPECIFICATION: return createTimeOffsetSpecification();
      case PhotoShowPackage.FOLDER_TIME_OFFSET_SPECIFICATION: return createFolderTimeOffsetSpecification();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PhotoShowSpecification createPhotoShowSpecification() {
    PhotoShowSpecificationImpl photoShowSpecification = new PhotoShowSpecificationImpl();
    return photoShowSpecification;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TimeOffsetSpecification createTimeOffsetSpecification() {
    TimeOffsetSpecificationImpl timeOffsetSpecification = new TimeOffsetSpecificationImpl();
    return timeOffsetSpecification;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FolderTimeOffsetSpecification createFolderTimeOffsetSpecification() {
    FolderTimeOffsetSpecificationImpl folderTimeOffsetSpecification = new FolderTimeOffsetSpecificationImpl();
    return folderTimeOffsetSpecification;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PhotoShowPackage getPhotoShowPackage() {
    return (PhotoShowPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static PhotoShowPackage getPackage() {
    return PhotoShowPackage.eINSTANCE;
  }

} //PhotoShowFactoryImpl
