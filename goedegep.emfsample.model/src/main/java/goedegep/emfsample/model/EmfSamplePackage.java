/**
 */
package goedegep.emfsample.model;

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
 * @see goedegep.emfsample.model.EmfSampleFactory
 * @model kind="package"
 * @generated
 */
public interface EmfSamplePackage extends EPackage {
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
  String eNS_URI = "http:/goedegep.emfsample/model";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "EmfSample";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  EmfSamplePackage eINSTANCE = goedegep.emfsample.model.impl.EmfSamplePackageImpl.init();

  /**
   * The meta object id for the '{@link goedegep.emfsample.model.impl.PersonImpl <em>Person</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.emfsample.model.impl.PersonImpl
   * @see goedegep.emfsample.model.impl.EmfSamplePackageImpl#getPerson()
   * @generated
   */
  int PERSON = 0;

  /**
   * The feature id for the '<em><b>Firstnames</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__FIRSTNAMES = 0;

  /**
   * The feature id for the '<em><b>Surname</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__SURNAME = 1;

  /**
   * The feature id for the '<em><b>Gender</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__GENDER = 2;

  /**
   * The feature id for the '<em><b>Birthday</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__BIRTHDAY = 3;

  /**
   * The feature id for the '<em><b>Retirement Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__RETIREMENT_DATE = 4;

  /**
   * The feature id for the '<em><b>Has Children</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__HAS_CHILDREN = 5;

  /**
   * The number of structural features of the '<em>Person</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON_FEATURE_COUNT = 6;

  /**
   * The number of operations of the '<em>Person</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.emfsample.model.impl.BirthdayImpl <em>Birthday</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.emfsample.model.impl.BirthdayImpl
   * @see goedegep.emfsample.model.impl.EmfSamplePackageImpl#getBirthday()
   * @generated
   */
  int BIRTHDAY = 1;

  /**
   * The feature id for the '<em><b>Day</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BIRTHDAY__DAY = 0;

  /**
   * The feature id for the '<em><b>Month</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BIRTHDAY__MONTH = 1;

  /**
   * The feature id for the '<em><b>Year</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BIRTHDAY__YEAR = 2;

  /**
   * The number of structural features of the '<em>Birthday</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BIRTHDAY_FEATURE_COUNT = 3;

  /**
   * The number of operations of the '<em>Birthday</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BIRTHDAY_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.emfsample.model.impl.CompanyImpl <em>Company</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.emfsample.model.impl.CompanyImpl
   * @see goedegep.emfsample.model.impl.EmfSamplePackageImpl#getCompany()
   * @generated
   */
  int COMPANY = 2;

  /**
   * The feature id for the '<em><b>Employees</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPANY__EMPLOYEES = 0;

  /**
   * The feature id for the '<em><b>Birthdays</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPANY__BIRTHDAYS = 1;

  /**
   * The feature id for the '<em><b>Former Employees</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPANY__FORMER_EMPLOYEES = 2;

  /**
   * The number of structural features of the '<em>Company</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPANY_FEATURE_COUNT = 3;

  /**
   * The number of operations of the '<em>Company</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPANY_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.emfsample.model.Gender <em>Gender</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.emfsample.model.Gender
   * @see goedegep.emfsample.model.impl.EmfSamplePackageImpl#getGender()
   * @generated
   */
  int GENDER = 3;


  /**
   * Returns the meta object for class '{@link goedegep.emfsample.model.Person <em>Person</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Person</em>'.
   * @see goedegep.emfsample.model.Person
   * @generated
   */
  EClass getPerson();

  /**
   * Returns the meta object for the attribute list '{@link goedegep.emfsample.model.Person#getFirstnames <em>Firstnames</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Firstnames</em>'.
   * @see goedegep.emfsample.model.Person#getFirstnames()
   * @see #getPerson()
   * @generated
   */
  EAttribute getPerson_Firstnames();

  /**
   * Returns the meta object for the attribute '{@link goedegep.emfsample.model.Person#getSurname <em>Surname</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Surname</em>'.
   * @see goedegep.emfsample.model.Person#getSurname()
   * @see #getPerson()
   * @generated
   */
  EAttribute getPerson_Surname();

  /**
   * Returns the meta object for the attribute '{@link goedegep.emfsample.model.Person#getGender <em>Gender</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Gender</em>'.
   * @see goedegep.emfsample.model.Person#getGender()
   * @see #getPerson()
   * @generated
   */
  EAttribute getPerson_Gender();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.emfsample.model.Person#getBirthday <em>Birthday</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Birthday</em>'.
   * @see goedegep.emfsample.model.Person#getBirthday()
   * @see #getPerson()
   * @generated
   */
  EReference getPerson_Birthday();

  /**
   * Returns the meta object for the attribute '{@link goedegep.emfsample.model.Person#getRetirementDate <em>Retirement Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Retirement Date</em>'.
   * @see goedegep.emfsample.model.Person#getRetirementDate()
   * @see #getPerson()
   * @generated
   */
  EAttribute getPerson_RetirementDate();

  /**
   * Returns the meta object for the attribute '{@link goedegep.emfsample.model.Person#isHasChildren <em>Has Children</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Has Children</em>'.
   * @see goedegep.emfsample.model.Person#isHasChildren()
   * @see #getPerson()
   * @generated
   */
  EAttribute getPerson_HasChildren();

  /**
   * Returns the meta object for class '{@link goedegep.emfsample.model.Birthday <em>Birthday</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Birthday</em>'.
   * @see goedegep.emfsample.model.Birthday
   * @generated
   */
  EClass getBirthday();

