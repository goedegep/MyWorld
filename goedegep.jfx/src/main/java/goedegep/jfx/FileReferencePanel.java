package goedegep.jfx;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;

import goedegep.jfx.eobjecteditor.EObjectEditor;
import goedegep.jfx.objectcontrols.ObjectControlFileSelecter;
import goedegep.jfx.objectcontrols.ObjectControlFolderSelecter;
import goedegep.jfx.objectcontrols.ObjectControlGroup;
import goedegep.jfx.objectcontrols.ObjectControlString;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * This class provides a panel to edit a {@link FileReference}.
 * <p>
 * By default the panel handles a reference to a file,
 * but by providing a list of {@code FileReferenceTypeInfo} references to folders are also supported.
 * In the latter case, there's an extra control via which the user can select the reference type.
 */
public class FileReferencePanel extends TitledPane {
  private static final Logger LOGGER = Logger.getLogger(FileReferencePanel.class.getName());
  
  /**
   * Clipboard content type used for drag and drop.
   */
  private static final DataFormat FILE_REFERENCE_PANEL = new DataFormat("FileReferencePanel");
  
  /**
   * The list of {@code FileReferencePanel}s to which a newly created panel has to add itself.
   * <p>
   * This list is used for reordering the panels via drag and drop.
   */
  private List<FileReferencePanel> fileReferencePanels = null;
  
  /**
   * Default panel title (used if nothing is filled in yet)
   */
  private String defaultPanelTitle = null;
  
  /**
   * Expand pane on creation
   */
  private boolean expandPanelOnCreation;
  
  /**
   * Supplier for the initial folder in the file or folder selecter.
   */
  private Supplier<String> initialFolderSupplier = null;
  
  /**
   * Information on the types of references to handle.
   */
  private List<FileReferenceTypeInfo> fileReferenceTypeInfos = null;
  
  /**
   * The currently selected reference type
   */
  FileReferenceTypeInfo currentFileReferenceTypeInfo = null;
  
  /**
   * Indication of whether we're handling a file or folder reference.
   */
  private Boolean handlingFileReference = null;
  
  /**
   * The GUI customization
   */
  private CustomizationFx customization;
  
  /**
   * Factory for creating the GUI components
   */
  private ComponentFactoryFx componentFactory;
  
  /**
   * {@code ObjectControlGroup} to which the object controls are added.
   */
  private ObjectControlGroup objectControlsGroup;
  
  /**
   * Reference to {@code this}. Used in lambdas where 'this' is not available.
   */
  private FileReferencePanel thisFileReferencePanel;
  
  // The ObjectControls
  /**
   * Object control for the reference type.
   */
  private ComboBox<String> referenceTypeComboBox;
  
  /**
   * Object control used if the reference is a file reference.
   */
  private ObjectControlFileSelecter fileSelecterObjectControl = null;
  
  /**
   * Object control used if the reference is a folder reference.
   */
  private ObjectControlFolderSelecter folderSelecterObjectControl = null;
  
  /**
   * Object control for the reference title.
   */
  private ObjectControlString titleObjectControl;

  /**
   * The pane containing the controls.
   */
  private GridPane gridPane;
  
  /**
   * Reference to the {@code DeskTop}, used to open a file.
   */
  private Desktop  desktop = null;
  
  /**
   * Constructor using builder
   * <p>
   * @param builder A builder with all configuration information.
   */
  public FileReferencePanel(FileReferencePanelBuilder builder) {
    this.customization = builder.customization;
    this.fileReferencePanels = builder.fileReferencePanels;
    this.defaultPanelTitle = builder.defaultPaneTitle;
    this.expandPanelOnCreation = builder.expandPaneOnCreation;
    this.initialFolderSupplier = builder.initialFolderSupplier;
    this.fileReferenceTypeInfos = builder.fileReferenceTypeInfos;
    
    componentFactory = customization.getComponentFactoryFx();
    
    createControls();

    createGUI();
    
    if (referenceTypeComboBox != null) {
      referenceTypeComboBox.setOnAction((e) -> handleNewReferenceTypeSelected());
    }
    
    installChangeListeners();
    
    installDragAndDropHandling();
        
    thisFileReferencePanel = this;
    
    setExpanded(expandPanelOnCreation);
    
    if (referenceTypeComboBox != null) {
      referenceTypeComboBox.getSelectionModel().select(0);
    }
    
    handleNewReferenceTypeSelected();
  }
  
