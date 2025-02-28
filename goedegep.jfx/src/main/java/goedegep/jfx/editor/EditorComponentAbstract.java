package goedegep.jfx.editor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.util.listener.ValueAndOrStatusChangeListener;
import goedegep.util.listener.ValueAndOrStatusChangeListenersManager;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

public abstract class EditorComponentAbstract<T> implements EditorComponent<T> {
  private static final Logger         LOGGER = Logger.getLogger(EditorComponentAbstract.class.getName());
  
  public static final String NOK_INDICATOR = "!";
  public static final String CHANGED_INDICATOR = "≠";
  public static final String NOT_CHANGED_INDICATOR = "=";

  protected CustomizationFx customization;
  protected ComponentFactoryFx componentFactory;
  
  /**
   * Indication of whether the object value is optional (if true) or mandatory.
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
   * Error information text, which can be set by the control.
   * <p>
   * This value is only used if there is no {@link errorTextSupplier} set.
   */
  protected String errorText = null;
  
  /**
   * Error text supplier. A method that provides an error text.
   */
  protected Supplier<String> errorTextSupplier;
  
  /**
   * Label base text
   * I don't know if this also makes sense for a compound component. But it doesn't harm to leave it at this level.
   */
  private String labelBaseText = null;
  
  /**
   * Label for the component.
   * I don't know if this also makes sense for a compound component. But it doesn't harm to leave it at this level.
   */
  private Label label = null;
  
  /**
   * The current value, initially set to {@code null}.
   */
  protected T value = null;
  
  /**
   * Reference value to check for changes, initially set to {@code value}.
   */
  protected T referenceValue = value;
  
  /**
   * Indication of whether the value differs from the reference value
   */
  protected boolean changed = false;
  
  /**
   * Id to identify the component.
   */
  private String id;
  
  /**
   * An optional {@link Comparator} to check on a changed {@code value}.
   */
  Comparator<T> comparator = null;
  
  /**
   * Ignore new user input when setting the value (in {@code setValue}).
   */
  protected boolean ignoreNewUserInput = false;
    
  /**
   * Status indicator.
   */
  private Label statusIndicator = null;
  
  /**
   * The invalidation listeners (for the {@code Observable} implementation).
   */
  protected List<InvalidationListener> invalidationListeners = new ArrayList<>();
  
//  /**
//   * The value changed.
//   */
//  private ValueChangedListenerManager<T> valueChangedListenerManager = new ValueChangedListenerManager<>();
  
  /**
   * The value changed.
   */
  private ValueAndOrStatusChangeListenersManager valueAndOrStatusChangeListenersManager = new ValueAndOrStatusChangeListenersManager();
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization
   * @param optional indication of whether the value is optional or not (mandatory).
   */
  protected EditorComponentAbstract(CustomizationFx customization, boolean optional) {
    this.customization = customization;
    this.optional = optional;
    
    componentFactory = customization.getComponentFactoryFx();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabelBaseText(String labelBaseText) {
    this.labelBaseText = labelBaseText;
  }
  
  public String getLabelBaseText() {
    return labelBaseText;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Label getLabel() {
    if (label == null) {
      StringBuilder buf = new StringBuilder();
      buf.append(labelBaseText);
      if (!isOptional()) {
        buf.append(" ")
        .append(MANDATORY_SYMBOL);
      }
      buf.append(":");
      label = new Label(buf.toString());
    }
    
    return label;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public final String getId() {
    return id;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public final void setId(String id) {
    this.id = id;
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
  public final boolean isFilledIn() {
    return filledIn;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isValid() {
    return valid;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isChanged() {
    return changed;
  }
  
  /**
   * Determine whether the new value differs from the {@code referenceValue}.
   * TODO Why isn't this used in the EditPanel?? Remove here if not needed there.
   * 
   * @param newValue the new value
   * @return {@code true} if {@code newValue} differs from the {@code referenceValue}, false otherwise.
   */
  protected boolean determineChanges(T newValue) {
    boolean result;
    
    if (comparator != null) {
      result = comparator.compare(newValue, referenceValue) != 0;
    } else {
      result = !Objects.equals(newValue, referenceValue);
    }
    
    return result;
  }
  
  /**
   * Provide feedback to the user if entered data is wrong.
   * <p>
   * Implement this method to e.g. change the input text from black to red in case of wrong input.
   * 
   * @param valid if true the data is valid, else it is wrong.
   */
  protected abstract void setErrorFeedback(boolean valid);
  
  /**
   * Determine whether the input is valid or not.
   */
  protected final boolean determineValidity(boolean filledIn, boolean dataValid) {
    if (isOptional()  &&  !filledIn) {
      return true;
    }
    
    return dataValid;
  }

  /**
   * Update the {@code statusIndicator}
   */
  protected final void updateStatusIndicator() {
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
  
  @Override
  public final String getStatusSymbol() {
    Label label = (Label) getStatusIndicator();
    return label.getText();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final Node getStatusIndicator() {
    LOGGER.info("=>");

    if (statusIndicator == null) {
      statusIndicator = new Label();
    }

    updateStatusIndicator();

    LOGGER.info("=> " + statusIndicator);
    return statusIndicator;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public final String getErrorText() {
    if (errorTextSupplier != null) {
      return errorTextSupplier.get();
    }
    
    return errorText;
  }
  
//  /**
//   * Fill the controls with default values.
//   */
//  protected abstract void fillControlsWithDefaultValues();
  
  /**
   * Fill the controls with the value of the object.
   */
  protected abstract void fillControlsFromObject();

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setErrorTextSupplier(Supplier<String> errorTextSupplier) {
    this.errorTextSupplier = errorTextSupplier;
  }
  
  /**
   * Set the {@code Comparator} for checking on changes.
   * TODO Check if this is also needed for an EditPanel. If not move this down, else add to EditorComponent interface.
   * 
   * @param comparator the {@code Comparator} for checking on changes.
   */
  protected void setComparator(Comparator<T> comparator) {
    this.comparator = comparator;
  }
  
//  @Override
//  public final void addListener(InvalidationListener listener) {
//    invalidationListeners.add(listener);    
//  }

//  @Override
//  public final void removeListener(InvalidationListener listener) {
//    invalidationListeners.remove(listener);    
//  }

//  @Override
//  public final void removeListeners() {
//    invalidationListeners.clear();
//  }
  
  /**
   * Notify the {@code invalidationListeners} that something has changed.
   */
  protected final void notifyListeners(Observable observable) {
    for (InvalidationListener invalidationListener: invalidationListeners) {
      invalidationListener.invalidated(observable);
    }
  }
  
//  protected void notifyValueChangedListeners(T newValue) {
//    valueChangedListenerManager.notifyListeners(newValue);
//  }

  
  @Override
  public void addValueAndOrStatusChangeListener(ValueAndOrStatusChangeListener listener) {
    valueAndOrStatusChangeListenersManager.addValueAndOrStatusChangeListener(listener);
  }

  @Override
  public void removeValueAndOrStatusChangeListener(ValueAndOrStatusChangeListener listener) {
    valueAndOrStatusChangeListenersManager.removeValueAndOrStatusChangeListener(listener);
  }

  @Override
  public void removeValueAndOrStatusChangeListeners() {
    valueAndOrStatusChangeListenersManager.removeValueAndOrStatusChangeListeners();
  }
  
  protected void notifyValueAndOrStatusChangeListeners(boolean valueChanged, boolean statusChanged) {
    valueAndOrStatusChangeListenersManager.notifyListeners(valueChanged, statusChanged);
  }

}
