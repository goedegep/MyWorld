/**
 */
package goedegep.configuration.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see goedegep.configuration.model.ConfigurationFactory
 * @model kind="package"
 * @generated
 */
public interface ConfigurationPackage extends EPackage {
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
  String eNS_URI = "http:///goedegep.configuration/model";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "configuration";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  ConfigurationPackage eINSTANCE = goedegep.configuration.model.impl.ConfigurationPackageImpl.init();

  /**
   * The meta object id for the '{@link goedegep.configuration.model.impl.LookInfoImpl <em>Look Info</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.configuration.model.impl.LookInfoImpl
   * @see goedegep.configuration.model.impl.ConfigurationPackageImpl#getLookInfo()
   * @generated
   */
  int LOOK_INFO = 2;

  /**
   * The meta object id for the '{@link goedegep.configuration.model.impl.ModuleLookImpl <em>Module Look</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.configuration.model.impl.ModuleLookImpl
   * @see goedegep.configuration.model.impl.ConfigurationPackageImpl#getModuleLook()
   * @generated
   */
  int MODULE_LOOK = 1;

  /**
   * The meta object id for the '{@link goedegep.configuration.model.impl.LookImpl <em>Look</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.configuration.model.impl.LookImpl
   * @see goedegep.configuration.model.impl.ConfigurationPackageImpl#getLook()
   * @generated
   */
  int LOOK = 0;

  /**
   * The feature id for the '<em><b>Background Color</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOK__BACKGROUND_COLOR = 0;

  /**
   * The feature id for the '<em><b>Button Background Color</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOK__BUTTON_BACKGROUND_COLOR = 1;

  /**
   * The feature id for the '<em><b>Panel Background Color</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOK__PANEL_BACKGROUND_COLOR = 2;

  /**
   * The feature id for the '<em><b>List Background Color</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOK__LIST_BACKGROUND_COLOR = 3;

  /**
   * The feature id for the '<em><b>Label Background Color</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOK__LABEL_BACKGROUND_COLOR = 4;

  /**
   * The feature id for the '<em><b>Box Background Color</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOK__BOX_BACKGROUND_COLOR = 5;

  /**
   * The feature id for the '<em><b>Text Field Background Color</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOK__TEXT_FIELD_BACKGROUND_COLOR = 6;

  /**
   * The number of structural features of the '<em>Look</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOK_FEATURE_COUNT = 7;

  /**
   * The number of operations of the '<em>Look</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOK_OPERATION_COUNT = 0;

  /**
   * The feature id for the '<em><b>Look</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODULE_LOOK__LOOK = 0;

  /**
   * The feature id for the '<em><b>Module Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODULE_LOOK__MODULE_NAME = 1;

  /**
   * The feature id for the '<em><b>Module Looks</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODULE_LOOK__MODULE_LOOKS = 2;

  /**
   * The feature id for the '<em><b>Resources Class Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODULE_LOOK__RESOURCES_CLASS_NAME = 3;

  /**
   * The feature id for the '<em><b>Parent Module Look</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODULE_LOOK__PARENT_MODULE_LOOK = 4;

  /**
   * The number of structural features of the '<em>Module Look</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODULE_LOOK_FEATURE_COUNT = 5;

  /**
   * The number of operations of the '<em>Module Look</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODULE_LOOK_OPERATION_COUNT = 0;

  /**
   * The feature id for the '<em><b>Module Looks</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOK_INFO__MODULE_LOOKS = 0;

  /**
   * The number of structural features of the '<em>Look Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOK_INFO_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Look Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOOK_INFO_OPERATION_COUNT = 0;

  /**
   * Returns the meta object for class '{@link goedegep.configuration.model.LookInfo <em>Look Info</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Look Info</em>'.
   * @see goedegep.configuration.model.LookInfo
   * @generated
   */
  EClass getLookInfo();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.configuration.model.LookInfo#getModuleLooks <em>Module Looks</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Module Looks</em>'.
   * @see goedegep.configuration.model.LookInfo#getModuleLooks()
   * @see #getLookInfo()
   * @generated
   */
  EReference getLookInfo_ModuleLooks();

