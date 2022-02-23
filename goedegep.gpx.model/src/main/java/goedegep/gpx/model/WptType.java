/**
 */
package goedegep.gpx.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Wpt Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 		wpt represents a waypoint, point of interest, or named feature on a map.
 * 	  
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.gpx.model.WptType#getEle <em>Ele</em>}</li>
 *   <li>{@link goedegep.gpx.model.WptType#getTime <em>Time</em>}</li>
 *   <li>{@link goedegep.gpx.model.WptType#getMagvar <em>Magvar</em>}</li>
 *   <li>{@link goedegep.gpx.model.WptType#getGeoidheight <em>Geoidheight</em>}</li>
 *   <li>{@link goedegep.gpx.model.WptType#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.gpx.model.WptType#getCmt <em>Cmt</em>}</li>
 *   <li>{@link goedegep.gpx.model.WptType#getDesc <em>Desc</em>}</li>
 *   <li>{@link goedegep.gpx.model.WptType#getSrc <em>Src</em>}</li>
 *   <li>{@link goedegep.gpx.model.WptType#getLink <em>Link</em>}</li>
 *   <li>{@link goedegep.gpx.model.WptType#getSym <em>Sym</em>}</li>
 *   <li>{@link goedegep.gpx.model.WptType#getType <em>Type</em>}</li>
 *   <li>{@link goedegep.gpx.model.WptType#getFix <em>Fix</em>}</li>
 *   <li>{@link goedegep.gpx.model.WptType#getSat <em>Sat</em>}</li>
 *   <li>{@link goedegep.gpx.model.WptType#getHdop <em>Hdop</em>}</li>
 *   <li>{@link goedegep.gpx.model.WptType#getVdop <em>Vdop</em>}</li>
 *   <li>{@link goedegep.gpx.model.WptType#getPdop <em>Pdop</em>}</li>
 *   <li>{@link goedegep.gpx.model.WptType#getAgeofdgpsdata <em>Ageofdgpsdata</em>}</li>
 *   <li>{@link goedegep.gpx.model.WptType#getDgpsid <em>Dgpsid</em>}</li>
 *   <li>{@link goedegep.gpx.model.WptType#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link goedegep.gpx.model.WptType#getLat <em>Lat</em>}</li>
 *   <li>{@link goedegep.gpx.model.WptType#getLon <em>Lon</em>}</li>
 * </ul>
 *
 * @see goedegep.gpx.model.GPXPackage#getWptType()
 * @model extendedMetaData="name='wptType' kind='elementOnly'"
 * @generated
 */
public interface WptType extends EObject {
  /**
   * Returns the value of the '<em><b>Ele</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 			Elevation (in meters) of the point.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Ele</em>' attribute.
   * @see #setEle(BigDecimal)
   * @see goedegep.gpx.model.GPXPackage#getWptType_Ele()
   * @model dataType="org.eclipse.emf.ecore.xml.type.Decimal"
   *        extendedMetaData="kind='element' name='ele' namespace='##targetNamespace'"
   * @generated
   */
  BigDecimal getEle();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.WptType#getEle <em>Ele</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Ele</em>' attribute.
   * @see #getEle()
   * @generated
   */
  void setEle(BigDecimal value);

  /**
   * Returns the value of the '<em><b>Time</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 			Creation/modification timestamp for element. Date and time in are in Univeral Coordinated Time (UTC), not local time! Conforms to ISO 8601 specification for date/time representation. Fractional seconds are allowed for millisecond timing in tracklogs. 
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Time</em>' attribute.
   * @see #setTime(XMLGregorianCalendar)
   * @see goedegep.gpx.model.GPXPackage#getWptType_Time()
   * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
   *        extendedMetaData="kind='element' name='time' namespace='##targetNamespace'"
   * @generated
   */
  XMLGregorianCalendar getTime();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.WptType#getTime <em>Time</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Time</em>' attribute.
   * @see #getTime()
   * @generated
   */
  void setTime(XMLGregorianCalendar value);

  /**
   * Returns the value of the '<em><b>Magvar</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 			Magnetic variation (in degrees) at the point
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Magvar</em>' attribute.
   * @see #setMagvar(BigDecimal)
   * @see goedegep.gpx.model.GPXPackage#getWptType_Magvar()
   * @model dataType="goedegep.gpx.model.DegreesType"
   *        extendedMetaData="kind='element' name='magvar' namespace='##targetNamespace'"
   * @generated
   */
  BigDecimal getMagvar();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.WptType#getMagvar <em>Magvar</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Magvar</em>' attribute.
   * @see #getMagvar()
   * @generated
   */
  void setMagvar(BigDecimal value);

  /**
   * Returns the value of the '<em><b>Geoidheight</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 			Height (in meters) of geoid (mean sea level) above WGS84 earth ellipsoid.  As defined in NMEA GGA message.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Geoidheight</em>' attribute.
   * @see #setGeoidheight(BigDecimal)
   * @see goedegep.gpx.model.GPXPackage#getWptType_Geoidheight()
   * @model dataType="org.eclipse.emf.ecore.xml.type.Decimal"
   *        extendedMetaData="kind='element' name='geoidheight' namespace='##targetNamespace'"
   * @generated
   */
  BigDecimal getGeoidheight();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.WptType#getGeoidheight <em>Geoidheight</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Geoidheight</em>' attribute.
   * @see #getGeoidheight()
   * @generated
   */
  void setGeoidheight(BigDecimal value);

  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 			The GPS name of the waypoint. This field will be transferred to and from the GPS. GPX does not place restrictions on the length of this field or the characters contained in it. It is up to the receiving application to validate the field before sending it to the GPS.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see goedegep.gpx.model.GPXPackage#getWptType_Name()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='name' namespace='##targetNamespace'"
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.WptType#getName <em>Name</em>}' attribute.
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
   * 			GPS waypoint comment. Sent to GPS as comment. 
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Cmt</em>' attribute.
   * @see #setCmt(String)
   * @see goedegep.gpx.model.GPXPackage#getWptType_Cmt()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='cmt' namespace='##targetNamespace'"
   * @generated
   */
  String getCmt();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.WptType#getCmt <em>Cmt</em>}' attribute.
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
   * 			A text description of the element. Holds additional information about the element intended for the user, not the GPS.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Desc</em>' attribute.
   * @see #setDesc(String)
   * @see goedegep.gpx.model.GPXPackage#getWptType_Desc()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='desc' namespace='##targetNamespace'"
   * @generated
   */
  String getDesc();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.WptType#getDesc <em>Desc</em>}' attribute.
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
   * 			Source of data. Included to give user some idea of reliability and accuracy of data.  "Garmin eTrex", "USGS quad Boston North", e.g.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Src</em>' attribute.
   * @see #setSrc(String)
   * @see goedegep.gpx.model.GPXPackage#getWptType_Src()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='src' namespace='##targetNamespace'"
   * @generated
   */
  String getSrc();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.WptType#getSrc <em>Src</em>}' attribute.
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
   * 			Link to additional information about the waypoint.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Link</em>' containment reference list.
   * @see goedegep.gpx.model.GPXPackage#getWptType_Link()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='link' namespace='##targetNamespace'"
   * @generated
   */
  EList<LinkType> getLink();

  /**
   * Returns the value of the '<em><b>Sym</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 			Text of GPS symbol name. For interchange with other programs, use the exact spelling of the symbol as displayed on the GPS.  If the GPS abbreviates words, spell them out.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Sym</em>' attribute.
   * @see #setSym(String)
   * @see goedegep.gpx.model.GPXPackage#getWptType_Sym()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='sym' namespace='##targetNamespace'"
   * @generated
   */
  String getSym();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.WptType#getSym <em>Sym</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Sym</em>' attribute.
   * @see #getSym()
   * @generated
   */
  void setSym(String value);

  /**
   * Returns the value of the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 			Type (classification) of the waypoint.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Type</em>' attribute.
   * @see #setType(String)
   * @see goedegep.gpx.model.GPXPackage#getWptType_Type()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='type' namespace='##targetNamespace'"
   * @generated
   */
  String getType();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.WptType#getType <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' attribute.
   * @see #getType()
   * @generated
   */
  void setType(String value);

  /**
   * Returns the value of the '<em><b>Fix</b></em>' attribute.
   * The literals are from the enumeration {@link goedegep.gpx.model.FixType}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 			Type of GPX fix.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Fix</em>' attribute.
   * @see goedegep.gpx.model.FixType
   * @see #isSetFix()
   * @see #unsetFix()
   * @see #setFix(FixType)
   * @see goedegep.gpx.model.GPXPackage#getWptType_Fix()
   * @model unsettable="true"
   *        extendedMetaData="kind='element' name='fix' namespace='##targetNamespace'"
   * @generated
   */
  FixType getFix();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.WptType#getFix <em>Fix</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Fix</em>' attribute.
   * @see goedegep.gpx.model.FixType
   * @see #isSetFix()
   * @see #unsetFix()
   * @see #getFix()
   * @generated
   */
  void setFix(FixType value);

  /**
   * Unsets the value of the '{@link goedegep.gpx.model.WptType#getFix <em>Fix</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetFix()
   * @see #getFix()
   * @see #setFix(FixType)
   * @generated
   */
  void unsetFix();

  /**
   * Returns whether the value of the '{@link goedegep.gpx.model.WptType#getFix <em>Fix</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Fix</em>' attribute is set.
   * @see #unsetFix()
   * @see #getFix()
   * @see #setFix(FixType)
   * @generated
   */
  boolean isSetFix();

  /**
   * Returns the value of the '<em><b>Sat</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 			Number of satellites used to calculate the GPX fix.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Sat</em>' attribute.
   * @see #setSat(BigInteger)
   * @see goedegep.gpx.model.GPXPackage#getWptType_Sat()
   * @model dataType="org.eclipse.emf.ecore.xml.type.NonNegativeInteger"
   *        extendedMetaData="kind='element' name='sat' namespace='##targetNamespace'"
   * @generated
   */
  BigInteger getSat();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.WptType#getSat <em>Sat</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Sat</em>' attribute.
   * @see #getSat()
   * @generated
   */
  void setSat(BigInteger value);

  /**
   * Returns the value of the '<em><b>Hdop</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 			Horizontal dilution of precision.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Hdop</em>' attribute.
   * @see #setHdop(BigDecimal)
   * @see goedegep.gpx.model.GPXPackage#getWptType_Hdop()
   * @model dataType="org.eclipse.emf.ecore.xml.type.Decimal"
   *        extendedMetaData="kind='element' name='hdop' namespace='##targetNamespace'"
   * @generated
   */
  BigDecimal getHdop();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.WptType#getHdop <em>Hdop</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Hdop</em>' attribute.
   * @see #getHdop()
   * @generated
   */
  void setHdop(BigDecimal value);

  /**
   * Returns the value of the '<em><b>Vdop</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 			Vertical dilution of precision.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Vdop</em>' attribute.
   * @see #setVdop(BigDecimal)
   * @see goedegep.gpx.model.GPXPackage#getWptType_Vdop()
   * @model dataType="org.eclipse.emf.ecore.xml.type.Decimal"
   *        extendedMetaData="kind='element' name='vdop' namespace='##targetNamespace'"
   * @generated
   */
  BigDecimal getVdop();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.WptType#getVdop <em>Vdop</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Vdop</em>' attribute.
   * @see #getVdop()
   * @generated
   */
  void setVdop(BigDecimal value);

  /**
   * Returns the value of the '<em><b>Pdop</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 			Position dilution of precision.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Pdop</em>' attribute.
   * @see #setPdop(BigDecimal)
   * @see goedegep.gpx.model.GPXPackage#getWptType_Pdop()
   * @model dataType="org.eclipse.emf.ecore.xml.type.Decimal"
   *        extendedMetaData="kind='element' name='pdop' namespace='##targetNamespace'"
   * @generated
   */
  BigDecimal getPdop();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.WptType#getPdop <em>Pdop</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Pdop</em>' attribute.
   * @see #getPdop()
   * @generated
   */
  void setPdop(BigDecimal value);

  /**
   * Returns the value of the '<em><b>Ageofdgpsdata</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 			Number of seconds since last DGPS update.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Ageofdgpsdata</em>' attribute.
   * @see #setAgeofdgpsdata(BigDecimal)
   * @see goedegep.gpx.model.GPXPackage#getWptType_Ageofdgpsdata()
   * @model dataType="org.eclipse.emf.ecore.xml.type.Decimal"
   *        extendedMetaData="kind='element' name='ageofdgpsdata' namespace='##targetNamespace'"
   * @generated
   */
  BigDecimal getAgeofdgpsdata();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.WptType#getAgeofdgpsdata <em>Ageofdgpsdata</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Ageofdgpsdata</em>' attribute.
   * @see #getAgeofdgpsdata()
   * @generated
   */
  void setAgeofdgpsdata(BigDecimal value);

  /**
   * Returns the value of the '<em><b>Dgpsid</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 			ID of DGPS station used in differential correction.
   * 		  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Dgpsid</em>' attribute.
   * @see #setDgpsid(BigInteger)
   * @see goedegep.gpx.model.GPXPackage#getWptType_Dgpsid()
   * @model dataType="goedegep.gpx.model.DgpsStationType"
   *        extendedMetaData="kind='element' name='dgpsid' namespace='##targetNamespace'"
   * @generated
   */
  BigInteger getDgpsid();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.WptType#getDgpsid <em>Dgpsid</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Dgpsid</em>' attribute.
   * @see #getDgpsid()
   * @generated
   */
  void setDgpsid(BigInteger value);

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
   * @see goedegep.gpx.model.GPXPackage#getWptType_Extensions()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='extensions' namespace='##targetNamespace'"
   * @generated
   */
  ExtensionsType getExtensions();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.WptType#getExtensions <em>Extensions</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Extensions</em>' containment reference.
   * @see #getExtensions()
   * @generated
   */
  void setExtensions(ExtensionsType value);

  /**
   * Returns the value of the '<em><b>Lat</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		The latitude of the point.  This is always in decimal degrees, and always in WGS84 datum.
   * 	  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Lat</em>' attribute.
   * @see #setLat(BigDecimal)
   * @see goedegep.gpx.model.GPXPackage#getWptType_Lat()
   * @model dataType="goedegep.gpx.model.LatitudeType" required="true"
   *        extendedMetaData="kind='attribute' name='lat'"
   * @generated
   */
  BigDecimal getLat();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.WptType#getLat <em>Lat</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Lat</em>' attribute.
   * @see #getLat()
   * @generated
   */
  void setLat(BigDecimal value);

  /**
   * Returns the value of the '<em><b>Lon</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   *       The longitude of the point.  This is always in decimal degrees, and always in WGS84 datum.
   *     
   * <!-- end-model-doc -->
   * @return the value of the '<em>Lon</em>' attribute.
   * @see #setLon(BigDecimal)
   * @see goedegep.gpx.model.GPXPackage#getWptType_Lon()
   * @model dataType="goedegep.gpx.model.LongitudeType" required="true"
   *        extendedMetaData="kind='attribute' name='lon'"
   * @generated
   */
  BigDecimal getLon();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.WptType#getLon <em>Lon</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Lon</em>' attribute.
   * @see #getLon()
   * @generated
   */
  void setLon(BigDecimal value);

} // WptType
