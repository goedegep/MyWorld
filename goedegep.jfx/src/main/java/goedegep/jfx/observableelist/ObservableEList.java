package goedegep.jfx.observableelist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EContentAdapter;

import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.util.emf.EmfUtil;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * This class wraps an EObjectContainmentEList into an ObservableList.
 *
 * @param <T> the object type of the elements in the list
 */
public class ObservableEList<T> implements ObservableList<T> {
  private static final Logger LOGGER = Logger.getLogger(EObjectTable.class.getName());
  private static final int[] EMPTY_PERM = new int[0];

  private EObject containingObject;
  private EList<T> eList;
  private ListListenerHelper<T> listenerHelper;
  private ObservableEList<T> thisList;
//  private List<T> presentationList;
  private List<InvalidationListener> tableRefreshNeededListeners = new ArrayList<>();
  
  // For handling remove(from, to), which is not available on EList and List
  private RemoveRangeState removeRangeState = null;
  private List<T> removedRangeList = null;
    
  
  /**
   * Constructor
   * 
   * @param containingObject
   * @param list
   */
  @SuppressWarnings("unchecked")
  public ObservableEList(boolean dummy, EObject containingObject, EReference eReference) {
    this.containingObject = containingObject;
    Object referredObject = containingObject.eGet(eReference);
    if (!(referredObject instanceof EList)) {
      throw new IllegalArgumentException("eReference doesn't refer to an EList");
    }
    eList = (EList<T>) referredObject;
    thisList = this;
//    presentationList = eList;
    
    LOGGER.info("EObjectTable: Installing adapter");
    Adapter adapter = new EContentAdapter() {
      public void notifyChanged(Notification notification) {
        LOGGER.info("Notification received: " + EmfUtil.printNotification(notification));
        super.notifyChanged(notification);
        ObservableEListChange<T> change = null;
        
        switch (notification.getEventType()) {
        case Notification.ADD:
          // for ADD isTouch() is always false
          LOGGER.info("Added item");
          LOGGER.info(EmfUtil.printNotification(notification));
          // if the notification is from this object, than a normal add. Else it can come from a child, which means the element is updated. Or from outside our list.
          if (notification.getFeature().equals(eReference)) {
            change = new ObservableEListChange<T>(notification.getPosition(), notification.getPosition() + 1, new ArrayList<T>(), EMPTY_PERM, false, thisList);
            ListListenerHelper.fireValueChangedEvent(listenerHelper, change);
          } else {
            int index = getDecendentIndex(notification);
            if (index != -1) {
              ListListenerHelper.fireValueChangedEvent(listenerHelper, new ObservableEListChange<T>(index, index + 1, new ArrayList<T>(), EMPTY_PERM, true, thisList));
            }
          }
          break;
          
        case Notification.ADD_MANY:
          // for ADD isTouch() is always false
          LOGGER.info("Added many items");
          LOGGER.severe(EmfUtil.printNotification(notification));
          // if the notification is from this object, than a normal add many. Else it comes from a child, which means the element is updated.
          if (notification.getFeature().equals(eReference)) {
            change = new ObservableEListChange<T>(notification.getPosition(), notification.getPosition() + ((List<T>) notification.getNewValue()).size(), new ArrayList<T>(), EMPTY_PERM, false, thisList);
            ListListenerHelper.fireValueChangedEvent(listenerHelper, change);
          } else {
            throw new RuntimeException("ADD_MANY not supported for this eReference");
          }
          break;
        
        case Notification.SET:
        case Notification.UNSET:
          if (!notification.isTouch()) {
            LOGGER.info("Changed item");
            if (notification.getFeature().equals(eReference)) {
              ListListenerHelper.fireValueChangedEvent(listenerHelper, new ObservableEListChange<T>(0, thisList.size() - 1, new ArrayList<T>(), EMPTY_PERM, true, thisList));
            } else {
              int index = getDecendentIndex(notification);
              ListListenerHelper.fireValueChangedEvent(listenerHelper, new ObservableEListChange<T>(index, index + 1, new ArrayList<T>(), EMPTY_PERM, true, thisList));
            }
          } else {
            LOGGER.severe("Notification is touch");
          }
          break;
          
        case Notification.REMOVE:
          // for REMOVE isTouch() is always false
          LOGGER.severe("Removed item");
          // if the notification is from this object, than a normal add many. Else it comes from a child, which means the element is updated.
          if (notification.getFeature().equals(eReference)) {
            T removedObject = (T) notification.getOldValue();
            if (removeRangeState == null) {
              List<T> removedList = List.of(removedObject);
              ListListenerHelper.fireValueChangedEvent(listenerHelper, new ObservableEListChange<T>(notification.getPosition(), notification.getPosition(), removedList, EMPTY_PERM, false, thisList));
            } else {
              switch (removeRangeState) {
              case FIRST:
                removedRangeList = new ArrayList<>();
                removedRangeList.add(removedObject);
                removeRangeState = RemoveRangeState.HANDLING;
                break;
                
              case HANDLING:
                removedRangeList.add(removedObject);
                break;
                
              case LAST:
                removedRangeList.add(removedObject);
                ListListenerHelper.fireValueChangedEvent(listenerHelper, new ObservableEListChange<T>(0, 0, removedRangeList, EMPTY_PERM, false, thisList));
                removeRangeState = null;
                removedRangeList = null;
                break;
              }
            }
          } else {
            int index = getDecendentIndex(notification);
            ListListenerHelper.fireValueChangedEvent(listenerHelper, new ObservableEListChange<T>(index, index + 1, new ArrayList<T>(), EMPTY_PERM, true, thisList));
          }
          break;
          
        case Notification.REMOVING_ADAPTER:
          // This is no change in the data, so no action.
          break;
          
        case Notification.REMOVE_MANY:
          // for REMOVE isTouch() is always false
          LOGGER.info("Removed many items");
          // if the notification is from this object, than a normal add many. Else it comes from a child, which means the element is updated.
          if (notification.getFeature().equals(eReference)) {
            if (notification.getPosition() == -1) {  // clear()
              List<T> removedManyList = (List<T>) notification.getOldValue();
              ListListenerHelper.fireValueChangedEvent(listenerHelper, new ObservableEListChange<T>(0, 0, removedManyList, EMPTY_PERM, false, thisList));
            } else {
              IterableObservableEListChange<T> iterableChange = new IterableObservableEListChange<>(thisList);
              int[] positions =  (int[]) notification.getNewValue();
              List<T> removedItems = (List<T>) notification.getOldValue();
              for (int i = 0; i < positions.length; i++) {
                int position = positions[i] - i;
                T removedObject = removedItems.get(i);
                List<T> removedList = List.of(removedObject);
                iterableChange.addChange(position, position, removedList, EMPTY_PERM, false);
              }
              ListListenerHelper.fireValueChangedEvent(listenerHelper, iterableChange);
            }
          } else {
            int index = getDecendentIndex(notification);
            ListListenerHelper.fireValueChangedEvent(listenerHelper, new ObservableEListChange<T>(index, index + 1, new ArrayList<T>(), EMPTY_PERM, true, thisList));
          }
          break;
        
        default:
          throw new RuntimeException ("Event type " + notification.getEventType() + " not supported");
        }
      }

    };
    
    containingObject.eAdapters().add(adapter);
    
  }
  
