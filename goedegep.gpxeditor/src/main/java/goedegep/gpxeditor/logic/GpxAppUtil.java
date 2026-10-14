package goedegep.gpxeditor.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.MetadataType;

public class GpxAppUtil {
  private static final Logger LOGGER = Logger.getLogger(GpxAppUtil.class.getName());

  /**
   * Get the activities of a GPX Track.
   * <p>
   * Note: moving (as keyword) is not considered an activity, as it doesn't add any information.
   * 
   * @param gpxType the GPX track
   * @return the Activities of the {@code gpxType}. If no activities are found, an empty list is returned.
   */
  public static List<Activity> getActivities(GpxType gpxType) {
    List<Activity> activities = new ArrayList<>();
    MetadataType metadataType = gpxType.getMetadata();
    
    if (metadataType == null) {
      return activities;
    }
    
    String keywords = metadataType.getKeywords();

    if ((keywords != null)  &&  !keywords.isEmpty()) {
      LOGGER.info("keywords not empty: " + keywords);
      for (String keyword: keywords.split(",")) {
        keyword = keyword.trim().toLowerCase();
        LOGGER.info("Handling keyword: " + keyword);
        
        // no activity for 'Moving' as doesn't add any information
        if ("moving".equals(keyword)) {
          return null;
        }
        
        for (Activity activity: Activity.values()) {
          boolean activityFound = false;
          LOGGER.info("Handling activity: " + activity.name());
          for (String activityText: activity.getGpxKeywords()) {
            LOGGER.info("Checking on activityText: " + activityText);
            if (keyword.equals(activityText)) {
              activities.add(activity);
              activityFound = true;
              break;
            }
          }
          if (!activityFound) {
            LOGGER.info("No Activity found for GPX Keyword '" + keyword + "'");
          }
        }
      }
    }
    
    return activities;
  }
}
