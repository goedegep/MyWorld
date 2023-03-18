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
import goedegep.jfx.objectcontrols.ObjectControlBoolean;
import goedegep.jfx.objectcontrols.ObjectControlCurrency;
import goedegep.jfx.objectcontrols.ObjectControlFlexDate;
import goedegep.jfx.objectcontrols.ObjectControlString;

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
    ObjectControlFlexDate dateObjectControl = componentFactory.createObjectControlFlexDate(null, 150.0, true, "the invoice date");
    dateObjectControl.setId(INVOICES_AND_PROPERTIES_PACKAGE.getInvoice_Date().getName());
    ObjectControlString companyObjectControl = componentFactory.createObjectControlString(null, 200, true, "the company you paid the invoice to");
    companyObjectControl.setId(INVOICES_AND_PROPERTIES_PACKAGE.getInvoice_Company().getName());
    ObjectControlString descriptionObjectControl = componentFactory.createObjectControlString(null, 200, false, "typically the product");
    descriptionObjectControl.setId(INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Description().getName());
    ObjectControlCurrency amountObjectControl = componentFactory.createObjectControlCurrency(null, 150, false, "the amount of money paid");
    amountObjectControl.setId(INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Amount().getName());
    ObjectControlString remarksObjectControl = componentFactory.createObjectControlString(null, 150.0, true, "any comments on this invoice");
    remarksObjectControl.setId(INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Remarks().getName());
    ObjectControlBoolean descriptionFromPropertyObjectControl = componentFactory.createObjectControlBoolean(null, false, true, "If set, the description will be derived from the related property");
    descriptionFromPropertyObjectControl.setId(INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_DescriptionFromProperty().getName());
        
    EObjectAttributeEditDescriptor[] eObjectAttributeEditDescriptors = {
        new EObjectAttributeEditDescriptor(DATE_ID, dateObjectControl, INVOICES_AND_PROPERTIES_PACKAGE.getInvoice_Date()),
        new EObjectAttributeEditDescriptor(COMPANY_ID, companyObjectControl, INVOICES_AND_PROPERTIES_PACKAGE.getInvoice_Company()),
        new EObjectAttributeEditDescriptor(DESCRIPTION_ID, descriptionObjectControl, INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Description()),
        new EObjectAttributeEditDescriptor(AMOUNT_ID, amountObjectControl, INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Amount()),
        new EObjectAttributeEditDescriptor(REMARKS_ID, remarksObjectControl, INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Remarks()),
        new EObjectAttributeEditDescriptor(DESCRIPTION_FROM_PROPERTY_ID, descriptionFromPropertyObjectControl, INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_DescriptionFromProperty())
        
    };
    
    getEObjectAttributeEditDescriptors().addAll(Arrays.asList(eObjectAttributeEditDescriptors));
  }
}

