/**
 */
package goedegep.vacations.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.poi.model.POICategoryId;
import goedegep.util.datetime.FlexDate;
import goedegep.vacations.model.ActivityLabel;
import goedegep.vacations.model.Boundary;
import goedegep.vacations.model.BoundingBox;
import goedegep.vacations.model.Location;
import goedegep.vacations.model.VacationsPackage;

import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Locatie</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.model.impl.LocationImpl#getLocationType <em>Location Type</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.LocationImpl#getCountry <em>Country</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.LocationImpl#getCity <em>City</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.LocationImpl#getStreet <em>Street</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.LocationImpl#getHouseNumber <em>House Number</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.LocationImpl#getLatitude <em>Latitude</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.LocationImpl#getLongitude <em>Longitude</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.LocationImpl#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.LocationImpl#getWebSite <em>Web Site</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.LocationImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.LocationImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.LocationImpl#getDuration <em>Duration</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.LocationImpl#getStartDate <em>Start Date</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.LocationImpl#getEndDate <em>End Date</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.LocationImpl#getBoundingbox <em>Boundingbox</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.LocationImpl#getBoundaries <em>Boundaries</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LocationImpl extends VacationElementImpl implements Location {
  /**
   * The default value of the '{@link #getLocationType() <em>Location Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLocationType()
   * @generated
   * @ordered
   */
  protected static final POICategoryId LOCATION_TYPE_EDEFAULT = POICategoryId.DEFAULT_POI;

  /**
   * The cached value of the '{@link #getLocationType() <em>Location Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLocationType()
   * @generated
   * @ordered
   */
  protected POICategoryId locationType = LOCATION_TYPE_EDEFAULT;

  /**
   * This is true if the Location Type attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean locationTypeESet;

  /**
   * The default value of the '{@link #getCountry() <em>Country</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCountry()
   * @generated
   * @ordered
   */
  protected static final String COUNTRY_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCountry() <em>Country</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCountry()
   * @generated
   * @ordered
   */
  protected String country = COUNTRY_EDEFAULT;

  /**
   * This is true if the Country attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean countryESet;

  /**
   * The default value of the '{@link #getCity() <em>City</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCity()
   * @generated
   * @ordered
   */
  protected static final String CITY_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCity() <em>City</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCity()
   * @generated
   * @ordered
   */
  protected String city = CITY_EDEFAULT;

  /**
   * This is true if the City attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean cityESet;

  /**
   * The default value of the '{@link #getStreet() <em>Street</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStreet()
   * @generated
   * @ordered
   */
  protected static final String STREET_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getStreet() <em>Street</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStreet()
   * @generated
   * @ordered
   */
  protected String street = STREET_EDEFAULT;

  /**
   * This is true if the Street attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean streetESet;

  /**
   * The default value of the '{@link #getHouseNumber() <em>House Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHouseNumber()
   * @generated
   * @ordered
   */
  protected static final String HOUSE_NUMBER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getHouseNumber() <em>House Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHouseNumber()
   * @generated
   * @ordered
   */
  protected String houseNumber = HOUSE_NUMBER_EDEFAULT;

  /**
   * This is true if the House Number attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean houseNumberESet;

  /**
   * The default value of the '{@link #getLatitude() <em>Latitude</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLatitude()
   * @generated
   * @ordered
   */
  protected static final Double LATITUDE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getLatitude() <em>Latitude</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLatitude()
   * @generated
   * @ordered
   */
  protected Double latitude = LATITUDE_EDEFAULT;

  /**
   * This is true if the Latitude attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean latitudeESet;

  /**
   * The default value of the '{@link #getLongitude() <em>Longitude</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLongitude()
   * @generated
   * @ordered
   */
  protected static final Double LONGITUDE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getLongitude() <em>Longitude</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLongitude()
   * @generated
   * @ordered
   */
  protected Double longitude = LONGITUDE_EDEFAULT;

  /**
   * This is true if the Longitude attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean longitudeESet;

  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * This is true if the Name attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean nameESet;

  /**
   * The default value of the '{@link #getWebSite() <em>Web Site</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWebSite()
   * @generated
   * @ordered
   */
  protected static final String WEB_SITE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getWebSite() <em>Web Site</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWebSite()
   * @generated
   * @ordered
   */
  protected String webSite = WEB_SITE_EDEFAULT;

  /**
   * This is true if the Web Site attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean webSiteESet;

  /**
   * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected static final String DESCRIPTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected String description = DESCRIPTION_EDEFAULT;

  /**
   * This is true if the Description attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean descriptionESet;

  /**
   * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLabel()
   * @generated
   * @ordered
   */
  protected static final ActivityLabel LABEL_EDEFAULT = ActivityLabel.UNSPECIFIED;

  /**
   * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLabel()
   * @generated
   * @ordered
   */
  protected ActivityLabel label = LABEL_EDEFAULT;

  /**
   * This is true if the Label attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean labelESet;

  /**
   * The default value of the '{@link #getDuration() <em>Duration</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDuration()
   * @generated
   * @ordered
   */
  protected static final Integer DURATION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDuration() <em>Duration</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDuration()
   * @generated
   * @ordered
   */
  protected Integer duration = DURATION_EDEFAULT;

  /**
   * This is true if the Duration attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean durationESet;

  /**
   * The default value of the '{@link #getStartDate() <em>Start Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStartDate()
   * @generated
   * @ordered
   */
  protected static final FlexDate START_DATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getStartDate() <em>Start Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStartDate()
   * @generated
   * @ordered
   */
  protected FlexDate startDate = START_DATE_EDEFAULT;

  /**
   * This is true if the Start Date attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean startDateESet;

  /**
   * The default value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEndDate()
   * @generated
   * @ordered
   */
  protected static final FlexDate END_DATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEndDate()
   * @generated
   * @ordered
   */
  protected FlexDate endDate = END_DATE_EDEFAULT;

  /**
   * This is true if the End Date attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean endDateESet;

  /**
   * The cached value of the '{@link #getBoundingbox() <em>Boundingbox</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBoundingbox()
   * @generated
   * @ordered
   */
  protected BoundingBox boundingbox;

  /**
   * This is true if the Boundingbox containment reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean boundingboxESet;

  /**
   * The cached value of the '{@link #getBoundaries() <em>Boundaries</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBoundaries()
   * @generated
   * @ordered
   */
  protected EList<Boundary> boundaries;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected LocationImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return VacationsPackage.Literals.LOCATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public POICategoryId getLocationType() {
    return locationType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setLocationType(POICategoryId newLocationType) {
    POICategoryId oldLocationType = locationType;
    locationType = newLocationType == null ? LOCATION_TYPE_EDEFAULT : newLocationType;
    boolean oldLocationTypeESet = locationTypeESet;
    locationTypeESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.LOCATION__LOCATION_TYPE, oldLocationType,
          locationType, !oldLocationTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetLocationType() {
    POICategoryId oldLocationType = locationType;
    boolean oldLocationTypeESet = locationTypeESet;
    locationType = LOCATION_TYPE_EDEFAULT;
    locationTypeESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, VacationsPackage.LOCATION__LOCATION_TYPE, oldLocationType,
          LOCATION_TYPE_EDEFAULT, oldLocationTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetLocationType() {
    return locationTypeESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getCountry() {
    return country;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCountry(String newCountry) {
    String oldCountry = country;
    country = newCountry;
    boolean oldCountryESet = countryESet;
    countryESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.LOCATION__COUNTRY, oldCountry, country,
          !oldCountryESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetCountry() {
    String oldCountry = country;
    boolean oldCountryESet = countryESet;
    country = COUNTRY_EDEFAULT;
    countryESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, VacationsPackage.LOCATION__COUNTRY, oldCountry,
          COUNTRY_EDEFAULT, oldCountryESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetCountry() {
    return countryESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getCity() {
    return city;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCity(String newCity) {
    String oldCity = city;
    city = newCity;
    boolean oldCityESet = cityESet;
    cityESet = true;
    if (eNotificationRequired())
      eNotify(
          new ENotificationImpl(this, Notification.SET, VacationsPackage.LOCATION__CITY, oldCity, city, !oldCityESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetCity() {
    String oldCity = city;
    boolean oldCityESet = cityESet;
    city = CITY_EDEFAULT;
    cityESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, VacationsPackage.LOCATION__CITY, oldCity, CITY_EDEFAULT,
          oldCityESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetCity() {
    return cityESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getStreet() {
    return street;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setStreet(String newStreet) {
    String oldStreet = street;
    street = newStreet;
    boolean oldStreetESet = streetESet;
    streetESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.LOCATION__STREET, oldStreet, street,
          !oldStreetESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetStreet() {
    String oldStreet = street;
    boolean oldStreetESet = streetESet;
    street = STREET_EDEFAULT;
    streetESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, VacationsPackage.LOCATION__STREET, oldStreet,
          STREET_EDEFAULT, oldStreetESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetStreet() {
    return streetESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getHouseNumber() {
    return houseNumber;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setHouseNumber(String newHouseNumber) {
    String oldHouseNumber = houseNumber;
    houseNumber = newHouseNumber;
    boolean oldHouseNumberESet = houseNumberESet;
    houseNumberESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.LOCATION__HOUSE_NUMBER, oldHouseNumber,
          houseNumber, !oldHouseNumberESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetHouseNumber() {
    String oldHouseNumber = houseNumber;
    boolean oldHouseNumberESet = houseNumberESet;
    houseNumber = HOUSE_NUMBER_EDEFAULT;
    houseNumberESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, VacationsPackage.LOCATION__HOUSE_NUMBER, oldHouseNumber,
          HOUSE_NUMBER_EDEFAULT, oldHouseNumberESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetHouseNumber() {
    return houseNumberESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Double getLatitude() {
    return latitude;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setLatitude(Double newLatitude) {
    Double oldLatitude = latitude;
    latitude = newLatitude;
    boolean oldLatitudeESet = latitudeESet;
    latitudeESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.LOCATION__LATITUDE, oldLatitude, latitude,
          !oldLatitudeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetLatitude() {
    Double oldLatitude = latitude;
    boolean oldLatitudeESet = latitudeESet;
    latitude = LATITUDE_EDEFAULT;
    latitudeESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, VacationsPackage.LOCATION__LATITUDE, oldLatitude,
          LATITUDE_EDEFAULT, oldLatitudeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetLatitude() {
    return latitudeESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Double getLongitude() {
    return longitude;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setLongitude(Double newLongitude) {
    Double oldLongitude = longitude;
    longitude = newLongitude;
    boolean oldLongitudeESet = longitudeESet;
    longitudeESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.LOCATION__LONGITUDE, oldLongitude,
          longitude, !oldLongitudeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetLongitude() {
    Double oldLongitude = longitude;
    boolean oldLongitudeESet = longitudeESet;
    longitude = LONGITUDE_EDEFAULT;
    longitudeESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, VacationsPackage.LOCATION__LONGITUDE, oldLongitude,
          LONGITUDE_EDEFAULT, oldLongitudeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetLongitude() {
    return longitudeESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setName(String newName) {
    String oldName = name;
    name = newName;
    boolean oldNameESet = nameESet;
    nameESet = true;
    if (eNotificationRequired())
      eNotify(
          new ENotificationImpl(this, Notification.SET, VacationsPackage.LOCATION__NAME, oldName, name, !oldNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetName() {
    String oldName = name;
    boolean oldNameESet = nameESet;
    name = NAME_EDEFAULT;
    nameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, VacationsPackage.LOCATION__NAME, oldName, NAME_EDEFAULT,
          oldNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetName() {
    return nameESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getWebSite() {
    return webSite;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setWebSite(String newWebSite) {
    String oldWebSite = webSite;
    webSite = newWebSite;
    boolean oldWebSiteESet = webSiteESet;
    webSiteESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.LOCATION__WEB_SITE, oldWebSite, webSite,
          !oldWebSiteESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetWebSite() {
    String oldWebSite = webSite;
    boolean oldWebSiteESet = webSiteESet;
    webSite = WEB_SITE_EDEFAULT;
    webSiteESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, VacationsPackage.LOCATION__WEB_SITE, oldWebSite,
          WEB_SITE_EDEFAULT, oldWebSiteESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetWebSite() {
    return webSiteESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDescription(String newDescription) {
    String oldDescription = description;
    description = newDescription;
    boolean oldDescriptionESet = descriptionESet;
    descriptionESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.LOCATION__DESCRIPTION, oldDescription,
          description, !oldDescriptionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDescription() {
    String oldDescription = description;
    boolean oldDescriptionESet = descriptionESet;
    description = DESCRIPTION_EDEFAULT;
    descriptionESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, VacationsPackage.LOCATION__DESCRIPTION, oldDescription,
          DESCRIPTION_EDEFAULT, oldDescriptionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDescription() {
    return descriptionESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ActivityLabel getLabel() {
    return label;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setLabel(ActivityLabel newLabel) {
    ActivityLabel oldLabel = label;
    label = newLabel == null ? LABEL_EDEFAULT : newLabel;
    boolean oldLabelESet = labelESet;
    labelESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.LOCATION__LABEL, oldLabel, label,
          !oldLabelESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetLabel() {
    ActivityLabel oldLabel = label;
    boolean oldLabelESet = labelESet;
    label = LABEL_EDEFAULT;
    labelESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, VacationsPackage.LOCATION__LABEL, oldLabel,
          LABEL_EDEFAULT, oldLabelESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetLabel() {
    return labelESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Integer getDuration() {
    return duration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDuration(Integer newDuration) {
    Integer oldDuration = duration;
    duration = newDuration;
    boolean oldDurationESet = durationESet;
    durationESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.LOCATION__DURATION, oldDuration, duration,
          !oldDurationESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDuration() {
    Integer oldDuration = duration;
    boolean oldDurationESet = durationESet;
    duration = DURATION_EDEFAULT;
    durationESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, VacationsPackage.LOCATION__DURATION, oldDuration,
          DURATION_EDEFAULT, oldDurationESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDuration() {
    return durationESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FlexDate getStartDate() {
    return startDate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setStartDate(FlexDate newStartDate) {
    FlexDate oldStartDate = startDate;
    startDate = newStartDate;
    boolean oldStartDateESet = startDateESet;
    startDateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.LOCATION__START_DATE, oldStartDate,
          startDate, !oldStartDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetStartDate() {
    FlexDate oldStartDate = startDate;
    boolean oldStartDateESet = startDateESet;
    startDate = START_DATE_EDEFAULT;
    startDateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, VacationsPackage.LOCATION__START_DATE, oldStartDate,
          START_DATE_EDEFAULT, oldStartDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetStartDate() {
    return startDateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FlexDate getEndDate() {
    return endDate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setEndDate(FlexDate newEndDate) {
    FlexDate oldEndDate = endDate;
    endDate = newEndDate;
    boolean oldEndDateESet = endDateESet;
    endDateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.LOCATION__END_DATE, oldEndDate, endDate,
          !oldEndDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetEndDate() {
    FlexDate oldEndDate = endDate;
    boolean oldEndDateESet = endDateESet;
    endDate = END_DATE_EDEFAULT;
    endDateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, VacationsPackage.LOCATION__END_DATE, oldEndDate,
          END_DATE_EDEFAULT, oldEndDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetEndDate() {
    return endDateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public BoundingBox getBoundingbox() {
    return boundingbox;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetBoundingbox(BoundingBox newBoundingbox, NotificationChain msgs) {
    BoundingBox oldBoundingbox = boundingbox;
    boundingbox = newBoundingbox;
    boolean oldBoundingboxESet = boundingboxESet;
    boundingboxESet = true;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
          VacationsPackage.LOCATION__BOUNDINGBOX, oldBoundingbox, newBoundingbox, !oldBoundingboxESet);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setBoundingbox(BoundingBox newBoundingbox) {
    if (newBoundingbox != boundingbox) {
      NotificationChain msgs = null;
      if (boundingbox != null)
        msgs = ((InternalEObject) boundingbox).eInverseRemove(this,
            EOPPOSITE_FEATURE_BASE - VacationsPackage.LOCATION__BOUNDINGBOX, null, msgs);
      if (newBoundingbox != null)
        msgs = ((InternalEObject) newBoundingbox).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - VacationsPackage.LOCATION__BOUNDINGBOX, null, msgs);
      msgs = basicSetBoundingbox(newBoundingbox, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else {
      boolean oldBoundingboxESet = boundingboxESet;
      boundingboxESet = true;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.LOCATION__BOUNDINGBOX, newBoundingbox,
            newBoundingbox, !oldBoundingboxESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicUnsetBoundingbox(NotificationChain msgs) {
    BoundingBox oldBoundingbox = boundingbox;
    boundingbox = null;
    boolean oldBoundingboxESet = boundingboxESet;
    boundingboxESet = false;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET,
          VacationsPackage.LOCATION__BOUNDINGBOX, oldBoundingbox, null, oldBoundingboxESet);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetBoundingbox() {
    if (boundingbox != null) {
      NotificationChain msgs = null;
      msgs = ((InternalEObject) boundingbox).eInverseRemove(this,
          EOPPOSITE_FEATURE_BASE - VacationsPackage.LOCATION__BOUNDINGBOX, null, msgs);
      msgs = basicUnsetBoundingbox(msgs);
      if (msgs != null)
        msgs.dispatch();
    } else {
      boolean oldBoundingboxESet = boundingboxESet;
      boundingboxESet = false;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.UNSET, VacationsPackage.LOCATION__BOUNDINGBOX, null, null,
            oldBoundingboxESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetBoundingbox() {
    return boundingboxESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Boundary> getBoundaries() {
    if (boundaries == null) {
      boundaries = new EObjectContainmentEList<Boundary>(Boundary.class, this, VacationsPackage.LOCATION__BOUNDARIES);
    }
    return boundaries;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case VacationsPackage.LOCATION__BOUNDINGBOX:
      return basicUnsetBoundingbox(msgs);
    case VacationsPackage.LOCATION__BOUNDARIES:
      return ((InternalEList<?>) getBoundaries()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case VacationsPackage.LOCATION__LOCATION_TYPE:
      return getLocationType();
    case VacationsPackage.LOCATION__COUNTRY:
      return getCountry();
    case VacationsPackage.LOCATION__CITY:
      return getCity();
    case VacationsPackage.LOCATION__STREET:
      return getStreet();
    case VacationsPackage.LOCATION__HOUSE_NUMBER:
      return getHouseNumber();
    case VacationsPackage.LOCATION__LATITUDE:
      return getLatitude();
    case VacationsPackage.LOCATION__LONGITUDE:
      return getLongitude();
    case VacationsPackage.LOCATION__NAME:
      return getName();
    case VacationsPackage.LOCATION__WEB_SITE:
      return getWebSite();
    case VacationsPackage.LOCATION__DESCRIPTION:
      return getDescription();
    case VacationsPackage.LOCATION__LABEL:
      return getLabel();
    case VacationsPackage.LOCATION__DURATION:
      return getDuration();
    case VacationsPackage.LOCATION__START_DATE:
      return getStartDate();
    case VacationsPackage.LOCATION__END_DATE:
      return getEndDate();
    case VacationsPackage.LOCATION__BOUNDINGBOX:
      return getBoundingbox();
    case VacationsPackage.LOCATION__BOUNDARIES:
      return getBoundaries();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
    case VacationsPackage.LOCATION__LOCATION_TYPE:
      setLocationType((POICategoryId) newValue);
      return;
    case VacationsPackage.LOCATION__COUNTRY:
      setCountry((String) newValue);
      return;
    case VacationsPackage.LOCATION__CITY:
      setCity((String) newValue);
      return;
    case VacationsPackage.LOCATION__STREET:
      setStreet((String) newValue);
      return;
    case VacationsPackage.LOCATION__HOUSE_NUMBER:
      setHouseNumber((String) newValue);
      return;
    case VacationsPackage.LOCATION__LATITUDE:
      setLatitude((Double) newValue);
      return;
    case VacationsPackage.LOCATION__LONGITUDE:
      setLongitude((Double) newValue);
      return;
    case VacationsPackage.LOCATION__NAME:
      setName((String) newValue);
      return;
    case VacationsPackage.LOCATION__WEB_SITE:
      setWebSite((String) newValue);
      return;
    case VacationsPackage.LOCATION__DESCRIPTION:
      setDescription((String) newValue);
      return;
    case VacationsPackage.LOCATION__LABEL:
      setLabel((ActivityLabel) newValue);
      return;
    case VacationsPackage.LOCATION__DURATION:
      setDuration((Integer) newValue);
      return;
    case VacationsPackage.LOCATION__START_DATE:
      setStartDate((FlexDate) newValue);
      return;
    case VacationsPackage.LOCATION__END_DATE:
      setEndDate((FlexDate) newValue);
      return;
    case VacationsPackage.LOCATION__BOUNDINGBOX:
      setBoundingbox((BoundingBox) newValue);
      return;
    case VacationsPackage.LOCATION__BOUNDARIES:
      getBoundaries().clear();
      getBoundaries().addAll((Collection<? extends Boundary>) newValue);
      return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID) {
    switch (featureID) {
    case VacationsPackage.LOCATION__LOCATION_TYPE:
      unsetLocationType();
      return;
    case VacationsPackage.LOCATION__COUNTRY:
      unsetCountry();
      return;
    case VacationsPackage.LOCATION__CITY:
      unsetCity();
      return;
    case VacationsPackage.LOCATION__STREET:
      unsetStreet();
      return;
    case VacationsPackage.LOCATION__HOUSE_NUMBER:
      unsetHouseNumber();
      return;
    case VacationsPackage.LOCATION__LATITUDE:
      unsetLatitude();
      return;
    case VacationsPackage.LOCATION__LONGITUDE:
      unsetLongitude();
      return;
    case VacationsPackage.LOCATION__NAME:
      unsetName();
      return;
    case VacationsPackage.LOCATION__WEB_SITE:
      unsetWebSite();
      return;
    case VacationsPackage.LOCATION__DESCRIPTION:
      unsetDescription();
      return;
    case VacationsPackage.LOCATION__LABEL:
      unsetLabel();
      return;
    case VacationsPackage.LOCATION__DURATION:
      unsetDuration();
      return;
    case VacationsPackage.LOCATION__START_DATE:
      unsetStartDate();
      return;
    case VacationsPackage.LOCATION__END_DATE:
      unsetEndDate();
      return;
    case VacationsPackage.LOCATION__BOUNDINGBOX:
      unsetBoundingbox();
      return;
    case VacationsPackage.LOCATION__BOUNDARIES:
      getBoundaries().clear();
      return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID) {
    switch (featureID) {
    case VacationsPackage.LOCATION__LOCATION_TYPE:
      return isSetLocationType();
    case VacationsPackage.LOCATION__COUNTRY:
      return isSetCountry();
    case VacationsPackage.LOCATION__CITY:
      return isSetCity();
    case VacationsPackage.LOCATION__STREET:
      return isSetStreet();
    case VacationsPackage.LOCATION__HOUSE_NUMBER:
      return isSetHouseNumber();
    case VacationsPackage.LOCATION__LATITUDE:
      return isSetLatitude();
    case VacationsPackage.LOCATION__LONGITUDE:
      return isSetLongitude();
    case VacationsPackage.LOCATION__NAME:
      return isSetName();
    case VacationsPackage.LOCATION__WEB_SITE:
      return isSetWebSite();
    case VacationsPackage.LOCATION__DESCRIPTION:
      return isSetDescription();
    case VacationsPackage.LOCATION__LABEL:
      return isSetLabel();
    case VacationsPackage.LOCATION__DURATION:
      return isSetDuration();
    case VacationsPackage.LOCATION__START_DATE:
      return isSetStartDate();
    case VacationsPackage.LOCATION__END_DATE:
      return isSetEndDate();
    case VacationsPackage.LOCATION__BOUNDINGBOX:
      return isSetBoundingbox();
    case VacationsPackage.LOCATION__BOUNDARIES:
      return boundaries != null && !boundaries.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy())
      return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (locationType: ");
    if (locationTypeESet)
      result.append(locationType);
    else
      result.append("<unset>");
    result.append(", country: ");
    if (countryESet)
      result.append(country);
    else
      result.append("<unset>");
    result.append(", city: ");
    if (cityESet)
      result.append(city);
    else
      result.append("<unset>");
    result.append(", street: ");
    if (streetESet)
      result.append(street);
    else
      result.append("<unset>");
    result.append(", houseNumber: ");
    if (houseNumberESet)
      result.append(houseNumber);
    else
      result.append("<unset>");
    result.append(", latitude: ");
    if (latitudeESet)
      result.append(latitude);
    else
      result.append("<unset>");
    result.append(", longitude: ");
    if (longitudeESet)
      result.append(longitude);
    else
      result.append("<unset>");
    result.append(", name: ");
    if (nameESet)
      result.append(name);
    else
      result.append("<unset>");
    result.append(", webSite: ");
    if (webSiteESet)
      result.append(webSite);
    else
      result.append("<unset>");
    result.append(", description: ");
    if (descriptionESet)
      result.append(description);
    else
      result.append("<unset>");
    result.append(", label: ");
    if (labelESet)
      result.append(label);
    else
      result.append("<unset>");
    result.append(", duration: ");
    if (durationESet)
      result.append(duration);
    else
      result.append("<unset>");
    result.append(", startDate: ");
    if (startDateESet)
      result.append(startDate);
    else
      result.append("<unset>");
    result.append(", endDate: ");
    if (endDateESet)
      result.append(endDate);
    else
      result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //LocatieImpl
