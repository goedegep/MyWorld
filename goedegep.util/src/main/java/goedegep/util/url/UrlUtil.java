package goedegep.util.url;

import java.util.Map;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlUtil {
  public static final String START_PARAM_URL_DELIMETER = "?";
  public static final String URL_VALUE_DELIMETER = "=";
  public static final String URL_PARAM_DELIMETER = "&";
  public static final String URL_DELIMETER = "/";


  /**
   * Constructor
   * <p>
   * A private constructor, so this utility class cannot be instantiated nor extended.
   */
  private UrlUtil() {

  }
  
  /**
   * Check whether a {@code String} is a URL.
   * 
   * @param text the {@code String} to be checked. This may not be null.
   * @return true if {@code text} is a URL, false otherwise.
   */
  public static boolean isURL(String text) {
    if (text.startsWith("http:")  ||  text.startsWith("https:")) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Append parameters to a URL string.
   * <p>
   * The 
   * 
   * @param url a partial URL to which the parameters will be added
   * @param params a <code>Map</code> with parameter definitions. The keys are the parameter names, the values are the parameter values.
   * @param dontAddQuestionMark if true, no question mark is added to the <code>url</code> before adding the parameters.
   *                            This method can be called more than once on the same url, the first time with <code>dontAddQuestionMark</code>
   *                            set to false, any next time with <code>dontAddQuestionMark</code> set to true.
   * @return <code>url</code> with the parameters added. 
   * @throws UnsupportedEncodingException in case any part of the url cannot be encoded.
   */
  public static String appendGetParamsToUrl(String url, Map<String, String> params, boolean dontAddQuestionMark) throws UnsupportedEncodingException {
    StringBuilder buf = new StringBuilder().append(url);

    if (params != null && params.size() > 0) {
      if (!dontAddQuestionMark) {
        buf.append(START_PARAM_URL_DELIMETER);
      }

      boolean first = true;
      for (String key: params.keySet()) {
        if (first) {
          first = false;
        } else {
          buf.append(URL_PARAM_DELIMETER);
        }
        String value = params.get(key);
        buf.append(key).append(URL_VALUE_DELIMETER).append(URLEncoder.encode(value, "UTF-8"));
      }
    }

    return buf.toString();
  }
}
