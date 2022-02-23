/**
 */
package goedegep.gpx.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ptseg Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 	 An ordered sequence of points.  (for polygons or polylines, e.g.)
 *     
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.gpx.model.PtsegType#getPt <em>Pt</em>}</li>
 * </ul>
 *
 * @see goedegep.gpx.model.GPXPackage#getPtsegType()
 * @model extendedMetaData="name='ptsegType' kind='elementOnly'"
 * @generated
 */
public interface PtsegType extends EObject {
  /**
   * Returns the value of the '<em><b>Pt</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.gpx.model.PtType}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		 Ordered list of geographic points.
   * 		
   * <!-- end-model-doc -->
   * @return the value of the '<em>Pt</em>' containment reference list.
   * @see goedegep.gpx.model.GPXPackage#getPtsegType_Pt()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='pt' namespace='##targetNamespace'"
   * @generated
   */
  EList<PtType> getPt();

} // PtsegType
