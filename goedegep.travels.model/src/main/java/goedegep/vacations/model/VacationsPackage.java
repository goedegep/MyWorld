/**
 */
package goedegep.vacations.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import goedegep.types.model.TypesPackage;

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
 * <!-- begin-model-doc -->
 * Een vakantie is een Event, waarbij de 'date' de vertrekdatum is.
 * De 'notes' bevatten de algemene informatie over de vakantie.
 * <!-- end-model-doc -->
 * @see goedegep.vacations.model.VacationsFactory
 * @model kind="package"
 * @generated
 */
public interface VacationsPackage extends EPackage {
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
  String eNS_URI = "http://goedegep.vacations/model";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "vacations";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  VacationsPackage eINSTANCE = goedegep.vacations.model.impl.VacationsPackageImpl.init();

  /**
   * The meta object id for the '{@link goedegep.vacations.model.impl.VacationsImpl <em>Vacations</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.model.impl.VacationsImpl
   * @see goedegep.vacations.model.impl.VacationsPackageImpl#getVacations()
   * @generated
   */
  int VACATIONS = 0;

  /**
   * The feature id for the '<em><b>Vacations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATIONS__VACATIONS = 0;

  /**
   * The feature id for the '<em><b>Home</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATIONS__HOME = 1;

  /**
   * The feature id for the '<em><b>Tips</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATIONS__TIPS = 2;

  /**
   * The feature id for the '<em><b>Day Trips</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATIONS__DAY_TRIPS = 3;

  /**
   * The feature id for the '<em><b>Travelcategories</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATIONS__TRAVELCATEGORIES = 4;

  /**
   * The number of structural features of the '<em>Vacations</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATIONS_FEATURE_COUNT = 5;

  /**
   * The operation id for the '<em>Find Vacation</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATIONS___FIND_VACATION__FLEXDATE_STRING = 0;

  /**
   * The operation id for the '<em>Add Vacation</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATIONS___ADD_VACATION__VACATION = 1;

  /**
   * The operation id for the '<em>Find Vacation</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATIONS___FIND_VACATION__FLEXDATE = 2;

  /**
   * The number of operations of the '<em>Vacations</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATIONS_OPERATION_COUNT = 3;

  /**
   * The meta object id for the '{@link goedegep.vacations.model.impl.VacationImpl <em>Vacation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.model.impl.VacationImpl
   * @see goedegep.vacations.model.impl.VacationsPackageImpl#getVacation()
   * @generated
   */
  int VACATION = 1;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION__DATE = TypesPackage.EVENT__DATE;

  /**
   * The feature id for the '<em><b>Notes</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION__NOTES = TypesPackage.EVENT__NOTES;

  /**
   * The feature id for the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION__TITLE = TypesPackage.EVENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>End Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION__END_DATE = TypesPackage.EVENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Documents</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION__DOCUMENTS = TypesPackage.EVENT_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Pictures</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION__PICTURES = TypesPackage.EVENT_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Elements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION__ELEMENTS = TypesPackage.EVENT_FEATURE_COUNT + 4;

  /**
   * The number of structural features of the '<em>Vacation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_FEATURE_COUNT = TypesPackage.EVENT_FEATURE_COUNT + 5;

  /**
   * The operation id for the '<em>Get Id</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION___GET_ID = TypesPackage.EVENT_OPERATION_COUNT + 0;

  /**
   * The operation id for the '<em>Get All File References</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION___GET_ALL_FILE_REFERENCES = TypesPackage.EVENT_OPERATION_COUNT + 1;

  /**
   * The operation id for the '<em>Find Document</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION___FIND_DOCUMENT__STRING = TypesPackage.EVENT_OPERATION_COUNT + 2;

  /**
   * The operation id for the '<em>Get All Referenced Files</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION___GET_ALL_REFERENCED_FILES = TypesPackage.EVENT_OPERATION_COUNT + 3;

  /**
   * The operation id for the '<em>Get Day Nr</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION___GET_DAY_NR__VACATIONELEMENT = TypesPackage.EVENT_OPERATION_COUNT + 4;

  /**
   * The number of operations of the '<em>Vacation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_OPERATION_COUNT = TypesPackage.EVENT_OPERATION_COUNT + 5;

  /**
   * The meta object id for the '{@link goedegep.vacations.model.impl.VacationElementImpl <em>Vacation Element</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.model.impl.VacationElementImpl
   * @see goedegep.vacations.model.impl.VacationsPackageImpl#getVacationElement()
   * @generated
   */
  int VACATION_ELEMENT = 3;

  /**
   * The feature id for the '<em><b>Children</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_ELEMENT__CHILDREN = 0;

  /**
   * The number of structural features of the '<em>Vacation Element</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_ELEMENT_FEATURE_COUNT = 1;

  /**
   * The operation id for the '<em>Get Day Nr</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_ELEMENT___GET_DAY_NR = 0;

  /**
   * The operation id for the '<em>Get Vacation</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_ELEMENT___GET_VACATION = 1;

  /**
   * The operation id for the '<em>Get Day Trip</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_ELEMENT___GET_DAY_TRIP = 2;

  /**
   * The operation id for the '<em>Get Day</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_ELEMENT___GET_DAY = 3;

  /**
   * The number of operations of the '<em>Vacation Element</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_ELEMENT_OPERATION_COUNT = 4;

  /**
   * The meta object id for the '{@link goedegep.vacations.model.impl.LocationImpl <em>Location</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.model.impl.LocationImpl
   * @see goedegep.vacations.model.impl.VacationsPackageImpl#getLocation()
   * @generated
   */
  int LOCATION = 2;

  /**
   * The feature id for the '<em><b>Children</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION__CHILDREN = VACATION_ELEMENT__CHILDREN;

  /**
   * The feature id for the '<em><b>Location Category</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION__LOCATION_CATEGORY = VACATION_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Country</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION__COUNTRY = VACATION_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>City</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION__CITY = VACATION_ELEMENT_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Street</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION__STREET = VACATION_ELEMENT_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>House Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION__HOUSE_NUMBER = VACATION_ELEMENT_FEATURE_COUNT + 4;

  /**
   * The feature id for the '<em><b>Latitude</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION__LATITUDE = VACATION_ELEMENT_FEATURE_COUNT + 5;

  /**
   * The feature id for the '<em><b>Longitude</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION__LONGITUDE = VACATION_ELEMENT_FEATURE_COUNT + 6;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION__NAME = VACATION_ELEMENT_FEATURE_COUNT + 7;

  /**
   * The feature id for the '<em><b>Web Site</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION__WEB_SITE = VACATION_ELEMENT_FEATURE_COUNT + 8;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION__DESCRIPTION = VACATION_ELEMENT_FEATURE_COUNT + 9;

  /**
   * The feature id for the '<em><b>Duration</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION__DURATION = VACATION_ELEMENT_FEATURE_COUNT + 10;

  /**
   * The feature id for the '<em><b>Start Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION__START_DATE = VACATION_ELEMENT_FEATURE_COUNT + 11;

  /**
   * The feature id for the '<em><b>End Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION__END_DATE = VACATION_ELEMENT_FEATURE_COUNT + 12;

  /**
   * The feature id for the '<em><b>Boundingbox</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION__BOUNDINGBOX = VACATION_ELEMENT_FEATURE_COUNT + 13;

  /**
   * The feature id for the '<em><b>Boundaries</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION__BOUNDARIES = VACATION_ELEMENT_FEATURE_COUNT + 14;

  /**
   * The feature id for the '<em><b>Reference Only</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION__REFERENCE_ONLY = VACATION_ELEMENT_FEATURE_COUNT + 15;

  /**
   * The feature id for the '<em><b>Stayed At This Location</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION__STAYED_AT_THIS_LOCATION = VACATION_ELEMENT_FEATURE_COUNT + 16;

  /**
   * The number of structural features of the '<em>Location</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION_FEATURE_COUNT = VACATION_ELEMENT_FEATURE_COUNT + 17;

  /**
   * The operation id for the '<em>Get Day Nr</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION___GET_DAY_NR = VACATION_ELEMENT___GET_DAY_NR;

  /**
   * The operation id for the '<em>Get Vacation</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION___GET_VACATION = VACATION_ELEMENT___GET_VACATION;

  /**
   * The operation id for the '<em>Get Day Trip</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION___GET_DAY_TRIP = VACATION_ELEMENT___GET_DAY_TRIP;

  /**
   * The operation id for the '<em>Get Day</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION___GET_DAY = VACATION_ELEMENT___GET_DAY;

  /**
   * The number of operations of the '<em>Location</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCATION_OPERATION_COUNT = VACATION_ELEMENT_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.vacations.model.impl.TextImpl <em>Text</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.model.impl.TextImpl
   * @see goedegep.vacations.model.impl.VacationsPackageImpl#getText()
   * @generated
   */
  int TEXT = 4;