  /**
   * Get the selected reference type
   * @return the selected reference type
   */
  public FileReferenceTypeInfo getReferenceType() {
    return currentFileReferenceTypeInfo;
  }
  
  /**
   * Get the file selecter object control.
   * <p>
   * The control will be created if this hasn't been done yet.
   * 
   * @return the file selecter object control.
   */
  public ObjectControlFileSelecter getFileSelecterObjectControl() {
    if (fileSelecterObjectControl == null) {
      fileSelecterObjectControl = componentFactory.createFileSelecterObjectControl(400, "Currently selected file",
          "Choose file", "Select a file via a file chooser", "Select the file", false);
      fileSelecterObjectControl.setId("fileSelecter");
      fileSelecterObjectControl.setInitialFolderProvider(initialFolderSupplier);
      fileSelecterObjectControl.addListener((observable) -> updatePaneTitle());
    }
    
    return fileSelecterObjectControl;
  }
  
  /**
   * Get the folderSelecter object control.
   * <p>
   * The control will be created if this hasn't been done yet.
   * 
   * @return the folderSelecter object control.
   */
  public ObjectControlFolderSelecter getFolderSelecterObjectControl() {
    if (folderSelecterObjectControl == null) {
      folderSelecterObjectControl = componentFactory.createFolderSelecter(400, "Currently selected folder",
          "Choose folder", "Select a folder via a folder chooser", "Select the folder", false);
      folderSelecterObjectControl.setId("folderSelecter");
      folderSelecterObjectControl.setInitialFolderProvider(initialFolderSupplier);
      folderSelecterObjectControl.addListener((observable) -> updatePaneTitle());
    }
    
    return folderSelecterObjectControl;
  }
  
  /**
   * Get the title object control.
   * 
   * @return the title object control.
   */
  public ObjectControlString getTitleObjectControl() {
    return titleObjectControl;
  }
  
  /**
   * Set the reference type
   */
  public void setReferenceType(String tag) {
    referenceTypeComboBox.setValue(tag);
  }
  
  /**
   * Set the selected file.
   * 
   * @param file the selected file.
   */
  public void setPathNameRelativeToPrefix(String file) {
    getFileSelecterObjectControl().setPathNameRelativeToPrefix(file);
  }
  
  /**
   * Get the selected file or folder path name, relative to the prefix.
   * 
   * @return the selected file or folder, if this has a valid value, null otherwise.
   */
  public String getPathNameRelativeToPrefix() {
    if (handlingFileReference) {
      if (getFileSelecterObjectControl().isValid()) {
        return getFileSelecterObjectControl().getPathNameRelativeToPrefix();
      } else {
        return null;
      }
    } else {
      if (getFolderSelecterObjectControl().isValid()) {
        return getFolderSelecterObjectControl().getPathNameRelativeToPrefix();
      } else {
        return null;
      }
    }
  }
  
  public File getFile() {
    if (handlingFileReference) {
      if (getFileSelecterObjectControl().isValid()) {
        return getFileSelecterObjectControl().getValue();
      } else {
        return null;
      }
    } else {
      if (getFolderSelecterObjectControl().isValid()) {
        return getFolderSelecterObjectControl().getValue();
      } else {
        return null;
      }
    }
  }
  
  /**
   * Get the {@code ObjectControlGroup} for the object controls of this panel.
   * 
   * @return the {@code ObjectControlGroup} for the object controls of this panel.
   */
  public ObjectControlGroup getObjectControlsGroup() {
    return objectControlsGroup;
  }
  
  /**
   * Create the controls and controls group.
   */
  private void createControls() {
    if (fileReferenceTypeInfos != null) {
      ObservableList<String> items = FXCollections.observableArrayList();
      for (FileReferenceTypeInfo fileReferenceTypeInfo: fileReferenceTypeInfos) {
        items.add(fileReferenceTypeInfo.displayName());
      }
      referenceTypeComboBox = new ComboBox<>(items);
    }
    
    titleObjectControl = componentFactory.createObjectControlString(null, 200, true, "a title for the file");
    titleObjectControl.setId("title");
    
    objectControlsGroup = new ObjectControlGroup();
    
    objectControlsGroup.addObjectControls(getFileSelecterObjectControl(), titleObjectControl);
  }
  
