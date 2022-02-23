/**
 */
package goedegep.finan.jobappointment.model;

import goedegep.types.model.TypesPackage;
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
 * @see goedegep.finan.jobappointment.model.JobAppointmentFactory
 * @model kind="package"
 * @generated
 */
public interface JobAppointmentPackage extends EPackage {
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
  String eNS_URI = "http://www.goedegep.org/jobappointment";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "JobAppointment";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  JobAppointmentPackage eINSTANCE = goedegep.finan.jobappointment.model.impl.JobAppointmentPackageImpl.init();

  /**
   * The meta object id for the '{@link goedegep.finan.jobappointment.model.impl.SalaryEventImpl <em>Salary Event</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.jobappointment.model.impl.SalaryEventImpl
   * @see goedegep.finan.jobappointment.model.impl.JobAppointmentPackageImpl#getSalaryEvent()
   * @generated
   */
  int SALARY_EVENT = 0;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_EVENT__DATE = TypesPackage.EVENT__DATE;

  /**
   * The feature id for the '<em><b>Notes</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_EVENT__NOTES = TypesPackage.EVENT__NOTES;

  /**
   * The feature id for the '<em><b>Salary Event Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_EVENT__SALARY_EVENT_TYPE = TypesPackage.EVENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Salary Event</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_EVENT_FEATURE_COUNT = TypesPackage.EVENT_FEATURE_COUNT + 1;

  /**
   * The number of operations of the '<em>Salary Event</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_EVENT_OPERATION_COUNT = TypesPackage.EVENT_OPERATION_COUNT + 0;


  /**
   * The meta object id for the '{@link goedegep.finan.jobappointment.model.impl.CollectiveRaiseImpl <em>Collective Raise</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.jobappointment.model.impl.CollectiveRaiseImpl
   * @see goedegep.finan.jobappointment.model.impl.JobAppointmentPackageImpl#getCollectiveRaise()
   * @generated
   */
  int COLLECTIVE_RAISE = 1;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COLLECTIVE_RAISE__DATE = SALARY_EVENT__DATE;

  /**
   * The feature id for the '<em><b>Notes</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COLLECTIVE_RAISE__NOTES = SALARY_EVENT__NOTES;

  /**
   * The feature id for the '<em><b>Salary Event Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COLLECTIVE_RAISE__SALARY_EVENT_TYPE = SALARY_EVENT__SALARY_EVENT_TYPE;

  /**
   * The feature id for the '<em><b>Percentage</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COLLECTIVE_RAISE__PERCENTAGE = SALARY_EVENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Amount</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COLLECTIVE_RAISE__AMOUNT = SALARY_EVENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Collective Raise</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COLLECTIVE_RAISE_FEATURE_COUNT = SALARY_EVENT_FEATURE_COUNT + 2;

  /**
   * The number of operations of the '<em>Collective Raise</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COLLECTIVE_RAISE_OPERATION_COUNT = SALARY_EVENT_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.finan.jobappointment.model.impl.ParttimePercentageImpl <em>Parttime Percentage</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.jobappointment.model.impl.ParttimePercentageImpl
   * @see goedegep.finan.jobappointment.model.impl.JobAppointmentPackageImpl#getParttimePercentage()
   * @generated
   */
  int PARTTIME_PERCENTAGE = 2;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTTIME_PERCENTAGE__DATE = SALARY_EVENT__DATE;

  /**
   * The feature id for the '<em><b>Notes</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTTIME_PERCENTAGE__NOTES = SALARY_EVENT__NOTES;

  /**
   * The feature id for the '<em><b>Salary Event Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTTIME_PERCENTAGE__SALARY_EVENT_TYPE = SALARY_EVENT__SALARY_EVENT_TYPE;

  /**
   * The feature id for the '<em><b>Parttime Percentage</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTTIME_PERCENTAGE__PARTTIME_PERCENTAGE = SALARY_EVENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Parttime Percentage</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTTIME_PERCENTAGE_FEATURE_COUNT = SALARY_EVENT_FEATURE_COUNT + 1;

  /**
   * The number of operations of the '<em>Parttime Percentage</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTTIME_PERCENTAGE_OPERATION_COUNT = SALARY_EVENT_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.finan.jobappointment.model.impl.SalaryNoticeImpl <em>Salary Notice</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.jobappointment.model.impl.SalaryNoticeImpl
   * @see goedegep.finan.jobappointment.model.impl.JobAppointmentPackageImpl#getSalaryNotice()
   * @generated
   */
  int SALARY_NOTICE = 3;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_NOTICE__DATE = SALARY_EVENT__DATE;

