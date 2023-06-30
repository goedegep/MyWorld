package goedegep.invandprop.app.guifx;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import goedegep.invandprop.app.InvoicesAndPropertiesRegistry;
import goedegep.invandprop.model.Expenditure;
import goedegep.invandprop.model.InvAndPropFactory;
import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.Invoice;
import goedegep.invandprop.model.InvoiceItem;
import goedegep.invandprop.model.InvoicesAndProperties;
import goedegep.invandprop.model.Property;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecteditor.EObjectAttributeEditDescriptor;
import goedegep.jfx.eobjecteditor.EObjectEditor;
import goedegep.jfx.eobjecteditor.EObjectEditorDescriptor;
import goedegep.jfx.objectcontrols.ObjectControl;
import goedegep.jfx.objectcontrols.ObjectControlBoolean;
import goedegep.jfx.objectcontrols.ObjectControlCurrency;
import goedegep.jfx.objectcontrols.ObjectControlFileSelecter;
import goedegep.jfx.objectcontrols.ObjectControlFlexDate;
import goedegep.jfx.objectcontrols.ObjectControlGroup;
import goedegep.jfx.objectcontrols.ObjectControlString;
import goedegep.jfx.objecteditor.ObjectEditorAbstract;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesFactory;
import goedegep.util.PgUtilities;
import goedegep.util.money.PgCurrency;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * This class provides an editor for an invoice and the related property.
 */
public class InvoiceAndPropertyEditor extends ObjectEditorAbstract {
  private static final Logger  LOGGER = Logger.getLogger(InvoiceItemPanel.class.getName());
  private static final String WINDOW_TITLE = "New invoice and property";
  private static final InvAndPropFactory INVOICES_AND_PROPERTIES_FACTORY = InvAndPropFactory.eINSTANCE;
  private static final InvAndPropPackage INVOICES_AND_PROPERTIES_PACKAGE = InvAndPropPackage.eINSTANCE;

  /**
   * The GUI customization
   */
  private CustomizationFx customization;
    
  /**
   * The invoices and properties to which new invoices and properties will be added.
   */
  private InvoicesAndProperties invoicesAndProperties;
  
  /**
   * Factory used to create all GUI elements
   */
  private ComponentFactoryFx componentFactory;
  
  /**
   * ObjectControlGroup used to check the validity of the invoice controls.
   */
  private ObjectControlGroup invoiceObjectControlGroup;
  
  /**
   * ObjectControlGroup used to check the validity of the property controls.
   */
  private ObjectControlGroup propertyObjectControlGroup;
  
  /**
   * Descriptor used for the controls of the invoice part of this editor.
   */
  private EObjectEditorDescriptor invoiceEditorDescriptor;
  
  /**
   * Descriptor used for the controls of the property part of this editor.
   */
  private EObjectEditorDescriptor propertyEditorDescriptor;
  
  /**
   * List of panels, one for each invoice item.
   */
  private ObservableList<InvoiceItemPanel> invoiceItemPanels;
  
  /**
   * The box which contains the invoice item panels.
   */
  private VBox invoiceItemsVBox;
  
  
  /*
   * References to the Invoice ObjectInput controls
   */
  private ObjectControlFlexDate invoiceDateObjectInput;
  private ObjectControlString invoiceCompanyObjectInput;
  private ObjectControlString invoiceDescriptionObjectInput;
  private ObjectControlCurrency invoiceAmountObjectInput;
  private ObjectControlString invoiceRemarksObjectInput;
  private ObjectControlBoolean invoiceDescriptionFromPropertyObjectInput;
  
  /*
   * References to the Property ObjectInput controls
   */
  private ObjectControlString propertyDescriptionObjectInput;
  private ObjectControlString propertyBrandObjectInput;
  private ObjectControlString propertyTypeObjectInput;
  private ObjectControlString propertySerialNumberObjectInput;
  private ObjectControlString propertyRemarksObjectInput;
  private ObjectControlFlexDate propertyFromDateObjectInput;
  private ObjectControlFlexDate propertyUntilDateObjectInput;
  private ObjectControlBoolean propertyArchiveObjectInput;
  
  private Map<InvoiceItemPanel, ObjectControlCurrency> amountObjectInputs = new HashMap<>();
  private Map<ObjectControlCurrency, InvalidationListener> amountObjectInputListeners = new HashMap<>();
  
  private VBox documentReferencesVBox;
  
  /**
   * List of panels, one for each document reference.
   */
  private ObservableList<FileReferencePanel> documentReferencePanels;
  
  private VBox pictureReferencesVBox;
  
  /**
   * List of panels, one for each picture reference.
   */
  private ObservableList<FileReferencePanel> pictureReferencePanels;
  
  private HBox pictureViewPanel;
  
  /**
   * Current invoice and property. If both are null, we're in 'new mode', otherwise we're in 'edit mode'.
   */
  private Invoice invoice;
  private Property property;
  
  /**
   * Button to create or update an invoice.
   */
  private Button createOrUpdateInvoiceButton;
  
  /**
   * Button to create or update a property.
   */
  private Button createOrUpdatePropertyButton;
  
  /**
   * Button to create or update an invoice and property.
   */
  private Button createOrUpdateInvoiceAndPropertyButton;
  
//  private EditType editType = null;

  
  /**
   * Constructor for creating a new invoice and/or property.
  * 
   * @param Customization the GUI customization.
   * @param invoicesAndProperties The invoices and properties to which new invoices and properties will be added.
   */
  public InvoiceAndPropertyEditor(CustomizationFx customization, InvoicesAndProperties invoicesAndProperties) {
    this(Objects.requireNonNull(customization, "argument ‘customization’ must not be null"), Objects.requireNonNull(invoicesAndProperties, "argument ‘invoicesAndProperties’ must not be null"), null, null);
  }

  /**
   * Constructor for editing an invoice (with a probably related property).
   * 
   * @param Customization the GUI customization.
   * @param invoice An invoice to edit
   */
  public InvoiceAndPropertyEditor(CustomizationFx customization, Invoice invoice) {
    this(Objects.requireNonNull(customization, "argument ‘customization’ must not be null"), null, Objects.requireNonNull(invoice, "argument ‘invoice’ must not be null"), null);
  }

  /**
   * Constructor for editing a property (with a probably related invoice).
   * 
   * @param Customization the GUI customization.
   * @param property A property to edit
   */
  public InvoiceAndPropertyEditor(CustomizationFx customization, Property property) {
    this(Objects.requireNonNull(customization, "argument ‘customization’ must not be null"), null, null, Objects.requireNonNull(property, "argument ‘property’ must not be null"));
  }
  
