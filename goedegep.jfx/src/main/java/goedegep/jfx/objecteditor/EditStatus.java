package goedegep.jfx.objecteditor;

public enum EditStatus {
  ADD("+"),         // Valid in NEW mode, so object can be added.    
  INVALID("!"),     // Value of controls is invalid - !
  NO_CHANGES("="),  // Valid, but none of the values has changed - =
  CHANGES("≠");     // Valid and there are changes - *
  
  private String statusIndicator;
  
  EditStatus(String statusIndicator) {
    this.statusIndicator = statusIndicator;
  }

  public String getStatusIndicator() {
    return statusIndicator;
  }
    
}
