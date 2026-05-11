package goedegep.mapview.view.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.logging.Logger;

import goedegep.mapview.impl.BaseMapAbstract;
import goedegep.mapview.impl.TileImageViewAbstract;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.image.Image;

public class TileImageView extends TileImageViewAbstract {
  private static final Logger LOGGER = Logger.getLogger(TileImageView.class.getName() );
  
  /**
   * The loading progress. 1.0 indicates fully loaded.
   */
  public ReadOnlyDoubleProperty progressProperty;
  
  /**
   *  A list of tiles that this tile is covering. In case the covered tiles are 
   *  not yet loaded, this tile will be rendered.
   */
  protected final List<TileImageViewAbstract> coveredTiles = new LinkedList<>();
  
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
  
  private CompletableFuture<Image> future;
  

  private final InvalidationListener zl = _ -> calculatePosition();

  /**
   * Constructor.
   * 
   * @param baseMapAbstract The base map for which this is a map tile.
   * @param myZoom The zoom level of the tile
   * @param i The tile column.
   * @param j The tile row.
   */
  TileImageView(BaseMapAbstract<?> baseMapAbstract, int myZoom, long i, long j) {
//    LOGGER.severe("=> Creating tile: " + myZoom + " [" + i + ", " + j + "]");
    super(baseMapAbstract, myZoom, i, j);

    loadTile(myZoom, i, j);
    
    // If the zoom level (or the translation of the base map changes), we need to recalculate the position of this tile.
    baseMapAbstract.readOnlyZoomProperty().addListener(new WeakInvalidationListener(zl));
//    baseMapAbstract.translateXProperty().addListener(new WeakInvalidationListener(zl));
//    baseMapAbstract.translateYProperty().addListener(new WeakInvalidationListener(zl));
    
    calculatePosition();
    this.setMouseTransparent(true);
    
//    LOGGER.severe("<= Tile created: " + myZoom + " [" + i + ", " + j + "]");
  }
  
  /**
   * Creates a new TileImageView for the given zoom, i and j.
   *
   * @param zoom the zoom level of the tile
   * @param i    the tile column
   * @param j    the tile row
   */
  public void loadTile(int zoom, long i, long j) {
    progressProperty = new ReadOnlyDoubleWrapper(this, "progress", 0);

    future = TILE_RETRIEVER.loadTile(zoom, i, j);
    if (!future.isDone()) {
      // Install the placeholder image if available
      Optional.ofNullable(placeholderImageSupplier).ifPresent(supplier -> setImage(supplier.get()));

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
//          setNeedsLayout(true);
        });
      });
    } else {
      //            LOGGER.severe("Tile retrieved from file cache: " + zoom + "/" + i + "/" + j);
      Image image = future.getNow(null);
      image = addTileParametersToImage(image, zoom, i, j);
      setImage(image);
      markDirty();
      //      setNeedsLayout(true);
    }
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
   * Indication that the tile is loading.
   * 
   * @return true if the tile is loading, false otherwise.
   */
  public boolean loading() {
    return future != null  &&  !future.isDone();
  }
  
  /**
   * Check whether this tile is covering any tiles.
   * 
   * @return true if this tile is covering one or more tiles, false otherwise.
   */
  public boolean isCovering() {
      return coveredTiles.size() > 0;
  }

  /**
   * This tile is covering for the child tile that is still being loaded.
   *
   * @param child
   */
  public void addCovering(TileImageView child) {
    coveredTiles.add(child);
    InvalidationListener il = createProgressListener(child);
    //        System.out.println("We have to cover, add "+il);
//    child.progress.addListener(il);
    calculatePosition();
  }

  private InvalidationListener createProgressListener(TileImageView child) {
    return new InvalidationListener() {
      @Override
      public void invalidated(Observable o) {
//        if (child.progress.get() >= 1.0d) {
//          MapTileImageView.this.coveredTiles.remove(child);
//          child.progress.removeListener(this);
//        }
      }
    };
  }

  public static void setPlaceholderImageSupplier(Supplier<Image> supplier) {
      placeholderImageSupplier = supplier;
  }

}