  /**
   * Constructor.
   * <p>
   * Of the parameters <code>invoicesAndProperties</code>, <code>invoice</code> and <code>property</code> exactly one parameter shall
   * not be null.<br/>
   * For a new invoice and/or property, <code>invoicesAndProperties</code> shall not be null.<br/>
   * To edit an invoice (with a probably related property), the <code>invoice</code> shall not be null.<br/>
   * To edit a property (with a probably related invoice), the <code>property</code> shall not be null.<br/>
   * 
   * @param Customization the GUI customization.
   * @param invoicesAndProperties The invoices and properties to which new invoices and properties will be added.
   * @param invoice An invoice to edit
   * @param property A property to edit
   */
  private InvoiceAndPropertyEditor(CustomizationFx customization, InvoicesAndProperties invoicesAndProperties, Invoice invoice, Property property) {
    super(customization, WINDOW_TITLE);
            
    this.customization = customization;
    this.invoicesAndProperties = invoicesAndProperties;
    
    componentFactory = customization.getComponentFactoryFx();
    
    invoiceEditorDescriptor = new InvoiceEditorDescriptor(customization);
    propertyEditorDescriptor = new PropertyEditorDescriptor(customization);
    
    // Get the references to the ObjectInputs for the invoice.
    invoiceDateObjectInput = (ObjectControlFlexDate) invoiceEditorDescriptor.getEObjectAttributeEditDescriptor(INVOICES_AND_PROPERTIES_PACKAGE.getInvoice_Date().getName()).getObjectControl();
    invoiceCompanyObjectInput = (ObjectControlString) invoiceEditorDescriptor.getEObjectAttributeEditDescriptor(INVOICES_AND_PROPERTIES_PACKAGE.getInvoice_Company().getName()).getObjectControl();
    invoiceDescriptionObjectInput = (ObjectControlString) invoiceEditorDescriptor.getEObjectAttributeEditDescriptor(INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Description().getName()).getObjectControl();
    invoiceAmountObjectInput = (ObjectControlCurrency) invoiceEditorDescriptor.getEObjectAttributeEditDescriptor(INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Amount().getName()).getObjectControl();
    invoiceRemarksObjectInput = (ObjectControlString) invoiceEditorDescriptor.getEObjectAttributeEditDescriptor(INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Remarks().getName()).getObjectControl();
    invoiceDescriptionFromPropertyObjectInput = (ObjectControlBoolean) invoiceEditorDescriptor.getEObjectAttributeEditDescriptor(INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_DescriptionFromProperty().getName()).getObjectControl();
    
    // Get the references to the ObjectInputs for the property
    propertyDescriptionObjectInput = (ObjectControlString) propertyEditorDescriptor.getEObjectAttributeEditDescriptor(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Description().getName()).getObjectControl();
    propertyBrandObjectInput = (ObjectControlString) propertyEditorDescriptor.getEObjectAttributeEditDescriptor(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Brand().getName()).getObjectControl();
    propertyTypeObjectInput = (ObjectControlString) propertyEditorDescriptor.getEObjectAttributeEditDescriptor(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Type().getName()).getObjectControl();
    propertySerialNumberObjectInput = (ObjectControlString) propertyEditorDescriptor.getEObjectAttributeEditDescriptor(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_SerialNumber().getName()).getObjectControl();
    propertyRemarksObjectInput = (ObjectControlString) propertyEditorDescriptor.getEObjectAttributeEditDescriptor(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Remarks().getName()).getObjectControl();
    propertyFromDateObjectInput = (ObjectControlFlexDate) propertyEditorDescriptor.getEObjectAttributeEditDescriptor(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_FromDate().getName()).getObjectControl();
    propertyUntilDateObjectInput = (ObjectControlFlexDate) propertyEditorDescriptor.getEObjectAttributeEditDescriptor(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_UntilDate().getName()).getObjectControl();
    propertyArchiveObjectInput = (ObjectControlBoolean) propertyEditorDescriptor.getEObjectAttributeEditDescriptor(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Archive().getName()).getObjectControl();    
    
    createObjectControlGroups();

    createGUI();
    
    if (invoice != null) {
      this.invoice = invoice;
      if (invoice.isSetPurchase()) {
        this.property = invoice.getPurchase();
      }
    }
    
    if (property != null) {
      this.property = property;
      Expenditure expenditure = property.getExpenditure();
      if (expenditure != null) {
        if (expenditure instanceof Invoice) {
          this.invoice = (Invoice) expenditure;
        } else {
          this.invoice = (Invoice) expenditure.eContainer();
        }
      }
    }
   
    if (this.invoice != null) {
      fillControlsFromInvoice(this.invoice);
    }
    
    if (this.property != null) {
      fillControlsFromProperty(this.property);
    }
    
    invoiceObjectControlGroup.addListener(observable -> handleChanges(observable));
    propertyObjectControlGroup.addListener(observable -> handleChanges(observable));
    
    handleInvoiceDescriptionType();
    handleChanges(null);
    show();
  }
  
  /**
   * Handle changes in any of the controls.
   * 
   * @param observable
   */
  private void handleChanges(Observable observable) {
    LOGGER.info("=> " + observable);
    
//    EditType newEditType = determineEditType();
//    if (newEditType != editType) {
//      editType = newEditType;
//      updateCreateOrUpdateButton();
//    }
    
    handleInvoiceDescriptionType();
    updateInvoiceDescription();
    
    updateCreateOrUpdateInvoiceButton();
    updateCreateOrUpdatePropertyButton();
    updateCreateOrUpdateInvoiceAndPropertyButton();
    createOrUpdateInvoiceButton.setDisable(!isInvoiceInputValid());
    createOrUpdatePropertyButton.setDisable(!isPropertyInputValid());
    createOrUpdateInvoiceAndPropertyButton.setDisable(!(isInvoiceInputValid()  &&  isPropertyInputValid()));
  }
  
  /**
   * Handle the edit mode for the <code>createOrUpdateInvoiceButton</code>.
   * <p>
   * Update the text and tooltip of the <code>createOrUpdateInvoiceButton</code> depending on whether we are in 'new' or 'update' mode.
   */
  private void updateCreateOrUpdateInvoiceButton() {
    if (invoice == null) {
      createOrUpdateInvoiceButton.setText("Create invoice");
      createOrUpdateInvoiceButton.setTooltip(new Tooltip("Create a new invoice based on the entered values"));
    } else {
      createOrUpdateInvoiceButton.setText("Update invoice");
      createOrUpdateInvoiceButton.setTooltip(new Tooltip("Update the invoice based on the entered values"));
    }
  }
  
  /**
   * Handle the edit mode for the <code>createOrUpdatePropertyButton</code>.
   * <p>
   * Update the text and tooltip of the <code>createOrUpdatePropertyButton</code> depending on whether we are in 'new' or 'update' mode.
   */
  private void updateCreateOrUpdatePropertyButton() {
    if (property == null) {
      createOrUpdatePropertyButton.setText("Create property");
      createOrUpdatePropertyButton.setTooltip(new Tooltip("Create a new property based on the entered values"));
    } else {
      createOrUpdatePropertyButton.setText("Update property");
      createOrUpdatePropertyButton.setTooltip(new Tooltip("Update the property based on the entered values"));
    }
  }
  
  /**
   * Handle the edit mode for the <code>createOrUpdateInvoiceAndPropertyButton</code>.
   * <p>
   * Update the text and tooltip of the <code>createOrUpdateInvoiceAndPropertyButton</code> depending on whether we are in 'new' or 'update' mode.
   */
  private void updateCreateOrUpdateInvoiceAndPropertyButton() {
    if ((invoice == null) && (property == null)) {
      createOrUpdateInvoiceAndPropertyButton.setText("Create invoice and property");
      createOrUpdateInvoiceAndPropertyButton.setTooltip(new Tooltip("Create a new invoice and property based on the entered values"));
    } else {
      createOrUpdateInvoiceAndPropertyButton.setText("Update invoice and property");
      createOrUpdateInvoiceAndPropertyButton.setTooltip(new Tooltip("Update the invoice and/or property based on the entered values"));
    }
  }
  
//  private boolean isInputValid() {
//    boolean inputValid = false;
//    
//    switch(editType) {
//    case NONE:
//      inputValid = false;
//      break;
//      
//    case INVOICE:
//      inputValid = isInvoiceInputValid();
//      break;
//      
//    case PROPERTY:
//      inputValid = isPropertyInputValid();
//      break;
//      
//    case INVOICE_AND_PROPERTY:
//      inputValid = isInvoiceInputValid()  &&  isPropertyInputValid();
//      break;
//    }
//    
//    return inputValid;
//  }
  
  private boolean isInvoiceInputValid() {
    return invoiceObjectControlGroup.isValid().get();
  }
  
  private boolean isPropertyInputValid() {
    return propertyObjectControlGroup.isValid().get();
  }
  
//  /**
//   * Check whether we are in 'new' mode.
//   * <p>
//   * We are in 'new' mode if both <code>invoice</code> and <code>property</code> are null.
//   * 
//   * @return
//   */
//  private boolean inNewMode() {
//    return (invoice == null)  &&  (property == null);
//  }
  
//  /**
//   * Determine the current edit type.
//   * <p>
//   * The edit type is defined as follows:<br/>
//   * Nothing is filled-in -> UNDEFINED<br/>
//   * Only invoice fields filled-in -> INVOICE<br/>
//   * Only property fields filled-in -> PROPERTY<br/>
//   * Invoice and property fields filled-in -> INVOICE_AND_PROPERTY
//   * 
//   * @return the newly determined <code>EditType</code>.
//   */
//  private EditType determineEditType() {
//    EditType editType = null;
//    
//    boolean anyInvoiceFieldFilledIn = isAnyInvoiceFieldFilledIn();
//    boolean anyPropertyFieldFilledIn = isAnyPropertyFieldFilledIn();
//    
//    if (!anyInvoiceFieldFilledIn  && !anyPropertyFieldFilledIn) {
//      editType = EditType.NONE;
//    } else if (anyInvoiceFieldFilledIn  && !anyPropertyFieldFilledIn) {
//      editType = EditType.INVOICE;
//    } else if (!anyInvoiceFieldFilledIn  && anyPropertyFieldFilledIn) {
//      editType = EditType.PROPERTY;
//    } else {
//      editType = EditType.INVOICE_AND_PROPERTY;
//    }
//    
//    LOGGER.info("<= " + editType);
//    return editType;
//  }

  /**
   * Check whether any invoice control is filled in.
   * 
   * @return
   */
  private boolean isAnyInvoiceFieldFilledIn() {
    boolean anyInvoiceFieldFilledIn = false;
    
    if (invoiceDateObjectInput.ocIsFilledIn()  ||
        invoiceCompanyObjectInput.ocIsFilledIn()  ||
        invoiceDescriptionObjectInput.ocIsFilledIn()  ||
        invoiceAmountObjectInput.ocIsFilledIn()  ||
        invoiceRemarksObjectInput.ocIsFilledIn()) {
      anyInvoiceFieldFilledIn = true;
    }
    
    LOGGER.info("<= " + anyInvoiceFieldFilledIn);
    return anyInvoiceFieldFilledIn;
  }

