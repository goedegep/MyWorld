/**
 */
package goedegep.vacations.model;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Map Image</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.model.MapImage#getTitle <em>Title</em>}</li>
 *   <li>{@link goedegep.vacations.model.MapImage#getImageWidth <em>Image Width</em>}</li>
 *   <li>{@link goedegep.vacations.model.MapImage#getImageHeight <em>Image Height</em>}</li>
 *   <li>{@link goedegep.vacations.model.MapImage#getZoom <em>Zoom</em>}</li>
 *   <li>{@link goedegep.vacations.model.MapImage#getCenterLatitude <em>Center Latitude</em>}</li>
 *   <li>{@link goedegep.vacations.model.MapImage#getCenterLongitude <em>Center Longitude</em>}</li>
 *   <li>{@link goedegep.vacations.model.MapImage#getFileName <em>File Name</em>}</li>
 * </ul>
 *
 * @see goedegep.vacations.model.VacationsPackage#getMapImage()
 * @model
 * @generated
 */
public interface MapImage extends VacationElement {
  /**
   * Returns the value of the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Title</em>' attribute.
   * @see #isSetTitle()
   * @see #unsetTitle()
   * @see #setTitle(String)
   * @see goedegep.vacations.model.VacationsPackage#getMapImage_Title()
   * @model unsettable="true"
   * @generated
   */
  String getTitle();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.MapImage#getTitle <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Title</em>' attribute.
   * @see #isSetTitle()
   * @see #unsetTitle()
   * @see #getTitle()
   * @generated
   */
  void setTitle(String value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.MapImage#getTitle <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetTitle()
   * @see #getTitle()
   * @see #setTitle(String)
   * @generated
   */
  void unsetTitle();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.MapImage#getTitle <em>Title</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Title</em>' attribute is set.
   * @see #unsetTitle()
   * @see #getTitle()
   * @see #setTitle(String)
   * @generated
   */
  boolean isSetTitle();

  /**
   * Returns the value of the '<em><b>Image Width</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Image Width</em>' attribute.
   * @see #setImageWidth(Double)
   * @see goedegep.vacations.model.VacationsPackage#getMapImage_ImageWidth()
   * @model required="true"
   * @generated
   */
  Double getImageWidth();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.MapImage#getImageWidth <em>Image Width</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Image Width</em>' attribute.
   * @see #getImageWidth()
   * @generated
   */
  void setImageWidth(Double value);

  /**
   * Returns the value of the '<em><b>Image Height</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Image Height</em>' attribute.
   * @see #setImageHeight(Double)
   * @see goedegep.vacations.model.VacationsPackage#getMapImage_ImageHeight()
   * @model required="true"
   * @generated
   */
  Double getImageHeight();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.MapImage#getImageHeight <em>Image Height</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Image Height</em>' attribute.
   * @see #getImageHeight()
   * @generated
   */
  void setImageHeight(Double value);

  /**
   * Returns the value of the '<em><b>Zoom</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Zoom</em>' attribute.
   * @see #setZoom(Double)
   * @see goedegep.vacations.model.VacationsPackage#getMapImage_Zoom()
   * @model required="true"
   * @generated
   */
  Double getZoom();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.MapImage#getZoom <em>Zoom</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Zoom</em>' attribute.
   * @see #getZoom()
   * @generated
   */
  void setZoom(Double value);

  /**
   * Returns the value of the '<em><b>Center Latitude</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Center Latitude</em>' attribute.
   * @see #setCenterLatitude(Double)
   * @see goedegep.vacations.model.VacationsPackage#getMapImage_CenterLatitude()
   * @model required="true"
   * @generated
   */
  Double getCenterLatitude();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.MapImage#getCenterLatitude <em>Center Latitude</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Center Latitude</em>' attribute.
   * @see #getCenterLatitude()
   * @generated
   */
  void setCenterLatitude(Double value);

  /**
   * Returns the value of the '<em><b>Center Longitude</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Center Longitude</em>' attribute.
   * @see #setCenterLongitude(Double)
   * @see goedegep.vacations.model.VacationsPackage#getMapImage_CenterLongitude()
   * @model required="true"
   * @generated
   */
  Double getCenterLongitude();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.MapImage#getCenterLongitude <em>Center Longitude</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Center Longitude</em>' attribute.
   * @see #getCenterLongitude()
   * @generated
   */
  void setCenterLongitude(Double value);

  /**
   * Returns the value of the '<em><b>File Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>File Name</em>' attribute.
   * @see #setFileName(String)
   * @see goedegep.vacations.model.VacationsPackage#getMapImage_FileName()
   * @model required="true"
   * @generated
   */
  String getFileName();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.MapImage#getFileName <em>File Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>File Name</em>' attribute.
   * @see #getFileName()
   * @generated
   */
  void setFileName(String value);

} // MapImage
