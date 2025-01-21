package goedegep.jfx.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import javafx.beans.InvalidationListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * This class is the Template class for creating an editor.
 * 
 * @param <T> The type of the value being edited.
 */
public abstract class EditorTemplate<T> extends JfxStage implements Editor<T> {
  private static Logger LOGGER = Logger.getLogger(EditorTemplate.class.getName());
  private static final String NEW_LINE = System.getProperty("line.separator");

  /**
   * A pre-defined {@link ButtonType} that displays "Add" (in new mode) or "Update" (in edit mode).
   */
  public static final ButtonType ADD_OR_UPDATE = new ButtonType("ObjectEditor.addorupdate.button");

  /**
   * A pre-defined {@link ButtonType} that displays "Add" to add the value being edited.
   */
  public static final ButtonType ADD = new ButtonType("ObjectEditor.add.button");

  /**
   * A pre-defined {@link ButtonType} that displays "Update" to update the value being edited.
   */
  public static final ButtonType UPDATE = new ButtonType("ObjectEditor.update.button");
  
  /**
   * A pre-defined {@link ButtonType} that displays "New" to start editing a new object.
   */
  public static final ButtonType NEW = new ButtonType("ObjectEditor.new.button");
  
  /*
   *  Configuration texts
   */
  
  /**
   * Text for the button for adding the object to the collection.
   */
  protected String addObjectText = "Add object";
  
  /**
   * Tooltip text for the button for adding the object to the collection.
   */
  protected String addObjectTooltipText = "Add the object to the collection";
  
  /**
   * Text for the button for updating the object.
   */
  protected String updateObjectText = "Update object";
  
  /**
   * Tooltip text for the button for updating the object.
   */
  protected String updateObjectTooltipText = "Update the current object";
  
  /**
   * Text for the button to clear the editor (start editing a new object).
   */
  protected String newObjectText = "New";
  
  /**
   * Tooltip text  for the button to clear the editor (start editing a new object).
   */
  protected String newObjectTooltipText = "Clear controls to start creating new InquiryData";
  
  /**
   * Other fields
   */
  
  /**
   * Callback method for adding a new object
   */
  private Consumer<T> addObjectMethod;
  
  /**
   * A {@code Label} for showing status information.
   */
  private Label statusLabel;
  
  /**
   * A {@code Label}, placed in front of the buttons, showing the edit status.
   */
  private Label editStatusLabel;
  
  /**
   * The action buttons.
   */
  private List<ButtonType> buttonTypes;
  
  /**
   * The 'Add' button
   */
  private Button addButton;
  
  /**
   * The 'Update' button
   */
  private Button updateButton;
  
  /**
   * The 'New' button
   */
  private Button newButton;
  
  /**
   * The 'Cancel' button
   */
  private Button cancelButton;

  /**
   * Panel for the action buttons (Add/Update and New).
   */
  protected HBox actionButtonsPanel;
  
  /**
   * The main edit panel.
   */
  private EditPanel<T> editPanel;
  
  /**
   * Indicates whether we're creating new object, or editing an existing object.
   */
  protected EditMode editMode = EditMode.NEW;
  

  private EditStatus editStatus = null;

  
  /**
   * The invalidation listeners
   */
  protected List<InvalidationListener> invalidationListeners = new ArrayList<>();
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   * @param title the window title.
   * @param addObjectMethod method for adding a new object.
   */
  protected EditorTemplate(CustomizationFx customization, String title, Consumer<T> addObjectMethod) {
    super(customization, title);
    
    this.addObjectMethod = addObjectMethod;
    
    statusLabel = componentFactory.createStrongLabel(null);
    
    editStatusLabel = componentFactory.createStrongLabel(null);
    String fontName = editStatusLabel.getFont().getName();
    editStatusLabel.setFont(new Font(fontName, 22));
    
    buttonTypes = new ArrayList<>();
    addButton = componentFactory.createButton(addObjectText, addObjectTooltipText);
    addButton.setOnAction(e -> addObjectAction());
    buttonTypes.add(ADD);
    buttonTypes = new ArrayList<>();
    updateButton = componentFactory.createButton(updateObjectText, updateObjectTooltipText);
    updateButton.setOnAction(e -> accept());
    buttonTypes.add(UPDATE);
    newButton = componentFactory.createButton(newObjectText, newObjectTooltipText);
    newButton.setOnAction(e -> newObject(true));
    buttonTypes.add(NEW);
    cancelButton = componentFactory.createButton("Cancel", "Discard any changes and close the editor");
    cancelButton.setOnAction(e -> closeWindow());
    buttonTypes.add(ButtonType.CANCEL);
  }
  
