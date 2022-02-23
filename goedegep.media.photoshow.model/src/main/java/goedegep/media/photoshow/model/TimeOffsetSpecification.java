/**
 */
package goedegep.media.photoshow.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Time Offset Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.photoshow.model.TimeOffsetSpecification#getPhoto <em>Photo</em>}</li>
 *   <li>{@link goedegep.media.photoshow.model.TimeOffsetSpecification#getTimeOffset <em>Time Offset</em>}</li>
 * </ul>
 *
 * @see goedegep.media.photoshow.model.PhotoShowPackage#getTimeOffsetSpecification()
 * @model
 * @generated
 */
public interface TimeOffsetSpecification extends EObject {
  /**
   * Returns the value of the '<em><b>Photo</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Photo</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Photo</em>' attribute.
   * @see #isSetPhoto()
   * @see #unsetPhoto()
   * @see #setPhoto(String)
   * @see goedegep.media.photoshow.model.PhotoShowPackage#getTimeOffsetSpecification_Photo()
   * @model unsettable="true"
   * @generated
   */
  String getPhoto();

  /**
   * Sets the value of the '{@link goedegep.media.photoshow.model.TimeOffsetSpecification#getPhoto <em>Photo</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Photo</em>' attribute.
   * @see #isSetPhoto()
   * @see #unsetPhoto()
   * @see #getPhoto()
   * @generated
   */
  void setPhoto(String value);

  /**
   * Unsets the value of the '{@link goedegep.media.photoshow.model.TimeOffsetSpecification#getPhoto <em>Photo</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetPhoto()
   * @see #getPhoto()
   * @see #setPhoto(String)
   * @generated
   */
  void unsetPhoto();

  /**
   * Returns whether the value of the '{@link goedegep.media.photoshow.model.TimeOffsetSpecification#getPhoto <em>Photo</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Photo</em>' attribute is set.
   * @see #unsetPhoto()
   * @see #getPhoto()
   * @see #setPhoto(String)
   * @generated
   */
  boolean isSetPhoto();

  /**
   * Returns the value of the '<em><b>Time Offset</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Format is: [dd:][hh:][mm:]ss
   * 
   * 
   * 
   * <!-- end-model-doc -->
   * @return the value of the '<em>Time Offset</em>' attribute.
   * @see #isSetTimeOffset()
   * @see #unsetTimeOffset()
   * @see #setTimeOffset(String)
   * @see goedegep.media.photoshow.model.PhotoShowPackage#getTimeOffsetSpecification_TimeOffset()
   * @model unsettable="true"
   * @generated
   */
  String getTimeOffset();

  /**
   * Sets the value of the '{@link goedegep.media.photoshow.model.TimeOffsetSpecification#getTimeOffset <em>Time Offset</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Time Offset</em>' attribute.
   * @see #isSetTimeOffset()
   * @see #unsetTimeOffset()
   * @see #getTimeOffset()
   * @generated
   */
  void setTimeOffset(String value);

  /**
   * Unsets the value of the '{@link goedegep.media.photoshow.model.TimeOffsetSpecification#getTimeOffset <em>Time Offset</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetTimeOffset()
   * @see #getTimeOffset()
   * @see #setTimeOffset(String)
   * @generated
   */
  void unsetTimeOffset();

  /**
   * Returns whether the value of the '{@link goedegep.media.photoshow.model.TimeOffsetSpecification#getTimeOffset <em>Time Offset</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Time Offset</em>' attribute is set.
   * @see #unsetTimeOffset()
   * @see #getTimeOffset()
   * @see #setTimeOffset(String)
   * @generated
   */
  boolean isSetTimeOffset();

} // TimeOffsetSpecification
