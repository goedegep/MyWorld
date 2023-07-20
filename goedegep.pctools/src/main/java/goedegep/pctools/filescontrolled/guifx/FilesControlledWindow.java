package goedegep.pctools.filescontrolled.guifx;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import goedegep.appgen.TableRowOperation;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttreeview.EObjectTreeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassListReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.jfx.eobjecttreeview.NodeOperationDescriptor;
import goedegep.jfx.eobjecttreeview.PresentationType;
import goedegep.jfx.workerstategui.WorkerStateMonitorWindow;
import goedegep.pctools.app.logic.PCToolsRegistry;
import goedegep.pctools.filescontrolled.logic.CheckFilesTask;
import goedegep.pctools.filescontrolled.logic.ControlledSetBuildingTask;
import goedegep.pctools.filescontrolled.model.ControlledFolderInfo;
import goedegep.pctools.filescontrolled.model.ControlledRootFolderInfo;
import goedegep.pctools.filescontrolled.model.DescribedItem;
import goedegep.pctools.filescontrolled.model.DirectorySpecification;
import goedegep.pctools.filescontrolled.model.DiscStructureSpecification;
import goedegep.pctools.filescontrolled.model.FileInfo;
import goedegep.pctools.filescontrolled.model.FolderInfo;
import goedegep.pctools.filescontrolled.model.PCToolsFactory;
import goedegep.pctools.filescontrolled.model.PCToolsPackage;
import goedegep.pctools.filescontrolled.model.Result;
import goedegep.pctools.filescontrolled.model.UncontrolledRootFolderInfo;
//import goedegep.pctools.filescontrolled.types.EqualityType;
//import goedegep.pctools.filescontrolled.model.DescribedItem;
//import goedegep.pctools.filescontrolled.model.DirectorySpecification;
//import goedegep.pctools.filescontrolled.model.DiscStructureSpecification;
import goedegep.pctools.filescontrolled.types.FileCopyInfo;
//import goedegep.pctools.filescontrolled.types.FileInfo;
import goedegep.pctools.filescontrolled.types.FileInfoMap;
//import goedegep.pctools.filescontrolled.model.PCToolsFactory;
//import goedegep.pctools.filescontrolled.model.PCToolsPackage;
import goedegep.util.Tuplet;
import goedegep.util.emf.EMFResource;
import goedegep.util.emf.EmfPackageHelper;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

/**
 * This tool checks for uncontrolled files and indicates whether uncontrolled files are copies of controlled files.
 * <p/>
 * What the program does:
 * <ul>
 * <li>
 * build a set of all checked directories and files<br/>
 * If the same file is found more than once, this is reported.
 * </li>
 * <li>
 * scan all uncontrolled directories and files that need to be checked and for each:
 *   <ul>
 *   <li>
 *   If there is a copy in the set of checked directories and files, show it in the Tree 'copy available'.<br/>
 *   There is a context menu for each node in the tree with the options: 'open location in explorer window' and 'delete'.
 *   </li>
 *   <li>
 *   If there is no copy in the set of checked directories and files, show it in the Tree 'uncontrolled directories and files'.<br/>
 *   There is a context menu for each node in the tree with the options: 'open location in explorer window' and 'delete'?
 *   </li>
 *   </ul>
 * </li>
 * </ul>
 *     
 * Implementation<br/>
 * Unique identification of a file:<br/>
 * A copy of a file shall have the same name, but will of course be in a different directory. So the directory (or the full path) can not
 * be part of the identification. The identification consist of:
 * <ul>
 * <li>filename</li>
 * <li>md5</li>
 * <li>last modification time</li>
 * </ul>
 * For each file the following information is stored:
 * <ul>
 * <li>filename</li>
 * <li>absolute directory path</li>
 * </ul>
 * 
 * Algorithm:  
 * <ul>
 * <li>
 * Build set of controlled directories and files
 *   <ul>
 *   <li>
 *   For all controlled directories in the Disc Structure Specification, iterate over all directories/files. 
 *     <ul>
 *     <li>
 *     For each file the unique identification (key) is generated, the file information (value) is created and this information is stored in a HashSet.
 *     </li>
 *     </ul>
 *   </li>
 *   </ul>
 * </li>
 * <li>
 * Check the directories and files to be checked.
 *   <ul>
 *   <li>
 *   For each directory to take into account:
 *     <ul>
 *     <li>
 *     If it is not a controlled directory, scan the whole tree. Stop when a controlled directory is encountered.<br/>
 *     For each file the unique identification (key) is generated. Try to find it in the set of controlled items. If found, it is added to the
 *     'copy available' list, else it is added to the 'uncontrolled directories and files' list.
 *     </li>
 *     </ul>
 *   </li>
 *   </ul>
 * </li>
 * </ul>
 */