  /**
   * Perform editor initialization.
   * 
   * @return this
   */
  protected void performInitialization() {
    configureEditor();
        
    VBox rootPane = componentFactory.createVBox();
    
    editPanel = getMainEditPanel();
    ScrollPane scrollPane = new ScrollPane(editPanel.getControl());
    rootPane.getChildren().add(scrollPane);
    
    createActionButtonsPanel();
    rootPane.getChildren().add(actionButtonsPanel);

    setScene(new Scene(rootPane));
        
    editPanel.addValueAndOrStatusChangeListener((valueChanged, statusChanged) -> handleChanges(valueChanged, statusChanged));
    installChangeListeners();
    
    editStatus = determineEditStatus();
    
    handleChanges(true, true);
  }
  
  /**
   * Get the main edit panel.
   * 
   * @return the main edit panel.
   */
  protected abstract EditPanel<T> getMainEditPanel();
  
  /**
   * Configure the editor.
   * <p>
   * Override this method to make calls to setAddObjectTexts(), setUpdateObjectTexts() and setNewObjectTexts() for setting the texts for the action buttons.
   */
  protected void configureEditor() {
    setAddObjectTexts("Add object", "Add the object to the collection");
    setUpdateObjectTexts("Update object", "Update the current object");
    setNewObjectTexts("New", "Clear the control to start entering new object data");
  }
  
  /**
   * Set the text and the tooltip text for the 'add object' button.
   * 
   * @param buttonText the text for the button
   * @param buttonTooltipText the tooltip text for the button
   */
  protected void setAddObjectTexts(String buttonText, String buttonTooltipText) {
    addObjectText = buttonText;
    addObjectTooltipText = buttonTooltipText;
  }
  
  /**
   * Set the text and the tooltip text for the 'update object' button.
   * 
   * @param buttonText the text for the button
   * @param buttonTooltipText the tooltip text for the button
   */
  protected void setUpdateObjectTexts(String buttonText, String buttonTooltipText) {
    updateObjectText = buttonText;
    updateObjectTooltipText = buttonTooltipText;
  }
  
  /**
   * Set the text and the tooltip text for the 'new object' button.
   * 
   * @param buttonText the text for the button
   * @param buttonTooltipText the tooltip text for the button
   */
  protected void setNewObjectTexts(String buttonText, String buttonTooltipText) {
    newObjectText = buttonText;
    newObjectTooltipText = buttonTooltipText;
  }
  
  /**
   * Start editing an object and inform the user about unsaved changes.
   * <p>
   * If there are any unsaved changes, show a dialog informing the user about this and ask for a confirmation.
   * {@code object} is set to the specified value.
   * All the controls are cleared and then filled with the information from the {@code object}.
   * 
   * @param object the value to be edited.
   */
  public void setObject(T object) {
    setObject(object, true);
  }

  /**
   * Start editing an object, with the option to inform the user about unsaved changes.
   * <p>
   * If there are any unsaved changes while checkOnUnsavedChanges is set, show a dialog informing the user about this and ask for a confirmation.
   * All the controls are set to their default values and then filled with the information from the {@code object}.
   * 
   * @param object the value to be edited, or null to start editing a new object.
   * @param checkOnUnsavedChanges if true and if there are any unsaved changes, show a dialog informing the user about this and ask for a confirmation.
   */
  public void setObject(T object, boolean checkOnUnsavedChanges) {
    Objects.requireNonNull(object, "object may not be null (use newObject()");
    
    if (checkOnUnsavedChanges  &&  !getUserConfirmationInCaseOfUnsavedChanges()) {
      return;
    }

    editPanel.setObject(object);
    
    editMode = EditMode.EDIT;

    handleChanges(true, true);
    notifyListeners();
  }
  
