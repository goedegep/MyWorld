package goedegep.jfx.objectcontrols;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.util.PgUtilities;
import javafx.beans.InvalidationListener;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

public abstract class ObjectControlAbstract<T> implements ObjectControl<T> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlAbstract.class.getName());
  
  public static final String OK_INDICATOR = "✓";
  public static final String NOK_INDICATOR = "!";
  
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
   * Reference value to check for changes.
   */
  protected T referenceValue;
  
  /**
   * The current value
   */
  protected T value;
  
  /**
   * Error information text
   */
  protected String errorText = null;
  
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
  public boolean ocIsOptional() {
    return optional;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean ocIsFilledIn() {
    return filledIn;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean ocIsValid() {
    return valid;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T ocGetValue() {
    return value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean ocIsChanged() {
//    return value != referenceValue;
    return !PgUtilities.equals(value, referenceValue);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String ocGetId() {
    return ocGetControl().getId();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void ocSetId(String id) {
    ocGetControl().setId(id);
  }
  
  /*
   * oci methods from here
   */

  /**
   * Handle any new user input (typically registered as listener on changes on the extended control).
   * <p>
   * Properties are set in the following order: ocValueProperty, ocValidProperty, ocFilledInProperty.
   * So when you listen to the ocValidProperty and this changes to true, the ocValueProperty will be valid.
   * Listeners to the contol are notified after the properties have been set.
   */
  protected void ociHandleNewUserInput() {
    boolean filledIn = ociDetermineFilledIn();
    boolean dataValid;
    T value;
    
    if (filledIn) {
      value = ociDetermineValue();
      if (value != null) {
        dataValid = true;
      } else {
        dataValid = false;
      }
    } else {
      dataValid = false;
      value = null;
    }
    ociSetErrorFeedback(dataValid);
    
    ociSetValue(value);
    ociSetValid(ociDetermineValidity(filledIn, dataValid));
    ociSetFilledIn(filledIn);
    
    ociNotifyListeners();
  }
  
  /**
   * Determine whether something is filled in or not.
   */
  protected abstract boolean ociDetermineFilledIn();
  
  /**
   * Determine the actual value.
   * <p>
   * PRE: control has to be filled in (determineFilledIn() has returned true).
   */
  protected abstract T ociDetermineValue();
  
  /**
   * Determine whether the input is valid or not.
   */
  protected boolean ociDetermineValidity(boolean filledIn, boolean dataValid) {
    
    if (ocIsOptional()  &&  !filledIn) {
      return true;
    }
        
    return (dataValid);
  }
  
  /**
   * Provide feedback to the user if entered data is wrong.
   * <p>
   * Overwrite this method to e.g. change the input text from black to red in case of wrong input.
   * 
   * @param valid if true the data is valid, else it is wrong.
   */
  protected abstract void ociSetErrorFeedback(boolean valid);
  
  /**
   * Redraw the value on focus lost.
   * <p>
   * Overwrite this method if you always want to present values in your 'standard' way.
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
   */
  public void ociSetValid(boolean valid) {
    this.valid = valid;
  }

  /**
   * Set the value.
   */
  public void ociSetValue(T value) {
    this.value = value;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Node ocGetValidIndicator() {
    final Label validIndicationLabel = new Label(ocIsValid() ? OK_INDICATOR : NOK_INDICATOR);
    setValidIndicatorTooltip(validIndicationLabel);
    
    addListener((o) -> setValidIndicatorTooltip(validIndicationLabel));
    
    return validIndicationLabel;
  }
  
  private void setValidIndicatorTooltip(Label validIndicationLabel) {
    String tooltipText = null;
    
    if (ocIsValid()) {
      validIndicationLabel.setText(OK_INDICATOR);
      tooltipText = "Ok";
    } else {
      validIndicationLabel.setText(NOK_INDICATOR);
      if (!ocIsFilledIn()) {
        if (!ocIsOptional()) {
          tooltipText = "This mandatory value is not filled in";
        }
      } else {
        tooltipText = ocGetErrorText();
      }
    }
    validIndicationLabel.setTooltip(new Tooltip(tooltipText));
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String ocGetErrorText() {
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
  
  /**
   * Notify the {@code invalidationListeners} that something has changed.
   */
  protected void ociNotifyListeners() {
    for (InvalidationListener invalidationListener: invalidationListeners) {
      invalidationListener.invalidated(this);
    }
  }
}