  private boolean isAnyPropertyFieldFilledIn() {
    boolean anyPropertyFieldFilledIn = false;
    
    if (propertyDescriptionObjectInput.ocIsFilledIn()  ||
        propertyBrandObjectInput.ocIsFilledIn()  ||
        propertyTypeObjectInput.ocIsFilledIn()  ||
        propertySerialNumberObjectInput.ocIsFilledIn()  ||
        propertyRemarksObjectInput.ocIsFilledIn()  ||
        propertyFromDateObjectInput.ocIsFilledIn()  ||
        propertyUntilDateObjectInput.ocIsFilledIn()) {
      anyPropertyFieldFilledIn = true;
    }
    
    return anyPropertyFieldFilledIn;
    
  }

  /**
   * Fill the controls for the invoice with the values of an invoice.
   * 
   * @param invoice The {@code Invoice} to fill the controls from.
   */
  private void fillControlsFromInvoice(Invoice invoice) {
    LOGGER.info("=> invoice=" + invoice);
    
    if (invoice.isSetDate()) {
      invoiceDateObjectInput.ocSetValue(invoice.getDate());
    }
    
    if (invoice.isSetCompany()) {
      invoiceCompanyObjectInput.ocSetValue(invoice.getCompany());
    }
    
    if (!invoice.isDescriptionFromProperty()) {
      invoiceDescriptionObjectInput.ocSetValue(invoice.getDescription());
    }
    
    if (invoice.isSetAmount()) {
      invoiceAmountObjectInput.ocSetValue(invoice.getAmount());
    }
    
    if (invoice.isSetRemarks()) {
      invoiceRemarksObjectInput.ocSetValue(invoice.getRemarks());
    }
    
    invoiceDescriptionFromPropertyObjectInput.ocSetValue(invoice.isDescriptionFromProperty());
    
    createInvoiceItemPanelsFromInvoice(invoice);
    
  }
  
  private void createInvoiceItemPanelsFromInvoice(Invoice invoice) {
    for (InvoiceItem invoiceItem: invoice.getInvoiceItems()) {
      InvoiceItemPanel invoiceItemPanel = createNewInvoiceItemPanel(false);
      
      invoiceItemPanel.getNumberOfItemsObjectInput().ocSetValue(invoiceItem.getNumberOfItems());
      
      if (invoiceItem.isSetDescription()) {
        invoiceItemPanel.getDescriptionObjectInput().ocSetValue(invoiceItem.getDescription());
      }
      
      if (invoiceItem.isSetAmount()) {
        invoiceItemPanel.getAmountObjectInput().ocSetValue(invoiceItem.getAmount());
      }
      
//      if (invoiceItem.isSetRemarks()) {
        invoiceItemPanel.getRemarksObjectInput().ocSetValue(invoiceItem.getRemarks());
//      }
      
    }
    
  }
  
  private void updateInvoiceFromControls(Invoice invoice) {

    for (EObjectAttributeEditDescriptor eObjectAttributeEditDescriptor: invoiceEditorDescriptor.getEObjectAttributeEditDescriptors()) {
      ObjectControl<?> objectInput = eObjectAttributeEditDescriptor.getObjectControl();
      if (objectInput.ocIsFilledIn()) {
        Object value;
        value = objectInput.ocGetValue();
        invoice.eSet(eObjectAttributeEditDescriptor.getStructuralFeature(), value);
      }
    }

    updateInvoiceItemsFromInvoiceItemPanels(invoice);
  }
  
  private void updateInvoiceItemsFromInvoiceItemPanels(Invoice invoice) {
    // Check for any changes. If there are changes, recreate the complete list.
    boolean changes = false;
    
    if (invoice.getInvoiceItems().size() != invoiceItemPanels.size()) {
      changes = true;
    }
    
    if (!changes) {
      int index = 0;
      for (InvoiceItem invoiceItem: invoice.getInvoiceItems()) {
        InvoiceItemPanel invoiceItemPanel = invoiceItemPanels.get(index++);
        if (!PgUtilities.equals((Integer) invoiceItem.getNumberOfItems(), invoiceItemPanel.getNumberOfItemsObjectInput().ocGetValue())  ||
            !PgUtilities.equals(invoiceItem.getDescription(), invoiceItemPanel.getDescriptionObjectInput().ocGetValue())  ||
            !PgUtilities.equals(invoiceItem.getAmount(), invoiceItemPanel.getAmountObjectInput().ocGetValue())  ||
            !PgUtilities.equals(invoiceItem.getRemarks(), invoiceItemPanel.getRemarksObjectInput().ocGetValue())) {
          changes = true;
          LOGGER.severe("NumberOfItems: " + invoiceItem.getNumberOfItems() + ", " + invoiceItemPanel.getNumberOfItemsObjectInput().ocGetValue());
          LOGGER.severe("Description: " + invoiceItem.getDescription() + ", " + invoiceItemPanel.getDescriptionObjectInput().ocGetValue());
          LOGGER.severe("Amount: " + invoiceItem.getAmount() + ", " + invoiceItemPanel.getAmountObjectInput().ocGetValue());
          LOGGER.severe("Remarks: " + invoiceItem.getRemarks() + ", " + invoiceItemPanel.getRemarksObjectInput().ocGetValue());
          break;
        }
      }
    }
    
    if (changes) {
      List<InvoiceItem> invoiceItems = invoice.getInvoiceItems();
      invoiceItems.clear();
      
      for (InvoiceItemPanel invoiceItemPanel: invoiceItemPanels) {
        InvoiceItem invoiceItem = INVOICES_AND_PROPERTIES_FACTORY.createInvoiceItem();
        updateInvoiceItemFromInvoiceItemPanel(invoiceItem, invoiceItemPanel);
        invoiceItems.add(invoiceItem);
      }
    }
  }

  private void updateInvoiceItemFromInvoiceItemPanel(InvoiceItem invoiceItem, InvoiceItemPanel invoiceItemPanel) {
    if (invoiceItemPanel.getNumberOfItemsObjectInput().ocIsFilledIn()) {
      invoiceItem.setNumberOfItems(invoiceItemPanel.getNumberOfItemsObjectInput().ocGetValue());
    }
    
    if (invoiceItemPanel.getDescriptionObjectInput().ocIsFilledIn()) {
      invoiceItem.setDescription(invoiceItemPanel.getDescriptionObjectInput().ocGetValue());
    }
    
    if (invoiceItemPanel.getAmountObjectInput().ocIsFilledIn()) {
      invoiceItem.setAmount(invoiceItemPanel.getAmountObjectInput().ocGetValue());
    }
    
    if (invoiceItemPanel.getRemarksObjectInput().ocIsFilledIn()) {
      invoiceItem.setRemarks(invoiceItemPanel.getRemarksObjectInput().ocGetValue());
    }

  }

  private void updatePropertyFromControls(Property property) {
    
    for (EObjectAttributeEditDescriptor eObjectAttributeEditDescriptor: propertyEditorDescriptor.getEObjectAttributeEditDescriptors()) {
      ObjectControl<?> objectInput = (ObjectControl<?>) eObjectAttributeEditDescriptor.getObjectControl();
      if (objectInput.ocIsFilledIn()) {
        Object value;
        value = objectInput.ocGetValue();
        property.eSet(eObjectAttributeEditDescriptor.getStructuralFeature(), value);
      }
    }
    
    updateDocumentReferencesFromDocumentReferencePanels(property);
    updatePictureReferencesFromDocumentReferencePanels(property);
  }
  
  private void updateDocumentReferencesFromDocumentReferencePanels(Property property) {
    // Check for any changes. If there are changes, recreate the complete list.
    boolean changes = false;
    
    if (property.getDocuments().size() != documentReferencePanels.size()) {
      changes = true;
    }
    
    if (!changes) {
      int index = 0;
      for (FileReference fileReference: property.getDocuments()) {
        FileReferencePanel fileReferencePanel = documentReferencePanels.get(index++);
        if ((!fileReference.getFile().equals(fileReferencePanel.getFile()))  ||
            (!fileReference.getTitle().equals(fileReferencePanel.titleTextField().ocGetValue()))) {
          changes = true;
          break;
        }
      }
    }
    
    if (changes) {
      List<FileReference> fileReferences = property.getDocuments();
      fileReferences.clear();
      
      for (FileReferencePanel fileReferencePanel: documentReferencePanels) {
        FileReference fileReference = TypesFactory.eINSTANCE.createFileReference();
        updateFileReferenceFromFileReferencePanel(fileReference, fileReferencePanel);
        fileReferences.add(fileReference);
      }
    }
  }
  
