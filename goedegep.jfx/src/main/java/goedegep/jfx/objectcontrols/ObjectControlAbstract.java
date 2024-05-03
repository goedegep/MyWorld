package goedegep.jfx.objectcontrols;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.util.PgUtilities;
import javafx.beans.InvalidationListener;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

/**
 * This class provides the common part for implementing an {@code ObjectControl}. So your class will extend this class.
 * <p>
 * <b>Constructor</b><br/>
 * Your constructor should at least have an 'isOptional' parameter and call super() with this value.<br/>
 * Create the GUI controls and make sure that on any change initiated by the user the method {@code ociHandleNewUserInput()} is called.<br/>
 * Finally call {@code setValue()} with the default value (typically null) as argument.
 * <p>
 * <b>Handling changes</b><br/>
 * As described above, on any change initiated by the user the method {@code ociHandleNewUserInput()} has to be called.<br/>
 * This method is the main algorithm for handling changes.
 * 
 * <b>Handling optional</b><br/>
 * Apart from calling the constructor of this class with the right value for the {@code optional} parameter nothing has to be done. This class provides the implementation for {@code isOptional()}.
 * 
 * @param <T> The value type handled by the control
 */
public abstract class ObjectControlAbstract<T> implements ObjectControl<T> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlAbstract.class.getName());
  
  /*
   * Object Control status:
   * Invalid: mandatory field not filled in, invalid value
   * Valid: changed
   * Valid: not changed
   */
