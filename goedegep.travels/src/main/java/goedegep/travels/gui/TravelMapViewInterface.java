package goedegep.travels.gui;

import goedegep.gpxeditor.gui.GPXLayer;

public interface TravelMapViewInterface {

  /**
   * Get the MapRelatedItemsLayer.
    *
   * @return the MapRelatedItemsLayer.
   */
  MapRelatedItemsLayer getMapRelatedItemsLayer();


  /**
   * Get the GPXLayer.
   *
   * @return the GPXLayer.
   */
  public GPXLayer getGpxLayer();

}