  private void updatePictureReferencesFromDocumentReferencePanels(Property property) {
    // Check for any changes. If there are changes, recreate the complete list.
    boolean changes = false;
    
    if (property.getPictures().size() != pictureReferencePanels.size()) {
      changes = true;
    }
    
    if (!changes) {
      int index = 0;
      for (FileReference fileReference: property.getPictures()) {
        FileReferencePanel fileReferencePanel = pictureReferencePanels.get(index++);
        if ((!fileReference.getFile().equals(fileReferencePanel.getFile()))  ||
            (!fileReference.getTitle().equals(fileReferencePanel.titleTextField().ocGetValue()))) {
          changes = true;
          break;
        }
      }
    }
    
    if (changes) {
      List<FileReference> fileReferences = property.getPictures();
      fileReferences.clear();
      
      for (FileReferencePanel fileReferencePanel: pictureReferencePanels) {
        FileReference fileReference = TypesFactory.eINSTANCE.createFileReference();
        updateFileReferenceFromFileReferencePanel(fileReference, fileReferencePanel);
        fileReferences.add(fileReference);
      }
    }
  }

  private void updateFileReferenceFromFileReferencePanel(FileReference fileReference, FileReferencePanel fileReferencePanel) {
    if (fileReferencePanel.getFile() != null) {
      fileReference.setFile(stripBaseDirFromFilename(fileReferencePanel.getFile()));
    }

    if (fileReferencePanel.titleTextField().ocIsFilledIn()) {
      fileReference.setTitle(fileReferencePanel.titleTextField().ocGetValue());
    }    
  }

  /**
   * Fill the controls for the property with the values of a property.
   * 
   * @param property The {@code Property} to fill the control from.
   */
  private void fillControlsFromProperty(Property property) {
    if (property.isSetDescription()) {
      propertyDescriptionObjectInput.ocSetValue(property.getDescription());
    }
    
    if (property.isSetBrand()) {
      propertyBrandObjectInput.ocSetValue(property.getBrand());
    }
    
    if (property.isSetType()) {
      propertyTypeObjectInput.ocSetValue(property.getType());
    }
    
    if (property.isSetSerialNumber()) {
      propertySerialNumberObjectInput.ocSetValue(property.getSerialNumber());
    }
    
    if (property.isSetRemarks()) {
      propertyRemarksObjectInput.ocSetValue(property.getRemarks());
    }
    
    if (property.isSetFromDate()) {
      propertyFromDateObjectInput.ocSetValue(property.getFromDate());
    }
    
    if (property.isSetUntilDate()) {
      propertyUntilDateObjectInput.ocSetValue(property.getUntilDate());
    }
    
    propertyArchiveObjectInput.ocSetValue(property.isArchive());
    
    createDocumentsPanelsFromProperty(property);
    createPicturesPanelsFromProperty(property);
  }
  
  /**
   * Create document panels from the documents of a property.
   * <p>
   * For each document reference of the property a FileReferencePanel is created and
   * the content of the controls is set to the values of the document reference.
   * 
   * @param property The Property for which the picture panels are to be created.
   */
  private void createDocumentsPanelsFromProperty(Property property) {
    for (FileReference fileReference: property.getDocuments()) {
      FileReferencePanel fileReferencePanel = createNewDocumentReferencePanel(false);
      
      if (fileReference.isSetFile()) {
        fileReferencePanel.getFileSelecter().ocSetFilename(prependBaseDirToFilename(fileReference.getFile()));
      }
      
      if (fileReference.isSetTitle()) {
        fileReferencePanel.titleTextField().ocSetValue(fileReference.getTitle());
      }
      
    }
    
  }
  
  /**
   * Create picture panels from the pictures of a property.
   * <p>
   * For each picture reference of the property a FileReferencePanel is created and
   * the content of the controls is set to the values of the picture reference.
   * 
   * @param property The Property for which the picture panels are to be created.
   */
  private void createPicturesPanelsFromProperty(Property property) {
    for (FileReference fileReference: property.getPictures()) {
      FileReferencePanel fileReferencePanel = createNewPictureReferencePanel(false);
      
      if (fileReference.isSetFile()) {
        fileReferencePanel.getFileSelecter().ocSetFilename(prependBaseDirToFilename(fileReference.getFile()));
      }
      
      if (fileReference.isSetTitle()) {
        fileReferencePanel.titleTextField().ocSetValue(fileReference.getTitle());
      }
      
    }
    
  }

  /**
   * Create an ObjectInputContainer and add all controls for the invoice and property.<br/>
   * The controls for invoice items are added/deleted when needed.
   */
  private void createObjectControlGroups() {
    invoiceObjectControlGroup = new ObjectControlGroup();
    
    for (EObjectAttributeEditDescriptor eObjectAttributeEditDescriptor: invoiceEditorDescriptor.getEObjectAttributeEditDescriptors()) {
      invoiceObjectControlGroup.addObjectControl((ObjectControl<?>) eObjectAttributeEditDescriptor.getObjectControl());
    }
    
    propertyObjectControlGroup = new ObjectControlGroup();
    
    for (EObjectAttributeEditDescriptor eObjectAttributeEditDescriptor: propertyEditorDescriptor.getEObjectAttributeEditDescriptors()) {
      propertyObjectControlGroup.addObjectControl((ObjectControl<?>) eObjectAttributeEditDescriptor.getObjectControl());
    }
  }

  /**
   * Create the GUI.
   * <p>
   * Left is invoice: top is invoice details, below that invoice items.
   * Right is property: top is property details and picture, below that documents and pictures.
   */
  protected void createGUI() {
    VBox rootPane = componentFactory.createVBox();
    
    HBox invoiceAndPropertyHBox = componentFactory.createHBox(3.0, 3.0);
    
    VBox invoiceVBox = componentFactory.createVBox(0.0, 3.0);
    invoiceVBox.getChildren().addAll(createInvoicePanel(), createInvoiceItemsPanel());
    
    VBox propertyVBox = componentFactory.createVBox();
    propertyVBox.getChildren().addAll(createPropertyAndPicturePanel(), createDocumentsAndPicturesPanel());
    
    invoiceAndPropertyHBox.getChildren().addAll(invoiceVBox, propertyVBox);
    
    rootPane.getChildren().addAll(invoiceAndPropertyHBox, createButtonsBox());
    
    setScene(new Scene(rootPane));
    
  }

  /**
   * Create the panel with the basic controls for an invoice.
   * @return the invoice panel.
   */
  private Node createInvoicePanel() {
    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    gridPane.setBorder(componentFactory.getRectangularBorder());
    
    Label label = componentFactory.createStrongLabel("Invoice");
    gridPane.add(label, 0, 0);
    
    int rowIndex = 1;
    
    for (EObjectAttributeEditDescriptor eObjectAttributeEditDescriptor: invoiceEditorDescriptor.getEObjectAttributeEditDescriptors()) {
      addAttributeEditControlsToGrid(gridPane, rowIndex++, eObjectAttributeEditDescriptor);
    }
    
    return gridPane;
  }

  /**
   * Determine and handle the 'invoice description type'.
   * <p>
   * If invoiceDescriptionFromProperty is set, the invoice description is derived from the property. Therefore invoiceDescriptionObjectInput is disabled
   * and its value is set via updateInvoiceDescription().<br/>
   * Otherwise invoiceDescriptionObjectInput is a normal editable text field.
   */
  private void handleInvoiceDescriptionType() {
    LOGGER.info("=>");
    if (invoiceDescriptionFromPropertyObjectInput.ocGetValue()) {
      // Invoice description is derived from property.
      // Disable the description control and set is to the value derived from the property.
      invoiceDescriptionObjectInput.ocGetControl().setDisable(true);
      updateInvoiceDescription();
    } else {
      // Enable the description control, don't change the value.
      invoiceDescriptionObjectInput.ocGetControl().setDisable(false);
    }
    
  }
  
  /**
   * Update the invoice description, if invoiceDescriptionFromProperty is set.
   * <p>
   * The description is a concatenation of the description, the brand and the type of the property.
   */
  private void updateInvoiceDescription() {
    if (invoiceDescriptionFromPropertyObjectInput.ocGetValue()) {
      StringBuilder buf = new StringBuilder();
      boolean spaceNeeded = false;
      
      String value = propertyDescriptionObjectInput.ocGetValue();
      if (value !=  null) {
        value = value.trim();
        if (!value.isEmpty()) {
          buf.append(value);
          spaceNeeded = true;
        }
      }
      
      value = propertyBrandObjectInput.ocGetValue();
      if (value !=  null) {
        value = value.trim();
        if (!value.isEmpty()) {
          if (spaceNeeded) {
            buf.append(" ");
          }
          buf.append(value);
          spaceNeeded = true;
        }
      }
      
      value = propertyTypeObjectInput.ocGetValue();
      if (value !=  null) {
        value = value.trim();
        if (!value.isEmpty()) {
          if (spaceNeeded) {
            buf.append(" ");
          }
          buf.append(value);
          spaceNeeded = true;
        }
      }
      
      invoiceDescriptionObjectInput.ocSetValue(buf.toString());
    }
  }

