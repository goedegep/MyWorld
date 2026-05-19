package goedegep.travels.gui;

import goedegep.gpxeditor.gui.GPXLayer;
import goedegep.jfx.CustomizationFx;
import javafx.stage.Stage;

public class TravelMapViewImage extends goedegep.mapview.image.MapImage implements TravelMapViewInterface {

  /**
   * The {@code MapRelatedItemsLayer} for this TravelMapViewImage.
   */
  private MapRelatedItemsLayer mapRelatedItemsLayer;
  
  /**
   * The {@code GPXLayer} for this TravelMapViewImage.
   */
  private GPXLayer trackLayer;

  /**
   * Constructor for TravelMapViewImage.
   *
   * @param customization The GUI customization.
   * @param ownerWindow The owner window for dialogs.
   */
  public TravelMapViewImage(CustomizationFx customization, Stage ownerWindow) {
    super();
    
    mapRelatedItemsLayer = new MapRelatedItemsLayer(customization, ownerWindow);
    addLayer(mapRelatedItemsLayer);
        
    trackLayer = new GPXLayer();
    addLayer(trackLayer);
  }

  @Override
  public MapRelatedItemsLayer getMapRelatedItemsLayer() {
    return mapRelatedItemsLayer;
  }


  @Override
  public GPXLayer getGpxLayer() {
    return trackLayer;
  }
}
