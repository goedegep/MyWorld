package goedegep.invandprop.app.guifx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
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
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControl;
import goedegep.jfx.objectcontrols.ObjectControlBoolean;
import goedegep.jfx.objectcontrols.ObjectControlCurrency;
import goedegep.jfx.objectcontrols.ObjectControlFlexDate;
import goedegep.jfx.objectcontrols.ObjectControlGroup;
import goedegep.jfx.objectcontrols.ObjectControlString;
import goedegep.jfx.objecteditor.EditMode;
import goedegep.jfx.objecteditor.EditStatus;
import goedegep.jfx.objecteditor.FileReferenceEditPanel;
import goedegep.jfx.objecteditor.FileReferenceTypeInfo;
import goedegep.jfx.objecteditor.ObjectEditorTemplate;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesFactory;
import goedegep.util.Debug;
import goedegep.util.PgUtilities;
import goedegep.util.emf.EmfUtil;
import goedegep.util.money.PgCurrency;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * This class provides an editor for an invoice and the related property.
 * <p>
 * This editor is basically two editors in one: an invoice editor and a property editor. This because you often create them together.<br/>
 * It extends the {@code ObjectEditorTemplate} for the invoice part. For the property part there are separate methods, all with 'property' in the name.
 */
public class InvoiceAndPropertyEditor extends ObjectEditorTemplate<Invoice> {
  /*
   * Implementation information:
   * The invoice part is the actual client of the ObjectEditorTemplate. For example the objectControlGroup is filled with the controls for the invoice.
   * For the property, things are implemented in a similar way in this class. For example there is a propertyObjectControlGroup for the controls for the property.
   */
  private static final Logger  LOGGER = Logger.getLogger(InvoiceItemPanel.class.getName());
  @SuppressWarnings("unused")
  private static final String NEW_LINE = System.getProperty("line.separator");
  private static final String WINDOW_TITLE = "New invoice and property";
  private static final InvAndPropFactory INVOICES_AND_PROPERTIES_FACTORY = InvAndPropFactory.eINSTANCE;
  private static final InvAndPropPackage INVOICES_AND_PROPERTIES_PACKAGE = InvAndPropPackage.eINSTANCE;
  
    
  /**
   * The invoices and properties to which new invoices and properties will be added.
   */
  private InvoicesAndProperties invoicesAndProperties;
  
  /**
   * ObjectControlGroup used to check the validity of the property controls.
   */
  private ObjectControlGroup propertyObjectControlsGroup;
  
  /**
   * List of panels, one for each invoice item.
   */
  private ObservableList<InvoiceItemPanel> invoiceItemPanels;
  
  /**
   * The box which contains the invoice item panels.
   */
  private VBox invoiceItemsVBox;
  
  
  /*
   * Invoice ObjectControls
   */
  private ObjectControlFlexDate invoiceDateObjectControl;
  private ObjectControlString invoiceCompanyObjectControl;
  private ObjectControlString invoiceDescriptionObjectControl;
  private ObjectControlCurrency invoiceAmountObjectControl;
  private ObjectControlString invoiceRemarksObjectControl;
  private ObjectControlBoolean invoiceDescriptionFromPropertyObjectControl;
  
  /*
   * Property ObjectControls
   */
  private ObjectControlString propertyDescriptionObjectControl;
  private ObjectControlString propertyBrandObjectControl;
  private ObjectControlString propertyTypeObjectControl;
  private ObjectControlString propertySerialNumberObjectControl;
  private ObjectControlString propertyRemarksObjectControl;
  private ObjectControlFlexDate propertyFromDateObjectControl;
  private ObjectControlFlexDate propertyUntilDateObjectControl;
  private ObjectControlBoolean propertyArchiveObjectControl;
  
  /**
   * List of panels, one for each document reference.
   */
  private ObservableList<FileReferenceEditPanel> documentReferencePanels;
  
  /**
   * The box which contains the document reference panels.
   */
  private VBox documentReferencesVBox;
  
  /**
   * List of panels, one for each picture reference.
   */
  private ObservableList<FileReferenceEditPanel> pictureReferencePanels;
  
  /**
   * The box which contains the picture references.
   */
  private VBox pictureReferencesVBox;
  
  /**
   * A picture preview box
   */
  private HBox pictureViewPanel;
  
  /**
   * Current property.
   */
  private Property property;
  
  
  /**
   * Factory method to obtain a new instance of an {@code InvoiceAndPropertyEditor}.
   * 
   * @param Customization the GUI customization.
   * @param invoicesAndProperties The invoices and properties to which new invoices and properties will be added.
   * @return a newly created {@code InvoiceAndPropertyEditor}.
   */
  public static InvoiceAndPropertyEditor newInstance(CustomizationFx customization, InvoicesAndProperties invoicesAndProperties) {
    InvoiceAndPropertyEditor invoiceAndPropertyEditor = new InvoiceAndPropertyEditor(customization, invoicesAndProperties);
    invoiceAndPropertyEditor.performInitialization();
    
    return invoiceAndPropertyEditor;
  }
  
