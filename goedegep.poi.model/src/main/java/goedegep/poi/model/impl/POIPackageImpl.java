/**
 */
package goedegep.poi.model.impl;

import goedegep.poi.model.POICategoryId;
import goedegep.poi.model.POIFactory;
import goedegep.poi.model.POIIconResourceDescriptor;
import goedegep.poi.model.POIIconResourceInfo;
import goedegep.poi.model.POIPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class POIPackageImpl extends EPackageImpl implements POIPackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass poiIconResourceDescriptorEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass poiIconResourceInfoEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum poiCategoryIdEEnum = null;

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
   * @see goedegep.poi.model.POIPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private POIPackageImpl() {
    super(eNS_URI, POIFactory.eINSTANCE);
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
   * <p>This method is used to initialize {@link POIPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static POIPackage init() {
    if (isInited) return (POIPackage)EPackage.Registry.INSTANCE.getEPackage(POIPackage.eNS_URI);

    // Obtain or create and register package
    Object registeredPOIPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
    POIPackageImpl thePOIPackage = registeredPOIPackage instanceof POIPackageImpl ? (POIPackageImpl)registeredPOIPackage : new POIPackageImpl();

    isInited = true;

    // Create package meta-data objects
    thePOIPackage.createPackageContents();

    // Initialize created meta-data
    thePOIPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    thePOIPackage.freeze();

    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(POIPackage.eNS_URI, thePOIPackage);
    return thePOIPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getPOIIconResourceDescriptor() {
    return poiIconResourceDescriptorEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPOIIconResourceDescriptor_Category() {
    return (EAttribute)poiIconResourceDescriptorEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPOIIconResourceDescriptor_IconFileName() {
    return (EAttribute)poiIconResourceDescriptorEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getPOIIconResourceInfo() {
    return poiIconResourceInfoEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getPOIIconResourceInfo_PoiIconResourceDescriptors() {
    return (EReference)poiIconResourceInfoEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EEnum getPOICategoryId() {
    return poiCategoryIdEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public POIFactory getPOIFactory() {
    return (POIFactory)getEFactoryInstance();
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
    poiIconResourceDescriptorEClass = createEClass(POI_ICON_RESOURCE_DESCRIPTOR);
    createEAttribute(poiIconResourceDescriptorEClass, POI_ICON_RESOURCE_DESCRIPTOR__CATEGORY);
    createEAttribute(poiIconResourceDescriptorEClass, POI_ICON_RESOURCE_DESCRIPTOR__ICON_FILE_NAME);

    poiIconResourceInfoEClass = createEClass(POI_ICON_RESOURCE_INFO);
    createEReference(poiIconResourceInfoEClass, POI_ICON_RESOURCE_INFO__POI_ICON_RESOURCE_DESCRIPTORS);

    // Create enums
    poiCategoryIdEEnum = createEEnum(POI_CATEGORY_ID);
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
    initEClass(poiIconResourceDescriptorEClass, POIIconResourceDescriptor.class, "POIIconResourceDescriptor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getPOIIconResourceDescriptor_Category(), this.getPOICategoryId(), "category", "Default POI", 0, 1, POIIconResourceDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPOIIconResourceDescriptor_IconFileName(), ecorePackage.getEString(), "iconFileName", null, 0, 1, POIIconResourceDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(poiIconResourceInfoEClass, POIIconResourceInfo.class, "POIIconResourceInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getPOIIconResourceInfo_PoiIconResourceDescriptors(), this.getPOIIconResourceDescriptor(), null, "poiIconResourceDescriptors", null, 0, -1, POIIconResourceInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Initialize enums and add enum literals
    initEEnum(poiCategoryIdEEnum, POICategoryId.class, "POICategoryId");
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.AMUSEMENT);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.AUTOMOBILE_CLUB);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.BANK);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.BORDER_CROSSING);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.BOWLING_CENTER);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.BUS_STATION);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.CAMPING);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.CAR_RENTAL);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.CAR_REPAIR);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.CASINO);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.CHURCH);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.CITY_CENTER);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.CINEMA);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.COMMUNITY_BUILDING);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.EXHIBITION_CENTER);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.FERRY);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.FIRE_DEPARTMENT);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.GOLF_COURSE);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.HOSPITAL);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.HOTEL);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.ICE_SKATING_RING);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.INDUSTRY);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.LEARNING);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.LIBRARY);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.MARINA);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.MONUMENT);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.MOTORCYCLE_SERVICE);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.MUNICIPALITY);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.MUSEUM);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.NIGHTLIFE);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.PARK);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.PARK_AND_RIDE);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.PARKING);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.PETROL_STATION);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.PHARMACY);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.AIRPORT);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.ATM);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.DEFAULT_POI);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.SKI_RESORT);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.POLICE);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.POST_OFFICE);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.RAILWAY_STATION);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.REST_AREA);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.RESTAURANT);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.SHOPPING_CENTER);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.SHOP);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.SPORT);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.THEATER);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.TOURIST_INFORMATION);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.TOURIST_ATTRACTION);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.WINERY);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.SNORKELING_LOCATION);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.HOME);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.BED_AND_BREAKFAST);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.SCENIC_VIEWPOINT);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.LODGE);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.CAR_WASH);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.CONVENTION_CENTER);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.CONVENIENCE_STORE);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.EMERGENCY);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.GOVERMENT);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.GUESTHOUSE);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.BAR);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.RECREATION);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.YOUTH_HOSTEL);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.CITY);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.MOUNTAIN);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.BEACH);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.CAVE);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.MEMORIAL);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.SQUARE);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.CASTLE);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.BRIDGE);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.NEIGHBOURHOOD);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.AQUADUCT);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.BUILDING);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.LAKE);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.CANYON);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.LANDSCAPE);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.MOUNTAIN_PASS);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.HIKING);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.GLACIER);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.WATERFALL);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.SWIMMING_POOL);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.MARKET);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.BUS_STOP);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.ZOO);
    addEEnumLiteral(poiCategoryIdEEnum, POICategoryId.ISLAND);

    // Create resource
    createResource(eNS_URI);
  }

} //POIPackageImpl
