package goedegep.util.i18n;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class TranslationFormatter {
  private MessageFormat formatter = new MessageFormat("");
  private ResourceBundle translations = null;
  
  /**
   * Constructor
   * 
   * @param translations The ResourceBundle to provide the text patterns.
   */
  public TranslationFormatter(ResourceBundle translations) {
    this.translations = translations;
  }

  public String formatText(String textId, Object ... arguments) {
    formatter.applyPattern(translations.getString(textId));
    
    return formatter.format(arguments);
  }

}
