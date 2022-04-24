package goedegep.jfx.eobjecttreeview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is a descriptor for an EEnum editor.
 * <p>
 * It provides display names for the enum literals.
 * In the constructor it can be specified that the returned list of display names shall be sorted.
 * 
 * @param <T> The enum literal type.
 */
public class EEnumEditorDescriptor<T> {
  private static final String NEWLINE = System.getProperty("line.separator");

  
  /**
   * A map from a display name to the related enum literal.
   */
  private Map<String, T> eEnumDisplayNamesMap = new HashMap<>();
  
  /**
   * If <code>true</code> the returned list of display names shall be sorted.
   */
  private boolean sort = false;
  
  /**
   * Constructor.
   * 
   * @param sort if <code>true</code> the returned list of display names shall be sorted.
   */
  public EEnumEditorDescriptor(boolean sort) {
    this.sort = sort;
  }
  
  /**
   * Add a display name for a literal.
   * 
   * @param eEnumLiteral an EEnum literal
   * @param displayName the display name for <code>eEnumLiteral</code>.
   */
  public void addDisplayNameForEEnum(T eEnumLiteral, String displayName) {
    eEnumDisplayNamesMap.put(displayName, eEnumLiteral);
  }
  
  /**
   * Get a list of display names.
   * 
   * @return a list of display names.
   */
  public List<String> getDisplayNames() {
    List<String> displayNames = new ArrayList<>(eEnumDisplayNamesMap.keySet());
    
    if (sort) {
      Collections.sort(displayNames);
    }
    return displayNames;
  }
  
  /**
   * Get the enum literal for a given display name.
   * 
   * @param displayName a display name.
   * @return the enum literal for <code>displayName</code>, or null if the name isn't known.
   */
  public T getEEnumLiteralForDisplayName(String displayName) {
    return eEnumDisplayNamesMap.get(displayName);
  }
  
  /**
   * Get the display name for an enum literal.
   * 
   * @param eEnumLiteral an enum literal.
   * @return the display name for <code>eEnumLiteral</code>, or null if the literal isn't known.
   */
  public String getDisplayNameForEEnumLiteral(T eEnumLiteral) {
    for (String displayName: eEnumDisplayNamesMap.keySet()) {
      if (eEnumDisplayNamesMap.get(displayName).equals(eEnumLiteral)) {
        return displayName;
      }
    }
    
    return null;
  }
  
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    for (String displayName: eEnumDisplayNamesMap.keySet()) {
      buf.append("key: ").append(displayName).append(", value: ").append(eEnumDisplayNamesMap.get(displayName)).append(NEWLINE);
    }
    
    return buf.toString();
  }
}
