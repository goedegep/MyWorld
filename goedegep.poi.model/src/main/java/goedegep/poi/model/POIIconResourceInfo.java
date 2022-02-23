/**
 */
package goedegep.poi.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Icon Resource Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.poi.model.POIIconResourceInfo#getPoiIconResourceDescriptors <em>Poi Icon Resource Descriptors</em>}</li>
 * </ul>
 *
 * @see goedegep.poi.model.POIPackage#getPOIIconResourceInfo()
 * @model
 * @generated
 */
public interface POIIconResourceInfo extends EObject {
  /**
   * Returns the value of the '<em><b>Poi Icon Resource Descriptors</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.poi.model.POIIconResourceDescriptor}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Poi Icon Resource Descriptors</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Poi Icon Resource Descriptors</em>' containment reference list.
   * @see goedegep.poi.model.POIPackage#getPOIIconResourceInfo_PoiIconResourceDescriptors()
   * @model containment="true"
   * @generated
   */
  EList<POIIconResourceDescriptor> getPoiIconResourceDescriptors();

} // POIIconResourceInfo
