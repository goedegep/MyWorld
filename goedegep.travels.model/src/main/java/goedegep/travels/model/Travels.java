/**
 */
package goedegep.travels.model;

import goedegep.util.datetime.FlexDate;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Vakanties</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.travels.model.Travels#getVacations <em>Vacations</em>}</li>
 *   <li>{@link goedegep.travels.model.Travels#getHome <em>Home</em>}</li>
 *   <li>{@link goedegep.travels.model.Travels#getTips <em>Tips</em>}</li>
 *   <li>{@link goedegep.travels.model.Travels#getDayTrips <em>Day Trips</em>}</li>
 *   <li>{@link goedegep.travels.model.Travels#getTravelCategories <em>Travel Categories</em>}</li>
 * </ul>
 *
 * @see goedegep.travels.model.TravelsPackage#getTravels()
 * @model
 * @generated
 */
public interface Travels extends EObject {
  /**
   * Returns the value of the '<em><b>Vacations</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.travels.model.Vacation}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The vacations should be ordered by date, latest date first. When a vacation is added via the method addVacation(), the vacation is automatically added at the right location.
   * However when you add a vacation via the method getVacations().add(vacation), you have to take care of the order yourself.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Vacations</em>' containment reference list.
   * @see #isSetVacations()
   * @see #unsetVacations()
   * @see goedegep.travels.model.TravelsPackage#getTravels_Vacations()
   * @model containment="true" unsettable="true"
   * @generated
   */
  EList<Vacation> getVacations();

  /**
   * Unsets the value of the '{@link goedegep.travels.model.Travels#getVacations <em>Vacations</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetVacations()
   * @see #getVacations()
   * @generated
   */
  void unsetVacations();

  /**
   * Returns whether the value of the '{@link goedegep.travels.model.Travels#getVacations <em>Vacations</em>}' containment reference list is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Vacations</em>' containment reference list is set.
   * @see #unsetVacations()
   * @see #getVacations()
   * @generated
   */
  boolean isSetVacations();

  /**
   * Returns the value of the '<em><b>Home</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Home</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Home</em>' containment reference.
   * @see #isSetHome()
   * @see #unsetHome()
   * @see #setHome(Location)
   * @see goedegep.travels.model.TravelsPackage#getTravels_Home()
   * @model containment="true" unsettable="true"
   * @generated
   */
  Location getHome();

  /**
   * Sets the value of the '{@link goedegep.travels.model.Travels#getHome <em>Home</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Home</em>' containment reference.
   * @see #isSetHome()
   * @see #unsetHome()
   * @see #getHome()
   * @generated
   */
  void setHome(Location value);

  /**
   * Unsets the value of the '{@link goedegep.travels.model.Travels#getHome <em>Home</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetHome()
   * @see #getHome()
   * @see #setHome(Location)
   * @generated
   */
  void unsetHome();

  /**
   * Returns whether the value of the '{@link goedegep.travels.model.Travels#getHome <em>Home</em>}' containment reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Home</em>' containment reference is set.
   * @see #unsetHome()
   * @see #getHome()
   * @see #setHome(Location)
   * @generated
   */
  boolean isSetHome();

  /**
   * Returns the value of the '<em><b>Tips</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Tips</em>' attribute.
   * @see #setTips(String)
   * @see goedegep.travels.model.TravelsPackage#getTravels_Tips()
   * @model
   * @generated
   */
  String getTips();

  /**
   * Sets the value of the '{@link goedegep.travels.model.Travels#getTips <em>Tips</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Tips</em>' attribute.
   * @see #getTips()
   * @generated
   */
  void setTips(String value);

  /**
   * Returns the value of the '<em><b>Day Trips</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.travels.model.DayTrip}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Day Trips</em>' containment reference list.
   * @see goedegep.travels.model.TravelsPackage#getTravels_DayTrips()
   * @model containment="true"
   * @generated
   */
  EList<DayTrip> getDayTrips();

  /**
   * Returns the value of the '<em><b>Travel Categories</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.travels.model.TravelCategory}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Travel Categories</em>' containment reference list.
   * @see goedegep.travels.model.TravelsPackage#getTravels_TravelCategories()
   * @model containment="true"
   * @generated
   */
  EList<TravelCategory> getTravelCategories();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Find a vacation that started on a specific date and with a specific title.
   * <!-- end-model-doc -->
   * @model dateDataType="goedegep.types.model.EFlexDate"
   * @generated
   */
  Vacation findVacation(FlexDate date, String title);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Add a vacation to the vacations. The vacation must have <b>date</b> set, as the vacations are ordered by date.
   * 
   * @param vacation the vacation to be added to the vacations.
   * <!-- end-model-doc -->
   * @model
   * @generated
   */
  void addVacation(Vacation vacation);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Find a vacation that started on a specific date and with a specific title.
   * <!-- end-model-doc -->
   * @model dateDataType="goedegep.types.model.EFlexDate"
   * @generated
   */
  Vacation findVacation(FlexDate date);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model kind="operation"
   * @generated
   */
  EList<Travel> getTravels();

} // Vakanties
