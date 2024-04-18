package goedegep.invandprop.app.guifx;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.appgen.Operation;
import goedegep.appgen.TableRowOperationDescriptor;
import goedegep.invandprop.app.PropertyStringConverter;
import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.Invoice;
import goedegep.invandprop.model.InvoiceItem;
import goedegep.invandprop.model.InvoicesAndProperties;
import goedegep.invandprop.model.Property;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorCustom;
import goedegep.jfx.eobjecttable.EObjectTableControlPanel;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.jfx.stringconverters.PgCurrencyStringConverter;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;
import goedegep.util.money.PgCurrencyPlusStatus;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * This class is a window to show information on the invoices which are part of an InvoicesAndProperties data structure.
 */
public class InvoicesWindow extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger     LOGGER = Logger.getLogger(InvoicesWindow.class.getName());
  private static final String     WINDOW_TITLE = "Invoices";
  
  private static final InvAndPropPackage INVOICES_AND_PROPERTIES_PACKAGE = InvAndPropPackage.eINSTANCE;
  
  private CustomizationFx customization = null;
  private InvoicesAndProperties invoicesAndProperties = null;
  private InvoicesAndPropertiesMenuWindow invoicesAndPropertiesMenuWindow;
  private ComponentFactoryFx componentFactory = null;
  
  private EObjectTableControlPanel eObjectTableControlPanel;
  private Button editButton;
  private EObjectTable<Invoice> invoicesTable;
  private EObjectTable<InvoiceItem> invoiceItemsTable;

  /**
   * Constructor
   * 
   * @param customization GUI customization.
   * @param invoicesAndProperties the invoices and properties from which the invoices are shown.
   * @param invoicesAndPropertiesMenuWindow 
   */
  public InvoicesWindow(CustomizationFx customization, InvoicesAndProperties invoicesAndProperties, InvoicesAndPropertiesMenuWindow invoicesAndPropertiesMenuWindow) {
    super(WINDOW_TITLE, customization);
    
    this.customization = customization;
    this.invoicesAndProperties = invoicesAndProperties;
    this.invoicesAndPropertiesMenuWindow = invoicesAndPropertiesMenuWindow;
    
    componentFactory = customization.getComponentFactoryFx();
    
    createControls();
    createGUI();
    
    eObjectTableControlPanel.filterTextProperty().addListener((observable, oldValue, newValue) -> invoicesTable.setFilterExpression(newValue, null));
    
    invoicesTable.addObjectSelectionListener((source, invoice) -> {
        if (invoice != null) {
          invoiceItemsTable.setObjects(invoice, INVOICES_AND_PROPERTIES_PACKAGE.getInvoice_InvoiceItems());
        } else {
          invoiceItemsTable.setObjects(null, null);
        }
    });
    
    show();
  }

  public void selectAndShow(Invoice invoice) {
    invoicesTable.getSelectionModel().select(invoice);
    invoicesTable.scrollTo(invoice);
  }
  
  private void createControls() {
    createInvoicesTable();
  }
  
  /**
   * Create the GUI.
   * <p>
   * The window consists of the following parts:
   * <ul>
   * <li>Top part is the invoices table with a control panel above it</li>
   * <li>Bottom part is an invoice items table.</li>
   * </ul>
   */
  private void createGUI() {
    VBox rootLayout = componentFactory.createVBox();
    
    eObjectTableControlPanel = new EObjectTableControlPanel(customization);
    HBox controlsBox = componentFactory.createHBox(12.0);
    controlsBox.getChildren().add(eObjectTableControlPanel);
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    controlsBox.getChildren().add(spacer);
    
    HBox buttonsBox = componentFactory.createHBox(12.0, 12.0);
    
    editButton = componentFactory.createButton("Edit Invoice", "click to edit the details of the selected invoice");
    invoicesTable.addObjectSelectionListener((object, invoice) -> updateEditButton(invoice));
    editButton.setOnAction(e -> showInvoiceEditor());
    buttonsBox.getChildren().add(editButton);
    
    Button newInvoiceButton = componentFactory.createButton("New Invoice and Property", "click to enter the details of a new invoice and property");
    newInvoiceButton.setOnAction(e -> showNewInvoiceAndPropertyEditor());
    updateEditButton(null);
    buttonsBox.getChildren().add(newInvoiceButton);
    controlsBox.getChildren().add(buttonsBox);
    
    rootLayout.getChildren().add(controlsBox);
    
    SplitPane tablesPane = new SplitPane();
    tablesPane.setOrientation(Orientation.VERTICAL);
    tablesPane.setDividerPositions(0.8);
    tablesPane.getItems().add(invoicesTable);
    tablesPane.getItems().add(createInvoiceItemsTable());
    rootLayout.getChildren().add(tablesPane);
    
    setScene(new Scene(rootLayout, 1300, 700));
  }
  
  private void updateEditButton(Invoice invoice) {
    if (invoice != null) {
      if (invoice.isSetPurchase()) {
        editButton.setText("Edit Invoice and Property");
      } else {
        editButton.setText("Edit Invoice");
      }
      editButton.setDisable(false);
    } else {
      editButton.setText("Edit Invoice");
      editButton.setDisable(true);
    }
  }
  
  /**
   * Open de invoice and property editor to edit the currently selected invoice.
   */
  private void showInvoiceEditor() {
    Invoice invoice = invoicesTable.getSelectedObject();
    if (invoice != null) {
      new InvoiceAndPropertyEditor(customization, invoicesAndProperties).runEditor().setObject(invoice);
    }
  }
  
  /**
   * Open de the invoice and property editor to create a new invoice.
   */
  private void showNewInvoiceAndPropertyEditor() {
    new InvoiceAndPropertyEditor(customization, invoicesAndProperties).runEditor();;
  }

  /**
   * Create the invoices table.
   * 
   * @return the created familiesTable
   */
  private EObjectTable<Invoice> createInvoicesTable() {
    invoicesTable = new EObjectTable<Invoice>(customization, INVOICES_AND_PROPERTIES_PACKAGE.getInvoice(), new InvoicesTableDescriptor(invoicesAndPropertiesMenuWindow), invoicesAndProperties.getInvoices(),INVOICES_AND_PROPERTIES_PACKAGE.getInvoices_Invoices());
        
    return invoicesTable;
  }
  
  /**
   * Create the invoices table.
   * 
   * @return the created familiesTable
   */
  private EObjectTable<InvoiceItem> createInvoiceItemsTable() {
    invoiceItemsTable = new EObjectTable<InvoiceItem>(customization, INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceItem(), new InvoiceItemsTableDescriptor(), null);
        
    return invoiceItemsTable;
  }
}


