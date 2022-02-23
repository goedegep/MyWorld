package goedegep.finan.stocks;

public enum OptieTransactieType {
  OPENINGSKOOP("openingskoop"),         // aankoop.
  SLUITINGSKOOP("sluitingskoop"),       // aankoop voor geschreven opties.
  OPENINGSVERKOOP("openingsverkoop"),   // opties schrijven.
  SLUITINGSVERKOOP("sluitingsverkoop"); // verkoop van in bezit zijnde opties.
  
  private String description;
  
  OptieTransactieType(String description) {
    this.description = description;
  }
  
  public String getDescription() {
    return description;
  }
  
  public static OptieTransactieType getOptieTransactieTypeVoorDescription(String description) {
    for (OptieTransactieType type: OptieTransactieType.values()) {
      if (type.description.equals(description)) {
        return type;
      }
    }
    
    return null;
  }
}
