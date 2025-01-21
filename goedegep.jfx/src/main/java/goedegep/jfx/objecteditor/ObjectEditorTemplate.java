package goedegep.jfx.objecteditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.objectcontrols.ObjectControlGroup;
import goedegep.jfx.objectcontrols.ObjectControlStatus;
import goedegep.jfx.objectcontrols.ObjectEditPanel;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * This class provides a template for an object editor and it provides some common functionality.
 * <p>
 * This class is the template class of a Template Design Pattern. An object editor can extend this class and overwrite the methods specific for editing that object.<br/>
 * The object being edited is typically part of a collection of objects or it is meant to be added to this collection.
 * <p>
 * Details
 * <p>
 * <b>Edit mode</b><br/>
 * The editor is always in one of the modes NEW or EDIT. Upon creation the editor is in NEW mode. In this mode all edit controls are filled with their default values (usually mostly empty).
 * Upon pressing the 'Add' button, an object of type T is created, it is filled with the values from the controls, it is added to the collection and the editor switches to the EDIT mode.<br/>
 * Upon calling {@code setObject()} with a non null value the editor switches to the EDIT mode, otherwise it switches to the NEW mode.
 * <p>
 * <b>Layout overview</b><br/>
 * From top to bottom:
 * <ul>
 *  <li>
 *   Title bar, shows the Editor Title.
 *  </li>
 *  <li>
 *   The main editor panel.
 *  </li>
 *  <li>
 *   A panel with the action buttons.<br/>
 *   The items in this panel are right aligned.
 *   By default it contains:
 *   <ul>
 *    <li>
 *     Edit status indicator:
 *     <ul>
 *      <li>
 *       '+' - in NEW mode and the input is valid, so you can add the object
 *      </li>
 *      <li>
 *       '!' - At least one control is invalid
 *      </li>
 *      <li>
 *       '=' - The controls are valid, but none of the values has changed
 *      </li>
 *      <li>
 *       '≠' - in UPDATE mode, the controls are valid and there are changes.
 *      </li>
 *     </ul>
 *    </li>
 *    <li>
 *     Cancel button: to quit the editor without making changes. This button is always enabled.
 *    </li>
 *    <li>
 *     Add/Update button:<br/>
 *     In NEW mode this is an 'Add' button, which is only enabled if all controls are valid.<br/>
 *     In UPDATE mode this is an 'Update' button, which is only enabled if all controls are valid and at least one control has changed. 
 *    </li>
 *    <li>
 *     New button: to start editing a new object. All controls are cleared (or filled with a default value) and the editor switches to NEW mode. This button is always enabled.
 *    </li>
 *   </ul>
 *  </li>
 * </ul>
 * <p>
 * <b>Customization</b><br/>
 * A {@code CustomizationFx} is passed to the constructor as the first argument.
 * <p>
 * <b>Configuration</b><br/>
 * The texts and tooltip texts of the action buttons can be set.
 * 
 * 
 * 
 * @param <T> The object type being edited
 */
public abstract class ObjectEditorTemplate<T> extends JfxStage implements Observable  {
  private static Logger LOGGER = Logger.getLogger(ObjectEditorTemplate.class.getName());
  private static final String NEW_LINE = System.getProperty("line.separator");

  /**
   * A pre-defined {@link ButtonType} that displays "Add" (in new mode) or "Update" (in edit mode).
   */
  public static final ButtonType ADD_OR_UPDATE = new ButtonType("ObjectEditor.addorupdate.button");
  
  /**
   * A pre-defined {@link ButtonType} that displays "New" to start editing a new object.
   */
  public static final ButtonType NEW = new ButtonType("ObjectEditor.new.button");
  
  /**
   * An {@code ObjectControlGroup} containing all {@code ObjectControl}s.
   * <p>
   * Any ObjectControl you create shall be added to this group.
   */
  protected ObjectControlGroup objectControlsGroup;
  
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
   * Indicates whether we're creating new object, or editing an existing object.
   */
  protected EditMode editMode = EditMode.NEW;
  
  /**
   * Main panel of the editor
   */
  private VBox rootPane = null;
  
  private List<ButtonType> buttonTypes;
    
  /**
   * Panel for the action buttons (Add/Update and New).
   */
  protected HBox actionButtonsPanel;
  
  /**
   * The object being edited. This value is null in NEW mode.
   */
  protected T object = null;
  
  /**
   * Callback method for adding a new object
   */
  private Consumer<T> addObjectMethod;
  
  /**
   * Ignore changes
   */
  protected boolean ignoreChanges;
  
  /**
   * The invalidation listeners
   */
  protected List<InvalidationListener> invalidationListeners = new ArrayList<>();

  
  /**
   * Constructor called by your constructor.
   * 
   * Your constructor will typically have a parameter for the data structure to which new items shall be added. Here referred to as dataCollection of type T.
   * Your constructor looks like this:
   * 
   *   this.dataCollection = dataCollection;
   *
   * @param customization
   * @param title
   */
  protected ObjectEditorTemplate(CustomizationFx customization, String title, Consumer<T> addObjectMethod) {
    super(customization, title);
    
    this.addObjectMethod = addObjectMethod;
    
    objectControlsGroup = new ObjectControlGroup();
    buttonTypes = new ArrayList<>();
    buttonTypes.add(ADD_OR_UPDATE);
    buttonTypes.add(NEW);
    buttonTypes.add(ButtonType.CANCEL);
  }
    
