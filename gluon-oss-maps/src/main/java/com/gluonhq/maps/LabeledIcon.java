package com.gluonhq.maps;

import java.util.logging.Logger;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;

/**
 * This class provides an icon with a label to be shown on a {@link MapLayer}.
 * <p>
 * The top-left point of the icon is meant to be at the location to which it belongs.<br/>
 * Therefore a small arrow is shown in the top-left of the icon (pointing to the point on the map).
 */
public class LabeledIcon extends VBox {
  private static final Logger LOGGER = Logger.getLogger(LabeledIcon.class.getName());
  
  /**
   * Small arrow shown in the top-left of the labeled icon.
   * This arrow is meant to point to the location.
   */
  private static Image topLeftArrow = null;
  
  /**
   * Width of the icon.
   */
  private double width;
  
  /**
   * Height of the icon.
   */
  private double height;
  
  /**
   * A {@link Canvas} on which the icon and arrow are drawn.
   */
  private Canvas iconCanvas;
  
  /**
   * A {@link Canvas} on which the text is drawn.
   */
  private Canvas textCanvas;
  
  /**
   * Create the {@code topLeftArrow}.
   */
  static {
    WritableImage writableImage = new WritableImage(5, 5);
    PixelWriter pixelWriter = writableImage.getPixelWriter();
    pixelWriter.setColor(0, 0, Color.BLACK);
    pixelWriter.setColor(1, 0, Color.BLACK);
    pixelWriter.setColor(2, 0, Color.BLACK);
    pixelWriter.setColor(3, 0, Color.BLACK);
    pixelWriter.setColor(0, 0, Color.BLACK);
    pixelWriter.setColor(0, 1, Color.BLACK);
    pixelWriter.setColor(0, 2, Color.BLACK);
    pixelWriter.setColor(0, 3, Color.BLACK);
    pixelWriter.setColor(1, 1, Color.BLACK);
    pixelWriter.setColor(2, 2, Color.BLACK);
    pixelWriter.setColor(3, 3, Color.BLACK);
    pixelWriter.setColor(4, 4, Color.BLACK);
    
    topLeftArrow = writableImage;
  }

  /**
   * Constructor
   * 
   * @param iconImage the icon to be shown
   * @param labelText the text to be shown with the icon
   */
  public LabeledIcon(Image iconImage, String labelText) {
    LOGGER.info("=> labelText=" + labelText);
    
    
    javafx.scene.layout.FlowPane fp;
    setAlignment(Pos.CENTER);
    
    width = iconImage.getWidth();
    height = iconImage.getHeight();

    iconCanvas = new Canvas(width, height);
    GraphicsContext gc = iconCanvas.getGraphicsContext2D();
    gc.drawImage(iconImage, 0, 0); 
    gc.drawImage(topLeftArrow, 0, 0);

    getChildren().add(iconCanvas);

    if (labelText != null) {
      final Text aText = new Text(labelText);
      final double textWidth = aText.getLayoutBounds().getWidth();
      final double textHeight = aText.getLayoutBounds().getHeight();
      LOGGER.info("textWidth=" + textWidth);
      LOGGER.info("textHeight=" + textHeight);

      textCanvas = new Canvas(textWidth, textHeight);
      gc = textCanvas.getGraphicsContext2D();
      gc.fillText(labelText, 0, textHeight - 4);
      getChildren().add(textCanvas);
      
      double shiftRight = width / 2;
      LOGGER.info("=> shiftRight=" + shiftRight);
      setLayoutX(shiftRight);
      double shiftDown = (height + textHeight) / 2;
      LOGGER.info("=> shiftDown=" + shiftDown);
      setLayoutY(shiftDown);
    }
    
  }
  
  /**
   * Set the style (highlight) for when this item is selected.
   * <p>
   * The selected style is a {@code DropShadow}.
   * 
   * @param selected if true the item is selected, else it is not selected.
   */
  public void setSelectedStyle(boolean selected) {
    // The {@code DropShadow} is set on both {@code Canvas} children.
    for (Node node: getChildren()) {
      if (node instanceof Canvas canvas) {
        if (selected) {
          DropShadow ds = new DropShadow();
          ds.setOffsetY(4.0f);
          ds.setOffsetX(4.0f);
          ds.setColor(Color.CORAL);
          canvas.setEffect(ds);
        } else {
          canvas.setEffect(null);
        }
      }
    }
  }
  
  /**
   * Install mouse event handling.
   * <p>
   * Event handling is installed for mouse clicks on both canvases.
   * 
   * @param eventHandler the mouse event handler to be called on mouse clicks.
   */
  public void installMouseEventHandling(EventHandler<? super MouseEvent> eventHandler) {
    iconCanvas.setOnMouseClicked(eventHandler);
    if (textCanvas != null) {
      textCanvas.setOnMouseClicked(eventHandler);
    }
  }
  
  /**
   * Get x,y correction based on the zoom level.
   * <p>
   * When (almost) fully zoomed in you want to be able to see a location on the map. So the icon should not be on top of (centered on) the location.
   * Therefore the top left of the icon is at the location.<br/>
   * But when you zoom out, you can't see the details of a location anymore and then it looks much better if the icon is centered on the location.
   * This is accomplished by the zoom dependend correction provided by this method.
   * 
   * @param zoomLevel the zoom level for which a correction is to be calculated.
   * @return the x,y correction for {@code zoomLevel}.
   */
  public Translate getZoomDependendTranslateCorrection(double zoomLevel) {
    double zoomCorrectionX;
    double zoomCorrectionY;
    
    if (zoomLevel > 18.6) {
      zoomCorrectionX = 0;
      zoomCorrectionY = 0;
    } else {
      zoomCorrectionX = 1 / zoomLevel * (width / 2);
      zoomCorrectionY = 1.7 / zoomLevel * (height / 2);
    }
    
    return new Translate(zoomCorrectionX, zoomCorrectionY);
  }

}
