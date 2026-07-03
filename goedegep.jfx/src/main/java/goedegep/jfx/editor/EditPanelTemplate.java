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
  protected EditPanelTemplate(CustomizationFx customization, String id, boolean optional) {
    super(customization, id, optional);   
   }
  
  /**
   * Initialize the control.
   */
  @Override
  public void performInitialization() {
    createControls();
    
    createEditPanel();
    
    installChangeListeners();
    
    newObject();

    initializationComplete = true;

    // make sure the initial status information is correct.
    handleChanges(true, true); // during initialization changes are ignored
  }
  
  protected <U> void acceptSubClass(EditPanelTemplate<U> editPanelTemplate, U object, java.util.function.Consumer<U> setObjectMethod) throws EditorException {
    // If the object is null and any value is non null in the edit panel, we get the object that was created by the edit panel.
    if (object == null && editPanelTemplate.anyControlNonNull()) {
      object = editPanelTemplate.getReferenceValue();
      setObjectMethod.accept(object);
    }
    
    if (!editPanelTemplate.anyControlNonNull()) {
      // If all values are null in the edit panel, we set the object to null.
      setObjectMethod.accept(null);
    } else {
      editPanelTemplate.fillObjectFromControls(object, true);   
    }
  }
  
}
