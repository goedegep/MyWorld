package goedegep.travels.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;

import goedegep.geo.WGS84BoundingBox;
import goedegep.geo.WGS84Coordinates;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.travels.model.Boundary;
import goedegep.travels.model.Location;
import goedegep.util.douglaspeuckerreducer.DouglasPeuckerReducer;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class BoundariesPointsReductionWindow extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(BoundariesPointsReductionWindow.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  
  /**
   * The slider to select the amount of reduction.
   */
  private Slider slider;
  
  /**
   * Text field to show the number of points before reduction.
   */
  private TextField originalNumberOfPointsTextField;
  
  /**
   * Text field to show the number of points after reduction.
   */
  private TextField reducedNumberOfPointsTextField;
  
  /**
   * The basic map to which the {@code MapRelatedItemsLayer} is added.
   */
  private MapView mapView;
  
  /**
   * The {@code MapRelatedItemsLayer} showing the boundaries with the currently selected reduction.
   */
  private MapRelatedItemsLayer mapRelatedItemsLayer;
  
  /**
   * The location being edited.
   */
  private Location location;
  
  /**
   * The original boundaries (as a list of lists of boundary points) of the location, before any reduction was applied.
   * Used to restore the original boundary points if the user cancels the operation.
   */
  private List<List<WGS84Coordinates>> originalBoundaries;

  /**
   * Constructor.
   * 
   * @param customization the GUI customization, may not be null.
   * @param location the {@code Location} whose boundaries are to be reduced. May not be null.
   */
  public BoundariesPointsReductionWindow(CustomizationFx customization, Location location) {
    Objects.requireNonNull(customization, "customization must not be null");
    Objects.requireNonNull(location, "location must not be null");
    
    super(customization, "Boundaries Points Reduction");
        
    createGUI();
    setOnCloseRequest(_ -> closeWithoutMakingChanges());
    
    setLocation(location);
    
    show();
  }
  
  /**
   * Create the GUI.
   * <p>
   * Main panel is a {@code SplitPane}; left is the control panel, right is the map panel.
   */
  private void createGUI() {
    SplitPane centerPane = new SplitPane();
    centerPane.setOrientation(Orientation.HORIZONTAL);
    centerPane.setDividerPositions(0.3);
    
    // Control panel
    centerPane.getItems().add(createControlPanel());
    
    // MapView
    centerPane.getItems().add(createMapView());
    
    setScene(new Scene(centerPane, 1700, 900));
  }
  
  /**
   * Create the control panel for the left side of the window.
   * 
   * @return the control panel
   */
  private Node createControlPanel() {
    VBox mainPanel = componentFactory.createVBox(10.0, 10.0);
    
    VBox controlsBox = componentFactory.createVBox(10.0, 10.0);
    TextArea textArea = componentFactory.createTextArea(
        "Use the slider to select the amount of reduction; higher values mean more reduction." + NEWLINE +
        "Preview the shape on the map and if you're happy with the result, click 'Reduce'.");
    
    slider = componentFactory.createSlider(0.0, 100.0, 10.0);
    slider.setShowTickLabels(true);
    slider.setMajorTickUnit(5);
    slider.setMinorTickCount(5);
    slider.setSnapToTicks(true);
    slider.setShowTickMarks(true);
    slider.setOnMouseReleased(_ -> handleNewSliderValue(slider.getValue()));
    
    GridPane reductionInfoPane = componentFactory.createGridPane(12.0, 12.0);
    reductionInfoPane.setPadding(new Insets(12.0));
    
    Label label;
    int row = 0;
    
    // Row: value labels
    label = componentFactory.createLabel("Number of points");
    reductionInfoPane.add(label, 1, row);
    
    row++;
    
    // Row: current values
    label = componentFactory.createLabel("Current:");
    reductionInfoPane.add(label, 0, row);
    originalNumberOfPointsTextField = componentFactory.createTextField(null, 100, "The current number of points of the track(segment)");
    reductionInfoPane.add(originalNumberOfPointsTextField, 1, row);
    
    row++;
    
    // Row: reduced values
    label = componentFactory.createLabel("Reduced:");
    reductionInfoPane.add(label, 0, row);
    reducedNumberOfPointsTextField = componentFactory.createTextField(null, 100, "The reduced number of points of the track(segment)");
    reductionInfoPane.add(reducedNumberOfPointsTextField, 1, row);
    
    controlsBox.getChildren().addAll(textArea, slider, reductionInfoPane);
    mainPanel.getChildren().add(controlsBox);
    
    mainPanel.getChildren().add(createButtonsPanel());
    
    return mainPanel;
  }
  

  /**
   * Create a panel with a 'Cancel' and 'Reduce' button.
   * 
   * @return the created panel
   */
  private Node createButtonsPanel() {
    HBox buttonsBox = componentFactory.createHBox(12.0, 12.0);
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    buttonsBox.getChildren().add(spacer);
    
    Button cancelButton = componentFactory.createButton("Cancel", "Close this window without changing the boundaries");
    cancelButton.setOnAction(_ -> closeWithoutMakingChanges());
    buttonsBox.getChildren().add(cancelButton);
    
    Button reduceButton = componentFactory.createButton("Reduce", "Reduce the boundaries and close this window");
    reduceButton.setOnAction(_ -> closeWindow());  // as the changes are already in the original GPX track, nothing has to be done here.
    buttonsBox.getChildren().add(reduceButton);
        
    return  buttonsBox;
  }
  
  /**
   * Create the MapView for the right side of the window.
   * 
   * @return the created MapView
   */
  private Node createMapView() {
    mapView = new MapView();
    mapRelatedItemsLayer = new MapRelatedItemsLayer(customization, this);
    mapView.addLayer(mapRelatedItemsLayer);
    
    return mapView;
  }
  
  /**
   * Set the location whose boundaries are to be reduced.
   * <p>
   * The map is updated to show the location boundaries.
   * 
   * @param location the location, may not be null.
   */
  private void setLocation(Location location) {
    this.location = location;
    
    // save original boundaries
    originalBoundaries = new ArrayList<>();
    for (Boundary boundary: location.getBoundaries()) {
      originalBoundaries.add(new ArrayList<>(boundary.getPoints()));
    }
    
    // Clear the map, add the location, zoom and center the map
    mapRelatedItemsLayer.clear();
    WGS84BoundingBox boundingBox = mapRelatedItemsLayer.addLocation(location);
    Double zoomLevel = MapView.getZoomLevel(boundingBox);
    if (zoomLevel != null) {
      mapView.setZoom(zoomLevel);
    }

    WGS84Coordinates center = boundingBox.getCenter();
    MapPoint mapCenter = new MapPoint(center.getLatitude(), center.getLongitude());

    if (mapCenter != null) {
      mapView.flyTo(0.0, mapCenter, 2);
    }
    
    handleNewSliderValue(slider.getValue());
  }
  
  /**
   * Handle a new slider value.
   * <p>
   * The new boundaries are calculated and shown on the map.
   * The GUI is updated by calling {@link #updateGUI}.
   * 
   * @param sliderValue the new slider value.
   */
  private void handleNewSliderValue(double sliderValue) {
    int originalTotalPoints = 0;
    int reducedTotalPoints = 0;
    
    for (int i = 0; i < location.getBoundaries().size(); i++) {
      
      List<WGS84Coordinates> originalPoints = originalBoundaries.get(i);
      originalTotalPoints += originalPoints.size();
      List<WGS84Coordinates> reducedBoundary = DouglasPeuckerReducer.reduceWithTolerance(originalPoints, Math.pow(sliderValue, 1.8), null);
      reducedTotalPoints += reducedBoundary.size();
      
      Boundary boundary = location.getBoundaries().get(i);
      boundary.getPoints().clear();
      boundary.getPoints().addAll(reducedBoundary);
    }
    originalNumberOfPointsTextField.setText(Integer.toString(originalTotalPoints));
    reducedNumberOfPointsTextField.setText(Integer.toString(reducedTotalPoints));
  }

  /**
   * Close the window without making changes.
   * <p>
   * As the changes are currently in the GPX track, the original lists of track points have to be restored.
   */
  private void closeWithoutMakingChanges() {
    // restore original boundary points
    for (int i = 0; i < location.getBoundaries().size(); i++) {
      
      List<WGS84Coordinates> originalPoints = originalBoundaries.get(i);      
      Boundary boundary = location.getBoundaries().get(i);
      boundary.getPoints().clear();
      boundary.getPoints().addAll(originalPoints);
    }
    
    closeWindow();
  }

  /**
   * Close the window
   */
  private void closeWindow() {
    close();
  }
}
