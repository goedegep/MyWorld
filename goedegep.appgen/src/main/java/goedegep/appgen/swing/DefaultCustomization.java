package goedegep.appgen.swing;

public class DefaultCustomization {
  private static Customization customization = null;
  
  public static Customization getInstance() {
    if (customization == null) {
      customization = new Customization();
      
      customization.setResources(DefaultAppResources.getInstance());
    }
    
    return customization;
  }
}
