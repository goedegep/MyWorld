package goedegep.jfx.objectcontrols;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

/**
 * This class provides a way to check the status of a number of ObjectControls and other ObjectControlGroups.
 * <p>
 * Instead of checking whether a single ObjectControl is filled in or its data is valid, you can add several ObjectControls to
 * an ObjectControlGroup to check them all at once.<br/>
 * Also a hierarchy of ObjectControlGroups is supported, so you can also add ObjectControlGroups to an ObjectControlGroup.
 * <p>
 * 
 */
public class ObjectControlGroup implements Iterable<ObjectControl<?>>, Observable {
  private static final Logger LOGGER = Logger.getLogger(ObjectControlGroup.class.getName());
  
  /*
   * To be able to provide the status across all ObjectControls, we add an InvalidationListener to each ObjectControl and each ObjectControlGroup.
   */
  
  /**
   * A free text to identify this {@code ObjectControlGroup}
   */
  private String id = null;
  
  /**
   * The list of object controls.
   */
  private List<ObjectControl<?>> objectControls = new ArrayList<>();
  
  /**
   * The list of object control groups.
   */
  private List<ObjectControlGroup> objectControlGroups = new ArrayList<>();
  
  
  /**
   * Indicates whether the control is valid or not.
   */
  private boolean isValid;
  
  /**
   * Indicates whether any control is filled in or not.
   */
  private boolean anyObjectControlFilledIn;
  
  /**
   * Indicates whether any control value (the object) has changed.
   */
  private boolean anyObjectChanged;
  
  /**
   * The first control in the group that is invalid.
   */
  private ObjectControl<?> firstInvalidObjectControl;
  
  /**
   * An {@code InvalidationListener} to listen for changes in any of the controls in the group.
   */
  private InvalidationListener invalidationListener;
  
  /**
   * The listeners for changes in any of the controls of this group.
   */
  private List<InvalidationListener> invalidationListeners = new ArrayList<>();
  
  
  /**
   * Constructor.
   */
  public ObjectControlGroup() {
    invalidationListener = (observable) -> handleChanges(observable);
    
    // make sure the status information is correct for the initially empty group.
    handleChanges();
  }
  
  /**
   * Set the free text to identify this {@code ObjectControlGroup}.
   * 
   * @param id a free text to identify this {@code ObjectControlGroup}.
   */
  public void setId(String id) {
    this.id = id;
  }
  
  /**
   * Get the text to identify this {@code ObjectControlGroup}.
   * 
   * @return the text to identify this {@code ObjectControlGroup}, which may be null.
   */
  public String getId() {
    return id;
  }
  
  /**
   * Add one or more {@code ObjectControl}s for which the status is to be maintained.
   * 
   * @param objectControls The {@code ObjectControl}s for which to maintain the status.
   */
  public void addObjectControls(ObjectControl<?>... objectControls) {
    LOGGER.info("=> ");
    
    for (ObjectControl<?> objectControl: objectControls) {
      objectControl.addListener(invalidationListener);
      this.objectControls.add(objectControl);
    }
    
    handleChanges(objectControls);
  }
  
  /**
   * Remove an {@code ObjectControl} from the container, so that its status is no longer taken into account.
   * 
   * @param objectControl The {@code ObjectControl} that will no longer be checked.
   */
  public void removeObjectControl(ObjectControl<?> objectControl) {
    objectControl.removeListener(invalidationListener);
    objectControls.remove(objectControl);
    
    handleChanges((ObjectControl<?>) null);
  }
  
  /**
   * Add an {@code ObjectControlGroup} for which the status is to be maintained.
   * 
   * @param objectControlGroup The {@code ObjectControlGroup} for which to maintain the status.
   */
  public void addObjectControlGroup(ObjectControlGroup objectControlGroup) {
    LOGGER.info("=>");
    
    objectControlGroup.addListener(invalidationListener);
    objectControlGroups.add(objectControlGroup);
    
    handleChanges(objectControlGroup);
    
    LOGGER.info("<=");
  }

  /**
   * Remove an {@code ObjectControlGroup} from the container, so that its validity etc. are no longer taken into account.
   * 
   * @param objectControlGroup The {@code ObjectControlGroup} that will no longer be checked.
   */
  public void removeObjectControlGroup(ObjectControlGroup objectControlGroup) {
    LOGGER.info("=>");

    objectControlGroup.removeListener(invalidationListener);
    objectControlGroups.remove(objectControlGroup);
    
    handleChanges(objectControlGroup);
    
    LOGGER.info("<=");
  }
  
