package goedegep.finan.mortgage.app.guifx;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import goedegep.finan.mortgage.InterestCompensationMortgageCalculator;
import goedegep.finan.mortgage.model.CompensationPayment;
import goedegep.finan.mortgage.model.InterestCompensationMortgage;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.util.datetime.DateUtil;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class CompensationPaymentsWindow extends JfxStage {
  private final static Logger LOGGER = Logger.getLogger(CompensationPaymentsWindow.class.getName());
  
  private final static String WINDOW_TITLE = "Compensation payments ";
  
  private CustomizationFx customization;
  private InterestCompensationMortgageCalculator mortgageCalculator;
  private EMap<Integer, EList<CompensationPayment>> compensationPaymentsPerYear;
  private CompensationPaymentsTable compensationPaymentsTable;

  private ComboBox<Integer> yearComboBox;
  
  public CompensationPaymentsWindow(CustomizationFx customization, InterestCompensationMortgageCalculator mortgageCalculator) {
    super(customization, WINDOW_TITLE + mortgageCalculator.getMortgage().getId());
    
    this.customization = customization;
    this.mortgageCalculator = mortgageCalculator;
    
    InterestCompensationMortgage interestCompensationMortgage = (InterestCompensationMortgage) mortgageCalculator.getMortgage();
    compensationPaymentsPerYear = interestCompensationMortgage.getCompensationPaymentsPerYear();
    
    createGUI();
    
    updateCompensationPaymentsTable();
    
    show();
  }

  private void createGUI() {
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    
    VBox mainPane = componentFactory.createVBox();
    
    // First part: options
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
      updateCompensationPaymentsTable();      
    });
    optionsPane.add(yearComboBox, 1, 0);
    
    mainPane.getChildren().add(optionsPane);
    
    // Second part: compensation payments table
    compensationPaymentsTable = new CompensationPaymentsTable(customization);
    mainPane.getChildren().add(compensationPaymentsTable);
    
    setScene(new Scene(mainPane));
  }
  
  private void updateCompensationPaymentsTable() {
    LOGGER.severe("=>");
    int year = (int) yearComboBox.getValue();
    compensationPaymentsTable.setObjects(compensationPaymentsPerYear.get((Integer) year));
  }
}
