/**
 */
package goedegep.travels.model.util;

import goedegep.travels.model.*;
import goedegep.types.model.Event;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see goedegep.travels.model.TravelsPackage
 * @generated
 */
public class VacationsSwitch<T> extends Switch<T> {
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static TravelsPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VacationsSwitch() {
    if (modelPackage == null) {
      modelPackage = TravelsPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage) {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject) {
    switch (classifierID) {
    case TravelsPackage.VACATIONS: {
      Vacations vacations = (Vacations) theEObject;
      T result = caseVacations(vacations);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case TravelsPackage.VACATION: {
      Vacation vacation = (Vacation) theEObject;
      T result = caseVacation(vacation);
      if (result == null)
        result = caseTravel(vacation);
      if (result == null)
        result = caseEvent(vacation);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case TravelsPackage.LOCATION: {
      Location location = (Location) theEObject;
      T result = caseLocation(location);
      if (result == null)
        result = caseVacationElement(location);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case TravelsPackage.VACATION_ELEMENT: {
      VacationElement vacationElement = (VacationElement) theEObject;
      T result = caseVacationElement(vacationElement);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case TravelsPackage.TEXT: {
      Text text = (Text) theEObject;
      T result = caseText(text);
      if (result == null)
        result = caseVacationElement(text);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case TravelsPackage.DAY: {
      Day day = (Day) theEObject;
      T result = caseDay(day);
      if (result == null)
        result = caseVacationElement(day);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case TravelsPackage.PICTURE: {
      Picture picture = (Picture) theEObject;
      T result = casePicture(picture);
      if (result == null)
        result = caseVacationElement(picture);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case TravelsPackage.GPX_TRACK: {
      GPXTrack gpxTrack = (GPXTrack) theEObject;
      T result = caseGPXTrack(gpxTrack);
      if (result == null)
        result = caseVacationElement(gpxTrack);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case TravelsPackage.BOUNDING_BOX: {
      BoundingBox boundingBox = (BoundingBox) theEObject;
      T result = caseBoundingBox(boundingBox);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case TravelsPackage.BOUNDARY: {
      Boundary boundary = (Boundary) theEObject;
      T result = caseBoundary(boundary);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case TravelsPackage.MAP_IMAGE: {
      MapImage mapImage = (MapImage) theEObject;
      T result = caseMapImage(mapImage);
      if (result == null)
        result = caseVacationElement(mapImage);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case TravelsPackage.DAY_TRIP: {
      DayTrip dayTrip = (DayTrip) theEObject;
      T result = caseDayTrip(dayTrip);
      if (result == null)
        result = caseTravel(dayTrip);
      if (result == null)
        result = caseEvent(dayTrip);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case TravelsPackage.DOCUMENT: {
      Document document = (Document) theEObject;
      T result = caseDocument(document);
      if (result == null)
        result = caseVacationElement(document);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case TravelsPackage.TRAVEL_CATEGORY: {
      TravelCategory travelCategory = (TravelCategory) theEObject;
      T result = caseTravelCategory(travelCategory);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case TravelsPackage.TRAVEL: {
      Travel travel = (Travel) theEObject;
      T result = caseTravel(travel);
      if (result == null)
        result = caseEvent(travel);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    default:
      return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Vacations</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Vacations</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseVacations(Vacations object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Vacation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Vacation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseVacation(Vacation object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Location</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Location</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseLocation(Location object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Vacation Element</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Vacation Element</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseVacationElement(VacationElement object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Text</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Text</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseText(Text object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Day</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Day</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDay(Day object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Picture</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Picture</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePicture(Picture object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>GPX Track</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>GPX Track</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGPXTrack(GPXTrack object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Bounding Box</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Bounding Box</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBoundingBox(BoundingBox object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Boundary</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Boundary</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBoundary(Boundary object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Map Image</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Map Image</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMapImage(MapImage object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Day Trip</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Day Trip</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDayTrip(DayTrip object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Document</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Document</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDocument(Document object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Travel Category</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Travel Category</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTravelCategory(TravelCategory object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Travel</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Travel</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTravel(Travel object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Event</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Event</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEvent(Event object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object) {
    return null;
  }

} //VacationsSwitch
