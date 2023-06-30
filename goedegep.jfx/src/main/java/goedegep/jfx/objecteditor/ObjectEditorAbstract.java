package goedegep.jfx.objecteditor;

import java.util.Optional;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.objectcontrols.ObjectControlGroup;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * This class provides a template for an object editor and it provides some common functionality.
 * <p>
 *
 */
public abstract class ObjectEditorAbstract<T> extends JfxStage {
  private static Logger LOGGER = Logger.getLogger(ObjectEditorAbstract.class.getName());
  
  
  private ComponentFactoryFx componentFactory;
  
  protected ObjectControlGroup objectControlsGroup;
  
  private String addObjectText = "Add object";
  private String addObjectTooltipText = "Add the object to the collection";
  private String updateObjectText = "Update object";
  private String updateObjectTooltipText = "Update the current object";
  private String newObjectText = "New";
  private String newObjectTooltipText = "Clear controls to start creating new InquiryData";
  
  /**
   * Indicates whether we're creating new InquiryData, or editing existing InquiryData.
   */
  protected EditMode editMode = EditMode.NEW;
  
  /**
   * Panel for the add/update and new buttons.
   */
  protected HBox addUpdateAndNewButtonsPanel;
  
  protected T object;

  
  /**
   * Constructor called by your constructor.
   * 
   * Your constructor will typically have a parameter for the data structure to which new items shall be added. Here referred to as dataList of type T.
   * Your constructor looks like this:
   * 
   *   this.dataList = dataList;
   *   componentFactory = customization.getComponentFactoryFx();
   *   
   *   createControls();
   *   createGUI();
   *   
   *   objectControlGroup.addListener((observable) -> updateAddUpdateAndNewButtonsPanel());

   *   setXXX(null, false);
   * 
   * @param customization
   * @param title
   */
  public ObjectEditorAbstract(CustomizationFx customization, String title) {
    super(title, customization);
    
    componentFactory = customization.getComponentFactoryFx();
    
    objectControlsGroup = new ObjectControlGroup();
    addUpdateAndNewButtonsPanel = componentFactory.createHBox();
    addUpdateAndNewButtonsPanel.setSpacing(30.0);
    addUpdateAndNewButtonsPanel.setPadding(new Insets(12.0));
  }
  
  public void setAddObjectTexts(String buttonText, String buttonTooltipText) {
    addObjectText = buttonText;
    addObjectTooltipText = buttonTooltipText;
  }
  
  public void setUpdateObjectTexts(String buttonText, String buttonTooltipText) {
    updateObjectText = buttonText;
    updateObjectTooltipText = buttonTooltipText;
  }
  
  public void setNewObjectTexts(String buttonText, String buttonTooltipText) {
    newObjectText = buttonText;
    newObjectTooltipText = buttonTooltipText;
  }
  
  /**
   * Start editing an object.
   * <p>
   * If there are any unsaved changes, show a dialog informing the user about this and ask for a confirmation.
   * {@code object} is set to the specified value.
   * All the controls are cleared then filled with the information from the {@code object}.
   * 
   * @param object the value to be edited.
   */
  public void setObject(T object) {
    setObject(object, true);
  }

  /**
   * Start editing an object.
   * <p>
   * If there are any unsaved changes while checkOnUnsavedChanges is set, show a dialog informing the user about this and ask for a confirmation.
   * {@code object} is set to the specified value.
   * All the controls are cleared then filled with the information from the {@code object}.
   * 
   * @param object the value to be edited.
   * @param checkOnUnsavedChanges if true and if there are any unsaved changes, show a dialog informing the user about this and ask for a confirmation.
   */
  public void setObject(T object, boolean checkOnUnsavedChanges) {
    if (checkOnUnsavedChanges  &&  !getUserConfirmationInCaseOfUnsavedChanges()) {
      return;
    }

    this.object = object;

    fillControlsFromObject();

    editMode = object != null ? EditMode.EDIT : EditMode.NEW;
    updateAddUpdateAndNewButtonsPanel();
  }

  /**
   * Create the GUI controls and add them to a ControlGroup
   */
  protected void createControls() {
    LOGGER.severe("You did not override this method");
  }
  
