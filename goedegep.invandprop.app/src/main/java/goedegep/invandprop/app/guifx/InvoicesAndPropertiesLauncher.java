package goedegep.invandprop.app.guifx;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import goedegep.invandprop.app.InvoicesAndPropertiesRegistry;
import goedegep.invandprop.app.InvoicesAndPropertiesService;
import goedegep.invandprop.model.InvAndPropFactory;
import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.InvoicesAndProperties;
import goedegep.jfx.CustomizationFx;
import goedegep.properties.app.guifx.PropertiesEditor;
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

    Stage stage = new InvoicesAndPropertiesMenuWindow(customization, invoicesAndPropertiesService);
    stage.centerOnScreen();
    stage.show();          

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
