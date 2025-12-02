/**
 */
package goedegep.travels.model.impl;

import goedegep.poi.app.LocationCategory;

import goedegep.travels.model.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
public class TravelsFactoryImpl extends EFactoryImpl implements TravelsFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static TravelsFactory init() {
    try {
      TravelsFactory theTravelsFactory = (TravelsFactory) EPackage.Registry.INSTANCE
          .getEFactory(TravelsPackage.eNS_URI);
      if (theTravelsFactory != null) {
        return theTravelsFactory;
      }
    } catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new TravelsFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TravelsFactoryImpl() {
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
    case TravelsPackage.VACATIONS:
      return createVacations();
    case TravelsPackage.VACATION:
      return createVacation();
    case TravelsPackage.LOCATION:
      return createLocation();
    case TravelsPackage.TEXT:
      return createText();
    case TravelsPackage.DAY:
      return createDay();
    case TravelsPackage.PICTURE:
      return createPicture();
    case TravelsPackage.GPX_TRACK:
      return createGPXTrack();
    case TravelsPackage.BOUNDING_BOX:
      return createBoundingBox();
    case TravelsPackage.BOUNDARY:
      return createBoundary();
    case TravelsPackage.MAP_IMAGE:
      return createMapImage();
    case TravelsPackage.DAY_TRIP:
      return createDayTrip();
    case TravelsPackage.DOCUMENT:
      return createDocument();
    case TravelsPackage.TRAVEL_CATEGORY:
      return createTravelCategory();
    case TravelsPackage.TRAVEL:
      return createTravel();
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
  public Object createFromString(EDataType eDataType, String initialValue) {
    switch (eDataType.getClassifierID()) {
    case TravelsPackage.ELOCATION_CATEGORY:
      return createELocationCategoryFromString(eDataType, initialValue);
    default:
      throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String convertToString(EDataType eDataType, Object instanceValue) {
    switch (eDataType.getClassifierID()) {
    case TravelsPackage.ELOCATION_CATEGORY:
      return convertELocationCategoryToString(eDataType, instanceValue);
    default:
      throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
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
  public LocationCategory createELocationCategoryFromString(EDataType eDataType, String initialValue) {
    return (LocationCategory) super.createFromString(eDataType, initialValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertELocationCategoryToString(EDataType eDataType, Object instanceValue) {
    return super.convertToString(eDataType, instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TravelsPackage getTravelsPackage() {
    return (TravelsPackage) getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static TravelsPackage getPackage() {
    return TravelsPackage.eINSTANCE;
  }

} //TravelsFactoryImpl
