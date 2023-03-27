package goedegep.finan.investmentinsurances.app.guifx;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.finan.investmentinsurance.model.AnnualStatement;
import goedegep.finan.investmentinsurance.model.InvestmentFund;
import goedegep.finan.investmentinsurance.model.InvestmentInsurance;
import goedegep.finan.investmentinsurance.model.InvestmentInsuranceFactory;
import goedegep.finan.investmentinsurance.model.InvestmentInsurancesData;
import goedegep.finan.investmentinsurance.model.Participation;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.objectcontrols.ObjectControlBoolean;
import goedegep.jfx.objectcontrols.ObjectControlCurrency;
import goedegep.jfx.objectcontrols.ObjectControlFixedPointValue;
import goedegep.jfx.objectcontrols.ObjectControlFlexDate;
import goedegep.jfx.objectcontrols.ObjectControlLocalDate;
import goedegep.types.model.DateRateTuplet;
import goedegep.types.model.Event;
import goedegep.types.model.TypesFactory;
import goedegep.util.datetime.FlexDate;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class provides an editor for an Annual Statement.
 * <p>
 * It is used to:
 * <ul>
 * <li>Edit (or view) an existing Annual Statement</li>
 * <li>Create a new Annual Statement<br/>
 * After saving a new statement, it automatically changes to editing an existing statement.
 * </li>
 * </ul>
 */
public class AnnualStatementEditor extends JfxStage {
  /*
   * Strategy:
   * For a new Annual Statement, all GUI components are set to default values. Upon save, a new Annual Statement is created and filled from the GUI components.
   * When an Annual Statement is edited, all GUI components are filled from the Annual Statement. Upon save, the Annual Statement is filled from the GUI components.
   */
  private static final Logger LOGGER = Logger.getLogger(AnnualStatementEditor.class.getName());
  private static final String WINDOW_TITLE   = "Annual Statement editor";
  
  private boolean newStatementMode; // if true, we're editing a new statement, else we're editing an existing statement.
  private AnnualStatement annualStatement;
  private InvestmentInsurancesData investmentInsurancesData;
  private InvestmentInsurance investmentInsurance;
  private ComponentFactoryFx componentFactory;
  
  private GridPane selectionGridPane;
  private GridPane gridPane;
  private List<InvestmentInsuranceWrapper> investmentInsuranceWrappers;
  private ComboBox<InvestmentInsuranceWrapper> investmentInsuranceComboBox;
  private ObservableList<AnnualStatementWrapper> annualStatementWrappers;
  private ComboBox<AnnualStatementWrapper> annualStatementComboBox;
  private ObjectControlFlexDate annualStatementDateTextField;
  private ObjectControlLocalDate periodFromTextField;
  private ObjectControlLocalDate periodUntilTextField;
  private ObjectControlCurrency premiumDeathRiskCoverageTextField;
  private ObjectControlCurrency continuingCostsInsuranceCompanyTextField;
  private ObjectControlCurrency managementCostsTextField;
  private ObjectControlCurrency earnedOnTheParticipationsTextField;
  private ObjectControlCurrency costsRestitutionTextField;
  private ObjectControlFixedPointValue expectedYearlyCostsIncreaseTextField;
  private ObjectControlLocalDate exampleCapitalOnEndDateTextField;
  private List<ParticipationControls> participationsControls;
  private Label statusBar;
 

  /**
   * Constructor
   * 
   * @param customization the window customization.
   * @param annualStatements the list of Annual Statements to which the new Annual Statement is to be added.
   * @param newStatementMode if true, start in new statement mode, else in edit existing statement mode.
   */
  public AnnualStatementEditor(CustomizationFx customization, InvestmentInsurancesData investmentInsurancesData, boolean newStatementMode) {
    super(WINDOW_TITLE, customization);
    
    LOGGER.fine("=>");
    
    this.investmentInsurancesData = investmentInsurancesData;
    this.newStatementMode = newStatementMode;
    componentFactory = getComponentFactory();
    
    createGUI();
    updateSelectionPane();
    
    if (!investmentInsuranceComboBox.getItems().isEmpty()) {
      LOGGER.fine("=> Going to select first Investment Insurance");
      investmentInsuranceComboBox.setValue(investmentInsuranceComboBox.getItems().get(0));
      handleInvestmentInsuranceChanged();
    }
    
    show();
  }
  
  private void createGUI() {
    BorderPane rootLayout = new BorderPane();
    VBox mainPane = componentFactory.createVBox();
    
    // Selection pane
    selectionGridPane = componentFactory.createGridPane(12.0, 10.0, 12.0);
    
    mainPane.getChildren().add(selectionGridPane);
    
    // Separator
    final Separator separator = new Separator();
    separator.setOrientation(Orientation.HORIZONTAL);
    separator.setHalignment(HPos.CENTER);
    mainPane.getChildren().add(separator);
    
    gridPane = componentFactory.createGridPane(12.0, 10.0, 12.0);
    
    // Investment insurance selection
    investmentInsuranceWrappers = InvestmentInsuranceWrapper.createInvestmentInsuranceWrapperList(investmentInsurancesData.getInvestmentInsurances());
    investmentInsuranceComboBox = componentFactory.createComboBox(investmentInsuranceWrappers);
    investmentInsuranceComboBox.setOnAction(e -> handleInvestmentInsuranceChanged());
    
    // Annual statement selection (only shown in edit mode).
    annualStatementComboBox = componentFactory.createComboBox(null);
    annualStatementComboBox.setOnAction(e -> handleAnnualStatementChanged());
    
    // Separator
    
    // Annual Statement date
    annualStatementDateTextField = componentFactory.createObjectControlFlexDate(null, 100, false, "Datering van de jaaropgave");
    
    // Period
    periodFromTextField = componentFactory.createObjectControlLocalDate((LocalDate) null, 100, false, "Begindatum van de periode waarover dit overzicht van toepassing is");
    periodUntilTextField = componentFactory.createObjectControlLocalDate((LocalDate) null, 100, false, "Einddatum van de periode waarover dit overzicht van toepassing is");
    
    // Premium death risk coverage
    premiumDeathRiskCoverageTextField = componentFactory.createObjectControlCurrency((PgCurrency) null, 100, true, "Betaalde premie voor de dekking van overlijdensrisico");
    premiumDeathRiskCoverageTextField.setValidFactorRange(100, 100);
    
    // Continuing Costs Insurance Company
    continuingCostsInsuranceCompanyTextField = componentFactory.createObjectControlCurrency((PgCurrency) null, 100, true, "Betaalde premie voor de dekking van overlijdensrisico");
    continuingCostsInsuranceCompanyTextField.setValidFactorRange(100, 100);
    
    // Management Costs
    managementCostsTextField = componentFactory.createObjectControlCurrency((PgCurrency) null, 100, true, "Management costs:");
    managementCostsTextField.setValidFactorRange(100, 100);
    
    // Earned on the participations
    earnedOnTheParticipationsTextField = componentFactory.createObjectControlCurrency((PgCurrency) null, 100, true, "Verdiend op de participaties");
    
    // Costs restitution
    costsRestitutionTextField = componentFactory.createObjectControlCurrency((PgCurrency) null, 100, true, "Teruggave te veel betaalde kosten");
    
    // expectedYearlyCostsIncrease
    expectedYearlyCostsIncreaseTextField = componentFactory.createObjectControlFixedPointValue(null, 100, true, "Wordt gebruikt in eigen berekening");
    expectedYearlyCostsIncreaseTextField.setValidFactorRange(100, 100);
    
    participationsControls = new ArrayList<>();
    //
    // Participations at the end of the period
    //
    
    //
    // Participation mutations
    //
    
    // Example end capital date
    exampleCapitalOnEndDateTextField = componentFactory.createObjectControlLocalDate((LocalDate) null, 100, true, "Datum waarvoor de voorbeeld kapitalen berekend zijn");

    //
    // Example capitals
    //
    // header 1
    
    //
    // Total expense ratios
    //
    // header
       
    
    mainPane.getChildren().add(gridPane);
    
    mainPane.getChildren().add(createButtonsPane());
    
    rootLayout.setCenter(mainPane);
    
    HBox hBox = componentFactory.createHBox();
    statusBar = componentFactory.createLabel(null);
    hBox.getChildren().add(statusBar);
    rootLayout.setBottom(hBox);

    setScene(new Scene(rootLayout));
  }
  
