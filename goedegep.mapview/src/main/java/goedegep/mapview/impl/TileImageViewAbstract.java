package goedegep.mapview.impl;

import goedegep.mapview.maptile.TileRetriever;
import goedegep.mapview.maptile.osm.CachedOsmTileRetriever;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.SnapshotParameters;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.VPos;
import javafx.scene.text.TextAlignment;

/**
 * This class is the common part of an ImageView for a single map tile (zoom, i and j).
 * The {@code Image} is loaded synchronously ({@link goedegep.mapview.image.impl.TileImageView}) or asynchronously ({@link goedegep.mapview.view.impl.TileImageView}) using a {@link TileRetriever}.<br/>
 * <br/>
 * Via the method {@link #setPlaceholderImageSupplier} a supplier of a placeholder Image can be set. This Image will then be shown while loading the actual tile.
 * <br/>
 * TODO: Clients are only interested in whether the tile is loaded or not. So maybe we can simplify the progress property to the boolean 'downloading' property.
 *       If not, remove the lazy creation of the progress property, as it is always used.
 * TODO: rename to MapTileImageViewAbstract (and subclasses also)
 */
public class TileImageViewAbstract extends ImageView {
  
  /**
   * The map provider specific {@code TileRetriever} used to load tiles.
   * For now we use a {@link CachedOsmTileRetriever}, but in the future we might want to make this configurable.
   */
  protected static final TileRetriever TILE_RETRIEVER = new CachedOsmTileRetriever();
  
  protected Image addTileParametersToImage(Image image, int zoom, long i, long j) {
    if (image == null) {
      return null;
    }

    double width = image.getWidth();
    double height = image.getHeight();
    if (width <= 0 || height <= 0) {
      return image;
    }

    // Draw original image onto a canvas
    Canvas canvas = new Canvas(width, height);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.drawImage(image, 0, 0, width, height);

    // Prepare text
    String text = zoom + " [" + i + ", " + j + "]";

    // Choose font size relative to the tile size
    double fontSize = Math.max(10, Math.min(width, height) * 0.18);
    Font font = Font.font("System", FontWeight.BOLD, fontSize);
    gc.setFont(font);
    gc.setTextAlign(TextAlignment.CENTER);
    gc.setTextBaseline(VPos.TOP);

    // Position near top center
    double x = width / 2.0;
    double y = Math.max(2.0, height * 0.03);

    // Draw stroke for readability, then fill
    gc.setLineWidth(Math.max(1.0, fontSize * 0.06));
    gc.setStroke(Color.color(0.0, 0.0, 0.0, 0.85));
    gc.strokeText(text, x, y);
    gc.setFill(Color.color(1.0, 1.0, 1.0, 0.95));
    gc.fillText(text, x, y);
    
    gc.setLineWidth(4.0);
    
    gc.setStroke(Color.color(0.0, 0.0, 0.0, 0.85));
    gc.setFill(Color.color(1.0, 1.0, 1.0, 0.95));
    gc.strokeRoundRect(2.0, 2.0, width - 4, height - 4, 5, 5);

    // Snapshot canvas to an Image and return
    SnapshotParameters params = new SnapshotParameters();
    params.setFill(Color.TRANSPARENT);
    WritableImage out = new WritableImage((int) Math.ceil(width), (int) Math.ceil(height));
    canvas.snapshot(params, out);
    return out;
  }

  public TileRetriever getTileRetriever() {
    return TILE_RETRIEVER;
  }
}