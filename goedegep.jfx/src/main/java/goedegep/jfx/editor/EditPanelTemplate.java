package goedegep.jfx.editor;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;

public abstract class EditPanelTemplate<T> extends EditorMultiControlTemplate<T> implements EditPanel<T> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(EditPanelTemplate.class.getName());
  
  
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization
   * @param optional indication of whether the value is optional or not.
   */
  protected EditPanelTemplate(CustomizationFx customization, boolean optional) {
    super(customization, optional);   
   }
  
  /**
   * Initialize the control.
   */
  @Override
  public void performInitialization() {
    createControls();
//    fillControlsWithDefaultValues();
    
    createEditPanel();
    
    installChangeListeners();
//    ignoreChanges = false;
//    
    
    newObject();

    initializationComplete = true;

    // make sure the initial status information is correct.
    handleChanges(true, true); // during initialization changes are ignored
  }
  
}
