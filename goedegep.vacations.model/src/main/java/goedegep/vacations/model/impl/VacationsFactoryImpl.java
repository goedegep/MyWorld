/**
 */
package goedegep.vacations.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import goedegep.vacations.model.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class VacationsFactoryImpl extends EFactoryImpl implements VacationsFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static VacationsFactory init() {
    try {
      VacationsFactory theVacationsFactory = (VacationsFactory) EPackage.Registry.INSTANCE
          .getEFactory(VacationsPackage.eNS_URI);
      if (theVacationsFactory != null) {
        return theVacationsFactory;
      }
    } catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new VacationsFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VacationsFactoryImpl() {
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
    case VacationsPackage.VACATIONS:
      return createVacations();
    case VacationsPackage.VACATION:
      return createVacation();
    case VacationsPackage.LOCATION:
      return createLocation();
    case VacationsPackage.TEXT:
      return createText();
    case VacationsPackage.DAY:
      return createDay();
    case VacationsPackage.PICTURE:
      return createPicture();
    case VacationsPackage.GPX_TRACK:
      return createGPXTrack();
    case VacationsPackage.BOUNDING_BOX:
      return createBoundingBox();
    case VacationsPackage.BOUNDARY:
      return createBoundary();
    case VacationsPackage.MAP_IMAGE:
      return createMapImage();
    case VacationsPackage.DAY_TRIP:
      return createDayTrip();
    case VacationsPackage.DOCUMENT:
      return createDocument();
    case VacationsPackage.TRAVEL_CATEGORY:
      return createTravelCategory();
    case VacationsPackage.TRAVEL:
      return createTravel();
    case VacationsPackage.TRAVEL_CATEGORIES:
      return createTravelCategories();
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
  public Vacations createVacations() {
    VacationsImpl vacations = new VacationsImpl();
    return vacations;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Vacation createVacation() {
    VacationImpl vacation = new VacationImpl();
    return vacation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Location createLocation() {
    LocationImpl location = new LocationImpl();
    return location;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Text createText() {
    TextImpl text = new TextImpl();
    return text;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Day createDay() {
    DayImpl day = new DayImpl();
    return day;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Picture createPicture() {
    PictureImpl picture = new PictureImpl();
    return picture;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public GPXTrack createGPXTrack() {
    GPXTrackImpl gpxTrack = new GPXTrackImpl();
    return gpxTrack;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public BoundingBox createBoundingBox() {
    BoundingBoxImpl boundingBox = new BoundingBoxImpl();
    return boundingBox;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Boundary createBoundary() {
    BoundaryImpl boundary = new BoundaryImpl();
    return boundary;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public MapImage createMapImage() {
    MapImageImpl mapImage = new MapImageImpl();
    return mapImage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public DayTrip createDayTrip() {
    DayTripImpl dayTrip = new DayTripImpl();
    return dayTrip;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Document createDocument() {
    DocumentImpl document = new DocumentImpl();
    return document;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TravelCategory createTravelCategory() {
    TravelCategoryImpl travelCategory = new TravelCategoryImpl();
    return travelCategory;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Travel createTravel() {
    TravelImpl travel = new TravelImpl();
    return travel;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TravelCategories createTravelCategories() {
    TravelCategoriesImpl travelCategories = new TravelCategoriesImpl();
    return travelCategories;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public VacationsPackage getVacationsPackage() {
    return (VacationsPackage) getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static VacationsPackage getPackage() {
    return VacationsPackage.eINSTANCE;
  }

} //VacationsFactoryImpl
