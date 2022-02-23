package goedegep.appgen;

import javax.swing.Icon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class PgButton extends JButton {
  Icon disabledIcon = null;
  
//  public PgButton(String text, Icon icon) {
//    super(text, icon);
//  }
  
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
