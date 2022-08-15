/**
 */
package goedegep.media.mediadb.model;

import goedegep.util.datetime.FlexDate;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Film</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.Video#getTitle <em>Title</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Video#getDate <em>Date</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Video#getImage <em>Image</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.Video#getSubjects <em>Subjects</em>}</li>
 * </ul>
 *
 * @see goedegep.media.mediadb.model.MediadbPackage#getVideo()
 * @model
 * @generated
 */
public interface Video extends EObject {
  /**
   * Returns the value of the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Title</em>' attribute.
   * @see #isSetTitle()
   * @see #unsetTitle()
   * @see #setTitle(String)
   * @see goedegep.media.mediadb.model.MediadbPackage#getVideo_Title()
   * @model unsettable="true"
   * @generated
   */
  String getTitle();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.Video#getTitle <em>Title</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Video#getTitle <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetTitle()
   * @see #getTitle()
   * @see #setTitle(String)
   * @generated
   */
  void unsetTitle();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Video#getTitle <em>Title</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Date</em>' attribute.
   * @see #isSetDate()
   * @see #unsetDate()
   * @see #setDate(FlexDate)
   * @see goedegep.media.mediadb.model.MediadbPackage#getVideo_Date()
   * @model unsettable="true" dataType="goedegep.types.model.EFlexDate"
   * @generated
   */
  FlexDate getDate();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.Video#getDate <em>Date</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Video#getDate <em>Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDate()
   * @see #getDate()
   * @see #setDate(FlexDate)
   * @generated
   */
  void unsetDate();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Video#getDate <em>Date</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Image</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Image</em>' attribute.
   * @see #isSetImage()
   * @see #unsetImage()
   * @see #setImage(String)
   * @see goedegep.media.mediadb.model.MediadbPackage#getVideo_Image()
   * @model unsettable="true"
   * @generated
   */
  String getImage();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.Video#getImage <em>Image</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Image</em>' attribute.
   * @see #isSetImage()
   * @see #unsetImage()
   * @see #getImage()
   * @generated
   */
  void setImage(String value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.Video#getImage <em>Image</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetImage()
   * @see #getImage()
   * @see #setImage(String)
   * @generated
   */
  void unsetImage();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.Video#getImage <em>Image</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Image</em>' attribute is set.
   * @see #unsetImage()
   * @see #getImage()
   * @see #setImage(String)
   * @generated
   */
  boolean isSetImage();

  /**
   * Returns the value of the '<em><b>Subjects</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.media.mediadb.model.Subject}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Subjects</em>' containment reference list.
   * @see goedegep.media.mediadb.model.MediadbPackage#getVideo_Subjects()
   * @model containment="true"
   * @generated
   */
  EList<Subject> getSubjects();

} // Film
