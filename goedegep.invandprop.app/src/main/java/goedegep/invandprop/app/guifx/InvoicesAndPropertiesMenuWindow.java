package goedegep.invandprop.app.guifx;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JDialog;

import goedegep.appgen.MessageDialogType;
import goedegep.appgen.swing.MessageDialog;
import goedegep.invandprop.app.InvoicesAndPropertiesRegistry;
import goedegep.invandprop.app.InvoicesAndPropertiesService;
import goedegep.invandprop.app.InvoicesAndPropertiesUtil;
import goedegep.invandprop.model.InvoicesAndProperties;
import goedegep.invandprop.model.Property;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.MenuUtil;
import goedegep.jfx.PropertyDescriptorsEditorFx;
import goedegep.jfx.collage.CollageImage;
import goedegep.properties.app.guifx.PropertiesEditor;
import goedegep.resources.ImageSize;
import goedegep.types.model.FileReference;
import goedegep.util.Result;
import goedegep.util.file.FileUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * This class is the menu window for the Invoices and Properties component.
 */
public class InvoicesAndPropertiesMenuWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(InvoicesAndPropertiesMenuWindow.class.getName());
  private final static String NEWLINE = System.getProperty("line.separator");
  
  private InvoicesAndPropertiesService invoicesAndPropertiesService;
  
  /**
   * Base of the window title.
   */
  private static final String WINDOW_TITLE   = "Invoices and properties menu window";
  private static final int MAX_NR_OF_PICTURES_IN_COLLAGE = 30;
  private static final int MIN_NR_OF_PICTURES_IN_COLLAGE = 5;
  
  private static final int WINDOW_WIDTH = 1920 / 2;
  private static final int WINDOW_HEIGHT = 1080 / 2;

  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;
  private InvoicesAndPropertiesAppResourcesFx appResources;
//  private EMFResource<InvoicesAndProperties> invoicesAndPropertiesResource;
  private InvoicesAndProperties invoicesAndProperties;
  private Label statusBar = null;       // Statusbar
  private File dataDumpFile = null;     // File to which data has been dumped.
  
//  private InvoicesWindow invoicesWindow;
//  private PropertiesWindow propertiesWindow;

  /**
   * Constructor.
   * 
   * @param customization GUI customization.
   */
  public InvoicesAndPropertiesMenuWindow(CustomizationFx customization, InvoicesAndPropertiesService invoicesAndPropertiesService) {
    super(customization, WINDOW_TITLE);
    
    this.customization = customization;
    this.invoicesAndPropertiesService = invoicesAndPropertiesService;
    
    componentFactory = getComponentFactory();
    appResources = (InvoicesAndPropertiesAppResourcesFx) getResources();
    invoicesAndProperties = invoicesAndPropertiesService.getInvoicesAndPropertiesResource().getEObject();
    
    createGUI();
    
    invoicesAndPropertiesService.getInvoicesAndPropertiesResource().dirtyProperty().addListener(new ChangeListener<Boolean>() {

      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        updateTitle();        
      }
      
    });
    
    invoicesAndPropertiesService.getInvoicesAndPropertiesResource().fileNameProperty().addListener(new ChangeListener<String>() {

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        updateTitle();
      }
      
    });

    updateTitle();
  }
  
  /**
   * Create the GUI.
   * <p>
   * The root layout is a BorderPane.<br/>
   * The center is a background image (a generated collage), with the application buttons on top.
   * The bottom is the statusBar.
   */
  private void createGUI() {
    BorderPane rootLayout = new BorderPane();

    rootLayout.setTop(createMenuBar());

    StackPane mainLayout = new StackPane();
    
    // Add the background image
    Canvas backGroundImage = createCollage();

    if (backGroundImage == null) {
      backGroundImage = new Canvas();
      GraphicsContext gc = backGroundImage.getGraphicsContext2D();
      gc.drawImage(appResources.getPicture(), 0, 0);
    }
    mainLayout.getChildren().add(backGroundImage);
        
    // Add the buttons
    GridPane grid = new GridPane();  // not created via componentFactory, as that sets a solid background color.
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(50));

    Button applicationButton;

    applicationButton = componentFactory.createToolButton("Invoices", appResources.getApplicationImage(ImageSize.SIZE_0), "Open the invoices window");
    applicationButton.setOnAction(e -> {
      InvoicesAndPropertiesLauncher.getInvoicesWindow().show();
    });
    grid.add(applicationButton, 0, 0);

    applicationButton = componentFactory.createToolButton("Properties", appResources.getApplicationImage(ImageSize.SIZE_0), "Open the properties window");
    applicationButton.setOnAction(e -> {
      InvoicesAndPropertiesLauncher.getPropertiesWindow().show();
    });
    grid.add(applicationButton, 1, 0);
    
    Button button;
    
    button = componentFactory.createButton("New Invoice and/or Property", "Create a new invoice and the related property");
    GridPane.setHalignment(button, HPos.CENTER);
    button.setOnAction(e -> InvoiceAndPropertyEditor.newInstance(customization, invoicesAndPropertiesService).show());
    grid.add(button, 0, 2, 2, 1);
    

    mainLayout.getChildren().add(grid);

    rootLayout.setCenter(mainLayout);

    statusBar = new Label("Welcome to Invoices and Properties");
    rootLayout.setBottom(statusBar);

    setScene(new Scene(rootLayout));
  }
