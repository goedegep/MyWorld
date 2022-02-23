package goedegep.app.finan.stocksapp;

import goedegep.appgen.swing.AppGenAbstractTableModel;
import goedegep.finan.stocks.Fund;
import goedegep.finan.stocks.RateType;
import goedegep.finan.stocks.Share;
import goedegep.types.model.DateRateTuplet;
import goedegep.util.money.PgCurrency;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

@SuppressWarnings("serial")
public class ShareRatesModel extends AppGenAbstractTableModel  {
  private static final int DATE_COLUMN = 0;
  private static final int RATE_COLUMN = 1;
  private static final int SHARE_COLUMN = 2;
  
  private Object[][] data;
  
  private static final String[] columnNames = {
      "Datum",
      "Koers",
      "Aandeel"
  };

  private static final Object[] longValues = {
    (new GregorianCalendar(9999, 12, 30)).getTime(),
    new PgCurrency(PgCurrency.EURO, 999999),
    "Zomaar een lange aandeel naam"};

  public ShareRatesModel() {
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
      
    case SHARE_COLUMN:
      return String.class;

    default:
      return null;
    }
  }

  public void setParameters(Fund fund, Share share, RateType rateType) {
    ArrayList<Share>  shareList = new ArrayList<Share>();
    int               nrOfRates;
    DateRateTuplet    dateRateTuplet;

    // Create list of shares for the rates have to be listed
    if (share == null) {
      for (Share sh: fund.getShares()) {
        shareList.add(sh);
      }
    } else {
      shareList.add(share);
    }

    // determine total number of shares to determine the size of the array
    nrOfRates = 0;
    for (Share sh: shareList) {
      switch (rateType) {
      case OPENINGS_KOERS:
        nrOfRates += sh.getOpeningRates().size();
        break;
      case SLOT_KOERS:
        nrOfRates += sh.getClosingRates().size();
        break;
      case IEDERE_KOERS:
        nrOfRates += sh.getAnyRates().size();
        break;
      }
    }

    if (nrOfRates == 0) {
      data = new String[1][3];
      data[0][1] = "geen data";
    } else {
      data = new Object[nrOfRates][3];

      // get all rates and put them in 'rates'.
      int index = 0;
      Iterator<DateRateTuplet> rateIterator = null;
      for (Share sh: shareList) {
        data[index][2] = sh.getName();
        switch (rateType) {
        case OPENINGS_KOERS:
          rateIterator = sh.getOpeningRates().iterator();
          break;
        case SLOT_KOERS:
          rateIterator = sh.getClosingRates().iterator();
          break;
        case IEDERE_KOERS:
          rateIterator = sh.getAnyRates().iterator();
          break;
        default:
          // can not happen
          break;
        }
        while (rateIterator.hasNext()) {
          dateRateTuplet = (DateRateTuplet) rateIterator.next();
          data[index][0] = dateRateTuplet.getDate();
          data[index][1] = dateRateTuplet.getRate();
          index++;
        }
      }
    }
  }
}
