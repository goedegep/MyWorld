package goedegep.finan.investmentinsurances.app.guifx;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import goedegep.finan.investmentinsurance.model.InvestmentInsurance;
import goedegep.finan.investmentinsurance.model.InvestmentInsurancesData;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.objectcontrols.ObjectControlCurrency;
import goedegep.jfx.objectcontrols.ObjectControlLocalDate;
import goedegep.rolodex.model.Person;
import goedegep.util.money.PgCurrency;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * This class is a window which shows the details of an investment insurance.
 */
public class InvestmentInsuranceWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(InvestmentInsuranceWindow.class.getName());
  private static final String WINDOW_TITLE   = "Investment insurance information";

  private InvestmentInsurancesData investmentInsurancesData;
  private InvestmentInsurance investmentInsurance;
  private ComponentFactoryFx componentFactory;
  
  private ComboBox<InvestmentInsuranceWrapper> investmentInsuranceComboBox;
  private ObjectControlLocalDate startingDateTextField;
  private ObjectControlCurrency insuredBenefitOnDeathTextField;
  private ObjectControlCurrency insuredBenefitOnDeathInEurosTextField;
  private ObjectControlCurrency premiumTextField;
  private ObjectControlCurrency premiumInEurosTextField;
  private TextField policyHolderTextField;

  public InvestmentInsuranceWindow(CustomizationFx customization, InvestmentInsurancesData investmentInsurancesData) {
    super(WINDOW_TITLE, customization);

    this.investmentInsurancesData = investmentInsurancesData;
    
    componentFactory = getComponentFactory();

    createGUI();
    
    if (!investmentInsuranceComboBox.getItems().isEmpty()) {
      LOGGER.fine("=> Going to select first Investment Insurance");
      investmentInsuranceComboBox.setValue(investmentInsuranceComboBox.getItems().get(0));
      handleInvestmentInsuranceChanged();
    }
    
    show();
  }
  
  private void createGUI() {
    BorderPane rootLayout = new BorderPane();
    
    GridPane gridPane = componentFactory.createGridPane(12.0, 10.0, 12.0);
    
    int row = 0;
    Label label;
    
    // Investment insurance selection
    label = componentFactory.createLabel("Beleggingsverzekering:");
    gridPane.add(label, 0, row);
    
    List<InvestmentInsuranceWrapper> investmentInsuranceWrappers = InvestmentInsuranceWrapper.createInvestmentInsuranceWrapperList(investmentInsurancesData.getInvestmentInsurances());
    investmentInsuranceComboBox = componentFactory.createComboBox(investmentInsuranceWrappers);
    investmentInsuranceComboBox.setOnAction(e -> handleInvestmentInsuranceChanged());
    gridPane.add(investmentInsuranceComboBox, 1, row);
    
    row++;
    
    // Starting date
    label = componentFactory.createLabel("Ingangsdatum:");
    gridPane.add(label, 0, row);

    startingDateTextField = componentFactory.createObjectControlLocalDate((LocalDate) null, 100, false, "Ingangsdatum van de verzekering");
    gridPane.add(startingDateTextField, 1, row);
    
    row++;
    
    // Policyholder
    label = componentFactory.createLabel("Verzekerde:");
    gridPane.add(label, 0, row);

    policyHolderTextField = componentFactory.createTextField(250, "Naam van de verzekerde");
    gridPane.add(policyHolderTextField, 1, row);
    
    row++;
    
    // Insured benefit on death
    label = componentFactory.createLabel("Verzekerde uitkering A:");
    gridPane.add(label, 0, row);

    insuredBenefitOnDeathTextField = componentFactory.createObjectControlCurrency((PgCurrency) null, 100, true, "Verzekerde uitkering bij overlijden (in oorspronkelijke munteenheid)");
    gridPane.add(insuredBenefitOnDeathTextField, 1, row);
    insuredBenefitOnDeathInEurosTextField = componentFactory.createObjectControlCurrency((PgCurrency) null, 100, true, "Verzekerde uitkering bij overlijden (in euros)");
    gridPane.add(insuredBenefitOnDeathInEurosTextField, 2, row);
    row++;
    TextArea insuredBenefitOnDeathInfoTextField = componentFactory.createTextArea(
        "De verzekerde uitkering is het uit te keren bedrag bij overlijden van de verzekerde en betaalbaar terstond na overlijden.\n" + 
        "Indien de guldenswaarde vermeerderd met 10% daarvan, van de ten behoeve van de verzekeringnemer uitstaande participaties" +
        "in de door de verzekeringnemer aangewezen fondsen op de overlijdensdatum van de verzekerde hoger is dan het in de" +
        "vorige volzin genoemde bedrag, wordt dit hogere bedrag uitgekeerd.");
    insuredBenefitOnDeathInfoTextField.setWrapText(true);
    gridPane.add(insuredBenefitOnDeathInfoTextField, 1, row, 2, 1);
    
    row++;
    
    // Premium
    label = componentFactory.createLabel("Premie:");
    gridPane.add(label, 0, row);

    premiumTextField = componentFactory.createObjectControlCurrency((PgCurrency) null, 100, true, "Betaalde premie (in oorspronkelijke munteenheid))");
    gridPane.add(premiumTextField, 1, row);
    premiumInEurosTextField = componentFactory.createObjectControlCurrency((PgCurrency) null, 100, true, "Betaalde premie (in euros))");
    gridPane.add(premiumInEurosTextField, 2, row);
    
    
    
    rootLayout.setCenter(gridPane);

    setScene(new Scene(rootLayout));
  }

  private void handleInvestmentInsuranceChanged() {
    InvestmentInsuranceWrapper investmentInsuranceWrapper = investmentInsuranceComboBox.getValue();
    if (investmentInsuranceWrapper != null) {
      investmentInsurance = investmentInsuranceWrapper.getInvestmentInsurance();
    } else {
      investmentInsurance = null;
    }
    
    clearControls();
    fillControlsFromInvestmentInsurance();
  }

  private void clearControls() {
    startingDateTextField.setText(null);
    insuredBenefitOnDeathTextField.setText(null);
    insuredBenefitOnDeathInEurosTextField.setText(null);
    premiumTextField.setText(null);
    premiumInEurosTextField.setText(null);
    policyHolderTextField.setText(null);
  }

  private void fillControlsFromInvestmentInsurance() {
    if (investmentInsurance == null) {
      return;
    }

    // Starting date
    if (investmentInsurance.isSetStartingDate()) {
      startingDateTextField.ocSetValue(investmentInsurance.getStartingDate());
    }

    // Insured benefit on death
    if (investmentInsurance.isSetInsuredBenefitOnDeath()) {
      insuredBenefitOnDeathTextField.ocSetValue(investmentInsurance.getInsuredBenefitOnDeath());
      insuredBenefitOnDeathInEurosTextField.ocSetValue(investmentInsurance.getInsuredBenefitOnDeath().certifyCurrency(PgCurrency.EURO));
    }

    // Premium
    if (investmentInsurance.isSetPremium()) {
      premiumTextField.ocSetValue(investmentInsurance.getPremium());
      premiumInEurosTextField.ocSetValue(investmentInsurance.getPremium().certifyCurrency(PgCurrency.EURO));
    }

    // Policyholder
    if (investmentInsurance.isSetPolicyHolder()) {
      Person person = investmentInsurance.getPolicyHolder();
      policyHolderTextField.setText(person.getName());
    }
  }
}