/**
 * This class provides the descriptor for the invoices table.
 */
class InvoicesTableDescriptor extends EObjectTableDescriptor<Invoice> {
  private static final Logger LOGGER = Logger.getLogger(InvoicesTableDescriptor.class.getName());
  private static InvAndPropPackage INVOICES_AND_PROPERTIES_PACKAGE = InvAndPropPackage.eINSTANCE;
  
  private static EObjectTableColumnDescriptorCustom<Invoice> purchaseColumn = new EObjectTableColumnDescriptorCustom<>(INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Purchase(), "Purchase", 300, false, true, null);

  
  private static List<EObjectTableColumnDescriptorAbstract<Invoice>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<Invoice>>of(
      new EObjectTableColumnDescriptorBasic<Invoice>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoice_Date(), "Date", true, true),
      new EObjectTableColumnDescriptorBasic<Invoice>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoice_Company(), "Company", true, true),
      new EObjectTableColumnDescriptorBasic<Invoice>(INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Description(), "Description", true, true),
      new EObjectTableColumnDescriptorCustom<Invoice>(null,
          "Amount", 130, true, true, invoice -> {
            TableCell<Invoice, Object> cell = new TableCell<>() {
              PgCurrencyFormat cf = new PgCurrencyFormat();
              TextField textField = null;

              @Override
              protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                
                
                if(empty || (item == null)) {
                  setText(null);
                  setGraphic(null);
                } else {
                  setText(null);
                  setGraphic(null);
                  Invoice invoice = (Invoice) item;
                  PgCurrencyPlusStatus moneyPlusStatus;
                  PgCurrency invoiceAmount = invoice.getAmount();
                  PgCurrency invoiceItemsAmount = invoice.getTotalAmountInvoiceItems();
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
              
              @Override
              public void startEdit() {
                LOGGER.severe("=> ");
                super.startEdit();
                textField = new TextField(getText());
                this.setGraphic(textField);
                textField.setOnAction(e -> this.commitEdit(textField.getText()));
              }
              
              @Override
              public void commitEdit(Object newValue) {
                LOGGER.severe("=> newvalue: " + newValue);
                Invoice invoice = (Invoice) getItem();
                LOGGER.severe("invoice: " + invoice.toString());
                PgCurrency money;
                try {
                  money = cf.parse((String) newValue);
                  invoice.setAmount(money);
                } catch (ParseException e) {
                  e.printStackTrace();
                }
                money = invoice.getAmount();
                setGraphic(null);
                
                setText(cf.format(money));
              }
            };

            return cell;
          }),
      new EObjectTableColumnDescriptorBasic<Invoice>(INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Remarks(), "Remarks", true, true),
      purchaseColumn
      );
  
  @SuppressWarnings("serial")
  private static Map<Operation, TableRowOperationDescriptor<Invoice>> rowOperations = new HashMap<>() {
    {
    put(Operation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete"));
    }
  };
  
  public InvoicesTableDescriptor(InvoicesAndPropertiesMenuWindow invoicesAndPropertiesMenuWindow) {
    
    super("There are no invoices to show", null, columnDescriptors, rowOperations);
    purchaseColumn.setCellFactory(new PurchaseCellFactory(invoicesAndPropertiesMenuWindow));
  }
  
}

