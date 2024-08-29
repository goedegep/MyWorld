package goedegep.jfx.objectcontrols;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import goedegep.util.PgUtilities;
import javafx.beans.InvalidationListener;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

/**
 * This class provides a template for implementing an {@code ObjectControl}.
 * <p>
 * <h3>What this class does</h3>
 * 
 * <h4>Handling optional</h4>
 * Apart from calling the constructor of this class with the right value for the {@code optional} parameter nothing has to be done. This class provides the implementation for {@code isOptional()}.
 * 
 * <h3>What your implementation has to do</h3>
 * 
 * <h4>Constructor</h4>
 * Your constructor shall call super() with the value for the {@code optional} parameter.<br/>
 * Create the GUI controls and make sure that on any change initiated by the user the method {@code ociHandleNewUserInput()} is called.<br/>
 * Finally call {@code setValue()} with the default value (typically null) as argument.
 * 
 * <h4>Handling changes</h4>
 * As described above, on any change initiated by the user the method {@code ociHandleNewUserInput()} has to be called.<br/>
 * This method is the main algorithm for handling changes.
 * 
 * <h4>Methods to implement</h4>
 * {@link #ociDetermineFilledIn}
 * {@link #ociDetermineValue}
 * {@link #ociSetErrorFeedback}
 * {@link #ociUpdateNonSourceControls}
 *
 * @param <T> The value type handled by the control
 */
