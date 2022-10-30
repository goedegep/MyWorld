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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;

import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.util.emf.EmfUtil;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

// ObservableList<T>
public class ObservableEList<T> implements ObservableList<T> {
  private static final Logger LOGGER = Logger.getLogger(EObjectTable.class.getName());
  private static final int[] EMPTY_PERM = new int[0];

  private EObjectContainmentEList<T> eObjectContainmentEList;
  private ListListenerHelper<T> listenerHelper;
  private ObservableEList<T> thisList;
  private List<T> presentationList;
  private List<InvalidationListener> tableRefreshNeededListeners = new ArrayList<>();
  
  /**
   * Constructor
   * 
   * @param containingObject
   * @param list
   */
  public ObservableEList(EObject containingObject, EObjectContainmentEList<T> list) {
    this.eObjectContainmentEList = list;
    thisList = this;
    presentationList = list;
    
    LOGGER.info("EObjectTable: Installing adapter");
    Adapter adapter = new EContentAdapter() {
      public void notifyChanged(Notification notification) {
        LOGGER.severe("Notification received: " + notification.toString());
        EObject listEObject = eObjectContainmentEList.getEObject();
        EObject container = listEObject.eContainer();
        if (!notification.getNotifier().equals(eObjectContainmentEList)  && !notification.getNotifier().equals(eObjectContainmentEList.getEObject())) {
          LOGGER.severe("NO ACTION: change not in this list");
          return;
        }
        ObservableEListChange<T> change = null;
        
        switch (notification.getEventType()) {
        case Notification.ADD:
          if (!notification.isTouch()) {
            LOGGER.info("Added item");
            LOGGER.severe(EmfUtil.printNotification(notification));
            change = new ObservableEListChange<T>(notification.getPosition(), notification.getPosition(), new ArrayList<T>(), EMPTY_PERM, false, thisList);
            ListListenerHelper.fireValueChangedEvent(listenerHelper, change);
          } else {
            LOGGER.severe("Notification is touch");
          }
          break;
        
        case Notification.SET:
        case Notification.UNSET:
          if (!notification.isTouch()) {
            LOGGER.info("Changed item");
            // An ObservableList only supports changes in the list, not changes in an item in the list.
            // To keep any table based on this list, we fire an update event on the first row.
            ListListenerHelper.fireValueChangedEvent(listenerHelper, new ObservableEListChange<T>(0, list.size() - 1, new ArrayList<T>(), EMPTY_PERM, true, thisList));
          } else {
            LOGGER.severe("Notification is touch");
          }
          break;
          
        case Notification.REMOVE:
          if (!notification.isTouch()) {
            LOGGER.severe("Removed item");
            // An ObservableList only supports changes in the list, not changes in an item in the list.
            // To keep any table based on this list, we fire an update event on the first row.
            @SuppressWarnings("unchecked")
            T removedObject = (T) notification.getOldValue();
            LOGGER.severe("Removed object: " + removedObject.toString());
            List<T> removedList = List.of(removedObject);
            int presentationIndex = presentationList.indexOf(removedObject);
            ListListenerHelper.fireValueChangedEvent(listenerHelper, new ObservableEListChange<T>(presentationIndex, presentationIndex, removedList, EMPTY_PERM, true, thisList));
          } else {
            LOGGER.severe("Notification is touch");
          }
          break;
          
        case Notification.REMOVE_MANY:
          LOGGER.severe(EmfUtil.printNotification(notification));
//          break;
        
        default:
          throw new RuntimeException ("Event type " + notification.getEventType() + " not supported");
        }
        if (!notification.isTouch()) {
          LOGGER.info("Updating data");
        }
      }

    };
    containingObject.eAdapters().add(adapter);
    
  }
  
  
  /**
   * Constructor
   * 
   * @param containingObject
   * @param list
   */
  public ObservableEList(boolean dummy, EObject containingObject, EReference eReference) {
    
    eObjectContainmentEList = (EObjectContainmentEList<T>) containingObject.eGet(eReference);
    thisList = this;
    presentationList = eObjectContainmentEList;
    
    LOGGER.info("EObjectTable: Installing adapter");
    Adapter adapter = new EContentAdapter() {
      public void notifyChanged(Notification notification) {
        LOGGER.severe("Notification received: " + notification.toString());
        super.notifyChanged(notification);
//        if (!notification.getNotifier().equals(containingObject)  && !notification.getFeature().equals(eReference)) {
//          LOGGER.severe("NO ACTION: change not in this list");
//          return;
//        }
        ObservableEListChange<T> change = null;
        
        switch (notification.getEventType()) {
        case Notification.ADD:
          if (!notification.isTouch()) {
            LOGGER.info("Added item");
            LOGGER.severe(EmfUtil.printNotification(notification));
            change = new ObservableEListChange<T>(notification.getPosition(), notification.getPosition() + 1, new ArrayList<T>(), EMPTY_PERM, false, thisList);
            ListListenerHelper.fireValueChangedEvent(listenerHelper, change);
          } else {
            LOGGER.severe("Notification is touch");
          }
          break;
        
        case Notification.SET:
        case Notification.UNSET:
          if (!notification.isTouch()) {
            LOGGER.severe("Changed item");
            // An ObservableList only supports changes in the list, not changes in an item in the list.
            // To keep any table based on this list, we fire an update event on the first row.
            ListListenerHelper.fireValueChangedEvent(listenerHelper, new ObservableEListChange<T>(0, thisList.size() - 1, new ArrayList<T>(), EMPTY_PERM, true, thisList));
            notifyTableRefreshNeededListeners();
          } else {
            LOGGER.severe("Notification is touch");
          }
          break;
          
        case Notification.REMOVE:
          if (!notification.isTouch()) {
            LOGGER.severe("Removed item");
            @SuppressWarnings("unchecked")
            T removedObject = (T) notification.getOldValue();
            LOGGER.severe("Removed object: " + removedObject.toString());
            List<T> removedList = List.of(removedObject);
            int presentationIndex = presentationList.indexOf(removedObject);
            ListListenerHelper.fireValueChangedEvent(listenerHelper, new ObservableEListChange<T>(presentationIndex, presentationIndex, removedList, EMPTY_PERM, true, thisList));
          } else {
            LOGGER.severe("Notification is touch");
          }
          break;
          
        case Notification.REMOVING_ADAPTER:
          // This is no change in the data, so no action.
          break;
          
        case Notification.REMOVE_MANY:
          notifyTableRefreshNeededListeners();
//          LOGGER.severe(EmfUtil.printNotification(notification));
          break;
        
        default:
          throw new RuntimeException ("Event type " + notification.getEventType() + " not supported");
        }
        if (!notification.isTouch()) {
          LOGGER.info("Updating data");
        }
      }


    };
    containingObject.eAdapters().add(adapter);
    
  }

