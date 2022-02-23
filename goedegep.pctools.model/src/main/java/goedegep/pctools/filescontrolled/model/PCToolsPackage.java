/**
 */
package goedegep.pctools.filescontrolled.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
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
 * <!-- begin-model-doc -->
 * A Disc Structure Specification describes the main disc structure.
 * 
 * There are 3 types of directories
 *  * Controlled directories.
 *     A directory or file is controlled, if it is 'automatically' replicated somewhere else. This hold for:
 *    * directories and files which are synchronized (e.g. to a NAS)
 *    * directories and files which are under version control
 *  * Uncontrolled directories that need to be checked
 *  * Uncontrolled directories that don't need to be checked
 * 
 * Rules:
 * * a subdirectory of an uncontrolled directory can be a controlled directory, but not the other way around.
 * * a subdirectory of an 'uncontrolled directory that doesn't need to be checked' may need to be checked, but not the other way around.
 * 
 * This specification is used to provide an overview of how I've organized the directory structure on my PC and to perform automated tests.
 * * I want to avoid having copies of a controlled file.
 *   This can be checked if I specify which directories are controlled, and which files and directories shall be ignored.
 * * To clean up an uncontrolled directory (e.g. a memory stick), it easy if you can check which files in this directory already have a controlled counterpart. As these can simply be removed.
 * 
 * <!-- end-model-doc -->
 * @see goedegep.pctools.filescontrolled.model.PCToolsFactory
 * @model kind="package"
 * @generated
 */
public interface PCToolsPackage extends EPackage {
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
  String eNS_URI = "http://www.goedegep.org/pctools";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "goedegep";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  PCToolsPackage eINSTANCE = goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl.init();

  /**
   * The meta object id for the '{@link goedegep.pctools.filescontrolled.model.impl.DiscStructureSpecificationImpl <em>Disc Structure Specification</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.pctools.filescontrolled.model.impl.DiscStructureSpecificationImpl
   * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getDiscStructureSpecification()
   * @generated
   */
  int DISC_STRUCTURE_SPECIFICATION = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISC_STRUCTURE_SPECIFICATION__NAME = 0;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISC_STRUCTURE_SPECIFICATION__DESCRIPTION = 1;

  /**
   * The feature id for the '<em><b>Directory Specifications</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISC_STRUCTURE_SPECIFICATION__DIRECTORY_SPECIFICATIONS = 2;

  /**
   * The feature id for the '<em><b>Files To Ignore Completely</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISC_STRUCTURE_SPECIFICATION__FILES_TO_IGNORE_COMPLETELY = 3;

  /**
   * The feature id for the '<em><b>Directories To Ignore Completely</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISC_STRUCTURE_SPECIFICATION__DIRECTORIES_TO_IGNORE_COMPLETELY = 4;

  /**
   * The number of structural features of the '<em>Disc Structure Specification</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISC_STRUCTURE_SPECIFICATION_FEATURE_COUNT = 5;

  /**
   * The number of operations of the '<em>Disc Structure Specification</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DISC_STRUCTURE_SPECIFICATION_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.pctools.filescontrolled.model.impl.DirectorySpecificationImpl <em>Directory Specification</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.pctools.filescontrolled.model.impl.DirectorySpecificationImpl
   * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getDirectorySpecification()
   * @generated
   */
  int DIRECTORY_SPECIFICATION = 1;

  /**
   * The feature id for the '<em><b>Directory Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DIRECTORY_SPECIFICATION__DIRECTORY_PATH = 0;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DIRECTORY_SPECIFICATION__DESCRIPTION = 1;

  /**
   * The feature id for the '<em><b>Synchronization Specification</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DIRECTORY_SPECIFICATION__SYNCHRONIZATION_SPECIFICATION = 2;

  /**
   * The feature id for the '<em><b>Source Control Specification</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DIRECTORY_SPECIFICATION__SOURCE_CONTROL_SPECIFICATION = 3;

  /**
   * The feature id for the '<em><b>To Be Checked</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DIRECTORY_SPECIFICATION__TO_BE_CHECKED = 4;

  /**
   * The number of structural features of the '<em>Directory Specification</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DIRECTORY_SPECIFICATION_FEATURE_COUNT = 5;

  /**
   * The operation id for the '<em>Is Controlled</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DIRECTORY_SPECIFICATION___IS_CONTROLLED = 0;

  /**
   * The number of operations of the '<em>Directory Specification</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DIRECTORY_SPECIFICATION_OPERATION_COUNT = 1;


  /**
   * The meta object id for the '{@link goedegep.pctools.filescontrolled.model.impl.DescribedItemImpl <em>Described Item</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.pctools.filescontrolled.model.impl.DescribedItemImpl
   * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getDescribedItem()
   * @generated
   */
  int DESCRIBED_ITEM = 2;

