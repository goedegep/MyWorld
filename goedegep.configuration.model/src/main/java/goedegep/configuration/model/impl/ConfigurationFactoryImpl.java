/**
 */
package goedegep.configuration.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import goedegep.configuration.model.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ConfigurationFactoryImpl extends EFactoryImpl implements ConfigurationFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static ConfigurationFactory init() {
    try {
      ConfigurationFactory theConfigurationFactory = (ConfigurationFactory) EPackage.Registry.INSTANCE
          .getEFactory(ConfigurationPackage.eNS_URI);
      if (theConfigurationFactory != null) {
        return theConfigurationFactory;
      }
    } catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new ConfigurationFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConfigurationFactoryImpl() {
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
    case ConfigurationPackage.LOOK:
      return createLook();
    case ConfigurationPackage.MODULE_LOOK:
      return createModuleLook();
    case ConfigurationPackage.LOOK_INFO:
      return createLookInfo();
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
  public LookInfo createLookInfo() {
    LookInfoImpl lookInfo = new LookInfoImpl();
    return lookInfo;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ModuleLook createModuleLook() {
    ModuleLookImpl moduleLook = new ModuleLookImpl();
    return moduleLook;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Look createLook() {
    LookImpl look = new LookImpl();
    return look;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ConfigurationPackage getConfigurationPackage() {
    return (ConfigurationPackage) getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static ConfigurationPackage getPackage() {
    return ConfigurationPackage.eINSTANCE;
  }

} //ConfigurationFactoryImpl
