package goedegep.appgen;

public enum MessageDialogType {
  ERROR("Foutmelding"),
  WARNING("Waarschuwing");
  
  private String title;

  MessageDialogType(String title) {
    this.title = title;
  }
  
  public String getTitle() {
    return title;
  }
}
