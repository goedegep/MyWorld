package goedegep.invandprop.app.guifx;

import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import goedegep.invandprop.app.InvoicesAndPropertiesRegistry;
import goedegep.invandprop.app.InvoicesAndPropertiesService;
import goedegep.invandprop.model.InvAndPropFactory;
import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.InvoiceAndProperty;
import goedegep.invandprop.model.InvoiceAndPropertyItem;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditPanelTemplate;
import goedegep.jfx.editor.EditorException;
import goedegep.jfx.editor.controls.EditorControlBoolean;
import goedegep.jfx.editor.controls.EditorControlCurrency;
import goedegep.jfx.editor.controls.EditorControlFlexDate;
import goedegep.jfx.editor.controls.EditorControlString;
import goedegep.jfx.editor.panels.FileReferenceTypeInfo;
import goedegep.jfx.editor.panels.FileReferencesEditPanel;
import goedegep.jfx.editor.panels.FileReferencesEditPanel.FileReferencesEditPanelBuilder;
import goedegep.types.model.FileReference;
import goedegep.util.emf.EmfUtil;
import goedegep.util.file.FileUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class is the main edit panel for the InvoiceAndPropertyEditor.
 */
public class InvoiceAndPropertyEditPanel extends EditPanelTemplate<InvoiceAndProperty> {
  
  private static InvAndPropPackage INV_AND_PROP_PACKAGE = InvAndPropPackage.eINSTANCE;
  private static InvAndPropFactory INV_AND_PROP_FACTORY = InvAndPropFactory.eINSTANCE;
  
  /**
   * A service used for adding invoices and properties.
   */
  private InvoicesAndPropertiesService invoicesAndPropertiesService;
  
  /**
   * The invoices and properties registry.
   */
  private InvoicesAndPropertiesRegistry invoicesAndPropertiesRegistry = InvoicesAndPropertiesRegistry.getInstance();
  
  /**
   * Editor controls
   */
  
  /**
   * {@code EditorControl} for the date of the InvoiceAndProperty.
   */
  private EditorControlFlexDate dateControl;
  
  /**
   * {@code EditorControl} for the company of the InvoiceAndProperty.
   */
  private EditorControlString companyControl;
  
  /**
   * {@code EditorControl} for the description of the InvoiceAndProperty.
   */
  private EditorControlString descriptionControl;
  
  /**
   * {@code EditorControl} for the brand of the InvoiceAndProperty.
   */
  private EditorControlString brandControl;
  
  /**
   * {@code EditorControl} for the type of the InvoiceAndProperty.
   */
  private EditorControlString typeControl;
  
  /**
   * {@code EditorControl} for the serial number of the InvoiceAndProperty.
   */
  private EditorControlString serialNumberControl;
  
  /**
   * {@code EditorControl} for the amount of the InvoiceAndProperty.
   */
  private EditorControlCurrency amountControl;
  
  /**
   * {@code EditorControl} for the from date of the InvoiceAndProperty.
   */
  private EditorControlFlexDate fromDateControl;
  
  /**
   * {@code EditorControl} for the until date of the InvoiceAndProperty.
   */
  private EditorControlFlexDate untilDateControl;
  
  /**
   * {@code EditorControl} for the remarks on the InvoiceAndProperty.
   */
  private EditorControlString remarksControl;
  
  /**
   * {@code EditorControl} for the archived property of the InvoiceAndProperty.
   */
  private EditorControlBoolean archiveControl;
    
  /**
   * {@code EditPanel} for the invoice and property documents
   */
  private FileReferencesEditPanel documentsEditPanel;

  /**
   * {@code EditPanel} for the invoice and property pictures
   */
  private FileReferencesEditPanel picturesEditPanel;
  
  /**
   * {@code EditPanel} for the invoice and property items
   */
  private InvoiceAndPropertyItemsPanel invoiceAndPropertyItemsPanel;
  
  

  /**
   * Other GUI controls
   */
  

