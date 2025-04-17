/**
 */
package goedegep.invandprop.model.util;

import goedegep.invandprop.model.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.invandprop.model.InvAndPropPackage
 * @generated
 */
public class InvAndPropAdapterFactory extends AdapterFactoryImpl {
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static InvAndPropPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InvAndPropAdapterFactory() {
    if (modelPackage == null) {
      modelPackage = InvAndPropPackage.eINSTANCE;
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
  protected InvAndPropSwitch<Adapter> modelSwitch =
    new InvAndPropSwitch<Adapter>() {
      @Override
      public Adapter caseInvoicesAndProperties(InvoicesAndProperties object) {
        return createInvoicesAndPropertiesAdapter();
      }
      @Override
      public Adapter caseInvoices(Invoices object) {
        return createInvoicesAdapter();
      }
      @Override
      public Adapter caseInvoice(Invoice object) {
        return createInvoiceAdapter();
      }
      @Override
      public Adapter caseExpenditure(Expenditure object) {
        return createExpenditureAdapter();
      }
      @Override
      public Adapter caseInvoiceItem(InvoiceItem object) {
        return createInvoiceItemAdapter();
      }
      @Override
      public Adapter caseProperties(Properties object) {
        return createPropertiesAdapter();
      }
      @Override
      public Adapter caseProperty(Property object) {
        return createPropertyAdapter();
      }
      @Override
      public Adapter caseInvoiceAndPropertyItem(InvoiceAndPropertyItem object) {
        return createInvoiceAndPropertyItemAdapter();
      }
      @Override
      public Adapter caseInvoiceAndProperty(InvoiceAndProperty object) {
        return createInvoiceAndPropertyAdapter();
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
   * Creates a new adapter for an object of class '{@link goedegep.invandprop.model.InvoicesAndProperties <em>Invoices And Properties</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.invandprop.model.InvoicesAndProperties
   * @generated
   */
  public Adapter createInvoicesAndPropertiesAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.invandprop.model.Invoices <em>Invoices</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.invandprop.model.Invoices
   * @generated
   */
  public Adapter createInvoicesAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.invandprop.model.Invoice <em>Invoice</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.invandprop.model.Invoice
   * @generated
   */
  public Adapter createInvoiceAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.invandprop.model.Expenditure <em>Expenditure</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.invandprop.model.Expenditure
   * @generated
   */
  public Adapter createExpenditureAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.invandprop.model.InvoiceItem <em>Invoice Item</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.invandprop.model.InvoiceItem
   * @generated
   */
  public Adapter createInvoiceItemAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.invandprop.model.Properties <em>Properties</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.invandprop.model.Properties
   * @generated
   */
  public Adapter createPropertiesAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.invandprop.model.Property <em>Property</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.invandprop.model.Property
   * @generated
   */
  public Adapter createPropertyAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.invandprop.model.InvoiceAndPropertyItem <em>Invoice And Property Item</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.invandprop.model.InvoiceAndPropertyItem
   * @generated
   */
  public Adapter createInvoiceAndPropertyItemAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.invandprop.model.InvoiceAndProperty <em>Invoice And Property</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.invandprop.model.InvoiceAndProperty
   * @generated
   */
  public Adapter createInvoiceAndPropertyAdapter() {
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

} //InvAndPropAdapterFactory
