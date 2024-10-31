/**
 */
package goedegep.media.mediadb.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Medium Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.media.mediadb.model.MediumInfo#getMediumType <em>Medium Type</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.MediumInfo#getInformationType <em>Information Type</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.MediumInfo#getSourceBitRate <em>Source Bit Rate</em>}</li>
 *   <li>{@link goedegep.media.mediadb.model.MediumInfo#getSourceType <em>Source Type</em>}</li>
 * </ul>
 *
 * @see goedegep.media.mediadb.model.MediadbPackage#getMediumInfo()
 * @model
 * @generated
 */
public interface MediumInfo extends EObject {
  /**
   * Returns the value of the '<em><b>Medium Type</b></em>' attribute.
   * The default value is <code>"<not-set>"</code>.
   * The literals are from the enumeration {@link goedegep.media.mediadb.model.MediumType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Medium Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Medium Type</em>' attribute.
   * @see goedegep.media.mediadb.model.MediumType
   * @see #isSetMediumType()
   * @see #unsetMediumType()
   * @see #setMediumType(MediumType)
   * @see goedegep.media.mediadb.model.MediadbPackage#getMediumInfo_MediumType()
   * @model default="&lt;not-set&gt;" unsettable="true"
   * @generated
   */
  MediumType getMediumType();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.MediumInfo#getMediumType <em>Medium Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Medium Type</em>' attribute.
   * @see goedegep.media.mediadb.model.MediumType
   * @see #isSetMediumType()
   * @see #unsetMediumType()
   * @see #getMediumType()
   * @generated
   */
  void setMediumType(MediumType value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.MediumInfo#getMediumType <em>Medium Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetMediumType()
   * @see #getMediumType()
   * @see #setMediumType(MediumType)
   * @generated
   */
  void unsetMediumType();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.MediumInfo#getMediumType <em>Medium Type</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Medium Type</em>' attribute is set.
   * @see #unsetMediumType()
   * @see #getMediumType()
   * @see #setMediumType(MediumType)
   * @generated
   */
  boolean isSetMediumType();

  /**
   * Returns the value of the '<em><b>Information Type</b></em>' attribute.
   * The literals are from the enumeration {@link goedegep.media.mediadb.model.InformationType}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Information Type</em>' attribute.
   * @see goedegep.media.mediadb.model.InformationType
   * @see #isSetInformationType()
   * @see #unsetInformationType()
   * @see #setInformationType(InformationType)
   * @see goedegep.media.mediadb.model.MediadbPackage#getMediumInfo_InformationType()
   * @model unsettable="true"
   * @generated
   */
  InformationType getInformationType();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.MediumInfo#getInformationType <em>Information Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Information Type</em>' attribute.
   * @see goedegep.media.mediadb.model.InformationType
   * @see #isSetInformationType()
   * @see #unsetInformationType()
   * @see #getInformationType()
   * @generated
   */
  void setInformationType(InformationType value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.MediumInfo#getInformationType <em>Information Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetInformationType()
   * @see #getInformationType()
   * @see #setInformationType(InformationType)
   * @generated
   */
  void unsetInformationType();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.MediumInfo#getInformationType <em>Information Type</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Information Type</em>' attribute is set.
   * @see #unsetInformationType()
   * @see #getInformationType()
   * @see #setInformationType(InformationType)
   * @generated
   */
  boolean isSetInformationType();

  /**
   * Returns the value of the '<em><b>Source Bit Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Source Bit Rate</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Source Bit Rate</em>' attribute.
   * @see #isSetSourceBitRate()
   * @see #unsetSourceBitRate()
   * @see #setSourceBitRate(int)
   * @see goedegep.media.mediadb.model.MediadbPackage#getMediumInfo_SourceBitRate()
   * @model unsettable="true"
   * @generated
   */
  int getSourceBitRate();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.MediumInfo#getSourceBitRate <em>Source Bit Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Source Bit Rate</em>' attribute.
   * @see #isSetSourceBitRate()
   * @see #unsetSourceBitRate()
   * @see #getSourceBitRate()
   * @generated
   */
  void setSourceBitRate(int value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.MediumInfo#getSourceBitRate <em>Source Bit Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetSourceBitRate()
   * @see #getSourceBitRate()
   * @see #setSourceBitRate(int)
   * @generated
   */
  void unsetSourceBitRate();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.MediumInfo#getSourceBitRate <em>Source Bit Rate</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Source Bit Rate</em>' attribute is set.
   * @see #unsetSourceBitRate()
   * @see #getSourceBitRate()
   * @see #setSourceBitRate(int)
   * @generated
   */
  boolean isSetSourceBitRate();

  /**
   * Returns the value of the '<em><b>Source Type</b></em>' attribute.
   * The literals are from the enumeration {@link goedegep.media.mediadb.model.InformationType}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Source Type</em>' attribute.
   * @see goedegep.media.mediadb.model.InformationType
   * @see #isSetSourceType()
   * @see #unsetSourceType()
   * @see #setSourceType(InformationType)
   * @see goedegep.media.mediadb.model.MediadbPackage#getMediumInfo_SourceType()
   * @model unsettable="true"
   * @generated
   */
  InformationType getSourceType();

  /**
   * Sets the value of the '{@link goedegep.media.mediadb.model.MediumInfo#getSourceType <em>Source Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Source Type</em>' attribute.
   * @see goedegep.media.mediadb.model.InformationType
   * @see #isSetSourceType()
   * @see #unsetSourceType()
   * @see #getSourceType()
   * @generated
   */
  void setSourceType(InformationType value);

  /**
   * Unsets the value of the '{@link goedegep.media.mediadb.model.MediumInfo#getSourceType <em>Source Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetSourceType()
   * @see #getSourceType()
   * @see #setSourceType(InformationType)
   * @generated
   */
  void unsetSourceType();

  /**
   * Returns whether the value of the '{@link goedegep.media.mediadb.model.MediumInfo#getSourceType <em>Source Type</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Source Type</em>' attribute is set.
   * @see #unsetSourceType()
   * @see #getSourceType()
   * @see #setSourceType(InformationType)
   * @generated
   */
  boolean isSetSourceType();

} // MediumInfo
