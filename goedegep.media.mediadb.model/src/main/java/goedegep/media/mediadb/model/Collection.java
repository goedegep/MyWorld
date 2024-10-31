/**
 */
package goedegep.media.mediadb.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Collection</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see goedegep.media.mediadb.model.MediadbPackage#getCollection()
 * @model
 * @generated
 */
public enum Collection implements Enumerator {
  /**
   * The '<em><b>EASY LISTENING</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Easy listening tracks.
   * <!-- end-model-doc -->
   * @see #EASY_LISTENING_VALUE
   * @generated
   * @ordered
   */
  EASY_LISTENING(0, "EASY_LISTENING", "Easy Listening"),
  /**
   * The '<em><b>FILM BACKING TRACKS</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Tracks that can be used as backing tracks in movies you edit.
   * <!-- end-model-doc -->
   * @see #FILM_BACKING_TRACKS_VALUE
   * @generated
   * @ordered
   */
  FILM_BACKING_TRACKS(1, "FILM_BACKING_TRACKS", "Film Backing Tracks"),
  /**
   * The '<em><b>FRANSTALIG</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * French track.
   * <!-- end-model-doc -->
   * @see #FRANSTALIG_VALUE
   * @generated
   * @ordered
   */
  FRANSTALIG(2, "FRANSTALIG", "Franstalig"),
  /**
   * The '<em><b>KLASSIEK</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Classical music tracks.
   * <!-- end-model-doc -->
   * @see #KLASSIEK_VALUE
   * @generated
   * @ordered
   */
  KLASSIEK(3, "KLASSIEK", "Klassiek"),
  /**
   * The '<em><b>NEDERLANDSTALIG</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Dutch tracks.
   * <!-- end-model-doc -->
   * @see #NEDERLANDSTALIG_VALUE
   * @generated
   * @ordered
   */
  NEDERLANDSTALIG(4, "NEDERLANDSTALIG", "Nederlandstalig"),
  /**
   * The '<em><b>POP</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Pop songs.
   * <!-- end-model-doc -->
   * @see #POP_VALUE
   * @generated
   * @ordered
   */
  POP(5, "POP", "Pop"),
  /**
   * The '<em><b>ROCK</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Rock music.
   * <!-- end-model-doc -->
   * @see #ROCK_VALUE
   * @generated
   * @ordered
   */
  ROCK(6, "ROCK", "Rock"),
  /**
   * The '<em><b>NOT SET</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Indicates that the value isn't set.
   * <!-- end-model-doc -->
   * @see #NOT_SET_VALUE
   * @generated
   * @ordered
   */
  NOT_SET(-1, "NOT_SET", "<not-set>"),
  /**
   * The '<em><b>PUNK</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Punk and new wave tracks.
   * <!-- end-model-doc -->
   * @see #PUNK_VALUE
   * @generated
   * @ordered
   */
  PUNK(8, "PUNK", "Punk"),
  /**
   * The '<em><b>SAMPLE</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Artist sample tracks.
   * <!-- end-model-doc -->
   * @see #SAMPLE_VALUE
   * @generated
   * @ordered
   */
  SAMPLE(9, "SAMPLE", "SAMPLE");

  /**
   * The '<em><b>EASY LISTENING</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>EASY LISTENING</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Easy listening tracks.
   * <!-- end-model-doc -->
   * @see #EASY_LISTENING
   * @model literal="Easy Listening"
   * @generated
   * @ordered
   */
  public static final int EASY_LISTENING_VALUE = 0;

  /**
   * The '<em><b>FILM BACKING TRACKS</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>FILM BACKING TRACKS</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Tracks that can be used as backing tracks in movies you edit.
   * <!-- end-model-doc -->
   * @see #FILM_BACKING_TRACKS
   * @model literal="Film Backing Tracks"
   * @generated
   * @ordered
   */
  public static final int FILM_BACKING_TRACKS_VALUE = 1;