  /**
   * Set the explanation text.
   * 
   * @param explanatoryText the explanation text.
   */
  public void setExplanation(String explanatoryText) {
    TextArea explanationText = componentFactory.createTextArea(explanatoryText);
    explanationText.setMinHeight(40);
    explanationText.setPrefHeight(40);
    rootPane.getChildren().addFirst(explanationText);
  }
  
  public List<ButtonType> getButtonTypes() {
    return buttonTypes;
  }
  
  /**
   * Perform editor initialization.
   * 
   * @return this
   */
  protected void performInitialization() {
    configureEditor();
    createObject();
        
    createControls();
    fillControlsWithDefaultValues();
    
    rootPane = componentFactory.createVBox();
    createEditPanel(rootPane);
    createActionButtonsPanel();
    rootPane.getChildren().add(actionButtonsPanel);
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(rootPane);

    setScene(new Scene(scrollPane));
    Double windowHeight = getWindowHeight();
    if (windowHeight != null) {
      setMaxHeight(windowHeight);
    }
    Double windowWidth = getWindowWidth();
    if (windowWidth != null) {
      setMaxWidth(windowWidth);
    }
        
    installChangeListeners();
    ignoreChanges = false;
    
    handleChanges();
  }

  /**
   * Get the object being edited.
   * @return the object being edited.
   */
  public T getObject() {
    return object;
  }
  
  /**
   * Start editing an object.
   * <p>
   * If there are any unsaved changes, show a dialog informing the user about this and ask for a confirmation.
   * {@code object} is set to the specified value.
   * All the controls are cleared and then filled with the information from the {@code object}.
   * 
   * @param object the value to be edited.
   */
  public void setObject(T object) {
    setObject(object, true, false);
  }

  /**
   * Start editing an object, or clear the editor for starting to create a new object.
   * <p>
   * If there are any unsaved changes while checkOnUnsavedChanges is set, show a dialog informing the user about this and ask for a confirmation.
   * {@code object} is set to the specified value.
   * All the controls are cleared and then, if {@code object} isn't null, filled with the information from the {@code object}.
   * 
   * @param object the value to be edited, or null to start editing a new object.
   * @param checkOnUnsavedChanges if true and if there are any unsaved changes, show a dialog informing the user about this and ask for a confirmation.
   */
  public void setObject(T object, boolean checkOnUnsavedChanges, boolean newObject) {
    Objects.requireNonNull(object, "object may not be null (use newObject()");
    
    if (checkOnUnsavedChanges  &&  !getUserConfirmationInCaseOfUnsavedChanges()) {
      return;
    }

    this.object = object;
    ignoreChanges = true;

    fillControlsWithDefaultValues();
    
    fillControlsFromObject();
    
    editMode = newObject ? EditMode.NEW : EditMode.EDIT;

    ignoreChanges = false;

    handleChanges();
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

    createObject();
    ignoreChanges = true;

    fillControlsWithDefaultValues();
    
    editMode = EditMode.NEW;
    ignoreChanges = false;

    handleChanges();
    notifyListeners();
  }
  
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
   * Create the GUI controls and add them to the {@code objectControlsGroup}
   */
  protected abstract void createControls();
    
  /**
   * Create the actual edit panel.
   */
  protected abstract void createEditPanel(VBox rootPane);
  
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
   * This default implementation only updates the action buttons.
   */
  protected void handleChanges() {
    if (ignoreChanges) {
      return;
    }
    
    updateActionButtonsPanel();
  }
  
