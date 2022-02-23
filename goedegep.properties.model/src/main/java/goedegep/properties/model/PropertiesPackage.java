/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.properties.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-model-doc -->
 * Properties are name/value pairs. The name identifies the property. The value is always a string, which can of course be interpreted as a number.<br/>
 * Example of a property:<br/>
 * name: "addressBookDataFile"<br/>
 * value: "C:\Users\Jim\My Documents\myAddressBook.xml"
 * <p/>
 * Properties are read from a file and stored in a Registry.
 * <p/>
 * Properties are hierarchically grouped in a PropertyGroup. A PropertyGroup can have zero or more Properties and zero or more sub groups.
 * <p/>
 * A Property Descriptor provides meta information on a property:
 * <ul>
 * <li>name - The name which identifies the Property (the same as 'name' of Property).</li>
 * <li>displayName - The name of the property which can be shown in an editor.</li>
 * <li>description - A textual description for the user. E.g. to be shown in an editor</li>
 * <li>type - The PropertyType. This can be used in a property editor. For the types FILE and DIRECTORY a File Chooser can be offered to select the value.</li>
 * <li>initialValue - The initial or default value of the property. So a file with Property Descriptors also provide the values. This means that for an application, besides a PropertyDescriptors file only an optional file is needed for user specific values.</li>
 * <li>userSettable - Some values cannot be changed by the user. In an editor these can be left out, or made read only. As an example, the name of a file with user settings cannot be changed.</li>
 * <li>registryName - Within the application the properties are stored in a Registry. This is the name of the attribute under which this property will be stored in the Registry. If not specified, the 'name' is used.</li>
 * <li>installInitialValue - Indicates whether the initial value shall be stored in the Registry of not.</li>
 * </ul>
 * 
 * Like Properties, Property Descriptors are hierarchically grouped in PropertyDescriptorGroups.
 * 
 * <!-- end-model-doc -->
 * @see goedegep.properties.model.PropertiesFactory
 * @model kind="package"
 * @generated
 */
public interface PropertiesPackage extends EPackage {
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "model";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://goedegep.properties/model";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "properties";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  PropertiesPackage eINSTANCE = goedegep.properties.model.impl.PropertiesPackageImpl.init();

  /**
   * The meta object id for the '{@link goedegep.properties.model.impl.PropertyDescriptorGroupImpl <em>Property Descriptor Group</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.properties.model.impl.PropertyDescriptorGroupImpl
   * @see goedegep.properties.model.impl.PropertiesPackageImpl#getPropertyDescriptorGroup()
   * @generated
   */
  int PROPERTY_DESCRIPTOR_GROUP = 0;

  /**
   * The feature id for the '<em><b>Property Descriptors</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_DESCRIPTOR_GROUP__PROPERTY_DESCRIPTORS = 0;

  /**
   * The feature id for the '<em><b>Property Descriptor Groups</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_DESCRIPTOR_GROUP__PROPERTY_DESCRIPTOR_GROUPS = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_DESCRIPTOR_GROUP__NAME = 2;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_DESCRIPTOR_GROUP__DESCRIPTION = 3;

  /**
   * The feature id for the '<em><b>Package Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_DESCRIPTOR_GROUP__PACKAGE_NAME = 4;

  /**
   * The feature id for the '<em><b>Registry Class Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_DESCRIPTOR_GROUP__REGISTRY_CLASS_NAME = 5;

  /**
   * The number of structural features of the '<em>Property Descriptor Group</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_DESCRIPTOR_GROUP_FEATURE_COUNT = 6;

  /**
   * The operation id for the '<em>Get Property Descriptor</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_DESCRIPTOR_GROUP___GET_PROPERTY_DESCRIPTOR__STRING = 0;

  /**
   * The operation id for the '<em>Get Property Descriptor Group</em>' operation.
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_DESCRIPTOR_GROUP___GET_PROPERTY_DESCRIPTOR_GROUP__STRING = 1;

  /**
   * The number of operations of the '<em>Property Descriptor Group</em>' class.
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_DESCRIPTOR_GROUP_OPERATION_COUNT = 2;

  /**
   * The meta object id for the '{@link goedegep.properties.model.impl.PropertyDescriptorImpl <em>Property Descriptor</em>}' class.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @see goedegep.properties.model.impl.PropertyDescriptorImpl
   * @see goedegep.properties.model.impl.PropertiesPackageImpl#getPropertyDescriptor()
   * @generated
   */
  int PROPERTY_DESCRIPTOR = 1;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_DESCRIPTOR__TYPE = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_DESCRIPTOR__NAME = 1;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_DESCRIPTOR__DESCRIPTION = 2;

