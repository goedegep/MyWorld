package goedegep.gpxeditor.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;

import goedegep.gpx.model.DocumentRoot;
import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.TrkType;
import goedegep.gpx.model.TrksegType;
import goedegep.gpx.model.WptType;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.util.douglaspeuckerreducer.DouglasPeuckerReducer;
import goedegep.util.objectselector.ObjectSelectionListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * This class provides functionality to reduce the number of waypoints of the segments of a GPX track.
 *
 */
public class ReduceTrackPointsWindow extends JfxStage implements ObjectSelectionListener<Object> {
  private static final Logger LOGGER = Logger.getLogger(ReduceTrackPointsWindow.class.getName());
  private static final String WINDOW_TITLE = "Reduce the number of waypoints";
  private static final String NEWLINE = System.getProperty("line.separator");
  
  private GpxType gpx = null;
  private TrkType track = null;
  private TrksegType segment = null;
  private GpxPartToReduce gpxPartToReduce = null;
  
  /**
   * This map holds to original waypoint lists of all segments in a GPX track.
   * When a new object is selected (see {@link #objectSelected}, which is part of a different GPX track,
   * this map is filled for the complete GPS track (so not only for e.g. a selected track segment).
   */
  private Map<TrksegType, List<WptType>> originalWaypointsPerSegment = new HashMap<>();
  private ComponentFactoryFx componentFactory;
  private Slider slider;
  private TextField originalNumberOfPointsTextField;
  private TextField originalDistanceField;
  private TextField originalTotalAscendField;
  private TextField reducedNumberOfPointsTextField;
  private TextField reducedDistanceField;
  private TextField reducedTotalAscendField;
  
