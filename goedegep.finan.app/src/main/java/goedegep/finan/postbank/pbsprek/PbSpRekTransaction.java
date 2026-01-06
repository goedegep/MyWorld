
//Title:        Financien
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Peter Goedegebure
//Company:      Solvation
//Description:  Postbankfonds Transaction

package goedegep.finan.postbank.pbsprek;

import java.time.LocalDate;
import java.time.Month;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;


public abstract class PbSpRekTransaction extends PgTransaction {
  // transactie typen
  public static final short   TT_GESCHENK_INLEG   =  1;
    // nog doen
  public static final short   TT_JAAROVERZICHT    =  2;
  public static final short   TT_OPHEFFING        =  3;
  public static final short   TT_OVERSCHRIJVING   =  4;
  public static final short   TT_RENTE            =  5;
  public static final short   TT_RENTE_AANPASSING =  6;

  FixedPointValue         rentePercentage = null;  // huidig rentepercentage

  public PbSpRekTransaction(PgAccount  account) {
    super(account);
  }

  public void setRentePercentage(FixedPointValue rentePercentage) {
    this.rentePercentage = rentePercentage;
  }

  public FixedPointValue getRentePercentage() {
    return rentePercentage;
  }

  public void handleFirst() {
    PbSpRek account = (PbSpRek) getAccount();
    
    account.handleFirst(this);

//    try {
//      if (transactionInEuros(getBookingDate())  &&  account.getCurrency() != PgCurrency.EURO) {
//        account.certifyCurrency(PgCurrency.EURO);
//      }
//    } catch (IllegalArgumentException e) {
//      // Do nothing.
//    }
  }

  public void handleLast() {
    PbSpRek account = (PbSpRek) getAccount();

    setNieuwTegoed(account.getBalance());
    setHandled(true);
    
    account.handleLast(this);
  }

  static boolean transactionInEuros(LocalDate trDate) {
    if (trDate == null) {
      throw new IllegalArgumentException("Transaction date is null");
    }
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