  /**
   * Create the panel for the invoice items.<br/>
   * Initially the list of invoice items will be empty.
   * 
   * @return the invoice items panel.
   */
  private Node createInvoiceItemsPanel() {
    VBox invoiceItemsMainVBox = componentFactory.createVBox(12.0, 12.0);
    invoiceItemsMainVBox.setMinSize(300, 500);
    invoiceItemsMainVBox.setBorder(componentFactory.getRectangularBorder());
    
    Label label = componentFactory.createStrongLabel("Invoice items");
    invoiceItemsMainVBox.getChildren().add(label);
    
    invoiceItemsVBox = componentFactory.createVBox();
    invoiceItemPanels = FXCollections.observableArrayList();
    invoiceItemPanels.addListener(new ListChangeListener<InvoiceItemPanel>() {

      @Override
      public void onChanged(Change<? extends InvoiceItemPanel> c) {
        while (c.next()) {
          if (c.wasPermutated()) {
            // No action needed here
            // for (int i = c.getFrom(); i < c.getTo(); ++i) {
            //    //permutate
            // }
          } else if (c.wasUpdated()) {
            // update item
          } else {
            for (InvoiceItemPanel invoiceItemPanel: c.getRemoved()) {
              invoiceObjectControlGroup.removeObjectControlGroup(invoiceItemPanel.getObjectInputContainer());
              ObjectControlCurrency invoiceItemAmountObjectInput = amountObjectInputs.get(invoiceItemPanel);
              InvalidationListener invalidationListener = amountObjectInputListeners.remove(invoiceItemAmountObjectInput);
              invoiceItemAmountObjectInput.removeListener(invalidationListener);
              amountObjectInputs.remove(invoiceItemPanel);
            }
            for (InvoiceItemPanel invoiceItemPanel: c.getAddedSubList()) {
              invoiceObjectControlGroup.addObjectControlGroup(invoiceItemPanel.getObjectInputContainer());
              ObjectControlCurrency invoiceItemAmountObjectInput = invoiceItemPanel.getAmountObjectInput();
              InvalidationListener invalidationListener = new InvalidationListener()  {
                @Override
                public void invalidated(Observable observable) {
                  updateAmountObjectInput();
                }
              };
              invoiceItemAmountObjectInput.addListener(invalidationListener);
              amountObjectInputListeners.put(invoiceItemAmountObjectInput, invalidationListener);
              amountObjectInputs.put(invoiceItemPanel, invoiceItemAmountObjectInput);
            }
          }
        }
        
        updateInvoiceItemsPanel();
        updateAmountObjectInput();
        
      }
      
    });

    invoiceItemsMainVBox.getChildren().add(invoiceItemsVBox);
    
    Button newInvoiceItemButton = componentFactory.createButton("+ Add invoice item", "Add an item to the invoice");
    newInvoiceItemButton.setOnAction(e -> createNewInvoiceItemPanel(true));
    invoiceItemsMainVBox.getChildren().add(newInvoiceItemButton);
    
    return invoiceItemsMainVBox;
  }

  /**
   * Create a new invoice item panel.
   */
  private InvoiceItemPanel createNewInvoiceItemPanel(boolean expanded) {
    InvoiceItemPanel invoiceItemPanel = new InvoiceItemPanel(customization, invoiceItemPanels, expanded);
    
    invoiceItemPanels.add(invoiceItemPanel);
    
    return invoiceItemPanel;
  }

  private void updateInvoiceItemsPanel() {
    invoiceItemsVBox.getChildren().clear();
    invoiceItemsVBox.getChildren().addAll(invoiceItemPanels);
  }
  
  /**
   * Update the amount input field
   * <p>
   * The amount on an invoice is the sum of the amounts of the invoice items, if there is at least one invoice item with an amount filled in.
   * Otherwise the value can be entered.
   */
  private void updateAmountObjectInput() {
    PgCurrency sumOfInvoiceItems = null;
    
    for (ObjectControl<PgCurrency> itemAmountObjectInput: amountObjectInputs.values())  {
      PgCurrency itemAmount;
      itemAmount = itemAmountObjectInput.ocGetValue();
      if (itemAmount != null) {
        if (sumOfInvoiceItems == null) {
          sumOfInvoiceItems = itemAmount;
        } else {
          sumOfInvoiceItems = sumOfInvoiceItems.add(itemAmount);
        }
      }
    }

    ObjectControlCurrency amountControl = (ObjectControlCurrency) invoiceAmountObjectInput;
    if (sumOfInvoiceItems == null) {
      amountControl.ocGetControl().setDisable(false);
    } else {
      amountControl.ocGetControl().setDisable(true);
      amountControl.ocSetValue(sumOfInvoiceItems);
    }
  }
  
  private Node createPropertyAndPicturePanel() {
    HBox hBox = componentFactory.createHBox();
    hBox.getChildren().addAll(createPropertyPanel(), createPictureViewPanel());
    
    return hBox;
  }

  private Node createPropertyPanel() {
    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    gridPane.setBorder(componentFactory.getRectangularBorder());
    
    Label label = componentFactory.createStrongLabel("Property");
    gridPane.add(label, 0, 0);

    int rowIndex = 1;
    
    for (EObjectAttributeEditDescriptor eObjectAttributeEditDescriptor: propertyEditorDescriptor.getEObjectAttributeEditDescriptors()) {
      addAttributeEditControlsToGrid(gridPane, rowIndex++, eObjectAttributeEditDescriptor);
    }
    
    return gridPane;
  }

  private Node createPictureViewPanel() {
    pictureViewPanel = componentFactory.createHBox(12.0);
    
    return pictureViewPanel;
  }
  
  private void updatePictureViewPanel(String fileName) {
    pictureViewPanel.getChildren().clear();
    
    Image image = new Image("file:" + fileName, 300, 300, true, true);
    ImageView imageView = new ImageView(image);
    
    pictureViewPanel.getChildren().add(imageView);
  }
  
  private Node createDocumentsAndPicturesPanel() {
    HBox hBox = componentFactory.createHBox();
    hBox.getChildren().addAll(createDocumentsPanel(), createPicturesPanel());
    
    return hBox;
  }
  
  private Node createDocumentsPanel() {
    VBox documentsMainVBox = componentFactory.createVBox(12.0, 12.0);
    documentsMainVBox.setMinSize(300, 500);
    documentsMainVBox.setBorder(componentFactory.getRectangularBorder());
    
    Label label = componentFactory.createStrongLabel("Documents");
    documentsMainVBox.getChildren().add(label);
    
    documentReferencesVBox = componentFactory.createVBox();
    documentReferencePanels = FXCollections.observableArrayList();
    documentReferencePanels.addListener(new ListChangeListener<FileReferencePanel>() {

      @Override
      public void onChanged(Change<? extends FileReferencePanel> c) {
        while (c.next()) {
          if (c.wasPermutated()) {
            // No action needed here
          } else if (c.wasUpdated()) {
            // update item
          } else {
            for (FileReferencePanel documentReferencePanel: c.getRemoved()) {
              propertyObjectControlGroup.removeObjectControlGroup(documentReferencePanel.getObjectInputContainer());
            }
            for (FileReferencePanel documentReferencePanel: c.getAddedSubList()) {
              propertyObjectControlGroup.addObjectControlGroup(documentReferencePanel.getObjectInputContainer());
            }
          }
        }
        
        updateDocumentReferencesPanel();
        
      }
      
    });

    documentsMainVBox.getChildren().add(documentReferencesVBox);
    
    Button newDocumentButton = componentFactory.createButton("+ Add document", "Add a document reference to the property");
    newDocumentButton.setOnAction(e -> createNewDocumentReferencePanel(true));
    documentsMainVBox.getChildren().add(newDocumentButton);
    
    return documentsMainVBox;
  }
  
  private FileReferencePanel createNewDocumentReferencePanel(boolean expand) {
    FileReferencePanel documentReferencePanel = new FileReferencePanel(customization, documentReferencePanels, expand);
    
    documentReferencePanels.add(documentReferencePanel);
    
    return documentReferencePanel;
  }

  private void updateDocumentReferencesPanel() {
    documentReferencesVBox.getChildren().clear();
    documentReferencesVBox.getChildren().addAll(documentReferencePanels);
  }
  