  /**
   * {@code EditorControl} for the folder where files related to the invoice and property are stored.
   */
  private EditorControlString attachmentsFolderControl;
  
  /**
   * A {@code Button} to create (if it doesn't exist yet) or open the attachments folder.
   */
  private Button createOrOpenAttachmentsFolderButton;
  
  /**
   * Preview of selected picture
   */
  private ImageView selectedPictureImageView;
  
  /**
   * Main edit panel
   */
  private VBox mainPane;
  
  /*
   * Anything else
   */
  
  /**
   * The generated or actual attachments folder (the folder where attachments of the {@code InvoiceAndProperty} are located).
   * <p>
   * If there is an attachment with a file reference to a sub folder of the InvoicesAndProperties Folder, this is the attachments folder.
   * Otherwise if there is a date and and description, the folder is derived from these values.
   * In both cases the folder itself may exist or not.
   */
  private SimpleObjectProperty<Path> attachmentsFolderPathProperty;


  /**
   * Create an instance of the {@code InvoiceAndPropertyEditPanel}.
   * 
   * @param customization the GUI customization
   * @param invoicesAndPropertiesService a service used for adding invoices and properties.
   * @return the newly created {@code InvoiceAndPropertyEditPanel}.
   */
  public static InvoiceAndPropertyEditPanel newInstance(CustomizationFx customization, InvoicesAndPropertiesService invoicesAndPropertiesService) {
    InvoiceAndPropertyEditPanel invoiceAndPropertyEditPanel = new InvoiceAndPropertyEditPanel(customization, invoicesAndPropertiesService);
    invoiceAndPropertyEditPanel.performInitialization();
    
    return invoiceAndPropertyEditPanel;
  }
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization
   * @param invoicesAndPropertiesService a service used for adding invoices and properties.
   */
  private InvoiceAndPropertyEditPanel(CustomizationFx customization, InvoicesAndPropertiesService invoicesAndPropertiesService) {
    super(customization, false);
    
    this.invoicesAndPropertiesService = invoicesAndPropertiesService;
    invoicesAndPropertiesRegistry = InvoicesAndPropertiesRegistry.getInstance();
    
    setId("InvoiceAndPropertyEditPanel");
  }