  /**
   * Remove all {@code ObjectControl}s and all {@code ObjectControlGroup}s.
   */
  public void clear()  {
    
    for (ObjectControl<?> objectControl: objectControls) {
      objectControl.removeListener(invalidationListener);
    }
    
    for (ObjectControlGroup objectControlGroup: objectControlGroups) {
      objectControlGroup.removeListener(invalidationListener);
    }
    
    objectControls.clear();
    objectControlGroups.clear();
    
    handleChanges();
    
    
//    for (ObjectControl<?> objectControl: objectControls) {
//      removeObjectControl(objectControl);
//    }
//    
//    for (ObjectControlGroup objectControlGroup: objectControlGroups) {
//      removeObjectControlGroup(objectControlGroup);
//    }
  }
    
  /**
   * Check whether the whole group is valid.
   * <p>
   * An editor will typically inform the user about the fact that at least one control is invalid.
   * 
   * @return {@code true} if the whole group is valid, {@code false} otherwise.
   */
  public boolean isValid() {
    return isValid;
  }  
  
  public boolean isAnyObjectControlFilledIn() {
    return anyObjectControlFilledIn;
  }

  /**
   * Check whether the value of any {@code ObjectControl} in the group has changed.
   * <p>
   * In an editor you can only update the object being edited if at least one control has a new value.
   * 
   * @return {@code true} if the value of any {@code ObjectControl} in the group has changed, {@code false} otherwise.
   */
  public boolean isAnyObjectChanged() {
    return anyObjectChanged;
  }
  
  /**
   * Get the first invalid control.
   * <p>
   * An editor will usually not list all errors at once. With this method the editor can inform the user about the first invalid control.
   * 
   * @return The first invalid control, or {@code null} if all controls are valid.
   */
  public ObjectControl<?> getFirstInvalidControl() {
    return firstInvalidObjectControl;
  }
  
  /**
   * Handle changes in any of the observed controls.
   *  
   * @param observable the control that was changed.
   */
  private void handleChanges(Observable... observables) {
    
    ObjectControl<?> newfirstInvalidObjectControl = determineValidity();
    boolean newIsValid = newfirstInvalidObjectControl == null;
    boolean newAnyObjectControlFilledIn = determineFilledIn();
    boolean newAnyObjectChanged = determineAnyObjectChanged();
    
    boolean notificationNeeded = false;
    if (newIsValid != isValid  ||
        newAnyObjectControlFilledIn != anyObjectControlFilledIn  ||
        newAnyObjectChanged != anyObjectChanged  ||
        !Objects.equals(newfirstInvalidObjectControl, firstInvalidObjectControl)) {
      notificationNeeded = true;
    }
    
    firstInvalidObjectControl = newfirstInvalidObjectControl;
    isValid = newIsValid;
    anyObjectControlFilledIn = newAnyObjectControlFilledIn;
    anyObjectChanged = newAnyObjectChanged;
    
    if (notificationNeeded) {
      Observable observable = null;

      if (observables.length > 0) {
        observable = observables[0];
      }

      notifyListeners(observable);
    }
  }

  /**
   * Determine whether all ObjectInputs and ObjectInputContainers are valid or not.
   * 
   * @return the first invalid object control, or null if all controls are valid.
   */
  private ObjectControl<?> determineValidity() {
    LOGGER.info("=>");

    ObjectControl<?> invalidObjectControl = null;

    for (ObjectControl<?> objectControl: this) {
      if (!objectControl.isValid()) {
        invalidObjectControl = objectControl;
        LOGGER.info("First invalid ObjectControl: " + objectControl.toString());
        break;
      }
    }

    LOGGER.info("<= " + invalidObjectControl);
    return invalidObjectControl;
  }
  
  /**
   * Check whether any ObjectInput or ObjectInputContainer is filled-in or not.
   * 
   * @return {@code true} if at least one control is filled in, {@code false} otherwise.
   */
  private boolean determineFilledIn() {
    LOGGER.info("=>");

    boolean isFilledIn = false;

    for (ObjectControl<?> objectInput: this) {
      LOGGER.info("objectInput: " + objectInput.getId());
      if (objectInput.isFilledIn()) {
        isFilledIn = true;
        LOGGER.info("Is filled");
        break;
      }
    }

    LOGGER.info("<= " + isFilledIn);
    return isFilledIn;
  }

