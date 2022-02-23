/**
 */
package goedegep.gpx.model;

import java.math.BigDecimal;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bounds Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 	 Two lat/lon pairs defining the extent of an element.
 *     
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.gpx.model.BoundsType#getMinlat <em>Minlat</em>}</li>
 *   <li>{@link goedegep.gpx.model.BoundsType#getMinlon <em>Minlon</em>}</li>
 *   <li>{@link goedegep.gpx.model.BoundsType#getMaxlat <em>Maxlat</em>}</li>
 *   <li>{@link goedegep.gpx.model.BoundsType#getMaxlon <em>Maxlon</em>}</li>
 * </ul>
 *
 * @see goedegep.gpx.model.GPXPackage#getBoundsType()
 * @model extendedMetaData="name='boundsType' kind='empty'"
 * @generated
 */
public interface BoundsType extends EObject {
  /**
   * Returns the value of the '<em><b>Minlat</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		The minimum latitude.
   * 	  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Minlat</em>' attribute.
   * @see #setMinlat(BigDecimal)
   * @see goedegep.gpx.model.GPXPackage#getBoundsType_Minlat()
   * @model dataType="goedegep.gpx.model.LatitudeType" required="true"
   *        extendedMetaData="kind='attribute' name='minlat'"
   * @generated
   */
  BigDecimal getMinlat();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.BoundsType#getMinlat <em>Minlat</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Minlat</em>' attribute.
   * @see #getMinlat()
   * @generated
   */
  void setMinlat(BigDecimal value);

  /**
   * Returns the value of the '<em><b>Minlon</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		The minimum longitude.
   * 	  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Minlon</em>' attribute.
   * @see #setMinlon(BigDecimal)
   * @see goedegep.gpx.model.GPXPackage#getBoundsType_Minlon()
   * @model dataType="goedegep.gpx.model.LongitudeType" required="true"
   *        extendedMetaData="kind='attribute' name='minlon'"
   * @generated
   */
  BigDecimal getMinlon();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.BoundsType#getMinlon <em>Minlon</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Minlon</em>' attribute.
   * @see #getMinlon()
   * @generated
   */
  void setMinlon(BigDecimal value);

  /**
   * Returns the value of the '<em><b>Maxlat</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		The maximum latitude.
   * 	  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Maxlat</em>' attribute.
   * @see #setMaxlat(BigDecimal)
   * @see goedegep.gpx.model.GPXPackage#getBoundsType_Maxlat()
   * @model dataType="goedegep.gpx.model.LatitudeType" required="true"
   *        extendedMetaData="kind='attribute' name='maxlat'"
   * @generated
   */
  BigDecimal getMaxlat();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.BoundsType#getMaxlat <em>Maxlat</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Maxlat</em>' attribute.
   * @see #getMaxlat()
   * @generated
   */
  void setMaxlat(BigDecimal value);

  /**
   * Returns the value of the '<em><b>Maxlon</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		The maximum longitude.
   * 	  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Maxlon</em>' attribute.
   * @see #setMaxlon(BigDecimal)
   * @see goedegep.gpx.model.GPXPackage#getBoundsType_Maxlon()
   * @model dataType="goedegep.gpx.model.LongitudeType" required="true"
   *        extendedMetaData="kind='attribute' name='maxlon'"
   * @generated
   */
  BigDecimal getMaxlon();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.BoundsType#getMaxlon <em>Maxlon</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Maxlon</em>' attribute.
   * @see #getMaxlon()
   * @generated
   */
  void setMaxlon(BigDecimal value);

} // BoundsType
