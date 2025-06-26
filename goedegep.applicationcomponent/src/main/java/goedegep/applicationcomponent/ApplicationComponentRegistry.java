package goedegep.applicationcomponent;

import java.util.HashMap;
import java.util.Map;

public class ApplicationComponentRegistry {
  private Map<String, ApplicationComponent> nameToComponentMap = new HashMap<>();
  
  public void addApplicationComponent(String name, ApplicationComponent applicationComponent) {
    nameToComponentMap.put(name, applicationComponent);
  }
  
  public ApplicationComponent getApplicationComponent(String name) {
    return nameToComponentMap.get(name);
  }
}
