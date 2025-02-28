package goedegep.jfx.objectcontrols;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objecteditor.ObjectEditorException;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * This class provides a template for an object edit panel and it provides some common functionality.
 * <p>
 * This class is the template class of a Template Design Pattern. An object edit panel can extend this class and overwrite the methods specific for editing that object.<br/>
 * An object edit panel is meant to be used to edit a sub class of the class being edited by an ObjectEditor (a class extending the {@code ObjectEditorTemplate}).<br/>
 * So this class implements the {@code ObjectControl} interface (by extending {@code ObjectControlTemplate} and it follows the same strategy as the {@code ObjectEditorTemplate}.
 * <p>
 * Details
 * <p>
 * <b>Edit mode</b><br/>
 * This is a control, not an editor, so the Edit mode as defined in the {@code ObjectEditorTemplate} is not applicable here.
 * <p>
 * <b>Layout overview</b><br/>
 * The layout only consist of an edit panel, which is the control.
 * <p>
 * <b>Customization</b><br/>
 * A {@code CustomizationFx} is passed to the constructor as the first argument.
 * 
 * <h3>ObjectControlGroup</h3>
 * This class can, to a large extend, be seen as a single ObjectControl. However, in that case it is not possible for the editor, using this panel, to indicate the first invalid control (other than marking the
 * whole panel invalid). Therefore this class uses an ObjectControlGroup.
 * TODO so can/shall it still extend ObjectControlTemplate??
 * 
 * @param <T> The object type being edited
 */
public abstract class ObjectEditPanelTemplate<T> extends ObjectControlAbstract<T> implements ObjectEditPanel<T> {
  private static Logger LOGGER = Logger.getLogger(ObjectEditPanelTemplate.class.getName());
  
  /**
   * The GUI customization ToDo move to ObjectEditPanelTemplate
   */
  protected CustomizationFx customization;
  
  /**
   * Factory for creating GUI components.
   */
  protected ComponentFactoryFx componentFactory;
  
  /**
   * An {@code ObjectControlGroup} containing all {@code ObjectControl}s.
   * <p>
   * Any ObjectControl you create shall be added to this group.
   */
  protected ObjectControlGroup objectControlsGroup;
  
  /**
   * The object being edited.
   */
  protected T object = null;
  
  /**
   * Reference value, used to check on changes.
   */
  protected T referenceValue = null;
  
  /**
   * Id
   */
  private String id;
  
  /**
   * Ignore changes
   */
  protected boolean ignoreChanges;
  
  /**
   *  A list of {@code ObjectControlStatus} elements.
   */
  private List<ObjectControl> objectControls = new ArrayList<>();

  
  /**
   * Constructor called by your constructor.
   *
   * @param customization The GUI customization.
   */
  public ObjectEditPanelTemplate(CustomizationFx customization) {
    this.customization = customization;
    componentFactory = customization.getComponentFactoryFx();
    objectControlsGroup = new ObjectControlGroup();
  }
  
  /**
   * Create and show the editor.
   * 
   * @return this
   */
  public void performInitialization() {
    createControls();
    fillControlsWithDefaultValues();
    
    VBox rootPane = componentFactory.createVBox();
    createEditPanel();
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(rootPane);
    
    installChangeListeners();
    ignoreChanges = false;
    
    handleChanges();
  }
  
  public T getValue() {
    return object;
  }
  
  @Override
  public final void setObject(T newValue) {
    LOGGER.info("=> " + newValue);
    
    if (newValue != object) {
      object = newValue;
    }
    fillControlsFromObject();
    referenceValue = newValue;
    
    LOGGER.info("<=");
  }
  
  public abstract Node getControl();
  
  public ObjectControlGroup getObjectControlsGroup() {
    return objectControlsGroup;
  }

  /**
   * Create the GUI controls and add them to the {@code objectControlsGroup}
   */
   protected abstract void createControls();
  
  /**
   * Create the actual edit panel.
   */
  protected abstract void createEditPanel();

//  /**
//   * Create a new instance of type T
//   * 
//   * @return the newly created object.
//   */
//  public abstract void createObject();
  

  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  /**
   * Update {@code object} with the values of the controls.
   * <p>
   * If the provided information is correct, the {@code object} is updated.
   * Otherwise, an Alert is shown with information about what is wrong.
   */
  public void updateObject() {
    try {
      updateObjectFromControls();
      
      setObject(object);
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
   * Install listeners for changes in the controls.
   * <p>
   * This implementation installs a listener on the {@code objectControlsGroup}.<br/>
   * If a client has any other controls, this method shall be overridden.
   */
  protected void installChangeListeners() {
    objectControlsGroup.addListener(observable -> handleChanges());
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
  }
  
  
  /**
   * Get a text which explains why the control has no valid value.
   * 
   * @return a text which explains why the control has no valid value.
   */
  public String getErrorText() {
    ObjectControlStatus objectControlStatus = objectControlsGroup.getFirstInvalidControl();
    
    if (objectControlStatus != null) {
      return objectControlStatus.getErrorText();
    } else {
      return null;
    }
  }

  /**
   * {@inheritDoc}
   */
  public List<ObjectControl> getObjectControls() {
    return objectControls;
  }
}
