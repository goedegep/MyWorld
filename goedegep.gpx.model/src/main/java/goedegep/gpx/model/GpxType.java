/**
 */
package goedegep.gpx.model;

import java.util.Date;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gpx Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 		GPX documents contain a metadata header, followed by waypoints, routes, and tracks.  You can add your own elements
 * 		to the extensions section of the GPX document.
 * 	  
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.gpx.model.GpxType#getMetadata <em>Metadata</em>}</li>
 *   <li>{@link goedegep.gpx.model.GpxType#getWpt <em>Wpt</em>}</li>
 *   <li>{@link goedegep.gpx.model.GpxType#getRte <em>Rte</em>}</li>
 *   <li>{@link goedegep.gpx.model.GpxType#getTrk <em>Trk</em>}</li>
 *   <li>{@link goedegep.gpx.model.GpxType#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link goedegep.gpx.model.GpxType#getVersion <em>Version</em>}</li>
 *   <li>{@link goedegep.gpx.model.GpxType#getCreator <em>Creator</em>}</li>
 * </ul>
 *
 * @see goedegep.gpx.model.GPXPackage#getGpxType()
 * @model extendedMetaData="name='gpxType' kind='elementOnly'"
 * @generated
 */
public interface GpxType extends EObject {
  /**
   * Returns the value of the '<em><b>Metadata</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		Metadata about the file.
   * 	   
   * <!-- end-model-doc -->
   * @return the value of the '<em>Metadata</em>' containment reference.
   * @see #setMetadata(MetadataType)
   * @see goedegep.gpx.model.GPXPackage#getGpxType_Metadata()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='metadata' namespace='##targetNamespace'"
   * @generated
   */
  MetadataType getMetadata();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.GpxType#getMetadata <em>Metadata</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Metadata</em>' containment reference.
   * @see #getMetadata()
   * @generated
   */
  void setMetadata(MetadataType value);

  /**
   * Returns the value of the '<em><b>Wpt</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.gpx.model.WptType}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		A list of waypoints.
   * 	   
   * <!-- end-model-doc -->
   * @return the value of the '<em>Wpt</em>' containment reference list.
   * @see goedegep.gpx.model.GPXPackage#getGpxType_Wpt()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='wpt' namespace='##targetNamespace'"
   * @generated
   */
  EList<WptType> getWpt();

  /**
   * Returns the value of the '<em><b>Rte</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.gpx.model.RteType}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		A list of routes.
   * 	   
   * <!-- end-model-doc -->
   * @return the value of the '<em>Rte</em>' containment reference list.
   * @see goedegep.gpx.model.GPXPackage#getGpxType_Rte()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='rte' namespace='##targetNamespace'"
   * @generated
   */
  EList<RteType> getRte();

  /**
   * Returns the value of the '<em><b>Trk</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.gpx.model.TrkType}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		A list of tracks.
   * 	   
   * <!-- end-model-doc -->
   * @return the value of the '<em>Trk</em>' containment reference list.
   * @see goedegep.gpx.model.GPXPackage#getGpxType_Trk()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='trk' namespace='##targetNamespace'"
   * @generated
   */
  EList<TrkType> getTrk();

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
   * @see goedegep.gpx.model.GPXPackage#getGpxType_Extensions()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='extensions' namespace='##targetNamespace'"
   * @generated
   */
  ExtensionsType getExtensions();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.GpxType#getExtensions <em>Extensions</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Extensions</em>' containment reference.
   * @see #getExtensions()
   * @generated
   */
  void setExtensions(ExtensionsType value);

  /**
   * Returns the value of the '<em><b>Version</b></em>' attribute.
   * The default value is <code>"1.1"</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		You must include the version number in your GPX document.
   * 	  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Version</em>' attribute.
   * @see #isSetVersion()
   * @see #unsetVersion()
   * @see #setVersion(String)
   * @see goedegep.gpx.model.GPXPackage#getGpxType_Version()
   * @model default="1.1" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='version'"
   * @generated
   */
  String getVersion();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.GpxType#getVersion <em>Version</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Version</em>' attribute.
   * @see #isSetVersion()
   * @see #unsetVersion()
   * @see #getVersion()
   * @generated
   */
  void setVersion(String value);

  /**
   * Unsets the value of the '{@link goedegep.gpx.model.GpxType#getVersion <em>Version</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetVersion()
   * @see #getVersion()
   * @see #setVersion(String)
   * @generated
   */
  void unsetVersion();

  /**
   * Returns whether the value of the '{@link goedegep.gpx.model.GpxType#getVersion <em>Version</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Version</em>' attribute is set.
   * @see #unsetVersion()
   * @see #getVersion()
   * @see #setVersion(String)
   * @generated
   */
  boolean isSetVersion();

  /**
   * Returns the value of the '<em><b>Creator</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		You must include the name or URL of the software that created your GPX document.  This allows others to
   * 		inform the creator of a GPX instance document that fails to validate.
   * 	  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Creator</em>' attribute.
   * @see #setCreator(String)
   * @see goedegep.gpx.model.GPXPackage#getGpxType_Creator()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='creator'"
   * @generated
   */
  String getCreator();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.GpxType#getCreator <em>Creator</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Creator</em>' attribute.
   * @see #getCreator()
   * @generated
   */
  void setCreator(String value);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Calculate the length of the GPX track.
   * <p>
   * The length is defined as the sum of the lengths of the tracks.
   * 
   * @return the length of the GPX track in meters.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  double getLength();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Calculate the total ascent in the GPX track.
   * <p>
   * The total ascent of the GPX track is the sum of the cumulative ascents of the tracks.
   * 
   * @return the GPX track's total ascent in meters, or null if this can't be calculated (e.g. if the cumulative ascent of one of the tracks can't be calculated).
   * 
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  double getCumulativeAscent();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Calculate the duration of the GPX track in milliseconds..
   * <p>
   * The duration is defined as the sum of the durations of the tracks.
   * 
   * <return> the duration of the GPX track in milliseconds..
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  Long getDuration();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get the start time of the GPX track.
   * <p>
   * The start time is the start time of the first track.
   * 
   * @return the start time of the GPX track.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  Date getStartTime();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get the end time of the GPX track.
   * <p>
   * The end time is the end time of the last track.
   * 
   * @return the end time of the GPX track.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  Date getEndTime();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Calculate the total descent in the GPX track.
   * <p>
   * The total descent of the GPX track is the sum of the cumulative descent of the tracks.
   * 
   * @return the GPX track's total descent in meters, or null if this can't be calculated (e.g. if the cumulative descent of one of the tracks can't be calculated).
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
   * Get the start elevation of the GPX track.
   * <p>
   * The start elevation is the start elevation of the first track.
   * 
   * @return the start elevation of the GPX track, or null if this isn't available.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  Double getStartElevation();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get the end elevation of the GPX track.
   * <p>
   * The end elevation is the end elevation of the last track.
   * 
   * @return the end elevation of the GPX track, or null if this isn't available.
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

} // GpxType
