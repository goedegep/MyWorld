package goedegep.jfx;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.controls.FileSelecter;
import goedegep.jfx.controls.ObjectControlGroup;
import goedegep.jfx.controls.ObjectControlString;
import goedegep.jfx.eobjecteditor.EObjectEditor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
  
  private List<FileReferencePanel> documentReferencePanels;
  private ComponentFactoryFx componentFactory;
  
  private ObjectControlGroup objectInputControlGroup;
  
  private static final DataFormat DOCUMENT_REFERENCE_PANEL = new DataFormat("DocumentReferencePanel");
  private FileReferencePanel thisDocumentReferencePanel;
  
  // The ObjectInputs
  private FileSelecter fileSelecter;
  private ObjectControlString titleTextField;

  private Desktop  desktop = null;
  

  public FileReferencePanel(CustomizationFx customization, List<FileReferencePanel> documentReferencePanels, boolean expand) {
    this.documentReferencePanels = documentReferencePanels;
    componentFactory = customization.getComponentFactoryFx();
    
    // Create the controls
    fileSelecter = componentFactory.createFileSelecter(null, 400, "Currently selected folder",
        "Choose file", "Select a file via a file chooser", "Select the file");
    fileSelecter.setId("fileSelecter");
    fileSelecter.objectValue().addListener((observable, oldValue, newValue) -> updateTitle());
    
    titleTextField = componentFactory.createObjectInputString(null, 200, true, "a title for the file", "title");
    titleTextField.objectValue().addListener((observable, oldValue, newValue) -> updateTitle());
    
    createObjectInputControlGroup();

    createGUI();
    
    objectInputControlGroup.isValid().addListener((observable, oldValue, newValue) -> updateTitle());
        
    thisDocumentReferencePanel = this;
    
    /*
     * Handle the starting of a drag event.
     * This can be dragged.
     */
    setOnDragDetected(new EventHandler<MouseEvent>() {

      public void handle(MouseEvent event) {
        ClipboardContent clipboardContent = new ClipboardContent();
        Integer myIndex = documentReferencePanels.indexOf(thisDocumentReferencePanel);
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
            FileReferencePanel sourcePanel = documentReferencePanels.get(sourceIndex);
            int sourceIndexInt = sourceIndex;
            documentReferencePanels.remove(sourceIndexInt);
            
            Integer myIndex = documentReferencePanels.indexOf(thisDocumentReferencePanel);
            documentReferencePanels.add(myIndex, sourcePanel);
          }

          /* let the source know whether the item was successfully transferred and used */
          event.setDropCompleted(success);
        }

        event.consume();
      }
    });
    
    setExpanded(expand);
    
    updateTitle();
  }
  
  /**
   * Get the fileSelecter.
   * 
   * @return the fileSelecter.
   */
  public FileSelecter getFileSelecter() {
    return fileSelecter;
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
    fileSelecter.setObjectValue(file);
  }
  
  public String getFile() {
    if (fileSelecter.getIsValid(null)) {
      return fileSelecter.getObjectValue();
    } else {
      return null;
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
    
    objectInputControlGroup.addObjectControl(fileSelecter);
    objectInputControlGroup.addObjectControl(titleTextField);
  }
  
  /**
   * Create the GUI
   */
  private void createGUI() {
    VBox rootPane = componentFactory.createVBox();

    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    
    // Row 0: file selection; label, textfield and button to start file chooser.
    Label fileNameLabel = componentFactory.createLabel("File:");
    gridPane.add(fileNameLabel, 0, 0);
        
    gridPane.add(fileSelecter.getPathTextField(), 1, 0);
    
    Button fileChooserButton = fileSelecter.getFileChooserButton();
    gridPane.add(fileChooserButton, 2, 0);
        
    // Row 1: title; label, textfield
    Label titleLabel = componentFactory.createLabel("Title:");
    gridPane.add(titleLabel, 0, 1);
    
    gridPane.add(titleTextField, 1, 1);
    
    rootPane.getChildren().addAll(gridPane, createButtonsBox());
    
    setContent(rootPane);
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
    deleteButton.setOnAction(e -> deleteInvoiceItem());
    buttonsBox.getChildren().add(deleteButton);
    
    return  buttonsBox;
  }
  
  private void deleteInvoiceItem() {
    documentReferencePanels.remove(this);
  }
  
  private void openItem() {
    Desktop desktop = getDesktop();
    if ((desktop == null)  ||  !desktop.isSupported(Desktop.Action.OPEN)) {
      componentFactory.createErrorDialog("Unable to open a item", "Opening items isn't supported on this platform").showAndWait();
      return;
    }
    
    try {
      URI uri = new URI(fileSelecter.getObjectValue());
      try {
        desktop.browse(uri);
      } catch (IOException e) {
        componentFactory.createErrorDialog("Unable to open URL", e.getMessage());
      }
    } catch (URISyntaxException e1) {
      File file = new File(fileSelecter.getObjectValue());
      
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
   */
  private void updateTitle() {
    StringBuilder buf = new  StringBuilder();
    
    String string = titleTextField.getObjectValue();
    if ((string == null)  ||  string.isEmpty()) {

      if (fileSelecter.getObjectValue() != null) {
        File file = new File(fileSelecter.getObjectValue());
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
