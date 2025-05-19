package goedegep.invandprop.app.guifx;

import java.awt.Desktop;
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
import goedegep.invandprop.app.FileReferenceWrapper;
import goedegep.invandprop.app.InvoicesAndPropertiesRegistry;
import goedegep.invandprop.app.InvoicesAndPropertiesService;
import goedegep.invandprop.app.InvoicesAndPropertiesUtil;
import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.InvoiceAndProperty;
import goedegep.invandprop.model.InvoiceAndPropertyItem;
import goedegep.invandprop.model.InvoicesAndProperties;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DoubleClickEventDispatcher;
import goedegep.jfx.JfxStage;
import goedegep.jfx.MenuUtil;
import goedegep.jfx.PropertyDescriptorsEditorFx;
import goedegep.jfx.collage.CollageImage;
import goedegep.jfx.eobjecttable.EObjectListContainerSpecification;
import goedegep.jfx.eobjecttable.EObjectTableControlPanel;
import goedegep.properties.app.guifx.PropertiesEditor;
import goedegep.resources.ImageSize;
import goedegep.types.model.FileReference;
import goedegep.util.Result;
import goedegep.util.desktop.DesktopUtil;
import goedegep.util.file.FileUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * This class is the overview window for the Invoices and Properties application.
 */
public class InvoicesAndPropertiesWindow extends JfxStage {
  private final static Logger LOGGER = Logger.getLogger(InvoicesAndPropertiesMenuWindow.class.getName());
  private final static String NEWLINE = System.getProperty("line.separator");
  private final static InvAndPropPackage INV_AND_PROP_PACKAGE = InvAndPropPackage.eINSTANCE;
  
  private InvoicesAndPropertiesService invoicesAndPropertiesService;
  
  /**
   * Base of the window title.
   */
  private static final String WINDOW_TITLE   = "Invoices and properties window";
  private static final int MAX_NR_OF_PICTURES_IN_COLLAGE = 30;
  private static final int MIN_NR_OF_PICTURES_IN_COLLAGE = 5;
  
  private static final int WINDOW_WIDTH = 1920 / 2;
  private static final int WINDOW_HEIGHT = 1080 / 2;

//  private CustomizationFx customization;
//  private ComponentFactoryFx componentFactory;
  private InvoicesAndPropertiesAppResourcesFx appResources;
  private InvoicesAndPropertiesTable invoicesAndPropertiesTable;
  private EObjectTableControlPanel eObjectTableControlPanel;
  private InvoiceAndPropertyItemsTable invoiceAndPropertyItemsTable;
  private ListView<FileReference> documentReferences;
  private ListView<FileReference> pictureReferences;
  private HBox pictureViewPanel;
  
//  private EMFResource<InvoicesAndProperties> invoicesAndPropertiesResource;
  private InvoicesAndProperties invoicesAndProperties;
  private Label statusBar = null;       // Statusbar
  private File dataDumpFile = null;     // File to which data has been dumped.
  

