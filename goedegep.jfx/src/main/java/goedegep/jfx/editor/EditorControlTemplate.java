package goedegep.jfx.editor;

import java.util.Objects;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlFileSelecter;
import javafx.scene.Node;

/**
 * This class is a Template for creating an {@code EditorControl}.
 * 
 * @param <T> the type handled by the control.
 */
public abstract class EditorControlTemplate<T> extends EditorComponentAbstract<T> implements EditorControl<T> {
  private static final Logger         LOGGER = Logger.getLogger(EditorControlTemplate.class.getName());

  /**
   * The current value (as known by the client), initially set to {@code null}.
   */
  protected T value = null;
  
  /**
   * Reference value to check for changes, initially set to {@code value}.
   */
  protected T referenceValue = value;
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization
   * @param optional indication of whether the value is optional or not.
   */
  protected EditorControlTemplate(CustomizationFx customization, String id, boolean optional) {
    super(customization, id, optional);
  }
  
  /**
   * Initialize the control.
   */
  public void performInitialization() {
    createControls();
    fillControlsWithDefaultValues();    
  }
  
  protected abstract void fillControlsWithDefaultValues();
  
  /**
   * Setting a new value programmatically.
   * <p>
   * The following is done:
   * <ul>
   * <li>
   * Determine whether something is filled in (legal or not).<br/>
   * If the new value isn't {@code null}, it is considered to be filled in.
   * </li>
   * <li>
   * Determine whether the data is legal<br/>
   * If the new value isn't {@code null}, it is considered to be a legal value.<br/>
   * If the control is not filled in we also say that the data is not legal, but actually this value is not relevant. If the control is not filled in, the legalness of the data is not defined.
   * </li>
   * <li>
   * Set the error feedback<br/>
   * The error feedback is set by calling {@link setErrorFeedback()} with the determined data legal value.
   * </li>
   * <li>
   * Determine whether the new input value differs from the current value.<br/>
   * If the value has changed, {@link value} is set to this new value.
   * </li>
   * <li>
   * Determine the validity of the control<br/>
   * The validity of the control is determined by calling {@link determineValidity()}.
   * </li>
   * <li>
   * Set the filled in status<br/>
   * </li>
   * <li>
   * Fill the control(s) with the new value by calling {@code fillControlsFromObject()}.
   * </li>
   * <li>
   * Set the reference value to the new value.
   * </li>
   * <li>
   * Set {@code changed} to false, as a new value is set programmatically.
   * </li>
   * <li>
   * Update the status indicator<br/>
   * The status indicator is updated by calling {@link updateStatusIndicator()}.
   * </li>
   * <li>
   * Finally, if the value has changed, the listeners are notified.
   * Notifying the listeners is done by calling {@link notifyListeners()}.
   * </li>
   * </ul>
   * 
   * @param newValue the new value.
   */
  public  void setValue(T newValue) {
    boolean newFilledIn = newValue != null;  // here filled in depends on the newValue set.
    
    boolean validDataValue;  // true if something is filled in and the value is valid value.
    if (newFilledIn) {
      validDataValue = isValidValue(newValue);
      if (!validDataValue) {
        LOGGER.severe("New value is not valid: " + newValue);
      }
    } else {
      validDataValue = false;
    }
    setErrorFeedback(validDataValue);
    
    boolean newValueIsDifferentValue = determineChanges(newValue);
    if (newValueIsDifferentValue) {
      value = newValue;
    } else {
      LOGGER.severe("New value is the same as the current value: " + newValue);
    }
    valid = determineValidity(newFilledIn, validDataValue);
    filledIn = newFilledIn;
    fillControlsFromObject();
    referenceValue = newValue;
    changed = false;
    updateStatusIndicator();
    
    if (newValueIsDifferentValue) {
      notifyValueAndOrStatusChangeListeners(true, true);
    }
  }
  
