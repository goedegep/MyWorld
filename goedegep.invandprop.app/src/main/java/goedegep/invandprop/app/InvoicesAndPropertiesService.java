package goedegep.invandprop.app;

import java.io.File;
import java.io.IOException;

import goedegep.invandprop.model.InvoiceAndProperty;
import goedegep.invandprop.model.InvoiceAndPropertyItem;
import goedegep.invandprop.model.InvoicesAndProperties;
import goedegep.types.model.FileReference;
import goedegep.util.Result;
import goedegep.util.Result.ResultType;
import goedegep.util.RunningInEclipse;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.emf.EMFResource;

public class InvoicesAndPropertiesService {
  private static final FlexDateFormat FDF = new FlexDateFormat();
  
  
  private EMFResource<InvoicesAndProperties> invoicesAndPropertiesResource;
  private InvoicesAndProperties invoicesAndProperties;

  public InvoicesAndPropertiesService(EMFResource<InvoicesAndProperties> invoicesAndPropertiesResource) {
    
    // If we're running within Eclipse, we set development mode to true. The application can use this information to add functionality which is for development only.
    if (RunningInEclipse.runningInEclipse()) {
      InvoicesAndPropertiesRegistry.developmentMode = true;
    }
    
    this.invoicesAndPropertiesResource = invoicesAndPropertiesResource;
    invoicesAndProperties = invoicesAndPropertiesResource.getEObject();
  }

  public EMFResource<InvoicesAndProperties> getInvoicesAndPropertiesResource() {
    return invoicesAndPropertiesResource;
  }

  
//  /**
//   * Add a new {@code Invoice} to the database.
//   * 
//   * @param invoice the {@code Invoice} to be added.
//   */
//  public void addInvoiceToInvoicesAndPropertiesDatabase(Invoice invoice) {
//    invoicesAndProperties.getInvoices().getInvoices().add(invoice);
//  }
  
  /**
   * Add a new {@code InvoiceAndProperty} to the database.
   * 
   * @param invoice the {@code Invoice} to be added.
   */
  public void addInvoiceAndPropertyToInvoicesAndPropertiesDatabase(InvoiceAndProperty invoiceAndProperty) {
    invoicesAndProperties.getInvoicseandpropertys().add(invoiceAndProperty);
  }
  
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
  public static String determineAttachmentsFolderName(InvoiceAndProperty invoiceAndProperty) {
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
  public static String deriveAttachmentsFolderNameFromAttachments(InvoiceAndProperty invoiceAndProperty) {
    if (InvoicesAndPropertiesRegistry.propertyRelatedFilesFolder == null) {
      return null;
    }
    
    for (FileReference fileReference: invoiceAndProperty.getDocuments()) {      
      String fileName = fileReference.getFile();
      if (fileName == null) {
        continue;
      }
      
      File file = new File(InvoicesAndPropertiesRegistry.propertyRelatedFilesFolder, fileName);
      if (file != null ) {
        file = file.getParentFile();  // This is the possible attachments directory
        if (file != null) {
          File attachmentsFolder = file.getParentFile();
          String attachmentsFolderName = attachmentsFolder.getAbsolutePath();
          if (attachmentsFolderName != null  &&  attachmentsFolderName.equals(InvoicesAndPropertiesRegistry.propertyRelatedFilesFolder)) {
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
  public static String deriveAttachmentsFolderNameFromDateAndDescription(InvoiceAndProperty invoiceAndProperty) {
    if (InvoicesAndPropertiesRegistry.propertyRelatedFilesFolder == null) {
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
    File attachmentsFolder = new File(InvoicesAndPropertiesRegistry.propertyRelatedFilesFolder, subFolderName);
    
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
}
