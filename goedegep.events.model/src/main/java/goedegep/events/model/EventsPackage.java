/**
 */
package goedegep.events.model;

import goedegep.types.model.TypesPackage;

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
 * @see goedegep.events.model.EventsFactory
 * @model kind="package"
 * @generated
 */
public interface EventsPackage extends EPackage {
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
  String eNS_URI = "http://www.goedegep.org/events";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "events";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  EventsPackage eINSTANCE = goedegep.events.model.impl.EventsPackageImpl.init();

  /**
   * The meta object id for the '{@link goedegep.events.model.impl.EventsImpl <em>Events</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.events.model.impl.EventsImpl
   * @see goedegep.events.model.impl.EventsPackageImpl#getEvents()
   * @generated
   */
  int EVENTS = 0;

  /**
   * The feature id for the '<em><b>Events</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENTS__EVENTS = 0;

  /**
   * The number of structural features of the '<em>Events</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENTS_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Events</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENTS_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.events.model.impl.EventInfoImpl <em>Event Info</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.events.model.impl.EventInfoImpl
   * @see goedegep.events.model.impl.EventsPackageImpl#getEventInfo()
   * @generated
   */
  int EVENT_INFO = 1;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_INFO__DATE = TypesPackage.EVENT__DATE;

  /**
   * The feature id for the '<em><b>Notes</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_INFO__NOTES = TypesPackage.EVENT__NOTES;

  /**
   * The feature id for the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_INFO__TITLE = TypesPackage.EVENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Picture</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_INFO__PICTURE = TypesPackage.EVENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_INFO__ATTACHMENTS = TypesPackage.EVENT_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Event Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_INFO_FEATURE_COUNT = TypesPackage.EVENT_FEATURE_COUNT + 3;

  /**
   * The number of operations of the '<em>Event Info</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_INFO_OPERATION_COUNT = TypesPackage.EVENT_OPERATION_COUNT + 0;

  /**
   * Returns the meta object for class '{@link goedegep.events.model.Events <em>Events</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Events</em>'.
   * @see goedegep.events.model.Events
   * @generated
   */
  EClass getEvents();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.events.model.Events#getEvents <em>Events</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Events</em>'.
   * @see goedegep.events.model.Events#getEvents()
   * @see #getEvents()
   * @generated
   */
  EReference getEvents_Events();

  /**
   * Returns the meta object for class '{@link goedegep.events.model.EventInfo <em>Event Info</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Event Info</em>'.
   * @see goedegep.events.model.EventInfo
   * @generated
   */
  EClass getEventInfo();

  /**
   * Returns the meta object for the attribute '{@link goedegep.events.model.EventInfo#getTitle <em>Title</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Title</em>'.
   * @see goedegep.events.model.EventInfo#getTitle()
   * @see #getEventInfo()
   * @generated
   */
  EAttribute getEventInfo_Title();

  /**
   * Returns the meta object for the attribute '{@link goedegep.events.model.EventInfo#getPicture <em>Picture</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Picture</em>'.
   * @see goedegep.events.model.EventInfo#getPicture()
   * @see #getEventInfo()
   * @generated
   */
  EAttribute getEventInfo_Picture();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.events.model.EventInfo#getAttachments <em>Attachments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Attachments</em>'.
   * @see goedegep.events.model.EventInfo#getAttachments()
   * @see #getEventInfo()
   * @generated
   */
  EReference getEventInfo_Attachments();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  EventsFactory getEventsFactory();

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
     * The meta object literal for the '{@link goedegep.events.model.impl.EventsImpl <em>Events</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.events.model.impl.EventsImpl
     * @see goedegep.events.model.impl.EventsPackageImpl#getEvents()
     * @generated
     */
    EClass EVENTS = eINSTANCE.getEvents();

    /**
     * The meta object literal for the '<em><b>Events</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EVENTS__EVENTS = eINSTANCE.getEvents_Events();

    /**
     * The meta object literal for the '{@link goedegep.events.model.impl.EventInfoImpl <em>Event Info</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.events.model.impl.EventInfoImpl
     * @see goedegep.events.model.impl.EventsPackageImpl#getEventInfo()
     * @generated
     */
    EClass EVENT_INFO = eINSTANCE.getEventInfo();

    /**
     * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EVENT_INFO__TITLE = eINSTANCE.getEventInfo_Title();

    /**
     * The meta object literal for the '<em><b>Picture</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EVENT_INFO__PICTURE = eINSTANCE.getEventInfo_Picture();

    /**
     * The meta object literal for the '<em><b>Attachments</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EVENT_INFO__ATTACHMENTS = eINSTANCE.getEventInfo_Attachments();

  }

} //EventsPackage
