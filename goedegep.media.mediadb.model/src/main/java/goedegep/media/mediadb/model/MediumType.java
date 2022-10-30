/**
 */
package goedegep.media.mediadb.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Medium Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see goedegep.media.mediadb.model.MediadbPackage#getMediumType()
 * @model
 * @generated
 */
public enum MediumType implements Enumerator {
  /**
   * The '<em><b>NOT SET</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #NOT_SET_VALUE
   * @generated
   * @ordered
   */
  NOT_SET(-1, "NOT_SET", "<not-set>"),
  /**
   * The '<em><b>CD AUDIO</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #CD_AUDIO_VALUE
   * @generated
   * @ordered
   */
  CD_AUDIO(1, "CD_AUDIO", "CD audio"),
  /**
   * The '<em><b>CDR AUDIO</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #CDR_AUDIO_VALUE
   * @generated
   * @ordered
   */
  CDR_AUDIO(2, "CDR_AUDIO", "CDR audio"),
  /**
   * The '<em><b>CDRW AUDIO</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #CDRW_AUDIO_VALUE
   * @generated
   * @ordered
   */
  CDRW_AUDIO(3, "CDRW_AUDIO", "CDRW audio"),
  /**
   * The '<em><b>LP</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #LP_VALUE
   * @generated
   * @ordered
   */
  LP(4, "LP", "lp"),
  /**
   * The '<em><b>SINGLE</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #SINGLE_VALUE
   * @generated
   * @ordered
   */
  SINGLE(5, "SINGLE", "single"),
  /**
   * The '<em><b>SACD</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #SACD_VALUE
   * @generated
   * @ordered
   */
  SACD(6, "SACD", "SACD"),
  /**
   * The '<em><b>DVD VIDEO</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #DVD_VIDEO_VALUE
   * @generated
   * @ordered
   */
  DVD_VIDEO(7, "DVD_VIDEO", "DVD video"),
  /**
   * The '<em><b>CD ROM</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #CD_ROM_VALUE
   * @generated
   * @ordered
   */
  CD_ROM(8, "CD_ROM", "CD ROM"),
  /**
   * The '<em><b>DVD ROM</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #DVD_ROM_VALUE
   * @generated
   * @ordered
   */
  DVD_ROM(9, "DVD_ROM", "DVD ROM"),
  /**
   * The '<em><b>HARDDISK</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #HARDDISK_VALUE
   * @generated
   * @ordered
   */
  HARDDISK(10, "HARDDISK", "harddisk");

  /**
   * The '<em><b>NOT SET</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>NOT SET</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #NOT_SET
   * @model literal="&lt;not-set&gt;"
   * @generated
   * @ordered
   */
  public static final int NOT_SET_VALUE = -1;

  /**
   * The '<em><b>CD AUDIO</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>CD AUDIO</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #CD_AUDIO
   * @model literal="CD audio"
   * @generated
   * @ordered
   */
  public static final int CD_AUDIO_VALUE = 1;

  /**
   * The '<em><b>CDR AUDIO</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #CDR_AUDIO
   * @model literal="CDR audio"
   * @generated
   * @ordered
   */
  public static final int CDR_AUDIO_VALUE = 2;

  /**
   * The '<em><b>CDRW AUDIO</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #CDRW_AUDIO
   * @model literal="CDRW audio"
   * @generated
   * @ordered
   */
  public static final int CDRW_AUDIO_VALUE = 3;

  /**
   * The '<em><b>LP</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>LP</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #LP
   * @model literal="lp"
   * @generated
   * @ordered
   */
  public static final int LP_VALUE = 4;

  /**
   * The '<em><b>SINGLE</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>SINGLE</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #SINGLE
   * @model literal="single"
   * @generated
   * @ordered
   */
  public static final int SINGLE_VALUE = 5;

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
  public static final int SACD_VALUE = 6;

  /**
   * The '<em><b>DVD VIDEO</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>DVD VIDEO</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #DVD_VIDEO
   * @model literal="DVD video"
   * @generated
   * @ordered
   */
  public static final int DVD_VIDEO_VALUE = 7;

  /**
   * The '<em><b>CD ROM</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>CD ROM</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #CD_ROM
   * @model literal="CD ROM"
   * @generated
   * @ordered
   */
  public static final int CD_ROM_VALUE = 8;

  /**
   * The '<em><b>DVD ROM</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>DVD ROM</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #DVD_ROM
   * @model literal="DVD ROM"
   * @generated
   * @ordered
   */
  public static final int DVD_ROM_VALUE = 9;

  /**
   * The '<em><b>HARDDISK</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #HARDDISK
   * @model literal="harddisk"
   * @generated
   * @ordered
   */
  public static final int HARDDISK_VALUE = 10;

  /**
   * An array of all the '<em><b>Medium Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final MediumType[] VALUES_ARRAY = new MediumType[] { NOT_SET, CD_AUDIO, CDR_AUDIO, CDRW_AUDIO, LP,
      SINGLE, SACD, DVD_VIDEO, CD_ROM, DVD_ROM, HARDDISK, };

  /**
   * A public read-only list of all the '<em><b>Medium Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<MediumType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Medium Type</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param literal the literal.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static MediumType get(String literal) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      MediumType result = VALUES_ARRAY[i];
      if (result.toString().equals(literal)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Medium Type</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param name the name.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static MediumType getByName(String name) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      MediumType result = VALUES_ARRAY[i];
      if (result.getName().equals(name)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Medium Type</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the integer value.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static MediumType get(int value) {
    switch (value) {
    case NOT_SET_VALUE:
      return NOT_SET;
    case CD_AUDIO_VALUE:
      return CD_AUDIO;
    case CDR_AUDIO_VALUE:
      return CDR_AUDIO;
    case CDRW_AUDIO_VALUE:
      return CDRW_AUDIO;
    case LP_VALUE:
      return LP;
    case SINGLE_VALUE:
      return SINGLE;
    case SACD_VALUE:
      return SACD;
    case DVD_VIDEO_VALUE:
      return DVD_VIDEO;
    case CD_ROM_VALUE:
      return CD_ROM;
    case DVD_ROM_VALUE:
      return DVD_ROM;
    case HARDDISK_VALUE:
      return HARDDISK;
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
  private MediumType(int value, String name, String literal) {
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

} //MediumType
