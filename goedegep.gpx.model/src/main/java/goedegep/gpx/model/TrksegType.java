/**
 */
package goedegep.gpx.model;

import java.util.Date;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trkseg Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *  	 A Track Segment holds a list of Track Points which are logically connected in order. To represent a single GPS track where GPS reception was lost, or the GPS receiver was turned off, start a new Track Segment for each continuous span of track data.
 *     
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.gpx.model.TrksegType#getTrkpt <em>Trkpt</em>}</li>
 *   <li>{@link goedegep.gpx.model.TrksegType#getExtensions <em>Extensions</em>}</li>
 * </ul>
 *
 * @see goedegep.gpx.model.GPXPackage#getTrksegType()
 * @model extendedMetaData="name='trksegType' kind='elementOnly'"
 * @generated
 */
public interface TrksegType extends EObject {
  /**
   * Returns the value of the '<em><b>Trkpt</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.gpx.model.WptType}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		A Track Point holds the coordinates, elevation, timestamp, and metadata for a single point in a track.
   * 	   
   * <!-- end-model-doc -->
   * @return the value of the '<em>Trkpt</em>' containment reference list.
   * @see goedegep.gpx.model.GPXPackage#getTrksegType_Trkpt()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='trkpt' namespace='##targetNamespace'"
   * @generated
   */
  EList<WptType> getTrkpt();

  /**
   * Returns the value of the '<em><b>Extensions</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		You can add extend GPX by adding your own elements from another schema here.
   * 	   
   * <!-- end-model-doc -->
   * @return the value of the '<em>Extensions</em>' containment reference.
   * @see #setExtensions(ExtensionsType)
   * @see goedegep.gpx.model.GPXPackage#getTrksegType_Extensions()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='extensions' namespace='##targetNamespace'"
   * @generated
   */
  ExtensionsType getExtensions();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.TrksegType#getExtensions <em>Extensions</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Extensions</em>' containment reference.
   * @see #getExtensions()
   * @generated
   */
  void setExtensions(ExtensionsType value);

		/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Calculate the length of the track segment in meters.
   * <p>
   * The length is defined as the sum of the distances between all consecutive points.
   * 
   * @return the length of the track segment in meters.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
	double getLength();

    /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Calculate the total ascent in the track segment.
   * <p>
   * The total ascent of the segment is calculated by comparing each of the segment's waypoints with their predecessors. If the
   * elevation of a waypoint is higher than the elevation of the predecessor, the total ascent is increased accordingly.
   * 
   * @return the segment's total ascent in meters, or null if this can't be calculated (e.g. if no elevation is available in the waypoints)
   * 
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  Double getCumulativeAscent();

    /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Calculate the duration of the track segment in milliseconds.
   * <p>
   * The duration is defined as the difference between the startTime and endTime.
   * 
   * <return> the duration of the track segment in milliseconds..
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  Long getDuration();

    /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get the start time of the track segment.
   * <p>
   * The start time is the time of the first waypoint.
   * 
   * @return the start time of the track segment.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  Date getStartTime();

    /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get the end time of the track segment.
   * <p>
   * The end time is the time of the last waypoint.
   * 
   * @return the end time of the track segment.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  Date getEndTime();

    /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Calculate the total descent in the segment.
   * <p>
   * The total descent of the segment is calculated by comparing each of the segment's waypoints with their predecessors. If the
   * elevation of a waypoint is lower than the elevation of the predecessor, the total descent is increased accordingly.
   * @return the segment's total descent in meters, or null if this can't be calculated (e.g. if no elevation is available in the waypoints).
   * 
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  Double getCumulativeDescent();

    /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get the start elevation of the track segment.
   * <p>
   * The start elevation is the elevation of the first waypoint.
   * 
   * @return the start elevation of the track segment, or null if this isn't available.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  Double getStartElevation();

    /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get the end elevation of the track segment.
   * <p>
   * The end elevation is the elevation of the last waypoint.
   * 
   * @return the end elevation of the track segment, or null if this isn't available.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  Double getEndElevation();

    /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model
   * @generated
   */
  void setStartTime(Date startTime);

    /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model
   * @generated
   */
  void setEndTime(Date endTime);

} // TrksegType
