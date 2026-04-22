package goedegep.mapview.image.impl;

import java.util.logging.Logger;

import goedegep.mapview.impl.TileImageViewAbstract;
import javafx.scene.image.Image;

/**
 * This class is an ImageView for a single map tile (zoom, i and j).
 * The {@code Image} is loaded synchronously using the {@link TILE_RETRIEVER}.
 */
public class TileImageView extends TileImageViewAbstract {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(TileImageView.class.getName());
    
    /**
     * Creates a new TileImageView for the given zoom, i and j.
     *
     * @param zoom the zoom level of the tile
     * @param i    the tile column
     * @param j    the tile row
     */
    public TileImageView(int zoom, long i, long j) {
      try {
        Image image = TILE_RETRIEVER.loadTileSynchronously(zoom, i, j);
        image = addTileParametersToImage(image, zoom, i, j);
        setImage(image);
      } catch (Exception e) {
        setImage(null);
      }
    }

}
