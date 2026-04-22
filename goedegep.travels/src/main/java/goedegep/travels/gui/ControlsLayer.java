package goedegep.travels.gui;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Supplier;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.mapview.MapLayer;
import goedegep.resources.ImageSize;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ControlsLayer extends MapLayer {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(ControlsLayer.class.getName());
  
  private HBox attribution;
  private double attributionWidth;
  private double attributionHeight;
    
  public ControlsLayer(CustomizationFx customization, Supplier<LocationSearchWindow> searchWindowSupplier) {
    
    Node searchIcon = new ImageView(TravelImageResource.SEARCH.getIcon(ImageSize.SIZE_2));
    searchIcon.setTranslateX(30);
    searchIcon.setTranslateY(30);
    searchIcon.setOnMouseClicked(_ -> searchWindowSupplier.get());
    
    getChildren().add(searchIcon);
    
    Label label = new Label("Search");
    label.setTranslateX(100);
    label.setTranslateY(100);
    getChildren().add(label);
    
    addOSMAttribution();
  }

  /**
   * Add a small attribution panel to the controls layer, with a link to OpenStreetMap.
   */
  private void addOSMAttribution() {
    
    attribution = new HBox();
    Color backgroundColor = Color.rgb(255, 255, 255, 0.8);
    double spacing = 5;
    
    // Create a dummy Canvas just to get its default font
    Canvas canvas = new Canvas();
    Font font = canvas.getGraphicsContext2D().getFont();
    
    // Canvas for the text "Map data from "
    String text = "Map data from";
    Text helper = new Text(text);
    helper.setFont(font);
    double textWidth = helper.getLayoutBounds().getWidth();
    double textHeight = helper.getLayoutBounds().getHeight();
    
    canvas = new Canvas(textWidth + spacing, textHeight + 2 * spacing);
    attributionWidth = canvas.getWidth();
    attributionHeight = canvas.getHeight();
    GraphicsContext gc = canvas.getGraphicsContext2D();
    
    gc.setFill(backgroundColor);
    gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
    
    gc.setFill(Color.BLACK);
    gc.fillText(text, spacing, textHeight);
    attribution.getChildren().add(canvas);
    
    // Canvas for the link
    text = "OpenStreetMap";
    helper = new Text(text);
    helper.setFont(font);
    textWidth = helper.getLayoutBounds().getWidth();
    
    canvas = new Canvas(textWidth + 2 * spacing, textHeight + 2 * spacing);
    attributionWidth += canvas.getWidth();
    gc = canvas.getGraphicsContext2D();
    
    gc.setFill(backgroundColor);
    gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());

    gc.setFill(Color.BLUE);
    double x = spacing;
    double y = textHeight;
    gc.fillText(text, x, y);

    // 3. Draw the line
    // We add a small offset (e.g., 2-3 pixels) so it's not touching the letters
    gc.setLineWidth(1.0);
    gc.setStroke(Color.BLUE);
    gc.strokeLine(x, y + 2, x + textWidth, y + 2);
    
    canvas.setOnMouseClicked(_ -> {
        try {
         URI uri = new URI("https://www.openstreetmap.org");
         goedegep.util.desktop.DesktopUtil.openBrowser(uri);
        } catch (URISyntaxException e) {
        }
      });
    
    attribution.getChildren().add(canvas);
    
    getChildren().add(attribution);
  }
  
  @Override
  public void layoutLayer() {

    double height = mapViewAbstract.getHeight();
    attribution.setTranslateY(height - attributionHeight);
    double width = mapViewAbstract.getWidth();
    attribution.setTranslateX(width - attributionWidth);
  }
}