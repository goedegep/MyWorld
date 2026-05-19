/**
 */
package goedegep.travels.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Information Level</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see goedegep.travels.model.TravelsPackage#getInformationLevel()
 * @model
 * @generated
 */
public enum InformationLevel implements Enumerator {
  /**
   * The '<em><b>ITEM</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #ITEM_VALUE
   * @generated
   * @ordered
   */
  ITEM(0, "ITEM", "ITEM"),
  /**
   * The '<em><b>DAY</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #DAY_VALUE
   * @generated
   * @ordered
   */
  DAY(1, "DAY", "DAY"),

  /**
   * The '<em><b>TRAVEL</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #TRAVEL_VALUE
   * @generated
   * @ordered
   */
  TRAVEL(2, "TRAVEL", "TRAVEL");

  /**
   * The '<em><b>ITEM</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #ITEM
   * @model
   * @generated
   * @ordered
   */
  public static final int ITEM_VALUE = 0;

  /**
   * The '<em><b>DAY</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #DAY
   * @model
   * @generated
   * @ordered
   */
  public static final int DAY_VALUE = 1;

  /**
   * The '<em><b>TRAVEL</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #TRAVEL
   * @model
   * @generated
   * @ordered
   */
  public static final int TRAVEL_VALUE = 2;

  /**
   * An array of all the '<em><b>Information Level</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final InformationLevel[] VALUES_ARRAY = new InformationLevel[] { ITEM, DAY, TRAVEL, };

  /**
   * A public read-only list of all the '<em><b>Information Level</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<InformationLevel> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Information Level</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param literal the literal.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static InformationLevel get(String literal) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      InformationLevel result = VALUES_ARRAY[i];
      if (result.toString().equals(literal)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Information Level</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param name the name.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static InformationLevel getByName(String name) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      InformationLevel result = VALUES_ARRAY[i];
      if (result.getName().equals(name)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Information Level</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the integer value.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static InformationLevel get(int value) {
    switch (value) {
    case ITEM_VALUE:
      return ITEM;
    case DAY_VALUE:
      return DAY;
    case TRAVEL_VALUE:
      return TRAVEL;
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
  private InformationLevel(int value, String name, String literal) {
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

} //InformationLevel
