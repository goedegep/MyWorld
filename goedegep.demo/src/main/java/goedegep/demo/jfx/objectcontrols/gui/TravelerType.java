package goedegep.demo.jfx.objectcontrols.gui;

public enum TravelerType {
  NEVER("I never travel"),
  SOMETIMES("I sometimes travel"),
  REGULAR("I'm a regular traveller"),
  FREQUENT("I'm a frequent traveller"),
  BUSINESS_ONLY("I only travel for my work");
  
  private String displayText;
  
  TravelerType(String displayText) {
    this.displayText = displayText;
  }

  public String toString() {
    return displayText;
  }
}
