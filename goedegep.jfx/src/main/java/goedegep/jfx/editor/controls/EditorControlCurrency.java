package goedegep.jfx.editor.controls;

import java.text.ParseException;
import java.util.function.Supplier;
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
  
  
  protected EditorControlCurrency(CurrencyBuilder builder) {
    super(builder);
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
  
  
  public static class CurrencyBuilder extends EditorControlTextField.Builder<PgCurrency> {
    
    /**
     * Constructor with mandatory arguments.
     * 
     * @param id The unique id of the ObjectControl (may not be null).
     */
    public CurrencyBuilder(String id) {
      super(id);
    }
    
    @Override
    public CurrencyBuilder setCustomization(CustomizationFx customization) {
      super.setCustomization(customization);
      
      return this;
    }
    
    @Override
    public CurrencyBuilder setWidth(Double width) {
      super.setWidth(width);
      
      return this;
    }
    
    @Override
    public CurrencyBuilder setLabelBaseText(String labelBaseText) {
      super.setLabelBaseText(labelBaseText);
      
      return this;
    }
    
    @Override
    public CurrencyBuilder setToolTipText(String toolTipText) {
      super.setToolTipText(toolTipText);
      
      return this;
    }
    
    @Override
    public CurrencyBuilder setOptional(boolean optional) {
      super.setOptional(optional);
      
      return this;
    }
    
    @Override
    public CurrencyBuilder setErrorTextSupplier(Supplier<String> errorTextSupplier) {
      super.setErrorTextSupplier(errorTextSupplier);
      
      return this;
    }

    @Override
    public EditorControlCurrency build() {
      EditorControlCurrency editorControlCurrency = new EditorControlCurrency(this);
      editorControlCurrency.performInitialization();
      
      return editorControlCurrency;
    }
  }
}
