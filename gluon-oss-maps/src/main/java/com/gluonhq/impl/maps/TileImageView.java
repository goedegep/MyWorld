/*
 * Copyright (c) 2020, 2022, Gluon
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
 * 
 * This is a modified version of com.gluonhq.impl.maps.TileImageView from the Gluon Maps library.
 * 
 */
package com.gluonhq.impl.maps;

import com.gluonhq.maps.tile.TileRetriever;
import com.gluonhq.maps.tile.TileRetrieverProvider;
import javafx.application.Platform;
//import javafx.beans.property.BooleanProperty;
//import javafx.beans.property.ReadOnlyBooleanProperty;
//import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * This class is an ImageView for a single map tile (zoom, i and j).
 * The {@code Image} is loaded asynchronously using a {@link TileRetriever}.<br/>
 * The progress of loading the image can be monitored using the {@link #progressProperty()}.
 * <br/>
 * Via the method {@link #setPlaceholderImageSupplier} a supplier of a placeholder Image can be set. This Image will then be shown while loading the actual tile.
 * <br/>
 * TODO: Clients are only interested in whether the tile is loaded or not. So maybe we can simplify the progress property to the boolean 'downloading' property.
 *       If not, remove the lazy creation of the progress property, as it is always used.
 */
public class TileImageView extends ImageView {
    private static final Logger LOGGER = Logger.getLogger(TileImageView.class.getName());
    
    /**
     * The map provider specific {@code TileRetriever} used to load tiles.
     */
    private static final TileRetriever TILE_RETRIEVER = TileRetrieverProvider.getInstance().load();

    /**
     * Creates a new TileImageView for the given zoom, i and j.
     *
     * @param zoom the zoom level of the tile
     * @param i    the tile column
     * @param j    the tile row
     */
    public TileImageView(int zoom, long i, long j) {
// PG: OSM tiles are 256x256, so we don't need to set fitWidth/fitHeight. If another tile source would have different tile size, I think different calculations are needed.
//        setFitHeight(256);
//        setFitWidth(256);
      
// PG: As tiles aren't scaled, we don't have to preserve the ratio.      
//        setPreserveRatio(true);
      
// PG: Moved to !future.isDone(). In this location the lazy creation of 'progress' property doesn't make sense.
//        setProgress(0);
        CompletableFuture<Image> future = TILE_RETRIEVER.loadTile(zoom, i, j);
        if (!future.isDone()) {
            setProgress(0);
            // Install the placeholder image if available
            Optional.ofNullable(placeholderImageSupplier).ifPresent(s -> setImage(s.get()));
            
//            LOGGER.severe("downloading tile: " + zoom + "/" + i + "/" + j);

// PG: The downloading indication removed. It wasn't used and isn't needed, as the progress property is available.
//            downloading.setValue(true);
            
            future.handle((image, throwable) -> {
                if (throwable != null) {
                    LOGGER.severe("Tile " + zoom + "/" + i + "/" + j + " failed with exception: " + throwable.getMessage());
                    setException(new Exception(throwable));
                    return null;
                }
                
                return image;
            }).thenAccept(tileImage -> {
//                LOGGER.severe("Tile available from downloaded file: " + zoom + "/" + i + "/" + j);
                Platform.runLater(() -> {
//                    downloading.setValue(false);
                    setImage(tileImage);
                    setProgress(1);
                });
            });
        } else {
//            LOGGER.severe("Tile retrieved from file cache: " + zoom + "/" + i + "/" + j);
            setImage(future.getNow(null));
            setProgress(1);
        }
    }

    /**
     * An optional supplier of an Image that can be used as placeholder by the Tile
     * while the final image is being retrieved.
     */
    private static Supplier<Image> placeholderImageSupplier;

    /**
     * Set a supplier of an Image that can be used as placeholder by the Tile
     * while the final image is being retrieved.
     * 
     * @param supplier the supplier of the placeholder Image
     */
    public static void setPlaceholderImageSupplier(Supplier<Image> supplier) {
        placeholderImageSupplier = supplier;
    }

//    private final ReadOnlyBooleanWrapper downloading = new ReadOnlyBooleanWrapper(TileImageView.this, "downloading", false);

//    public final boolean isDownloading() {
//        return downloading != null && downloading.get();
//    }

//    public final ReadOnlyBooleanProperty downloadingProperty() {
//        return downloading.getReadOnlyProperty();
//    }

    /**
     * The exception occurred during loading the tile image, or null if no
     * exception occurred.
     */
    private ReadOnlyObjectWrapper<Exception> exception;

    /**
     * Set the exception value.
     * 
     * @param value
     */
    private void setException(Exception value) {
        exceptionPropertyImpl().set(value);
    }
    
    /**
     * Get the exception value.
     * 
     * @return the exception value, which is null if no exception occurred.
     */
    public final Exception getException() {
        return exception == null ? null : exception.get();
    }

    /**
     * Get the read-only exception property.
     * 
     * @return the read-only exception property
     */
    public final ReadOnlyObjectProperty<Exception> exceptionProperty() {
        return exceptionPropertyImpl().getReadOnlyProperty();
    }
    
    /**
     * Get the exception {@code Property}.
     * The property itself is created lazily.
     * 
     * @return the exception {@code Property}.
     */
    private ReadOnlyObjectWrapper<Exception> exceptionPropertyImpl() {
        if (exception == null) {
            exception = new ReadOnlyObjectWrapper<Exception>(this, "exception");
        }
        return exception;
    }

    /**
     * The progress of loading the tile image (0.0 to 1.0).
     */
    private ReadOnlyDoubleWrapper progress;


    /**
     * Set the progress value.
     * 
     * @param value
     */
    private void setProgress(double value) {
        progressPropertyImpl().set(value);
    }

    /**
     * Get the progress value.
     * 
     * @return the progress value
     */
    public final double getProgress() {
        return progress == null ? 0.0 : progress.get();
    }

    /**
     * Get the read-only progress property.
     * 
     * @return the read-only progress property
     */
    public final ReadOnlyDoubleProperty progressProperty() {
        return progressPropertyImpl().getReadOnlyProperty();
    }

    /**
     * Get the progress {@code Property}.
     * The property itself is created lazily.
     * 
     * @return the progress {@code Property}.
     */
    private ReadOnlyDoubleWrapper progressPropertyImpl() {
        if (progress == null) {
            progress = new ReadOnlyDoubleWrapper(this, "progress");
        }
        return progress;
    }

}
