/**
 */
package goedegep.gpx10.model.util;

import goedegep.gpx10.model.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.gpx10.model.GPX10Package
 * @generated
 */
public class GPX10AdapterFactory extends AdapterFactoryImpl {
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static GPX10Package modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GPX10AdapterFactory() {
    if (modelPackage == null) {
      modelPackage = GPX10Package.eINSTANCE;
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
  protected GPX10Switch<Adapter> modelSwitch =
    new GPX10Switch<Adapter>() {
      @Override
      public Adapter caseBoundsType(BoundsType object) {
        return createBoundsTypeAdapter();
      }
      @Override
      public Adapter caseDocumentRoot(DocumentRoot object) {
        return createDocumentRootAdapter();
      }
      @Override
      public Adapter caseGpxType(GpxType object) {
        return createGpxTypeAdapter();
      }
      @Override
      public Adapter caseRteptType(RteptType object) {
        return createRteptTypeAdapter();
      }
      @Override
      public Adapter caseRteType(RteType object) {
        return createRteTypeAdapter();
      }
      @Override
      public Adapter caseTrkptType(TrkptType object) {
        return createTrkptTypeAdapter();
      }
      @Override
      public Adapter caseTrksegType(TrksegType object) {
        return createTrksegTypeAdapter();
      }
      @Override
      public Adapter caseTrkType(TrkType object) {
        return createTrkTypeAdapter();
      }
      @Override
      public Adapter caseWptType(WptType object) {
        return createWptTypeAdapter();
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
   * Creates a new adapter for an object of class '{@link goedegep.gpx10.model.BoundsType <em>Bounds Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.gpx10.model.BoundsType
   * @generated
   */
  public Adapter createBoundsTypeAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.gpx10.model.DocumentRoot <em>Document Root</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.gpx10.model.DocumentRoot
   * @generated
   */
  public Adapter createDocumentRootAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.gpx10.model.GpxType <em>Gpx Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.gpx10.model.GpxType
   * @generated
   */
  public Adapter createGpxTypeAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.gpx10.model.RteptType <em>Rtept Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.gpx10.model.RteptType
   * @generated
   */
  public Adapter createRteptTypeAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.gpx10.model.RteType <em>Rte Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.gpx10.model.RteType
   * @generated
   */
  public Adapter createRteTypeAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.gpx10.model.TrkptType <em>Trkpt Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.gpx10.model.TrkptType
   * @generated
   */
  public Adapter createTrkptTypeAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.gpx10.model.TrksegType <em>Trkseg Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.gpx10.model.TrksegType
   * @generated
   */
  public Adapter createTrksegTypeAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.gpx10.model.TrkType <em>Trk Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.gpx10.model.TrkType
   * @generated
   */
  public Adapter createTrkTypeAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.gpx10.model.WptType <em>Wpt Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.gpx10.model.WptType
   * @generated
   */
  public Adapter createWptTypeAdapter() {
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

} //GPX10AdapterFactory
