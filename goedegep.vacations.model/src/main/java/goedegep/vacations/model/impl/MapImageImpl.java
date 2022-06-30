/**
 */
package goedegep.vacations.model.impl;

import goedegep.vacations.model.MapImage;
import goedegep.vacations.model.VacationsPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Map Image</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.model.impl.MapImageImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.MapImageImpl#getImageWidth <em>Image Width</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.MapImageImpl#getImageHeight <em>Image Height</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.MapImageImpl#getZoom <em>Zoom</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.MapImageImpl#getCenterLatitude <em>Center Latitude</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.MapImageImpl#getCenterLongitude <em>Center Longitude</em>}</li>
 *   <li>{@link goedegep.vacations.model.impl.MapImageImpl#getFileName <em>File Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MapImageImpl extends VacationElementImpl implements MapImage {
  /**
   * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTitle()
   * @generated
   * @ordered
   */
  protected static final String TITLE_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTitle()
   * @generated
   * @ordered
   */
  protected String title = TITLE_EDEFAULT;
  /**
   * This is true if the Title attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean titleESet;

  /**
   * The default value of the '{@link #getImageWidth() <em>Image Width</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getImageWidth()
   * @generated
   * @ordered
   */
  protected static final Double IMAGE_WIDTH_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getImageWidth() <em>Image Width</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getImageWidth()
   * @generated
   * @ordered
   */
  protected Double imageWidth = IMAGE_WIDTH_EDEFAULT;

  /**
   * The default value of the '{@link #getImageHeight() <em>Image Height</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getImageHeight()
   * @generated
   * @ordered
   */
  protected static final Double IMAGE_HEIGHT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getImageHeight() <em>Image Height</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getImageHeight()
   * @generated
   * @ordered
   */
  protected Double imageHeight = IMAGE_HEIGHT_EDEFAULT;

  /**
   * The default value of the '{@link #getZoom() <em>Zoom</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getZoom()
   * @generated
   * @ordered
   */
  protected static final Double ZOOM_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getZoom() <em>Zoom</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getZoom()
   * @generated
   * @ordered
   */
  protected Double zoom = ZOOM_EDEFAULT;

  /**
   * The default value of the '{@link #getCenterLatitude() <em>Center Latitude</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCenterLatitude()
   * @generated
   * @ordered
   */
  protected static final Double CENTER_LATITUDE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCenterLatitude() <em>Center Latitude</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCenterLatitude()
   * @generated
   * @ordered
   */
  protected Double centerLatitude = CENTER_LATITUDE_EDEFAULT;

  /**
   * The default value of the '{@link #getCenterLongitude() <em>Center Longitude</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCenterLongitude()
   * @generated
   * @ordered
   */
  protected static final Double CENTER_LONGITUDE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCenterLongitude() <em>Center Longitude</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCenterLongitude()
   * @generated
   * @ordered
   */
  protected Double centerLongitude = CENTER_LONGITUDE_EDEFAULT;

  /**
   * The default value of the '{@link #getFileName() <em>File Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFileName()
   * @generated
   * @ordered
   */
  protected static final String FILE_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFileName() <em>File Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFileName()
   * @generated
   * @ordered
   */
  protected String fileName = FILE_NAME_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MapImageImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return VacationsPackage.Literals.MAP_IMAGE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getTitle() {
    return title;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTitle(String newTitle) {
    String oldTitle = title;
    title = newTitle;
    boolean oldTitleESet = titleESet;
    titleESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.MAP_IMAGE__TITLE, oldTitle, title,
          !oldTitleESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetTitle() {
    String oldTitle = title;
    boolean oldTitleESet = titleESet;
    title = TITLE_EDEFAULT;
    titleESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, VacationsPackage.MAP_IMAGE__TITLE, oldTitle,
          TITLE_EDEFAULT, oldTitleESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetTitle() {
    return titleESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Double getImageWidth() {
    return imageWidth;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setImageWidth(Double newImageWidth) {
    Double oldImageWidth = imageWidth;
    imageWidth = newImageWidth;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.MAP_IMAGE__IMAGE_WIDTH, oldImageWidth,
          imageWidth));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Double getImageHeight() {
    return imageHeight;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setImageHeight(Double newImageHeight) {
    Double oldImageHeight = imageHeight;
    imageHeight = newImageHeight;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.MAP_IMAGE__IMAGE_HEIGHT, oldImageHeight,
          imageHeight));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Double getZoom() {
    return zoom;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setZoom(Double newZoom) {
    Double oldZoom = zoom;
    zoom = newZoom;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.MAP_IMAGE__ZOOM, oldZoom, zoom));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Double getCenterLatitude() {
    return centerLatitude;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCenterLatitude(Double newCenterLatitude) {
    Double oldCenterLatitude = centerLatitude;
    centerLatitude = newCenterLatitude;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.MAP_IMAGE__CENTER_LATITUDE,
          oldCenterLatitude, centerLatitude));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Double getCenterLongitude() {
    return centerLongitude;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCenterLongitude(Double newCenterLongitude) {
    Double oldCenterLongitude = centerLongitude;
    centerLongitude = newCenterLongitude;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VacationsPackage.MAP_IMAGE__CENTER_LONGITUDE,
          oldCenterLongitude, centerLongitude));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getFileName() {
    return fileName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFileName(String newFileName) {
    String oldFileName = fileName;
    fileName = newFileName;
    if (eNotificationRequired())
      eNotify(
          new ENotificationImpl(this, Notification.SET, VacationsPackage.MAP_IMAGE__FILE_NAME, oldFileName, fileName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case VacationsPackage.MAP_IMAGE__TITLE:
      return getTitle();
    case VacationsPackage.MAP_IMAGE__IMAGE_WIDTH:
      return getImageWidth();
    case VacationsPackage.MAP_IMAGE__IMAGE_HEIGHT:
      return getImageHeight();
    case VacationsPackage.MAP_IMAGE__ZOOM:
      return getZoom();
    case VacationsPackage.MAP_IMAGE__CENTER_LATITUDE:
      return getCenterLatitude();
    case VacationsPackage.MAP_IMAGE__CENTER_LONGITUDE:
      return getCenterLongitude();
    case VacationsPackage.MAP_IMAGE__FILE_NAME:
      return getFileName();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
    case VacationsPackage.MAP_IMAGE__TITLE:
      setTitle((String) newValue);
      return;
    case VacationsPackage.MAP_IMAGE__IMAGE_WIDTH:
      setImageWidth((Double) newValue);
      return;
    case VacationsPackage.MAP_IMAGE__IMAGE_HEIGHT:
      setImageHeight((Double) newValue);
      return;
    case VacationsPackage.MAP_IMAGE__ZOOM:
      setZoom((Double) newValue);
      return;
    case VacationsPackage.MAP_IMAGE__CENTER_LATITUDE:
      setCenterLatitude((Double) newValue);
      return;
    case VacationsPackage.MAP_IMAGE__CENTER_LONGITUDE:
      setCenterLongitude((Double) newValue);
      return;
    case VacationsPackage.MAP_IMAGE__FILE_NAME:
      setFileName((String) newValue);
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
    case VacationsPackage.MAP_IMAGE__TITLE:
      unsetTitle();
      return;
    case VacationsPackage.MAP_IMAGE__IMAGE_WIDTH:
      setImageWidth(IMAGE_WIDTH_EDEFAULT);
      return;
    case VacationsPackage.MAP_IMAGE__IMAGE_HEIGHT:
      setImageHeight(IMAGE_HEIGHT_EDEFAULT);
      return;
    case VacationsPackage.MAP_IMAGE__ZOOM:
      setZoom(ZOOM_EDEFAULT);
      return;
    case VacationsPackage.MAP_IMAGE__CENTER_LATITUDE:
      setCenterLatitude(CENTER_LATITUDE_EDEFAULT);
      return;
    case VacationsPackage.MAP_IMAGE__CENTER_LONGITUDE:
      setCenterLongitude(CENTER_LONGITUDE_EDEFAULT);
      return;
    case VacationsPackage.MAP_IMAGE__FILE_NAME:
      setFileName(FILE_NAME_EDEFAULT);
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
    case VacationsPackage.MAP_IMAGE__TITLE:
      return isSetTitle();
    case VacationsPackage.MAP_IMAGE__IMAGE_WIDTH:
      return IMAGE_WIDTH_EDEFAULT == null ? imageWidth != null : !IMAGE_WIDTH_EDEFAULT.equals(imageWidth);
    case VacationsPackage.MAP_IMAGE__IMAGE_HEIGHT:
      return IMAGE_HEIGHT_EDEFAULT == null ? imageHeight != null : !IMAGE_HEIGHT_EDEFAULT.equals(imageHeight);
    case VacationsPackage.MAP_IMAGE__ZOOM:
      return ZOOM_EDEFAULT == null ? zoom != null : !ZOOM_EDEFAULT.equals(zoom);
    case VacationsPackage.MAP_IMAGE__CENTER_LATITUDE:
      return CENTER_LATITUDE_EDEFAULT == null ? centerLatitude != null
          : !CENTER_LATITUDE_EDEFAULT.equals(centerLatitude);
    case VacationsPackage.MAP_IMAGE__CENTER_LONGITUDE:
      return CENTER_LONGITUDE_EDEFAULT == null ? centerLongitude != null
          : !CENTER_LONGITUDE_EDEFAULT.equals(centerLongitude);
    case VacationsPackage.MAP_IMAGE__FILE_NAME:
      return FILE_NAME_EDEFAULT == null ? fileName != null : !FILE_NAME_EDEFAULT.equals(fileName);
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
    result.append(" (title: ");
    if (titleESet)
      result.append(title);
    else
      result.append("<unset>");
    result.append(", imageWidth: ");
    result.append(imageWidth);
    result.append(", imageHeight: ");
    result.append(imageHeight);
    result.append(", zoom: ");
    result.append(zoom);
    result.append(", centerLatitude: ");
    result.append(centerLatitude);
    result.append(", centerLongitude: ");
    result.append(centerLongitude);
    result.append(", fileName: ");
    result.append(fileName);
    result.append(')');
    return result.toString();
  }

} //MapImageImpl
