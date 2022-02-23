/**
 */
package goedegep.icons.iconset.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see goedegep.icons.iconset.model.ModelFactory
 * @model kind="package"
 * @generated
 */
public interface ModelPackage extends EPackage {
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
  String eNS_URI = "http://goedegep.icons.iconset/model";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "iconset";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  ModelPackage eINSTANCE = goedegep.icons.iconset.model.impl.ModelPackageImpl.init();

  /**
   * The meta object id for the '{@link goedegep.icons.iconset.model.impl.IconSetImpl <em>Icon Set</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.icons.iconset.model.impl.IconSetImpl
   * @see goedegep.icons.iconset.model.impl.ModelPackageImpl#getIconSet()
   * @generated
   */
  int ICON_SET = 0;

  /**
   * The feature id for the '<em><b>Icon Set Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_SET__ICON_SET_ID = 0;

  /**
   * The feature id for the '<em><b>Color Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_SET__COLOR_TYPE = 1;

  /**
   * The feature id for the '<em><b>Dimension</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_SET__DIMENSION = 2;

  /**
   * The feature id for the '<em><b>Size</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_SET__SIZE = 3;

  /**
   * The feature id for the '<em><b>Day Time Icon</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_SET__DAY_TIME_ICON = 4;

  /**
   * The feature id for the '<em><b>Theme Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_SET__THEME_ID = 5;

  /**
   * The feature id for the '<em><b>Medium Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_SET__MEDIUM_TYPE = 6;

  /**
   * The feature id for the '<em><b>Icon Descriptors</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_SET__ICON_DESCRIPTORS = 7;

  /**
   * The number of structural features of the '<em>Icon Set</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_SET_FEATURE_COUNT = 8;

  /**
   * The number of operations of the '<em>Icon Set</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_SET_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.icons.iconset.model.impl.IconSizeImpl <em>Icon Size</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.icons.iconset.model.impl.IconSizeImpl
   * @see goedegep.icons.iconset.model.impl.ModelPackageImpl#getIconSize()
   * @generated
   */
  int ICON_SIZE = 1;

  /**
   * The feature id for the '<em><b>Width</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_SIZE__WIDTH = 0;

  /**
   * The feature id for the '<em><b>Height</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_SIZE__HEIGHT = 1;

  /**
   * The feature id for the '<em><b>Dpi</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_SIZE__DPI = 2;

  /**
   * The number of structural features of the '<em>Icon Size</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_SIZE_FEATURE_COUNT = 3;

  /**
   * The number of operations of the '<em>Icon Size</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_SIZE_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.icons.iconset.model.impl.IconDescriptorImpl <em>Icon Descriptor</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.icons.iconset.model.impl.IconDescriptorImpl
   * @see goedegep.icons.iconset.model.impl.ModelPackageImpl#getIconDescriptor()
   * @generated
   */
  int ICON_DESCRIPTOR = 2;

  /**
   * The feature id for the '<em><b>Url</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_DESCRIPTOR__URL = 0;

  /**
   * The feature id for the '<em><b>Icon Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_DESCRIPTOR__ICON_ID = 1;

  /**
   * The number of structural features of the '<em>Icon Descriptor</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_DESCRIPTOR_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Icon Descriptor</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_DESCRIPTOR_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.icons.iconset.model.impl.IconDataImpl <em>Icon Data</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.icons.iconset.model.impl.IconDataImpl
   * @see goedegep.icons.iconset.model.impl.ModelPackageImpl#getIconData()
   * @generated
   */
  int ICON_DATA = 3;

  /**
   * The feature id for the '<em><b>Data</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_DATA__DATA = 0;

  /**
   * The number of structural features of the '<em>Icon Data</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_DATA_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Icon Data</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_DATA_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.icons.iconset.model.impl.IconInfoImpl <em>Icon Info</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.icons.iconset.model.impl.IconInfoImpl
   * @see goedegep.icons.iconset.model.impl.ModelPackageImpl#getIconInfo()
   * @generated
   */
  int ICON_INFO = 4;

