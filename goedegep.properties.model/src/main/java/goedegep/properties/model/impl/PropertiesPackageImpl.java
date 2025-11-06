/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.properties.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import goedegep.properties.model.FilePropertyDescriptor;
import goedegep.properties.model.PropertiesFactory;
import goedegep.properties.model.PropertiesPackage;
import goedegep.properties.model.Property;
import goedegep.properties.model.PropertyDescriptor;
import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.properties.model.PropertyGroup;
import goedegep.properties.model.PropertyType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PropertiesPackageImpl extends EPackageImpl implements PropertiesPackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass propertyDescriptorGroupEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass propertyDescriptorEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass filePropertyDescriptorEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass propertyEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass propertyGroupEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum propertyTypeEEnum = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see goedegep.properties.model.PropertiesPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private PropertiesPackageImpl() {
    super(eNS_URI, PropertiesFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   *
   * <p>This method is used to initialize {@link PropertiesPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static PropertiesPackage init() {
    if (isInited)
      return (PropertiesPackage) EPackage.Registry.INSTANCE.getEPackage(PropertiesPackage.eNS_URI);

    // Obtain or create and register package
    Object registeredPropertiesPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
    PropertiesPackageImpl thePropertiesPackage = registeredPropertiesPackage instanceof PropertiesPackageImpl
        ? (PropertiesPackageImpl) registeredPropertiesPackage
        : new PropertiesPackageImpl();

    isInited = true;

    // Create package meta-data objects
    thePropertiesPackage.createPackageContents();

    // Initialize created meta-data
    thePropertiesPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    thePropertiesPackage.freeze();

    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(PropertiesPackage.eNS_URI, thePropertiesPackage);
    return thePropertiesPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getPropertyDescriptorGroup() {
    return propertyDescriptorGroupEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getPropertyDescriptorGroup_PropertyDescriptors() {
    return (EReference) propertyDescriptorGroupEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPropertyDescriptorGroup_Name() {
    return (EAttribute) propertyDescriptorGroupEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPropertyDescriptorGroup_Description() {
    return (EAttribute) propertyDescriptorGroupEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getPropertyDescriptorGroup__GetPropertyDescriptor__String() {
    return propertyDescriptorGroupEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getPropertyDescriptor() {
    return propertyDescriptorEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPropertyDescriptor_Type() {
    return (EAttribute) propertyDescriptorEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPropertyDescriptor_Name() {
    return (EAttribute) propertyDescriptorEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPropertyDescriptor_Description() {
    return (EAttribute) propertyDescriptorEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPropertyDescriptor_DisplayName() {
    return (EAttribute) propertyDescriptorEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPropertyDescriptor_RegistryName() {
    return (EAttribute) propertyDescriptorEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getFilePropertyDescriptor() {
    return filePropertyDescriptorEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getFilePropertyDescriptor_FileExtensions() {
    return (EAttribute) filePropertyDescriptorEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getProperty() {
    return propertyEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getProperty_Name() {
    return (EAttribute) propertyEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getProperty_Value() {
    return (EAttribute) propertyEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getPropertyGroup() {
    return propertyGroupEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getPropertyGroup_Properties() {
    return (EReference) propertyGroupEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPropertyGroup_Name() {
    return (EAttribute) propertyGroupEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EEnum getPropertyType() {
    return propertyTypeEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PropertiesFactory getPropertiesFactory() {
    return (PropertiesFactory) getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents() {
    if (isCreated)
      return;
    isCreated = true;

    // Create classes and their features
    propertyDescriptorGroupEClass = createEClass(PROPERTY_DESCRIPTOR_GROUP);
    createEReference(propertyDescriptorGroupEClass, PROPERTY_DESCRIPTOR_GROUP__PROPERTY_DESCRIPTORS);
    createEAttribute(propertyDescriptorGroupEClass, PROPERTY_DESCRIPTOR_GROUP__NAME);
    createEAttribute(propertyDescriptorGroupEClass, PROPERTY_DESCRIPTOR_GROUP__DESCRIPTION);
    createEOperation(propertyDescriptorGroupEClass, PROPERTY_DESCRIPTOR_GROUP___GET_PROPERTY_DESCRIPTOR__STRING);

    propertyDescriptorEClass = createEClass(PROPERTY_DESCRIPTOR);
    createEAttribute(propertyDescriptorEClass, PROPERTY_DESCRIPTOR__TYPE);
    createEAttribute(propertyDescriptorEClass, PROPERTY_DESCRIPTOR__NAME);
    createEAttribute(propertyDescriptorEClass, PROPERTY_DESCRIPTOR__DESCRIPTION);
    createEAttribute(propertyDescriptorEClass, PROPERTY_DESCRIPTOR__DISPLAY_NAME);
    createEAttribute(propertyDescriptorEClass, PROPERTY_DESCRIPTOR__REGISTRY_NAME);

    filePropertyDescriptorEClass = createEClass(FILE_PROPERTY_DESCRIPTOR);
    createEAttribute(filePropertyDescriptorEClass, FILE_PROPERTY_DESCRIPTOR__FILE_EXTENSIONS);

    propertyEClass = createEClass(PROPERTY);
    createEAttribute(propertyEClass, PROPERTY__NAME);
    createEAttribute(propertyEClass, PROPERTY__VALUE);

    propertyGroupEClass = createEClass(PROPERTY_GROUP);
    createEReference(propertyGroupEClass, PROPERTY_GROUP__PROPERTIES);
    createEAttribute(propertyGroupEClass, PROPERTY_GROUP__NAME);

    // Create enums
    propertyTypeEEnum = createEEnum(PROPERTY_TYPE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents() {
    if (isInitialized)
      return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    filePropertyDescriptorEClass.getESuperTypes().add(this.getPropertyDescriptor());

    // Initialize classes, features, and operations; add parameters
    initEClass(propertyDescriptorGroupEClass, PropertyDescriptorGroup.class, "PropertyDescriptorGroup", !IS_ABSTRACT,
        !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getPropertyDescriptorGroup_PropertyDescriptors(), this.getPropertyDescriptor(), null,
        "propertyDescriptors", null, 0, -1, PropertyDescriptorGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPropertyDescriptorGroup_Name(), ecorePackage.getEString(), "name", null, 0, 1,
        PropertyDescriptorGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPropertyDescriptorGroup_Description(), ecorePackage.getEString(), "description", null, 0, 1,
        PropertyDescriptorGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);

    EOperation op = initEOperation(getPropertyDescriptorGroup__GetPropertyDescriptor__String(),
        this.getPropertyDescriptor(), "getPropertyDescriptor", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "propertyName", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(propertyDescriptorEClass, PropertyDescriptor.class, "PropertyDescriptor", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getPropertyDescriptor_Type(), this.getPropertyType(), "type", "STRING", 1, 1,
        PropertyDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPropertyDescriptor_Name(), ecorePackage.getEString(), "name", null, 1, 1,
        PropertyDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPropertyDescriptor_Description(), ecorePackage.getEString(), "description", null, 0, 1,
        PropertyDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPropertyDescriptor_DisplayName(), ecorePackage.getEString(), "displayName", null, 0, 1,
        PropertyDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPropertyDescriptor_RegistryName(), ecorePackage.getEString(), "registryName", null, 0, 1,
        PropertyDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);

    initEClass(filePropertyDescriptorEClass, FilePropertyDescriptor.class, "FilePropertyDescriptor", !IS_ABSTRACT,
        !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getFilePropertyDescriptor_FileExtensions(), ecorePackage.getEString(), "fileExtensions", null, 0, -1,
        FilePropertyDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);

    initEClass(propertyEClass, Property.class, "Property", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getProperty_Name(), ecorePackage.getEString(), "name", null, 1, 1, Property.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getProperty_Value(), ecorePackage.getEString(), "value", null, 1, 1, Property.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(propertyGroupEClass, PropertyGroup.class, "PropertyGroup", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEReference(getPropertyGroup_Properties(), this.getProperty(), null, "properties", null, 0, -1,
        PropertyGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
        !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPropertyGroup_Name(), ecorePackage.getEString(), "name", null, 1, 1, PropertyGroup.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Initialize enums and add enum literals
    initEEnum(propertyTypeEEnum, PropertyType.class, "PropertyType");
    addEEnumLiteral(propertyTypeEEnum, PropertyType.DIRECTORY);
    addEEnumLiteral(propertyTypeEEnum, PropertyType.FILE);
    addEEnumLiteral(propertyTypeEEnum, PropertyType.STRING);
    addEEnumLiteral(propertyTypeEEnum, PropertyType.BOOLEAN);

    // Create resource
    createResource(eNS_URI);
  }

} //PropertiesPackageImpl
