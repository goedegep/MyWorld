/**
 */
package goedegep.finan.lynx2finan.model;

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
 * @see goedegep.finan.lynx2finan.model.LynxToFinanFactory
 * @model kind="package"
 * @generated
 */
public interface LynxToFinanPackage extends EPackage {
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
  String eNS_URI = "http://www.goedegep.org/finan";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "finan";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  LynxToFinanPackage eINSTANCE = goedegep.finan.lynx2finan.model.impl.LynxToFinanPackageImpl.init();

  /**
   * The meta object id for the '{@link goedegep.finan.lynx2finan.model.impl.LynxToFinanShareIdListImpl <em>Share Id List</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.lynx2finan.model.impl.LynxToFinanShareIdListImpl
   * @see goedegep.finan.lynx2finan.model.impl.LynxToFinanPackageImpl#getLynxToFinanShareIdList()
   * @generated
   */
  int LYNX_TO_FINAN_SHARE_ID_LIST = 0;

  /**
   * The feature id for the '<em><b>Entries</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LYNX_TO_FINAN_SHARE_ID_LIST__ENTRIES = 0;

  /**
   * The number of structural features of the '<em>Share Id List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LYNX_TO_FINAN_SHARE_ID_LIST_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Share Id List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LYNX_TO_FINAN_SHARE_ID_LIST_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.finan.lynx2finan.model.impl.LynxToFinanShareIdListEntryImpl <em>Share Id List Entry</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.lynx2finan.model.impl.LynxToFinanShareIdListEntryImpl
   * @see goedegep.finan.lynx2finan.model.impl.LynxToFinanPackageImpl#getLynxToFinanShareIdListEntry()
   * @generated
   */
  int LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY = 1;

  /**
   * The feature id for the '<em><b>Unique Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__UNIQUE_ID = 0;

  /**
   * The feature id for the '<em><b>Security Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__SECURITY_NAME = 1;

  /**
   * The feature id for the '<em><b>Finan Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__FINAN_NAME = 2;

  /**
   * The feature id for the '<em><b>Fi Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__FI_ID = 3;

  /**
   * The feature id for the '<em><b>Unique Id Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__UNIQUE_ID_TYPE = 4;

  /**
   * The feature id for the '<em><b>Ticker Symbol</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__TICKER_SYMBOL = 5;

  /**
   * The number of structural features of the '<em>Share Id List Entry</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY_FEATURE_COUNT = 6;

  /**
   * The number of operations of the '<em>Share Id List Entry</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY_OPERATION_COUNT = 0;


  /**
   * Returns the meta object for class '{@link goedegep.finan.lynx2finan.model.LynxToFinanShareIdList <em>Share Id List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Share Id List</em>'.
   * @see goedegep.finan.lynx2finan.model.LynxToFinanShareIdList
   * @generated
   */
  EClass getLynxToFinanShareIdList();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.finan.lynx2finan.model.LynxToFinanShareIdList#getEntries <em>Entries</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Entries</em>'.
   * @see goedegep.finan.lynx2finan.model.LynxToFinanShareIdList#getEntries()
   * @see #getLynxToFinanShareIdList()
   * @generated
   */
  EReference getLynxToFinanShareIdList_Entries();

  /**
   * Returns the meta object for class '{@link goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry <em>Share Id List Entry</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Share Id List Entry</em>'.
   * @see goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry
   * @generated
   */
  EClass getLynxToFinanShareIdListEntry();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry#getUniqueId <em>Unique Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Unique Id</em>'.
   * @see goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry#getUniqueId()
   * @see #getLynxToFinanShareIdListEntry()
   * @generated
   */
  EAttribute getLynxToFinanShareIdListEntry_UniqueId();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry#getSecurityName <em>Security Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Security Name</em>'.
   * @see goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry#getSecurityName()
   * @see #getLynxToFinanShareIdListEntry()
   * @generated
   */
  EAttribute getLynxToFinanShareIdListEntry_SecurityName();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry#getFinanName <em>Finan Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Finan Name</em>'.
   * @see goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry#getFinanName()
   * @see #getLynxToFinanShareIdListEntry()
   * @generated
   */
  EAttribute getLynxToFinanShareIdListEntry_FinanName();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry#getFiId <em>Fi Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Fi Id</em>'.
   * @see goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry#getFiId()
   * @see #getLynxToFinanShareIdListEntry()
   * @generated
   */
  EAttribute getLynxToFinanShareIdListEntry_FiId();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry#getUniqueIdType <em>Unique Id Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Unique Id Type</em>'.
   * @see goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry#getUniqueIdType()
   * @see #getLynxToFinanShareIdListEntry()
   * @generated
   */
  EAttribute getLynxToFinanShareIdListEntry_UniqueIdType();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry#getTickerSymbol <em>Ticker Symbol</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Ticker Symbol</em>'.
   * @see goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry#getTickerSymbol()
   * @see #getLynxToFinanShareIdListEntry()
   * @generated
   */
  EAttribute getLynxToFinanShareIdListEntry_TickerSymbol();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  LynxToFinanFactory getLynxToFinanFactory();

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
     * The meta object literal for the '{@link goedegep.finan.lynx2finan.model.impl.LynxToFinanShareIdListImpl <em>Share Id List</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.lynx2finan.model.impl.LynxToFinanShareIdListImpl
     * @see goedegep.finan.lynx2finan.model.impl.LynxToFinanPackageImpl#getLynxToFinanShareIdList()
     * @generated
     */
    EClass LYNX_TO_FINAN_SHARE_ID_LIST = eINSTANCE.getLynxToFinanShareIdList();

    /**
     * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference LYNX_TO_FINAN_SHARE_ID_LIST__ENTRIES = eINSTANCE.getLynxToFinanShareIdList_Entries();

    /**
     * The meta object literal for the '{@link goedegep.finan.lynx2finan.model.impl.LynxToFinanShareIdListEntryImpl <em>Share Id List Entry</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.lynx2finan.model.impl.LynxToFinanShareIdListEntryImpl
     * @see goedegep.finan.lynx2finan.model.impl.LynxToFinanPackageImpl#getLynxToFinanShareIdListEntry()
     * @generated
     */
    EClass LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY = eINSTANCE.getLynxToFinanShareIdListEntry();

    /**
     * The meta object literal for the '<em><b>Unique Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__UNIQUE_ID = eINSTANCE.getLynxToFinanShareIdListEntry_UniqueId();

    /**
     * The meta object literal for the '<em><b>Security Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__SECURITY_NAME = eINSTANCE.getLynxToFinanShareIdListEntry_SecurityName();

    /**
     * The meta object literal for the '<em><b>Finan Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__FINAN_NAME = eINSTANCE.getLynxToFinanShareIdListEntry_FinanName();

    /**
     * The meta object literal for the '<em><b>Fi Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__FI_ID = eINSTANCE.getLynxToFinanShareIdListEntry_FiId();

    /**
     * The meta object literal for the '<em><b>Unique Id Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__UNIQUE_ID_TYPE = eINSTANCE.getLynxToFinanShareIdListEntry_UniqueIdType();

    /**
     * The meta object literal for the '<em><b>Ticker Symbol</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__TICKER_SYMBOL = eINSTANCE.getLynxToFinanShareIdListEntry_TickerSymbol();

  }

} //LynxToFinanPackage