public class FilesControlledWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(FilesControlledWindow.class.getName());
  private static final String WINDOW_TITLE = "Disc Structure Check";
  
  private static final PCToolsPackage PC_TOOLS_PACKAGE = PCToolsPackage.eINSTANCE;  
  private static final PCToolsFactory PCTOOLS_FACTORY = PCToolsFactory.eINSTANCE;
  
  private ComponentFactoryFx componentFactory = null;
  private EMFResource<DiscStructureSpecification> emfResource = null;
  private DiscStructureSpecification discStructureSpecification = null;
  private EObjectTreeView treeView = null;
  private Label statusLabel = new Label("Nothing to report");
  private VBox resultSelectionBox = null;
  private VBox resultBox = null;
  private BorderPane tabLayoutUncontrolled = null;
  private BorderPane tabLayoutControlledCopy = null;
  
  private MenuItem generateDiscStructureReportMenuItem = null;   // This menu item changes from 'generate' to 'cancel'.
  private boolean generatingDiscStructureReport = false;
  private ControlledSetBuildingTask controlledSetBuildingTask;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   */
  public FilesControlledWindow(CustomizationFx customization) {
    super(WINDOW_TITLE, customization);
    LOGGER.info("=>");
    
    componentFactory = customization.getComponentFactoryFx();
    
    emfResource = new EMFResource<>(
        PCToolsPackage.eINSTANCE,
        () -> PCToolsFactory.eINSTANCE.createDiscStructureSpecification(), ".xmi");
    emfResource.dirtyProperty().addListener((observable, oldValue, newValue) -> updateTitle());
    
    createControls();
    createGUI();

    if (PCToolsRegistry.defaultDiscStructureSpecificationFile != null) {
      openDiscStructureSpecification(PCToolsRegistry.defaultDiscStructureSpecificationFile);
    } else {
      newDiscStructureSpecification();
    }

    show();
    
    LOGGER.info("<=");
  }
  
  /**
   * Create the instance level GUI controls.
   */
  private void createControls() {
    EObjectTreeDescriptor eObjectTreeDescriptor = createEObjectTreeDescriptor();
    treeView = new EObjectTreeView(discStructureSpecification, eObjectTreeDescriptor, true);
    treeView.setEditable(true);
    treeView.setMinWidth(600);
  }
  
  /**
   * Create the GUI.
   * <p>
   * Main pane is a BorderPane.
   * North is the menu bar
   * Center is an HBox with:
   *   - first item (left) is a TreeView for the Disc Structure Specification.
   *   - second item (right) is TabPane for the results:
   *     - first tab is for the "Controlled files which have a copy under controlled files."
   *     - second tab is for the "Files without a controlled copy."
   *     - third tab is for the "Files with a controlled copy."
   * Bottom is a status label
   */
  private void createGUI() {
    BorderPane mainPane = new BorderPane();
    
    mainPane.setTop(createMenuBar(PCToolsRegistry.developmentMode));
    
    HBox centerLayout = new HBox();
    
    centerLayout.getChildren().add(treeView);
    
    resultSelectionBox = componentFactory.createVBox(8.0, 12.0);
    centerLayout.getChildren().add(resultSelectionBox);
    
    resultBox = componentFactory.createVBox(8.0, 12.0);
    centerLayout.getChildren().add(resultBox);
    
    TabPane resultTreesPane = new TabPane();
    resultTreesPane.setMinWidth(400);
    
    Tab tab;
    Label label;
    
    tab = new Tab();
    tab.setText("Uncontrolled");
    tabLayoutUncontrolled = new BorderPane();
    label = new Label("Files without a controlled copy.");
    tabLayoutUncontrolled.setTop(label);
    label = new Label("no results available yet");
    label.setStyle("-fx-text-fill: LightGray; -fx-font-size: 200%;");
    tabLayoutUncontrolled.setCenter(label);
    tab.setContent(tabLayoutUncontrolled);
    resultTreesPane.getTabs().add(tab);
    
    tab = new Tab();
    tab.setText("Controlled copy");
    tabLayoutControlledCopy = new BorderPane();
    label = new Label("Files with a controlled copy.");
    tabLayoutControlledCopy.setTop(label);
    label = new Label("no results available yet");
    label.setStyle("-fx-text-fill: LightGray; -fx-font-size: 200%;");
    tabLayoutControlledCopy.setCenter(label);
    tab.setContent(tabLayoutControlledCopy);
    resultTreesPane.getTabs().add(tab);
    
    centerLayout.getChildren().add(resultTreesPane);
    mainPane.setCenter(centerLayout);
    
    mainPane.setBottom(statusLabel);

    setScene(new Scene(mainPane, 2000, 600));
    
  }

  /**
   * Create the EObjectTreeDescriptor for the DiscStructureSpecification tree view.
   * 
   * @return the EObjectTreeDescriptor for the DiscStructureSpecification tree view
   */
  private EObjectTreeDescriptor createEObjectTreeDescriptor() {
    EmfPackageHelper discStructureSpecificationPackageHelper = new EmfPackageHelper(PC_TOOLS_PACKAGE);
    EObjectTreeDescriptor eObjectTreeDescriptor = new EObjectTreeDescriptor();
    
    createAndAddEObjectTreeDescriptorForDiscStructureSpecification(eObjectTreeDescriptor, discStructureSpecificationPackageHelper);
    createAndAddEObjectTreeDescriptorForDirectorySpecification(eObjectTreeDescriptor, discStructureSpecificationPackageHelper);
    createAndAddEObjectTreeDescriptorForDescribedItem(eObjectTreeDescriptor, discStructureSpecificationPackageHelper);

    return eObjectTreeDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.pctools.filescontrolled.model.DiscStructureSpecification.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the DiscStructureSpecification descriptor is to be added.
   * @param discStructureSpecificationPackageHelper an <code>EmfPackageHelper</code> for the <code>PCToolsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForDiscStructureSpecification(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper discStructureSpecificationPackageHelper) {
    EClass eClass = discStructureSpecificationPackageHelper.getEClass("goedegep.pctools.filescontrolled.model.DiscStructureSpecification");
        
    // DiscStructureSpecification (root node)
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> "Disc Structure Specification", true, null);

    // DiscStructureSpecification.directorySpecifications
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Create Directory Specification"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(PC_TOOLS_PACKAGE.getDiscStructureSpecification_DirectorySpecifications(), "Directory specifications", true, nodeOperationDescriptors));
    
    // DiscStructureSpecification.filesToIgnoreCompletely
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New file to ignore completely"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(PC_TOOLS_PACKAGE.getDiscStructureSpecification_FilesToIgnoreCompletely(), "Files to ignore completely", true, nodeOperationDescriptors));
    
    // DiscStructureSpecification.directoriesToIgnoreCompletely
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New directory to ignore completely"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(PC_TOOLS_PACKAGE.getDiscStructureSpecification_DirectoriesToIgnoreCompletely(), "Directories to ignore completely", true, nodeOperationDescriptors));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.pctools.filescontrolled.model.DirectorySpecification.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the DirectorySpecification descriptor is to be added.
   * @param discStructureSpecificationPackageHelper an <code>EmfPackageHelper</code> for the <code>PCToolsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForDirectorySpecification(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper discStructureSpecificationPackageHelper) {
    EClass eClass = discStructureSpecificationPackageHelper.getEClass("goedegep.pctools.filescontrolled.model.DirectorySpecification");
        
    // DirectorySpecification
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New Directory Specification before this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New Directory Specification after this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Move Directory Specification up"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Move Directory Specification down"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete Directory Specification"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass,
        eObject -> {
          StringBuilder buf = new StringBuilder();
          DirectorySpecification directorySpecification = (DirectorySpecification) eObject;
          if (directorySpecification.isSetDirectoryPath()) {
            buf.append(directorySpecification.getDirectoryPath());
          } else {
            buf.append("<name not specified>");
          }
          buf.append(": ");
          if (directorySpecification.isControlled()) {
            buf.append("(controlled)");
          } else {
            buf.append("(not controlled)");
          }
          
          return buf.toString();
        }, false, nodeOperationDescriptors);
    
    // DirectorySpecification.directoryPath
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PC_TOOLS_PACKAGE.getDirectorySpecification_DirectoryPath(), "Directory path", PresentationType.FOLDER, null));
    // DirectorySpecification.description
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PC_TOOLS_PACKAGE.getDirectorySpecification_Description(), "Description", PresentationType.MULTI_LINE_TEXT, null));
    // DirectorySpecification.synchronizationSpecification
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PC_TOOLS_PACKAGE.getDirectorySpecification_SynchronizationSpecification(), "Synchronization Specification", null));
    // DirectorySpecification.sourceControlSpecification
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PC_TOOLS_PACKAGE.getDirectorySpecification_SourceControlSpecification(), "Source Control Specification", null));
    // DirectorySpecification.toBeChecked
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PC_TOOLS_PACKAGE.getDirectorySpecification_ToBeChecked(), "To be checked", null));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.pctools.filescontrolled.model.DescribedItem.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the DescribedItem descriptor is to be added.
   * @param discStructureSpecificationPackageHelper an <code>EmfPackageHelper</code> for the <code>PCToolsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForDescribedItem(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper discStructureSpecificationPackageHelper) {
    EClass eClass = discStructureSpecificationPackageHelper.getEClass("goedegep.pctools.filescontrolled.model.DescribedItem");
        
    // DescribedItem
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New item before this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New item after this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Move item up"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Move item down"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete item"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass,
        eObject -> {
          DescribedItem describedItem = (DescribedItem) eObject;
          
          if (describedItem.isSetItem()) {
            return describedItem.getItem();
          }
          else {
            return "<not specified>";
          }
        }, false, nodeOperationDescriptors);
    
    // DescribedItem.item
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PC_TOOLS_PACKAGE.getDescribedItem_Item(), "Item", null));
    // DescribedItem.description
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PC_TOOLS_PACKAGE.getDescribedItem_Description(), "Description", PresentationType.MULTI_LINE_TEXT, null));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }
  
  /**
   * Create the menu bar for this window.
   * @param developmentMode 
   * 
   * @return the menu bar for this window.
   */
  private MenuBar createMenuBar(boolean developmentMode) {
    MenuBar menuBar = componentFactory.createMenuBar();
    Menu menu;
    MenuItem menuItem;

    // File menu
    menu = componentFactory.createMenu("File");
    
    // File/New Disc Structure Specification
    menuItem = componentFactory.createMenuItem("New Disc Structure Specification");
    menuItem.setOnAction((e) -> newDiscStructureSpecification());
    menu.getItems().add(menuItem);
    
    // File/Open Disc Structure Specification ...
    menuItem = componentFactory.createMenuItem("Open Disc Structure Specification ...");
    menuItem.setOnAction((e) -> openDiscStructureSpecification());
    menu.getItems().add(menuItem);
    
    // File/Save Disc Structure Specification
    menuItem = componentFactory.createMenuItem("Save Disc Structure Specification");
    menuItem.setOnAction((e) -> saveDiscStructureSpecification());
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
    menu.getItems().add(menuItem);
    
    // File/Save Disc Structure Specification as ...
    menuItem = componentFactory.createMenuItem("Save Disc Structure Specification as ...");
    menuItem.setOnAction((e) -> saveDiscStructureSpecificationAs());
    menu.getItems().add(menuItem);
    
    // File/Generate Disc Structure Report
    // This menu item toggles between "Generate Disc Structure Report" (if generatingDiscStructureReport is false) and
    // "Cancel Disc Structure Report generation" (if generatingDiscStructureReport is true).
    generateDiscStructureReportMenuItem = componentFactory.createMenuItem("Generate Disc Structure Report");
    generateDiscStructureReportMenuItem.setOnAction((e) -> {
      if (generatingDiscStructureReport) {
        // clean up
        controlledSetBuildingTask.cancel();
        generateDiscStructureReportMenuItem.setText("Generate Disc Structure Report");
        generatingDiscStructureReport = false;
      } else {
        generatingDiscStructureReport = true;
        generateDiscStructureReportMenuItem.setText("Cancel Disc Structure Report generation");
        generateDiscStructureReportStep1(discStructureSpecification);
      }
    });
    menu.getItems().add(generateDiscStructureReportMenuItem);
    
    // File/Print
    menuItem = componentFactory.createMenuItem("Print");
    menuItem.setOnAction((e) -> System.out.println(treeView.toString()));
    menu.getItems().add(menuItem);
    
    // File/Use Test Disc Structure Specification (Development only)
    if (developmentMode) {
      
      menuItem = componentFactory.createMenuItem("Use Test Disc Structure Specification");
      menuItem.setOnAction((e) -> {
        createTestDiscStructureSpecification();
        treeView.setEObject(discStructureSpecification);
      });
      menu.getItems().add(menuItem);
      
      // File/Use hard-coded Disc Structure Specification (Development only)
      menuItem = componentFactory.createMenuItem("Use hard-coded Disc Structure Specification");
      menuItem.setOnAction((e) -> {
        createDiscStructureSpecification();
        treeView.setEObject(discStructureSpecification);
      });
      menu.getItems().add(menuItem);
    }
    
    // File/Exit
    menuItem = componentFactory.createMenuItem("Exit");
    menuItem.setOnAction((e) -> Platform.exit());
    menu.getItems().add(menuItem);
    
    menuBar.getMenus().add(menu);
    
    // Help menu
    menu = componentFactory.createMenu("Help");
    
    // Help/Help
    menuItem = componentFactory.createMenuItem("Help");
    menuItem.setOnAction((e) -> provideHelp());
    menu.getItems().add(menuItem);
    
    menuBar.getMenus().add(menu); 

    return menuBar;
  }
  
  /**
   * Create a Disc Structure Specification.
   * This specification will later be stored as EMF resource.
   * 
   * @return The Disc Structure Specification for my Acer Aspire.
   */
  private void createDiscStructureSpecification() {
    PCToolsFactory pcToolsFactory = PCToolsFactory.eINSTANCE;
    discStructureSpecification = emfResource.newEObject();
    DescribedItem describedItem = null;
    
//    DiscStructureSpecification discStructureSpecification = pcToolsFactory.createDiscStructureSpecification();
    discStructureSpecification.setName("Acer Aspire disc structuur");
    discStructureSpecification.setDescription("Belangrijkste directories op deze PC.");
    
    describedItem = pcToolsFactory.createDescribedItem();
    describedItem.setItem("desktop.ini");
    describedItem.setDescription("Windows systeem bestand");
    discStructureSpecification.getFilesToIgnoreCompletely().add(describedItem);
    describedItem = pcToolsFactory.createDescribedItem();
    describedItem.setItem("klaar.txt");
    describedItem.setDescription("Leeg bestand wat ik gebruik om aan te geven dat een foto map klaar is");
    discStructureSpecification.getFilesToIgnoreCompletely().add(describedItem);
    describedItem = pcToolsFactory.createDescribedItem();
    describedItem.setItem("Thumbs.db");
    describedItem.setDescription("Windows systeem bestand");
    discStructureSpecification.getFilesToIgnoreCompletely().add(describedItem);
    
    describedItem = pcToolsFactory.createDescribedItem();
    describedItem.setItem(".metadata");
    describedItem.setDescription("Meta data map van een Eclipse workspace");
    discStructureSpecification.getDirectoriesToIgnoreCompletely().add(describedItem);
    describedItem = pcToolsFactory.createDescribedItem();
    describedItem.setItem("target");
    describedItem.setDescription("Output map van een Eclipse project");
    discStructureSpecification.getDirectoriesToIgnoreCompletely().add(describedItem);
    describedItem = pcToolsFactory.createDescribedItem();
    describedItem.setItem("Adobe Premiere Elements 8.0");
    describedItem.setDescription("Eigenlijk de submap Resources, maar die naam is te generiek. Hier staan wat kopien van foto's.");
    discStructureSpecification.getDirectoriesToIgnoreCompletely().add(describedItem);
//    describedItem = pcToolsFactory.createDescribedItem();
//    describedItem.setItem("Jacqueline");
//    describedItem.setDescription("Voorlopig even de bestanden van Jacqueline overslaan.");
//    discStructureSpecification.getDirectoriesToIgnoreCompletely().add(describedItem);
    
    DirectorySpecification directorySpecification;
    
//    directorySpecification = new DirectorySpecification();
//    directorySpecification.setDirectoryPath("C:\\Applic");
//    directorySpecification.setDescription("Gesynchroniseerde applicaties");
//    directorySpecification.setSynchronizationSpecification("\\\\NAS-DS216J\\Applic");
//    discStructureSpecification.getDirectorySpecifications().add(directorySpecification);
    
    directorySpecification = pcToolsFactory.createDirectorySpecification();
    directorySpecification.setDirectoryPath("D:\\Jacqueline");
    directorySpecification.setDescription("Alle bestanden van Jacqueline");
    directorySpecification.setSynchronizationSpecification("\\\\NAS-DS216J\\Jacqueline");
    discStructureSpecification.getDirectorySpecifications().add(directorySpecification);
    
    directorySpecification = pcToolsFactory.createDirectorySpecification();
    directorySpecification.setDirectoryPath("D:\\jacq back-up");
    directorySpecification.setDescription("Op te ruimen");
    discStructureSpecification.getDirectorySpecifications().add(directorySpecification);
    
//    directorySpecification = new DirectorySpecification();
//    directorySpecification.setDirectoryPath("D:\\Google Drive");
//    directorySpecification.setDescription("'oude' oplossing voor gedeelde bestanden");
//    directorySpecification.setSynchronizationSpecification("Google Drive");
//    discStructureSpecification.getDirectorySpecifications().add(directorySpecification);
    
//    directorySpecification = new DirectorySpecification();
//    directorySpecification.setDirectoryPath("F:");
//    directorySpecification.setDescription("USB Stick to check");
//    directorySpecification.setToBeChecked(true);
//    discStructureSpecification.getDirectorySpecifications().add(directorySpecification);

//    return discStructureSpecification;
  }

  /**
   * Create a test Disc Structure Specification.
   * This specification applies to the directory "src/test/resources/Test Directory Structure".
   * TODO: move this to a JUnit test after the main development is done.
   * @return
   */
  private void createTestDiscStructureSpecification() {
    PCToolsFactory pcToolsFactory = PCToolsFactory.eINSTANCE;
    discStructureSpecification = emfResource.newEObject();
    DescribedItem describedItem = null;
        
    discStructureSpecification.setName("Test disc structure specification");
    discStructureSpecification.setDescription("Specification for a directory structure under 'src/test/resources' for testing this program");
    
    describedItem = pcToolsFactory.createDescribedItem();
    describedItem.setItem("blablafile.bla");
    describedItem.setDescription("Dit is alleen maar blabla");
    discStructureSpecification.getFilesToIgnoreCompletely().add(describedItem);
    
    describedItem = pcToolsFactory.createDescribedItem();
    describedItem.setItem("ignoreMeDirectory");
    describedItem.setDescription("Een map die genegeerd moet worden");
    discStructureSpecification.getDirectoriesToIgnoreCompletely().add(describedItem);
    
    DirectorySpecification directorySpecification;
    
    directorySpecification = pcToolsFactory.createDirectorySpecification();
    directorySpecification.setDirectoryPath("C:\\MyWorld\\goedegep.pctools\\target\\test-classes\\Test Directory Structure\\directory not to be checked\\controlled directory");
    directorySpecification.setDescription("Controlled directory");
    directorySpecification.setSynchronizationSpecification("Yes this is marked as synchronized");
    discStructureSpecification.getDirectorySpecifications().add(directorySpecification);
    
    directorySpecification = pcToolsFactory.createDirectorySpecification();
    directorySpecification.setDirectoryPath("C:\\MyWorld\\goedegep.pctools\\target\\test-classes\\Test Directory Structure\\directory to be checked");
    directorySpecification.setDescription("Directory to be checked");
    directorySpecification.setToBeChecked(true);
    discStructureSpecification.getDirectorySpecifications().add(directorySpecification);
  }
  
  /**
   * Start with a new (empty) {@code DiscStructureSpecification}.
   */
  private void newDiscStructureSpecification() {
    discStructureSpecification = emfResource.newEObject();
    handleNewDiscStructureSpecification();
  }
  
  /**
   * Open an existing {@code DiscStructureSpecification}.
   * <p>
   * The user can select the file via a FileChooser.
   * 
   */
  private void openDiscStructureSpecification() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Disc Structure Specification");
    if (PCToolsRegistry.dataDirectory != null) {
      File dataDirectory = new File(PCToolsRegistry.dataDirectory);
      fileChooser.setInitialDirectory(dataDirectory);
    }
    File file = fileChooser.showOpenDialog(this);
    openDiscStructureSpecification(file.getAbsolutePath());
  }
  
  /**
   * Open a specific {@code DiscStructureSpecification}.
   * 
   * @param discStructureSpecificationFile the file to open.
   */
  private void openDiscStructureSpecification(String discStructureSpecificationFile) {
    LOGGER.info("Opening: " + discStructureSpecificationFile);

    try {
      discStructureSpecification = emfResource.load(discStructureSpecificationFile);
      handleNewDiscStructureSpecification();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (WrappedException wrappedException) {
      componentFactory.createExceptionDialog("An exception occurred while reading the file: '" + discStructureSpecificationFile + "'.", wrappedException).show();
    }
  }
  
  /**
   * Handle a new {@code DiscStructureSpecification}.
   */
  private void handleNewDiscStructureSpecification() {
    treeView.setEObject(discStructureSpecification);
    updateTitle();
  }
  
  private void saveDiscStructureSpecification() {
    if (!emfResource.getFileName().isEmpty()) {
      try {
        emfResource.save();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      saveDiscStructureSpecificationAs();
    }
  }
  
  private void saveDiscStructureSpecificationAs() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Sla Disc Structuur Specificatie op");
    File file = fileChooser.showSaveDialog(this);
    LOGGER.severe("Saving: " + file.getAbsolutePath());
    try {
      emfResource.save(file.getAbsolutePath());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void provideHelp() {
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
      try {
        Desktop.getDesktop().browse(new URI("https://petersdigitallife.nl/myworld-user-manual/pctools/files-maintenance/"));
      } catch (IOException e) {
        e.printStackTrace();
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
    }
  }
  
  /**
   * Perform the first step for generating a disc structure report.
   * <p>
   * In this step the controlled directories are scanned and information on each file is stored. Any files 
   * The work is done in a separate thread, for which a WorkerStateMonitorWindow is shown to show the progress.<br/>
   * The directory which is being handled is also show via the statusLabel.<br/>
   * Upon successful  completion, method generateDiscStructureReportStep2() is called.
   * 
   * @param discStructureSpecification the disc structure specification according to which the checking will be done.
   */
  private void generateDiscStructureReportStep1(DiscStructureSpecification discStructureSpecification) {
    LOGGER.severe("=>");
    
    tabLayoutUncontrolled.setCenter(null);
    tabLayoutControlledCopy.setCenter(null);

    Result result = PCTOOLS_FACTORY.createResult();
    controlledSetBuildingTask = new ControlledSetBuildingTask(discStructureSpecification, result);
    WorkerStateMonitorWindow<Tuplet<FileInfoMap, List<FileCopyInfo>>> workerMonitor = new WorkerStateMonitorWindow<>(getCustomization(), controlledSetBuildingTask);
    workerMonitor.setTitle("Building Controlled Set progress");
    workerMonitor.show();

    // show messages in the statusLabel
    controlledSetBuildingTask.messageProperty().addListener((observable, oldValue, newValue) -> {
//      LOGGER.severe("Message: " + newValue);
      statusLabel.setText(newValue);
    });

    // upon successful completion, call generateDiscStructureReportStep2()
    controlledSetBuildingTask.setOnSucceeded((event) -> {
//      LOGGER.severe("Step 1 succeeded: " + event);
      workerMonitor.close();
//      Tuplet<FileInfoMap, List<FileCopyInfo>> resultOld = controlledSetBuildingTask.getValue();
//      generateDiscStructureReportStep2(resultOld.getObject1(), resultOld.getObject2(), result);
      generateDiscStructureReportStep2(result);
      statusLabel.setText("Step1 ready");
    });
    
    // Report failure via statusLabel
    controlledSetBuildingTask.setOnFailed((event) -> {
      LOGGER.severe("Failed: " + (event != null ? event.toString() : "null event"));
      statusLabel.setText("Report generation failed: " + event.toString());
    });
    
    // Report cancelled via statusLabel
    controlledSetBuildingTask.setOnCancelled((event) -> {
      LOGGER.severe("Cancelled: " + event);
      statusLabel.setText("Report generation aborted: " + event.toString());
    });

    // report any intermediate suspicious file copies found.
    controlledSetBuildingTask.valueProperty().addListener((observable, oldValue, newValue) -> reportSuspiciousCopies(result));

    Thread  buildControlledSetThread = new Thread(controlledSetBuildingTask);
    buildControlledSetThread.setDaemon(true);
    buildControlledSetThread.start();

    LOGGER.severe("<=");
  }
  
//  private void generateDiscStructureReportStep2(FileInfoMap controlledFiles, List<FileCopyInfo> suspiciousFileCopyInfos, Result result) {
  private void generateDiscStructureReportStep2(Result result) {
//    LOGGER.severe("-> number of controlled files: " + controlledFiles.keySet().size() +
//        ", number of suspicious copies: " + suspiciousFileCopyInfos.size());
//    reportSuspiciousCopies(suspiciousFileCopyInfos);
    
//    CheckFilesTask checkFilesTask = new CheckFilesTask(controlledFiles, discStructureSpecification, result);
    CheckFilesTask checkFilesTask = new CheckFilesTask(discStructureSpecification, result);
    WorkerStateMonitorWindow<Result> workerMonitor = new WorkerStateMonitorWindow<>(getCustomization(), checkFilesTask);
    workerMonitor.setTitle("Checking Files progress");
    workerMonitor.show();
    
    // show messages in the statusLabel
    checkFilesTask.messageProperty().addListener((observable, oldValue, newValue) -> {
      LOGGER.info("Message: " + newValue);
      statusLabel.setText(newValue);
    });

    // Show results when ready
    checkFilesTask.setOnSucceeded((event) -> {
      LOGGER.severe("Step 2 succeeded: " + event);
      Result resultNew = checkFilesTask.getValue();
      handleResult(resultNew);
      reportSuspiciousCopies(result);
      reportEmptyFolders(result);
      reportResult(resultNew);
//      reportprobablyRemovableFiles(result.getObject1());
//      reportUncontrolledFiles(result.getObject2());
      workerMonitor.close();
      statusLabel.setText("Report ready");
      generateDiscStructureReportMenuItem.setText("Genereer disc structuur rapport");
      generatingDiscStructureReport = false;
    });
    
    checkFilesTask.setOnCancelled((event) -> {
      LOGGER.severe("Cancelled: " + event);
      statusLabel.setText("Genereren afgebroken: " + event.toString());
    });

    // Show intermediate results
    checkFilesTask.valueProperty().addListener((observable, oldValue, newValue) -> {
      LOGGER.severe("Changed...");
//      reportprobablyRemovableFiles(newValue.getObject1());
//      reportUncontrolledFiles(newValue.getObject2());
//      statusLabel.setText("Results so far: " + newValue.getObject1().size() + " (possible) copies found," + newValue.getObject2().size() + " uncontrolled files found");
      generateDiscStructureReportMenuItem.setText("Genereer disc structuur rapport");
      statusLabel.setText("Results so far: " + "TODO");
    });

    Thread  checkFilesThread = new Thread(checkFilesTask);
    checkFilesThread.setDaemon(true);
    checkFilesThread.start();
  }
  
  private void handleResult(Result result) {
    fillResultSelectionBox(result);
  }
  
  private void fillResultSelectionBox(Result result) {
    resultSelectionBox.getChildren().clear();  // TODO move to new
    Label label = componentFactory.createStrongLabel("Show:");
    resultSelectionBox.getChildren().add(label);
    
    ToggleGroup toggleGroup = new ToggleGroup();
    RadioButton radioButton;
    
    radioButton = new RadioButton("Controlled copies");
    radioButton.setToggleGroup(toggleGroup);
    radioButton.setOnAction((e) -> reportSuspiciousCopies(result));
    resultSelectionBox.getChildren().add(radioButton);
    
    radioButton = new RadioButton("Empty folders");
    radioButton.setToggleGroup(toggleGroup);
    radioButton.setOnAction((e) -> reportEmptyFolders(result));
    resultSelectionBox.getChildren().add(radioButton);
  }

  private void reportSuspiciousCopies(Result result) {
    resultBox.getChildren().clear();
    
    VBox vBox = componentFactory.createVBox(6.0, 12.0);
    ScrollPane scrollPane = new ScrollPane(vBox);
    Label label;
    
    label = componentFactory.createStrongLabel("Controlled files which have a copy under controlled files.");
    vBox.getChildren().add(label);
    
    for (ControlledRootFolderInfo rootFolder: result.getControlledrootfolderinfos()) {
      TreeIterator<EObject> treeIterator = rootFolder.eAllContents();
      while (treeIterator.hasNext()) {
        EObject eObject = treeIterator.next();
        if (eObject instanceof FileInfo controlledFileInfo) {
          FileInfo copyFileInfo = controlledFileInfo.getCopyOf();
          if (copyFileInfo != null) {
            LOGGER.info("Copy found: " + copyFileInfo.toString() + ", " + controlledFileInfo.toString());
            GridPane gridPane = componentFactory.createGridPane(12.0, 6.0, 4.0);
            label = componentFactory.createLabel("File:");
            gridPane.add(label, 0, 0);
            TextField textField = componentFactory.createTextField(controlledFileInfo.getFileName(), 900.0, null);
            textField.setEditable(false);
            gridPane.add(textField, 1, 0);
            label = componentFactory.createLabel("in:");
            gridPane.add(label, 2, 0);
            textField = componentFactory.createTextField(copyFileInfo.getFullPathname(), 1000.0, null);
            gridPane.add(textField, 1, 1);
            Button button = componentFactory.createButton("Delete", "Delete the file in this folder");
            button.setOnAction((e) -> {
              try {
                Path path = Paths.get(copyFileInfo.getFullPathname());
                LOGGER.severe("Going to delete: " + path.toString());
                Files.delete(path);
              } catch(IOException ioException) {
                ioException.printStackTrace();
              }
            });
            gridPane.add(button, 2, 1);
            textField = componentFactory.createTextField(controlledFileInfo.getFullPathname(), 1000.0, null);
            gridPane.add(textField, 1, 2);
            button = componentFactory.createButton("Delete", "Delete the file in this folder");
            button.setOnAction((e) -> {
              try {
                Path path = Paths.get(controlledFileInfo.getFullPathname());
                LOGGER.severe("Going to delete: " + path.toString());
                Files.delete(path);
              } catch(IOException ioException) {
                ioException.printStackTrace();
              }
            });
            gridPane.add(button, 2, 2);
            vBox.getChildren().add(gridPane);
          }
        }
      }
    }
    
    resultBox.getChildren().add(scrollPane);
    
    // controlled files with copies
    // file: <file> in
    //       <dir1> (right mouse to delete or delete button.)
    //       <dir2> (right mouse to delete or delete button.)
    
    // uncontrolled files with copies
    
//    Collections.sort(suspiciousFileCopyInfos);

//    TreeItem<String> rootTreeItem = new TreeItem<>("Suspicious copies");
//    rootTreeItem.setExpanded(true);
//
//    for (FileCopyInfo fileCopyInfo: suspiciousFileCopyInfos) {
//      StringBuilder buf = new StringBuilder();
//      buf.append(fileCopyInfo.getFirstFileFoundInfo().getFileName());
//      buf.append(" ");
//      buf.append(fileCopyInfo.getEqualityType().toString());
//      TreeItem<String> fileTreeItem = new TreeItem<>(buf.toString());
//      TreeItem<String> treeItem = new TreeItem<>(fileCopyInfo.getFirstFileFoundInfo().getDirectoryName());
//      fileTreeItem.getChildren().add(treeItem);
//      treeItem = new TreeItem<>(fileCopyInfo.getSecondFileFoundInfo().getDirectoryName());
//      fileTreeItem.getChildren().add(treeItem);
//      rootTreeItem.getChildren().add(fileTreeItem);
//    }
//    TreeView<String> treeView = new TreeView<> (rootTreeItem);
//
  }
  
  private List<String> reportEmptyFolders(Result result) {
    resultBox.getChildren().clear();
    
    List<String> allEmptyFolders = new ArrayList<>();
    
    for (ControlledRootFolderInfo rootFolder: result.getControlledrootfolderinfos()) {
      List<String> emptyFolders = findEmptyFolders(rootFolder);
      allEmptyFolders.addAll(emptyFolders);
    }
    
    for (UncontrolledRootFolderInfo rootFolder: result.getUncontrolledRootFolderInfos()) {
      List<String> emptyFolders = findEmptyFolders(rootFolder);
      allEmptyFolders.addAll(emptyFolders);
    }
    
    for (String emptyFolder: allEmptyFolders) {
      LOGGER.severe("Empty folder: " + emptyFolder);
      Label label = componentFactory.createLabel(emptyFolder);
      resultBox.getChildren().add(label);
    }
    return allEmptyFolders;
  }
  
  private List<String> findEmptyFolders(FolderInfo folderInfo) {
    List<String> emptyFolders = new ArrayList<>();
    
    TreeIterator<EObject> treeIterator = folderInfo.eAllContents();
    while (treeIterator.hasNext()) {
      EObject eObject = treeIterator.next();
      if (eObject instanceof FolderInfo) {
        if (eObject instanceof ControlledFolderInfo subFolderInfo) {
          if (subFolderInfo.getFileinfos().isEmpty()  && subFolderInfo.getSubFolderInfos().isEmpty()) {
            String emptyFolder = subFolderInfo.getFullPathname();
            LOGGER.severe("emptyFolder: " + emptyFolder);
            emptyFolders.add(emptyFolder);
          }
        }
      }
    }
    
    return emptyFolders;
  }
  
  private void reportResult(Result result) {
    EObjectTreeView treeView = new EObjectTreeView(result, false);
    tabLayoutControlledCopy.setCenter(treeView);
  }

//  /**
//   * Report probable removable files.
//   * 
//   * @param probablyRemovableFileCopyInfos
//   */
//  private void reportprobablyRemovableFiles(List<FileCopyInfo> probablyRemovableFileCopyInfos) {
//    TreeItem<String> rootTreeItem = new TreeItem<>("Probably removable files");
//    rootTreeItem.setExpanded(true);
//    
//    for (FileCopyInfo fileCopyInfo: probablyRemovableFileCopyInfos) {
//      StringBuilder buf = new StringBuilder();
//      buf.append(fileCopyInfo.getFirstFileFoundInfo().getFile().toString());
//      buf.append(" ");
//      buf.append(fileCopyInfo.getEqualityType().toString());
//      TreeItem<String> fileTreeItem = new TreeItem<>(buf.toString());
//
//      TreeItem<String> treeItem = new TreeItem<>(fileCopyInfo.getFirstFileFoundInfo().getDirectory().toString());
//      fileTreeItem.getChildren().add(treeItem);
//      treeItem = new TreeItem<>(fileCopyInfo.getSecondFileFoundInfo().getDirectory().toString());
//      fileTreeItem.getChildren().add(treeItem);
//      rootTreeItem.getChildren().add(fileTreeItem);
//    }
//    TreeView<String> treeView = new TreeView<> (rootTreeItem);
//    
//    tabLayoutControlledCopy.setCenter(treeView);
//  }

//  private void reportUncontrolledFiles(List<FileInfo> uncontrolledFiles) {
//    ListView<String> uncontrolledFilesListView = new ListView<String>();
//    
////    TreeItem<String> rootTreeItem = new TreeItem<>("Uncontolled files");
////    rootTreeItem.setExpanded(true);
//    
//    for (FileInfo fileInfo: uncontrolledFiles) {
//      uncontrolledFilesListView.getItems().add(fileInfo.getDirectory().toString() + "/" + fileInfo.getFile().toString());
//    }
//    
////    TreeView<String> treeView = new TreeView<> (rootTreeItem);
//    
//    tabLayoutUncontrolled.setCenter(uncontrolledFilesListView);
////    tabLayoutUncontrolled.setCenter(treeView);
//  }

//  private void addControlledFile(FileInfoMap controlledFiles, Path file, BasicFileAttributes attrs, List<FileCopyInfo> fileCopyInfos) throws IOException {
//    LOGGER.fine("=>");
//    
//    String fileId = generateFileId(file, attrs);
//    
//    FileInfo fileInfo = new FileInfo();
//    fileInfo.setDirectory(file.getParent());
//    fileInfo.setFile(file.getFileName());
//    LOGGER.fine("Adding fileInfo: " + fileInfo.toString());
//    
//    // check whether this file already exists, if so create 'copy found' report.
//    FileInfo copyFileInfo = controlledFiles.get(fileId);
//    if (copyFileInfo == null) {
//      controlledFiles.put(fileId, fileInfo);
//    } else {
//      FileCopyInfo fileCopyInfo = new FileCopyInfo();
//      fileCopyInfo.setFirstFileFoundInfo(copyFileInfo);
//      fileCopyInfo.setSecondFileFoundInfo(fileInfo);
//      fileCopyInfos.add(fileCopyInfo);
//    }
//    
//    LOGGER.fine("<=");
//  }
  
//  /**
//   * Generate an identification for a file, referred to as the FileId.
//   * <p>
//   * A FileId has the following format: &lt;MD5&gt;=&lt;file-name&gt;<br/>
//   * Where:<br/>
//   * &lt;MD5&gt is the 128-bit MD5 hash value<br/>
//   * &lt;file-name&gt; is the plain file name (i.e. without directory path)
//   * 
//   * @param file the file for which a fileId is to be generated
//   * @param attrs the BasicFileAttributes of the file (currently not used)
//   * @return the fileId of the file
//   * @throws IOException if the file could not be read
//   */
//  private String generateFileId(Path file, BasicFileAttributes attrs) throws IOException {
//    String fileName = file.getFileName().toString();
//    LOGGER.fine("fileName: " + fileName);
//    
////    String md5 = generateMD5String(file.toAbsolutePath().toString());
////    LOGGER.fine("md5: " + md5);
//    
////    FileTime lastModifiedTime = attrs.lastModifiedTime();
////    LOGGER.fine("lastModifiedTime: " + lastModifiedTime.toString());
////    String lastModifiedTimeText = String.valueOf(lastModifiedTime.toMillis());
////    LOGGER.fine("lastModifiedTimeText: " + lastModifiedTimeText);
//    
////    String fileId = md5 + "=" + lastModifiedTimeText + "=" + fileName;
////    String fileId = md5 + "=" + fileName;
//    String fileId = attrs.size() + "=" + fileName;
//    LOGGER.fine("fileId: " + fileId);
//
//    return fileId;
//  }
  
//  public static String generateMD5String(String fileName) throws IOException {
//    MessageDigest md;
//    try {
//      md = MessageDigest.getInstance("MD5");
//      FileInputStream fis = new FileInputStream(fileName);
//
//      byte[] dataBytes = new byte[1024];
//
//      int nread = 0;
//      while ((nread = fis.read(dataBytes)) != -1) {
//        md.update(dataBytes, 0, nread);
//      };
//      byte[] mdbytes = md.digest();
//      StringBuffer sb = new StringBuffer();
//      for (int i = 0; i < mdbytes.length; i++) {
//        sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
//      }
//      fis.close();
//
//      return sb.toString();
//    } catch (NoSuchAlgorithmException e) {
//      e.printStackTrace();
//    }
//    
//    return null;
//  }
  
//  /**
//   * This class is a {@link Task} for building a Controlled Set.
//   */
//  class ControlledSetBuildingTask extends Task<Tuplet<FileInfoMap, List<FileCopyInfo>>> {
//    private final DiscStructureSpecification discStructureSpecification;
//    private int directoriesHandled = 0;
//    
//    /**
//     * Constructor
//     * 
//     * @param discStructureSpecification the disc structure specification according to which the Controlled Set is to be created.
//     */
//    public ControlledSetBuildingTask(final DiscStructureSpecification discStructureSpecification) {
//      updateTitle("Build Control Set Task");
//      this.discStructureSpecification = discStructureSpecification;
//    }
//
//    /**
//     * Do the actual work.
//     */
//    @Override
//    protected Tuplet<FileInfoMap, List<FileCopyInfo>> call() throws Exception {
//      LOGGER.severe("=>");
//      
//      updateProgress(directoriesHandled, 100);
//      
//      FileInfoMap controlledFiles = new FileInfoMap();
//      List<FileCopyInfo> fileCopyInfos = new ArrayList<>();
//      Tuplet<FileInfoMap, List<FileCopyInfo>> result = new Tuplet<>(controlledFiles, fileCopyInfos);
//      
//      for (DirectorySpecification directorySpecification: discStructureSpecification.getDirectorySpecifications()) {
//        if (!directorySpecification.isControlled()) {
//          LOGGER.severe("Skipping uncontrolled directory: " + directorySpecification.getDirectoryPath());
//          continue;
//        }
//        
//        LOGGER.severe("Handling directory: " + directorySpecification.getDirectoryPath());
//        Path path = Paths.get(directorySpecification.getDirectoryPath());
//        
//        handleControlledDirectory(path, result);        
//      }
//      
//      LOGGER.severe("<=");
//      return result;
//    }
//    
//    private void handleControlledDirectory(Path path, Tuplet<FileInfoMap, List<FileCopyInfo>> result) {
//      LOGGER.severe("=>");
//      // use this i.s.o. Files.walkFileTree() to be able to skip controlled directories and directories to ignore.
//      try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
//        for (Path file: stream) {
//          String fileName = file.getFileName().toString();
//          LOGGER.fine("Handling file: " + fileName);
//          
//          if (Files.isDirectory(file)) {
//            LOGGER.fine("handling dir: " + file);
//            updateMessage("Handling controlled directory: " + file);
//            updateProgress(directoriesHandled, 100);
//            if (!discStructureSpecification.getDirectoriesToIgnoreCompletely().contains(fileName)) {
//              handleControlledDirectory(file, result);
//            } else {
//              LOGGER.severe("skipping directory to ignore completely: " + fileName);
//            }
////            try {
////              Thread.sleep(100);
////            } catch (InterruptedException e) {
////              e.printStackTrace();
////            }
//          } else {
//            LOGGER.fine("Handling file: " + file);
//            LOGGER.fine("fileName: " + fileName);
//            if (!discStructureSpecification.getFilesToIgnoreCompletely().contains(fileName)) {
//              if (addControlledFile(result.getObject1(), file, Files.readAttributes(file, BasicFileAttributes.class), result.getObject2())) {
//                updateValue(result);
//              }
//            } else {
//              LOGGER.severe("skipping file to ignore completely: " + fileName);
//            }
//          }
//        }
//      } catch (IOException | DirectoryIteratorException x) {
//        LOGGER.severe("Exception: " + x);
////        System.err.println(x);
//      }
//      LOGGER.severe("<=");
//    }
//
//    private boolean addControlledFile(FileInfoMap controlledFiles, Path file, BasicFileAttributes attrs, List<FileCopyInfo> fileCopyInfos) throws IOException {
//      LOGGER.fine("=>");
//      
//      boolean fileIsACopy = false;
//      String fileId = generateFileId(file, attrs);
//      
//      FileInfo fileInfo = new FileInfo();
//      fileInfo.setDirectory(file.getParent());
//      fileInfo.setFile(file.getFileName());
//      LOGGER.fine("Adding fileInfo: " + fileInfo.toString());
//      
//      // check whether this file already exists, if so create 'copy found' report.
//      FileInfo copyFileInfo = controlledFiles.get(fileId);
//      if (copyFileInfo == null) {
//        controlledFiles.put(fileId, fileInfo);
//      } else {
//        FileCopyInfo fileCopyInfo = new FileCopyInfo();
//        fileCopyInfo.setFirstFileFoundInfo(copyFileInfo);
//        fileCopyInfo.setSecondFileFoundInfo(fileInfo);
//        fileCopyInfos.add(fileCopyInfo);
//        fileIsACopy = true;
//      }
//      
//      LOGGER.fine("<= " + fileIsACopy);
//      return fileIsACopy;
//    }
//  }

  
//  class CheckFilesTask extends Task<Tuplet<List<FileCopyInfo>, List<FileInfo>>> {
//    private FileInfoMap controlledFiles;
//    private final DiscStructureSpecification discStructureSpecification;
//    
//    public CheckFilesTask(FileInfoMap controlledFiles, final DiscStructureSpecification discStructureSpecification) {
//      this.controlledFiles = controlledFiles;
//      this.discStructureSpecification = discStructureSpecification;
//    }
//
//    @Override
//    protected Tuplet<List<FileCopyInfo>, List<FileInfo>> call() throws Exception {
//      LOGGER.severe("=>");
//      List<FileCopyInfo> probablyRemovableFileCopyInfos = new ArrayList<>();
//      List<FileInfo> uncontrolledFiles = new ArrayList<>();
//      Tuplet<List<FileCopyInfo>, List<FileInfo>> result = new Tuplet<>(probablyRemovableFileCopyInfos, uncontrolledFiles);
//      
//      checkFiles(controlledFiles, discStructureSpecification, probablyRemovableFileCopyInfos, uncontrolledFiles);
//            
//      LOGGER.severe("<=");
//      return result;
//    }
//    
//    private void checkFiles(FileInfoMap controlledFiles, DiscStructureSpecification discStructureSpecification, List<FileCopyInfo> probablyRemovableFileCopyInfos, List<FileInfo> uncontrolledFiles) {
//      LOGGER.fine("=>");
//      
//      for (DirectorySpecification directorySpecification: discStructureSpecification.getDirectorySpecifications()) {
//        if (directorySpecification.isControlled()) {
//          LOGGER.fine("Skipping controlled directory: " + directorySpecification.getDirectoryPath());
//          continue;
//        }
//        
//        Path directory = Paths.get(directorySpecification.getDirectoryPath());
//        checkFilesInDirectory(controlledFiles, directory, probablyRemovableFileCopyInfos, uncontrolledFiles);
//      }
//          
//      LOGGER.fine("<=");
//    }
//
//    /**
//     * This method checks for uncontrolled files and files 
//     * @param controlledFiles
//     * @param directory
//     * @param probablyRemovableFileCopyInfos
//     * @param uncontrolledFiles
//     */
//    private void checkFilesInDirectory(FileInfoMap controlledFiles, Path directory,
//        List<FileCopyInfo> probablyRemovableFileCopyInfos, List<FileInfo> uncontrolledFiles) {
//      // Note: we cannot use Files.walkFileTree(), because we have to skip controlled directories and directories to ignore.
//      try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
//        for (Path file: stream) {
//          LOGGER.fine("Handling file/directory: " + file.getFileName().toString());
//          if (Files.isDirectory(file)) {
//            // recursively call this method.
//            checkFilesInDirectory(controlledFiles, file, probablyRemovableFileCopyInfos, uncontrolledFiles);
//          } else {
//            BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
//            String fileId = generateFileId(file, attr);
//            FileInfo thisFileInfo = new FileInfo();
//            thisFileInfo.setDirectory(file.getParent());
//            thisFileInfo.setFile(file.getFileName());
//            
//            FileInfo fileInfo = controlledFiles.get(fileId);
//            if (fileInfo == null) {
//              uncontrolledFiles.add(thisFileInfo);
//              LOGGER.fine("Uncontrolled file found: " + thisFileInfo.toString());
//            } else {
//              FileCopyInfo fileCopyInfo = new FileCopyInfo();
//              fileCopyInfo.setFirstFileFoundInfo(fileInfo);
//              fileCopyInfo.setSecondFileFoundInfo(thisFileInfo);
//              probablyRemovableFileCopyInfos.add(fileCopyInfo);
//              LOGGER.fine("Copy of file found: " + fileInfo.toString());
//            }
//          }
//        }
//      } catch (IOException | DirectoryIteratorException x) {
//        System.err.println(x);
//      }
//      
//      updateMessage("Directory checked: " + directory.toString());
//      try {
//        Thread.sleep(100);
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
//      
//    }
//  }
  
//  private static class RenameMenuTreeCell extends TextFieldTreeCell<TreeItemEObjectContent> {
//    private ContextMenu menu = new ContextMenu();
//
//    public RenameMenuTreeCell() {
//      super();
//
//      MenuItem renameItem = new MenuItem("Rename");
//      menu.getItems().add(renameItem);
//      renameItem.setOnAction(new EventHandler<ActionEvent>() {
//        @Override
//        public void handle(ActionEvent arg0) {
//          startEdit();
//        }
//      });
//    }
//
//    @Override
//    public void updateItem(TreeItemEObjectContent item, boolean empty) {
//      super.updateItem(item, empty);
//
//      if (!isEditing()) {
//        setContextMenu(menu);
//      }
//    }
//  }
  

  /**
   * Update the window title.
   * <p>
   * The format of the title is: &lt;title&gt; - &lt;dirty&gt;&lt;file-name&gt;<br/>
   * Where:<br/>
   * &lt;title&gt; is the language specific window title<br/>
   * &lt;dirty&gt; is a '*' symbol if the vacations file is dirty, empty otherwise<br/>
   * &lt;file-name&gt; is the name of the vacations file.
   * 
   */
  private void updateTitle() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(WINDOW_TITLE);
    buf.append(" - ");
    if (emfResource.isDirty()) {
      buf.append("*");
    }
    String fileName = emfResource.getFileName();
    if (fileName == null) {
      fileName = "<NoName>";
    }
    buf.append(fileName);
    
    setTitle(buf.toString());
  }
}
