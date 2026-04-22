package goedegep.mapview.maptile.osm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.gluonhq.attach.storage.StorageService;

import javafx.scene.image.Image;

/**
 * This class is an implementation of {@link TileRetriever} that caches tiles on the file system.
 */
public class CachedOsmTileRetriever extends OsmTileRetriever {

  private static final Logger LOGGER = Logger.getLogger(CachedOsmTileRetriever.class.getName());

  /**
   * Root directory for caching tiles. It is determined at runtime based on the availability of a private storage.
   * If no private storage is available, there is no cache and then loading tiles will fail.
   * This to avoid that you think that your tiles are cached, but in reality they are not and you end up with a lot of network requests and slow performance.
   */
  static File cacheRoot;
  
  /**
   * Indicates whether a file cache is available. This is true if a private storage is available and the cache directory could be created or already exists.
   */
  static boolean hasFileCache;
  
  static int tilesLoaded = 100;

  static {
    // Determine the cacheRoot and set hasFileCache based on whether a cache root is available or not.
    hasFileCache = false;

    Optional<File> storageRoot = StorageService.create()
        .flatMap(StorageService::getPrivateStorage);
    if (storageRoot.isPresent()) {
      cacheRoot = new File(storageRoot.get(), ".gluonmaps");
      if (!cacheRoot.isDirectory()) {
        hasFileCache = cacheRoot.mkdirs();
      } else {
        hasFileCache = true;
      }
    }
  }

  @Override
  public CompletableFuture<Image> loadTile(int zoom, long i, long j) {
    cleanupTileCache();

    if (!hasFileCache) {
      throw new RuntimeException("Failed to open/create cache directory");
    }
    
    // If the tile is already available in the cache, load it from there and return a completed future.
    if (isTileAvailableInCache(zoom, i, j)) {
      Image image = fromFileCache(zoom, i, j);
      return CompletableFuture.completedFuture(image);
    }
    
    LOGGER.severe("Tile not available in disk cache: " + zoom + "[" + i + "," + j + "]");
    
    // Otherwise, fetch the tile asynchronously and cache it.
    return CompletableFuture.supplyAsync(() -> {
      try {
        Path path = cacheFile(zoom, i, j);
        return new Image(new FileInputStream(path.toFile()));
      } catch (Exception e) {
        throw new RuntimeException("Error " + e.getMessage());
      }
    }, EXECUTOR);
    
  }

  @Override
  public Image loadTileSynchronously(int zoom, long i, long j) throws Exception {
    cleanupTileCache();

    if (!hasFileCache) {
      throw new RuntimeException("Failed to open/create cache directory");
    }
    
    if (!isTileAvailableInCache(zoom, i, j)) {
      try {
        cacheFile(zoom, i, j);
      } catch (IOException e) {
        LOGGER.severe("Exception in caching tile: " + e.toString());
      }
    }
    
    Image image = fromFileCache(zoom, i, j);
    return image;
  }

  /**
   * Caches the tile for the given zoom, i and j by fetching the tile data from the network and writing it to the file system.
   * 
   * @param zoom the zoom level for the tile to load
   * @param i the horizontal position of the tile to load
   * @param j the vertical position of the tile to load
   * @return the {@code Path} for the cached tile
   * @throws Exception if an error occurs while fetching the tile data or writing it to the file system
   */
  private static Path cacheFile(int zoom, long i, long j) throws Exception {
    byte[] imageData = fetchTileData(zoom, i, j);
    Path path = getTileFilePath(zoom, i, j);
    Files.createDirectories(path.getParent());
    Files.write(path, imageData);
    
    return path;
  }

  /**
   * Loads the tile for the given zoom, i and j from the file cache if it exists, or returns null if it does not exist.
     *
   * @param zoom the zoom level for the tile to load
   * @param i the horizontal position of the tile to load
   * @param j the vertical position of the tile to load
   * @return the Image loaded from the file cache.
   */
  static private Image fromFileCache(int zoom, long i, long j) {
    Path path = getTileFilePath(zoom, i, j);
    path.toUri();
    
    return new Image(path.toUri().toString(), false);
  }

  /**
   * Returns the file path for the tile with the given zoom, i and j in the cache directory.
   * 
   * @param zoom the zoom level for the tile to load
   * @param i the horizontal position of the tile to load
   * @param j the vertical position of the tile to load
   * @return the file path for the tile with the given zoom, i and j in the cache directory.
   */
  private static Path getTileFilePath(int zoom, long i, long j) {
    return Paths.get(cacheRoot.getAbsolutePath(), String.valueOf(zoom), String.valueOf(i), j + ".png");
  }
  
  /**
   * Checks if the tile for the given zoom, i and j is available in the file cache.
   * 
   * @param zoom the zoom level for the tile to check
   * @param i the horizontal position of the tile to check
   * @param j the vertical position of the tile to check
   * @return true if the tile for the given zoom, i and j is available in the file cache, false otherwise.
   */
  private static boolean isTileAvailableInCache(int zoom, long i, long j) {
    return Files.exists(getTileFilePath(zoom, i, j));
  }

  public void cleanupTileCache() {
    tilesLoaded++;
    if (tilesLoaded >= 100) {
      tilesLoaded = 0;
    } else {
      return;
    }
    
    int maxNumberOfFiles = 2000; // Set a threshold for the maximum number of cached files
    int cleanupToNumberOfFiles = 1700; // Set a threshold for the number of cached files to clean up to when the maximum is exceeded
    
    if (!hasFileCache) {
      return;
    }
    
    try {
      List<Path> files = Files.walk(cacheRoot.toPath())
          .filter(Files::isRegularFile)
          .collect(Collectors.toList());

      int totalFiles = files.size();
      LOGGER.severe("Total cached tiles: " + totalFiles);
      if (totalFiles <= maxNumberOfFiles) {
        return;
      }

      // Sort files by last modified time ascending (oldest first)
      files.sort(Comparator.comparingLong(path -> {
        try {
          return Files.getLastModifiedTime(path).toMillis();
        } catch (IOException e) {
          // If we can't read last modified time, treat it as very new so it won't be deleted prematurely
          return Long.MAX_VALUE;
        }
      }));

      int filesToDelete = totalFiles - cleanupToNumberOfFiles;
      for (int i = 0; i < filesToDelete; i++) {
        Path p = files.get(i);
        try {
          LOGGER.severe("Deleting cached tile: " + p.toString());
          Files.deleteIfExists(p);
        } catch (IOException e) {
          LOGGER.severe("Failed to delete cached tile: " + p.toString() + " : " + e.getMessage());
        }
      }

    } catch (IOException e) {
      LOGGER.severe("Failed to clean up tile cache: " + e.getMessage());
    }
  }
}