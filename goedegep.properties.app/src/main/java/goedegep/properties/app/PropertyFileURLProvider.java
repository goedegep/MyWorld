package goedegep.properties.app;

import java.net.URL;

public interface PropertyFileURLProvider {
  URL getPropertyFileURL();
  
  URL getCustomizationFileURL();
}