  /**
   * The feature id for the '<em><b>Display Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_DESCRIPTOR__DISPLAY_NAME = 3;

  /**
   * The feature id for the '<em><b>Registry Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_DESCRIPTOR__REGISTRY_NAME = 4;

  /**
   * The feature id for the '<em><b>Initial Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_DESCRIPTOR__INITIAL_VALUE = 5;

  /**
   * The feature id for the '<em><b>User Settable</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_DESCRIPTOR__USER_SETTABLE = 6;

  /**
   * The feature id for the '<em><b>Install Initial Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_DESCRIPTOR__INSTALL_INITIAL_VALUE = 7;

  /**
   * The number of structural features of the '<em>Property Descriptor</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_DESCRIPTOR_FEATURE_COUNT = 8;

  /**
   * The number of operations of the '<em>Property Descriptor</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_DESCRIPTOR_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.properties.model.impl.FilePropertyDescriptorImpl <em>File Property Descriptor</em>}' class.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @see goedegep.properties.model.impl.FilePropertyDescriptorImpl
   * @see goedegep.properties.model.impl.PropertiesPackageImpl#getFilePropertyDescriptor()
   * @generated
   */
  int FILE_PROPERTY_DESCRIPTOR = 2;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_PROPERTY_DESCRIPTOR__TYPE = PROPERTY_DESCRIPTOR__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_PROPERTY_DESCRIPTOR__NAME = PROPERTY_DESCRIPTOR__NAME;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_PROPERTY_DESCRIPTOR__DESCRIPTION = PROPERTY_DESCRIPTOR__DESCRIPTION;

  /**
   * The feature id for the '<em><b>Display Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_PROPERTY_DESCRIPTOR__DISPLAY_NAME = PROPERTY_DESCRIPTOR__DISPLAY_NAME;

  /**
   * The feature id for the '<em><b>Registry Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_PROPERTY_DESCRIPTOR__REGISTRY_NAME = PROPERTY_DESCRIPTOR__REGISTRY_NAME;

  /**
   * The feature id for the '<em><b>Initial Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_PROPERTY_DESCRIPTOR__INITIAL_VALUE = PROPERTY_DESCRIPTOR__INITIAL_VALUE;

  /**
   * The feature id for the '<em><b>User Settable</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_PROPERTY_DESCRIPTOR__USER_SETTABLE = PROPERTY_DESCRIPTOR__USER_SETTABLE;

  /**
   * The feature id for the '<em><b>Install Initial Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_PROPERTY_DESCRIPTOR__INSTALL_INITIAL_VALUE = PROPERTY_DESCRIPTOR__INSTALL_INITIAL_VALUE;

  /**
   * The feature id for the '<em><b>File Extensions</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_PROPERTY_DESCRIPTOR__FILE_EXTENSIONS = PROPERTY_DESCRIPTOR_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>File Property Descriptor</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_PROPERTY_DESCRIPTOR_FEATURE_COUNT = PROPERTY_DESCRIPTOR_FEATURE_COUNT + 1;

  /**
   * The number of operations of the '<em>File Property Descriptor</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_PROPERTY_DESCRIPTOR_OPERATION_COUNT = PROPERTY_DESCRIPTOR_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.properties.model.impl.PropertyImpl <em>Property</em>}' class.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @see goedegep.properties.model.impl.PropertyImpl
   * @see goedegep.properties.model.impl.PropertiesPackageImpl#getProperty()
   * @generated
   */
  int PROPERTY = 3;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__NAME = 0;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__VALUE = 1;

  /**
   * The number of structural features of the '<em>Property</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Property</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.properties.model.impl.PropertyGroupImpl <em>Property Group</em>}' class.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @see goedegep.properties.model.impl.PropertyGroupImpl
   * @see goedegep.properties.model.impl.PropertiesPackageImpl#getPropertyGroup()
   * @generated
   */
  int PROPERTY_GROUP = 4;