  /**
   * The feature id for the '<em><b>Icon Set Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_INFO__ICON_SET_ID = 0;

  /**
   * The feature id for the '<em><b>Icon Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_INFO__ICON_ID = 1;

  /**
   * The number of structural features of the '<em>Icon Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_INFO_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Icon Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_INFO_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.icons.iconset.model.impl.IconDefinitionImpl <em>Icon Definition</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.icons.iconset.model.impl.IconDefinitionImpl
   * @see goedegep.icons.iconset.model.impl.ModelPackageImpl#getIconDefinition()
   * @generated
   */
  int ICON_DEFINITION = 5;

  /**
   * The feature id for the '<em><b>Icon Info</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_DEFINITION__ICON_INFO = 0;

  /**
   * The feature id for the '<em><b>Icon Data</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_DEFINITION__ICON_DATA = 1;

  /**
   * The number of structural features of the '<em>Icon Definition</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_DEFINITION_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Icon Definition</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ICON_DEFINITION_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.icons.iconset.model.ColorType <em>Color Type</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.icons.iconset.model.ColorType
   * @see goedegep.icons.iconset.model.impl.ModelPackageImpl#getColorType()
   * @generated
   */
  int COLOR_TYPE = 6;

  /**
   * The meta object id for the '{@link goedegep.icons.iconset.model.IconDimension <em>Icon Dimension</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.icons.iconset.model.IconDimension
   * @see goedegep.icons.iconset.model.impl.ModelPackageImpl#getIconDimension()
   * @generated
   */
  int ICON_DIMENSION = 7;


  /**
   * Returns the meta object for class '{@link goedegep.icons.iconset.model.IconSet <em>Icon Set</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Icon Set</em>'.
   * @see goedegep.icons.iconset.model.IconSet
   * @generated
   */
  EClass getIconSet();

  /**
   * Returns the meta object for the attribute '{@link goedegep.icons.iconset.model.IconSet#getIconSetId <em>Icon Set Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Icon Set Id</em>'.
   * @see goedegep.icons.iconset.model.IconSet#getIconSetId()
   * @see #getIconSet()
   * @generated
   */
  EAttribute getIconSet_IconSetId();

  /**
   * Returns the meta object for the attribute '{@link goedegep.icons.iconset.model.IconSet#getColorType <em>Color Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Color Type</em>'.
   * @see goedegep.icons.iconset.model.IconSet#getColorType()
   * @see #getIconSet()
   * @generated
   */
  EAttribute getIconSet_ColorType();

  /**
   * Returns the meta object for the attribute '{@link goedegep.icons.iconset.model.IconSet#getDimension <em>Dimension</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Dimension</em>'.
   * @see goedegep.icons.iconset.model.IconSet#getDimension()
   * @see #getIconSet()
   * @generated
   */
  EAttribute getIconSet_Dimension();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.icons.iconset.model.IconSet#getSize <em>Size</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Size</em>'.
   * @see goedegep.icons.iconset.model.IconSet#getSize()
   * @see #getIconSet()
   * @generated
   */
  EReference getIconSet_Size();

  /**
   * Returns the meta object for the attribute '{@link goedegep.icons.iconset.model.IconSet#isDayTimeIcon <em>Day Time Icon</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Day Time Icon</em>'.
   * @see goedegep.icons.iconset.model.IconSet#isDayTimeIcon()
   * @see #getIconSet()
   * @generated
   */
  EAttribute getIconSet_DayTimeIcon();

  /**
   * Returns the meta object for the attribute '{@link goedegep.icons.iconset.model.IconSet#getThemeId <em>Theme Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Theme Id</em>'.
   * @see goedegep.icons.iconset.model.IconSet#getThemeId()
   * @see #getIconSet()
   * @generated
   */
  EAttribute getIconSet_ThemeId();

