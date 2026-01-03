/**
 */
package goedegep.travels.model;

import goedegep.types.model.Event;
import goedegep.types.model.FileReference;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Travel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.travels.model.Travel#getTitle <em>Title</em>}</li>
 *   <li>{@link goedegep.travels.model.Travel#getElements <em>Elements</em>}</li>
 *   <li>{@link goedegep.travels.model.Travel#getDocuments <em>Documents</em>}</li>
 *   <li>{@link goedegep.travels.model.Travel#getPictures <em>Pictures</em>}</li>
 * </ul>
 *
 * @see goedegep.travels.model.TravelsPackage#getTravel()
 * @model
 * @generated
 */
public interface Travel extends Event {
  /**
   * Returns the value of the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Title</em>' attribute.
   * @see #isSetTitle()
   * @see #unsetTitle()
   * @see #setTitle(String)
   * @see goedegep.travels.model.TravelsPackage#getTravel_Title()
   * @model unsettable="true"
   * @generated
   */
  String getTitle();

  /**
   * Sets the value of the '{@link goedegep.travels.model.Travel#getTitle <em>Title</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.travels.model.Travel#getTitle <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetTitle()
   * @see #getTitle()
   * @see #setTitle(String)
   * @generated
   */
  void unsetTitle();

  /**
   * Returns whether the value of the '{@link goedegep.travels.model.Travel#getTitle <em>Title</em>}' attribute is set.
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
   * The list contents are of type {@link goedegep.travels.model.VacationElement}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Elements</em>' containment reference list.
   * @see goedegep.travels.model.TravelsPackage#getTravel_Elements()
   * @model containment="true"
   * @generated
   */
  EList<VacationElement> getElements();

  /**
   * Returns the value of the '<em><b>Documents</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.types.model.FileReference}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Documents</em>' containment reference list.
   * @see goedegep.travels.model.TravelsPackage#getTravel_Documents()
   * @model containment="true"
   * @generated
   */
  EList<FileReference> getDocuments();

  /**
   * Returns the value of the '<em><b>Pictures</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Pictures</em>' attribute.
   * @see #setPictures(String)
   * @see goedegep.travels.model.TravelsPackage#getTravel_Pictures()
   * @model
   * @generated
   */
  String getPictures();

  /**
   * Sets the value of the '{@link goedegep.travels.model.Travel#getPictures <em>Pictures</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Pictures</em>' attribute.
   * @see #getPictures()
   * @generated
   */
  void setPictures(String value);

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
   * @model
   * @generated
   */
  FileReference findDocument(String documentPath);

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

} // Travel
