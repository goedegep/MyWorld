/**
 */
package goedegep.finan.jobappointment.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.finan.jobappointment.model.JobAppointmentPackage
 * @generated
 */
public interface JobAppointmentFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  JobAppointmentFactory eINSTANCE = goedegep.finan.jobappointment.model.impl.JobAppointmentFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Collective Raise</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Collective Raise</em>'.
   * @generated
   */
  CollectiveRaise createCollectiveRaise();

  /**
   * Returns a new object of class '<em>Parttime Percentage</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Parttime Percentage</em>'.
   * @generated
   */
  ParttimePercentage createParttimePercentage();

  /**
   * Returns a new object of class '<em>Salary Notice</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Salary Notice</em>'.
   * @generated
   */
  SalaryNotice createSalaryNotice();

  /**
   * Returns a new object of class '<em>Salary Payment</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Salary Payment</em>'.
   * @generated
   */
  SalaryPayment createSalaryPayment();

  /**
   * Returns a new object of class '<em>Job Appointment</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Job Appointment</em>'.
   * @generated
   */
  JobAppointment createJobAppointment();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  JobAppointmentPackage getJobAppointmentPackage();

} //JobAppointmentFactory
