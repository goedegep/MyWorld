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
 * A representation of the model object '<em><b>Notas</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link goedegep.finan.nota.Notas#getNotas <em>Notas</em>}</li>
 * </ul>
 * </p>
 *
 * @see goedegep.finan.nota.NotaPackage#getNotas()
 * @model
 * @generated
 */
public interface Notas extends EObject {
  /**
   * Returns the value of the '<em><b>Notas</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.finan.nota.Nota}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Notas</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Notas</em>' containment reference list.
   * @see goedegep.finan.nota.NotaPackage#getNotas_Notas()
   * @model containment="true"
   * @generated
   */
  EList<Nota> getNotas();
  
  void moveNota(int index, boolean up);

} // Notas
