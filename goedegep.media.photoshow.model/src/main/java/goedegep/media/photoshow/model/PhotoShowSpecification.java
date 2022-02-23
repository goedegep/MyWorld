/**
 */
package goedegep.media.photoshow.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.photoshow.model.PhotoShowSpecification#getPhotoFolders <em>Photo Folders</em>}</li>
 *   <li>{@link goedegep.media.photoshow.model.PhotoShowSpecification#getTimeoffsetspecifications <em>Timeoffsetspecifications</em>}</li>
 *   <li>{@link goedegep.media.photoshow.model.PhotoShowSpecification#getTitle <em>Title</em>}</li>
 *   <li>{@link goedegep.media.photoshow.model.PhotoShowSpecification#getFolderTimeOffsetSpecifications <em>Folder Time Offset Specifications</em>}</li>
 *   <li>{@link goedegep.media.photoshow.model.PhotoShowSpecification#getPhotosToShow <em>Photos To Show</em>}</li>
 * </ul>
 *
 * @see goedegep.media.photoshow.model.PhotoShowPackage#getPhotoShowSpecification()
 * @model
 * @generated
 */
public interface PhotoShowSpecification extends EObject {
  /**
   * Returns the value of the '<em><b>Photo Folders</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Photo Folders</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Photo Folders</em>' attribute list.
   * @see goedegep.media.photoshow.model.PhotoShowPackage#getPhotoShowSpecification_PhotoFolders()
   * @model
   * @generated
   */
  EList<String> getPhotoFolders();

  /**
   * Returns the value of the '<em><b>Timeoffsetspecifications</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.media.photoshow.model.TimeOffsetSpecification}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Timeoffsetspecifications</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Timeoffsetspecifications</em>' containment reference list.
   * @see goedegep.media.photoshow.model.PhotoShowPackage#getPhotoShowSpecification_Timeoffsetspecifications()
   * @model containment="true"
   * @generated
   */
  EList<TimeOffsetSpecification> getTimeoffsetspecifications();

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
   * @see goedegep.media.photoshow.model.PhotoShowPackage#getPhotoShowSpecification_Title()
   * @model unsettable="true"
   * @generated
   */
  String getTitle();

  /**
   * Sets the value of the '{@link goedegep.media.photoshow.model.PhotoShowSpecification#getTitle <em>Title</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.media.photoshow.model.PhotoShowSpecification#getTitle <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetTitle()
   * @see #getTitle()
   * @see #setTitle(String)
   * @generated
   */
  void unsetTitle();

  /**
   * Returns whether the value of the '{@link goedegep.media.photoshow.model.PhotoShowSpecification#getTitle <em>Title</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Folder Time Offset Specifications</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.media.photoshow.model.FolderTimeOffsetSpecification}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Folder Time Offset Specifications</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Folder Time Offset Specifications</em>' containment reference list.
   * @see goedegep.media.photoshow.model.PhotoShowPackage#getPhotoShowSpecification_FolderTimeOffsetSpecifications()
   * @model containment="true"
   * @generated
   */
  EList<FolderTimeOffsetSpecification> getFolderTimeOffsetSpecifications();

  /**
   * Returns the value of the '<em><b>Photos To Show</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Photos To Show</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Photos To Show</em>' attribute list.
   * @see goedegep.media.photoshow.model.PhotoShowPackage#getPhotoShowSpecification_PhotosToShow()
   * @model
   * @generated
   */
  EList<String> getPhotosToShow();

} // PhotoShowSpecification
