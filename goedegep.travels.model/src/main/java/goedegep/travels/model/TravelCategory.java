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
} // TravelCategory
