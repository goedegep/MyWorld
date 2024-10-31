package goedegep.jfx.objecteditor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectEditPanelTemplate;
import goedegep.types.model.FileReference;
import goedegep.util.PgUtilities;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.VBox;

/**
 * This class is an object edit panel (extension of {@link ObjectEditPanelTemplate} to
 * show/edit a list of file reference (a list of {@link FileReference}).
 * <p>
 * The panel (the Control) is a {@link TitledPane}.<br/>
 * The file references can be reordered via drag and drop.
 */
public class FileReferencesEditPanel extends ObjectEditPanelTemplate<List<FileReference>> {
  private static final Logger LOGGER = Logger.getLogger(FileReferencesEditPanel.class.getName());
  
  /**
   * Clipboard content type used for drag and drop.
   */
  private static final DataFormat FILE_REFERENCE_PANEL = new DataFormat("FileReferencePanel");

  
  /**
   * Title of the {@code TitledPane}.
   */
  private String referencesEditPanelTitle;
  
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
   * Top level panel.
   */
  protected TitledPane titledPane;
  
  /**
   * Pane for the file references.
   */
  protected VBox fileReferencesVBox;
  
  ObservableList<FileReferenceEditPanel> fileReferencePanels;
  
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
    super(builder.customization);
    
    this.referencesEditPanelTitle = builder.referencesEditPanelTitle;
    this.referenceEditPanelTitle = builder.referenceEditPanelTitle;
    this.addFileReferenceButtonText = builder.addFileReferenceButtonText;
    this.addFileReferenceButtonTooltipText = builder.addFileReferenceButtonTooltipText;
    this.initialFolderSupplier = builder.initialFolderSupplier;
    this.fileReferenceTypeInfos = builder.fileReferenceTypeInfos;
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
  protected void createControls() {
    fileReferencePanels = FXCollections.observableArrayList();
    fileReferencePanels.addListener(new ListChangeListener<FileReferenceEditPanel>() {

      @Override
      public void onChanged(Change<? extends FileReferenceEditPanel> c) {
        while (c.next()) {
          if (c.wasPermutated()) {  // NOPMD
            // No action needed here
          } else if (c.wasUpdated()) {
            LOGGER.severe("Update not handled!!");
          } else {
//            for (FileReferenceEditPanel documentReferencePanel: c.getRemoved()) {
//              objectControlsGroup.removeObjectControlGroup(documentReferencePanel.getObjectControlsGroup());
//              handleChanges();
//            }
//            for (FileReferenceEditPanel documentReferencePanel: c.getAddedSubList()) {
//              objectControlsGroup.addObjectControlGroup(documentReferencePanel.getObjectControlsGroup());
//              handleChanges();
//            }
          }
        }
        
        updateAttachmentPanel();
        
      }
      
    });
    objectControlsGroup.setId("file references edit panel");
  }
  
  protected void handleChanges() {
//    updateGeneratedEventFolder();
//    updateEventFolderInfo();
//    updatePictureImageView();
    
//    super.handleChanges();
  }
  
  private void updateAttachmentPanel() {
    fileReferencesVBox.getChildren().clear();
    for (FileReferenceEditPanel fileReferenceEditPanel: fileReferencePanels) {
      fileReferencesVBox.getChildren().add(fileReferenceEditPanel.getControl());
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
    
    titledPane = new TitledPane(referencesEditPanelTitle, contentVBox);
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
        .build();

    objectControlsGroup.addObjectControlGroup(fileReferenceEditPanel.getObjectControlsGroup());
    installDragAndDropHandling(fileReferenceEditPanel);
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
//  public void createObject() {
//    // TODO Auto-generated method stub
//    
//  }

  @Override
  protected void fillControlsWithDefaultValues() {
    // TODO Auto-generated method stub
    
  }

  @Override
  protected void fillControlsFromObject() {
    for (FileReference fileReference: object) {
      FileReferenceEditPanel fileReferenceEditPanel = new FileReferenceEditPanel.FileReferencePanelBuilder(customization)
          .setDefaultPaneTitle(referenceEditPanelTitle)
          .setExpandPaneOnCreation(true)
          .setInitialFolderSupplier(initialFolderSupplier)
          .setFileReferenceTypes(fileReferenceTypeInfos)
          .build();
      
      installDragAndDropHandling(fileReferenceEditPanel);
      objectControlsGroup.addObjectControlGroup(fileReferenceEditPanel.getObjectControlsGroup());
      fileReferenceEditPanel.setObject(fileReference);

      fileReferencePanels.add(fileReferenceEditPanel);
    }
    
  }

  @Override
  protected void updateObjectFromControls() throws ObjectEditorException {
    // First update all panels
    for (FileReferenceEditPanel fileReferenceEditPanel: fileReferencePanels) {
      fileReferenceEditPanel.updateObjectFromControls();
    }
    
    // Check for any changes. If there are changes, recreate the complete list.
    boolean changes = false;
    
    if (object.size() != fileReferencePanels.size()) {
      LOGGER.severe("changes because of different list sizes");
      changes = true;
    }
    
    if (!changes) {
      int index = 0;
      for (FileReference attachment: object) {
        FileReferenceEditPanel fileReferencePanel = fileReferencePanels.get(index++);
        FileReferenceTypeInfo fileReferencePanelReferenceType = fileReferencePanel.getReferenceType();
        String fileReferencePanelReferenceTypeTag = fileReferencePanelReferenceType != null ? fileReferencePanelReferenceType.tag() : null;
        String fileReferencePanelFile = fileReferencePanel.getPathNameRelativeToPrefix();
        if (!PgUtilities.equals(attachment.getTags(), fileReferencePanelReferenceTypeTag)  ||
            !attachment.getFile().equals(fileReferencePanelFile)  ||
            !PgUtilities.equals(attachment.getTitle(), fileReferencePanel.getTitleObjectControl().getValue())) {
          changes = true;
          break;
        }
      }
    }
    
    if (changes) {
//      List<FileReference> fileReferences = object.getAttachments();
      object.clear();
      
      for (FileReferenceEditPanel fileReferencePanel: fileReferencePanels) {
//        FileReference fileReference = TypesFactory.eINSTANCE.createFileReference();
//        updateFileReferenceFromFileReferencePanel(fileReference, fileReferencePanel);
        object.add(fileReferencePanel.getValue());
      }
    }
    
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

}

