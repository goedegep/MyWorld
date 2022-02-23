/**
 */
package goedegep.vacations.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Activity Label</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see goedegep.vacations.model.VacationsPackage#getActivityLabel()
 * @model
 * @generated
 */
public enum ActivityLabel implements Enumerator {
  /**
   * The '<em><b>VERBLIJF</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #VERBLIJF_VALUE
   * @generated
   * @ordered
   */
  VERBLIJF(0, "VERBLIJF", "Verblijf"),

  /**
   * The '<em><b>WANDELING</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #WANDELING_VALUE
   * @generated
   * @ordered
   */
  WANDELING(1, "WANDELING", "Wandeling"),
  /**
   * The '<em><b>HEENREIS</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #HEENREIS_VALUE
   * @generated
   * @ordered
   */
  HEENREIS(2, "HEENREIS", "Heenreis"),
  /**
   * The '<em><b>TERUGREIS</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #TERUGREIS_VALUE
   * @generated
   * @ordered
   */
  TERUGREIS(3, "TERUGREIS", "Terugreis"),
  /**
   * The '<em><b>HUURAUTO OPHALEN</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #HUURAUTO_OPHALEN_VALUE
   * @generated
   * @ordered
   */
  HUURAUTO_OPHALEN(4, "HUURAUTO_OPHALEN", "Huurauto ophalen"),
  /**
   * The '<em><b>HUURAUTO WEGBRENGEN</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #HUURAUTO_WEGBRENGEN_VALUE
   * @generated
   * @ordered
   */
  HUURAUTO_WEGBRENGEN(5, "HUURAUTO_WEGBRENGEN", "Huurauto wegbrengen"),
  /**
   * The '<em><b>UNSPECIFIED</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #UNSPECIFIED_VALUE
   * @generated
   * @ordered
   */
  UNSPECIFIED(6, "UNSPECIFIED", "UNSPECIFIED"),
  /**
   * The '<em><b>AUTORIT</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #AUTORIT_VALUE
   * @generated
   * @ordered
   */
  AUTORIT(7, "AUTORIT", "AUTORIT");

  /**
   * The '<em><b>VERBLIJF</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>VERBLIJF</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #VERBLIJF
   * @model literal="Verblijf"
   * @generated
   * @ordered
   */
  public static final int VERBLIJF_VALUE = 0;

  /**
   * The '<em><b>WANDELING</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>WANDELING</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #WANDELING
   * @model literal="Wandeling"
   * @generated
   * @ordered
   */
  public static final int WANDELING_VALUE = 1;

  /**
   * The '<em><b>HEENREIS</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>HEENREIS</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #HEENREIS
   * @model literal="Heenreis"
   * @generated
   * @ordered
   */
  public static final int HEENREIS_VALUE = 2;

  /**
   * The '<em><b>TERUGREIS</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>TERUGREIS</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #TERUGREIS
   * @model literal="Terugreis"
   * @generated
   * @ordered
   */
  public static final int TERUGREIS_VALUE = 3;

  /**
   * The '<em><b>HUURAUTO OPHALEN</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>HUURAUTO OPHALEN</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #HUURAUTO_OPHALEN
   * @model literal="Huurauto ophalen"
   * @generated
   * @ordered
   */
  public static final int HUURAUTO_OPHALEN_VALUE = 4;

  /**
   * The '<em><b>HUURAUTO WEGBRENGEN</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>HUURAUTO WEGBRENGEN</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #HUURAUTO_WEGBRENGEN
   * @model literal="Huurauto wegbrengen"
   * @generated
   * @ordered
   */
  public static final int HUURAUTO_WEGBRENGEN_VALUE = 5;

  /**
   * The '<em><b>UNSPECIFIED</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>UNSPECIFIED</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #UNSPECIFIED
   * @model
   * @generated
   * @ordered
   */
  public static final int UNSPECIFIED_VALUE = 6;

  /**
   * The '<em><b>AUTORIT</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>AUTORIT</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #AUTORIT
   * @model
   * @generated
   * @ordered
   */
  public static final int AUTORIT_VALUE = 7;

  /**
   * An array of all the '<em><b>Activity Label</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final ActivityLabel[] VALUES_ARRAY = new ActivityLabel[] { VERBLIJF, WANDELING, HEENREIS, TERUGREIS,
      HUURAUTO_OPHALEN, HUURAUTO_WEGBRENGEN, UNSPECIFIED, AUTORIT, };

  /**
   * A public read-only list of all the '<em><b>Activity Label</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<ActivityLabel> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Activity Label</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param literal the literal.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static ActivityLabel get(String literal) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      ActivityLabel result = VALUES_ARRAY[i];
      if (result.toString().equals(literal)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Activity Label</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param name the name.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static ActivityLabel getByName(String name) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      ActivityLabel result = VALUES_ARRAY[i];
      if (result.getName().equals(name)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Activity Label</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the integer value.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static ActivityLabel get(int value) {
    switch (value) {
    case VERBLIJF_VALUE:
      return VERBLIJF;
    case WANDELING_VALUE:
      return WANDELING;
    case HEENREIS_VALUE:
      return HEENREIS;
    case TERUGREIS_VALUE:
      return TERUGREIS;
    case HUURAUTO_OPHALEN_VALUE:
      return HUURAUTO_OPHALEN;
    case HUURAUTO_WEGBRENGEN_VALUE:
      return HUURAUTO_WEGBRENGEN;
    case UNSPECIFIED_VALUE:
      return UNSPECIFIED;
    case AUTORIT_VALUE:
      return AUTORIT;
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
  private ActivityLabel(int value, String name, String literal) {
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

} //ActivityLabel
