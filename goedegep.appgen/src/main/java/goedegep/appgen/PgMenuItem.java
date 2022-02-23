package goedegep.appgen;

import javax.swing.Icon;

@SuppressWarnings("serial")
public class PgMenuItem extends javax.swing.JMenuItem {
  Icon disabledIcon = null;
  
  public PgMenuItem(String text) {
    super(text);
  }
  
  public PgMenuItem(String text, Icon icon) {
    super(text, icon);
  }
  
  @Override
  public void setDisabledIcon(Icon disabledIcon) {
    this.disabledIcon = disabledIcon;
  }
  
  public Icon getIcon() {
    if (!isEnabled() && (disabledIcon != null)) {
      return disabledIcon;
    } else {
      return super.getIcon();
    }
  }
}