  /**
   * Layout the GUI controls
   */
  protected void createGUI() {
    LOGGER.severe("You did not override this method");
  }
  
  /**
   * Update the panel with the Add/Update and Cancel buttons.
   */
  protected void updateAddUpdateAndNewButtonsPanel() {
    addUpdateAndNewButtonsPanel.getChildren().clear();

    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    addUpdateAndNewButtonsPanel.getChildren().add(spacer);
    
    Button button;
    
    EditStatus editStatus = determineEditStatus();
    Label editStatusLabel = componentFactory.createLabel(editStatus.getStatusIndicator());
    if (editStatus == EditStatus.INVALID) {
      editStatusLabel.setTooltip(new Tooltip("At least one of the controls has an invalid value"));
    }
    addUpdateAndNewButtonsPanel.getChildren().add(editStatusLabel);
    
    Button addOrUpdateButton;
    if (editMode == EditMode.NEW) {
      addOrUpdateButton = componentFactory.createButton(addObjectText, addObjectTooltipText);
      addOrUpdateButton.setOnAction(e -> addObject());
      if (!objectControlsGroup.getIsValid()) {
        addOrUpdateButton.setDisable(true);
      }
    } else {
      addOrUpdateButton = componentFactory.createButton(updateObjectText, updateObjectTooltipText);
      addOrUpdateButton.setOnAction(e -> updateObject());
      if (editStatus != EditStatus.CHANGES) {
        addOrUpdateButton.setDisable(true);
      }
    }
    addUpdateAndNewButtonsPanel.getChildren().add(addOrUpdateButton);
    
    button = componentFactory.createButton(newObjectText, newObjectTooltipText);
    button.setOnAction(e -> setObject(null, true));
    addUpdateAndNewButtonsPanel.getChildren().add(button);
  }
  
  /**
   * Add a new object to the data list.
   * <p>
   * A new object is created and filled with the values of the controls.
   * This new object is then added to the list.
   * {@code object} is set to this new object and the editor switches to Edit mode.
   */
  protected void addObject() {
    LOGGER.severe("You did not override this method");
  }
  
  /**
   * Update {@code object} with the values of the controls.
   */
  private void updateObject() {
    updateObjectFromControls();
    
    setObject(object, false);
  }
  
  /**
   * Determine the edit status.
   * <p>
   * If any of the controls is invalid, the status is INVALID.
   * Otherwise, the status is CHANGES if there are any changes in the controls, or NO_CHANGES.
   * 
   * @return the newly determined EditStatus.
   */
  private EditStatus determineEditStatus() {
    if (objectControlsGroup.getIsValid()) {
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
  
//  /**
//   * Handle changes in any of the controls.
//   */
//  protected void handleChanges() {
//    LOGGER.severe("You did not override this method");
//  }
  
  /**
   * Fill the controls with the value of the object.
   */
  protected void fillControlsFromObject() {
    LOGGER.severe("You did not override this method");
  }
  
  /**
   * Update object from controls.
   */
  protected void updateObjectFromControls() {
    LOGGER.severe("You did not override this method");
  }
  
  /**
   * Check whether there are any changes in the controls.
   * <p>
   * In EDIT mode there are changes if any control has a different value than the current value of {@code inquiryData}.
   * In NEW mode there are changes if any control is filled in.
   * 
   * @return
   */
  protected boolean changesInInput() {
    LOGGER.severe("You did not override this method");
    return false;
  }
  
  /**
   * If there are unsaved changes, show a dialog informing the user about this and ask for a confirmation.
   * 
   * @return true if there are no unsaved changes, or the user has confirmed to continue and discard the changes. False otherwise.
   */
  private boolean getUserConfirmationInCaseOfUnsavedChanges() {
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
}


enum EditStatus {
  ADD("+"),         // Valid in NEW mode, so object can be added.    
  INVALID("!"),     // Value of controls is invalid - !
  NO_CHANGES("="),  // Valid, but none of the values has changed - =
  CHANGES("*");     // Valid and there are changes - *
  
  private String statusIndicator;
  
  EditStatus(String statusIndicator) {
    this.statusIndicator = statusIndicator;
  }

  public String getStatusIndicator() {
    return statusIndicator;
  }
    
}


