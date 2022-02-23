/**
 */
package goedegep.invandprop.model.impl;

import goedegep.invandprop.model.*;

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
public class InvAndPropFactoryImpl extends EFactoryImpl implements InvAndPropFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static InvAndPropFactory init() {
    try {
      InvAndPropFactory theInvAndPropFactory = (InvAndPropFactory)EPackage.Registry.INSTANCE.getEFactory(InvAndPropPackage.eNS_URI);
      if (theInvAndPropFactory != null) {
        return theInvAndPropFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new InvAndPropFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InvAndPropFactoryImpl() {
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
      case InvAndPropPackage.INVOICES_AND_PROPERTIES: return createInvoicesAndProperties();
      case InvAndPropPackage.INVOICES: return createInvoices();
      case InvAndPropPackage.INVOICE: return createInvoice();
      case InvAndPropPackage.EXPENDITURE: return createExpenditure();
      case InvAndPropPackage.INVOICE_ITEM: return createInvoiceItem();
      case InvAndPropPackage.PROPERTIES: return createProperties();
      case InvAndPropPackage.PROPERTY: return createProperty();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InvoicesAndProperties createInvoicesAndProperties() {
    InvoicesAndPropertiesImpl invoicesAndProperties = new InvoicesAndPropertiesImpl();
    return invoicesAndProperties;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Invoices createInvoices() {
    InvoicesImpl invoices = new InvoicesImpl();
    return invoices;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Invoice createInvoice() {
    InvoiceImpl invoice = new InvoiceImpl();
    return invoice;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expenditure createExpenditure() {
    ExpenditureImpl expenditure = new ExpenditureImpl();
    return expenditure;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InvoiceItem createInvoiceItem() {
    InvoiceItemImpl invoiceItem = new InvoiceItemImpl();
    return invoiceItem;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Properties createProperties() {
    PropertiesImpl properties = new PropertiesImpl();
    return properties;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Property createProperty() {
    PropertyImpl property = new PropertyImpl();
    return property;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InvAndPropPackage getInvAndPropPackage() {
    return (InvAndPropPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static InvAndPropPackage getPackage() {
    return InvAndPropPackage.eINSTANCE;
  }

} //InvAndPropFactoryImpl
