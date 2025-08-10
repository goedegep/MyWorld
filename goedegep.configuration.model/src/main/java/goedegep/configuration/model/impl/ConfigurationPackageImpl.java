/**
 */
package goedegep.configuration.model.impl;

import goedegep.configuration.model.ConfigurationFactory;
import goedegep.configuration.model.ConfigurationPackage;
import goedegep.configuration.model.Look;
import goedegep.configuration.model.LookInfo;
import goedegep.configuration.model.ModuleLook;
import goedegep.types.model.TypesPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ConfigurationPackageImpl extends EPackageImpl implements ConfigurationPackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass lookInfoEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass moduleLookEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass lookEClass = null;

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
   * @see goedegep.configuration.model.ConfigurationPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private ConfigurationPackageImpl() {
    super(eNS_URI, ConfigurationFactory.eINSTANCE);
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
   * <p>This method is used to initialize {@link ConfigurationPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static ConfigurationPackage init() {
    if (isInited)
      return (ConfigurationPackage) EPackage.Registry.INSTANCE.getEPackage(ConfigurationPackage.eNS_URI);

    // Obtain or create and register package
    Object registeredConfigurationPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
    ConfigurationPackageImpl theConfigurationPackage = registeredConfigurationPackage instanceof ConfigurationPackageImpl
        ? (ConfigurationPackageImpl) registeredConfigurationPackage
        : new ConfigurationPackageImpl();

    isInited = true;

    // Initialize simple dependencies
    TypesPackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theConfigurationPackage.createPackageContents();

    // Initialize created meta-data
    theConfigurationPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theConfigurationPackage.freeze();

    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(ConfigurationPackage.eNS_URI, theConfigurationPackage);
    return theConfigurationPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getLookInfo() {
    return lookInfoEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getLookInfo_ModuleLooks() {
    return (EReference) lookInfoEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getModuleLook() {
    return moduleLookEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getModuleLook_Look() {
    return (EReference) moduleLookEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getModuleLook_ModuleName() {
    return (EAttribute) moduleLookEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getModuleLook_ResourcesClassName() {
    return (EAttribute) moduleLookEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getLook() {
    return lookEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLook_BackgroundColor() {
    return (EAttribute) lookEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLook_ButtonBackgroundColor() {
    return (EAttribute) lookEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLook_PanelBackgroundColor() {
    return (EAttribute) lookEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLook_ListBackgroundColor() {
    return (EAttribute) lookEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLook_LabelBackgroundColor() {
    return (EAttribute) lookEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLook_BoxBackgroundColor() {
    return (EAttribute) lookEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLook_TextFieldBackgroundColor() {
    return (EAttribute) lookEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ConfigurationFactory getConfigurationFactory() {
    return (ConfigurationFactory) getEFactoryInstance();
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
    lookEClass = createEClass(LOOK);
    createEAttribute(lookEClass, LOOK__BACKGROUND_COLOR);
    createEAttribute(lookEClass, LOOK__BUTTON_BACKGROUND_COLOR);
    createEAttribute(lookEClass, LOOK__PANEL_BACKGROUND_COLOR);
    createEAttribute(lookEClass, LOOK__LIST_BACKGROUND_COLOR);
    createEAttribute(lookEClass, LOOK__LABEL_BACKGROUND_COLOR);
    createEAttribute(lookEClass, LOOK__BOX_BACKGROUND_COLOR);
    createEAttribute(lookEClass, LOOK__TEXT_FIELD_BACKGROUND_COLOR);

    moduleLookEClass = createEClass(MODULE_LOOK);
    createEReference(moduleLookEClass, MODULE_LOOK__LOOK);
    createEAttribute(moduleLookEClass, MODULE_LOOK__MODULE_NAME);
    createEAttribute(moduleLookEClass, MODULE_LOOK__RESOURCES_CLASS_NAME);

    lookInfoEClass = createEClass(LOOK_INFO);
    createEReference(lookInfoEClass, LOOK_INFO__MODULE_LOOKS);
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

    // Obtain other dependent packages
    TypesPackage theTypesPackage = (TypesPackage) EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes

    // Initialize classes, features, and operations; add parameters
    initEClass(lookEClass, Look.class, "Look", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getLook_BackgroundColor(), theTypesPackage.getEColor(), "backgroundColor", null, 0, 1, Look.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLook_ButtonBackgroundColor(), theTypesPackage.getEColor(), "buttonBackgroundColor", null, 0, 1,
        Look.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEAttribute(getLook_PanelBackgroundColor(), theTypesPackage.getEColor(), "panelBackgroundColor", "1,2,3", 0, 1,
        Look.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEAttribute(getLook_ListBackgroundColor(), theTypesPackage.getEColor(), "listBackgroundColor", null, 0, 1,
        Look.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEAttribute(getLook_LabelBackgroundColor(), theTypesPackage.getEColor(), "labelBackgroundColor", null, 0, 1,
        Look.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEAttribute(getLook_BoxBackgroundColor(), theTypesPackage.getEColor(), "boxBackgroundColor", null, 0, 1,
        Look.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEAttribute(getLook_TextFieldBackgroundColor(), theTypesPackage.getEColor(), "textFieldBackgroundColor", null, 0,
        1, Look.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);

    initEClass(moduleLookEClass, ModuleLook.class, "ModuleLook", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEReference(getModuleLook_Look(), this.getLook(), null, "look", null, 1, 1, ModuleLook.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEAttribute(getModuleLook_ModuleName(), ecorePackage.getEString(), "moduleName", null, 0, 1, ModuleLook.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getModuleLook_ResourcesClassName(), ecorePackage.getEString(), "resourcesClassName", null, 0, 1,
        ModuleLook.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);

    initEClass(lookInfoEClass, LookInfo.class, "LookInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getLookInfo_ModuleLooks(), this.getModuleLook(), null, "moduleLooks", null, 0, -1, LookInfo.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //ConfigurationPackageImpl
