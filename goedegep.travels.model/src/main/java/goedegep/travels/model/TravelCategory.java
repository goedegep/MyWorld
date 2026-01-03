/**
 */
package goedegep.travels.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Travel Category</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.travels.model.TravelCategory#getTravels <em>Travels</em>}</li>
 *   <li>{@link goedegep.travels.model.TravelCategory#getTravelCategoryName <em>Travel Category Name</em>}</li>
 * </ul>
 *
 * @see goedegep.travels.model.TravelsPackage#getTravelCategory()
 * @model
 * @generated
 */
public interface TravelCategory extends EObject {

  /**
   * Returns the value of the '<em><b>Travels</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.travels.model.Travel}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Travels</em>' containment reference list.
   * @see goedegep.travels.model.TravelsPackage#getTravelCategory_Travels()
   * @model containment="true"
   * @generated
   */
  EList<Travel> getTravels();

  /**
   * Returns the value of the '<em><b>Travel Category Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Travel Category Name</em>' attribute.
   * @see #isSetTravelCategoryName()
   * @see #unsetTravelCategoryName()
   * @see #setTravelCategoryName(String)
   * @see goedegep.travels.model.TravelsPackage#getTravelCategory_TravelCategoryName()
   * @model unsettable="true"
   * @generated
   */
  String getTravelCategoryName();

  /**
   * Sets the value of the '{@link goedegep.travels.model.TravelCategory#getTravelCategoryName <em>Travel Category Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Travel Category Name</em>' attribute.
   * @see #isSetTravelCategoryName()
   * @see #unsetTravelCategoryName()
   * @see #getTravelCategoryName()
   * @generated
   */
  void setTravelCategoryName(String value);

  /**
   * Unsets the value of the '{@link goedegep.travels.model.TravelCategory#getTravelCategoryName <em>Travel Category Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetTravelCategoryName()
   * @see #getTravelCategoryName()
   * @see #setTravelCategoryName(String)
   * @generated
   */
  void unsetTravelCategoryName();

  /**
   * Returns whether the value of the '{@link goedegep.travels.model.TravelCategory#getTravelCategoryName <em>Travel Category Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Travel Category Name</em>' attribute is set.
   * @see #unsetTravelCategoryName()
   * @see #getTravelCategoryName()
   * @see #setTravelCategoryName(String)
   * @generated
   */
  boolean isSetTravelCategoryName();
} // TravelCategory
