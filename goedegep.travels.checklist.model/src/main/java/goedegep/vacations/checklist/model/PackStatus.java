/**
 */
package goedegep.vacations.checklist.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Pack Status</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see goedegep.vacations.checklist.model.VacationChecklistPackage#getPackStatus()
 * @model
 * @generated
 */
public enum PackStatus implements Enumerator {
  /**
   * The '<em><b>TODO</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #TODO_VALUE
   * @generated
   * @ordered
   */
  TODO(0, "TODO", "TODO"),

  /**
   * The '<em><b>PACKED</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #PACKED_VALUE
   * @generated
   * @ordered
   */
  PACKED(1, "PACKED", "PACKED"),

  /**
   * The '<em><b>NOT NEEDED</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #NOT_NEEDED_VALUE
   * @generated
   * @ordered
   */
  NOT_NEEDED(2, "NOT_NEEDED", "NOT_NEEDED");

  /**
   * The '<em><b>TODO</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #TODO
   * @model
   * @generated
   * @ordered
   */
  public static final int TODO_VALUE = 0;

  /**
   * The '<em><b>PACKED</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #PACKED
   * @model
   * @generated
   * @ordered
   */
  public static final int PACKED_VALUE = 1;

  /**
   * The '<em><b>NOT NEEDED</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #NOT_NEEDED
   * @model
   * @generated
   * @ordered
   */
  public static final int NOT_NEEDED_VALUE = 2;

  /**
   * An array of all the '<em><b>Pack Status</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final PackStatus[] VALUES_ARRAY =
    new PackStatus[] {
      TODO,
      PACKED,
      NOT_NEEDED,
    };

  /**
   * A public read-only list of all the '<em><b>Pack Status</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<PackStatus> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Pack Status</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param literal the literal.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static PackStatus get(String literal) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      PackStatus result = VALUES_ARRAY[i];
      if (result.toString().equals(literal)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Pack Status</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param name the name.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static PackStatus getByName(String name) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      PackStatus result = VALUES_ARRAY[i];
      if (result.getName().equals(name)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Pack Status</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the integer value.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static PackStatus get(int value) {
    switch (value) {
      case TODO_VALUE: return TODO;
      case PACKED_VALUE: return PACKED;
      case NOT_NEEDED_VALUE: return NOT_NEEDED;
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
  private PackStatus(int value, String name, String literal) {
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
  
} //PackStatus
