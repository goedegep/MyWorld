/**
 */
package goedegep.vacations.model;

import goedegep.poi.model.POICategoryId;
import goedegep.util.datetime.FlexDate;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Locatie</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.model.Location#getLocationType <em>Location Type</em>}</li>
 *   <li>{@link goedegep.vacations.model.Location#getCountry <em>Country</em>}</li>
 *   <li>{@link goedegep.vacations.model.Location#getCity <em>City</em>}</li>
 *   <li>{@link goedegep.vacations.model.Location#getStreet <em>Street</em>}</li>
 *   <li>{@link goedegep.vacations.model.Location#getHouseNumber <em>House Number</em>}</li>
 *   <li>{@link goedegep.vacations.model.Location#getLatitude <em>Latitude</em>}</li>
 *   <li>{@link goedegep.vacations.model.Location#getLongitude <em>Longitude</em>}</li>
 *   <li>{@link goedegep.vacations.model.Location#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.vacations.model.Location#getWebSite <em>Web Site</em>}</li>
 *   <li>{@link goedegep.vacations.model.Location#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.vacations.model.Location#getLabel <em>Label</em>}</li>
 *   <li>{@link goedegep.vacations.model.Location#getDuration <em>Duration</em>}</li>
 *   <li>{@link goedegep.vacations.model.Location#getStartDate <em>Start Date</em>}</li>
 *   <li>{@link goedegep.vacations.model.Location#getEndDate <em>End Date</em>}</li>
 *   <li>{@link goedegep.vacations.model.Location#getBoundingbox <em>Boundingbox</em>}</li>
 *   <li>{@link goedegep.vacations.model.Location#getBoundaries <em>Boundaries</em>}</li>
 * </ul>
 *
 * @see goedegep.vacations.model.VacationsPackage#getLocation()
 * @model
 * @generated
 */
public interface Location extends VacationElement {
  /**
   * Returns the value of the '<em><b>Location Type</b></em>' attribute.
   * The default value is <code>"Default POI"</code>.
   * The literals are from the enumeration {@link goedegep.poi.model.POICategoryId}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Location Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Location Type</em>' attribute.
   * @see goedegep.poi.model.POICategoryId
   * @see #isSetLocationType()
   * @see #unsetLocationType()
   * @see #setLocationType(POICategoryId)
   * @see goedegep.vacations.model.VacationsPackage#getLocation_LocationType()
   * @model default="Default POI" unsettable="true"
   * @generated
   */
  POICategoryId getLocationType();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Location#getLocationType <em>Location Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Location Type</em>' attribute.
   * @see goedegep.poi.model.POICategoryId
   * @see #isSetLocationType()
   * @see #unsetLocationType()
   * @see #getLocationType()
   * @generated
   */
  void setLocationType(POICategoryId value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.Location#getLocationType <em>Location Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetLocationType()
   * @see #getLocationType()
   * @see #setLocationType(POICategoryId)
   * @generated
   */
  void unsetLocationType();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Location#getLocationType <em>Location Type</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Location Type</em>' attribute is set.
   * @see #unsetLocationType()
   * @see #getLocationType()
   * @see #setLocationType(POICategoryId)
   * @generated
   */
  boolean isSetLocationType();

  /**
   * Returns the value of the '<em><b>Country</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Country</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Country</em>' attribute.
   * @see #isSetCountry()
   * @see #unsetCountry()
   * @see #setCountry(String)
   * @see goedegep.vacations.model.VacationsPackage#getLocation_Country()
   * @model unsettable="true"
   * @generated
   */
  String getCountry();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Location#getCountry <em>Country</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Country</em>' attribute.
   * @see #isSetCountry()
   * @see #unsetCountry()
   * @see #getCountry()
   * @generated
   */
  void setCountry(String value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.Location#getCountry <em>Country</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetCountry()
   * @see #getCountry()
   * @see #setCountry(String)
   * @generated
   */
  void unsetCountry();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Location#getCountry <em>Country</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Country</em>' attribute is set.
   * @see #unsetCountry()
   * @see #getCountry()
   * @see #setCountry(String)
   * @generated
   */
  boolean isSetCountry();

  /**
   * Returns the value of the '<em><b>City</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>City</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>City</em>' attribute.
   * @see #isSetCity()
   * @see #unsetCity()
   * @see #setCity(String)
   * @see goedegep.vacations.model.VacationsPackage#getLocation_City()
   * @model unsettable="true"
   * @generated
   */
  String getCity();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Location#getCity <em>City</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>City</em>' attribute.
   * @see #isSetCity()
   * @see #unsetCity()
   * @see #getCity()
   * @generated
   */
  void setCity(String value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.Location#getCity <em>City</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetCity()
   * @see #getCity()
   * @see #setCity(String)
   * @generated
   */
  void unsetCity();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Location#getCity <em>City</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>City</em>' attribute is set.
   * @see #unsetCity()
   * @see #getCity()
   * @see #setCity(String)
   * @generated
   */
  boolean isSetCity();

  /**
   * Returns the value of the '<em><b>Street</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Street</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Street</em>' attribute.
   * @see #isSetStreet()
   * @see #unsetStreet()
   * @see #setStreet(String)
   * @see goedegep.vacations.model.VacationsPackage#getLocation_Street()
   * @model unsettable="true"
   * @generated
   */
  String getStreet();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Location#getStreet <em>Street</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Street</em>' attribute.
   * @see #isSetStreet()
   * @see #unsetStreet()
   * @see #getStreet()
   * @generated
   */
  void setStreet(String value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.Location#getStreet <em>Street</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetStreet()
   * @see #getStreet()
   * @see #setStreet(String)
   * @generated
   */
  void unsetStreet();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Location#getStreet <em>Street</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Street</em>' attribute is set.
   * @see #unsetStreet()
   * @see #getStreet()
   * @see #setStreet(String)
   * @generated
   */
  boolean isSetStreet();

  /**
   * Returns the value of the '<em><b>House Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>House Number</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>House Number</em>' attribute.
   * @see #isSetHouseNumber()
   * @see #unsetHouseNumber()
   * @see #setHouseNumber(String)
   * @see goedegep.vacations.model.VacationsPackage#getLocation_HouseNumber()
   * @model unsettable="true"
   * @generated
   */
  String getHouseNumber();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Location#getHouseNumber <em>House Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>House Number</em>' attribute.
   * @see #isSetHouseNumber()
   * @see #unsetHouseNumber()
   * @see #getHouseNumber()
   * @generated
   */
  void setHouseNumber(String value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.Location#getHouseNumber <em>House Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetHouseNumber()
   * @see #getHouseNumber()
   * @see #setHouseNumber(String)
   * @generated
   */
  void unsetHouseNumber();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Location#getHouseNumber <em>House Number</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>House Number</em>' attribute is set.
   * @see #unsetHouseNumber()
   * @see #getHouseNumber()
   * @see #setHouseNumber(String)
   * @generated
   */
  boolean isSetHouseNumber();

  /**
   * Returns the value of the '<em><b>Latitude</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Latitude</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Latitude</em>' attribute.
   * @see #isSetLatitude()
   * @see #unsetLatitude()
   * @see #setLatitude(Double)
   * @see goedegep.vacations.model.VacationsPackage#getLocation_Latitude()
   * @model unsettable="true"
   * @generated
   */
  Double getLatitude();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Location#getLatitude <em>Latitude</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Latitude</em>' attribute.
   * @see #isSetLatitude()
   * @see #unsetLatitude()
   * @see #getLatitude()
   * @generated
   */
  void setLatitude(Double value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.Location#getLatitude <em>Latitude</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetLatitude()
   * @see #getLatitude()
   * @see #setLatitude(Double)
   * @generated
   */
  void unsetLatitude();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Location#getLatitude <em>Latitude</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Latitude</em>' attribute is set.
   * @see #unsetLatitude()
   * @see #getLatitude()
   * @see #setLatitude(Double)
   * @generated
   */
  boolean isSetLatitude();

  /**
   * Returns the value of the '<em><b>Longitude</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Longitude</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Longitude</em>' attribute.
   * @see #isSetLongitude()
   * @see #unsetLongitude()
   * @see #setLongitude(Double)
   * @see goedegep.vacations.model.VacationsPackage#getLocation_Longitude()
   * @model unsettable="true"
   * @generated
   */
  Double getLongitude();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Location#getLongitude <em>Longitude</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Longitude</em>' attribute.
   * @see #isSetLongitude()
   * @see #unsetLongitude()
   * @see #getLongitude()
   * @generated
   */
  void setLongitude(Double value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.Location#getLongitude <em>Longitude</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetLongitude()
   * @see #getLongitude()
   * @see #setLongitude(Double)
   * @generated
   */
  void unsetLongitude();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Location#getLongitude <em>Longitude</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Longitude</em>' attribute is set.
   * @see #unsetLongitude()
   * @see #getLongitude()
   * @see #setLongitude(Double)
   * @generated
   */
  boolean isSetLongitude();

  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #isSetName()
   * @see #unsetName()
   * @see #setName(String)
   * @see goedegep.vacations.model.VacationsPackage#getLocation_Name()
   * @model unsettable="true"
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Location#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #isSetName()
   * @see #unsetName()
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.Location#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetName()
   * @see #getName()
   * @see #setName(String)
   * @generated
   */
  void unsetName();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Location#getName <em>Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Name</em>' attribute is set.
   * @see #unsetName()
   * @see #getName()
   * @see #setName(String)
   * @generated
   */
  boolean isSetName();

  /**
   * Returns the value of the '<em><b>Web Site</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Web Site</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Web Site</em>' attribute.
   * @see #isSetWebSite()
   * @see #unsetWebSite()
   * @see #setWebSite(String)
   * @see goedegep.vacations.model.VacationsPackage#getLocation_WebSite()
   * @model unsettable="true"
   * @generated
   */
  String getWebSite();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Location#getWebSite <em>Web Site</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Web Site</em>' attribute.
   * @see #isSetWebSite()
   * @see #unsetWebSite()
   * @see #getWebSite()
   * @generated
   */
  void setWebSite(String value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.Location#getWebSite <em>Web Site</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetWebSite()
   * @see #getWebSite()
   * @see #setWebSite(String)
   * @generated
   */
  void unsetWebSite();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Location#getWebSite <em>Web Site</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Web Site</em>' attribute is set.
   * @see #unsetWebSite()
   * @see #getWebSite()
   * @see #setWebSite(String)
   * @generated
   */
  boolean isSetWebSite();

  /**
   * Returns the value of the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Description</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Description</em>' attribute.
   * @see #isSetDescription()
   * @see #unsetDescription()
   * @see #setDescription(String)
   * @see goedegep.vacations.model.VacationsPackage#getLocation_Description()
   * @model unsettable="true"
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Location#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #isSetDescription()
   * @see #unsetDescription()
   * @see #getDescription()
   * @generated
   */
  void setDescription(String value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.Location#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDescription()
   * @see #getDescription()
   * @see #setDescription(String)
   * @generated
   */
  void unsetDescription();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Location#getDescription <em>Description</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Description</em>' attribute is set.
   * @see #unsetDescription()
   * @see #getDescription()
   * @see #setDescription(String)
   * @generated
   */
  boolean isSetDescription();

  /**
   * Returns the value of the '<em><b>Label</b></em>' attribute.
   * The default value is <code>"UNSPECIFIED"</code>.
   * The literals are from the enumeration {@link goedegep.vacations.model.ActivityLabel}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Label</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Label</em>' attribute.
   * @see goedegep.vacations.model.ActivityLabel
   * @see #isSetLabel()
   * @see #unsetLabel()
   * @see #setLabel(ActivityLabel)
   * @see goedegep.vacations.model.VacationsPackage#getLocation_Label()
   * @model default="UNSPECIFIED" unsettable="true"
   * @generated
   */
  ActivityLabel getLabel();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Location#getLabel <em>Label</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Label</em>' attribute.
   * @see goedegep.vacations.model.ActivityLabel
   * @see #isSetLabel()
   * @see #unsetLabel()
   * @see #getLabel()
   * @generated
   */
  void setLabel(ActivityLabel value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.Location#getLabel <em>Label</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetLabel()
   * @see #getLabel()
   * @see #setLabel(ActivityLabel)
   * @generated
   */
  void unsetLabel();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Location#getLabel <em>Label</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Label</em>' attribute is set.
   * @see #unsetLabel()
   * @see #getLabel()
   * @see #setLabel(ActivityLabel)
   * @generated
   */
  boolean isSetLabel();

  /**
   * Returns the value of the '<em><b>Duration</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Duration</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Duration</em>' attribute.
   * @see #isSetDuration()
   * @see #unsetDuration()
   * @see #setDuration(Integer)
   * @see goedegep.vacations.model.VacationsPackage#getLocation_Duration()
   * @model unsettable="true"
   * @generated
   */
  Integer getDuration();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Location#getDuration <em>Duration</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Duration</em>' attribute.
   * @see #isSetDuration()
   * @see #unsetDuration()
   * @see #getDuration()
   * @generated
   */
  void setDuration(Integer value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.Location#getDuration <em>Duration</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDuration()
   * @see #getDuration()
   * @see #setDuration(Integer)
   * @generated
   */
  void unsetDuration();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Location#getDuration <em>Duration</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Duration</em>' attribute is set.
   * @see #unsetDuration()
   * @see #getDuration()
   * @see #setDuration(Integer)
   * @generated
   */
  boolean isSetDuration();

  /**
   * Returns the value of the '<em><b>Start Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Start Date</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Start Date</em>' attribute.
   * @see #isSetStartDate()
   * @see #unsetStartDate()
   * @see #setStartDate(FlexDate)
   * @see goedegep.vacations.model.VacationsPackage#getLocation_StartDate()
   * @model unsettable="true" dataType="goedegep.types.model.EFlexDate"
   * @generated
   */
  FlexDate getStartDate();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Location#getStartDate <em>Start Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Start Date</em>' attribute.
   * @see #isSetStartDate()
   * @see #unsetStartDate()
   * @see #getStartDate()
   * @generated
   */
  void setStartDate(FlexDate value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.Location#getStartDate <em>Start Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetStartDate()
   * @see #getStartDate()
   * @see #setStartDate(FlexDate)
   * @generated
   */
  void unsetStartDate();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Location#getStartDate <em>Start Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Start Date</em>' attribute is set.
   * @see #unsetStartDate()
   * @see #getStartDate()
   * @see #setStartDate(FlexDate)
   * @generated
   */
  boolean isSetStartDate();

  /**
   * Returns the value of the '<em><b>End Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>End Date</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>End Date</em>' attribute.
   * @see #isSetEndDate()
   * @see #unsetEndDate()
   * @see #setEndDate(FlexDate)
   * @see goedegep.vacations.model.VacationsPackage#getLocation_EndDate()
   * @model unsettable="true" dataType="goedegep.types.model.EFlexDate"
   * @generated
   */
  FlexDate getEndDate();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Location#getEndDate <em>End Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>End Date</em>' attribute.
   * @see #isSetEndDate()
   * @see #unsetEndDate()
   * @see #getEndDate()
   * @generated
   */
  void setEndDate(FlexDate value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.Location#getEndDate <em>End Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetEndDate()
   * @see #getEndDate()
   * @see #setEndDate(FlexDate)
   * @generated
   */
  void unsetEndDate();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Location#getEndDate <em>End Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>End Date</em>' attribute is set.
   * @see #unsetEndDate()
   * @see #getEndDate()
   * @see #setEndDate(FlexDate)
   * @generated
   */
  boolean isSetEndDate();

  /**
   * Returns the value of the '<em><b>Boundingbox</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Boundingbox</em>' containment reference.
   * @see #isSetBoundingbox()
   * @see #unsetBoundingbox()
   * @see #setBoundingbox(BoundingBox)
   * @see goedegep.vacations.model.VacationsPackage#getLocation_Boundingbox()
   * @model containment="true" unsettable="true"
   * @generated
   */
  BoundingBox getBoundingbox();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.Location#getBoundingbox <em>Boundingbox</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Boundingbox</em>' containment reference.
   * @see #isSetBoundingbox()
   * @see #unsetBoundingbox()
   * @see #getBoundingbox()
   * @generated
   */
  void setBoundingbox(BoundingBox value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.Location#getBoundingbox <em>Boundingbox</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetBoundingbox()
   * @see #getBoundingbox()
   * @see #setBoundingbox(BoundingBox)
   * @generated
   */
  void unsetBoundingbox();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.Location#getBoundingbox <em>Boundingbox</em>}' containment reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Boundingbox</em>' containment reference is set.
   * @see #unsetBoundingbox()
   * @see #getBoundingbox()
   * @see #setBoundingbox(BoundingBox)
   * @generated
   */
  boolean isSetBoundingbox();

  /**
   * Returns the value of the '<em><b>Boundaries</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.vacations.model.Boundary}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Boundaries</em>' containment reference list.
   * @see goedegep.vacations.model.VacationsPackage#getLocation_Boundaries()
   * @model containment="true"
   * @generated
   */
  EList<Boundary> getBoundaries();

} // Locatie
