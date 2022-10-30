package goedegep.vacations.app.guifx;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.atlis.location.nominatim.NominatimAPI;
import com.gluonhq.maps.GPXLayer;
import com.gluonhq.maps.MapView;

import goedegep.jfx.CustomizationFx;
import goedegep.poi.app.guifx.POIIcons;
import goedegep.util.objectselector.ObjectSelectionListener;
import goedegep.util.objectselector.ObjectSelector;
import javafx.stage.Stage;

public class TravelMapView extends MapView implements ObjectSelector<Object> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(TravelMapView.class.getName());

//  private ComponentFactoryFx componentFactory = null;
  private MapRelatedItemsLayer mapRelatedItemsLayer;
  private GPXLayer trackLayer;
  private SearchResultLayer searchResultLayer;
  private ControlsLayer controlsLayer;
  
  private List<ObjectSelectionListener<Object>> objectSelectionListeners = new ArrayList<>();

  public TravelMapView(CustomizationFx customization, Stage ownerWindow, VacationsWindow vacationsWindow, POIIcons poiIcons, NominatimAPI nominatimAPI) {
//    componentFactory = customization.getComponentFactoryFx();
    
    mapRelatedItemsLayer = new MapRelatedItemsLayer(customization, poiIcons, ownerWindow);
    addLayer(mapRelatedItemsLayer);
    
    trackLayer = new GPXLayer();
    addLayer(trackLayer);
    
    searchResultLayer = new SearchResultLayer();
    addLayer(searchResultLayer);
    
    controlsLayer = new ControlsLayer(customization, vacationsWindow, nominatimAPI, searchResultLayer);
    addLayer(controlsLayer);
    
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
