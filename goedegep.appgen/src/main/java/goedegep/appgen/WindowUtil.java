package goedegep.appgen;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * This is a utility class with methods which operate on windows (Window, JFrame and JDialog).
 *
 */
public class WindowUtil {
  @SuppressWarnings("unused")
  private static final Logger     LOGGER = Logger.getLogger(WindowUtil.class.getName());
  
  public static void showFrameCenteredOnScreen(JFrame frame, int shiftX, int shiftY) {

    frame.validate();
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    frame.setLocation((screenSize.width - frameSize.width) / 2 + shiftX,
        (screenSize.height - frameSize.height) / 2 + shiftY);
    frame.setVisible(true);
  }
  
  public static void showFrameCenteredOnScreen(JFrame frame) {
    showFrameCenteredOnScreen(frame, 0, 0);
  }
  
  public static void showDialogCenteredOnParent(Window parent, JDialog dialog) {
    Dimension dialogSize = dialog.getPreferredSize();
    Dimension frameSize = parent.getSize();
    Point parentLocation = parent.getLocation();
    dialog.setLocation((frameSize.width - dialogSize.width) / 2 + parentLocation.x,
        (frameSize.height - dialogSize.height) / 2 + parentLocation.y);
    dialog.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
    dialog.setVisible(true);
  }
  
  public static boolean isPointWithinComponents(Point point, List<Component> components)
  {
      for (Component component: components) {
        if (isMouseWithinComponent(point, component)) {
          return true;
        }
      }
      
      return false;
  }
  
  public static boolean isMouseWithinComponent(Point point, Component component)
  {
      Rectangle bounds = component.getBounds();
      bounds.setLocation(component.getLocationOnScreen());
      
      return bounds.contains(point);
  }
}
