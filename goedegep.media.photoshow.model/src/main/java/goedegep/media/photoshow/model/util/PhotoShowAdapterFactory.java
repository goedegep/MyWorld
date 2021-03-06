/**
 */
package goedegep.media.photoshow.model.util;

import goedegep.media.photoshow.model.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.media.photoshow.model.PhotoShowPackage
 * @generated
 */
public class PhotoShowAdapterFactory extends AdapterFactoryImpl {
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static PhotoShowPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PhotoShowAdapterFactory() {
    if (modelPackage == null) {
      modelPackage = PhotoShowPackage.eINSTANCE;
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
  protected PhotoShowSwitch<Adapter> modelSwitch =
    new PhotoShowSwitch<Adapter>() {
      @Override
      public Adapter casePhotoShowSpecification(PhotoShowSpecification object) {
        return createPhotoShowSpecificationAdapter();
      }
      @Override
      public Adapter caseTimeOffsetSpecification(TimeOffsetSpecification object) {
        return createTimeOffsetSpecificationAdapter();
      }
      @Override
      public Adapter caseFolderTimeOffsetSpecification(FolderTimeOffsetSpecification object) {
        return createFolderTimeOffsetSpecificationAdapter();
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
   * Creates a new adapter for an object of class '{@link goedegep.media.photoshow.model.PhotoShowSpecification <em>Specification</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.media.photoshow.model.PhotoShowSpecification
   * @generated
   */
  public Adapter createPhotoShowSpecificationAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.media.photoshow.model.TimeOffsetSpecification <em>Time Offset Specification</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.media.photoshow.model.TimeOffsetSpecification
   * @generated
   */
  public Adapter createTimeOffsetSpecificationAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.media.photoshow.model.FolderTimeOffsetSpecification <em>Folder Time Offset Specification</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.media.photoshow.model.FolderTimeOffsetSpecification
   * @generated
   */
  public Adapter createFolderTimeOffsetSpecificationAdapter() {
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

} //PhotoShowAdapterFactory
