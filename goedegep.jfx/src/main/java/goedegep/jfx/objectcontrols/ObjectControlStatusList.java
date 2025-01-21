package goedegep.jfx.objectcontrols;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * This class handles a list of {@code ObjectControl}s.
 * <p>
 * This class implements the {@code ObjectControl} functionality for a list objects.
 * 
 * <h5>optional</h5>
 * In this case optional is interpreted as the list may be empty.
 * 
 * <h5>filled in</h5>
 * If the list is not empty, it is filled in.
 * 
 * This class can be used if the object you are editing has a reference to a list of sub object. For example: a music album has a list of discs.
 * In this case you could have an Object Edit Panel for each disc
 * and then group these panels in this {@code ObjectControlList}.<br/>
 * This class then 
 */
public class ObjectControlStatusList implements ObjectControlStatus {
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlTemplate.class.getName());
  
  
  /**
   * Indication of whether filling the list is optional.
   */
  private boolean optional = false;
  
  /**
   * The current list.
   */
  ObservableList<? super Object> list = FXCollections.observableArrayList();
  
  /**
   * A reference list for detecting changes.
   */
  List<? extends Object> referenceList;
  
  /**
   * Indication of whether the list has changed of not.
   */
  private boolean changed = false;
  
  /**
   * Identification of this control
   */
  private String id;
  
  /**
   * The invalidation listeners
   */
  protected List<InvalidationListener> invalidationListeners = new ArrayList<>();
  
  public ObjectControlStatusList(boolean optional) {
    this.optional = optional;
  }

  @Override
  public boolean isOptional() {
    return optional;
  }

  @Override
  public boolean isFilledIn() {
    return !list.isEmpty();
  }

  @Override
  public boolean isValid() {
    return true;
  }

  public List<? super Object> getValue() {
    return list;
  }

  public void setObject(List<?> list) {
    referenceList = list;
    this.list = FXCollections.observableArrayList(list);
    
    InvalidationListener invalidationListener = new InvalidationListener() {

      @Override
      public void invalidated(Observable observable) {
       LOGGER.severe("Invalidated");
       handleChanges();
      }
      
    };
    this.list.addListener(invalidationListener);
    
    ListChangeListener<Object> listChangeListener = new ListChangeListener<>() {

      @Override
      public void onChanged(Change<? extends Object> c) {
        LOGGER.severe("Changed: " + c.toString());
        handleChanges();
      }

      
    };
    this.list.addListener(listChangeListener);
  }
  
  private void handleChanges() {
    changed = !list.equals(referenceList);
    notifyListeners();
  }

  @Override
  public Node getStatusIndicator() {
     return null;
  }

  @Override
  public String getErrorText() {
    return null;
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
  public boolean isChanged() {
     return changed;
  }
  
  @Override
  public final void addListener(InvalidationListener listener) {
    LOGGER.info("<=> " + listener);
    invalidationListeners.add(listener);    
  }

  @Override
  public final void removeListener(InvalidationListener listener) {
    LOGGER.info("<=> " + listener);
    invalidationListeners.remove(listener);    
  }

  @Override
  public final void removeListeners() {
    LOGGER.info("=>");
    invalidationListeners.clear();
    LOGGER.info("<=");
  }
  
  /**
   * Notify the {@code invalidationListeners} that something has changed.
   */
  protected final void notifyListeners() {
    LOGGER.info("=>");
    for (InvalidationListener invalidationListener: invalidationListeners) {
      invalidationListener.invalidated(this);
    }
    LOGGER.info("<=");
  }
  

  /**
   * {@inheritDoc}
   */
  public List<ObjectControl> getObjectControls() {
    throw new UnsupportedOperationException();
  }
}
