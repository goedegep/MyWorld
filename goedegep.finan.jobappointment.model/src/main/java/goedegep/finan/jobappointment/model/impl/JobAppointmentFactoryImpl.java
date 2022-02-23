/**
 */
package goedegep.finan.jobappointment.model.impl;

import goedegep.finan.jobappointment.model.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class JobAppointmentFactoryImpl extends EFactoryImpl implements JobAppointmentFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static JobAppointmentFactory init() {
    try {
      JobAppointmentFactory theJobAppointmentFactory = (JobAppointmentFactory)EPackage.Registry.INSTANCE.getEFactory(JobAppointmentPackage.eNS_URI);
      if (theJobAppointmentFactory != null) {
        return theJobAppointmentFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new JobAppointmentFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JobAppointmentFactoryImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass) {
    switch (eClass.getClassifierID()) {
      case JobAppointmentPackage.COLLECTIVE_RAISE: return createCollectiveRaise();
      case JobAppointmentPackage.PARTTIME_PERCENTAGE: return createParttimePercentage();
      case JobAppointmentPackage.SALARY_NOTICE: return createSalaryNotice();
      case JobAppointmentPackage.SALARY_PAYMENT: return createSalaryPayment();
      case JobAppointmentPackage.JOB_APPOINTMENT: return createJobAppointment();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object createFromString(EDataType eDataType, String initialValue) {
    switch (eDataType.getClassifierID()) {
      case JobAppointmentPackage.SALARY_EVENT_TYPE:
        return createSalaryEventTypeFromString(eDataType, initialValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String convertToString(EDataType eDataType, Object instanceValue) {
    switch (eDataType.getClassifierID()) {
      case JobAppointmentPackage.SALARY_EVENT_TYPE:
        return convertSalaryEventTypeToString(eDataType, instanceValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public CollectiveRaise createCollectiveRaise() {
    CollectiveRaiseImpl collectiveRaise = new CollectiveRaiseImpl();
    return collectiveRaise;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ParttimePercentage createParttimePercentage() {
    ParttimePercentageImpl parttimePercentage = new ParttimePercentageImpl();
    return parttimePercentage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public SalaryNotice createSalaryNotice() {
    SalaryNoticeImpl salaryNotice = new SalaryNoticeImpl();
    return salaryNotice;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public SalaryPayment createSalaryPayment() {
    SalaryPaymentImpl salaryPayment = new SalaryPaymentImpl();
    return salaryPayment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public JobAppointment createJobAppointment() {
    JobAppointmentImpl jobAppointment = new JobAppointmentImpl();
    return jobAppointment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SalaryEventType createSalaryEventTypeFromString(EDataType eDataType, String initialValue) {
    SalaryEventType result = SalaryEventType.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertSalaryEventTypeToString(EDataType eDataType, Object instanceValue) {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public JobAppointmentPackage getJobAppointmentPackage() {
    return (JobAppointmentPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static JobAppointmentPackage getPackage() {
    return JobAppointmentPackage.eINSTANCE;
  }

} //JobAppointmentFactoryImpl
