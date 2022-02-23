package goedegep.finan.jobappointment.guifx;

import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.util.EcoreUtil;

import goedegep.finan.jobappointment.model.SalaryPayment;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.FormattersFactory;
import goedegep.util.datetime.YearMonthAsLong;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.StringConverter;

/**
 * This class provides a chart which shows the salary development, based on te salary payments.
 * <p>
 * The x-axis represents time (from first payment date till last payment date)
 * The y-axis shows the monthly payment.
 */
public class SalaryDevelopmentChart extends HBox {
  private final static Logger LOGGER = Logger.getLogger(SalaryDevelopmentChart.class.getName());
  private final PgCurrencyFormat pfCurrencyFormat = FormattersFactory.getPgCurrencyFormat();

  /**
   * Constructor for creating a salary development chart.
   * <p>
   * The chart shows the salary payments over time.<p>
   * The currency used in the chart, is that of the last payment. Payments in other currencies are converted to this currency.</br>
   * Also all payments are converted to use a factor 1, as they all have to be with the same factor and one is good enough for this chart.</br>
   * If an empty list is provided, an empty chart is shown with some default values:
   * <ul>
   * <li>The time axis shows the months of the current year.</li>
   * <li>The salary axis ranges from 0 to 100.000 EURO.</li>
   * </ul>
   * 
   * @param salaryPayments The salary payments to show in the chart.
   */
  public SalaryDevelopmentChart(CustomizationFx customization, List<SalaryPayment> salaryPayments) {
    LOGGER.info("=>");
    
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    componentFactory.customizePane(this);
    
    getChildren().clear();
        
    YearMonth yearMonth;
    long firstYearMonthValue;
    long lastYearMonthValue;
    List<XYChart.Series<Number, Number>> xyChartSeries = null;
    int currencyUnit;
    
    if (!salaryPayments.isEmpty()) {
      yearMonth = salaryPayments.get(0).getPeriod();
      firstYearMonthValue = YearMonthAsLong.yearMonthToLong(yearMonth);

      yearMonth = salaryPayments.get(salaryPayments.size() - 1).getPeriod();
      lastYearMonthValue = YearMonthAsLong.yearMonthToLong(yearMonth);

      currencyUnit = salaryPayments.get(salaryPayments.size() - 1).getGrossSalary().getCurrency();

      List<SalaryPayment> adaptedSalaryPayments = new ArrayList<>();
      for (SalaryPayment salaryPayment: salaryPayments) {
        SalaryPayment adaptedSalaryPayment = EcoreUtil.copy(salaryPayment);
        PgCurrency adaptedGrossSalary = salaryPayment.getGrossSalary().certifyCurrency(currencyUnit).certifyFactor(1);
        adaptedSalaryPayment.setGrossSalary(adaptedGrossSalary);
        adaptedSalaryPayments.add(adaptedSalaryPayment);
      }
      xyChartSeries = createChartSeries(adaptedSalaryPayments);
    } else {
      yearMonth = YearMonth.now();
      yearMonth = YearMonth.of(yearMonth.getYear(), Month.JANUARY);
      firstYearMonthValue = YearMonthAsLong.yearMonthToLong(yearMonth);
      
      yearMonth = YearMonth.of(yearMonth.getYear(), Month.DECEMBER);
      lastYearMonthValue = YearMonthAsLong.yearMonthToLong(yearMonth);
      
      currencyUnit = PgCurrency.EURO;
    }
        
    //Defining X axis
    NumberAxis xAxis = new NumberAxis(firstYearMonthValue, lastYearMonthValue, 24);
    xAxis.setMinorTickCount(2);
    xAxis.setLabel("Date");
    xAxis.setTickLabelFormatter(new StringConverter<Number>() {
      DateTimeFormatter dateTimeFormatter = FormattersFactory.getYearMonthDateTimeFormatterInstance();

      @Override
      public String toString(Number number) {
        return dateTimeFormatter.format(YearMonthAsLong.longToYearMonth(number.longValue()));
      }

      @Override
      public Number fromString(String string) {
        return null;
      }
      
    });
    
    //Defining y axis
    NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel("Gross salary");
    yAxis.setAutoRanging(true);
    yAxis.setTickLabelFormatter(new StringConverter<Number>() {

      @Override
      public String toString(Number number) {
        Double doubleValue = number.doubleValue();
        PgCurrency rate = new PgCurrency(currencyUnit, doubleValue.intValue(), 1);
        return pfCurrencyFormat.format(rate);
      }

      @Override
      public Number fromString(String string) {
        return null;
      }
      
    });
    
    LineChart<Number, Number> linechart = new LineChart<>(xAxis, yAxis);
    linechart.autosize();
    linechart.setTitle("Salary development");
    if (xyChartSeries != null) {
      linechart.getData().addAll(xyChartSeries);
    }
    
    getChildren().add(linechart);

    LOGGER.info("<=");
  }
  
  /**
   * Create the data series for the chart.
   * <p>
   * 
   * @param salaryPayments The salary payments.
   * @return a list of XYChart.Series, with one entry. This entry has a data item for each salary payment.
   */
  private static List<XYChart.Series<Number, Number>> createChartSeries(List<SalaryPayment> salaryPayments) {
    LOGGER.info("=>");

    List<XYChart.Series<Number, Number>> xyChartSeries = new ArrayList<>();
    
    XYChart.Series<Number, Number> xyChartSerie = new XYChart.Series<>();
    xyChartSerie.setName("Gross monthly salary");
    xyChartSeries.add(xyChartSerie);
    
    for (SalaryPayment salaryPayment: salaryPayments) {
        YearMonth yearMonth = salaryPayment.getPeriod();
        
        PgCurrency grossSalary = salaryPayment.getGrossSalary();
        Double doubleValue = grossSalary.getDoubleValue();
        
        XYChart.Data<Number, Number> data = new XYChart.Data<>(YearMonthAsLong.yearMonthToLong(yearMonth), doubleValue);
        data.setNode(new Circle(1, Color.GRAY));
        xyChartSerie.getData().add(data);
    }

    LOGGER.info("<=");
    return xyChartSeries;
  }

}
