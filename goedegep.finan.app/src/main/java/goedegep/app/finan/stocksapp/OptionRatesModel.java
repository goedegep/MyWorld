package goedegep.app.finan.stocksapp;

import goedegep.appgen.swing.AppGenAbstractTableModel;
import goedegep.finan.stocks.OptionSerie;
import goedegep.finan.stocks.RateType;
import goedegep.types.model.DateRateTuplet;
import goedegep.util.money.PgCurrency;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@SuppressWarnings("serial")
public class OptionRatesModel extends AppGenAbstractTableModel {
  private static final int DATE_COLUMN = 0;
  private static final int RATE_COLUMN = 1;
  
  private Object[][] data;
  
  private static final String[] columnNames = {
      "Datum",
      "Koers"
  };

  private static final Object[] longValues = {
    (new GregorianCalendar(9999, 12, 30)).getTime(),
    new PgCurrency(PgCurrency.EURO, 999999)};

  public OptionRatesModel() {
    super(columnNames, longValues);
  }
  
  public int getRowCount() {
    if (data != null) {
      return data.length;
    } else {
      return 0;
    }
  }

  public Object getValueAt(int row, int col) {
    return data[row][col];
  }
  
  /*
   * JTable uses this method to determine the default renderer/
   * editor for each cell.  If we didn't implement this method,
   * then the last column would contain text ("true"/"false"),
   * rather than a check box.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Class getColumnClass(int col) {
    switch (col) {
    case DATE_COLUMN:
      return Date.class;

    case RATE_COLUMN:
      return PgCurrency.class;

    default:
      return null;
    }
  }

  public void setParameters(OptionSerie optionSerie, RateType rateType) {
    int               nrOfRates = 0;

    if (optionSerie != null) {
      switch (rateType) {
      case OPENINGS_KOERS:
        nrOfRates = optionSerie.getOpeningRates().size();
        break;
      case SLOT_KOERS:
        nrOfRates = optionSerie.getClosingRates().size();
        break;
      case IEDERE_KOERS:
        nrOfRates = optionSerie.getAnyRates().size();
        break;
      }
    }

    if (nrOfRates == 0) {
      data = new String[1][2];
      data[0][1] = "geen data";
    } else {
      data = new Object[nrOfRates][2];

      // get all rates and put them in 'rates'.
      int index = 0;
      List<DateRateTuplet> rates = null;
      switch (rateType) {
      case OPENINGS_KOERS:
        rates = optionSerie.getOpeningRates();
        break;
      case SLOT_KOERS:
        rates = optionSerie.getClosingRates();
        break;
      case IEDERE_KOERS:
        rates = optionSerie.getAnyRates();
        break;
      default:
        // can not happen
        break;
      }
      
      for (DateRateTuplet dateRateTuplet: rates) {
        data[index][0] = dateRateTuplet.getDate();
        data[index][1] = dateRateTuplet.getRate();
        index++;
      }
    }
  }
}
