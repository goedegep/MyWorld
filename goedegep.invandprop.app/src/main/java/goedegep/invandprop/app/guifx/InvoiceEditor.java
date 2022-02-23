package goedegep.invandprop.app.guifx;

import java.util.Arrays;

import org.eclipse.emf.common.util.EList;

import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.Invoice;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecteditor.EObjectAttributeEditDescriptor;
import goedegep.jfx.eobjecteditor.EObjectEditor;
import goedegep.jfx.eobjecteditor.EObjectEditorDescriptor;

public class InvoiceEditor extends EObjectEditor<Invoice> {

  public InvoiceEditor(CustomizationFx customization, EList<Invoice> invoices) {
    super(customization, InvAndPropPackage.eINSTANCE.getInvoice(), new InvoiceEditorDescriptor(customization),  invoices);    
  }
  
}


class InvoiceEditorDescriptor extends EObjectEditorDescriptor {
  private static final InvAndPropPackage INVOICES_AND_PROPERTIES_PACKAGE = InvAndPropPackage.eINSTANCE;

  public static final String DATE_ID = "Date";
  public static final String COMPANY_ID = "Company";
  public static final String DESCRIPTION_ID = "Description";
  public static final String AMOUNT_ID = "Amount";
  public static final String REMARKS_ID = "Remarks";
  public static final String DESCRIPTION_FROM_PROPERTY_ID = "Derive description from property";

  public InvoiceEditorDescriptor(CustomizationFx customization) {
    setWindowTitle("invoice");
    
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
        
    EObjectAttributeEditDescriptor[] eObjectAttributeEditDescriptors = {
        new EObjectAttributeEditDescriptor(DATE_ID, componentFactory.createObjectInputFlexDate(null, 150.0, true, "the invoice date", INVOICES_AND_PROPERTIES_PACKAGE.getInvoice_Date().getName()), INVOICES_AND_PROPERTIES_PACKAGE.getInvoice_Date()),
        new EObjectAttributeEditDescriptor(COMPANY_ID, componentFactory.createObjectInputString(null, 200, true, "the company you paid the invoice to", INVOICES_AND_PROPERTIES_PACKAGE.getInvoice_Company().getName()), INVOICES_AND_PROPERTIES_PACKAGE.getInvoice_Company()),
        new EObjectAttributeEditDescriptor(DESCRIPTION_ID, componentFactory.createObjectInputString(null, 200, false, "typically the product", INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Description().getName()), INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Description()),
        new EObjectAttributeEditDescriptor(AMOUNT_ID, componentFactory.createObjectInputCurrency("", 150, false, "the amount of money paid", INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Amount().getName()), INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Amount()),
        new EObjectAttributeEditDescriptor(REMARKS_ID, componentFactory.createObjectInputString(null, 150.0, true, "any comments on this invoice", INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Remarks().getName()), INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Remarks()),
        new EObjectAttributeEditDescriptor(DESCRIPTION_FROM_PROPERTY_ID, componentFactory.createObjectInputBoolean(null, false, true, "If set, the description will be derived from the related property", INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_DescriptionFromProperty().getName()), INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_DescriptionFromProperty())
        
    };
    
    getEObjectAttributeEditDescriptors().addAll(Arrays.asList(eObjectAttributeEditDescriptors));
  }
}

