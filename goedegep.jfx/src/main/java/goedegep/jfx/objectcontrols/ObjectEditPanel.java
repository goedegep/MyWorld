package goedegep.jfx.objectcontrols;

/**
 * This is the interface for a panel for editing a (child) object. Such a panel is used as a part of an object editor.
 * <p>
 * <h3>ObjectControlsGroup</h3>
 * An implementation creates his own ObjectControlGroup, which can be obtained via getObjectControlsGroup().
 */
public interface ObjectEditPanel<T> extends ObjectControlStatus {
  
  ObjectControlGroup getObjectControlsGroup();

  
}
