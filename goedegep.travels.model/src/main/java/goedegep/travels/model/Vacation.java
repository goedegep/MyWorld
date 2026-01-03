/**
 */
package goedegep.travels.model;

import goedegep.types.model.Event;
import goedegep.util.datetime.FlexDate;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Vakantie</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A vacation may have been at a single location. In this case fill in 'location'. Fill in 'bezochteLocaties' for any location visited.<br/>
 * If you stayed at different locations, you can mix the locations where you stayed with the visited places, all in 'bezochteLocaties'.
 * 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.travels.model.Vacation#getEndDate <em>End Date</em>}</li>
 * </ul>
 *
 * @see goedegep.travels.model.TravelsPackage#getVacation()
 * @model
 * @generated
 */
public interface Vacation extends Event, Travel {

  /**
   * Returns the value of the '<em><b>End Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>End Date</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>End Date</em>' attribute.
   * @see #isSetEndDate()
   * @see #unsetEndDate()
   * @see #setEndDate(FlexDate)
   * @see goedegep.travels.model.TravelsPackage#getVacation_EndDate()
   * @model unsettable="true" dataType="goedegep.types.model.EFlexDate"
   * @generated
   */
  FlexDate getEndDate();

  /**
   * Sets the value of the '{@link goedegep.travels.model.Vacation#getEndDate <em>End Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>End Date</em>' attribute.
   * @see #isSetEndDate()
   * @see #unsetEndDate()
   * @see #getEndDate()
   * @generated
   */
  void setEndDate(FlexDate value);

  /**
   * Unsets the value of the '{@link goedegep.travels.model.Vacation#getEndDate <em>End Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetEndDate()
   * @see #getEndDate()
   * @see #setEndDate(FlexDate)
   * @generated
   */
  void unsetEndDate();

  /**
   * Returns whether the value of the '{@link goedegep.travels.model.Vacation#getEndDate <em>End Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>End Date</em>' attribute is set.
   * @see #unsetEndDate()
   * @see #getEndDate()
   * @see #setEndDate(FlexDate)
   * @generated
   */
  boolean isSetEndDate();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model
   * @generated
   */
  Integer getDayNr(VacationElement vacationElement);
} // Vakantie