  /**
   * The feature id for the '<em><b>Notes</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_NOTICE__NOTES = SALARY_EVENT__NOTES;

  /**
   * The feature id for the '<em><b>Salary Event Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_NOTICE__SALARY_EVENT_TYPE = SALARY_EVENT__SALARY_EVENT_TYPE;

  /**
   * The feature id for the '<em><b>Starting Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_NOTICE__STARTING_DATE = SALARY_EVENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Current Salary Fulltime</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_NOTICE__CURRENT_SALARY_FULLTIME = SALARY_EVENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Current Salary Parttime</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_NOTICE__CURRENT_SALARY_PARTTIME = SALARY_EVENT_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Current Parttime Percentage</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_NOTICE__CURRENT_PARTTIME_PERCENTAGE = SALARY_EVENT_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>New Salary Fulltime</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_NOTICE__NEW_SALARY_FULLTIME = SALARY_EVENT_FEATURE_COUNT + 4;

  /**
   * The feature id for the '<em><b>New Salary Parttime</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_NOTICE__NEW_SALARY_PARTTIME = SALARY_EVENT_FEATURE_COUNT + 5;

  /**
   * The feature id for the '<em><b>New Parttime Percentage</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_NOTICE__NEW_PARTTIME_PERCENTAGE = SALARY_EVENT_FEATURE_COUNT + 6;

  /**
   * The feature id for the '<em><b>Function Group</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_NOTICE__FUNCTION_GROUP = SALARY_EVENT_FEATURE_COUNT + 7;

  /**
   * The number of structural features of the '<em>Salary Notice</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_NOTICE_FEATURE_COUNT = SALARY_EVENT_FEATURE_COUNT + 8;

  /**
   * The number of operations of the '<em>Salary Notice</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_NOTICE_OPERATION_COUNT = SALARY_EVENT_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.finan.jobappointment.model.impl.SalaryPaymentImpl <em>Salary Payment</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.jobappointment.model.impl.SalaryPaymentImpl
   * @see goedegep.finan.jobappointment.model.impl.JobAppointmentPackageImpl#getSalaryPayment()
   * @generated
   */
  int SALARY_PAYMENT = 4;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_PAYMENT__DATE = SALARY_EVENT__DATE;

  /**
   * The feature id for the '<em><b>Notes</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_PAYMENT__NOTES = SALARY_EVENT__NOTES;

  /**
   * The feature id for the '<em><b>Salary Event Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_PAYMENT__SALARY_EVENT_TYPE = SALARY_EVENT__SALARY_EVENT_TYPE;

  /**
   * The feature id for the '<em><b>Gross Salary</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_PAYMENT__GROSS_SALARY = SALARY_EVENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Period</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_PAYMENT__PERIOD = SALARY_EVENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Salary Payment</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_PAYMENT_FEATURE_COUNT = SALARY_EVENT_FEATURE_COUNT + 2;

  /**
   * The number of operations of the '<em>Salary Payment</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SALARY_PAYMENT_OPERATION_COUNT = SALARY_EVENT_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.finan.jobappointment.model.impl.JobAppointmentImpl <em>Job Appointment</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.jobappointment.model.impl.JobAppointmentImpl
   * @see goedegep.finan.jobappointment.model.impl.JobAppointmentPackageImpl#getJobAppointment()
   * @generated
   */
  int JOB_APPOINTMENT = 5;