  /**
   * Get the index of the element in the {@code eList} for which a descendent fired the {@code Notification}
   * 
   * @param notification the {@code Notification}
   * @return the index of the element in the {@code eList} of which a decendent fired the {@code notification}.
   */
  private int getDecendentIndex(Notification notification) {
    Object notifier = notification.getNotifier();
    if (notifier instanceof EObject eObject) {
      EObject container = eObject.eContainer();
      while (container != containingObject  &&  container != null) {
        eObject = container;
        container = eObject.eContainer();
      }
      int index = -1;
      if (container != null) {
        index = eList.indexOf(eObject);
      }
      return index;
    } else {
      throw new RuntimeException("notifier isn't an EObject");
    }
    
  }

//  private void notifyTableRefreshNeededListeners() {
//    for (InvalidationListener listener: tableRefreshNeededListeners) {
//      listener.invalidated(thisList);
//    }
//    
//  }
  
  public void addTableRefreshNeededListener(InvalidationListener listener) {
    tableRefreshNeededListeners.add(listener);
  }
  
  
//  public void setPresentationList(List<T> presentationList) {
//    this.presentationList = presentationList;
//  }

  @Override
  public int size() {
    return eList.size();
  }

  @Override
  public boolean isEmpty() {
    return eList.isEmpty();
  }

