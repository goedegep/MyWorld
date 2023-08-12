/**
 */
package goedegep.gpx10.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.gpx10.model.GPX10Package
 * @generated
 */
public interface GPX10Factory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  GPX10Factory eINSTANCE = goedegep.gpx10.model.impl.GPX10FactoryImpl.init();

  /**
   * Returns a new object of class '<em>Bounds Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Bounds Type</em>'.
   * @generated
   */
  BoundsType createBoundsType();

  /**
   * Returns a new object of class '<em>Document Root</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Document Root</em>'.
   * @generated
   */
  DocumentRoot createDocumentRoot();

  /**
   * Returns a new object of class '<em>Gpx Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Gpx Type</em>'.
   * @generated
   */
  GpxType createGpxType();

  /**
   * Returns a new object of class '<em>Rtept Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Rtept Type</em>'.
   * @generated
   */
  RteptType createRteptType();

  /**
   * Returns a new object of class '<em>Rte Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Rte Type</em>'.
   * @generated
   */
  RteType createRteType();

  /**
   * Returns a new object of class '<em>Trkpt Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Trkpt Type</em>'.
   * @generated
   */
  TrkptType createTrkptType();

  /**
   * Returns a new object of class '<em>Trkseg Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Trkseg Type</em>'.
   * @generated
   */
  TrksegType createTrksegType();

  /**
   * Returns a new object of class '<em>Trk Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Trk Type</em>'.
   * @generated
   */
  TrkType createTrkType();

  /**
   * Returns a new object of class '<em>Wpt Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Wpt Type</em>'.
   * @generated
   */
  WptType createWptType();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  GPX10Package getGPX10Package();

} //GPX10Factory