  /**
   * Returns the meta object for class '{@link goedegep.configuration.model.ModuleLook <em>Module Look</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Module Look</em>'.
   * @see goedegep.configuration.model.ModuleLook
   * @generated
   */
  EClass getModuleLook();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.configuration.model.ModuleLook#getLook <em>Look</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Look</em>'.
   * @see goedegep.configuration.model.ModuleLook#getLook()
   * @see #getModuleLook()
   * @generated
   */
  EReference getModuleLook_Look();

  /**
   * Returns the meta object for the attribute '{@link goedegep.configuration.model.ModuleLook#getModuleName <em>Module Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Module Name</em>'.
   * @see goedegep.configuration.model.ModuleLook#getModuleName()
   * @see #getModuleLook()
   * @generated
   */
  EAttribute getModuleLook_ModuleName();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.configuration.model.ModuleLook#getModuleLooks <em>Module Looks</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Module Looks</em>'.
   * @see goedegep.configuration.model.ModuleLook#getModuleLooks()
   * @see #getModuleLook()
   * @generated
   */
  EReference getModuleLook_ModuleLooks();

  /**
   * Returns the meta object for the attribute '{@link goedegep.configuration.model.ModuleLook#getResourcesClassName <em>Resources Class Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Resources Class Name</em>'.
   * @see goedegep.configuration.model.ModuleLook#getResourcesClassName()
   * @see #getModuleLook()
   * @generated
   */
  EAttribute getModuleLook_ResourcesClassName();

  /**
   * Returns the meta object for the reference '{@link goedegep.configuration.model.ModuleLook#getParentModuleLook <em>Parent Module Look</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Parent Module Look</em>'.
   * @see goedegep.configuration.model.ModuleLook#getParentModuleLook()
   * @see #getModuleLook()
   * @generated
   */
  EReference getModuleLook_ParentModuleLook();

  /**
   * Returns the meta object for class '{@link goedegep.configuration.model.Look <em>Look</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Look</em>'.
   * @see goedegep.configuration.model.Look
   * @generated
   */
  EClass getLook();

  /**
   * Returns the meta object for the attribute '{@link goedegep.configuration.model.Look#getBackgroundColor <em>Background Color</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Background Color</em>'.
   * @see goedegep.configuration.model.Look#getBackgroundColor()
   * @see #getLook()
   * @generated
   */
  EAttribute getLook_BackgroundColor();

  /**
   * Returns the meta object for the attribute '{@link goedegep.configuration.model.Look#getButtonBackgroundColor <em>Button Background Color</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Button Background Color</em>'.
   * @see goedegep.configuration.model.Look#getButtonBackgroundColor()
   * @see #getLook()
   * @generated
   */
  EAttribute getLook_ButtonBackgroundColor();

  /**
   * Returns the meta object for the attribute '{@link goedegep.configuration.model.Look#getPanelBackgroundColor <em>Panel Background Color</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Panel Background Color</em>'.
   * @see goedegep.configuration.model.Look#getPanelBackgroundColor()
   * @see #getLook()
   * @generated
   */
  EAttribute getLook_PanelBackgroundColor();

  /**
   * Returns the meta object for the attribute '{@link goedegep.configuration.model.Look#getListBackgroundColor <em>List Background Color</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>List Background Color</em>'.
   * @see goedegep.configuration.model.Look#getListBackgroundColor()
   * @see #getLook()
   * @generated
   */
  EAttribute getLook_ListBackgroundColor();

  /**
   * Returns the meta object for the attribute '{@link goedegep.configuration.model.Look#getLabelBackgroundColor <em>Label Background Color</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Label Background Color</em>'.
   * @see goedegep.configuration.model.Look#getLabelBackgroundColor()
   * @see #getLook()
   * @generated
   */
  EAttribute getLook_LabelBackgroundColor();

  /**
   * Returns the meta object for the attribute '{@link goedegep.configuration.model.Look#getBoxBackgroundColor <em>Box Background Color</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Box Background Color</em>'.
   * @see goedegep.configuration.model.Look#getBoxBackgroundColor()
   * @see #getLook()
   * @generated
   */
  EAttribute getLook_BoxBackgroundColor();

