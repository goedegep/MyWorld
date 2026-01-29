package goedegep.invandprop.gui;

import java.util.List;

import goedegep.invandprop.logic.InvoicesAndPropertiesRegistry;
import goedegep.invandprop.model.InvAndPropFactory;
import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.InvoiceAndPropertyItem;
import goedegep.invandprop.svc.InvoicesAndPropertiesService;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditPanelTemplate;
import goedegep.jfx.editor.EditorException;
import goedegep.jfx.editor.controls.EditorControlBoolean;
import goedegep.jfx.editor.controls.EditorControlCurrency;
import goedegep.jfx.editor.controls.EditorControlFlexDate;
import goedegep.jfx.editor.controls.EditorControlString;
import goedegep.jfx.editor.panels.FileReferencesEditPanel;
import goedegep.jfx.editor.panels.FileReferencesEditPanel.FileReferencesEditPanelBuilder;
import goedegep.types.model.FileReference;
import goedegep.util.emf.EmfUtil;
import goedegep.util.file.FileUtils;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class is the edit panel for an {@code InvoiceAndPropertyItem}.
 */
public class InvoiceAndPropertyItemPanel extends EditPanelTemplate<InvoiceAndPropertyItem> {
  private static InvAndPropPackage INV_AND_PROP_PACKAGE = InvAndPropPackage.eINSTANCE;
  
  private InvoicesAndPropertiesRegistry invoicesAndPropertiesRegistry;
  
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
   * Preview of selected picture
   */
  private ImageView selectedPictureImageView;
  
  /**
   * Top level panel.
   */
  protected TitledPane titledPane;
  
  
  /**
   * Factory method to create a new {@code InvoiceAndPropertyItemPanel} instance.
   * 
   * @param customization The GUI customization.
   * @return a new {@code InvoiceAndPropertyItemPanel} instance.
   */
  public static InvoiceAndPropertyItemPanel newInstance(CustomizationFx customization) {
    InvoiceAndPropertyItemPanel invoiceAndPropertyItemPanel = new InvoiceAndPropertyItemPanel(customization);
    
    invoiceAndPropertyItemPanel.performInitialization();
    
    return invoiceAndPropertyItemPanel;
  }
  
  /**
   * Constructor
   * <p>
   * @param customization The GUI customization.
   */
  private InvoiceAndPropertyItemPanel(CustomizationFx customization) {
    super(customization, true);
            
    invoicesAndPropertiesRegistry = InvoicesAndPropertiesRegistry.getInstance();
    value = InvAndPropFactory.eINSTANCE.createInvoiceAndPropertyItem();  // TODO shall there be a createObject() ?
  }