  @Override
  public void createControls() {
    
    dateControl = new EditorControlFlexDate.FlexDateBuilder("date")
        .setWidth(300d)
        .setLabelBaseText("Date")
        .setToolTipText("Date")
        .setOptional(true)
        .setErrorTextSupplier(() -> "The date is not filled in")
        .build();
    
    dateControl.setErrorTextSupplier(() -> "The date is not filled in");
    
    companyControl = new EditorControlString.Builder("company")
        .setWidth(300d)
        .setLabelBaseText("Company")
        .setToolTipText("Company")
        .setOptional(true)
        .setErrorTextSupplier(() -> "The company is not filled in")
        .build();
    
    descriptionControl = new EditorControlString.Builder("description")
        .setWidth(300d)
        .setLabelBaseText("Description")
        .setToolTipText("Description")
        .setErrorTextSupplier(() -> "The description is not filled in")
        .build();
    
    brandControl = new EditorControlString.Builder("brand")
        .setWidth(300d)
        .setLabelBaseText("Brand")
        .setToolTipText("Brand")
        .setOptional(true)
        .build();
    
    typeControl = new EditorControlString.Builder("type")
        .setWidth(300d)
        .setLabelBaseText("Type")
        .setToolTipText("Type")
        .setOptional(true)
        .build();
    
    serialNumberControl = new EditorControlString.Builder("serialNumber")
        .setWidth(300d)
        .setLabelBaseText("Serial number")
        .setToolTipText("Serial number")
        .setOptional(true)
        .build();
    
    amountControl = new EditorControlCurrency.CurrencyBuilder("amount")
        .setWidth(300d)
        .setLabelBaseText("Amount")
        .setToolTipText("Amount")
        .setOptional(true)
        .build();
            
    fromDateControl = new EditorControlFlexDate.FlexDateBuilder("fromDate")
        .setWidth(300d)
        .setLabelBaseText("From date")
        .setToolTipText("From date")
        .setOptional(true)
        .build();
    
    
    untilDateControl = new EditorControlFlexDate.FlexDateBuilder("untilDate")
        .setWidth(300d)
        .setLabelBaseText("Until date")
        .setToolTipText("Until date")
        .setOptional(true)
        .build();
        
    remarksControl = new EditorControlString.Builder("remarks")
        .setWidth(300d)
        .setLabelBaseText("Remarks")
        .setToolTipText("Remarks")
        .setOptional(true)
        .build();
        
    archiveControl = new EditorControlBoolean.Builder("archived")
        .setCustomization(customization)
        .setLabelBaseText("Archived")
        .setToolTipText("Archived")
        .build();
    
    documentsEditPanel = new FileReferencesEditPanelBuilder(customization)
        .setReferencesEditPanelTitle("Documents")
        .setReferenceEditPanelTitle("Document")
        .setAddFileReferenceButtonText("Add document")
        .setAddFileReferenceButtonTooltipText("Add a document")
        .setInitialFolderSupplier(this::getInitialFolder)
        .setPrefix(invoicesAndPropertiesRegistry.getPropertyRelatedFilesFolder())
        .build();
    documentsEditPanel.setId("documents");
    documentsEditPanel.addValueAndOrStatusChangeListener((_, _) -> updateAttachmentsFolderPathProperty());
    
    picturesEditPanel = new FileReferencesEditPanelBuilder(customization)
        .setReferencesEditPanelTitle("Pictures")
        .setReferenceEditPanelTitle("Picture")
        .setAddFileReferenceButtonText("Add picture")
        .setAddFileReferenceButtonTooltipText("Add a picture")
        .setInitialFolderSupplier(this::getInitialFolder)
        .setPrefix(invoicesAndPropertiesRegistry.getPropertyRelatedFilesFolder())
        .build();
    documentsEditPanel.setId("pictures");
    documentsEditPanel.addValueAndOrStatusChangeListener((_, _) -> updateAttachmentsFolderPathProperty());
    
    invoiceAndPropertyItemsPanel = InvoiceAndPropertyItemsPanel.newInstance(customization, invoicesAndPropertiesService);
    invoiceAndPropertyItemsPanel.setId("invoiceAndPropertyItemsPanel");
   
    registerEditorComponents(dateControl, companyControl, descriptionControl, brandControl, typeControl, serialNumberControl,
        amountControl, fromDateControl, untilDateControl, remarksControl, archiveControl, invoiceAndPropertyItemsPanel);
    
    
    attachmentsFolderPathProperty = new SimpleObjectProperty<>();
    attachmentsFolderPathProperty.addListener((_) -> updateAttachmentsFolderInfo());
    
    attachmentsFolderControl = new EditorControlString.Builder("attachments folder")
        .setWidth(300d)
        .setLabelBaseText("Attachments folder")
        .setToolTipText("Folder where invoice and property documents and pictures are stored")
        .setOptional(true)
        .build();
    
//    attachmentsFolderControl = componentFactory.createEditorControlString(300, true, "Folder where invoice and property documents and pictures are stored");
//    attachmentsFolderControl.setId("attachments folder");
//    attachmentsFolderControl.setLabelBaseText("Attachments folder");
    attachmentsFolderControl.getControl().setEditable(false);
    
    
    createOrOpenAttachmentsFolderButton = componentFactory.createButton("", "");
    createOrOpenAttachmentsFolderButton.setId("create or open attachments folder button");
    
    selectedPictureImageView = new ImageView();
    selectedPictureImageView.setFitWidth(400);
    selectedPictureImageView.setFitHeight(400);
    selectedPictureImageView.setPreserveRatio(true);
    
    picturesEditPanel.addObjectSelectionListener((_, fileReference) -> {
      if (fileReference != null  &&  fileReference.getFile() != null) {
        Image image = new Image("file:" + FileUtils.createPathNameFromPrefixAndFileName(invoicesAndPropertiesRegistry.getPropertyRelatedFilesFolder(), fileReference.getFile()));
        selectedPictureImageView.setImage(image);
      }
    });
  }