  /**
   * The feature id for the '<em><b>Properties</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_GROUP__PROPERTIES = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_GROUP__NAME = 1;

  /**
   * The feature id for the '<em><b>Property Groups</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_GROUP__PROPERTY_GROUPS = 2;

  /**
   * The number of structural features of the '<em>Property Group</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_GROUP_FEATURE_COUNT = 3;

  /**
   * The number of operations of the '<em>Property Group</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_GROUP_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.properties.model.PropertyType <em>Property Type</em>}' enum.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @see goedegep.properties.model.PropertyType
   * @see goedegep.properties.model.impl.PropertiesPackageImpl#getPropertyType()
   * @generated
   */
  int PROPERTY_TYPE = 5;

  /**
   * Returns the meta object for class '{@link goedegep.properties.model.PropertyDescriptorGroup <em>Property Descriptor Group</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Property Descriptor Group</em>'.
   * @see goedegep.properties.model.PropertyDescriptorGroup
   * @generated
   */
  EClass getPropertyDescriptorGroup();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.properties.model.PropertyDescriptorGroup#getPropertyDescriptors <em>Property Descriptors</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Property Descriptors</em>'.
   * @see goedegep.properties.model.PropertyDescriptorGroup#getPropertyDescriptors()
   * @see #getPropertyDescriptorGroup()
   * @generated
   */
  EReference getPropertyDescriptorGroup_PropertyDescriptors();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.properties.model.PropertyDescriptorGroup#getPropertyDescriptorGroups <em>Property Descriptor Groups</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Property Descriptor Groups</em>'.
   * @see goedegep.properties.model.PropertyDescriptorGroup#getPropertyDescriptorGroups()
   * @see #getPropertyDescriptorGroup()
   * @generated
   */
  EReference getPropertyDescriptorGroup_PropertyDescriptorGroups();

  /**
   * Returns the meta object for the attribute '{@link goedegep.properties.model.PropertyDescriptorGroup#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see goedegep.properties.model.PropertyDescriptorGroup#getName()
   * @see #getPropertyDescriptorGroup()
   * @generated
   */
  EAttribute getPropertyDescriptorGroup_Name();

  /**
   * Returns the meta object for the attribute '{@link goedegep.properties.model.PropertyDescriptorGroup#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see goedegep.properties.model.PropertyDescriptorGroup#getDescription()
   * @see #getPropertyDescriptorGroup()
   * @generated
   */
  EAttribute getPropertyDescriptorGroup_Description();

  /**
   * Returns the meta object for the attribute '{@link goedegep.properties.model.PropertyDescriptorGroup#getPackageName <em>Package Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Package Name</em>'.
   * @see goedegep.properties.model.PropertyDescriptorGroup#getPackageName()
   * @see #getPropertyDescriptorGroup()
   * @generated
   */
  EAttribute getPropertyDescriptorGroup_PackageName();

  /**
   * Returns the meta object for the attribute '{@link goedegep.properties.model.PropertyDescriptorGroup#getRegistryClassName <em>Registry Class Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Registry Class Name</em>'.
   * @see goedegep.properties.model.PropertyDescriptorGroup#getRegistryClassName()
   * @see #getPropertyDescriptorGroup()
   * @generated
   */
  EAttribute getPropertyDescriptorGroup_RegistryClassName();

  /**
   * Returns the meta object for the '{@link goedegep.properties.model.PropertyDescriptorGroup#getPropertyDescriptor(java.lang.String) <em>Get Property Descriptor</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Property Descriptor</em>' operation.
   * @see goedegep.properties.model.PropertyDescriptorGroup#getPropertyDescriptor(java.lang.String)
   * @generated
   */
  EOperation getPropertyDescriptorGroup__GetPropertyDescriptor__String();

  /**
   * Returns the meta object for the '{@link goedegep.properties.model.PropertyDescriptorGroup#getPropertyDescriptorGroup(java.lang.String) <em>Get Property Descriptor Group</em>}' operation.
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Property Descriptor Group</em>' operation.
   * @see goedegep.properties.model.PropertyDescriptorGroup#getPropertyDescriptorGroup(java.lang.String)
   * @generated
   */
  EOperation getPropertyDescriptorGroup__GetPropertyDescriptorGroup__String();

