/**
 */
package goedegep.pctools.filescontrolled.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Disc Structure Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getDirectorySpecifications <em>Directory Specifications</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getFilesToIgnoreCompletely <em>Files To Ignore Completely</em>}</li>
 *   <li>{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getDirectoriesToIgnoreCompletely <em>Directories To Ignore Completely</em>}</li>
 * </ul>
 *
 * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getDiscStructureSpecification()
 * @model
 * @generated
 */
public interface DiscStructureSpecification extends EObject {
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Name of this specification
   * <!-- end-model-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #isSetName()
   * @see #unsetName()
   * @see #setName(String)
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getDiscStructureSpecification_Name()
   * @model unsettable="true"
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #isSetName()
   * @see #unsetName()
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Unsets the value of the '{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetName()
   * @see #getName()
   * @see #setName(String)
   * @generated
   */
  void unsetName();

  /**
   * Returns whether the value of the '{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getName <em>Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Name</em>' attribute is set.
   * @see #unsetName()
   * @see #getName()
   * @see #setName(String)
   * @generated
   */
  boolean isSetName();

  /**
   * Returns the value of the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Description of this specification.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Description</em>' attribute.
   * @see #isSetDescription()
   * @see #unsetDescription()
   * @see #setDescription(String)
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getDiscStructureSpecification_Description()
   * @model unsettable="true"
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getDescription <em>Description</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDescription()
   * @see #getDescription()
   * @see #setDescription(String)
   * @generated
   */
  void unsetDescription();

  /**
   * Returns whether the value of the '{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getDescription <em>Description</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Files To Ignore Completely</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.pctools.filescontrolled.model.DescribedItem}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Files To Ignore Completely</em>' containment reference list.
   * @see #isSetFilesToIgnoreCompletely()
   * @see #unsetFilesToIgnoreCompletely()
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getDiscStructureSpecification_FilesToIgnoreCompletely()
   * @model containment="true" unsettable="true"
   * @generated
   */
  EList<DescribedItem> getFilesToIgnoreCompletely();

  /**
   * Unsets the value of the '{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getFilesToIgnoreCompletely <em>Files To Ignore Completely</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetFilesToIgnoreCompletely()
   * @see #getFilesToIgnoreCompletely()
   * @generated
   */
  void unsetFilesToIgnoreCompletely();

  /**
   * Returns whether the value of the '{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getFilesToIgnoreCompletely <em>Files To Ignore Completely</em>}' containment reference list is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Files To Ignore Completely</em>' containment reference list is set.
   * @see #unsetFilesToIgnoreCompletely()
   * @see #getFilesToIgnoreCompletely()
   * @generated
   */
  boolean isSetFilesToIgnoreCompletely();

  /**
   * Returns the value of the '<em><b>Directories To Ignore Completely</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.pctools.filescontrolled.model.DescribedItem}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Directories To Ignore Completely</em>' containment reference list.
   * @see #isSetDirectoriesToIgnoreCompletely()
   * @see #unsetDirectoriesToIgnoreCompletely()
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getDiscStructureSpecification_DirectoriesToIgnoreCompletely()
   * @model containment="true" unsettable="true"
   * @generated
   */
  EList<DescribedItem> getDirectoriesToIgnoreCompletely();

  /**
   * Unsets the value of the '{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getDirectoriesToIgnoreCompletely <em>Directories To Ignore Completely</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDirectoriesToIgnoreCompletely()
   * @see #getDirectoriesToIgnoreCompletely()
   * @generated
   */
  void unsetDirectoriesToIgnoreCompletely();

  /**
   * Returns whether the value of the '{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getDirectoriesToIgnoreCompletely <em>Directories To Ignore Completely</em>}' containment reference list is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Directories To Ignore Completely</em>' containment reference list is set.
   * @see #unsetDirectoriesToIgnoreCompletely()
   * @see #getDirectoriesToIgnoreCompletely()
   * @generated
   */
  boolean isSetDirectoriesToIgnoreCompletely();

  /**
   * Returns the value of the '<em><b>Directory Specifications</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.pctools.filescontrolled.model.DirectorySpecification}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Directory Specifications</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Directory Specifications</em>' containment reference list.
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getDiscStructureSpecification_DirectorySpecifications()
   * @model containment="true"
   * @generated
   */
  EList<DirectorySpecification> getDirectorySpecifications();

} // DiscStructureSpecification
