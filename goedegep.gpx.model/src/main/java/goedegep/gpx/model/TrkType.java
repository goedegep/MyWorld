/**
 */
package goedegep.gpx.model;

import java.math.BigInteger;

import java.util.Date;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trk Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 		trk represents a track - an ordered list of points describing a path.
 * 	  
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.gpx.model.TrkType#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.gpx.model.TrkType#getCmt <em>Cmt</em>}</li>
 *   <li>{@link goedegep.gpx.model.TrkType#getDesc <em>Desc</em>}</li>
 *   <li>{@link goedegep.gpx.model.TrkType#getSrc <em>Src</em>}</li>
 *   <li>{@link goedegep.gpx.model.TrkType#getLink <em>Link</em>}</li>
 *   <li>{@link goedegep.gpx.model.TrkType#getNumber <em>Number</em>}</li>
 *   <li>{@link goedegep.gpx.model.TrkType#getType <em>Type</em>}</li>
 *   <li>{@link goedegep.gpx.model.TrkType#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link goedegep.gpx.model.TrkType#getTrkseg <em>Trkseg</em>}</li>
 * </ul>
 *
 * @see goedegep.gpx.model.GPXPackage#getTrkType()
 * @model extendedMetaData="name='trkType' kind='elementOnly'"
 * @generated
 */
public interface TrkType extends EObject {
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 			GPS name of track.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see goedegep.gpx.model.GPXPackage#getTrkType_Name()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='name' namespace='##targetNamespace'"
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.TrkType#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Cmt</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 			GPS comment for track.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Cmt</em>' attribute.
   * @see #setCmt(String)
   * @see goedegep.gpx.model.GPXPackage#getTrkType_Cmt()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='cmt' namespace='##targetNamespace'"
   * @generated
   */
  String getCmt();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.TrkType#getCmt <em>Cmt</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Cmt</em>' attribute.
   * @see #getCmt()
   * @generated
   */
  void setCmt(String value);

  /**
   * Returns the value of the '<em><b>Desc</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 			User description of track.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Desc</em>' attribute.
   * @see #setDesc(String)
   * @see goedegep.gpx.model.GPXPackage#getTrkType_Desc()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='desc' namespace='##targetNamespace'"
   * @generated
   */
  String getDesc();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.TrkType#getDesc <em>Desc</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Desc</em>' attribute.
   * @see #getDesc()
   * @generated
   */
  void setDesc(String value);

  /**
   * Returns the value of the '<em><b>Src</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 			Source of data. Included to give user some idea of reliability and accuracy of data.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Src</em>' attribute.
   * @see #setSrc(String)
   * @see goedegep.gpx.model.GPXPackage#getTrkType_Src()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='src' namespace='##targetNamespace'"
   * @generated
   */
  String getSrc();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.TrkType#getSrc <em>Src</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Src</em>' attribute.
   * @see #getSrc()
   * @generated
   */
  void setSrc(String value);

  /**
   * Returns the value of the '<em><b>Link</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.gpx.model.LinkType}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 			Links to external information about track.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Link</em>' containment reference list.
   * @see goedegep.gpx.model.GPXPackage#getTrkType_Link()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='link' namespace='##targetNamespace'"
   * @generated
   */
  EList<LinkType> getLink();

  /**
   * Returns the value of the '<em><b>Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 			GPS track number.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Number</em>' attribute.
   * @see #setNumber(BigInteger)
   * @see goedegep.gpx.model.GPXPackage#getTrkType_Number()
   * @model dataType="org.eclipse.emf.ecore.xml.type.NonNegativeInteger"
   *        extendedMetaData="kind='element' name='number' namespace='##targetNamespace'"
   * @generated
   */
  BigInteger getNumber();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.TrkType#getNumber <em>Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Number</em>' attribute.
   * @see #getNumber()
   * @generated
   */
  void setNumber(BigInteger value);

  /**
   * Returns the value of the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 			Type (classification) of track.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Type</em>' attribute.
   * @see #setType(String)
   * @see goedegep.gpx.model.GPXPackage#getTrkType_Type()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='type' namespace='##targetNamespace'"
   * @generated
   */
  String getType();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.TrkType#getType <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' attribute.
   * @see #getType()
   * @generated
   */
  void setType(String value);

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
   * @see goedegep.gpx.model.GPXPackage#getTrkType_Extensions()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='extensions' namespace='##targetNamespace'"
   * @generated
   */
  ExtensionsType getExtensions();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.TrkType#getExtensions <em>Extensions</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Extensions</em>' containment reference.
   * @see #getExtensions()
   * @generated
   */
  void setExtensions(ExtensionsType value);

  /**
   * Returns the value of the '<em><b>Trkseg</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.gpx.model.TrksegType}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		A Track Segment holds a list of Track Points which are logically connected in order. To represent a single GPS track where GPS reception was lost, or the GPS receiver was turned off, start a new Track Segment for each continuous span of track data.
   * 	   
   * <!-- end-model-doc -->
   * @return the value of the '<em>Trkseg</em>' containment reference list.
   * @see goedegep.gpx.model.GPXPackage#getTrkType_Trkseg()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='trkseg' namespace='##targetNamespace'"
   * @generated
   */
  EList<TrksegType> getTrkseg();

		/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Calculate the length of the track in meters.
   * <p>
   * The length is defined as the sum of the lengths of the track segments.
   * 
   * @return the length of the track in meters.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
	double getLength();

    /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Calculate the total ascent in the track.
   * <p>
   * The total ascent of the track is the sum of the cumulative ascents of the segments of the track.
   * 
   * @return the track's total ascent in meters, or null if this can't be calculated (e.g. if the cumulative ascent of one of the segments can't be calculated).
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
   * Calculate the duration of the track in milliseconds..
   * <p>
   * The duration is defined as the sum of the durations of the track segments.
   * 
   * <return> the duration of the track in milliseconds..
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  Long getDuration();

    /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get the start time of the track.
   * <p>
   * The start time is the start time of the first segment.
   * 
   * @return the start time of the track.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  Date getStartTime();

    /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get the end time of the track.
   * <p>
   * The end time is the end time of the last segment.
   * 
   * @return the end time of the track.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  Date getEndTime();

    /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Calculate the total descent in the track.
   * <p>
   * The total descent of the track is the sum of the cumulative descent of the segments of the track.
   * 
   * @return the track's total descent in meters, or null if this can't be calculated (e.g. if the cumulative descent of one of the segments can't be calculated).
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
   * Get the start elevation of the track.
   * <p>
   * The start elevation is the start elevation of the first segment.
   * 
   * @return the start elevation of the track, or null if this isn't available.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  Double getStartElevation();

    /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get the end elevation of the track.
   * <p>
   * The end elevation is the end elevation of the last segment.
   * 
   * @return the end elevation of the track, or null if this isn't available.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  Double getEndElevation();

} // TrkType
