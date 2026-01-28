package goedegep.gpx.app;

import java.util.logging.Logger;

import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.MetadataType;

public class GpxAppUtil {
  private static final Logger LOGGER = Logger.getLogger(GpxAppUtil.class.getName());

  /**
   * Get the activity of the GPX Track
   * 
   * @param gpxType the GPX track
   * @return the Activity of the <code>gpxType</code>, or null if this isn't available.
   */
  public static Activity getActivity(GpxType gpxType) {
    MetadataType metadataType = gpxType.getMetadata();
    
    if (metadataType == null) {
      return null;
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
          LOGGER.info("Handling activity: " + activity.name());
          for (String activityText: activity.getGpxKeywords()) {
            LOGGER.info("Checking on activityText: " + activityText);
            if (keyword.equals(activityText)) {
              return activity;
            }
          }
        }
      }
      LOGGER.severe("No Activity found for GPX Keywords '" + keywords + "'");
    }
    
    return null;
  }
}
