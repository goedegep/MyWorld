package goedegep.finan.stocks;

import java.util.ArrayList;
import java.util.List;

public enum DividendType {
  CASH("contant"),        // contant
  STOCK("stock"),         // stock dividend   
  STOCK_OR_CASH("keuze"), // keuzedividend
  DRIP ("drip"),;         // Drip

  private String  text;
  
  private DividendType(String text) {
    this.text = text;
  }
  
  public String getText() {
    return text;
  }
  
  public static String[] getTextsAsArray() {
    List<String> typeTexts = new ArrayList<String>();
    for (DividendType dividendType: DividendType.values()) {
      typeTexts.add(dividendType.getText());
    }
    return (String[]) typeTexts.toArray(new String[0]);
  }

  public static DividendType getDividendTypeForText(String text) {
    for (DividendType dividendType: values()) {
      if (dividendType.text.equals(text)) {
        return dividendType;
      }
    }
    
    return null;
  }
}
