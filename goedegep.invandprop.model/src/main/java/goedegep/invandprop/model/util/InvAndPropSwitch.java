/**
 */
package goedegep.invandprop.model.util;

import goedegep.invandprop.model.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see goedegep.invandprop.model.InvAndPropPackage
 * @generated
 */
public class InvAndPropSwitch<T> extends Switch<T> {
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static InvAndPropPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InvAndPropSwitch() {
    if (modelPackage == null) {
      modelPackage = InvAndPropPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage) {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject) {
    switch (classifierID) {
      case InvAndPropPackage.INVOICES_AND_PROPERTIES: {
        InvoicesAndProperties invoicesAndProperties = (InvoicesAndProperties)theEObject;
        T result = caseInvoicesAndProperties(invoicesAndProperties);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case InvAndPropPackage.INVOICES: {
        Invoices invoices = (Invoices)theEObject;
        T result = caseInvoices(invoices);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case InvAndPropPackage.INVOICE: {
        Invoice invoice = (Invoice)theEObject;
        T result = caseInvoice(invoice);
        if (result == null) result = caseExpenditure(invoice);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case InvAndPropPackage.EXPENDITURE: {
        Expenditure expenditure = (Expenditure)theEObject;
        T result = caseExpenditure(expenditure);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case InvAndPropPackage.INVOICE_ITEM: {
        InvoiceItem invoiceItem = (InvoiceItem)theEObject;
        T result = caseInvoiceItem(invoiceItem);
        if (result == null) result = caseExpenditure(invoiceItem);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case InvAndPropPackage.PROPERTIES: {
        Properties properties = (Properties)theEObject;
        T result = caseProperties(properties);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case InvAndPropPackage.PROPERTY: {
        Property property = (Property)theEObject;
        T result = caseProperty(property);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM: {
        InvoiceAndPropertyItem invoiceAndPropertyItem = (InvoiceAndPropertyItem)theEObject;
        T result = caseInvoiceAndPropertyItem(invoiceAndPropertyItem);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case InvAndPropPackage.INVOICE_AND_PROPERTY: {
        InvoiceAndProperty invoiceAndProperty = (InvoiceAndProperty)theEObject;
        T result = caseInvoiceAndProperty(invoiceAndProperty);
        if (result == null) result = caseInvoiceAndPropertyItem(invoiceAndProperty);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Invoices And Properties</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Invoices And Properties</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInvoicesAndProperties(InvoicesAndProperties object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Invoices</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Invoices</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInvoices(Invoices object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Invoice</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Invoice</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInvoice(Invoice object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Expenditure</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Expenditure</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseExpenditure(Expenditure object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Invoice Item</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Invoice Item</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInvoiceItem(InvoiceItem object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Properties</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Properties</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseProperties(Properties object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Property</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Property</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseProperty(Property object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Invoice And Property Item</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Invoice And Property Item</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInvoiceAndPropertyItem(InvoiceAndPropertyItem object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Invoice And Property</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Invoice And Property</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInvoiceAndProperty(InvoiceAndProperty object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object) {
    return null;
  }

} //InvAndPropSwitch
