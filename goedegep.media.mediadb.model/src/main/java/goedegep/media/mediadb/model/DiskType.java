/**
 */
package goedegep.media.mediadb.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Disk Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see goedegep.mediadb.mediadbPackage#getDiskType()
 * @model
 * @generated
 */
public enum DiskType implements Enumerator {
  /**
   * The '<em><b>CD</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #CD_VALUE
   * @generated
   * @ordered
   */
  CD(0, "CD", "CD"),

  /**
   * The '<em><b>CDR</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #CDR_VALUE
   * @generated
   * @ordered
   */
  CDR(1, "CDR", "CDR"),

  /**
   * The '<em><b>CDRW</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #CDRW_VALUE
   * @generated
   * @ordered
   */
  CDRW(2, "CDRW", "CDRW"),

  /**
   * The '<em><b>SACD</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SACD_VALUE
   * @generated
   * @ordered
   */
  SACD(3, "SACD", "SACD"),

  /**
   * The '<em><b>DVD</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #DVD_VALUE
   * @generated
   * @ordered
   */
  DVD(4, "DVD", "DVD");

  /**
   * The '<em><b>CD</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>CD</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #CD
   * @model
   * @generated
   * @ordered
   */
  public static final int CD_VALUE = 0;

  /**
   * The '<em><b>CDR</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>CDR</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #CDR
   * @model
   * @generated
   * @ordered
   */
  public static final int CDR_VALUE = 1;

  /**
   * The '<em><b>CDRW</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>CDRW</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #CDRW
   * @model
   * @generated
   * @ordered
   */
  public static final int CDRW_VALUE = 2;

  /**
   * The '<em><b>SACD</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>SACD</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #SACD
   * @model
   * @generated
   * @ordered
   */
  public static final int SACD_VALUE = 3;

  /**
   * The '<em><b>DVD</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>DVD</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #DVD
   * @model
   * @generated
   * @ordered
   */
  public static final int DVD_VALUE = 4;

  /**
   * An array of all the '<em><b>Disk Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final DiskType[] VALUES_ARRAY =
    new DiskType[] {
      CD,
      CDR,
      CDRW,
      SACD,
      DVD,
    };

  /**
   * A public read-only list of all the '<em><b>Disk Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<DiskType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Disk Type</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static DiskType get(String literal) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      DiskType result = VALUES_ARRAY[i];
      if (result.toString().equals(literal)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Disk Type</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static DiskType getByName(String name) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      DiskType result = VALUES_ARRAY[i];
      if (result.getName().equals(name)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Disk Type</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static DiskType get(int value) {
    switch (value) {
      case CD_VALUE: return CD;
      case CDR_VALUE: return CDR;
      case CDRW_VALUE: return CDRW;
      case SACD_VALUE: return SACD;
      case DVD_VALUE: return DVD;
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
  private DiskType(int value, String name, String literal) {
    this.value = value;
    this.name = name;
    this.literal = literal;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getValue() {
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName() {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
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
  
} //DiskType
