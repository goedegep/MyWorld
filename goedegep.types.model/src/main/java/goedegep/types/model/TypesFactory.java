/**
 */
package goedegep.types.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.types.model.TypesPackage
 * @generated
 */
public interface TypesFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  TypesFactory eINSTANCE = goedegep.types.model.impl.TypesFactoryImpl.init();

  /**
   * Returns a new object of class '<em>File Reference</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>File Reference</em>'.
   * @generated
   */
  FileReference createFileReference();

  /**
   * Returns a new object of class '<em>Date Rate Tuplet</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Date Rate Tuplet</em>'.
   * @generated
   */
  DateRateTuplet createDateRateTuplet();

  /**
   * Returns a new object of class '<em>Event</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Event</em>'.
   * @generated
   */
  Event createEvent();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  TypesPackage getTypesPackage();

} //TypesFactory
