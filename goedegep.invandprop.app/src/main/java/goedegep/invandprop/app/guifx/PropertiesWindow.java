package goedegep.invandprop.app.guifx;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.appgen.TableRowOperation;
import goedegep.appgen.TableRowOperationDescriptor;
import goedegep.invandprop.app.InvoicesAndPropertiesRegistry;
import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.InvoicesAndProperties;
import goedegep.invandprop.model.Property;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableControlPanel;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.types.model.FileReference;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PropertiesWindow extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger     LOGGER = Logger.getLogger(PropertiesWindow.class.getName());
  private static final String     WINDOW_TITLE = "Properties";

  private static final InvAndPropPackage INVOICES_AND_PROPERTIES_PACKAGE = InvAndPropPackage.eINSTANCE;
  
  
  private CustomizationFx customization;
  private InvoicesAndProperties invoicesAndProperties = null;
  
  private ComponentFactoryFx componentFactory;
  
  private EObjectTableControlPanel eObjectTableControlPanel;
  private EObjectTable<Property> propertiesTable;
  private VBox documentReferencesBox = null;
  private ListView<FileReference> documentReferences;
  private ListView<FileReferenceWrapper> pictureReferences;
  private HBox pictureViewPanel;

  /**
   * Constructor.
   * 
   * @param customization GUI customization.
   * @param invoicesAndProperties the invoices and properties from which the properties are shown.
   */
  public PropertiesWindow(CustomizationFx customization, InvoicesAndProperties invoicesAndProperties) {
    super(WINDOW_TITLE, customization);
    
    this.customization = customization;
    this.invoicesAndProperties = invoicesAndProperties;
    
    componentFactory = customization.getComponentFactoryFx();
    
    createGUI();
    
    eObjectTableControlPanel.filterTextProperty().addListener((observable, oldValue, newValue) -> propertiesTable.setFilterExpression(newValue, null));
    
    show();
  }
    
  /**
   * Create the GUI.
   * <p>
   * Top is the properties table.<br/>
   * Below that, three colums: document references, picture references and the selected picture.
   */
  private void createGUI() {
    VBox rootLayout = componentFactory.createVBox();
    
    eObjectTableControlPanel = new EObjectTableControlPanel(customization);
    HBox controlsBox = componentFactory.createHBox(12.0);
    controlsBox.getChildren().add(eObjectTableControlPanel);
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    controlsBox.getChildren().add(spacer);
    
    HBox editPropertyButtonBox = componentFactory.createHBox(0.0, 12.0);
    Button editPropertyButton = componentFactory.createButton("Edit Property", "click to edit the details of the selected property");
    editPropertyButton.setOnAction(e -> editSelectedProperty());
    editPropertyButtonBox.getChildren().add(editPropertyButton);
    controlsBox.getChildren().add(editPropertyButtonBox);
    
    HBox newPropertyButtonBox = componentFactory.createHBox(0.0, 12.0);
    Button newPropertyButton = componentFactory.createButton("New Property", "click to enter the details of a new property");
    newPropertyButton.setOnAction(e -> showPropertyEditor());
    newPropertyButtonBox.getChildren().add(newPropertyButton);
    controlsBox.getChildren().add(newPropertyButtonBox);
    rootLayout.getChildren().add(controlsBox);
    
    SplitPane splitPane = new SplitPane();
    splitPane.setOrientation(Orientation.VERTICAL);
    splitPane.setDividerPositions(0.6);
    splitPane.getItems().add(createPropertiesTable());
    
    HBox documentsAndPicturesHBox = componentFactory.createHBox();
    documentReferencesBox = componentFactory.createVBox();
    documentReferences = componentFactory.createListView(null);
    documentReferences.setPlaceholder(new Label("No documents"));
    documentReferences.setCellFactory((listView) -> new ListCellWithContextMenu(listView));
    documentReferencesBox.getChildren().add(documentReferences);
    documentsAndPicturesHBox.getChildren().add(documentReferencesBox);
    
    pictureReferences = componentFactory.createListView(FXCollections.observableArrayList());
    pictureReferences.setPlaceholder(new Label("No pictures"));
    pictureReferences.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updatePictureViewPanel(newValue));
    documentsAndPicturesHBox.getChildren().add(pictureReferences);
    pictureViewPanel = componentFactory.createHBox(0.0, 12.0);
    documentsAndPicturesHBox.getChildren().add(pictureViewPanel);
    
    propertiesTable.addObjectSelectionListener(this::handleNewPropertySelected);
    
    splitPane.getItems().add(documentsAndPicturesHBox);
    rootLayout.getChildren().add(splitPane);
    
    setScene(new Scene(rootLayout, 1300, 700));
  }
  
  public static void openDocument(FileReference fileReference) {
    String fileName = fileReference.getFile();
    try {
      File file = new File(InvoicesAndPropertiesRegistry.propertyRelatedFilesFolder + "\\" + fileName);
      Desktop.getDesktop().open(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Handle the fact that a new property is selected.
   * <p>
   * The documents and pictures lists are updated and the picture view panel is updated.
   * 
   * @param property the newly selected <code>Property</code>.
   */
  private void handleNewPropertySelected(Object source, Property property) {
    documentReferencesBox.getChildren().clear();

    if (property != null) {
      ObservableList<FileReference> observableDocumentsList = FXCollections.observableList(property.getDocuments());
      documentReferences = componentFactory.createListView(observableDocumentsList);
    } else {
      documentReferences = componentFactory.createListView(null);
    }
    documentReferences.setPlaceholder(new Label("No documents"));
    documentReferences.setCellFactory((listView) -> new ListCellWithContextMenu(listView));
    documentReferencesBox.getChildren().add(documentReferences);

    if (property != null) {
      pictureReferences.setItems(FileReferenceWrapper.createFileReferenceWrapperList(property.getPictures()));
    } else {
      pictureReferences.setItems(null);
    }
    
    if ((pictureReferences.getItems()) != null  &&  !pictureReferences.getItems().isEmpty()) {
      updatePictureViewPanel(pictureReferences.getItems().get(0));
    } else {
      updatePictureViewPanel(null);
    }
  }
  
  private void updatePictureViewPanel(FileReferenceWrapper fileReferenceWrapper) {
    LOGGER.info("=> fileReferenceWrapper=" + fileReferenceWrapper);
    pictureViewPanel.getChildren().clear();

    if (fileReferenceWrapper != null) {
      FileReference fileReference = fileReferenceWrapper.getFileReference();
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
  
  private void editSelectedProperty() {
    Property property = propertiesTable.getSelectedObject();
    if (property != null) {
      new InvoiceAndPropertyEditor(customization, property);
    }
  }
  
  private void showPropertyEditor() {
    new InvoiceAndPropertyEditor(customization, invoicesAndProperties);
  }

  /**
   * Create the properties table.
   * 
   * @return the created propertiesTable.
   */
  private EObjectTable<Property> createPropertiesTable() {
    propertiesTable = new EObjectTable<Property>(customization, INVOICES_AND_PROPERTIES_PACKAGE.getInvoice(), new PropertiesTableDescriptor(), invoicesAndProperties.getProperties(), invoicesAndProperties.getProperties().getProperties());
        
    return propertiesTable;
  }
}


/**
 * This class provides the descriptor for the properties table.
 */
class PropertiesTableDescriptor extends EObjectTableDescriptor<Property> {
  private static InvAndPropPackage INVOICES_AND_PROPERTIES_PACKAGE = InvAndPropPackage.eINSTANCE;
  
  private static List<EObjectTableColumnDescriptorAbstract<Property>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<Property>>of(
      new EObjectTableColumnDescriptorBasic<Property>(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Description(), "Description", true, true),
      new EObjectTableColumnDescriptorBasic<Property>(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Brand(), "Brand", true, true),
      new EObjectTableColumnDescriptorBasic<Property>(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Type(), "Type", true, true),
      new EObjectTableColumnDescriptorBasic<Property>(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_SerialNumber(), "Serialnumber", true, true),
      new EObjectTableColumnDescriptorBasic<Property>(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Remarks(), "Remarks", true, true),
      new EObjectTableColumnDescriptorBasic<Property>(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_FromDate(), "From date", true, true),
      new EObjectTableColumnDescriptorBasic<Property>(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_UntilDate(), "Until date", true, true),
      new EObjectTableColumnDescriptorBasic<Property>(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Archive(), "Archived", true, true),
      new EObjectTableColumnDescriptorBasic<Property>(INVOICES_AND_PROPERTIES_PACKAGE.getProperty_Expenditure(), "Expenditure", 300, true, true, new ExpenditureStringConverter())
      );
  
  @SuppressWarnings("serial")
  private static Map<TableRowOperation, TableRowOperationDescriptor<Property>> rowOperations = new HashMap<>() {
    {
    put(TableRowOperation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete"));
    }
  };
  
  public PropertiesTableDescriptor() {
    super("There are no properties to show", null, columnDescriptors, rowOperations);
  }
  
}

class ListCellWithContextMenu extends ListCell<FileReference> {
  
  public ListCellWithContextMenu(ListView<FileReference> listView) {
  }
  
  @Override
  public void updateItem(FileReference item, boolean empty) {
    if (empty) {
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
      menuItem.setOnAction(e -> PropertiesWindow.openDocument(item));
      contextMenu.getItems().add(menuItem);
      setContextMenu(contextMenu);

    }
  }
}

