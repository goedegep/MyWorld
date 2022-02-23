/**
 */
package goedegep.finan.jobappointment.model.impl;

import goedegep.finan.jobappointment.model.CollectiveRaise;
import goedegep.finan.jobappointment.model.JobAppointment;
import goedegep.finan.jobappointment.model.JobAppointmentFactory;
import goedegep.finan.jobappointment.model.JobAppointmentPackage;
import goedegep.finan.jobappointment.model.ParttimePercentage;
import goedegep.finan.jobappointment.model.SalaryEvent;

import goedegep.finan.jobappointment.model.SalaryEventType;
import goedegep.finan.jobappointment.model.SalaryNotice;
import goedegep.finan.jobappointment.model.SalaryPayment;
import goedegep.types.model.TypesPackage;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class JobAppointmentPackageImpl extends EPackageImpl implements JobAppointmentPackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass salaryEventEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass collectiveRaiseEClass = null;
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass parttimePercentageEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass salaryNoticeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass salaryPaymentEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jobAppointmentEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum salaryEventTypeEEnum = null;

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
   * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private JobAppointmentPackageImpl() {
    super(eNS_URI, JobAppointmentFactory.eINSTANCE);
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
   * <p>This method is used to initialize {@link JobAppointmentPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static JobAppointmentPackage init() {
    if (isInited) return (JobAppointmentPackage)EPackage.Registry.INSTANCE.getEPackage(JobAppointmentPackage.eNS_URI);

    // Obtain or create and register package
    Object registeredJobAppointmentPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
    JobAppointmentPackageImpl theJobAppointmentPackage = registeredJobAppointmentPackage instanceof JobAppointmentPackageImpl ? (JobAppointmentPackageImpl)registeredJobAppointmentPackage : new JobAppointmentPackageImpl();

    isInited = true;

    // Initialize simple dependencies
    TypesPackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theJobAppointmentPackage.createPackageContents();

    // Initialize created meta-data
    theJobAppointmentPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theJobAppointmentPackage.freeze();

    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(JobAppointmentPackage.eNS_URI, theJobAppointmentPackage);
    return theJobAppointmentPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getSalaryEvent() {
    return salaryEventEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getSalaryEvent_SalaryEventType() {
    return (EAttribute)salaryEventEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getCollectiveRaise() {
    return collectiveRaiseEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getCollectiveRaise_Percentage() {
    return (EAttribute)collectiveRaiseEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getCollectiveRaise_Amount() {
    return (EAttribute)collectiveRaiseEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getParttimePercentage() {
    return parttimePercentageEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getParttimePercentage_ParttimePercentage() {
    return (EAttribute)parttimePercentageEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getSalaryNotice() {
    return salaryNoticeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getSalaryNotice_StartingDate() {
    return (EAttribute)salaryNoticeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getSalaryNotice_CurrentSalaryFulltime() {
    return (EAttribute)salaryNoticeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getSalaryNotice_CurrentSalaryParttime() {
    return (EAttribute)salaryNoticeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getSalaryNotice_CurrentParttimePercentage() {
    return (EAttribute)salaryNoticeEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getSalaryNotice_NewSalaryFulltime() {
    return (EAttribute)salaryNoticeEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getSalaryNotice_NewSalaryParttime() {
    return (EAttribute)salaryNoticeEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getSalaryNotice_NewParttimePercentage() {
    return (EAttribute)salaryNoticeEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getSalaryNotice_FunctionGroup() {
    return (EAttribute)salaryNoticeEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getSalaryPayment() {
    return salaryPaymentEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getSalaryPayment_GrossSalary() {
    return (EAttribute)salaryPaymentEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getSalaryPayment_Period() {
    return (EAttribute)salaryPaymentEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getJobAppointment() {
    return jobAppointmentEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getJobAppointment_Salaryevents() {
    return (EReference)jobAppointmentEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getJobAppointment_CommencementOfEmploymentDate() {
    return (EAttribute)jobAppointmentEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getJobAppointment_StartingSalary() {
    return (EAttribute)jobAppointmentEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getJobAppointment_StartingParttimePercentage() {
    return (EAttribute)jobAppointmentEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EEnum getSalaryEventType() {
    return salaryEventTypeEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public JobAppointmentFactory getJobAppointmentFactory() {
    return (JobAppointmentFactory)getEFactoryInstance();
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
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    salaryEventEClass = createEClass(SALARY_EVENT);
    createEAttribute(salaryEventEClass, SALARY_EVENT__SALARY_EVENT_TYPE);

    collectiveRaiseEClass = createEClass(COLLECTIVE_RAISE);
    createEAttribute(collectiveRaiseEClass, COLLECTIVE_RAISE__PERCENTAGE);
    createEAttribute(collectiveRaiseEClass, COLLECTIVE_RAISE__AMOUNT);

    parttimePercentageEClass = createEClass(PARTTIME_PERCENTAGE);
    createEAttribute(parttimePercentageEClass, PARTTIME_PERCENTAGE__PARTTIME_PERCENTAGE);

    salaryNoticeEClass = createEClass(SALARY_NOTICE);
    createEAttribute(salaryNoticeEClass, SALARY_NOTICE__STARTING_DATE);
    createEAttribute(salaryNoticeEClass, SALARY_NOTICE__CURRENT_SALARY_FULLTIME);
    createEAttribute(salaryNoticeEClass, SALARY_NOTICE__CURRENT_SALARY_PARTTIME);
    createEAttribute(salaryNoticeEClass, SALARY_NOTICE__CURRENT_PARTTIME_PERCENTAGE);
    createEAttribute(salaryNoticeEClass, SALARY_NOTICE__NEW_SALARY_FULLTIME);
    createEAttribute(salaryNoticeEClass, SALARY_NOTICE__NEW_SALARY_PARTTIME);
    createEAttribute(salaryNoticeEClass, SALARY_NOTICE__NEW_PARTTIME_PERCENTAGE);
    createEAttribute(salaryNoticeEClass, SALARY_NOTICE__FUNCTION_GROUP);

    salaryPaymentEClass = createEClass(SALARY_PAYMENT);
    createEAttribute(salaryPaymentEClass, SALARY_PAYMENT__GROSS_SALARY);
    createEAttribute(salaryPaymentEClass, SALARY_PAYMENT__PERIOD);

    jobAppointmentEClass = createEClass(JOB_APPOINTMENT);
    createEReference(jobAppointmentEClass, JOB_APPOINTMENT__SALARYEVENTS);
    createEAttribute(jobAppointmentEClass, JOB_APPOINTMENT__COMMENCEMENT_OF_EMPLOYMENT_DATE);
    createEAttribute(jobAppointmentEClass, JOB_APPOINTMENT__STARTING_SALARY);
    createEAttribute(jobAppointmentEClass, JOB_APPOINTMENT__STARTING_PARTTIME_PERCENTAGE);

    // Create enums
    salaryEventTypeEEnum = createEEnum(SALARY_EVENT_TYPE);
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
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    TypesPackage theTypesPackage = (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    salaryEventEClass.getESuperTypes().add(theTypesPackage.getEvent());
    collectiveRaiseEClass.getESuperTypes().add(this.getSalaryEvent());
    parttimePercentageEClass.getESuperTypes().add(this.getSalaryEvent());
    salaryNoticeEClass.getESuperTypes().add(this.getSalaryEvent());
    salaryPaymentEClass.getESuperTypes().add(this.getSalaryEvent());

    // Initialize classes, features, and operations; add parameters
    initEClass(salaryEventEClass, SalaryEvent.class, "SalaryEvent", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getSalaryEvent_SalaryEventType(), this.getSalaryEventType(), "salaryEventType", null, 0, 1, SalaryEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(collectiveRaiseEClass, CollectiveRaise.class, "CollectiveRaise", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getCollectiveRaise_Percentage(), theTypesPackage.getEFixedPointValue(), "percentage", null, 0, 1, CollectiveRaise.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCollectiveRaise_Amount(), theTypesPackage.getEMoney(), "amount", null, 0, 1, CollectiveRaise.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(parttimePercentageEClass, ParttimePercentage.class, "ParttimePercentage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getParttimePercentage_ParttimePercentage(), ecorePackage.getEIntegerObject(), "parttimePercentage", null, 0, 1, ParttimePercentage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(salaryNoticeEClass, SalaryNotice.class, "SalaryNotice", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getSalaryNotice_StartingDate(), theTypesPackage.getELocalDate(), "startingDate", null, 0, 1, SalaryNotice.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getSalaryNotice_CurrentSalaryFulltime(), theTypesPackage.getEMoney(), "currentSalaryFulltime", null, 0, 1, SalaryNotice.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getSalaryNotice_CurrentSalaryParttime(), theTypesPackage.getEMoney(), "currentSalaryParttime", null, 0, 1, SalaryNotice.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getSalaryNotice_CurrentParttimePercentage(), ecorePackage.getEIntegerObject(), "currentParttimePercentage", null, 0, 1, SalaryNotice.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getSalaryNotice_NewSalaryFulltime(), theTypesPackage.getEMoney(), "newSalaryFulltime", null, 0, 1, SalaryNotice.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getSalaryNotice_NewSalaryParttime(), theTypesPackage.getEMoney(), "newSalaryParttime", null, 0, 1, SalaryNotice.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getSalaryNotice_NewParttimePercentage(), ecorePackage.getEIntegerObject(), "newParttimePercentage", null, 0, 1, SalaryNotice.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getSalaryNotice_FunctionGroup(), ecorePackage.getEString(), "functionGroup", null, 0, 1, SalaryNotice.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(salaryPaymentEClass, SalaryPayment.class, "SalaryPayment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getSalaryPayment_GrossSalary(), theTypesPackage.getEMoney(), "grossSalary", null, 0, 1, SalaryPayment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getSalaryPayment_Period(), theTypesPackage.getEYearMonth(), "period", null, 0, 1, SalaryPayment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(jobAppointmentEClass, JobAppointment.class, "JobAppointment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getJobAppointment_Salaryevents(), this.getSalaryEvent(), null, "salaryevents", null, 0, -1, JobAppointment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getJobAppointment_CommencementOfEmploymentDate(), theTypesPackage.getELocalDate(), "commencementOfEmploymentDate", null, 0, 1, JobAppointment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getJobAppointment_StartingSalary(), theTypesPackage.getEMoney(), "startingSalary", null, 0, 1, JobAppointment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getJobAppointment_StartingParttimePercentage(), ecorePackage.getEIntegerObject(), "startingParttimePercentage", null, 0, 1, JobAppointment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Initialize enums and add enum literals
    initEEnum(salaryEventTypeEEnum, SalaryEventType.class, "SalaryEventType");
    addEEnumLiteral(salaryEventTypeEEnum, SalaryEventType.COLLECTIVE_RAISE);
    addEEnumLiteral(salaryEventTypeEEnum, SalaryEventType.PARTTIME_PERCENTAGE);
    addEEnumLiteral(salaryEventTypeEEnum, SalaryEventType.SALARY_NOTICE);
    addEEnumLiteral(salaryEventTypeEEnum, SalaryEventType.SALARY_PAYMENT);

    // Create resource
    createResource(eNS_URI);
  }

} //JobAppointmentPackageImpl
