/**
 */
package goedegep.properties.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Descriptor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.properties.model.PropertyDescriptor#getType <em>Type</em>}</li>
 *   <li>{@link goedegep.properties.model.PropertyDescriptor#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.properties.model.PropertyDescriptor#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.properties.model.PropertyDescriptor#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link goedegep.properties.model.PropertyDescriptor#getRegistryName <em>Registry Name</em>}</li>
 *   <li>{@link goedegep.properties.model.PropertyDescriptor#getInitialValue <em>Initial Value</em>}</li>
 *   <li>{@link goedegep.properties.model.PropertyDescriptor#isUserSettable <em>User Settable</em>}</li>
 *   <li>{@link goedegep.properties.model.PropertyDescriptor#isInstallInitialValue <em>Install Initial Value</em>}</li>
 * </ul>
 *
 * @see goedegep.properties.model.PropertiesPackage#getPropertyDescriptor()
 * @model
 * @generated
 */
public interface PropertyDescriptor extends EObject {
  /**
   * Returns the value of the '<em><b>Type</b></em>' attribute.
   * The default value is <code>"STRING"</code>.
   * The literals are from the enumeration {@link goedegep.properties.model.PropertyType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The kind of information provided by the property.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Type</em>' attribute.
   * @see goedegep.properties.model.PropertyType
   * @see #setType(PropertyType)
   * @see goedegep.properties.model.PropertiesPackage#getPropertyDescriptor_Type()
   * @model default="STRING" required="true"
   * @generated
   */
  PropertyType getType();

  /**
   * Sets the value of the '{@link goedegep.properties.model.PropertyDescriptor#getType <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' attribute.
   * @see goedegep.properties.model.PropertyType
   * @see #getType()
   * @generated
   */
  void setType(PropertyType value);

  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * name of the property. This name is the unique Id of the property. 
   * <!-- end-model-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #isSetName()
   * @see #unsetName()
   * @see #setName(String)
   * @see goedegep.properties.model.PropertiesPackage#getPropertyDescriptor_Name()
   * @model unsettable="true" required="true"
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link goedegep.properties.model.PropertyDescriptor#getName <em>Name</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.properties.model.PropertyDescriptor#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetName()
   * @see #getName()
   * @see #setName(String)
   * @generated
   */
  void unsetName();

  /**
   * Returns whether the value of the '{@link goedegep.properties.model.PropertyDescriptor#getName <em>Name</em>}' attribute is set.
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
   * A description of the property. This is used to explain the property to the user.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Description</em>' attribute.
   * @see #isSetDescription()
   * @see #unsetDescription()
   * @see #setDescription(String)
   * @see goedegep.properties.model.PropertiesPackage#getPropertyDescriptor_Description()
   * @model unsettable="true"
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link goedegep.properties.model.PropertyDescriptor#getDescription <em>Description</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.properties.model.PropertyDescriptor#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDescription()
   * @see #getDescription()
   * @see #setDescription(String)
   * @generated
   */
  void unsetDescription();

  /**
   * Returns whether the value of the '{@link goedegep.properties.model.PropertyDescriptor#getDescription <em>Description</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Display Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The name shown to the user (e.g. in a properties editor).
   * <!-- end-model-doc -->
   * @return the value of the '<em>Display Name</em>' attribute.
   * @see #isSetDisplayName()
   * @see #unsetDisplayName()
   * @see #setDisplayName(String)
   * @see goedegep.properties.model.PropertiesPackage#getPropertyDescriptor_DisplayName()
   * @model unsettable="true"
   * @generated
   */
  String getDisplayName();

  /**
   * Sets the value of the '{@link goedegep.properties.model.PropertyDescriptor#getDisplayName <em>Display Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Display Name</em>' attribute.
   * @see #isSetDisplayName()
   * @see #unsetDisplayName()
   * @see #getDisplayName()
   * @generated
   */
  void setDisplayName(String value);

  /**
   * Unsets the value of the '{@link goedegep.properties.model.PropertyDescriptor#getDisplayName <em>Display Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDisplayName()
   * @see #getDisplayName()
   * @see #setDisplayName(String)
   * @generated
   */
  void unsetDisplayName();

  /**
   * Returns whether the value of the '{@link goedegep.properties.model.PropertyDescriptor#getDisplayName <em>Display Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Display Name</em>' attribute is set.
   * @see #unsetDisplayName()
   * @see #getDisplayName()
   * @see #setDisplayName(String)
   * @generated
   */
  boolean isSetDisplayName();

  /**
   * Returns the value of the '<em><b>Registry Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Registry Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The name under which the property is stored in the Registry. If not set the 'name' is used.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Registry Name</em>' attribute.
   * @see #isSetRegistryName()
   * @see #unsetRegistryName()
   * @see #setRegistryName(String)
   * @see goedegep.properties.model.PropertiesPackage#getPropertyDescriptor_RegistryName()
   * @model unsettable="true"
   * @generated
   */
  String getRegistryName();

  /**
   * Sets the value of the '{@link goedegep.properties.model.PropertyDescriptor#getRegistryName <em>Registry Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Registry Name</em>' attribute.
   * @see #isSetRegistryName()
   * @see #unsetRegistryName()
   * @see #getRegistryName()
   * @generated
   */
  void setRegistryName(String value);

  /**
   * Unsets the value of the '{@link goedegep.properties.model.PropertyDescriptor#getRegistryName <em>Registry Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetRegistryName()
   * @see #getRegistryName()
   * @see #setRegistryName(String)
   * @generated
   */
  void unsetRegistryName();

  /**
   * Returns whether the value of the '{@link goedegep.properties.model.PropertyDescriptor#getRegistryName <em>Registry Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Registry Name</em>' attribute is set.
   * @see #unsetRegistryName()
   * @see #getRegistryName()
   * @see #setRegistryName(String)
   * @generated
   */
  boolean isSetRegistryName();

  /**
   * Returns the value of the '<em><b>Initial Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Initial Value</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The system default value of the property. This is the value used, if it isn't overwritten by a user specific value (see Property).
   * <!-- end-model-doc -->
   * @return the value of the '<em>Initial Value</em>' attribute.
   * @see #isSetInitialValue()
   * @see #unsetInitialValue()
   * @see #setInitialValue(String)
   * @see goedegep.properties.model.PropertiesPackage#getPropertyDescriptor_InitialValue()
   * @model unsettable="true"
   * @generated
   */
  String getInitialValue();

  /**
   * Sets the value of the '{@link goedegep.properties.model.PropertyDescriptor#getInitialValue <em>Initial Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Initial Value</em>' attribute.
   * @see #isSetInitialValue()
   * @see #unsetInitialValue()
   * @see #getInitialValue()
   * @generated
   */
  void setInitialValue(String value);

  /**
   * Unsets the value of the '{@link goedegep.properties.model.PropertyDescriptor#getInitialValue <em>Initial Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetInitialValue()
   * @see #getInitialValue()
   * @see #setInitialValue(String)
   * @generated
   */
  void unsetInitialValue();

  /**
   * Returns whether the value of the '{@link goedegep.properties.model.PropertyDescriptor#getInitialValue <em>Initial Value</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Initial Value</em>' attribute is set.
   * @see #unsetInitialValue()
   * @see #getInitialValue()
   * @see #setInitialValue(String)
   * @generated
   */
  boolean isSetInitialValue();

  /**
   * Returns the value of the '<em><b>User Settable</b></em>' attribute.
   * The default value is <code>"false"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>User Settable</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Indicates whether the property can be set by the user of not. Set by the user means that the user can specify a value for the property in a user properties file.
   * <!-- end-model-doc -->
   * @return the value of the '<em>User Settable</em>' attribute.
   * @see #setUserSettable(boolean)
   * @see goedegep.properties.model.PropertiesPackage#getPropertyDescriptor_UserSettable()
   * @model default="false"
   * @generated
   */
  boolean isUserSettable();

  /**
   * Sets the value of the '{@link goedegep.properties.model.PropertyDescriptor#isUserSettable <em>User Settable</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>User Settable</em>' attribute.
   * @see #isUserSettable()
   * @generated
   */
  void setUserSettable(boolean value);

  /**
   * Returns the value of the '<em><b>Install Initial Value</b></em>' attribute.
   * The default value is <code>"false"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Install Initial Value</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Indicates whether the property value has to be installed in the Registry or not.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Install Initial Value</em>' attribute.
   * @see #isSetInstallInitialValue()
   * @see #unsetInstallInitialValue()
   * @see #setInstallInitialValue(boolean)
   * @see goedegep.properties.model.PropertiesPackage#getPropertyDescriptor_InstallInitialValue()
   * @model default="false" unsettable="true" required="true"
   * @generated
   */
  boolean isInstallInitialValue();

  /**
   * Sets the value of the '{@link goedegep.properties.model.PropertyDescriptor#isInstallInitialValue <em>Install Initial Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Install Initial Value</em>' attribute.
   * @see #isSetInstallInitialValue()
   * @see #unsetInstallInitialValue()
   * @see #isInstallInitialValue()
   * @generated
   */
  void setInstallInitialValue(boolean value);

  /**
   * Unsets the value of the '{@link goedegep.properties.model.PropertyDescriptor#isInstallInitialValue <em>Install Initial Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetInstallInitialValue()
   * @see #isInstallInitialValue()
   * @see #setInstallInitialValue(boolean)
   * @generated
   */
  void unsetInstallInitialValue();

  /**
   * Returns whether the value of the '{@link goedegep.properties.model.PropertyDescriptor#isInstallInitialValue <em>Install Initial Value</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Install Initial Value</em>' attribute is set.
   * @see #unsetInstallInitialValue()
   * @see #isInstallInitialValue()
   * @see #setInstallInitialValue(boolean)
   * @generated
   */
  boolean isSetInstallInitialValue();

} // PropertyDescriptor