  /**
   * The feature id for the '<em><b>Salaryevents</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JOB_APPOINTMENT__SALARYEVENTS = 0;

  /**
   * The feature id for the '<em><b>Commencement Of Employment Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JOB_APPOINTMENT__COMMENCEMENT_OF_EMPLOYMENT_DATE = 1;

  /**
   * The feature id for the '<em><b>Starting Salary</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JOB_APPOINTMENT__STARTING_SALARY = 2;

  /**
   * The feature id for the '<em><b>Starting Parttime Percentage</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JOB_APPOINTMENT__STARTING_PARTTIME_PERCENTAGE = 3;

  /**
   * The number of structural features of the '<em>Job Appointment</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JOB_APPOINTMENT_FEATURE_COUNT = 4;

  /**
   * The number of operations of the '<em>Job Appointment</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JOB_APPOINTMENT_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.finan.jobappointment.model.SalaryEventType <em>Salary Event Type</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.jobappointment.model.SalaryEventType
   * @see goedegep.finan.jobappointment.model.impl.JobAppointmentPackageImpl#getSalaryEventType()
   * @generated
   */
  int SALARY_EVENT_TYPE = 6;


  /**
   * Returns the meta object for class '{@link goedegep.finan.jobappointment.model.SalaryEvent <em>Salary Event</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Salary Event</em>'.
   * @see goedegep.finan.jobappointment.model.SalaryEvent
   * @generated
   */
  EClass getSalaryEvent();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.jobappointment.model.SalaryEvent#getSalaryEventType <em>Salary Event Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Salary Event Type</em>'.
   * @see goedegep.finan.jobappointment.model.SalaryEvent#getSalaryEventType()
   * @see #getSalaryEvent()
   * @generated
   */
  EAttribute getSalaryEvent_SalaryEventType();

  /**
   * Returns the meta object for class '{@link goedegep.finan.jobappointment.model.CollectiveRaise <em>Collective Raise</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Collective Raise</em>'.
   * @see goedegep.finan.jobappointment.model.CollectiveRaise
   * @generated
   */
  EClass getCollectiveRaise();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.jobappointment.model.CollectiveRaise#getPercentage <em>Percentage</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Percentage</em>'.
   * @see goedegep.finan.jobappointment.model.CollectiveRaise#getPercentage()
   * @see #getCollectiveRaise()
   * @generated
   */
  EAttribute getCollectiveRaise_Percentage();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.jobappointment.model.CollectiveRaise#getAmount <em>Amount</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Amount</em>'.
   * @see goedegep.finan.jobappointment.model.CollectiveRaise#getAmount()
   * @see #getCollectiveRaise()
   * @generated
   */
  EAttribute getCollectiveRaise_Amount();

  /**
   * Returns the meta object for class '{@link goedegep.finan.jobappointment.model.ParttimePercentage <em>Parttime Percentage</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Parttime Percentage</em>'.
   * @see goedegep.finan.jobappointment.model.ParttimePercentage
   * @generated
   */
  EClass getParttimePercentage();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.jobappointment.model.ParttimePercentage#getParttimePercentage <em>Parttime Percentage</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Parttime Percentage</em>'.
   * @see goedegep.finan.jobappointment.model.ParttimePercentage#getParttimePercentage()
   * @see #getParttimePercentage()
   * @generated
   */
  EAttribute getParttimePercentage_ParttimePercentage();

