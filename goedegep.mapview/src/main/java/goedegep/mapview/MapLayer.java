package goedegep.mapview;

import goedegep.gluonhq.impl.maps.BaseMap;
import goedegep.mapview.impl.BaseMapAbstract;
import javafx.scene.Parent;

/**
 * A MapLayer can be added on top of the base map (which provides the map tiles).
 * MapLayers contain specific functionality that is rendered by overriding the
 * {@link #layoutLayer()} method.
 * <p>
 * There are 2 reasons why the {@link #layoutLayer() } will be called:
 * <ul>
 * <li>The MapLayer {@link #layoutLayer() } method will be called by the MapView
 * in case the coordinates (center/zoom) are changed.
 * <li>When the content of the MapLayer implementation changes (e.g. a POI is
 * added or moved), it should call the {@link #markDirty() } method.
 * This will mark this layer dirty and request it to be recalculated in the next
 * Pulse.
 * </ul>
 * <p>
 * The MapLayer has access to the {@link MapViewAbstract} for information of the map.
 */
public abstract class MapLayer extends Parent {

  protected MapViewAbstract mapViewAbstract;
  public boolean dirty = false;

  /**
   * Only the MapView should call this method. We want implementations to
   * access the MapView (since they need to be able to act on changes in
   * center/zoom values).
   *
   * @param baseMap
   */
  public final void setMapView(MapViewAbstract mapViewAbstract) {
    this.mapViewAbstract = mapViewAbstract;

    initialize();
  }

  /**
   * This method is called by the framework when the MapLayer is created and
   * added to the Map. At this point, it is safe to use the
   * <code>baseMap</code> and its fields.
   * The default implementation doesn't do anything. It is up to specific
   * layers to add layer-specific initialization.
   */
  protected void initialize() {
    
  }
  
  /**
   * Implementations should call this function when the content of the data
   * has changed. It will set the {@code dirty} flag.
   */
  public void markDirty() {
    this.dirty = true;
    this.requestLayout();
  }

  @Override
  protected void layoutChildren() {
    if (dirty) {
      layoutLayer();
    }
  }

  /**
   * This method is called when a Pulse is running and it is detected that
   * the layer should be redrawn, as a consequence of an earlier call to
   * {@link #markDirty() } (which should happen in case the info in the
   * specific layer has changed) or when the {@link com.gluonhq.maps.MapView}
   * has its dirty flag set to true (which happens when the map is moved/zoomed).
   * The default implementation doesn't do anything. It is up to specific
   * layers to add layer-specific rendering.
   */
  public abstract void layoutLayer();


}
