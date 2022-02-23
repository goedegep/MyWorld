package goedegep.jfx.observableelist;

import java.util.List;

import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;

public class ObservableEListChange<E> extends Change<E> {
  private int from;
  private int to;
  private List<E> removed;
  private int[] perm;
  private boolean updated;
  private boolean onChange;  // if true, next() has been called

  public ObservableEListChange(int from, int to, List<E> removed, int[] perm, boolean updated, ObservableList<E> list) {
      super(list);
      
      this.from = from;
      this.to = to;
      this.removed = removed;
      this.perm = perm;
      this.updated = updated;
  }

  @Override
  public boolean next() {
      if (onChange) {
          return false;
      }
      onChange = true;
      return true;
  }

  @Override
  public void reset() {
      onChange = false;
  }

  @Override
  public int getFrom() {
      checkState();
      return from;
  }

  @Override
  public int getTo() {
      checkState();
      return to;
  }

  @Override
  public List<E> getRemoved() {
      checkState();
      return removed;
  }

  @Override
  protected int[] getPermutation() {
      checkState();
      return perm;
  }

  @Override
  public boolean wasUpdated() {
      checkState();
      return updated;
  }

  private void checkState() {
      if (!onChange) {
          throw new IllegalStateException("Invalid Change state: next() must be called before inspecting the Change.");
      }
  }

  @Override
  public String toString() {
      String ret;
      if (perm.length != 0) {
          ret = ChangeHelper.permChangeToString(perm);
      } else if (updated) {
          ret = ChangeHelper.updateChangeToString(from, to);
      } else {
          ret = ChangeHelper.addRemoveChangeToString(from, to, getList(), removed);
      }
      return "{ " + ret + " }";
  }

}
