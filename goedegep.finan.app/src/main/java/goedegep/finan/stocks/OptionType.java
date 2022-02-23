package goedegep.finan.stocks;

import java.util.Comparator;

public enum OptionType implements Comparator<OptionType> {
  CALL("CALL"),
  PUT("PUT");
  
  private String text;
  
  OptionType(String text) {
    this.text = text;
  }
  
  public String getText() {
    return text;
  }
  
  public static String[] getTexts() {
    String[] texts = new String[OptionType.values().length];
    
    int i = 0;
    for (OptionType optionType: OptionType.values()) {
      texts[i++] = optionType.text;
    }
    
    return texts;
  }
  
  public static OptionType getOptionTypeForText(String text) {
    for (OptionType type: OptionType.values()) {
      if (type.text.equals(text)) {
        return type;
      }
    }
    
    return null;
  }

  public int compare(OptionType o1, OptionType o2) {
    return o1.text.compareTo(o2.text);
  }
}
