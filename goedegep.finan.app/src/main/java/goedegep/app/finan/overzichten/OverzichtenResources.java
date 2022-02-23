package goedegep.app.finan.overzichten;

import javax.swing.ImageIcon;

public class OverzichtenResources {
  static ImageIcon  overzichtenIcon = null;

  static public ImageIcon getOverzichtenIcon() {
    if (overzichtenIcon == null) {
      overzichtenIcon = new ImageIcon(OverzichtenResources.class.getResource("OverzichtenIcon.png"));
    }

    return overzichtenIcon;
  }
}
