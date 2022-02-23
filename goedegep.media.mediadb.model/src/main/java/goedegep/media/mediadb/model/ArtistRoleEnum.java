/**
 */
package goedegep.media.mediadb.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Artist Role Enum</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see goedegep.media.mediadb.model.MediadbPackage#getArtistRoleEnum()
 * @model
 * @generated
 */
public enum ArtistRoleEnum implements Enumerator {
  /**
   * The '<em><b>SONGWRITER</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SONGWRITER_VALUE
   * @generated
   * @ordered
   */
  SONGWRITER(0, "SONGWRITER", "SONGWRITER"),

  /**
   * The '<em><b>BAND</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #BAND_VALUE
   * @generated
   * @ordered
   */
  BAND(1, "BAND", "BAND"),

  /**
   * The '<em><b>SINGER</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SINGER_VALUE
   * @generated
   * @ordered
   */
  SINGER(2, "SINGER", "SINGER"),

  /**
   * The '<em><b>MUSICIAN</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #MUSICIAN_VALUE
   * @generated
   * @ordered
   */
  MUSICIAN(3, "MUSICIAN", "MUSICIAN");

  /**
   * The '<em><b>SONGWRITER</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>SONGWRITER</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #SONGWRITER
   * @model
   * @generated
   * @ordered
   */
  public static final int SONGWRITER_VALUE = 0;

  /**
   * The '<em><b>BAND</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>BAND</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #BAND
   * @model
   * @generated
   * @ordered
   */
  public static final int BAND_VALUE = 1;

  /**
   * The '<em><b>SINGER</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>SINGER</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #SINGER
   * @model
   * @generated
   * @ordered
   */
  public static final int SINGER_VALUE = 2;

  /**
   * The '<em><b>MUSICIAN</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>MUSICIAN</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #MUSICIAN
   * @model
   * @generated
   * @ordered
   */
  public static final int MUSICIAN_VALUE = 3;

  /**
   * An array of all the '<em><b>Artist Role Enum</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final ArtistRoleEnum[] VALUES_ARRAY = new ArtistRoleEnum[] { SONGWRITER, BAND, SINGER, MUSICIAN, };

  /**
   * A public read-only list of all the '<em><b>Artist Role Enum</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<ArtistRoleEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Artist Role Enum</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param literal the literal.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static ArtistRoleEnum get(String literal) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      ArtistRoleEnum result = VALUES_ARRAY[i];
      if (result.toString().equals(literal)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Artist Role Enum</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param name the name.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static ArtistRoleEnum getByName(String name) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      ArtistRoleEnum result = VALUES_ARRAY[i];
      if (result.getName().equals(name)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Artist Role Enum</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the integer value.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static ArtistRoleEnum get(int value) {
    switch (value) {
    case SONGWRITER_VALUE:
      return SONGWRITER;
    case BAND_VALUE:
      return BAND;
    case SINGER_VALUE:
      return SINGER;
    case MUSICIAN_VALUE:
      return MUSICIAN;
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
  private ArtistRoleEnum(int value, String name, String literal) {
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

} //ArtistRoleEnum
