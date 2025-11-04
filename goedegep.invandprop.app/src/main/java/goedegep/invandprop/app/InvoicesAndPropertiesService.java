package goedegep.invandprop.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

import goedegep.configuration.model.Look;
import goedegep.invandprop.app.guifx.InvoicesAndPropertiesAppResourcesFx;
import goedegep.invandprop.app.guifx.InvoicesAndPropertiesWindow;
import goedegep.invandprop.model.InvAndPropFactory;
import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.InvoiceAndProperty;
import goedegep.invandprop.model.InvoiceAndPropertyItem;
import goedegep.invandprop.model.InvoicesAndProperties;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxApplication;
import goedegep.myworld.common.Registry;
import goedegep.myworld.common.Service;
import goedegep.properties.app.PropertiesHandler;
import goedegep.types.model.FileReference;
import goedegep.util.Result;
import goedegep.util.Result.ResultType;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.emf.EMFResource;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;

/**
 * The InvoicesAndPropertiesService class is the main class for the Invoices and Properties application.
 */
public class InvoicesAndPropertiesService extends Service {
  private static final Logger LOGGER = Logger.getLogger(InvoicesAndPropertiesService.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final FlexDateFormat FDF = new FlexDateFormat();
  
  /**
   * The singleton instance of the InvoicesAndPropertiesService.
   */
  private static InvoicesAndPropertiesService instance;
  
  private InvoicesAndPropertiesRegistry invoicesAndPropertiesRegistry;
  
  /**
   * The resource containing the Invoices and Properties information.
   */
  private EMFResource<InvoicesAndProperties> invoicesAndPropertiesResource;
  
  /**
   * The Invoices and Properties information.
   */
  private InvoicesAndProperties invoicesAndProperties;
    
  /**
   * Get the singleton instance of the InvoicesAndPropertiesService.
   * 
   * @return the singleton instance of the InvoicesAndPropertiesService.
   */
  public static InvoicesAndPropertiesService getInstance() {
    if (instance == null) {
      instance = new InvoicesAndPropertiesService();
      instance.initialize();
      instance.invoicesAndPropertiesRegistry = InvoicesAndPropertiesRegistry.getInstance();
    }
    return instance;
  }
  
  /**
   * Show the Invoices and Properties main window.
   */
  public void showInvoicesAndPropertiesWindow() {
    InvoicesAndPropertiesWindow invoicesAndPropertiesWindow = new InvoicesAndPropertiesWindow(customization, this);
    invoicesAndPropertiesWindow.show();
  }

  /**
   * Private constructor to enforce singleton pattern.
   */
  private InvoicesAndPropertiesService() {
    
    try {
      // Read the properties, which are stored in the registry.
      URL url = getClass().getResource(invoicesAndPropertiesRegistry.getPropertyDescriptorsFileName());
      PropertiesHandler.handleProperties(url.toURI());
    } catch (IOException | URISyntaxException e) {
      JfxApplication.reportException(null, e);
    }
    
    if (!checkRegistry()) {
      return;
    }

    checkAndRepairDataModel();
    
    EMFResource<InvoicesAndProperties> invoicesAndPropertiesResource = getInvoicesAndPropertiesResource(customization);
    if (invoicesAndPropertiesResource == null) {
      return;
    }
    
    this.invoicesAndPropertiesResource = invoicesAndPropertiesResource;
    invoicesAndProperties = invoicesAndPropertiesResource.getEObject();
  }

  public EMFResource<InvoicesAndProperties> getInvoicesAndPropertiesResource() {
    return invoicesAndPropertiesResource;
  }
  
  /**
   * Add a new {@code InvoiceAndProperty} to the database.
   * 
   * @param invoice the {@code Invoice} to be added.
   */
  public void addInvoiceAndPropertyToInvoicesAndPropertiesDatabase(InvoiceAndProperty invoiceAndProperty) {
    invoicesAndProperties.getInvoicseandpropertys().add(invoiceAndProperty);
  }
  
  /**
   * Save the invoices and properties to file.
   * 
   * @return the result of the save operation.
   */
  public Result saveInvoicesAndProperties() {
    Result result = new Result();
    
    try {
      invoicesAndPropertiesResource.save();
      result.setResultType(ResultType.OK);
      result.setMessage("Invoices and properties saved to '" + invoicesAndPropertiesResource.getFileName() + "'");
    } catch (IOException e) {        
      result.setResultType(ResultType.FAILED);
      result.setMessage("System error message: "  + e.getMessage());
    }
    
    return result;
  }
  
  
  /**
   * Get the name of the folder where the attachments of an invoice and property are stored.
   * <p>
   * If there is an attachment (document or picture) with a file reference to a sub folder of the Invoices and Properies Folder, this is the invoice and property folder.
   * Otherwise if there is a date and description, the folder is derived from these values.
   * 
   * @param invoiceAndProperty the {@code InvoiceAndProperty} for which the folder name is requested.
   * @return the name of the folder where the attachments of a {@code event} are stored, or null if this cannot be determined.
   */
  public String determineAttachmentsFolderName(InvoiceAndProperty invoiceAndProperty) {
    String attachmentsFolderName = deriveAttachmentsFolderNameFromAttachments(invoiceAndProperty);
    
    if (attachmentsFolderName == null) {
      attachmentsFolderName = deriveAttachmentsFolderNameFromDateAndDescription(invoiceAndProperty);
    }
    
    return attachmentsFolderName;
  }
  
  
  /**
   * Get the attachments folder name derived from the attachments.
   * <p>
   * The attachments folder is the folder of the first attachment, which is a sub folder of the Invoices and Properties Folder.
   * 
   * @param invoiceAndProperty the {@code InvoiceAndProperty} for which the folder name is requested.
   * @return the attachments folder name derived from the attachments, or null if this cannot be determined.
   */
  public String deriveAttachmentsFolderNameFromAttachments(InvoiceAndProperty invoiceAndProperty) {
    if (invoicesAndPropertiesRegistry.getPropertyRelatedFilesFolder() == null) {
      return null;
    }
    
    for (FileReference fileReference: invoiceAndProperty.getDocuments()) {      
      String fileName = fileReference.getFile();
      if (fileName == null) {
        continue;
      }
      
      File file = new File(invoicesAndPropertiesRegistry.getPropertyRelatedFilesFolder(), fileName);
      if (file != null ) {
        file = file.getParentFile();  // This is the possible attachments directory
        if (file != null) {
          File attachmentsFolder = file.getParentFile();
          String attachmentsFolderName = attachmentsFolder.getAbsolutePath();
          if (attachmentsFolderName != null  &&  attachmentsFolderName.equals(invoicesAndPropertiesRegistry.getPropertyRelatedFilesFolder())) {
            String attachmentFolderName = file.getAbsolutePath();
            return attachmentFolderName;
          }
        }
      }
    }
    
    return null;
  }
  
  /**
   * Derive the attachments folder from the date and description.
   * 
   * @param invoiceAndProperty the {@code InvoiceAndProperty} for which the folder name is requested.
   * @return the event folder derived from the date and title controls, or null if these controls don't have the needed information.
   */
  public String deriveAttachmentsFolderNameFromDateAndDescription(InvoiceAndProperty invoiceAndProperty) {
    if (invoicesAndPropertiesRegistry.getPropertyRelatedFilesFolder() == null) {
      return null;
    }
    
    FlexDate date = invoiceAndProperty.getDate();
    if (date == null) {
      return null;
    }
    
    String dateText = FDF.format(date);
    
    String description = invoiceAndProperty.getDescription();
    if (description == null) {
      return null;
    }
    
    String subFolderName = dateText + " " + description;
    File attachmentsFolder = new File(invoicesAndPropertiesRegistry.getPropertyRelatedFilesFolder(), subFolderName);
    
    return attachmentsFolder.getAbsolutePath();
  }
  
  /**
   * Generate the invoice and property Id.
   * <p>
   * This consists of the description, brand and type.
   * 
   * @param invoiceAndProperty the {@code InvoiceAndProperty} for which the Id is to be generated.
   * @return the invoice and property Id for the {@code invoiceAndProperty}.
   */
  public static String getInvoiceAndPropertyId(InvoiceAndPropertyItem invoiceAndPropertyItem) {
    StringBuilder buf = new StringBuilder();
    boolean spaceNeeded = false;
    
    if (invoiceAndPropertyItem.getDescription() != null) {
      buf.append(invoiceAndPropertyItem.getDescription());
      spaceNeeded = true;
    }
    
    if (invoiceAndPropertyItem.getBrand() != null) {
      if (spaceNeeded) {
        buf.append(" ");
      }
      buf.append(invoiceAndPropertyItem.getBrand());
    }
    
    if (invoiceAndPropertyItem.getType() != null) {
      if (spaceNeeded) {
        buf.append(" ");
      }
      buf.append(invoiceAndPropertyItem.getType());
    }
    
    return buf.toString();
  }

  /**
   * Try to get the Invoices and Properties resource.
   * 
   * @return true if the resource could be opened, false otherwise.
   */
  private EMFResource<InvoicesAndProperties> getInvoicesAndPropertiesResource(CustomizationFx customization) {
    boolean returnValue = false;

    EMFResource<InvoicesAndProperties> invoicesAndPropertiesResource = new EMFResource<InvoicesAndProperties>(InvAndPropPackage.eINSTANCE, () -> InvAndPropFactory.eINSTANCE.createInvoicesAndProperties(), ".xmi", true);

    try {
      invoicesAndPropertiesResource.load(invoicesAndPropertiesRegistry.getInvoicesAndPropertiesFile());
      returnValue = true;
    } catch (IOException e) {
      LOGGER.severe("File not found: " + e.getMessage());
      Alert alert = customization.getComponentFactoryFx().createYesNoConfirmationDialog(
          null,
          "The file with invoices and properties (" + invoicesAndPropertiesRegistry.getInvoicesAndPropertiesFile() + ") doesn't exist yet.",
          "Do you want to create this file now?" + NEWLINE +
          "If you choose \"No\" the InvoicesAndProperties application will not be started.");
      Optional<ButtonType> response = alert.showAndWait();
      if (response.isPresent()  &&  response.get() == ButtonType.YES) {
        LOGGER.severe("yes, create file");
        invoicesAndPropertiesResource.newEObject();
        try {
          invoicesAndPropertiesResource.save(invoicesAndPropertiesRegistry.getInvoicesAndPropertiesFile());
          returnValue = true;
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      } else {
        LOGGER.severe("no, don't create file");
      }
    }

    return returnValue ? invoicesAndPropertiesResource : null;
  }

  /**
   * Check the registry for required settings.
   * 
   * @return true if the registry is OK, false otherwise.
   */
  private boolean checkRegistry() {
    
    if (invoicesAndPropertiesRegistry.getInvoicesAndPropertiesFile() == null) {
      Alert alert = customization.getComponentFactoryFx().createErrorDialog(
          "There's no filename configured for the file with invoices and properties",
          "Configure the filename (e.g. via the 'Edit User Settings' button below) and restart the application." +
              NEWLINE +
              "A restart is needed, because the settings are only read at startup.");
      
      ButtonType editorButtonType = new ButtonType("Edit User Settings");
      alert.getButtonTypes().add(editorButtonType);
      
      alert.showAndWait().ifPresent(response -> {
        if (response == editorButtonType) {
          showPropertiesEditor();
        }
      });
      
      return false;
    }
    
    return true;
  }
  
  private static void checkAndRepairDataModel() {
//    InvoicesAndProperties invoicesAndProperties = invoicesAndPropertiesService.getInvoicesAndPropertiesResource().getEObject();
//    
//    Invoices invoices = invoicesAndProperties.getInvoices();
//    for (Invoice invoice: invoices.getInvoices()) {
//      checkAndRepairInvoice(invoice);
//    }
  }

//  private static void checkAndRepairInvoice(Invoice invoice) {
//    // descriptionFromProperty may only be set if: there is a related purchase AND the description of the invoice is not set.
//    if (invoice.isDescriptionFromProperty()) {
//      if (invoice.getPurchase() == null) {
//        LOGGER.severe("Description from property is set, but there is no related purchase: " + invoice);
//      }
//    }
//    
//    if (invoice.isDescriptionFromProperty()) {
//      if (invoice.getDescription() != null) {
//        Property property = invoice.getPurchase();
//        String descriptionFromProperty = getDescriptionFromProperty(property);
//        if (!invoice.getDescription().equals(descriptionFromProperty)) {
//          LOGGER.severe("Description from property is set, but differs: '" + invoice.getDescription() + "' , generated: '" + descriptionFromProperty + "'");
////          invoice.setDescription(descriptionFromProperty);
//        }
//      }
//    }
//    
//  }
  
  
  @Override
  protected void readApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("InvoicesAndPropertiesApplication.properties")) {
        props.load(in);
        
        invoicesAndPropertiesRegistry.setVersion(props.getProperty("invandprop.app.version"));
        invoicesAndPropertiesRegistry.setApplicationName(props.getProperty("invandprop.app.name"));
    } catch (Exception e) {
      JfxApplication.reportException(null, e);
      System.exit(1);
    }
  }
  
  @Override
  protected void fillLook(Look look) {
    look.setBackgroundColor(Color.rgb(238,238,238));
    look.setButtonBackgroundColor(Color.rgb(238,238,238));
    look.setPanelBackgroundColor(Color.rgb(238,238,238));
    look.setListBackgroundColor(Color.rgb(238,238,238));
    look.setLabelBackgroundColor(Color.rgb(238,238,238));
    look.setBoxBackgroundColor(Color.rgb(238,238,238));
    look.setTextFieldBackgroundColor(Color.rgb(255,255,255));
  }
  
  @Override
  protected AppResourcesFx getAppResourcesFxClass() {
    return new InvoicesAndPropertiesAppResourcesFx();
  }
  
  @Override
  protected Registry getRegistry() {
    return invoicesAndPropertiesRegistry;
  }
}
