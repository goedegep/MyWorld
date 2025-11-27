package goedegep.vacations.app.guifx;

import java.util.List;
import java.util.logging.Logger;

import com.gluonhq.maps.MapView;

import goedegep.gpx.app.GPXLayer;
import goedegep.gpx.app.GPXTreeViewCreator;
import goedegep.gpx.app.GPXWindow;
import goedegep.gpx.model.TrkType;
import goedegep.gpx.model.TrksegType;
import goedegep.gpx.model.WptType;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttreeview.EObjectTreeItem;
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
  private static final Logger LOGGER = Logger.getLogger(BoundariesPointsReductionWindow.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  
  private Slider slider;
  private TextField originalNumberOfPointsTextField;
  private TextField reducedNumberOfPointsTextField;
  
  private MapView mapView;
  private MapRelatedItemsLayer mapRelatedItemsLayer;


  public BoundariesPointsReductionWindow(CustomizationFx customization, EObjectTreeItem treeItem) {
    super(customization, "Boundaries Points Reduction");
    
    LOGGER.severe("=>");
    
    Object value = treeItem.getValue();
    
    createGUI();
    
    show();
  }
  
  /**
   * Create the GUI.
   * <p>
   * Main panel is an HBox; left is the control panel, right is the map panel.
   */
  private void createGUI() {
    HBox mainPanel = componentFactory.createHBox();
    
    // Splitpane: left is control panel, right is map
    SplitPane centerPane = new SplitPane();
    centerPane.setOrientation(Orientation.HORIZONTAL);
    centerPane.setDividerPositions(0.3);
    
    // Control panel
    centerPane.getItems().add(createControlPanel());
    
    // MapView
    centerPane.getItems().add(createMapView());
    
    mainPanel.getChildren().add(centerPane);
    
    setScene(new Scene(mainPanel, 1700, 900));
  }
  
  /**
   * Create the control panel on the left side of the window.
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
    cancelButton.setOnAction(e -> closeWithoutMakingChanges());
    buttonsBox.getChildren().add(cancelButton);
    
    Button reduceButton = componentFactory.createButton("Reduce", "Reduce the boundaries and close this window");
    reduceButton.setOnAction(e -> closeWindow());  // as the changes are already in the original GPX track, nothing has to be done here.
    buttonsBox.getChildren().add(reduceButton);
        
    return  buttonsBox;
  }
  
  private Node createMapView() {
    mapView = new MapView();
    mapRelatedItemsLayer = new MapRelatedItemsLayer(customization, this);
    mapView.addLayer(mapRelatedItemsLayer);
    
    return mapView;
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
//    
//    switch (gpxPartToReduce) {
//    case NULL:
//      // no action
//      break;
//      
//    case GPX:
//      for (TrkType aTrack: gpx.getTrk()) {
//        for (TrksegType aSegment: aTrack.getTrkseg()) {
//          List<WptType> reducedWaypoints = DouglasPeuckerReducer.reduceWithTolerance(originalWaypointsPerSegment.get(aSegment), 2 * sliderValue, GPXWindow::coordinateExtractor);
//          aSegment.getTrkpt().clear();
//          aSegment.getTrkpt().addAll(reducedWaypoints);
//        }
//      }
//      break;
//      
//    case TRACK:
//      for (TrksegType aSegment: track.getTrkseg()) {
//        List<WptType> reducedWaypoints = DouglasPeuckerReducer.reduceWithTolerance(originalWaypointsPerSegment.get(aSegment), 2 * sliderValue, GPXWindow::coordinateExtractor);
//        aSegment.getTrkpt().clear();
//        aSegment.getTrkpt().addAll(reducedWaypoints);
//      }
//      break;
//      
//    case SEGMENT:
//      List<WptType> reducedWaypoints = DouglasPeuckerReducer.reduceWithTolerance(originalWaypointsPerSegment.get(segment), 2 * sliderValue, GPXWindow::coordinateExtractor);
//      segment.getTrkpt().clear();
//      segment.getTrkpt().addAll(reducedWaypoints);
//      break;
//      
//    }
//        
//    updateGUI();    
//    
  }


  /**
   * Close the window without making changes.
   * <p>
   * As the changes are currently in the GPX track, the original lists of track points have to be restored.
   */
  private void closeWithoutMakingChanges() {
//    // restore original segment points
//    for (TrkType track: gpx.getTrk()) {
//      for (TrksegType segment: track.getTrkseg()) {
//        segment.getTrkpt().clear();
//        segment.getTrkpt().addAll(originalWaypointsPerSegment.get(segment));
//      }
//    }

    closeWindow();
  }

  /**
   * Close the window
   */
  private void closeWindow() {


    close();
  }
}