  /**
   * Update the panel with the Add/Update and Cancel buttons.
   */
  protected void updateActionButtonsPanel() {
    actionButtonsPanel.getChildren().clear();
    
    TextArea statusTextArea = componentFactory.createTextArea();
    statusTextArea.setEditable(false);
    statusTextArea.setMaxHeight(30);
    ObjectControlStatus objectControl = objectControlsGroup.getFirstInvalidControl();
    LOGGER.info("First invalid object control: " + (objectControl != null ? objectControl.toString() : "All controls are valid"));
    if (objectControl != null) {
      String errorText = objectControl.getErrorText();
      if (errorText == null) {
        errorText = objectControl.toString();
      }
      LOGGER.info("errorText=" + errorText);
      statusTextArea.setText(errorText);
    } else {
      statusTextArea.setText("All is fine");
    }
    actionButtonsPanel.getChildren().add(statusTextArea);

    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    actionButtonsPanel.getChildren().add(spacer);
    
    Button button;
    
    EditStatus editStatus = determineEditStatus();
    Label editStatusLabel = componentFactory.createStrongLabel(editStatus.getStatusIndicator());
    String fontName = editStatusLabel.getFont().getName();
    editStatusLabel.setFont(new Font(fontName, 22));
    if (editStatus == EditStatus.INVALID) {
      editStatusLabel.setTooltip(new Tooltip("At least one of the controls has an invalid value"));
    }
    actionButtonsPanel.getChildren().add(editStatusLabel);
    
    Button addOrUpdateButton;
    if (editMode == EditMode.NEW) {
      // Add button
      addOrUpdateButton = componentFactory.createButton(addObjectText, addObjectTooltipText);
      addOrUpdateButton.setOnAction(e -> addObjectAction());
      if (!objectControlsGroup.isValid()) {
        addOrUpdateButton.setDisable(true);
      }
    } else {
      // Update button
      addOrUpdateButton = componentFactory.createButton(updateObjectText, updateObjectTooltipText);
      addOrUpdateButton.setOnAction(e -> updateObject());
      if (editStatus != EditStatus.CHANGES) {
        addOrUpdateButton.setDisable(true);
      }
    }
    actionButtonsPanel.getChildren().add(addOrUpdateButton);
    
    // New button
    if (buttonTypes.contains(NEW)) {
      button = componentFactory.createButton(newObjectText, newObjectTooltipText);
      button.setOnAction(e -> newObject(true));
      actionButtonsPanel.getChildren().add(button);
    }
    
    // Cancel button
    button = componentFactory.createButton("Cancel", "Discard any changes and close the editor");
    button.setOnAction(e -> closeWindow());
    actionButtonsPanel.getChildren().add(button);
  }
  
  /**
   * Install listeners for changes in the controls.
   * <p>
   * This implementation installs a listener on the {@code objectControlsGroup}.<br/>
   * If a client has any other controls, this method shall be overridden.
   */
  protected void installChangeListeners() {
    objectControlsGroup.addListener(observable -> handleChanges());
  }
  
  /**
   * Add the object to the data list.
   * <p>
   * The object is filled with the values of the controls and then added to the list.
   * The editor switches to Edit mode.
   */
  protected void addObjectAction() {
    
    try {
      updateObjectFromControls();
      
      addObjectMethod.accept(object);
//      addObjectToCollection();
      
      // calling setObject causes: all controls to be unchanged, all values shown in the preferred way
      setObject(object, false, false);
      
      handleChanges();
      notifyListeners();
    } catch (ObjectEditorException e) {
      StringBuilder buf = new StringBuilder();
      for (String problem: e.getProblems()) {
        buf.append(problem);
        buf.append(NEW_LINE);
      }
      componentFactory.createErrorDialog("Problem in object details", buf.toString()).showAndWait();
    }
  }
  
  /**
   * Create a new instance of type T
   */
  protected abstract void createObject();
  
//  /**
//   * Add the new object to the collection.
//   */
//  protected abstract void addObjectToCollection();
  
  /**
   * Update {@code object} with the values of the controls.
   * <p>
   * If the provided information is correct, the {@code object} is updated.
   * Otherwise, an Alert is shown with information about what is wrong.
   */
  protected void updateObject() {
    try {
      updateObjectFromControls();
      
//      setObject(object, false);
      notifyListeners();
    } catch (ObjectEditorException e) {
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
   * Otherwise, the status is CHANGES if there are any changes in the controls, or NO_CHANGES.
   * 
   * @return the newly determined EditStatus.
   */
  protected EditStatus determineEditStatus() {
    if (objectControlsGroup.isValid()) {
      if (editMode == EditMode.NEW) {
        return EditStatus.ADD;
      } else {
        if (changesInInput()) {
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
   * Fill the controls with default values.
   */
  protected abstract void fillControlsWithDefaultValues();

  /**
   * Fill the controls with the value of the object.
   */
  protected abstract void fillControlsFromObject();
  
  /**
   * Update object from controls.
   */
  protected abstract void updateObjectFromControls() throws ObjectEditorException;
  
  /**
   * Check whether there are any changes in the controls.
   * <p>
   * In EDIT mode there are changes if any control has a different value than the current value of {@code object}.
   * In NEW mode this method throws an exception.
   * 
   * @return true if there are any changes in the controls, false otherwise.
   */
  protected boolean changesInInput() {        
    return objectControlsGroup.isAnyObjectChanged();
  }
  
  /**
   * If there are unsaved changes, show a dialog informing the user about this and ask for a confirmation.
   * 
   * @return true if there are no unsaved changes, or the user has confirmed to continue and discard the changes. False otherwise.
   */
  protected boolean getUserConfirmationInCaseOfUnsavedChanges() {
    if (changesInInput()) {
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
   * Get the requested window height.
   * 
   * @return the requested window height, or null if the default height is to be used.
   */
  protected Double getWindowHeight() {
    return 1400.0;
  }
  
  /**
   * Get the requested window width.
   * 
   * @return the requested window width, or null if the default width is to be used.
   */
  protected Double getWindowWidth() {
    return 1200.0;
  }
  
  public final void addListener(InvalidationListener listener) {
    LOGGER.info("<=> " + listener);
    invalidationListeners.add(listener);    
  }

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


