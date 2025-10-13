package goedegep.vacations.app.guifx;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.types.model.FileReference;
import goedegep.vacations.app.logic.VacationsChecker;
import goedegep.vacations.model.Boundary;
import goedegep.vacations.model.Location;
import goedegep.vacations.model.Vacation;
import goedegep.vacations.model.VacationElement;
import goedegep.vacations.model.Vacations;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * TODO: check that all files in these folders are referenced
 * TODO: check that all main folders in Photo/Vakanties are referenced
 */
public class CheckVacationsWindow extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(CheckVacationsWindow.class.getName());
  private static final String WINDOW_TITLE = "Check Vacations";
  
  private Vacations vacations;
  private ComponentFactoryFx componentFactory;
  
  private VBox dataStructureErrorsPanel;
  private VBox suspiciousFilesPanel;
  private VBox referencesNotFoundPanel;
  private VBox foldersNotReferredToPanel;
  
  /**
   * Constructor.
   * 
   * @param customization The GUI customization.
   * @param vacations the vacations structure.
   */
  public CheckVacationsWindow(CustomizationFx customization, Vacations vacations) {
    super(customization, WINDOW_TITLE);
    
    this.vacations = vacations;
    componentFactory = customization.getComponentFactoryFx();
    
    createGUI();
    
    show();
  }
  
  private void createGUI() {
    VBox checksBox = componentFactory.createVBox(10.0, 10.0);
    
    Button runButton = componentFactory.createButton("Run checks", "Run all checks");
    runButton.setOnAction((e) -> runChecks());
    checksBox.getChildren().add(runButton);
    
    Node node = createDataStructureCheckPanel();
    checksBox.getChildren().add(node);
    
    node = createSuspiciousFilesCheckPanel();
    checksBox.getChildren().add(node);
    
    node = createCheckThatAllReferencesExistPanel();
    checksBox.getChildren().add(node);
    
    node = createCheckThatAllVacationFoldersAreReferredTo();
    checksBox.getChildren().add(node);
    
    setScene(new Scene(checksBox, 1700, 900));
  }
  
  private Node createDataStructureCheckPanel() {
    VBox vBox = createErrorPanel("Check that there are no unwanted things in the vacations data structure", "Currently no real check performed");
        
    dataStructureErrorsPanel = componentFactory.createVBox();
    vBox.getChildren().add(dataStructureErrorsPanel);
    
    return vBox;
  }
  
  private Node createSuspiciousFilesCheckPanel() {
    VBox vBox = createErrorPanel("Check for suspicious files in the top level vacations folder", "Only certain files shall reside in this folder.");
        
    suspiciousFilesPanel = componentFactory.createVBox();
    vBox.getChildren().add(suspiciousFilesPanel);
    
    return vBox;
  }
  
  private Node createCheckThatAllReferencesExistPanel() {
    VBox vBox = createErrorPanel("Check that all references exist", "All files referred to from the Vacations shall exist");
        
    referencesNotFoundPanel = componentFactory.createVBox();
    vBox.getChildren().add(referencesNotFoundPanel);
    
    return vBox;
  }
  
  private Node createCheckThatAllVacationFoldersAreReferredTo() {
    VBox vBox = createErrorPanel("Check that all folders in the vacations folder are referenced from vacations.", "Each folder in the vacations folder shall be referred to by a vacation");
        
    foldersNotReferredToPanel = componentFactory.createVBox();
    vBox.getChildren().add(foldersNotReferredToPanel);
    
    return vBox;
  }
  
  private void clearErrorPanels() {
    dataStructureErrorsPanel.getChildren().clear();
    suspiciousFilesPanel.getChildren().clear();
    referencesNotFoundPanel.getChildren().clear();
    foldersNotReferredToPanel.getChildren().clear();
  }
  
  private void runChecks() {
    clearErrorPanels();
    
    runDataStructureCheck();
    runSuspiciousFilesCheck();
    runCheckThatAllReferencesExist(); 
    runCheckThatAllVacationFoldersAreReferredTo();
    runLocationBoundariesSizesCheck();
  }

  private void runDataStructureCheck() {
    boolean vacationStructureError = false;
    
    for (Vacation vacation: vacations.getVacations()) {
      vacationStructureError = VacationsChecker.checkVacation(vacation);
      if (vacationStructureError) {
        Label label = componentFactory.createLabel("Problem in structure of vacation " + vacation.getId());
        dataStructureErrorsPanel.getChildren().add(label);
        break;
      }
    }
    
    if (!vacationStructureError) {
      Label label = componentFactory.createLabel("OK");
      dataStructureErrorsPanel.getChildren().add(label);
    }
  }

  private void runSuspiciousFilesCheck() {
    List<String> suspiciousFiles = VacationsChecker.findSupiciousTopLevelFiles();
    
    if (suspiciousFiles == null) {
      Label label = componentFactory.createLabel("OK");
      suspiciousFilesPanel.getChildren().add(label);
    } else {
      for (String suspiciousFile: suspiciousFiles) {
        Label label = componentFactory.createLabel("File found in vacations folder: \'" + suspiciousFile + "\'.");
        suspiciousFilesPanel.getChildren().add(label);
      }
    }
  }

  private void runCheckThatAllReferencesExist() {
    List<FileReference> referencesNotFound = VacationsChecker.checkThatAllReferencesExist(vacations);
    
    if (referencesNotFound == null) {
      Label label = componentFactory.createLabel("OK");
      referencesNotFoundPanel.getChildren().add(label);
    } else {
      for (FileReference fileReference: referencesNotFound) {
        Label label = componentFactory.createLabel("Reference to non existing file found: file is " + fileReference.getFile() + ", title is " + fileReference.getTitle());
        referencesNotFoundPanel.getChildren().add(label);
      }
    }
  }

  private void runCheckThatAllVacationFoldersAreReferredTo() {
    List<String> foldersNotReferredTo = VacationsChecker.checkThatAllVacationFoldersAndFilesAreReferredTo(vacations.getVacations());
    
    if (foldersNotReferredTo == null) {
      Label label = componentFactory.createLabel("OK");
      foldersNotReferredToPanel.getChildren().add(label);
    } else {
      for (String folderNotReferredTo: foldersNotReferredTo) {
        Label label = componentFactory.createLabel("No vacation for folder: " + folderNotReferredTo);
        foldersNotReferredToPanel.getChildren().add(label);
      }
    }
  }
  
  private void runLocationBoundariesSizesCheck() {
    List<VacationElement> elements = new ArrayList<>();
    for (Vacation vacation: vacations.getVacations()) {
      for (VacationElement element: vacation.getElements()) {
        checkElement(vacation, elements, element);
      }
    }
  }

  private void checkElement(Vacation vacation, List<VacationElement> elements, VacationElement element) {
    elements.add(element);
    
    if (element instanceof Location) {
      Location location = (Location) element;
      for (Boundary boundary: location.getBoundaries()) {
        
        StringBuilder buf = new StringBuilder();
        buf.append(vacation.getTitle()).append(".");
        for (VacationElement vacationElement: elements) {
          buf.append(vacationElement.getClass().getName()).append(".");
        }
        buf.append(" ").append(boundary.getPoints().size());
        LOGGER.severe("SIZE: " + buf.toString());
      }
    }
    
    for (VacationElement vacationElement: element.getChildren()) {
      checkElement(vacation, elements, vacationElement);
    }
    
    elements.remove(element);
  }
  
  private VBox createErrorPanel(String title, String explanation) {
    VBox vBox = componentFactory.createVBox();
    vBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    vBox.setPadding(new Insets(10.0));
    Label label = componentFactory.createLabel(title);
    Tooltip tooltip = new Tooltip(explanation);
    label.setTooltip(tooltip);
    label.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
    vBox.getChildren().add(label);

    return vBox;
  }
}
