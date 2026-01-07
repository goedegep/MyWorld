package goedegep.finan.investmentinsurances.guifx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.appgen.swing.AppFrame;
import goedegep.finan.investmentinsurance.model.InsuranceCompany;
import goedegep.finan.investmentinsurance.model.InvestmentFund;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class InvestmentFundStockPriceDevelopmentPanel extends SplitPane {
  private static final Logger LOGGER = Logger.getLogger(InvestmentFundStockPriceDevelopmentPanel.class.getName());
  
  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;
  
  private ObservableList<InvestmentFund> selectedInvestmentFunds;
  private boolean normalizedStockPricesSelected;
  private InvestmentFundStockPriceDevelopmentChart investmentFundStockPriceDevelopmentChart;

  public InvestmentFundStockPriceDevelopmentPanel(CustomizationFx customization, List<InsuranceCompany> insuranceCompanies) {
    this.customization = customization;
    componentFactory = customization.getComponentFactoryFx();
    
    investmentFundStockPriceDevelopmentChart = new InvestmentFundStockPriceDevelopmentChart();
    VBox controlPanel = createFondsOntwikkelingsGrafiekControlPanel(null, insuranceCompanies);
        
    updateFondsOntwikkelingsGrafiek();
    
    getItems().addAll(controlPanel, investmentFundStockPriceDevelopmentChart);

    setOrientation(Orientation.HORIZONTAL);
    setDividerPositions(0.3f);
  }
  
  /**
   * Create the control panel.
   * <p>
   * On the top there are radio buttons to select the stock price type; absolute or normalized.
   * Below that is a tree to select companies and funds to show in the chart.
   * 
   * Het controle paneel is een JTree. De top nodes zijn de maatschappijen, met daaronder de nodes voor de bijbehorende fondsen.
   * Aangezien ik geen goede 'tri-state' checkboxes heb kunnen vinden, zijn de maatschappij-nodes gewone nodes.
   * De fonds-nodes zijn checkbox nodes, die alleen ieder afzonderlijk aan en uit te zetten zijn.
   * (Het zou handiger zijn als de maatschappij-nodes tri-state checkboxes zouden zijn. Selecteren zou alle bijbehorende fondsen
   * selecteren. Als slechts een deel van de fondsen van een maatschappij geselecteerd zijn, is de maatschappij node in de 3e toestand).
   * 
   * @param maatschappijen de maatschappijen die ingevuld worden in het paneel.
   * @return het controle paneel.
   */
  private VBox createFondsOntwikkelingsGrafiekControlPanel(AppFrame owner, List<InsuranceCompany> maatschappijen) {
    VBox vBox = componentFactory.createVBox(12.0);
    
    VBox buttonPanel = createButtonPanel();
    vBox.getChildren().add(buttonPanel);
    
    FundSelectionPanel fundSelectionPanel = new FundSelectionPanel(customization, maatschappijen);
    selectedInvestmentFunds = fundSelectionPanel.getSelectedInvestmentFunds();
    selectedInvestmentFunds.addListener(new ListChangeListener<InvestmentFund>() {

      @Override
      public void onChanged(Change<? extends InvestmentFund> c) {
        LOGGER.severe("CHANGED");
        for (InvestmentFund investmentFund: c.getList()) {
          LOGGER.severe("Fund: " + investmentFund.getName());
        }
        updateFondsOntwikkelingsGrafiek();
      }
    });
    vBox.getChildren().add(fundSelectionPanel);
    
    return vBox;
  }

  private VBox createButtonPanel() {
    VBox vBox = componentFactory.createVBox(4.0, 12.0);
    
    Label stockPriceLabel = componentFactory.createLabel("Stock price type:");
    vBox.getChildren().add(stockPriceLabel);
        
    ToggleGroup toggleGroup = new ToggleGroup();
    RadioButton absoluteRadioButton = new RadioButton("Absolute");
    absoluteRadioButton.setSelected(true);
    absoluteRadioButton.setToggleGroup(toggleGroup);
    RadioButton normalizedRadioButton = new RadioButton("Normalized");
    normalizedRadioButton.setToggleGroup(toggleGroup);
    vBox.getChildren().addAll(absoluteRadioButton, normalizedRadioButton);

    toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {
      RadioButton radioButton = (RadioButton) newVal;
      normalizedStockPricesSelected = radioButton.getText().equals("Normalized");
      updateFondsOntwikkelingsGrafiek();
      });

    return vBox;
  }
  
  private void updateFondsOntwikkelingsGrafiek() {
    investmentFundStockPriceDevelopmentChart.changeSettings(selectedInvestmentFunds, normalizedStockPricesSelected);
  }
}

class FundSelectionPanel extends VBox {
  
  private ObservableList<InvestmentFund> selectedInvestmentFunds = FXCollections.observableArrayList();
  private Map<String, InvestmentFund> fundIdToFundMap = new HashMap<>();

  public FundSelectionPanel(CustomizationFx customization, List<InsuranceCompany> insuranceCompanies) {
    
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    
    setPadding(new Insets(12.0));
    setSpacing(4.0);
    
    Label label = componentFactory.createLabel("Investment funds to show:");
    getChildren().add(label);

    
    for (InsuranceCompany insuranceCompany: insuranceCompanies) {
      for (InvestmentFund investmentFund: insuranceCompany.getInvestmentFunds()) {
        String fundId = getFundId(insuranceCompany, investmentFund);
        
        fundIdToFundMap.put(fundId, investmentFund);
        
        CheckBox checkBox = componentFactory.createCheckBox(fundId, true);
        selectedInvestmentFunds.add(investmentFund);
        checkBox.setOnAction((e) -> {
          String checkBoxFundId = checkBox.getText();
          InvestmentFund checkBoxInvestmentFund = fundIdToFundMap.get(checkBoxFundId);
          if (checkBoxInvestmentFund != null) {
            if (checkBox.isSelected()) {
              selectedInvestmentFunds.add(checkBoxInvestmentFund);
            } else {
              selectedInvestmentFunds.remove(checkBoxInvestmentFund);
            }
          }
        });
        getChildren().add(checkBox);
      }
    }    
  }
  
  public ObservableList<InvestmentFund> getSelectedInvestmentFunds() {
    return selectedInvestmentFunds;
  }

  private String getFundId(InsuranceCompany insuranceCompany, InvestmentFund investmentFund) {
    String companyName = insuranceCompany.getName();
    if (companyName == null) {
      companyName = "<no-company>";
    }
    
    String fundName = "<no-fund>";
    if (investmentFund.isSetName()) {
      fundName = investmentFund.getName();
    }
    
    return companyName + " - " + fundName;
  }
  
}
