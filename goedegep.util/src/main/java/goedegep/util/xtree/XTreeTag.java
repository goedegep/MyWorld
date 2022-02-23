package goedegep.util.xtree;

import java.util.HashMap;

public enum XTreeTag {
  QUERY_SUBTREE(1),
  QUERY_INDEX(2),
  QUERY_RANGE(3),
  QUERY_WHERE(4),
  QUERY_RESULT_ROOT(5);
  
  public static final short OFFSET_FOR_USER_TAGS = 1000;
  private static HashMap<Integer, String> nameForValueMap = new HashMap<Integer, String>();
    
  short value;
  
  XTreeTag(int value) {
      this.value = (short) value;
  }
  
  public final short getValue() {
      return value;
  }
  
  public String getName() {
      return name();
  }
  
  public static short getOffsetForUserTags() {
    return OFFSET_FOR_USER_TAGS;
  }
  
  public static String getNameForTagValue(int tagValue) {
    return nameForValueMap.get(tagValue);
  }
  
  public static XTreeTag getXTreeTagForValue(int tagValue) {
    for (XTreeTag xTreeTag: values()) {
      if (xTreeTag.value == tagValue) {
        return xTreeTag;
      }
    }
    
    throw new IllegalArgumentException("There's no XTreeTag with value: " + tagValue);
  }
}