  /**
   * Create the GUI
   */
  private void createGUI() {
    VBox rootPane = componentFactory.createVBox();

    gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    
    rootPane.getChildren().addAll(gridPane, createButtonsBox());
    
    setContent(rootPane);
  }

  /**
   * Create the box with the action buttons: 'Open item' and 'Delete this item'.
   * 
   * @return the created box.
   */
  private Node createButtonsBox() {
    HBox buttonsBox = componentFactory.createHBox(12.0, 12.0);
    
    Button openItemButton = componentFactory.createButton("Open item", "Open this attachment with the related application");
    openItemButton.setOnAction(e -> openItem());
    buttonsBox.getChildren().add(openItemButton);
    
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    buttonsBox.getChildren().add(spacer);
    
    Button deleteButton = componentFactory.createButton("Delete this reference", "Delete this reference");
    deleteButton.setOnAction(e -> deleteThisFileReference());
    buttonsBox.getChildren().add(deleteButton);
    
    return  buttonsBox;
  }
  
  /**
   * Install change listeners.
   * <p>
   * The title of this panel is built up from a number of control values. So for these controls an invalidation listener is installed and
   * upon a change {@code updateTitle()} is called.<br/>
   * Listeners are installed on: fileSelecterObjectControl, titleObjectControl, objectControlsGroup.isValid.
   */
  private void installChangeListeners() {
    objectControlsGroup.isValid().addListener((observable, oldValue, newValue) -> updatePaneTitle());
    titleObjectControl.addListener((observable) -> updatePaneTitle());
  }
  
