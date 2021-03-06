/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.properties.model.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import goedegep.properties.model.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.properties.model.PropertiesPackage
 * @generated
 */
public class PropertiesAdapterFactory extends AdapterFactoryImpl {
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static PropertiesPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PropertiesAdapterFactory() {
    if (modelPackage == null) {
      modelPackage = PropertiesPackage.eINSTANCE;
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
      return ((EObject) object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PropertiesSwitch<Adapter> modelSwitch = new PropertiesSwitch<Adapter>() {
    @Override
    public Adapter casePropertyDescriptorGroup(PropertyDescriptorGroup object) {
      return createPropertyDescriptorGroupAdapter();
    }

    @Override
    public Adapter casePropertyDescriptor(PropertyDescriptor object) {
      return createPropertyDescriptorAdapter();
    }

    @Override
    public Adapter caseFilePropertyDescriptor(FilePropertyDescriptor object) {
      return createFilePropertyDescriptorAdapter();
    }

    @Override
    public Adapter caseProperty(Property object) {
      return createPropertyAdapter();
    }

    @Override
    public Adapter casePropertyGroup(PropertyGroup object) {
      return createPropertyGroupAdapter();
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
    return modelSwitch.doSwitch((EObject) target);
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.properties.model.PropertyDescriptorGroup <em>Property Descriptor Group</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.properties.model.PropertyDescriptorGroup
   * @generated
   */
  public Adapter createPropertyDescriptorGroupAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.properties.model.PropertyDescriptor <em>Property Descriptor</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.properties.model.PropertyDescriptor
   * @generated
   */
  public Adapter createPropertyDescriptorAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.properties.model.FilePropertyDescriptor <em>File Property Descriptor</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.properties.model.FilePropertyDescriptor
   * @generated
   */
  public Adapter createFilePropertyDescriptorAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.properties.model.Property <em>Property</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.properties.model.Property
   * @generated
   */
  public Adapter createPropertyAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.properties.model.PropertyGroup <em>Property Group</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.properties.model.PropertyGroup
   * @generated
   */
  public Adapter createPropertyGroupAdapter() {
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

} //PropertiesAdapterFactory