  /**
   * The feature id for the '<em><b>Children</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TEXT__CHILDREN = VACATION_ELEMENT__CHILDREN;

  /**
   * The feature id for the '<em><b>Text</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TEXT__TEXT = VACATION_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Text</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TEXT_FEATURE_COUNT = VACATION_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The operation id for the '<em>Get Day Nr</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TEXT___GET_DAY_NR = VACATION_ELEMENT___GET_DAY_NR;

  /**
   * The operation id for the '<em>Get Vacation</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TEXT___GET_VACATION = VACATION_ELEMENT___GET_VACATION;

  /**
   * The operation id for the '<em>Get Day Trip</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TEXT___GET_DAY_TRIP = VACATION_ELEMENT___GET_DAY_TRIP;

  /**
   * The operation id for the '<em>Get Day</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TEXT___GET_DAY = VACATION_ELEMENT___GET_DAY;

  /**
   * The number of operations of the '<em>Text</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TEXT_OPERATION_COUNT = VACATION_ELEMENT_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.vacations.model.impl.DayImpl <em>Day</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.model.impl.DayImpl
   * @see goedegep.vacations.model.impl.VacationsPackageImpl#getDay()
   * @generated
   */
  int DAY = 5;

  /**
   * The feature id for the '<em><b>Children</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DAY__CHILDREN = VACATION_ELEMENT__CHILDREN;

  /**
   * The feature id for the '<em><b>Days</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DAY__DAYS = VACATION_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DAY__TITLE = VACATION_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Day</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DAY_FEATURE_COUNT = VACATION_ELEMENT_FEATURE_COUNT + 2;

  /**
   * The operation id for the '<em>Get Day Nr</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DAY___GET_DAY_NR = VACATION_ELEMENT___GET_DAY_NR;

  /**
   * The operation id for the '<em>Get Vacation</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DAY___GET_VACATION = VACATION_ELEMENT___GET_VACATION;

  /**
   * The operation id for the '<em>Get Day Trip</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DAY___GET_DAY_TRIP = VACATION_ELEMENT___GET_DAY_TRIP;

  /**
   * The operation id for the '<em>Get Day</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DAY___GET_DAY = VACATION_ELEMENT___GET_DAY;

  /**
   * The operation id for the '<em>Get Date</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DAY___GET_DATE = VACATION_ELEMENT_OPERATION_COUNT + 0;

  /**
   * The number of operations of the '<em>Day</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DAY_OPERATION_COUNT = VACATION_ELEMENT_OPERATION_COUNT + 1;

  /**
   * The meta object id for the '{@link goedegep.vacations.model.impl.PictureImpl <em>Picture</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.model.impl.PictureImpl
   * @see goedegep.vacations.model.impl.VacationsPackageImpl#getPicture()
   * @generated
   */
  int PICTURE = 6;

  /**
   * The feature id for the '<em><b>Children</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PICTURE__CHILDREN = VACATION_ELEMENT__CHILDREN;

  /**
   * The feature id for the '<em><b>Picture Reference</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PICTURE__PICTURE_REFERENCE = VACATION_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Picture</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PICTURE_FEATURE_COUNT = VACATION_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The operation id for the '<em>Get Day Nr</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PICTURE___GET_DAY_NR = VACATION_ELEMENT___GET_DAY_NR;

  /**
   * The operation id for the '<em>Get Vacation</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PICTURE___GET_VACATION = VACATION_ELEMENT___GET_VACATION;

  /**
   * The operation id for the '<em>Get Day Trip</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PICTURE___GET_DAY_TRIP = VACATION_ELEMENT___GET_DAY_TRIP;

  /**
   * The operation id for the '<em>Get Day</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PICTURE___GET_DAY = VACATION_ELEMENT___GET_DAY;

  /**
   * The number of operations of the '<em>Picture</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PICTURE_OPERATION_COUNT = VACATION_ELEMENT_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.vacations.model.impl.GPXTrackImpl <em>GPX Track</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.model.impl.GPXTrackImpl
   * @see goedegep.vacations.model.impl.VacationsPackageImpl#getGPXTrack()
   * @generated
   */
  int GPX_TRACK = 7;

  /**
   * The feature id for the '<em><b>Children</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GPX_TRACK__CHILDREN = VACATION_ELEMENT__CHILDREN;

  /**
   * The feature id for the '<em><b>Track Reference</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GPX_TRACK__TRACK_REFERENCE = VACATION_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>GPX Track</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GPX_TRACK_FEATURE_COUNT = VACATION_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The operation id for the '<em>Get Day Nr</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GPX_TRACK___GET_DAY_NR = VACATION_ELEMENT___GET_DAY_NR;

  /**
   * The operation id for the '<em>Get Vacation</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GPX_TRACK___GET_VACATION = VACATION_ELEMENT___GET_VACATION;

  /**
   * The operation id for the '<em>Get Day Trip</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GPX_TRACK___GET_DAY_TRIP = VACATION_ELEMENT___GET_DAY_TRIP;

  /**
   * The operation id for the '<em>Get Day</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GPX_TRACK___GET_DAY = VACATION_ELEMENT___GET_DAY;

  /**
   * The number of operations of the '<em>GPX Track</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GPX_TRACK_OPERATION_COUNT = VACATION_ELEMENT_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.vacations.model.impl.BoundingBoxImpl <em>Bounding Box</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.model.impl.BoundingBoxImpl
   * @see goedegep.vacations.model.impl.VacationsPackageImpl#getBoundingBox()
   * @generated
   */
  int BOUNDING_BOX = 8;