  @Override
  public void createControls() {
    
    descriptionControl = new EditorControlString.Builder("itemDescription")
        .setWidth(300d)
        .setLabelBaseText("Description")
        .setToolTipText("Description")
        .setErrorTextSupplier(() -> "The description is not filled in")
        .build();
    
    brandControl = new EditorControlString.Builder("itemBrand")
        .setWidth(300d)
        .setLabelBaseText("Brand")
        .setToolTipText("Brand")
        .setOptional(true)
        .build();
    
    typeControl = new EditorControlString.Builder("itemType")
        .setWidth(300d)
        .setLabelBaseText("Type")
        .setToolTipText("Type")
        .setOptional(true)
        .build();
    
    serialNumberControl = new EditorControlString.Builder("itemSerialNumber")
        .setWidth(300d)
        .setLabelBaseText("Serial number")
        .setToolTipText("Serial number")
        .setOptional(true)
        .build();

    
    amountControl = new EditorControlCurrency.CurrencyBuilder("itemAmount")
        .setWidth(300d)
        .setLabelBaseText("Amount")
        .setToolTipText("Amount")
        .setOptional(true)
        .build();
    
    fromDateControl = new EditorControlFlexDate.FlexDateBuilder("itemFromDate")
        .setWidth(300d)
        .setLabelBaseText("From date")
        .setToolTipText("From date")
        .setOptional(true)
        .build();
    
    untilDateControl = new EditorControlFlexDate.FlexDateBuilder("itemUntilDate")
        .setWidth(300d)
        .setLabelBaseText("Until date")
        .setToolTipText("Until date")
        .setOptional(true)
        .build();
    
    remarksControl = new EditorControlString.Builder("itemRemarks")
        .setWidth(300d)
        .setLabelBaseText("Remarks")
        .setToolTipText("Remarks")
        .setOptional(true)
        .build();
    
//    remarksControl = componentFactory.createEditorControlString(300, true, "Remarks");
//    remarksControl.setId("itemRemarks");
//    remarksControl.setLabelBaseText("Remarks");
    
    
    archiveControl = new EditorControlBoolean.Builder("itemArchived")
        .setCustomization(customization)
        .setLabelBaseText("Archived")
        .setToolTipText("Archived")
        .build();
    
    documentsEditPanel = new FileReferencesEditPanelBuilder(customization)
        .setReferencesEditPanelTitle("Documents")
        .setReferenceEditPanelTitle("Document")
        .setAddFileReferenceButtonText("Add document")
        .setAddFileReferenceButtonTooltipText("Add a document")
//        .setInitialFolderSupplier(this::getInitialFolder)
        .setPrefix(invoicesAndPropertiesRegistry.getPropertyRelatedFilesFolder() + "/")
        .build();
    documentsEditPanel.setId("documents");
    
    picturesEditPanel = new FileReferencesEditPanelBuilder(customization)
        .setReferencesEditPanelTitle("Pictures")
        .setReferenceEditPanelTitle("Picture")
        .setAddFileReferenceButtonText("Add picture")
        .setAddFileReferenceButtonTooltipText("Add a picture")
//        .setInitialFolderSupplier(this::getInitialFolder)
        .setPrefix(invoicesAndPropertiesRegistry.getPropertyRelatedFilesFolder() + "/")
        .build();
    documentsEditPanel.setId("pictures");    
   
    registerEditorComponents(descriptionControl, brandControl, typeControl, serialNumberControl,
        amountControl, fromDateControl, untilDateControl, remarksControl, archiveControl);
    
    selectedPictureImageView = new ImageView();
    selectedPictureImageView.setFitWidth(400);
    selectedPictureImageView.setPreserveRatio(true);
    
    picturesEditPanel.addObjectSelectionListener((_, fileReference) -> {
      if (fileReference != null  &&  fileReference.getFile() != null) {
        Image image = new Image("file:" + fileReference.getFile());
        selectedPictureImageView.setImage(image);
      }
    });
  }

  @Override
  public Node getControl() {
    return titledPane;
  }

  @Override
  public String getValueAsFormattedText() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected void createEditPanel() {
    HBox mainPane = componentFactory.createHBox();
    
    HBox attributesBox = componentFactory.createHBox();

    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);

    // Description
    gridPane.add(descriptionControl.getLabel(), 0, 0);
    gridPane.add(descriptionControl.getControl(), 1, 0);
    gridPane.add(descriptionControl.getStatusIndicator(), 2, 0);

    // Brand
    gridPane.add(brandControl.getLabel(), 0, 1);
    gridPane.add(brandControl.getControl(), 1, 1);
    gridPane.add(brandControl.getStatusIndicator(), 2, 1);

    // Type
    gridPane.add(typeControl.getLabel(), 0, 2);
    gridPane.add(typeControl.getControl(), 1, 2);
    gridPane.add(typeControl.getStatusIndicator(), 2, 2);

    // Serial number
    gridPane.add(serialNumberControl.getLabel(), 0, 3);
    gridPane.add(serialNumberControl.getControl(), 1, 3);
    gridPane.add(serialNumberControl.getStatusIndicator(), 2, 3);

    // Amount
    gridPane.add(amountControl.getLabel(), 0, 4);
    gridPane.add(amountControl.getControl(), 1, 4);
    gridPane.add(amountControl.getStatusIndicator(), 2, 4);

