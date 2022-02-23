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
   * The feature id for the '<em><b>Firstname</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__FIRSTNAME = 0;

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
   * The feature id for the '<em><b>Birthday</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__BIRTHDAY = 3;

  /**
   * The number of structural features of the '<em>Person</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON_FEATURE_COUNT = 4;

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
   * The meta object id for the '{@link goedegep.emfsample.model.Gender <em>Gender</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.emfsample.model.Gender
   * @see goedegep.emfsample.model.impl.EmfSamplePackageImpl#getGender()
   * @generated
   */
  int GENDER = 2;


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
   * Returns the meta object for the attribute '{@link goedegep.emfsample.model.Person#getFirstname <em>Firstname</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Firstname</em>'.
   * @see goedegep.emfsample.model.Person#getFirstname()
   * @see #getPerson()
   * @generated
   */
  EAttribute getPerson_Firstname();

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
   * Returns the meta object for the reference '{@link goedegep.emfsample.model.Person#getBirthday <em>Birthday</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Birthday</em>'.
   * @see goedegep.emfsample.model.Person#getBirthday()
   * @see #getPerson()
   * @generated
   */
  EReference getPerson_Birthday();

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
     * The meta object literal for the '<em><b>Firstname</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PERSON__FIRSTNAME = eINSTANCE.getPerson_Firstname();

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
     * The meta object literal for the '<em><b>Birthday</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PERSON__BIRTHDAY = eINSTANCE.getPerson_Birthday();

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
