package goedegep.pctools.filescontrolled.guifx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;

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
import goedegep.pctools.filescontrolled.model.DescribedItem;
import goedegep.pctools.filescontrolled.model.DirectorySpecification;
import goedegep.pctools.filescontrolled.model.DiscStructureSpecification;
import goedegep.pctools.filescontrolled.model.PCToolsFactory;
import goedegep.pctools.filescontrolled.model.PCToolsPackage;
//import goedegep.pctools.filescontrolled.model.DescribedItem;
//import goedegep.pctools.filescontrolled.model.DirectorySpecification;
//import goedegep.pctools.filescontrolled.model.DiscStructureSpecification;
import goedegep.pctools.filescontrolled.types.FileCopyInfo;
import goedegep.pctools.filescontrolled.types.FileInfo;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
  
  private ComponentFactoryFx componentFactory = null;
  private EMFResource<DiscStructureSpecification> emfResource = null;
  private DiscStructureSpecification discStructureSpecification = null;
  private EObjectTreeView treeView = null;
  private Label statusLabel = new Label("Nothing to report");
  private BorderPane tabLayoutCopy = null;
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
        () -> PCToolsFactory.eINSTANCE.createDiscStructureSpecification());

    if (PCToolsRegistry.defaultDiscStructureSpecificationFile != null) {
      try {
        discStructureSpecification = emfResource.load(PCToolsRegistry.defaultDiscStructureSpecificationFile);
      } catch (FileNotFoundException e) {
      }
    } else {
      discStructureSpecification = emfResource.newEObject();
    }
    
    
    setTitle(WINDOW_TITLE);
    setX(10);
    setY(20);
    
    /*
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
    BorderPane mainPane = new BorderPane();
    
    mainPane.setTop(createMenuBar(PCToolsRegistry.developmentMode));
    
    HBox centerLayout = new HBox();

    EObjectTreeDescriptor eObjectTreeDescriptor = createEObjectTreeDescriptor();
    treeView = new EObjectTreeView(discStructureSpecification, eObjectTreeDescriptor, true);
    treeView.setEditable(true);
    treeView.setMinWidth(600);
    centerLayout.getChildren().add(treeView);
    
    TabPane resultTreesPane = new TabPane();
    resultTreesPane.setMinWidth(1000);
    
    Tab tab = new Tab();
    tab.setText("Copy");
    tabLayoutCopy = new BorderPane();
    Label label = new Label("Controlled files which have a copy under controlled files.");
    tabLayoutCopy.setTop(label);
    label = new Label("no results available yet");
    label.setStyle("-fx-text-fill: LightGray; -fx-font-size: 200%;");
    tabLayoutCopy.setCenter(label);
    tab.setContent(tabLayoutCopy);
    resultTreesPane.getTabs().add(tab);
    
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
    
    tab = new Tab();
    tab.setText("Help text");
    BorderPane helpTextPane = new BorderPane();
    helpTextPane.setTop(new Label("Label for helptext"));
    helpTextPane.setCenter(createHelpText());
    tab.setContent(helpTextPane);
    resultTreesPane.getTabs().add(tab);
    
    centerLayout.getChildren().add(resultTreesPane);
    mainPane.setCenter(centerLayout);
    
    mainPane.setBottom(statusLabel);

    setScene(new Scene(mainPane, 1600, 600));

    show();
    
    LOGGER.info("<=");
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

    // Bestand menu
    menu = componentFactory.createMenu("Bestand");
    
    menuItem = componentFactory.createMenuItem("Nieuwe Disc Structuur Specificatie");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        newDiscStructureSpecification();
      }
    });
    menu.getItems().add(menuItem);
    
    menuItem = componentFactory.createMenuItem("Disc Structuur Specificatie openen ...");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        openDiscStructureSpecification();
      }
    });
    menu.getItems().add(menuItem);
    
    menuItem = componentFactory.createMenuItem("Disc Structuur Specificatie opslaan");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        saveDiscStructureSpecification();
      }
    });
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
    menu.getItems().add(menuItem);
    
    menuItem = componentFactory.createMenuItem("Disc Structuur Specificatie opslaan als ...");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        saveDiscStructureSpecificationAs();
      }
    });
    menu.getItems().add(menuItem);
    
    generateDiscStructureReportMenuItem = componentFactory.createMenuItem("Genereer disc structuur rapport");
    generateDiscStructureReportMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        if (generatingDiscStructureReport) {
          // clean up
          controlledSetBuildingTask.cancel();
          generateDiscStructureReportMenuItem.setText("Genereer disc structuur rapport");
          generatingDiscStructureReport = false;
        } else {
          generatingDiscStructureReport = true;
          generateDiscStructureReportMenuItem.setText("Stop het disc structuur rapport genereren");
          generateDiscStructureReportStep1(discStructureSpecification);
        }
      }
    });
    menu.getItems().add(generateDiscStructureReportMenuItem);
    
    menuItem = componentFactory.createMenuItem("Print");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        System.out.println(treeView.toString());
      }
      
    });
    menu.getItems().add(menuItem);
    
    if (developmentMode) {
      
      menuItem = componentFactory.createMenuItem("Gebruik test disc structuur specificatie");
      menuItem.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
          createTestDiscStructureSpecification();
          treeView.setEObject(discStructureSpecification);
        }
      });
      menu.getItems().add(menuItem);
      
      menuItem = componentFactory.createMenuItem("Gebruik hard-coded disc structuur specificatie");
      menuItem.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
          createDiscStructureSpecification();
          treeView.setEObject(discStructureSpecification);
        }
      });
      menu.getItems().add(menuItem);
    }
    
    menuItem = componentFactory.createMenuItem("Exit");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        Platform.exit();
      }
    });
    menu.getItems().add(menuItem);
    
    menuBar.getMenus().add(menu);

    return menuBar;
  }
  
  private Node createHelpText() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("By describing how your disc structure is organized, this application can perform several checks");
    buf.append("<h3>Controlled folders</h3>");
    buf.append("For each directory you have to decide:");
    buf.append("<ul>");
    buf.append("<li>");
    buf.append("Do I want to protect this information from getting lost");
    buf.append("</li>");
    buf.append("<li>");
    buf.append("Do I want to share this information across my computers");
    buf.append("</li>");
    buf.append("</ul>");
        
    WebView helpTextArea = new WebView();
    helpTextArea.getEngine().loadContent(buf.toString());
    
    return helpTextArea;
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
        
//    DiscStructureSpecification discStructureSpecification = pcToolsFactory.createDiscStructureSpecification();
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
    directorySpecification.setDirectoryPath("C:\\EclipseWorkspace\\goedegep.pctools\\target\\test-classes\\Test Directory Structure\\directory not to be checked\\controlled directory");
    directorySpecification.setDescription("Controlled directory");
    directorySpecification.setSynchronizationSpecification("Yes this is marked as synchronized");
    discStructureSpecification.getDirectorySpecifications().add(directorySpecification);
    
    directorySpecification = pcToolsFactory.createDirectorySpecification();
    directorySpecification.setDirectoryPath("C:\\EclipseWorkspace\\goedegep.pctools\\target\\test-classes\\Test Directory Structure\\directory to be checked");
    directorySpecification.setDescription("Directory to be checked");
    directorySpecification.setToBeChecked(true);
    discStructureSpecification.getDirectorySpecifications().add(directorySpecification);

//    return discStructureSpecification;
  }
  
  private void newDiscStructureSpecification() {
    discStructureSpecification = emfResource.newEObject();
    treeView.setEObject(discStructureSpecification);
  }
  
  private void openDiscStructureSpecification() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Disc Structure Specification");
    if (PCToolsRegistry.dataDirectory != null) {
      File dataDirectory = new File(PCToolsRegistry.dataDirectory);
      fileChooser.setInitialDirectory(dataDirectory);
    }
    File file = fileChooser.showOpenDialog(this);
    LOGGER.severe("Opening: " + file.getAbsolutePath());
    
    try {
      discStructureSpecification = emfResource.load(file.getAbsolutePath());
      treeView.setEObject(discStructureSpecification);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
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
    LOGGER.info("=>");

    controlledSetBuildingTask = new ControlledSetBuildingTask(discStructureSpecification);
    WorkerStateMonitorWindow<Tuplet<FileInfoMap, List<FileCopyInfo>>> workerMonitor = new WorkerStateMonitorWindow<>(getCustomization(), controlledSetBuildingTask);
    workerMonitor.show();

    // show messages in the statusLabel
    controlledSetBuildingTask.messageProperty().addListener(new ChangeListener<String>() {

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        LOGGER.severe("Message: " + newValue);
        statusLabel.setText(newValue);
      }

    });

    // upon successful completion, call generateDiscStructureReportStep2()
    controlledSetBuildingTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

      @Override
      public void handle(WorkerStateEvent event) {
        LOGGER.severe("Succeeded: " + event);
        workerMonitor.hide();
        Tuplet<FileInfoMap, List<FileCopyInfo>> result = controlledSetBuildingTask.getValue();
        generateDiscStructureReportStep2(result.getObject1(), result.getObject2());
        statusLabel.setText("Step1 ready");
      }

    });
    
    // Report failure via statusLabel
    controlledSetBuildingTask.setOnFailed(new EventHandler<WorkerStateEvent>() {

      @Override
      public void handle(WorkerStateEvent event) {
        LOGGER.severe("Failed: " + event);
        statusLabel.setText("Genereren mislukt: " + event.toString());
      }

    });
    
    // Report cancelled via statusLabel
    controlledSetBuildingTask.setOnCancelled(new EventHandler<WorkerStateEvent>() {

      @Override
      public void handle(WorkerStateEvent event) {
        LOGGER.severe("Cancelled: " + event);
        statusLabel.setText("Genereren afgebroken: " + event.toString());
      }

    });

    // report any intermediate suspicious file copies found.
    controlledSetBuildingTask.valueProperty().addListener(new ChangeListener<Tuplet<FileInfoMap, List<FileCopyInfo>>>() {

      @Override
      public void changed(ObservableValue<? extends Tuplet<FileInfoMap, List<FileCopyInfo>>> observable,
          Tuplet<FileInfoMap, List<FileCopyInfo>> oldValue,
          Tuplet<FileInfoMap, List<FileCopyInfo>> newValue) {
        reportSuspiciousCopies(newValue.getObject2());
      }

    });

    Thread  buildControlledSetThread = new Thread(controlledSetBuildingTask);
    buildControlledSetThread.setDaemon(true);
    buildControlledSetThread.start();

    LOGGER.info("<=");
  }
  
  private void generateDiscStructureReportStep2(FileInfoMap controlledFiles, List<FileCopyInfo> suspiciousFileCopyInfos) {
    reportSuspiciousCopies(suspiciousFileCopyInfos);
    
    CheckFilesTask checkFilesTask = new CheckFilesTask(controlledFiles, discStructureSpecification);
    WorkerStateMonitorWindow<Tuplet<List<FileCopyInfo>, List<FileInfo>>> workerMonitor = new WorkerStateMonitorWindow<>(getCustomization(), checkFilesTask);
    workerMonitor.show();
    
    // show messages in the statusLabel
    checkFilesTask.messageProperty().addListener(new ChangeListener<String>() {

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        LOGGER.severe("Message: " + newValue);
        statusLabel.setText(newValue);
      }

    });

    // Show results when ready
    checkFilesTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

      @Override
      public void handle(WorkerStateEvent event) {
        LOGGER.severe("Succeeded: " + event);
        Tuplet<List<FileCopyInfo>, List<FileInfo>> result = checkFilesTask.getValue();
        reportprobablyRemovableFiles(result.getObject1());
        reportUncontrolledFiles(result.getObject2());
        workerMonitor.hide();
        statusLabel.setText("Report ready");
        generateDiscStructureReportMenuItem.setText("Genereer disc structuur rapport");
      }

    });
    
    checkFilesTask.setOnCancelled(new EventHandler<WorkerStateEvent>() {

      @Override
      public void handle(WorkerStateEvent event) {
        LOGGER.severe("Cancelled: " + event);
        statusLabel.setText("Genereren afgebroken: " + event.toString());
      }

    });

    // Show intermediate results
    checkFilesTask.valueProperty().addListener(new ChangeListener<Tuplet<List<FileCopyInfo>, List<FileInfo>>>() {

      @Override
      public void changed(ObservableValue<? extends Tuplet<List<FileCopyInfo>, List<FileInfo>>> observable,
          Tuplet<List<FileCopyInfo>, List<FileInfo>> oldValue,
          Tuplet<List<FileCopyInfo>, List<FileInfo>> newValue) {
        LOGGER.severe("Changed...");
        reportprobablyRemovableFiles(newValue.getObject1());
        reportUncontrolledFiles(newValue.getObject2());
        statusLabel.setText("Results so far: " + newValue.getObject1().size() + " (possible) copies found," + newValue.getObject2().size() + " uncontrolled files found");
      }

    });

    Thread  checkFilesThread = new Thread(checkFilesTask);
    checkFilesThread.setDaemon(true);
    checkFilesThread.start();

  }
  
  private void reportSuspiciousCopies(List<FileCopyInfo> suspiciousFileCopyInfos) {
    Collections.sort(suspiciousFileCopyInfos);
    
    TreeItem<String> rootTreeItem = new TreeItem<>("Suspicious copies");
    rootTreeItem.setExpanded(true);
    
    for (FileCopyInfo fileCopyInfo: suspiciousFileCopyInfos) {
      StringBuilder buf = new StringBuilder();
      buf.append(fileCopyInfo.getFirstFileFoundInfo().getFile().toString());
      buf.append(" ");
      buf.append(fileCopyInfo.getEqualityType().toString());
      TreeItem<String> fileTreeItem = new TreeItem<>(buf.toString());
      TreeItem<String> treeItem = new TreeItem<>(fileCopyInfo.getFirstFileFoundInfo().getDirectory().toString());
      fileTreeItem.getChildren().add(treeItem);
      treeItem = new TreeItem<>(fileCopyInfo.getSecondFileFoundInfo().getDirectory().toString());
      fileTreeItem.getChildren().add(treeItem);
      rootTreeItem.getChildren().add(fileTreeItem);
    }
    TreeView<String> treeView = new TreeView<> (rootTreeItem);
    
    tabLayoutCopy.setCenter(treeView);
  }

  private void reportprobablyRemovableFiles(List<FileCopyInfo> probablyRemovableFileCopyInfos) {
    TreeItem<String> rootTreeItem = new TreeItem<>("Probably removable files");
    rootTreeItem.setExpanded(true);
    
    for (FileCopyInfo fileCopyInfo: probablyRemovableFileCopyInfos) {
      StringBuilder buf = new StringBuilder();
      buf.append(fileCopyInfo.getFirstFileFoundInfo().getFile().toString());
      buf.append(" ");
      buf.append(fileCopyInfo.getEqualityType().toString());
      TreeItem<String> fileTreeItem = new TreeItem<>(buf.toString());

      TreeItem<String> treeItem = new TreeItem<>(fileCopyInfo.getFirstFileFoundInfo().getDirectory().toString());
      fileTreeItem.getChildren().add(treeItem);
      treeItem = new TreeItem<>(fileCopyInfo.getSecondFileFoundInfo().getDirectory().toString());
      fileTreeItem.getChildren().add(treeItem);
      rootTreeItem.getChildren().add(fileTreeItem);
    }
    TreeView<String> treeView = new TreeView<> (rootTreeItem);
    
    tabLayoutControlledCopy.setCenter(treeView);
  }

  private void reportUncontrolledFiles(List<FileInfo> uncontrolledFiles) {
    ListView<String> uncontrolledFilesListView = new ListView<String>();
    
//    TreeItem<String> rootTreeItem = new TreeItem<>("Uncontolled files");
//    rootTreeItem.setExpanded(true);
    
    for (FileInfo fileInfo: uncontrolledFiles) {
      uncontrolledFilesListView.getItems().add(fileInfo.getDirectory().toString() + "/" + fileInfo.getFile().toString());
    }
    
//    TreeView<String> treeView = new TreeView<> (rootTreeItem);
    
    tabLayoutUncontrolled.setCenter(uncontrolledFilesListView);
//    tabLayoutUncontrolled.setCenter(treeView);
  }

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
}
