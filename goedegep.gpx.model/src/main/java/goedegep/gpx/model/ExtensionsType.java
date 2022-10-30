/**
 */
package goedegep.gpx.model;

import java.math.BigDecimal;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Extensions Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 	 You can add extend GPX by adding your own elements from another schema here.
 *     
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.gpx.model.ExtensionsType#getAny <em>Any</em>}</li>
 *   <li>{@link goedegep.gpx.model.ExtensionsType#getSpeed <em>Speed</em>}</li>
 * </ul>
 *
 * @see goedegep.gpx.model.GPXPackage#getExtensionsType()
 * @model extendedMetaData="name='extensionsType' kind='elementOnly'"
 * @generated
 */
public interface ExtensionsType extends EObject {
  /**
   * Returns the value of the '<em><b>Any</b></em>' attribute list.
   * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		 You can add extend GPX by adding your own elements from another schema here.
   * 		
   * <!-- end-model-doc -->
   * @return the value of the '<em>Any</em>' attribute list.
   * @see goedegep.gpx.model.GPXPackage#getExtensionsType_Any()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='elementWildcard' wildcards='##other' name=':0' processing='lax'"
   * @generated
   */
  FeatureMap getAny();

  /**
   * Returns the value of the '<em><b>Speed</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Speed</em>' attribute.
   * @see #isSetSpeed()
   * @see #unsetSpeed()
   * @see #setSpeed(BigDecimal)
   * @see goedegep.gpx.model.GPXPackage#getExtensionsType_Speed()
   * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Decimal"
   * @generated
   */
  BigDecimal getSpeed();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.ExtensionsType#getSpeed <em>Speed</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Speed</em>' attribute.
   * @see #isSetSpeed()
   * @see #unsetSpeed()
   * @see #getSpeed()
   * @generated
   */
  void setSpeed(BigDecimal value);

  /**
   * Unsets the value of the '{@link goedegep.gpx.model.ExtensionsType#getSpeed <em>Speed</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetSpeed()
   * @see #getSpeed()
   * @see #setSpeed(BigDecimal)
   * @generated
   */
  void unsetSpeed();

  /**
   * Returns whether the value of the '{@link goedegep.gpx.model.ExtensionsType#getSpeed <em>Speed</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Speed</em>' attribute is set.
   * @see #unsetSpeed()
   * @see #getSpeed()
   * @see #setSpeed(BigDecimal)
   * @generated
   */
  boolean isSetSpeed();

} // ExtensionsType