  /**
   * The feature id for the '<em><b>Item</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DESCRIBED_ITEM__ITEM = 0;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DESCRIBED_ITEM__DESCRIPTION = 1;

  /**
   * The number of structural features of the '<em>Described Item</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DESCRIBED_ITEM_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Described Item</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DESCRIBED_ITEM_OPERATION_COUNT = 0;


  /**
   * Returns the meta object for class '{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification <em>Disc Structure Specification</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Disc Structure Specification</em>'.
   * @see goedegep.pctools.filescontrolled.model.DiscStructureSpecification
   * @generated
   */
  EClass getDiscStructureSpecification();

  /**
   * Returns the meta object for the attribute '{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getName()
   * @see #getDiscStructureSpecification()
   * @generated
   */
  EAttribute getDiscStructureSpecification_Name();

  /**
   * Returns the meta object for the attribute '{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getDescription()
   * @see #getDiscStructureSpecification()
   * @generated
   */
  EAttribute getDiscStructureSpecification_Description();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getFilesToIgnoreCompletely <em>Files To Ignore Completely</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Files To Ignore Completely</em>'.
   * @see goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getFilesToIgnoreCompletely()
   * @see #getDiscStructureSpecification()
   * @generated
   */
  EReference getDiscStructureSpecification_FilesToIgnoreCompletely();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getDirectoriesToIgnoreCompletely <em>Directories To Ignore Completely</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Directories To Ignore Completely</em>'.
   * @see goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getDirectoriesToIgnoreCompletely()
   * @see #getDiscStructureSpecification()
   * @generated
   */
  EReference getDiscStructureSpecification_DirectoriesToIgnoreCompletely();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getDirectorySpecifications <em>Directory Specifications</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Directory Specifications</em>'.
   * @see goedegep.pctools.filescontrolled.model.DiscStructureSpecification#getDirectorySpecifications()
   * @see #getDiscStructureSpecification()
   * @generated
   */
  EReference getDiscStructureSpecification_DirectorySpecifications();

  /**
   * Returns the meta object for class '{@link goedegep.pctools.filescontrolled.model.DirectorySpecification <em>Directory Specification</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Directory Specification</em>'.
   * @see goedegep.pctools.filescontrolled.model.DirectorySpecification
   * @generated
   */
  EClass getDirectorySpecification();

  /**
   * Returns the meta object for the attribute '{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#getDirectoryPath <em>Directory Path</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Directory Path</em>'.
   * @see goedegep.pctools.filescontrolled.model.DirectorySpecification#getDirectoryPath()
   * @see #getDirectorySpecification()
   * @generated
   */
  EAttribute getDirectorySpecification_DirectoryPath();

  /**
   * Returns the meta object for the attribute '{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see goedegep.pctools.filescontrolled.model.DirectorySpecification#getDescription()
   * @see #getDirectorySpecification()
   * @generated
   */
  EAttribute getDirectorySpecification_Description();

  /**
   * Returns the meta object for the attribute '{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#getSynchronizationSpecification <em>Synchronization Specification</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Synchronization Specification</em>'.
   * @see goedegep.pctools.filescontrolled.model.DirectorySpecification#getSynchronizationSpecification()
   * @see #getDirectorySpecification()
   * @generated
   */
  EAttribute getDirectorySpecification_SynchronizationSpecification();

  /**
   * Returns the meta object for the attribute '{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#getSourceControlSpecification <em>Source Control Specification</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Source Control Specification</em>'.
   * @see goedegep.pctools.filescontrolled.model.DirectorySpecification#getSourceControlSpecification()
   * @see #getDirectorySpecification()
   * @generated
   */
  EAttribute getDirectorySpecification_SourceControlSpecification();

  /**
   * Returns the meta object for the attribute '{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#isToBeChecked <em>To Be Checked</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>To Be Checked</em>'.
   * @see goedegep.pctools.filescontrolled.model.DirectorySpecification#isToBeChecked()
   * @see #getDirectorySpecification()
   * @generated
   */
  EAttribute getDirectorySpecification_ToBeChecked();

  /**
   * Returns the meta object for the '{@link goedegep.pctools.filescontrolled.model.DirectorySpecification#isControlled() <em>Is Controlled</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Is Controlled</em>' operation.
   * @see goedegep.pctools.filescontrolled.model.DirectorySpecification#isControlled()
   * @generated
   */
  EOperation getDirectorySpecification__IsControlled();