  private void notifyTableRefreshNeededListeners() {
    for (InvalidationListener listener: tableRefreshNeededListeners) {
      listener.invalidated(thisList);
    }
    
  }
  
  public void addTableRefreshNeededListener(InvalidationListener listener) {
    tableRefreshNeededListeners.add(listener);
  }
  
  
  public void setPresentationList(List<T> presentationList) {
    this.presentationList = presentationList;
  }

  @Override
  public int size() {
    return eObjectContainmentEList.size();
  }

  @Override
  public boolean isEmpty() {
    return eObjectContainmentEList.isEmpty();
  }

  @Override
  public boolean contains(Object object) {
    return eObjectContainmentEList.contains(object);
  }

  @Override
  public Iterator<T> iterator() {
    return eObjectContainmentEList.iterator();
  }

  @Override
  public Object[] toArray() {
    return eObjectContainmentEList.toArray();
  }

  @Override
  public <S> S[] toArray(S[] array) {
    return eObjectContainmentEList.basicToArray(array);
  }

  @Override
  public boolean add(T e) {
    return eObjectContainmentEList.add(e);
  }

  @Override
  public boolean remove(Object o) {
    return eObjectContainmentEList.remove(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return eObjectContainmentEList.basicContainsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    return eObjectContainmentEList.addAll(c);
  }

  @Override
  public boolean addAll(int index, Collection<? extends T> c) {
    return eObjectContainmentEList.addAll(index, c);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return eObjectContainmentEList.removeAll(c);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return eObjectContainmentEList.retainAll(c);
  }

  @Override
  public void clear() {
    eObjectContainmentEList.clear();
  }

  @Override
  public T get(int index) {
    return eObjectContainmentEList.get(index);
  }

  @Override
  public T set(int index, T element) {
    return eObjectContainmentEList.set(index, element);
  }

  @Override
  public void add(int index, T element) {
    eObjectContainmentEList.add(index, element);
    
  }

  @Override
  public T remove(int index) {
    return eObjectContainmentEList.remove(index);
  }

  @Override
  public int indexOf(Object o) {
    return eObjectContainmentEList.indexOf(o);
  }

  @Override
  public int lastIndexOf(Object o) {
    return eObjectContainmentEList.basicLastIndexOf(o);
  }

  @Override
  public ListIterator<T> listIterator() {
    return eObjectContainmentEList.listIterator();
  }

  @Override
  public ListIterator<T> listIterator(int index) {
    if (index == -1) {
      index = 0;
    }
    for (T t: eObjectContainmentEList) {
      LOGGER.severe("T: " + t);
    }
    return eObjectContainmentEList.basicListIterator(index);
  }

  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    return eObjectContainmentEList.subList(fromIndex, toIndex);
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
   * <p>This method is called by the {@code clear} operation on this list
   * and its subLists.  Overriding this method to take advantage of
   * the internals of the list implementation can <i>substantially</i>
   * improve the performance of the {@code clear} operation on this list
   * and its subLists.
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
      for (int i=0, n=toIndex-fromIndex; i<n; i++) {
          it.next();
          it.remove();
      }
  }

}
