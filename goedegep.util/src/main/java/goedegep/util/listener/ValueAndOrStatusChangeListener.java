package goedegep.util.listener;

@FunctionalInterface
public interface ValueAndOrStatusChangeListener {
  void valueAndOrStatusChanged(boolean valueChanged, boolean statusChanged);
}