  /**
   * Returns the meta object for the attribute '{@link goedegep.emfsample.model.Birthday#getDay <em>Day</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Day</em>'.
   * @see goedegep.emfsample.model.Birthday#getDay()
   * @see #getBirthday()
   * @generated
   */
  EAttribute getBirthday_Day();

  /**
   * Returns the meta object for the attribute '{@link goedegep.emfsample.model.Birthday#getMonth <em>Month</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Month</em>'.
   * @see goedegep.emfsample.model.Birthday#getMonth()
   * @see #getBirthday()
   * @generated
   */
  EAttribute getBirthday_Month();

  /**
   * Returns the meta object for the attribute '{@link goedegep.emfsample.model.Birthday#getYear <em>Year</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Year</em>'.
   * @see goedegep.emfsample.model.Birthday#getYear()
   * @see #getBirthday()
   * @generated
   */
  EAttribute getBirthday_Year();

  /**
   * Returns the meta object for class '{@link goedegep.emfsample.model.Company <em>Company</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Company</em>'.
   * @see goedegep.emfsample.model.Company
   * @generated
   */
  EClass getCompany();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.emfsample.model.Company#getEmployees <em>Employees</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Employees</em>'.
   * @see goedegep.emfsample.model.Company#getEmployees()
   * @see #getCompany()
   * @generated
   */
  EReference getCompany_Employees();

  /**
   * Returns the meta object for the reference list '{@link goedegep.emfsample.model.Company#getBirthdays <em>Birthdays</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Birthdays</em>'.
   * @see goedegep.emfsample.model.Company#getBirthdays()
   * @see #getCompany()
   * @generated
   */
  EReference getCompany_Birthdays();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.emfsample.model.Company#getFormerEmployees <em>Former Employees</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Former Employees</em>'.
   * @see goedegep.emfsample.model.Company#getFormerEmployees()
   * @see #getCompany()
   * @generated
   */
  EReference getCompany_FormerEmployees();

  /**
   * Returns the meta object for enum '{@link goedegep.emfsample.model.Gender <em>Gender</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Gender</em>'.
   * @see goedegep.emfsample.model.Gender
   * @generated
   */
  EEnum getGender();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  EmfSampleFactory getEmfSampleFactory();

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
     * The meta object literal for the '{@link goedegep.emfsample.model.impl.PersonImpl <em>Person</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.emfsample.model.impl.PersonImpl
     * @see goedegep.emfsample.model.impl.EmfSamplePackageImpl#getPerson()
     * @generated
     */
    EClass PERSON = eINSTANCE.getPerson();

    /**
     * The meta object literal for the '<em><b>Firstnames</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PERSON__FIRSTNAMES = eINSTANCE.getPerson_Firstnames();

    /**
     * The meta object literal for the '<em><b>Surname</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PERSON__SURNAME = eINSTANCE.getPerson_Surname();

    /**
     * The meta object literal for the '<em><b>Gender</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PERSON__GENDER = eINSTANCE.getPerson_Gender();

    /**
     * The meta object literal for the '<em><b>Birthday</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PERSON__BIRTHDAY = eINSTANCE.getPerson_Birthday();

    /**
     * The meta object literal for the '<em><b>Retirement Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PERSON__RETIREMENT_DATE = eINSTANCE.getPerson_RetirementDate();

    /**
     * The meta object literal for the '<em><b>Has Children</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PERSON__HAS_CHILDREN = eINSTANCE.getPerson_HasChildren();

    /**
     * The meta object literal for the '{@link goedegep.emfsample.model.impl.BirthdayImpl <em>Birthday</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.emfsample.model.impl.BirthdayImpl
     * @see goedegep.emfsample.model.impl.EmfSamplePackageImpl#getBirthday()
     * @generated
     */
    EClass BIRTHDAY = eINSTANCE.getBirthday();

    /**
     * The meta object literal for the '<em><b>Day</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BIRTHDAY__DAY = eINSTANCE.getBirthday_Day();

    /**
     * The meta object literal for the '<em><b>Month</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BIRTHDAY__MONTH = eINSTANCE.getBirthday_Month();

    /**
     * The meta object literal for the '<em><b>Year</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BIRTHDAY__YEAR = eINSTANCE.getBirthday_Year();

    /**
     * The meta object literal for the '{@link goedegep.emfsample.model.impl.CompanyImpl <em>Company</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.emfsample.model.impl.CompanyImpl
     * @see goedegep.emfsample.model.impl.EmfSamplePackageImpl#getCompany()
     * @generated
     */
    EClass COMPANY = eINSTANCE.getCompany();

    /**
     * The meta object literal for the '<em><b>Employees</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference COMPANY__EMPLOYEES = eINSTANCE.getCompany_Employees();

    /**
     * The meta object literal for the '<em><b>Birthdays</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference COMPANY__BIRTHDAYS = eINSTANCE.getCompany_Birthdays();

    /**
     * The meta object literal for the '<em><b>Former Employees</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference COMPANY__FORMER_EMPLOYEES = eINSTANCE.getCompany_FormerEmployees();

    /**
     * The meta object literal for the '{@link goedegep.emfsample.model.Gender <em>Gender</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.emfsample.model.Gender
     * @see goedegep.emfsample.model.impl.EmfSamplePackageImpl#getGender()
     * @generated
     */
    EEnum GENDER = eINSTANCE.getGender();

  }

} //EmfSamplePackage