  /**
   * The feature id for the '<em><b>West</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOUNDING_BOX__WEST = 0;

  /**
   * The feature id for the '<em><b>North</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOUNDING_BOX__NORTH = 1;

  /**
   * The feature id for the '<em><b>East</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOUNDING_BOX__EAST = 2;

  /**
   * The feature id for the '<em><b>South</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOUNDING_BOX__SOUTH = 3;

  /**
   * The number of structural features of the '<em>Bounding Box</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOUNDING_BOX_FEATURE_COUNT = 4;

  /**
   * The operation id for the '<em>Is Valid</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOUNDING_BOX___IS_VALID = 0;

  /**
   * The number of operations of the '<em>Bounding Box</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOUNDING_BOX_OPERATION_COUNT = 1;

  /**
   * The meta object id for the '{@link goedegep.vacations.model.impl.BoundaryImpl <em>Boundary</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.model.impl.BoundaryImpl
   * @see goedegep.vacations.model.impl.VacationsPackageImpl#getBoundary()
   * @generated
   */
  int BOUNDARY = 9;

  /**
   * The feature id for the '<em><b>Points</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOUNDARY__POINTS = 0;

  /**
   * The number of structural features of the '<em>Boundary</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOUNDARY_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Boundary</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOUNDARY_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.vacations.model.impl.MapImageImpl <em>Map Image</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.model.impl.MapImageImpl
   * @see goedegep.vacations.model.impl.VacationsPackageImpl#getMapImage()
   * @generated
   */
  int MAP_IMAGE = 10;

  /**
   * The feature id for the '<em><b>Children</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAP_IMAGE__CHILDREN = VACATION_ELEMENT__CHILDREN;

  /**
   * The feature id for the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAP_IMAGE__TITLE = VACATION_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Image Width</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAP_IMAGE__IMAGE_WIDTH = VACATION_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Image Height</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAP_IMAGE__IMAGE_HEIGHT = VACATION_ELEMENT_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Zoom</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAP_IMAGE__ZOOM = VACATION_ELEMENT_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Center Latitude</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAP_IMAGE__CENTER_LATITUDE = VACATION_ELEMENT_FEATURE_COUNT + 4;

  /**
   * The feature id for the '<em><b>Center Longitude</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAP_IMAGE__CENTER_LONGITUDE = VACATION_ELEMENT_FEATURE_COUNT + 5;

  /**
   * The feature id for the '<em><b>File Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAP_IMAGE__FILE_NAME = VACATION_ELEMENT_FEATURE_COUNT + 6;

  /**
   * The number of structural features of the '<em>Map Image</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAP_IMAGE_FEATURE_COUNT = VACATION_ELEMENT_FEATURE_COUNT + 7;

  /**
   * The operation id for the '<em>Get Day Nr</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAP_IMAGE___GET_DAY_NR = VACATION_ELEMENT___GET_DAY_NR;

  /**
   * The operation id for the '<em>Get Vacation</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAP_IMAGE___GET_VACATION = VACATION_ELEMENT___GET_VACATION;

  /**
   * The operation id for the '<em>Get Day Trip</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAP_IMAGE___GET_DAY_TRIP = VACATION_ELEMENT___GET_DAY_TRIP;

  /**
   * The operation id for the '<em>Get Day</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAP_IMAGE___GET_DAY = VACATION_ELEMENT___GET_DAY;

  /**
   * The number of operations of the '<em>Map Image</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAP_IMAGE_OPERATION_COUNT = VACATION_ELEMENT_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.vacations.model.impl.TravelImpl <em>Travel</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.model.impl.TravelImpl
   * @see goedegep.vacations.model.impl.VacationsPackageImpl#getTravel()
   * @generated
   */
  int TRAVEL = 14;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRAVEL__DATE = TypesPackage.EVENT__DATE;

  /**
   * The feature id for the '<em><b>Notes</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRAVEL__NOTES = TypesPackage.EVENT__NOTES;

  /**
   * The feature id for the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRAVEL__TITLE = TypesPackage.EVENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Travel</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRAVEL_FEATURE_COUNT = TypesPackage.EVENT_FEATURE_COUNT + 1;

  /**
   * The operation id for the '<em>Get Id</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRAVEL___GET_ID = TypesPackage.EVENT_OPERATION_COUNT + 0;

  /**
   * The operation id for the '<em>Get All File References</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRAVEL___GET_ALL_FILE_REFERENCES = TypesPackage.EVENT_OPERATION_COUNT + 1;

  /**
   * The number of operations of the '<em>Travel</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRAVEL_OPERATION_COUNT = TypesPackage.EVENT_OPERATION_COUNT + 2;

  /**
   * The meta object id for the '{@link goedegep.vacations.model.impl.DayTripImpl <em>Day Trip</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.model.impl.DayTripImpl
   * @see goedegep.vacations.model.impl.VacationsPackageImpl#getDayTrip()
   * @generated
   */
  int DAY_TRIP = 11;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DAY_TRIP__DATE = TRAVEL__DATE;

  /**
   * The feature id for the '<em><b>Notes</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DAY_TRIP__NOTES = TRAVEL__NOTES;

  /**
   * The meta object id for the '{@link goedegep.vacations.model.impl.DocumentImpl <em>Document</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.model.impl.DocumentImpl
   * @see goedegep.vacations.model.impl.VacationsPackageImpl#getDocument()
   * @generated
   */
  int DOCUMENT = 12;

  /**
   * The meta object id for the '{@link goedegep.vacations.model.impl.TravelCategoryImpl <em>Travel Category</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.model.impl.TravelCategoryImpl
   * @see goedegep.vacations.model.impl.VacationsPackageImpl#getTravelCategory()
   * @generated
   */
  int TRAVEL_CATEGORY = 13;

  /**
   * The meta object id for the '{@link goedegep.vacations.model.impl.TravelCategoriesImpl <em>Travel Categories</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.model.impl.TravelCategoriesImpl
   * @see goedegep.vacations.model.impl.VacationsPackageImpl#getTravelCategories()
   * @generated
   */
  int TRAVEL_CATEGORIES = 15;

  /**
   * The feature id for the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DAY_TRIP__TITLE = TRAVEL__TITLE;

  /**
   * The feature id for the '<em><b>Elements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DAY_TRIP__ELEMENTS = TRAVEL_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Day Trip</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DAY_TRIP_FEATURE_COUNT = TRAVEL_FEATURE_COUNT + 1;

  /**
   * The operation id for the '<em>Get Id</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DAY_TRIP___GET_ID = TRAVEL___GET_ID;

  /**
   * The operation id for the '<em>Get All File References</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DAY_TRIP___GET_ALL_FILE_REFERENCES = TRAVEL___GET_ALL_FILE_REFERENCES;

  /**
   * The number of operations of the '<em>Day Trip</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DAY_TRIP_OPERATION_COUNT = TRAVEL_OPERATION_COUNT + 0;

  /**
   * The feature id for the '<em><b>Children</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT__CHILDREN = VACATION_ELEMENT__CHILDREN;

  /**
   * The feature id for the '<em><b>Document Reference</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT__DOCUMENT_REFERENCE = VACATION_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Document</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_FEATURE_COUNT = VACATION_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The operation id for the '<em>Get Day Nr</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT___GET_DAY_NR = VACATION_ELEMENT___GET_DAY_NR;

  /**
   * The operation id for the '<em>Get Vacation</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT___GET_VACATION = VACATION_ELEMENT___GET_VACATION;

  /**
   * The operation id for the '<em>Get Day Trip</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT___GET_DAY_TRIP = VACATION_ELEMENT___GET_DAY_TRIP;

  /**
   * The operation id for the '<em>Get Day</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT___GET_DAY = VACATION_ELEMENT___GET_DAY;

  /**
   * The number of operations of the '<em>Document</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_OPERATION_COUNT = VACATION_ELEMENT_OPERATION_COUNT + 0;

  /**
   * The feature id for the '<em><b>Travels</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRAVEL_CATEGORY__TRAVELS = 0;

  /**
   * The number of structural features of the '<em>Travel Category</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRAVEL_CATEGORY_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Travel Category</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRAVEL_CATEGORY_OPERATION_COUNT = 0;

  /**
   * The feature id for the '<em><b>Travelcategories</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRAVEL_CATEGORIES__TRAVELCATEGORIES = 0;

  /**
   * The number of structural features of the '<em>Travel Categories</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRAVEL_CATEGORIES_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Travel Categories</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRAVEL_CATEGORIES_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '<em>ELocation Category</em>' data type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.poi.app.LocationCategory
   * @see goedegep.vacations.model.impl.VacationsPackageImpl#getELocationCategory()
   * @generated
   */
  int ELOCATION_CATEGORY = 16;

