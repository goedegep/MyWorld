package goedegep.mapview.maptile.osm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.concurrent.Task;

/**
 * This class provides a Task which cleans up the OSM tile cache.
 */
public class TileCacheCleanupTask extends Task<Void> {
  private static final Logger LOGGER = Logger.getLogger(TileCacheCleanupTask.class.getName());

  private static final long CLEANUP_FIRST_DELAY_MS = 1000 * 60 * 4; // Use an initial delay of 4 minutes to give the application some time to start up and load tiles before we start cleaning up the cache.
  private static final long CLEANUP_INTERVAL_MS = 1000 * 60 * 10;   // After the initial cleanup, we will clean up the cache every 10 minutes.
  
  private boolean firstCleanupDone = false;

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
  
  static {
    // Determine the cacheRoot and set hasFileCache based on whether a cache root is available or not.
    hasFileCache = false;

    cacheRoot = new File("C:/User/Peter", ".gluonmaps");
    if (!cacheRoot.isDirectory()) {
      hasFileCache = cacheRoot.mkdirs();
    } else {
      hasFileCache = true;
    }
  }

  @Override
  protected Void call() throws Exception {
    for ( ; ; ) {
      if (!firstCleanupDone) {
        Thread.sleep(CLEANUP_FIRST_DELAY_MS);
        firstCleanupDone = true;
      } else {
        Thread.sleep(CLEANUP_INTERVAL_MS);
      }
      
      LOGGER.severe("Starting tile cache cleanup...");
      cleanupTileCache();
    }
  }


  public void cleanupTileCache() {
    
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
//      LOGGER.severe("Total cached tiles: " + totalFiles);
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
//          LOGGER.severe("Deleting cached tile: " + p.toString());
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
