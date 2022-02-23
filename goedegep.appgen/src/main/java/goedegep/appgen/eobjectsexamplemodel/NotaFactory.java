/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.appgen.eobjectsexamplemodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.appgen.eobjectsexamplemodel.NotaPackage
 * @generated
 */
public interface NotaFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  NotaFactory eINSTANCE = goedegep.appgen.eobjectsexamplemodel.impl.NotaFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Notas</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Notas</em>'.
   * @generated
   */
  Notas createNotas();

  /**
   * Returns a new object of class '<em>Nota</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Nota</em>'.
   * @generated
   */
  Nota createNota();

  /**
   * Returns a new object of class '<em>Item</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Item</em>'.
   * @generated
   */
  NotaItem createNotaItem();

  /**
   * Returns a new object of class '<em>Eigendommen Lijst</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Eigendommen Lijst</em>'.
   * @generated
   */
  EigendommenLijst createEigendommenLijst();

  /**
   * Returns a new object of class '<em>Eigendom</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Eigendom</em>'.
   * @generated
   */
  Eigendom createEigendom();

  /**
   * Returns a new object of class '<em>Uitgave</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Uitgave</em>'.
   * @generated
   */
  Uitgave createUitgave();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  NotaPackage getNotaPackage();

} //NotaFactory
