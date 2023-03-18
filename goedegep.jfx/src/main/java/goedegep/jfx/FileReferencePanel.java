package goedegep.jfx;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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
 */
public class FileReferencePanel extends TitledPane {
  private static final Logger LOGGER = Logger.getLogger(FileReferencePanel.class.getName());
  private static String DEFAULT_TITLE = "New file reference";
  
  /**
   * The list of {@code FileReferencePanel}s in which this panel is contained.
   * <p>
   * This is used to let this panel be able to remove itself and for Drag and Drop (reordering items in the list).
   * 
   */
  private List<FileReferencePanel> fileReferencePanels;
  
  /**
   * Information on the types of references to handle.
   */
  private List<FileReferenceTypeInfo> fileReferenceTypeInfos;
  
  /**
   * The currently selected reference type
   */
  FileReferenceTypeInfo currentFileReferenceTypeInfo = null;
  
  /**
   * Indication of whether were handling a file or folder reference.
   */
  private Boolean handlingFileReference = null;
  
  private ComponentFactoryFx componentFactory;
  
  private ObjectControlGroup objectInputControlGroup;
  
  private static final DataFormat DOCUMENT_REFERENCE_PANEL = new DataFormat("DocumentReferencePanel");
  private FileReferencePanel thisDocumentReferencePanel;
  
  // The ObjectInputs
  private ComboBox<String> typeComboBox;
  private ObjectControlFileSelecter fileSelecter = null;
  private ObjectControlFolderSelecter folderSelecter = null;
  private ObjectControlString titleTextField;

  private GridPane gridPane;
  private Desktop  desktop = null;
  
  public FileReferencePanel(CustomizationFx customization, List<FileReferencePanel> documentReferencePanels, boolean expand) {
    this(customization, documentReferencePanels, expand, null);
  }