  private Node createPicturesPanel() {
    VBox picturesMainVBox = componentFactory.createVBox(12.0, 12.0);
    picturesMainVBox.setMinSize(300, 500);
    picturesMainVBox.setBorder(componentFactory.getRectangularBorder());
    
    Label label = componentFactory.createStrongLabel("Pictures");
    picturesMainVBox.getChildren().add(label);
    
    pictureReferencesVBox = componentFactory.createVBox();
    pictureReferencePanels = FXCollections.observableArrayList();
    pictureReferencePanels.addListener(new ListChangeListener<FileReferencePanel>() {

      @Override
      public void onChanged(Change<? extends FileReferencePanel> c) {
        while (c.next()) {
          if (c.wasPermutated()) {
            // No action needed here
          } else if (c.wasUpdated()) {
            // update item
          } else {
            for (FileReferencePanel pictureReferencePanel: c.getRemoved()) {
              propertyObjectControlGroup.removeObjectControlGroup(pictureReferencePanel.getObjectInputContainer());
            }
            for (FileReferencePanel pictureReferencePanel: c.getAddedSubList()) {
              propertyObjectControlGroup.addObjectControlGroup(pictureReferencePanel.getObjectInputContainer());
            }
          }
        }
        
        updatePictureReferencesPanel();
        
      }
      
    });

    picturesMainVBox.getChildren().add(pictureReferencesVBox);
    
    Button newPictureButton = componentFactory.createButton("+ Add picture", "Add a picture reference to the property");
    newPictureButton.setOnAction(e -> createNewPictureReferencePanel(true));
    picturesMainVBox.getChildren().add(newPictureButton);
    
    return picturesMainVBox;
  }
  
  private FileReferencePanel createNewPictureReferencePanel(boolean expand) {
    FileReferencePanel pictureReferencePanel = new FileReferencePanel(customization, pictureReferencePanels, expand);
    
    ChangeListener<Boolean> cl = new ChangeListener<Boolean>() {

      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue) {
          String fileName = pictureReferencePanel.getFile();
          if (fileName != null) {
            updatePictureViewPanel(fileName);
          }
        }
        
      }
    };
    pictureReferencePanel.focusedProperty().addListener(cl);
    
    pictureReferencePanels.add(pictureReferencePanel);
    
    return pictureReferencePanel;
  }

  private void updatePictureReferencesPanel() {
    pictureReferencesVBox.getChildren().clear();
    pictureReferencesVBox.getChildren().addAll(pictureReferencePanels);
  }

  /**
   * Create the box with the action buttons.
   * <p>
   * The box has the following buttons:
   * <ul>
   * <li>cancel - close the window without any changes</li>
   * <li>create or update an invoice</li>
   * <li>create or update a property</li>
   * <li>create or update an invoice and property</li>
   * </ul>
   * 
   * @return
   */
  private Node createButtonsBox() {
    HBox buttonsBox = componentFactory.createHBox(12.0, 12.0);
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    buttonsBox.getChildren().add(spacer);
    
    // Cancel button
    Button cancelButton = componentFactory.createButton("Cancel", "Close this window without creating an invoice and property");
    cancelButton.setOnAction(e -> this.close());
    buttonsBox.getChildren().add(cancelButton);
    
    // Create or update invoice
    createOrUpdateInvoiceButton = componentFactory.createButton("", "");
    updateCreateOrUpdateInvoiceButton();
    createOrUpdateInvoiceButton.setOnAction(e -> createOrUpdateInvoice());
    buttonsBox.getChildren().add(createOrUpdateInvoiceButton);
    
    // Create or update property button
    createOrUpdatePropertyButton = componentFactory.createButton("", "");
    updateCreateOrUpdatePropertyButton();
    createOrUpdatePropertyButton.setOnAction(e -> createOrUpdateProperty());
    buttonsBox.getChildren().add(createOrUpdatePropertyButton);
    
    // Create or update invoice and property button
    createOrUpdateInvoiceAndPropertyButton = componentFactory.createButton("", "");
    updateCreateOrUpdateInvoiceAndPropertyButton();
    createOrUpdateInvoiceAndPropertyButton.setOnAction(e -> createOrUpdateInvoiceAndProperty());
    buttonsBox.getChildren().add(createOrUpdateInvoiceAndPropertyButton);
        
    return  buttonsBox;
  }

  /*
   * Add the controls (Label and ObjectInput) for one attribute to the gridPane.
   * 
   * @param gridPane The GridPane to add the controls to.
   * @param rowIndex Index for the row in the GridPane to which the controls are to be added.
   * @param eObjectAttributeEditDescriptor EObjectAttributeEditDescriptor for the attribute.
   */
  private void addAttributeEditControlsToGrid(GridPane gridPane, int rowIndex, EObjectAttributeEditDescriptor eObjectAttributeEditDescriptor) {
    // Label
    StringBuilder buf = new StringBuilder();
    buf.append(eObjectAttributeEditDescriptor.getLabelText());
    if (!eObjectAttributeEditDescriptor.getObjectControl().ocIsOptional()) {
      buf.append(" *");
    }
    buf.append(":");
    Label label = componentFactory.createLabel(buf.toString());
    gridPane.add(label, 0, rowIndex);
    
    // ObjectInput control
    ObjectControl<?> objectInput = eObjectAttributeEditDescriptor.getObjectControl();
    gridPane.add(objectInput.ocGetControl(), 1, rowIndex); 
    
    // Ok/Not OK label
    Label statusLabel = componentFactory.createLabel(null);
    objectInput.addListener((o) -> EObjectEditor.updateStatusLabel(statusLabel, objectInput.ocIsValid()));
     EObjectEditor.updateStatusLabel(statusLabel, objectInput.ocIsValid());
    gridPane.add(statusLabel, 2, rowIndex);
  }

  /**
   * Create or update an invoice.
   */
  private void createOrUpdateInvoice() {
    if (invoice == null) {
      invoice = INVOICES_AND_PROPERTIES_FACTORY.createInvoice();
      invoicesAndProperties.getInvoices().getInvoices().add(invoice);
      
      updateCreateOrUpdateInvoiceButton();
    }
    
    updateInvoiceFromControls(invoice);
  }

  /**
   * Create or update a property
   */
  private void createOrUpdateProperty() {
    if (property == null) {
      property  = INVOICES_AND_PROPERTIES_FACTORY.createProperty();
      invoicesAndProperties.getProperties().getProperties().add(property);
      
      updateCreateOrUpdatePropertyButton();
    }
    
    updatePropertyFromControls(property);
  }

  private void createOrUpdateInvoiceAndProperty() {
    boolean linkInvoiceAndProperty = false;
    
    if (invoice == null) {
      invoice = INVOICES_AND_PROPERTIES_FACTORY.createInvoice();
      invoicesAndProperties.getInvoices().getInvoices().add(invoice);
      linkInvoiceAndProperty = true;
    }
    
    if (property == null) {
      property  = INVOICES_AND_PROPERTIES_FACTORY.createProperty();
      invoicesAndProperties.getProperties().getProperties().add(property);
      linkInvoiceAndProperty = true;
    }
    
    if (linkInvoiceAndProperty) {      
      invoice.setPurchase(property);
    }
    
    updateCreateOrUpdateInvoiceButton();
    updateCreateOrUpdatePropertyButton();
    updateCreateOrUpdateInvoiceAndPropertyButton();
    
    updateInvoiceFromControls(invoice);
    updatePropertyFromControls(property);
  }

  private String prependBaseDirToFilename(String filename) {
    File file = new File(InvoicesAndPropertiesRegistry.propertyRelatedFilesFolder, filename);
    
    return file.getAbsolutePath();
  }
  
  private String stripBaseDirFromFilename(String fileName) {
    File baseDir = new File(InvoicesAndPropertiesRegistry.propertyRelatedFilesFolder);
    if ((fileName.length() > baseDir.getAbsolutePath().length() + 1)  &&
        fileName.startsWith(baseDir.getAbsolutePath())  &&
        (fileName.charAt(baseDir.getAbsolutePath().length()) == '\\')) {
      fileName = fileName.substring(baseDir.getAbsolutePath().length() + 1);
    }
    
    return fileName;
  }
}


/**
 * This class provides a panel to edit an invoice item.
 */
class InvoiceItemPanel extends TitledPane {
  private static final Logger LOGGER = Logger.getLogger(InvoiceItemPanel.class.getName());
  private static String DEFAULT_TITLE = "New invoice item";
  private static final InvAndPropPackage INVOICES_AND_PROPERTIES_PACKAGE = InvAndPropPackage.eINSTANCE;
  
  private List<InvoiceItemPanel> invoiceItemPanels;
  private ComponentFactoryFx componentFactory;
  
  private EObjectEditorDescriptor invoiceItemEditorDescriptor;
  private ObjectControlGroup objectInputContainer;
  
  private static final DataFormat INVOICE_ITEM_PANEL = new DataFormat("InvoiceItemPanel");
  private InvoiceItemPanel thisInvoiceItemPanel;
  
  // References to the ObjectInputs
  private ObjectControl<Integer> numberOfItemsObjectInput;
  private ObjectControlString descriptionObjectInput;
  private ObjectControlCurrency amountObjectInput;
  private ObjectControlString remarksObjectInput;

