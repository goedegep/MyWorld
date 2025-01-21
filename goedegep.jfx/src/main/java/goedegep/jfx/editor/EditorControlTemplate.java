package goedegep.jfx.editor;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlFileSelecter;

/**
 * This class is a Template for creating an {@code EditorControl}.
 * 
 * @param <T> the type handled by the control.
 */
public abstract class EditorControlTemplate<T> extends EditorComponentAbstract<T> implements EditorControl<T> {
  private static final Logger         LOGGER = Logger.getLogger(EditorControlTemplate.class.getName());
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization
   * @param optional indication of whether the value is optional or not.
   */
  protected EditorControlTemplate(CustomizationFx customization, boolean optional) {
    super(customization, optional);
  }
  
  /**
   * Initialize the control.
   */
  public void performInitialization() {
    createControls();
    fillControlsWithDefaultValues();
    
//    installChangeListeners();
//    ignoreChanges = false;
//    
//    handleChanges();
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
   * Fill the control with the new value by calling {@code fillControlsFromObject()}.
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
  public  void setObject(T newValue) {
    LOGGER.info("=> " + newValue);
    
    ignoreNewUserInput = true;
    
    boolean newFilledIn = newValue != null;
    boolean dataLegal;
    
    if (newFilledIn) {
      dataLegal = true;
    } else {
      dataLegal = false;
    }
    setErrorFeedback(dataLegal);
    
    boolean newValueIsDifferentValue = determineChanges(newValue);
    if (newValueIsDifferentValue) {
      value = newValue;
    }
    valid = determineValidity(newFilledIn, dataLegal);
    filledIn = newFilledIn;
    fillControlsFromObject();
    referenceValue = newValue;
    changed = false;
    updateStatusIndicator();
    
    if (newValueIsDifferentValue) {
      notifyValueAndOrStatusChangeListeners(true, true);
    }
    
    ignoreNewUserInput = false;
    
    LOGGER.info("<=");
  }
  
  /**
   * Handle the fact that the user has done something with the control (e.g. type text or click on something).
   * 
   * @param source the control which detected the changes.
   */
  protected final void handleNewUserInput(Object source) {
    LOGGER.info("=> " + source);
    
    if (ignoreNewUserInput) {
      return;
    }
    
    boolean newFilledIn = determineFilledIn(source);
    boolean dataValid;
    T inputValue;
    
    if (newFilledIn) {
      inputValue = determineValue(source);
      if (inputValue != null) {
        dataValid = true;
      } else {
        dataValid = false;
      }
    } else {
      dataValid = false;
      inputValue = null;
    }
    setErrorFeedback(dataValid);
    
    changed = determineChanges(inputValue);
    if (changed) {
      value = inputValue;
    }
    valid = determineValidity(newFilledIn, dataValid);
    filledIn = newFilledIn;
    updateNonSourceControls(source);
    updateStatusIndicator();
    
    if (changed) {
      notifyValueAndOrStatusChangeListeners(true, true);
    }

    LOGGER.info("<=");
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
    updateNonSourceControls(null); 
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
    LOGGER.info("<=> " + value);
    return value;
  }
  
  /**
   * Redraw the value on focus lost.
   * <p>
   * Have a non-empty implementation of this method if you always want to present values in your 'standard' way.
   * E.g.: the user may e.g. enter a date as 14-3-2023, this may be redrawn as 14-03-2023.
   */
  protected abstract void redrawValue();
}
