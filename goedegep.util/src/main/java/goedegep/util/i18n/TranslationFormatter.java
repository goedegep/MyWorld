package goedegep.util.i18n;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * This class provides a simple way to format text based on a ResourceBundle. It uses the MessageFormat class to format the text patterns provided by the ResourceBundle.
 */
public class TranslationFormatter {
  private MessageFormat formatter = new MessageFormat("");
  private ResourceBundle translations = null;
  
  /**
   * Constructor
   * 
   * @param translations The {@code ResourceBundle} to provide the text patterns.
   */
  public TranslationFormatter(ResourceBundle translations) {
    this.translations = translations;
  }

    /**
    * Format the text pattern with the given arguments.
    * 
    * @param textId The id of the text pattern to format, as defined in the ResourceBundle.
    * @param arguments The arguments to use for formatting the text pattern.
    * @return The formatted text.
    */
  public String formatText(String textId, Object ... arguments) {
    formatter.applyPattern(translations.getString(textId));
    
    return formatter.format(arguments);
  }

}