  @Override
  public Node getControl() {
    return mainPane;
  }

  @Override
  protected void fillControlsFromObject() {
    if (value == null) {
      return;
    }
    
    dateControl.setObject(value.getDate());
    companyControl.setObject(value.getCompany());
    descriptionControl.setObject(value.getDescription());
    brandControl.setObject(value.getBrand());
    typeControl.setObject(value.getType());
    serialNumberControl.setObject(value.getSerialNumber());
    amountControl.setObject(value.getAmount());
    fromDateControl.setObject(value.getFromDate());
    untilDateControl.setObject(value.getUntilDate());
    remarksControl.setObject(value.getRemarks());
    archiveControl.setObject(value.isArchive());
    
    documentsEditPanel.setObject(value.getDocuments());
    picturesEditPanel.setObject(value.getPictures());
    
    invoiceAndPropertyItemsPanel.setObject(value.getInvoiceandpropertyitems());
    
    updateAttachmentsFolderPathProperty();
  }

  @Override
  public String getValueAsFormattedText() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Main pane is an HBox; left are the attributes, right is the attachments information.
   * The attachments information is a VBox; top is attachment folder controls, bottom is an HBox with the documents and pictures.
   */
  @Override
  protected void createEditPanel() {
    mainPane = componentFactory.createVBox();
    
    HBox invoiceAndPropertyPane = componentFactory.createHBox();
    
    HBox attributesBox = componentFactory.createHBox();

    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);

    // Date
    gridPane.add(dateControl.getLabel(), 0, 0);
    gridPane.add(dateControl.getControl(), 1, 0);
    gridPane.add(dateControl.getStatusIndicator(), 2, 0);

    // Company
    gridPane.add(companyControl.getLabel(), 0, 1);
    gridPane.add(companyControl.getControl(), 1, 1);
    gridPane.add(companyControl.getStatusIndicator(), 2, 1);

    // Description
    gridPane.add(descriptionControl.getLabel(), 0, 2);
    gridPane.add(descriptionControl.getControl(), 1, 2);
    gridPane.add(descriptionControl.getStatusIndicator(), 2, 2);

    // Brand
    gridPane.add(brandControl.getLabel(), 0, 3);
    gridPane.add(brandControl.getControl(), 1, 3);
    gridPane.add(brandControl.getStatusIndicator(), 2, 3);

    // Type
    gridPane.add(typeControl.getLabel(), 0, 4);
    gridPane.add(typeControl.getControl(), 1, 4);
    gridPane.add(typeControl.getStatusIndicator(), 2, 4);

    // Serial number
    gridPane.add(serialNumberControl.getLabel(), 0, 5);
    gridPane.add(serialNumberControl.getControl(), 1, 5);
    gridPane.add(serialNumberControl.getStatusIndicator(), 2, 5);

    // Amount
    gridPane.add(amountControl.getLabel(), 0, 6);
    gridPane.add(amountControl.getControl(), 1, 6);
    gridPane.add(amountControl.getStatusIndicator(), 2, 6);

    // From date
    gridPane.add(fromDateControl.getLabel(), 0, 7);
    gridPane.add(fromDateControl.getControl(), 1, 7);
    gridPane.add(fromDateControl.getStatusIndicator(), 2, 7);

    // Until date
    gridPane.add(untilDateControl.getLabel(), 0, 8);
    gridPane.add(untilDateControl.getControl(), 1, 8);
    gridPane.add(untilDateControl.getStatusIndicator(), 2, 8);

    // Remarks
    gridPane.add(remarksControl.getLabel(), 0, 9);
    gridPane.add(remarksControl.getControl(), 1, 9);
    gridPane.add(remarksControl.getStatusIndicator(), 2, 9);