  /**
   * Install drag and drop handling.
   * <p>
   * The file references on our {@code fileReferencePanels} can be reordered via drag and drop.
   */
  private void installDragAndDropHandling() {
    
    /*
     * Handle the starting of a drag event.
     * This can be dragged.
     */
    setOnDragDetected(new EventHandler<MouseEvent>() {

      public void handle(MouseEvent event) {
        ClipboardContent clipboardContent = new ClipboardContent();
        Integer myIndex = fileReferencePanels.indexOf(thisFileReferencePanel);
        clipboardContent.put(FILE_REFERENCE_PANEL, myIndex);

        Dragboard dragBoard = startDragAndDrop(TransferMode.MOVE);
        dragBoard.setContent(clipboardContent);

        event.consume();
      }
    });
    
    /*
     * Check whether a drop event can be handled (upon a drag over).
     * Drop is supported for FILE_REFERENCE_PANEL.
     */
    setOnDragOver(new EventHandler<DragEvent>() {
      public void handle(DragEvent event) {
        /* data is dragged over a (possible) target */
        /* accept it only if it is not dragged from the same node 
         * and if it has a supported data format. */
        Dragboard dragboard = event.getDragboard();
        if (event.getGestureSource() != thisFileReferencePanel) {
          if (dragboard.hasContent(FILE_REFERENCE_PANEL)) {
            event.acceptTransferModes(TransferMode.MOVE);
          }
        }

        event.consume();
      }
    });
    
    /*
     * Handle the dropping on the target
     */
    setOnDragDropped(new EventHandler<DragEvent>() {
      public void handle(DragEvent event) {
        LOGGER.info("=>");

        boolean success = false;

        // Get the index from the drag board, and if it isn't null use it.
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasContent(FILE_REFERENCE_PANEL)) {
          Integer sourceIndex = (Integer) dragboard.getContent(FILE_REFERENCE_PANEL);
          if (sourceIndex != null) {
            FileReferencePanel sourcePanel = fileReferencePanels.get(sourceIndex);
            int sourceIndexInt = sourceIndex;
            fileReferencePanels.remove(sourceIndexInt);
            
            Integer myIndex = fileReferencePanels.indexOf(thisFileReferencePanel);
            fileReferencePanels.add(myIndex, sourcePanel);
          }

          /* let the source know whether the item was successfully transferred and used */
          event.setDropCompleted(success);
        }

        event.consume();
      }
    });
    
  }
  
  /**
   * Handle the fact that a new reference type is selected.
   */
  private void handleNewReferenceTypeSelected() {
    boolean isFile = true;  // File is default
    
    if (referenceTypeComboBox != null) {
      String typeString = referenceTypeComboBox.getSelectionModel().getSelectedItem();
      currentFileReferenceTypeInfo = null;
      for (FileReferenceTypeInfo fileReferenceTypeInfo: fileReferenceTypeInfos) {
        if (fileReferenceTypeInfo.displayName().equals(typeString)) {
          currentFileReferenceTypeInfo = fileReferenceTypeInfo;
          isFile = !fileReferenceTypeInfo.isFolder();
          break;
        }
      }
    }
    
    boolean typeChanged = false;
    if (handlingFileReference == null  ||  handlingFileReference != isFile) {
      typeChanged = true;
    }
    
    handlingFileReference = isFile;
    
    String prefix = null;
    if (currentFileReferenceTypeInfo != null) {
      prefix = currentFileReferenceTypeInfo.filePathPrefix();
    }
    if (isFile) {
      getFileSelecterObjectControl().setPrefix(prefix);
    } else {
      getFolderSelecterObjectControl().setPrefix(prefix);
    }
    
    if (typeChanged) {
      updateGUI();
      updatePaneTitle();
    }
  }
  
  /**
   * Update the GUI (to handle a new reference type).
   */
  private void updateGUI() {    
    gridPane.getChildren().clear();
    
    int row = 0;
    
    if (referenceTypeComboBox != null) {
      // Row 0: type selection ComboBox
      Label typeLabel = componentFactory.createLabel("Reference type:");
      gridPane.add(typeLabel, 0, row);

      gridPane.add(referenceTypeComboBox, 1, row);

      row++;
    }

    if (handlingFileReference) {
      // Row 1: file selection; label, textfield and button to start file chooser.
      Label fileNameLabel = componentFactory.createLabel("File:");
      gridPane.add(fileNameLabel, 0, row);
          
      gridPane.add(getFileSelecterObjectControl().getControl(), 1, row);
      
      Button fileChooserButton = getFileSelecterObjectControl().getFileChooserButton();
      gridPane.add(fileChooserButton, 2, row);
    } else {      
      // Row 1: folder selection; label, textfield and button to start folder chooser.
      Label fileNameLabel = componentFactory.createLabel("Folder:");
      gridPane.add(fileNameLabel, 0, row);
          
      gridPane.add(getFolderSelecterObjectControl().getControl(), 1, row);
      
      Button fileChooserButton = getFolderSelecterObjectControl().getFolderChooserButton();
      gridPane.add(fileChooserButton, 2, row);
    }
    
    row++;
    
    // Row 2: title; label, textfield
    Label titleLabel = componentFactory.createLabel("Title:");
    gridPane.add(titleLabel, 0, row);

    gridPane.add(titleObjectControl.getControl(), 1, row);     
  }
  
  /**
   * Remove this reference (panel) from the {@code fileReferencePanels}.
   */
  private void deleteThisFileReference() {
    fileReferencePanels.remove(this);
  }
  
  /**
   * Open the referred file or folder.
   */
  private void openItem() {
    Desktop desktop = getDesktop();
    if ((desktop == null)  ||  !desktop.isSupported(Desktop.Action.OPEN)) {
      componentFactory.createErrorDialog("Unable to open a item", "Opening items isn't supported on this platform").showAndWait();
      return;
    }
    
    String fileOrFolderPath = null;
    if (handlingFileReference) {
      fileOrFolderPath = getFileSelecterObjectControl().getAbsolutePath();
    } else {
      fileOrFolderPath = getFolderSelecterObjectControl().getAbsolutePath();
    }
    
    try {
      URI uri = new URI(fileOrFolderPath);
      try {
        desktop.browse(uri);
      } catch (IOException e) {
        componentFactory.createErrorDialog("Unable to open URL", e.getMessage());
      }
    } catch (URISyntaxException e1) {
      File file = new File(fileOrFolderPath);
      
      try {
        desktop.open(file);
      } catch (IllegalArgumentException | IOException e) {
        componentFactory.createErrorDialog("An error occurred on trying to open the file", e.getMessage()).showAndWait();
      }
    }
  }
  
  private Desktop getDesktop() {
    if (desktop == null) {
      // Before more Desktop API is used, first check 
      // whether the API is supported by this particular 
      // virtual machine (VM) on this particular host.
      if (Desktop.isDesktopSupported()) {
          desktop = Desktop.getDesktop();          
      }
      
    }
    
    return desktop;
  }
  
  /**
   * Update the 'title' of this TitledPane.
   * <p>
   * This title is the value of the {@code titleObjectControl} if this is not null or empty.<br/>
   * Else it is the file/folder name of the file/folder selected by the file/folder chooser.<br/>
   * If this is also not set, the {@code title} is used.<br/>
   * 
   * To this a valid/invalid indication is added, based on the status of the {@code objectControlsGroup}.
   */
  private void updatePaneTitle() {
    StringBuilder buf = new  StringBuilder();
    
    String string = titleObjectControl.getValue();
    if ((string == null)  ||  string.isEmpty()) {

      if (getFileSelecterObjectControl().getValue() != null) {
        string = getFileSelecterObjectControl().getValue().getName();
      } else {
        string = defaultPanelTitle;
      }
    }
    
    buf.append(string).append(" ");
        
    // Add (in)valid indication
    if (objectControlsGroup.isValid().getValue()) {
      buf.append(EObjectEditor.OK_INDICATOR);
    } else {
      buf.append(EObjectEditor.NOK_INDICATOR);
    }
    
    setText(buf.toString());
  }
  
  /**
   * Builder class for creating a FileReferencePanel.
   */
  public static class FileReferencePanelBuilder {
    /*
     * Required parameters
     */
    
    /**
     * GUI customization
     */
    private CustomizationFx customization;
    
    /**
     * The list of {@code FileReferencePanel}s to which a newly created panel has to add itself.
     * <p>
     * This list is used for reordering the panels via drag and drop.
     */
    private List<FileReferencePanel> fileReferencePanels;
    
    
    /*
     * Optional parameters
     */
    
    /**
     * Default pane title (used if nothing is filled in yet)
     */
    private String defaultPaneTitle = null;
    
    /**
     * Expand pane on creation
     */
    private boolean expandPaneOnCreation;
    
    /**
     * Supplier for the initial folder in the file or folder selecter.
     */
    private Supplier<String> initialFolderSupplier;
    
    /**
     * Information on the types of references to handle.
     */
    private List<FileReferenceTypeInfo> fileReferenceTypeInfos = null;
    
    
    /**
     * Constructor providing required parameters.
     * 
     * @param customization the GUI customization.
     */
    public FileReferencePanelBuilder(CustomizationFx customization, List<FileReferencePanel> fileReferencePanels) {
      this.customization = customization;
      this.fileReferencePanels = fileReferencePanels;
    }
    
    /**
     * Set the default pane title.
     * 
     * @param defaultPaneTitle the default pane title.
     * @return this.
     */
    public FileReferencePanelBuilder setDefaultPaneTitle(String defaultPaneTitle) {
      this.defaultPaneTitle = defaultPaneTitle;
      return this;
    }
    
    /**
     * Set whether the pane shall be expanded upon creation or not.
     * 
     * @param expandPaneOnCreation if true, the pane will be expanded on creation.
     * @return this.
     */
    public FileReferencePanelBuilder setExpandPaneOnCreation(boolean expandPaneOnCreation) {
      this.expandPaneOnCreation = expandPaneOnCreation;
      return this;
    }
    
    /**
     * Set the initial folder supplier.
     */
    public FileReferencePanelBuilder setInitialFolderSupplier(Supplier<String> initialFolderSupplier) {
      this.initialFolderSupplier = initialFolderSupplier;
      return this;
    }
    
    public FileReferencePanelBuilder addFileReferenceTypes(FileReferenceTypeInfo... fileReferenceTypeInfo) {
      if (fileReferenceTypeInfos == null) {
        fileReferenceTypeInfos = new ArrayList<>();
      }
      
      for (FileReferenceTypeInfo aFileReferenceTypeInfo: fileReferenceTypeInfo) {
        fileReferenceTypeInfos.add(aFileReferenceTypeInfo);
      }
      
      return this;
    }
    
    /**
     * Construct the panel.
     * 
     * @return the created panel.
     */
    public FileReferencePanel build() {
      return new FileReferencePanel(this);
    }
  }
}

//class FileReferenceTypeDescriptor implements FileReferenceTypeInfo {
//  
//  private String tag;
//  private String displayName;
//  private boolean isFolder;
//  private String prefix;
//
//  FileReferenceTypeDescriptor(String tag, String displayName, boolean isFolder, String prefix) {
//    this.tag = tag;
//    this.displayName = displayName;
//    this.isFolder = isFolder;
//    this.prefix = prefix;
//  }
//  
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public String getTag() {
//    return tag;
//  }
//  
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public String getDisplayName() {
//    return displayName;
//  }
//  
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public boolean isFolder() {
//    return isFolder;
//  }
//
//  @Override
//  public String getPrefix() {
//    return prefix;
//  }
//}
