/**
 */
package goedegep.finan.jobappointment.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Salary Event Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getSalaryEventType()
 * @model
 * @generated
 */
public enum SalaryEventType implements Enumerator {
  /**
   * The '<em><b>Collective Raise</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #COLLECTIVE_RAISE_VALUE
   * @generated
   * @ordered
   */
  COLLECTIVE_RAISE(0, "CollectiveRaise", "COLLECTIVE_RAISE"), /**
   * The '<em><b>Parttime Percentage</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #PARTTIME_PERCENTAGE_VALUE
   * @generated
   * @ordered
   */
  PARTTIME_PERCENTAGE(1, "ParttimePercentage", "PARTTIME_PERCENTAGE"), /**
   * The '<em><b>Salary Notice</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SALARY_NOTICE_VALUE
   * @generated
   * @ordered
   */
  SALARY_NOTICE(2, "SalaryNotice", "SALARY_NOTICE"), /**
   * The '<em><b>Salary Payment</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SALARY_PAYMENT_VALUE
   * @generated
   * @ordered
   */
  SALARY_PAYMENT(3, "SalaryPayment", "SALARY_PAYMENT");

  /**
   * The '<em><b>Collective Raise</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #COLLECTIVE_RAISE
   * @model name="CollectiveRaise" literal="COLLECTIVE_RAISE"
   * @generated
   * @ordered
   */
  public static final int COLLECTIVE_RAISE_VALUE = 0;

  /**
   * The '<em><b>Parttime Percentage</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #PARTTIME_PERCENTAGE
   * @model name="ParttimePercentage" literal="PARTTIME_PERCENTAGE"
   * @generated
   * @ordered
   */
  public static final int PARTTIME_PERCENTAGE_VALUE = 1;

  /**
   * The '<em><b>Salary Notice</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SALARY_NOTICE
   * @model name="SalaryNotice" literal="SALARY_NOTICE"
   * @generated
   * @ordered
   */
  public static final int SALARY_NOTICE_VALUE = 2;

  /**
   * The '<em><b>Salary Payment</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SALARY_PAYMENT
   * @model name="SalaryPayment" literal="SALARY_PAYMENT"
   * @generated
   * @ordered
   */
  public static final int SALARY_PAYMENT_VALUE = 3;

  /**
   * An array of all the '<em><b>Salary Event Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final SalaryEventType[] VALUES_ARRAY =
    new SalaryEventType[] {
      COLLECTIVE_RAISE,
      PARTTIME_PERCENTAGE,
      SALARY_NOTICE,
      SALARY_PAYMENT,
    };

  /**
   * A public read-only list of all the '<em><b>Salary Event Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<SalaryEventType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Salary Event Type</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param literal the literal.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static SalaryEventType get(String literal) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      SalaryEventType result = VALUES_ARRAY[i];
      if (result.toString().equals(literal)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Salary Event Type</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param name the name.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static SalaryEventType getByName(String name) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      SalaryEventType result = VALUES_ARRAY[i];
      if (result.getName().equals(name)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Salary Event Type</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the integer value.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static SalaryEventType get(int value) {
    switch (value) {
      case COLLECTIVE_RAISE_VALUE: return COLLECTIVE_RAISE;
      case PARTTIME_PERCENTAGE_VALUE: return PARTTIME_PERCENTAGE;
      case SALARY_NOTICE_VALUE: return SALARY_NOTICE;
      case SALARY_PAYMENT_VALUE: return SALARY_PAYMENT;
    }
    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final int value;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final String name;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final String literal;

  /**
   * Only this class can construct instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private SalaryEventType(int value, String name, String literal) {
    this.value = value;
    this.name = name;
    this.literal = literal;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int getValue() {
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getLiteral() {
    return literal;
  }

  /**
   * Returns the literal value of the enumerator, which is its string representation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    return literal;
  }
  
} //SalaryEventType
