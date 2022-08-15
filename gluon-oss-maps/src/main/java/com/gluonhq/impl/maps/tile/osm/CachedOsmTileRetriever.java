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
package com.gluonhq.impl.maps.tile.osm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gluonhq.attach.storage.StorageService;
import com.gluonhq.maps.tile.TileRetriever;

import javafx.scene.image.Image;

public class CachedOsmTileRetriever implements TileRetriever {

  private static final Logger LOGGER = Logger.getLogger(CachedOsmTileRetriever.class.getName());
  private static final int TIMEOUT = 5000;
  private static final String HOST = "http://tile.openstreetmap.org/";
  private static final String HTTP_AGENT = "goedegep MyWorld " + "(" + System.getProperty("os.name") + " / " + System.getProperty("os.version") + " / " + System.getProperty("os.arch") + ")";

  private static File CACHE_ROOT;
  private static boolean HAS_FILE_CACHE;
  private static CacheThread CACHE_THREAD = null;

  private final static Executor EXECUTOR = Executors.newFixedThreadPool(2, runnable -> {
    Thread thread = new Thread(runnable);
    thread.setName("ExecuterThread");
    thread.setDaemon(true);
    return thread;
  });

  static {
    try {
      File storageRoot = StorageService.create()
          .flatMap(StorageService::getPrivateStorage)
          .orElseThrow(() -> new IOException("Storage Service is not available"));

      CACHE_ROOT = new File(storageRoot, ".gluonmaps");
      LOGGER.fine("[JVDBG] cacheroot = " + CACHE_ROOT);
      if (!CACHE_ROOT.isDirectory()) {
        HAS_FILE_CACHE = CACHE_ROOT.mkdirs();
      } else {
        HAS_FILE_CACHE = true;
      }
      if (HAS_FILE_CACHE) {
        CACHE_THREAD = new CacheThread();
        CACHE_THREAD.start();
      }
      LOGGER.info("hasfilecache = " + HAS_FILE_CACHE);
    } catch (IOException ex) {
      HAS_FILE_CACHE = false;
      LOGGER.log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Create the URL string for a single tile image.
   * <p>
   * The URL string has the format: http://tile.openstreetmap.org/zoom/i/j.png
   * 
   * @param zoom the zoom level, typically from 1 through 19.
   * @param i The column index, goes from 0 (left edge is 180 °W) to 2^zoom − 1 (right edge is 180 °E)
   * @param j Index within the column, goes from 0 (top edge is 85.0511 °N) to 2^zoom − 1 (bottom edge is 85.0511 °S)
   * @return the URL string for specified tile image.
   */
  static String buildImageUrlString(int zoom, long i, long j) {
    return HOST + zoom + "/" + i + "/" + j + ".png";
  }

  /**
   * Asynchronously request a tile image.
   * <p>
   * If the file for the requested tile is available in the cache, it is directly provided to the listener.
   * Else the file is queued for being read from the OSM server and when it is retrieved it is provided to the listener.
   * 
   * @param zoom the zoom level, typically from 1 through 19.
   * @param i The column index, goes from 0 (left edge is 180 °W) to 2^zoom − 1 (right edge is 180 °E)
   * @param j Index within the column, goes from 0 (top edge is 85.0511 °N) to 2^zoom − 1 (bottom edge is 85.0511 °S)
   * @param listener the <code>Consumer</code> of the tile image.
   */
  public void loadTile(int zoom, long i, long j, java.util.function.Consumer<Image> listener) {
    if (tileIsAvailableInCache(zoom, i, j)) {
      // the tile is already in the cache, provide it directly.
      Image image = fromFileCache(zoom, i, j);
      listener.accept(image);
    } else {
      // queue the tile for being fetched from the server.
      EXECUTOR.execute(() -> {
        try {
          CACHE_THREAD.cacheTileImage(zoom, i, j, listener);
        } catch (Throwable ex) {
          LOGGER.log(Level.SEVERE, null, ex);
        }
      });
    }
  }

  /**
   * Synchronously request a tile image.
   * <p>
   * If the file for the requested tile is available in the cache, it is directly returned.
   * Else the file is read from the OSM server and then it is returned.
   * 
   * @param zoom the zoom level, typically from 1 through 19.
   * @param i The column index, goes from 0 (left edge is 180 °W) to 2^zoom − 1 (right edge is 180 °E)
   * @param j Index within the column, goes from 0 (top edge is 85.0511 °N) to 2^zoom − 1 (bottom edge is 85.0511 °S)
   */
  public Image loadTile(int zoom, long i, long j) {
    if (!tileIsAvailableInCache(zoom, i, j)) {
      final HttpURLConnection openConnection;
      String urlString = buildImageUrlString(zoom, i, j);
      try {
        URL url = new URL(urlString);
        openConnection = (HttpURLConnection) url.openConnection();
        /*
         * A user agent is any software, acting on behalf of a user, which "retrieves, renders and facilitates end-user interaction with Web content.
         * In HTTP, the User-Agent string is often used for content negotiation, where the origin server selects suitable content or operating parameters for the response.
         * For example, the User-Agent string might be used by a web server to choose variants based on the known capabilities of a particular version of client software.
         * The concept of content tailoring is built into the HTTP standard in RFC 1945 "for the sake of tailoring responses to avoid particular user agent limitations.”
         * 
         * OSM requires a valid HTTP User-Agent identifying the application. 
         */
        openConnection.addRequestProperty("User-Agent", HTTP_AGENT);
        openConnection.setConnectTimeout(TIMEOUT);
        openConnection.setReadTimeout(TIMEOUT);
      } catch (IOException ex) {
        LOGGER.log(Level.SEVERE, null, ex);
        return null;
      }

      InputStream inputStream = null;
      FileOutputStream fos = null;
      String enc = File.separator + zoom + File.separator + i + File.separator + j + ".png";
      File tileFile = new File(CACHE_ROOT, enc);
      try {
        inputStream = openConnection.getInputStream();  // Implicitly performs .connect().
        LOGGER.info("retrieve " + urlString + " and store " + enc);
        tileFile.getParentFile().mkdirs();
        fos = new FileOutputStream(tileFile);
        byte[] buff = new byte[4096];
        int len = inputStream.read(buff);
        while (len > 0) {
          fos.write(buff, 0, len);
          len = inputStream.read(buff);
        }
      } catch (IOException ex) {
        LOGGER.log(Level.SEVERE, null, ex);
      } finally {
        try {
          if (fos != null) {
            fos.close();
          }
          if (inputStream != null) {
            inputStream.close();
          }
        } catch (IOException ex) {
          LOGGER.log(Level.WARNING, null, ex);
        }
      }
    }

    Image image = fromFileCache(zoom, i, j);
    return image;
  }

  /**
   * Check whether a tile image is available in the cache.
   * 
   * @param zoom the zoom level, typically from 1 through 19.
   * @param i The column index, goes from 0 (left edge is 180 °W) to 2^zoom − 1 (right edge is 180 °E)
   * @param j Index within the column, goes from 0 (top edge is 85.0511 °N) to 2^zoom − 1 (bottom edge is 85.0511 °S)
   * @return true if the tile image is available in the cache, false otherwise
   */
  private boolean tileIsAvailableInCache(int zoom, long i, long j) {
    File tileFile = createTileFile(zoom, i, j);
    return tileFile.exists();
  }

  /**
   * Create the File object for a tile image in the cache.
   * 
   * @param zoom the zoom level, typically from 1 through 19.
   * @param i The column index, goes from 0 (left edge is 180 °W) to 2^zoom − 1 (right edge is 180 °E)
   * @param j Index within the column, goes from 0 (top edge is 85.0511 °N) to 2^zoom − 1 (bottom edge is 85.0511 °S)
   * @return the File object for the given tile image.
   */
  private File createTileFile(int zoom, long i, long j) {
    String tileFileName = zoom + File.separator + i + File.separator + j + ".png";
    return new File(CACHE_ROOT, tileFileName);
  }

  //    @Override
  //    public Image loadTile(int zoom, long i, long j) {
  //        Image image = fromFileCache(zoom, i, j);
  //        if (image == null) {
  //            if (HAS_FILE_CACHE) {
  //                EXECUTOR.execute(() -> {
  //                    try {
  //                        cacheThread.cacheImage(zoom, i, j);
  //                    } catch (Throwable ex) {
  //                        logger.log(Level.SEVERE, null, ex);
  //                    }
  //                });
  //            }
  //            image = super.loadTile(zoom, i, j);
  //        }
  //        return image;
  //      return null;
  //    }

  /**
   * Return an image from filecache, or null if the cache doesn't contain the
   * image
   *
   * @param zoom
   * @param i
   * @param j
   * @return
   */
  static private Image fromFileCache(int zoom, long i, long j) {
    if (!HAS_FILE_CACHE) {
      return null;
    }
    String tag = zoom + File.separator + i + File.separator + j + ".png";
    File f = new File(CACHE_ROOT, tag);
    if (f.exists()) {
      Image answer = new Image(f.toURI().toString(), true);
      return answer;
    }
    return null;
  }

  /**
   * Thread to read tiles from the OSM server and store them in the cache.
   * <p>
   * Clients can request tiles by calling {@link #cacheImage}.
   */
  private static class CacheThread extends Thread {

    /**
     * Double ended queue for the tile requests.
     */
    private final BlockingDeque<TileReference> deque = new LinkedBlockingDeque<>();

    /**
     * Constructor.
     */
    public CacheThread() {
      setDaemon(true);
      setName("CacheImagesThread");
    }

    @Override
    public void run() {
      while (true) {
        try {
          TileReference tileReference = deque.pollFirst(10, TimeUnit.SECONDS);
          if (tileReference != null) {
            doCache(tileReference);
          }
        } catch (InterruptedException e) {
          LOGGER.log(Level.WARNING, null, e);
        }
      }
    }

    /**
     * Request for a map tile to be cached.
     * 
     * @param zoom the zoom level, typically from 1 through 19.
     * @param i The column index, goes from 0 (left edge is 180 °W) to 2^zoom − 1 (right edge is 180 °E)
     * @param j Index within the column, goes from 0 (top edge is 85.0511 °N) to 2^zoom − 1 (bottom edge is 85.0511 °S)
     * @param listener listener on the request.
     */
    public void cacheTileImage(int zoom, long i, long j, Consumer<Image> listener) {
      TileReference tileReference = new TileReference(zoom, i, j, listener);
      synchronized (deque) {
        if (!deque.contains(tileReference)) {
          deque.offerFirst(tileReference);
        } else {
          LOGGER.severe("Skipping already queued tile");
        }
      }
    }

    /**
     * Retrieve a map tile from the OSM server.
     * 
     * @param tileReference specification of the tile to be retrieved.
     */
    private void doCache(TileReference tileReference) {
      final HttpURLConnection openConnection;
      String urlString = buildImageUrlString(tileReference.zoom(), tileReference.i(), tileReference.j());
      try {
        URL url = new URL(urlString);
        openConnection = (HttpURLConnection) url.openConnection();
        /*
         * A user agent is any software, acting on behalf of a user, which "retrieves, renders and facilitates end-user interaction with Web content.
         * In HTTP, the User-Agent string is often used for content negotiation, where the origin server selects suitable content or operating parameters for the response.
         * For example, the User-Agent string might be used by a web server to choose variants based on the known capabilities of a particular version of client software.
         * The concept of content tailoring is built into the HTTP standard in RFC 1945 "for the sake of tailoring responses to avoid particular user agent limitations.”
         * 
         * OSM requires a valid HTTP User-Agent identifying the application. 
         */
        openConnection.addRequestProperty("User-Agent", HTTP_AGENT);
        openConnection.setConnectTimeout(TIMEOUT);
        openConnection.setReadTimeout(TIMEOUT);
      } catch (IOException ex) {
        LOGGER.log(Level.SEVERE, null, ex);
        return;
      }

      InputStream inputStream = null;
      FileOutputStream fos = null;
      String enc = File.separator + tileReference.zoom() + File.separator + tileReference.i() + File.separator + tileReference.j() + ".png";
      File tileFile = new File(CACHE_ROOT, enc);
      try {
        inputStream = openConnection.getInputStream();  // Implicitly performs .connect().
        LOGGER.info("retrieve " + urlString + " and store " + enc);
        tileFile.getParentFile().mkdirs();
        fos = new FileOutputStream(tileFile);
        byte[] buff = new byte[4096];
        int len = inputStream.read(buff);
        while (len > 0) {
          fos.write(buff, 0, len);
          len = inputStream.read(buff);
        }
      } catch (IOException ex) {
        LOGGER.log(Level.SEVERE, null, ex);
      } finally {
        try {
          if (fos != null) {
            fos.close();
          }
          if (inputStream != null) {
            inputStream.close();
          }
        } catch (IOException ex) {
          LOGGER.log(Level.WARNING, null, ex);
        }
      }

      Image image = new Image("file:" + tileFile.getAbsolutePath());
      tileReference.listener.accept(image);
    }
  }
  
  /**
   * Specification for a tile image.
   * @param zoom the zoom level, typically from 1 through 19.
   * @param i The column index, goes from 0 (left edge is 180 °W) to 2^zoom − 1 (right edge is 180 °E)
   * @param j Index within the column, goes from 0 (top edge is 85.0511 °N) to 2^zoom − 1 (bottom edge is 85.0511 °S)
   * @param listener listener for the result.
   */
  record TileReference(int zoom, long i, long j, Consumer<Image> listener) {}

}
