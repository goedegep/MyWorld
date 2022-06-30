/*
 * Copyright (c) 2016, 2018, Gluon
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
package com.gluonhq.impl.maps;

import static java.lang.Math.floor;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.gluonhq.maps.tile.TileRetriever;
import com.gluonhq.maps.tile.TileRetrieverProvider;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.transform.Scale;

/**
 *
 */
class MapTile extends Region {

    private static final Logger LOGGER = Logger.getLogger(MapTile.class.getName());
    private static final TileRetriever TILE_RETRIEVER = TileRetrieverProvider.getInstance().load();

    final int myZoom;
    final long i, j;
    private final BaseMap baseMap;
    // a list of tiles that this tile is covering. In case the covered tiles are 
    // not yet loaded, this tile will be rendered.
    private final List<MapTile> coveredTiles = new LinkedList<>();
    
    /**
     * In most cases, a tile will be shown scaled. The value for the scale
     * factor depends on the active zoom and the tile-specific myZoom
     */
    private final Scale scale = new Scale();

    private final InvalidationListener invalidationListener = o -> calculatePosition();
    private DoubleProperty progress = new SimpleDoubleProperty(0.1);

    /**
     * Check whether this tile is covering tiles.
     * <p>
     * This tile is covering tiles if <code>coveredTiles</code> is not empty.
     * 
     * @return true if this tile is covering tiles.
     */
    public boolean isCovering() {
        return coveredTiles.size() > 0;
    }

    /**
     * Constructor
     * 
     * @param baseMap the <code>BaseMap</code> to which this tile applies.
     * @param zoomLevel the zoom level of the tile
     * @param i The column index, goes from 0 (left edge is 180 °W) to 2^zoom − 1 (right edge is 180 °E)
     * @param j Index within the column, goes from 0 (top edge is 85.0511 °N) to 2^zoom − 1 (bottom edge is 85.0511 °S)
     */
    MapTile(BaseMap baseMap, int zoomLevel, long i, long j) {
        LOGGER.info("nearestZoom=" + zoomLevel + ", i=" + i + ", j=" + j);
        this.baseMap = baseMap;
        this.myZoom = zoomLevel;
        this.i = i;
        this.j = j;
        scale.setPivotX(0);
        scale.setPivotY(0);
        getTransforms().add(scale);
        debug("load image [" + myZoom + "], i = " + i + ", j = " + j);

        
//        TILE_RETRIEVER.loadTile(myZoom, i, j, this::handleTileAvailable);
        Image image = TILE_RETRIEVER.loadTile(myZoom, i, j);
        handleTileAvailable2(image);
        baseMap.zoom().addListener(new WeakInvalidationListener(invalidationListener));
        baseMap.translateXProperty().addListener(new WeakInvalidationListener(invalidationListener));
        baseMap.translateYProperty().addListener(new WeakInvalidationListener(invalidationListener));
        calculatePosition();
//        this.setMouseTransparent(true);  PG removed.
    }
    
    protected void layoutChildren() {
      LOGGER.severe("zoom=" + myZoom + ", i=" + i + ", j=" + j);
      super.layoutChildren();
  }
     
    /**
     * Handle the fact that the tile image is available.
     * <p>
     * The actual handling is done by calling handleTileAvailable2 in the JavaFx Application Thread.
     * 
     * @param image the loaded tile image.
     */
    private void handleTileAvailable(Image image) {
      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          handleTileAvailable2(image);
        }
      });
    }
    
    /**
     * Actually handle the tile image.
     * 
     * @param image the loaded tile image.
     */
    private void handleTileAvailable2(Image image) {
      ImageView imageView = new ImageView(image);
      imageView.setMouseTransparent(true);
      progress.set(1.0);
      
      getChildren().add(imageView);
      setNeedsLayout(true);
    }

    boolean loading() {
        return !(progress.greaterThanOrEqualTo(1.)).get();
    }

    /**
     * The (fixed) zoom level of this tile.
     *
     * @return the zoom level of this tile.
     */
    int getZoomLevel() {
        return myZoom;
    }

    private void calculatePosition() {
        double currentZoom = baseMap.zoom().get();
        int visibleWindow = (int) floor(currentZoom + BaseMap.TIPPING);
        boolean visible =  visibleWindow == myZoom ||
                           isCovering() ||
                           ((visibleWindow >= BaseMap.MAX_ZOOM) && (myZoom == BaseMap.MAX_ZOOM - 1));
        this.setVisible(visible);
        LOGGER.fine("visible tile " + this + "? " + this.isVisible() + (this.isVisible() ? " covering? " + isCovering() : ""));
        double sf = Math.pow(2, currentZoom - myZoom);
        scale.setX(sf);
        scale.setY(sf);
        setTranslateX(256 * i * sf);
        setTranslateY(256 * j * sf);
    }

    @Override
    public String toString() {
        return "tile with z = " + myZoom + " [" + i + "," + j + "]";
    }

    /**
     * This tile is covering for the child tile that is still being loaded.
     *
     * @param child
     */
    void addCovering(MapTile child) {
        coveredTiles.add(child);
        InvalidationListener il = createProgressListener(child);
//        System.out.println("We have to cover, add "+il);
        child.progress.addListener(il);
        calculatePosition();
    }

    private InvalidationListener createProgressListener(MapTile child) {
        return new InvalidationListener() {
            @Override
            public void invalidated(Observable o) {
                if (child.progress.get() >= 1.0d) {
                    MapTile.this.coveredTiles.remove(child);
                    child.progress.removeListener(this);
                }
            }
        };
    }

    private void debug(String s) {
        LOGGER.fine("LOG " + System.currentTimeMillis() % 10000 + ": " + s);
    }
}
