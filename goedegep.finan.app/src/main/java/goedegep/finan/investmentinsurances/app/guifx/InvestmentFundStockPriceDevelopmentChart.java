package goedegep.finan.investmentinsurances.app.guifx;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;

import goedegep.finan.investmentinsurance.model.InvestmentFund;
import goedegep.types.model.DateRateTuplet;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.StringConverter;

/**
 * This class provides a chart which shows the development of the stock prices of investement funds.
 * <p>
 * On the y-axis it is possible to show the real stock prices, or normalized stock prices.
 */
public class InvestmentFundStockPriceDevelopmentChart extends Group {
  private final static Logger LOGGER = Logger.getLogger(InvestmentFundStockPriceDevelopmentChart.class.getName());
  private final static PgCurrencyFormat CF = new PgCurrencyFormat();

  /**
   * Change the inputs of the chart
   * <p>
   * For ease of implementation, all years are supposed to have 365 days. This leads to minor 'errors' in the chart, but it is expected that this wont be visible.
   * 
   * @param investmentFunds The investment funds for which to show the stock prices.
   * @param normalizeRates if true, all rates will start at 100, otherwise the real prices are shown.
   */
  public void changeSettings(List<InvestmentFund> investmentFunds, boolean normalizeRates) {
    LOGGER.info("=>");
    
    getChildren().clear();
    
    // Gather information
    final int firstRateYear = getFirstRateYear(investmentFunds);
    int lastRateYearPlusOne = getLastRateYear(investmentFunds) + 1;
    
    List<XYChart.Series<Number, Number>> xyChartSeries = createChartSeries(investmentFunds, normalizeRates, firstRateYear);
        
    //Defining X axis
    NumberAxis xAxis = new NumberAxis(0, (lastRateYearPlusOne - firstRateYear) * 365, 365);
    xAxis.setLabel("Date"); 
    xAxis.setTickLabelFormatter(new StringConverter<Number>() {

      @Override
      public String toString(Number number) {
        long dayNumber = number.longValue();
        long year = dayNumber / 365 + firstRateYear;
        return Long.toString(year);
      }

      @Override
      public Number fromString(String string) {
        return null;
      }
      
    });
    
    //Defining y axis 
    NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel("Stock price");
    yAxis.setAutoRanging(true);
    yAxis.setTickLabelFormatter(new StringConverter<Number>() {

      @Override
      public String toString(Number number) {
        long rateValue = number.longValue();
        if (normalizeRates) {
          return Long.toString(rateValue / 100);
        } else {
          PgCurrency rate = new PgCurrency(rateValue);
          return CF.format(rate);
        }
      }

      @Override
      public Number fromString(String string) {
        return null;
      }
      
    });
    
    LineChart<Number, Number> linechart = new LineChart<>(xAxis, yAxis);
    linechart.autosize();
    linechart.setTitle("Investment fund stock price development");
    linechart.getData().addAll(xyChartSeries);
    
    getChildren().add(linechart);


    LOGGER.info("<=");
  }
  
  /**
   * Get the first year for which a stock price is available.
   * 
   * @param investmentFunds The investment funds of which the stock prices are searched through.
   * @return the first year for which a stock price is available.
   */
  private static int getFirstRateYear(List<InvestmentFund> investmentFunds) {
    int firstRateYear = -1;
    
    for (InvestmentFund investmentFund: investmentFunds) {
      if (investmentFund.getStockPrices().size() > 0) {
        LocalDate firstRateDate = investmentFund.getStockPrices().get(0).getDate();
        int year = firstRateDate.getYear();
        if (firstRateYear == -1) {
          firstRateYear = year;
        } else {
          if (year < firstRateYear) {
            firstRateYear = year;
          }
        }
      }
    }
    
    LOGGER.info("=> " + firstRateYear);
    return firstRateYear;
  }
  
  /**
   * Get the last year for which a stock price is available.
   * 
   * @param investmentFunds The investment funds of which the stock prices are searched through.
   * @return the last year for which a stock price is available.
   */
  private static int getLastRateYear(List<InvestmentFund> investmentFunds) {
    int lastRateYear = -1;
    
    for (InvestmentFund investmentFund: investmentFunds) {
      if (investmentFund.getStockPrices().size() > 0) {
        LocalDate firstRateDate = investmentFund.getStockPrices().get(investmentFund.getStockPrices().size() - 1).getDate();
        int year = firstRateDate.getYear();
        if (lastRateYear == -1) {
          lastRateYear = year;
        } else {
          if (year > lastRateYear) {
            lastRateYear = year;
          }
        }
      }
    }
    
    LOGGER.info("=> " + lastRateYear);
    return lastRateYear;
  }
  
  /**
   * Create the data series for the chart.
   * <p>
   * The values of the y-axis depends on the <code>normalizeRates</code> paramater.
   * <ul>
   * <li>not normalized (<code>normalizeRates</code> is false)<br/>
   * The value is the stock price in cents.
   * </li>
   * <li>normalized (<code>normalizeRates</code> is true)<br/>
   * On the stock prizes, a factor is applied, such that for the first date all stock prices start at 100.
   * This means that it's easier to compare the return on investment of the funds.
   * </li>
   * </ul>
   * The values of the x-axis are: ('year of the stock price date' - firstRateYear) * 365 + 'day in the year of the stock price date';
   * 
   * @param investmentFunds The investment funds for which to show the stock prices.
   * @param normalizeRates if true, all rates will start at 100, otherwise the real prices are shown.
   * @param firstRateYear the first year for which a stock price is available
   * @return
   */
  private static List<XYChart.Series<Number, Number>> createChartSeries(List<InvestmentFund> investmentFunds, boolean normalizeRates, int firstRateYear) {
    LOGGER.info("=>");

    List<XYChart.Series<Number, Number>> xyChartSeries = new ArrayList<>();

    Double normalizationFactor = 1.0;
    
    for (InvestmentFund investmentFund: investmentFunds) {
      if (normalizeRates) {
        if (investmentFund.getStockPrices().size() > 0) {
          PgCurrency firstStockPrice = investmentFund.getStockPrices().get(0).getRate();
          normalizationFactor = 100 / firstStockPrice.getDoubleValue();
        }
      }

      EList<DateRateTuplet> stockPrices = investmentFund.getStockPrices();
      XYChart.Series<Number, Number> xyChartSerie = new XYChart.Series<>();
      xyChartSerie.setName(investmentFund.getName());

      for (DateRateTuplet dataRateTuplet: stockPrices) {
        PgCurrency rate = dataRateTuplet.getRate().certifyFactor(100);
        long rateValue = rate.getAmount();
        if (normalizeRates) {
          rateValue = (long) (normalizationFactor * rateValue);
        }
        LocalDate localDate = dataRateTuplet.getDate();
        long dateValue = (localDate.getYear() - firstRateYear) * 365 + localDate.getDayOfYear();
        XYChart.Data<Number, Number> data = new XYChart.Data<>(dateValue, rateValue);
        data.setNode(new Circle(2, Color.GRAY));
        xyChartSerie.getData().add(data);
      }

      xyChartSeries.add(xyChartSerie);
    }

    LOGGER.info("<=");
    return xyChartSeries;
  }
}
