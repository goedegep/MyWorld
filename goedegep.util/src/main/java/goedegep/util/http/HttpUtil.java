package goedegep.util.http;

public class HttpUtil {
  /** Constructor
   * <p>
   * A private constructor as this is a utility class.
   */
  private HttpUtil() {
    
  }
  
  /**
   * Setup Fiddler monitoring
   * <p>
   * Call this method to make Http request visible in Fiddler.<br/>
   * Note: Only use this method when Fiddler is running, otherwise Http connections will fail.
   * 
   * @param ipAddress The IP address, e.g. "127.0.0.1".
   */
  public static void setupFiddlerMonitoring(String ipAddress) {
    System.setProperty("http.proxyHost", ipAddress);
    System.setProperty("https.proxyHost", ipAddress);
    System.setProperty("http.proxyPort", "8888");
    System.setProperty("https.proxyPort", "8888");    
  }
}