  /**
   * Returns the meta object for class '{@link goedegep.properties.model.PropertyDescriptor <em>Property Descriptor</em>}'.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @return the meta object for class '<em>Property Descriptor</em>'.
   * @see goedegep.properties.model.PropertyDescriptor
   * @generated
   */
  EClass getPropertyDescriptor();

  /**
   * Returns the meta object for the attribute '{@link goedegep.properties.model.PropertyDescriptor#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Type</em>'.
   * @see goedegep.properties.model.PropertyDescriptor#getType()
   * @see #getPropertyDescriptor()
   * @generated
   */
  EAttribute getPropertyDescriptor_Type();

  /**
   * Returns the meta object for the attribute '{@link goedegep.properties.model.PropertyDescriptor#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see goedegep.properties.model.PropertyDescriptor#getName()
   * @see #getPropertyDescriptor()
   * @generated
   */
  EAttribute getPropertyDescriptor_Name();

  /**
   * Returns the meta object for the attribute '{@link goedegep.properties.model.PropertyDescriptor#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see goedegep.properties.model.PropertyDescriptor#getDescription()
   * @see #getPropertyDescriptor()
   * @generated
   */
  EAttribute getPropertyDescriptor_Description();

  /**
   * Returns the meta object for the attribute '{@link goedegep.properties.model.PropertyDescriptor#getDisplayName <em>Display Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Display Name</em>'.
   * @see goedegep.properties.model.PropertyDescriptor#getDisplayName()
   * @see #getPropertyDescriptor()
   * @generated
   */
  EAttribute getPropertyDescriptor_DisplayName();

  /**
   * Returns the meta object for the attribute '{@link goedegep.properties.model.PropertyDescriptor#getRegistryName <em>Registry Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Registry Name</em>'.
   * @see goedegep.properties.model.PropertyDescriptor#getRegistryName()
   * @see #getPropertyDescriptor()
   * @generated
   */
  EAttribute getPropertyDescriptor_RegistryName();

  /**
   * Returns the meta object for the attribute '{@link goedegep.properties.model.PropertyDescriptor#getInitialValue <em>Initial Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Initial Value</em>'.
   * @see goedegep.properties.model.PropertyDescriptor#getInitialValue()
   * @see #getPropertyDescriptor()
   * @generated
   */
  EAttribute getPropertyDescriptor_InitialValue();

  /**
   * Returns the meta object for the attribute '{@link goedegep.properties.model.PropertyDescriptor#isUserSettable <em>User Settable</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>User Settable</em>'.
   * @see goedegep.properties.model.PropertyDescriptor#isUserSettable()
   * @see #getPropertyDescriptor()
   * @generated
   */
  EAttribute getPropertyDescriptor_UserSettable();

  /**
   * Returns the meta object for the attribute '{@link goedegep.properties.model.PropertyDescriptor#isInstallInitialValue <em>Install Initial Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Install Initial Value</em>'.
   * @see goedegep.properties.model.PropertyDescriptor#isInstallInitialValue()
   * @see #getPropertyDescriptor()
   * @generated
   */
  EAttribute getPropertyDescriptor_InstallInitialValue();

  /**
   * Returns the meta object for class '{@link goedegep.properties.model.FilePropertyDescriptor <em>File Property Descriptor</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>File Property Descriptor</em>'.
   * @see goedegep.properties.model.FilePropertyDescriptor
   * @generated
   */
  EClass getFilePropertyDescriptor();

  /**
   * Returns the meta object for the attribute list '{@link goedegep.properties.model.FilePropertyDescriptor#getFileExtensions <em>File Extensions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>File Extensions</em>'.
   * @see goedegep.properties.model.FilePropertyDescriptor#getFileExtensions()
   * @see #getFilePropertyDescriptor()
   * @generated
   */
  EAttribute getFilePropertyDescriptor_FileExtensions();

  /**
   * Returns the meta object for class '{@link goedegep.properties.model.Property <em>Property</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Property</em>'.
   * @see goedegep.properties.model.Property
   * @generated
   */
  EClass getProperty();