  /**
   * Returns the meta object for the attribute '{@link goedegep.icons.iconset.model.IconSet#getMediumType <em>Medium Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Medium Type</em>'.
   * @see goedegep.icons.iconset.model.IconSet#getMediumType()
   * @see #getIconSet()
   * @generated
   */
  EAttribute getIconSet_MediumType();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.icons.iconset.model.IconSet#getIconDescriptors <em>Icon Descriptors</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Icon Descriptors</em>'.
   * @see goedegep.icons.iconset.model.IconSet#getIconDescriptors()
   * @see #getIconSet()
   * @generated
   */
  EReference getIconSet_IconDescriptors();

  /**
   * Returns the meta object for class '{@link goedegep.icons.iconset.model.IconSize <em>Icon Size</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Icon Size</em>'.
   * @see goedegep.icons.iconset.model.IconSize
   * @generated
   */
  EClass getIconSize();

  /**
   * Returns the meta object for the attribute '{@link goedegep.icons.iconset.model.IconSize#getWidth <em>Width</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Width</em>'.
   * @see goedegep.icons.iconset.model.IconSize#getWidth()
   * @see #getIconSize()
   * @generated
   */
  EAttribute getIconSize_Width();

  /**
   * Returns the meta object for the attribute '{@link goedegep.icons.iconset.model.IconSize#getHeight <em>Height</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Height</em>'.
   * @see goedegep.icons.iconset.model.IconSize#getHeight()
   * @see #getIconSize()
   * @generated
   */
  EAttribute getIconSize_Height();

  /**
   * Returns the meta object for the attribute '{@link goedegep.icons.iconset.model.IconSize#getDpi <em>Dpi</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Dpi</em>'.
   * @see goedegep.icons.iconset.model.IconSize#getDpi()
   * @see #getIconSize()
   * @generated
   */
  EAttribute getIconSize_Dpi();

  /**
   * Returns the meta object for class '{@link goedegep.icons.iconset.model.IconDescriptor <em>Icon Descriptor</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Icon Descriptor</em>'.
   * @see goedegep.icons.iconset.model.IconDescriptor
   * @generated
   */
  EClass getIconDescriptor();

  /**
   * Returns the meta object for the attribute '{@link goedegep.icons.iconset.model.IconDescriptor#getUrl <em>Url</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Url</em>'.
   * @see goedegep.icons.iconset.model.IconDescriptor#getUrl()
   * @see #getIconDescriptor()
   * @generated
   */
  EAttribute getIconDescriptor_Url();

  /**
   * Returns the meta object for the attribute '{@link goedegep.icons.iconset.model.IconDescriptor#getIconId <em>Icon Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Icon Id</em>'.
   * @see goedegep.icons.iconset.model.IconDescriptor#getIconId()
   * @see #getIconDescriptor()
   * @generated
   */
  EAttribute getIconDescriptor_IconId();

  /**
   * Returns the meta object for class '{@link goedegep.icons.iconset.model.IconData <em>Icon Data</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Icon Data</em>'.
   * @see goedegep.icons.iconset.model.IconData
   * @generated
   */
  EClass getIconData();

  /**
   * Returns the meta object for the attribute '{@link goedegep.icons.iconset.model.IconData#getData <em>Data</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Data</em>'.
   * @see goedegep.icons.iconset.model.IconData#getData()
   * @see #getIconData()
   * @generated
   */
  EAttribute getIconData_Data();

  /**
   * Returns the meta object for class '{@link goedegep.icons.iconset.model.IconInfo <em>Icon Info</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Icon Info</em>'.
   * @see goedegep.icons.iconset.model.IconInfo
   * @generated
   */
  EClass getIconInfo();

