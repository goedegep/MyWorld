/**
 */
package goedegep.types.model;

import goedegep.util.datetime.FlexDate;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.types.model.Event#getDate <em>Date</em>}</li>
 *   <li>{@link goedegep.types.model.Event#getNotes <em>Notes</em>}</li>
 * </ul>
 *
 * @see goedegep.types.model.TypesPackage#getEvent()
 * @model
 * @generated
 */
public interface Event extends EObject {
  /**
   * Returns the value of the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Date</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Date</em>' attribute.
   * @see #isSetDate()
   * @see #unsetDate()
   * @see #setDate(FlexDate)
   * @see goedegep.types.model.TypesPackage#getEvent_Date()
   * @model unsettable="true" dataType="goedegep.types.model.EFlexDate"
   * @generated
   */
  FlexDate getDate();

  /**
   * Sets the value of the '{@link goedegep.types.model.Event#getDate <em>Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Date</em>' attribute.
   * @see #isSetDate()
   * @see #unsetDate()
   * @see #getDate()
   * @generated
   */
  void setDate(FlexDate value);

  /**
   * Unsets the value of the '{@link goedegep.types.model.Event#getDate <em>Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDate()
   * @see #getDate()
   * @see #setDate(FlexDate)
   * @generated
   */
  void unsetDate();

  /**
   * Returns whether the value of the '{@link goedegep.types.model.Event#getDate <em>Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Date</em>' attribute is set.
   * @see #unsetDate()
   * @see #getDate()
   * @see #setDate(FlexDate)
   * @generated
   */
  boolean isSetDate();

  /**
   * Returns the value of the '<em><b>Notes</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Notes</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Notes</em>' attribute.
   * @see #isSetNotes()
   * @see #unsetNotes()
   * @see #setNotes(String)
   * @see goedegep.types.model.TypesPackage#getEvent_Notes()
   * @model unsettable="true"
   * @generated
   */
  String getNotes();

  /**
   * Sets the value of the '{@link goedegep.types.model.Event#getNotes <em>Notes</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Notes</em>' attribute.
   * @see #isSetNotes()
   * @see #unsetNotes()
   * @see #getNotes()
   * @generated
   */
  void setNotes(String value);

  /**
   * Unsets the value of the '{@link goedegep.types.model.Event#getNotes <em>Notes</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetNotes()
   * @see #getNotes()
   * @see #setNotes(String)
   * @generated
   */
  void unsetNotes();

  /**
   * Returns whether the value of the '{@link goedegep.types.model.Event#getNotes <em>Notes</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Notes</em>' attribute is set.
   * @see #unsetNotes()
   * @see #getNotes()
   * @see #setNotes(String)
   * @generated
   */
  boolean isSetNotes();

} // Event
