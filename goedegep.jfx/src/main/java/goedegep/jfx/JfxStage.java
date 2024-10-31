package goedegep.jfx;

import java.util.logging.Logger;

import goedegep.configuration.model.Look;
import goedegep.resources.ImageSize;
import javafx.stage.Stage;

/**
 * This class is a {@link Stage} which is customized according an instance of {@link CustomizationFx}.
 */
public class JfxStage extends Stage {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(JfxStage.class.getName());
  
  /**
   * The GUI customization.
   */
  protected CustomizationFx customization = null;
  
  /**
   * Factory for creating GUI components.
   */
  protected ComponentFactoryFx componentFactory = null;

  /**
   * Constructor
   * 
   * @param owner the owner (parent?) window of this Stage.
   * @param title the title of this Stage.
   */
  public JfxStage(CustomizationFx customization, String title) {
    if (title != null) {
      setTitle(title);
    }
    
    if (customization == null) {
      this.customization = DefaultCustomizationFx.getInstance();
    } else {
      this.customization = customization;
    }
    componentFactory = this.customization.getComponentFactoryFx();
    if (componentFactory == null) {
      throw new IllegalArgumentException("parameter customization has no ComponentFactory");
    }
    
    getIcons().add(this.customization.getResources().getApplicationImage(ImageSize.SIZE_0));
  }
    
  /**
   * Get the look of this stage.
   * 
   * @return the look of this stage.
   */
  public Look getLook() {
    return customization.getLook();
  }
  
  /**
   * Get the application resources of this stage.
   * 
   * @return the application resources of this stage.
   */
  public AppResourcesFx getResources() {
    return customization.getResources();
  }
  
  /**
   * Get the component factory for this stage.
   * 
   * @return the component factory for this stage.
   */
  public ComponentFactoryFx getComponentFactory() {
    return componentFactory;
  }
  
}
