package goedegep.vacations.app.guifx;

import java.util.List;
import java.util.logging.Logger;

import com.gluonhq.maps.MapView;

import goedegep.geo.WGS84Coordinates;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.util.douglaspeuckerreducer.DouglasPeuckerReducer;
import goedegep.vacations.model.Boundary;
import goedegep.vacations.model.Location;
import goedegep.vacations.model.VacationsFactory;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * This class provides a window to reduce the sizes of boundaries of a {@link Location}.
 * <p>
 * The reduces boundaries are shown in lawn green on a {@link MapView}.<br/>
 * The amount of reduction is controlled via a slider and both the original and new number of
 * points of the boundaries are shown.
 *
 */
public class ReduceBoundarySizesWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(ReduceBoundarySizesWindow.class.getName());
  private static final String WINDOW_TITLE = "Reduce location boundary sizes";
  private static final String NEWLINE = System.getProperty("line.separator");
  
  private Location location;
  private Location previewLocation;
  private ComponentFactoryFx componentFactory;
  private MapRelatedItemsLayer mapRelatedItemsLayer;
  private MapView mapView;
  private Slider slider;
  private TextField currentNumberOfPointsTextField;
  private TextField newNumberOfPointsTextField;
  
  /**
   * Constructor.
   * 
   * @param customization The GUI customization.
   * @param location the {@code Location} of which the number of points of the boundaries are to be reduced.
   */
  public ReduceBoundarySizesWindow(CustomizationFx customization, Location location, MapView mapView) {
    super(customization, WINDOW_TITLE);
    
    this.location = location;
    this.mapView = mapView;
    componentFactory = customization.getComponentFactoryFx();
    
    previewLocation = VacationsFactory.eINSTANCE.createLocation();
    
    mapRelatedItemsLayer = new MapRelatedItemsLayer(customization, this);
    mapView.addLayer(mapRelatedItemsLayer);
    
    createGUI();
    
    handleNewSliderValue(slider.getValue());
    setOnCloseRequest(event -> cleanupAndClose());
    
    show();
  }

  /**
   * Create the GUI
   * <p>
   * From top to bottom:
   * <ul>
   * <li>
   * Explaining text panel.
   * </li>
   * <li>
   * A slider to control the reduction amount
   * </li>
   * <li>
   * Labels and text fields showing the current and new number of points
   * </li>
   * <li>
   * Buttons to apply the reduction, or to cancel the operation.
   * </li>
   * </ul>
   * 
   */
  private void createGUI() {
    VBox mainPanel = componentFactory.createVBox(10.0, 10.0);
    
    VBox controlsBox = componentFactory.createVBox(10.0, 10.0);
    TextArea textArea = componentFactory.createTextArea(
        "Use the slider to select the amount of reduction; higher values mean more reduction." + NEWLINE +
        "Preview the shape on the map and if you're happy with the result, click 'Reduce'.");
    
    slider = componentFactory.createSlider(0.0, 100.0, 50.0);
    slider.setShowTickLabels(true);
    slider.setMajorTickUnit(5);
    slider.setMinorTickCount(5);
    slider.setSnapToTicks(true);
    slider.setShowTickMarks(true);
    slider.setOnMouseReleased(event -> {
      handleNewSliderValue(slider.getValue());
    });
    
    HBox reductionInfoBox =  componentFactory.createHBox();
    Label label = componentFactory.createLabel("Current number of points:");
    reductionInfoBox.getChildren().add(label);
    currentNumberOfPointsTextField = componentFactory.createTextField(null, 100, "The current number of nodes of the boundaries");
    reductionInfoBox.getChildren().add(currentNumberOfPointsTextField);
    label = componentFactory.createLabel("Reduced number of points:");
    reductionInfoBox.getChildren().add(label);
    newNumberOfPointsTextField = componentFactory.createTextField(null, 100, "The number of points of the boundaries after reduction");
    reductionInfoBox.getChildren().add(newNumberOfPointsTextField);

    controlsBox.getChildren().addAll(textArea, slider, reductionInfoBox);
    mainPanel.getChildren().add(controlsBox);
    
    mainPanel.getChildren().add(createButtonsPanel());
    
    setScene(new Scene(mainPanel, 450, 250));
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
    cancelButton.setOnAction(e -> cleanupAndClose());
    buttonsBox.getChildren().add(cancelButton);
    
    Button reduceButton = componentFactory.createButton("Reduce", "Reduce the boundaries and close this window");
    reduceButton.setOnAction(e -> reduceBoundariesSizes());
    buttonsBox.getChildren().add(reduceButton);
        
    return  buttonsBox;
  }
  
  /**
   * Handle a new slider value.
   * <p>
   * The new boundaries are calculated and shown on the map.
   * The current and new number of points is calculated and shown in this window.
   * 
   * @param sliderValue the new slider value.
   */
  private void handleNewSliderValue(double sliderValue) {
    LOGGER.severe("=> " + sliderValue);
    mapRelatedItemsLayer.clear();
    
    previewLocation.getBoundaries().clear();
    
    int currentNumberOfPoints = 0;
    int newNumberOfPoints = 0;
    for (Boundary boundary: location.getBoundaries()) {
      List<WGS84Coordinates> currentBoundary = boundary.getPoints();
      currentNumberOfPoints += currentBoundary.size();
      List<WGS84Coordinates> reducedBoundary = DouglasPeuckerReducer.reduceWithTolerance(currentBoundary, 10 * sliderValue);
      newNumberOfPoints += reducedBoundary.size();
      LOGGER.severe("Reduced from " + boundary.getPoints().size() + " to " + reducedBoundary.size());
      Boundary newBoundary = VacationsFactory.eINSTANCE.createBoundary();
      newBoundary.getPoints().addAll(reducedBoundary);
      previewLocation.getBoundaries().add(newBoundary);
    }
    currentNumberOfPointsTextField.setText(Integer.toString(currentNumberOfPoints));
    newNumberOfPointsTextField.setText(Integer.toString(newNumberOfPoints));
        
    mapRelatedItemsLayer.addLocation(previewLocation, null, Color.LAWNGREEN);
  }
  
  /**
   * Perform the actual reduction.
   * <p>
   * The (reduced) boundaries, which were already calculated by handleNewSliderValue(), are set on the {@code Location}.<br/>
   * cleanupAndClose() is called to clean-up and close the window.
   */
  private void reduceBoundariesSizes() {
    // replace boundaries
    location.getBoundaries().clear();
    location.getBoundaries().addAll(previewLocation.getBoundaries());
    
    cleanupAndClose();
  }
  
  /**
   * Cleanup and close.
   * <p>
   * Remove the map layer and close this window.
   */
  private void cleanupAndClose() {
    mapView.removeLayer(mapRelatedItemsLayer);
    
    close();
  }
}