  private void updateSelectionPane() {
    Label label;
    int row = 0;
    
    selectionGridPane.getChildren().clear();
    
    // Investment insurance selection
    label = componentFactory.createLabel("Beleggingsverzekering:");
    selectionGridPane.add(label, 0, row);
    
    selectionGridPane.add(investmentInsuranceComboBox, 1, row, 2, 1);
    
    // Annual Statement selection, only in edit existing statement mode.
    if (!newStatementMode) {      
      row++;
      
      label = componentFactory.createLabel("Jaaropgave:");
      selectionGridPane.add(label, 0, row);
      
      selectionGridPane.add(annualStatementComboBox, 1, row, 2, 1);
    }
  }
    
  private void redrawGUI() {
    Label label;
    int row = 0;
    
    gridPane.getChildren().clear();
        
    // Annual Statement date
    label = componentFactory.createLabel("Datum jaaropgave:");
    gridPane.add(label, 0, row);
    
    gridPane.add(annualStatementDateTextField, 1, row);
    
    row++;
    
    // Period
    label = componentFactory.createLabel("Periode:");
    gridPane.add(label, 0, row);
    
    gridPane.add(periodFromTextField, 1, row);
    
    label = componentFactory.createLabel("tot");
    gridPane.add(label, 2, row);
    
    gridPane.add(periodUntilTextField, 3, row);
    
    row++;
    
    // Premium death risk coverage
    label = componentFactory.createLabel("Premies overlijdensrisicodekking:");
    gridPane.add(label, 0, row);
    
    gridPane.add(premiumDeathRiskCoverageTextField, 1, row);
    
    row++;
    
    // Continuing Costs Insurance Company
    label = componentFactory.createLabel("Doorlopende kosten verzekeringsmaatschappij:");
    gridPane.add(label, 0, row);
    
    gridPane.add(continuingCostsInsuranceCompanyTextField, 1, row);
    
    row++;
    
    // Management Costs
    label = componentFactory.createLabel("Beheerskosten:");
    gridPane.add(label, 0, row);
    
    gridPane.add(managementCostsTextField, 1, row);
    
    row++;
    
    // Earned on the participations
    label = componentFactory.createLabel("Verdiend op de participaties:");
    gridPane.add(label, 0, row);
    
    gridPane.add(earnedOnTheParticipationsTextField, 1, row);
    
    row++;
    
    // Costs restitution
    label = componentFactory.createLabel("Teruggave kosten:");
    gridPane.add(label, 0, row);
    
    gridPane.add(costsRestitutionTextField, 1, row);
    
    row++;
    
    // expectedYearlyCostsIncrease
    label = componentFactory.createLabel("Verwachtte kostenstijging per jaar:");
    gridPane.add(label, 0, row);
    
    gridPane.add(expectedYearlyCostsIncreaseTextField, 1, row);
    
    row++;
    
    //
    // Participations at the end of the period
    //
    label = componentFactory.createLabel("Aantal participaties aan het eind van de periode");
    gridPane.add(label, 0, row);    
    row++;
    // header
    label = componentFactory.createLabel("Fonds:");
    gridPane.add(label, 0, row);
    label = componentFactory.createLabel("Aantal participaties:");
    gridPane.add(label, 1, row);
    label = componentFactory.createLabel("Koers:");
    gridPane.add(label, 2, row);
    label = componentFactory.createLabel("Verdelingspercentage:");
    gridPane.add(label, 3, row);
    
    row++;
    
    // One row per participation.    
    for (ParticipationControls participationControls: participationsControls) {
      InvestmentFund investmentFund = participationControls.participation.getInvestmentFund();
      String investmentFundName = null;
      if (investmentFund != null  &&  investmentFund.isSetName()) {
        investmentFundName = investmentFund.getName();
      }
      label = componentFactory.createLabel(investmentFundName);
      gridPane.add(label, 0, row);
      
      gridPane.add(participationControls.numberOfParticipationsEndControl, 1, row);
      
      gridPane.add(participationControls.stockPriceEndControl, 2, row);
      
      gridPane.add(participationControls.distributionPercentageControl, 3, row);
      
      row++;
    }
    
    //
    // Participation mutations
    //
    label = componentFactory.createLabel("Overzicht van de aangekochte en verkochte participaties");
    gridPane.add(label, 0, row);    
    row++;
    // header
    label = componentFactory.createLabel("Fonds:");
    gridPane.add(label, 0, row);
    label = componentFactory.createLabel("Participaties bij:");
    gridPane.add(label, 1, row);
    label = componentFactory.createLabel("Participaties af:");
    gridPane.add(label, 2, row);
    
    row++;
    
    // One row per fund.    
    for (ParticipationControls participationControls: participationsControls) {
      label = componentFactory.createLabel(participationControls.participation.getInvestmentFund().getName());
      gridPane.add(label, 0, row);
      
      gridPane.add(participationControls.numberOfParticipationsBoughtControl, 1, row);
      
      gridPane.add(participationControls.numberOfParticipationsSoldControl, 2, row);
      
      gridPane.add(participationControls.participationMutationsCompleteControl, 3, row);
      
      row++;
    }
    
    // Example end capital date
    label = componentFactory.createLabel("Voorbeeld kapitalen voor:");
    gridPane.add(label, 0, row);
    
    gridPane.add(exampleCapitalOnEndDateTextField, 1, row);
    
    row++;

    //
    // Example capitals
    //
    // header 1
    label = componentFactory.createLabel("Fonds:");
    gridPane.add(label, 0, row);
    label = componentFactory.createLabel("%:");
    gridPane.add(label, 1, row);
    label = componentFactory.createLabel("Netto historisch:");
    gridPane.add(label, 2, row);
    label = componentFactory.createLabel("%:");
    gridPane.add(label, 3, row);
    label = componentFactory.createLabel("4% Bruto:");
    gridPane.add(label, 4, row);
    
    row++;
    
    // One row per fund.    
    for (ParticipationControls participationControls: participationsControls) {
      label = componentFactory.createLabel(participationControls.participation.getInvestmentFund().getName());
      gridPane.add(label, 0, row);
      
      gridPane.add(participationControls.exampleReturnOnInvestmentNetHistoricControl, 1, row);
      
      gridPane.add(participationControls.exampleCapitalNetHistoricControl, 2, row);
      
      gridPane.add(participationControls.exampleReturnOnInvestmentGrossControl, 3, row);
      
      gridPane.add(participationControls.exampleCapitalGrossControl, 4, row);
      
      row++;
    }
    
    // header 2
    label = componentFactory.createLabel("Fonds:");
    gridPane.add(label, 0, row);
    label = componentFactory.createLabel("%:");
    gridPane.add(label, 1, row);
    label = componentFactory.createLabel("Bruto eigen:");
    gridPane.add(label, 2, row);
    label = componentFactory.createLabel("%:");
    gridPane.add(label, 3, row);
    label = componentFactory.createLabel("Pessimistisch:");
    gridPane.add(label, 4, row);
    
    row++;
    
    // One row per fund.    
    for (ParticipationControls participationControls: participationsControls) {
      label = componentFactory.createLabel(participationControls.participation.getInvestmentFund().getName());
      gridPane.add(label, 0, row);
      
      gridPane.add(participationControls.exampleReturnOnInvestmentGrossCompanyOwnControl, 1, row);
      
      gridPane.add(participationControls.exampleCapitalGrossCompanyOwnControl, 2, row);
      
      
      gridPane.add(participationControls.exampleReturnOnInvestmentPessimisticControl, 3, row);
      
      gridPane.add(participationControls.exampleCapitalPessimisticControl, 4, row);
      
      row++;
    }
    
    //
    // Total expense ratios
    //
    label = componentFactory.createLabel("Total Expense Ratios");
    gridPane.add(label, 0, row);
    
    row++;
    
    // header
    label = componentFactory.createLabel("Fonds:");
    gridPane.add(label, 0, row);
    label = componentFactory.createLabel("Lopende kosten:");
    gridPane.add(label, 1, row);
    
    row++;
    
    // One row per fund.    
    for (ParticipationControls participationControls: participationsControls) {
      label = componentFactory.createLabel(participationControls.participation.getInvestmentFund().getName());
      gridPane.add(label, 0, row);
      
      gridPane.add(participationControls.totalExpenseRatioControl, 1, row);
            
      row++;
    }
   
  }

