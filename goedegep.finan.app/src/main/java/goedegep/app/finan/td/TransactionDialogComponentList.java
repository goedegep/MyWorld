package goedegep.app.finan.td;


import java.awt.Component;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * <p>@author Peter Goedegebure</p>
 * <p>@version 0.1</p>
 * <p> </p>
 * <p> </p>
 *  not attributable
 *
 */

@SuppressWarnings("serial")
public class TransactionDialogComponentList extends LinkedList<DC<? extends Component>> {
  private static final Logger         LOGGER = Logger.getLogger(TransactionDialogComponentList.class.getName());

  public TransactionDialogComponentList() {
  }
  
  public DC<? extends Component> get(String componentName) {
    for (DC<? extends Component> dc: this) {
      if (dc.getComponentDescriptor().getName().compareTo(componentName) == 0) {
        return dc;
      }
    }
    return null;
  }

  public DC<? extends Component> getNext(DC<? extends Component> dc) {
    if (dc == null) {
      throw new IllegalArgumentException();
    }
    int index = indexOf(dc) + 1;

    if (index < this.size()) {
      return get(index);
    } else {
      return null;
    }
  }

  public void truncateFrom(DC<? extends Component> c) {
    LOGGER.info("Deleting from component: " + c.getComponentDescriptor().getName());
    while (!getLast().equals(c)) {
      LOGGER.info("Removing " +
                         (getLast()).getComponentDescriptor().getName());
      removeLast();
    }
    removeLast();
  }
}