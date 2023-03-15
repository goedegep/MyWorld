package goedegep.jfx.objecteditor;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;

/**
 * This class provides a template for an object editor and it provides some common functionality.
 * <p>
 *
 */
public abstract class ObjectEditorAbstract extends JfxStage {
  private static Logger LOGGER = Logger.getLogger(ObjectEditorAbstract.class.getName());
  
  public ObjectEditorAbstract(CustomizationFx customization, String title) {
    super(title, customization);
    
    // createControls();
  }

  /**
   * Create the GUI controls and add them to a ControlGroup
   */
  protected void createControls() {
    LOGGER.severe("You did not override this method");
  }
  
  /**
   * Layout the GUI controls
   */
  protected void createGUI() {
    LOGGER.severe("You did not override this method");
  }
  
  /**
   * Handle changes in any of the controls.
   */
  protected void handleChanges() {
    LOGGER.severe("You did not override this method");
  }
  
  /**
   * Fill the controls with the value of the object.
   */
  protected void fillControlsFromObject() {
    LOGGER.severe("You did not override this method");
  }
  
  /**
   * Update object from controls.
   */
  protected void updateObjectFromControls() {
    LOGGER.severe("You did not override this method");
  }
}


