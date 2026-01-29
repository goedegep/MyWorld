package goedegep.finan.investmentinsurances.gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import goedegep.finan.investmentinsurance.model.InvestmentInsurance;
import goedegep.finan.investmentinsurance.model.InvestmentInsurancesData;
import goedegep.finan.investmentinsurance.model.impl.InvestmentInsuranceImpl;
import goedegep.rolodex.model.Person;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class InvestmentInsurancesOverviewTable extends TableView<InvestmentInsurance> {
  private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private static final PgCurrencyFormat CF = new PgCurrencyFormat(12);
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();
  
  public InvestmentInsurancesOverviewTable(final InvestmentInsurancesData investmentInsurancesData) {
    List<InvestmentInsurance> investmentInsurances = investmentInsurancesData.getInvestmentInsurances();
    ObservableList<InvestmentInsurance> items = FXCollections.observableArrayList();
    
    // Company name
    TableColumn<InvestmentInsurance, Object> tableColumn = new TableColumn<>("Company name");
    tableColumn.setCellValueFactory(arg0 -> {
      InvestmentInsurance investmentInsurance = arg0.getValue();
      String value = null;
      if (investmentInsurance instanceof InvestmentInsurancesTotal) {
        value = "Total";
      } else {
        value = investmentInsurance.getInsuranceCompany().getName();
      }
      
      return new SimpleObjectProperty<>(value);
    });
    tableColumn.setMinWidth(180);
    getColumns().add(tableColumn);
    
    // Policy number
    tableColumn = new TableColumn<>("Policy number");
    tableColumn.setCellValueFactory(arg0 -> {
      InvestmentInsurance investmentInsurance = arg0.getValue();
      String value = null;
      if (!(investmentInsurance instanceof InvestmentInsurancesTotal)) {
        value = investmentInsurance.getPolicyNumber();
      }
      
      return new SimpleObjectProperty<>(value);
    });
    tableColumn.setMinWidth(120);
    getColumns().add(tableColumn);
    
    // Holder
    tableColumn = new TableColumn<>("Holder");
    tableColumn.setCellValueFactory(arg0 -> {
      InvestmentInsurance investmentInsurance = arg0.getValue();
      String value = null;
      if (!(investmentInsurance instanceof InvestmentInsurancesTotal)) {
        Person policyHolder = investmentInsurance.getPolicyHolder();
        if (policyHolder != null) {
          if (policyHolder.getFirstname().isEmpty()) {
            value = policyHolder.getName();
          } else {
            value =  policyHolder.getFirstname();
          }
        } else {
          value = "<no policy holder>";
        }
      }
      
      return new SimpleObjectProperty<>(value);
    });
    tableColumn.setMinWidth(100);
    getColumns().add(tableColumn);
    
    // Starting date
    tableColumn = new TableColumn<>("Starting date");
    tableColumn.setCellValueFactory(arg0 -> {
      InvestmentInsurance investmentInsurance = arg0.getValue();
      String value = null;
      if (!(investmentInsurance instanceof InvestmentInsurancesTotal)) {
        LocalDate startingDate = investmentInsurance.getStartingDate();
        
        value = DTF.format(startingDate);
      }
      
      return new SimpleObjectProperty<>(value);
    });
    tableColumn.setMinWidth(100);
    getColumns().add(tableColumn);
    
    // Premium
    tableColumn = new TableColumn<>("Premium");
    tableColumn.setCellValueFactory(arg0 -> {
      InvestmentInsurance investmentInsurance = arg0.getValue();
      String value = null;
      if (!(investmentInsurance instanceof InvestmentInsurancesTotal)) {
        if (investmentInsurance.isSetPremium()) {
          PgCurrency premium = investmentInsurance.getPremium().certifyCurrency(PgCurrency.EURO);
          value = CF.format(premium);
        }
      } else {
        PgCurrency totalPremium = investmentInsurancesData.getTotalPremium();
        value = CF.format(totalPremium);
      }
      
      return new SimpleObjectProperty<>(value);
    });
    tableColumn.setMinWidth(100);
    getColumns().add(tableColumn);
    
    // Last known value date
    tableColumn = new TableColumn<>("Last known value date");
    tableColumn.setCellValueFactory(arg0 -> {
      InvestmentInsurance investmentInsurance = arg0.getValue();
      String value = null;
      if (!(investmentInsurance instanceof InvestmentInsurancesTotal)) {
        LocalDate lastKnownValueDate = investmentInsurance.getDateForLastKnownValue();
        value = DTF.format(lastKnownValueDate);
      } else {
        LocalDate lastKnownTotalValueDate = investmentInsurancesData.getDateForLastKnownTotalValue();
        value = DTF.format(lastKnownTotalValueDate);
      }
      
      return new SimpleObjectProperty<>(value);
    });
    tableColumn.setMinWidth(150);
    getColumns().add(tableColumn);
    
    // Last known value
    tableColumn = new TableColumn<>("Last known value");
    tableColumn.setCellValueFactory(arg0 -> {
      InvestmentInsurance investmentInsurance = arg0.getValue();
      String value = null;
      if (!(investmentInsurance instanceof InvestmentInsurancesTotal)) {
        PgCurrency lastKnownValue = investmentInsurance.getLastKnownValue().certifyCurrency(PgCurrency.EURO);
        value = CF.format(lastKnownValue);
      } else {
        PgCurrency lastKnownTotalValue = investmentInsurancesData.getLastKnownTotalValue().certifyCurrency(PgCurrency.EURO);
        value = CF.format(lastKnownTotalValue);
      }
      
      return new SimpleObjectProperty<>(value);
    });
    tableColumn.setMinWidth(150);
    getColumns().add(tableColumn);
    
    // Avg. return on investment
    tableColumn = new TableColumn<>("Avg. return on investment");
    tableColumn.setCellValueFactory(arg0 -> {
      InvestmentInsurance investmentInsurance = arg0.getValue();
      String value = null;
      if (!(investmentInsurance instanceof InvestmentInsurancesTotal)) {
        FixedPointValue avgReturnOnInvestment = investmentInsurance.getAverageReturnOnInvestment();
        value = FPVF.format(avgReturnOnInvestment);
      } else {
        FixedPointValue avgTotalReturnOnInvestment = investmentInsurancesData.getAverageTotalReturnOnInvestment();
        value = FPVF.format(avgTotalReturnOnInvestment);
      }
      
      return new SimpleObjectProperty<>(value);
    });
    tableColumn.setMinWidth(180);
    getColumns().add(tableColumn);
    
    
    InvestmentInsurancesTotal investmentInsuranceInfo;
    for (InvestmentInsurance investmentInsurance: investmentInsurances) {
      items.add(investmentInsurance);
    }
    
    // Extra item for the totals row
    investmentInsuranceInfo = new InvestmentInsurancesTotal();
    items.add(investmentInsuranceInfo);
    
    this.setItems(items);
  }

}


class InvestmentInsurancesTotal extends InvestmentInsuranceImpl {
}