  @SuppressWarnings("unchecked")
  public InvoiceItemPanel(CustomizationFx customization, List<InvoiceItemPanel> invoiceItemPanels, boolean expanded) {
    this.invoiceItemPanels = invoiceItemPanels;
    componentFactory = customization.getComponentFactoryFx();
    
    invoiceItemEditorDescriptor = new InvoiceItemEditorDescriptor(customization);
    
    // Get references to the ObjectInputs
    numberOfItemsObjectInput = (ObjectControl<Integer>) invoiceItemEditorDescriptor.getEObjectAttributeEditDescriptor(INVOICES_AND_PROPERTIES_PACKAGE.getInvoiceItem_NumberOfItems().getName()).getObjectControl();
    descriptionObjectInput = (ObjectControlString) invoiceItemEditorDescriptor.getEObjectAttributeEditDescriptor(INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Description().getName()).getObjectControl();
    amountObjectInput = (ObjectControlCurrency) invoiceItemEditorDescriptor.getEObjectAttributeEditDescriptor(INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Amount().getName()).getObjectControl();
    remarksObjectInput = (ObjectControlString) invoiceItemEditorDescriptor.getEObjectAttributeEditDescriptor(INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Remarks().getName()).getObjectControl();
    
    createObjectInputContainer();

    createGUI();
    
    objectInputContainer.isValid().addListener(new ChangeListener<>() {

      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        updateTitle();        
      }

        
    });
    
    numberOfItemsObjectInput.addListener(new InvalidationListener() {

      @Override
      public void invalidated(Observable observable) {
        updateTitle();
      }

        
    });
    
    descriptionObjectInput.addListener(new InvalidationListener() {

      @Override
      public void invalidated(Observable observable) {
        updateTitle();
      }

        
    });
    
    amountObjectInput.addListener(new InvalidationListener() {

      @Override
      public void invalidated(Observable observable) {
        updateTitle();
      }

        
    });
    
    thisInvoiceItemPanel = this;
    
    /*
     * Handle the starting of a drag event.
     * This can be dragged.
     */
    setOnDragDetected(new EventHandler<MouseEvent>() {

      public void handle(MouseEvent event) {
        ClipboardContent clipboardContent = new ClipboardContent();
        Integer myIndex = invoiceItemPanels.indexOf(thisInvoiceItemPanel);
        clipboardContent.put(INVOICE_ITEM_PANEL, myIndex);

        Dragboard dragBoard = startDragAndDrop(TransferMode.MOVE);
        dragBoard.setContent(clipboardContent);

        event.consume();
      }
    });
    
    /*
     * Check whether a drop event can be handled (upon a drag over).
     * Drop is supported for INVOICE_ITEM_PANEL.
     */
    setOnDragOver(new EventHandler<DragEvent>() {
      public void handle(DragEvent event) {
        /* data is dragged over a (possible) target */
        /* accept it only if it is not dragged from the same node 
         * and if it has a supported data format. */
        Dragboard dragboard = event.getDragboard();
        if (event.getGestureSource() != thisInvoiceItemPanel) {
          if (dragboard.hasContent(INVOICE_ITEM_PANEL)) {
            event.acceptTransferModes(TransferMode.MOVE);
          }
        }

        event.consume();
      }
    });
    
    /*
     * Handle the dropping on the target
     */
    setOnDragDropped(new EventHandler<DragEvent>() {
      public void handle(DragEvent event) {
        LOGGER.info("=>");

        boolean success = false;

        // Get the index from the drag board, and if it isn't null use it.
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasContent(INVOICE_ITEM_PANEL)) {
          Integer sourceIndex = (Integer) dragboard.getContent(INVOICE_ITEM_PANEL);
          if (sourceIndex != null) {
            InvoiceItemPanel sourcePanel = invoiceItemPanels.get(sourceIndex);
            int sourceIndexInt = sourceIndex;
            invoiceItemPanels.remove(sourceIndexInt);
            
            Integer myIndex = invoiceItemPanels.indexOf(thisInvoiceItemPanel);
            invoiceItemPanels.add(myIndex, sourcePanel);
          }

          /* let the source know whether the item was successfully transferred and used */
          event.setDropCompleted(success);
        }

        event.consume();
      }
    });
    
    this.setExpanded(expanded);
    updateTitle();
  }

  public ObjectControl<Integer> getNumberOfItemsObjectInput() {
    return numberOfItemsObjectInput;
  }

  public ObjectControlString getDescriptionObjectInput() {
    return descriptionObjectInput;
  }

  public ObjectControlCurrency getAmountObjectInput() {
    return amountObjectInput;
  }

  public ObjectControlString getRemarksObjectInput() {
    return remarksObjectInput;
  }

  public ObjectControlGroup getObjectInputContainer() {
    return objectInputContainer;
  }

  private void createObjectInputContainer() {
    objectInputContainer = new ObjectControlGroup();
    
    for (EObjectAttributeEditDescriptor eObjectAttributeEditDescriptor: invoiceItemEditorDescriptor.getEObjectAttributeEditDescriptors()) {
      objectInputContainer.addObjectControl((ObjectControl<?>) eObjectAttributeEditDescriptor.getObjectControl());
    }
  }
  
  private void createGUI() {
    VBox rootPane = componentFactory.createVBox();

    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
        
    int rowIndex = 1;
    
    for (EObjectAttributeEditDescriptor eObjectAttributeEditDescriptor: invoiceItemEditorDescriptor.getEObjectAttributeEditDescriptors()) {
      addAttributeEditControlsToGrid(gridPane, rowIndex++, eObjectAttributeEditDescriptor);
    }
    
    rootPane.getChildren().addAll(gridPane, createButtonsBox());
    
    setContent(rootPane);
  }

  /**
   * Add the controls (Label and ObjectInput) for one attribute to the gridPane.
   * 
   * @param gridPane The GridPane to add the controls to.
   * @param rowIndex Index for the row in the GridPane to which the controls are to be added.
   * @param eObjectAttributeEditDescriptor EObjectAttributeEditDescriptor for the attribute.
   */
  private void addAttributeEditControlsToGrid(GridPane gridPane, int rowIndex, EObjectAttributeEditDescriptor eObjectAttributeEditDescriptor) {
    // Label
    StringBuilder buf = new StringBuilder();
    buf.append(eObjectAttributeEditDescriptor.getLabelText());
    if (!eObjectAttributeEditDescriptor.getObjectControl().ocIsOptional()) {
      buf.append(" *");
    }
    buf.append(":");
    Label label = componentFactory.createLabel(buf.toString());
    gridPane.add(label, 0, rowIndex);
    
    // ObjectInput control
    Node node = eObjectAttributeEditDescriptor.getObjectControl().ocGetControl();
    gridPane.add(node, 1, rowIndex); 
    
    // Ok/Not OK label
    Label statusLabel = componentFactory.createLabel(null);
    ObjectControl<?> objectInput = (ObjectControl<?>) node;
    objectInput.addListener((o) -> EObjectEditor.updateStatusLabel(statusLabel, objectInput.ocIsValid()));   
    EObjectEditor.updateStatusLabel(statusLabel, objectInput.ocIsValid());
    gridPane.add(statusLabel, 2, rowIndex);
  }

  private Node createButtonsBox() {
    HBox buttonsBox = componentFactory.createHBox(12.0, 12.0);
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    buttonsBox.getChildren().add(spacer);
    
    Button deleteButton = componentFactory.createButton("Delete this item", "Delete this invoice item");
    deleteButton.setOnAction(e -> deleteInvoiceItem());
    buttonsBox.getChildren().add(deleteButton);
    
    return  buttonsBox;
  }
  
  private void deleteInvoiceItem() {
    invoiceItemPanels.remove(this);
  }
  
  /**
   * Update the title of this TitledPane.
   */
  private void updateTitle() {
    StringBuilder buf = new  StringBuilder();
    
    // Add number of items if available
    Integer numberOfItems = null;
    if (numberOfItemsObjectInput.ocIsValid() && numberOfItemsObjectInput.ocIsFilledIn()) {
      numberOfItems = numberOfItemsObjectInput.ocGetValue();
    }
    if (numberOfItems != null  &&  numberOfItems != 0) {
      buf.append(numberOfItems).append("x ");
    }
    
    // Add description if available
    String description = null;
    description = descriptionObjectInput.ocGetValue();
     if (description == null  ||  description.isEmpty()) {
      description = DEFAULT_TITLE;
    }
    buf.append(description).append(" ");
    
    // Add amount if available
    PgCurrency amount = null;
    if (amountObjectInput.ocIsValid() && amountObjectInput.ocIsFilledIn()) {
      amount = amountObjectInput.ocGetValue();
    }
    if (amount != null) {
      buf.append(amountObjectInput.ocGetObjectValueAsFormattedText()).append(" ");
    }
    
    // Add (in)valid indication
    if (objectInputContainer.isValid().getValue()) {
      buf.append(EObjectEditor.OK_INDICATOR);
    } else {
      buf.append(EObjectEditor.NOK_INDICATOR);
    }
    
    setText(buf.toString());
  }
}