  /**
   * Returns the meta object for class '{@link goedegep.vacations.model.Vacations <em>Vacations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Vacations</em>'.
   * @see goedegep.vacations.model.Vacations
   * @generated
   */
  EClass getVacations();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.vacations.model.Vacations#getVacations <em>Vacations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Vacations</em>'.
   * @see goedegep.vacations.model.Vacations#getVacations()
   * @see #getVacations()
   * @generated
   */
  EReference getVacations_Vacations();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.vacations.model.Vacations#getHome <em>Home</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Home</em>'.
   * @see goedegep.vacations.model.Vacations#getHome()
   * @see #getVacations()
   * @generated
   */
  EReference getVacations_Home();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Vacations#getTips <em>Tips</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Tips</em>'.
   * @see goedegep.vacations.model.Vacations#getTips()
   * @see #getVacations()
   * @generated
   */
  EAttribute getVacations_Tips();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.vacations.model.Vacations#getDayTrips <em>Day Trips</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Day Trips</em>'.
   * @see goedegep.vacations.model.Vacations#getDayTrips()
   * @see #getVacations()
   * @generated
   */
  EReference getVacations_DayTrips();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.vacations.model.Vacations#getTravelcategories <em>Travelcategories</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Travelcategories</em>'.
   * @see goedegep.vacations.model.Vacations#getTravelcategories()
   * @see #getVacations()
   * @generated
   */
  EReference getVacations_Travelcategories();

  /**
   * Returns the meta object for the '{@link goedegep.vacations.model.Vacations#findVacation(goedegep.util.datetime.FlexDate, java.lang.String) <em>Find Vacation</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Find Vacation</em>' operation.
   * @see goedegep.vacations.model.Vacations#findVacation(goedegep.util.datetime.FlexDate, java.lang.String)
   * @generated
   */
  EOperation getVacations__FindVacation__FlexDate_String();

  /**
   * Returns the meta object for the '{@link goedegep.vacations.model.Vacations#addVacation(goedegep.vacations.model.Vacation) <em>Add Vacation</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Add Vacation</em>' operation.
   * @see goedegep.vacations.model.Vacations#addVacation(goedegep.vacations.model.Vacation)
   * @generated
   */
  EOperation getVacations__AddVacation__Vacation();

  /**
   * Returns the meta object for the '{@link goedegep.vacations.model.Vacations#findVacation(goedegep.util.datetime.FlexDate) <em>Find Vacation</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Find Vacation</em>' operation.
   * @see goedegep.vacations.model.Vacations#findVacation(goedegep.util.datetime.FlexDate)
   * @generated
   */
  EOperation getVacations__FindVacation__FlexDate();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.model.Vacation <em>Vacation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Vacation</em>'.
   * @see goedegep.vacations.model.Vacation
   * @generated
   */
  EClass getVacation();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Vacation#getEndDate <em>End Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>End Date</em>'.
   * @see goedegep.vacations.model.Vacation#getEndDate()
   * @see #getVacation()
   * @generated
   */
  EAttribute getVacation_EndDate();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.vacations.model.Vacation#getDocuments <em>Documents</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Documents</em>'.
   * @see goedegep.vacations.model.Vacation#getDocuments()
   * @see #getVacation()
   * @generated
   */
  EReference getVacation_Documents();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Vacation#getPictures <em>Pictures</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Pictures</em>'.
   * @see goedegep.vacations.model.Vacation#getPictures()
   * @see #getVacation()
   * @generated
   */
  EAttribute getVacation_Pictures();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.vacations.model.Vacation#getElements <em>Elements</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Elements</em>'.
   * @see goedegep.vacations.model.Vacation#getElements()
   * @see #getVacation()
   * @generated
   */
  EReference getVacation_Elements();

  /**
   * Returns the meta object for the '{@link goedegep.vacations.model.Vacation#findDocument(java.lang.String) <em>Find Document</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Find Document</em>' operation.
   * @see goedegep.vacations.model.Vacation#findDocument(java.lang.String)
   * @generated
   */
  EOperation getVacation__FindDocument__String();

  /**
   * Returns the meta object for the '{@link goedegep.vacations.model.Vacation#getAllReferencedFiles() <em>Get All Referenced Files</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get All Referenced Files</em>' operation.
   * @see goedegep.vacations.model.Vacation#getAllReferencedFiles()
   * @generated
   */
  EOperation getVacation__GetAllReferencedFiles();

