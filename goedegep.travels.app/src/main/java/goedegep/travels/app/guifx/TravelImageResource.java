package goedegep.travels.app.guifx;

import goedegep.resources.ImageResource;
import goedegep.resources.ImageSize;
import javafx.scene.image.Image;

/**
 * This class is a mapping of the images used in the travels application to the ImageResource images.
 * <p>
 * This is there to have a consistent usage of images in the application.
 */
public enum TravelImageResource {
  /**
   * Image for the {@code Day} travel element.
   */
  DAY(ImageResource.SUNRISE),
  
  /**
   * Image for the {@code DayTrip} travel element.
   */
  DAY_TRIP(ImageResource.BACKPACK),
  
  /**
   * Image for the {@code MapImage} travel element.
   */
  MAP_IMAGE(ImageResource.MAP),
  
  /**
   * Image for opening the search view.
   */
  SEARCH(ImageResource.MAGNIFYING_GLASS),
  
  /**
   * Image for the {@code Text} travel element..
   */
  TEXT(ImageResource.TEXT),
  
  /**
   * Image for {@code TravelCategory}.
   */
  TRAVEL_CATEGORY(ImageResource.PLANE_AROUND_THE_WORLD),
  
  /**
   * Image for {@code Travels}.
   */
  TRAVELS(ImageResource.ROAD_TO_HORIZON),
  
  /**
   * Image for a {@code Vacation}.
   */
  VACATION(ImageResource.SNOW_MOUNTAIN),
  
  /**
   * Image for the {@code Vacations} travel element.
   */
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
