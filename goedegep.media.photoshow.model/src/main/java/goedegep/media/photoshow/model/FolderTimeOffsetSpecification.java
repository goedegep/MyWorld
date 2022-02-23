/**
 */
package goedegep.media.photoshow.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Folder Time Offset Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.photoshow.model.FolderTimeOffsetSpecification#getFolderName <em>Folder Name</em>}</li>
 *   <li>{@link goedegep.media.photoshow.model.FolderTimeOffsetSpecification#getTimeOffset <em>Time Offset</em>}</li>
 * </ul>
 *
 * @see goedegep.media.photoshow.model.PhotoShowPackage#getFolderTimeOffsetSpecification()
 * @model
 * @generated
 */
public interface FolderTimeOffsetSpecification extends EObject {
  /**
   * Returns the value of the '<em><b>Folder Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Folder Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Folder Name</em>' attribute.
   * @see #isSetFolderName()
   * @see #unsetFolderName()
   * @see #setFolderName(String)
   * @see goedegep.media.photoshow.model.PhotoShowPackage#getFolderTimeOffsetSpecification_FolderName()
   * @model unsettable="true"
   * @generated
   */
  String getFolderName();

  /**
   * Sets the value of the '{@link goedegep.media.photoshow.model.FolderTimeOffsetSpecification#getFolderName <em>Folder Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Folder Name</em>' attribute.
   * @see #isSetFolderName()
   * @see #unsetFolderName()
   * @see #getFolderName()
   * @generated
   */
  void setFolderName(String value);

  /**
   * Unsets the value of the '{@link goedegep.media.photoshow.model.FolderTimeOffsetSpecification#getFolderName <em>Folder Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetFolderName()
   * @see #getFolderName()
   * @see #setFolderName(String)
   * @generated
   */
  void unsetFolderName();

  /**
   * Returns whether the value of the '{@link goedegep.media.photoshow.model.FolderTimeOffsetSpecification#getFolderName <em>Folder Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Folder Name</em>' attribute is set.
   * @see #unsetFolderName()
   * @see #getFolderName()
   * @see #setFolderName(String)
   * @generated
   */
  boolean isSetFolderName();

  /**
   * Returns the value of the '<em><b>Time Offset</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Time Offset</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Format is: [dd:][hh:][mm:]ss
   * 
   * If the time of a device is ahead of the time of the reference device, the offset (change to timeCorrection) is negative.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Time Offset</em>' attribute.
   * @see #isSetTimeOffset()
   * @see #unsetTimeOffset()
   * @see #setTimeOffset(String)
   * @see goedegep.media.photoshow.model.PhotoShowPackage#getFolderTimeOffsetSpecification_TimeOffset()
   * @model unsettable="true"
   * @generated
   */
  String getTimeOffset();

  /**
   * Sets the value of the '{@link goedegep.media.photoshow.model.FolderTimeOffsetSpecification#getTimeOffset <em>Time Offset</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.media.photoshow.model.FolderTimeOffsetSpecification#getTimeOffset <em>Time Offset</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetTimeOffset()
   * @see #getTimeOffset()
   * @see #setTimeOffset(String)
   * @generated
   */
  void unsetTimeOffset();

  /**
   * Returns whether the value of the '{@link goedegep.media.photoshow.model.FolderTimeOffsetSpecification#getTimeOffset <em>Time Offset</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Time Offset</em>' attribute is set.
   * @see #unsetTimeOffset()
   * @see #getTimeOffset()
   * @see #setTimeOffset(String)
   * @generated
   */
  boolean isSetTimeOffset();

} // FolderTimeOffsetSpecification
