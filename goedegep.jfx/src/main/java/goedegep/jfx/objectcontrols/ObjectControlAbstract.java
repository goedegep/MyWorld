package goedegep.jfx.objectcontrols;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.logging.Logger;

import javafx.beans.InvalidationListener;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

public abstract class ObjectControlAbstract<T> implements ObjectControl<T> {
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlAbstract.class.getName());
  protected static final String NEW_LINE = System.getProperty("line.separator");
  
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
   * Error text supplier.
   */
  protected Supplier<String> errorTextSupplier;
  
  /**
   * The current value, initially set to {@code null}
   */
  protected T value = null;
  
  /**
   * Reference value to check for changes, initially set to the value of {@code value}.
   */
  protected T referenceValue = value;
  
  /**
   * An optional {@link Comparator} to check on a changed {@code value}.
   */
  Comparator<T> comparator = null;
  
  /**
   * Status indicator
   */
  private Label statusIndicator = null;
  
  /**
   * The invalidation listeners
   */
  protected List<InvalidationListener> invalidationListeners = new ArrayList<>();
  
  
  

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
   * <p>
   */
  @Override
  public final boolean isChanged() {
    boolean result;
    
    LOGGER.info("=>");
    if (comparator != null) {
      result = comparator.compare(value, referenceValue) != 0;
    } else {
      result = !Objects.equals(value, referenceValue);
    }
    LOGGER.info("<= " + result);
    return result;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public final void setErrorTextSupplier(Supplier<String> errorTextSupplier) {
    this.errorTextSupplier = errorTextSupplier;
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

    ociUpdateStatusIndicator();

    LOGGER.info("=> " + statusIndicator);
    return statusIndicator;
  }
  
  /**
   * {@inheritDoc}
   */
  public String getValueAsFormattedText() {
    throw new UnsupportedOperationException();
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
   * Update the {@code statusIndicator}
   */
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
}
