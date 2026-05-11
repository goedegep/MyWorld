package goedegep.mapview.image;

import java.util.logging.Logger;

import goedegep.mapview.image.impl.BaseMap;
import goedegep.mapview.impl.MapViewCommon;

/**
 *
 * This is the top UI element for a map image. The center location and the
 * zoom level of the map can be altered by input events (mouse/touch/gestures)
 * or by calling the methods setCenter and setZoom.
 */
public class MapImage extends MapViewCommon {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(MapImage.class.getName());


  /**
   * Constructor.
   */
  public MapImage() {
    baseMapAbstract = new BaseMap(this);
    getChildren().add(baseMapAbstract);
  }
  
  /**
   * Set the size of the map image. This method sets the width and height of the map image to the specified values.
   *
   * @param width  the width of the map image
   * @param height the height of the map image
   */
  public void setSize(double width, double height) {
    super.setDimensions(width, height);
    setWidth(width);
    setHeight(height);
    setPrefWidth(width);
    setPrefHeight(height);
    setMinWidth(width);
    setMinHeight(height);
    initialize();
  }

}
