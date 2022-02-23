/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.properties.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>File Property Descriptor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.properties.model.FilePropertyDescriptor#getFileExtensions <em>File Extensions</em>}</li>
 * </ul>
 *
 * @see goedegep.properties.model.PropertiesPackage#getFilePropertyDescriptor()
 * @model
 * @generated
 */
public interface FilePropertyDescriptor extends PropertyDescriptor {
  /**
   * Returns the value of the '<em><b>File Extensions</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>File Extensions</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>File Extensions</em>' attribute list.
   * @see goedegep.properties.model.PropertiesPackage#getFilePropertyDescriptor_FileExtensions()
   * @model
   * @generated
   */
  EList<String> getFileExtensions();

} // FilePropertyDescriptor
