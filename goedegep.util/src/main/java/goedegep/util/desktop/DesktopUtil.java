package goedegep.util.desktop;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import goedegep.util.url.UrlUtil;

/**
 * This class provides utility methods for java.awt.Desktop
 */
public class DesktopUtil {
  private static Desktop desktop = null;
  
  /**
   * Private constructor as this is a utility class.
   */
  private DesktopUtil() {
    
  }
  
  /**
   * Open 'any' path (filename or url)
   * <p>
   * If the {@code path} is a url, a browser is opened, else the standard 'open' is called.
   * 
   * @param path the path to open.
   */
  public static void open(String path) {
    if (UrlUtil.isURL(path)) {
      try {
        URI uri = new URI(path);
        openBrowser(uri);
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
    } else {
      openFile(new File(path));
    }
  }
  
  /**
   * Open a {@code File}.
   * 
   * @param file the {@code File} to open.
   */
  private static void openFile(File file) {
    try {
      getDesktopInstance().open(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Open a browser for a specific uri.
   * 
   * @param uri the uri to open in the browser.
   */
  public static void openBrowser(URI uri) {
    try {
      getDesktopInstance().browse(uri);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Get the {@code Desktop} instance.
   * @return the {@code Desktop} instance.
   */
  public static Desktop getDesktopInstance() {
    if (desktop == null) {
      desktop = Desktop.getDesktop();
    }
    
    return desktop;
  }
}
