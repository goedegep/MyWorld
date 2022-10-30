/**
 */
package goedegep.events.model;

import goedegep.types.model.Event;
import goedegep.types.model.FileReference;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.events.model.EventInfo#getTitle <em>Title</em>}</li>
 *   <li>{@link goedegep.events.model.EventInfo#getPicture <em>Picture</em>}</li>
 *   <li>{@link goedegep.events.model.EventInfo#getAttachments <em>Attachments</em>}</li>
 * </ul>
 *
 * @see goedegep.events.model.EventsPackage#getEventInfo()
 * @model
 * @generated
 */
public interface EventInfo extends Event {
  /**
   * Returns the value of the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Title</em>' attribute.
   * @see #isSetTitle()
   * @see #unsetTitle()
   * @see #setTitle(String)
   * @see goedegep.events.model.EventsPackage#getEventInfo_Title()
   * @model unsettable="true"
   * @generated
   */
  String getTitle();

  /**
   * Sets the value of the '{@link goedegep.events.model.EventInfo#getTitle <em>Title</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.events.model.EventInfo#getTitle <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetTitle()
   * @see #getTitle()
   * @see #setTitle(String)
   * @generated
   */
  void unsetTitle();

  /**
   * Returns whether the value of the '{@link goedegep.events.model.EventInfo#getTitle <em>Title</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Picture</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Picture</em>' attribute.
   * @see #isSetPicture()
   * @see #unsetPicture()
   * @see #setPicture(String)
   * @see goedegep.events.model.EventsPackage#getEventInfo_Picture()
   * @model unsettable="true"
   * @generated
   */
  String getPicture();

  /**
   * Sets the value of the '{@link goedegep.events.model.EventInfo#getPicture <em>Picture</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Picture</em>' attribute.
   * @see #isSetPicture()
   * @see #unsetPicture()
   * @see #getPicture()
   * @generated
   */
  void setPicture(String value);

  /**
   * Unsets the value of the '{@link goedegep.events.model.EventInfo#getPicture <em>Picture</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetPicture()
   * @see #getPicture()
   * @see #setPicture(String)
   * @generated
   */
  void unsetPicture();

  /**
   * Returns whether the value of the '{@link goedegep.events.model.EventInfo#getPicture <em>Picture</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Picture</em>' attribute is set.
   * @see #unsetPicture()
   * @see #getPicture()
   * @see #setPicture(String)
   * @generated
   */
  boolean isSetPicture();

  /**
   * Returns the value of the '<em><b>Attachments</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.types.model.FileReference}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Attachments</em>' containment reference list.
   * @see goedegep.events.model.EventsPackage#getEventInfo_Attachments()
   * @model containment="true"
   * @generated
   */
  EList<FileReference> getAttachments();

} // EventInfo
