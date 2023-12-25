package goedegep.jfx.objectcontrols;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * This class provides a way to check the status of a number of ObjectControls and ObjectControlGroups.
 * <p>
 * Instead of checking whether a single ObjectControl is filled in or its data is valid, you can add several ObjectControls to
 * an ObjectControlGroup to check them all at once.<br/>
 * Also a hierarchy of ObjectControlGroups is supported, so you can also add ObjectControlGroups to an ObjectControlGroup.
 */
public class ObjectControlGroup implements Iterable<ObjectControl<? extends Object>>, Observable {
  private static final Logger LOGGER = Logger.getLogger(ObjectControlGroup.class.getName());
    
  private Map<ObjectControl<?>, InvalidationListener> objectInputInvalidationListeners = new HashMap<>();
  private Map<ObjectControlGroup, InvalidationListener> objectControlGroups = new HashMap<>();
  private BooleanProperty  isValid = new SimpleBooleanProperty();
  private BooleanProperty  anyObjectInputFilledIn = new SimpleBooleanProperty();
  private List<InvalidationListener> invalidationListeners = new ArrayList<>();
  
  
  /**
   * Add an {@code ObjectControl} for which the status is to be checked.
   * 
   * @param objectControl The ObjectInput to check.
   */
  public void addObjectControl(ObjectControl<?> objectControl) {
    LOGGER.info("=> " + (objectControl.getId() != null ? objectControl.getId() : "<null>"));
    
    InvalidationListener invalidationListener = (observable) -> handleChanges(observable);
    objectControl.addListener(invalidationListener);
    objectInputInvalidationListeners.put(objectControl, invalidationListener);
    
    handleChanges(objectControl);
  }
  
  /**
   * Add an {@code ObjectControl} for which the status is to be checked.
   * 
   * @param objectControl The ObjectInput to check.
   */
  public void addObjectControls(ObjectControl<?>... objectControls) {
    LOGGER.info("=> ");
    
    InvalidationListener invalidationListener = (observable) -> handleChanges(observable);
    for (ObjectControl<?> objectControl: objectControls) {
      objectControl.addListener(invalidationListener);
      objectInputInvalidationListeners.put(objectControl, invalidationListener);
    }
    
    handleChanges(objectControls);
  }
  
  /**
   * Remove an ObjectInput from the container, so that its validity is no longer taken into account.
   * 
   * @param objectInput The ObjectInput that will no longer be checked.
   */
  public void removeObjectInput(ObjectControl<?> objectInput) {    
    InvalidationListener invalidationListener = objectInputInvalidationListeners.remove(objectInput);
    objectInput.removeListener(invalidationListener);
    
    handleChanges((ObjectControl<?>) null);
  }
  
  /**
   * Add an ObjectInput for which the validity is to be checked.
   * 
   * @param objectInput The ObjectInput to check.
   */
  public void addObjectControlGroup(ObjectControlGroup objectControlGroup) {
    LOGGER.info("=>");
    
    InvalidationListener invalidationListener = (observable) -> handleChanges(observable);
    objectControlGroup.addListener(invalidationListener);

    objectControlGroups.put(objectControlGroup, invalidationListener);
    
    handleChanges(objectControlGroup);
    
    LOGGER.info("<=");
  }

  /**
   * Remove an ObjectInputContainer from the container, so that its validity is no longer taken into account.
   * 
   * @param objectControlGroup The ObjectInputContainer that will no longer be checked.
   */
  public void removeObjectControlGroup(ObjectControlGroup objectControlGroup) {
    LOGGER.info("=>");

    BooleanProperty inputIsValidProperty = objectControlGroup.isValid();
    InvalidationListener changeListener = objectControlGroups.get(objectControlGroup);
    inputIsValidProperty.removeListener(changeListener);
    objectControlGroups.remove(objectControlGroup);
    updateValidity();
    
    LOGGER.info("<=");
  }
  
  /**
   * Get the {@code isValid} property.
   * 
   * @return the {@code isValid} property.
   */
  public BooleanProperty isValid() {
    return isValid;
  }
  
  /**
   * Get the value of the {@code isValid} property.
   * 
   * @return the value of the {@code isValid} property.
   */
  public boolean getIsValid() {
    return isValid.get();
  }
  
  /**
   * Get the {@code anyObjectInputFilledIn} property.
   * 
   * @return the {@code anyObjectInputFilledIn} property.
   */
  public BooleanProperty anyObjectInputFilledInProperty() {
    return anyObjectInputFilledIn;
  }
  
