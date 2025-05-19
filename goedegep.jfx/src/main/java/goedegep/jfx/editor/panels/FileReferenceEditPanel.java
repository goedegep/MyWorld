package goedegep.jfx.editor.panels;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditPanelTemplate;
import goedegep.jfx.editor.EditorControl;
import goedegep.jfx.editor.EditorException;
import goedegep.jfx.editor.controls.EditorControlFileOrFolderSelecterAbstract;
import goedegep.jfx.editor.controls.EditorControlFileSelecter;
import goedegep.jfx.editor.controls.EditorControlFolderSelecter;
import goedegep.jfx.editor.controls.EditorControlString;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesFactory;
import goedegep.types.model.TypesPackage;
import goedegep.util.emf.EmfUtil;
import goedegep.util.objectselector.ObjectSelectionListener;
import goedegep.util.objectselector.ObjectSelector;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * This class is an edit panel (extension of {@link EditPanelTemplate} to show/edit a {@link FileReference}.
 * <p>
 * By default the panel handles a reference to a file,
 * but by providing a list of {@code FileReferenceTypeInfo} references to folders are also supported.
 * In the latter case, there's an extra control via which the user can select the reference type.<br/>
 * This class implements the ObjectSelector interface, which in this case means that the FileReference is either selected or not.
 * <p>
 * The panel (the Control) is a {@link TitledPane}.
 */
public class FileReferenceEditPanel extends EditPanelTemplate<FileReference> implements ObjectSelector<FileReference> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(FileReferenceEditPanel.class.getName());
  
  private static int instanceNr = 1;
  
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
  private Function<FileReferenceTypeInfo, String> initialFolderSupplier = null;
  
  /**
   * Path prefix
   */
  private String prefix = null;
  
  /**
   * Information on the types of references to handle.
   */
  private List<FileReferenceTypeInfo> fileReferenceTypeInfos = null;
  
  /**
   * The currently selected reference type
   */
  FileReferenceTypeInfo currentFileReferenceTypeInfo = null;
  
  /**
   * Indication of whether we're handling a file or folder reference (or nothing).
   */
  private Boolean handlingFileReference = null;
    
  
  // The ObjectControls
  
  /**
   * Editor control used if the reference is a file reference.
   */
  private EditorControlFileSelecter fileSelecterEditorControl = null;
  
  /**
   * Editor control used if the reference is a folder reference.
   */
  private EditorControlFolderSelecter folderSelecterEditorControl = null;
  
  /**
   * Object control for the reference title.
   */
  private EditorControlString titleEditorControl;
  
  
  // Other
  
  /**
   * Control for the reference type.
   * This is only used if {@code fileReferenceTypeInfos} is not null.
   */
  private ComboBox<String> referenceTypeComboBox;
  
  /**
   * Top level panel.
   */
  protected TitledPane titledPane;

  /**
   * The pane containing the controls.
   */
  private GridPane gridPane;
  
  /**
   * Reference to the {@code DeskTop}, used to open a file.
   */
  private Desktop  desktop = null;
  
  private List<ObjectSelectionListener<FileReference>> objectSelectionListeners = new ArrayList<>();
  
  
  /**
   * Factory method to create a new {@code FileReferenceEditPanel} instance.
   * 
   * @param builder A builder with all configuration information.
   * @return a new {@code FileReferenceEditPanel} instance, configured according to the {@code builder}.
   */
  public static FileReferenceEditPanel newInstance(FileReferencePanelBuilder builder) {
    FileReferenceEditPanel fileReferenceEditPanel = new FileReferenceEditPanel(builder);
    
    fileReferenceEditPanel.performInitialization();
    
    return fileReferenceEditPanel;
  }
  
  /**
   * Constructor using builder
   * <p>
   * @param builder A builder with all configuration information.
   */
  private FileReferenceEditPanel(FileReferencePanelBuilder builder) {
    super(builder.customization, true);
    
    this.customization = builder.customization;
    this.defaultPanelTitle = builder.defaultPaneTitle;
    this.expandPanelOnCreation = builder.expandPaneOnCreation;
    this.initialFolderSupplier = builder.initialFolderSupplier;
    this.prefix = builder.prefix;
    this.fileReferenceTypeInfos = builder.fileReferenceTypeInfos;
        
    value = TypesFactory.eINSTANCE.createFileReference();  // TODO shall there be a createObject() ?
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Node getControl() {
    return titledPane;
  }
    
  /**
   * Get the file selecter object control.
   * <p>
   * The control will be created if this hasn't been done yet.
   * 
   * @return the file selecter object control.
   */
  private EditorControlFileSelecter getFileSelecterEditorControl() {
    if (fileSelecterEditorControl == null) {
      fileSelecterEditorControl = componentFactory.createEditorControlFileSelecter(400, "Currently selected file",
          "Choose file", "Select a file via a file chooser", "Select the file", false);
      fileSelecterEditorControl.setId("fileSelecter");
      fileSelecterEditorControl.setInitialFolderProvider(this::getInitialFolder);
      if (prefix != null) {
        fileSelecterEditorControl.setPrefix(prefix);
      }
      registerEditorComponents(fileSelecterEditorControl);
    }
    
    return fileSelecterEditorControl;
  }
  
  /**
   * Get the initial folder as text.
   * @return  the initial folder as text.
   */
  private String getInitialFolder() {
    return initialFolderSupplier != null ? initialFolderSupplier.apply(currentFileReferenceTypeInfo) : null;
  }
  
  /**
   * Get the folderSelecter object control.
   * <p>
   * The control will be created if this hasn't been done yet.
   * 
   * @return the folderSelecter object control.
   */
  private EditorControlFolderSelecter getFolderSelecterEditorControl() {
    if (folderSelecterEditorControl == null) {
      folderSelecterEditorControl = componentFactory.createEditorControlFolderSelecter(400, "Currently selected folder",
          "Choose folder", "Select a folder via a folder chooser", "Select the folder", false);
      folderSelecterEditorControl.setId("folderSelecter");
      folderSelecterEditorControl.setInitialFolderProvider(this::getInitialFolder);
      registerEditorComponents(folderSelecterEditorControl);
    }
    
    return folderSelecterEditorControl;
  }
        
//  /**
//   * Get the selected file or folder path name, relative to the prefix.
//   * 
//   * @return the selected file or folder, if this has a valid value, null otherwise.
//   */
//  private String getPathNameRelativeToPrefix() {
//    if (handlingFileReference) {
//      if (getFileSelecterEditorControl().isValid()) {
//        return getFileSelecterEditorControl().getPathNameRelativeToPrefix();
//      } else {
//        return null;
//      }
//    } else {
//      if (getFolderSelecterEditorControl().isValid()) {
//        return getFolderSelecterEditorControl().getPathNameRelativeToPrefix();
//      } else {
//        return null;
//      }
//    }
//  }
  
//  /**
//   * Get the File (which may be a folder) from the file or folder selecter.
//   * 
//   * @return the File (which may be a folder) from the file or folder selecter.
//   */
//  public File getFile() {
//    if (handlingFileReference) {
//      if (getFileSelecterEditorControl().isValid()) {
//        return getFileSelecterEditorControl().getValue();
//      } else {
//        return null;
//      }
//    } else {
//      if (getFolderSelecterEditorControl().isValid()) {
//        return getFolderSelecterEditorControl().getValue();
//      } else {
//        return null;
//      }
//    }
//  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void createControls() {
    if (fileReferenceTypeInfos != null) {
      ObservableList<String> items = FXCollections.observableArrayList();
      for (FileReferenceTypeInfo fileReferenceTypeInfo: fileReferenceTypeInfos) {
        items.add(fileReferenceTypeInfo.displayName());
      }
      referenceTypeComboBox = new ComboBox<>(items);      
      referenceTypeComboBox.setOnAction(_ -> handleNewReferenceTypeSelected());
      referenceTypeComboBox.getSelectionModel().select(0);
    }
    
    titleEditorControl = new EditorControlString.Builder("title")
        .setWidth(200d)
        .setLabelBaseText("Title")
        .setToolTipText("A title for the file")
        .setOptional(true)
        .build();
    
//    titleEditorControl = componentFactory.createEditorControlString(200, true, "a title for the file");
//    titleEditorControl.setId("title");
    registerEditorComponents(titleEditorControl);
        
    titledPane = new TitledPane();
    titledPane.setId("fileReferenceEditPanel " + instanceNr++);    
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void createEditPanel() {
    VBox rootPane = componentFactory.createVBox();

    gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    
    rootPane.getChildren().addAll(gridPane, createButtonsBox());
    
    titledPane.setContent(rootPane);
    titledPane.setExpanded(expandPanelOnCreation);
    
    handleNewReferenceTypeSelected();
   }

  /**
   * Create the box with the action buttons: 'Open item' and 'Delete this item'.
   * 
   * @return the created box.
   */
  private Node createButtonsBox() {
    HBox buttonsBox = componentFactory.createHBox(12.0, 12.0);
    
    Button openItemButton = componentFactory.createButton("Open item", "Open this attachment with the related application");
    openItemButton.setOnAction(_ -> openItem());
    buttonsBox.getChildren().add(openItemButton);
    
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    buttonsBox.getChildren().add(spacer);
    
// TODO
//    Button deleteButton = componentFactory.createButton("Delete this reference", "Delete this reference");
//    deleteButton.setOnAction(e -> deleteThisFileReference());
//    buttonsBox.getChildren().add(deleteButton);
    return  buttonsBox;
  }
  
  @Override
  protected void installChangeListeners() {
    addValueAndOrStatusChangeListener((_, _) -> updatePaneTitle());
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
    
    String prefix = this.prefix;
    if (currentFileReferenceTypeInfo != null) {
      prefix = currentFileReferenceTypeInfo.filePathPrefix();
    }
    if (isFile) {
      getFileSelecterEditorControl().setPrefix(prefix);
    } else {
    getFolderSelecterEditorControl().setPrefix(prefix);
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
    EditorControl<?> fileOrFolderSelecterEditorControl;
    
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
          
      gridPane.add(getFileSelecterEditorControl().getControl(), 1, row);
      fileOrFolderSelecterEditorControl = getFileSelecterEditorControl();
      
      Button fileChooserButton = getFileSelecterEditorControl().getFileChooserButton();
      gridPane.add(fileChooserButton, 2, row);
    } else {
      // Row 1: folder selection; label, textfield and button to start folder chooser.
      Label fileNameLabel = componentFactory.createLabel("Folder:");
      gridPane.add(fileNameLabel, 0, row);
          
      gridPane.add(getFolderSelecterEditorControl().getControl(), 1, row);
      fileOrFolderSelecterEditorControl = getFolderSelecterEditorControl();
      
      Button fileChooserButton = getFolderSelecterEditorControl().getFolderChooserButton();
      gridPane.add(fileChooserButton, 2, row);
    }
    
    row++;
    
    // Row 2: title; label, textfield
    Label titleLabel = componentFactory.createLabel("Title:");
    gridPane.add(titleLabel, 0, row);

    gridPane.add(titleEditorControl.getControl(), 1, row);
    ChangeListener<Boolean> cl = new ChangeListener<>() {

      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        focusedProperty.set(newValue);
      }
      
    };
    titleEditorControl.getControl().focusedProperty().addListener(cl);
    fileOrFolderSelecterEditorControl.getControl().focusedProperty().addListener(cl);
    titledPane.focusedProperty().addListener(cl);
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
      fileOrFolderPath = getFileSelecterEditorControl().getAbsolutePath();
    } else {
      fileOrFolderPath = getFolderSelecterEditorControl().getAbsolutePath();
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
    // Before more Desktop API is used, first check 
    // whether the API is supported by this particular 
    // virtual machine (VM) on this particular host.
    if (desktop == null  &&  Desktop.isDesktopSupported()) {
      desktop = Desktop.getDesktop();                
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
   * To this a valid/invalid indication is added, based on the status of the {@code myObjectControlsGroup}.
   */
  private void updatePaneTitle() {
    StringBuilder buf = new  StringBuilder();
    
    String string = titleEditorControl.getValue();
    if ((string == null)  ||  string.isEmpty()) {

      if (getFileSelecterEditorControl().getValue() != null) {
        string = getFileSelecterEditorControl().getValue().getName();
      } else {
        string = defaultPanelTitle;
      }
    }
    
    buf.append(string)
    .append(" ")
    .append(getStatusSymbol());
        
    titledPane.setText(buf.toString());
  }

//  @Override
//  protected void fillControlsWithDefaultValues() {
//    getFileSelecterEditorControl().setObject(null);
//    titleEditorControl.setObject(null);
//  }

  @Override
  protected void fillControlsFromObject() {
    if (value == null) {
      return;
    }
    
    String fileName = value.getFile();
    if (fileName != null) {
      String prefix = getPrefixForTag(value.getTags());
      if (prefix != null) {
        fileSelecterEditorControl.setPathNameRelativeToPrefix(fileName);
      } else {
        File file = new File(fileName);
        fileSelecterEditorControl.setObject(file);
      }
    }
    
    titleEditorControl.setObject(value.getTitle());
  }
  
  private String getPrefixForTag(String tags) {
    if (fileReferenceTypeInfos == null) {
      return prefix;
    }
    
    if (tags == null) {
      return null;
    }
    
    for (FileReferenceTypeInfo fileReferenceTypeInfo: fileReferenceTypeInfos) {
      if (tags.equals(fileReferenceTypeInfo.tag())) {
        return fileReferenceTypeInfo.filePathPrefix();
      }
    }
    
    return null;
  }

  @Override
  protected void fillObjectFromControls(FileReference object, boolean getCurrentValue) throws EditorException {
    if (currentFileReferenceTypeInfo != null) {
      EmfUtil.setFeatureValue(object, TypesPackage.eINSTANCE.getFileReference_Tags(), currentFileReferenceTypeInfo.tag());
    }
    EmfUtil.setFeatureValue(object, TypesPackage.eINSTANCE.getFileReference_File(), fileSelecterEditorControl.getPathNameRelativeToPrefix());
    EmfUtil.setFeatureValue(object, TypesPackage.eINSTANCE.getFileReference_Title(), titleEditorControl.getValue());
  }

  @Override
  protected void setErrorFeedback(boolean valid) {
    // TODO Auto-generated method stub
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FileReference createObject() {
    return TypesFactory.eINSTANCE.createFileReference();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("File: " + fileSelecterEditorControl.getValue());
    buf.append("Title: " + titleEditorControl.getValue());
    buf.append("titledPane: " + titledPane);
    
    return buf.toString();
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

  private void notifyListeners(Boolean newValue) {
    for (ObjectSelectionListener<FileReference> objectSelectionListener: objectSelectionListeners) {
      try {
        objectSelectionListener.objectSelected(this, getCurrentValue());
      } catch (EditorException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public FileReference getSelectedObject() {
    // TODO Auto-generated method stub
    return null;
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
    private Function<FileReferenceTypeInfo, String> initialFolderSupplier = null;
    
    /**
     * Path prefix.
     */
    private String prefix;
    
    /**
     * Information on the types of references to handle.
     */
    private List<FileReferenceTypeInfo> fileReferenceTypeInfos = null;
    
    
    /**
     * Constructor providing required parameters.
     * 
     * @param customization the GUI customization.
     */
    public FileReferencePanelBuilder(CustomizationFx customization) {
      this.customization = customization;
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
     * 
     * @param initialFolderSupplier a method to provide an initial folder.
    * @return this.
     */
    public FileReferencePanelBuilder setInitialFolderSupplier(Function<FileReferenceTypeInfo, String> initialFolderSupplier) {
      this.initialFolderSupplier = initialFolderSupplier;
      return this;
    }

    /**
     * Set a path prefix.
     * 
     * @param prefix the file/folder prefix.
     * @return this.
     */
    public FileReferencePanelBuilder setPrefix(String prefix) {
      this.prefix = prefix;
      return this;
    }
    
    /**
     * Add one or more reference types.
     * 
     * @param fileReferenceTypeInfo
     * @return this.
     */
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
     * Set the reference types.
     * 
     * @param fileReferenceTypeInfos
     * @return this.
     */
    public FileReferencePanelBuilder setFileReferenceTypes(List<FileReferenceTypeInfo> fileReferenceTypeInfos) {
      this.fileReferenceTypeInfos = fileReferenceTypeInfos;
      
      return this;
    }
    
    /**
     * Construct the panel.
     * 
     * @return the created panel.
     */
    public FileReferenceEditPanel build() {
      return FileReferenceEditPanel.newInstance(this);
    }
  }  // End of FileReferencePanelBuilder
}
