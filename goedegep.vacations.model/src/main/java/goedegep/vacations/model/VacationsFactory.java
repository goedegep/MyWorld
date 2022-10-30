/**
 */
package goedegep.vacations.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.vacations.model.VacationsPackage
 * @generated
 */
public interface VacationsFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  VacationsFactory eINSTANCE = goedegep.vacations.model.impl.VacationsFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Vacations</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Vacations</em>'.
   * @generated
   */
  Vacations createVacations();

  /**
   * Returns a new object of class '<em>Vacation</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Vacation</em>'.
   * @generated
   */
  Vacation createVacation();

  /**
   * Returns a new object of class '<em>Location</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Location</em>'.
   * @generated
   */
  Location createLocation();

  /**
   * Returns a new object of class '<em>Text</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Text</em>'.
   * @generated
   */
  Text createText();

  /**
   * Returns a new object of class '<em>Day</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Day</em>'.
   * @generated
   */
  Day createDay();

  /**
   * Returns a new object of class '<em>Picture</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Picture</em>'.
   * @generated
   */
  Picture createPicture();

  /**
   * Returns a new object of class '<em>GPX Track</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>GPX Track</em>'.
   * @generated
   */
  GPXTrack createGPXTrack();

  /**
   * Returns a new object of class '<em>Bounding Box</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Bounding Box</em>'.
   * @generated
   */
  BoundingBox createBoundingBox();

  /**
   * Returns a new object of class '<em>Boundary</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Boundary</em>'.
   * @generated
   */
  Boundary createBoundary();

  /**
   * Returns a new object of class '<em>Map Image</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Map Image</em>'.
   * @generated
   */
  MapImage createMapImage();

  /**
   * Returns a new object of class '<em>Day Trip</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Day Trip</em>'.
   * @generated
   */
  DayTrip createDayTrip();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  VacationsPackage getVacationsPackage();

} //VacationsFactory
