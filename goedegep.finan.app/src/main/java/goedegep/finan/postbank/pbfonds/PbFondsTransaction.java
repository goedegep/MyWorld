
//Title:        Financien
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Peter Goedegebure
//Company:      Solvation
//Description:  Postbankfonds Transaction
package goedegep.finan.postbank.pbfonds;

import java.time.LocalDate;
import java.time.Month;
//import java.text.SimpleDateFormat;
import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;


public abstract class PbFondsTransaction extends PgTransaction {
  // transactie typen
  public static final short   TT_DIVIDEND                =  1;
  public static final short   TT_JAAROVERZICHT           =  2;
  public static final short   TT_OVERSCHRIJVING          =  3;
  public static final short   TT_STOCK_DIVIDEND          =  4;

  private PgCurrency      koers = null;           // openingskoers op transactie datum
  private FixedPointValue newNumberOfShares = null;


  public PbFondsTransaction(PgAccount account) {
    super(account);
  }

  public void setKoers(PgCurrency koers) {
    this.koers = koers;
  }

  public PgCurrency getKoers() {
    return koers;
  }

  public FixedPointValue getNewNumberOfShares() {
    return newNumberOfShares;
  }

  public void handleFirst() {
    PbFonds   fonds = (PbFonds) getAccount();

    if (transactionInEuros(this.getBookingDate())  &&
        fonds.getInvestment() != null  &&
        fonds.getInvestment().getCurrency() != PgCurrency.EURO) {
      fonds.changeCurrency(PgCurrency.EURO);
    }

    if ((fonds.getVrijOpneembaarDividend() != null)  &&  this.getBookingDate().isAfter(fonds.getDividendVrijOpneembaarTot())) {
      fonds.clearVrijOpneembaarDividendValue();
    }
  }

  public void handleLast() {
    PbFonds        fonds = (PbFonds) getAccount();

    newNumberOfShares = fonds.getNumberOfShares();
    setHandled(true);
  }

  static boolean transactionInEuros(LocalDate trDate) {
    final   LocalDate euroIntroDate = LocalDate.of(1999, Month.JANUARY, 1);
    boolean inEuro;

    if (trDate.isBefore(euroIntroDate)) {
      inEuro = false;
    }
    else {
      inEuro = true;
    }

    return inEuro;
  }
}