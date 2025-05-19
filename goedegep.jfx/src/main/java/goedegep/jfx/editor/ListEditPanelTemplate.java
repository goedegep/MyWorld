package goedegep.jfx.editor;

import java.util.function.Supplier;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.util.listener.ValueAndOrStatusChangeListener;
import goedegep.util.listener.ValueAndOrStatusChangeListenersManager;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
/**
 * This template is for an edit panel for a list of objects.<br/>
 * It handles:
 * * deleting an item from the list
 * 
 * @param <T> The type edited by this panel (typically a List).
 * @param <U> The type of the items in the list.
 * @param <V> The type of the sub panel handling type U.
 */
public abstract class ListEditPanelTemplate<T, U, V extends EditorComponent<U>> extends EditPanelTemplate<T> {
  private static Logger LOGGER = Logger.getLogger(ListEditPanelTemplate.class.getName());
  
  
  /**
   * The list of {@code PlayerMultiControl}s.
   */
  protected ObservableList<V> listItemPanels;
  
  private PseudoEditorComponent pseudoEditorComponent;

  
  public ListEditPanelTemplate(CustomizationFx customization, boolean optional) {
    super(customization, optional);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createControls() {
    listItemPanels = FXCollections.observableArrayList();
    
    /*
     * React on changes in the list.
     * A change of the value of an item in the list is handled by the editor component itself.
     * Here we only have to handle changes in the list itself: add, remove and permutations.
     * For an 'Add' the component is registered.
     * For a 'Remove' the component is unregistered.
     * For all cases the list in the GUI has to be redrawn. For this updateListPresentation() is called
     */
    listItemPanels.addListener(new ListChangeListener<V>() {

      @Override
      public void onChanged(Change<? extends V> c) {
        while (c.next()) {
          if (c.wasPermutated()) {  // NOPMD
            // No action needed here
          } else if (c.wasUpdated()) {
            LOGGER.severe("Update not handled!!");
          } else {
            for (V panel: c.getRemoved()) {
              unregisterEditorComponents(panel);
            }
            for (V panel: c.getAddedSubList()) {
              registerEditorComponents(panel);
            }
          }
        }
        
        pseudoEditorComponent.handleUpdatedList(listItemPanels);
        updateListPresentation();
        
      }
      
    });
    
    pseudoEditorComponent = new PseudoEditorComponent(listItemPanels, "listItemPanels");
    registerEditorComponents(pseudoEditorComponent);
    
    this.addValueAndOrStatusChangeListener((_, _) -> updateListPresentation());
  }
  
  protected abstract void updateListPresentation();

}

class PseudoEditorComponent implements EditorComponent<Object> {
  private ObservableList<? extends Object> observableList;
  private String id;
  private boolean changed = false;

  /**
   * The value changed.
   */
  private ValueAndOrStatusChangeListenersManager valueAndOrStatusChangeListenersManager = new ValueAndOrStatusChangeListenersManager();
  
  PseudoEditorComponent(ObservableList<? extends Object> observableList, String id) {
    this.observableList = FXCollections.observableArrayList(observableList);
    this.id = id;
  }
  
  public void handleUpdatedList(ObservableList<? extends Object> observableList) {
    boolean currentChanged = changed;
    changed = !observableList.equals(this.observableList);
    if (changed != currentChanged) {
      notifyValueAndOrStatusChangeListeners(true, true);
    }
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  @Override
  public boolean isOptional() {
    return true;
  }

  @Override
  public boolean isFilledIn() {
    return true;
  }

  @Override
  public boolean isValid() {
    return true;
  }

  @Override
  public boolean isChanged() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String getStatusSymbol() {
    // Not used
    return null;
  }

  @Override
  public Node getStatusIndicator() {
    // Not used
    return null;
  }

  @Override
  public String getErrorText() {
    // Not applicable
    return null;
  }

  @Override
  public void setErrorTextSupplier(Supplier<String> errorTextSupplier) {
    // Not applicable    
  }
  
  @Override
  public void addValueAndOrStatusChangeListener(ValueAndOrStatusChangeListener listener) {
    valueAndOrStatusChangeListenersManager.addValueAndOrStatusChangeListener(listener);
  }

  @Override
  public void removeValueAndOrStatusChangeListener(ValueAndOrStatusChangeListener listener) {
    valueAndOrStatusChangeListenersManager.removeValueAndOrStatusChangeListener(listener);
  }

  @Override
  public void removeValueAndOrStatusChangeListeners() {
    valueAndOrStatusChangeListenersManager.removeValueAndOrStatusChangeListeners();
  }
  
  protected void notifyValueAndOrStatusChangeListeners(boolean valueChanged, boolean statusChanged) {
    valueAndOrStatusChangeListenersManager.notifyListeners(valueChanged, statusChanged);
  }

  @Override
  public void createControls() {
    // Not applicable
  }

  @Override
  public Node getControl() {
    // Not applicable
    return null;
  }

  @Override
  public void setLabelBaseText(String labelBaseText) {
    // Not applicable
    
  }

  @Override
  public Label getLabel() {
    // Not applicable
    return null;
  }

  @Override
  public Object getCurrentValue() throws EditorException {
    // Not applicable
    return null;
  }

  @Override
  public String getValueAsFormattedText() {
    // Not applicable
    return null;
  }

  @Override
  public ReadOnlyBooleanProperty focusedProperty() {
    // Not applicable
    return null;
  }
}