  /**
   * Returns the meta object for the '{@link goedegep.vacations.model.Vacation#getDayNr(goedegep.vacations.model.VacationElement) <em>Get Day Nr</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Day Nr</em>' operation.
   * @see goedegep.vacations.model.Vacation#getDayNr(goedegep.vacations.model.VacationElement)
   * @generated
   */
  EOperation getVacation__GetDayNr__VacationElement();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.model.Location <em>Location</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Location</em>'.
   * @see goedegep.vacations.model.Location
   * @generated
   */
  EClass getLocation();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Location#getLocationCategory <em>Location Category</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Location Category</em>'.
   * @see goedegep.vacations.model.Location#getLocationCategory()
   * @see #getLocation()
   * @generated
   */
  EAttribute getLocation_LocationCategory();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Location#getCountry <em>Country</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Country</em>'.
   * @see goedegep.vacations.model.Location#getCountry()
   * @see #getLocation()
   * @generated
   */
  EAttribute getLocation_Country();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Location#getCity <em>City</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>City</em>'.
   * @see goedegep.vacations.model.Location#getCity()
   * @see #getLocation()
   * @generated
   */
  EAttribute getLocation_City();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Location#getStreet <em>Street</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Street</em>'.
   * @see goedegep.vacations.model.Location#getStreet()
   * @see #getLocation()
   * @generated
   */
  EAttribute getLocation_Street();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Location#getHouseNumber <em>House Number</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>House Number</em>'.
   * @see goedegep.vacations.model.Location#getHouseNumber()
   * @see #getLocation()
   * @generated
   */
  EAttribute getLocation_HouseNumber();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Location#getLatitude <em>Latitude</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Latitude</em>'.
   * @see goedegep.vacations.model.Location#getLatitude()
   * @see #getLocation()
   * @generated
   */
  EAttribute getLocation_Latitude();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Location#getLongitude <em>Longitude</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Longitude</em>'.
   * @see goedegep.vacations.model.Location#getLongitude()
   * @see #getLocation()
   * @generated
   */
  EAttribute getLocation_Longitude();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Location#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see goedegep.vacations.model.Location#getName()
   * @see #getLocation()
   * @generated
   */
  EAttribute getLocation_Name();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Location#getWebSite <em>Web Site</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Web Site</em>'.
   * @see goedegep.vacations.model.Location#getWebSite()
   * @see #getLocation()
   * @generated
   */
  EAttribute getLocation_WebSite();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Location#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see goedegep.vacations.model.Location#getDescription()
   * @see #getLocation()
   * @generated
   */
  EAttribute getLocation_Description();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Location#getDuration <em>Duration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Duration</em>'.
   * @see goedegep.vacations.model.Location#getDuration()
   * @see #getLocation()
   * @generated
   */
  EAttribute getLocation_Duration();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Location#getStartDate <em>Start Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Start Date</em>'.
   * @see goedegep.vacations.model.Location#getStartDate()
   * @see #getLocation()
   * @generated
   */
  EAttribute getLocation_StartDate();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Location#getEndDate <em>End Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>End Date</em>'.
   * @see goedegep.vacations.model.Location#getEndDate()
   * @see #getLocation()
   * @generated
   */
  EAttribute getLocation_EndDate();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.vacations.model.Location#getBoundingbox <em>Boundingbox</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Boundingbox</em>'.
   * @see goedegep.vacations.model.Location#getBoundingbox()
   * @see #getLocation()
   * @generated
   */
  EReference getLocation_Boundingbox();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.vacations.model.Location#getBoundaries <em>Boundaries</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Boundaries</em>'.
   * @see goedegep.vacations.model.Location#getBoundaries()
   * @see #getLocation()
   * @generated
   */
  EReference getLocation_Boundaries();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Location#isReferenceOnly <em>Reference Only</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Reference Only</em>'.
   * @see goedegep.vacations.model.Location#isReferenceOnly()
   * @see #getLocation()
   * @generated
   */
  EAttribute getLocation_ReferenceOnly();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Location#isStayedAtThisLocation <em>Stayed At This Location</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Stayed At This Location</em>'.
   * @see goedegep.vacations.model.Location#isStayedAtThisLocation()
   * @see #getLocation()
   * @generated
   */
  EAttribute getLocation_StayedAtThisLocation();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.model.VacationElement <em>Vacation Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Vacation Element</em>'.
   * @see goedegep.vacations.model.VacationElement
   * @generated
   */
  EClass getVacationElement();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.vacations.model.VacationElement#getChildren <em>Children</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Children</em>'.
   * @see goedegep.vacations.model.VacationElement#getChildren()
   * @see #getVacationElement()
   * @generated
   */
  EReference getVacationElement_Children();

  /**
   * Returns the meta object for the '{@link goedegep.vacations.model.VacationElement#getDayNr() <em>Get Day Nr</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Day Nr</em>' operation.
   * @see goedegep.vacations.model.VacationElement#getDayNr()
   * @generated
   */
  EOperation getVacationElement__GetDayNr();

  /**
   * Returns the meta object for the '{@link goedegep.vacations.model.VacationElement#getVacation() <em>Get Vacation</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Vacation</em>' operation.
   * @see goedegep.vacations.model.VacationElement#getVacation()
   * @generated
   */
  EOperation getVacationElement__GetVacation();

  /**
   * Returns the meta object for the '{@link goedegep.vacations.model.VacationElement#getDayTrip() <em>Get Day Trip</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Day Trip</em>' operation.
   * @see goedegep.vacations.model.VacationElement#getDayTrip()
   * @generated
   */
  EOperation getVacationElement__GetDayTrip();

  /**
   * Returns the meta object for the '{@link goedegep.vacations.model.VacationElement#getDay() <em>Get Day</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Day</em>' operation.
   * @see goedegep.vacations.model.VacationElement#getDay()
   * @generated
   */
  EOperation getVacationElement__GetDay();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.model.Text <em>Text</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Text</em>'.
   * @see goedegep.vacations.model.Text
   * @generated
   */
  EClass getText();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Text#getText <em>Text</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Text</em>'.
   * @see goedegep.vacations.model.Text#getText()
   * @see #getText()
   * @generated
   */
  EAttribute getText_Text();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.model.Day <em>Day</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Day</em>'.
   * @see goedegep.vacations.model.Day
   * @generated
   */
  EClass getDay();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Day#getDays <em>Days</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Days</em>'.
   * @see goedegep.vacations.model.Day#getDays()
   * @see #getDay()
   * @generated
   */
  EAttribute getDay_Days();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Day#getTitle <em>Title</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Title</em>'.
   * @see goedegep.vacations.model.Day#getTitle()
   * @see #getDay()
   * @generated
   */
  EAttribute getDay_Title();

  /**
   * Returns the meta object for the '{@link goedegep.vacations.model.Day#getDate() <em>Get Date</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Date</em>' operation.
   * @see goedegep.vacations.model.Day#getDate()
   * @generated
   */
  EOperation getDay__GetDate();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.model.Picture <em>Picture</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Picture</em>'.
   * @see goedegep.vacations.model.Picture
   * @generated
   */
  EClass getPicture();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.vacations.model.Picture#getPictureReference <em>Picture Reference</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Picture Reference</em>'.
   * @see goedegep.vacations.model.Picture#getPictureReference()
   * @see #getPicture()
   * @generated
   */
  EReference getPicture_PictureReference();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.model.GPXTrack <em>GPX Track</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>GPX Track</em>'.
   * @see goedegep.vacations.model.GPXTrack
   * @generated
   */
  EClass getGPXTrack();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.vacations.model.GPXTrack#getTrackReference <em>Track Reference</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Track Reference</em>'.
   * @see goedegep.vacations.model.GPXTrack#getTrackReference()
   * @see #getGPXTrack()
   * @generated
   */
  EReference getGPXTrack_TrackReference();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.model.BoundingBox <em>Bounding Box</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Bounding Box</em>'.
   * @see goedegep.vacations.model.BoundingBox
   * @generated
   */
  EClass getBoundingBox();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.BoundingBox#getWest <em>West</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>West</em>'.
   * @see goedegep.vacations.model.BoundingBox#getWest()
   * @see #getBoundingBox()
   * @generated
   */
  EAttribute getBoundingBox_West();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.BoundingBox#getNorth <em>North</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>North</em>'.
   * @see goedegep.vacations.model.BoundingBox#getNorth()
   * @see #getBoundingBox()
   * @generated
   */
  EAttribute getBoundingBox_North();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.BoundingBox#getEast <em>East</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>East</em>'.
   * @see goedegep.vacations.model.BoundingBox#getEast()
   * @see #getBoundingBox()
   * @generated
   */
  EAttribute getBoundingBox_East();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.BoundingBox#getSouth <em>South</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>South</em>'.
   * @see goedegep.vacations.model.BoundingBox#getSouth()
   * @see #getBoundingBox()
   * @generated
   */
  EAttribute getBoundingBox_South();

