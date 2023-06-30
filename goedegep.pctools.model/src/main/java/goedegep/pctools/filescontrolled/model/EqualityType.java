/**
 */
package goedegep.pctools.filescontrolled.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Equality Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * This enum defines levels of 'equality'.
 *  <p>
 *  The following levels are defined:
 *  <ul>
 *    <li>SIZE - files have the same size, but have a different MD5.</li>
 *    <li>MD5 - files have the same MD5 hash, but are not EQUAL (probably rare).</li>
 *    <li>EQUAL - Files are equal, based on byte by byte compare. So they have also same MD5 and same size.</li>
 *  </ul>
 * 
 * <!-- end-model-doc -->
 * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#getEqualityType()
 * @model
 * @generated
 */
public enum EqualityType implements Enumerator {
  /**
   * The '<em><b>SIZE</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Files have the same size, but have a different MD5.
   * <!-- end-model-doc -->
   * @see #SIZE_VALUE
   * @generated
   * @ordered
   */
  SIZE(0, "SIZE", "SIZE"),

  /**
   * The '<em><b>MD5</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Files have the same MD5 hash, but are not EQUAL.
   * <!-- end-model-doc -->
   * @see #MD5_VALUE
   * @generated
   * @ordered
   */
  MD5(1, "MD5", "MD5"),

  /**
   * The '<em><b>EQUAL</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Files are equal, based on byte by byte compare. So they have also same MD5 and same size.
   * <!-- end-model-doc -->
   * @see #EQUAL_VALUE
   * @generated
   * @ordered
   */
  EQUAL(2, "EQUAL", "EQUAL"),

  /**
   * The '<em><b>NOT EQUAL</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #NOT_EQUAL_VALUE
   * @generated
   * @ordered
   */
  NOT_EQUAL(3, "NOT_EQUAL", "NOT_EQUAL");

  /**
   * The '<em><b>SIZE</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Files have the same size, but have a different MD5.
   * <!-- end-model-doc -->
   * @see #SIZE
   * @model
   * @generated
   * @ordered
   */
  public static final int SIZE_VALUE = 0;

  /**
   * The '<em><b>MD5</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Files have the same MD5 hash, but are not EQUAL.
   * <!-- end-model-doc -->
   * @see #MD5
   * @model
   * @generated
   * @ordered
   */
  public static final int MD5_VALUE = 1;

  /**
   * The '<em><b>EQUAL</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Files are equal, based on byte by byte compare. So they have also same MD5 and same size.
   * <!-- end-model-doc -->
   * @see #EQUAL
   * @model
   * @generated
   * @ordered
   */
  public static final int EQUAL_VALUE = 2;

  /**
   * The '<em><b>NOT EQUAL</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #NOT_EQUAL
   * @model
   * @generated
   * @ordered
   */
  public static final int NOT_EQUAL_VALUE = 3;

  /**
   * An array of all the '<em><b>Equality Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final EqualityType[] VALUES_ARRAY =
    new EqualityType[] {
      SIZE,
      MD5,
      EQUAL,
      NOT_EQUAL,
    };

  /**
   * A public read-only list of all the '<em><b>Equality Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<EqualityType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Equality Type</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param literal the literal.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static EqualityType get(String literal) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      EqualityType result = VALUES_ARRAY[i];
      if (result.toString().equals(literal)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Equality Type</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param name the name.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static EqualityType getByName(String name) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      EqualityType result = VALUES_ARRAY[i];
      if (result.getName().equals(name)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Equality Type</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the integer value.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static EqualityType get(int value) {
    switch (value) {
      case SIZE_VALUE: return SIZE;
      case MD5_VALUE: return MD5;
      case EQUAL_VALUE: return EQUAL;
      case NOT_EQUAL_VALUE: return NOT_EQUAL;
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
  private EqualityType(int value, String name, String literal) {
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
  
} //EqualityType