  /**
   * Determine whether the value is valid with respect to the business rules.
   * <p>
   * This method is called from {@code handleNewUserInput} after it has checked that the control is filled in. TODO
   * A subclass may override this method to check whether the value is valid with respect to the business rules.
   * 
   * @param value The value to be checked for validity. This value may not be {@code null}.
   * @return true if the value is valid, false otherwise.
   */
  protected boolean isValidValue(T value) {
    Objects.requireNonNull(value, "The value may not be null");
    
    return true;
  }
  
  /**
   * Handle the fact that the user has done something with the control (e.g. type text or click on something).
   * 
   * @param source the control which detected the changes.
   */
  protected final void handleNewUserInput(Object source) {
    if (ignoreNewUserInput) {
      return;
    }
    
    boolean newFilledIn = determineFilledIn(source);  // here it depends on the value in the source control.
    boolean validDataValue;  // true if something is filled in and the value is valid value.
    
    T inputValue;    
    if (newFilledIn) {
      inputValue = determineValue(source);
      if (inputValue != null) {
        validDataValue = true;
      } else {
        validDataValue = false;
      }
    } else {
      validDataValue = false;
      inputValue = null;
    }
    setErrorFeedback(validDataValue);
    
    changed = determineChanges(inputValue);
    if (changed) {
      value = inputValue;
    }
    valid = determineValidity(newFilledIn, validDataValue);
    filledIn = newFilledIn;
    updateNonSourceControls(source);
    updateStatusIndicator();
    
    if (changed) {
      notifyValueAndOrStatusChangeListeners(true, true);
    }
  }
  
  /**
   * Determine whether the new value differs from the current value.
   * 
   * @param newValue the new value
   * @return {@code true} if {@code newValue} differs from the current value, false otherwise.
   */
  protected boolean determineChanges(T newValue) {
    boolean result;
    
    if (comparator != null) {
      result = comparator.compare(newValue, value) != 0;
    } else {
      result = !Objects.equals(newValue, value);
    }
    
    return result;
  }
  
  /**
   * Determine whether something is filled in or not. It is not relevant whether the value is legal or illegal.
   */
  protected abstract boolean determineFilledIn(Object source);
  
  /**
   * Determine the new value set by the user via a specific GUI control.
   * <p>
   * This method is called from {@code handleNewUserInput} after it has checked that the control is filled in.
   * 
   * @param The source of a change, which is the value with which {@code ociHandleNewUserInput} was called. This is needed if there is more than one GUI control, like for e.g. the {@link ObjectControlFileSelecter}.
   */
  protected abstract T determineValue(Object source);

  /**
   * {@inheritDoc}
   */
  @Override
  protected void fillControlsFromObject() {
    ignoreNewUserInput = true;
    
    updateNonSourceControls(null);
    
    ignoreNewUserInput = false;
  }
  
  /**
   * Update the value of other controls than the control that called handleNewUserInput().
   * <p>
   * If an ObjectControl has more than one GUI control, then if the user enters a new value in one control probably the other controls also have to be updated.
   * By default nothing is done, so you only have to override this method if your EditorControl has more than one GUI control.
   * 
   * @param source the object that caused the change.
   */
  protected abstract void updateNonSourceControls(Object source);

  /*
   * {@inheritDoc}
   */
  @Override
  public final T getCurrentValue() {
    return getValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final T getValue() {
    return value;
  }
  
  @Override
  protected T getReferenceValue() {
    return referenceValue;
  }
  
  
  /**
   * Add one or more {@code Nodes}s to be reacted on changees.
   * 
   * @param controls The {@code Node}s on which to monitor changes.
   */
  protected void registerControls(Node... controls) {
    
    for (Node control: controls) {
      control.focusedProperty().addListener((_, _, newValue) -> {
        if (!newValue)
          LOGGER.severe("Focus lost on " + control.getId());
          redrawValue();
      });
    }
  }
  
  /**
   * Redraw the value on focus lost.
   * <p>
   * Have a non-empty implementation of this method if you always want to present values in your 'standard' way.
   * E.g.: the user may e.g. enter a date as 14-3-2023, this may be redrawn as 14-03-2023.
   */
  protected abstract void redrawValue();
}
