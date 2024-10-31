package goedegep.finan.mortgage.app.guifx;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import goedegep.finan.mortgage.MortgageCalculator;
import goedegep.finan.mortgage.MortgageReportsGenerator;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.objectcontrols.ObjectControlFileSelecter;
import goedegep.util.datetime.DateUtil;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class YearlyReportOptionsWindow extends JfxStage {
  private final static Logger LOGGER = Logger.getLogger(YearlyReportOptionsWindow.class.getName());
  
  private final static String WINDOW_TITLE = "Yearly Report options";
  
  private MortgageCalculator mortgageCalculator;

//  private BooleanProperty selectionValidProperty;
  private ComboBox<Integer> yearComboBox;
  private ObjectControlFileSelecter fileSelecter;
  private Button okButton;
  
  public YearlyReportOptionsWindow(CustomizationFx customization, MortgageCalculator mortgageCalculator) {
    super(customization, WINDOW_TITLE);
    
    this.mortgageCalculator = mortgageCalculator;
    
    createGUI();
    checkOptions();      
    
    show();
  }

  private void createGUI() {
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    
    GridPane optionsPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    
    Label label;
    
    // First row: year selection
    label = componentFactory.createLabel("Year: ");
    optionsPane.add(label, 0, 0);
    
    List<Integer> yearsList = mortgageCalculator.getActualDurationYearsList();
    yearComboBox = componentFactory.createComboBox(yearsList);
    // Select most likely year to generate report for; the year before the current date, if that year exists in the list, otherwise the first entry in the list.
    Date currentDate = new Date();
    Integer lastYear = DateUtil.getDateYear(currentDate) - 1;
    if (yearsList.size() > 0) {
      if ((lastYear >= yearsList.get(0))  &&  (lastYear <= yearsList.get(yearsList.size() - 1))) {
        yearComboBox.setValue(lastYear);
      } else {
        yearComboBox.setValue(yearsList.get(0));
      }
    }
    yearComboBox.setOnAction((e) -> {
      LOGGER.severe("yearComboBox action");
      checkOptions();      
    });
    optionsPane.add(yearComboBox, 1, 0);
    
    // Second row: File selection
    label = componentFactory.createLabel("File: ");
    optionsPane.add(label, 0, 1);
    
    fileSelecter = componentFactory.createFileSelecterObjectControl(400, "File to save the report to", "Choose file", "Open a file chooser", "Report file selection", false);
    fileSelecter.addFileType(".pdf", "Portable Data Format", true);
    fileSelecter.setOpenOrSaveDialog(true);
    Node fileNameTextField = fileSelecter.getControl();
    fileSelecter.addListener((observable) -> {
      LOGGER.severe("In textProperty Listener");
      checkOptions();      
    });
    optionsPane.add(fileNameTextField, 1, 1);
    
    Button button = fileSelecter.getFileChooserButton();
    optionsPane.add(button, 2, 1);
    
//    selectionValidProperty = fileSelecter.ocValidProperty();

    // Third row: OK and cancel buttons
    button = componentFactory.createButton("Cancel", "Exit window without generating report");
    optionsPane.add(button, 0, 2);
    
    okButton = componentFactory.createButton("OK", "Save the report to the specified file");
    okButton.setOnAction((e) -> {
      generateYearlyReport();
    });
    optionsPane.add(okButton, 2, 2);
    
    setScene(new Scene(optionsPane));
  }
  
  private void checkOptions() {
    boolean selectionIsValid = false;
    
    if ((yearComboBox.getValue() != null)  &&
        fileSelecter.isValid()) {
      selectionIsValid = true;
    }
    
    okButton.setDisable(!selectionIsValid);
  }
  
  private void generateYearlyReport() {
    LOGGER.severe("=>" + fileSelecter.getValue());
    int year = (int) yearComboBox.getValue();
    File file = fileSelecter.getValue();
    MortgageReportsGenerator.generateYearlyPdfReport(mortgageCalculator, year, file);
    LOGGER.severe("Yearly report for " + year + " generated to:  " + file.getAbsolutePath());    
  }
}
