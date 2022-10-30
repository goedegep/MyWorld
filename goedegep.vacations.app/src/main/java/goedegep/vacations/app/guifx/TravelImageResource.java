package goedegep.vacations.app.guifx;

import goedegep.resources.ImageResource;
import goedegep.resources.ImageSize;
import javafx.scene.image.Image;

/**
 * This class is a mapping of the images used in the travel application to the ImageResource images.
 *
 */
public enum TravelImageResource {
  DAY(ImageResource.SUNRISE),
  DAY_TRIP(ImageResource.BACKPACK),
  SEARCH(ImageResource.MAGNIFYING_GLASS),
  TRAVEL(ImageResource.ROAD_TO_HORIZON),
  VACATIONS(ImageResource.SNOW_MOUNTAINS);
  
  private ImageResource imageResource;
  
  TravelImageResource(ImageResource imageResource) {
    this.imageResource = imageResource;
  }
  
  public Image getIcon() {
    return imageResource.getImage();
  }
  
  public Image getIcon(ImageSize imageSize) {
    return imageResource.getImage(imageSize);
  }
}