  /**
   * Returns the meta object for the attribute '{@link goedegep.configuration.model.Look#getTextFieldBackgroundColor <em>Text Field Background Color</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Text Field Background Color</em>'.
   * @see goedegep.configuration.model.Look#getTextFieldBackgroundColor()
   * @see #getLook()
   * @generated
   */
  EAttribute getLook_TextFieldBackgroundColor();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  ConfigurationFactory getConfigurationFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each operation of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals {
    /**
    	 * The meta object literal for the '{@link goedegep.configuration.model.impl.LookInfoImpl <em>Look Info</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.configuration.model.impl.LookInfoImpl
    	 * @see goedegep.configuration.model.impl.ConfigurationPackageImpl#getLookInfo()
    	 * @generated
    	 */
    EClass LOOK_INFO = eINSTANCE.getLookInfo();

    /**
    	 * The meta object literal for the '<em><b>Module Looks</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference LOOK_INFO__MODULE_LOOKS = eINSTANCE.getLookInfo_ModuleLooks();

    /**
    	 * The meta object literal for the '{@link goedegep.configuration.model.impl.ModuleLookImpl <em>Module Look</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.configuration.model.impl.ModuleLookImpl
    	 * @see goedegep.configuration.model.impl.ConfigurationPackageImpl#getModuleLook()
    	 * @generated
    	 */
    EClass MODULE_LOOK = eINSTANCE.getModuleLook();

    /**
    	 * The meta object literal for the '<em><b>Look</b></em>' containment reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference MODULE_LOOK__LOOK = eINSTANCE.getModuleLook_Look();

    /**
    	 * The meta object literal for the '<em><b>Module Name</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute MODULE_LOOK__MODULE_NAME = eINSTANCE.getModuleLook_ModuleName();

    /**
    	 * The meta object literal for the '<em><b>Module Looks</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference MODULE_LOOK__MODULE_LOOKS = eINSTANCE.getModuleLook_ModuleLooks();

    /**
    	 * The meta object literal for the '<em><b>Resources Class Name</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute MODULE_LOOK__RESOURCES_CLASS_NAME = eINSTANCE.getModuleLook_ResourcesClassName();

    /**
    	 * The meta object literal for the '<em><b>Parent Module Look</b></em>' reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference MODULE_LOOK__PARENT_MODULE_LOOK = eINSTANCE.getModuleLook_ParentModuleLook();

    /**
    	 * The meta object literal for the '{@link goedegep.configuration.model.impl.LookImpl <em>Look</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.configuration.model.impl.LookImpl
    	 * @see goedegep.configuration.model.impl.ConfigurationPackageImpl#getLook()
    	 * @generated
    	 */
    EClass LOOK = eINSTANCE.getLook();

    /**
    	 * The meta object literal for the '<em><b>Background Color</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOOK__BACKGROUND_COLOR = eINSTANCE.getLook_BackgroundColor();

    /**
    	 * The meta object literal for the '<em><b>Button Background Color</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOOK__BUTTON_BACKGROUND_COLOR = eINSTANCE.getLook_ButtonBackgroundColor();

    /**
    	 * The meta object literal for the '<em><b>Panel Background Color</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOOK__PANEL_BACKGROUND_COLOR = eINSTANCE.getLook_PanelBackgroundColor();

    /**
    	 * The meta object literal for the '<em><b>List Background Color</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOOK__LIST_BACKGROUND_COLOR = eINSTANCE.getLook_ListBackgroundColor();

    /**
    	 * The meta object literal for the '<em><b>Label Background Color</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOOK__LABEL_BACKGROUND_COLOR = eINSTANCE.getLook_LabelBackgroundColor();

    /**
    	 * The meta object literal for the '<em><b>Box Background Color</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOOK__BOX_BACKGROUND_COLOR = eINSTANCE.getLook_BoxBackgroundColor();

    /**
    	 * The meta object literal for the '<em><b>Text Field Background Color</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute LOOK__TEXT_FIELD_BACKGROUND_COLOR = eINSTANCE.getLook_TextFieldBackgroundColor();

  }

} //ConfigurationPackage