    // Archived
    gridPane.add(archiveControl.getLabel(), 0, 10);
    gridPane.add(archiveControl.getControl(), 1, 10);
    gridPane.add(archiveControl.getStatusIndicator(), 2, 10);
    
    attributesBox.getChildren().add(gridPane);

    invoiceAndPropertyPane.getChildren().add(attributesBox);
    
    VBox attachmentsInfoBox = componentFactory.createVBox();
    
    HBox attachmentsFolderControlsBox = componentFactory.createHBox(12.0, 12.0);
    attachmentsFolderControlsBox.setBorder(componentFactory.getRectangularBorder());
    attachmentsFolderControlsBox.getChildren().add(attachmentsFolderControl.getLabel());
    attachmentsFolderControlsBox.getChildren().add(attachmentsFolderControl.getControl());
    attachmentsFolderControlsBox.getChildren().add(createOrOpenAttachmentsFolderButton);
    attachmentsInfoBox.getChildren().add(attachmentsFolderControlsBox);
    
    HBox documentsAndPicturesHBox = componentFactory.createHBox(12.0, 12.0);
    documentsAndPicturesHBox.getChildren().addAll(documentsEditPanel.getControl(), picturesEditPanel.getControl(), selectedPictureImageView);
    attachmentsInfoBox.getChildren().add(documentsAndPicturesHBox);
    
