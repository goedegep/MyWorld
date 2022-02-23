package goedegep.finan.lynx.lynxeffrek;

import java.util.Map;

public class LynxSecurityInfo {
  private static final String         NEWLINE = System.getProperty("line.separator");
  
  public String fiId = null;
  public String uniqueId = null; 
  public String uniqueIdType = null;
  public String securityName = null;
  public String tickerSymbol = null;
  
  public static String lynxSecurityInfoListToString(Map<String, LynxSecurityInfo> lynxSecurityInfoList) {
    StringBuilder buf = new StringBuilder();
    
    for (LynxSecurityInfo lynxSecurityInfo: lynxSecurityInfoList.values()) {
      buf.append(lynxSecurityInfo.toString());
      buf.append(NEWLINE);
    }
    
    return buf.toString();
  }
  
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("fiId=");
    buf.append(fiId);
    buf.append(", uniqueId=");
    buf.append(uniqueId);
    buf.append(", uniqueIdType=");
    buf.append(uniqueIdType);
    buf.append(", securityName=");
    buf.append(securityName);
    buf.append(", tickerSymbol=");
    buf.append(tickerSymbol);
    
    return buf.toString();
  }
}
