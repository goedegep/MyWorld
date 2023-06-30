/**
 */
package goedegep.pctools.filescontrolled.model.util;

import goedegep.pctools.filescontrolled.model.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.pctools.filescontrolled.model.PCToolsPackage
 * @generated
 */
public class PCToolsAdapterFactory extends AdapterFactoryImpl {
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static PCToolsPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PCToolsAdapterFactory() {
    if (modelPackage == null) {
      modelPackage = PCToolsPackage.eINSTANCE;
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
  protected PCToolsSwitch<Adapter> modelSwitch =
    new PCToolsSwitch<Adapter>() {
      @Override
      public Adapter caseDiscStructureSpecification(DiscStructureSpecification object) {
        return createDiscStructureSpecificationAdapter();
      }
      @Override
      public Adapter caseDirectorySpecification(DirectorySpecification object) {
        return createDirectorySpecificationAdapter();
      }
      @Override
      public Adapter caseDescribedItem(DescribedItem object) {
        return createDescribedItemAdapter();
      }
      @Override
      public Adapter caseResult(Result object) {
        return createResultAdapter();
      }
      @Override
      public Adapter caseUncontrolledFolderInfo(UncontrolledFolderInfo object) {
        return createUncontrolledFolderInfoAdapter();
      }
      @Override
      public Adapter caseFileInfo(FileInfo object) {
        return createFileInfoAdapter();
      }
      @Override
      public Adapter caseControlledRootFolderInfo(ControlledRootFolderInfo object) {
        return createControlledRootFolderInfoAdapter();
      }
      @Override
      public Adapter caseControlledFolderInfo(ControlledFolderInfo object) {
        return createControlledFolderInfoAdapter();
      }
      @Override
      public Adapter caseFolderInfo(FolderInfo object) {
        return createFolderInfoAdapter();
      }
      @Override
      public Adapter caseUncontrolledRootFolderInfo(UncontrolledRootFolderInfo object) {
        return createUncontrolledRootFolderInfoAdapter();
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
   * Creates a new adapter for an object of class '{@link goedegep.pctools.filescontrolled.model.DiscStructureSpecification <em>Disc Structure Specification</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.pctools.filescontrolled.model.DiscStructureSpecification
   * @generated
   */
  public Adapter createDiscStructureSpecificationAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.pctools.filescontrolled.model.DirectorySpecification <em>Directory Specification</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.pctools.filescontrolled.model.DirectorySpecification
   * @generated
   */
  public Adapter createDirectorySpecificationAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.pctools.filescontrolled.model.DescribedItem <em>Described Item</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.pctools.filescontrolled.model.DescribedItem
   * @generated
   */
  public Adapter createDescribedItemAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.pctools.filescontrolled.model.Result <em>Result</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.pctools.filescontrolled.model.Result
   * @generated
   */
  public Adapter createResultAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.pctools.filescontrolled.model.UncontrolledFolderInfo <em>Uncontrolled Folder Info</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.pctools.filescontrolled.model.UncontrolledFolderInfo
   * @generated
   */
  public Adapter createUncontrolledFolderInfoAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.pctools.filescontrolled.model.FolderInfo <em>Folder Info</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.pctools.filescontrolled.model.FolderInfo
   * @generated
   */
  public Adapter createFolderInfoAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.pctools.filescontrolled.model.UncontrolledRootFolderInfo <em>Uncontrolled Root Folder Info</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.pctools.filescontrolled.model.UncontrolledRootFolderInfo
   * @generated
   */
  public Adapter createUncontrolledRootFolderInfoAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.pctools.filescontrolled.model.FileInfo <em>File Info</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.pctools.filescontrolled.model.FileInfo
   * @generated
   */
  public Adapter createFileInfoAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.pctools.filescontrolled.model.ControlledRootFolderInfo <em>Controlled Root Folder Info</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.pctools.filescontrolled.model.ControlledRootFolderInfo
   * @generated
   */
  public Adapter createControlledRootFolderInfoAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.pctools.filescontrolled.model.ControlledFolderInfo <em>Controlled Folder Info</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.pctools.filescontrolled.model.ControlledFolderInfo
   * @generated
   */
  public Adapter createControlledFolderInfoAdapter() {
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

} //PCToolsAdapterFactory
