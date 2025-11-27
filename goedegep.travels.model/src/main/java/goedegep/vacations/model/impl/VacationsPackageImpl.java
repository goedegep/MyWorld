/**
 */
package goedegep.vacations.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import goedegep.poi.app.LocationCategory;
import goedegep.types.model.TypesPackage;
import goedegep.vacations.model.Boundary;
import goedegep.vacations.model.BoundingBox;
import goedegep.vacations.model.Day;
import goedegep.vacations.model.DayTrip;
import goedegep.vacations.model.Document;
import goedegep.vacations.model.GPXTrack;
import goedegep.vacations.model.Location;
import goedegep.vacations.model.MapImage;
import goedegep.vacations.model.Picture;
import goedegep.vacations.model.Text;
import goedegep.vacations.model.Travel;
import goedegep.vacations.model.TravelCategory;
import goedegep.vacations.model.Vacation;
import goedegep.vacations.model.VacationElement;
import goedegep.vacations.model.Vacations;
import goedegep.vacations.model.VacationsFactory;
import goedegep.vacations.model.VacationsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class VacationsPackageImpl extends EPackageImpl implements VacationsPackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass vacationsEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass vacationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass locationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass vacationElementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass textEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass dayEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass pictureEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass gpxTrackEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass boundingBoxEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass boundaryEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass mapImageEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass dayTripEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass documentEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass travelCategoryEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass travelEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EDataType eLocationCategoryEDataType = null;

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
   * @see goedegep.vacations.model.VacationsPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private VacationsPackageImpl() {
    super(eNS_URI, VacationsFactory.eINSTANCE);
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
   * <p>This method is used to initialize {@link VacationsPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static VacationsPackage init() {
    if (isInited)
      return (VacationsPackage) EPackage.Registry.INSTANCE.getEPackage(VacationsPackage.eNS_URI);

    // Obtain or create and register package
    Object registeredVacationsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
    VacationsPackageImpl theVacationsPackage = registeredVacationsPackage instanceof VacationsPackageImpl
        ? (VacationsPackageImpl) registeredVacationsPackage
        : new VacationsPackageImpl();

    isInited = true;

    // Initialize simple dependencies
    TypesPackage.eINSTANCE.eClass();
    XMLTypePackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theVacationsPackage.createPackageContents();

    // Initialize created meta-data
    theVacationsPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theVacationsPackage.freeze();

    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(VacationsPackage.eNS_URI, theVacationsPackage);
    return theVacationsPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getVacations() {
    return vacationsEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getVacations_Vacations() {
    return (EReference) vacationsEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getVacations_Home() {
    return (EReference) vacationsEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getVacations_Tips() {
    return (EAttribute) vacationsEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getVacations_DayTrips() {
    return (EReference) vacationsEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getVacations_TravelCategories() {
    return (EReference) vacationsEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getVacations__FindVacation__FlexDate_String() {
    return vacationsEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getVacations__AddVacation__Vacation() {
    return vacationsEClass.getEOperations().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getVacations__FindVacation__FlexDate() {
    return vacationsEClass.getEOperations().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getVacations__GetTravels() {
    return vacationsEClass.getEOperations().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getVacation() {
    return vacationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getVacation_EndDate() {
    return (EAttribute) vacationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getVacation_Documents() {
    return (EReference) vacationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getVacation_Pictures() {
    return (EAttribute) vacationEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getVacation_Elements() {
    return (EReference) vacationEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getVacation__FindDocument__String() {
    return vacationEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getVacation__GetAllReferencedFiles() {
    return vacationEClass.getEOperations().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getVacation__GetDayNr__VacationElement() {
    return vacationEClass.getEOperations().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getLocation() {
    return locationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLocation_LocationCategory() {
    return (EAttribute) locationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLocation_Country() {
    return (EAttribute) locationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLocation_City() {
    return (EAttribute) locationEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLocation_Street() {
    return (EAttribute) locationEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLocation_HouseNumber() {
    return (EAttribute) locationEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLocation_Latitude() {
    return (EAttribute) locationEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLocation_Longitude() {
    return (EAttribute) locationEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLocation_Name() {
    return (EAttribute) locationEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLocation_WebSite() {
    return (EAttribute) locationEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLocation_Description() {
    return (EAttribute) locationEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLocation_Duration() {
    return (EAttribute) locationEClass.getEStructuralFeatures().get(10);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLocation_StartDate() {
    return (EAttribute) locationEClass.getEStructuralFeatures().get(11);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLocation_EndDate() {
    return (EAttribute) locationEClass.getEStructuralFeatures().get(12);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getLocation_Boundingbox() {
    return (EReference) locationEClass.getEStructuralFeatures().get(13);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getLocation_Boundaries() {
    return (EReference) locationEClass.getEStructuralFeatures().get(14);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLocation_ReferenceOnly() {
    return (EAttribute) locationEClass.getEStructuralFeatures().get(15);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLocation_StayedAtThisLocation() {
    return (EAttribute) locationEClass.getEStructuralFeatures().get(16);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getVacationElement() {
    return vacationElementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getVacationElement_Children() {
    return (EReference) vacationElementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getVacationElement__GetDayNr() {
    return vacationElementEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getVacationElement__GetVacation() {
    return vacationElementEClass.getEOperations().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getVacationElement__GetDayTrip() {
    return vacationElementEClass.getEOperations().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getVacationElement__GetDay() {
    return vacationElementEClass.getEOperations().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getText() {
    return textEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getText_Text() {
    return (EAttribute) textEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getDay() {
    return dayEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getDay_Days() {
    return (EAttribute) dayEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getDay_Title() {
    return (EAttribute) dayEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getDay__GetDate() {
    return dayEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getPicture() {
    return pictureEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getPicture_PictureReference() {
    return (EReference) pictureEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getGPXTrack() {
    return gpxTrackEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getGPXTrack_TrackReference() {
    return (EReference) gpxTrackEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getBoundingBox() {
    return boundingBoxEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getBoundingBox_West() {
    return (EAttribute) boundingBoxEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getBoundingBox_North() {
    return (EAttribute) boundingBoxEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getBoundingBox_East() {
    return (EAttribute) boundingBoxEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getBoundingBox_South() {
    return (EAttribute) boundingBoxEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getBoundingBox__IsValid() {
    return boundingBoxEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getBoundary() {
    return boundaryEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getBoundary_Points() {
    return (EAttribute) boundaryEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getMapImage() {
    return mapImageEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMapImage_Title() {
    return (EAttribute) mapImageEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMapImage_ImageWidth() {
    return (EAttribute) mapImageEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMapImage_ImageHeight() {
    return (EAttribute) mapImageEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMapImage_Zoom() {
    return (EAttribute) mapImageEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMapImage_CenterLatitude() {
    return (EAttribute) mapImageEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMapImage_CenterLongitude() {
    return (EAttribute) mapImageEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMapImage_FileName() {
    return (EAttribute) mapImageEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getDayTrip() {
    return dayTripEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getDayTrip_Elements() {
    return (EReference) dayTripEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getDocument() {
    return documentEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getDocument_DocumentReference() {
    return (EReference) documentEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getTravelCategory() {
    return travelCategoryEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getTravelCategory_Travels() {
    return (EReference) travelCategoryEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getTravel() {
    return travelEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getTravel_Title() {
    return (EAttribute) travelEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getTravel__GetId() {
    return travelEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getTravel__GetAllFileReferences() {
    return travelEClass.getEOperations().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EDataType getELocationCategory() {
    return eLocationCategoryEDataType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public VacationsFactory getVacationsFactory() {
    return (VacationsFactory) getEFactoryInstance();
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
    if (isCreated)
      return;
    isCreated = true;

    // Create classes and their features
    vacationsEClass = createEClass(VACATIONS);
    createEReference(vacationsEClass, VACATIONS__VACATIONS);
    createEReference(vacationsEClass, VACATIONS__HOME);
    createEAttribute(vacationsEClass, VACATIONS__TIPS);
    createEReference(vacationsEClass, VACATIONS__DAY_TRIPS);
    createEReference(vacationsEClass, VACATIONS__TRAVEL_CATEGORIES);
    createEOperation(vacationsEClass, VACATIONS___FIND_VACATION__FLEXDATE_STRING);
    createEOperation(vacationsEClass, VACATIONS___ADD_VACATION__VACATION);
    createEOperation(vacationsEClass, VACATIONS___FIND_VACATION__FLEXDATE);
    createEOperation(vacationsEClass, VACATIONS___GET_TRAVELS);

    vacationEClass = createEClass(VACATION);
    createEAttribute(vacationEClass, VACATION__END_DATE);
    createEReference(vacationEClass, VACATION__DOCUMENTS);
    createEAttribute(vacationEClass, VACATION__PICTURES);
    createEReference(vacationEClass, VACATION__ELEMENTS);
    createEOperation(vacationEClass, VACATION___FIND_DOCUMENT__STRING);
    createEOperation(vacationEClass, VACATION___GET_ALL_REFERENCED_FILES);
    createEOperation(vacationEClass, VACATION___GET_DAY_NR__VACATIONELEMENT);

    locationEClass = createEClass(LOCATION);
    createEAttribute(locationEClass, LOCATION__LOCATION_CATEGORY);
    createEAttribute(locationEClass, LOCATION__COUNTRY);
    createEAttribute(locationEClass, LOCATION__CITY);
    createEAttribute(locationEClass, LOCATION__STREET);
    createEAttribute(locationEClass, LOCATION__HOUSE_NUMBER);
    createEAttribute(locationEClass, LOCATION__LATITUDE);
    createEAttribute(locationEClass, LOCATION__LONGITUDE);
    createEAttribute(locationEClass, LOCATION__NAME);
    createEAttribute(locationEClass, LOCATION__WEB_SITE);
    createEAttribute(locationEClass, LOCATION__DESCRIPTION);
    createEAttribute(locationEClass, LOCATION__DURATION);
    createEAttribute(locationEClass, LOCATION__START_DATE);
    createEAttribute(locationEClass, LOCATION__END_DATE);
    createEReference(locationEClass, LOCATION__BOUNDINGBOX);
    createEReference(locationEClass, LOCATION__BOUNDARIES);
    createEAttribute(locationEClass, LOCATION__REFERENCE_ONLY);
    createEAttribute(locationEClass, LOCATION__STAYED_AT_THIS_LOCATION);

    vacationElementEClass = createEClass(VACATION_ELEMENT);
    createEReference(vacationElementEClass, VACATION_ELEMENT__CHILDREN);
    createEOperation(vacationElementEClass, VACATION_ELEMENT___GET_DAY_NR);
    createEOperation(vacationElementEClass, VACATION_ELEMENT___GET_VACATION);
    createEOperation(vacationElementEClass, VACATION_ELEMENT___GET_DAY_TRIP);
    createEOperation(vacationElementEClass, VACATION_ELEMENT___GET_DAY);

    textEClass = createEClass(TEXT);
    createEAttribute(textEClass, TEXT__TEXT);

    dayEClass = createEClass(DAY);
    createEAttribute(dayEClass, DAY__DAYS);
    createEAttribute(dayEClass, DAY__TITLE);
    createEOperation(dayEClass, DAY___GET_DATE);

    pictureEClass = createEClass(PICTURE);
    createEReference(pictureEClass, PICTURE__PICTURE_REFERENCE);

    gpxTrackEClass = createEClass(GPX_TRACK);
    createEReference(gpxTrackEClass, GPX_TRACK__TRACK_REFERENCE);

    boundingBoxEClass = createEClass(BOUNDING_BOX);
    createEAttribute(boundingBoxEClass, BOUNDING_BOX__WEST);
    createEAttribute(boundingBoxEClass, BOUNDING_BOX__NORTH);
    createEAttribute(boundingBoxEClass, BOUNDING_BOX__EAST);
    createEAttribute(boundingBoxEClass, BOUNDING_BOX__SOUTH);
    createEOperation(boundingBoxEClass, BOUNDING_BOX___IS_VALID);

    boundaryEClass = createEClass(BOUNDARY);
    createEAttribute(boundaryEClass, BOUNDARY__POINTS);

    mapImageEClass = createEClass(MAP_IMAGE);
    createEAttribute(mapImageEClass, MAP_IMAGE__TITLE);
    createEAttribute(mapImageEClass, MAP_IMAGE__IMAGE_WIDTH);
    createEAttribute(mapImageEClass, MAP_IMAGE__IMAGE_HEIGHT);
    createEAttribute(mapImageEClass, MAP_IMAGE__ZOOM);
    createEAttribute(mapImageEClass, MAP_IMAGE__CENTER_LATITUDE);
    createEAttribute(mapImageEClass, MAP_IMAGE__CENTER_LONGITUDE);
    createEAttribute(mapImageEClass, MAP_IMAGE__FILE_NAME);

    dayTripEClass = createEClass(DAY_TRIP);
    createEReference(dayTripEClass, DAY_TRIP__ELEMENTS);

    documentEClass = createEClass(DOCUMENT);
    createEReference(documentEClass, DOCUMENT__DOCUMENT_REFERENCE);

    travelCategoryEClass = createEClass(TRAVEL_CATEGORY);
    createEReference(travelCategoryEClass, TRAVEL_CATEGORY__TRAVELS);

    travelEClass = createEClass(TRAVEL);
    createEAttribute(travelEClass, TRAVEL__TITLE);
    createEOperation(travelEClass, TRAVEL___GET_ID);
    createEOperation(travelEClass, TRAVEL___GET_ALL_FILE_REFERENCES);

    // Create data types
    eLocationCategoryEDataType = createEDataType(ELOCATION_CATEGORY);
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
    if (isInitialized)
      return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    TypesPackage theTypesPackage = (TypesPackage) EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);
    XMLTypePackage theXMLTypePackage = (XMLTypePackage) EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    vacationEClass.getESuperTypes().add(theTypesPackage.getEvent());
    vacationEClass.getESuperTypes().add(this.getTravel());
    locationEClass.getESuperTypes().add(this.getVacationElement());
    textEClass.getESuperTypes().add(this.getVacationElement());
    dayEClass.getESuperTypes().add(this.getVacationElement());
    pictureEClass.getESuperTypes().add(this.getVacationElement());
    gpxTrackEClass.getESuperTypes().add(this.getVacationElement());
    mapImageEClass.getESuperTypes().add(this.getVacationElement());
    dayTripEClass.getESuperTypes().add(this.getTravel());
    documentEClass.getESuperTypes().add(this.getVacationElement());
    travelEClass.getESuperTypes().add(theTypesPackage.getEvent());

    // Initialize classes, features, and operations; add parameters
    initEClass(vacationsEClass, Vacations.class, "Vacations", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getVacations_Vacations(), this.getVacation(), null, "vacations", null, 0, -1, Vacations.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEReference(getVacations_Home(), this.getLocation(), null, "home", null, 0, 1, Vacations.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEAttribute(getVacations_Tips(), ecorePackage.getEString(), "tips", null, 0, 1, Vacations.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVacations_DayTrips(), this.getDayTrip(), null, "dayTrips", null, 0, -1, Vacations.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEReference(getVacations_TravelCategories(), this.getTravelCategory(), null, "travelCategories", null, 0, -1,
        Vacations.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
        IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    EOperation op = initEOperation(getVacations__FindVacation__FlexDate_String(), this.getVacation(), "findVacation", 0,
        1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, theTypesPackage.getEFlexDate(), "date", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "title", 0, 1, IS_UNIQUE, IS_ORDERED);

    op = initEOperation(getVacations__AddVacation__Vacation(), null, "addVacation", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, this.getVacation(), "vacation", 0, 1, IS_UNIQUE, IS_ORDERED);

    op = initEOperation(getVacations__FindVacation__FlexDate(), this.getVacation(), "findVacation", 0, 1, IS_UNIQUE,
        IS_ORDERED);
    addEParameter(op, theTypesPackage.getEFlexDate(), "date", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEOperation(getVacations__GetTravels(), this.getTravel(), "getTravels", 0, -1, IS_UNIQUE, IS_ORDERED);

    initEClass(vacationEClass, Vacation.class, "Vacation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getVacation_EndDate(), theTypesPackage.getEFlexDate(), "endDate", null, 0, 1, Vacation.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVacation_Documents(), theTypesPackage.getFileReference(), null, "documents", null, 0, -1,
        Vacation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
        IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVacation_Pictures(), ecorePackage.getEString(), "pictures", null, 0, 1, Vacation.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVacation_Elements(), this.getVacationElement(), null, "elements", null, 0, -1, Vacation.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);

    op = initEOperation(getVacation__FindDocument__String(), theTypesPackage.getFileReference(), "findDocument", 0, 1,
        IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "documentPath", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEOperation(getVacation__GetAllReferencedFiles(), theXMLTypePackage.getString(), "getAllReferencedFiles", 0, -1,
        IS_UNIQUE, IS_ORDERED);

    op = initEOperation(getVacation__GetDayNr__VacationElement(), ecorePackage.getEIntegerObject(), "getDayNr", 0, 1,
        IS_UNIQUE, IS_ORDERED);
    addEParameter(op, this.getVacationElement(), "vacationElement", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(locationEClass, Location.class, "Location", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getLocation_LocationCategory(), this.getELocationCategory(), "locationCategory", "DEFAULT_POI", 0, 1,
        Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEAttribute(getLocation_Country(), ecorePackage.getEString(), "country", null, 0, 1, Location.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocation_City(), ecorePackage.getEString(), "city", null, 0, 1, Location.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocation_Street(), ecorePackage.getEString(), "street", null, 0, 1, Location.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocation_HouseNumber(), ecorePackage.getEString(), "houseNumber", null, 0, 1, Location.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocation_Latitude(), ecorePackage.getEDoubleObject(), "latitude", null, 0, 1, Location.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocation_Longitude(), ecorePackage.getEDoubleObject(), "longitude", null, 0, 1, Location.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocation_Name(), ecorePackage.getEString(), "name", null, 0, 1, Location.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocation_WebSite(), ecorePackage.getEString(), "webSite", null, 0, 1, Location.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocation_Description(), ecorePackage.getEString(), "description", null, 0, 1, Location.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocation_Duration(), ecorePackage.getEIntegerObject(), "duration", null, 0, 1, Location.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocation_StartDate(), theTypesPackage.getEFlexDate(), "startDate", null, 0, 1, Location.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocation_EndDate(), theTypesPackage.getEFlexDate(), "endDate", null, 0, 1, Location.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getLocation_Boundingbox(), this.getBoundingBox(), null, "boundingbox", null, 0, 1, Location.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEReference(getLocation_Boundaries(), this.getBoundary(), null, "boundaries", null, 0, -1, Location.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocation_ReferenceOnly(), ecorePackage.getEBoolean(), "referenceOnly", null, 0, 1, Location.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocation_StayedAtThisLocation(), theXMLTypePackage.getBoolean(), "stayedAtThisLocation", "false",
        0, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);

    initEClass(vacationElementEClass, VacationElement.class, "VacationElement", IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEReference(getVacationElement_Children(), this.getVacationElement(), null, "children", null, 0, -1,
        VacationElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
        !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEOperation(getVacationElement__GetDayNr(), ecorePackage.getEIntegerObject(), "getDayNr", 0, 1, IS_UNIQUE,
        IS_ORDERED);

    initEOperation(getVacationElement__GetVacation(), this.getVacation(), "getVacation", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEOperation(getVacationElement__GetDayTrip(), this.getDayTrip(), "getDayTrip", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEOperation(getVacationElement__GetDay(), this.getDay(), "getDay", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(textEClass, Text.class, "Text", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getText_Text(), ecorePackage.getEString(), "text", null, 0, 1, Text.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(dayEClass, Day.class, "Day", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDay_Days(), ecorePackage.getEIntegerObject(), "days", "1", 0, 1, Day.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDay_Title(), ecorePackage.getEString(), "title", null, 0, 1, Day.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEOperation(getDay__GetDate(), ecorePackage.getEDate(), "getDate", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(pictureEClass, Picture.class, "Picture", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getPicture_PictureReference(), theTypesPackage.getFileReference(), null, "pictureReference", null, 0,
        1, Picture.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, IS_UNSETTABLE,
        IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(gpxTrackEClass, GPXTrack.class, "GPXTrack", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getGPXTrack_TrackReference(), theTypesPackage.getFileReference(), null, "trackReference", null, 0, 1,
        GPXTrack.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, IS_UNSETTABLE,
        IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(boundingBoxEClass, BoundingBox.class, "BoundingBox", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getBoundingBox_West(), theXMLTypePackage.getDoubleObject(), "west", null, 0, 1, BoundingBox.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getBoundingBox_North(), theXMLTypePackage.getDoubleObject(), "north", null, 0, 1, BoundingBox.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getBoundingBox_East(), theXMLTypePackage.getDoubleObject(), "east", null, 0, 1, BoundingBox.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getBoundingBox_South(), theXMLTypePackage.getDoubleObject(), "south", null, 0, 1, BoundingBox.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEOperation(getBoundingBox__IsValid(), ecorePackage.getEBoolean(), "isValid", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(boundaryEClass, Boundary.class, "Boundary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getBoundary_Points(), theTypesPackage.getEWGS84Coordinates(), "points", null, 0, -1, Boundary.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(mapImageEClass, MapImage.class, "MapImage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getMapImage_Title(), ecorePackage.getEString(), "title", null, 0, 1, MapImage.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMapImage_ImageWidth(), ecorePackage.getEDoubleObject(), "imageWidth", null, 1, 1, MapImage.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMapImage_ImageHeight(), ecorePackage.getEDoubleObject(), "imageHeight", null, 1, 1,
        MapImage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEAttribute(getMapImage_Zoom(), ecorePackage.getEDoubleObject(), "zoom", null, 1, 1, MapImage.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMapImage_CenterLatitude(), ecorePackage.getEDoubleObject(), "centerLatitude", null, 1, 1,
        MapImage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEAttribute(getMapImage_CenterLongitude(), ecorePackage.getEDoubleObject(), "centerLongitude", null, 1, 1,
        MapImage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEAttribute(getMapImage_FileName(), ecorePackage.getEString(), "fileName", null, 1, 1, MapImage.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(dayTripEClass, DayTrip.class, "DayTrip", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getDayTrip_Elements(), this.getVacationElement(), null, "elements", null, 0, -1, DayTrip.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);

    initEClass(documentEClass, Document.class, "Document", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getDocument_DocumentReference(), theTypesPackage.getFileReference(), null, "documentReference", null,
        0, 1, Document.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
        IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(travelCategoryEClass, TravelCategory.class, "TravelCategory", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEReference(getTravelCategory_Travels(), this.getTravel(), null, "travels", null, 0, -1, TravelCategory.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);

    initEClass(travelEClass, Travel.class, "Travel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getTravel_Title(), ecorePackage.getEString(), "title", null, 0, 1, Travel.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEOperation(getTravel__GetId(), ecorePackage.getEString(), "getId", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEOperation(getTravel__GetAllFileReferences(), theTypesPackage.getFileReference(), "getAllFileReferences", 0, -1,
        IS_UNIQUE, !IS_ORDERED);

    // Initialize data types
    initEDataType(eLocationCategoryEDataType, LocationCategory.class, "ELocationCategory", IS_SERIALIZABLE,
        !IS_GENERATED_INSTANCE_CLASS);

    // Create resource
    createResource(eNS_URI);
  }

} //VacationsPackageImpl