//  public static final String OK_INDICATOR = "✓";
  public static final String NOK_INDICATOR = "!";
  public static final String CHANGED_INDICATOR = "≠";
  public static final String NOT_CHANGED_INDICATOR = "=";
  
  /**
   * Indication of whether the control is optional (if true) or mandatory.
   */
  protected boolean optional;
  
  /**
   * Indication of whether the control is filled-in or not.
   */
  private boolean filledIn = false;
  
  /**
   * Indication of whether the control has a valid value or not.
   */
  private boolean valid = false;
  
  /**
   * The current value, initially set to {@code null}
   */
  protected T value = null;
  
  /**
   * Reference value to check for changes, initially set to the value of {@code value}.
   */
  protected T referenceValue = value;
  
  /**
   * Error information text
   */
  protected String errorText = null;
  
  /**
   * Status indicator
   */
  private Label statusIndicator;
  
  /**
   * The invalidation listeners
   */
  protected List<InvalidationListener> invalidationListeners = new ArrayList<>();
  
  
  /**
   * Constructor.
   * 
   * @param isOptional Indication of whether the control is optional (if true) or mandatory.
   */
  public ObjectControlAbstract(boolean optional) {    
    this.optional = optional;
  }
  

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isOptional() {
    return optional;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFilledIn() {
    return filledIn;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValid() {
    return valid;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T getValue() {
    return value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isChanged() {
    return !PgUtilities.equals(value, referenceValue);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {
    return getControl().getId();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(String id) {
    getControl().setId(id);
  }
  
  /*
   * oci methods from here
   */

  /**
   * Handle any new user input (typically registered as listener on changes on the GUI controls).
   * <p>
   * Determine whether the control is filled in by calling {@link #ociDetermineFilledIn()}.<br/>
   * If the control is filled in try to get the value by calling {@link #ociDetermineValue()}, else the value is null.<br/>
   * If {@code ociDetermineValue()} returned null the data is not valid (as something is filled in), else it is valid.<br/>
   * If the control is not filled in we also say that the data is not valid, but actually this value is not relevant. If the control is not filled in, the validity of the data is not defined.<br/>
   * The error feedback is set by calling {@link ociSetErrorFeedback()} with the determined data valid value.<br/>
   * Next it is determined whether the new input value differs from the current value.
   * If the value has changed, {@link ociSetValue()} is called with this new value.<br/>
   * The validity of the control is set by calling {@link ociSetValid()} with the value returned by {@link ociDetermineValidity()}. Note that the validity of the control is not the same as the validity of the data.<br/>
   * The filled in status is set by calling {@link ociSetFilledIn()}.<br/>
   * The status indicator is updated by calling {@link ociUpdateStatusIndicator()}.
   * Finally, if the value has changed, the listeners are notified by calling {@link ociNotifyListeners()}.
   * 
   * @param source the object that caused the change.  This is needed if there is more than one GUI control, like for e.g. the {@link ObjectControlFileSelecter}.
   */
  protected void ociHandleNewUserInput(Object source) {
    boolean filledIn = ociDetermineFilledIn();
    boolean dataValid;
    T inputValue;
    
    if (filledIn) {
      inputValue = ociDetermineValue(source);
      if (inputValue != null) {
        dataValid = true;
      } else {
        dataValid = false;
      }
    } else {
      dataValid = false;
      inputValue = null;
    }
    ociSetErrorFeedback(dataValid);
    
    boolean changed = !PgUtilities.equals(inputValue, getValue());
    if (changed) {
      ociSetValue(inputValue);
    }
    ociSetValid(ociDetermineValidity(filledIn, dataValid));
    ociSetFilledIn(filledIn);
    ociUpdateNonSourceControls(source);
    ociUpdateStatusIndicator();
    
    if (changed) {
      ociNotifyListeners();
    }
  }
  
  /**
   * Determine whether something is filled in or not. It is not relevant whether the value valid or not.
   */
  protected abstract boolean ociDetermineFilledIn();
  
  /**
   * Determine the actual value.
   * <p>
   * PRE: control has to be filled in (determineFilledIn() has returned true).
   * 
   * @param The source of a change. This is needed if there is more than one GUI control, like for e.g. the {@link ObjectControlFileSelecter}.
   */
  protected abstract T ociDetermineValue(Object source);
  
  /**
   * Determine whether the input is valid or not.
   */
  protected boolean ociDetermineValidity(boolean filledIn, boolean dataValid) {
    
    if (isOptional()  &&  !filledIn) {
      return true;
    }
        
    return (dataValid);
  }
  
  /**
   * Provide feedback to the user if entered data is wrong.
   * <p>
   * Implement this method to e.g. change the input text from black to red in case of wrong input.
   * 
   * @param valid if true the data is valid, else it is wrong.
   */
  protected abstract void ociSetErrorFeedback(boolean valid);
  
  /**
   * Redraw the value on focus lost.
   * <p>
   * Have a non-empty implementation of this method if you always want to present values in your 'standard' way.
   * E.g.: the user may e.g. enter a date as 14-3-2023, this may be redrawn as 14-03-2023.
   */
  protected abstract void ociRedrawValue();
  
  /**
   * Set the filledIn flag.
   */
  public void ociSetFilledIn(boolean filledIn) {
    this.filledIn = filledIn;
  }

  /**
   * Set the valid flag.
   * 
   * @param valid the new valid value.
   */
  public void ociSetValid(boolean valid) {
    this.valid = valid;
  }

  /**
   * Set the value.
   * 
   * @param value the new value of the ObjectControl.
   */
  public void ociSetValue(T value) {
    this.value = value;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Node getStatusIndicator() {
    if (statusIndicator == null) {
      statusIndicator = new Label();
    }
    
    ociUpdateStatusIndicator();
    
//    final Label validIndicationLabel = new Label(statusString);
//    final Label validIndicationLabel = new Label(ocIsValid() ? OK_INDICATOR : NOK_INDICATOR);
//    setValidIndicatorTooltip(validIndicationLabel);
//    
//    addListener((o) -> setValidIndicatorTooltip(validIndicationLabel));
    ociUpdateStatusIndicator();
    
    return statusIndicator;
  }
  
//  private void setValidIndicatorTooltip(Label validIndicationLabel) {
//    String tooltipText = null;
//    
//    if (ocIsValid()) {
//      if (ocIsChanged()) {
//        tooltipText = "Value is changed and OK";
//      } else {
//        tooltipText = "Value is not changed and OK";
//      }
//    } else {
//      if (!ocIsFilledIn()) {
//        if (!ocIsOptional()) {
//          tooltipText = "This mandatory value is not filled in";
//        }
//      } else {
//        tooltipText = ocGetErrorText();
//      }
//    }
//    validIndicationLabel.setTooltip(new Tooltip(tooltipText));
//  }
  
  private void ociUpdateStatusIndicator() {
    if (statusIndicator == null) {
      return;
    }
    
    // Label text
    String statusString;
    if (!isValid()) {
      statusString = NOK_INDICATOR;
    } else {
      if (isChanged()) {
        statusString = CHANGED_INDICATOR;
      } else {
        statusString = NOT_CHANGED_INDICATOR;
      }
    }
    
    statusIndicator.setText(statusString);
    
    // Label tooltip
    String tooltipText = null;
    
    if (isValid()) {
      if (isChanged()) {
        tooltipText = "Value is changed and OK";
      } else {
        tooltipText = "Value is not changed and OK";
      }
    } else {
      if (!isFilledIn()) {
        if (!isOptional()) {
          tooltipText = "This mandatory value is not filled in";
        }
      } else {
        tooltipText = getErrorText();
      }
    }
    statusIndicator.setTooltip(new Tooltip(tooltipText));
    
  }
  
  /**
   * Update the value of other controls than the control that called ociHandleNewUserInput().
   * <p>
   * If an ObjectControl has more than one GUI control, then if the user enters a new value in one control probable the other controls also have to be updated.
   * By default nothing is done, so if you have to override this method if your ObjectControl has more than one GUI control.
   * 
   * @param source the object that caused the change.
   */
  protected void ociUpdateNonSourceControls(Object source) {
    // default no action
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String getErrorText() {
      return errorText;
  }
  
  @Override
  public void addListener(InvalidationListener listener) {
    invalidationListeners.add(listener);    
  }

  @Override
  public void removeListener(InvalidationListener listener) {
    invalidationListeners.remove(listener);    
  }

  @Override
  public void removeListeners() {
    invalidationListeners.clear();    
  }
  
  /**
   * Notify the {@code invalidationListeners} that something has changed.
   */
  protected void ociNotifyListeners() {
    for (InvalidationListener invalidationListener: invalidationListeners) {
      invalidationListener.invalidated(this);
    }
  }
  
  @Override
  public String toString() {
    return getId() + ":" + getValue();
  }
}
