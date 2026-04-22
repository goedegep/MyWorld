package goedegep.mapview.maptile;

import javafx.scene.image.Image;

import java.util.concurrent.CompletableFuture;

/**
 * Interface defining the contract for retrieving map tiles.
 * <p>
 * There are two methods for retrieving tiles: one asynchronous and one synchronous.
 * The asynchronous method returns a {@link CompletableFuture} that will complete with the tile image once it is loaded, while the synchronous
 * method blocks until the tile is loaded and returns the image directly.
 */
public interface TileRetriever {

    /**
     * Loads a tile, asynchronously, at the specified zoom level and coordinates and returns it
     * as an {@link Image}.
     *
     * @param zoom the desired zoom level for the tile to load
     * @param i the horizontal position of the tile to load
     * @param j the vertical position of the tile to load
     * @return a completableFuture with the image representing the tile
     */
    CompletableFuture<Image> loadTile(int zoom, long i, long j);


    /**
     * Loads a tile, synchronously, at the specified zoom level and coordinates and returns it
     * as an {@link Image}.
     *
     * @param zoom the desired zoom level for the tile to load
     * @param i the horizontal position of the tile to load
     * @param j the vertical position of the tile to load
     * @return a completableFuture with the image representing the tile
     */
    Image loadTileSynchronously(int zoom, long i, long j) throws Exception;
    
}
