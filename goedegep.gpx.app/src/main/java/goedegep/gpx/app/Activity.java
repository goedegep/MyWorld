package goedegep.gpx.app;

import java.net.URL;

import goedegep.resources.ImageResource;
import goedegep.resources.ImageSize;
import javafx.scene.image.Image;

/**
 * This class defines activities like cycling, walking, etc.
 * <p>
 * For each activity the following are provided:
 * <ul>
 * <li>
 * GPX keywords<br/>
 * The keywords that may be used in a GPX file to mark this file as being this activity.
 * </li>
 * <li>
 * ImageResource<br/>
 * The {@link ImageResource} that can be used for this activity.
 * </li>
 * </ul>
 */
public enum Activity {
  CANOEING(new String[] {"kanotocht"}, ImageResource.CANOEING),
  CAR_RIDE(new String[] {"autorit", "driving"}, ImageResource.CAR),
  BUS_RIDE(new String[] {"busrit", "on a bus"}, ImageResource.BUS),
  CYCLING(new String[] {"fietstocht"}, ImageResource.CYCLING),
  FLYING(new String[] {"vliegen", "flying"}, ImageResource.PLANE_TAKEOFF),
  SKIÏNG(new String[] {"skiën"}, ImageResource.SKIÏNG),
  WALKING(new String[] {"wandeling"}, ImageResource.WALKING);
  
  private String[] gpxKeywords;
  private ImageResource imageResource;
  
  Activity(String[] gpxKeywords, ImageResource imageId) {
    this.gpxKeywords = gpxKeywords;
    this.imageResource = imageId;
  }

  public String[] getGpxKeywords() {
    return gpxKeywords;
  }

  public ImageResource getImageId() {
    return imageResource;
  }
  
  public Image getIcon() {
    return imageResource.getImage(32,32);
  }
  
  public URL getIconUrl(ImageSize imageSize) {
    return imageResource.getImageUrl(imageSize);
  }
  
}