/**
 * This class provides a panel to edit a file reference.
 */
class FileReferencePanel extends TitledPane {
  private static final Logger LOGGER = Logger.getLogger(InvoiceItemPanel.class.getName());
  private static String DEFAULT_TITLE = "New document reference";
  
  private List<FileReferencePanel> documentReferencePanels;
  private ComponentFactoryFx componentFactory;
  
  private ObjectControlGroup objectInputContainer;
  
  private static final DataFormat DOCUMENT_REFERENCE_PANEL = new DataFormat("DocumentReferencePanel");
  private FileReferencePanel thisDocumentReferencePanel;
  
  // The ObjectInputs
  private ObjectControlFileSelecter fileSelecter;
  private ObjectControlString titleTextField;

  private Desktop  desktop = null;
  

  public FileReferencePanel(CustomizationFx customization, List<FileReferencePanel> documentReferencePanels, boolean expand) {
    this.documentReferencePanels = documentReferencePanels;
    componentFactory = customization.getComponentFactoryFx();
    
    fileSelecter = componentFactory.createFileSelecter(getPropertyFilesFolder(), 400, "Currently selected folder",
        "Choose file", "Select a file via a file chooser", "Select the file");
    fileSelecter.ocSetId("fileSelecter");
    fileSelecter.addListener(new InvalidationListener() {

      @Override
      public void invalidated(Observable observable) {
        updateTitle();        
      }
        
    });
    titleTextField = componentFactory.createObjectControlString(null, 200, true, "a title for the file");
    titleTextField.ocSetId("title");
    titleTextField.addListener(new InvalidationListener() {

      @Override
      public void invalidated(Observable observable) {
        updateTitle();        
      }
        
    });
    
    createObjectInputContainer();

    createGUI();
    
    objectInputContainer.isValid().addListener(new ChangeListener<>() {

      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        updateTitle();        
      }
        
    });
        
    thisDocumentReferencePanel = this;
    
    /*
     * Handle the starting of a drag event.
     * This can be dragged.
     */
    setOnDragDetected(new EventHandler<MouseEvent>() {

      public void handle(MouseEvent event) {
        ClipboardContent clipboardContent = new ClipboardContent();
        Integer myIndex = documentReferencePanels.indexOf(thisDocumentReferencePanel);
        clipboardContent.put(DOCUMENT_REFERENCE_PANEL, myIndex);

        Dragboard dragBoard = startDragAndDrop(TransferMode.MOVE);
        dragBoard.setContent(clipboardContent);

        event.consume();
      }
    });
    
    /*
     * Check whether a drop event can be handled (upon a drag over).
     * Drop is supported for DOCUMENT_REFERENCE_PANEL.
     */
    setOnDragOver(new EventHandler<DragEvent>() {
      public void handle(DragEvent event) {
        /* data is dragged over a (possible) target */
        /* accept it only if it is not dragged from the same node 
         * and if it has a supported data format. */
        Dragboard dragboard = event.getDragboard();
        if (event.getGestureSource() != thisDocumentReferencePanel) {
          if (dragboard.hasContent(DOCUMENT_REFERENCE_PANEL)) {
            event.acceptTransferModes(TransferMode.MOVE);
          }
        }

        event.consume();
      }
    });
    
    /*
     * Handle the dropping on the target
     */
    setOnDragDropped(new EventHandler<DragEvent>() {
      public void handle(DragEvent event) {
        LOGGER.info("=>");

        boolean success = false;

        // Get the index from the drag board, and if it isn't null use it.
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasContent(DOCUMENT_REFERENCE_PANEL)) {
          Integer sourceIndex = (Integer) dragboard.getContent(DOCUMENT_REFERENCE_PANEL);
          if (sourceIndex != null) {
            FileReferencePanel sourcePanel = documentReferencePanels.get(sourceIndex);
            int sourceIndexInt = sourceIndex;
            documentReferencePanels.remove(sourceIndexInt);
            
            Integer myIndex = documentReferencePanels.indexOf(thisDocumentReferencePanel);
            documentReferencePanels.add(myIndex, sourcePanel);
          }

          /* let the source know whether the item was successfully transferred and used */
          event.setDropCompleted(success);
        }

        event.consume();
      }
    });
    
    setExpanded(expand);
    
    updateTitle();
  }
  
  public ObjectControlFileSelecter getFileSelecter() {
    return fileSelecter;
  }
  
  public ObjectControlString titleTextField() {
    return titleTextField;
  }
  
  public String getFile() {
    if (fileSelecter.ocIsValid()) {
      return fileSelecter.ocGetAbsolutePath();
    } else {
      return null;
    }
  }
  
  private String getPropertyFilesFolder() {
    String propertyFilesFolder = null;
    
    for (FileReferencePanel documentReferencePanel: documentReferencePanels) {
      File file = documentReferencePanel.fileSelecter.ocGetValue();
      propertyFilesFolder = file.getParent();
      if (propertyFilesFolder != null) {
        return propertyFilesFolder;
      }
    }
    
    return null;
  }

  public ObjectControlGroup getObjectInputContainer() {
    return objectInputContainer;
  }

  private void createObjectInputContainer() {
    objectInputContainer = new ObjectControlGroup();
    
    objectInputContainer.addObjectControl(fileSelecter);
    objectInputContainer.addObjectControl(titleTextField);
  }
  
  private void createGUI() {
    VBox rootPane = componentFactory.createVBox();

    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    
    // Row 0: file selection; label, textfield and button to start file chooser.
    Label fileNameLabel = componentFactory.createLabel("File:");
    gridPane.add(fileNameLabel, 0, 0);
        
    gridPane.add(fileSelecter.ocGetControl(), 1, 0);
    
    Button fileChooserButton = fileSelecter.getFileChooserButton();
    gridPane.add(fileChooserButton, 2, 0);
        
    // Row 1: title; label, textfield
    Label titleLabel = componentFactory.createLabel("Title:");
    gridPane.add(titleLabel, 0, 1);
    
    gridPane.add(titleTextField.ocGetControl(), 1, 1);
    
    rootPane.getChildren().addAll(gridPane, createButtonsBox());
    
    setContent(rootPane);
  }

  private Node createButtonsBox() {
    HBox buttonsBox = componentFactory.createHBox(12.0, 12.0);
    
    Button openItemButton = componentFactory.createButton("Open item", "Open this item with the related application");
    openItemButton.setOnAction(e -> openItem());
    buttonsBox.getChildren().add(openItemButton);
    
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    buttonsBox.getChildren().add(spacer);
    
    Button deleteButton = componentFactory.createButton("Delete this item", "Delete this invoice item");
    deleteButton.setOnAction(e -> deleteInvoiceItem());
    buttonsBox.getChildren().add(deleteButton);
    
    return  buttonsBox;
  }
  
  private void deleteInvoiceItem() {
    documentReferencePanels.remove(this);
  }
  
  private void openItem() {
    Desktop desktop = getDesktop();
    if ((desktop == null)  ||  !desktop.isSupported(Desktop.Action.OPEN)) {
      componentFactory.createErrorDialog("Unable to open a item", "Opening items isn't supported on this platform").showAndWait();
      return;
    }
    
    try {
      URI uri = new URI(fileSelecter.ocGetAbsolutePath());
      try {
        desktop.browse(uri);
      } catch (IOException e) {
        componentFactory.createErrorDialog("Unable to open URL", e.getMessage());
      }
    } catch (URISyntaxException e1) {
      File file = fileSelecter.ocGetValue();
      
      try {
        desktop.open(file);
      } catch (IllegalArgumentException | IOException e) {
        componentFactory.createErrorDialog("An error occurred on trying to open the file", e.getMessage()).showAndWait();
      }
    }
  }
  
  private Desktop getDesktop() {
    if (desktop == null) {
      // Before more Desktop API is used, first check 
      // whether the API is supported by this particular 
      // virtual machine (VM) on this particular host.
      if (Desktop.isDesktopSupported()) {
          desktop = Desktop.getDesktop();          
      }
      
    }
    
    return desktop;
  }
  
  /**
   * Update the 'title' (actually the text) of this TitledPane.
   */
  private void updateTitle() {
    StringBuilder buf = new  StringBuilder();
    
    String string = titleTextField.ocGetValue();
    if ((string == null)  ||  string.isEmpty()) {

      if (fileSelecter.ocGetValue() != null) {
        File file = fileSelecter.ocGetValue();
        string = file.getName();
      } else {
        string = DEFAULT_TITLE;
      }
    }
    
    buf.append(string).append(" ");
        
    // Add (in)valid indication
    if (objectInputContainer.isValid().getValue()) {
      buf.append(EObjectEditor.OK_INDICATOR);
    } else {
      buf.append(EObjectEditor.NOK_INDICATOR);
    }
    
    setText(buf.toString());
  }
}
