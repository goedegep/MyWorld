/**
 */
package goedegep.pctools.filescontrolled.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>File Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.pctools.filescontrolled.model.FileInfo#getFileName <em>File Name</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.FileInfo#getCopyOf <em>Copy Of</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.FileInfo#getEqualityType <em>Equality Type</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.FileInfo#getMd5String <em>Md5 String</em>}</li>
 * </ul>
 *
 * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getFileInfo()
 * @model
 * @generated
 */
public interface FileInfo extends EObject {

  /**
   * Returns the value of the '<em><b>File Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>File Name</em>' attribute.
   * @see #isSetFileName()
   * @see #unsetFileName()
   * @see #setFileName(String)
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getFileInfo_FileName()
   * @model unsettable="true"
   * @generated
   */
  String getFileName();

  /**
   * Sets the value of the '{@link goedegep.pctools.filescontrolled.model.FileInfo#getFileName <em>File Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>File Name</em>' attribute.
   * @see #isSetFileName()
   * @see #unsetFileName()
   * @see #getFileName()
   * @generated
   */
  void setFileName(String value);

  /**
   * Unsets the value of the '{@link goedegep.pctools.filescontrolled.model.FileInfo#getFileName <em>File Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetFileName()
   * @see #getFileName()
   * @see #setFileName(String)
   * @generated
   */
  void unsetFileName();

  /**
   * Returns whether the value of the '{@link goedegep.pctools.filescontrolled.model.FileInfo#getFileName <em>File Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>File Name</em>' attribute is set.
   * @see #unsetFileName()
   * @see #getFileName()
   * @see #setFileName(String)
   * @generated
   */
  boolean isSetFileName();

  /**
   * Returns the value of the '<em><b>Copy Of</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Copy Of</em>' reference.
   * @see #isSetCopyOf()
   * @see #unsetCopyOf()
   * @see #setCopyOf(FileInfo)
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getFileInfo_CopyOf()
   * @model unsettable="true"
   * @generated
   */
  FileInfo getCopyOf();

  /**
   * Sets the value of the '{@link goedegep.pctools.filescontrolled.model.FileInfo#getCopyOf <em>Copy Of</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Copy Of</em>' reference.
   * @see #isSetCopyOf()
   * @see #unsetCopyOf()
   * @see #getCopyOf()
   * @generated
   */
  void setCopyOf(FileInfo value);

  /**
   * Unsets the value of the '{@link goedegep.pctools.filescontrolled.model.FileInfo#getCopyOf <em>Copy Of</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetCopyOf()
   * @see #getCopyOf()
   * @see #setCopyOf(FileInfo)
   * @generated
   */
  void unsetCopyOf();

  /**
   * Returns whether the value of the '{@link goedegep.pctools.filescontrolled.model.FileInfo#getCopyOf <em>Copy Of</em>}' reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Copy Of</em>' reference is set.
   * @see #unsetCopyOf()
   * @see #getCopyOf()
   * @see #setCopyOf(FileInfo)
   * @generated
   */
  boolean isSetCopyOf();

  /**
   * Returns the value of the '<em><b>Equality Type</b></em>' attribute.
   * The default value is <code>""</code>.
   * The literals are from the enumeration {@link goedegep.pctools.filescontrolled.model.EqualityType}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Equality Type</em>' attribute.
   * @see goedegep.pctools.filescontrolled.model.EqualityType
   * @see #setEqualityType(EqualityType)
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getFileInfo_EqualityType()
   * @model default=""
   * @generated
   */
  EqualityType getEqualityType();

  /**
   * Sets the value of the '{@link goedegep.pctools.filescontrolled.model.FileInfo#getEqualityType <em>Equality Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Equality Type</em>' attribute.
   * @see goedegep.pctools.filescontrolled.model.EqualityType
   * @see #getEqualityType()
   * @generated
   */
  void setEqualityType(EqualityType value);

  /**
   * Returns the value of the '<em><b>Md5 String</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Md5 String</em>' attribute.
   * @see #isSetMd5String()
   * @see #unsetMd5String()
   * @see #setMd5String(String)
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getFileInfo_Md5String()
   * @model unsettable="true"
   * @generated
   */
  String getMd5String();

  /**
   * Sets the value of the '{@link goedegep.pctools.filescontrolled.model.FileInfo#getMd5String <em>Md5 String</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Md5 String</em>' attribute.
   * @see #isSetMd5String()
   * @see #unsetMd5String()
   * @see #getMd5String()
   * @generated
   */
  void setMd5String(String value);

  /**
   * Unsets the value of the '{@link goedegep.pctools.filescontrolled.model.FileInfo#getMd5String <em>Md5 String</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetMd5String()
   * @see #getMd5String()
   * @see #setMd5String(String)
   * @generated
   */
  void unsetMd5String();

  /**
   * Returns whether the value of the '{@link goedegep.pctools.filescontrolled.model.FileInfo#getMd5String <em>Md5 String</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Md5 String</em>' attribute is set.
   * @see #unsetMd5String()
   * @see #getMd5String()
   * @see #setMd5String(String)
   * @generated
   */
  boolean isSetMd5String();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model kind="operation" required="true"
   * @generated
   */
  String getFullPathname();
} // FileInfo
