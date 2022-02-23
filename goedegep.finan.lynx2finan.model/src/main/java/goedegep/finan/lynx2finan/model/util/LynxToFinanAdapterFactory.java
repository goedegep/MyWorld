/**
 */
package goedegep.finan.lynx2finan.model.util;

import goedegep.finan.lynx2finan.model.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.finan.lynx2finan.model.LynxToFinanPackage
 * @generated
 */
public class LynxToFinanAdapterFactory extends AdapterFactoryImpl {
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static LynxToFinanPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LynxToFinanAdapterFactory() {
    if (modelPackage == null) {
      modelPackage = LynxToFinanPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object) {
    if (object == modelPackage) {
      return true;
    }
    if (object instanceof EObject) {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected LynxToFinanSwitch<Adapter> modelSwitch =
    new LynxToFinanSwitch<Adapter>() {
      @Override
      public Adapter caseLynxToFinanShareIdList(LynxToFinanShareIdList object) {
        return createLynxToFinanShareIdListAdapter();
      }
      @Override
      public Adapter caseLynxToFinanShareIdListEntry(LynxToFinanShareIdListEntry object) {
        return createLynxToFinanShareIdListEntryAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object) {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target) {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.lynx2finan.model.LynxToFinanShareIdList <em>Share Id List</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.lynx2finan.model.LynxToFinanShareIdList
   * @generated
   */
  public Adapter createLynxToFinanShareIdListAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry <em>Share Id List Entry</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry
   * @generated
   */
  public Adapter createLynxToFinanShareIdListEntryAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter() {
    return null;
  }

} //LynxToFinanAdapterFactory
