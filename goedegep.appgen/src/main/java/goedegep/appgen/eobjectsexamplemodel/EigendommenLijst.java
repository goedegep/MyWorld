/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.appgen.eobjectsexamplemodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Eigendommen Lijst</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link goedegep.finan.nota.EigendommenLijst#getEigendommen <em>Eigendommen</em>}</li>
 * </ul>
 * </p>
 *
 * @see goedegep.finan.nota.NotaPackage#getEigendommenLijst()
 * @model
 * @generated
 */
public interface EigendommenLijst extends EObject {
  /**
   * Returns the value of the '<em><b>Eigendommen</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.finan.nota.Eigendom}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Eigendommen</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Eigendommen</em>' containment reference list.
   * @see goedegep.finan.nota.NotaPackage#getEigendommenLijst_Eigendommen()
   * @model containment="true" upper="8"
   * @generated
   */
  EList<Eigendom> getEigendommen();

//  void moveEigendom(int row, boolean up);

} // EigendommenLijst