  /**
   * Start editing a new object.
   * <p>
   * If there are any unsaved changes while checkOnUnsavedChanges is set, show a dialog informing the user about this and ask for a confirmation.
   * All the controls are set to their default values.
   * 
   * @param checkOnUnsavedChanges if true and if there are any unsaved changes, show a dialog informing the user about this and ask for a confirmation.
   */
  public void newObject(boolean checkOnUnsavedChanges) {
    if (checkOnUnsavedChanges  &&  !getUserConfirmationInCaseOfUnsavedChanges()) {
      return;
    }

    editPanel.newObject();
    
    editMode = EditMode.NEW;

    handleChanges(true, true);
    notifyListeners();
  }
  
  /**
   * Create the panel for the action buttons.
   */
  private void createActionButtonsPanel() {
    actionButtonsPanel = componentFactory.createHBox();
    actionButtonsPanel.setSpacing(30.0);
    actionButtonsPanel.setPadding(new Insets(12.0));
  }

  /**
   * Handle changes in any of the controls
   * <p>
   * If an implementation needs to do more in case of changes, it shall override this method and call this method.
   * It is up to the implementation to determine the order of handling changes.
   */
  protected void handleChanges(boolean valueChanged, boolean statusChanged) {
    updateActionButtonsPanel();
  }
  
  /**
   * Update the panel with the Add/Update and Cancel buttons.
   * <p>
   * 
   */
  protected void updateActionButtonsPanel() {
    actionButtonsPanel.getChildren().clear();
    
    // Determine whether there are any errors. If so get the error information provided by the first invalid control and show it in the statusTextArea.
    EditorComponent<?> firstInvalidComponent = editPanel.getFirstInvalidControl();
//    if (firstInvalidComponent != null) {
//      LOGGER.severe("First invalid object control: " + firstInvalidComponent.toString());
//    }
    String errorText = "";// "All is fine";
    if (firstInvalidComponent != null) {
      errorText = firstInvalidComponent.getErrorText();
      if (errorText == null) {  // If this is the case the firstInvalidComponent implementation has to be improved!!
        errorText = firstInvalidComponent.toString();
        LOGGER.severe("Component without providing a decent error text: " + firstInvalidComponent.toString());
      }
    }
    statusLabel.setText(errorText);
    actionButtonsPanel.getChildren().add(statusLabel);

    // Use a spacer pane to right align the buttons
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    actionButtonsPanel.getChildren().add(spacer);
    
    // Determine the edit status and update the editStatusLabel accordingly.
    EditStatus editStatus = determineEditStatus();
    editStatusLabel.setText(editStatus.getStatusIndicator());
    if (editStatus == EditStatus.INVALID) {
      editStatusLabel.setTooltip(new Tooltip("At least one of the controls has an invalid value"));
    }
    actionButtonsPanel.getChildren().add(editStatusLabel);
    
    if (editMode == EditMode.NEW) {
      // If the edit mode is NEW, there is always the ADD button, but it is only enabled if controls are valid.
      addButton.setDisable(!editPanel.isValid());
      actionButtonsPanel.getChildren().add(addButton);
    } else {
      // If the edit mode is EDIT, there is always the UPDATE button, but it is only enabled if the edit status is CHANGES (controls are all valid and at least one change)
        updateButton.setDisable(editStatus != EditStatus.CHANGES);
        actionButtonsPanel.getChildren().add(updateButton);
    }
    
    // Always add the NEW button
    if (buttonTypes.contains(NEW)) {
      actionButtonsPanel.getChildren().add(newButton);
    }
    
    // Always add the CANCEL button
    actionButtonsPanel.getChildren().add(cancelButton);
    
    if (!this.editStatus.equals(editStatus)) {
      notifyListeners();
      this.editStatus = editStatus;
    }
  }
  
  /**
   * Install any optional listeners for changes.
   * <p>
   * If an implementation needs to listen to other changes, this method can be overridden.
   */
  protected void installChangeListeners() {
  }
  
  
  /**
   * Update {@code object} with the values of the controls.
   * <p>
   * If the provided information is correct, the {@code object} is updated.
   * Otherwise, an Alert is shown with information about what is wrong.
   */
  protected void accept() {
    try {
      editPanel.accept();

      notifyListeners();
    } catch (EditorException e) {
      StringBuilder buf = new StringBuilder();
      for (String problem: e.getProblems()) {
        buf.append(problem);
        buf.append(NEW_LINE);
      }
      componentFactory.createErrorDialog("Problem in object details", buf.toString()).showAndWait();
    }
  }
  