  /**
   * Check whether any ObjectControl has changed.
   * 
   * @return {@code true} if at least one control has changed, {@code false} otherwise.
   */
  private boolean determineAnyObjectChanged() {
    LOGGER.info("=>");

    boolean isChanged = false;

    for (ObjectControl<?> objectControl: this) {
      if (objectControl.isChanged()) {
        isChanged = true;
        LOGGER.info("First changed ObjectControl: " + objectControl.toString());
        break;
      }
    }

    LOGGER.info("<= " + isChanged);
    return isChanged;
  }

  /**
   * Notify all the registered listeners that something has changed.
   * 
   * @param observable the control that was changed.
   */
  private void notifyListeners(Observable observable) {
    for (InvalidationListener invalidationListener: invalidationListeners) {
      invalidationListener.invalidated(observable);
    }
  }

  @Override
  public Iterator<ObjectControl<?>> iterator() {
    return new ObjectControlGroupIterator(this);
  }

  @Override
  public void addListener(InvalidationListener listener) {
    invalidationListeners.add(listener);
  }

  @Override
  public void removeListener(InvalidationListener listener) {
    invalidationListeners.remove(listener);
  }
  
  public String toString() {
    StringBuilder buf = new StringBuilder();

    for (ObjectControl<?> objectControl: objectControls) {
      buf.append(objectControl.getId() + " - " + objectControl.getClass().getName() + "\n");
    }

    for (ObjectControlGroup objectControlGroup: objectControlGroups) {
      buf.append("ObjectControlGroup\n");
      buf.append(objectControlGroup.toString());
    }

    return buf.toString();
  }



  /**
   * This class provides an {@code Iterator} to iterate over all controls (so also the controls in the sub-groups.
   */
  class ObjectControlGroupIterator implements Iterator<ObjectControl<?>> {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(ObjectControlGroupIterator.class.getName());

    /**
     * Iterator to iterate over the ObjectControls of the groups.
     */
    private Iterator<ObjectControl<?>> objectControlsIterator;

    /**
     * Complete set of all sub groups
     */
    private List<ObjectControlGroup> subObjectControlGroups = null;

    /**
     * Iterator to iterate over the subObjectControlGroups
     */
    private Iterator<ObjectControlGroup> objectControlGroupsIterator;

    private ObjectControlGroup subObjectControlGroup;

    /**
     * Constructor
     * 
     * @param objectControlGroup
     */
    ObjectControlGroupIterator(ObjectControlGroup objectControlGroup) {
      subObjectControlGroups = new ArrayList<>();
      subObjectControlGroups.add(objectControlGroup);
      addControlGroupsToSubObjectControlGroups(objectControlGroup.objectControlGroups);

      objectControlGroupsIterator = subObjectControlGroups.iterator();    
      objectControlsIterator = objectControlGroupsIterator.next().objectControls.iterator();
    }

    /**
     * @InheritDoc
     */
    @Override
    public boolean hasNext() {      
      if (objectControlsIterator.hasNext()) {
        return true;
      } else {
        boolean nextFound = false;
        while (!nextFound  &&  objectControlGroupsIterator.hasNext()) {
          subObjectControlGroup = objectControlGroupsIterator.next();
          objectControlsIterator = subObjectControlGroup.objectControls.iterator();
          if (objectControlsIterator.hasNext()) {
            nextFound = true;
            return true;
          }
        }
      }

      return false;
    }

    /**
     * @InheritDoc
     */
    @Override
    public ObjectControl<?> next() {
      hasNext();

      ObjectControl<?> objectControl = objectControlsIterator.next();
      //    LOGGER.info("=> " + objectControl.toString());

      return objectControl;
    }

    /**
     * Add a set of ObjectControlGroups to the total set of subObjectControlGroups.
     * 
     * @param objectControlGroups the groups to add.
     */
    private void addControlGroupsToSubObjectControlGroups(List<ObjectControlGroup> objectControlGroups) {
      subObjectControlGroups.addAll(objectControlGroups);
      for (ObjectControlGroup objectControlGroup: objectControlGroups) {
        addControlGroupsToSubObjectControlGroups(objectControlGroup.objectControlGroups);
      }
    }
  }  // End of ObjectControlGroupIterator


}

