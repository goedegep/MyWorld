/**
 */
package goedegep.finan.lynx2finan.model.impl;

import goedegep.finan.lynx2finan.model.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LynxToFinanFactoryImpl extends EFactoryImpl implements LynxToFinanFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static LynxToFinanFactory init() {
    try {
      LynxToFinanFactory theLynxToFinanFactory = (LynxToFinanFactory)EPackage.Registry.INSTANCE.getEFactory(LynxToFinanPackage.eNS_URI);
      if (theLynxToFinanFactory != null) {
        return theLynxToFinanFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new LynxToFinanFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LynxToFinanFactoryImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass) {
    switch (eClass.getClassifierID()) {
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST: return createLynxToFinanShareIdList();
      case LynxToFinanPackage.LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY: return createLynxToFinanShareIdListEntry();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public LynxToFinanShareIdList createLynxToFinanShareIdList() {
    LynxToFinanShareIdListImpl lynxToFinanShareIdList = new LynxToFinanShareIdListImpl();
    return lynxToFinanShareIdList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public LynxToFinanShareIdListEntry createLynxToFinanShareIdListEntry() {
    LynxToFinanShareIdListEntryImpl lynxToFinanShareIdListEntry = new LynxToFinanShareIdListEntryImpl();
    return lynxToFinanShareIdListEntry;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public LynxToFinanPackage getLynxToFinanPackage() {
    return (LynxToFinanPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static LynxToFinanPackage getPackage() {
    return LynxToFinanPackage.eINSTANCE;
  }

} //LynxToFinanFactoryImpl
