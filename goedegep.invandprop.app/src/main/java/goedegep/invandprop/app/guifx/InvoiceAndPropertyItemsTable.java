package goedegep.invandprop.app.guifx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.appgen.Operation;
import goedegep.appgen.TableRowOperationDescriptor;
import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.InvoiceAndPropertyItem;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorCustom;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;
import javafx.scene.control.TableCell;

public class InvoiceAndPropertyItemsTable extends EObjectTable<InvoiceAndPropertyItem> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(InvoiceAndPropertyItemsTable.class.getName());
  InvAndPropPackage invAndPropPackage = InvAndPropPackage.eINSTANCE;
  
  @SuppressWarnings("unused")
  private CustomizationFx customization;

  public InvoiceAndPropertyItemsTable(CustomizationFx customization) {
    super(customization, InvAndPropPackage.eINSTANCE.getInvoiceAndPropertyItem(), new InvoiceAndPropertyItemsDescriptor(customization));
    
    this.customization = customization;
    
    setMinWidth(1700);
    setMaxHeight(150);
  }
}



/**
 * This class provides the descriptor for the invoices and properties table.
 */
class InvoiceAndPropertyItemsDescriptor extends EObjectTableDescriptor<InvoiceAndPropertyItem> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(InvoiceAndPropertyItemsDescriptor.class.getName());
  private static InvAndPropPackage INVOICES_AND_PROPERTIES_PACKAGE = InvAndPropPackage.eINSTANCE;
  
  private static List<EObjectTableColumnDescriptorAbstract<InvoiceAndPropertyItem>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<InvoiceAndPropertyItem>>of(
      new EObjectTableColumnDescriptorBasic<InvoiceAndPropertyItem>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceAndPropertyItem_NumberOfItems(), "Number", false, true),
      new EObjectTableColumnDescriptorBasic<InvoiceAndPropertyItem>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceAndPropertyItem_Description(), "Description", 300, false, true),
      new EObjectTableColumnDescriptorBasic<InvoiceAndPropertyItem>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceAndPropertyItem_Brand(), "Brand", false, true),
      new EObjectTableColumnDescriptorBasic<InvoiceAndPropertyItem>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceAndPropertyItem_Type(), "Type", 200, false, true),
      new EObjectTableColumnDescriptorBasic<InvoiceAndPropertyItem>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceAndPropertyItem_SerialNumber(), "Serialnumber", false, true),
      new EObjectTableColumnDescriptorCustom<InvoiceAndPropertyItem>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceAndPropertyItem_Amount(),
          "Amount", 130, true, true, invoiceAndProperty -> {
            TableCell<InvoiceAndPropertyItem, Object> cell = new TableCell<>() {
              PgCurrencyFormat cf = new PgCurrencyFormat();

              @Override
              protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                
                
                if(empty || (item == null)) {
                  setText(null);
                  setGraphic(null);
                } else {
                  setText(null);
                  setGraphic(null);
                  PgCurrency invoiceAmount = (PgCurrency) item;
                  
                  setText(cf.format(invoiceAmount));
                 }
              }                            
            };

            return cell;
          }),
      new EObjectTableColumnDescriptorBasic<InvoiceAndPropertyItem>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceAndPropertyItem_Remarks(), "Remarks", false, true),
      new EObjectTableColumnDescriptorBasic<InvoiceAndPropertyItem>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceAndPropertyItem_FromDate(), "From date", false, true),
      new EObjectTableColumnDescriptorBasic<InvoiceAndPropertyItem>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceAndPropertyItem_UntilDate(), "Until date", false, true),
      new EObjectTableColumnDescriptorBasic<InvoiceAndPropertyItem>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceAndPropertyItem_Archive(), "Archived", false, true)
      );
  
  @SuppressWarnings("serial")
  private static Map<Operation, TableRowOperationDescriptor<InvoiceAndPropertyItem>> rowOperations = new HashMap<>() {
    {
      put(Operation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete"));
    }
  };
  
  public InvoiceAndPropertyItemsDescriptor(CustomizationFx customization) {
    super("There are no invoice and property items to show", null, columnDescriptors, rowOperations);
  }
  
}