  /**
   * Handle changes in any of the observed controls.
   *  
   * @param observable the control that was changed.
   */
  private void handleChanges(Observable... observables) {
    
    updateValidity();
    updateFilledIn();
    
    Observable observable = null;
    
    if (observables.length > 0) {
      observable = observables[0];
    }
    
    notifyListeners(observable);
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
     
  /**
   * Check whether all ObjectInputs and ObjectInputContainers are valid or not.
   * The isValidProperty is set to true if all are valid, false otherwise.
   */
  private void updateValidity() {
    LOGGER.info("=>");

    boolean isValidValue = true;
    
    for (ObjectControl<?> objectControl: this) {
      if (!objectControl.isValid()) {
        isValidValue = false;
        LOGGER.info("First invalid ObjectControl: " + objectControl.toString());
        break;
      }
    }
        
    isValid.set(isValidValue);
  
    LOGGER.info("=>");
  }
  
  /**
   * Check whether any ObjectInput or ObjectInputContainer is filled-in or not.
   * The anyObjectInputFilledInProperty is set to true if any control is filled-in, false otherwise.
   */
  private void updateFilledIn() {
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

    anyObjectInputFilledIn.set(isFilledIn);

    LOGGER.info("<=");
  }

  @Override
  public Iterator<ObjectControl<? extends Object>> iterator() {
    return new ObjectControlGroupIterator(this);
  }
  
  public Set<ObjectControl<?>> getObjectControls() {
    return objectInputInvalidationListeners.keySet();
  }
  
  public Set<ObjectControlGroup> getObjectControlGroups() {
    return objectControlGroups.keySet();
  }

  public boolean isAnyObjectInputFilledIn() {
    Iterator<ObjectControl<?>> iterator = iterator();
    
    while (iterator.hasNext()) {
      ObjectControl<?> objectInput = iterator.next();
      if (objectInput.isFilledIn()) {
        return true;
      }
    }
    
    return false;
  }

  public boolean isAnyObjectControlChanged() {
    Iterator<ObjectControl<?>> iterator = iterator();
    
    while (iterator.hasNext()) {
      ObjectControl<?> objectControl = iterator.next();
      if (objectControl.isChanged()) {
        LOGGER.info("First Object control changed: " + objectControl);
        objectControl.isChanged();
        return true;
      } else {
        LOGGER.info("Object control not changed: " + objectControl);
      }
    }
    
    return false;
  }

  @Override
  public void addListener(InvalidationListener listener) {
    invalidationListeners.add(listener);
  }

  @Override
  public void removeListener(InvalidationListener listener) {
    invalidationListeners.remove(listener);
  }
}

/**
 * This class provides an {@code Iterator} to iterate over all controls (so also the controls in the sub-groups.
 */
class ObjectControlGroupIterator implements Iterator<ObjectControl<? extends Object>> {
//  private boolean iterateOverControls = true; // if false, we're iterating over the groups.
//  private ObjectControlGroup objectControlGroup;
  
  /**
   * Iterator to iterate over the ObjectControls of the groups.
   */
  private Iterator<ObjectControl<?>> objectControlsIterator;
  
  /**
   * Complete set of all sub groups
   */
  private Set<ObjectControlGroup> subObjectControlGroups = null;
  
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
    subObjectControlGroups = new HashSet<>();
    subObjectControlGroups.add(objectControlGroup);
    addControlGroupsToSubObjectControlGroups(objectControlGroup.getObjectControlGroups());
    
    objectControlGroupsIterator = subObjectControlGroups.iterator();    
    objectControlsIterator = objectControlGroupsIterator.next().getObjectControls().iterator();
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
        objectControlsIterator = subObjectControlGroup.getObjectControls().iterator();
        if (objectControlsIterator.hasNext()) {
          nextFound = true;
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
    
    return objectControlsIterator.next();
  }
  
  /**
   * Add a set of ObjectControlGroups to the total set of subObjectControlGroups.
   * 
   * @param objectControlGroups the groups to add.
   */
  private void addControlGroupsToSubObjectControlGroups(Set<ObjectControlGroup> objectControlGroups) {
    subObjectControlGroups.addAll(objectControlGroups);
    for (ObjectControlGroup objectControlGroup: objectControlGroups) {
      addControlGroupsToSubObjectControlGroups(objectControlGroup.getObjectControlGroups());
    }
  }
    
}
