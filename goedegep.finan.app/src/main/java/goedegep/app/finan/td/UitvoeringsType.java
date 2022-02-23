package goedegep.app.finan.td;

public enum UitvoeringsType {
  VOLLEDIG("volledig"),
  DEELUITVOERING_EERSTE("deeluitvoering (eerste)"),
  DEELUITVOERING("deeluitvoering"),
  DEELUITVOERING_LAATSTE("deeluitvoering (laatste)");
  
  private String text;

  private UitvoeringsType(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }
  
  public static String[] getTexts() {
    String[] texts = new String[UitvoeringsType.values().length];
    
    int i = 0;
    for (UitvoeringsType uitvoeringsType: UitvoeringsType.values()) {
      texts[i++] = uitvoeringsType.text;
    }
    
    return texts;
  }
}