  /**
   * Returns the meta object for the attribute '{@link goedegep.properties.model.Property#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see goedegep.properties.model.Property#getName()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_Name();

  /**
   * Returns the meta object for the attribute '{@link goedegep.properties.model.Property#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see goedegep.properties.model.Property#getValue()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_Value();

  /**
   * Returns the meta object for class '{@link goedegep.properties.model.PropertyGroup <em>Property Group</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Property Group</em>'.
   * @see goedegep.properties.model.PropertyGroup
   * @generated
   */
  EClass getPropertyGroup();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.properties.model.PropertyGroup#getProperties <em>Properties</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Properties</em>'.
   * @see goedegep.properties.model.PropertyGroup#getProperties()
   * @see #getPropertyGroup()
   * @generated
   */
  EReference getPropertyGroup_Properties();

  /**
   * Returns the meta object for the attribute '{@link goedegep.properties.model.PropertyGroup#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see goedegep.properties.model.PropertyGroup#getName()
   * @see #getPropertyGroup()
   * @generated
   */
  EAttribute getPropertyGroup_Name();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.properties.model.PropertyGroup#getPropertyGroups <em>Property Groups</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Property Groups</em>'.
   * @see goedegep.properties.model.PropertyGroup#getPropertyGroups()
   * @see #getPropertyGroup()
   * @generated
   */
  EReference getPropertyGroup_PropertyGroups();