  /**
   * Create the pane with the 'Cancel' and 'Save changes' buttons.
   * 
   * @return the created buttons pane.
   */
  private Node createButtonsPane() {
    HBox buttonPane = componentFactory.createHBox(12.0, new Insets(10d, 15d, 10d, 15d));
    
    Button button = componentFactory.createButton("Cancel", "Close window without saving changes");
    button.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        closeWindow();
      }
      
    });
    buttonPane.getChildren().add(button);
    
    button = componentFactory.createButton("Add/update annual statement", "Save the changes");
    button.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        saveAnnualStatement();
      }
      
    });
    buttonPane.getChildren().add(button);

    return buttonPane;
  }
  
  private void handleInvestmentInsuranceChanged() {
    LOGGER.fine("=>");
    
    clearControls();
        
    InvestmentInsuranceWrapper investmentInsuranceWrapper = investmentInsuranceComboBox.getValue();
    
    if (investmentInsuranceWrapper != null) {
      investmentInsurance = investmentInsuranceWrapper.getInvestmentInsurance();
      
      if (newStatementMode) {
        // Start edit for new statement, based on some info from previous statement.
        fillGUIFromInvestmentInsurance();
        
        redrawGUI();
      } else {
        // Show Annual Statements for selected investement insurance.
        List<Event> events = investmentInsurance.getEvents();
        ObservableList<AnnualStatement> annualStatements = FXCollections.observableArrayList();
        for (Event event: events) {
          if (event instanceof AnnualStatement) {
            annualStatements.add((AnnualStatement) event);
          }
        }
        annualStatementWrappers = AnnualStatementWrapper.createAnnualStatementWrapperList(annualStatements);
        annualStatementComboBox.setItems(annualStatementWrappers);
        if (!annualStatementWrappers.isEmpty()) {
          annualStatementComboBox.setValue(annualStatementComboBox.getItems().get(annualStatementComboBox.getItems().size() - 1));
          updateSelectionPane();
          handleAnnualStatementChanged();
        }
      }
    }
  }
  
  private void handleAnnualStatementChanged() {
    LOGGER.fine("=>");
    
    clearControls();
    
    AnnualStatementWrapper annualStatementWrapper = annualStatementComboBox.getValue();
    if (annualStatementWrapper != null) {
      annualStatement = annualStatementWrapper.getAnnualStatement();
    } else {
      annualStatement = null;
    }
    fillGUIFromAnnualStatement();
    
    redrawGUI();
  }
  
  private void saveAnnualStatement() {
    if (!controlValuesValid()) {
      return;
    }
    if (newStatementMode) {
      annualStatement = InvestmentInsuranceFactory.eINSTANCE.createAnnualStatement();
      investmentInsurance.getEvents().add(annualStatement);
    } else {
      clearAnnualStatement();
    }
    newStatementMode = false;
    
    fillAnnualStatementFromGUI();
    updateSelectionPane();
    handleInvestmentInsuranceChanged();
//    clearControls();
//    fillGUIFromAnnualStatement();
//    redrawGUI();
  }
  
  private boolean controlValuesValid() {
    LOGGER.fine("=>");
    
    // Annual Statement date
    if (!annualStatementDateTextField.ocIsValid()) {
      statusBar.setText("Fout in het veld 'Datum jaaropgave': " + annualStatementDateTextField.ocGetErrorText());
      return false;
    }

    // Period
    if (!periodFromTextField.ocIsValid()) {
      statusBar.setText("Fout in het veld 'Periode van': " + periodFromTextField.ocGetErrorText());
      return false;
    }
    if (!periodUntilTextField.ocIsValid()) {
      statusBar.setText("Fout in het veld 'Periode tot': " + periodUntilTextField.ocGetErrorText());
      return false;
    }

    // Premium death risk coverage
    if (!premiumDeathRiskCoverageTextField.ocIsValid()) {
      statusBar.setText("Fout in het veld 'Premies overlijdensrisicodekking': " + premiumDeathRiskCoverageTextField.ocGetErrorText());
      return false;
    }

    // Continuing Costs Insurance Company; seems stable, so copy from last year
    if (!continuingCostsInsuranceCompanyTextField.ocIsValid()) {
      statusBar.setText("Fout in het veld 'Doorlopende kosten verzekeringsmaatschappij': " + continuingCostsInsuranceCompanyTextField.ocGetErrorText());
      return false;
    }

    // Management Costs
    if (!managementCostsTextField.ocIsValid()) {
      statusBar.setText("Fout in het veld 'Beheerskosten': " + managementCostsTextField.ocGetErrorText());
      return false;
    }

    // Earned on the participations
    if (!earnedOnTheParticipationsTextField.ocIsValid()) {
      statusBar.setText("Fout in het veld 'Verdiend op de participaties': " + earnedOnTheParticipationsTextField.ocGetErrorText());
      return false;
    }

    // Costs restitution
    if (!costsRestitutionTextField.ocIsValid()) {
      statusBar.setText("Fout in het veld 'Teruggave kosten': " + costsRestitutionTextField.ocGetErrorText());
      return false;
    }

    // expectedYearlyCostsIncrease.
    if (!expectedYearlyCostsIncreaseTextField.ocIsValid()) {
      statusBar.setText("Fout in het veld 'Verwachtte kostenstijging per jaar': " + expectedYearlyCostsIncreaseTextField.ocGetErrorText());
      return false;
    }

    // Participations
    for (ParticipationControls participationControls: participationsControls) {
      if (!participationControlValuesValid(participationControls)) {
        return false;
      }
    }

    // Example end capital date.
    if (!exampleCapitalOnEndDateTextField.ocIsValid()) {
      statusBar.setText("Fout in het veld 'Voorbeeld kapitalen voor': " + exampleCapitalOnEndDateTextField.ocGetErrorText());
      return false;
    }
    
    LOGGER.fine("<=");
    return true;
  }
  
  private boolean participationControlValuesValid(ParticipationControls participationControls) {
    // Participations at the end of the period.
    if (!participationControls.numberOfParticipationsEndControl.ocIsValid()) {
      statusBar.setText("Fout in een veld 'Aantal participaties': " + participationControls.numberOfParticipationsEndControl.ocGetErrorText());
      return false;
    }
    // Distribution percentage
    if (!participationControls.distributionPercentageControl.ocIsValid()) {
      statusBar.setText("Fout in een veld 'Verdelingspercentage': " + participationControls.distributionPercentageControl.ocGetErrorText());
      return false;
    }
      
    //
    // Participation mutations
    //

    // Participations bought.
    if (!participationControls.numberOfParticipationsBoughtControl.ocIsValid()) {
      statusBar.setText("Fout in een veld 'Participaties bij': " + participationControls.numberOfParticipationsBoughtControl.ocGetErrorText());
      return false;
    }

    // Participations sold.
    if (!participationControls.numberOfParticipationsSoldControl.ocIsValid()) {
      statusBar.setText("Fout in een veld 'Participaties af': " + participationControls.numberOfParticipationsSoldControl.ocGetErrorText());
      return false;
    }

    // Participation mutations complete.
    // always valid
    
    //
    // Example capitals
    //
    if (!participationControls.exampleReturnOnInvestmentNetHistoricControl.ocIsValid()) {
      statusBar.setText("Fout in een veld 'Percentag Netto historisch': " + participationControls.exampleReturnOnInvestmentNetHistoricControl.ocGetErrorText());
      return false;
    }
    if (!participationControls.exampleCapitalNetHistoricControl.ocIsValid()) {
      statusBar.setText("Fout in een veld 'Voorbeeld kapitaal Netto historisch': " + participationControls.exampleCapitalNetHistoricControl.ocGetErrorText());
      return false;
    }
    
    if (!participationControls.exampleReturnOnInvestmentGrossControl.ocIsValid()) {
      statusBar.setText("Fout in een veld 'Percentag 4% Bruto': " + participationControls.exampleReturnOnInvestmentGrossControl.ocGetErrorText());
      return false;
    }
    if (!participationControls.exampleCapitalGrossControl.ocIsValid()) {
      statusBar.setText("Fout in een veld 'Voorbeeld kapitaal 4% Bruto': " + participationControls.exampleCapitalGrossControl.ocGetErrorText());
      return false;
    }
    
    if (!participationControls.exampleReturnOnInvestmentPessimisticControl.ocIsValid()) {
      statusBar.setText("Fout in een veld 'Percentag Pessimistisch': " + participationControls.exampleReturnOnInvestmentPessimisticControl.ocGetErrorText());
      return false;
    }
    if (!participationControls.exampleCapitalPessimisticControl.ocIsValid()) {
      statusBar.setText("Fout in een veld 'Voorbeeld kapitaal Pessimistisch': " + participationControls.exampleCapitalPessimisticControl.ocGetErrorText());
      return false;
    }
    
    if (!participationControls.exampleReturnOnInvestmentGrossCompanyOwnControl.ocIsValid()) {
      statusBar.setText("Fout in een veld 'Percentag Bruto eigen': " + participationControls.exampleReturnOnInvestmentGrossCompanyOwnControl.ocGetErrorText());
      return false;
    }
    if (!participationControls.exampleCapitalGrossCompanyOwnControl.ocIsValid()) {
      statusBar.setText("Fout in een veld 'Voorbeeld kapitaal Bruto eigen': " + participationControls.exampleCapitalGrossCompanyOwnControl.ocGetErrorText());
      return false;
    }
    
    if (!participationControls.returnOnInvestmentReductionNetHistoricControl.ocIsValid()) {
      statusBar.setText("Fout in een veld 'Percentag Gereduceerd eigen': " + participationControls.returnOnInvestmentReductionNetHistoricControl.ocGetErrorText());
      return false;
    }
    if (!participationControls.exampleCapitalAfterReductionControl.ocIsValid()) {
      statusBar.setText("Fout in een veld 'Voorbeeld kapitaal Gereduceerd eigen': " + participationControls.exampleCapitalAfterReductionControl.ocGetErrorText());
      return false;
    }
    
    //
    // Total expense ratios.
    //
    if (!participationControls.totalExpenseRatioControl.ocIsValid()) {
      statusBar.setText("Fout in een veld 'Lopende kosten': " + participationControls.totalExpenseRatioControl.ocGetErrorText());
      return false;
    }
    
    return true;    
  }
  
  private void closeWindow() {
    this.close();
  }
  
  private void clearControls() {
        
    // Annual Statement date
    annualStatementDateTextField.setText(null);
    
    // Period
    periodFromTextField.setText(null);
    periodUntilTextField.setText(null);
    
    // Premium death risk coverage
    premiumDeathRiskCoverageTextField.setText(null);
    
    // Continuing Costs Insurance Company
    continuingCostsInsuranceCompanyTextField.setText(null);
    
    // Management Costs
    managementCostsTextField.setText(null);
    
    // Earned on the participations
    earnedOnTheParticipationsTextField.setText(null);
    
    // Costs restitution
    costsRestitutionTextField.setText(null);
    
    // expectedYearlyCostsIncrease
    expectedYearlyCostsIncreaseTextField.setText(null);
    
    //
    // Participations at the end of the period
    //
    
    //
    // Participation mutations
    //
    
    // Example end capital date
    exampleCapitalOnEndDateTextField.setText(null);

    //
    // Example capitals
    //
    // header 1
    
    //
    // Total expense ratios
    //
    // header
  }
  
  /**
   * Based on a selected investment insurance, this method tries to fill in some values for the annual statement in the GUI.
   * 
   * @param investmentInsurance The currently selected investment insurance.
   */
  private void fillGUIFromInvestmentInsurance() {
    
    AnnualStatement lastAnnualStatement = investmentInsurance.getLastAnnualStatement();
    
    // Annual Statement date; this is typically one year after the date of the previous annual statement.
    if (lastAnnualStatement != null  &&  lastAnnualStatement.isSetDate()) {
      FlexDate previousAnnualStatementDate = lastAnnualStatement.getDate();
      FlexDate annualStatementDate = new FlexDate(previousAnnualStatementDate.getDay(), previousAnnualStatementDate.getMonth(), previousAnnualStatementDate.getYear() + 1);
      annualStatementDateTextField.ocSetValue(annualStatementDate);
    }
    
    // Period; this is typically one year after the period of the previous annual statement.
    if (lastAnnualStatement != null  &&  lastAnnualStatement.isSetPeriodUntil()) {
      LocalDate lastAnnualStatementPeriodUntil = lastAnnualStatement.getPeriodUntil();
      periodFromTextField.ocSetValue(lastAnnualStatementPeriodUntil);
      periodUntilTextField.ocSetValue(lastAnnualStatementPeriodUntil.plusYears(1));
    }
    
    // Premium death risk coverage; no use setting this value, as it changes every year
    
    // Continuing Costs Insurance Company; seems stable, so copy from last year
    if (lastAnnualStatement != null  &&  lastAnnualStatement.isSetContinuingCostsInsuranceCompany()) {
      continuingCostsInsuranceCompanyTextField.ocSetValue(lastAnnualStatement.getContinuingCostsInsuranceCompany());
    }
    
    // Management Costs; no use setting this value, as it changes every year
    
    // Earned on the participations; no use setting this value, as it changes every year
    
    // Costs restitution; no use setting this value, as it changes every year
    
    // expectedYearlyCostsIncrease; I use a fixed value for this, so copy from last year.
    if (lastAnnualStatement != null  &&  lastAnnualStatement.isSetExpectedYearlyCostsIncrease()) {
      expectedYearlyCostsIncreaseTextField.ocSetValue(lastAnnualStatement.getExpectedYearlyCostsIncrease());
    }
    
    
    participationsControls.clear();
    for (Participation participation: lastAnnualStatement.getParticipations()) {
      ParticipationControls participationControls = createParticipationControlsForOldParticipation(participation);
      participationsControls.add(participationControls);
    }
    
    // Example end capital date; normally the same date, so copy from last year
    if (lastAnnualStatement != null  &&  lastAnnualStatement.isSetExampleCapitalOnEndDate()) {
      exampleCapitalOnEndDateTextField.ocSetValue(lastAnnualStatement.getExampleCapitalOnEndDate());
    }
    
  }
  
  ParticipationControls createParticipationControlsForOldParticipation(Participation participation) {
    ParticipationControls participationControls = new ParticipationControls();    
    
    participationControls.participation = participation;
    
    // Participations at the end of the period; no use setting this value, as it changes every year
    participationControls.numberOfParticipationsEndControl = componentFactory.createObjectControlFixedPointValue(null, 100, true, "number of participations at the end of the period");
    
    // Stock price at the end of the period.
    PgCurrency stockPriceEnd = null;
    InvestmentFund investmentFund = participation.getInvestmentFund();
    if (investmentFund != null) {
      AnnualStatement annualStatement = participation.getAnnualStatement();
      if (annualStatement.isSetPeriodUntil()) {
        LocalDate periodUntil = participation.getAnnualStatement().getPeriodUntil().plusYears(1);
        stockPriceEnd = investmentFund.getStockPrice(periodUntil);
        LOGGER.fine("Stockprice for " + periodUntil + " is " + stockPriceEnd);
      }
    }
    participationControls.stockPriceEndControl = componentFactory.createObjectControlCurrency(stockPriceEnd, 100, true, "stock price at the end of the period");
    
    // Distribution percentage: doesn't change, so copy from last year.
    FixedPointValue distributionPercentage = null;
    if (participation.isSetDistributionPercentage()) {
      distributionPercentage = participation.getDistributionPercentage();
    }
    participationControls.distributionPercentageControl = componentFactory.createObjectControlFixedPointValue(distributionPercentage, 100, true, "relative size of the participation");
    
  
    //
    // Participation mutations
    //

    // Participations bought: no use setting this value, as it changes every year
    participationControls.numberOfParticipationsBoughtControl = componentFactory.createObjectControlFixedPointValue(null, 100, true, "Participaties bij (aangekochte participaties)");

    // Participations sold: no use setting this value, as it changes every year
    participationControls.numberOfParticipationsSoldControl = componentFactory.createObjectControlFixedPointValue(null, 100, true, "Participaties af (verkochte participaties)");

    // Participation mutations complete: for safety 'false' by default
    participationControls.participationMutationsCompleteControl = componentFactory.createObjectControlBoolean("Participatiemutaties volledig", false, false,
        "Als dit aangevinkt is, geeft dat aan dat het overzicht van aangekochte en verkochte participaties volledig is.");
    
    //
    // Example capitals; no proposals for any of these
    //
    participationControls.exampleReturnOnInvestmentNetHistoricControl = componentFactory.createObjectControlFixedPointValue(null, 100, true, "TODO)");
    participationControls.exampleCapitalNetHistoricControl = componentFactory.createObjectControlCurrency((PgCurrency) null, 100, true, "TODO");
    
    participationControls.exampleReturnOnInvestmentGrossControl = componentFactory.createObjectControlFixedPointValue(null, 100, true, "TODO");
    participationControls.exampleCapitalGrossControl = componentFactory.createObjectControlCurrency((PgCurrency) null, 100, true, "TODO");
    
    participationControls.exampleReturnOnInvestmentPessimisticControl = componentFactory.createObjectControlFixedPointValue(null, 100, true, "TODO");
    participationControls.exampleCapitalPessimisticControl = componentFactory.createObjectControlCurrency((PgCurrency) null, 100, true, "TODO");
    
    participationControls.exampleReturnOnInvestmentGrossCompanyOwnControl = componentFactory.createObjectControlFixedPointValue(null, 100, true, "TODO");
    participationControls.exampleCapitalGrossCompanyOwnControl = componentFactory.createObjectControlCurrency((PgCurrency) null, 100, true, "TODO");
    
    participationControls.returnOnInvestmentReductionNetHistoricControl = componentFactory.createObjectControlFixedPointValue(null, 100, true, "TODO");
    participationControls.exampleCapitalAfterReductionControl = componentFactory.createObjectControlCurrency((PgCurrency) null, 100, true, "TODO");
    
    //
    // Total expense ratios; don't change every year, so copy from last year
    //
    participationControls.totalExpenseRatioControl = componentFactory.createObjectControlFixedPointValue(participation.getTotalExpenseRatio(), 100, true, "Total Expense Ratio (TER)");
    
    return participationControls;
  }

  
  ParticipationControls createParticipationControlsForParticipation(Participation participation) {
    ParticipationControls participationControls = new ParticipationControls();
    
    participationControls.participation = participation;
    
    // Participations at the end of the period.
    FixedPointValue numberOfParticipationsEnd = null;
    if (participation.isSetNumberOfParticipationsEnd()) {
      numberOfParticipationsEnd = participation.getNumberOfParticipationsEnd();
    }
    participationControls.numberOfParticipationsEndControl = componentFactory.createObjectControlFixedPointValue(numberOfParticipationsEnd, 100, true, "number of participations at the end of the period");
    
    // Stock price at the end of the period.
    PgCurrency stockPriceEnd = null;
    InvestmentFund investmentFund = participation.getInvestmentFund();
    if (investmentFund != null) {
      if (annualStatement.isSetPeriodUntil()) {
        LocalDate periodUntil = annualStatement.getPeriodUntil();
        stockPriceEnd = investmentFund.getStockPrice(periodUntil);
      }
    }
    participationControls.stockPriceEndControl = componentFactory.createObjectControlCurrency(stockPriceEnd, 100, true, "stock price at the end of the period");
    
    // Distribution percentage
    FixedPointValue distributionPercentage = null;
    if (participation.isSetDistributionPercentage()) {
      distributionPercentage = participation.getDistributionPercentage();
    }
    participationControls.distributionPercentageControl = componentFactory.createObjectControlFixedPointValue(distributionPercentage, 100, true, "relative size of the participation");
  
    //
    // Participation mutations
    //

    // Participations bought.
    FixedPointValue participationsBought = participation.numberOfParticipationsBought();
    participationControls.numberOfParticipationsBoughtControl = componentFactory.createObjectControlFixedPointValue(participationsBought, 100, true, "Participaties bij (aangekochte participaties)");

    // Participations sold.
    FixedPointValue participationsSold = participation.numberOfParticipationsSold();
    participationControls.numberOfParticipationsSoldControl = componentFactory.createObjectControlFixedPointValue(participationsSold, 100, true, "Participaties af (verkochte participaties)");

    // Participation mutations complete.
    participationControls.participationMutationsCompleteControl = componentFactory.createObjectControlBoolean("Participatiemutaties volledig", participation.isParticipationMutationsComplete(), false,
        "Als dit aangevinkt is, geeft dat aan dat het overzicht van aangekochte en verkochte participaties volledig is.");
    
    //
    // Example capitals
    //
    FixedPointValue exampleReturnOnInvestmentNetHistoric = participation.getExampleReturnOnInvestmentNetHistoric();
    participationControls.exampleReturnOnInvestmentNetHistoricControl = componentFactory.createObjectControlFixedPointValue(exampleReturnOnInvestmentNetHistoric, 100, true, "TODO)");
    PgCurrency exampleCapitalNetHistoric = participation.getExampleCapitalNetHistoric();
    participationControls.exampleCapitalNetHistoricControl = componentFactory.createObjectControlCurrency(exampleCapitalNetHistoric, 100, true, "TODO");
    
    FixedPointValue exampleReturnOnInvestmentGross = participation.getExampleReturnOnInvestmentGross();
    participationControls.exampleReturnOnInvestmentGrossControl = componentFactory.createObjectControlFixedPointValue(exampleReturnOnInvestmentGross, 100, true, "TODO");
    PgCurrency exampleCapitalGross = participation.getExampleCapitalGross();
    participationControls.exampleCapitalGrossControl = componentFactory.createObjectControlCurrency(exampleCapitalGross, 100, true, "TODO");
    
    FixedPointValue exampleReturnOnInvestmentPessimistic = participation.getExampleReturnOnInvestmentPessimistic();
    participationControls.exampleReturnOnInvestmentPessimisticControl = componentFactory.createObjectControlFixedPointValue(exampleReturnOnInvestmentPessimistic, 100, true, "TODO");
    PgCurrency exampleCapitalPessimistic = participation.getExampleCapitalPessimistic();
    participationControls.exampleCapitalPessimisticControl = componentFactory.createObjectControlCurrency(exampleCapitalPessimistic, 100, true, "TODO");
    
    FixedPointValue exampleReturnOnInvestmentGrossCompanyOwn = participation.getExampleReturnOnInvestmentGrossCompanyOwn();
    participationControls.exampleReturnOnInvestmentGrossCompanyOwnControl = componentFactory.createObjectControlFixedPointValue(exampleReturnOnInvestmentGrossCompanyOwn, 100, true, "TODO");
    PgCurrency exampleCapitalGrossCompanyOwn = participation.getExampleCapitalGrossCompanyOwn();
    participationControls.exampleCapitalGrossCompanyOwnControl = componentFactory.createObjectControlCurrency(exampleCapitalGrossCompanyOwn, 100, true, "TODO");
    
    FixedPointValue returnOnInvestmentReductionNetHistoric = participation.getReturnOnInvestmentReductionNetHistoric();
    participationControls.returnOnInvestmentReductionNetHistoricControl = componentFactory.createObjectControlFixedPointValue(returnOnInvestmentReductionNetHistoric, 100, true, "TODO");
    PgCurrency exampleCapitalAfterReduction = participation.getExampleCapitalAfterReduction();
    participationControls.exampleCapitalAfterReductionControl = componentFactory.createObjectControlCurrency(exampleCapitalAfterReduction, 100, true, "TODO");
    
    //
    // Total expense ratios.
    //
    participationControls.totalExpenseRatioControl = componentFactory.createObjectControlFixedPointValue(participation.getTotalExpenseRatio(), 100, true, "Total Expense Ratio (TER)");
    
    return participationControls;
  }

  
  private void fillGUIFromAnnualStatement() {
    LOGGER.fine("=>");
    
    // nothing to fill in if annualStatement is null
    if (annualStatement == null) {
      return;
    }
    
    // Annual Statement date
    if (annualStatement.isSetDate()) {
      annualStatementDateTextField.ocSetValue(annualStatement.getDate());
    }
    
    // Period;
    if (annualStatement.isSetPeriodFrom()) {
      periodFromTextField.ocSetValue(annualStatement.getPeriodFrom());
    }
    if (annualStatement.isSetPeriodUntil()) {
      periodUntilTextField.ocSetValue(annualStatement.getPeriodUntil());
    }
    
    // Premium death risk coverage
    if (annualStatement.isSetPremiumDeathRiskCoverage()) {
      premiumDeathRiskCoverageTextField.ocSetValue(annualStatement.getPremiumDeathRiskCoverage());
    }
    
    // Continuing Costs Insurance Company.
    if (annualStatement.isSetContinuingCostsInsuranceCompany()) {
      continuingCostsInsuranceCompanyTextField.ocSetValue(annualStatement.getContinuingCostsInsuranceCompany());
    }
    
    // Management Costs
    if (annualStatement.isSetManagementCosts()) {
      managementCostsTextField.ocSetValue(annualStatement.getManagementCosts());
    }
    
    // Earned on the participations
    if (annualStatement.isSetEarnedOnTheParticipations()) {
      earnedOnTheParticipationsTextField.ocSetValue(annualStatement.getEarnedOnTheParticipations());
    }
    
    // Costs restitution
    if (annualStatement.isSetCostsRestitution()) {
      costsRestitutionTextField.ocSetValue(annualStatement.getCostsRestitution());
    }
    
    // expectedYearlyCostsIncrease
    if (annualStatement.isSetExpectedYearlyCostsIncrease()) {
      expectedYearlyCostsIncreaseTextField.ocSetValue(annualStatement.getExpectedYearlyCostsIncrease());
    }
    
    
    participationsControls.clear();
    for (Participation participation: annualStatement.getParticipations()) {
      ParticipationControls participationControls = createParticipationControlsForParticipation(participation);
      participationsControls.add(participationControls);
    }
    
    // Example end capital date
    if (annualStatement.isSetExampleCapitalOnEndDate()) {
      exampleCapitalOnEndDateTextField.ocSetValue(annualStatement.getExampleCapitalOnEndDate());
    }
    
    LOGGER.fine("<=");
  }
  
  private void clearAnnualStatement() {
    annualStatement.unsetDate();
    annualStatement.unsetPeriodFrom();
    annualStatement.unsetPeriodUntil();
    annualStatement.unsetPremiumDeathRiskCoverage();
    annualStatement.unsetContinuingCostsInsuranceCompany();
    annualStatement.unsetManagementCosts();
    annualStatement.unsetEarnedOnTheParticipations();
    annualStatement.unsetCostsRestitution();
    annualStatement.unsetExpectedYearlyCostsIncrease();
    annualStatement.getParticipations().clear();
    annualStatement.unsetExampleCapitalOnEndDate();
    
    annualStatement.unsetBuyAndSellCosts();
    annualStatement.unsetCorrections();
    annualStatement.unsetFirstCostsInsuranceCompany();
    annualStatement.unsetMutationCosts();
    annualStatement.unsetNotes();
    annualStatement.unsetPremiumOccupationalDisabilityRiskCoverage();
    annualStatement.unsetPremiumPaid();
    annualStatement.unsetUpkeepPremium();
  }
  
  
  private void fillAnnualStatementFromGUI() {
    LOGGER.fine("=>");
    
    // Annual Statement date
    FlexDate annualStatementDate = annualStatementDateTextField.ocGetValue();
    if (annualStatementDate != null) {
      annualStatement.setDate(annualStatementDate);
    }
    
    // Period;
    LocalDate periodFrom = periodFromTextField.ocGetValue();
    if (periodFrom != null) {
      annualStatement.setPeriodFrom(periodFrom);
    }
    LocalDate periodUntil = periodUntilTextField.ocGetValue();
    if (periodUntil != null) {
      annualStatement.setPeriodUntil(periodUntil);
    }

    // Premium death risk coverage
    PgCurrency premiumDeathRiskCoverage = premiumDeathRiskCoverageTextField.ocGetValue();
    if (premiumDeathRiskCoverage != null) {
      annualStatement.setPremiumDeathRiskCoverage(premiumDeathRiskCoverage);
    }
    
    // Continuing Costs Insurance Company; seems stable, so copy from last year
    PgCurrency continuingCostsInsuranceCompany = continuingCostsInsuranceCompanyTextField.ocGetValue();
    if (continuingCostsInsuranceCompany != null) {
      annualStatement.setContinuingCostsInsuranceCompany(continuingCostsInsuranceCompany);
    }
    
    // Management Costs
    PgCurrency managementCosts = managementCostsTextField.ocGetValue();
    if (managementCosts != null) {
      annualStatement.setManagementCosts(managementCosts);
    }
    
    // Earned on the participations
    PgCurrency earnedOnTheParticipations = earnedOnTheParticipationsTextField.ocGetValue();
    if (earnedOnTheParticipations != null) {
      annualStatement.setEarnedOnTheParticipations(earnedOnTheParticipations);
    }
    
    // Costs restitution
    PgCurrency costsRestitution = costsRestitutionTextField.ocGetValue();
    if (costsRestitution != null) {
      annualStatement.setCostsRestitution(costsRestitution);
    }
    
    // expectedYearlyCostsIncrease.
    FixedPointValue expectedYearlyCostsIncrease = expectedYearlyCostsIncreaseTextField.ocGetValue();
    if (expectedYearlyCostsIncrease != null) {
      annualStatement.setExpectedYearlyCostsIncrease(expectedYearlyCostsIncrease);
    }
    
    // Participations
    for (ParticipationControls participationControls: participationsControls) {
      Participation participation = createParticipationFromParticipationControls(participationControls);
      participation.setAnnualStatement(annualStatement);
      annualStatement.getParticipations().add(participation);
    }
    
    // Save stockprices
    for (ParticipationControls participationControls: participationsControls) {
      if (periodUntil != null) {
        // We have a date
        PgCurrency stockPrice = participationControls.stockPriceEndControl.ocGetValue();
        if (stockPrice != null) {
          // And we have a price
          // Check whether there is already a price for this date, if so it should be equal to the value filled in.
          InvestmentFund investmentFund = participationControls.participation.getInvestmentFund();
          if (investmentFund != null) {
            PgCurrency storedStockPrice = investmentFund.getStockPrice(periodUntil);
            if (storedStockPrice == null) {
              DateRateTuplet dateRateTuplet = TypesFactory.eINSTANCE.createDateRateTuplet();
              dateRateTuplet.setDate(periodUntil);
              dateRateTuplet.setRate(stockPrice);
              investmentFund.getStockPrices().add(dateRateTuplet);
            } else {
              if (!stockPrice.equals(storedStockPrice)) {
                LOGGER.severe("Stockprice differs from what is stored");
              }
            }
          }
        }
      }
    }
    
    
    // Example end capital date.
    LocalDate exampleCapitalOnEndDate = exampleCapitalOnEndDateTextField.ocGetValue();
    if (exampleCapitalOnEndDate != null) {
      annualStatement.setExampleCapitalOnEndDate(exampleCapitalOnEndDate);
    }    
    
    LOGGER.fine("<=");
  }

  private Participation createParticipationFromParticipationControls(ParticipationControls participationControls) {
    Participation participation = InvestmentInsuranceFactory.eINSTANCE.createParticipation();
    
    participation.setInvestmentFund(participationControls.participation.getInvestmentFund());
    
    // Participations at the end of the period.    
    FixedPointValue numberOfParticipationsEnd = participationControls.numberOfParticipationsEndControl.ocGetValue();
    if (numberOfParticipationsEnd != null) {
      participation.setNumberOfParticipationsEnd(numberOfParticipationsEnd);
    }
    // Distribution percentage
    FixedPointValue distributionPercentage = participationControls.distributionPercentageControl.ocGetValue();
    if (distributionPercentage != null) {
      participation.setDistributionPercentage(distributionPercentage);
    }
      
    //
    // Participation mutations
    //

    // Participations bought.
    FixedPointValue participationsBought = participationControls.numberOfParticipationsBoughtControl.ocGetValue();
    if (participationsBought != null) {
      participation.getParticipationMutations().add(participationsBought);
    }

    // Participations sold.
    FixedPointValue participationsSold = participationControls.numberOfParticipationsSoldControl.ocGetValue();
    if (participationsSold != null) {
      participationsSold = participationsSold.changeSign();
      participation.getParticipationMutations().add(participationsSold);
    }

    // Participation mutations complete.
    participation.setParticipationMutationsComplete(participationControls.participationMutationsCompleteControl.ocGetValue());
    
    //
    // Example capitals
    //
    FixedPointValue exampleReturnOnInvestmentNetHistoric = participationControls.exampleReturnOnInvestmentNetHistoricControl.ocGetValue();
    if (exampleReturnOnInvestmentNetHistoric != null) {
      participation.setExampleReturnOnInvestmentNetHistoric(exampleReturnOnInvestmentNetHistoric);
    }
    PgCurrency exampleCapitalNetHistoric = participationControls.exampleCapitalNetHistoricControl.ocGetValue();
    if (exampleCapitalNetHistoric != null) {
      participation.setExampleCapitalNetHistoric(exampleCapitalNetHistoric);
    }
    
    FixedPointValue exampleReturnOnInvestmentGross = participationControls.exampleReturnOnInvestmentGrossControl.ocGetValue();
    if (exampleReturnOnInvestmentGross != null) {
      participation.setExampleReturnOnInvestmentGross(exampleReturnOnInvestmentGross);
    }
    PgCurrency exampleCapitalGross = participationControls.exampleCapitalGrossControl.ocGetValue();
    if (exampleCapitalGross != null) {
      participation.setExampleCapitalGross(exampleCapitalGross);
    }
    
    FixedPointValue exampleReturnOnInvestmentPessimistic = participationControls.exampleReturnOnInvestmentPessimisticControl.ocGetValue();
    if (exampleReturnOnInvestmentPessimistic != null) {
      participation.setExampleReturnOnInvestmentPessimistic(exampleReturnOnInvestmentPessimistic);
    }
    PgCurrency exampleCapitalPessimistic = participationControls.exampleCapitalPessimisticControl.ocGetValue();
    if (exampleCapitalPessimistic != null) {
      participation.setExampleCapitalPessimistic(exampleCapitalPessimistic);
    }
    
    FixedPointValue exampleReturnOnInvestmentGrossCompanyOwn = participationControls.exampleReturnOnInvestmentGrossCompanyOwnControl.ocGetValue();
    if (exampleReturnOnInvestmentGrossCompanyOwn != null) {
      participation.setExampleReturnOnInvestmentGrossCompanyOwn(exampleReturnOnInvestmentGrossCompanyOwn);
    }
    PgCurrency exampleCapitalGrossCompanyOwn = participationControls.exampleCapitalGrossCompanyOwnControl.ocGetValue();
    if (exampleCapitalGrossCompanyOwn != null) {
      participation.setExampleCapitalGrossCompanyOwn(exampleCapitalGrossCompanyOwn);
    }
    
    FixedPointValue returnOnInvestmentReductionNetHistoric = participationControls.returnOnInvestmentReductionNetHistoricControl.ocGetValue();
    if (returnOnInvestmentReductionNetHistoric != null) {
      participation.setReturnOnInvestmentReductionNetHistoric(returnOnInvestmentReductionNetHistoric);
    }
    PgCurrency exampleCapitalAfterReduction = participationControls.exampleCapitalAfterReductionControl.ocGetValue();
    if (exampleCapitalAfterReduction != null) {
      participation.setExampleCapitalAfterReduction(exampleCapitalAfterReduction);
    }
    
    //
    // Total expense ratios.
    //
    FixedPointValue totalExpenseRatio = participationControls.totalExpenseRatioControl.ocGetValue();
    if (totalExpenseRatio != null) {
      participation.setTotalExpenseRatio(totalExpenseRatio);
    }
    
    return participation;
  }
}

