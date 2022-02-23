package goedegep.finan.mortgage.app.guifx;

import java.text.SimpleDateFormat;

import goedegep.finan.mortgage.MortgageCalculator;
import goedegep.finan.mortgage.model.Mortgage;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrencyFormat;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class MortgageInfoPanel extends GridPane {
  private static final SimpleDateFormat  DF = new SimpleDateFormat("dd-MM-yyyy");
  private static final PgCurrencyFormat  CF = new PgCurrencyFormat(0, false, false, false);
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();
    
  private ComponentFactoryFx componentFactory;
  
  private TextField lenderTextField = null;
  private TextField naamTextField = null;
  private TextField hypotheekNrTextField = null;
  private TextField typeTextField = null;
  private TextField looptijdTextField = null;
  private TextField hoofdsomTextField = null;
  private TextField rentepercentageTextField = null;
  private TextField rentevastPeriodeTextField = null;
  private TextField ingangsdatumTextField = null;
  private TextField huidigeSchuldTextField = null;
  
  public MortgageInfoPanel(CustomizationFx customization, MortgageCalculator mortgageCalculator) {
    componentFactory = customization.getComponentFactoryFx();
    
    createPanel();
    setHypotheek(mortgageCalculator);
  }

  public void setHypotheek(MortgageCalculator mortgageCalculator) {
    
    if (mortgageCalculator != null) {
      Mortgage mortgage = mortgageCalculator.getMortgage();

      lenderTextField.setText(mortgage.getLender());
      naamTextField.setText(mortgage.getMortgageName());
      hypotheekNrTextField.setText(mortgage.getMortgageNumber());
      if (mortgage.isSetMortgageType()) {
        MortgageTypeWrapper mortgageTypeWrapper = MortgageTypeWrapper.getMortgageTypeWrapper(mortgage.getMortgageType());
        typeTextField.setText(mortgageTypeWrapper.toString());
      }
      looptijdTextField.setText(Integer.toString(mortgage.getDuration()));
      hoofdsomTextField.setText(CF.format(mortgage.getPrincipal()));
      rentepercentageTextField.setText(FPVF.format(mortgage.getInterestRate()));
      if (mortgage.getFixedInterestPeriod() % 12 == 0) {
        rentevastPeriodeTextField.setText(Integer.toString(mortgage.getFixedInterestPeriod() / 12) + " years");
      } else {
        rentevastPeriodeTextField.setText(Integer.toString(mortgage.getFixedInterestPeriod()) + " months");
      }
      if (mortgage.isSetStartingDate()) {
        ingangsdatumTextField.setText(DF.format(mortgage.getStartingDate()));
      } else {
        ingangsdatumTextField.setText(null);
      }
      huidigeSchuldTextField.setText(CF.format(mortgageCalculator.getCurrentDebt()));
    } else {
      lenderTextField.setText(null);
      naamTextField.setText(null);
      hypotheekNrTextField.setText(null);
      typeTextField.setText(null);
      looptijdTextField.setText(null);
      hoofdsomTextField.setText(null);
      rentepercentageTextField.setText(null);
      rentevastPeriodeTextField.setText(null);
      ingangsdatumTextField.setText(null);
      huidigeSchuldTextField.setText(null);
    }
  }
  
  private void createPanel() {
    componentFactory.customizePane(this);
    this.setHgap(12.0);
    this.setPadding(new Insets(12.0));
    
    Label label;
    
    label = componentFactory.createLabel("Lender:");
    add(label, 0, 0);        
    lenderTextField = componentFactory.createTextField(null, 200, null);
    add(lenderTextField, 1, 0);
    
    label = new Label("     ");
    add(label, 2, 0);        
    
    label = componentFactory.createLabel("Mortgage name:");
    add(label, 3, 0);        
    naamTextField = componentFactory.createTextField(null, 200, null);
    add(naamTextField, 4, 0);
    
    label = componentFactory.createLabel("Mortgage number:");
    add(label, 0, 1);        
    hypotheekNrTextField = componentFactory.createTextField(null, 200, null);
    add(hypotheekNrTextField, 1, 1);
    
    label = componentFactory.createLabel("Mortgage type:");
    add(label, 3, 1);        
    typeTextField = componentFactory.createTextField(null, 200, null);
    add(typeTextField, 4, 1);
    
    label = componentFactory.createLabel("Duration (months):");
    add(label, 0, 2);        
    looptijdTextField = componentFactory.createTextField(null, 200, null);
    add(looptijdTextField, 1, 2);
    
    label = componentFactory.createLabel("Principal:");
    add(label, 3, 2);        
    hoofdsomTextField = componentFactory.createTextField(null, 200, null);
    add(hoofdsomTextField, 4, 2);
    
    label = componentFactory.createLabel("Interest rate:");
    add(label, 0, 3);        
    rentepercentageTextField = componentFactory.createTextField(null, 200, null);
    add(rentepercentageTextField, 1, 3);
    
    label = componentFactory.createLabel("Fixed interest period:");
    add(label, 3, 3);        
    rentevastPeriodeTextField = componentFactory.createTextField(null, 200, null);
    add(rentevastPeriodeTextField, 4, 3);
    
    label = componentFactory.createLabel("Starting date:");
    add(label, 0, 4);        
    ingangsdatumTextField = componentFactory.createTextField(null, 200, null);
    add(ingangsdatumTextField, 1, 4);
    
    label = componentFactory.createLabel("Current debt:");
    add(label, 3, 4);        
    huidigeSchuldTextField = componentFactory.createTextField(null, 200, null);
    add(huidigeSchuldTextField, 4, 4);
  }  
}
