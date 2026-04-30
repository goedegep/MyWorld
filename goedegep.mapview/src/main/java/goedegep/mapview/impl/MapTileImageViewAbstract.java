package goedegep.mapview.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.mapview.maptile.TileRetriever;
import goedegep.mapview.maptile.osm.CachedOsmTileRetriever;
import javafx.geometry.VPos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Scale;

public class MapTileImageViewAbstract extends ImageView {
  private static final Logger LOGGER = Logger.getLogger(MapTileImageViewAbstract.class.getName());
  
  /**
   * The map provider specific {@code TileRetriever} used to load tiles.
   * For now we use a {@link CachedOsmTileRetriever}, but in the future we might want to make this configurable.
   */
  protected static final TileRetriever TILE_RETRIEVER = new CachedOsmTileRetriever();

  /**
   * The zoom level of the tile (which may differ from the current zoom level of the map display).
   */
  public final int tileZoom;
  
  /**
   * The tile column.
   */
  public final long i;
  
  /**
   * The tile row.
   */
  public final long j;

  /**
   * The base map for which this is a map tile.
   * In this class we don't need any specific MapTile.
   */
  protected final BaseMapAbstract<?> baseMapAbstract;

  /**
   * In most cases, a tile will be shown scaled. The value for the scale
   * factor depends on the active zoom and the tile-specific myZoom.
   * Note that covering tiles will have a different scale than the 'normal' tiles. Therefore the scale cannot be at base map level. 
   */
  protected final Scale scale = new Scale();
  
  
  /**
   * Constructor.
   * 
   * @param baseMapAbstract The base map for which this is a map tile.
   * @param tileZoom The zoom level of the tile
   * @param i The tile column.
   * @param j The tile row.
   */
  public MapTileImageViewAbstract(BaseMapAbstract<?> baseMapAbstract, int tileZoom, long i, long j) {
    this.baseMapAbstract = baseMapAbstract;
    this.tileZoom = tileZoom;
    this.i = i;
    this.j = j;
    
    // Pivot point for scaling is the top left corner of the tile.
    scale.setPivotX(0);
    scale.setPivotY(0);
    getTransforms().add(scale);
  }

  /**
   * Recalculates the position of this tile (translateX, translateY). The position depends on the current zoom level (and translation of the base map).
   * TODO: rename to calculatePositionAndVisible or remove visibility
   */
  public void calculatePosition() {
    LOGGER.severe("=> calculating position for " + this);
    double mapZoom = baseMapAbstract.zoom().get();
//    LOGGER.info("Visible: " + isVisible());
//    if (baseMapAbstract.getChildren().contains(this)) {
//      LOGGER.info("Tile is in scene graph");
//    } else {
//      LOGGER.severe("Tile is NOT in scene graph");
//    }
//    int visibleWindow = (int) floor(mapZoom + BaseMapAbstract.TIPPING);
//    boolean visible =  visibleWindow == myZoom ||
//        isCovering() ||
//        ((visibleWindow >= BaseMap.MAX_ZOOM) && (myZoom == BaseMap.MAX_ZOOM - 1));
//    this.setVisible(visible);
//    LOGGER.fine("visible tile " + this + "? " + this.isVisible() + (this.isVisible() ? " covering? " + isCovering() : ""));
    double sf = Math.pow(2, mapZoom - tileZoom);
    scale.setX(sf);
    scale.setY(sf);
    double translateX = 256 * i * sf;  //  + baseMapAbstract.translateXProperty().get()
    double translateY = 256 * j * sf;  // + baseMapAbstract.translateYProperty().get()
//    LOGGER.severe("TILE: translateX = " + translateX + ", translateY = " + translateY);
    setTranslateX(translateX);
    setTranslateY(translateY);
    LOGGER.severe("<= BASE_MAP: translateX = " + baseMapAbstract.translateXProperty().get() + ", translateY = " + baseMapAbstract.translateYProperty().get());
  }
  
  /**
   * Get the zoom level of this tile (which may differ from the current zoom level of the map display).
   * 
   * @return the zoom level of the tile.
   */
  public int getTileZoom() {
    return tileZoom;
  }

  /**
   * Recalculates the position of this tile. The position depends on the current zoom level and translation of the base map.
   */
  private void calculatePosition2() {
    double currentZoom = baseMapAbstract.zoom().get();
//    int visibleWindow = (int) floor(currentZoom + BaseMapAbstract.TIPPING);
//    boolean visible =  visibleWindow == myZoom ||
//        ((visibleWindow >= BaseMap.MAX_ZOOM) && (myZoom == BaseMap.MAX_ZOOM - 1));
//    this.setVisible(visible);
//    LOGGER.fine("visible tile " + this + "? " + this.isVisible());
    double sf = Math.pow(2, currentZoom - tileZoom);
    scale.setX(sf);
    scale.setY(sf);
    setTranslateX(256 * i * sf);
    setTranslateY(256 * j * sf);
  }
  
  protected void markDirty() {
    baseMapAbstract.markDirty();
  }

  @Override
  public String toString() {
    return tileZoom + " [" + i + "," + j + "]";
  }
  
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
  
  /**
   * Get status information about the the tile.
   * @return status information about the tile, including the tile zoom level and tile coordinates.
   */
  public String getStatusInformation() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("Tile: \n");
    buf.append("  Zoom: ").append(tileZoom)
    .append(", Column: ").append(i)
    .append(", Row: ").append(j)
    .append(", Scale: ").append(scale.getX()).append(" x ").append(scale.getY()).append("\n");
    buf.append("TranslateX/Y: " + getTranslateX() + ", " + getTranslateY() + "\n");
    
    return buf.toString();
  }

}
