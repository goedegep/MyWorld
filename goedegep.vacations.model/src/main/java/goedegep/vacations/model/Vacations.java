/**
 */
package goedegep.vacations.model;

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
 *   <li>{@link goedegep.vacations.model.Vacations#getVacations <em>Vacations</em>}</li>
 *   <li>{@link goedegep.vacations.model.Vacations#getHome <em>Home</em>}</li>
 *   <li>{@link goedegep.vacations.model.Vacations#getTips <em>Tips</em>}</li>
 *   <li>{@link goedegep.vacations.model.Vacations#getDayTrips <em>Day Trips</em>}</li>
 *   <li>{@link goedegep.vacations.model.Vacations#getTravelcategories <em>Travelcategories</em>}</li>
 * </ul>
 *
 * @see goedegep.vacations.model.VacationsPackage#getVacations()
 * @model
 * @generated
 */
public interface Vacations extends EObject {
  /**
   * Returns the value of the '<em><b>Vacations</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.vacations.model.Vacation}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The vacations should be ordered by date, latest date first. When a vacation is added via the method addVacation(), the vacation is automatically added at the right location.
   * However when you add a vacation via the method getVacations().add(vacation), you have to take care of the order yourself.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Vacations</em>' containment reference list.
   * @see #isSetVacations()
   * @see #unsetVacations()
   * @see goedegep.vacations.model.VacationsPackage#getVacations_Vacations()
   * @model containment="true" unsettable="true"
   * @generated
   */
  EList<Vacation> getVacations();

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.Vacations#getVacations <em>Vacations</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetVacations()
   * @see #getVacations()
   * @generated
   */
  void unsetVacations();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Vacations#getVacations <em>Vacations</em>}' containment reference list is set.
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
   * @see goedegep.vacations.model.VacationsPackage#getVacations_Home()
   * @model containment="true" unsettable="true"
   * @generated
   */
  Location getHome();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Vacations#getHome <em>Home</em>}' containment reference.
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
   * Unsets the value of the '{@link goedegep.vacations.model.Vacations#getHome <em>Home</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetHome()
   * @see #getHome()
   * @see #setHome(Location)
   * @generated
   */
  void unsetHome();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Vacations#getHome <em>Home</em>}' containment reference is set.
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
   * @see goedegep.vacations.model.VacationsPackage#getVacations_Tips()
   * @model
   * @generated
   */
  String getTips();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Vacations#getTips <em>Tips</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Tips</em>' attribute.
   * @see #getTips()
   * @generated
   */
  void setTips(String value);

  /**
   * Returns the value of the '<em><b>Day Trips</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.vacations.model.DayTrip}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Day Trips</em>' containment reference list.
   * @see goedegep.vacations.model.VacationsPackage#getVacations_DayTrips()
   * @model containment="true"
   * @generated
   */
  EList<DayTrip> getDayTrips();

  /**
   * Returns the value of the '<em><b>Travelcategories</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.vacations.model.TravelCategories}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Travelcategories</em>' containment reference list.
   * @see goedegep.vacations.model.VacationsPackage#getVacations_Travelcategories()
   * @model containment="true"
   * @generated
   */
  EList<TravelCategories> getTravelcategories();

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

} // Vakanties
