package goedegep.mapview.maptile.osm;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

import goedegep.mapview.maptile.TileRetriever;
import javafx.scene.image.Image;

/**
 * Implementation of {@link TileRetriever} that retrieves map tiles from the OpenStreetMap (OSM) tile server.
 * <p>
 * The OpenStreetMap tile server provides map tiles in the format of PNG images, which can be accessed via a URL pattern.
 * This class constructs the appropriate URL for the requested tile and loads the image asynchronously.
 * 
 * In the requests to the OSM tile server a User-Agent is set. For this the application has to set the system property "http.agent".
 * If this isn't set a warning will be logged and a default value will be used. This may lead to requests being denied from the server.
 */
public class OsmTileRetriever implements TileRetriever {
  private static final Logger LOGGER = Logger.getLogger(OsmTileRetriever.class.getName());

  /**
   * 10 seconds timeout for reading from the tile server.
   */
  private static final int TIMEOUT = 10000;

  /**
   * The OpenStreetMap service site.
   */
  private static final String host = "https://tile.openstreetmap.org/";
  
  /**
   * The value for the User-Agent.
   */
  protected static final String httpAgent;

  /**
   * Executor service for asynchronous tile loading.
   */
  protected static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(2, new DaemonThreadFactory());

  static {
    // Create the User-Agent
    String agent = System.getProperty("http.agent");  // The http.agent java system property sets the default user agent for http requests.
    if (agent == null) {
      agent = "(" + System.getProperty("os.name") + " / " + System.getProperty("os.version") + " / " + System.getProperty("os.arch") + ")";
      LOGGER.severe("http.agent system property not set, using default: " + agent);
    }
    httpAgent = "Gluon Maps/2.0.0 " + agent;
  }

  /**
   * create the URL string for a specific tile.
   * 
   * @param zoom the zoom level
   * @param i the i index of the tile at the specified zoom level
   * @param j the j index of the tile at the specified zoom level
   * @return the URL string for the tile with zoom level {@code zoom} and indexes i and j.
   */
  private static String buildImageUrlString(int zoom, long i, long j) {
    return host + zoom + "/" + i + "/" + j + ".png";
  }

  @Override
  public CompletableFuture<Image> loadTile(int zoom, long i, long j) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        return loadTileSynchronously(zoom, i, j);
      } catch (Exception e) {
        throw new RuntimeException("Error " + e.getMessage());
      }
    }, EXECUTOR);
  }

  @Override
  public Image loadTileSynchronously(int zoom, long i, long j) throws Exception {
    byte[] imageData = fetchTileData(zoom, i, j);
    InputStream is =  new ByteArrayInputStream(imageData);
    Image image = new Image(is);
    return image;
  }

  /**
   * Fetches the tile data from the OpenStreetMap tile server for the specified zoom level and coordinates.
   *
   * @param zoom the zoom level for the tile to load
   * @param i the horizontal position of the tile to load
   * @param j the vertical position of the tile to load
   * @return a byte array containing the tile data
   * @throws Exception if an error occurs while fetching the tile data
   */
  protected static byte[] fetchTileData(int zoom, long i, long j) throws Exception {
    String urlString = buildImageUrlString(zoom, i, j);
    final URLConnection openConnection;
    URI uri = URI.create(urlString);
    URL url = uri.toURL();
    openConnection = url.openConnection();
    openConnection.addRequestProperty("User-Agent", httpAgent);
    openConnection.setConnectTimeout(TIMEOUT);
    openConnection.setReadTimeout(TIMEOUT);
    try (InputStream inputStream = openConnection.getInputStream()) {
      String enc = File.separator + zoom + File.separator + i + File.separator + j + ".png";
      LOGGER.fine("retrieve " + urlString + " and store " + enc);
      byte[] buff = new byte[100000];
      int offset = 0;
      int remainingLength = buff.length;
      int len = inputStream.read(buff, offset, remainingLength);
      while (len > 0) {
        offset += len;
        remainingLength -= len;
        if (remainingLength == 0) {
          throw new RuntimeException("Tile data is too large to fit in buffer");
        }
        len = inputStream.read(buff, offset, remainingLength);
      }
      return buff;
    }
  }
  
  /**
   * A {@code ThreadFactory} for the {@code EXECUTOR}.
   */
  private static class DaemonThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(final Runnable r) {
      Thread t = new Thread(r);
      t.setDaemon(true);
      return t;
    }
  }

}
