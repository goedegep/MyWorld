package goedegep.vacations.app.guifx;


import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import goedegep.vacations.model.ActivityLabel;
import javafx.scene.image.Image;

/**
 * This class provides Activity icons.
 * <p>
 * Upon creation, it loads the icon resource descriptors from the specified files.<br/>
 * Each icon is loaded when it is first requested (lazy loading).
 */
public class ActivityIcons {
  private static Map<ActivityLabel, String> iconFileNameMap = new HashMap<>();
  private Map<ActivityLabel, Image> iconMap = new HashMap<>();
  
  public ActivityIcons() {
  }
  
  public Image getIcon(ActivityLabel activityLabel) {
    Image icon = iconMap.get(activityLabel);
    if (icon == null) {
      String iconFileName = iconFileNameMap.get(activityLabel);
      icon = new Image(ActivityIcons.class.getResourceAsStream(iconFileName), 36, 36, true, true);
      iconMap.put(activityLabel, icon);
    }
    
    return icon;
  }
  
  public URL getIconUrl(ActivityLabel activityLabel) {
    String activityFileName = iconFileNameMap.get(activityLabel);
    URL iconURL = ActivityIcons.class.getResource(activityFileName);
    
    return iconURL;
  }
  
  public String getIconFileName(ActivityLabel activityLabel) {
    return iconFileNameMap.get(activityLabel);
  }

  static {
    iconFileNameMap.put(ActivityLabel.AUTORIT, "auto-icon-png-7.jpg");
    iconFileNameMap.put(ActivityLabel.TERUGREIS, "auto-icon-png-7.jpg");
    iconFileNameMap.put(ActivityLabel.HEENREIS, "auto-icon-png-7.jpg");
    iconFileNameMap.put(ActivityLabel.WANDELING, "walking-icon-7390.png");
  }

}
