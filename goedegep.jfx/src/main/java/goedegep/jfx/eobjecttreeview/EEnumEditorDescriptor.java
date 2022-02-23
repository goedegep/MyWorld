package goedegep.jfx.eobjecttreeview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EEnumEditorDescriptor<T> {

  private Map<String, T> eEnumDisplayNamesMap = new HashMap<>();
  private boolean sort = false;
  
  public EEnumEditorDescriptor(boolean sort) {
    this.sort = sort;
  }
  
  public void addDisplayNameForEEnum(T eEnumLiteral, String displayName) {
    eEnumDisplayNamesMap.put(displayName, eEnumLiteral);
  }
  
  public List<String> getDisplayNames() {
    List<String> displayNames = new ArrayList<>(eEnumDisplayNamesMap.keySet());
    
    if (sort) {
      Collections.sort(displayNames);
    }
    return displayNames;
  }
  
  public T getEEnumLiteralForDisplayName(String displayName) {
    return eEnumDisplayNamesMap.get(displayName);
  }
  
  public String getDisplayNameForEEnumLiteral(T eEnumLiteral) {
    for (String displayName: eEnumDisplayNamesMap.keySet()) {
      if (eEnumDisplayNamesMap.get(displayName).equals(eEnumLiteral)) {
        return displayName;
      }
    }
    
    return null;
  }
}
