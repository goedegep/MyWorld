package goedegep.invandprop.app.guifx;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.util.EcoreUtil;

import goedegep.invandprop.app.InvoicesAndPropertiesRegistry;
import goedegep.invandprop.app.InvoicesAndPropertiesService;
import goedegep.invandprop.model.InvAndPropFactory;
import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.Invoice;
import goedegep.invandprop.model.InvoiceAndProperty;
import goedegep.invandprop.model.InvoiceAndPropertyItem;
import goedegep.invandprop.model.InvoiceItem;
import goedegep.invandprop.model.Invoices;
import goedegep.invandprop.model.InvoicesAndProperties;
import goedegep.invandprop.model.Properties;
import goedegep.invandprop.model.Property;
import goedegep.jfx.CustomizationFx;
import goedegep.properties.app.guifx.PropertiesEditor;
import goedegep.types.model.FileReference;
import goedegep.util.emf.EMFResource;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * This class is used to launch the InvoicesAndProperties application.
 * <p>
 * The InvoicesAndPropertiesService is started and an InvoicesAndPropertiesMenuWindow is started on top of this service.
 */
public class InvoicesAndPropertiesLauncher {
  private static final Logger LOGGER = Logger.getLogger(InvoicesAndPropertiesLauncher.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");

  private static CustomizationFx customization = null;
  private static InvoicesAndPropertiesService invoicesAndPropertiesService;
  private static InvoicesWindow invoicesWindow = null;
  private static PropertiesWindow propertiesWindow = null;

  public static void launchInvoicesAndPropertiesApplication(CustomizationFx customization) {
    InvoicesAndPropertiesLauncher.customization = customization;
    
    if (!checkRegistry(customization)) {
      return;
    }
    
    EMFResource<InvoicesAndProperties> invoicesAndPropertiesResource = getInvoicesAndPropertiesResource(customization);
    if (invoicesAndPropertiesResource == null) {
      return;
    }

    invoicesAndPropertiesService = new InvoicesAndPropertiesService(invoicesAndPropertiesResource);
    
    checkAndRepairDataModel();
//    convertDataModel();
//    removeOldData();

//    Stage stage = new InvoicesAndPropertiesMenuWindow(customization, invoicesAndPropertiesService);
//    stage.centerOnScreen();
//    stage.show();

    Stage stage2 = new InvoicesAndPropertiesWindow(customization, invoicesAndPropertiesService);
    stage2.show();          

  }
  
  public static InvoicesWindow getInvoicesWindow() {
    if (invoicesWindow == null) {
      invoicesWindow = new InvoicesWindow(customization, invoicesAndPropertiesService);
    }
    
    return invoicesWindow;
  }
  
  public static PropertiesWindow getPropertiesWindow() {
    if (propertiesWindow == null) {
      propertiesWindow = new PropertiesWindow(customization, invoicesAndPropertiesService);
    }
    
    return propertiesWindow;
  }
  
  private static void checkAndRepairDataModel() {
//    InvoicesAndProperties invoicesAndProperties = invoicesAndPropertiesService.getInvoicesAndPropertiesResource().getEObject();
//    
//    Invoices invoices = invoicesAndProperties.getInvoices();
//    for (Invoice invoice: invoices.getInvoices()) {
//      checkAndRepairInvoice(invoice);
//    }
  }

  private static void checkAndRepairInvoice(Invoice invoice) {
    // descriptionFromProperty may only be set if: there is a related purchase AND the description of the invoice is not set.
    if (invoice.isDescriptionFromProperty()) {
      if (invoice.getPurchase() == null) {
        LOGGER.severe("Description from property is set, but there is no related purchase: " + invoice);
      }
    }
    
    if (invoice.isDescriptionFromProperty()) {
      if (invoice.getDescription() != null) {
        Property property = invoice.getPurchase();
        String descriptionFromProperty = getDescriptionFromProperty(property);
        if (!invoice.getDescription().equals(descriptionFromProperty)) {
          LOGGER.severe("Description from property is set, but differs: '" + invoice.getDescription() + "' , generated: '" + descriptionFromProperty + "'");
//          invoice.setDescription(descriptionFromProperty);
        }
      }
    }
    
  }

  private static String getDescriptionFromProperty(Property property) {
    StringBuilder buf = new StringBuilder();
    boolean spaceNeeded = false;

    String value = property.getDescription();
    if (value !=  null) {
      value = value.trim();
      if (!value.isEmpty()) {
        buf.append(value);
        spaceNeeded = true;
      }
    }

    value = property.getBrand();
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

    value = property.getType();
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

    return buf.toString();
  }
  
//  private static void convertDataModel() {
//    InvoicesAndProperties invoicesAndProperties = invoicesAndPropertiesService.getInvoicesAndPropertiesResource().getEObject();
//    
//    // For each invoice and its related property, create an InvoiceAndProperty.
//    // To make sure all properties are converted, we copy the list of properties and remove the ones that are handled.
//    Properties properties = invoicesAndProperties.getProperties();
//    List<Property> propertiesCheckList = new ArrayList<>();
//    for (Property property: properties.getProperties()) {
//      propertiesCheckList.add(property);
//    }
//    
//    Invoices invoices = invoicesAndProperties.getInvoices();
//    for (Invoice invoice: invoices.getInvoices()) {
//      InvoiceAndProperty invoiceAndProperty = convertInvoice(invoice);
//      Property property = invoice.getPurchase();
//      if (property != null) {
//        propertiesCheckList.remove(property);
//      }
//      for (InvoiceItem invoiceItem: invoice.getInvoiceItems()) {
//        property = invoiceItem.getPurchase();
//        if (property != null) {
//          propertiesCheckList.remove(property);
//        }
//      }
//      invoicesAndProperties.getInvoicseandpropertys().add(invoiceAndProperty);
//    }
//  }
  
  private static void  removeOldData() {
    InvoicesAndProperties invoicesAndProperties = invoicesAndPropertiesService.getInvoicesAndPropertiesResource().getEObject();
    
    invoicesAndProperties.getInvoices().getInvoices().clear();
    invoicesAndProperties.setInvoices(null);
    
    invoicesAndProperties.getProperties().getProperties().clear();
    invoicesAndProperties.setProperties(null);
  }
  
  private static InvoiceAndProperty convertInvoice(Invoice invoice) {
    InvAndPropFactory invAndPropFactory = InvAndPropFactory.eINSTANCE;
    
    InvoiceAndProperty invoiceAndProperty = invAndPropFactory.createInvoiceAndProperty();
    
    Property purchase = invoice.getPurchase();
    
    // date is date
    if (invoice.getDate() != null) {
      invoiceAndProperty.setDate(invoice.getDate());
    }
    
    // company is company
    if (invoice.getCompany() != null) {
      invoiceAndProperty.setCompany(invoice.getCompany());
    }
    
    // description
    String description = invoice.getDescription();
    if (description != null) {

      if (invoice.isDescriptionFromProperty()) {
        // in this case there has to be a purchase. If not throw exception.
        if (purchase == null) {
          throw new RuntimeException("Desc. from property set, but there is no purchase: " + invoice);
        } else {
          // The description shall be the same as the result of getDescriptionFromProperty()
          String descriptionFromProperty= getDescriptionFromProperty(purchase);
          if (!description.equals(descriptionFromProperty)) {
            throw new RuntimeException("Description differs from Description from property: " + invoice);
          } else {
            // all is fine, description of invoice can be ignored. Take it from purchase
            String purchaseDescription = purchase.getDescription();
            if (purchaseDescription != null) {
              invoiceAndProperty.setDescription(purchaseDescription);
            }
          }
        }
      } else {
        if (purchase != null) {
          String purchaseDescription = purchase.getDescription();
          if (purchaseDescription != null) {
            // we have a problem, as there are 2 descriptions.
            String descriptionFromProperty= getDescriptionFromProperty(purchase);
            if (description.equals(descriptionFromProperty)) {
              // all is fine, description of invoice can be ignored, use description from purchase
              if (purchase.getDescription() != null) {
                invoiceAndProperty.setDescription(purchase.getDescription());
              }
            } else {
//              LOGGER.severe("Both invoice and purchase have a description: '" + description + "', '" + descriptionFromProperty + "'");
              // Manually checked, use description from purchase
              if (purchase.getDescription() != null) {
                invoiceAndProperty.setDescription(purchase.getDescription());
              }
            }
          }
        } else {
          // all is fine, description is the description of the invoice
          invoiceAndProperty.setDescription(description);
        }
      }
    } else {
      // Use description from purchase (if available)
      if (purchase != null  &&  purchase.getDescription() != null) {
        invoiceAndProperty.setDescription(purchase.getDescription());
      }
    }
    
    if (invoice.getAmount() != null) {
      invoiceAndProperty.setAmount(invoice.getAmount());
    }
    
    if (invoice.getRemarks() != null) {
      String remarks = invoice.getRemarks();
      if (purchase != null) {
        String purchaseRemarks = purchase.getRemarks();
        if (purchaseRemarks != null) {
          // Remarks set at 2 location. I've checked them all and now I can just take the remarks of the invoice.
//          LOGGER.severe("Remarks on invoice and purchase: '" + remarks + "', '" + purchaseRemarks + "'");
        }
      }
      invoiceAndProperty.setRemarks(remarks);
    } else {
      // use remarks from purchase (if available)
      if (purchase != null  &&  purchase.getRemarks() != null) {
        invoiceAndProperty.setRemarks(purchase.getRemarks());
      }
    }
    
    if (!invoice.getDocuments().isEmpty()) {
      LOGGER.severe("At least one document for: " + invoice);  // As expected, there aren't any
    }
    
    if (purchase != null) {
      if (purchase.getDescription() != null) {
        // Descriptions are already handled above
      }
      
      if (purchase.getBrand() != null) {
        invoiceAndProperty.setBrand(purchase.getBrand());
      }
      
      if (purchase.getType() != null) {
        invoiceAndProperty.setType(purchase.getType());
      }
      
      if (purchase.getSerialNumber() != null) {
        invoiceAndProperty.setSerialNumber(purchase.getSerialNumber());
      }
      
      if (purchase.getRemarks() != null) {
        // Remarks are already handled above
      }
      
      if (purchase.getFromDate() != null) {
        invoiceAndProperty.setFromDate(purchase.getFromDate());
      }
      
      if (purchase.getUntilDate() != null) {
        invoiceAndProperty.setUntilDate(purchase.getUntilDate());
      }
      
      invoiceAndProperty.setArchive(purchase.isArchive());
      
      for (FileReference document: purchase.getDocuments()) {
        FileReference documentCopy = (FileReference) EcoreUtil.copy(document);
        invoiceAndProperty.getDocuments().add(documentCopy);
      }
      
      for (FileReference picture: purchase.getPictures()) {
        FileReference pictureCopy = (FileReference) EcoreUtil.copy(picture);
        invoiceAndProperty.getPictures().add(pictureCopy);
      }
    }
    
    for (InvoiceItem invoiceItem: invoice.getInvoiceItems()) {
      InvoiceAndPropertyItem invoiceAndPropertyItem = invAndPropFactory.createInvoiceAndPropertyItem();
      Property itemPurchase = invoiceItem.getPurchase();
      
      
      // description
      String itemDescription = invoiceItem.getDescription();
      
      if (itemDescription != null) {

        if (invoiceItem.isDescriptionFromProperty()) {
          // in this case there has to be a purchase. If not just clear it and keep description from invoiceItem.
          if (itemPurchase == null) {
            invoiceItem.setDescriptionFromProperty(false);
            invoiceAndPropertyItem.setDescription(itemDescription);
          } else {
            // The description shall be the same as the result of getDescriptionFromProperty()
            String descriptionFromProperty= getDescriptionFromProperty(itemPurchase);
            if (!itemDescription.equals(descriptionFromProperty)) {
//              LOGGER.severe("Item Description differs from Description from property: " + invoiceItem);
              // All checked. Take description from purchase.
              String purchaseDescription = itemPurchase.getDescription();
              if (purchaseDescription != null) {
                invoiceAndPropertyItem.setDescription(purchaseDescription);
              } else {
                throw new RuntimeException("Description of itemPurchase is null");
              }
            } else {
              // all is fine, description of invoice can be ignored. Take it from purchase
              String purchaseDescription = itemPurchase.getDescription();
              if (purchaseDescription != null) {
                invoiceAndPropertyItem.setDescription(purchaseDescription);
              }
            }
          }
        } else {
          if (itemPurchase != null) {
            String purchaseDescription = itemPurchase.getDescription();
            if (purchaseDescription != null) {
              // we have a problem, as there are 2 descriptions.
              String descriptionFromProperty= getDescriptionFromProperty(purchase);
              if (description.equals(descriptionFromProperty)) {
                // all is fine, description of invoice can be ignored, use description from purchase
                if (itemPurchase.getDescription() != null) {
                  invoiceAndPropertyItem.setDescription(itemPurchase.getDescription());
                }
              } else {
                LOGGER.severe("Both invoice and purchase have a description: '" + description + "', '" + descriptionFromProperty + "'");
                // Manually checked, use description from purchase
                if (itemPurchase.getDescription() != null) {
                  invoiceAndPropertyItem.setDescription(itemPurchase.getDescription());
                }
              }
            }
          } else {
            // all is fine, description is the description of the invoice
            invoiceAndProperty.setDescription(itemDescription);
          }
        }
      } else {
        // Use description from purchase (if available)
        if (itemPurchase != null  &&  itemPurchase.getDescription() != null) {
          invoiceAndProperty.setDescription(itemPurchase.getDescription());
        }
      }
      
      if (invoiceItem.getAmount() != null) {
        invoiceAndPropertyItem.setAmount(invoiceItem.getAmount());
      }
      
      if (invoiceItem.getRemarks() != null) {
        String remarks = invoiceItem.getRemarks();
        if (itemPurchase != null) {
          String purchaseRemarks = itemPurchase.getRemarks();
          if (purchaseRemarks != null) {
            // Remarks set at 2 locations. TODO I've checked them all and now I can just take the remarks of the invoice.
            LOGGER.severe("Remarks on invoiceItem and itemPurchase: '" + remarks + "', '" + purchaseRemarks + "'");
          }
        }
        invoiceAndPropertyItem.setRemarks(remarks);
      } else {
        // use remarks from purchase (if available)
        if (itemPurchase != null  &&  itemPurchase.getRemarks() != null) {
          invoiceAndPropertyItem.setRemarks(itemPurchase.getRemarks());
        }
      }
      
      if (itemPurchase != null) {
//        LOGGER.severe("Purchase set for invoiceItem: " + invoiceItem);
        
        if (itemPurchase.getDescription() != null) {
          // Descriptions are already handled above
        }
        
        if (itemPurchase.getBrand() != null) {
          invoiceAndPropertyItem.setBrand(itemPurchase.getBrand());
        }
        
        if (itemPurchase.getType() != null) {
          invoiceAndPropertyItem.setType(itemPurchase.getType());
        }
        
        if (itemPurchase.getSerialNumber() != null) {
          invoiceAndPropertyItem.setSerialNumber(itemPurchase.getSerialNumber());
        }
                
        if (itemPurchase.getRemarks() != null) {
          // Remarks are already handled above
        }
        
        if (itemPurchase.getFromDate() != null) {
          invoiceAndPropertyItem.setFromDate(itemPurchase.getFromDate());
        }
        
        if (itemPurchase.getUntilDate() != null) {
          invoiceAndPropertyItem.setUntilDate(itemPurchase.getUntilDate());
        }
        
        invoiceAndPropertyItem.setArchive(itemPurchase.isArchive());
        
        for (FileReference document: itemPurchase.getDocuments()) {
          FileReference documentCopy = (FileReference) EcoreUtil.copy(document);
          invoiceAndPropertyItem.getDocuments().add(documentCopy);
        }
        
        for (FileReference picture: itemPurchase.getPictures()) {
          FileReference pictureCopy = (FileReference) EcoreUtil.copy(picture);
          invoiceAndPropertyItem.getPictures().add(pictureCopy);
        }
      }
      
      invoiceAndProperty.getInvoiceandpropertyitems().add(invoiceAndPropertyItem);
    }
    
    return invoiceAndProperty;
  }

  private static boolean checkRegistry(CustomizationFx customization) {
    
    if (InvoicesAndPropertiesRegistry.invoicesAndPropertiesFile == null) {
      Alert alert = customization.getComponentFactoryFx().createErrorDialog(
          "There's no filename configured for the file with invoices and properties",
          "Configure the filename (e.g. via the 'Edit User Settings' button below) and restart the application." +
              NEWLINE +
              "A restart is needed, because the settings are only read at startup.");
      
      ButtonType editorButtonType = new ButtonType("Edit User Settings");
      alert.getButtonTypes().add(editorButtonType);
      
      alert.showAndWait().ifPresent(response -> {
        if (response == editorButtonType) {
          showPropertiesEditor(customization);
        }
      });
      
      return false;
    }
    
    return true;
  }

  /**
   * Try to get the Invoices and Properties resource.
   * 
   * @return true if the resource could be opened, false otherwise.
   */
  private static EMFResource<InvoicesAndProperties> getInvoicesAndPropertiesResource(CustomizationFx customization) {
    boolean returnValue = false;

    EMFResource<InvoicesAndProperties> invoicesAndPropertiesResource = new EMFResource<InvoicesAndProperties>(InvAndPropPackage.eINSTANCE, () -> InvAndPropFactory.eINSTANCE.createInvoicesAndProperties(), ".xmi", true);

    try {
      invoicesAndPropertiesResource.load(InvoicesAndPropertiesRegistry.invoicesAndPropertiesFile);
      returnValue = true;
    } catch (FileNotFoundException e) {
      LOGGER.severe("File not found: " + e.getMessage());
      Alert alert = customization.getComponentFactoryFx().createYesNoConfirmationDialog(
          null,
          "The file with invoices and properties (" + InvoicesAndPropertiesRegistry.invoicesAndPropertiesFile + ") doesn't exist yet.",
          "Do you want to create this file now?" + NEWLINE +
          "If you choose \"No\" the InvoicesAndProperties application will not be started.");
      Optional<ButtonType> response = alert.showAndWait();
      if (response.isPresent()  &&  response.get() == ButtonType.YES) {
        LOGGER.severe("yes, create file");
        invoicesAndPropertiesResource.newEObject();
        try {
          invoicesAndPropertiesResource.save(InvoicesAndPropertiesRegistry.invoicesAndPropertiesFile);
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
   * Show the User Properties editor.
   */
  private static void showPropertiesEditor(CustomizationFx customization) {
    new PropertiesEditor("Invoices and Properties properties", customization, InvoicesAndPropertiesRegistry.propertyDescriptorsResource, InvoicesAndPropertiesRegistry.customPropertiesFile);
  }
}