  /**
   * Returns the meta object for the attribute '{@link goedegep.icons.iconset.model.IconInfo#getIconSetId <em>Icon Set Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Icon Set Id</em>'.
   * @see goedegep.icons.iconset.model.IconInfo#getIconSetId()
   * @see #getIconInfo()
   * @generated
   */
  EAttribute getIconInfo_IconSetId();

  /**
   * Returns the meta object for the attribute '{@link goedegep.icons.iconset.model.IconInfo#getIconId <em>Icon Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Icon Id</em>'.
   * @see goedegep.icons.iconset.model.IconInfo#getIconId()
   * @see #getIconInfo()
   * @generated
   */
  EAttribute getIconInfo_IconId();

  /**
   * Returns the meta object for class '{@link goedegep.icons.iconset.model.IconDefinition <em>Icon Definition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Icon Definition</em>'.
   * @see goedegep.icons.iconset.model.IconDefinition
   * @generated
   */
  EClass getIconDefinition();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.icons.iconset.model.IconDefinition#getIconInfo <em>Icon Info</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Icon Info</em>'.
   * @see goedegep.icons.iconset.model.IconDefinition#getIconInfo()
   * @see #getIconDefinition()
   * @generated
   */
  EReference getIconDefinition_IconInfo();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.icons.iconset.model.IconDefinition#getIconData <em>Icon Data</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Icon Data</em>'.
   * @see goedegep.icons.iconset.model.IconDefinition#getIconData()
   * @see #getIconDefinition()
   * @generated
   */
  EReference getIconDefinition_IconData();

  /**
   * Returns the meta object for enum '{@link goedegep.icons.iconset.model.ColorType <em>Color Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Color Type</em>'.
   * @see goedegep.icons.iconset.model.ColorType
   * @generated
   */
  EEnum getColorType();

  /**
   * Returns the meta object for enum '{@link goedegep.icons.iconset.model.IconDimension <em>Icon Dimension</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Icon Dimension</em>'.
   * @see goedegep.icons.iconset.model.IconDimension
   * @generated
   */
  EEnum getIconDimension();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  ModelFactory getModelFactory();

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
     * The meta object literal for the '{@link goedegep.icons.iconset.model.impl.IconSetImpl <em>Icon Set</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.icons.iconset.model.impl.IconSetImpl
     * @see goedegep.icons.iconset.model.impl.ModelPackageImpl#getIconSet()
     * @generated
     */
    EClass ICON_SET = eINSTANCE.getIconSet();

    /**
     * The meta object literal for the '<em><b>Icon Set Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ICON_SET__ICON_SET_ID = eINSTANCE.getIconSet_IconSetId();

    /**
     * The meta object literal for the '<em><b>Color Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ICON_SET__COLOR_TYPE = eINSTANCE.getIconSet_ColorType();

    /**
     * The meta object literal for the '<em><b>Dimension</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ICON_SET__DIMENSION = eINSTANCE.getIconSet_Dimension();

    /**
     * The meta object literal for the '<em><b>Size</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ICON_SET__SIZE = eINSTANCE.getIconSet_Size();

    /**
     * The meta object literal for the '<em><b>Day Time Icon</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ICON_SET__DAY_TIME_ICON = eINSTANCE.getIconSet_DayTimeIcon();

    /**
     * The meta object literal for the '<em><b>Theme Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ICON_SET__THEME_ID = eINSTANCE.getIconSet_ThemeId();

    /**
     * The meta object literal for the '<em><b>Medium Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ICON_SET__MEDIUM_TYPE = eINSTANCE.getIconSet_MediumType();

    /**
     * The meta object literal for the '<em><b>Icon Descriptors</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ICON_SET__ICON_DESCRIPTORS = eINSTANCE.getIconSet_IconDescriptors();

    /**
     * The meta object literal for the '{@link goedegep.icons.iconset.model.impl.IconSizeImpl <em>Icon Size</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.icons.iconset.model.impl.IconSizeImpl
     * @see goedegep.icons.iconset.model.impl.ModelPackageImpl#getIconSize()
     * @generated
     */
    EClass ICON_SIZE = eINSTANCE.getIconSize();

