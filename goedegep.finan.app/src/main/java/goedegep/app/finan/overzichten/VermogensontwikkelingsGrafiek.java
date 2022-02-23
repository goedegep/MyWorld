package goedegep.app.finan.overzichten;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.PeriodAxisLabelInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StackedXYAreaRenderer2;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimePeriodAnchor;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.time.TimeTableXYDataset;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

import goedegep.appgen.swing.AppFrame;
import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.QuarterlyData;
import goedegep.util.datetime.Quarter;

/**
 * Een vermogensontwikkelingsgrafiek bestaat uit:
 * 
 * 
 */

@SuppressWarnings("serial")
public class VermogensontwikkelingsGrafiek extends ChartPanel {
  /** Symbols for roman numbered quarters. */
  private static final String[] ROMAN_QUARTERS  = new String[] {"I", "II", "III", "IV"};
  private static QuarterDateFormat QUARTER_FORMAT = new QuarterDateFormat();
  
//  private Look look;

  /**
   * Creeer een Vermogenontwikkelings grafiek.
   *
   * @param look de te gebruiken 'look'.
   * @param title  titel van de grafiek.
   * @param sumAccount sum account waarvan de gegevens getoond worden.
   */
  public VermogensontwikkelingsGrafiek(AppFrame owner, String title) {
    super(createChart(createValueDataset(null), createProfitDataset(null)), false);
//    this.look = owner.getLook();
  }

  private static TimeTableXYDataset createValueDataset(List<Object> displayItems) {

    TimeTableXYDataset dataset = new TimeTableXYDataset();

    if (displayItems != null) {
      for (Object displayItem: displayItems) {
        PgAccount account = (PgAccount) displayItem;
        List<QuarterlyData> quarterlyDataList = account.getQuarterlyData();

        if (quarterlyDataList != null) {
          for (QuarterlyData quarterlyData: quarterlyDataList) {
            if (quarterlyData.getBalance() != null) {
              Quarter myQuarter = quarterlyData.getQuarter();
              org.jfree.data.time.Quarter q = new org.jfree.data.time.Quarter(myQuarter.getQuarter(), myQuarter.getYear());
              dataset.add(q, quarterlyData.getBalance().getAmount()/100, account.getName());              
            }
          }
        }      
      }

      dataset.setXPosition(TimePeriodAnchor.END);
    }

    return dataset;
  }

  private static XYDataset createProfitDataset(List<Object> displayItems) {
    TimeSeries winstSet = new TimeSeries("winst");
    TimeSeries winstCumulatiefSet = new TimeSeries("winst cumulatief");
    TimeSeriesCollection tscDataset = new TimeSeriesCollection();

    // De winst kan gewoon per kwartaal ingevuld worden.
    if (displayItems != null) {
      for (Object displayItem: displayItems) {
        PgAccount account = (PgAccount) displayItem;
        System.out.println("Winst set toevoegen voor: " + account.getName());
        List<QuarterlyData> quarterlyDataList = account.getQuarterlyData();
        if (quarterlyDataList != null) {
          for (QuarterlyData quarterlyData: quarterlyDataList) {
            if (quarterlyData.getProfit() != null) {
              Quarter myQuarter = quarterlyData.getQuarter();
              org.jfree.data.time.Quarter q = new org.jfree.data.time.Quarter(myQuarter.getQuarter(), myQuarter.getYear());
              TimeSeriesDataItem i;
              long amount;
              i = winstSet.getDataItem(q);
              amount = quarterlyData.getProfit().getAmount()/100;
              if (i != null) {
                System.out.println("Updating entry for quarter: " + q.getYearValue() + ":" + q.getQuarter());
                i.setValue(i.getValue().longValue() + amount);
              } else {
                System.out.println("Adding entry for quarter: " + q.getYearValue() + ":" + q.getQuarter());
                winstSet.add(q, amount);  
                winstCumulatiefSet.add(q, 0L);
              }
              i = winstCumulatiefSet.getDataItem(q);
            } else {
              System.out.println("Skipping entry (because there's no profit info) for quarter: " + quarterlyData.getQuarter().getQuarter() + ":" + quarterlyData.getQuarter().getYear());
              org.jfree.data.time.Quarter q = new org.jfree.data.time.Quarter(quarterlyData.getQuarter().getQuarter(), quarterlyData.getQuarter().getYear());
              winstCumulatiefSet.add(q, 0L);
            }
          }
        } else {
          System.out.println("No QuarterlyData for: " + account.getName());
        }
      }

      // De bijdrage aan de cumulatieve winst loopt door voor de kwartalen waarvoor
      // geen kwartaaldata beschikbaar is (na het opheffen van de rekening).
      for (Object displayItem: displayItems) {
        PgAccount account = (PgAccount) displayItem;
        List<QuarterlyData> quarterlyDataList = account.getQuarterlyData();
        if (quarterlyDataList != null) {
          long lastCumulativeProfit = 0L;
          org.jfree.data.time.Quarter lastQuarter = null;
          for (QuarterlyData quarterlyData: quarterlyDataList) {
            Quarter myQuarter = quarterlyData.getQuarter();
            org.jfree.data.time.Quarter q = new org.jfree.data.time.Quarter(myQuarter.getQuarter(), myQuarter.getYear());
            long amount;
            TimeSeriesDataItem i = winstCumulatiefSet.getDataItem(q);
            if (quarterlyData.getCumulativeProfit() != null) {
              amount = quarterlyData.getCumulativeProfit().getAmount()/100;
            } else {
              amount = 0L;
            }
            if (i == null) {
              System.out.println("no entry for quarter: " + q.getYearValue() + ":" + q.getQuarter());
            }
            i.setValue(i.getValue().longValue() + amount);

            lastCumulativeProfit = amount;
            lastQuarter = q;
          }

          System.out.println("winstCumulatiefSet.getItemCount() = " + winstCumulatiefSet.getItemCount());
          System.out.println("winstSet.getItemCount() = " + winstSet.getItemCount());

          for (int index = winstSet.getIndex(lastQuarter) + 1; index < winstSet.getItemCount(); index++) {
            System.out.println("Extending, index = " + index);
            TimeSeriesDataItem i = winstCumulatiefSet.getDataItem(index);
            i.setValue(i.getValue().longValue() + lastCumulativeProfit);
          }
        }
      }

      tscDataset.addSeries(winstSet);
      tscDataset.addSeries(winstCumulatiefSet);
      tscDataset.setXPosition(TimePeriodAnchor.END);
    }

    return tscDataset;
  }

