/**
 */
package goedegep.poi.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see goedegep.poi.model.POIFactory
 * @model kind="package"
 * @generated
 */
public interface POIPackage extends EPackage {
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
  String eNS_URI = "http://www.goedegep.org/poi";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "poi";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  POIPackage eINSTANCE = goedegep.poi.model.impl.POIPackageImpl.init();

  /**
   * The meta object id for the '{@link goedegep.poi.model.impl.POIIconResourceDescriptorImpl <em>Icon Resource Descriptor</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.poi.model.impl.POIIconResourceDescriptorImpl
   * @see goedegep.poi.model.impl.POIPackageImpl#getPOIIconResourceDescriptor()
   * @generated
   */
  int POI_ICON_RESOURCE_DESCRIPTOR = 0;

  /**
   * The feature id for the '<em><b>Category</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int POI_ICON_RESOURCE_DESCRIPTOR__CATEGORY = 0;

  /**
   * The feature id for the '<em><b>Icon File Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int POI_ICON_RESOURCE_DESCRIPTOR__ICON_FILE_NAME = 1;

  /**
   * The number of structural features of the '<em>Icon Resource Descriptor</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int POI_ICON_RESOURCE_DESCRIPTOR_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Icon Resource Descriptor</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int POI_ICON_RESOURCE_DESCRIPTOR_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.poi.model.impl.POIIconResourceInfoImpl <em>Icon Resource Info</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.poi.model.impl.POIIconResourceInfoImpl
   * @see goedegep.poi.model.impl.POIPackageImpl#getPOIIconResourceInfo()
   * @generated
   */
  int POI_ICON_RESOURCE_INFO = 1;

  /**
   * The feature id for the '<em><b>Poi Icon Resource Descriptors</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int POI_ICON_RESOURCE_INFO__POI_ICON_RESOURCE_DESCRIPTORS = 0;

  /**
   * The number of structural features of the '<em>Icon Resource Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int POI_ICON_RESOURCE_INFO_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Icon Resource Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int POI_ICON_RESOURCE_INFO_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.poi.model.POICategoryId <em>Category Id</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.poi.model.POICategoryId
   * @see goedegep.poi.model.impl.POIPackageImpl#getPOICategoryId()
   * @generated
   */
  int POI_CATEGORY_ID = 2;


  /**
   * Returns the meta object for class '{@link goedegep.poi.model.POIIconResourceDescriptor <em>Icon Resource Descriptor</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Icon Resource Descriptor</em>'.
   * @see goedegep.poi.model.POIIconResourceDescriptor
   * @generated
   */
  EClass getPOIIconResourceDescriptor();

  /**
   * Returns the meta object for the attribute '{@link goedegep.poi.model.POIIconResourceDescriptor#getCategory <em>Category</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Category</em>'.
   * @see goedegep.poi.model.POIIconResourceDescriptor#getCategory()
   * @see #getPOIIconResourceDescriptor()
   * @generated
   */
  EAttribute getPOIIconResourceDescriptor_Category();

  /**
   * Returns the meta object for the attribute '{@link goedegep.poi.model.POIIconResourceDescriptor#getIconFileName <em>Icon File Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Icon File Name</em>'.
   * @see goedegep.poi.model.POIIconResourceDescriptor#getIconFileName()
   * @see #getPOIIconResourceDescriptor()
   * @generated
   */
  EAttribute getPOIIconResourceDescriptor_IconFileName();

  /**
   * Returns the meta object for class '{@link goedegep.poi.model.POIIconResourceInfo <em>Icon Resource Info</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Icon Resource Info</em>'.
   * @see goedegep.poi.model.POIIconResourceInfo
   * @generated
   */
  EClass getPOIIconResourceInfo();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.poi.model.POIIconResourceInfo#getPoiIconResourceDescriptors <em>Poi Icon Resource Descriptors</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Poi Icon Resource Descriptors</em>'.
   * @see goedegep.poi.model.POIIconResourceInfo#getPoiIconResourceDescriptors()
   * @see #getPOIIconResourceInfo()
   * @generated
   */
  EReference getPOIIconResourceInfo_PoiIconResourceDescriptors();

  /**
   * Returns the meta object for enum '{@link goedegep.poi.model.POICategoryId <em>Category Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Category Id</em>'.
   * @see goedegep.poi.model.POICategoryId
   * @generated
   */
  EEnum getPOICategoryId();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  POIFactory getPOIFactory();

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
     * The meta object literal for the '{@link goedegep.poi.model.impl.POIIconResourceDescriptorImpl <em>Icon Resource Descriptor</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.poi.model.impl.POIIconResourceDescriptorImpl
     * @see goedegep.poi.model.impl.POIPackageImpl#getPOIIconResourceDescriptor()
     * @generated
     */
    EClass POI_ICON_RESOURCE_DESCRIPTOR = eINSTANCE.getPOIIconResourceDescriptor();

    /**
     * The meta object literal for the '<em><b>Category</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute POI_ICON_RESOURCE_DESCRIPTOR__CATEGORY = eINSTANCE.getPOIIconResourceDescriptor_Category();

    /**
     * The meta object literal for the '<em><b>Icon File Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute POI_ICON_RESOURCE_DESCRIPTOR__ICON_FILE_NAME = eINSTANCE.getPOIIconResourceDescriptor_IconFileName();

    /**
     * The meta object literal for the '{@link goedegep.poi.model.impl.POIIconResourceInfoImpl <em>Icon Resource Info</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.poi.model.impl.POIIconResourceInfoImpl
     * @see goedegep.poi.model.impl.POIPackageImpl#getPOIIconResourceInfo()
     * @generated
     */
    EClass POI_ICON_RESOURCE_INFO = eINSTANCE.getPOIIconResourceInfo();

    /**
     * The meta object literal for the '<em><b>Poi Icon Resource Descriptors</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference POI_ICON_RESOURCE_INFO__POI_ICON_RESOURCE_DESCRIPTORS = eINSTANCE.getPOIIconResourceInfo_PoiIconResourceDescriptors();

    /**
     * The meta object literal for the '{@link goedegep.poi.model.POICategoryId <em>Category Id</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.poi.model.POICategoryId
     * @see goedegep.poi.model.impl.POIPackageImpl#getPOICategoryId()
     * @generated
     */
    EEnum POI_CATEGORY_ID = eINSTANCE.getPOICategoryId();

  }

} //POIPackage