  @Override
  public boolean contains(Object object) {
    return eList.contains(object);
  }

  @Override
  public Iterator<T> iterator() {
    return eList.iterator();
  }

  @Override
  public Object[] toArray() {
    return eList.toArray();
  }

  @SuppressWarnings("unchecked")
  @Override
  public <S> S[] toArray(S[] array) {
    return (S[]) eList.toArray();
  }

  @Override
  public boolean add(T e) {
    return eList.add(e);
  }

  @Override
  public boolean remove(Object o) {
    return eList.remove(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return eList.containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    return eList.addAll(c);
  }

  @Override
  public boolean addAll(int index, Collection<? extends T> c) {
    return eList.addAll(index, c);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return eList.removeAll(c);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return eList.retainAll(c);
  }

  @Override
  public void clear() {
    eList.clear();
  }

  @Override
  public T get(int index) {
    return eList.get(index);
  }

  @Override
  public T set(int index, T element) {
    return eList.set(index, element);
  }

  @Override
  public void add(int index, T element) {
    eList.add(index, element);
    
  }

  @Override
  public T remove(int index) {
    return eList.remove(index);
  }

  @Override
  public int indexOf(Object o) {
    return eList.indexOf(o);
  }

  @Override
  public int lastIndexOf(Object o) {
    return eList.lastIndexOf(o);
  }

  @Override
  public ListIterator<T> listIterator() {
    return eList.listIterator();
  }

  @Override
  public ListIterator<T> listIterator(int index) {
    if (index == -1) {
      index = 0;
    }
    return eList.listIterator(index);
  }

  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    return eList.subList(fromIndex, toIndex);
  }

  @Override
  public void addListener(InvalidationListener listener) {
    listenerHelper = ListListenerHelper.addListener(listenerHelper, listener);
  }

  @Override
  public void removeListener(InvalidationListener listener) {
    listenerHelper = ListListenerHelper.removeListener(listenerHelper, listener);
  }

  @Override
  public void addListener(ListChangeListener<? super T> listener) {
    listenerHelper = ListListenerHelper.addListener(listenerHelper, listener);
  }

  @Override
  public void removeListener(ListChangeListener<? super T> listener) {
    listenerHelper = ListListenerHelper.removeListener(listenerHelper, listener);
  }

  @Override
  public boolean addAll(@SuppressWarnings("unchecked") T... elements) {
    return addAll(Arrays.asList(elements));
  }

  @Override
  public boolean setAll(@SuppressWarnings("unchecked") T... elements) {
    return setAll(Arrays.asList(elements));
  }

  @Override
  public boolean setAll(Collection<? extends T> col) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeAll(@SuppressWarnings("unchecked") T... elements) {
    return removeAll(Arrays.asList(elements));
  }

  @Override
  public boolean retainAll(@SuppressWarnings("unchecked") T... elements) {
    return retainAll(Arrays.asList(elements));
  }

  @Override
  public void remove(int from, int to) {
    removeRange(from, to);
  }

  // Copied from java.util.AbstractList
  /**
   * Removes from this list all of the elements whose index is between
   * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.
   * Shifts any succeeding elements to the left (reduces their index).
   * This call shortens the list by {@code (toIndex - fromIndex)} elements.
   * (If {@code toIndex==fromIndex}, this operation has no effect.)
   *
   * @implSpec
   * This implementation gets a list iterator positioned before
   * {@code fromIndex}, and repeatedly calls {@code ListIterator.next}
   * followed by {@code ListIterator.remove} until the entire range has
   * been removed.  <b>Note: if {@code ListIterator.remove} requires linear
   * time, this implementation requires quadratic time.</b>
   *
   * @param fromIndex index of first element to be removed
   * @param toIndex index after last element to be removed
   */
  protected void removeRange(int fromIndex, int toIndex) {
    ListIterator<T> it = listIterator(fromIndex);
    int n = toIndex - fromIndex;
    removeRangeState = RemoveRangeState.FIRST;
    for (int i = 0; i < n; i++) {
      it.next();
      if (i == n - 1) {
        removeRangeState = RemoveRangeState.LAST;
      }
      it.remove();
    }
  }

}

enum RemoveRangeState {
  FIRST,     // first remove of many, initialize removed object list and add removed object
  HANDLING,  // add removed object
  LAST       // add removed object, fire change, set removed object list to null         
}