    /**
     * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ICON_SIZE__WIDTH = eINSTANCE.getIconSize_Width();

    /**
     * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ICON_SIZE__HEIGHT = eINSTANCE.getIconSize_Height();

    /**
     * The meta object literal for the '<em><b>Dpi</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ICON_SIZE__DPI = eINSTANCE.getIconSize_Dpi();

    /**
     * The meta object literal for the '{@link goedegep.icons.iconset.model.impl.IconDescriptorImpl <em>Icon Descriptor</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.icons.iconset.model.impl.IconDescriptorImpl
     * @see goedegep.icons.iconset.model.impl.ModelPackageImpl#getIconDescriptor()
     * @generated
     */
    EClass ICON_DESCRIPTOR = eINSTANCE.getIconDescriptor();

    /**
     * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ICON_DESCRIPTOR__URL = eINSTANCE.getIconDescriptor_Url();

    /**
     * The meta object literal for the '<em><b>Icon Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ICON_DESCRIPTOR__ICON_ID = eINSTANCE.getIconDescriptor_IconId();

    /**
     * The meta object literal for the '{@link goedegep.icons.iconset.model.impl.IconDataImpl <em>Icon Data</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.icons.iconset.model.impl.IconDataImpl
     * @see goedegep.icons.iconset.model.impl.ModelPackageImpl#getIconData()
     * @generated
     */
    EClass ICON_DATA = eINSTANCE.getIconData();

    /**
     * The meta object literal for the '<em><b>Data</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ICON_DATA__DATA = eINSTANCE.getIconData_Data();

    /**
     * The meta object literal for the '{@link goedegep.icons.iconset.model.impl.IconInfoImpl <em>Icon Info</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.icons.iconset.model.impl.IconInfoImpl
     * @see goedegep.icons.iconset.model.impl.ModelPackageImpl#getIconInfo()
     * @generated
     */
    EClass ICON_INFO = eINSTANCE.getIconInfo();

    /**
     * The meta object literal for the '<em><b>Icon Set Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ICON_INFO__ICON_SET_ID = eINSTANCE.getIconInfo_IconSetId();

    /**
     * The meta object literal for the '<em><b>Icon Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ICON_INFO__ICON_ID = eINSTANCE.getIconInfo_IconId();

    /**
     * The meta object literal for the '{@link goedegep.icons.iconset.model.impl.IconDefinitionImpl <em>Icon Definition</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.icons.iconset.model.impl.IconDefinitionImpl
     * @see goedegep.icons.iconset.model.impl.ModelPackageImpl#getIconDefinition()
     * @generated
     */
    EClass ICON_DEFINITION = eINSTANCE.getIconDefinition();

    /**
     * The meta object literal for the '<em><b>Icon Info</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ICON_DEFINITION__ICON_INFO = eINSTANCE.getIconDefinition_IconInfo();

    /**
     * The meta object literal for the '<em><b>Icon Data</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ICON_DEFINITION__ICON_DATA = eINSTANCE.getIconDefinition_IconData();

    /**
     * The meta object literal for the '{@link goedegep.icons.iconset.model.ColorType <em>Color Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.icons.iconset.model.ColorType
     * @see goedegep.icons.iconset.model.impl.ModelPackageImpl#getColorType()
     * @generated
     */
    EEnum COLOR_TYPE = eINSTANCE.getColorType();

    /**
     * The meta object literal for the '{@link goedegep.icons.iconset.model.IconDimension <em>Icon Dimension</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.icons.iconset.model.IconDimension
     * @see goedegep.icons.iconset.model.impl.ModelPackageImpl#getIconDimension()
     * @generated
     */
    EEnum ICON_DIMENSION = eINSTANCE.getIconDimension();

  }

} //ModelPackage