  /**
   * Constructor.
  * 
   * @param Customization the GUI customization.
   * @param invoicesAndProperties The invoices and properties to which new invoices and properties will be added.
   */
  private InvoiceAndPropertyEditor(CustomizationFx customization, InvoicesAndProperties invoicesAndProperties) {
    super(customization, WINDOW_TITLE);
    
    Objects.requireNonNull(customization, "argument ‘customization’ must not be null");
    Objects.requireNonNull(invoicesAndProperties, "argument ‘invoicesAndProperties’ must not be null");
            
    this.invoicesAndProperties = invoicesAndProperties;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void setObject(Invoice invoice, boolean checkOnUnsavedChanges, boolean newObject) {
    if (checkOnUnsavedChanges  &&  !getUserConfirmationInCaseOfUnsavedChanges()) {
      return;
    }

    this.object = invoice;
    ignoreChanges = true;
    
    if (invoice != null  &&  invoice.isSetPurchase()) {
      property = invoice.getPurchase();
    } else {
      property = null;
    }

    fillControlsWithDefaultValues();
    fillPropertyControlsWithDefaultValues();
    
    if (object != null) {
      fillControlsFromObject();
      editMode = EditMode.EDIT;
    } else {
      editMode = EditMode.NEW;
    }
    
    if (property != null) {
      fillControlsFromProperty();
    }

    ignoreChanges = false;
    handleChanges();
  }
  
  /**
   * Start editing a property.
   * <p>
   * If there are any unsaved changes, show a dialog informing the user about this and ask for a confirmation.
   * {@code property} is set to the specified value.
   * All the controls are cleared then, if property isn't null, filled with the information from the {@code property}.
   * 
   * @param property the value to be edited.
   */
  public void setProperty(Property property) {
    setProperty(property, true);
  }
  
  /**
   * Start editing a property.
   * <p>
   * If there are any unsaved changes while checkOnUnsavedChanges is set, show a dialog informing the user about this and ask for a confirmation.
   * {@code property} is set to the specified value.
   * All the controls are cleared then, if the property isn't null, filled with the information from the {@code property}.
   * 
   * @param property the value to be edited.
   * @param checkOnUnsavedChanges if true and if there are any unsaved changes, show a dialog informing the user about this and ask for a confirmation.
   */
  public void setProperty(Property property, boolean checkOnUnsavedChanges) {
    if (checkOnUnsavedChanges  &&  !getUserConfirmationInCaseOfUnsavedChanges()) {
      return;
    }

    this.property = property;
    ignoreChanges = true;
    
    if (property != null  &&  property.getExpenditure() != null) {
      Expenditure expenditure = property.getExpenditure();
      if (expenditure instanceof Invoice) {
        object = (Invoice) expenditure;
      } else {
        object = (Invoice) expenditure.eContainer();
      }
    } else {
      object = null;
    }

    fillControlsWithDefaultValues();
    fillPropertyControlsWithDefaultValues();

    
    if (property != null) {
      fillControlsFromProperty();
      editMode = EditMode.EDIT;
    } else {
      editMode = EditMode.NEW;
    }
    
    if (object != null) {
      fillControlsFromObject();
    }

    ignoreChanges = false;
    handleChanges();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void configureEditor() {
    // no action as this editor has its own buttons.
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void createControls() {
//    createAttributeEditDescriptors();
    
    invoiceDateObjectControl = componentFactory.createObjectControlFlexDate(null, 150.0, true, "the invoice date");
    invoiceDateObjectControl.setId("InvoiceDate");
    invoiceCompanyObjectControl = componentFactory.createObjectControlString(null, 200, true, "the company you paid the invoice to");
    invoiceCompanyObjectControl.setId("InvoiceCompany");
    invoiceDescriptionObjectControl = componentFactory.createObjectControlString(null, 200, false, "typically the product");
    invoiceDescriptionObjectControl.setId("ÏnvoiceDescription");
    invoiceAmountObjectControl = componentFactory.createObjectControlCurrency(null, 150, false, "the amount of money paid");
    invoiceAmountObjectControl.setId("InvoiceAmount");
    invoiceRemarksObjectControl = componentFactory.createObjectControlString(null, 150.0, true, "any comments on this invoice");
    invoiceRemarksObjectControl.setId("InvoiceRemarks");
    invoiceDescriptionFromPropertyObjectControl = componentFactory.createObjectControlBoolean(null, false, true, "If set, the description will be derived from the related property");
    invoiceDescriptionFromPropertyObjectControl.setId("InvoiceDescriptionFromProperty");
    invoiceDescriptionFromPropertyObjectControl.addListener((e) -> handleChanges());
    
    invoiceItemPanels = FXCollections.observableArrayList();
        
    objectControlsGroup.addObjectControls(
        invoiceDateObjectControl,
        invoiceCompanyObjectControl,
        invoiceDescriptionObjectControl,
        invoiceAmountObjectControl,
        invoiceRemarksObjectControl,
        invoiceDescriptionFromPropertyObjectControl
    );
    
    propertyDescriptionObjectControl = componentFactory.createObjectControlString(null, 150.0, false, "the property description");
    propertyDescriptionObjectControl.setId("PropertyDescription");
    propertyBrandObjectControl = componentFactory.createObjectControlString(null, 150.0, true, "the brand of the property");
    propertyBrandObjectControl.setId("PropertyBrand");
    propertyTypeObjectControl = componentFactory.createObjectControlString(null, 150.0, true, "the property type");
    propertyTypeObjectControl.setId("PropertyType");
    propertySerialNumberObjectControl = componentFactory.createObjectControlString(null, 150.0, true, "the serial number of the property");
    propertySerialNumberObjectControl.setId("PropertySerialNumber");
    propertyRemarksObjectControl = componentFactory.createObjectControlString(null, 150.0, true, "any comments on this property");
    propertyRemarksObjectControl.setId("PropertyRemarks");
    propertyFromDateObjectControl = componentFactory.createObjectControlFlexDate(null, 150.0, true, "date from when you own(ed) the property");
    propertyFromDateObjectControl.setId("PropertyFromDate");
    propertyUntilDateObjectControl = componentFactory.createObjectControlFlexDate(null, 150.0, true, "date until when you owned the property");
    propertyUntilDateObjectControl.setId("PropertyUntilDate");
    propertyArchiveObjectControl = componentFactory.createObjectControlBoolean(null, false, true, "select if you don't own the property anymore");
    propertyArchiveObjectControl.setId("PropertyArchive");

    documentReferencePanels = FXCollections.observableArrayList();
    pictureReferencePanels = FXCollections.observableArrayList();
    
    propertyObjectControlsGroup = new ObjectControlGroup();
    propertyObjectControlsGroup.addObjectControls(
        propertyDescriptionObjectControl,
        propertyBrandObjectControl,
        propertyTypeObjectControl,
        propertySerialNumberObjectControl,
        propertyRemarksObjectControl,
        propertyFromDateObjectControl,
        propertyUntilDateObjectControl,
        propertyArchiveObjectControl
    );

  }
    
  /**
   * {@inheritDoc}
   */
  @Override
  protected void fillControlsWithDefaultValues() {
    invoiceDateObjectControl.setValue(null);
    invoiceCompanyObjectControl.setValue(null);
    invoiceDescriptionObjectControl.setValue(null);
    invoiceAmountObjectControl.setValue(null);
    invoiceRemarksObjectControl.setValue(null);
    invoiceDescriptionFromPropertyObjectControl.setValue(false);
    
    invoiceItemPanels.clear();
  }
  
  /**
   * Fill the Property controls with default values.
   * <p>
   * This is the counterpart of {@code fillControlsWithDefaultValues()) for the Property.
   */
  private void fillPropertyControlsWithDefaultValues() {
    propertyDescriptionObjectControl.setValue(null);
    propertyBrandObjectControl.setValue(null);
    propertyTypeObjectControl.setValue(null);
    propertySerialNumberObjectControl.setValue(null);
    propertyRemarksObjectControl.setValue(null);
    propertyFromDateObjectControl.setValue(null);
    propertyUntilDateObjectControl.setValue(null);
    propertyArchiveObjectControl.setValue(false);
    
    documentReferencePanels.clear();
    pictureReferencePanels.clear();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void fillControlsFromObject() {
    LOGGER.info("=>");
    
    if (object.isSetDate()) {
      invoiceDateObjectControl.setValue(object.getDate());
    }
    
    if (object.isSetCompany()) {
      invoiceCompanyObjectControl.setValue(object.getCompany());
    }
    
    if (!object.isDescriptionFromProperty()) {
      invoiceDescriptionObjectControl.setValue(object.getDescription());
    }
    
    if (object.isSetAmount()) {
      invoiceAmountObjectControl.setValue(object.getAmount());
    }
    
    if (object.isSetRemarks()) {
      invoiceRemarksObjectControl.setValue(object.getRemarks());
    }
    
    invoiceDescriptionFromPropertyObjectControl.setValue(object.isDescriptionFromProperty());
    
    createInvoiceItemPanelsFromInvoice();
    
  }
  
  /**
   * File the invoice item panels from the invoice
   */
  private void createInvoiceItemPanelsFromInvoice() {
    for (InvoiceItem invoiceItem: object.getInvoiceItems()) {
      InvoiceItemPanel invoiceItemPanel = createNewInvoiceItemPanel(false);
      
      invoiceItemPanel.getNumberOfItemsObjectInput().setValue(invoiceItem.getNumberOfItems());
      
      invoiceItemPanel.getDescriptionObjectInput().setValue(invoiceItem.getDescription());

      invoiceItemPanel.getAmountObjectInput().setValue(invoiceItem.getAmount());
      
      invoiceItemPanel.getRemarksObjectInput().setValue(invoiceItem.getRemarks());
    }
    
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void updateObjectFromControls() {    
    EmfUtil.setFeatureValue(object, INVOICES_AND_PROPERTIES_PACKAGE.getInvoice_Date(), invoiceDateObjectControl.getValue());
    EmfUtil.setFeatureValue(object, INVOICES_AND_PROPERTIES_PACKAGE.getInvoice_Company(), invoiceCompanyObjectControl.getValue());
    EmfUtil.setFeatureValue(object, INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Description(), invoiceDescriptionObjectControl.getValue());
    EmfUtil.setFeatureValue(object, INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Amount(), invoiceAmountObjectControl.getValue());
    EmfUtil.setFeatureValue(object, INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_Remarks(), invoiceRemarksObjectControl.getValue());
    EmfUtil.setFeatureValue(object, INVOICES_AND_PROPERTIES_PACKAGE.getExpenditure_DescriptionFromProperty(), invoiceDescriptionFromPropertyObjectControl.getValue());

    updateInvoiceItemsFromInvoiceItemPanels();
  }
  
  /**
   * Update the invoice items of the invoice from the {@code invoiceItemPanels}.
   * <p>
   * If there are any changes, the complete list of invoice items is recreated.
   */
  private void updateInvoiceItemsFromInvoiceItemPanels() {
    // Check for any changes. If there are changes, recreate the complete list.
    boolean changes = false;
    
    if (object.getInvoiceItems().size() != invoiceItemPanels.size()) {
      changes = true;
    }
    
    if (!changes) {
      int index = 0;
      for (InvoiceItem invoiceItem: object.getInvoiceItems()) {
        InvoiceItemPanel invoiceItemPanel = invoiceItemPanels.get(index++);
        if (!PgUtilities.equals((Integer) invoiceItem.getNumberOfItems(), invoiceItemPanel.getNumberOfItemsObjectInput().getValue())  ||
            !PgUtilities.equals(invoiceItem.getDescription(), invoiceItemPanel.getDescriptionObjectInput().getValue())  ||
            !PgUtilities.equals(invoiceItem.getAmount(), invoiceItemPanel.getAmountObjectInput().getValue())  ||
            !PgUtilities.equals(invoiceItem.getRemarks(), invoiceItemPanel.getRemarksObjectInput().getValue())) {
          changes = true;
          if (Debug.ON) {
            LOGGER.severe("NumberOfItems: " + invoiceItem.getNumberOfItems() + ", " + invoiceItemPanel.getNumberOfItemsObjectInput().getValue());
            LOGGER.severe("Description: " + invoiceItem.getDescription() + ", " + invoiceItemPanel.getDescriptionObjectInput().getValue());
            LOGGER.severe("Amount: " + invoiceItem.getAmount() + ", " + invoiceItemPanel.getAmountObjectInput().getValue());
            LOGGER.severe("Remarks: " + invoiceItem.getRemarks() + ", " + invoiceItemPanel.getRemarksObjectInput().getValue());
          }
          break;
        }
      }
    }
    
    if (changes) {
      List<InvoiceItem> invoiceItems = object.getInvoiceItems();
      invoiceItems.clear();
      
      for (InvoiceItemPanel invoiceItemPanel: invoiceItemPanels) {
        InvoiceItem invoiceItem = INVOICES_AND_PROPERTIES_FACTORY.createInvoiceItem();
        updateInvoiceItemFromInvoiceItemPanel(invoiceItem, invoiceItemPanel);
        invoiceItems.add(invoiceItem);
      }
    }
  }

  /**
   * Update an {@code InvoiceItem} with the values of an {@code InvoiceItemPanel}.
   * 
   * @param invoiceItem the {@code InvoiceItem} to be updated
   * @param invoiceItemPanel the {@code InvoiceItemPanel} from which {@code invoiceItem} will be updated.
   */
  private void updateInvoiceItemFromInvoiceItemPanel(InvoiceItem invoiceItem, InvoiceItemPanel invoiceItemPanel) {
    if (invoiceItemPanel.getNumberOfItemsObjectInput().isFilledIn()) {
      invoiceItem.setNumberOfItems(invoiceItemPanel.getNumberOfItemsObjectInput().getValue());
    }
    
    if (invoiceItemPanel.getDescriptionObjectInput().isFilledIn()) {
      invoiceItem.setDescription(invoiceItemPanel.getDescriptionObjectInput().getValue());
    }
    
    if (invoiceItemPanel.getAmountObjectInput().isFilledIn()) {
      invoiceItem.setAmount(invoiceItemPanel.getAmountObjectInput().getValue());
    }
    
    if (invoiceItemPanel.getRemarksObjectInput().isFilledIn()) {
      invoiceItem.setRemarks(invoiceItemPanel.getRemarksObjectInput().getValue());
    }

  }

  /**
   * Update the {@code property} from the controls.
   */
  private void updatePropertyFromControls() {
    EmfUtil.setFeatureValue(property, INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Description(), propertyDescriptionObjectControl.getValue());
    EmfUtil.setFeatureValue(property, INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Brand(), propertyBrandObjectControl.getValue());
    EmfUtil.setFeatureValue(property, INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Type(), propertyTypeObjectControl.getValue());
    EmfUtil.setFeatureValue(property, INVOICES_AND_PROPERTIES_PACKAGE.getProperty_SerialNumber(), propertySerialNumberObjectControl.getValue());
    EmfUtil.setFeatureValue(property, INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Remarks(), propertyRemarksObjectControl.getValue());
    EmfUtil.setFeatureValue(property, INVOICES_AND_PROPERTIES_PACKAGE.getProperty_FromDate(), propertyFromDateObjectControl.getValue());
    EmfUtil.setFeatureValue(property, INVOICES_AND_PROPERTIES_PACKAGE.getProperty_UntilDate(), propertyUntilDateObjectControl.getValue());
    EmfUtil.setFeatureValue(property, INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Archive(), propertyArchiveObjectControl.getValue());
    
    updateDocumentReferencesFromDocumentReferencePanels();
    updatePictureReferencesFromDocumentReferencePanels();
  }
  
  /**
   * Update the document references of the {@code property} from the {@code documentReferencePanels}.
   * <p>
   * If there are any changes, the complete list of document references is recreated.
   */
  private void updateDocumentReferencesFromDocumentReferencePanels() {
    // Check for any changes. If there are changes, recreate the complete list.
    boolean changes = false;
    
    if (property.getDocuments().size() != documentReferencePanels.size()) {
      changes = true;
    }
    
    if (!changes) {
      int index = 0;
      for (FileReference fileReference: property.getDocuments()) {
        FileReferenceEditPanel fileReferencePanel = documentReferencePanels.get(index++);
        File file = fileReferencePanel.getFile();
        String fileName = file != null ? file.getAbsolutePath() : null;
        if ((!fileReference.getFile().equals(fileName))  ||
            (!fileReference.getTitle().equals(fileReferencePanel.getTitleObjectControl().getValue()))) {
          changes = true;
          break;
        }
      }
    }
    
    if (changes) {
      List<FileReference> fileReferences = property.getDocuments();
      fileReferences.clear();
      
      for (FileReferenceEditPanel fileReferencePanel: documentReferencePanels) {
        FileReference fileReference = TypesFactory.eINSTANCE.createFileReference();
        updateFileReferenceFromFileReferencePanel(fileReference, fileReferencePanel);
        fileReferences.add(fileReference);
      }
    }
  }
  
  /**
   * Update the picture references of the {@code property} from the {@code pictureReferencePanels}.
   * <p>
   * If there are any changes, the complete list of picture references is recreated.
   */
  private void updatePictureReferencesFromDocumentReferencePanels() {
    // Check for any changes. If there are changes, recreate the complete list.
    boolean changes = false;
    
    if (property.getPictures().size() != pictureReferencePanels.size()) {
      changes = true;
    }
    
    if (!changes) {
      int index = 0;
      for (FileReference fileReference: property.getPictures()) {
        FileReferenceEditPanel fileReferencePanel = pictureReferencePanels.get(index++);
        File file = fileReferencePanel.getFile();
        String fileName = file != null ? file.getAbsolutePath() : null;
        if ((!fileReference.getFile().equals(fileName))  ||
            (!fileReference.getTitle().equals(fileReferencePanel.getTitleObjectControl().getValue()))) {
          changes = true;
          break;
        }
      }
    }
    
    if (changes) {
      List<FileReference> fileReferences = property.getPictures();
      fileReferences.clear();
      
      for (FileReferenceEditPanel fileReferencePanel: pictureReferencePanels) {
        FileReference fileReference = TypesFactory.eINSTANCE.createFileReference();
        updateFileReferenceFromFileReferencePanel(fileReference, fileReferencePanel);
        fileReferences.add(fileReference);
      }
    }
  }

  /**
   * Update a {@code FileReference} from a {@code FileReferencePanel}.
   * 
   * @param fileReference the {@code FileReference} to be updated.
   * @param fileReferencePanel the {@code FileReferencePanel} from which the {@code fileReference} will be updated.
   */
  private void updateFileReferenceFromFileReferencePanel(FileReference fileReference, FileReferenceEditPanel fileReferencePanel) {
    String fileName = fileReferencePanel.getPathNameRelativeToPrefix();
    if (fileName != null) {
      fileReference.setFile(fileName);
    }

    if (fileReferencePanel.getTitleObjectControl().isFilledIn()) {
      fileReference.setTitle(fileReferencePanel.getTitleObjectControl().getValue());
    }    
  }

  /**
   * Fill the controls for the property with the values of a property.
   */
  private void fillControlsFromProperty() {
    if (property.isSetDescription()) {
      propertyDescriptionObjectControl.setValue(property.getDescription());
    }
    
    if (property.isSetBrand()) {
      propertyBrandObjectControl.setValue(property.getBrand());
    }
    
    if (property.isSetType()) {
      propertyTypeObjectControl.setValue(property.getType());
    }
    
    if (property.isSetSerialNumber()) {
      propertySerialNumberObjectControl.setValue(property.getSerialNumber());
    }
    
    if (property.isSetRemarks()) {
      propertyRemarksObjectControl.setValue(property.getRemarks());
    }
    
    if (property.isSetFromDate()) {
      propertyFromDateObjectControl.setValue(property.getFromDate());
    }
    
    if (property.isSetUntilDate()) {
      propertyUntilDateObjectControl.setValue(property.getUntilDate());
    }
    
    propertyArchiveObjectControl.setValue(property.isArchive());
    
    createDocumentReferencePanelsFromProperty();
    createPicturesPanelsFromProperty();
  }
  
  /**
   * Create document reference panels from the documents of the {@code property}.
   * <p>
   * For each document reference of the property a FileReferencePanel is created and
   * the content of the controls is set to the values of the document reference.
   */
  private void createDocumentReferencePanelsFromProperty() {
    for (FileReference fileReference: property.getDocuments()) {
      FileReferenceEditPanel fileReferencePanel = createNewDocumentReferencePanel(false);
      
      if (fileReference.isSetFile()) {
        fileReferencePanel.setPathNameRelativeToPrefix(fileReference.getFile());
      }
      
      if (fileReference.isSetTitle()) {
        fileReferencePanel.getTitleObjectControl().setValue(fileReference.getTitle());
      }
      
    }
  }
  
  /**
   * Create picture reference panels from the pictures of the {@code property}.
   * <p>
   * For each picture reference of the property a FileReferencePanel is created and
   * the content of the controls is set to the values of the picture reference.
   */
  private void createPicturesPanelsFromProperty() {
    for (FileReference fileReference: property.getPictures()) {
      FileReferenceEditPanel fileReferencePanel = createNewPictureReferencePanel(false);
      
      if (fileReference.isSetFile()) {
        fileReferencePanel.setPathNameRelativeToPrefix(fileReference.getFile());
      }
      
      if (fileReference.isSetTitle()) {
        fileReferencePanel.getTitleObjectControl().setValue(fileReference.getTitle());
      }
      
    }
  }

  /**
   * {@inheritDoc}
   * Create the GUI.
   * <p>
   * Left is invoice: top is invoice details, below that invoice items.
   * Right is property: top is property details and picture, below that documents and pictures.
   */
  @Override
  protected void createEditPanel(VBox rootPane) {    
    HBox invoiceAndPropertyHBox = componentFactory.createHBox(3.0, 3.0);
    
    VBox invoiceVBox = componentFactory.createVBox(0.0, 3.0);
    invoiceVBox.getChildren().addAll(createInvoicePanel(), createInvoiceItemsPanel());
    
    VBox propertyVBox = componentFactory.createVBox();
    propertyVBox.getChildren().addAll(createPropertyAndPicturePanel(), createDocumentsAndPicturesPanel());
    
    invoiceAndPropertyHBox.getChildren().addAll(invoiceVBox, propertyVBox);
    
    rootPane.getChildren().add(invoiceAndPropertyHBox);    
  }

  /**
   * Create the panel with the basic controls for an invoice.
   * 
   * @return the invoice panel.
   */
  private Node createInvoicePanel() {
    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    gridPane.setBorder(componentFactory.getRectangularBorder());
    
    Label label = componentFactory.createStrongLabel("Invoice");
    gridPane.add(label, 0, 0);
    
    int rowIndex = 1;
    
    addAttributeEditControlsToGrid(gridPane, rowIndex++, "Date", invoiceDateObjectControl);
    addAttributeEditControlsToGrid(gridPane, rowIndex++, "Company", invoiceCompanyObjectControl);
    addAttributeEditControlsToGrid(gridPane, rowIndex++, "Description", invoiceDescriptionObjectControl);
    addAttributeEditControlsToGrid(gridPane, rowIndex++, "Amount", invoiceAmountObjectControl);
    addAttributeEditControlsToGrid(gridPane, rowIndex++, "Remarks", invoiceRemarksObjectControl);
    addAttributeEditControlsToGrid(gridPane, rowIndex++, "Derive description from property", invoiceDescriptionFromPropertyObjectControl);    
    
    return gridPane;
  }

  /**
   * {@inheritDoc}
   * 
   */
  @Override
  protected void handleChanges() {
    handleInvoiceDescriptionType();

    updateActionButtonsPanel();
  }
  
  /**
   * {@inheritDoc}
   * Handle changes in any of the controls.
   * 
   */
  @Override
  protected void updateActionButtonsPanel() {
    if (Debug.ON) {
      LOGGER.info("=> ");
    }
    
    actionButtonsPanel.getChildren().clear();

    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    actionButtonsPanel.getChildren().add(spacer);
    
    EditStatus editStatus = determineEditStatus();  // This is the total status for invoice and property
    
    // Status label
    Label editStatusLabel = componentFactory.createStrongLabel(editStatus.getStatusIndicator());
    String fontName = editStatusLabel.getFont().getName();
    editStatusLabel.setFont(new Font(fontName, 22));
    if (editStatus == EditStatus.INVALID) {
      editStatusLabel.setTooltip(new Tooltip("At least one of the controls has an invalid value"));
    }
    actionButtonsPanel.getChildren().add(editStatusLabel);
    
    Button button;
    
    // Add or update invoice button
    if (editMode == EditMode.NEW) {
      button = componentFactory.createButton("Create invoice", "Create a new invoice based on the entered values");
      button.setOnAction(e -> addObjectAction());
      if (!objectControlsGroup.isValid()) {
        button.setDisable(true);
      }
    } else {
      button = componentFactory.createButton("Update invoice", "Update the invoice based on the entered values");
      button.setOnAction(e -> updateObject());
      EditStatus invoiceEditStatus = determineInvoiceEditStatus();
      if (invoiceEditStatus != EditStatus.CHANGES) {
        button.setDisable(true);
      }
    }
    actionButtonsPanel.getChildren().add(button);
    
    // Add or update property button
    if (editMode == EditMode.NEW) {
      button = componentFactory.createButton("Create property", "Create a new property based on the entered values");
      button.setOnAction(e -> addPropertyAction());
      if (!objectControlsGroup.isValid()) {
        button.setDisable(true);
      }
    } else {
      button = componentFactory.createButton("Update property", "Update the property based on the entered values");
      button.setOnAction(e -> updateProperty());
      EditStatus propertyEditStatus = determinePropertyEditStatus();
      if (propertyEditStatus != EditStatus.CHANGES) {
        button.setDisable(true);
      }
    }
    actionButtonsPanel.getChildren().add(button);
        
    // Add or update invoice and property button
    if (editMode == EditMode.NEW) {
      button = componentFactory.createButton("Create invoice & property", "Create a new invoice and its related property based on the entered values");
      button.setOnAction(e -> addInvoiceAndPropertyAction());
      if (!objectControlsGroup.isValid()) {
        button.setDisable(true);
      }
    } else {
      button = componentFactory.createButton("Update invoice & property", "Update the invoice and property based on the entered values");
      button.setOnAction(e -> updateInvoiceAndProperty());
      if (editStatus != EditStatus.CHANGES) {
        button.setDisable(true);
      }
    }
    actionButtonsPanel.getChildren().add(button);
    
    // New button
    button = componentFactory.createButton(newObjectText, newObjectTooltipText);
    button.setOnAction(e -> newObject(true));
    actionButtonsPanel.getChildren().add(button);
    
    // Cancel button
    button = componentFactory.createButton("Cancel", "Discard any changes and close the editor");
    button.setOnAction(e -> closeWindow());
    actionButtonsPanel.getChildren().add(button);
  }
  
  /**
   * {@inheritDoc}
   * <p>
   * For valid/invalid here we also take the {@code propertyObjectControlsGroup} into account.
   */
  @Override
  protected EditStatus determineEditStatus() {
    if (objectControlsGroup.isValid() && propertyObjectControlsGroup.isValid()) {
      if (editMode == EditMode.NEW) {
        return EditStatus.ADD;
      } else {
        if (changesInInput()) {
          return EditStatus.CHANGES;
        } else {
          return EditStatus.NO_CHANGES;
        }
      }
    } else {
      return EditStatus.INVALID;
    }
  }
  
  /**
   * Determine the edit status for the invoice part of the editor.
   * <p>
   * If any of the controls is invalid, the status is INVALID.
   * Otherwise, the status is CHANGES if there are any changes in the controls, or NO_CHANGES.
   * 
   * @return the newly determined EditStatus.
   */
  private EditStatus determineInvoiceEditStatus() {
    if (objectControlsGroup.isValid()) {
      if (editMode == EditMode.NEW) {
        return EditStatus.ADD;
      } else {
        if (objectControlsGroup.isAnyObjectChanged()) {
          return EditStatus.CHANGES;
        } else {
          return EditStatus.NO_CHANGES;
        }
      }
    } else {
      return EditStatus.INVALID;
    }
    
  }
  
  /**
   * Determine the edit status for the property part of the editor.
   * <p>
   * If any of the controls is invalid, the status is INVALID.
   * Otherwise, the status is CHANGES if there are any changes in the controls, or NO_CHANGES.
   * 
   * @return the newly determined EditStatus.
   */
  private EditStatus determinePropertyEditStatus() {
    if (propertyObjectControlsGroup.isValid()) {
      if (editMode == EditMode.NEW) {
        return EditStatus.ADD;
      } else {
        if (propertyObjectControlsGroup.isAnyObjectChanged()) {
          return EditStatus.CHANGES;
        } else {
          return EditStatus.NO_CHANGES;
        }
      }
    } else {
      return EditStatus.INVALID;
    }
    
  }
  
  /**
   * {@inheritDoc}
   * Check whether any value of the controls differs from the invoice/property value.
   * 
   * @return true if any value of the controls differs from the invoice/property value, false otherwise.
   */
  @Override
  protected boolean changesInInput() {    
    return objectControlsGroup.isAnyObjectChanged()  ||  propertyObjectControlsGroup.isAnyObjectChanged();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void installChangeListeners() {
    super.installChangeListeners();
    
    propertyObjectControlsGroup.addListener(observable -> handleChanges());
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
    if (invoiceDescriptionFromPropertyObjectControl.getValue()) {
      // Invoice description is derived from property.
      // Disable the description control and set is to the value derived from the property.
      invoiceDescriptionObjectControl.getControl().setDisable(true);
      setInvoiceDescriptionFromPropertyControls();
    } else {
      // Enable the description control, don't change the value.
      invoiceDescriptionObjectControl.getControl().setDisable(false);
    }
    
  }
  
  /**
   * Update the invoice description, if invoiceDescriptionFromProperty is set.
   * <p>
   * The description is a concatenation of the description, the brand and the type of the property.
   */
  private void setInvoiceDescriptionFromPropertyControls() {
      StringBuilder buf = new StringBuilder();
      boolean spaceNeeded = false;
      
      String value = propertyDescriptionObjectControl.getValue();
      if (value !=  null) {
        value = value.trim();
        if (!value.isEmpty()) {
          buf.append(value);
          spaceNeeded = true;
        }
      }
      
      value = propertyBrandObjectControl.getValue();
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
      
      value = propertyTypeObjectControl.getValue();
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
      
      invoiceDescriptionObjectControl.setValue(buf.toString());
  }

  /**
   * Create the panel for the invoice items.<br/>
   * <p>
   * Initially the list of invoice items will be empty.<br/>
   * Via a listener changes in the list of invoice item panels are handled:
   * <ul>
   *  <li>
   *   For added panels, a listener is added to the 'amount' control. If this changes {@code updateAmountObjectControl()} is called.
   *  </li>
   *  <li>
   *   for removed panels, the {@code ObjectControlGroup} of the panel is removed from the {@code objectControlsGroup} and any listeners
   *   are removed from the 'amount' control.
   *  </li>
   * </ul>
   * 
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
    invoiceItemPanels.addListener(new ListChangeListener<InvoiceItemPanel>() {

      @Override
      public void onChanged(Change<? extends InvoiceItemPanel> c) {
        while (c.next()) {
          if (c.wasPermutated()) {
            // No action needed here
          } else if (c.wasUpdated()) {
            // update item
          } else {
            // removed or updated
            for (InvoiceItemPanel invoiceItemPanel: c.getRemoved()) {
              objectControlsGroup.removeObjectControlGroup(invoiceItemPanel.getObjectControlGroup());
              ObjectControlCurrency invoiceItemAmountObjectInput = invoiceItemPanel.getAmountObjectInput();
              invoiceItemAmountObjectInput.removeListeners();
            }
            for (InvoiceItemPanel invoiceItemPanel: c.getAddedSubList()) {
              ObjectControlCurrency invoiceItemAmountObjectInput = invoiceItemPanel.getAmountObjectInput();
              invoiceItemAmountObjectInput.addListener((observable) -> updateAmountObjectControl());
            }
          }
        }
        
        updateInvoiceItemsPanel();
        updateAmountObjectControl();
        
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
   * 
   * @param expanded If true, the panel is expanded upon creation.
   * @return the created panel
   */
  private InvoiceItemPanel createNewInvoiceItemPanel(boolean expanded) {
    InvoiceItemPanel invoiceItemPanel = new InvoiceItemPanel(customization, invoiceItemPanels, expanded);
    objectControlsGroup.addObjectControlGroup(invoiceItemPanel.getObjectControlGroup());
    
    return invoiceItemPanel;
  }

  /**
   * Update the invoice items panel (the {@code invoiceItemsVBox}).
   */
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
  private void updateAmountObjectControl() {
    PgCurrency sumOfInvoiceItems = null;
    
    for (InvoiceItemPanel invoiceItemPanel: invoiceItemPanels) {
      ObjectControlCurrency itemAmountObjectInput = invoiceItemPanel.getAmountObjectInput();
      PgCurrency itemAmount = itemAmountObjectInput.getValue();
      if (itemAmount != null) {
        if (sumOfInvoiceItems == null) {
          sumOfInvoiceItems = itemAmount;
        } else {
          sumOfInvoiceItems = sumOfInvoiceItems.add(itemAmount.certifyCurrency(sumOfInvoiceItems.getCurrency()));
        }
      }
    }

    ObjectControlCurrency amountControl = (ObjectControlCurrency) invoiceAmountObjectControl;
    if (sumOfInvoiceItems == null) {
      amountControl.getControl().setDisable(false);
    } else {
      amountControl.getControl().setDisable(true);
      amountControl.setValue(sumOfInvoiceItems);
    }
  }
  
  /**
   * Create the Property and Picture view panel.
   * <p>
   * The left side is the Property panel, the right side the Picture view panel (showing the selected picture reference).
   * 
   * @return the created panel.
   */
  private Node createPropertyAndPicturePanel() {
    HBox hBox = componentFactory.createHBox();
    hBox.getChildren().addAll(createPropertyPanel(), createPictureViewPanel());
    
    return hBox;
  }

  /**
   * Create the Property panel.
   * 
   * @return the created panel
   */
  private Node createPropertyPanel() {
    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    gridPane.setBorder(componentFactory.getRectangularBorder());
    
    Label label = componentFactory.createStrongLabel("Property");
    gridPane.add(label, 0, 0);

    int rowIndex = 1;
    
    addAttributeEditControlsToGrid(gridPane, rowIndex++, "Description", propertyDescriptionObjectControl);
    addAttributeEditControlsToGrid(gridPane, rowIndex++, "Brand", propertyBrandObjectControl);
    addAttributeEditControlsToGrid(gridPane, rowIndex++, "Type", propertyTypeObjectControl);
    addAttributeEditControlsToGrid(gridPane, rowIndex++, "Serial number", propertySerialNumberObjectControl);
    addAttributeEditControlsToGrid(gridPane, rowIndex++, "Remarks", propertyRemarksObjectControl);
    addAttributeEditControlsToGrid(gridPane, rowIndex++, "From", propertyFromDateObjectControl);
    addAttributeEditControlsToGrid(gridPane, rowIndex++, "Until", propertyUntilDateObjectControl);
    addAttributeEditControlsToGrid(gridPane, rowIndex++, "Archive", propertyArchiveObjectControl);
    
    return gridPane;
  }

  /**
   * Create the Picture view panel.
   * 
   * @return the created panel
   */
  private Node createPictureViewPanel() {
    pictureViewPanel = componentFactory.createHBox(12.0);
    
    return pictureViewPanel;
  }
  
  /**
   * Update the Picture view panel.
   * 
   * @param fileName the file name of the picture to show.
   */
  private void updatePictureViewPanel(File file) {
    pictureViewPanel.getChildren().clear();
    
    Image image;
    try {
      image = new Image(new FileInputStream(file), 300, 300, true, true);
      ImageView imageView = new ImageView(image);
      
      pictureViewPanel.getChildren().add(imageView);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Create the document references and pictures references panel.
   * 
   * @return the created panel.
   */
  private Node createDocumentsAndPicturesPanel() {
    HBox hBox = componentFactory.createHBox();
    hBox.getChildren().addAll(createDocumentsPanel(), createPicturesPanel());
    
    return hBox;
  }
  
  /**
   * Create the document references panel.
   * 
   * @return the created panel.
   */
  private Node createDocumentsPanel() {
    VBox documentsMainVBox = componentFactory.createVBox(12.0, 12.0);
    documentsMainVBox.setMinSize(300, 500);
    documentsMainVBox.setBorder(componentFactory.getRectangularBorder());
    
    Label label = componentFactory.createStrongLabel("Documents");
    documentsMainVBox.getChildren().add(label);
    
    documentReferencesVBox = componentFactory.createVBox();
    documentReferencePanels.addListener(new ListChangeListener<FileReferenceEditPanel>() {

      @Override
      public void onChanged(Change<? extends FileReferenceEditPanel> c) {
        while (c.next()) {
          if (c.wasPermutated()) {
            // No action needed here
          } else if (c.wasUpdated()) {
            // update item
          } else {
            for (FileReferenceEditPanel documentReferencePanel: c.getRemoved()) {
              propertyObjectControlsGroup.removeObjectControlGroup(documentReferencePanel.getObjectControlsGroup());
            }
            for (FileReferenceEditPanel documentReferencePanel: c.getAddedSubList()) {
              propertyObjectControlsGroup.addObjectControlGroup(documentReferencePanel.getObjectControlsGroup());
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
  
  /**
   * Create a new document reference panel, which is a {@code FileReferencePanel}.
   * 
   * @param expand If true, the panel is expanded upon creation.
   * @return the created panel
   */
  private FileReferenceEditPanel createNewDocumentReferencePanel(boolean expand) {
    FileReferenceEditPanel documentReferencePanel = new FileReferenceEditPanel.FileReferencePanelBuilder(customization)
        .setDefaultPaneTitle("Document reference")
        .setExpandPaneOnCreation(true)
        .setInitialFolderSupplier(this::getPropertyRelatedFilesFolder)
        .setPrefix(InvoicesAndPropertiesRegistry.propertyRelatedFilesFolder)
        .build();
    
    objectControlsGroup.addObjectControlGroup(documentReferencePanel.getObjectControlsGroup());
    documentReferencePanels.add(documentReferencePanel);
    
    return documentReferencePanel;
  }
  
  private String getPropertyRelatedFilesFolder(FileReferenceTypeInfo fileReferenceTypeInfo) {
    // Try to get the folder from existing document and picture references.
    
    for (FileReferenceEditPanel fileReferencePanel: documentReferencePanels) {
      File file = fileReferencePanel.getFile();
      if (file != null) {
        return file.getParent();
      }
    }
    
    for (FileReferenceEditPanel fileReferencePanel: pictureReferencePanels) {
      File file = fileReferencePanel.getFile();
      if (file != null) {
        return file.getParent();
      }
    }
    
    return InvoicesAndPropertiesRegistry.propertyRelatedFilesFolder;
  }

  private void updateDocumentReferencesPanel() {
    documentReferencesVBox.getChildren().clear();
    for (FileReferenceEditPanel fileReferenceEditPanel: documentReferencePanels) {
      documentReferencesVBox.getChildren().add(fileReferenceEditPanel.getControl());
    }
  }
  
  private Node createPicturesPanel() {
    VBox picturesMainVBox = componentFactory.createVBox(12.0, 12.0);
    picturesMainVBox.setMinSize(300, 500);
    picturesMainVBox.setBorder(componentFactory.getRectangularBorder());
    
    Label label = componentFactory.createStrongLabel("Pictures");
    picturesMainVBox.getChildren().add(label);
    
    pictureReferencesVBox = componentFactory.createVBox();
    pictureReferencePanels.addListener(new ListChangeListener<FileReferenceEditPanel>() {

      @Override
      public void onChanged(Change<? extends FileReferenceEditPanel> c) {
        while (c.next()) {
          if (c.wasPermutated()) {
            // No action needed here
          } else if (c.wasUpdated()) {
            // update item
          } else {
            for (FileReferenceEditPanel pictureReferencePanel: c.getRemoved()) {
              propertyObjectControlsGroup.removeObjectControlGroup(pictureReferencePanel.getObjectControlsGroup());
            }
            for (FileReferenceEditPanel pictureReferencePanel: c.getAddedSubList()) {
              propertyObjectControlsGroup.addObjectControlGroup(pictureReferencePanel.getObjectControlsGroup());
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
  
  private FileReferenceEditPanel createNewPictureReferencePanel(boolean expand) {
    FileReferenceEditPanel pictureReferencePanel = new FileReferenceEditPanel.FileReferencePanelBuilder(customization)
        .setDefaultPaneTitle("Picture reference")
        .setExpandPaneOnCreation(true)
        .setInitialFolderSupplier(this::getPropertyRelatedFilesFolder)
        .build();
    
    ChangeListener<Boolean> cl = new ChangeListener<Boolean>() {

      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue) {
          File file = pictureReferencePanel.getFile();
          if (file != null) {
            updatePictureViewPanel(file);
          }
        }
        
      }
    };
    pictureReferencePanel.getControl().focusedProperty().addListener(cl);
    
    objectControlsGroup.addObjectControlGroup(pictureReferencePanel.getObjectControlsGroup());
    pictureReferencePanels.add(pictureReferencePanel);
    
    return pictureReferencePanel;
  }

  private void updatePictureReferencesPanel() {
    pictureReferencesVBox.getChildren().clear();
    for (FileReferenceEditPanel fileReferenceEditPanel: pictureReferencePanels) {
      pictureReferencesVBox.getChildren().add(fileReferenceEditPanel.getControl());
    }
  }

  /**
   * 
   */
  private  void addPropertyAction() {
      createProperty();
      updatePropertyFromControls();
      invoicesAndProperties.getProperties().getProperties().add(property);
      
      updateActionButtonsPanel();
  }
  
  private void addInvoiceAndPropertyAction() {
    createObject();
    createProperty();

    updateObjectFromControls();
    updatePropertyFromControls();
    object.setPurchase(property);
    
    invoicesAndProperties.getInvoices().getInvoices().add(object);
    invoicesAndProperties.getProperties().getProperties().add(property);

    updateActionButtonsPanel();    
  }
  
  /**
   * {@inheritDoc}
   * Update {@code property} with the values of the controls.
   */
  protected void updateProperty() {
    updatePropertyFromControls();
    
    setObject(object, false, false);
  }
  
  private void updateInvoiceAndProperty() {
    updateObjectFromControls();
    updatePropertyFromControls();

    updateActionButtonsPanel();    
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void createObject() {
    object = INVOICES_AND_PROPERTIES_FACTORY.createInvoice();
  }

  private void createProperty() {
    property = INVOICES_AND_PROPERTIES_FACTORY.createProperty();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void addObjectToCollection() {
    invoicesAndProperties.getInvoices().getInvoices().add(object);
  }


  /*
   * Add the controls (Label and ObjectInput) for one attribute to the gridPane.
   * 
   * @param gridPane The GridPane to add the controls to.
   * @param rowIndex Index for the row in the GridPane to which the controls are to be added.
   * @param eObjectAttributeEditDescriptor EObjectAttributeEditDescriptor for the attribute.
   */
  private void addAttributeEditControlsToGrid(GridPane gridPane, int rowIndex, String labelText, ObjectControl<?> objectControl) {
    // Label
    StringBuilder buf = new StringBuilder();
    buf.append(labelText);
    if (!objectControl.isOptional()) {
      buf.append(" *");
    }
    buf.append(":");
    Label label = componentFactory.createLabel(buf.toString());
    gridPane.add(label, 0, rowIndex);
    
    // ObjectInput control
    ObjectControl<?> objectInput = objectControl;
    gridPane.add(objectInput.getControl(), 1, rowIndex); 
    
    // Ok/Not OK label
    Node statusIndicator = objectControl.getStatusIndicator();
    gridPane.add(statusIndicator, 2, rowIndex);
  }
}

