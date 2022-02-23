package com.gluonhq.maps;

import java.util.logging.Logger;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LabeledIcon extends VBox {
  private static final Logger LOGGER = Logger.getLogger(LabeledIcon.class.getName());
  private static Image topLeftArrow = null;
  
  private double width;
  private double height;
  
  private Canvas iconCanvas;
  private Canvas textCanvas;
  
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
   */
  public LabeledIcon(Image iconImage, String labelText) {
    LOGGER.info("=> labelText=" + labelText);
    
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
  
  public void mySetOnMouseClicked(EventHandler<? super MouseEvent> eventHandler) {
    iconCanvas.setOnMouseClicked(eventHandler);
    if (textCanvas != null) {
      textCanvas.setOnMouseClicked(eventHandler);
    }
  }

  public double getZoomDependendTranslateXCorrection(double zoomLevel) {
    LOGGER.info("=> vBox width=" + getWidth());
    double zoomCorrectionX;
    
    if (zoomLevel > 18.6) {
      zoomCorrectionX = 0;
    } else {
      zoomCorrectionX = 1 / zoomLevel * (width / 2);
    }
    
    LOGGER.info("width=" + width + ", zoomLevel=" + zoomLevel + ", zoomCorrectionX=" + zoomCorrectionX);
    return zoomCorrectionX;
  }

  public double getZoomDependendTranslateYCorrection(double zoomLevel) {
    LOGGER.info("=> vBox height=" + getHeight());
    double zoomCorrectionY;
    
    if (zoomLevel > 18.6) {
      zoomCorrectionY = 0;
    } else {
      zoomCorrectionY = 1.7 / zoomLevel * (height / 2);
    }
    
    LOGGER.info("height=" + height + ", zoomLevel=" + zoomLevel + ", zoomCorrectionY=" + zoomCorrectionY);
    return zoomCorrectionY;
  }
}
