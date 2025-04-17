package goedegep.jfx.editor.controls;

import java.text.ParseException;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

/**
 * This class provides a TextField to be used to edit a {@link PgCurrency}.
 */
public class EditorControlCurrency extends EditorControlTextField<PgCurrency> {
  private static final Logger         LOGGER = Logger.getLogger(EditorControlCurrency.class.getName());
  private static final PgCurrencyFormat CF = new PgCurrencyFormat();
  
  @SuppressWarnings("unchecked")
  public static EditorControlCurrency newInstance(CustomizationFx customization, double width, boolean isOptional, String toolTipText) {
    EditorControlCurrency editorControlCurrency = new EditorControlCurrency(customization, width, isOptional, toolTipText);
    editorControlCurrency.performInitialization();
    
    return editorControlCurrency;
  }
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization
   * @param width Width of the TextField.
   * @param isOptional Indication of whether the value is an optional values.
   * @param toolTipText An optional Tooltip text.
   */
  public EditorControlCurrency(CustomizationFx customization, double width, boolean isOptional, String toolTipText) {
    super(customization, width, isOptional, toolTipText);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected PgCurrency stringToObject(String valueAsString) {
    PgCurrency currency = null;
    
      try {
        currency = CF.parse(valueAsString);
      } catch (ParseException e) {
        LOGGER.info("ParseException on value: " + valueAsString);
      }
    
    return currency;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected String objectToString(PgCurrency value) {
    return CF.format(value);
  }
}
