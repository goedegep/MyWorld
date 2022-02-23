package goedegep.appgen;

import javax.swing.Icon;

public class DataWithIcon {
  protected Object data;
  protected Icon icon;
  
  public DataWithIcon(Object data, Icon icon) {
    this.data = data;
    this.icon = icon;
  }

  public Object getData() {
    return data;
  }

  public Icon getIcon() {
    return icon;
  }
  
  public String toString() {
    return data.toString();
  }
}