    // From date
    gridPane.add(fromDateControl.getLabel(), 0, 5);
    gridPane.add(fromDateControl.getControl(), 1, 5);
    gridPane.add(fromDateControl.getStatusIndicator(), 2, 5);

    // Until date
    gridPane.add(untilDateControl.getLabel(), 0, 6);
    gridPane.add(untilDateControl.getControl(), 1, 6);
    gridPane.add(untilDateControl.getStatusIndicator(), 2, 6);

    // Remarks
    gridPane.add(remarksControl.getLabel(), 0, 7);
    gridPane.add(remarksControl.getControl(), 1, 7);
    gridPane.add(remarksControl.getStatusIndicator(), 2, 7);

    // Archived
    gridPane.add(archiveControl.getLabel(), 0, 8);
    gridPane.add(archiveControl.getControl(), 1, 8);
    gridPane.add(archiveControl.getStatusIndicator(), 2, 8);
    
    attributesBox.getChildren().add(gridPane);

    mainPane.getChildren().add(attributesBox);
    
    VBox attachmentsInfoBox = componentFactory.createVBox();
    
    HBox documentsAndPicturesHBox = componentFactory.createHBox(12.0, 12.0);
    documentsAndPicturesHBox.getChildren().addAll(documentsEditPanel.getControl(), picturesEditPanel.getControl(), selectedPictureImageView);
    attachmentsInfoBox.getChildren().add(documentsAndPicturesHBox);
    
    mainPane.getChildren().add(attachmentsInfoBox);

    titledPane = new TitledPane(getLabelBaseText(), mainPane);
    titledPane.setExpanded(false);
  }

  @Override
  protected void fillObjectFromControls(InvoiceAndPropertyItem object, boolean getCurrentValue) throws EditorException {
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
      makeReferencesRelative(documents);
      object.getDocuments().clear();
      object.getDocuments().addAll(documents);
    } else {
      documentsEditPanel.accept();
    }

    if (getCurrentValue) {
      List<FileReference> pictures = picturesEditPanel.getCurrentValue();
      makeReferencesRelative(pictures);
      object.getPictures().clear();
      object.getPictures().addAll(pictures);
    } else {
      picturesEditPanel.accept();
    }
  }

  private void makeReferencesRelative(List<FileReference> fileReferences) {
    for (FileReference fileReference: fileReferences) {
      fileReference.setFile(FileUtils.getPathRelativeToFolder(invoicesAndPropertiesRegistry.getPropertyRelatedFilesFolder(), fileReference.getFile()));
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
  protected InvoiceAndPropertyItem createObject() {
    return InvAndPropFactory.eINSTANCE.createInvoiceAndPropertyItem();
  }
  
  /**
   * Update the 'title' of this {@code TitledPane}.
   * <p>
   * This title is the value of the {@code titleObjectControl} if this is not null or empty.<br/>
   * Else it is the file/folder name of the file/folder selected by the file/folder chooser.<br/>
   * If this is also not set, the {@code title} is used.<br/>
   * 
   * To this a valid/invalid indication is added, based on the status of the {@code myObjectControlsGroup}.
   */
  private void updatePaneTitle() {
    StringBuilder buf = new  StringBuilder();
    
    String invoiceAndPropertyId = null;
    
    try {
      invoiceAndPropertyId = InvoicesAndPropertiesService.getInvoiceAndPropertyId(getCurrentValue());
    } catch (EditorException e) {
      // no action needed.
    }
    
    if (invoiceAndPropertyId.isEmpty()) {
      invoiceAndPropertyId = "No id for invoice and property item";
    }
    
    buf.append(invoiceAndPropertyId)
    .append(" ")
    .append(getStatusSymbol());
        
    titledPane.setText(buf.toString());
  }

  @Override
  protected void fillControlsFromObject() {
    if (value == null) {
      return;
    }
    
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
  }
  
  @Override
  protected void installChangeListeners() {
    addValueAndOrStatusChangeListener((_, _) -> updatePaneTitle());
  }
}
