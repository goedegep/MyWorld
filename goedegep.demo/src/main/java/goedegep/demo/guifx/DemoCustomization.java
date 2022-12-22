package goedegep.demo.guifx;

import goedegep.jfx.CustomizationFx;

public class DemoCustomization extends CustomizationFx {
  private static DemoCustomization demoCustomization = null;
  
  public DemoCustomization() {
    super(null, DemoAppResources.getInstance());
  }
  
  public static DemoCustomization getInstance() {
    if (demoCustomization == null) {
      demoCustomization = new DemoCustomization();
    }
    
    return demoCustomization;
  }
}
