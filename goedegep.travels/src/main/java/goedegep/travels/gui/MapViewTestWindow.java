package goedegep.travels.gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import goedegep.geo.WGS84BoundingBox;
import goedegep.jfx.JfxStage;
import goedegep.mapview.MapPoint;
import goedegep.mapview.image.MapImage;
import goedegep.mapview.view.MapView;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MapViewTestWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(TravelsWindow.class.getName());
  private static final double FULL_MAP_ZOOM_LEVEL = 2.0;     // Shows almost complete world map. Determined with trial and error.
  
  private goedegep.gluonhq.maps.MapView gluonMapView;
  private MapView myMapView;
  private MapViewTestLayer mapViewTestLayer;
  private TextArea statusTextArea;
  
  public MapViewTestWindow() {
    super(null, "MapView test window");
    
//    gluonMapView = new goedegep.gluonhq.maps.MapView();
    myMapView = new MapView();
//    mapViewTestLayer = new MapViewTestLayer();
//    myMapView.addLayer(mapViewTestLayer);
    
    myMapView.zoom().addListener((_, _, _) -> updateStatusTextArea());
    
    createGUI();
    
    updateStatusTextArea();
    
    this.show();
  }
  
  private void createGUI() {
    VBox mainBox = new VBox();
    mainBox.getChildren().add(createButtonBar());
    
    HBox mapsBox = new HBox();
    mapsBox.setPadding(new Insets(12.0));
    mapsBox.setSpacing(20.0);
    mapsBox.setMinWidth(500);
    mapsBox.setMinHeight(300);
    
    HBox gluonBox = new HBox();
    gluonBox.setMinWidth(300);
    gluonBox.setMaxWidth(300);
    gluonBox.setMinHeight(150);
    gluonBox.setMaxHeight(150);
    gluonBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , null)));