//  
//  public InvoicesWindow getInvoicesWindow() {
//    if (invoicesWindow == null) {
//      invoicesWindow = new InvoicesWindow(customization, invoicesAndPropertiesService);
//    }
//    
//    return invoicesWindow;
//  }
//  
//  public PropertiesWindow getPropertiesWindow() {
//    if (propertiesWindow == null) {
//      propertiesWindow = new PropertiesWindow(customization, invoicesAndPropertiesService);
//    }
//    
//    return propertiesWindow;
//  }

  /**
   * Create the menu bar.
   * 
   * @return the created menu bar.
   */
  private MenuBar createMenuBar() {
    MenuBar menuBar = componentFactory.createMenuBar();
    Menu menu;

    // File menu
    menu = componentFactory.createMenu("File");
    
    // File: Save
    MenuItem menuItem = componentFactory.createMenuItem("Save");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        saveInvoicesAndProperties();
      }
    });
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
    menu.getItems().add(menuItem);

    // File: Edit property descriptors
    if (InvoicesAndPropertiesRegistry.developmentMode) {
      MenuUtil.addMenuItem(menu, "Edit property descriptors", e -> showPropertyDescriptorsEditor());
    }

    // File: Edit user settings
    MenuUtil.addMenuItem(menu, "Edit user settings", e -> showPropertiesEditor());
    
    // File: Dump data
    MenuUtil.addMenuItem(menu, "Dump data", e-> dumpData());
    

    menuBar.getMenus().add(menu);


    // Help menu
    menu = componentFactory.createMenu("Help");

    // Help: About
    MenuUtil.addMenuItem(menu, "About", e-> showHelpAboutDialog());

    menuBar.getMenus().add(menu);

    return menuBar;
  }

  /**
   * Create a photo collage for the background picture.
   * <p>
   * The collage is created from a random selection of the 'afbeeldingen' of the 'eigendommen'.<br/>
   * Only pictures of belongings that are not archived are used.<br/>
   * Only the first picture of a belonging is used.
   * 
   * @return the generated photo collage.
   */
  private Canvas createCollage() {
    LOGGER.info("=>");
    
    Canvas collage = null;
    List<Property> properties = invoicesAndProperties.getProperties().getProperties();
    LOGGER.info("number of properties: " + properties.size());
    
    // Candidates are the first pictures of properties which aren't archived.
    List<FileReference> candidates = new ArrayList<>();
    for (Property property: properties) {
      FileReference fileReference = null;
      if (!property.isArchive()  &&  property.getPictures().size() > 0) {
        fileReference = property.getPictures().get(0);
      }
      if (fileReference != null  &&  fileReference.getFile() != null) {
        candidates.add(fileReference);
      }
    }
    LOGGER.info("number of candidates: " + candidates.size());
    
    // Randomly remove candidates until we have the right amount of pictures left over
    while (candidates.size() > MAX_NR_OF_PICTURES_IN_COLLAGE) {
      int index = (int) (Math.random() * candidates.size());
      LOGGER.info("Going to remove candidate with index: "  + index);
      candidates.remove(index);
    }
    LOGGER.info("number of candidates: " + candidates.size());
    
    // Only create the collage if there are sufficient pictures
    if (candidates.size() > MIN_NR_OF_PICTURES_IN_COLLAGE) {
      List<File> imageFiles = new ArrayList<File>();
      for (FileReference fileReference: candidates) {
        File file = new File(fileReference.getFile());
        if (!file.isAbsolute()) {
          file = new File(InvoicesAndPropertiesRegistry.propertyRelatedFilesFolder, fileReference.getFile());
        }
        
        imageFiles.add(file);
      }

      collage = CollageImage.createCollageImage(WINDOW_WIDTH, WINDOW_HEIGHT, imageFiles);
    } else {
      LOGGER.severe("Not enough pictures");
    }
    
    return collage;
  }
  
  private void saveInvoicesAndProperties() {
    Result result = invoicesAndPropertiesService.saveInvoicesAndProperties();
    switch (result.getResultType()) {
    case OK:
      statusBar.setText(result.getMessage());
      break;
      
    case FAILED:
      componentFactory.createErrorDialog(
          "Saving the invoices and properties has failed.",
          result.getMessage()
          ).showAndWait();
      
    }
  }

  private void showPropertyDescriptorsEditor() {
    new PropertyDescriptorsEditorFx(customization, InvoicesAndPropertiesRegistry.propertyDescriptorsResource);
  }

  private void showPropertiesEditor() {
    new PropertiesEditor("Invoices and Properties properties", customization, InvoicesAndPropertiesRegistry.propertyDescriptorsResource, InvoicesAndPropertiesRegistry.customPropertiesFile);
  }

  private void dumpData() {
    FileChooser fileChooser = componentFactory.createFileChooser("Dump data");
    if (dataDumpFile != null) {
      fileChooser.setInitialFileName(dataDumpFile.getAbsolutePath());
    } else {
      File file = new File(InvoicesAndPropertiesRegistry.invoicesAndPropertiesFile);
      fileChooser.setInitialDirectory(new File(file.getParent()));
    }
    ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files", "*.txt");
    fileChooser.getExtensionFilters().add(extFilter);

    dataDumpFile = fileChooser.showOpenDialog(this);
    if (dataDumpFile != null) {
      try {
        FileUtils.moveFileToBackupFolder(dataDumpFile.getParent(), dataDumpFile.getName(), true);
      } catch (IOException e1) {
        showMessageDialog(MessageDialogType.ERROR, "Het opslaan is mislukt, melding: " + e1.getMessage());
      }

      dumpDataToFile(dataDumpFile);
      statusBar.setText("Data gedumpt naar " + dataDumpFile.getAbsolutePath() + ".");
    }
  }
  
  private void dumpDataToFile(File file) {
    try {
      BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
      
      InvoicesAndPropertiesUtil.dumpData(invoicesAndProperties, out);
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Show the dialog with information about this application.
   */
  private void showHelpAboutDialog() {
    componentFactory.createApplicationInformationDialog(
        "About Invoices and Properties",
        appResources.getApplicationImage(ImageSize.SIZE_3),
        null, 
        InvoicesAndPropertiesRegistry.shortProductInfo + NEWLINE +
        "Versie: " + InvoicesAndPropertiesRegistry.version + NEWLINE +
        InvoicesAndPropertiesRegistry.copyrightMessage + NEWLINE +
        "Auteur: " + InvoicesAndPropertiesRegistry.author)
        .showAndWait();
  }
  
  // TODO remove
  public void showMessageDialog(MessageDialogType messageDialogType, String message) {
    showMessageDialog(messageDialogType, message, null);
  }
  
  public void showMessageDialog(MessageDialogType messageDialogType, String message, Image image) {
    JDialog dlg;
    dlg = MessageDialog.createMessageDialog(null, messageDialogType, null, message);
    dlg.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
    dlg.setVisible(true);
  }

  /**
   * Update the window title.
   * <p>
   * The format of the title is: {@link #WINDOW_TITLE} - &lt;dirty&gt;&lt;file-name&gt;<br/>
   * Where:<br/>
   * &lt;dirty&gt; is a '*' symbol if the data file is dirty, empty otherwise.<br/>
   * &lt;file-name&gt; is the name of the file being operated on, or '&lt;NoName&gt' if there's no file name available.
   */
  private void updateTitle() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(WINDOW_TITLE);
    buf.append(" - ");
    if (invoicesAndPropertiesService.getInvoicesAndPropertiesResource().isDirty()) {
      buf.append("*");
    }
    String fileName = invoicesAndPropertiesService.getInvoicesAndPropertiesResource().getFileName();
    if (fileName.equals("")) {
      fileName = "<NoName>";
    }
    buf.append(fileName);
    
    setTitle(buf.toString());
  }
  
}