  /**
   * Creeer de JFreeChart grafiek.
   * 
   * @param dataset  the dataset.
   * 
   * @return The chart.
   */
  private static JFreeChart createChart(XYDataset valueDataset, XYDataset profitData) {

    // create the chart...
    // The standard renderer of the chart is used to draw the profit lines. 
    JFreeChart chart = ChartFactory.createTimeSeriesChart(
        "Vermogen- en Winst ontwikkeling",  // chart title
        null,                               // domain axis label
        "Waarde/Winst",                     // range axis label
        profitData,                         // data
        true,                               // include legend
        true,                               // tooltips?
        false                               // URLs?
    );

    // Customize the chart ...
    // set the background color for the chart...
//    chart.setBackgroundPaint(look.getBackgroundColor());

    // Get the plot to customize it and to add a second renderer + data set.
    XYPlot plot = (XYPlot) chart.getPlot();

    // The crosshairs make it easier to determine values in the chart.
    plot.setDomainCrosshairVisible(true);
    plot.setRangeCrosshairVisible(true);
//    plot.setBackgroundPaint(look.getListBackgroundColor());
//    plot.setDomainGridlinePaint(look.getButtonBackgroundColor());
    plot.setDomainGridlinesVisible(true);
//    plot.setRangeGridlinePaint(look.getBackgroundColor());

    // customize the renderer
    XYItemRenderer renderer = plot.getRenderer();
    if (renderer instanceof XYLineAndShapeRenderer) {
      XYLineAndShapeRenderer rr = (XYLineAndShapeRenderer) renderer;
//      rr.setBaseShapesVisible(true);
//      rr.setBaseShapesFilled(true);
      rr.setBaseItemLabelsVisible(true);
      rr.setSeriesPaint(0, Color.black);
      rr.setSeriesPaint(1, Color.black);
    }

    PeriodAxis domainAxis = new PeriodAxis("Periode");
    domainAxis.setAutoRangeTimePeriodClass(org.jfree.data.time.Quarter.class);
    domainAxis.setMajorTickTimePeriodClass(org.jfree.data.time.Quarter.class);
    PeriodAxisLabelInfo[] info = new PeriodAxisLabelInfo[2];
    info[0] = new PeriodAxisLabelInfo(org.jfree.data.time.Quarter.class,
        QUARTER_FORMAT, new RectangleInsets(2, 2, 2, 2),
        new Font("SansSerif", Font.BOLD, 10), Color.blue, false,
        new BasicStroke(0.0f), Color.lightGray);
    info[1] = new PeriodAxisLabelInfo(Year.class,
        new SimpleDateFormat("yyyy"));
    domainAxis.setLabelInfo(info);
    plot.setDomainAxis(domainAxis);

    // Add a stacked renderer for drawing the values.
    XYItemRenderer xyr = new StackedXYAreaRenderer2();    
    plot.setDataset(1, valueDataset);
    plot.setRenderer(1, xyr);

    // set the range axis to display integers only...
    final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

    return chart;
  }

  public void changeSettings(List<Object> displayItems) {
    setChart(createChart(createValueDataset(displayItems), createProfitDataset(displayItems)));
  }   

  static class QuarterDateFormat extends DateFormat {
    public QuarterDateFormat() {
      this.calendar = new GregorianCalendar();
    }

    public StringBuffer format(Date date, StringBuffer toAppendTo,
        FieldPosition fieldPosition) {
      this.calendar.setTime(date);
      int month = this.calendar.get(Calendar.MONTH);
      int quarter = month / 3;
      toAppendTo.append(ROMAN_QUARTERS[quarter]);
      return toAppendTo;
    }

    @Override
    public Date parse(String arg0, ParsePosition arg1) {
      throw new UnsupportedOperationException();
    }

    @Override
    public Object clone()
    {
      QuarterDateFormat other = new QuarterDateFormat();
      return other;
    }
  }
}
