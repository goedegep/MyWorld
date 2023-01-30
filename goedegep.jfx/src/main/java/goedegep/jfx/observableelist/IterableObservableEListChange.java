package goedegep.jfx.observableelist;

import java.util.ArrayList;
import java.util.List;


import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;

public class IterableObservableEListChange<E> extends Change<E> {

  private List<SubChange<E>> changes;
  private int cursor = -1;

//  private IterableObservableEListChange(SubChange[] changes, ObservableList<E> list) {
//      super(list);
//      this.changes = changes;
//  }

  public IterableObservableEListChange(ObservableList<E> list) {
      super(list);
      changes = new ArrayList<>();
  }
  
  public void addChange(int from, int to, List<E> removed, int[] perm, boolean updated) {
    SubChange<E> subChange = new SubChange<>(from, to, removed, perm, updated);
    changes.add(subChange);
  }

  @Override
  public boolean next() {
      if (cursor + 1 < changes.size()) {
          ++cursor;
          return true;
      }
      return false;
  }

  @Override
  public void reset() {
      cursor = -1;
  }

  @Override
  public int getFrom() {
      checkState();
      return changes.get(cursor).from;
  }

  @Override
  public int getTo() {
      checkState();
      return changes.get(cursor).to;
  }

  @Override
  public List<E> getRemoved() {
      checkState();
      return changes.get(cursor).removed;
  }

  @Override
  protected int[] getPermutation() {
      checkState();
      return changes.get(cursor).perm;
  }

  @Override
  public boolean wasUpdated() {
      checkState();
      return changes.get(cursor).updated;
  }

  private void checkState() {
      if (cursor == -1) {
          throw new IllegalStateException("Invalid Change state: next() must be called before inspecting the Change.");
      }
  }

  @Override
  public String toString() {
      int c = 0;
      StringBuilder b = new StringBuilder();
      b.append("{ ");
      while (c < changes.size()) {
          if (changes.get(c).perm.length != 0) {
              b.append(ChangeHelper.permChangeToString(changes.get(c).perm));
          } else if (changes.get(c).updated) {
              b.append(ChangeHelper.updateChangeToString(changes.get(c).from, changes.get(c).to));
          } else {
              b.append(ChangeHelper.addRemoveChangeToString(changes.get(c).from, changes.get(c).to, getList(), changes.get(c).removed));
          }
          if (c != changes.size() - 1) {
              b.append(", ");
          }
          ++c;
      }
      b.append(" }");
      return b.toString();
  }

  private static class SubChange<E> {

    int from, to;
    List<E> removed;
    int[] perm;
    boolean updated;

    public SubChange(int from, int to, List<E> removed, int[] perm, boolean updated) {
      this.from = from;
      this.to = to;
      this.removed = removed;
      this.perm = perm;
      this.updated = updated;
    }
  }
}
