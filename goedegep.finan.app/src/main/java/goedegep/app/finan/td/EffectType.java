package goedegep.app.finan.td;

public enum EffectType {
  AANDELEN("aandelen"),
  CLAIM_RIGHTS("claimrechten"),
  OPTIES("opties");
  
  private String name;

  private EffectType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
  
  public static String[] getNames() {
    String[] names = new String[EffectType.values().length];
    
    int i = 0;
    for (EffectType effectType: EffectType.values()) {
      names[i++] = effectType.name;
    }
    
    return names;
  }
}