  /**
   * Constructor.
   * 
   * @param customization The GUI customization.
   * @param location the {@code Location} of which the number of points of the boundaries are to be reduced.
   */
  public ReduceTrackPointsWindow(CustomizationFx customization, EObject gpxOrTrackOrSegment) {
    super(customization, WINDOW_TITLE);
    
    componentFactory = customization.getComponentFactoryFx();
        
    createGUI();
    
    setOnCloseRequest(event -> closeWindow());
    
    objectSelected(null, gpxOrTrackOrSegment);
    
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
   * Labels and text fields showing the current and new number of points, total length and total ascend.
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
    
    slider = componentFactory.createSlider(0.0, 100.0, 10.0);
    slider.setShowTickLabels(true);
    slider.setMajorTickUnit(5);
    slider.setMinorTickCount(5);
    slider.setSnapToTicks(true);
    slider.setShowTickMarks(true);
    slider.setOnMouseReleased(event -> {
      handleNewSliderValue(slider.getValue());
    });
    
    GridPane reductionInfoPane = componentFactory.createGridPane(12.0, 12.0);
    reductionInfoPane.setPadding(new Insets(12.0));
    
    Label label;
    int row = 0;
    
    // Row: value labels
    label = componentFactory.createLabel("Number of points");
    reductionInfoPane.add(label, 1, row);
    label = componentFactory.createLabel("Distance");
    reductionInfoPane.add(label, 2, row);
    label = componentFactory.createLabel("Total ascend");
    reductionInfoPane.add(label, 3, row);
    
    row++;
    
    // Row: current values
    label = componentFactory.createLabel("Current:");
    reductionInfoPane.add(label, 0, row);
    originalNumberOfPointsTextField = componentFactory.createTextField(null, 100, "The current number of points of the track(segment)");
    reductionInfoPane.add(originalNumberOfPointsTextField, 1, row);
    originalDistanceField = componentFactory.createTextField(null, 100, "The current length of the track(segment)");
    reductionInfoPane.add(originalDistanceField, 2, row);
    originalTotalAscendField = componentFactory.createTextField(null, 100, "The current total ascend of the track(segment)");
    reductionInfoPane.add(originalTotalAscendField, 3, row);
    
    row++;
    
    // Row: reduced values
    label = componentFactory.createLabel("Reduced:");
    reductionInfoPane.add(label, 0, row);
    reducedNumberOfPointsTextField = componentFactory.createTextField(null, 100, "The reduced number of points of the track(segment)");
    reductionInfoPane.add(reducedNumberOfPointsTextField, 1, row);
    reducedDistanceField = componentFactory.createTextField(null, 100, "The reduced length of the track(segment)");
    reductionInfoPane.add(reducedDistanceField, 2, row);
    reducedTotalAscendField = componentFactory.createTextField(null, 100, "The reduced total ascend of the track(segment)");
    reductionInfoPane.add(reducedTotalAscendField, 3, row);
    
    controlsBox.getChildren().addAll(textArea, slider, reductionInfoPane);
    mainPanel.getChildren().add(controlsBox);
    
    mainPanel.getChildren().add(createButtonsPanel());
    
    setScene(new Scene(mainPanel));
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
  
  /**
   * Handle a new slider value.
   * <p>
   * The new boundaries are calculated and shown on the map.
   * The GUI is updated by calling {@link #updateGUI}.
   * 
   * @param sliderValue the new slider value.
   */
  private void handleNewSliderValue(double sliderValue) {
    
    switch (gpxPartToReduce) {
    case NULL:
      // no action
      break;
      
    case GPX:
      for (TrkType aTrack: gpx.getTrk()) {
        for (TrksegType aSegment: aTrack.getTrkseg()) {
          List<WptType> reducedWaypoints = DouglasPeuckerReducer.reduceWithTolerance(originalWaypointsPerSegment.get(aSegment), 2 * sliderValue, GPXWindow::coordinateExtractor);
          aSegment.getTrkpt().clear();
          aSegment.getTrkpt().addAll(reducedWaypoints);
        }
      }
      break;
      
    case TRACK:
      for (TrksegType aSegment: track.getTrkseg()) {
        List<WptType> reducedWaypoints = DouglasPeuckerReducer.reduceWithTolerance(originalWaypointsPerSegment.get(aSegment), 2 * sliderValue, GPXWindow::coordinateExtractor);
        aSegment.getTrkpt().clear();
        aSegment.getTrkpt().addAll(reducedWaypoints);
      }
      break;
      
    case SEGMENT:
      List<WptType> reducedWaypoints = DouglasPeuckerReducer.reduceWithTolerance(originalWaypointsPerSegment.get(segment), 2 * sliderValue, GPXWindow::coordinateExtractor);
      segment.getTrkpt().clear();
      segment.getTrkpt().addAll(reducedWaypoints);
      break;
      
    }
        
    updateGUI();    
    
  }
    
  /**
   * Close the window without making changes.
   * <p>
   * As the changes are currently in the GPX track, the original lists of track points have to be restored.
   */
  private void closeWithoutMakingChanges() {
    // restore original segment points
    for (TrkType track: gpx.getTrk()) {
      for (TrksegType segment: track.getTrkseg()) {
        segment.getTrkpt().clear();
        segment.getTrkpt().addAll(originalWaypointsPerSegment.get(segment));
      }
    }

    closeWindow();
  }
  
  /**
   * Close the window
   */
  private void closeWindow() {
    
    
    close();
  }

  /**
   * {@inheritDoc}
   * <br/><br/>
   * In this case: Handle the fact that a new object is selected to be reduced.
   * <p>
   * New can mean a different GPX track (or any child of a different GPX track), or it can mean a different part of the current GPX track.
   * 
   * @param object the new object, which may be a DocumentRoot, a complete GPX type, a single Track, or a single Segment.
   */
  @Override
  public void objectSelected(Object source, Object object) {
    LOGGER.severe("=> " + object);
    GpxType newGpx = getGpxForObject(object);
    
    if (!Objects.equals(gpx, newGpx)) {
      originalWaypointsPerSegment.clear();
      
      if (newGpx != null) {
        fillOriginalWaypointsPerSegment(newGpx);
      }
    }
    if (gpx == null  &&  newGpx != null) ;
    gpx = null;
    track = null;
    segment = null;
    
    if (object == null) {
      gpxPartToReduce = GpxPartToReduce.NULL;
    } else if (object instanceof GpxType) {
      gpx = (GpxType) object;
      gpxPartToReduce = GpxPartToReduce.GPX;
    } else if (object instanceof TrkType) {
      track = (TrkType) object;
      gpxPartToReduce = GpxPartToReduce.TRACK;
    } else if (object instanceof TrksegType) {
      segment = (TrksegType) object;
      gpxPartToReduce = GpxPartToReduce.SEGMENT;
    } else {
      throw new IllegalArgumentException("Illegal object: " + object);
    }
    
    int numberOfPoints = 0;
    double length = 0;
    double totalAscend = 0;
    
    switch (gpxPartToReduce) {
    case NULL:
      // no action
      break;
      
    case GPX:
      for (TrkType aTrack: gpx.getTrk()) {
        for (TrksegType aSegment: aTrack.getTrkseg()) {
          numberOfPoints += aSegment.getTrkpt().size();
          length += aSegment.getLength();
          totalAscend += aSegment.getCumulativeAscent();
        }
      }
      break;
      
    case TRACK:
      for (TrksegType aSegment: track.getTrkseg()) {
        numberOfPoints += aSegment.getTrkpt().size();
        length += aSegment.getLength();
        totalAscend += aSegment.getCumulativeAscent();
      }
      break;
      
    case SEGMENT:
      numberOfPoints = segment.getTrkpt().size();
      length = segment.getLength();
      totalAscend = segment.getCumulativeAscent();
      break;
      
    }
    
    originalNumberOfPointsTextField.setText(Integer.toString(numberOfPoints));    
    originalDistanceField.setText(String.format("%1$.1f", length/1000d));    
    originalTotalAscendField.setText(String.format("%1$f", totalAscend));
    
    handleNewSliderValue(slider.getValue());
  }
  
  /**
   * Get the GpxType related to an object.
   * 
   * @param object the object for which the related GpxType is to be obtained.
   * @return the GpxType related to <code>object</code>, or null if the cannot be determined.
   */
  private GpxType getGpxForObject(Object object) {
    
    if (object == null  ||  !(object instanceof EObject)) {
      return null;
    }
    
    EObject eObject = (EObject) object;
    
    if (eObject instanceof DocumentRoot documentRoot) {
      return documentRoot.getGpx();
    }
    
    while (!(eObject instanceof GpxType)  &&  eObject != null) {
      eObject = eObject.eContainer();
    }
    
    return (GpxType) eObject;
  }
  
  /**
   * Fill <code>originalWaypointsPerSegment</code> with the original waypoint lists of all segments in a GpxType.
   * @param newGpx
   */
  private void fillOriginalWaypointsPerSegment(GpxType newGpx) {
    for (TrkType track: newGpx.getTrk()) {
      for (TrksegType segment: track.getTrkseg()) {
        originalWaypointsPerSegment.put(segment, new ArrayList<>(segment.getTrkpt()));
      }
    }
  }

  /**
   * Update the GUI for the current reduction information.
   */
  private void updateGUI() {
    int numberOfPoints = 0;
    double length = 0;
    double totalAscend = 0;
    
    switch (gpxPartToReduce) {
    case NULL:
      // no action
      break;
      
    case GPX:
      for (TrkType aTrack: gpx.getTrk()) {
        for (TrksegType aSegment: aTrack.getTrkseg()) {
          numberOfPoints += aSegment.getTrkpt().size();
          length += aSegment.getLength();
          totalAscend += aSegment.getCumulativeAscent();
        }
      }
      break;
      
    case TRACK:
      for (TrksegType aSegment: track.getTrkseg()) {
        numberOfPoints += aSegment.getTrkpt().size();
        length += aSegment.getLength();
        totalAscend += aSegment.getCumulativeAscent();
      }
      break;
      
    case SEGMENT:
      numberOfPoints = segment.getTrkpt().size();
      length = segment.getLength();
      totalAscend = segment.getCumulativeAscent();
      break;
      
    }
    
    reducedNumberOfPointsTextField.setText(Integer.toString(numberOfPoints));    
    reducedDistanceField.setText(String.format("%1$.1f", length/1000d));    
    reducedTotalAscendField.setText(String.format("%1$f", totalAscend));
  }

}

enum GpxPartToReduce {
  NULL,   // no source
  GPX,    // all segments in all tracks
  TRACK,  // all segments in a track
  SEGMENT // one segment
}
