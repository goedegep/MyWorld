package goedegep.travels.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;

import com.gluonhq.maps.MapView;

import goedegep.gpxeditor.gui.GPXLayer;
import goedegep.jfx.CustomizationFx;
import goedegep.util.objectselector.ObjectSelectionListener;
import goedegep.util.objectselector.ObjectSelector;
import javafx.stage.Stage;

/**
 * This class extends a {@link MapView} with a number of {@link MapLayer}s.
 */
public class TravelMapView extends MapView implements ObjectSelector<Object> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(TravelMapView.class.getName());

  private MapRelatedItemsLayer mapRelatedItemsLayer;
  private GPXLayer trackLayer;
  private SearchResultLayer searchResultLayer;
  private ControlsLayer controlsLayer;
  
  private List<ObjectSelectionListener<Object>> objectSelectionListeners = new ArrayList<>();

  /**
   * Constructor for TravelMapView.
   * 
   * @param customization The GUI customization.
   * @param ownerWindow The owner window for dialogs.
   * @param searchWindowSupplier A supplier for the LocationSearchWindow.
   */
  public TravelMapView(CustomizationFx customization, Stage ownerWindow, Supplier<LocationSearchWindow> searchWindowSupplier) {
    
    mapRelatedItemsLayer = new MapRelatedItemsLayer(customization, ownerWindow);
    addLayer(mapRelatedItemsLayer);
    
    trackLayer = new GPXLayer();
    addLayer(trackLayer);
    
    searchResultLayer = new SearchResultLayer();
    addLayer(searchResultLayer);
    
    controlsLayer = new ControlsLayer(customization, searchWindowSupplier);
    addLayer(controlsLayer);
    
    mapRelatedItemsLayer.addObjectSelectionListener((_, object) -> notifyListeners(object));
  }
  
  public void setEditMode(boolean editMode) {
    mapRelatedItemsLayer.setEditMode(editMode);
  }

  public void clear() {
    mapRelatedItemsLayer.clear();
    trackLayer.clear();
    searchResultLayer.clear();
  }

  public MapRelatedItemsLayer getMapRelatedItemsLayer() {
    return mapRelatedItemsLayer;
  }

  public GPXLayer getGpxLayer() {
    return trackLayer;
  }
  
  public SearchResultLayer getSearchResultLayer() {
    return searchResultLayer;
  }
  
  public void removeControlsLayer() {
    removeLayer(controlsLayer);
  }
  
  private void notifyListeners(Object object) {
    for (ObjectSelectionListener<Object> listener: objectSelectionListeners) {
      listener.objectSelected(this, object);
    }
  }

  @Override
  public void addObjectSelectionListener(ObjectSelectionListener<Object> objectSelectionListener) {
    objectSelectionListeners.add(objectSelectionListener); 
  }

  @Override
  public void removeObjectSelectionListener(ObjectSelectionListener<Object> objectSelectionListener) {
    objectSelectionListeners.remove(objectSelectionListener); 
  }

  @Override
  public Object getSelectedObject() {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public boolean selectObject(Object object) {
    boolean selected = false;
    
    selected = mapRelatedItemsLayer.selectObject(object);
    
    return selected;
  }
}