  /**
   * Returns the meta object for the '{@link goedegep.vacations.model.BoundingBox#isValid() <em>Is Valid</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Is Valid</em>' operation.
   * @see goedegep.vacations.model.BoundingBox#isValid()
   * @generated
   */
  EOperation getBoundingBox__IsValid();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.model.Boundary <em>Boundary</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Boundary</em>'.
   * @see goedegep.vacations.model.Boundary
   * @generated
   */
  EClass getBoundary();

  /**
   * Returns the meta object for the attribute list '{@link goedegep.vacations.model.Boundary#getPoints <em>Points</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Points</em>'.
   * @see goedegep.vacations.model.Boundary#getPoints()
   * @see #getBoundary()
   * @generated
   */
  EAttribute getBoundary_Points();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.model.MapImage <em>Map Image</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Map Image</em>'.
   * @see goedegep.vacations.model.MapImage
   * @generated
   */
  EClass getMapImage();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.MapImage#getTitle <em>Title</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Title</em>'.
   * @see goedegep.vacations.model.MapImage#getTitle()
   * @see #getMapImage()
   * @generated
   */
  EAttribute getMapImage_Title();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.MapImage#getImageWidth <em>Image Width</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Image Width</em>'.
   * @see goedegep.vacations.model.MapImage#getImageWidth()
   * @see #getMapImage()
   * @generated
   */
  EAttribute getMapImage_ImageWidth();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.MapImage#getImageHeight <em>Image Height</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Image Height</em>'.
   * @see goedegep.vacations.model.MapImage#getImageHeight()
   * @see #getMapImage()
   * @generated
   */
  EAttribute getMapImage_ImageHeight();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.MapImage#getZoom <em>Zoom</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Zoom</em>'.
   * @see goedegep.vacations.model.MapImage#getZoom()
   * @see #getMapImage()
   * @generated
   */
  EAttribute getMapImage_Zoom();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.MapImage#getCenterLatitude <em>Center Latitude</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Center Latitude</em>'.
   * @see goedegep.vacations.model.MapImage#getCenterLatitude()
   * @see #getMapImage()
   * @generated
   */
  EAttribute getMapImage_CenterLatitude();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.MapImage#getCenterLongitude <em>Center Longitude</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Center Longitude</em>'.
   * @see goedegep.vacations.model.MapImage#getCenterLongitude()
   * @see #getMapImage()
   * @generated
   */
  EAttribute getMapImage_CenterLongitude();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.MapImage#getFileName <em>File Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>File Name</em>'.
   * @see goedegep.vacations.model.MapImage#getFileName()
   * @see #getMapImage()
   * @generated
   */
  EAttribute getMapImage_FileName();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.model.DayTrip <em>Day Trip</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Day Trip</em>'.
   * @see goedegep.vacations.model.DayTrip
   * @generated
   */
  EClass getDayTrip();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.vacations.model.DayTrip#getElements <em>Elements</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Elements</em>'.
   * @see goedegep.vacations.model.DayTrip#getElements()
   * @see #getDayTrip()
   * @generated
   */
  EReference getDayTrip_Elements();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.model.Document <em>Document</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Document</em>'.
   * @see goedegep.vacations.model.Document
   * @generated
   */
  EClass getDocument();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.vacations.model.Document#getDocumentReference <em>Document Reference</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Document Reference</em>'.
   * @see goedegep.vacations.model.Document#getDocumentReference()
   * @see #getDocument()
   * @generated
   */
  EReference getDocument_DocumentReference();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.model.TravelCategory <em>Travel Category</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Travel Category</em>'.
   * @see goedegep.vacations.model.TravelCategory
   * @generated
   */
  EClass getTravelCategory();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.vacations.model.TravelCategory#getTravels <em>Travels</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Travels</em>'.
   * @see goedegep.vacations.model.TravelCategory#getTravels()
   * @see #getTravelCategory()
   * @generated
   */
  EReference getTravelCategory_Travels();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.model.Travel <em>Travel</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Travel</em>'.
   * @see goedegep.vacations.model.Travel
   * @generated
   */
  EClass getTravel();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.model.Travel#getTitle <em>Title</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Title</em>'.
   * @see goedegep.vacations.model.Travel#getTitle()
   * @see #getTravel()
   * @generated
   */
  EAttribute getTravel_Title();

  /**
   * Returns the meta object for the '{@link goedegep.vacations.model.Travel#getId() <em>Get Id</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Id</em>' operation.
   * @see goedegep.vacations.model.Travel#getId()
   * @generated
   */
  EOperation getTravel__GetId();

  /**
   * Returns the meta object for the '{@link goedegep.vacations.model.Travel#getAllFileReferences() <em>Get All File References</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get All File References</em>' operation.
   * @see goedegep.vacations.model.Travel#getAllFileReferences()
   * @generated
   */
  EOperation getTravel__GetAllFileReferences();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.model.TravelCategories <em>Travel Categories</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Travel Categories</em>'.
   * @see goedegep.vacations.model.TravelCategories
   * @generated
   */
  EClass getTravelCategories();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.vacations.model.TravelCategories#getTravelcategories <em>Travelcategories</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Travelcategories</em>'.
   * @see goedegep.vacations.model.TravelCategories#getTravelcategories()
   * @see #getTravelCategories()
   * @generated
   */
  EReference getTravelCategories_Travelcategories();

  /**
   * Returns the meta object for data type '{@link goedegep.poi.app.LocationCategory <em>ELocation Category</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for data type '<em>ELocation Category</em>'.
   * @see goedegep.poi.app.LocationCategory
   * @model instanceClass="goedegep.poi.app.LocationCategory"
   * @generated
   */
  EDataType getELocationCategory();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  VacationsFactory getVacationsFactory();

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
    	 * The meta object literal for the '{@link goedegep.vacations.model.impl.VacationsImpl <em>Vacations</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.vacations.model.impl.VacationsImpl
    	 * @see goedegep.vacations.model.impl.VacationsPackageImpl#getVacations()
    	 * @generated
    	 */
    EClass VACATIONS = eINSTANCE.getVacations();

    /**
    	 * The meta object literal for the '<em><b>Vacations</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference VACATIONS__VACATIONS = eINSTANCE.getVacations_Vacations();

    /**
    	 * The meta object literal for the '<em><b>Home</b></em>' containment reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference VACATIONS__HOME = eINSTANCE.getVacations_Home();

    /**
    	 * The meta object literal for the '<em><b>Tips</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute VACATIONS__TIPS = eINSTANCE.getVacations_Tips();

    /**
    	 * The meta object literal for the '<em><b>Day Trips</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference VACATIONS__DAY_TRIPS = eINSTANCE.getVacations_DayTrips();

    /**
    	 * The meta object literal for the '<em><b>Travelcategories</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference VACATIONS__TRAVELCATEGORIES = eINSTANCE.getVacations_Travelcategories();

    /**
    	 * The meta object literal for the '<em><b>Find Vacation</b></em>' operation.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation VACATIONS___FIND_VACATION__FLEXDATE_STRING = eINSTANCE.getVacations__FindVacation__FlexDate_String();

    /**
    	 * The meta object literal for the '<em><b>Add Vacation</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation VACATIONS___ADD_VACATION__VACATION = eINSTANCE.getVacations__AddVacation__Vacation();

    /**
    	 * The meta object literal for the '<em><b>Find Vacation</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation VACATIONS___FIND_VACATION__FLEXDATE = eINSTANCE.getVacations__FindVacation__FlexDate();

    /**
    	 * The meta object literal for the '{@link goedegep.vacations.model.impl.VacationImpl <em>Vacation</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.vacations.model.impl.VacationImpl
    	 * @see goedegep.vacations.model.impl.VacationsPackageImpl#getVacation()
    	 * @generated
    	 */
    EClass VACATION = eINSTANCE.getVacation();

