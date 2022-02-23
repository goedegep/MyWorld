package goedegep.finan.mortgage.app.guifx;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import goedegep.finan.mortgage.model.InterestRateSet;
import goedegep.finan.mortgage.model.Mortgage;
import goedegep.finan.mortgage.model.MortgageEvent;
import goedegep.finan.mortgage.model.NewInterestRate;
import goedegep.finan.mortgage.model.Rate;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.util.fixedpointvalue.FixedPointValue;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class MortgageInterestRateDevelopmentWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(MortgageInterestRateDevelopmentWindow.class.getName());
  
  private CustomizationFx customization;
  private Mortgage mortgage;
  private List<InterestRateSet> interestRateSets;
//  private InterestDevelopmentChart interestDevelopmentChart;
  private VBox chartVBox;

  public MortgageInterestRateDevelopmentWindow(CustomizationFx customization, Mortgage mortgage, List<InterestRateSet> interestRateSets) {
    super("Mortgage interest rate development", customization);
    
    this.customization = customization;
    this.mortgage = mortgage;
    this.interestRateSets = interestRateSets;
    
    createGUI();
    
    updateChart();
    
    show();
  }
  
  private void createGUI() {
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    chartVBox = componentFactory.createVBox();
    
    Scene scene = new Scene(chartVBox);
    setScene(scene);
  }
  
  private void updateChart() {
    Date firstDate = null;
    Date lastDate = null;
    
    XYChart.Series<Number, Number> mortgageInterestRates = new XYChart.Series<>();
    mortgageInterestRates.setName(mortgage.getId());

    for (MortgageEvent mortgageEvent: mortgage.getMortgageEvents()) {
      if (mortgageEvent instanceof NewInterestRate) {
        NewInterestRate newInterestRate = (NewInterestRate) mortgageEvent;
        if (newInterestRate.isSetStartingDate()  &&  newInterestRate.isSetNewInterestRate()) {
          Date startDate = newInterestRate.getStartingDate();
          if (firstDate == null) {
            firstDate = startDate;
          }
          lastDate = startDate;
          long startDateAsLong = startDate.getTime();
          FixedPointValue interestRate = newInterestRate.getNewInterestRate();
          double interestRateAsDouble = interestRate.doubleValue();
          mortgageInterestRates.getData().add(new XYChart.Data<>(startDateAsLong, interestRateAsDouble));
        }  else {
          if (!newInterestRate.isSetStartingDate()) {
            LOGGER.severe("Starting date not set, event: " + MortgageEventDescriptions.getMortgageEventDescription(newInterestRate));
          }
          if (!newInterestRate.isSetNewInterestRate()) {
            LOGGER.severe("Interest rate not set, event: " + MortgageEventDescriptions.getMortgageEventDescription(newInterestRate));
          }
        }
      }
    }
    
    LineChart<Number, Number> lineChart = new LineChart<>(createXAxis(firstDate, lastDate), createYAxis());

    lineChart.getData().add(mortgageInterestRates);
    
    int referenceNr = 1;
    for (InterestRateSet interestRateSet: interestRateSets) {
      XYChart.Series<Number, Number> referenceInterestRates = new XYChart.Series<>();
      if (interestRateSet.isSetName()) {
        referenceInterestRates.setName(interestRateSet.getName());
      } else {
        referenceInterestRates.setName("Reference " + referenceNr);
      }

      for (Rate rate: interestRateSet.getRates()) {
          if (rate.isSetDate()  &&  rate.isSetRate()) {
            Date date = rate.getDate();
            long dateAsLong = date.getTime();
            FixedPointValue interestRate = rate.getRate();
            double interestRateAsDouble = interestRate.doubleValue();
            referenceInterestRates.getData().add(new XYChart.Data<>(dateAsLong, interestRateAsDouble));
          }  else {
            if (!rate.isSetDate()) {
              LOGGER.severe("Date not set, Rate: " + rate.toString());
            }
            if (!rate.isSetRate()) {
              LOGGER.severe("Interest rate not set, Rate: " + rate.toString());
            }
          }
      }
      
      
      lineChart.getData().add(referenceInterestRates);
      
      referenceNr++;
    }
    
    for (InterestRateSet interestRateSet: interestRateSets) {
      if (!(interestRateSet.getName().equals("One month Euribor"))) {
        continue;
      }
      
      XYChart.Series<Number, Number> adaptedEuriborInterestRates = new XYChart.Series<>();
      adaptedEuriborInterestRates.setName("One month Euribor + 0.75%, shifted 2 months ");

      for (Rate rate: interestRateSet.getRates()) {
          if (rate.isSetDate()  &&  rate.isSetRate()) {
            Date date = rate.getDate();
            long dateAsLong = date.getTime() + 2l * 30 * 24 * 60 * 60 * 1000;
            FixedPointValue interestRate = rate.getRate();
            double interestRateAsDouble = interestRate.doubleValue() + 0.75;
            adaptedEuriborInterestRates.getData().add(new XYChart.Data<>(dateAsLong, interestRateAsDouble));
          }  else {
            if (!rate.isSetDate()) {
              LOGGER.severe("Date not set, Rate: " + rate.toString());
            }
            if (!rate.isSetRate()) {
              LOGGER.severe("Interest rate not set, Rate: " + rate.toString());
            }
          }
      }
      
      
      lineChart.getData().add(adaptedEuriborInterestRates);
      
      referenceNr++;
    }
    
    
    chartVBox.getChildren().add(lineChart);
  }

  private static NumberAxis createXAxis(Date firstDate, Date lastDate) {
    final NumberAxis xAxis = new NumberAxis(firstDate.getTime(), lastDate.getTime(), 30d * 24 * 60 * 60 * 1000);
//    final NumberAxis xAxis = new NumberAxis();
    xAxis.setLabel("Date");
    xAxis.setTickLabelFormatter(new DateValueToStringConverter());

    return xAxis;
  }

  // Y axis: interest rate (%)
  private static NumberAxis createYAxis() {
    final NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel("Interest rate");

    return yAxis;
  }
}

class DateValueToStringConverter extends StringConverter<Number> {
  DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

  @Override
  public String toString(Number number) {
    long dateInMillis = number.longValue();
    Date date = new Date(dateInMillis);
    return DF.format(date);
  }

  @Override
  public Number fromString(String string) {
    try {
      Date date = DF.parse(string);
      return date.getTime();
    } catch (ParseException e) {
      return null;
    }
  }
  
}
