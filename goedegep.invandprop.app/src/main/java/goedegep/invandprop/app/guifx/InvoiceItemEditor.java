package goedegep.invandprop.app.guifx;

import java.util.Arrays;

import org.eclipse.emf.common.util.EList;

import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.InvoiceItem;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecteditor.EObjectAttributeEditDescriptor;
import goedegep.jfx.eobjecteditor.EObjectEditor;
import goedegep.jfx.eobjecteditor.EObjectEditorDescriptor;

public class InvoiceItemEditor extends EObjectEditor<InvoiceItem> {

  public InvoiceItemEditor(CustomizationFx customization, EList<InvoiceItem> invoiceItems) {
    super(customization, InvAndPropPackage.eINSTANCE.getInvoiceItem(), new InvoiceItemEditorDescriptor(customization),  invoiceItems);    
  }
  
}

class InvoiceItemEditorDescriptor extends EObjectEditorDescriptor {
  public static final String NUMBER_OF_ITEMS_ID = "Number of items";
  public static final String DESCRIPTION_ID = "Description";
  public static final String AMOUNT_ID = "Amount";
  public static final String REMARKS_ID = "Remarks";

  private static final InvAndPropPackage INVOICES_AND_PROPERTIES_PACKAGE = InvAndPropPackage.eINSTANCE;

  public InvoiceItemEditorDescriptor(CustomizationFx customization) {
    setWindowTitle("invoice item");
    
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
        
    EObjectAttributeEditDescriptor[] eObjectAttributeEditDescriptors = {
        new EObjectAttributeEditDescriptor(NUMBER_OF_ITEMS_ID, componentFactory.createObjectControlInteger(1, 150.0, true, "the number of items"), INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceItem_NumberOfItems()),
        new EObjectAttributeEditDescriptor(DESCRIPTION_ID, componentFactory.createObjectControlString(null, 200, false, "typically the product"), INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Description()),
        new EObjectAttributeEditDescriptor(AMOUNT_ID, componentFactory.createObjectControlCurrency(null, 150, false, "the amount of money paid"), INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Amount()),
        new EObjectAttributeEditDescriptor(REMARKS_ID, componentFactory.createObjectControlString(null, 150.0, true, "any comments on this invoice"), INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Remarks())
    };
    
    getEObjectAttributeEditDescriptors().addAll(Arrays.asList(eObjectAttributeEditDescriptors));
  }
}