  /**
   * Constructor.
   * 
   * @param customization GUI customization.
   */
  public InvoicesAndPropertiesWindow(CustomizationFx customization, InvoicesAndPropertiesService invoicesAndPropertiesService) {
    super(customization, WINDOW_TITLE);

    // SETTING CUSTOM EVENT DISPATCHER TO SCENE
    setEventDispatcher(new DoubleClickEventDispatcher(getEventDispatcher()));

    
//    this.customization = customization;
    this.invoicesAndPropertiesService = invoicesAndPropertiesService;
    
//    componentFactory = getComponentFactory();
    appResources = (InvoicesAndPropertiesAppResourcesFx) getResources();
    invoicesAndProperties = invoicesAndPropertiesService.getInvoicesAndPropertiesResource().getEObject();
    
    createControls();
    
    invoicesAndPropertiesTable.addObjectSelectionListener((source, invoiceAndProperty) -> {
      EObjectListContainerSpecification eObjectListContainerSpecification = new EObjectListContainerSpecification(invoiceAndProperty, INV_AND_PROP_PACKAGE.getInvoiceAndProperty_Invoiceandpropertyitems());
      invoiceAndPropertyItemsTable.setObjects(eObjectListContainerSpecification);
      updateDocumentsList();
      updatePicturesList();
    });
    
    invoiceAndPropertyItemsTable.addObjectSelectionListener((source, invoiceAndProperty) -> {
      updateDocumentsList();
      updatePicturesList();
    });
    
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
  
  private void createControls() {
    invoicesAndPropertiesTable = new InvoicesAndPropertiesTable(customization, invoicesAndPropertiesService);
    invoicesAndPropertiesTable.setEditable(false);

    eObjectTableControlPanel = new EObjectTableControlPanel(customization);
    eObjectTableControlPanel.filterTextProperty().addListener((observable, oldValue, newValue) -> invoicesAndPropertiesTable.setFilterExpression(newValue, null));
    
    invoiceAndPropertyItemsTable = new InvoiceAndPropertyItemsTable(customization);
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
    
    HBox controlsBox = componentFactory.createHBox(12.0);
    controlsBox.getChildren().add(createMenuBar());
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    controlsBox.getChildren().add(spacer);
    controlsBox.getChildren().add(eObjectTableControlPanel);
        
    Button newInvoiceAndPropertyButton = componentFactory.createButton("New Invoice and Property", "click to enter the details of a new invoice and property");
    newInvoiceAndPropertyButton.setOnAction((e) -> {
      InvoiceAndPropertyEditor2.newInstance(customization, invoicesAndPropertiesService).show();
    });
    controlsBox.getChildren().add(newInvoiceAndPropertyButton);

    rootLayout.setTop(controlsBox);

    
//    // Add the background image
//    Canvas backGroundCanvas = createCollage();
//
//    if (backGroundCanvas == null) {
//      backGroundCanvas = new Canvas();
//      GraphicsContext gc = backGroundCanvas.getGraphicsContext2D();
//      gc.drawImage(appResources.getPicture(), 0, 0);
//    }
        
//    invoicesAndPropertiesTable.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
//    invoicesAndPropertiesTable.setStyle("-fx-control-inner-background: transparent; -fx-table-column-header-background: transparent; -fx-background-color: transparent; -fx-table-cell-border-color: transparent; -fx-text-fill: black;");
    
//    Image bgImage = backGroundCanvas.snapshot(null, new WritableImage(500, 500));
//    BackgroundImage backgroundImage = new BackgroundImage(bgImage, null, null, null, null);
//    Background bg = new Background(backgroundImage);
    invoicesAndPropertiesTable.setObjects(new EObjectListContainerSpecification(invoicesAndPropertiesService.getInvoicesAndPropertiesResource().getEObject(), InvAndPropPackage.eINSTANCE.getInvoicesAndProperties_Invoicseandpropertys()));

    VBox mainLayout = new VBox();
//    mainLayout.setBackground(bg);
    mainLayout.getChildren().add(invoicesAndPropertiesTable);
    mainLayout.getChildren().add(invoiceAndPropertyItemsTable);
    
    HBox documentsAndPicturesHBox = componentFactory.createHBox();
    
    VBox vBox = componentFactory.createVBox(12.0, 12.0);   
    Label label = componentFactory.createStrongLabel("Documents");
    vBox.getChildren().add(label);
    documentReferences = componentFactory.createListView(FXCollections.observableArrayList());
    documentReferences.setPlaceholder(new Label("No documents for current selection"));
    documentReferences.setCellFactory((listView) -> new FileReferenceListCell(listView));
    vBox.getChildren().add(documentReferences);
    documentsAndPicturesHBox.getChildren().add(vBox);
    
    vBox = componentFactory.createVBox(12.0, 12.0);   
    label = componentFactory.createStrongLabel("Pictures");
    vBox.getChildren().add(label);
    pictureReferences = componentFactory.createListView(FXCollections.observableArrayList());
    pictureReferences.setPlaceholder(new Label("No pictures for current selection"));
    pictureReferences.setCellFactory((listView) -> new FileReferenceListCell(listView));
    pictureReferences.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updatePictureViewPanel(newValue));
    vBox.getChildren().add(pictureReferences);
    documentsAndPicturesHBox.getChildren().add(vBox);
    
    pictureViewPanel = componentFactory.createHBox(0.0, 12.0);
    documentsAndPicturesHBox.getChildren().add(pictureViewPanel);
    
    mainLayout.getChildren().add(documentsAndPicturesHBox);
    

    rootLayout.setCenter(mainLayout);

    statusBar = new Label("Welcome to Invoices and Properties");
    rootLayout.setBottom(statusBar);

    setScene(new Scene(rootLayout));
  }

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
   * Show documents of selected InvoiceAndProperty plus selected InvoiceAndPropertyItem.
   */
  private void updateDocumentsList() {
    documentReferences.getItems().clear();
    
    InvoiceAndProperty invoiceAndProperty = invoicesAndPropertiesTable.getSelectedObject();
    
    if (invoiceAndProperty != null) {
      documentReferences.getItems().addAll(invoiceAndProperty.getDocuments());
    }
    
    InvoiceAndPropertyItem invoiceAndPropertyItem = invoiceAndPropertyItemsTable.getSelectedObject();
    
    if (invoiceAndPropertyItem != null) {
      documentReferences.getItems().addAll(invoiceAndPropertyItem.getDocuments());
    }
  }
  