  /**
   * Returns the meta object for class '{@link goedegep.finan.jobappointment.model.SalaryNotice <em>Salary Notice</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Salary Notice</em>'.
   * @see goedegep.finan.jobappointment.model.SalaryNotice
   * @generated
   */
  EClass getSalaryNotice();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.jobappointment.model.SalaryNotice#getStartingDate <em>Starting Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Starting Date</em>'.
   * @see goedegep.finan.jobappointment.model.SalaryNotice#getStartingDate()
   * @see #getSalaryNotice()
   * @generated
   */
  EAttribute getSalaryNotice_StartingDate();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.jobappointment.model.SalaryNotice#getCurrentSalaryFulltime <em>Current Salary Fulltime</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Current Salary Fulltime</em>'.
   * @see goedegep.finan.jobappointment.model.SalaryNotice#getCurrentSalaryFulltime()
   * @see #getSalaryNotice()
   * @generated
   */
  EAttribute getSalaryNotice_CurrentSalaryFulltime();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.jobappointment.model.SalaryNotice#getCurrentSalaryParttime <em>Current Salary Parttime</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Current Salary Parttime</em>'.
   * @see goedegep.finan.jobappointment.model.SalaryNotice#getCurrentSalaryParttime()
   * @see #getSalaryNotice()
   * @generated
   */
  EAttribute getSalaryNotice_CurrentSalaryParttime();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.jobappointment.model.SalaryNotice#getCurrentParttimePercentage <em>Current Parttime Percentage</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Current Parttime Percentage</em>'.
   * @see goedegep.finan.jobappointment.model.SalaryNotice#getCurrentParttimePercentage()
   * @see #getSalaryNotice()
   * @generated
   */
  EAttribute getSalaryNotice_CurrentParttimePercentage();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.jobappointment.model.SalaryNotice#getNewSalaryFulltime <em>New Salary Fulltime</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>New Salary Fulltime</em>'.
   * @see goedegep.finan.jobappointment.model.SalaryNotice#getNewSalaryFulltime()
   * @see #getSalaryNotice()
   * @generated
   */
  EAttribute getSalaryNotice_NewSalaryFulltime();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.jobappointment.model.SalaryNotice#getNewSalaryParttime <em>New Salary Parttime</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>New Salary Parttime</em>'.
   * @see goedegep.finan.jobappointment.model.SalaryNotice#getNewSalaryParttime()
   * @see #getSalaryNotice()
   * @generated
   */
  EAttribute getSalaryNotice_NewSalaryParttime();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.jobappointment.model.SalaryNotice#getNewParttimePercentage <em>New Parttime Percentage</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>New Parttime Percentage</em>'.
   * @see goedegep.finan.jobappointment.model.SalaryNotice#getNewParttimePercentage()
   * @see #getSalaryNotice()
   * @generated
   */
  EAttribute getSalaryNotice_NewParttimePercentage();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.jobappointment.model.SalaryNotice#getFunctionGroup <em>Function Group</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Function Group</em>'.
   * @see goedegep.finan.jobappointment.model.SalaryNotice#getFunctionGroup()
   * @see #getSalaryNotice()
   * @generated
   */
  EAttribute getSalaryNotice_FunctionGroup();

  /**
   * Returns the meta object for class '{@link goedegep.finan.jobappointment.model.SalaryPayment <em>Salary Payment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Salary Payment</em>'.
   * @see goedegep.finan.jobappointment.model.SalaryPayment
   * @generated
   */
  EClass getSalaryPayment();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.jobappointment.model.SalaryPayment#getGrossSalary <em>Gross Salary</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Gross Salary</em>'.
   * @see goedegep.finan.jobappointment.model.SalaryPayment#getGrossSalary()
   * @see #getSalaryPayment()
   * @generated
   */
  EAttribute getSalaryPayment_GrossSalary();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.jobappointment.model.SalaryPayment#getPeriod <em>Period</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Period</em>'.
   * @see goedegep.finan.jobappointment.model.SalaryPayment#getPeriod()
   * @see #getSalaryPayment()
   * @generated
   */
  EAttribute getSalaryPayment_Period();

