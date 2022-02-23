/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Connection Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see goedegep.rolodex.model.RolodexPackage#getConnectionType()
 * @model
 * @generated
 */
public enum ConnectionType implements Enumerator {
  /**
   * The '<em><b>VAST</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #VAST_VALUE
   * @generated
   * @ordered
   */
  VAST(0, "VAST", "Vast"),

  /**
   * The '<em><b>MOBIEL</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #MOBIEL_VALUE
   * @generated
   * @ordered
   */
  MOBIEL(1, "MOBIEL", "Mobiel"),
  /**
   * The '<em><b>SERVICENUMMER BETAALD</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #SERVICENUMMER_BETAALD_VALUE
   * @generated
   * @ordered
   */
  SERVICENUMMER_BETAALD(2, "SERVICENUMMER_BETAALD", "Servicenummer (betaald)"),
  /**
   * The '<em><b>SERVICENUMMER GRATIS</b></em>' literal object.
   * <!-- begin-user-doc -->
  * <!-- end-user-doc -->
   * @see #SERVICENUMMER_GRATIS_VALUE
   * @generated
   * @ordered
   */
  SERVICENUMMER_GRATIS(3, "SERVICENUMMER_GRATIS", "Servicenummer (gratis)");

  /**
   * The '<em><b>VAST</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>VAST</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #VAST
   * @model literal="Vast"
   * @generated
   * @ordered
   */
  public static final int VAST_VALUE = 0;

  /**
   * The '<em><b>MOBIEL</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>MOBIEL</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #MOBIEL
   * @model literal="Mobiel"
   * @generated
   * @ordered
   */
  public static final int MOBIEL_VALUE = 1;

  /**
   * The '<em><b>SERVICENUMMER BETAALD</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>SERVICENUMMER BETAALD</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #SERVICENUMMER_BETAALD
   * @model literal="Servicenummer (betaald)"
   * @generated
   * @ordered
   */
  public static final int SERVICENUMMER_BETAALD_VALUE = 2;

  /**
   * The '<em><b>SERVICENUMMER GRATIS</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>SERVICENUMMER GRATIS</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #SERVICENUMMER_GRATIS
   * @model literal="Servicenummer (gratis)"
   * @generated
   * @ordered
   */
  public static final int SERVICENUMMER_GRATIS_VALUE = 3;

  /**
   * An array of all the '<em><b>Connection Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final ConnectionType[] VALUES_ARRAY = new ConnectionType[] { VAST, MOBIEL, SERVICENUMMER_BETAALD,
      SERVICENUMMER_GRATIS, };

  /**
   * A public read-only list of all the '<em><b>Connection Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<ConnectionType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Connection Type</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param literal the literal.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static ConnectionType get(String literal) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      ConnectionType result = VALUES_ARRAY[i];
      if (result.toString().equals(literal)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Connection Type</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param name the name.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static ConnectionType getByName(String name) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      ConnectionType result = VALUES_ARRAY[i];
      if (result.getName().equals(name)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Connection Type</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the integer value.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static ConnectionType get(int value) {
    switch (value) {
    case VAST_VALUE:
      return VAST;
    case MOBIEL_VALUE:
      return MOBIEL;
    case SERVICENUMMER_BETAALD_VALUE:
      return SERVICENUMMER_BETAALD;
    case SERVICENUMMER_GRATIS_VALUE:
      return SERVICENUMMER_GRATIS;
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
  private ConnectionType(int value, String name, String literal) {
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

} //ConnectionType
