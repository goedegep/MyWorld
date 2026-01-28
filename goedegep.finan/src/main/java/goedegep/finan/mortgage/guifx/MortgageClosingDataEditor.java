package goedegep.finan.mortgage.guifx;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import goedegep.finan.mortgage.model.Mortgage;
import goedegep.finan.mortgage.model.MortgageFactory;
import goedegep.finan.mortgage.model.MortgageType;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.rolodex.model.Address;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MortgageClosingDataEditor extends JfxStage {
  /*
   * Strategy:
   * For a new mortgage, all GUI components are set to default values. Upon save, a new Mortgage is created and filled from the GUI components.
   * When a mortgage is edited, all GUI components are filled from the mortgage. Upon save, the mortgage is filled from the GUI components. 
   */
  
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(MortgageClosingDataEditor.class.getName());
  private static final PgCurrencyFormat CF = new PgCurrencyFormat();
  private static final SimpleDateFormat  DF = new SimpleDateFormat("dd-MM-yyyy");
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();
  private static final String WINDOW_TITLE   = "Mortgage closing data editor";
  
  private Mortgage mortgage;
  private List<Mortgage> mortgages;
  private ComponentFactoryFx componentFactory;
  private boolean editMortgage;
  
  private TextField lenderNameTextField;
  private TextField lenderStreetTextField;
  private TextField lenderHouseNumberTextField;
  private TextField lenderPostalCodeTextField;
  private TextField lenderCityTextField;
  private TextField lenderSigner1TextField;
  private TextField lenderSigner2TextField;
  private TextField lenderBankAccountNrTextField;
  private TextField lenderBankAccountNumberNameAndAddressTextField;
  private TextField borrowerTitleAndNameTextField;
  private TextField borrowerStreetTextField;
  private TextField borrowerHouseNumberTextField;
  private TextField borrowerPostalCodeTextField;
  private TextField borrowerCityTextField;
  private TextField mortgageNameTextField;
  private TextField mortgageNumberTextField;
  private ComboBox<MortgageTypeWrapper> mortgageTypeComboBox;
  private TextField startingDateTextField;
  private TextField durationTextField;
  private TextField firstPaymentDateTextField;
  private TextField principalTextField;
  private TextField interestRateTextField;
  private TextField fixedInterestPeriodTextField;
  

  /**
   * Constructor to edit an existing mortgage.
   * 
   * @param customization the window customization.
   * @param mortgage the mortgage of which the values are to be edited.
   */
  public MortgageClosingDataEditor(CustomizationFx customization, Mortgage mortgage) {
    super(customization, WINDOW_TITLE);
    
    this.mortgage = mortgage;
    editMortgage = true;
    componentFactory = getComponentFactory();
    
    createGUI();
    
    clearGUI();
    fillGUIFromMortgage();
    
    show();
  }

  /**
   * Constructor to create a new mortgage.
   * 
   * @param customization the window customization.
   * @param mortgages the list of mortgages to which the new mortgage is to be added.
   */
  public MortgageClosingDataEditor(CustomizationFx customization, List<Mortgage> mortgages) {
    super(customization, WINDOW_TITLE);
    
    this.mortgages = mortgages;
    editMortgage = false;
    componentFactory = getComponentFactory();
    
    createGUI();
    
    clearGUI();
    
    show();
  }

  /**
   * Create the GUI.
   */
  private void createGUI() {
    BorderPane rootLayout = new BorderPane();
    VBox mainPane = componentFactory.createVBox();
    
    GridPane gridPane = componentFactory.createGridPane(12.0, 10.0, 12.0);
    Label label;
    
    /*
     * Top left - Lender details
     */
    
    label = componentFactory.createLabel("Lender");
    label.setStyle("-fx-font-weight: bold;");
    gridPane.add(label, 0, 0);

    label = componentFactory.createLabel("Name:");
    gridPane.add(label, 0, 1);    
    lenderNameTextField = componentFactory.createTextField(300, "Name of the lender");
    gridPane.add(lenderNameTextField, 1, 1);    

    label = componentFactory.createLabel("Street and housenumber:");
    gridPane.add(label, 0, 2);
    lenderStreetTextField = componentFactory.createTextField(300, "Streetname of the address of the lender");
    gridPane.add(lenderStreetTextField, 1, 2);
    lenderHouseNumberTextField = componentFactory.createTextField(80, "Housenumber of the address of the lender");
    gridPane.add(lenderHouseNumberTextField, 2, 2);

    label = componentFactory.createLabel("Postal code and city:");
    gridPane.add(label, 0, 3);
    lenderPostalCodeTextField = componentFactory.createTextField(150, "Postal code of the address of the lender");
    gridPane.add(lenderPostalCodeTextField, 1, 3);
    lenderCityTextField = componentFactory.createTextField(300, "City of the address of the lender");
    gridPane.add(lenderCityTextField, 2, 3);

    label = componentFactory.createLabel("First signer:");
    gridPane.add(label, 0, 4);
    lenderSigner1TextField = componentFactory.createTextField(300, "First person to sign on behalf of the lender");
    gridPane.add(lenderSigner1TextField, 1, 4);

    label = componentFactory.createLabel("Second signer:");
    gridPane.add(label, 0, 5);
    lenderSigner2TextField = componentFactory.createTextField(300, "Second person to sign on behalf of the lender");
    gridPane.add(lenderSigner2TextField, 1, 5);

    label = componentFactory.createLabel("Account number:");
    gridPane.add(label, 0, 6);
    lenderBankAccountNrTextField = componentFactory.createTextField(300, "Bank account number of the lender");
    gridPane.add(lenderBankAccountNrTextField, 1, 6);

    label = componentFactory.createLabel("Name and address:");
    gridPane.add(label, 0, 7);
    lenderBankAccountNumberNameAndAddressTextField = componentFactory.createTextField(300, "Address related to the bank account number of the lender");
    gridPane.add(lenderBankAccountNumberNameAndAddressTextField, 1, 7);
    
  
    /*
     * Top right - Borrower details
     */
    
    label = componentFactory.createLabel("Borrower");
    label.setStyle("-fx-font-weight: bold;");
    gridPane.add(label, 4, 0);

    label = componentFactory.createLabel("Name:");
    gridPane.add(label, 4, 1);
    borrowerTitleAndNameTextField = componentFactory.createTextField(300, "Title and name of the borrower");
    gridPane.add(borrowerTitleAndNameTextField, 5, 1);    

    label = componentFactory.createLabel("Street and housenumber:");
    gridPane.add(label, 4, 2);
    borrowerStreetTextField = componentFactory.createTextField(300, "Streetname of the address of the borrower");
    gridPane.add(borrowerStreetTextField, 5, 2);
    borrowerHouseNumberTextField = componentFactory.createTextField(80, "Housenumber of the address of the borrower");
    gridPane.add(borrowerHouseNumberTextField, 6, 2);

    label = componentFactory.createLabel("Postal code and city:");
    gridPane.add(label, 4, 3);
    borrowerPostalCodeTextField = componentFactory.createTextField(150, "Postal code of the address of the borrower");
    gridPane.add(borrowerPostalCodeTextField, 5, 3);
    borrowerCityTextField = componentFactory.createTextField(300, "City of the address of the borrower");
    gridPane.add(borrowerCityTextField, 6, 3);

    /*
     * Bottom - mortgage details
     */
    
    label = componentFactory.createLabel("Mortgage details");
    label.setStyle("-fx-font-weight: bold;");
    gridPane.add(label, 0, 8);

    label = componentFactory.createLabel("Name:");
    gridPane.add(label, 0, 9);    
    mortgageNameTextField = componentFactory.createTextField(300, "Name of the mortgage");
    gridPane.add(mortgageNameTextField, 1, 9);    

    label = componentFactory.createLabel("Number:");
    gridPane.add(label, 3, 9);    
    mortgageNumberTextField = componentFactory.createTextField(300, "The mortgage identification number");
    gridPane.add(mortgageNumberTextField, 4, 9);    

    label = componentFactory.createLabel("Mortgage type:");
    gridPane.add(label, 0, 10);
    mortgageTypeComboBox = componentFactory.createComboBox(MortgageTypeWrapper.values());
    gridPane.add(mortgageTypeComboBox, 1, 10);    

    label = componentFactory.createLabel("Starting date:");
    gridPane.add(label, 3, 10);    
    startingDateTextField = componentFactory.createTextField(300, "Starting date of the mortgage");
    gridPane.add(startingDateTextField, 4, 10);    

    label = componentFactory.createLabel("Duration (months):");
    gridPane.add(label, 0, 11);
    durationTextField = componentFactory.createTextField(300, "Duration (in months) of the mortgage");
    gridPane.add(durationTextField, 1, 11);    

    label = componentFactory.createLabel("First payment date:");
    gridPane.add(label, 3, 11);    
    firstPaymentDateTextField = componentFactory.createTextField(300, "Date at which the first payment has to be paid");
    gridPane.add(firstPaymentDateTextField, 4, 11);    

    label = componentFactory.createLabel("Principal:");
    gridPane.add(label, 0, 12);
    principalTextField = componentFactory.createTextField(300, "Principal of the mortgage");
    gridPane.add(principalTextField, 1, 12);    

    label = componentFactory.createLabel("Interest rate:");
    gridPane.add(label, 3, 12);    
    interestRateTextField = componentFactory.createTextField(300, "Interest rate");
    gridPane.add(interestRateTextField, 4, 12);    

    label = componentFactory.createLabel("Fixed interest period:");
    gridPane.add(label, 0, 13);
    fixedInterestPeriodTextField = componentFactory.createTextField(300, "Fixed interest period");
    gridPane.add(fixedInterestPeriodTextField, 1, 13);
    
    mainPane.getChildren().add(gridPane);
    
    mainPane.getChildren().add(createButtonsPane());
    
    rootLayout.setCenter(mainPane);

    setScene(new Scene(rootLayout));
  }

  private void clearGUI() {
    // Lender details
    lenderNameTextField.setText(null);

    lenderStreetTextField.setText(null);
    lenderHouseNumberTextField.setText(null);
    lenderPostalCodeTextField.setText(null);
    lenderCityTextField.setText(null);

    lenderSigner1TextField.setText(null);

    lenderSigner2TextField.setText(null);

    lenderBankAccountNrTextField.setText(null);

    lenderBankAccountNumberNameAndAddressTextField.setText(null);

    // borrower details
    borrowerTitleAndNameTextField.setText(null);

    borrowerStreetTextField.setText(null);
    borrowerHouseNumberTextField.setText(null);
    borrowerPostalCodeTextField.setText(null);
    borrowerCityTextField.setText(null);

    // mortgage details
    mortgageNameTextField.setText(null);

    mortgageNumberTextField.setText(null);

    mortgageTypeComboBox.setValue(null);

    startingDateTextField.setText(null);

    durationTextField.setText(null);

    firstPaymentDateTextField.setText(null);

    principalTextField.setText(null);

    interestRateTextField.setText(null);

    fixedInterestPeriodTextField.setText(null);
  }

  private void fillGUIFromMortgage() {
    // Lender details
    if (mortgage.isSetLender()) {
      lenderNameTextField.setText(mortgage.getLender());
    }
    
    if (mortgage.isSetLenderAddress()) {
      Address address = mortgage.getLenderAddress();
      lenderStreetTextField.setText(address.getStreetName());
      lenderHouseNumberTextField.setText(address.getHouseNumber().toString());
      lenderPostalCodeTextField.setText(address.getPostalCode());
      lenderCityTextField.setText(address.getCity().getCityName());
    }
    
    if (mortgage.isSetLenderSigner1()) {
      lenderSigner1TextField.setText(mortgage.getLenderSigner1());
    }
    
    if (mortgage.isSetLenderSigner2()) {
      lenderSigner2TextField.setText(mortgage.getLenderSigner2());
    }
    
    if (mortgage.isSetLenderBankAccountNumber()) {
      lenderBankAccountNrTextField.setText(mortgage.getLenderBankAccountNumber());
    }
    
    if (mortgage.isSetLenderBankAccountNumberNameAndAddress()) {
      lenderBankAccountNumberNameAndAddressTextField.setText(mortgage.getLenderBankAccountNumberNameAndAddress());
    }
    
    // borrower details
    if (mortgage.isSetBorrowerTitleAndName()) {
      borrowerTitleAndNameTextField.setText(mortgage.getBorrowerTitleAndName());
    }
    
    if (mortgage.isSetBorrowerAddress()) {
      Address address = mortgage.getBorrowerAddress();
      borrowerStreetTextField.setText(address.getStreetName());
      borrowerHouseNumberTextField.setText(address.getHouseNumber().toString());
      borrowerPostalCodeTextField.setText(address.getPostalCode());
      borrowerCityTextField.setText(address.getCity().getCityName());
    }
    
    // mortgage details
    if (mortgage.isSetMortgageName()) {
      mortgageNameTextField.setText(mortgage.getMortgageName());
    }
    
    if (mortgage.isSetMortgageNumber()) {
      mortgageNumberTextField.setText(mortgage.getMortgageNumber());
    }
    
    if (mortgage.isSetMortgageType()) {
      mortgageTypeComboBox.setValue(MortgageTypeWrapper.getMortgageTypeWrapper(mortgage.getMortgageType()));
    }
    
    if (mortgage.isSetStartingDate()) {
      startingDateTextField.setText(DF.format(mortgage.getStartingDate()));
    }
    
    if (mortgage.isSetDuration()) {
      durationTextField.setText(String.valueOf(mortgage.getDuration()));
    }
    
    if (mortgage.isSetFirstPaymentDate()) {
      firstPaymentDateTextField.setText(DF.format(mortgage.getFirstPaymentDate()));
    }
    
    if (mortgage.isSetPrincipal()) {
      principalTextField.setText(CF.format(mortgage.getPrincipal()));
    }
    
    if (mortgage.isSetInterestRate()) {
      interestRateTextField.setText(FPVF.format(mortgage.getInterestRate()));
    }
    
    if (mortgage.isSetFixedInterestPeriod()) {
      fixedInterestPeriodTextField.setText(String.valueOf(mortgage.getFixedInterestPeriod()));
    }
  }

  private void fillMortgageFromGUI() {
    String text;
    
    // Lender details
    text = getTextFieldText(lenderNameTextField);
    if (text != null) {
      mortgage.setLender(text);
    }
    
//    if (mortgage.isSetLenderAddress()) {
//      Address address = mortgage.getLenderAddress();
//      lenderStreetTextField.setText(address.getStreetName());
//      lenderHouseNumberTextField.setText(address.getHouseNumber().toString());
//      lenderPostalCodeTextField.setText(address.getPostalCode());
//      lenderCityTextField.setText(address.getCity().getCityName());
//    }
    
    text = getTextFieldText(lenderSigner1TextField);
    if (text != null) {
      mortgage.setLenderSigner1(text);
    }
    
    text = getTextFieldText(lenderSigner2TextField);
    if (text != null) {
      mortgage.setLenderSigner2(text);
    }
    
    text = getTextFieldText(lenderBankAccountNrTextField);
    if (text != null) {
      mortgage.setLenderBankAccountNumber(text);
    }
    
    text = getTextFieldText(lenderBankAccountNumberNameAndAddressTextField);
    if (text != null) {
      mortgage.setLenderBankAccountNumberNameAndAddress(text);
    }
    
    // borrower details
    text = getTextFieldText(borrowerTitleAndNameTextField);
    if (text != null) {
      mortgage.setBorrowerTitleAndName(text);
    }
    
//    if (mortgage.isSetBorrowerAddress()) {
//      Address address = mortgage.getBorrowerAddress();
//      borrowerStreetTextField.setText(address.getStreetName());
//      borrowerHouseNumberTextField.setText(address.getHouseNumber().toString());
//      borrowerPostalCodeTextField.setText(address.getPostalCode());
//      borrowerCityTextField.setText(address.getCity().getCityName());
//    }
    
    // mortgage details
    text = getTextFieldText(mortgageNameTextField);
    if (text != null) {
      mortgage.setMortgageName(text);
    }
    
    text = getTextFieldText(mortgageNumberTextField);
    if (text != null) {
      mortgage.setMortgageNumber(text);
    }
    
    MortgageTypeWrapper mortgageTypeWrapper = mortgageTypeComboBox.getValue();
    if (mortgageTypeWrapper != null) {
      MortgageType mortgageType = mortgageTypeWrapper.getMortgageType();
      mortgage.setMortgageType(mortgageType);
    }
    
    text = getTextFieldText(startingDateTextField);
    if (text != null) {
      try {
        Date date = DF.parse(text);
        mortgage.setStartingDate(date);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    
    text = getTextFieldText(durationTextField);
    if (text != null) {
      mortgage.setDuration(Integer.valueOf(text));
    }
    
    text = getTextFieldText(firstPaymentDateTextField);
    if (text != null) {
      try {
        Date date = DF.parse(text);
        mortgage.setFirstPaymentDate(date);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    
    text = getTextFieldText(principalTextField);
    if (text != null) {
      try {
        PgCurrency money = CF.parse(text);
        mortgage.setPrincipal(money);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    
    text = getTextFieldText(interestRateTextField);
    if (text != null) {
      try {
        FixedPointValue fixedPointValue = FPVF.parse(text);
        mortgage.setInterestRate(fixedPointValue);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    
    text = getTextFieldText(fixedInterestPeriodTextField);
    if (text != null) {
      mortgage.setFixedInterestPeriod(Integer.valueOf(text));
    }
  }
  
  private String getTextFieldText(TextField textField) {
    String text = textField.getText();
    
    if (text != null) {
      text = text.trim();
      if (text.isEmpty()) {
        text = null;
      }
    }
    
    return text;
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
    
    button = componentFactory.createButton("Save changes", "Save the changes");
    button.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        saveMortgageData();
      }
      
    });
    buttonPane.getChildren().add(button);

    return buttonPane;
  }
  
  private void saveMortgageData() {
    if (!editMortgage) {
      mortgage = MortgageFactory.eINSTANCE.createMortgage();
      mortgages.add(mortgage);
    }
    
    fillMortgageFromGUI();
    
    editMortgage = true;
  }
  
  private void closeWindow() {
    this.close();
  }
}
