package goedegep.mapview.view.impl;


import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.logging.Logger;

import goedegep.mapview.impl.TileImageViewAbstract;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.image.Image;

/**
 * This class is an ImageView for a single map tile (zoom, i and j).
 * The {@code Image} is loaded asynchronously using the {@link TILE_RETRIEVER}.<br/>
 * The progress of loading the image can be monitored using the {@link #progressProperty()}.
 * <br/>
 * Via the method {@link #setPlaceholderImageSupplier} a supplier of a placeholder Image can be set. This Image will then be shown while loading the actual tile.
 * <br/>
 */
public class TileImageView extends TileImageViewAbstract {
  private static final Logger LOGGER = Logger.getLogger(TileImageView.class.getName());

  /**
   * The progress of loading the tile image (0.0 to 1.0).
   */
  protected ReadOnlyDoubleWrapper progressProperty;

  /**
   * An optional supplier of an Image that can be used as placeholder by the Tile
   * while the final image is being retrieved.
   */
  private static Supplier<Image> placeholderImageSupplier;

  /**
   * An exception that occurred during loading the tile image, or null if no
   * exception occurred.
   */
  private ReadOnlyObjectWrapper<Exception> exception;

  /**
   * Creates a new TileImageView for the given zoom, i and j.
   *
   * @param zoom the zoom level of the tile
   * @param i    the tile column
   * @param j    the tile row
   */
  public TileImageView(int zoom, long i, long j) {
    progressProperty = new ReadOnlyDoubleWrapper(this, "progress", 0);

    CompletableFuture<Image> future = TILE_RETRIEVER.loadTile(zoom, i, j);
    if (!future.isDone()) {
      // Install the placeholder image if available
      Optional.ofNullable(placeholderImageSupplier).ifPresent(supplier -> setImage(supplier.get()));

      //            LOGGER.severe("downloading tile: " + zoom + "/" + i + "/" + j);

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
          setImage(addTileParametersToImage(tileImage, zoom, i, j));
          progressProperty.set(1);
        });
      });
    } else {
      //            LOGGER.severe("Tile retrieved from file cache: " + zoom + "/" + i + "/" + j);
      Image image = future.getNow(null);
      image = addTileParametersToImage(image, zoom, i, j);
      setImage(image);
      progressProperty.set(1);
    }
  }

  /**
   * Set a supplier of an Image that can be used as placeholder by the Tile
   * while the final image is being retrieved.
   * 
   * @param supplier the supplier of the placeholder Image
   */
  public static void setPlaceholderImageSupplier(Supplier<Image> supplier) {
    placeholderImageSupplier = supplier;
  }

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
   * Get the read-only exception {@code Property}.
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
   * Get the read-only progress {@code Property}.
   * 
   * @return the read-only progress property
   */
  public final ReadOnlyDoubleProperty progressProperty() {
    return progressProperty.getReadOnlyProperty();
  }

}