  /**
   * Returns the meta object for class '{@link goedegep.finan.jobappointment.model.JobAppointment <em>Job Appointment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Job Appointment</em>'.
   * @see goedegep.finan.jobappointment.model.JobAppointment
   * @generated
   */
  EClass getJobAppointment();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.finan.jobappointment.model.JobAppointment#getSalaryevents <em>Salaryevents</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Salaryevents</em>'.
   * @see goedegep.finan.jobappointment.model.JobAppointment#getSalaryevents()
   * @see #getJobAppointment()
   * @generated
   */
  EReference getJobAppointment_Salaryevents();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.jobappointment.model.JobAppointment#getCommencementOfEmploymentDate <em>Commencement Of Employment Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Commencement Of Employment Date</em>'.
   * @see goedegep.finan.jobappointment.model.JobAppointment#getCommencementOfEmploymentDate()
   * @see #getJobAppointment()
   * @generated
   */
  EAttribute getJobAppointment_CommencementOfEmploymentDate();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.jobappointment.model.JobAppointment#getStartingSalary <em>Starting Salary</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Starting Salary</em>'.
   * @see goedegep.finan.jobappointment.model.JobAppointment#getStartingSalary()
   * @see #getJobAppointment()
   * @generated
   */
  EAttribute getJobAppointment_StartingSalary();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.jobappointment.model.JobAppointment#getStartingParttimePercentage <em>Starting Parttime Percentage</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Starting Parttime Percentage</em>'.
   * @see goedegep.finan.jobappointment.model.JobAppointment#getStartingParttimePercentage()
   * @see #getJobAppointment()
   * @generated
   */
  EAttribute getJobAppointment_StartingParttimePercentage();