  /**
   * Show pictures of selected InvoiceAndProperty plus selected InvoiceAndPropertyItem.
   */
  private void updatePicturesList() {
    pictureReferences.getItems().clear();
    
    InvoiceAndProperty invoiceAndProperty = invoicesAndPropertiesTable.getSelectedObject();
    
    if (invoiceAndProperty != null) {
      pictureReferences.getItems().addAll(invoiceAndProperty.getPictures());
    }
    
    InvoiceAndPropertyItem invoiceAndPropertyItem = invoiceAndPropertyItemsTable.getSelectedObject();
    
    if (invoiceAndPropertyItem != null) {
      pictureReferences.getItems().addAll(invoiceAndPropertyItem.getPictures());
    }
    
    if (pictureReferences.getItems().isEmpty()) {
      updatePictureViewPanel(null);
    } else {
      updatePictureViewPanel(pictureReferences.getItems().get(0));
    }
  }
  
  private void updatePictureViewPanel(FileReference fileReference) {
    LOGGER.info("=> fileReference =" + fileReference);
    pictureViewPanel.getChildren().clear();

    if (fileReference != null) {
      String fileName = prependBaseDirToFilename(fileReference.getFile());
      if (fileName != null) {
        LOGGER.info("fileName=" + fileName);
        Image image = new Image("file:" + fileName, 300, 300, true, true);
        ImageView imageView = new ImageView(image);

        pictureViewPanel.getChildren().add(imageView);
      }
    }
  }
  
  private String prependBaseDirToFilename(String filename) {
    File file = new File(InvoicesAndPropertiesRegistry.propertyRelatedFilesFolder, filename);
    
    return file.getAbsolutePath();
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
    LOGGER.info("number of properties: " + invoicesAndProperties.getInvoicseandpropertys().size());
    
    // Candidates are the first pictures of properties which aren't archived.
    List<FileReference> candidates = new ArrayList<>();
    for (InvoiceAndProperty invoiceAndProperty: invoicesAndProperties.getInvoicseandpropertys()) {
      FileReference fileReference = null;
      if (!invoiceAndProperty.isArchive()  &&  invoiceAndProperty.getPictures().size() > 0) {
        fileReference = invoiceAndProperty.getPictures().get(0);
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
  
  
  public static void openDocument(FileReference fileReference) {
    String fileName = fileReference.getFile();
    if (fileName == null  ||  fileName.isEmpty()) {
      return;
    }
    
    fileName = InvoicesAndPropertiesUtil.prependBaseDirToRelativeFilename(fileName);
    try {
      File file = new File(fileName);
      Desktop.getDesktop().open(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}


class FileReferenceListCell extends ListCell<FileReference> {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(FileReferenceListCell.class.getName());
  
  public FileReferenceListCell(ListView<FileReference> listView) {
    addEventHandler(DoubleClickEventDispatcher.MOUSE_DOUBLE_CLICKED, e -> handleMouseDoubleClickedEvent(e));
  }
  
  private void handleMouseDoubleClickedEvent(MouseEvent mouseEvent) {
    FileReferenceListCell fileReferenceListCell = (FileReferenceListCell) mouseEvent.getSource();
    FileReference fileReference = fileReferenceListCell.getItem();
    InvoicesAndPropertiesWindow.openDocument(fileReference);
  }
  
  @Override
  public void updateItem(FileReference item, boolean empty) {
    super.updateItem(item, empty);
    if (empty  ||  item == null) {
      setText("");
    } else {
      // item text
      String string = item.getTitle();
      if ((string == null)  &&  (item.getFile() != null)) {
        File file = new File(item.getFile());
        string = file.getName();
      }
      
      setText(string);
            
      // item context menu
      ContextMenu contextMenu = new ContextMenu();
      MenuItem menuItem = new MenuItem("Open document");
      menuItem.setOnAction(_ -> DesktopUtil.open(InvoicesAndPropertiesUtil.prependBaseDirToRelativeFilename(item.getFile())));
      contextMenu.getItems().add(menuItem);
      setContextMenu(contextMenu);

    }
  }
}
