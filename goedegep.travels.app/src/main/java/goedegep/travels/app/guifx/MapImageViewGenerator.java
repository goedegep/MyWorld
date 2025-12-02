package goedegep.travels.app.guifx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.eclipse.emf.ecore.EObject;

import com.gluonhq.maps.MapPoint;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.travels.app.logic.MapImageType;
import goedegep.travels.app.logic.TravelsRegistry;
import goedegep.travels.app.logic.VacationsUtils;
import goedegep.travels.model.Day;
import goedegep.travels.model.MapImage;
import goedegep.travels.model.Vacation;
import javafx.beans.value.ChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 * A JavaFX Stage that generates a view for a MapImage and save it as an image file.
 * The view is based on the properties of the provided MapImage object.
 * 
 * TODO: This class requires methods of the VacationsWindow. So either put these methods in a vacations GUI util class or make this class a subclass of VacationsWindow.
 */

public class MapImageViewGenerator extends JfxStage {
  
  /**
   * Constructor for MapImageViewGenerator.
   *
   * @param customization The GUI customization.
   * @param vacationsWindow The VacationsWindow instance, used to add days or vacations to the map view.
   * @param mapImageParent The parent EObject of the {@code mapImage}, used to determine context.
   * @param mapImage The {@code MapImage} for which an image file is to be generated.
   * @param mapImageType The type of the {@code mapImage}, determining how to populate the map view.
   */
  public MapImageViewGenerator(CustomizationFx customization, TravelsWindow vacationsWindow, EObject mapImageParent, MapImage mapImage, MapImageType mapImageType) {
    // The window title is the title of the map image, or "MapImage" if the title is null.
    super(customization, mapImage.getTitle() != null ? mapImage.getTitle() : "MapImage");
    
    TravelMapView imageTravelMapView = new TravelMapView(customization, this, null);

    // Set the size of the map view.
    Double height = mapImage.getImageHeight();
    if (height != null) {
      imageTravelMapView.setPrefHeight(height);
    }
    Double width = mapImage.getImageWidth();
    if (width != null) {
      imageTravelMapView.setPrefWidth(width);
    }

//    imageTravelMapView.getBaseMap().setCenter(mapImage.getCenterLatitude(), mapImage.getCenterLongitude());

    // Set the zoom level.
    imageTravelMapView.setZoom(mapImage.getZoom());

    // Set the center.
//    Point2D center = new Point2D(mapImage.getCenterLatitude(), mapImage.getCenterLongitude());
//    imageTravelMapView.getBaseMap().setCenter(center);
//    BaseMap baseMap = imageTravelMapView.getBaseMap();
    MapPoint mapPoint = new MapPoint(mapImage.getCenterLatitude(), mapImage.getCenterLongitude());
    imageTravelMapView.flyTo(0, mapPoint, 0.2);
    

//    baseMap.prefCenterLat().set(mapImage.getCenterLatitude());
//    baseMap.prefCenterLon().set(mapImage.getCenterLongitude());
    
    // Add the map image type specific content.
    switch (mapImageType) {
    case LOCATION -> {
      System.out.println("MapImageType LOCATION not implemented yet.");
      //    Location location = getLocationForObject();
      //    if (location != null) {
      //      addLocationToMapView(imageTravelMapView, location, false);
      //    }
    }
    case DAY -> {
      Day day = VacationsUtils.getAncestorOfType(mapImageParent, Day.class);
      if (day != null) {
        vacationsWindow.addDayToMapView(imageTravelMapView, day, false);
      }
    }
    case TRAVEL -> {
      Vacation vacation = VacationsUtils.getVacationForObject(mapImageParent);
      if (vacation != null) {
        vacationsWindow.addVacationToMapView(imageTravelMapView, vacation, false, false);
      }
    }
    }
    
    Scene scene = new Scene(imageTravelMapView);
    setScene(scene);
    
    show();
    
    /*
     * Create a MapImageViewProgressTask, on ready write the map.
     */
    MapImageViewProgressTask mapImageViewProgressTask = new MapImageViewProgressTask(imageTravelMapView, scene);
    
    // Handle results - write the image to the file.
    ChangeListener<Boolean> listener = (_, _, _) -> {
      handleMapReady(mapImage, imageTravelMapView);
    };
    mapImageViewProgressTask.valueProperty().addListener(listener);

    Thread mapImageViewGeneratorThread = new Thread(mapImageViewProgressTask);
    mapImageViewGeneratorThread.setDaemon(true);
    mapImageViewGeneratorThread.start();
    
  }
  
  private void handleMapReady(MapImage mapImage, TravelMapView imageTravelMapView) {
    // write the image to a file
    SnapshotParameters snapShotParameters = new SnapshotParameters();
    WritableImage writebleImage = new WritableImage((int) imageTravelMapView.getWidth(), (int) imageTravelMapView.getHeight());
    imageTravelMapView.snapshot(snapShotParameters, writebleImage);
    
    // Save the image
    Path path = Paths.get(TravelsRegistry.getInstance().getVacationsFolderName(), mapImage.getFileName());
    try {
      Files.deleteIfExists(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
    saveImageAsJpeg(writebleImage, path);
  }
      

  public static void saveImageAsJpeg(Image image, Path path) {
    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
    BufferedImage imageRGB = new BufferedImage(
        bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.OPAQUE);
    Graphics2D graphics = imageRGB.createGraphics();
    graphics.drawImage(bufferedImage, 0, 0, null);
    try {
      ImageIO.write(imageRGB, "jpg", path.toFile());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