  /**
   * Returns the meta object for enum '{@link goedegep.finan.jobappointment.model.SalaryEventType <em>Salary Event Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Salary Event Type</em>'.
   * @see goedegep.finan.jobappointment.model.SalaryEventType
   * @generated
   */
  EEnum getSalaryEventType();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  JobAppointmentFactory getJobAppointmentFactory();

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
     * The meta object literal for the '{@link goedegep.finan.jobappointment.model.impl.SalaryEventImpl <em>Salary Event</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.jobappointment.model.impl.SalaryEventImpl
     * @see goedegep.finan.jobappointment.model.impl.JobAppointmentPackageImpl#getSalaryEvent()
     * @generated
     */
    EClass SALARY_EVENT = eINSTANCE.getSalaryEvent();
    /**
     * The meta object literal for the '<em><b>Salary Event Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SALARY_EVENT__SALARY_EVENT_TYPE = eINSTANCE.getSalaryEvent_SalaryEventType();
    /**
     * The meta object literal for the '{@link goedegep.finan.jobappointment.model.impl.CollectiveRaiseImpl <em>Collective Raise</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.jobappointment.model.impl.CollectiveRaiseImpl
     * @see goedegep.finan.jobappointment.model.impl.JobAppointmentPackageImpl#getCollectiveRaise()
     * @generated
     */
    EClass COLLECTIVE_RAISE = eINSTANCE.getCollectiveRaise();
    /**
     * The meta object literal for the '<em><b>Percentage</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute COLLECTIVE_RAISE__PERCENTAGE = eINSTANCE.getCollectiveRaise_Percentage();
    /**
     * The meta object literal for the '<em><b>Amount</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute COLLECTIVE_RAISE__AMOUNT = eINSTANCE.getCollectiveRaise_Amount();
    /**
     * The meta object literal for the '{@link goedegep.finan.jobappointment.model.impl.ParttimePercentageImpl <em>Parttime Percentage</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.jobappointment.model.impl.ParttimePercentageImpl
     * @see goedegep.finan.jobappointment.model.impl.JobAppointmentPackageImpl#getParttimePercentage()
     * @generated
     */
    EClass PARTTIME_PERCENTAGE = eINSTANCE.getParttimePercentage();
    /**
     * The meta object literal for the '<em><b>Parttime Percentage</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARTTIME_PERCENTAGE__PARTTIME_PERCENTAGE = eINSTANCE.getParttimePercentage_ParttimePercentage();
    /**
     * The meta object literal for the '{@link goedegep.finan.jobappointment.model.impl.SalaryNoticeImpl <em>Salary Notice</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.jobappointment.model.impl.SalaryNoticeImpl
     * @see goedegep.finan.jobappointment.model.impl.JobAppointmentPackageImpl#getSalaryNotice()
     * @generated
     */
    EClass SALARY_NOTICE = eINSTANCE.getSalaryNotice();
    /**
     * The meta object literal for the '<em><b>Starting Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SALARY_NOTICE__STARTING_DATE = eINSTANCE.getSalaryNotice_StartingDate();
    /**
     * The meta object literal for the '<em><b>Current Salary Fulltime</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SALARY_NOTICE__CURRENT_SALARY_FULLTIME = eINSTANCE.getSalaryNotice_CurrentSalaryFulltime();
    /**
     * The meta object literal for the '<em><b>Current Salary Parttime</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SALARY_NOTICE__CURRENT_SALARY_PARTTIME = eINSTANCE.getSalaryNotice_CurrentSalaryParttime();
    /**
     * The meta object literal for the '<em><b>Current Parttime Percentage</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SALARY_NOTICE__CURRENT_PARTTIME_PERCENTAGE = eINSTANCE.getSalaryNotice_CurrentParttimePercentage();
    /**
     * The meta object literal for the '<em><b>New Salary Fulltime</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SALARY_NOTICE__NEW_SALARY_FULLTIME = eINSTANCE.getSalaryNotice_NewSalaryFulltime();
    /**
     * The meta object literal for the '<em><b>New Salary Parttime</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SALARY_NOTICE__NEW_SALARY_PARTTIME = eINSTANCE.getSalaryNotice_NewSalaryParttime();
    /**
     * The meta object literal for the '<em><b>New Parttime Percentage</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SALARY_NOTICE__NEW_PARTTIME_PERCENTAGE = eINSTANCE.getSalaryNotice_NewParttimePercentage();
    /**
     * The meta object literal for the '<em><b>Function Group</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SALARY_NOTICE__FUNCTION_GROUP = eINSTANCE.getSalaryNotice_FunctionGroup();
    /**
     * The meta object literal for the '{@link goedegep.finan.jobappointment.model.impl.SalaryPaymentImpl <em>Salary Payment</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.jobappointment.model.impl.SalaryPaymentImpl
     * @see goedegep.finan.jobappointment.model.impl.JobAppointmentPackageImpl#getSalaryPayment()
     * @generated
     */
    EClass SALARY_PAYMENT = eINSTANCE.getSalaryPayment();
    /**
     * The meta object literal for the '<em><b>Gross Salary</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SALARY_PAYMENT__GROSS_SALARY = eINSTANCE.getSalaryPayment_GrossSalary();
    /**
     * The meta object literal for the '<em><b>Period</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SALARY_PAYMENT__PERIOD = eINSTANCE.getSalaryPayment_Period();
    /**
     * The meta object literal for the '{@link goedegep.finan.jobappointment.model.impl.JobAppointmentImpl <em>Job Appointment</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.jobappointment.model.impl.JobAppointmentImpl
     * @see goedegep.finan.jobappointment.model.impl.JobAppointmentPackageImpl#getJobAppointment()
     * @generated
     */
    EClass JOB_APPOINTMENT = eINSTANCE.getJobAppointment();
    /**
     * The meta object literal for the '<em><b>Salaryevents</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JOB_APPOINTMENT__SALARYEVENTS = eINSTANCE.getJobAppointment_Salaryevents();
    /**
     * The meta object literal for the '<em><b>Commencement Of Employment Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute JOB_APPOINTMENT__COMMENCEMENT_OF_EMPLOYMENT_DATE = eINSTANCE.getJobAppointment_CommencementOfEmploymentDate();
    /**
     * The meta object literal for the '<em><b>Starting Salary</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute JOB_APPOINTMENT__STARTING_SALARY = eINSTANCE.getJobAppointment_StartingSalary();
    /**
     * The meta object literal for the '<em><b>Starting Parttime Percentage</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute JOB_APPOINTMENT__STARTING_PARTTIME_PERCENTAGE = eINSTANCE.getJobAppointment_StartingParttimePercentage();
    /**
     * The meta object literal for the '{@link goedegep.finan.jobappointment.model.SalaryEventType <em>Salary Event Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.jobappointment.model.SalaryEventType
     * @see goedegep.finan.jobappointment.model.impl.JobAppointmentPackageImpl#getSalaryEventType()
     * @generated
     */
    EEnum SALARY_EVENT_TYPE = eINSTANCE.getSalaryEventType();

  }

} //JobAppointmentPackage
