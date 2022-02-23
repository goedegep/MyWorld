package goedegep.appgen;

import java.util.logging.Logger;

/**
 * This class is meant to be used to render nodes in a tree as check boxes.
 * This class adds a 'selected' attribute to a user object. This class is set as the user object of a DefaultMutableTreeNode.
 * @author Peter
 *
 */
public class CheckBoxTreeNodeUserObjectWrapper {
  private static final Logger LOGGER = Logger.getLogger(CheckBoxTreeNodeUserObjectWrapper.class.getName());

  private Object userObject;
  private boolean selected;

  public CheckBoxTreeNodeUserObjectWrapper(Object userObject) {
    this(userObject, false);
  }

  public CheckBoxTreeNodeUserObjectWrapper(Object userObject, boolean selected) {
    LOGGER.fine("=> userObject=" + userObject + ", selected=" + selected);
    
    this.userObject = userObject;
    this.selected = selected;

    LOGGER.fine("<=");
  }

  public Object getUserObject() {
    return userObject;
  }

  public void setSelected(boolean selected) {
    LOGGER.severe("=> selected=" + selected + "(userObject=" + userObject);
    
    this.selected = selected;

    LOGGER.fine("<=");
  }

  public boolean isSelected() {
    LOGGER.fine("<=> " + selected);
    return selected;
  }
  
  public String toString() {
    return "selected=" + selected + ", userObject=" + userObject.toString();
  }
}
