/**
 */
package goedegep.vacations.model;

import goedegep.types.model.FileReference;
import goedegep.types.model.Event;
import goedegep.util.datetime.FlexDate;
import org.eclipse.emf.common.util.EList;

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
 *   <li>{@link goedegep.vacations.model.Vacation#getEndDate <em>End Date</em>}</li>
 *   <li>{@link goedegep.vacations.model.Vacation#getDocuments <em>Documents</em>}</li>
 *   <li>{@link goedegep.vacations.model.Vacation#getPictures <em>Pictures</em>}</li>
 *   <li>{@link goedegep.vacations.model.Vacation#getTitle <em>Title</em>}</li>
 *   <li>{@link goedegep.vacations.model.Vacation#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @see goedegep.vacations.model.VacationsPackage#getVacation()
 * @model
 * @generated
 */
public interface Vacation extends Event {

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
   * @see goedegep.vacations.model.VacationsPackage#getVacation_EndDate()
   * @model unsettable="true" dataType="goedegep.types.model.EFlexDate"
   * @generated
   */
  FlexDate getEndDate();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Vacation#getEndDate <em>End Date</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.vacations.model.Vacation#getEndDate <em>End Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetEndDate()
   * @see #getEndDate()
   * @see #setEndDate(FlexDate)
   * @generated
   */
  void unsetEndDate();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Vacation#getEndDate <em>End Date</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Documents</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.types.model.FileReference}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Documents</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Documents</em>' containment reference list.
   * @see goedegep.vacations.model.VacationsPackage#getVacation_Documents()
   * @model containment="true"
   * @generated
   */
  EList<FileReference> getDocuments();

  /**
   * Returns the value of the '<em><b>Pictures</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Pictures</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Pictures</em>' attribute.
   * @see #setPictures(String)
   * @see goedegep.vacations.model.VacationsPackage#getVacation_Pictures()
   * @model
   * @generated
   */
  String getPictures();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Vacation#getPictures <em>Pictures</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Pictures</em>' attribute.
   * @see #getPictures()
   * @generated
   */
  void setPictures(String value);

  /**
   * Returns the value of the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Title</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Title</em>' attribute.
   * @see #isSetTitle()
   * @see #unsetTitle()
   * @see #setTitle(String)
   * @see goedegep.vacations.model.VacationsPackage#getVacation_Title()
   * @model unsettable="true"
   * @generated
   */
  String getTitle();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Vacation#getTitle <em>Title</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.vacations.model.Vacation#getTitle <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetTitle()
   * @see #getTitle()
   * @see #setTitle(String)
   * @generated
   */
  void unsetTitle();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Vacation#getTitle <em>Title</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.vacations.model.VacationElement}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Elements</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Elements</em>' containment reference list.
   * @see goedegep.vacations.model.VacationsPackage#getVacation_Elements()
   * @model containment="true"
   * @generated
   */
  EList<VacationElement> getElements();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model
   * @generated
   */
  FileReference findDocument(String documentPath);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * This method returns all file references (as FileReference) of the vacation. This means all documents of the vacation and all pictures of all Locations.
   * <!-- end-model-doc -->
   * @model kind="operation" ordered="false"
   * @generated
   */
  EList<FileReference> getAllFileReferences();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * This method returns all referenced files (as String) of the vacation. This means all  'Bestand' attributes  of the documents of the vacation and all pictures of all Locations.
   * <!-- end-model-doc -->
   * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.String"
   * @generated
   */
  EList<String> getAllReferencedFiles();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model kind="operation"
   * @generated
   */
  String getId();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model
   * @generated
   */
  Integer getDayNr(VacationElement vacationElement);
} // Vakantie
