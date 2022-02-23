package goedegep.finan.stocks;

public enum RateType {
  OPENINGS_KOERS("Openings koers"),
  SLOT_KOERS("Slot koers"),
  IEDERE_KOERS("Iedere koers");
  
  private String text;
  
  RateType(String text) {
    this.text = text;
  }
  
  public String getText() {
    return text;
  }
  
  public String toString() {
    return text;
  }
}
