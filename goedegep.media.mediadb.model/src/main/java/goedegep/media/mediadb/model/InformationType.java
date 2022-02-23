/**
 */
package goedegep.media.mediadb.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Source Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see goedegep.media.mediadb.model.MediadbPackage#getInformationType()
 * @model
 * @generated
 */
public enum InformationType implements Enumerator {
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
   * The '<em><b>MP3</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #MP3_VALUE
   * @generated
   * @ordered
   */
  MP3(1, "MP3", "MP3"),
  /**
   * The '<em><b>VINYL ANALOG</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #VINYL_ANALOG_VALUE
   * @generated
   * @ordered
   */
  VINYL_ANALOG(2, "VINYL_ANALOG", "VINYL_ANALOG"),
  /**
   * The '<em><b>M2TS</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #M2TS_VALUE
   * @generated
   * @ordered
   */
  M2TS(4, "M2TS", "M2TS"),
  /**
   * The '<em><b>FLAC</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #FLAC_VALUE
   * @generated
   * @ordered
   */
  FLAC(5, "FLAC", "FLAC"),
  /**
   * The '<em><b>WAV</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #WAV_VALUE
   * @generated
   * @ordered
   */
  WAV(6, "WAV", "WAV"),
  /**
   * The '<em><b>APE</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #APE_VALUE
   * @generated
   * @ordered
   */
  APE(7, "APE", "APE"),
  /**
   * The '<em><b>AIFF</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #AIFF_VALUE
   * @generated
   * @ordered
   */
  AIFF(8, "AIFF", "AIFF");

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
   * The '<em><b>MP3</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>MP3</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #MP3
   * @model
   * @generated
   * @ordered
   */
  public static final int MP3_VALUE = 1;

  /**
   * The '<em><b>VINYL ANALOG</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #VINYL_ANALOG
   * @model
   * @generated
   * @ordered
   */
  public static final int VINYL_ANALOG_VALUE = 2;

  /**
   * The '<em><b>M2TS</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>M2TS</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #M2TS
   * @model
   * @generated
   * @ordered
   */
  public static final int M2TS_VALUE = 4;

  /**
   * The '<em><b>FLAC</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #FLAC
   * @model
   * @generated
   * @ordered
   */
  public static final int FLAC_VALUE = 5;

  /**
   * The '<em><b>WAV</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #WAV
   * @model
   * @generated
   * @ordered
   */
  public static final int WAV_VALUE = 6;

  /**
   * The '<em><b>APE</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #APE
   * @model
   * @generated
   * @ordered
   */
  public static final int APE_VALUE = 7;

  /**
   * The '<em><b>AIFF</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #AIFF
   * @model
   * @generated
   * @ordered
   */
  public static final int AIFF_VALUE = 8;

  /**
   * An array of all the '<em><b>Information Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final InformationType[] VALUES_ARRAY = new InformationType[] { NOT_SET, MP3, VINYL_ANALOG, M2TS, FLAC,
      WAV, APE, AIFF, };

  /**
   * A public read-only list of all the '<em><b>Information Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<InformationType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Information Type</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param literal the literal.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static InformationType get(String literal) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      InformationType result = VALUES_ARRAY[i];
      if (result.toString().equals(literal)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Information Type</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param name the name.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static InformationType getByName(String name) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      InformationType result = VALUES_ARRAY[i];
      if (result.getName().equals(name)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Information Type</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the integer value.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static InformationType get(int value) {
    switch (value) {
    case NOT_SET_VALUE:
      return NOT_SET;
    case MP3_VALUE:
      return MP3;
    case VINYL_ANALOG_VALUE:
      return VINYL_ANALOG;
    case M2TS_VALUE:
      return M2TS;
    case FLAC_VALUE:
      return FLAC;
    case WAV_VALUE:
      return WAV;
    case APE_VALUE:
      return APE;
    case AIFF_VALUE:
      return AIFF;
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
  private InformationType(int value, String name, String literal) {
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

} //SourceType
