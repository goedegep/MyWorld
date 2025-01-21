package goedegep.jfx.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.util.listener.ValueAndOrStatusChangeListener;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public abstract class EditorMultiControlTemplate<T> extends EditorComponentAbstract<T> implements EditorMultiControl<T> {
  private static final Logger         LOGGER = Logger.getLogger(EditorMultiControlTemplate.class.getName());
  
  
  /**
   * A list of {@link EditorComponent}s determining the status of this panel.
   */
  private List<EditorComponent<?>> editorComponents = new ArrayList<>();

  /**
   * The first component in the list that is invalid.
   */
  private EditorComponent<?> firstInvalidEditorComponent;
  
//  /**
//   * An {@code InvalidationListener} to listen for changes in any of the controls in the group.
//   */
//  private InvalidationListener invalidationListener;

  /**
   * A {@code ValueAndOrStatusChangeListener} to listen for changes in any of the {@code editorComponents}.
   */
  private ValueAndOrStatusChangeListener valueAndOrStatusChangeListener;
  
  boolean initializationComplete = false;

  
  public EditorMultiControlTemplate(CustomizationFx customization, boolean optional) {
    super(customization, optional);

//    invalidationListener = (observable) -> handleChanges(observable);
    valueAndOrStatusChangeListener = (valueChanged, statusChanged) -> handleChanges(valueChanged, statusChanged);
  }
  
  public EditorComponent<?> getFirstInvalidControl() {
    return firstInvalidEditorComponent;
  }
  
  /**
   * Initialize the control.
   */
  public void performInitialization() {
    createControls();
//    fillControlsWithDefaultValues();
    
    installChangeListeners();
//    ignoreChanges = false;
    
    newObject();
    
    initializationComplete = true;
    handleChanges(true, true); // during initialization changes are ignored
  }
  
  /**
   * Install any optional listeners for changes.
   * <p>
   * If an implementation needs to listen to other changes, this method can be overridden.
   */
  protected void installChangeListeners() {
  }
  
  /**
   * Handle changes in any of the observed editor components.
   *  
   * @param observable the component that was changed.
   */
  protected final void handleChanges(boolean valueChanged, boolean statusChanged) {
    if (!initializationComplete) {
      return;
    }
    
    EditorComponent<?> newfirstInvalidEditorComponent = determineValidity();
    boolean newValid = newfirstInvalidEditorComponent == null;
    boolean newAnyEditorComponentFilledIn = determineFilledIn();
    boolean newAnyEditorComponentChanged = determineAnyObjectChanged();
    
//    boolean statusChanged = false;
//    if (newValid != valid  ||
//        newAnyEditorComponentFilledIn != filledIn  ||
//            newAnyEditorComponentChanged != changed  ||
//        !Objects.equals(newfirstInvalidEditorComponent, firstInvalidEditorComponent)) {
//      statusChanged = true;
//    }
    
    firstInvalidEditorComponent = newfirstInvalidEditorComponent;
    valid = newValid;
    filledIn = newAnyEditorComponentFilledIn;
    changed = newAnyEditorComponentChanged;
    
//    if (statusChanged) {
//      Observable observable = null;
//
//      if (observables.length > 0) {
//        observable = observables[0];
//      }
//
//      notifyListeners(observable);
//    }

    notifyValueAndOrStatusChangeListeners(true, statusChanged);
  }
  
  /**
   * Check whether any {@code EditorComponent} is filled-in or not.
   * 
   * @return {@code true} if at least one component is filled in, {@code false} otherwise.
   */
  private boolean determineFilledIn() {
    LOGGER.info("=>");

    boolean isFilledIn = false;

    for (EditorComponent<?> editorComponent: editorComponents) {
      LOGGER.info("editorComponent: " + editorComponent.getId());
      if (editorComponent.isFilledIn()) {
        isFilledIn = true;
        LOGGER.info("Is filled");
        break;
      }
    }

    LOGGER.info("<= " + isFilledIn);
    return isFilledIn;
  }

  /**
   * Determine whether all {@code EditorComponent}s are valid or not.
   * 
   * @return the first invalid editor component, or null if all components are valid.
   */
  public EditorComponent<?> determineValidity() {
    LOGGER.info("=>");

    EditorComponent<?> invalidEditorComponent = null;

    for (EditorComponent<?> editorComponent: editorComponents) {
      if (!editorComponent.isValid()) {
        if (editorComponent instanceof EditorMultiControl editorMultiControl) {
          invalidEditorComponent = editorMultiControl.determineValidity();
        } else {
          invalidEditorComponent = editorComponent;
        }
        break;
      }
    }

    LOGGER.info("<= " + invalidEditorComponent);
    return invalidEditorComponent;
  }
  /**
   * Check whether any {@code EditorComponent} has changed.
   * 
   * @return {@code true} if at least one component has changed, {@code false} otherwise.
   */
  private boolean determineAnyObjectChanged() {
    LOGGER.info("=>");

    boolean isChanged = false;

    for (EditorComponent<?> editorComponent: editorComponents) {
      if (editorComponent.isChanged()) {
        isChanged = true;
        LOGGER.info("First changed editorComponent: " + editorComponent.toString());
        break;
      }
    }

    LOGGER.info("<= " + isChanged);
    return isChanged;
  }

  /**
   * Create the actual edit panel.
   */
  protected abstract void createEditPanel();
  
  /**
   * Add one or more {@code EditorComponent}s for which the status is to be maintained.
   * 
   * @param objectControls The {@code EditorComponent}s for which to maintain the status.
   */
  protected void registerEditorComponents(EditorComponent<?>... editorComponents) {
    
    for (EditorComponent<?> editorComponent: editorComponents) {
      editorComponent.addValueAndOrStatusChangeListener(valueAndOrStatusChangeListener);
//      editorComponent.addListener(invalidationListener);
      this.editorComponents.add(editorComponent);
    }
    
    handleChanges(true, true);
  }
  
  /**
   * Remove one or more {@code EditorComponent}s for which the status is no longer to be maintained.
   * 
   * @param objectControls The {@code EditorComponent}s to be removed.
   */
  protected void unregisterEditorComponents(EditorComponent<?>... editorComponents) {
    
    for (EditorComponent<?> editorComponent: editorComponents) {
      editorComponent.removeValueAndOrStatusChangeListener(valueAndOrStatusChangeListener);
//      editorComponent.removeListener(invalidationListener);
      this.editorComponents.remove(editorComponent);
    }
    
    handleChanges(true, true);
  }
  
  protected abstract void fillObjectFromControls(T object, boolean getCurrentValue) throws EditorException;
  
  @Override
  public T getCurrentValue() throws EditorException {
    T t = createObject();
    fillObjectFromControls(t, true);
    
    return t;
  }
  
  @Override
  public final void setObject(T newValue) {
    LOGGER.info("=> " + newValue);
    
    if (newValue != value) {
      value = newValue;
    }
    fillControlsFromObject();
    referenceValue = newValue;
    
    LOGGER.info("<=");
  }
  
  @Override
  public T accept() throws EditorException {
      fillObjectFromControls(value, false);
      
      setObject(value);
      
      return value;
  }
  
  public void newObject() {
    setObject(createObject());
  }
  
  protected T createObject() {
    throw new UnsupportedOperationException();
  }


}