    /**
    	 * The meta object literal for the '<em><b>End Date</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute VACATION__END_DATE = eINSTANCE.getVacation_EndDate();

    /**
    	 * The meta object literal for the '<em><b>Documents</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference VACATION__DOCUMENTS = eINSTANCE.getVacation_Documents();

    /**
    	 * The meta object literal for the '<em><b>Pictures</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute VACATION__PICTURES = eINSTANCE.getVacation_Pictures();

    /**
    	 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference VACATION__ELEMENTS = eINSTANCE.getVacation_Elements();

    /**
    	 * The meta object literal for the '<em><b>Find Document</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation VACATION___FIND_DOCUMENT__STRING = eINSTANCE.getVacation__FindDocument__String();

    /**
    	 * The meta object literal for the '<em><b>Get All Referenced Files</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation VACATION___GET_ALL_REFERENCED_FILES = eINSTANCE.getVacation__GetAllReferencedFiles();

    /**
    	 * The meta object literal for the '<em><b>Get Day Nr</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation VACATION___GET_DAY_NR__VACATIONELEMENT = eINSTANCE.getVacation__GetDayNr__VacationElement();

    /**
    	 * The meta object literal for the '{@link goedegep.vacations.model.impl.LocationImpl <em>Location</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.vacations.model.impl.LocationImpl
    	 * @see goedegep.vacations.model.impl.VacationsPackageImpl#getLocation()
    	 * @generated
    	 */
    EClass LOCATION = eINSTANCE.getLocation();

    /**
    	 * The meta object literal for the '<em><b>Location Category</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOCATION__LOCATION_CATEGORY = eINSTANCE.getLocation_LocationCategory();

    /**
    	 * The meta object literal for the '<em><b>Country</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOCATION__COUNTRY = eINSTANCE.getLocation_Country();

    /**
    	 * The meta object literal for the '<em><b>City</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOCATION__CITY = eINSTANCE.getLocation_City();

    /**
    	 * The meta object literal for the '<em><b>Street</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOCATION__STREET = eINSTANCE.getLocation_Street();

    /**
    	 * The meta object literal for the '<em><b>House Number</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOCATION__HOUSE_NUMBER = eINSTANCE.getLocation_HouseNumber();

    /**
    	 * The meta object literal for the '<em><b>Latitude</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOCATION__LATITUDE = eINSTANCE.getLocation_Latitude();

    /**
    	 * The meta object literal for the '<em><b>Longitude</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOCATION__LONGITUDE = eINSTANCE.getLocation_Longitude();

    /**
    	 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOCATION__NAME = eINSTANCE.getLocation_Name();

    /**
    	 * The meta object literal for the '<em><b>Web Site</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOCATION__WEB_SITE = eINSTANCE.getLocation_WebSite();

    /**
    	 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOCATION__DESCRIPTION = eINSTANCE.getLocation_Description();

    /**
    	 * The meta object literal for the '<em><b>Duration</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOCATION__DURATION = eINSTANCE.getLocation_Duration();

    /**
    	 * The meta object literal for the '<em><b>Start Date</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOCATION__START_DATE = eINSTANCE.getLocation_StartDate();

    /**
    	 * The meta object literal for the '<em><b>End Date</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOCATION__END_DATE = eINSTANCE.getLocation_EndDate();

    /**
    	 * The meta object literal for the '<em><b>Boundingbox</b></em>' containment reference feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference LOCATION__BOUNDINGBOX = eINSTANCE.getLocation_Boundingbox();

    /**
    	 * The meta object literal for the '<em><b>Boundaries</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference LOCATION__BOUNDARIES = eINSTANCE.getLocation_Boundaries();

    /**
    	 * The meta object literal for the '<em><b>Reference Only</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOCATION__REFERENCE_ONLY = eINSTANCE.getLocation_ReferenceOnly();

    /**
    	 * The meta object literal for the '<em><b>Stayed At This Location</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOCATION__STAYED_AT_THIS_LOCATION = eINSTANCE.getLocation_StayedAtThisLocation();

    /**
    	 * The meta object literal for the '{@link goedegep.vacations.model.impl.VacationElementImpl <em>Vacation Element</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.vacations.model.impl.VacationElementImpl
    	 * @see goedegep.vacations.model.impl.VacationsPackageImpl#getVacationElement()
    	 * @generated
    	 */
    EClass VACATION_ELEMENT = eINSTANCE.getVacationElement();

    /**
    	 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference VACATION_ELEMENT__CHILDREN = eINSTANCE.getVacationElement_Children();

    /**
    	 * The meta object literal for the '<em><b>Get Day Nr</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation VACATION_ELEMENT___GET_DAY_NR = eINSTANCE.getVacationElement__GetDayNr();

    /**
    	 * The meta object literal for the '<em><b>Get Vacation</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation VACATION_ELEMENT___GET_VACATION = eINSTANCE.getVacationElement__GetVacation();

    /**
    	 * The meta object literal for the '<em><b>Get Day Trip</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation VACATION_ELEMENT___GET_DAY_TRIP = eINSTANCE.getVacationElement__GetDayTrip();

    /**
    	 * The meta object literal for the '<em><b>Get Day</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation VACATION_ELEMENT___GET_DAY = eINSTANCE.getVacationElement__GetDay();

    /**
    	 * The meta object literal for the '{@link goedegep.vacations.model.impl.TextImpl <em>Text</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.vacations.model.impl.TextImpl
    	 * @see goedegep.vacations.model.impl.VacationsPackageImpl#getText()
    	 * @generated
    	 */
    EClass TEXT = eINSTANCE.getText();

    /**
    	 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute TEXT__TEXT = eINSTANCE.getText_Text();

    /**
    	 * The meta object literal for the '{@link goedegep.vacations.model.impl.DayImpl <em>Day</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.vacations.model.impl.DayImpl
    	 * @see goedegep.vacations.model.impl.VacationsPackageImpl#getDay()
    	 * @generated
    	 */
    EClass DAY = eINSTANCE.getDay();

    /**
    	 * The meta object literal for the '<em><b>Days</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute DAY__DAYS = eINSTANCE.getDay_Days();

    /**
    	 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute DAY__TITLE = eINSTANCE.getDay_Title();

    /**
    	 * The meta object literal for the '<em><b>Get Date</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation DAY___GET_DATE = eINSTANCE.getDay__GetDate();

    /**
    	 * The meta object literal for the '{@link goedegep.vacations.model.impl.PictureImpl <em>Picture</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.vacations.model.impl.PictureImpl
    	 * @see goedegep.vacations.model.impl.VacationsPackageImpl#getPicture()
    	 * @generated
    	 */
    EClass PICTURE = eINSTANCE.getPicture();