public abstract class ObjectControlTemplate<T> implements ObjectControl<T> {
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlTemplate.class.getName());
  
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
  protected boolean filledIn = false;
  
  /**
   * Indication of whether the control has a valid value or not.
   */
  protected boolean valid = false;
  
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
   * An optional {@link Comparator} to check on a changed {@code value}.
   */
  Comparator<T> comparator = null;
  
  /**
   * Ignore new user input when setting the value (in {@code setValue}).
   */
  protected boolean ignoreNewUserInput = false;
  
  /**
   * Constructor.
   * <p>
   * Your constructor shall call this super() constructor with the value for the {@code optional} parameter.<br/>
   * In your constructor:<br/>
   * Create the GUI controls and make sure that on any change initiated by the user the method {@code ociHandleNewUserInput()} is called.<br/>
   * If needed, set a comparator via {@code setComparator()}. This is needed if {@code !Objects.equals(value, referenceValue)} doesn't work.<br/>
   * Finally call {@code setValue()} with the default value (typically null) as argument. Note: an application will normally set its own default value after constructing this control.
   * 
   * @param isOptional Indication of whether the control is optional (if true) or mandatory.
   */
  public ObjectControlTemplate(boolean optional) {
    LOGGER.info("=>");
    this.optional = optional;
    LOGGER.info("<=");
  }
  
  protected void setComparator(Comparator<T> comparator) {
    this.comparator = comparator;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isOptional() {
    LOGGER.info("<=> " + optional);
    return optional;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isFilledIn() {
    LOGGER.info("<=> " + filledIn);
    return filledIn;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isValid() {
    LOGGER.info("<=> " + valid);
    return valid;
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
   * {@inheritDoc}
   * <p>
   */
  @Override
  public final boolean isChanged() {
    boolean result;
    
    LOGGER.info("=>");
    if (comparator != null) {
      result = comparator.compare(value, referenceValue) != 0;
    } else {
      result = !PgUtilities.equals(value, referenceValue);
    }
    LOGGER.info("<= " + result);
    return result;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public final String getId() {
    LOGGER.info("=>");
    String result = getControl().getId();
    LOGGER.info("<= " + result);
    return result;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public final void setId(String id) {
    LOGGER.info("=> " + id);
    getControl().setId(id);
    LOGGER.info("<=");
  }
  
  /*
   * oci methods from here
   */

  /**
   * Handle any new user input (typically registered as listener on changes on the GUI controls).
   * <p>
   * The following is done:
   * <ul>
   * <li>
   * Determine whether something is filled in (valid or not).<br/>
   * This is done by calling {@link #ociDetermineFilledIn()}.
   * </li>
   * <li>
   * If something is filled in,  try to get the 'new value' (which may be {@code null}). If nothing is filled in the 'new value' is also {@code null}.<br/>
   * Getting the 'new value' is done by calling {@link #ociDetermineValue()}.
   * </li>
   * <li>
   * Determine whether the data is valid<br/>
   * If {@code ociDetermineValue()} returned null the data is not valid (as something is filled in), else it is valid.<br/>
   * If the control is not filled in we also say that the data is not valid, but actually this value is not relevant. If the control is not filled in, the validity of the data is not defined.
   * </li>
   * <li>
   * Set the error feedback<br/>
   * The error feedback is set by calling {@link ociSetErrorFeedback()} with the determined data valid value.
   * </li>
   * <li>
   * Determine whether the new input value differs from the current value.<br/>
   * If the value has changed, {@link value} is set to the new value.
   * </li>
   * <li>
   * Set the validity of the control<br/>
   * The validity of the control ({@code valid}) is set with the value returned by {@link ociDetermineValidity()}. Note that the validity of the control is not the same as the validity of the data.
   * </li>
   * <li>
   * Set the filled in status<br/>
   * The filled in status ({@code filledInf}) is set.
   * </li>
   * <li>
   * Update the status indicator<br/>
   * The status indicator is updated by calling {@link ociUpdateStatusIndicator()}.
   * </li>
   * <li>
   * Finally, if the value has changed, the listeners are notified.
   * Notifying the listeners is done by calling {@link ociNotifyListeners()}.
   * </li>
   * </ul>
   * 
   * @param source the object that caused the change.  This is needed if there is more than one GUI control, like for e.g. the {@link ObjectControlFileSelecter}.
   */
  protected final void ociHandleNewUserInput(Object source) {
    LOGGER.info("=> " + source);
    
    if (ignoreNewUserInput) {
      return;
    }
    
    boolean newFilledIn = ociDetermineFilledIn(source);
    boolean dataValid;
    T inputValue;
    
    if (newFilledIn) {
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
      value = inputValue;
    }
    valid = ociDetermineValidity(newFilledIn, dataValid);
    filledIn = newFilledIn;
    ociUpdateNonSourceControls(source);
    ociUpdateStatusIndicator();
    
    if (changed) {
      ociNotifyListeners();
    }

    LOGGER.info("<=");
  }
  
  /**
   * Setting a new value programmatically.
   * <p>
   * The following is done:
   * <ul>
   * <li>
   * Determine whether something is filled in (valid or not).<br/>
   * If the value isn't {@code null}, it is considered to be filled in.
   * </li>
   * <li>
   * If something is filled in,  try to get the 'new value' (which may be {@code null}). If nothing is filled in the 'new value' is also {@code null}.<br/>
   * Getting the 'new value' is done by calling {@link #ociDetermineValue()}.
   * </li>
   * <li>
   * Determine whether the data is valid<br/>
   * If {@code ociDetermineValue()} returned null the data is not valid (as something is filled in), else it is valid.<br/>
   * If the control is not filled in we also say that the data is not valid, but actually this value is not relevant. If the control is not filled in, the validity of the data is not defined.
   * </li>
   * <li>
   * Set the error feedback<br/>
   * The error feedback is set by calling {@link ociSetErrorFeedback()} with the determined data valid value.
   * </li>
   * <li>
   * Determine whether the new input value differs from the current value.<br/>
   * If the value has changed, {@link ociSetValue()} is called with this new value.
   * </li>
   * <li>
   * Set the validity of the control<br/>
   * The validity of the control is set by calling {@link ociSetValid()} with the value returned by {@link ociDetermineValidity()}. Note that the validity of the control is not the same as the validity of the data.
   * </li>
   * <li>
   * Set the filled in status<br/>
   * The filled in status is set by calling {@link ociSetFilledIn()}.
   * </li>
   * <li>
   * Update the status indicator<br/>
   * The status indicator is updated by calling {@link ociUpdateStatusIndicator()}.
   * </li>
   * <li>
   * Finally, if the value has changed, the listeners are notified.
   * Notifying the listeners is done by calling {@link ociNotifyListeners()}.
   * </li>
   * </ul>
   * 
   * @param source the object that caused the change.  This is needed if there is more than one GUI control, like for e.g. the {@link ObjectControlFileSelecter}.
   */
  public  void setValue(T newValue) {
    LOGGER.info("=> " + newValue);
    
    ignoreNewUserInput = true;
    
    boolean newFilledIn = newValue != null;
    boolean dataValid;
    
    if (newFilledIn) {
      if (newValue != null) {
        dataValid = ociIsValueValid(newValue);
      } else {
        dataValid = false;
      }
    } else {
      dataValid = false;
    }
    ociSetErrorFeedback(dataValid);
    
    boolean changed = !PgUtilities.equals(newValue, getValue());
    if (changed) {
      value = newValue;
    }
    valid = ociDetermineValidity(newFilledIn, dataValid);
    filledIn = newFilledIn;
    ociUpdateNonSourceControls(null);
    referenceValue = newValue;
    ociUpdateStatusIndicator();
    
    if (changed) {
      ociNotifyListeners();
    }
    
    ignoreNewUserInput = false;
    
    LOGGER.info("<=");
  }
  
  protected boolean ociIsValueValid(T value) {
    return value != null;
  }
  
  /**
   * Determine whether something is filled in or not. It is not relevant whether the value is valid or not.
   */
  protected abstract boolean ociDetermineFilledIn(Object source);
  
  /**
   * Determine the new value set by the user via a specific GUI control.
   * <p>
   * This method is called from {@code ociHandleNewUserInput} after it has checked that the control is filled in.
   * 
   * @param The source of a change, which is the value with which {@code ociHandleNewUserInput} was called. This is needed if there is more than one GUI control, like for e.g. the {@link ObjectControlFileSelecter}.
   */
  protected abstract T ociDetermineValue(Object source);
  
  /**
   * Determine whether the input is valid or not.
   */
  protected final boolean ociDetermineValidity(boolean filledIn, boolean dataValid) {
    LOGGER.info("=> filledIn=" + filledIn + ", dataValid=" + dataValid);
        
    if (isOptional()  &&  !filledIn) {
      return true;
    }
        
    LOGGER.info("<= " + dataValid);
    
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
   * {@inheritDoc}
   */
  @Override
  public final Node getStatusIndicator() {
    LOGGER.info("=>");
    
    if (statusIndicator == null) {
      statusIndicator = new Label();
    }
    
    ociUpdateStatusIndicator();
    
    LOGGER.info("=> " + statusIndicator);
    return statusIndicator;
  }
    
  protected final void ociUpdateStatusIndicator() {
    LOGGER.info("=>");

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
    
    LOGGER.info("<=");
  }
  
  /**
   * Update the value of other controls than the control that called ociHandleNewUserInput().
   * <p>
   * If an ObjectControl has more than one GUI control, then if the user enters a new value in one control probable the other controls also have to be updated.
   * By default nothing is done, so if you have to override this method if your ObjectControl has more than one GUI control.
   * 
   * @param source the object that caused the change.
   */
  protected abstract void ociUpdateNonSourceControls(Object source);
  
  /**
   * {@inheritDoc}
   */
  @Override
  public final String getErrorText() {
    LOGGER.info("<=> " + errorText);
    return errorText;
  }
  
  @Override
  public final void addListener(InvalidationListener listener) {
    LOGGER.info("<=> " + listener);
    invalidationListeners.add(listener);    
  }

  @Override
  public final void removeListener(InvalidationListener listener) {
    LOGGER.info("<=> " + listener);
    invalidationListeners.remove(listener);    
  }

  @Override
  public final void removeListeners() {
    LOGGER.info("=>");
    invalidationListeners.clear();
    LOGGER.info("<=");
  }
  
  /**
   * Notify the {@code invalidationListeners} that something has changed.
   */
  protected final void ociNotifyListeners() {
    LOGGER.info("=>");
    for (InvalidationListener invalidationListener: invalidationListeners) {
      invalidationListener.invalidated(this);
    }
    LOGGER.info("<=");
  }
  
  @Override
  public String toString() {
    LOGGER.info("=>");
    String id = getId();
    if (id == null) {
      id = getClass().getName();
    }
    String result = id + ":" + getValue();
    LOGGER.info("<= " + result);
    return result;
  }
}
