/**
 */
package goedegep.media.mediadb.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Disk Format</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see goedegep.mediadb.mediadbPackage#getDiskFormat()
 * @model
 * @generated
 */
public enum DiskFormat implements Enumerator {
  /**
   * The '<em><b>CD AUDIO</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #CD_AUDIO_VALUE
   * @generated
   * @ordered
   */
  CD_AUDIO(0, "CD_AUDIO", "CD_AUDIO"),

  /**
   * The '<em><b>CD ROM</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #CD_ROM_VALUE
   * @generated
   * @ordered
   */
  CD_ROM(1, "CD_ROM", "CD_ROM"),

  /**
   * The '<em><b>DVD VIDEO</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #DVD_VIDEO_VALUE
   * @generated
   * @ordered
   */
  DVD_VIDEO(2, "DVD_VIDEO", "DVD_VIDEO"),

  /**
   * The '<em><b>DVD ROM</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #DVD_ROM_VALUE
   * @generated
   * @ordered
   */
  DVD_ROM(3, "DVD_ROM", "DVD_ROM");

  /**
   * The '<em><b>CD AUDIO</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>CD AUDIO</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #CD_AUDIO
   * @model
   * @generated
   * @ordered
   */
  public static final int CD_AUDIO_VALUE = 0;

  /**
   * The '<em><b>CD ROM</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>CD ROM</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #CD_ROM
   * @model
   * @generated
   * @ordered
   */
  public static final int CD_ROM_VALUE = 1;

  /**
   * The '<em><b>DVD VIDEO</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>DVD VIDEO</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #DVD_VIDEO
   * @model
   * @generated
   * @ordered
   */
  public static final int DVD_VIDEO_VALUE = 2;

  /**
   * The '<em><b>DVD ROM</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>DVD ROM</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #DVD_ROM
   * @model
   * @generated
   * @ordered
   */
  public static final int DVD_ROM_VALUE = 3;

  /**
   * An array of all the '<em><b>Disk Format</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final DiskFormat[] VALUES_ARRAY =
    new DiskFormat[] {
      CD_AUDIO,
      CD_ROM,
      DVD_VIDEO,
      DVD_ROM,
    };

  /**
   * A public read-only list of all the '<em><b>Disk Format</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<DiskFormat> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Disk Format</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static DiskFormat get(String literal) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      DiskFormat result = VALUES_ARRAY[i];
      if (result.toString().equals(literal)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Disk Format</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static DiskFormat getByName(String name) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      DiskFormat result = VALUES_ARRAY[i];
      if (result.getName().equals(name)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Disk Format</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static DiskFormat get(int value) {
    switch (value) {
      case CD_AUDIO_VALUE: return CD_AUDIO;
      case CD_ROM_VALUE: return CD_ROM;
      case DVD_VIDEO_VALUE: return DVD_VIDEO;
      case DVD_ROM_VALUE: return DVD_ROM;
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
  private DiskFormat(int value, String name, String literal) {
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
  
} //DiskFormat