  /**
   * The '<em><b>FRANSTALIG</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>FRANSTALIG</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * French track.
   * <!-- end-model-doc -->
   * @see #FRANSTALIG
   * @model literal="Franstalig"
   * @generated
   * @ordered
   */
  public static final int FRANSTALIG_VALUE = 2;

  /**
   * The '<em><b>KLASSIEK</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>KLASSIEK</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Classical music tracks.
   * <!-- end-model-doc -->
   * @see #KLASSIEK
   * @model literal="Klassiek"
   * @generated
   * @ordered
   */
  public static final int KLASSIEK_VALUE = 3;

  /**
   * The '<em><b>NEDERLANDSTALIG</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>NEDERLANDSTALIG</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Dutch tracks.
   * <!-- end-model-doc -->
   * @see #NEDERLANDSTALIG
   * @model literal="Nederlandstalig"
   * @generated
   * @ordered
   */
  public static final int NEDERLANDSTALIG_VALUE = 4;

  /**
   * The '<em><b>POP</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>POP</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Pop songs.
   * <!-- end-model-doc -->
   * @see #POP
   * @model literal="Pop"
   * @generated
   * @ordered
   */
  public static final int POP_VALUE = 5;

  /**
   * The '<em><b>ROCK</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Rock</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Rock music.
   * <!-- end-model-doc -->
   * @see #ROCK
   * @model literal="Rock"
   * @generated
   * @ordered
   */
  public static final int ROCK_VALUE = 6;

  /**
   * The '<em><b>NOT SET</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>NOT SET</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Indicates that the value isn't set.
   * <!-- end-model-doc -->
   * @see #NOT_SET
   * @model literal="&lt;not-set&gt;"
   * @generated
   * @ordered
   */
  public static final int NOT_SET_VALUE = -1;

  /**
   * The '<em><b>PUNK</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Punk and new wave tracks.
   * <!-- end-model-doc -->
   * @see #PUNK
   * @model literal="Punk"
   * @generated
   * @ordered
   */
  public static final int PUNK_VALUE = 8;

  /**
   * The '<em><b>SAMPLE</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Artist sample tracks.
   * <!-- end-model-doc -->
   * @see #SAMPLE
   * @model
   * @generated
   * @ordered
   */
  public static final int SAMPLE_VALUE = 9;

  /**
   * An array of all the '<em><b>Collection</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final Collection[] VALUES_ARRAY = new Collection[] { EASY_LISTENING, FILM_BACKING_TRACKS, FRANSTALIG,
      KLASSIEK, NEDERLANDSTALIG, POP, ROCK, NOT_SET, PUNK, SAMPLE, };

  /**
   * A public read-only list of all the '<em><b>Collection</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<Collection> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Collection</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param literal the literal.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static Collection get(String literal) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      Collection result = VALUES_ARRAY[i];
      if (result.toString().equals(literal)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Collection</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param name the name.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static Collection getByName(String name) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      Collection result = VALUES_ARRAY[i];
      if (result.getName().equals(name)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Collection</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the integer value.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static Collection get(int value) {
    switch (value) {
    case EASY_LISTENING_VALUE:
      return EASY_LISTENING;
    case FILM_BACKING_TRACKS_VALUE:
      return FILM_BACKING_TRACKS;
    case FRANSTALIG_VALUE:
      return FRANSTALIG;
    case KLASSIEK_VALUE:
      return KLASSIEK;
    case NEDERLANDSTALIG_VALUE:
      return NEDERLANDSTALIG;
    case POP_VALUE:
      return POP;
    case ROCK_VALUE:
      return ROCK;
    case NOT_SET_VALUE:
      return NOT_SET;
    case PUNK_VALUE:
      return PUNK;
    case SAMPLE_VALUE:
      return SAMPLE;
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
  private Collection(int value, String name, String literal) {
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

} //Collection