//    gluonBox.getChildren().add(gluonMapView);
    
    HBox myBox = new HBox();
    myBox.setMinWidth(300);
    myBox.setMaxWidth(300);
    myBox.setMinHeight(150);
    myBox.setMaxHeight(150);
    myBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , null)));
    myBox.getChildren().add(myMapView);
    
    mapsBox.getChildren().addAll(gluonBox, myBox);
    
    mainBox.getChildren().add(mapsBox);    
    
    mainBox.getChildren().add(statusTextArea = new TextArea());
    
    Scene scene = new Scene(mainBox);
    setScene(scene);
  }
  
  private Node createButtonBar() {
    HBox hBox = new HBox();
    hBox.setSpacing(20.0);
    hBox.setPadding(new Insets(6.0, 12.0, 6.0, 12.0));
    
    Button button;
    
    button = new Button("Zoom to full map");
    button.setOnAction(_ -> zoomToFullMap());
    hBox.getChildren().add(button);
    
    button = new Button("+");
    button.setOnAction(_ -> zoomIn());
    hBox.getChildren().add(button);
    
    button = new Button("-");
    button.setOnAction(_ -> zoomOut());
    hBox.getChildren().add(button);
    
    button = new Button("↑");
    button.setOnAction(_ -> moveUp());
    hBox.getChildren().add(button);
    
    button = new Button("↓");
    button.setOnAction(_ -> moveDown());
    hBox.getChildren().add(button);
    
    button = new Button("←");
    button.setOnAction(_ -> moveLeft());
    hBox.getChildren().add(button);
    
    button = new Button("→");
    button.setOnAction(_ -> moveRight());
    hBox.getChildren().add(button);
    
    button = new Button("Home");
    button.setOnAction(_ -> setCenterToHome());
    hBox.getChildren().add(button);
    
    button = new Button("Fly home");
    button.setOnAction(_ -> flyHome());
    hBox.getChildren().add(button);
    
    button = new Button("Save map image");
    button.setOnAction(_ -> saveMapImage());
    hBox.getChildren().add(button);
    
    return hBox;
  }

  private Object zoomToFullMap() {
    gluonMapView.setZoom(FULL_MAP_ZOOM_LEVEL);
    myMapView.setZoom(FULL_MAP_ZOOM_LEVEL);
    
    return null;
  }

  private void zoomIn() {
    LOGGER.severe("=> XXXXXXXXXX Zooming in");
//    double gluonZoom = gluonMapView.getZoom();
//    gluonMapView.setZoom(gluonZoom + 1);
    double myZoom = myMapView.getZoom();
    myMapView.setZoom(myZoom + 1);
  }

  private void zoomOut() {
//    double gluonZoom = gluonMapView.getZoom();
//    gluonMapView.setZoom(gluonZoom - 1);
    double myZoom = myMapView.getZoom();
    myMapView.setZoom(myZoom - 1);
  }

  private void moveUp() {
    gluonMapView.moveY(50.0);
    myMapView.moveY(50.0);
  }

  private void moveDown() {
    gluonMapView.moveY(-50.0);
    myMapView.moveY(-50.0);
  }

  private void moveLeft() {
    gluonMapView.moveX(50.0);
    myMapView.moveX(50.0);
  }

  private void moveRight() {
    gluonMapView.moveX(-50.0);
    myMapView.moveX(-50.0);
  }

  private void setCenterToHome() {
//    gluonMapView.setCenter(51.476743, 5.429724);
    myMapView.setCenter(51.476743, 5.429724);
  }
  
  private void flyHome() {
    // gluonMapView.flyTo(0.0, new goedegep.gluonhq.maps.MapPoint(51.476743, 5.429724), 4.0);
    myMapView.flyTo(0.0, new MapPoint(51.476743, 5.429724), 4.0, 8.0);
  }

  private void saveMapImage() {
    MapImage mapImage =  new MapImage();
    MapViewTestLayer mapViewTestLayer = new MapViewTestLayer();
    mapImage.addLayer(mapViewTestLayer);

    mapImage.setSize(500, 300);
//    double zoom = myMapView.getZoom();
    mapImage.setZoom(6.0);
//    MapPoint center = myMapView.getCenter();
//    mapImage.setCenter(center);
    
    Path mapImageFilePath = Path.of("D:\\SoulSeek\\MapImage.jpg");
    try {
      Files.deleteIfExists(mapImageFilePath);
      
      SnapshotParameters snapShotParameters = new SnapshotParameters();
      WritableImage writebleImage = new WritableImage((int) mapImage.getWidth(), (int) mapImage.getHeight());
      mapImage.snapshot(snapShotParameters, writebleImage);

      saveImageAsJpeg(writebleImage, mapImageFilePath.toFile());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public static void saveImageAsJpeg(Image image, File file) {
    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
    BufferedImage imageRGB = new BufferedImage(
        bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.OPAQUE);
    Graphics2D graphics = imageRGB.createGraphics();
    graphics.drawImage(bufferedImage, 0, 0, null);
    try {
      ImageIO.write(imageRGB, "jpg", file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void updateStatusTextArea() {
    StringBuilder buf = new StringBuilder();
    MapPoint center = myMapView.getCenter();
    buf.append("Center: ").append(center.getLatitude()).append(", ").append(center.getLongitude()).append("\n");
    buf.append("Zoom: ").append(myMapView.getZoom()).append("\n");
    WGS84BoundingBox visibleMapCoordinates = myMapView.getVisibleMapCoordinates();
    buf.append("Visible map coordinates: ");
    if (visibleMapCoordinates != null) {
      buf.append(visibleMapCoordinates.getNorth()).append(", ").append(visibleMapCoordinates.getEast()).append(", ")
      .append(visibleMapCoordinates.getSouth()).append(", ").append(visibleMapCoordinates.getWest()).append("\n");
    } else {
      buf.append("not available\n");
    }
    
    statusTextArea.setText(buf.toString());
  }
}
