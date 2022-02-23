/**
 */
package goedegep.icons.iconset.model.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import goedegep.icons.iconset.model.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.icons.iconset.model.ModelPackage
 * @generated
 */
public class ModelAdapterFactory extends AdapterFactoryImpl {
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static ModelPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModelAdapterFactory() {
    if (modelPackage == null) {
      modelPackage = ModelPackage.eINSTANCE;
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
  protected ModelSwitch<Adapter> modelSwitch =
    new ModelSwitch<Adapter>() {
      @Override
      public Adapter caseIconSet(IconSet object) {
        return createIconSetAdapter();
      }
      @Override
      public Adapter caseIconSize(IconSize object) {
        return createIconSizeAdapter();
      }
      @Override
      public Adapter caseIconDescriptor(IconDescriptor object) {
        return createIconDescriptorAdapter();
      }
      @Override
      public Adapter caseIconData(IconData object) {
        return createIconDataAdapter();
      }
      @Override
      public Adapter caseIconInfo(IconInfo object) {
        return createIconInfoAdapter();
      }
      @Override
      public Adapter caseIconDefinition(IconDefinition object) {
        return createIconDefinitionAdapter();
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
   * Creates a new adapter for an object of class '{@link goedegep.icons.iconset.model.IconSet <em>Icon Set</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.icons.iconset.model.IconSet
   * @generated
   */
  public Adapter createIconSetAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.icons.iconset.model.IconSize <em>Icon Size</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.icons.iconset.model.IconSize
   * @generated
   */
  public Adapter createIconSizeAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.icons.iconset.model.IconDescriptor <em>Icon Descriptor</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.icons.iconset.model.IconDescriptor
   * @generated
   */
  public Adapter createIconDescriptorAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.icons.iconset.model.IconData <em>Icon Data</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.icons.iconset.model.IconData
   * @generated
   */
  public Adapter createIconDataAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.icons.iconset.model.IconInfo <em>Icon Info</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.icons.iconset.model.IconInfo
   * @generated
   */
  public Adapter createIconInfoAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.icons.iconset.model.IconDefinition <em>Icon Definition</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.icons.iconset.model.IconDefinition
   * @generated
   */
  public Adapter createIconDefinitionAdapter() {
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

} //ModelAdapterFactory