  /**
   * Returns the meta object for class '{@link goedegep.pctools.filescontrolled.model.DescribedItem <em>Described Item</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Described Item</em>'.
   * @see goedegep.pctools.filescontrolled.model.DescribedItem
   * @generated
   */
  EClass getDescribedItem();

  /**
   * Returns the meta object for the attribute '{@link goedegep.pctools.filescontrolled.model.DescribedItem#getItem <em>Item</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Item</em>'.
   * @see goedegep.pctools.filescontrolled.model.DescribedItem#getItem()
   * @see #getDescribedItem()
   * @generated
   */
  EAttribute getDescribedItem_Item();

  /**
   * Returns the meta object for the attribute '{@link goedegep.pctools.filescontrolled.model.DescribedItem#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see goedegep.pctools.filescontrolled.model.DescribedItem#getDescription()
   * @see #getDescribedItem()
   * @generated
   */
  EAttribute getDescribedItem_Description();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  PCToolsFactory getPCToolsFactory();

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
     * The meta object literal for the '{@link goedegep.pctools.filescontrolled.model.impl.DiscStructureSpecificationImpl <em>Disc Structure Specification</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.pctools.filescontrolled.model.impl.DiscStructureSpecificationImpl
     * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getDiscStructureSpecification()
     * @generated
     */
    EClass DISC_STRUCTURE_SPECIFICATION = eINSTANCE.getDiscStructureSpecification();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DISC_STRUCTURE_SPECIFICATION__NAME = eINSTANCE.getDiscStructureSpecification_Name();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DISC_STRUCTURE_SPECIFICATION__DESCRIPTION = eINSTANCE.getDiscStructureSpecification_Description();

    /**
     * The meta object literal for the '<em><b>Files To Ignore Completely</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DISC_STRUCTURE_SPECIFICATION__FILES_TO_IGNORE_COMPLETELY = eINSTANCE.getDiscStructureSpecification_FilesToIgnoreCompletely();

    /**
     * The meta object literal for the '<em><b>Directories To Ignore Completely</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DISC_STRUCTURE_SPECIFICATION__DIRECTORIES_TO_IGNORE_COMPLETELY = eINSTANCE.getDiscStructureSpecification_DirectoriesToIgnoreCompletely();

    /**
     * The meta object literal for the '<em><b>Directory Specifications</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DISC_STRUCTURE_SPECIFICATION__DIRECTORY_SPECIFICATIONS = eINSTANCE.getDiscStructureSpecification_DirectorySpecifications();

    /**
     * The meta object literal for the '{@link goedegep.pctools.filescontrolled.model.impl.DirectorySpecificationImpl <em>Directory Specification</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.pctools.filescontrolled.model.impl.DirectorySpecificationImpl
     * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getDirectorySpecification()
     * @generated
     */
    EClass DIRECTORY_SPECIFICATION = eINSTANCE.getDirectorySpecification();

    /**
     * The meta object literal for the '<em><b>Directory Path</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DIRECTORY_SPECIFICATION__DIRECTORY_PATH = eINSTANCE.getDirectorySpecification_DirectoryPath();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DIRECTORY_SPECIFICATION__DESCRIPTION = eINSTANCE.getDirectorySpecification_Description();

    /**
     * The meta object literal for the '<em><b>Synchronization Specification</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DIRECTORY_SPECIFICATION__SYNCHRONIZATION_SPECIFICATION = eINSTANCE.getDirectorySpecification_SynchronizationSpecification();

    /**
     * The meta object literal for the '<em><b>Source Control Specification</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DIRECTORY_SPECIFICATION__SOURCE_CONTROL_SPECIFICATION = eINSTANCE.getDirectorySpecification_SourceControlSpecification();

    /**
     * The meta object literal for the '<em><b>To Be Checked</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DIRECTORY_SPECIFICATION__TO_BE_CHECKED = eINSTANCE.getDirectorySpecification_ToBeChecked();

    /**
     * The meta object literal for the '<em><b>Is Controlled</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation DIRECTORY_SPECIFICATION___IS_CONTROLLED = eINSTANCE.getDirectorySpecification__IsControlled();

    /**
     * The meta object literal for the '{@link goedegep.pctools.filescontrolled.model.impl.DescribedItemImpl <em>Described Item</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.pctools.filescontrolled.model.impl.DescribedItemImpl
     * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getDescribedItem()
     * @generated
     */
    EClass DESCRIBED_ITEM = eINSTANCE.getDescribedItem();

    /**
     * The meta object literal for the '<em><b>Item</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DESCRIBED_ITEM__ITEM = eINSTANCE.getDescribedItem_Item();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DESCRIBED_ITEM__DESCRIPTION = eINSTANCE.getDescribedItem_Description();

  }

} //PCToolsPackage