class ParticipationControls {
  Participation participation;
  
  ObjectControlFixedPointValue numberOfParticipationsEndControl;
  ObjectControlCurrency stockPriceEndControl;
  ObjectControlFixedPointValue distributionPercentageControl;
  
  ObjectControlFixedPointValue numberOfParticipationsBoughtControl;
  ObjectControlFixedPointValue numberOfParticipationsSoldControl;
  ObjectControlBoolean participationMutationsCompleteControl;
  
  ObjectControlFixedPointValue exampleReturnOnInvestmentNetHistoricControl;
  ObjectControlCurrency exampleCapitalNetHistoricControl;
  
  ObjectControlFixedPointValue exampleReturnOnInvestmentGrossControl;
  ObjectControlCurrency exampleCapitalGrossControl;
  
  ObjectControlFixedPointValue exampleReturnOnInvestmentPessimisticControl;
  ObjectControlCurrency exampleCapitalPessimisticControl;
  
  ObjectControlFixedPointValue exampleReturnOnInvestmentGrossCompanyOwnControl;
  ObjectControlCurrency exampleCapitalGrossCompanyOwnControl;
  
  ObjectControlFixedPointValue returnOnInvestmentReductionNetHistoricControl;
  ObjectControlCurrency exampleCapitalAfterReductionControl;
  
  ObjectControlFixedPointValue standardFundReturnOnInvestmentControl;
  ObjectControlCurrency exampleCapitalStandardFundReturnOnInvestmentControl;
    
  ObjectControlFixedPointValue totalExpenseRatioControl;
}
