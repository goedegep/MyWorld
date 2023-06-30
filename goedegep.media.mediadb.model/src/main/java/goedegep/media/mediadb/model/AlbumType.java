/**
 */
package goedegep.media.mediadb.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Album Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see goedegep.media.mediadb.model.MediadbPackage#getAlbumType()
 * @model
 * @generated
 */
public enum AlbumType implements Enumerator {
  /**
   * The '<em><b>NORMAL ALBUM</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #NORMAL_ALBUM_VALUE
   * @generated
   * @ordered
   */
  NORMAL_ALBUM(0, "NORMAL_ALBUM", "Normal"),

  /**
   * The '<em><b>COMPILATION ALBUM</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #COMPILATION_ALBUM_VALUE
   * @generated
   * @ordered
   */
  COMPILATION_ALBUM(1, "COMPILATION_ALBUM", "Compilation"),

  /**
   * The '<em><b>VARIOUS ARTISTS ALBUM</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #VARIOUS_ARTISTS_ALBUM_VALUE
   * @generated
   * @ordered
   */
  VARIOUS_ARTISTS_ALBUM(2, "VARIOUS_ARTISTS_ALBUM", "Various Artists"),

  /**
   * The '<em><b>SOUNDTRACK ALBUM</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SOUNDTRACK_ALBUM_VALUE
   * @generated
   * @ordered
   */
  SOUNDTRACK_ALBUM(3, "SOUNDTRACK_ALBUM", "Soundtrack"),

  /**
   * The '<em><b>OWN COMPILATION ALBUM</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #OWN_COMPILATION_ALBUM_VALUE
   * @generated
   * @ordered
   */
  OWN_COMPILATION_ALBUM(4, "OWN_COMPILATION_ALBUM", "Own Compilation");

  /**
   * The '<em><b>NORMAL ALBUM</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #NORMAL_ALBUM
   * @model literal="Normal"
   * @generated
   * @ordered
   */
  public static final int NORMAL_ALBUM_VALUE = 0;

  /**
   * The '<em><b>COMPILATION ALBUM</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #COMPILATION_ALBUM
   * @model literal="Compilation"
   * @generated
   * @ordered
   */
  public static final int COMPILATION_ALBUM_VALUE = 1;

  /**
   * The '<em><b>VARIOUS ARTISTS ALBUM</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #VARIOUS_ARTISTS_ALBUM
   * @model literal="Various Artists"
   * @generated
   * @ordered
   */
  public static final int VARIOUS_ARTISTS_ALBUM_VALUE = 2;

  /**
   * The '<em><b>SOUNDTRACK ALBUM</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SOUNDTRACK_ALBUM
   * @model literal="Soundtrack"
   * @generated
   * @ordered
   */
  public static final int SOUNDTRACK_ALBUM_VALUE = 3;

  /**
   * The '<em><b>OWN COMPILATION ALBUM</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #OWN_COMPILATION_ALBUM
   * @model literal="Own Compilation"
   * @generated
   * @ordered
   */
  public static final int OWN_COMPILATION_ALBUM_VALUE = 4;

  /**
   * An array of all the '<em><b>Album Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final AlbumType[] VALUES_ARRAY = new AlbumType[] { NORMAL_ALBUM, COMPILATION_ALBUM,
      VARIOUS_ARTISTS_ALBUM, SOUNDTRACK_ALBUM, OWN_COMPILATION_ALBUM, };

  /**
   * A public read-only list of all the '<em><b>Album Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<AlbumType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Album Type</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param literal the literal.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static AlbumType get(String literal) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      AlbumType result = VALUES_ARRAY[i];
      if (result.toString().equals(literal)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Album Type</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param name the name.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static AlbumType getByName(String name) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      AlbumType result = VALUES_ARRAY[i];
      if (result.getName().equals(name)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Album Type</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the integer value.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static AlbumType get(int value) {
    switch (value) {
    case NORMAL_ALBUM_VALUE:
      return NORMAL_ALBUM;
    case COMPILATION_ALBUM_VALUE:
      return COMPILATION_ALBUM;
    case VARIOUS_ARTISTS_ALBUM_VALUE:
      return VARIOUS_ARTISTS_ALBUM;
    case SOUNDTRACK_ALBUM_VALUE:
      return SOUNDTRACK_ALBUM;
    case OWN_COMPILATION_ALBUM_VALUE:
      return OWN_COMPILATION_ALBUM;
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
  private AlbumType(int value, String name, String literal) {
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

} //AlbumType
