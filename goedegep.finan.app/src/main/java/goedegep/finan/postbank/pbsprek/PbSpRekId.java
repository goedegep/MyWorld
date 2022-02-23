package goedegep.finan.postbank.pbsprek;


public enum PbSpRekId {
  RENTEREKENING("Renterekening"),
  PLUSREKENING("Plusrekening"),
  STERREKENING("Sterrekening"),
  LEEUWREKENING("Leeuwrekening"),
  KAPITAALREKENING("Kapitaalrekening");

  private String name;
  
  private PbSpRekId(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  public static PbSpRekId getIdForName(String name) {
    for (PbSpRekId id: values()) {
      if (id.name.equals(name)) {
        return id;
      }
    }
    
    return null;
  }
}
