package goedegep.jfx.editor.panels;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditPanelTemplate;
import goedegep.jfx.editor.EditorException;
import goedegep.types.model.FileReference;
import goedegep.util.objectselector.ObjectSelectionListener;
import goedegep.util.objectselector.ObjectSelector;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class is an edit panel (extension of {@link EditPanelTemplate} to
 * show/edit a list of file references (a list of {@link FileReference}).
 * <p>
 * The panel (the Control) is a {@link TitledPane}.<br/>
 * The file references can be reordered via drag and drop.
 */
public class FileReferencesEditPanel extends EditPanelTemplate<List<FileReference>> implements ObjectSelector<FileReference> {
  private static final Logger LOGGER = Logger.getLogger(FileReferencesEditPanel.class.getName());
  
  /**
   * Clipboard content type used for drag and drop.
   */
  private static final DataFormat FILE_REFERENCE_PANEL = new DataFormat("FileReferencePanel");
  
  /**
   * Default title of the {@code TitledPane} of a {@code FileReferenceEditPanel}.
   */
  private String referenceEditPanelTitle;
  
  /**
   * 'Add file reference' button text
   */
  private String addFileReferenceButtonText = null;
  
  /**
   * 'Add file reference' button tooltip text
   */
  private String addFileReferenceButtonTooltipText = null;
  
  /**
   * Supplier for the initial folder in the file or folder selector.
   */
  private Function<FileReferenceTypeInfo, String> initialFolderSupplier = null;
    
  /**
   * Information on the types of references to handle.
   */
  private List<FileReferenceTypeInfo> fileReferenceTypeInfos = null;
  
  /**
   * Path prefix.
   */
  private String prefix;
  
  /**
   * Top level panel.
   */
  protected TitledPane titledPane;
  
  /**
   * Pane for the file references.
   */
  protected VBox fileReferencesVBox;
  
  /**
   * The list of {@code FileReferenceEditPanel}s.
   */
  ObservableList<FileReferenceEditPanel> fileReferencePanels;
  
  boolean ignoreChanges = false;
  
  private List<ObjectSelectionListener<FileReference>> objectSelectionListeners = new ArrayList<>();
  
  /**
   * Create a new {@code FileReferencesEditPanel}
   * 
   * @param builder a builder specifying the panel.
   * @return a newly created {@code FileReferencesEditPanel}
   */
  public static FileReferencesEditPanel newInstance(FileReferencesEditPanelBuilder builder) {
    FileReferencesEditPanel fileReferencesEditPanel = new FileReferencesEditPanel(builder);
    
    fileReferencesEditPanel.performInitialization();
    
    return fileReferencesEditPanel;
  }
  
  /**
   * Constructor.
   * 
   * @param builder a builder specifying the panel.
   */
  private FileReferencesEditPanel(FileReferencesEditPanelBuilder builder) {
    super(builder.customization, true);
    
    setLabelBaseText(builder.referencesEditPanelTitle);
//    label = builder.referencesEditPanelTitle;
    this.referenceEditPanelTitle = builder.referenceEditPanelTitle;
    this.addFileReferenceButtonText = builder.addFileReferenceButtonText;
    this.addFileReferenceButtonTooltipText = builder.addFileReferenceButtonTooltipText;
    this.initialFolderSupplier = builder.initialFolderSupplier;
    this.fileReferenceTypeInfos = builder.fileReferenceTypeInfos;
    this.prefix = builder.prefix;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Node getControl() {
    return titledPane;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createControls() {
    fileReferencePanels = FXCollections.observableArrayList();
    fileReferencePanels.addListener(new ListChangeListener<FileReferenceEditPanel>() {

      @Override
      public void onChanged(Change<? extends FileReferenceEditPanel> c) {
        if (ignoreChanges) {
          return;
        }
        
        while (c.next()) {
          if (c.wasPermutated()) {  // NOPMD
            // No action needed here
          } else if (c.wasUpdated()) {
            LOGGER.severe("Update not handled!!");
          } else {
            for (FileReferenceEditPanel documentReferencePanel: c.getRemoved()) {
              unregisterEditorComponents(documentReferencePanel);
              handleChanges();
            }
            for (FileReferenceEditPanel documentReferencePanel: c.getAddedSubList()) {
              registerEditorComponents(documentReferencePanel);
              handleChanges();
            }
          }
        }
        
        updateAttachmentPanel();
        
      }
      
    });
  }
  
  protected void handleChanges() {
    updatePanelTitle();
//    updateGeneratedEventFolder();
//    updateEventFolderInfo();
//    updatePictureImageView();
    
//    super.handleChanges();
  }
  
  private void updatePanelTitle() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(getLabelBaseText());
    if (!isOptional()) {
      buf.append(" ")
      .append(MANDATORY_SYMBOL);
    }
    buf.append(" ")
    .append(getStatusSymbol());
    
    titledPane.setText(buf.toString());
  }
  
  /**
   * Redraw the file references panel ({@link fileReferencesVBox}).
   */
  private void updateAttachmentPanel() {
    fileReferencesVBox.getChildren().clear();
    
    for (FileReferenceEditPanel fileReferenceEditPanel: fileReferencePanels) {
      HBox hBox = componentFactory.createHBox(12.0);
      Button deleteButton = componentFactory.createButton("Delete", "Remove this item");
      deleteButton.setId("Delete ");
      deleteButton.setOnAction((e) -> deleteFileReference(e));
      hBox.getChildren().addAll(fileReferenceEditPanel.getControl(), deleteButton);
      fileReferencesVBox.getChildren().add(hBox);      
    }
  }
  
  /**
   * Handle the action of one of the delete buttons.
   * 
   * @param actionEvent the {@code ActionEvent}.
   */
  private void deleteFileReference(ActionEvent actionEvent) {
    /*
     * The source of the actionEvent is a delete button.
     * Find the HBox with this button, then the first node in this HBox is the control of one of the fileReferencePanels.
     */
    Object eventSource = actionEvent.getSource();
    
    for (Node node: fileReferencesVBox.getChildren()) {
      if (node instanceof HBox hBox) {
        Node lastNode = hBox.getChildren().getLast();
        if (lastNode instanceof Button deleteButton) {
          if (deleteButton == eventSource) {
            Node firstNode = hBox.getChildren().getFirst();
            FileReferenceEditPanel fileReferenceEditPanelToDelete = null;
            for (FileReferenceEditPanel fileReferenceEditPanel: fileReferencePanels) {
              if (fileReferenceEditPanel.getControl() == firstNode) {
                fileReferenceEditPanelToDelete = fileReferenceEditPanel;
                break;
              }
            }
            fileReferencePanels.remove(fileReferenceEditPanelToDelete);
            break;
          }
        }
      }
    }    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createEditPanel() {
    
    VBox contentVBox  = componentFactory.createVBox(12.0, 12.0);
    
    fileReferencesVBox = componentFactory.createVBox(12.0, 12.0);
    contentVBox.getChildren().add(fileReferencesVBox);
    
    Button newFileReferencesButton = componentFactory.createButton("+ " + addFileReferenceButtonText, addFileReferenceButtonTooltipText);
    newFileReferencesButton.setOnAction(e -> createNewAttachmentEditPanel(true));
    contentVBox.getChildren().add(newFileReferencesButton);
    
    titledPane = new TitledPane(getLabelBaseText(), contentVBox);
  }

  
  /**
   * Create a new file reference (attachment) panel.
   * 
   * @param expand if true, the panel will be expanded upon creation.
   * @return the created file reference (attachment) panel.
   */
  private FileReferenceEditPanel createNewAttachmentEditPanel(boolean expand) {
    FileReferenceEditPanel fileReferenceEditPanel = new FileReferenceEditPanel.FileReferencePanelBuilder(customization)
        .setDefaultPaneTitle(referenceEditPanelTitle)
        .setExpandPaneOnCreation(expand)
        .setInitialFolderSupplier(initialFolderSupplier)
        .setFileReferenceTypes(fileReferenceTypeInfos)
        .setPrefix(prefix)
        .build();

    installDragAndDropHandling(fileReferenceEditPanel);
    fileReferenceEditPanel.addObjectSelectionListener((source, fileReference) -> notifyListeners(fileReference));
    fileReferencePanels.add(fileReferenceEditPanel);
    
    return fileReferenceEditPanel;
  }
  
  
  /**
   * Install drag and drop handling.
   * <p>
   * The file references can be reordered via drag and drop.
   */
  private void installDragAndDropHandling(FileReferenceEditPanel fileReferenceEditPanel) {
    
    /*
     * Handle the starting of a drag event.
     * This can be dragged.
     */
    fileReferenceEditPanel.getControl().setOnDragDetected(new EventHandler<MouseEvent>() {

      public void handle(MouseEvent event) {
        ClipboardContent clipboardContent = new ClipboardContent();
        Integer myIndex = fileReferencePanels.indexOf(fileReferenceEditPanel);
        clipboardContent.put(FILE_REFERENCE_PANEL, myIndex);

        Dragboard dragBoard = titledPane.startDragAndDrop(TransferMode.MOVE);
        dragBoard.setContent(clipboardContent);

        event.consume();
      }
    });
    
    /*
     * Check whether a drop event can be handled (upon a drag over).
     * Drop is supported for FILE_REFERENCE_PANEL.
     */
    fileReferenceEditPanel.getControl().setOnDragOver(new EventHandler<DragEvent>() {
      public void handle(DragEvent event) {
        /* data is dragged over a (possible) target */
        /* accept it only if it is not dragged from the same node 
         * and if it has a supported data format. */
        Dragboard dragboard = event.getDragboard();
        if (event.getGestureSource() != fileReferenceEditPanel  &&  dragboard.hasContent(FILE_REFERENCE_PANEL)) {
          event.acceptTransferModes(TransferMode.MOVE);
        }

        event.consume();
      }
    });
    
    /*
     * Handle the dropping on the target
     */
    fileReferenceEditPanel.getControl().setOnDragDropped(new EventHandler<DragEvent>() {
      public void handle(DragEvent event) {
        LOGGER.info("=>");

        boolean success = false;

        // Get the index from the drag board, and if it isn't null use it.
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasContent(FILE_REFERENCE_PANEL)) {
          Integer sourceIndex = (Integer) dragboard.getContent(FILE_REFERENCE_PANEL);
          if (sourceIndex != null) {
            FileReferenceEditPanel sourcePanel = fileReferencePanels.get(sourceIndex);
            int sourceIndexInt = sourceIndex;
            fileReferencePanels.remove(sourceIndexInt);
            
            Integer myIndex = fileReferencePanels.indexOf(fileReferenceEditPanel);
            fileReferencePanels.add(myIndex, sourcePanel);
          }

          /* let the source know whether the item was successfully transferred and used */
          event.setDropCompleted(success);
        }

        event.consume();
      }
    });
    
  }
  
//  @Override
//  protected void fillControlsWithDefaultValues() {
//    // TODO Auto-generated method stub
//    
//  }

  @Override
  protected void fillControlsFromObject() {
    if (value == null) {
      return;
    }
    ignoreChanges = true;
    
    for (FileReference fileReference: value) {
      FileReferenceEditPanel fileReferenceEditPanel = createNewAttachmentEditPanel(false);
      fileReferenceEditPanel.setObject(fileReference);
    }
    
    ignoreChanges = false;
    handleChanges();
    updateAttachmentPanel();
  }

  @Override
  protected void fillObjectFromControls(List<FileReference> fileReferences, boolean getCurrentValue) throws EditorException {
    if (getCurrentValue) {
      fileReferences.clear();
      
      for (FileReferenceEditPanel fileReferenceEditPanel: fileReferencePanels) {
        FileReference fileReference = fileReferenceEditPanel.getCurrentValue();
        fileReferences.add(fileReference);
      }
    } else {
    // First update all panels
    for (FileReferenceEditPanel fileReferenceEditPanel: fileReferencePanels) {
      fileReferenceEditPanel.accept();
    }
    
    // Check for any changes. If there are changes, recreate the complete list.
    boolean changes = false;
    
    if (fileReferences.size() != fileReferencePanels.size()) {
      LOGGER.info("changes because of different list sizes");
      changes = true;
    }
    
    if (!changes) {
//      int index = 0;
//      for (FileReference attachment: fileReferences) {
//        FileReferenceEditPanel fileReferencePanel = fileReferencePanels.get(index++);
//        FileReferenceTypeInfo fileReferencePanelReferenceType = fileReferencePanel.getReferenceType();
//        String fileReferencePanelReferenceTypeTag = fileReferencePanelReferenceType != null ? fileReferencePanelReferenceType.tag() : null;
//        String fileReferencePanelFile = fileReferencePanel.getPathNameRelativeToPrefix();
//        if (!PgUtilities.equals(attachment.getTags(), fileReferencePanelReferenceTypeTag)  ||
//            !attachment.getFile().equals(fileReferencePanelFile)  ||
//            !PgUtilities.equals(attachment.getTitle(), fileReferencePanel.getTitleObjectControl().getValue())) {
//          changes = true;
//          break;
//        }
//      }
    }
    
    if (changes) {
//      object.clear();
      
//      for (FileReferenceEditPanel fileReferencePanel: fileReferencePanels) {
////        FileReference fileReference = TypesFactory.eINSTANCE.createFileReference();
////        updateFileReferenceFromFileReferencePanel(fileReference, fileReferencePanel);
//        object.add(fileReferencePanel.getValue());
//      }
    }
    
    }
    
  }

  @Override
  protected void setErrorFeedback(boolean valid) {
    // TODO Auto-generated method stub
    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<FileReference> createObject() {
    return new ArrayList<FileReference>();
  }

  /**
   * Builder class for creating a FileReferencesPanel.
   */
  public static class FileReferencesEditPanelBuilder {
    /*
     * Required parameters
     */

    /**
     * GUI customization
     */
    private CustomizationFx customization;
    
    
    /*
     * Optional parameters
     */
    
    /**
     * FileReferencesEditPanel panel title.
     */
    private String referencesEditPanelTitle = null;
    
    /**
     * Default FileReferenceEditPanel panel title.
     */
    private String referenceEditPanelTitle = null;
    
    /**
     * 'Add file reference' button text
     */
    private String addFileReferenceButtonText = null;
    
    /**
     * 'Add file reference' button tooltip text
     */
    private String addFileReferenceButtonTooltipText = null;
    
    /**
     * Supplier for the initial folder in the file or folder selecter.
     */
    private Function<FileReferenceTypeInfo, String> initialFolderSupplier = null;
    
    /**
     * Information on the types of references to handle.
     */
    private List<FileReferenceTypeInfo> fileReferenceTypeInfos = null;
    
    /**
     * Path prefix.
     */
    private String prefix;
    


    /**
     * Constructor providing required parameters.
     * 
     * @param customization the GUI customization.
     */
    public FileReferencesEditPanelBuilder(CustomizationFx customization) {
      this.customization = customization;
    }
    
    /**
     * Set the references edit panel title.
     * 
     * @param referencesEditPanelTitle the references edit panel title.
     * @return this.
     */
    public FileReferencesEditPanelBuilder setReferencesEditPanelTitle(String referencesEditPanelTitle) {
      this.referencesEditPanelTitle = referencesEditPanelTitle;
      return this;
    }
    
    /**
     * Set the default reference edit panel title.
     * 
     * @param referenceEditPanelTitle the reference edit panel title.
     * @return this.
     */
    public FileReferencesEditPanelBuilder setReferenceEditPanelTitle(String referenceEditPanelTitle) {
      this.referenceEditPanelTitle = referenceEditPanelTitle;
      return this;
    }
    
    /**
     * Set the text for the 'add file reference' button.
     * 
     * @param addFileReferenceButtonText the text for the 'add file reference' button.
     * @return this.
     */
    public FileReferencesEditPanelBuilder setAddFileReferenceButtonText(String addFileReferenceButtonText) {
      this.addFileReferenceButtonText = addFileReferenceButtonText;
      return this;
    }
    
    /**
     * Set the tooltip text for the 'add file reference' button.
     * 
     * @param addFileReferenceButtonText the text for the 'add file reference' button.
     * @return this.
     */
    public FileReferencesEditPanelBuilder setAddFileReferenceButtonTooltipText(String addFileReferenceButtonTooltipText) {
      this.addFileReferenceButtonTooltipText = addFileReferenceButtonTooltipText;
      return this;
    }
    
    /**
     * Set the initial folder supplier.
     * 
     * @param initialFolderSupplier a method to provide an initial folder.
    * @return this.
     */
    public FileReferencesEditPanelBuilder setInitialFolderSupplier(Function<FileReferenceTypeInfo, String> initialFolderSupplier) {
      this.initialFolderSupplier = initialFolderSupplier;
      return this;
    }
    
    /**
     * Add one or more reference types.
     * 
     * @param fileReferenceTypeInfo
     * @return this.
     */
    public FileReferencesEditPanelBuilder addFileReferenceTypes(FileReferenceTypeInfo... fileReferenceTypeInfo) {
      if (fileReferenceTypeInfos == null) {
        fileReferenceTypeInfos = new ArrayList<>();
      }
      
      for (FileReferenceTypeInfo aFileReferenceTypeInfo: fileReferenceTypeInfo) {
        fileReferenceTypeInfos.add(aFileReferenceTypeInfo);
      }
      
      return this;
    }

    /**
     * Set a path prefix.
     * 
     * @param prefix the file/folder prefix.
     * @return this.
     */
    public FileReferencesEditPanelBuilder setPrefix(String prefix) {
      this.prefix = prefix;
      return this;
    }

    
    /**
     * Construct the panel.
     * 
     * @return the created panel.
     */
    public FileReferencesEditPanel build() {
      return FileReferencesEditPanel.newInstance(this);
    }
  }

  public List<FileReferenceEditPanel> getFileReferenceEditPanels() {
    return fileReferencePanels;
  }

  @Override
  public String getValueAsFormattedText() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void addObjectSelectionListener(ObjectSelectionListener<FileReference> objectSelectionListener) {
    objectSelectionListeners.add(objectSelectionListener);
  }

  @Override
  public void removeObjectSelectionListener(ObjectSelectionListener<FileReference> objectSelectionListener) {
    objectSelectionListeners.remove(objectSelectionListener);
  }
  
  private void notifyListeners(FileReference fileReference) {
    for (ObjectSelectionListener<FileReference> objectSelectionListener: objectSelectionListeners) {
      objectSelectionListener.objectSelected(this, fileReference);
    }
  }

  @Override
  public FileReference getSelectedObject() {
    for (FileReferenceEditPanel fileReferenceEditPanel: fileReferencePanels) {
      FileReference fileReference = fileReferenceEditPanel.getSelectedObject();
      if (fileReference != null) {
        return fileReference;
      }
    }
    
    return null;
  }

}

