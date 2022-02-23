package goedegep.jfx.collections;

import java.util.List;

import javafx.collections.ListChangeListener.Change;

public class ChangeListenerUtil {
  private static String NEWLINE = System.getProperty("line.separator");

  public static <T> void  ListChangeToString(StringBuilder buf, Change<T> change) {
    
    String className = change.getClass().getName();
    buf.append("className: " + className).append(NEWLINE);
    
    while (change.next()) {
      int addedSize = change.getAddedSize();
      buf.append("addedSize: " + addedSize).append(NEWLINE);
      
      buf.append("addedSubList:").append(NEWLINE);
      List<T> addedSubList = change.getAddedSubList();
      for (T t: addedSubList) {
        buf.append("    object: " + t.toString()).append(NEWLINE);
      }
      
      int from = change.getFrom();
      buf.append("from: " + from).append(NEWLINE);
      
      int to = change.getTo();
      buf.append("to: " + to).append(NEWLINE);
      
//      change.getPermutation(i);
      buf.append("list:").append(NEWLINE);
      List<T> list = change.getList();
      for (T t: list) {
        buf.append("    object: " + t.toString()).append(NEWLINE);
      }

      int removedSize = change.getRemovedSize();
      buf.append("removedSize: " + removedSize).append(NEWLINE);
      
      buf.append("removed:").append(NEWLINE);
      List<T> removed = change.getRemoved();
      for (T t: removed) {
        buf.append("    object: " + t.toString()).append(NEWLINE);
      }
    }
    
    buf.append("LAST").append(NEWLINE);
    
    int addedSize = change.getAddedSize();
    buf.append("addedSize: " + addedSize).append(NEWLINE);
    
    buf.append("addedSubList:").append(NEWLINE);
    List<T> addedSubList = change.getAddedSubList();
    for (T t: addedSubList) {
      buf.append("    object: " + t.toString()).append(NEWLINE);
    }
    
    int from = change.getFrom();
    buf.append("from: " + from).append(NEWLINE);
    
    int to = change.getTo();
    buf.append("to: " + to).append(NEWLINE);
    
//    change.getPermutation(i);
    buf.append("list:").append(NEWLINE);
    List<T> list = change.getList();
    for (T t: list) {
      buf.append("    object: " + t.toString()).append(NEWLINE);
    }

    int removedSize = change.getRemovedSize();
    buf.append("removedSize: " + removedSize).append(NEWLINE);
    
    buf.append("removed:").append(NEWLINE);
    List<T> removed = change.getRemoved();
    for (T t: removed) {
      buf.append("    object: " + t.toString()).append(NEWLINE);
    }
    
//    Change c = new ObservableEListChange();
  }
}
