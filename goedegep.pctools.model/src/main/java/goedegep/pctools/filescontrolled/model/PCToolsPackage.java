/**
 */
package goedegep.pctools.filescontrolled.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
   * The meta object id for the '{@link goedegep.pctools.filescontrolled.model.impl.ResultImpl <em>Result</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.pctools.filescontrolled.model.impl.ResultImpl
   * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getResult()
   * @generated
   */
  int RESULT = 3;

  /**
   * The feature id for the '<em><b>Controlledrootfolderinfos</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESULT__CONTROLLEDROOTFOLDERINFOS = 0;

  /**
   * The feature id for the '<em><b>Uncontrolled Root Folder Infos</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESULT__UNCONTROLLED_ROOT_FOLDER_INFOS = 1;

  /**
   * The number of structural features of the '<em>Result</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESULT_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Result</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESULT_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.pctools.filescontrolled.model.impl.FolderInfoImpl <em>Folder Info</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.pctools.filescontrolled.model.impl.FolderInfoImpl
   * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getFolderInfo()
   * @generated
   */
  int FOLDER_INFO = 8;

  /**
   * The feature id for the '<em><b>Folder Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOLDER_INFO__FOLDER_NAME = 0;

  /**
   * The number of structural features of the '<em>Folder Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOLDER_INFO_FEATURE_COUNT = 1;

  /**
   * The operation id for the '<em>Get Full Pathname</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOLDER_INFO___GET_FULL_PATHNAME = 0;

  /**
   * The number of operations of the '<em>Folder Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOLDER_INFO_OPERATION_COUNT = 1;

  /**
   * The meta object id for the '{@link goedegep.pctools.filescontrolled.model.impl.UncontrolledFolderInfoImpl <em>Uncontrolled Folder Info</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.pctools.filescontrolled.model.impl.UncontrolledFolderInfoImpl
   * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getUncontrolledFolderInfo()
   * @generated
   */
  int UNCONTROLLED_FOLDER_INFO = 4;

  /**
   * The feature id for the '<em><b>Folder Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNCONTROLLED_FOLDER_INFO__FOLDER_NAME = FOLDER_INFO__FOLDER_NAME;

  /**
   * The feature id for the '<em><b>All Contents Has Copies</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNCONTROLLED_FOLDER_INFO__ALL_CONTENTS_HAS_COPIES = FOLDER_INFO_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Fileinfos</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNCONTROLLED_FOLDER_INFO__FILEINFOS = FOLDER_INFO_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Sub Folders Infos</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNCONTROLLED_FOLDER_INFO__SUB_FOLDERS_INFOS = FOLDER_INFO_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Uncontrolled Folder Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNCONTROLLED_FOLDER_INFO_FEATURE_COUNT = FOLDER_INFO_FEATURE_COUNT + 3;

  /**
   * The operation id for the '<em>Get Full Pathname</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNCONTROLLED_FOLDER_INFO___GET_FULL_PATHNAME = FOLDER_INFO___GET_FULL_PATHNAME;

  /**
   * The number of operations of the '<em>Uncontrolled Folder Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNCONTROLLED_FOLDER_INFO_OPERATION_COUNT = FOLDER_INFO_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.pctools.filescontrolled.model.impl.FileInfoImpl <em>File Info</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.pctools.filescontrolled.model.impl.FileInfoImpl
   * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getFileInfo()
   * @generated
   */
  int FILE_INFO = 5;

  /**
   * The feature id for the '<em><b>File Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_INFO__FILE_NAME = 0;

  /**
   * The feature id for the '<em><b>Copy Of</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_INFO__COPY_OF = 1;

  /**
   * The feature id for the '<em><b>Equality Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_INFO__EQUALITY_TYPE = 2;

  /**
   * The feature id for the '<em><b>Md5 String</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_INFO__MD5_STRING = 3;

  /**
   * The number of structural features of the '<em>File Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_INFO_FEATURE_COUNT = 4;

  /**
   * The operation id for the '<em>Get Full Pathname</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_INFO___GET_FULL_PATHNAME = 0;

  /**
   * The number of operations of the '<em>File Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_INFO_OPERATION_COUNT = 1;


  /**
   * The meta object id for the '{@link goedegep.pctools.filescontrolled.model.impl.ControlledFolderInfoImpl <em>Controlled Folder Info</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.pctools.filescontrolled.model.impl.ControlledFolderInfoImpl
   * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getControlledFolderInfo()
   * @generated
   */
  int CONTROLLED_FOLDER_INFO = 7;

  /**
   * The feature id for the '<em><b>Folder Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTROLLED_FOLDER_INFO__FOLDER_NAME = FOLDER_INFO__FOLDER_NAME;

  /**
   * The feature id for the '<em><b>Sub Folder Infos</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTROLLED_FOLDER_INFO__SUB_FOLDER_INFOS = FOLDER_INFO_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Fileinfos</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTROLLED_FOLDER_INFO__FILEINFOS = FOLDER_INFO_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Controlled Folder Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTROLLED_FOLDER_INFO_FEATURE_COUNT = FOLDER_INFO_FEATURE_COUNT + 2;

  /**
   * The operation id for the '<em>Get Full Pathname</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTROLLED_FOLDER_INFO___GET_FULL_PATHNAME = FOLDER_INFO___GET_FULL_PATHNAME;

  /**
   * The number of operations of the '<em>Controlled Folder Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTROLLED_FOLDER_INFO_OPERATION_COUNT = FOLDER_INFO_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.pctools.filescontrolled.model.impl.ControlledRootFolderInfoImpl <em>Controlled Root Folder Info</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.pctools.filescontrolled.model.impl.ControlledRootFolderInfoImpl
   * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getControlledRootFolderInfo()
   * @generated
   */
  int CONTROLLED_ROOT_FOLDER_INFO = 6;

  /**
   * The feature id for the '<em><b>Folder Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTROLLED_ROOT_FOLDER_INFO__FOLDER_NAME = CONTROLLED_FOLDER_INFO__FOLDER_NAME;

  /**
   * The feature id for the '<em><b>Sub Folder Infos</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTROLLED_ROOT_FOLDER_INFO__SUB_FOLDER_INFOS = CONTROLLED_FOLDER_INFO__SUB_FOLDER_INFOS;

  /**
   * The feature id for the '<em><b>Fileinfos</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTROLLED_ROOT_FOLDER_INFO__FILEINFOS = CONTROLLED_FOLDER_INFO__FILEINFOS;

  /**
   * The feature id for the '<em><b>Folder Base Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTROLLED_ROOT_FOLDER_INFO__FOLDER_BASE_PATH = CONTROLLED_FOLDER_INFO_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Controlled Root Folder Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTROLLED_ROOT_FOLDER_INFO_FEATURE_COUNT = CONTROLLED_FOLDER_INFO_FEATURE_COUNT + 1;

  /**
   * The operation id for the '<em>Get Full Pathname</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTROLLED_ROOT_FOLDER_INFO___GET_FULL_PATHNAME = CONTROLLED_FOLDER_INFO___GET_FULL_PATHNAME;

  /**
   * The number of operations of the '<em>Controlled Root Folder Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTROLLED_ROOT_FOLDER_INFO_OPERATION_COUNT = CONTROLLED_FOLDER_INFO_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.pctools.filescontrolled.model.impl.UncontrolledRootFolderInfoImpl <em>Uncontrolled Root Folder Info</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.pctools.filescontrolled.model.impl.UncontrolledRootFolderInfoImpl
   * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getUncontrolledRootFolderInfo()
   * @generated
   */
  int UNCONTROLLED_ROOT_FOLDER_INFO = 9;

  /**
   * The feature id for the '<em><b>Folder Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNCONTROLLED_ROOT_FOLDER_INFO__FOLDER_NAME = UNCONTROLLED_FOLDER_INFO__FOLDER_NAME;

  /**
   * The feature id for the '<em><b>All Contents Has Copies</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNCONTROLLED_ROOT_FOLDER_INFO__ALL_CONTENTS_HAS_COPIES = UNCONTROLLED_FOLDER_INFO__ALL_CONTENTS_HAS_COPIES;

  /**
   * The feature id for the '<em><b>Fileinfos</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNCONTROLLED_ROOT_FOLDER_INFO__FILEINFOS = UNCONTROLLED_FOLDER_INFO__FILEINFOS;

  /**
   * The feature id for the '<em><b>Sub Folders Infos</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNCONTROLLED_ROOT_FOLDER_INFO__SUB_FOLDERS_INFOS = UNCONTROLLED_FOLDER_INFO__SUB_FOLDERS_INFOS;

  /**
   * The feature id for the '<em><b>Folder Base Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNCONTROLLED_ROOT_FOLDER_INFO__FOLDER_BASE_PATH = UNCONTROLLED_FOLDER_INFO_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Uncontrolled Root Folder Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNCONTROLLED_ROOT_FOLDER_INFO_FEATURE_COUNT = UNCONTROLLED_FOLDER_INFO_FEATURE_COUNT + 1;

  /**
   * The operation id for the '<em>Get Full Pathname</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNCONTROLLED_ROOT_FOLDER_INFO___GET_FULL_PATHNAME = UNCONTROLLED_FOLDER_INFO___GET_FULL_PATHNAME;

  /**
   * The number of operations of the '<em>Uncontrolled Root Folder Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNCONTROLLED_ROOT_FOLDER_INFO_OPERATION_COUNT = UNCONTROLLED_FOLDER_INFO_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.pctools.filescontrolled.model.EqualityType <em>Equality Type</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.pctools.filescontrolled.model.EqualityType
   * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getEqualityType()
   * @generated
   */
  int EQUALITY_TYPE = 10;


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
   * Returns the meta object for class '{@link goedegep.pctools.filescontrolled.model.Result <em>Result</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Result</em>'.
   * @see goedegep.pctools.filescontrolled.model.Result
   * @generated
   */
  EClass getResult();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.pctools.filescontrolled.model.Result#getControlledrootfolderinfos <em>Controlledrootfolderinfos</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Controlledrootfolderinfos</em>'.
   * @see goedegep.pctools.filescontrolled.model.Result#getControlledrootfolderinfos()
   * @see #getResult()
   * @generated
   */
  EReference getResult_Controlledrootfolderinfos();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.pctools.filescontrolled.model.Result#getUncontrolledRootFolderInfos <em>Uncontrolled Root Folder Infos</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Uncontrolled Root Folder Infos</em>'.
   * @see goedegep.pctools.filescontrolled.model.Result#getUncontrolledRootFolderInfos()
   * @see #getResult()
   * @generated
   */
  EReference getResult_UncontrolledRootFolderInfos();

  /**
   * Returns the meta object for class '{@link goedegep.pctools.filescontrolled.model.UncontrolledFolderInfo <em>Uncontrolled Folder Info</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Uncontrolled Folder Info</em>'.
   * @see goedegep.pctools.filescontrolled.model.UncontrolledFolderInfo
   * @generated
   */
  EClass getUncontrolledFolderInfo();

  /**
   * Returns the meta object for the attribute '{@link goedegep.pctools.filescontrolled.model.UncontrolledFolderInfo#isAllContentsHasCopies <em>All Contents Has Copies</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>All Contents Has Copies</em>'.
   * @see goedegep.pctools.filescontrolled.model.UncontrolledFolderInfo#isAllContentsHasCopies()
   * @see #getUncontrolledFolderInfo()
   * @generated
   */
  EAttribute getUncontrolledFolderInfo_AllContentsHasCopies();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.pctools.filescontrolled.model.UncontrolledFolderInfo#getFileinfos <em>Fileinfos</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Fileinfos</em>'.
   * @see goedegep.pctools.filescontrolled.model.UncontrolledFolderInfo#getFileinfos()
   * @see #getUncontrolledFolderInfo()
   * @generated
   */
  EReference getUncontrolledFolderInfo_Fileinfos();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.pctools.filescontrolled.model.UncontrolledFolderInfo#getSubFoldersInfos <em>Sub Folders Infos</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Sub Folders Infos</em>'.
   * @see goedegep.pctools.filescontrolled.model.UncontrolledFolderInfo#getSubFoldersInfos()
   * @see #getUncontrolledFolderInfo()
   * @generated
   */
  EReference getUncontrolledFolderInfo_SubFoldersInfos();

  /**
   * Returns the meta object for class '{@link goedegep.pctools.filescontrolled.model.FolderInfo <em>Folder Info</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Folder Info</em>'.
   * @see goedegep.pctools.filescontrolled.model.FolderInfo
   * @generated
   */
  EClass getFolderInfo();

  /**
   * Returns the meta object for the attribute '{@link goedegep.pctools.filescontrolled.model.FolderInfo#getFolderName <em>Folder Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Folder Name</em>'.
   * @see goedegep.pctools.filescontrolled.model.FolderInfo#getFolderName()
   * @see #getFolderInfo()
   * @generated
   */
  EAttribute getFolderInfo_FolderName();

  /**
   * Returns the meta object for the '{@link goedegep.pctools.filescontrolled.model.FolderInfo#getFullPathname() <em>Get Full Pathname</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Full Pathname</em>' operation.
   * @see goedegep.pctools.filescontrolled.model.FolderInfo#getFullPathname()
   * @generated
   */
  EOperation getFolderInfo__GetFullPathname();

  /**
   * Returns the meta object for class '{@link goedegep.pctools.filescontrolled.model.UncontrolledRootFolderInfo <em>Uncontrolled Root Folder Info</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Uncontrolled Root Folder Info</em>'.
   * @see goedegep.pctools.filescontrolled.model.UncontrolledRootFolderInfo
   * @generated
   */
  EClass getUncontrolledRootFolderInfo();

  /**
   * Returns the meta object for the attribute '{@link goedegep.pctools.filescontrolled.model.UncontrolledRootFolderInfo#getFolderBasePath <em>Folder Base Path</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Folder Base Path</em>'.
   * @see goedegep.pctools.filescontrolled.model.UncontrolledRootFolderInfo#getFolderBasePath()
   * @see #getUncontrolledRootFolderInfo()
   * @generated
   */
  EAttribute getUncontrolledRootFolderInfo_FolderBasePath();

  /**
   * Returns the meta object for class '{@link goedegep.pctools.filescontrolled.model.FileInfo <em>File Info</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>File Info</em>'.
   * @see goedegep.pctools.filescontrolled.model.FileInfo
   * @generated
   */
  EClass getFileInfo();

  /**
   * Returns the meta object for the attribute '{@link goedegep.pctools.filescontrolled.model.FileInfo#getFileName <em>File Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>File Name</em>'.
   * @see goedegep.pctools.filescontrolled.model.FileInfo#getFileName()
   * @see #getFileInfo()
   * @generated
   */
  EAttribute getFileInfo_FileName();

  /**
   * Returns the meta object for the reference '{@link goedegep.pctools.filescontrolled.model.FileInfo#getCopyOf <em>Copy Of</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Copy Of</em>'.
   * @see goedegep.pctools.filescontrolled.model.FileInfo#getCopyOf()
   * @see #getFileInfo()
   * @generated
   */
  EReference getFileInfo_CopyOf();

  /**
   * Returns the meta object for the attribute '{@link goedegep.pctools.filescontrolled.model.FileInfo#getEqualityType <em>Equality Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Equality Type</em>'.
   * @see goedegep.pctools.filescontrolled.model.FileInfo#getEqualityType()
   * @see #getFileInfo()
   * @generated
   */
  EAttribute getFileInfo_EqualityType();

  /**
   * Returns the meta object for the attribute '{@link goedegep.pctools.filescontrolled.model.FileInfo#getMd5String <em>Md5 String</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Md5 String</em>'.
   * @see goedegep.pctools.filescontrolled.model.FileInfo#getMd5String()
   * @see #getFileInfo()
   * @generated
   */
  EAttribute getFileInfo_Md5String();

  /**
   * Returns the meta object for the '{@link goedegep.pctools.filescontrolled.model.FileInfo#getFullPathname() <em>Get Full Pathname</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Full Pathname</em>' operation.
   * @see goedegep.pctools.filescontrolled.model.FileInfo#getFullPathname()
   * @generated
   */
  EOperation getFileInfo__GetFullPathname();

  /**
   * Returns the meta object for class '{@link goedegep.pctools.filescontrolled.model.ControlledRootFolderInfo <em>Controlled Root Folder Info</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Controlled Root Folder Info</em>'.
   * @see goedegep.pctools.filescontrolled.model.ControlledRootFolderInfo
   * @generated
   */
  EClass getControlledRootFolderInfo();

  /**
   * Returns the meta object for the attribute '{@link goedegep.pctools.filescontrolled.model.ControlledRootFolderInfo#getFolderBasePath <em>Folder Base Path</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Folder Base Path</em>'.
   * @see goedegep.pctools.filescontrolled.model.ControlledRootFolderInfo#getFolderBasePath()
   * @see #getControlledRootFolderInfo()
   * @generated
   */
  EAttribute getControlledRootFolderInfo_FolderBasePath();

  /**
   * Returns the meta object for class '{@link goedegep.pctools.filescontrolled.model.ControlledFolderInfo <em>Controlled Folder Info</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Controlled Folder Info</em>'.
   * @see goedegep.pctools.filescontrolled.model.ControlledFolderInfo
   * @generated
   */
  EClass getControlledFolderInfo();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.pctools.filescontrolled.model.ControlledFolderInfo#getSubFolderInfos <em>Sub Folder Infos</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Sub Folder Infos</em>'.
   * @see goedegep.pctools.filescontrolled.model.ControlledFolderInfo#getSubFolderInfos()
   * @see #getControlledFolderInfo()
   * @generated
   */
  EReference getControlledFolderInfo_SubFolderInfos();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.pctools.filescontrolled.model.ControlledFolderInfo#getFileinfos <em>Fileinfos</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Fileinfos</em>'.
   * @see goedegep.pctools.filescontrolled.model.ControlledFolderInfo#getFileinfos()
   * @see #getControlledFolderInfo()
   * @generated
   */
  EReference getControlledFolderInfo_Fileinfos();

  /**
   * Returns the meta object for enum '{@link goedegep.pctools.filescontrolled.model.EqualityType <em>Equality Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Equality Type</em>'.
   * @see goedegep.pctools.filescontrolled.model.EqualityType
   * @generated
   */
  EEnum getEqualityType();

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

    /**
     * The meta object literal for the '{@link goedegep.pctools.filescontrolled.model.impl.ResultImpl <em>Result</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.pctools.filescontrolled.model.impl.ResultImpl
     * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getResult()
     * @generated
     */
    EClass RESULT = eINSTANCE.getResult();

    /**
     * The meta object literal for the '<em><b>Controlledrootfolderinfos</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RESULT__CONTROLLEDROOTFOLDERINFOS = eINSTANCE.getResult_Controlledrootfolderinfos();

    /**
     * The meta object literal for the '<em><b>Uncontrolled Root Folder Infos</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RESULT__UNCONTROLLED_ROOT_FOLDER_INFOS = eINSTANCE.getResult_UncontrolledRootFolderInfos();

    /**
     * The meta object literal for the '{@link goedegep.pctools.filescontrolled.model.impl.UncontrolledFolderInfoImpl <em>Uncontrolled Folder Info</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.pctools.filescontrolled.model.impl.UncontrolledFolderInfoImpl
     * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getUncontrolledFolderInfo()
     * @generated
     */
    EClass UNCONTROLLED_FOLDER_INFO = eINSTANCE.getUncontrolledFolderInfo();

    /**
     * The meta object literal for the '<em><b>All Contents Has Copies</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute UNCONTROLLED_FOLDER_INFO__ALL_CONTENTS_HAS_COPIES = eINSTANCE.getUncontrolledFolderInfo_AllContentsHasCopies();

    /**
     * The meta object literal for the '<em><b>Fileinfos</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference UNCONTROLLED_FOLDER_INFO__FILEINFOS = eINSTANCE.getUncontrolledFolderInfo_Fileinfos();

    /**
     * The meta object literal for the '<em><b>Sub Folders Infos</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference UNCONTROLLED_FOLDER_INFO__SUB_FOLDERS_INFOS = eINSTANCE.getUncontrolledFolderInfo_SubFoldersInfos();

    /**
     * The meta object literal for the '{@link goedegep.pctools.filescontrolled.model.impl.FolderInfoImpl <em>Folder Info</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.pctools.filescontrolled.model.impl.FolderInfoImpl
     * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getFolderInfo()
     * @generated
     */
    EClass FOLDER_INFO = eINSTANCE.getFolderInfo();

    /**
     * The meta object literal for the '<em><b>Folder Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FOLDER_INFO__FOLDER_NAME = eINSTANCE.getFolderInfo_FolderName();

    /**
     * The meta object literal for the '<em><b>Get Full Pathname</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation FOLDER_INFO___GET_FULL_PATHNAME = eINSTANCE.getFolderInfo__GetFullPathname();

    /**
     * The meta object literal for the '{@link goedegep.pctools.filescontrolled.model.impl.UncontrolledRootFolderInfoImpl <em>Uncontrolled Root Folder Info</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.pctools.filescontrolled.model.impl.UncontrolledRootFolderInfoImpl
     * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getUncontrolledRootFolderInfo()
     * @generated
     */
    EClass UNCONTROLLED_ROOT_FOLDER_INFO = eINSTANCE.getUncontrolledRootFolderInfo();

    /**
     * The meta object literal for the '<em><b>Folder Base Path</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute UNCONTROLLED_ROOT_FOLDER_INFO__FOLDER_BASE_PATH = eINSTANCE.getUncontrolledRootFolderInfo_FolderBasePath();

    /**
     * The meta object literal for the '{@link goedegep.pctools.filescontrolled.model.impl.FileInfoImpl <em>File Info</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.pctools.filescontrolled.model.impl.FileInfoImpl
     * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getFileInfo()
     * @generated
     */
    EClass FILE_INFO = eINSTANCE.getFileInfo();

    /**
     * The meta object literal for the '<em><b>File Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FILE_INFO__FILE_NAME = eINSTANCE.getFileInfo_FileName();

    /**
     * The meta object literal for the '<em><b>Copy Of</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FILE_INFO__COPY_OF = eINSTANCE.getFileInfo_CopyOf();

    /**
     * The meta object literal for the '<em><b>Equality Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FILE_INFO__EQUALITY_TYPE = eINSTANCE.getFileInfo_EqualityType();

    /**
     * The meta object literal for the '<em><b>Md5 String</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FILE_INFO__MD5_STRING = eINSTANCE.getFileInfo_Md5String();

    /**
     * The meta object literal for the '<em><b>Get Full Pathname</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation FILE_INFO___GET_FULL_PATHNAME = eINSTANCE.getFileInfo__GetFullPathname();

    /**
     * The meta object literal for the '{@link goedegep.pctools.filescontrolled.model.impl.ControlledRootFolderInfoImpl <em>Controlled Root Folder Info</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.pctools.filescontrolled.model.impl.ControlledRootFolderInfoImpl
     * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getControlledRootFolderInfo()
     * @generated
     */
    EClass CONTROLLED_ROOT_FOLDER_INFO = eINSTANCE.getControlledRootFolderInfo();

    /**
     * The meta object literal for the '<em><b>Folder Base Path</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONTROLLED_ROOT_FOLDER_INFO__FOLDER_BASE_PATH = eINSTANCE.getControlledRootFolderInfo_FolderBasePath();

    /**
     * The meta object literal for the '{@link goedegep.pctools.filescontrolled.model.impl.ControlledFolderInfoImpl <em>Controlled Folder Info</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.pctools.filescontrolled.model.impl.ControlledFolderInfoImpl
     * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getControlledFolderInfo()
     * @generated
     */
    EClass CONTROLLED_FOLDER_INFO = eINSTANCE.getControlledFolderInfo();

    /**
     * The meta object literal for the '<em><b>Sub Folder Infos</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONTROLLED_FOLDER_INFO__SUB_FOLDER_INFOS = eINSTANCE.getControlledFolderInfo_SubFolderInfos();

    /**
     * The meta object literal for the '<em><b>Fileinfos</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONTROLLED_FOLDER_INFO__FILEINFOS = eINSTANCE.getControlledFolderInfo_Fileinfos();

    /**
     * The meta object literal for the '{@link goedegep.pctools.filescontrolled.model.EqualityType <em>Equality Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.pctools.filescontrolled.model.EqualityType
     * @see goedegep.pctools.filescontrolled.model.impl.PCToolsPackageImpl#getEqualityType()
     * @generated
     */
    EEnum EQUALITY_TYPE = eINSTANCE.getEqualityType();

  }

} //PCToolsPackage