  public FileReferencePanel(CustomizationFx customization, List<FileReferencePanel> fileReferencePanels, boolean expand, List<FileReferenceTypeInfo> fileReferenceTypeInfos) {
    if (fileReferenceTypeInfos != null  &&  fileReferenceTypeInfos.isEmpty()) {
      throw new IllegalArgumentException("If fileReferenceTypeInfos is not null, it may not be empty");
    }
    
    this.fileReferencePanels = fileReferencePanels;
    this.fileReferenceTypeInfos = fileReferenceTypeInfos;
    componentFactory = customization.getComponentFactoryFx();
    
    // Create the controls
    if (fileReferenceTypeInfos != null) {
      ObservableList<String> items = FXCollections.observableArrayList();
      for (FileReferenceTypeInfo fileReferenceTypeInfo: fileReferenceTypeInfos) {
        items.add(fileReferenceTypeInfo.getDisplayName());
      }
      typeComboBox = new ComboBox<>(items);
      typeComboBox.setOnAction((e) -> handleNewReferenceTypeSelected());
    }
    
    titleTextField = componentFactory.createObjectControlString(null, 200, true, "a title for the file");
    titleTextField.setId("title");
    titleTextField.objectValue().addListener((observable, oldValue, newValue) -> updatePaneTitle());
    
    createObjectInputControlGroup();

    createGUI();
    
    objectInputControlGroup.isValid().addListener((observable, oldValue, newValue) -> updatePaneTitle());
        
    thisDocumentReferencePanel = this;
    
    /*
     * Handle the starting of a drag event.
     * This can be dragged.
     */
    setOnDragDetected(new EventHandler<MouseEvent>() {

      public void handle(MouseEvent event) {
        ClipboardContent clipboardContent = new ClipboardContent();
        Integer myIndex = fileReferencePanels.indexOf(thisDocumentReferencePanel);
        clipboardContent.put(DOCUMENT_REFERENCE_PANEL, myIndex);

        Dragboard dragBoard = startDragAndDrop(TransferMode.MOVE);
        dragBoard.setContent(clipboardContent);

        event.consume();
      }
    });
    
    /*
     * Check whether a drop event can be handled (upon a drag over).
     * Drop is supported for DOCUMENT_REFERENCE_PANEL.
     */
    setOnDragOver(new EventHandler<DragEvent>() {
      public void handle(DragEvent event) {
        /* data is dragged over a (possible) target */
        /* accept it only if it is not dragged from the same node 
         * and if it has a supported data format. */
        Dragboard dragboard = event.getDragboard();
        if (event.getGestureSource() != thisDocumentReferencePanel) {
          if (dragboard.hasContent(DOCUMENT_REFERENCE_PANEL)) {
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
        if (dragboard.hasContent(DOCUMENT_REFERENCE_PANEL)) {
          Integer sourceIndex = (Integer) dragboard.getContent(DOCUMENT_REFERENCE_PANEL);
          if (sourceIndex != null) {
            FileReferencePanel sourcePanel = fileReferencePanels.get(sourceIndex);
            int sourceIndexInt = sourceIndex;
            fileReferencePanels.remove(sourceIndexInt);
            
            Integer myIndex = fileReferencePanels.indexOf(thisDocumentReferencePanel);
            fileReferencePanels.add(myIndex, sourcePanel);
          }

          /* let the source know whether the item was successfully transferred and used */
          event.setDropCompleted(success);
        }

        event.consume();
      }
    });
    
    setExpanded(expand);
    
    if (typeComboBox != null) {
      typeComboBox.getSelectionModel().select(0);
    }
    
    handleNewReferenceTypeSelected();
//    updatePaneTitle();
//    updateGUI();
  }

  public FileReferenceTypeInfo getReferenceType() {
    return currentFileReferenceTypeInfo;
  }
  
  /**
   * Get the fileSelecter.
   * 
   * @return the fileSelecter.
   */
  public ObjectControlFileSelecter getFileSelecter() {
    if (fileSelecter == null) {
      fileSelecter = componentFactory.createFileSelecter(null, 400, "Currently selected file",
          "Choose file", "Select a file via a file chooser", "Select the file");
      fileSelecter.setId("fileSelecter");
      fileSelecter.objectValue().addListener((observable, oldValue, newValue) -> updatePaneTitle());
    }
    
    return fileSelecter;
  }
  
  /**
   * Get the folderSelecter.
   * 
   * @return the folderSelecter.
   */
  public ObjectControlFolderSelecter getFolderSelecter() {
    if (folderSelecter == null) {
      folderSelecter = componentFactory.createFolderSelecter(null, 400, "Currently selected folder",
          "Choose folder", "Select a folder via a folder chooser", "Select the folder");
      folderSelecter.setId("folderSelecter");
      folderSelecter.objectValue().addListener((observable, oldValue, newValue) -> updatePaneTitle());
    }
    
    return folderSelecter;
  }
  
  /**
   * Get the titleTextField.
   * 
   * @return the titleTextField.
   */
  public ObjectControlString titleTextField() {
    return titleTextField;
  }
  
  public void setFile(String file) {
    getFileSelecter().setObjectValue(file);
  }
  
  public String getFile() {
    if (handlingFileReference) {
      if (getFileSelecter().getIsValid(null)) {
        return getFileSelecter().getObjectValue();
      } else {
        return null;
      }
    } else {
      if (getFolderSelecter().getIsValid(null)) {
        return getFolderSelecter().getObjectValue();
      } else {
        return null;
      }
    }
  }
  
//  private String getPropertyFilesFolder() {
//    String propertyFilesFolder = null;
//    
//    for (FileReferencePanel documentReferencePanel: documentReferencePanels) {
//      File file = new File(documentReferencePanel.fileSelecter.getFilePathTextField().getText());
//      propertyFilesFolder = file.getParent();
//      if (propertyFilesFolder != null) {
//        return propertyFilesFolder;
//      }
//    }
//    
//    return null;
//  }

  public ObjectControlGroup getObjectInputContainer() {
    return objectInputControlGroup;
  }

  /**
   * Create the <code>objectInputControlGroup</code> and add the controls to it.
   */
  private void createObjectInputControlGroup() {
    objectInputControlGroup = new ObjectControlGroup();
    
    objectInputControlGroup.addObjectControl(getFileSelecter());
    objectInputControlGroup.addObjectControl(titleTextField);
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
  
  private void handleNewReferenceTypeSelected() {
    boolean isFile = true;  // File is default
    
    if (typeComboBox != null) {
      String typeString = typeComboBox.getSelectionModel().getSelectedItem();
      currentFileReferenceTypeInfo = null;
      for (FileReferenceTypeInfo fileReferenceTypeInfo: fileReferenceTypeInfos) {
        if (fileReferenceTypeInfo.getDisplayName().equals(typeString)) {
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
    
    if (typeChanged) {
      updateGUI();
      updatePaneTitle();
    }
  }
  
  private void updateGUI() {    
    gridPane.getChildren().clear();
    
    int row = 0;
    
    if (typeComboBox != null) {
      // Row 0: type selection ComboBox
      Label typeLabel = componentFactory.createLabel("Reference type:");
      gridPane.add(typeLabel, 0, row);

      gridPane.add(typeComboBox, 1, row);

      row++;
    }

    if (handlingFileReference) {
      // Row 1: file selection; label, textfield and button to start file chooser.
      Label fileNameLabel = componentFactory.createLabel("File:");
      gridPane.add(fileNameLabel, 0, row);
          
      gridPane.add(getFileSelecter().getPathTextField(), 1, row);
      
      Button fileChooserButton = getFileSelecter().getFileChooserButton();
      gridPane.add(fileChooserButton, 2, row);
    } else {      
      // Row 1: folder selection; label, textfield and button to start folder chooser.
      Label fileNameLabel = componentFactory.createLabel("Folder:");
      gridPane.add(fileNameLabel, 0, row);
          
      gridPane.add(getFolderSelecter().getPathTextField(), 1, row);
      
      Button fileChooserButton = getFolderSelecter().getFolderChooserButton();
      gridPane.add(fileChooserButton, 2, row);
    }
    
    row++;
    
    // Row 2: title; label, textfield
    Label titleLabel = componentFactory.createLabel("Title:");
    gridPane.add(titleLabel, 0, row);

    gridPane.add(titleTextField, 1, row);     
  }

  private Node createButtonsBox() {
    HBox buttonsBox = componentFactory.createHBox(12.0, 12.0);
    
    Button openItemButton = componentFactory.createButton("Open attachment", "Open this attachment with the related application");
    openItemButton.setOnAction(e -> openItem());
    buttonsBox.getChildren().add(openItemButton);
    
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    buttonsBox.getChildren().add(spacer);
    
    Button deleteButton = componentFactory.createButton("Delete this attachment", "Delete this attachment");
    deleteButton.setOnAction(e -> deleteThisFileReference());
    buttonsBox.getChildren().add(deleteButton);
    
    return  buttonsBox;
  }
  
  /**
   * Remove this reference (panel) from the {@code fileReferencePanels}.
   */
  private void deleteThisFileReference() {
    fileReferencePanels.remove(this);
  }
  
  private void openItem() {
    Desktop desktop = getDesktop();
    if ((desktop == null)  ||  !desktop.isSupported(Desktop.Action.OPEN)) {
      componentFactory.createErrorDialog("Unable to open a item", "Opening items isn't supported on this platform").showAndWait();
      return;
    }
    
    String fileOrFolderPath = null;
    if (handlingFileReference) {
      fileOrFolderPath = getFileSelecter().getObjectValue();
    } else {
      fileOrFolderPath = getFolderSelecter().getObjectValue();
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
   * Update the 'title' (actually the text) of this TitledPane.
   * <p>
   * This title is the value of the {@code titleTextField} if this is not null or empty.<br/>
   * Else it is the file/folder name of the file/folder selected by the file/folder chooser.<br/>
   * If this is also not set, the {@code DEFAULT_TITLE} is used.<br/>
   * 
   * To this a valid/invalid indication is added, based on the status of the {@code objectInputControlGroup}.
   */
  private void updatePaneTitle() {
    StringBuilder buf = new  StringBuilder();
    
    String string = titleTextField.getObjectValue();
    if ((string == null)  ||  string.isEmpty()) {

      if (getFileSelecter().getObjectValue() != null) {
        File file = new File(getFileSelecter().getObjectValue());
        string = file.getName();
      } else {
        string = DEFAULT_TITLE;
      }
    }
    
    buf.append(string).append(" ");
        
    // Add (in)valid indication
    if (objectInputControlGroup.isValid().getValue()) {
      buf.append(EObjectEditor.OK_INDICATOR);
    } else {
      buf.append(EObjectEditor.NOK_INDICATOR);
    }
    
    setText(buf.toString());
  }
}
