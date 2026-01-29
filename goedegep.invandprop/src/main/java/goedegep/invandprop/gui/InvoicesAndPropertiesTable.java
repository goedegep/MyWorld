package goedegep.invandprop.gui;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.InvoiceAndProperty;
import goedegep.invandprop.svc.InvoicesAndPropertiesService;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.Operation;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorCustom;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.jfx.eobjecttable.TableRowOperationDescriptor;
import goedegep.types.model.FileReference;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;
import goedegep.util.money.PgCurrencyPlusStatus;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;

public class InvoicesAndPropertiesTable extends EObjectTable<InvoiceAndProperty> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(InvoicesAndPropertiesTable.class.getName());
  InvAndPropPackage invAndPropPackage = InvAndPropPackage.eINSTANCE;
  
  private CustomizationFx customization;
  private InvoicesAndPropertiesService invoicesAndPropertiesService;

  public InvoicesAndPropertiesTable(CustomizationFx customization, InvoicesAndPropertiesService invoicesAndPropertiesService) {
    super(customization, InvAndPropPackage.eINSTANCE.getInvoiceAndProperty(), new InvoicesAndPropertieseDescriptor(customization),
        invoicesAndPropertiesService.getInvoicesAndPropertiesResource().getEObject(), InvAndPropPackage.eINSTANCE.getInvoicesAndProperties_Invoicseandpropertys());
    
    this.customization = customization;
    this.invoicesAndPropertiesService = invoicesAndPropertiesService;
    
    setMinWidth(1700);
  }

  protected void handleRowDoubleClicked(InvoiceAndProperty invoiceAndProperty) {
//    InvoiceAndPropertyEditor invoiceAndPropertyEditor = InvoiceAndPropertyEditor.newInstance(customization, invoicesAndPropertiesService);
//    Invoice invoice = invoicesAndPropertiesService.getInvoiceForInvoiceAndProperty(invoiceAndProperty);
//    invoiceAndPropertyEditor.setObject(invoiceAndProperty);
//    invoiceAndPropertyEditor.show();
    InvoiceAndPropertyEditor2 invoiceAndPropertyEditor2 = InvoiceAndPropertyEditor2.newInstance(customization, invoicesAndPropertiesService);
    invoiceAndPropertyEditor2.setObject(invoiceAndProperty);
    invoiceAndPropertyEditor2.show();
  }
}



/**
 * This class provides the descriptor for the invoices and properties table.
 */
class InvoicesAndPropertieseDescriptor extends EObjectTableDescriptor<InvoiceAndProperty> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(InvoicesAndPropertieseDescriptor.class.getName());
  private static InvAndPropPackage INVOICES_AND_PROPERTIES_PACKAGE = InvAndPropPackage.eINSTANCE;
  
  private static List<EObjectTableColumnDescriptorAbstract<InvoiceAndProperty>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<InvoiceAndProperty>>of(
      new EObjectTableColumnDescriptorBasic<InvoiceAndProperty>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceAndProperty_Date(), "Date", false, true),
      new EObjectTableColumnDescriptorBasic<InvoiceAndProperty>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceAndProperty_Company(), "Company", false, true),
      new EObjectTableColumnDescriptorBasic<InvoiceAndProperty>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceAndPropertyItem_Description(), "Description", false, true),
      new EObjectTableColumnDescriptorBasic<InvoiceAndProperty>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceAndPropertyItem_Brand(), "Brand", false, true),
      new EObjectTableColumnDescriptorBasic<InvoiceAndProperty>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceAndPropertyItem_Type(), "Type", false, true),
      new EObjectTableColumnDescriptorBasic<InvoiceAndProperty>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceAndPropertyItem_SerialNumber(), "Serialnumber", false, true),
      new EObjectTableColumnDescriptorCustom<InvoiceAndProperty>(null,
          "Amount", 130, true, true, invoiceAndProperty -> {
            TableCell<InvoiceAndProperty, Object> cell = new TableCell<>() {
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
                  InvoiceAndProperty invoiceAndProperty = (InvoiceAndProperty) item;
                  PgCurrencyPlusStatus moneyPlusStatus;
                  PgCurrency invoiceAmount = invoiceAndProperty.getAmount();
                  PgCurrency invoiceItemsAmount = invoiceAndProperty.getTotalAmountInvoiceItems();
                  if (invoiceAmount != null) {
                    if (invoiceItemsAmount == null) {
                      moneyPlusStatus = new PgCurrencyPlusStatus(invoiceAmount);
                    } else {
                      moneyPlusStatus = new PgCurrencyPlusStatus(invoiceAmount, PgCurrencyPlusStatus.Status.SUSPICIOUS);
                    }
                  } else {
                    moneyPlusStatus = new PgCurrencyPlusStatus(invoiceItemsAmount);
                  }
                  
                  switch (moneyPlusStatus.getStatus()) {
                  case ERROR:
                    this.setStyle("-fx-text-fill: red;");
                    break;
                    
                  case OK:
                    this.setStyle("-fx-text-fill: black;");
                    break;
                    
                  case SUSPICIOUS:
                    this.setStyle("-fx-text-fill: orange;");
                    break;
                  }
                  setText(cf.format(moneyPlusStatus.getMoney()));
                 }
              }                            
            };

            return cell;
          }),
      new EObjectTableColumnDescriptorBasic<InvoiceAndProperty>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceAndPropertyItem_Remarks(), "Remarks", false, true),
      new EObjectTableColumnDescriptorBasic<InvoiceAndProperty>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceAndPropertyItem_FromDate(), "From date", false, true),
      new EObjectTableColumnDescriptorBasic<InvoiceAndProperty>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceAndPropertyItem_UntilDate(), "Until date", false, true),
      new EObjectTableColumnDescriptorBasic<InvoiceAndProperty>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceAndPropertyItem_Archive(), "Archived", false, true)
      );
  
  @SuppressWarnings("serial")
  private static Map<Operation, TableRowOperationDescriptor<InvoiceAndProperty>> rowOperations = new HashMap<>() {
    {
      put(Operation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete"));
    }
  };
  
  public InvoicesAndPropertieseDescriptor(CustomizationFx customization) {
    super("There are no invoices and/or properties to show", null, columnDescriptors, rowOperations);
  }
  
}


class ListCellWithContextMenu2 extends ListCell<FileReference> {
  private static final Logger LOGGER = Logger.getLogger(ListCellWithContextMenu2.class.getName());
  
  public ListCellWithContextMenu2(ListView<FileReference> listView) {
  }
  
  @Override
  public void updateItem(FileReference item, boolean empty) {
    if (empty) {
      setText("");
    } else {
      // item text
      String string = item.getTitle();
      if ((string == null)  &&  (item.getFile() != null)) {
        File file = new File(item.getFile());
        string = file.getName();
      }
      
      setText(string);
            
      // item context menu
      ContextMenu contextMenu = new ContextMenu();
      MenuItem menuItem = new MenuItem("Open document");
      LOGGER.severe("NOT IMPLEMENTED");
//      menuItem.setOnAction(e -> PropertiesWindow.openDocument(item));  // TOdo Implement via service
      contextMenu.getItems().add(menuItem);
      setContextMenu(contextMenu);

    }
  }
}