    /**
    	 * The meta object literal for the '<em><b>Picture Reference</b></em>' containment reference feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference PICTURE__PICTURE_REFERENCE = eINSTANCE.getPicture_PictureReference();

    /**
    	 * The meta object literal for the '{@link goedegep.vacations.model.impl.GPXTrackImpl <em>GPX Track</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.vacations.model.impl.GPXTrackImpl
    	 * @see goedegep.vacations.model.impl.VacationsPackageImpl#getGPXTrack()
    	 * @generated
    	 */
    EClass GPX_TRACK = eINSTANCE.getGPXTrack();

    /**
    	 * The meta object literal for the '<em><b>Track Reference</b></em>' containment reference feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference GPX_TRACK__TRACK_REFERENCE = eINSTANCE.getGPXTrack_TrackReference();

    /**
    	 * The meta object literal for the '{@link goedegep.vacations.model.impl.BoundingBoxImpl <em>Bounding Box</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.vacations.model.impl.BoundingBoxImpl
    	 * @see goedegep.vacations.model.impl.VacationsPackageImpl#getBoundingBox()
    	 * @generated
    	 */
    EClass BOUNDING_BOX = eINSTANCE.getBoundingBox();

    /**
    	 * The meta object literal for the '<em><b>West</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute BOUNDING_BOX__WEST = eINSTANCE.getBoundingBox_West();

    /**
    	 * The meta object literal for the '<em><b>North</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute BOUNDING_BOX__NORTH = eINSTANCE.getBoundingBox_North();

    /**
    	 * The meta object literal for the '<em><b>East</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute BOUNDING_BOX__EAST = eINSTANCE.getBoundingBox_East();

    /**
    	 * The meta object literal for the '<em><b>South</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute BOUNDING_BOX__SOUTH = eINSTANCE.getBoundingBox_South();

    /**
    	 * The meta object literal for the '<em><b>Is Valid</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation BOUNDING_BOX___IS_VALID = eINSTANCE.getBoundingBox__IsValid();

    /**
    	 * The meta object literal for the '{@link goedegep.vacations.model.impl.BoundaryImpl <em>Boundary</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.vacations.model.impl.BoundaryImpl
    	 * @see goedegep.vacations.model.impl.VacationsPackageImpl#getBoundary()
    	 * @generated
    	 */
    EClass BOUNDARY = eINSTANCE.getBoundary();

    /**
    	 * The meta object literal for the '<em><b>Points</b></em>' attribute list feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute BOUNDARY__POINTS = eINSTANCE.getBoundary_Points();

    /**
    	 * The meta object literal for the '{@link goedegep.vacations.model.impl.MapImageImpl <em>Map Image</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.vacations.model.impl.MapImageImpl
    	 * @see goedegep.vacations.model.impl.VacationsPackageImpl#getMapImage()
    	 * @generated
    	 */
    EClass MAP_IMAGE = eINSTANCE.getMapImage();

    /**
    	 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute MAP_IMAGE__TITLE = eINSTANCE.getMapImage_Title();

    /**
    	 * The meta object literal for the '<em><b>Image Width</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute MAP_IMAGE__IMAGE_WIDTH = eINSTANCE.getMapImage_ImageWidth();

    /**
    	 * The meta object literal for the '<em><b>Image Height</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute MAP_IMAGE__IMAGE_HEIGHT = eINSTANCE.getMapImage_ImageHeight();

    /**
    	 * The meta object literal for the '<em><b>Zoom</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute MAP_IMAGE__ZOOM = eINSTANCE.getMapImage_Zoom();

    /**
    	 * The meta object literal for the '<em><b>Center Latitude</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute MAP_IMAGE__CENTER_LATITUDE = eINSTANCE.getMapImage_CenterLatitude();

    /**
    	 * The meta object literal for the '<em><b>Center Longitude</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute MAP_IMAGE__CENTER_LONGITUDE = eINSTANCE.getMapImage_CenterLongitude();

    /**
    	 * The meta object literal for the '<em><b>File Name</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute MAP_IMAGE__FILE_NAME = eINSTANCE.getMapImage_FileName();

    /**
    	 * The meta object literal for the '{@link goedegep.vacations.model.impl.DayTripImpl <em>Day Trip</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.vacations.model.impl.DayTripImpl
    	 * @see goedegep.vacations.model.impl.VacationsPackageImpl#getDayTrip()
    	 * @generated
    	 */
    EClass DAY_TRIP = eINSTANCE.getDayTrip();

    /**
    	 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference DAY_TRIP__ELEMENTS = eINSTANCE.getDayTrip_Elements();

    /**
    	 * The meta object literal for the '{@link goedegep.vacations.model.impl.DocumentImpl <em>Document</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.vacations.model.impl.DocumentImpl
    	 * @see goedegep.vacations.model.impl.VacationsPackageImpl#getDocument()
    	 * @generated
    	 */
    EClass DOCUMENT = eINSTANCE.getDocument();

    /**
    	 * The meta object literal for the '<em><b>Document Reference</b></em>' containment reference feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference DOCUMENT__DOCUMENT_REFERENCE = eINSTANCE.getDocument_DocumentReference();

    /**
    	 * The meta object literal for the '{@link goedegep.vacations.model.impl.TravelCategoryImpl <em>Travel Category</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.vacations.model.impl.TravelCategoryImpl
    	 * @see goedegep.vacations.model.impl.VacationsPackageImpl#getTravelCategory()
    	 * @generated
    	 */
    EClass TRAVEL_CATEGORY = eINSTANCE.getTravelCategory();

    /**
    	 * The meta object literal for the '<em><b>Travels</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference TRAVEL_CATEGORY__TRAVELS = eINSTANCE.getTravelCategory_Travels();

    /**
    	 * The meta object literal for the '{@link goedegep.vacations.model.impl.TravelImpl <em>Travel</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.vacations.model.impl.TravelImpl
    	 * @see goedegep.vacations.model.impl.VacationsPackageImpl#getTravel()
    	 * @generated
    	 */
    EClass TRAVEL = eINSTANCE.getTravel();

    /**
    	 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute TRAVEL__TITLE = eINSTANCE.getTravel_Title();

    /**
    	 * The meta object literal for the '<em><b>Get Id</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation TRAVEL___GET_ID = eINSTANCE.getTravel__GetId();

    /**
    	 * The meta object literal for the '<em><b>Get All File References</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation TRAVEL___GET_ALL_FILE_REFERENCES = eINSTANCE.getTravel__GetAllFileReferences();

    /**
    	 * The meta object literal for the '{@link goedegep.vacations.model.impl.TravelCategoriesImpl <em>Travel Categories</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.vacations.model.impl.TravelCategoriesImpl
    	 * @see goedegep.vacations.model.impl.VacationsPackageImpl#getTravelCategories()
    	 * @generated
    	 */
    EClass TRAVEL_CATEGORIES = eINSTANCE.getTravelCategories();

    /**
    	 * The meta object literal for the '<em><b>Travelcategories</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference TRAVEL_CATEGORIES__TRAVELCATEGORIES = eINSTANCE.getTravelCategories_Travelcategories();

    /**
    	 * The meta object literal for the '<em>ELocation Category</em>' data type.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.poi.app.LocationCategory
    	 * @see goedegep.vacations.model.impl.VacationsPackageImpl#getELocationCategory()
    	 * @generated
    	 */
    EDataType ELOCATION_CATEGORY = eINSTANCE.getELocationCategory();

  }

} //VacationsPackage
