package goedegep.jfx.objectcontrols;

import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objecteditor.ObjectEditorException;
import goedegep.util.PgUtilities;

/**
 * This class provides a template for a number of ObjectControls representing an Object.
 * It is related to the ObjectEditPanelTemplate, but here there is not GUI panel, only a number
 * of ObjectControls which have to be shown by e.g. an ObjectEditor or ObjectEditPanel.
 * 
 * @param <T>
 */
public abstract class ObjectControlAggregationTemplate<T> {
  private static Logger LOGGER = Logger.getLogger(ObjectControlAggregationTemplate.class.getName());
  private static final String NEW_LINE = System.getProperty("line.separator");
  
  /**
   * The GUI customization.
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
   * Constructor called by your constructor.
   *
   * @param customization The GUI customization.
   */
  public ObjectControlAggregationTemplate(CustomizationFx customization) {
    this.customization = customization;
    componentFactory = customization.getComponentFactoryFx();
    objectControlsGroup = new ObjectControlGroup();
  }
  
  /**
   * Perform initialization.
   * 
   * @return this
   */
  public ObjectControlAggregationTemplate<T> runEditor() {
    createControls();
    fillControlsWithDefaultValues();
    
    ignoreChanges = false;
    
    return this;
  }
  
  public T getValue() {
    return object;
  }
  
  public final void setObject(T newValue) {
    LOGGER.info("=> " + newValue);
        
    boolean changed = !PgUtilities.equals(newValue, getValue());
    if (changed) {
      object = newValue;
    }
    fillControlsFromObject();
    referenceValue = newValue;
        
    LOGGER.info("<=");
  }
  
  public ObjectControlGroup getObjectControlsGroup() {
    return objectControlsGroup;
  }

  /**
   * Create the GUI controls and add them to the {@code objectControlsGroup}
   */
  protected abstract void createControls();

  /**
   * Fill the controls with default values.
   */
  protected abstract void fillControlsWithDefaultValues();
  

  /**
   * Fill the controls with the value of the object.
   */
  protected abstract void fillControlsFromObject();

  /**
   * Create a new instance of type T
   * 
   * @return the newly created object.
   */
  public abstract void createObject();

  /**
   * Update {@code object} with the values of the controls.
   * <p>
   * If the provided information is correct, the {@code object} is updated.
   * Otherwise, an Alert is shown with information about what is wrong.
   */
  protected void updateObject() {
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
   * Update object from controls.
   */
  protected abstract void updateObjectFromControls() throws ObjectEditorException;
  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