  /**
   * Determine the edit status.
   * <p>
   * If any of the controls is invalid, the status is INVALID.
   * Otherwise the edit status depends on the edit mode.
   * If the edit mode is NEW the status is ADD, else the status is CHANGES if there are any changes in the controls, or NO_CHANGES.
   * 
   * @return the newly determined EditStatus.
   */
  protected EditStatus determineEditStatus() {
    if (editPanel.isValid()) {
      if (editMode == EditMode.NEW) {
        return EditStatus.ADD;
      } else {
        if (editPanel.isChanged()) {
          return EditStatus.CHANGES;
        } else {
          return EditStatus.NO_CHANGES;
        }
      }
    } else {
      return EditStatus.INVALID;
    }
  }
  
  /**
   * Add the object to the data list.
   * <p>
   * The object is filled with the values of the controls and then added to the list.
   * The editor switches to Edit mode.
   */
  protected void addObjectAction() {
    
    try {
      T object = editPanel.accept();

      addObjectMethod.accept(object);

      // calling setObject causes: all controls to be unchanged, all values shown in the preferred way
      setObject(object, false);

      handleChanges(true, true);
      notifyListeners();
    } catch (EditorException e) {
      StringBuilder buf = new StringBuilder();
      for (String problem: e.getProblems()) {
        buf.append(problem);
        buf.append(NEW_LINE);
      }
      componentFactory.createErrorDialog("Problem in object details", buf.toString()).showAndWait();
    }
  }

  /**
   * Discard any changes and close the window.
   * <p>
   * If there are any unsaved changes, show a dialog informing the user about this and ask for a confirmation.
   */
  protected void closeWindow() {
    if (!getUserConfirmationInCaseOfUnsavedChanges()) {
      return;
    }

    this.close();
  }
  
  /**
   * If there are unsaved changes, show a dialog informing the user about this and ask for a confirmation.
   * 
   * @return true if there are no unsaved changes, or the user has confirmed to continue and discard the changes. False otherwise.
   */
  protected boolean getUserConfirmationInCaseOfUnsavedChanges() {
    if (editPanel.isChanged()) {
      Optional<ButtonType> userChoice = componentFactory.createOkCancelConfirmationDialog("Unsaved changes", "There are unsaved changes in the editor, these will be lost if you continue.", "Do you want to continue?")
          .showAndWait();
      if (userChoice.isEmpty()  ||
          !userChoice.get().equals(ButtonType.OK)) {
            return false;
          }
    }
    
    return true;
  }
  
  /**
   * {@inheritDoc}
   */
  public final void addListener(InvalidationListener listener) {
    LOGGER.info("<=> " + listener);
    invalidationListeners.add(listener);    
  }

  /**
   * {@inheritDoc}
   */
  public final void removeListener(InvalidationListener listener) {
    LOGGER.info("<=> " + listener);
    invalidationListeners.remove(listener);    
  }

  public final void removeListeners() {
    LOGGER.info("=>");
    invalidationListeners.clear();
    LOGGER.info("<=");
  }
  
  /**
   * Notify the {@code invalidationListeners} that something has changed.
   */
  private void notifyListeners() {
    LOGGER.info("=>");
    for (InvalidationListener invalidationListener: invalidationListeners) {
      invalidationListener.invalidated(this);
    }
    LOGGER.info("<=");
  }

}


/**
 * This enum defines the operational modes of the Editor.
 */
enum EditMode {
  /**
   * Creating a new object.
   */
  NEW,

  /**
   * Editing an existing object.
   */
  EDIT
}

/**
 * This enum defines the status values for the editor, together with their indicator symbols.
 */
enum EditStatus {
  ADD("+"),         // Valid in NEW mode, so object can be added 
  INVALID("!"),     // Value of at least one control is invalid
  NO_CHANGES("="),  // Valid, but none of the values has changed
  CHANGES("≠");     // Valid and at least one control has changed
  
  private String statusIndicator;
  
  EditStatus(String statusIndicator) {
    this.statusIndicator = statusIndicator;
  }

  public String getStatusIndicator() {
    return statusIndicator;
  }
    
}


