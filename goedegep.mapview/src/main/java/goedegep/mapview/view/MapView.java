/*
 * Copyright (c) 2016 - 2018, Gluon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL GLUON BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package goedegep.mapview.view;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;

import com.gluonhq.attach.util.Platform;

import goedegep.geo.WGS84BoundingBox;
import goedegep.mapview.MapPoint;
import goedegep.mapview.impl.BaseMapAbstract;
import goedegep.mapview.impl.MapViewCommon;
import goedegep.mapview.MapLayer;
import goedegep.mapview.view.impl.BaseMap;
import goedegep.mapview.view.impl.TileImageView;
import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * This is the top UI element of the map component. The center location and the
 * zoom level of the map can be altered by input events (mouse/touch/gestures)
 * or by calling the methods setCenter and setZoom.
 */
public class MapView extends MapViewCommon {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(MapView.class.getName());
  
  /**
   * X, Y coordinates of the start of a drag event, used to calculate the delta when dragging the map.
   */
  public double x0, y0;

  private MapPoint centerPoint = null;
  private boolean zooming = false;
  private boolean enableDragging = false;

  /**
   * Create a MapView component.
   */
  public MapView() {
    LOGGER.severe("=>");
    
    baseMapAbstract = new BaseMap(this);
    getChildren().add(baseMapAbstract);
    registerInputListeners();

    center.addListener(_ -> markDirty());
    this.layoutBoundsProperty().addListener(_ -> {
      // in case our assigned space changes, AND in case we are requested
      // to center at a specific point, we need to re-center.
      if (centerPoint != null) {
        // we will set the center to a slightly different location first, in order 
        // to trigger the invalidationListeners.
        setCenter(centerPoint.getLatitude()+.00001, centerPoint.getLongitude()+.00001);
        setCenter(centerPoint);
      }
    });
    
    LOGGER.severe("<=");
  }

  //    public BaseMap getBaseMap() {
  //      return baseMap;
  //    }


  /**
   * Install mouse listeners to support dragging and zooming the map.
   */
  private void registerInputListeners() {
    // Save x, y for a possible drag event.
    setOnMousePressed(t -> {
      if (zooming) return;
      x0 = t.getX();
      y0 = t.getY();
      centerPoint = null; // once the user starts moving, we don't track the center anymore.
      // dragging is enabled only after a pressed event, to prevent dragging right after zooming
      enableDragging = true;
    });
    
    // Handle dragging the map.
    setOnMouseDragged(t -> {
      if (zooming || !enableDragging) {
        return;
      }
      baseMapAbstract.moveX(x0 - t.getX());
      baseMapAbstract.moveY(y0 - t.getY());
      x0 = t.getX();
      y0 = t.getY();
    });
    
    // End off dragging when the mouse is released.
    setOnMouseReleased(t -> enableDragging = false);
    
    setOnZoomStarted(t -> {
      zooming = true;
      enableDragging = false;
    });
    setOnZoomFinished(t -> zooming = false);
    setOnZoom(t -> baseMapAbstract.zoom(t.getZoomFactor() - 1, t.getX(), t.getY()));  // zoom factor greater than 1 means zoom in, smaller than 1 means zoom out, so we need to subtract 1 to get the delta.
    if (Platform.isDesktop()) {
      setOnScroll(t -> {
        // Using the mouse wheel, we zoom in or out by a fixed .1 step.
        final double delta;
        if (t.isControlDown()) {
          delta = t.getDeltaY() > 1 ? 1.0 : t.getDeltaY() < -1 ? -1.0 : 0;
        } else {
          delta = t.getDeltaY() > 1 ? .1 : t.getDeltaY() < -1 ? -.1 : 0;
        }
        baseMapAbstract.zoom(delta, t.getX(), t.getY());
      });
    }
  }

  /**
   * Get the position on the map represented by a given coordinate
   *
   * @param sceneX x coordinate
   * @param sceneY y coordinate
   * @return map position
   */
  public MapPoint getMapPosition(double sceneX, double sceneY) {
    return baseMapAbstract.getMapPosition(sceneX, sceneY);
  }

  /**
   * Request the map to position itself around the specified center
   *
   * @param mapPoint
   */
  public void setCenter(MapPoint mapPoint) {
    setCenter(mapPoint.getLatitude(), mapPoint.getLongitude());
  }

  //    /**
  //     * Returns the center point of this map
  //     * @return the center point
  //     */
  //    public MapPoint getCenter() {
  //        Point2D center = baseMapAbstract.getCenter();
  //        return new MapPoint(center.getX(), center.getY());
  //    }

  /**
   * Calculate the zoom level needed to show a bounding box
   * 
   * @param boundingBox a {@code WGS84BoundingBox}.
   * @return the zoom level needed to show {@code boundingBox}.
   */
  public static double getZoomLevel(WGS84BoundingBox boundingBox) {
    // TODO: calculate zooom level
    // http://stackoverflow.com/questions/4266754/how-to-calculate-google-maps-zoom-level-for-a-bounding-box-in-java
    int zoomLevel;

    final double maxDiff = Math.max(boundingBox.getWidth(), boundingBox.getHeight());
    if (maxDiff < 360d / Math.pow(2, 20)) {
      zoomLevel = 21;
    } else {
      zoomLevel = (int) (-1d*( (Math.log(maxDiff)/Math.log(2d)) - (Math.log(360d)/Math.log(2d))) + 1d);
      if (zoomLevel < 1)
        zoomLevel = 1;
    }

    return zoomLevel;
  }

  /**
   * Set a supplier of an Image that can be used as placeholder by the Tile
   * while the final image is being retrieved
   *
   * @param supplier a supplier that provides a placeholder Image
   */
  public static void setPlaceholderImageSupplier(Supplier<Image> supplier) {
    TileImageView.setPlaceholderImageSupplier(supplier);
  }

  private boolean dirty = false;

  protected void markDirty() {
    dirty = true;
    this.setNeedsLayout(true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void layoutChildren() {
    LOGGER.severe("=>");

    super.layoutChildren();
    dirty = false;

    // we need to get these values or we won't be notified on new changes
    center.get();
  }

  @Override
  public WGS84BoundingBox getVisibleMapCoordinates() {
    return baseMapAbstract.getVisibleMapCoordinates();
  }

  @Override
  public Point2D getMapPoint(double lat, double lon) {
    return baseMapAbstract.getMapPoint(lat, lon);
  }

  @Override
  public Point2D getMapPoint(double zoom, double lat, double lon) {
    return baseMapAbstract.getMapPoint(zoom, lat, lon);
  }
  
  public void moveX(double distance) {
    baseMapAbstract.moveX(distance);
  }
  
  public void moveY(double distance) {
    baseMapAbstract.moveY(distance);
  }
}