    invoiceAndPropertyPane.getChildren().add(attachmentsInfoBox);

    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setMaxHeight(600);
    scrollPane.setPrefHeight(600);
    scrollPane.setContent(invoiceAndPropertyItemsPanel.getControl());
    mainPane.getChildren().addAll(invoiceAndPropertyPane, scrollPane);
  }

  @Override
  protected void fillObjectFromControls(InvoiceAndProperty object, boolean getCurrentValue) throws EditorException {
    EmfUtil.setFeatureValue(object, INV_AND_PROP_PACKAGE.getInvoiceAndProperty_Date(), dateControl.getValue());
    EmfUtil.setFeatureValue(object, INV_AND_PROP_PACKAGE.getInvoiceAndProperty_Company(), companyControl.getValue());
    EmfUtil.setFeatureValue(object, INV_AND_PROP_PACKAGE.getInvoiceAndPropertyItem_Description(), descriptionControl.getValue());
    EmfUtil.setFeatureValue(object, INV_AND_PROP_PACKAGE.getInvoiceAndPropertyItem_Brand(), brandControl.getValue());
    EmfUtil.setFeatureValue(object, INV_AND_PROP_PACKAGE.getInvoiceAndPropertyItem_Type(), typeControl.getValue());
    EmfUtil.setFeatureValue(object, INV_AND_PROP_PACKAGE.getInvoiceAndPropertyItem_SerialNumber(), serialNumberControl.getValue());
    EmfUtil.setFeatureValue(object, INV_AND_PROP_PACKAGE.getInvoiceAndPropertyItem_Amount(), amountControl.getValue());
    EmfUtil.setFeatureValue(object, INV_AND_PROP_PACKAGE.getInvoiceAndPropertyItem_FromDate(), fromDateControl.getValue());
    EmfUtil.setFeatureValue(object, INV_AND_PROP_PACKAGE.getInvoiceAndPropertyItem_UntilDate(), untilDateControl.getValue());
    EmfUtil.setFeatureValue(object, INV_AND_PROP_PACKAGE.getInvoiceAndPropertyItem_Remarks(), remarksControl.getValue());
    EmfUtil.setFeatureValue(object, INV_AND_PROP_PACKAGE.getInvoiceAndPropertyItem_Archive(), archiveControl.getValue());
       
    if (getCurrentValue) {
      List<FileReference> documents = documentsEditPanel.getCurrentValue();
      object.getDocuments().clear();
      object.getDocuments().addAll(documents);
    } else {
      documentsEditPanel.accept();
    }

    if (getCurrentValue) {
      List<FileReference> pictures = picturesEditPanel.getCurrentValue();
      object.getPictures().clear();
      object.getPictures().addAll(pictures);
    } else {
      picturesEditPanel.accept();
    }
    
    if (getCurrentValue) {
      List<InvoiceAndPropertyItem> invoiceAndPropertyItems = invoiceAndPropertyItemsPanel.getCurrentValue();
      object.getInvoiceandpropertyitems().clear();
      object.getInvoiceandpropertyitems().addAll(invoiceAndPropertyItems);
    } else {
      invoiceAndPropertyItemsPanel.accept();
    }
  }

  @Override
  protected void setErrorFeedback(boolean valid) {
    // TODO Auto-generated method stub
    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected InvoiceAndProperty createObject() {
    return INV_AND_PROP_FACTORY.createInvoiceAndProperty();
  }
  
  /**
   * Update the {@code eventFolderPathProperty}.
   * @param valueChanged
   * @param statusChanged
   */
  private void updateAttachmentsFolderPathProperty() {
    String attachmentsFolderName = null;
    try {
      attachmentsFolderName = invoicesAndPropertiesService.determineAttachmentsFolderName(getCurrentValue());
    } catch (EditorException e) {
      e.printStackTrace();
    }
    attachmentsFolderPathProperty.set(attachmentsFolderName != null ? Paths.get(attachmentsFolderName) : null);
  }
  
  /**
   * Update information related to the invoice and property folder.
   * <p>
   * This method is to be called when this information might have changed.
   * This is the case if:
   * <ul>
   * <li>The folder name is changed</li>
   * <li>There is a changed in the file system below the events folder</li>
   * </ul>
   */
  private void updateAttachmentsFolderInfo() {
    Path attachmentsFolder = attachmentsFolderPathProperty.get();
    
    String attachmentsFolderName = null;
    String toolTipText = "Folder where invoice and property attachments are stored";
    if (attachmentsFolder != null) {
      attachmentsFolderName = FileUtils.getPathRelativeToFolder(invoicesAndPropertiesRegistry.getPropertyRelatedFilesFolder(), attachmentsFolder.toString());
      toolTipText = "Attachments of the invoice and property are stored in the folder: " + attachmentsFolder.toString();
    }
    attachmentsFolderControl.setObject(attachmentsFolderName);
    attachmentsFolderControl.getControl().setTooltip(new Tooltip(toolTipText));
    
    if (attachmentsFolder != null  &&  Files.exists(attachmentsFolder)) {
      createOrOpenAttachmentsFolderButton.setText("Open");
      createOrOpenAttachmentsFolderButton.setOnAction((_) -> {
        try {
          Desktop.getDesktop().open(attachmentsFolder.toFile());
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      });
    } else {
      createOrOpenAttachmentsFolderButton.setText("Create");
      createOrOpenAttachmentsFolderButton.setOnAction((_) -> {
        createAttachmentsFolder(null);
      });
      createOrOpenAttachmentsFolderButton.setDisable(attachmentsFolder == null);
    }
    
  }
    
  /**
   * Get the initial folder to be set as initial folder in a FolderChooser.
   * <p>
   * The initial folder depends on the tag specified in a FileReferenceTypeInfo.
   * 
   * @param fileReferenceTypeInfo
   * @return
   */
  private String getInitialFolder(FileReferenceTypeInfo fileReferenceTypeInfo) {
    return getAttachmentsFolder();
  }
  
  /**
   * Get the event related files folder, or if this is not available, the Events Folder.
   * 
   * @return the event related files folder, or if this is not available, the Events Folder.
   */
  private String getAttachmentsFolder() {
    if (attachmentsFolderPathProperty.get() != null) {
      return attachmentsFolderPathProperty.get().toString();
    } else {
      return invoicesAndPropertiesRegistry.getPropertyRelatedFilesFolder();
    }
  }
  
  /**
   * Create a folder for the files related to an event.
   */
  private void createAttachmentsFolder(ActionEvent event) {
    if (attachmentsFolderPathProperty.get() != null) {
      try {
        Files.createDirectory(attachmentsFolderPathProperty.get());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
