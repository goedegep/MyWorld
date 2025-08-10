/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.properties.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Descriptor Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.properties.model.PropertyDescriptorGroup#getPropertyDescriptors <em>Property Descriptors</em>}</li>
 *   <li>{@link goedegep.properties.model.PropertyDescriptorGroup#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.properties.model.PropertyDescriptorGroup#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.properties.model.PropertyDescriptorGroup#getPackageName <em>Package Name</em>}</li>
 *   <li>{@link goedegep.properties.model.PropertyDescriptorGroup#getRegistryClassName <em>Registry Class Name</em>}</li>
 * </ul>
 *
 * @see goedegep.properties.model.PropertiesPackage#getPropertyDescriptorGroup()
 * @model
 * @generated
 */
public interface PropertyDescriptorGroup extends EObject {
  /**
   * Returns the value of the '<em><b>Property Descriptors</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.properties.model.PropertyDescriptor}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Property Descriptors</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Property Descriptors</em>' containment reference list.
   * @see goedegep.properties.model.PropertiesPackage#getPropertyDescriptorGroup_PropertyDescriptors()
   * @model containment="true"
   * @generated
   */
  EList<PropertyDescriptor> getPropertyDescriptors();

  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The name of the PropertyDescriptorGroup. This name is used to identify the group.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #isSetName()
   * @see #unsetName()
   * @see #setName(String)
   * @see goedegep.properties.model.PropertiesPackage#getPropertyDescriptorGroup_Name()
   * @model unsettable="true"
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link goedegep.properties.model.PropertyDescriptorGroup#getName <em>Name</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.properties.model.PropertyDescriptorGroup#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetName()
   * @see #getName()
   * @see #setName(String)
   * @generated
   */
  void unsetName();

  /**
   * Returns whether the value of the '{@link goedegep.properties.model.PropertyDescriptorGroup#getName <em>Name</em>}' attribute is set.
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
   * <p>
   * If the meaning of the '<em>Description</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * A description of this PropertyDescriptorGroup.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Description</em>' attribute.
   * @see #isSetDescription()
   * @see #unsetDescription()
   * @see #setDescription(String)
   * @see goedegep.properties.model.PropertiesPackage#getPropertyDescriptorGroup_Description()
   * @model unsettable="true"
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link goedegep.properties.model.PropertyDescriptorGroup#getDescription <em>Description</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.properties.model.PropertyDescriptorGroup#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDescription()
   * @see #getDescription()
   * @see #setDescription(String)
   * @generated
   */
  void unsetDescription();

  /**
   * Returns whether the value of the '{@link goedegep.properties.model.PropertyDescriptorGroup#getDescription <em>Description</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Package Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Package Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The package name for the Registry class.
   * <p/>
   * Together with the registryClassName, this specifies the Registry class.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Package Name</em>' attribute.
   * @see #isSetPackageName()
   * @see #unsetPackageName()
   * @see #setPackageName(String)
   * @see goedegep.properties.model.PropertiesPackage#getPropertyDescriptorGroup_PackageName()
   * @model unsettable="true"
   * @generated
   */
  String getPackageName();

  /**
   * Sets the value of the '{@link goedegep.properties.model.PropertyDescriptorGroup#getPackageName <em>Package Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Package Name</em>' attribute.
   * @see #isSetPackageName()
   * @see #unsetPackageName()
   * @see #getPackageName()
   * @generated
   */
  void setPackageName(String value);

  /**
   * Unsets the value of the '{@link goedegep.properties.model.PropertyDescriptorGroup#getPackageName <em>Package Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetPackageName()
   * @see #getPackageName()
   * @see #setPackageName(String)
   * @generated
   */
  void unsetPackageName();

  /**
   * Returns whether the value of the '{@link goedegep.properties.model.PropertyDescriptorGroup#getPackageName <em>Package Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Package Name</em>' attribute is set.
   * @see #unsetPackageName()
   * @see #getPackageName()
   * @see #setPackageName(String)
   * @generated
   */
  boolean isSetPackageName();

  /**
   * Returns the value of the '<em><b>Registry Class Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Registry Class Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The name of the Registry class.
   * <p/>
   * Together with the packageName, this specifies the Registry class.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Registry Class Name</em>' attribute.
   * @see #isSetRegistryClassName()
   * @see #unsetRegistryClassName()
   * @see #setRegistryClassName(String)
   * @see goedegep.properties.model.PropertiesPackage#getPropertyDescriptorGroup_RegistryClassName()
   * @model unsettable="true"
   * @generated
   */
  String getRegistryClassName();

  /**
   * Sets the value of the '{@link goedegep.properties.model.PropertyDescriptorGroup#getRegistryClassName <em>Registry Class Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Registry Class Name</em>' attribute.
   * @see #isSetRegistryClassName()
   * @see #unsetRegistryClassName()
   * @see #getRegistryClassName()
   * @generated
   */
  void setRegistryClassName(String value);

  /**
   * Unsets the value of the '{@link goedegep.properties.model.PropertyDescriptorGroup#getRegistryClassName <em>Registry Class Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetRegistryClassName()
   * @see #getRegistryClassName()
   * @see #setRegistryClassName(String)
   * @generated
   */
  void unsetRegistryClassName();

  /**
   * Returns whether the value of the '{@link goedegep.properties.model.PropertyDescriptorGroup#getRegistryClassName <em>Registry Class Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Registry Class Name</em>' attribute is set.
   * @see #unsetRegistryClassName()
   * @see #getRegistryClassName()
   * @see #setRegistryClassName(String)
   * @generated
   */
  boolean isSetRegistryClassName();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model
   * @generated
   */
  PropertyDescriptor getPropertyDescriptor(String propertyName);

} // PropertyDescriptorGroup
