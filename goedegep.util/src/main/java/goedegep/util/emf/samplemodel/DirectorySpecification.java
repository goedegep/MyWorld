/**
 */
package goedegep.util.emf.samplemodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Directory Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#getDirectoryPath <em>Directory Path</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#getSynchronizationSpecification <em>Synchronization Specification</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#getSourceControlSpecification <em>Source Control Specification</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#isToBeChecked <em>To Be Checked</em>}</li>
 * </ul>
 *
 * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getDirectorySpecification()
 * @model
 * @generated
 */
public interface DirectorySpecification extends EObject {
  /**
   * Returns the value of the '<em><b>Directory Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The full pathname of the directory.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Directory Path</em>' attribute.
   * @see #isSetDirectoryPath()
   * @see #unsetDirectoryPath()
   * @see #setDirectoryPath(String)
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getDirectorySpecification_DirectoryPath()
   * @model unsettable="true"
   * @generated
   */
  String getDirectoryPath();

  /**
   * Sets the value of the '{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#getDirectoryPath <em>Directory Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Directory Path</em>' attribute.
   * @see #isSetDirectoryPath()
   * @see #unsetDirectoryPath()
   * @see #getDirectoryPath()
   * @generated
   */
  void setDirectoryPath(String value);

  /**
   * Unsets the value of the '{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#getDirectoryPath <em>Directory Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDirectoryPath()
   * @see #getDirectoryPath()
   * @see #setDirectoryPath(String)
   * @generated
   */
  void unsetDirectoryPath();

  /**
   * Returns whether the value of the '{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#getDirectoryPath <em>Directory Path</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Directory Path</em>' attribute is set.
   * @see #unsetDirectoryPath()
   * @see #getDirectoryPath()
   * @see #setDirectoryPath(String)
   * @generated
   */
  boolean isSetDirectoryPath();

  /**
   * Returns the value of the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * E.g. the kind of information stored in that directory.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Description</em>' attribute.
   * @see #isSetDescription()
   * @see #unsetDescription()
   * @see #setDescription(String)
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getDirectorySpecification_Description()
   * @model unsettable="true"
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #isSetDescription()
   * @see #unsetDescription()
   * @see #getDescription()
   * @generated
   */
  void setDescription(String value);

  /**
   * Unsets the value of the '{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDescription()
   * @see #getDescription()
   * @see #setDescription(String)
   * @generated
   */
  void unsetDescription();

  /**
   * Returns whether the value of the '{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#getDescription <em>Description</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Description</em>' attribute is set.
   * @see #unsetDescription()
   * @see #getDescription()
   * @see #setDescription(String)
   * @generated
   */
  boolean isSetDescription();

  /**
   * Returns the value of the '<em><b>Synchronization Specification</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The term 'synchronized' here is used to indicate that the directory is automatically or manually replicated to/from some other location.
   * Examples are:
   * * directories synchronized to a NAS
   * * a Google Drive directory
   * * a DropBox directory
   * 
   * If not set, the directory is not synchronized.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Synchronization Specification</em>' attribute.
   * @see #isSetSynchronizationSpecification()
   * @see #unsetSynchronizationSpecification()
   * @see #setSynchronizationSpecification(String)
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getDirectorySpecification_SynchronizationSpecification()
   * @model unsettable="true"
   * @generated
   */
  String getSynchronizationSpecification();

  /**
   * Sets the value of the '{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#getSynchronizationSpecification <em>Synchronization Specification</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Synchronization Specification</em>' attribute.
   * @see #isSetSynchronizationSpecification()
   * @see #unsetSynchronizationSpecification()
   * @see #getSynchronizationSpecification()
   * @generated
   */
  void setSynchronizationSpecification(String value);

  /**
   * Unsets the value of the '{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#getSynchronizationSpecification <em>Synchronization Specification</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetSynchronizationSpecification()
   * @see #getSynchronizationSpecification()
   * @see #setSynchronizationSpecification(String)
   * @generated
   */
  void unsetSynchronizationSpecification();

  /**
   * Returns whether the value of the '{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#getSynchronizationSpecification <em>Synchronization Specification</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Synchronization Specification</em>' attribute is set.
   * @see #unsetSynchronizationSpecification()
   * @see #getSynchronizationSpecification()
   * @see #setSynchronizationSpecification(String)
   * @generated
   */
  boolean isSetSynchronizationSpecification();

  /**
   * Returns the value of the '<em><b>Source Control Specification</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Indicates how this directory is under version control.
   * If not set, the directory is not under version control.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Source Control Specification</em>' attribute.
   * @see #isSetSourceControlSpecification()
   * @see #unsetSourceControlSpecification()
   * @see #setSourceControlSpecification(String)
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getDirectorySpecification_SourceControlSpecification()
   * @model unsettable="true"
   * @generated
   */
  String getSourceControlSpecification();

  /**
   * Sets the value of the '{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#getSourceControlSpecification <em>Source Control Specification</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Source Control Specification</em>' attribute.
   * @see #isSetSourceControlSpecification()
   * @see #unsetSourceControlSpecification()
   * @see #getSourceControlSpecification()
   * @generated
   */
  void setSourceControlSpecification(String value);

  /**
   * Unsets the value of the '{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#getSourceControlSpecification <em>Source Control Specification</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetSourceControlSpecification()
   * @see #getSourceControlSpecification()
   * @see #setSourceControlSpecification(String)
   * @generated
   */
  void unsetSourceControlSpecification();

  /**
   * Returns whether the value of the '{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#getSourceControlSpecification <em>Source Control Specification</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Source Control Specification</em>' attribute is set.
   * @see #unsetSourceControlSpecification()
   * @see #getSourceControlSpecification()
   * @see #setSourceControlSpecification(String)
   * @generated
   */
  boolean isSetSourceControlSpecification();

  /**
   * Returns the value of the '<em><b>To Be Checked</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Specifies whether this directory is to be checked or not.
   * <!-- end-model-doc -->
   * @return the value of the '<em>To Be Checked</em>' attribute.
   * @see #setToBeChecked(boolean)
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getDirectorySpecification_ToBeChecked()
   * @model
   * @generated
   */
  boolean isToBeChecked();

  /**
   * Sets the value of the '{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#isToBeChecked <em>To Be Checked</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>To Be Checked</em>' attribute.
   * @see #isToBeChecked()
   * @generated
   */
  void setToBeChecked(boolean value);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   *  A directory is controlled, if it is 'automatically' replicated somewhere else. This is the case if it is synchronized or under version control.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  boolean isControlled();

} // DirectorySpecification
