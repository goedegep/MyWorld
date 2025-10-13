/**
 */
package goedegep.vacations.model;

import java.util.Date;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Vacation Element Day</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.model.Day#getDays <em>Days</em>}</li>
 *   <li>{@link goedegep.vacations.model.Day#getTitle <em>Title</em>}</li>
 * </ul>
 *
 * @see goedegep.vacations.model.VacationsPackage#getDay()
 * @model
 * @generated
 */
public interface Day extends VacationElement {

  /**
   * Returns the value of the '<em><b>Days</b></em>' attribute.
   * The default value is <code>"1"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Days</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Days</em>' attribute.
   * @see #isSetDays()
   * @see #unsetDays()
   * @see #setDays(Integer)
   * @see goedegep.vacations.model.VacationsPackage#getDay_Days()
   * @model default="1" unsettable="true"
   * @generated
   */
  Integer getDays();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Day#getDays <em>Days</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Days</em>' attribute.
   * @see #isSetDays()
   * @see #unsetDays()
   * @see #getDays()
   * @generated
   */
  void setDays(Integer value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.Day#getDays <em>Days</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDays()
   * @see #getDays()
   * @see #setDays(Integer)
   * @generated
   */
  void unsetDays();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Day#getDays <em>Days</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Days</em>' attribute is set.
   * @see #unsetDays()
   * @see #getDays()
   * @see #setDays(Integer)
   * @generated
   */
  boolean isSetDays();

  /**
   * Returns the value of the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Title</em>' attribute.
   * @see #isSetTitle()
   * @see #unsetTitle()
   * @see #setTitle(String)
   * @see goedegep.vacations.model.VacationsPackage#getDay_Title()
   * @model unsettable="true"
   * @generated
   */
  String getTitle();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Day#getTitle <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Title</em>' attribute.
   * @see #isSetTitle()
   * @see #unsetTitle()
   * @see #getTitle()
   * @generated
   */
  void setTitle(String value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.Day#getTitle <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetTitle()
   * @see #getTitle()
   * @see #setTitle(String)
   * @generated
   */
  void unsetTitle();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Day#getTitle <em>Title</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Title</em>' attribute is set.
   * @see #unsetTitle()
   * @see #getTitle()
   * @see #setTitle(String)
   * @generated
   */
  boolean isSetTitle();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model kind="operation"
   * @generated
   */
  Date getDate();
} // VacationElementDay
