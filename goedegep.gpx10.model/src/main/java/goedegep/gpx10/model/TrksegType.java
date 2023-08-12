/**
 */
package goedegep.gpx10.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trkseg Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.gpx10.model.TrksegType#getTrkpt <em>Trkpt</em>}</li>
 * </ul>
 *
 * @see goedegep.gpx10.model.GPX10Package#getTrksegType()
 * @model extendedMetaData="name='trkseg_._type' kind='elementOnly'"
 * @generated
 */
public interface TrksegType extends EObject {
  /**
   * Returns the value of the '<em><b>Trkpt</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.gpx10.model.TrkptType}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Trkpt</em>' containment reference list.
   * @see goedegep.gpx10.model.GPX10Package#getTrksegType_Trkpt()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='trkpt' namespace='##targetNamespace'"
   * @generated
   */
  EList<TrkptType> getTrkpt();

} // TrksegType