class PurchaseCellFactory implements Callback<TableColumn<Invoice, Object>, TableCell<Invoice, Object>>  {
  private InvoicesAndPropertiesMenuWindow invoicesAndPropertiesMenuWindow;
  
  public PurchaseCellFactory(InvoicesAndPropertiesMenuWindow invoicesAndPropertiesMenuWindow) {
    this.invoicesAndPropertiesMenuWindow = invoicesAndPropertiesMenuWindow;
  }

  @Override
  public TableCell<Invoice, Object> call(TableColumn<Invoice, Object> param) {
    return new PurchaseCell(invoicesAndPropertiesMenuWindow);
  }
  
}

class PurchaseCell extends TextFieldTableCell<Invoice, Object> {
  private static final Logger LOGGER = Logger.getLogger(PurchaseCell.class.getName());
  
  private PropertyStringConverter stringConverter = new PropertyStringConverter();
  private Property property;

  public PurchaseCell(InvoicesAndPropertiesMenuWindow invoicesAndPropertiesMenuWindow) {
    setOnMouseClicked(e -> {
      PropertiesWindow propertiesWindow = invoicesAndPropertiesMenuWindow.getPropertiesWindow();
      propertiesWindow.selectAndShow(property);
      propertiesWindow.show();
    });
  }

  @Override
  public void updateItem(Object item, boolean empty) {
    setText(null);

    if (item != null) {
      property = (Property) item;
      setText(stringConverter.toString(property));
    } else {
      property = null;
    }
    
  }

}


/**
 * This class provides the descriptor for the invoice items table.
 */
class InvoiceItemsTableDescriptor extends EObjectTableDescriptor<InvoiceItem> {
  private static InvAndPropPackage INVOICES_AND_PROPERTIES_PACKAGE = InvAndPropPackage.eINSTANCE;
  
  private static List<EObjectTableColumnDescriptorAbstract<InvoiceItem>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<InvoiceItem>>of(
      new EObjectTableColumnDescriptorBasic<InvoiceItem>(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceItem_NumberOfItems(), "Number", true, true),
      new EObjectTableColumnDescriptorBasic<InvoiceItem>(INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Description(), "Description", 300, true, true),
      new EObjectTableColumnDescriptorBasic<InvoiceItem>(INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Amount(), "Amount", 130, true, true, new PgCurrencyStringConverter()),
      new EObjectTableColumnDescriptorBasic<InvoiceItem>(INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Remarks(), "Remarks", true, true),
      new EObjectTableColumnDescriptorBasic<InvoiceItem>(INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Purchase(), "Property", 300, true, true, new PropertyStringConverter())
      );
  
  @SuppressWarnings("serial")
  private static Map<Operation, TableRowOperationDescriptor<InvoiceItem>> rowOperations = new HashMap<>() {
    {
    put(Operation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete"));
    }
  };
  
  public InvoiceItemsTableDescriptor() {
    super("There are no items to show", null, columnDescriptors, rowOperations);
  }
  
 
}
