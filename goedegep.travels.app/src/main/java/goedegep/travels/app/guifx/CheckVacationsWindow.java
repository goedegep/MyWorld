package goedegep.travels.app.guifx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.editor.controls.EditorControlInteger;
import goedegep.jfx.editor.controls.EditorControlInteger.IntegerBuilder;
import goedegep.jfx.eobjecttreeview.EObjectTreeItem;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.travels.app.logic.VacationsChecker;
import goedegep.travels.app.logic.VacationsUtils;
import goedegep.travels.model.Location;
import goedegep.travels.model.Travel;
import goedegep.travels.model.Vacation;
import goedegep.travels.model.Vacations;
import goedegep.types.model.FileReference;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * TODO: Check vacation folder on naming convention
 * TODO: Check that all references, except for pictures, are to the related vacation folder
 * TODO: Check that if there is a photo folder for a vacation, that the corresponding attribute is set.
 * TODO: Check that all picture references are to the pictures folder of the vacation. And not in an ignore directory.
 */
/**
 * This class presents a window to check the {@code Vacations} structure.
 * 
 */
public class CheckVacationsWindow extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(CheckVacationsWindow.class.getName());
  protected static final String NEWLINE = System.getProperty("line.separator");
  private static final String WINDOW_TITLE = "Check Vacations";
  
  private static final int MIN_TEXT_AREA_HEIGHT = 60;
  
  /**
   * The {@code Vacations} structure to be checked (if the check is not on a specific vacation.
   */
  private Vacations vacations;
  
  /**
   * The tree view showing the vacations structure.
   * <p>
   * This is used to get the selected travel, and to select 'error' objects in the tree view.
   */
  private EObjectTreeView treeView;
  
  /**
   * The panel to which each check adds its own result panel.
   */
  private VBox checkResultsPanel;
  
  private EditorControlInteger integerControlBoundarySizeThreshold;
  
  /**
   * Constructor for creating the window.
   * 
   * @param customization The GUI customization.
   * @param vacations the {@code Vacations} structure.
   */
  public CheckVacationsWindow(CustomizationFx customization, Vacations vacations, EObjectTreeView treeView) {
    super(customization, WINDOW_TITLE);
    
    this.vacations = vacations;
    this.treeView = treeView;
    
    createGUI();
    
    show();
  }

  /**
   * Create the GUI.
   * <p>
   * At the top there is the controls to run the checks.
   * Below that that is a panel for each check.
   * 
   */
  private void createGUI() {
    VBox mainPanel = componentFactory.createVBox(10.0, 10.0);
    
    HBox buttonsBox = componentFactory.createHBox(20.0, 6.0);
    
    HBox allOrSelectedBox = componentFactory.createHBox(10.0, 0.0);
    ToggleGroup toggleGroup = new ToggleGroup();
    RadioButton checkAllTravelsButton = new RadioButton("Check all Travels");
    checkAllTravelsButton.setToggleGroup(toggleGroup);
    RadioButton checkSelectedTravelButton = new RadioButton("Check selected Travel");
    checkSelectedTravelButton.setToggleGroup(toggleGroup);
    checkSelectedTravelButton.setSelected(true);
    allOrSelectedBox.getChildren().addAll(checkAllTravelsButton, checkSelectedTravelButton);
    
    HBox boundarySizeThresholdBox = componentFactory.createHBox(10.0, 0.0);
    integerControlBoundarySizeThreshold = new IntegerBuilder("boundarySizeThreshold")
        .setLabelBaseText("Boundary size threshold")
        .setWidth(40)
        .setToolTipText("The threshold for the number of points in the Location boundaries above which a warning is issued")
        .setCustomization(customization)
        .build();
    integerControlBoundarySizeThreshold.setObject(400);
    boundarySizeThresholdBox.getChildren().addAll(integerControlBoundarySizeThreshold.getLabel(), integerControlBoundarySizeThreshold.getControl());
    
    Button runButton = componentFactory.createButton("Run checks", "Execute the checks");
    runButton.setOnAction((_) -> runChecks(toggleGroup.getSelectedToggle() == checkAllTravelsButton));
    buttonsBox.getChildren().addAll(allOrSelectedBox, boundarySizeThresholdBox, runButton);
    
    mainPanel.getChildren().add(buttonsBox);
    
    checkResultsPanel = componentFactory.createVBox(8.0);
    ScrollPane scrollPane = new ScrollPane(checkResultsPanel);
    mainPanel.getChildren().add(scrollPane);
    
    setScene(new Scene(mainPanel, 1700, 900));
  }
  
  
  /**
   * Create the panel to report that no checks are performed, because there is no travel selected.
   * 
   * @return the panel to report that no travel is selected.
   */
  private Node createNoTravelSelectedPanel() {
    VBox vBox = createErrorPanel("Nothing checked because there is no travel selected in the travels tree view", "To check a single travel, the travel has to be selected in the Travels tree view");
    
    return vBox;
  }
  
  /**
   * Create the panel to report that no suspicious files check is performed, because only a single travel is checked.
   * 
   * @return the panel to report that no suspicious files check is performed.
   */
  private Node createNoSuspiciousFilesCheckPanel() {
    VBox vBox = createErrorPanel("The suspicious files check is not performed, because the check is done for a single travel", "To check for suspicious files, please run the check for all travels");
    
    return vBox;
  }
  
  /**
   * Create the panel to report that no suspicious files check is performed, because only a single travel is checked.
   * 
   * @return the panel to report that no suspicious files check is performed.
   */
  private Node createNoVacationFoldersNotReferredToCheckPanel() {
    VBox vBox = createErrorPanel("The check on vacation folders not referred to is not performed, because the check is done for a single travel",
        "To check vacation folders not referred to, please run the check for all travels");
    
    return vBox;
  }
  
  /**
   * Run the checks.
   * 
   * @param checkAllTravels If true, all travels are checked, else only the selected travel is checked.
   */
  private void runChecks(boolean checkAllTravels) {
    checkResultsPanel.getChildren().clear();
    
    // If the selected travel is to be checked, but there is no travel selected, report this and return.
    Vacation vacation = null;
    if (!checkAllTravels) {
      EObjectTreeItem treeItem = treeView.getSelectedObject();
      if (treeItem == null  ||  ((vacation = VacationsUtils.getVacationForObject(treeItem.getValue())) == null)) {
        checkResultsPanel.getChildren().add(createNoTravelSelectedPanel());
        return;
      }
    }
    
    if (vacation != null) {
      checkResultsPanel.getChildren().add(createNoSuspiciousFilesCheckPanel());
      checkResultsPanel.getChildren().add(createNoVacationFoldersNotReferredToCheckPanel());
      runCheckThatAllReferencesAreSet(vacation);
      runCheckThatAllReferencesExist(vacation);
      runCheckThatAllFilesInVacationFolderAreReferredTo(vacation);
      runCheckOnBoundarySizes(vacation);
      runPicturesFolderAccordingToConventionCheck(vacation);
      runPicturesSetIfTravelHasPicturesCheck(vacation);
      runCheckThatAllPhotosAreReferredTo(vacation);
    } else {
      runSuspiciousFilesCheck();
      runVacationFoldersNotReferredToCheck();
      runCheckThatAllReferencesAreSet();
      runCheckThatAllReferencesExist();
      runCheckThatAllFilesInVacationFoldersAreReferredTo();
      runCheckOnBoundarySizes();
      runPicturesFolderAccordingToConventionCheck();
      runPicturesSetIfTravelHasPicturesCheck();
      runCheckThatAllPhotosAreReferredTo();
    }
  }

  /**
   * Check on suspicious files in the vacations folder.
   */
  private void runSuspiciousFilesCheck() {
    VBox checkResultPanel = createCheckResultPanel();

    List<String> suspiciousFiles = VacationsChecker.findSupiciousTopLevelFiles();
    
    Label label = null;
    if (suspiciousFiles.isEmpty()) {
      // Show message that there are no suspicious files
      label = createTitleLabel(true, "There are no suspicious files found", null);
      checkResultPanel.getChildren().add(label);
    } else {
      // Show message that there are suspicious files
      label = createTitleLabel(false, "There are suspicious files found", null);
      checkResultPanel.getChildren().add(label);
      
      GridPane gridPane = componentFactory.createGridPane(6.0, 6.0);
      int row = 0;

      for (String suspiciousFile: suspiciousFiles) {
        TextArea textArea = componentFactory.createTextArea(suspiciousFile);
        textArea.setMaxHeight(16);  // Low value, actual height determined by the button
        Button button = componentFactory.createButton("Show file", "Show the file in an explorer window");
        button.setOnAction(_ -> {
          try {
            String[] command = {"explorer", "/select,", suspiciousFile};
            Runtime.getRuntime().exec(command);
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
        gridPane.addRow(row++, textArea, button);
      }

      checkResultPanel.getChildren().add(gridPane);
    }

    checkResultsPanel.getChildren().add(checkResultPanel);    
  }
  
  /**
   * Check on vacation folders which aren't referred to.
   */
  private void runVacationFoldersNotReferredToCheck() {
    VBox checkResultPanel = createCheckResultPanel();

    List<Path> vacationFoldersNotReferredTo = VacationsChecker.checkThatAllVacationFoldersAreReferredTo(vacations);
    
    Label label = null;
    if (vacationFoldersNotReferredTo.isEmpty()) {
      // Show message that there are no folders not referred to
      label = createTitleLabel(true, "There are no vacation folders which aren't referred to", null);
      checkResultPanel.getChildren().add(label);
    } else {
      // Show message that there are vacation folders not referred to
      label = createTitleLabel(false, "There are vacation folders which aren't referred to", null);
      checkResultPanel.getChildren().add(label);
      
      TextArea textArea = componentFactory.createTextArea();
      textArea.setMinHeight(MIN_TEXT_AREA_HEIGHT);
      StringBuilder buf = new StringBuilder();
      boolean first = true;
      for (Path vacationFolderNotReferredTo: vacationFoldersNotReferredTo) {
        if (first) {
          first = false;
        } else {
          buf.append(NEWLINE);
        }
        buf.append(vacationFolderNotReferredTo.toString());
      }
      textArea.setText(buf.toString());
      checkResultPanel.getChildren().add(textArea);
    }

    checkResultsPanel.getChildren().add(checkResultPanel);    
  }

  /**
   * Check that all references in a specific travel are set.
   * 
   * @param travel All references in this {@code Travel} are checked. Value may not be null.
   */
  private void runCheckThatAllReferencesAreSet(Travel travel) {

    VBox checkResultPanel = createCheckResultPanel();

    // Run the check on the travel
    List<FileReference> referencesNotSet = VacationsChecker.checkThatAllReferencesAreSet(travel);

    // If there are errors
    if (!referencesNotSet.isEmpty()) {
      // Show message that there are errors in the references.
      Label label = createTitleLabel(false, "There are file references which don't have the file attribute set", null);
      checkResultPanel.getChildren().add(label);

      // Report the list of errors. For each error, show the title if available and the file name referred to. Show the path in travel and button to go to in treeview
      GridPane gridPane = componentFactory.createGridPane(6.0, 6.0);
      int row = 0;

      for (FileReference fileReference: referencesNotSet) {
        TextArea textArea = componentFactory.createTextArea();
        textArea.setMinHeight(MIN_TEXT_AREA_HEIGHT);
        StringBuilder buf = new StringBuilder();
        buf.append("Path in travel: ").append(VacationsUtils.getTextPathToEObject(fileReference, travel)).append(NEWLINE)
        .append("File: ").append(fileReference.getFile()).append(", title: ").append(fileReference.getTitle());
        textArea.setText(buf.toString());
        Button button = componentFactory.createButton("Select in tree view", "Select the file reference in the tree view");
        button.setOnAction(_ -> treeView.selectObjectForObject(fileReference));
        gridPane.addRow(row++, textArea, button);
      }

      checkResultPanel.getChildren().add(gridPane);
    } else {
      // Show message that all references are OK
      Label label = createTitleLabel(true, "All file references are set", null);
      checkResultPanel.getChildren().add(label);
    }

    checkResultsPanel.getChildren().add(checkResultPanel);
  }

  /**
   * Check that all references in all travels are set.
   */
  private void runCheckThatAllReferencesAreSet() {

    VBox checkResultPanel = createCheckResultPanel();

    // Report the errors per Travel (grouped per TravelCategory). Show Travel Id, followed by the errors in that travel.
    boolean errorsFound = false;
    GridPane gridPane = null;
    int row = 0;

    for (Travel travel: vacations.getTravels()) {
      // Run the check on the travel
      List<FileReference> referencesNotSet = VacationsChecker.checkThatAllReferencesAreSet(travel);

      // If there are errors
      if (!referencesNotSet.isEmpty()) {
        if (!errorsFound) {
          // Show message that there are errors in the references.
          Label label = createTitleLabel(false, "There are file references which don't have the file attribute set", null);
          checkResultPanel.getChildren().add(label);

          gridPane = componentFactory.createGridPane(6.0, 6.0);

          errorsFound = true;
        }

        Label travelIdLabel = componentFactory.createLabel(travel.getId());
        gridPane.add(travelIdLabel, 0, row++, 2, 1);

        // Report the list of errors. For each error, show the title if available and the file name referred to. Show the path in travel and button to go to in treeview
        for (FileReference fileReference: referencesNotSet) {
          TextArea textArea = componentFactory.createTextArea();
          textArea.setMinHeight(MIN_TEXT_AREA_HEIGHT);
          StringBuilder buf = new StringBuilder();
          buf.append("Path in travel: ").append(VacationsUtils.getTextPathToEObject(fileReference, travel)).append(NEWLINE)
          .append("File: ").append(fileReference.getFile()).append(", title: ").append(fileReference.getTitle());
          textArea.setText(buf.toString());
          Button button = componentFactory.createButton("Select in tree view", "Select the file reference in the tree view");
          button.setOnAction(_ -> treeView.selectObjectForObject(fileReference));
          gridPane.addRow(row++, textArea, button);
        }

        checkResultPanel.getChildren().add(gridPane);
      }
    }

    if (!errorsFound) {
      // Show message that all references are OK
      Label label = createTitleLabel(true, "All file references are set", null);
      checkResultPanel.getChildren().add(label);
    }

    checkResultsPanel.getChildren().add(checkResultPanel);
  }

  /**
   * Check that all references in a specific travel refer to an existing file.
   * 
   * @param travel All references in this {@code Travel} are checked. Value may not be null.
   */
  private void runCheckThatAllReferencesExist(Travel travel) {

    VBox checkResultPanel = createCheckResultPanel();

    // Run the check on the travel
    List<FileReference> referencesNotFound = VacationsChecker.checkThatAllReferencesExist(travel);

    // If there are errors
    if (!referencesNotFound.isEmpty()) {
      // Show message that there are errors in the references.
      Label label = createTitleLabel(false, "There are file references which refer to a file that doesn't exist", null);
      checkResultPanel.getChildren().add(label);

      // Report the list of errors. For each error, show the title if available and the file name referred to. Show the path in travel and button to go to in treeview
      GridPane gridPane = componentFactory.createGridPane(6.0, 6.0);
      int row = 0;

      for (FileReference fileReference: referencesNotFound) {
        TextArea textArea = componentFactory.createTextArea();
        textArea.setMinHeight(MIN_TEXT_AREA_HEIGHT);
        StringBuilder buf = new StringBuilder();
        buf.append("Path in travel: ").append(VacationsUtils.getTextPathToEObject(fileReference, travel)).append(NEWLINE)
        .append("File: ").append(fileReference.getFile()).append(", title: ").append(fileReference.getTitle());
        textArea.setText(buf.toString());
        Button button = componentFactory.createButton("Select in tree view", "Select the file reference in the tree view");
        button.setOnAction(_ -> treeView.selectObjectForObject(fileReference));
        gridPane.addRow(row++, textArea, button);
      }

      checkResultPanel.getChildren().add(gridPane);
    } else {
      // Show message that all references are OK
      Label label = createTitleLabel(true, "All file references refer to existing files", null);
      checkResultPanel.getChildren().add(label);
    }

    checkResultsPanel.getChildren().add(checkResultPanel);
  }

  /**
   * Check that all references in all travels refer to an existing file.
   */
  private void runCheckThatAllReferencesExist() {

    VBox checkResultPanel = createCheckResultPanel();

    // Report the errors per Travel (grouped per TravelCategory). Show Travel Id, followed by the errors in that travel.
    boolean errorsFound = false;
    GridPane gridPane = null;
    int row = 0;

    for (Travel aTravel: vacations.getTravels()) {
      // Run the check on the travel
      List<FileReference> referencesNotFound = VacationsChecker.checkThatAllReferencesExist(aTravel);

      // If there are errors
      if (!referencesNotFound.isEmpty()) {
        if (!errorsFound) {
          // Show message that there are errors in the references.
          Label label = createTitleLabel(false, "There are file references which refer to a file that doesn't exist", null);
          checkResultPanel.getChildren().add(label);

          gridPane = componentFactory.createGridPane(6.0, 6.0);

          errorsFound = true;
        }

        Label travelIdLabel = componentFactory.createLabel(aTravel.getId());
        gridPane.add(travelIdLabel, 0, row++, 2, 1);

        // Report the list of errors. For each error, show the title if available and the file name referred to. Show the path in travel and button to go to in treeview
        for (FileReference fileReference: referencesNotFound) {
          TextArea textArea = componentFactory.createTextArea();
          textArea.setMinHeight(MIN_TEXT_AREA_HEIGHT);
          StringBuilder buf = new StringBuilder();
          buf.append("Path in travel: ").append(VacationsUtils.getTextPathToEObject(fileReference, aTravel)).append(NEWLINE)
          .append("File: ").append(fileReference.getFile()).append(", title: ").append(fileReference.getTitle());
          textArea.setText(buf.toString());
          Button button = componentFactory.createButton("Select in tree view", "Select the file reference in the tree view");
          button.setOnAction(_ -> treeView.selectObjectForObject(fileReference));
          gridPane.addRow(row++, textArea, button);
        }

        checkResultPanel.getChildren().add(gridPane);
      }
    }

    if (!errorsFound) {
      // Show message that all references are OK
      Label label = createTitleLabel(true, "All file references refer to existing files", null);
      checkResultPanel.getChildren().add(label);
    }

    checkResultsPanel.getChildren().add(checkResultPanel);
  }

  /**
   * Check that all files a travel folder are referred to.
   * <p>
   * @param travel All files in the folder related to this {@code Travel} are checked. Value may not be null.
   */
  private void runCheckThatAllFilesInVacationFolderAreReferredTo(Travel travel) {
    
    // Get the related travel folder. If this doesn't exist, report this. The check cannot be performed.
    String travelFolder = VacationsUtils.getTravelFilesFolder(travel);
    Path travelFolderPath = Path.of(travelFolder);
    if (!Files.exists(travelFolderPath)) {
      Label label = createTitleLabel(false, "The folder with travel related files '" + travelFolder + "' doesn't exist. So the check couldn't be performed.", null);
      checkResultsPanel.getChildren().add(label);
      return;
    }

    //  Run the check on the travel
    List<Path> filesNotReferredTo;
    try {
      filesNotReferredTo = VacationsChecker.checkThatAllFilesInTravelFolderAreReferredTo(travel, travelFolderPath);
    } catch (IOException e) {
      Label label = createTitleLabel(false, "The folder with travel related files '" + travelFolder + "' couldn't be checked. System error" + e.getMessage(), null);
      checkResultsPanel.getChildren().add(label);
      return;
    }

    VBox checkResultPanel = createCheckResultPanel();
    
    // If there are errors
    if (!filesNotReferredTo.isEmpty()) {
      // Show message that there are errors in the references.
      Label label = createTitleLabel(false, "There are files in the travel folder \'" + travelFolder + "\' which aren't referred to", null);
      checkResultPanel.getChildren().add(label);
      

      // Report the list of errors. For each error, show the title if available and the file name referred to. Show the path in travel and button to go to in treeview
      GridPane gridPane = componentFactory.createGridPane(6.0, 6.0);
      int row = 0;

      for (Path fileNotReferredTo: filesNotReferredTo) {
        TextArea textArea = componentFactory.createTextArea(fileNotReferredTo.toString());
        textArea.setMaxHeight(16);  // Low value, actual height determined by the button
        textArea.setMinWidth(1000);
        Button button = componentFactory.createButton("Show file", "Show the file in an explorer window");
        button.setOnAction(_ -> {
          try {
            String[] command = {"explorer", "/select,", fileNotReferredTo.toString()};
            Runtime.getRuntime().exec(command);
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
        gridPane.addRow(row++, textArea, button);
      }
      
      checkResultPanel.getChildren().add(gridPane);
    } else {
      // Show message that all files are referred to
      Label label = createTitleLabel(true, "All files in the folder \'" + travelFolder + "\' are referred to", null);
      checkResultPanel.getChildren().add(label);
    }

    checkResultsPanel.getChildren().add(checkResultPanel);
  }

  /**
   * Check that all files in the travel folders are referred to.
   */
  private void runCheckThatAllFilesInVacationFoldersAreReferredTo() {

    VBox checkResultPanel = createCheckResultPanel();

    // Report the errors per Travel (grouped per TravelCategory). Show Travel Id, followed by the errors in that travel.
    boolean errorsFound = false;
    GridPane gridPane = null;
    ScrollPane scrollPane = null;
    int row = 0;

    for (Travel travel: vacations.getTravels()) {
      
      // Get the related travel folder. If this doesn't exist, the check cannot be performed.
      String travelFolder = VacationsUtils.getTravelFilesFolder(travel);
      Path travelFolderPath = Path.of(travelFolder);
      if (!Files.exists(travelFolderPath)) {
        // Skip check for this travel. If the travel has references, these either have no correct file reference (reported by another check), or the folder must exist.
        continue;
      }

      //  Run the check on the travel
      List<Path> filesNotReferredTo = null;
      IOException ioException = null;
      try {
        filesNotReferredTo = VacationsChecker.checkThatAllFilesInTravelFolderAreReferredTo(travel, travelFolderPath);
      } catch (IOException e) {
        ioException = e;
      }

      // If there are errors
      if (ioException != null  ||  !filesNotReferredTo.isEmpty()) {
        if (!errorsFound) {
          // Show message that there are errors in the references.
          Label label = createTitleLabel(false, "There are files which aren't referred to", null);  // actually it may also be an IOException
          checkResultPanel.getChildren().add(label);

          gridPane = componentFactory.createGridPane(6.0, 6.0);
          scrollPane = new ScrollPane(gridPane);
          scrollPane.setMaxHeight(200);
          errorsFound = true;
        }

        Label travelIdLabel = componentFactory.createLabel(travel.getId());
        gridPane.add(travelIdLabel, 0, row++, 2, 1);
        
        if (ioException != null) {
          TextArea textArea = componentFactory.createTextArea("The folder with travel related files '" + travelFolder + "' couldn't be checked. System error" + ioException.getMessage());
          textArea.setMinHeight(24);
          gridPane.addRow(row++, textArea);
        } else {
          // Report the list of errors. For each error, show the title if available and the file name referred to. Show the path in travel and button to go to in treeview
          for (Path fileNotReferredTo: filesNotReferredTo) {
            TextArea textArea = componentFactory.createTextArea(fileNotReferredTo.toString());
            textArea.setMaxHeight(16);  // Low value, actual height determined by the button
            textArea.setMinWidth(1000);
            Button button = componentFactory.createButton("Show file", "Show the file in an explorer window");
            button.setOnAction(_ -> {
              try {
                String[] command = {"explorer", "/select,", fileNotReferredTo.toString()};
                Runtime.getRuntime().exec(command);
              } catch (IOException e) {
                e.printStackTrace();
              }
            });
            gridPane.addRow(row++, textArea, button);
          }
        }

      }
    }
    checkResultPanel.getChildren().add(scrollPane);

    if (!errorsFound) {
      // Show message that files are referred to
      Label label = createTitleLabel(true, "All files in the travel folders are referred to", null);
      checkResultPanel.getChildren().add(label);
    }

    checkResultsPanel.getChildren().add(checkResultPanel);
  }

  /**
   * Report on {@code Location}s in a travel which have a large boundary size.
   * <p>
   * @param travel All {@code Location}s in this {@code Travel} are checked. Value may not be null.
   */
  private void runCheckOnBoundarySizes(Travel travel) {
    
    //  Run the check on the travel
    List<Location> locationsWithLargeBoundaries = VacationsChecker.getLocationsWithLargeBoundaries(travel, integerControlBoundarySizeThreshold.getValue());
    
    VBox checkResultPanel = createCheckResultPanel();
    
    // If there are errors
    if (!locationsWithLargeBoundaries.isEmpty()) {
      // Show message that there are boundaries with a large number of points.
      Label label = createTitleLabel(false, "There are Locations with a large number of points", null);
      checkResultPanel.getChildren().add(label);
      

      // Report the list of locations with large boundaries. For each location, show the name and the number of points. Show the path in travel and button to go to in treeview
      GridPane gridPane = componentFactory.createGridPane(6.0, 6.0);
      int row = 0;

      for (Location locationWithLargeBoundaries: locationsWithLargeBoundaries) {
        StringBuilder buf = new StringBuilder();
        buf.append("The location \'")
        .append(locationWithLargeBoundaries.getName())
        .append(" has boundaries with ")
        .append(VacationsUtils.getNumberOfPointsInLocationBoundary(locationWithLargeBoundaries))
        .append(" points.");
        TextArea textArea = componentFactory.createTextArea(buf.toString());
        textArea.setMaxHeight(16);  // Low value, actual height determined by the button
        Button button = componentFactory.createButton("Select in tree view", "Select the file reference in the tree view");
        button.setOnAction(_ -> treeView.selectObjectForObject(locationWithLargeBoundaries));
        gridPane.addRow(row++, textArea, button);
      }
      
      checkResultPanel.getChildren().add(gridPane);
    } else {
      // Show message that all files are referred to
      Label label = createTitleLabel(true, "There are no locations with large boundaries", null);
      checkResultPanel.getChildren().add(label);
    }

    checkResultsPanel.getChildren().add(checkResultPanel);
  }
  
  /**
   * Report on {@code Location}s in all travel which have a large boundary size.
   */
  private void runCheckOnBoundarySizes() {

    VBox checkResultPanel = createCheckResultPanel();

    // Report the large sizes per Travel (grouped per TravelCategory). Show Travel Id, followed by the errors in that travel.
    boolean errorsFound = false;
    GridPane gridPane = null;
    ScrollPane scrollPane = null;
    int row = 0;

    for (Travel travel: vacations.getTravels()) {
      
      // Get the related travel folder. If this doesn't exist, the check cannot be performed.
      String travelFolder = VacationsUtils.getTravelFilesFolder(travel);
      Path travelFolderPath = Path.of(travelFolder);
      if (!Files.exists(travelFolderPath)) {
        // Skip check for this travel.
        continue;
      }
      
      //  Run the check on the travel
      List<Location> locationsWithLargeBoundaries = VacationsChecker.getLocationsWithLargeBoundaries(travel, integerControlBoundarySizeThreshold.getValue());

      // If there are errors
      if (!locationsWithLargeBoundaries.isEmpty()) {
        if (!errorsFound) {
          // Show message that there are errors in the references.
          Label label = createTitleLabel(false, "There are Locations with a large number of points", null);
          checkResultPanel.getChildren().add(label);

          gridPane = componentFactory.createGridPane(6.0, 6.0);
          scrollPane = new ScrollPane(gridPane);
          scrollPane.setMaxHeight(200);
          errorsFound = true;
        }

        Label travelIdLabel = componentFactory.createLabel(travel.getId());
        gridPane.add(travelIdLabel, 0, row++, 2, 1);

        // Report the list of errors. For each error, show the title if available and the file name referred to. Show the path in travel and button to go to in treeview
        for (Location locationWithLargeBoundaries: locationsWithLargeBoundaries) {
          StringBuilder buf = new StringBuilder();
          buf.append("The location \'")
          .append(locationWithLargeBoundaries.getName())
          .append(" has boundaries with ")
          .append(VacationsUtils.getNumberOfPointsInLocationBoundary(locationWithLargeBoundaries))
          .append(" points.");
          TextArea textArea = componentFactory.createTextArea(buf.toString());
          textArea.setMaxHeight(16);  // Low value, actual height determined by the button
          Button button = componentFactory.createButton("Select in tree view", "Select the file reference in the tree view");
          button.setOnAction(_ -> treeView.selectObjectForObject(locationWithLargeBoundaries));
          gridPane.addRow(row++, textArea, button);
        }

      }
    }
    checkResultPanel.getChildren().add(scrollPane);

    if (!errorsFound) {
      // Show message that files are referred to
      Label label = createTitleLabel(true, "There are no locations with large boundaries", null);
      checkResultPanel.getChildren().add(label);
    }

    checkResultsPanel.getChildren().add(checkResultPanel);
  }
  
  /**
   * Check that the pictures attribute, if set, refers to a folder with the correct naming convention.
   * <p>
   * @param travel All {@code Location}s in this {@code Travel} are checked. Value may not be null.
   */
  private void runPicturesFolderAccordingToConventionCheck(Travel travel) {
    if (!(travel instanceof Vacation)) {
      return;
    }
    
    Vacation vacation = (Vacation) travel;
    
    //  Run the check on the travel
    boolean picturesFolderAccordingToConvention = VacationsChecker.isPicturesFolderAccordingToConvention(vacation);
    
    VBox checkResultPanel = createCheckResultPanel();
    
    // If there is an error
    if (!picturesFolderAccordingToConvention) {
      // Show message that name is not according to convention.
      Label label = createTitleLabel(false, "The name of the pictures folder isn't according to the naming convention", null);
      checkResultPanel.getChildren().add(label);
      

      // Report the 'wrong' name.
      GridPane gridPane = componentFactory.createGridPane(6.0, 6.0);
      int row = 0;

      StringBuilder buf = new StringBuilder();
      buf.append("Pictures folder name:  \'")
      .append(vacation.getPictures())
      .append("\', according to the convention it should be: \'")
      .append(VacationsUtils.getVacationPhotosFolderPathByConvention(vacation))
      .append("\'.");
      TextArea textArea = componentFactory.createTextArea(buf.toString());
      textArea.setMaxHeight(16);  // Low value, actual height determined by the button
      Button button = componentFactory.createButton("Select in tree view", "Select the file reference in the tree view");
      button.setOnAction(_ -> treeView.selectObjectForObject(vacation));
      gridPane.addRow(row++, textArea, button);
      
      checkResultPanel.getChildren().add(gridPane);
    } else {
      // Show message that all files are referred to
      Label label = createTitleLabel(true, "The name of the pictures folder, if set, is according to the naming convention", null);
      checkResultPanel.getChildren().add(label);
    }

    checkResultsPanel.getChildren().add(checkResultPanel);
  }
  
  /**
   * Check that the pictures attribute, if set in a vacation, refers to a folder with the correct naming convention.
   */
  private void runPicturesFolderAccordingToConventionCheck() {

    VBox checkResultPanel = createCheckResultPanel();

    // Report the 'wrong' name per Travel. Show Travel Id, followed by the wrong name in that travel.
    boolean errorsFound = false;
    GridPane gridPane = null;
    ScrollPane scrollPane = null;
    int row = 0;

    for (Travel travel: vacations.getTravels()) {
      if (!(travel instanceof Vacation)) {
        continue;
      }
      
      Vacation vacation = (Vacation) travel;
      
      //  Run the check on the travel
      boolean picturesFolderAccordingToConvention = VacationsChecker.isPicturesFolderAccordingToConvention(vacation);

      // If there are errors
      if (!picturesFolderAccordingToConvention) {
        if (!errorsFound) {
          // Show message that there is one or more wrong pictures folder name.
          Label label = createTitleLabel(false, "There are travels with a pictures folder which  isn't according to the naming convention", null);
          checkResultPanel.getChildren().add(label);

          gridPane = componentFactory.createGridPane(6.0, 6.0);
          scrollPane = new ScrollPane(gridPane);
          scrollPane.setMaxHeight(200);
          errorsFound = true;
        }

        Label travelIdLabel = componentFactory.createLabel(travel.getId());
        gridPane.add(travelIdLabel, 0, row++, 2, 1);

        // Report the wrong name and what it should be according to the convention.
        StringBuilder buf = new StringBuilder();
        buf.append("Travel \'")
        .append(travel.getId())
        .append("\': Pictures folder name:  \'")
        .append(vacation.getPictures())
        .append("\', according to the convention it should be: \'")
        .append(VacationsUtils.getVacationPhotosFolderPathByConvention(vacation))
        .append("\'.");
        TextArea textArea = componentFactory.createTextArea(buf.toString());
        textArea.setMaxHeight(16);  // Low value, actual height determined by the button
        Button button = componentFactory.createButton("Select in tree view", "Select the file reference in the tree view");
        button.setOnAction(_ -> treeView.selectObjectForObject(vacation));
        gridPane.addRow(row++, textArea, button);

      }
    }
    checkResultPanel.getChildren().add(scrollPane);

    if (!errorsFound) {
      // Show message that files are referred to
      Label label = createTitleLabel(true, "The names of the pictures folders, if set, are according to the naming convention", null);
      checkResultPanel.getChildren().add(label);
    }

    checkResultsPanel.getChildren().add(checkResultPanel);
  }
  
  /**
   * Check that if a travel has pictures, the Pictures attribute is set.
   * <p>
   * @param travel The {@code Travel} to check. Value may not be null.
   */
  private void runPicturesSetIfTravelHasPicturesCheck(Travel travel) {
    if (!(travel instanceof Vacation)) {
      return;
    }
    
    Vacation vacation = (Vacation) travel;
    
    //  Run the check on the travel
    boolean picturesSetIfTravelHasPictures = VacationsChecker.isPicturesSetIfTravelHasPictures(vacation);
    
    VBox checkResultPanel = createCheckResultPanel();
    
    // If there is an error
    if (!picturesSetIfTravelHasPictures) {
      // Show message that pictures attribute isn't set.
      Label label = createTitleLabel(false, "The pictures folder isn't set, while the travel has pictures", null);
      checkResultPanel.getChildren().add(label);
    } else {
      // Show message that all files are referred to
      Label label = createTitleLabel(true, "The travel has pictures and the pictures folder is set", null);
      checkResultPanel.getChildren().add(label);
    }

    checkResultsPanel.getChildren().add(checkResultPanel);
  }
  
  /**
   * Check that if a travel has pictures, the Pictures attribute is set.
   */
  private void runPicturesSetIfTravelHasPicturesCheck() {

    VBox checkResultPanel = createCheckResultPanel();

    boolean errorsFound = false;
    GridPane gridPane = null;
    ScrollPane scrollPane = null;
    int row = 0;

    for (Travel travel: vacations.getTravels()) {
      if (!(travel instanceof Vacation)) {
        continue;
      }
      
      Vacation vacation = (Vacation) travel;
      
      //  Run the check on the travel
      boolean picturesSetIfTravelHasPictures = VacationsChecker.isPicturesSetIfTravelHasPictures(vacation);

      // If there are errors
      if (!picturesSetIfTravelHasPictures) {
        if (!errorsFound) {
          // Show message that there is one or more travel with pictures, while the pictures folder isn't set.
          Label label = createTitleLabel(false, "There are travels with pictures that don't have the pictures folder set", null);
          checkResultPanel.getChildren().add(label);

          gridPane = componentFactory.createGridPane(6.0, 6.0);
          scrollPane = new ScrollPane(gridPane);
          scrollPane.setMaxHeight(200);
          errorsFound = true;
        }

        Label travelIdLabel = componentFactory.createLabel(vacation.getId());
        gridPane.add(travelIdLabel, 0, row++, 2, 1);

        // Report the travel.
        StringBuilder buf = new StringBuilder();
        buf.append("Travel \'")
        .append(vacation.getId())
        .append("\'");
        TextArea textArea = componentFactory.createTextArea(buf.toString());
        textArea.setMaxHeight(16);  // Low value, actual height determined by the button
        Button button = componentFactory.createButton("Select in tree view", "Select the file reference in the tree view");
        button.setOnAction(_ -> treeView.selectObjectForObject(vacation));
        gridPane.addRow(row++, textArea, button);
      }
    }
    checkResultPanel.getChildren().add(scrollPane);

    if (!errorsFound) {
      // Show message that files are referred to
      Label label = createTitleLabel(true, "The names of the pictures folders, if set, are according to the naming convention", null);
      checkResultPanel.getChildren().add(label);
    }

    checkResultsPanel.getChildren().add(checkResultPanel);
  }

  /**
   * Check that all photos related to a travel are referred to.
   * 
   * @param travel The {@code Travel} to check. Value may not be null.
   */
  private void runCheckThatAllPhotosAreReferredTo(Travel travel) {

    VBox checkResultPanel = createCheckResultPanel();

    // Run the check on the travel
    List<Path> photosNotReferredTo = VacationsChecker.getTravelPhotosNotReferredTo(travel);

    // If there are photos not referred to
    if (!photosNotReferredTo.isEmpty()) {
      // Show message that there are photos which aren't referred to.
      Label label = createTitleLabel(false, "There are photos which aren't referred to", null);
      checkResultPanel.getChildren().add(label);

      // Report the list of errors. For each error, show the title if available and the file name referred to. Show the path in travel and button to go to in treeview
      TextArea textArea = componentFactory.createTextArea();
      textArea.setMaxHeight(200);
      StringBuilder buf = new StringBuilder();
      boolean first = true;

      for (Path path: photosNotReferredTo) {
        if (first) {
          first = false;
        } else {
          buf.append(NEWLINE);
        }
        
        buf.append(path.toString());
      }
      
      textArea.setText(buf.toString());


      checkResultPanel.getChildren().add(textArea);
    } else {
      // Show message that all photos are referred to
      Label label = createTitleLabel(true, "All photos are referred to", null);
      checkResultPanel.getChildren().add(label);
    }

    checkResultsPanel.getChildren().add(checkResultPanel);
  }

  /**
   * Check that for each travel all photos related to that travel are referred to.
   */
  private void runCheckThatAllPhotosAreReferredTo() {

    VBox checkResultPanel = createCheckResultPanel();

    // Report the errors per Travel (grouped per TravelCategory). Show Travel Id, followed by the errors in that travel.
    boolean errorsFound = false;
    GridPane gridPane = null;
    int row = 0;

    for (Travel aTravel: vacations.getTravels()) {
      // Run the check on the travel
      List<Path> photosNotReferredTo = VacationsChecker.getTravelPhotosNotReferredTo(aTravel);

      // If there are errors
      if (!photosNotReferredTo.isEmpty()) {
        if (!errorsFound) {
          // Show message that there are photos which aren't referred to.
          Label label = createTitleLabel(false, "There are photos which aren't referred to", null);
          checkResultPanel.getChildren().add(label);

          gridPane = componentFactory.createGridPane(6.0, 6.0);

          errorsFound = true;
        }

        Label travelIdLabel = componentFactory.createLabel(aTravel.getId());
        gridPane.add(travelIdLabel, 0, row++, 2, 1);

        TextArea textArea = componentFactory.createTextArea();
        textArea.setMinHeight(MIN_TEXT_AREA_HEIGHT);
        StringBuilder buf = new StringBuilder();
        boolean first = true;

        // Report the list of errors. For each error, show the title if available and the file name referred to. Show the path in travel and button to go to in treeview
        for (Path path: photosNotReferredTo) {
          if (first) {
            first = false;
          } else {
            buf.append(NEWLINE);
          }
          buf.append(path.toString());
        }
        textArea.setText(buf.toString());
        gridPane.addRow(row++, textArea);
      }
    }
    checkResultPanel.getChildren().add(gridPane);

    if (!errorsFound) {
      // Show message that all references are OK
      Label label = createTitleLabel(true, "All photos are referred to", null);
      checkResultPanel.getChildren().add(label);
    }

    checkResultsPanel.getChildren().add(checkResultPanel);
  }

  
  private VBox createErrorPanel(String title, String explanation) {
    return createErrorPanel(false, title, explanation);
  }
  
  /**
   * Create an error panel.
   * <p>
   * The panel has a border.<br/>
   * 
   * @return the newly created panel.
   */
  private VBox createCheckResultPanel() {
    VBox vBox = componentFactory.createVBox();
    vBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    vBox.setPadding(new Insets(10.0));

    return vBox;
  }
  
  /**
   * Create a {@code Label} to be shown as the top line in a check result panel.
   * 
   * @param checkOK if true, the a '✓' is prepended to the title.
   * @param title the text of the label (this may not be null).
   * @param explanation an optional explanation, which will be added as a Tooltip.
   * @return the created {@code Label}.
   */
  private Label createTitleLabel(boolean checkOK, String title, String explanation) {
    Label label = componentFactory.createLabel((checkOK ? "✓ " : "") + title);
    
    if (explanation != null) {
      Tooltip tooltip = new Tooltip(explanation);
      label.setTooltip(tooltip);
    }
    
    label.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
    
    return label;
  }
  
  /**
   * Create an error panel.
   * <p>
   * The panel has a border.<br/>
   * It starts with the {@code title} in a bold text. The {@code explanation} is the tooltip of this label.
   * After the tests are run. The result is listed below this title.
   * 
   * @param title the title of the test.
   * @param explanation an explanation of the {@code title}, shown as the tooltip of the {@code title}.
   * @return
   */
  private VBox createErrorPanel(boolean checkOK, String title, String explanation) {
    VBox vBox = componentFactory.createVBox();
    vBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    vBox.setPadding(new Insets(10.0));
    if (title != null) {
      Label label = componentFactory.createLabel((checkOK ? "✓ " : "") + title);
      Tooltip tooltip = new Tooltip(explanation);
      label.setTooltip(tooltip);
      label.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
      vBox.getChildren().add(label);
    }

    return vBox;
  }
}