  /**
   * Returns the meta object for enum '{@link goedegep.properties.model.PropertyType <em>Property Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Property Type</em>'.
   * @see goedegep.properties.model.PropertyType
   * @generated
   */
  EEnum getPropertyType();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  PropertiesFactory getPropertiesFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals {
    /**
    	 * The meta object literal for the '{@link goedegep.properties.model.impl.PropertyDescriptorGroupImpl <em>Property Descriptor Group</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.properties.model.impl.PropertyDescriptorGroupImpl
    	 * @see goedegep.properties.model.impl.PropertiesPackageImpl#getPropertyDescriptorGroup()
    	 * @generated
    	 */
    EClass PROPERTY_DESCRIPTOR_GROUP = eINSTANCE.getPropertyDescriptorGroup();

    /**
    	 * The meta object literal for the '<em><b>Property Descriptors</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference PROPERTY_DESCRIPTOR_GROUP__PROPERTY_DESCRIPTORS = eINSTANCE
        .getPropertyDescriptorGroup_PropertyDescriptors();

    /**
    	 * The meta object literal for the '<em><b>Property Descriptor Groups</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference PROPERTY_DESCRIPTOR_GROUP__PROPERTY_DESCRIPTOR_GROUPS = eINSTANCE
        .getPropertyDescriptorGroup_PropertyDescriptorGroups();

    /**
    	 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PROPERTY_DESCRIPTOR_GROUP__NAME = eINSTANCE.getPropertyDescriptorGroup_Name();

    /**
    	 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PROPERTY_DESCRIPTOR_GROUP__DESCRIPTION = eINSTANCE.getPropertyDescriptorGroup_Description();

    /**
    	 * The meta object literal for the '<em><b>Package Name</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PROPERTY_DESCRIPTOR_GROUP__PACKAGE_NAME = eINSTANCE.getPropertyDescriptorGroup_PackageName();

    /**
    	 * The meta object literal for the '<em><b>Registry Class Name</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PROPERTY_DESCRIPTOR_GROUP__REGISTRY_CLASS_NAME = eINSTANCE
        .getPropertyDescriptorGroup_RegistryClassName();

    /**
    	 * The meta object literal for the '<em><b>Get Property Descriptor</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation PROPERTY_DESCRIPTOR_GROUP___GET_PROPERTY_DESCRIPTOR__STRING = eINSTANCE
        .getPropertyDescriptorGroup__GetPropertyDescriptor__String();

    /**
    	 * The meta object literal for the '<em><b>Get Property Descriptor Group</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation PROPERTY_DESCRIPTOR_GROUP___GET_PROPERTY_DESCRIPTOR_GROUP__STRING = eINSTANCE
        .getPropertyDescriptorGroup__GetPropertyDescriptorGroup__String();

    /**
    	 * The meta object literal for the '{@link goedegep.properties.model.impl.PropertyDescriptorImpl <em>Property Descriptor</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.properties.model.impl.PropertyDescriptorImpl
    	 * @see goedegep.properties.model.impl.PropertiesPackageImpl#getPropertyDescriptor()
    	 * @generated
    	 */
    EClass PROPERTY_DESCRIPTOR = eINSTANCE.getPropertyDescriptor();

    /**
    	 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PROPERTY_DESCRIPTOR__TYPE = eINSTANCE.getPropertyDescriptor_Type();

    /**
    	 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PROPERTY_DESCRIPTOR__NAME = eINSTANCE.getPropertyDescriptor_Name();

    /**
    	 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PROPERTY_DESCRIPTOR__DESCRIPTION = eINSTANCE.getPropertyDescriptor_Description();

    /**
    	 * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PROPERTY_DESCRIPTOR__DISPLAY_NAME = eINSTANCE.getPropertyDescriptor_DisplayName();

    /**
    	 * The meta object literal for the '<em><b>Registry Name</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PROPERTY_DESCRIPTOR__REGISTRY_NAME = eINSTANCE.getPropertyDescriptor_RegistryName();

    /**
    	 * The meta object literal for the '<em><b>Initial Value</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PROPERTY_DESCRIPTOR__INITIAL_VALUE = eINSTANCE.getPropertyDescriptor_InitialValue();

    /**
    	 * The meta object literal for the '<em><b>User Settable</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PROPERTY_DESCRIPTOR__USER_SETTABLE = eINSTANCE.getPropertyDescriptor_UserSettable();

    /**
    	 * The meta object literal for the '<em><b>Install Initial Value</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PROPERTY_DESCRIPTOR__INSTALL_INITIAL_VALUE = eINSTANCE.getPropertyDescriptor_InstallInitialValue();

    /**
    	 * The meta object literal for the '{@link goedegep.properties.model.impl.FilePropertyDescriptorImpl <em>File Property Descriptor</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.properties.model.impl.FilePropertyDescriptorImpl
    	 * @see goedegep.properties.model.impl.PropertiesPackageImpl#getFilePropertyDescriptor()
    	 * @generated
    	 */
    EClass FILE_PROPERTY_DESCRIPTOR = eINSTANCE.getFilePropertyDescriptor();

    /**
    	 * The meta object literal for the '<em><b>File Extensions</b></em>' attribute list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute FILE_PROPERTY_DESCRIPTOR__FILE_EXTENSIONS = eINSTANCE.getFilePropertyDescriptor_FileExtensions();

    /**
    	 * The meta object literal for the '{@link goedegep.properties.model.impl.PropertyImpl <em>Property</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.properties.model.impl.PropertyImpl
    	 * @see goedegep.properties.model.impl.PropertiesPackageImpl#getProperty()
    	 * @generated
    	 */
    EClass PROPERTY = eINSTANCE.getProperty();

    /**
    	 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PROPERTY__NAME = eINSTANCE.getProperty_Name();

    /**
    	 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PROPERTY__VALUE = eINSTANCE.getProperty_Value();

    /**
    	 * The meta object literal for the '{@link goedegep.properties.model.impl.PropertyGroupImpl <em>Property Group</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.properties.model.impl.PropertyGroupImpl
    	 * @see goedegep.properties.model.impl.PropertiesPackageImpl#getPropertyGroup()
    	 * @generated
    	 */
    EClass PROPERTY_GROUP = eINSTANCE.getPropertyGroup();

    /**
    	 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference PROPERTY_GROUP__PROPERTIES = eINSTANCE.getPropertyGroup_Properties();

    /**
    	 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PROPERTY_GROUP__NAME = eINSTANCE.getPropertyGroup_Name();

    /**
    	 * The meta object literal for the '<em><b>Property Groups</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference PROPERTY_GROUP__PROPERTY_GROUPS = eINSTANCE.getPropertyGroup_PropertyGroups();

    /**
    	 * The meta object literal for the '{@link goedegep.properties.model.PropertyType <em>Property Type</em>}' enum.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.properties.model.PropertyType
    	 * @see goedegep.properties.model.impl.PropertiesPackageImpl#getPropertyType()
    	 * @generated
    	 */
    EEnum PROPERTY_TYPE = eINSTANCE.getPropertyType();

  }

} //PropertiesPackage
