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
  BOAT_TRIP(new String[] {"boottocht", "on a ferry", "boating"}, ImageResource.BOAT),
  CANOEING(new String[] {"kanotocht"}, ImageResource.CANOEING),
  CABLE_CAR(new String[] {"kabelbaan", "cable car", "in a gondola lift"}, ImageResource.CABLE_CAR),
  CAR_RIDE(new String[] {"autorit", "driving"}, ImageResource.CAR),
  BUS_RIDE(new String[] {"busrit", "on a bus"}, ImageResource.BUS),
  CYCLING(new String[] {"fietstocht", "cycling"}, ImageResource.CYCLING),
  FLYING(new String[] {"vliegen", "flying"}, ImageResource.PLANE_TAKEOFF),
  HORSE_RIDING(new String[] {"paardrijden", "horseriding"}, ImageResource.HORSE_RIDING),
  MOTOR_CYCLING(new String[] {"motorrijden", "motorcycling"}, ImageResource.MOTORCYCLE),
  RUNNING(new String[] {"hardlopen", "running"}, ImageResource.RUNNING),
  SKIÏNG(new String[] {"skiën"}, ImageResource.SKIÏNG),
  TRAIN_RIDE(new String[] {"treinrit", "on a train", "on the subway"}, ImageResource.TRAIN),
  WALKING(new String[] {"wandeling", "walking"}, ImageResource.WALKING);
  
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
