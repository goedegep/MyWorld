
//Title:        Financien
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Peter Goedegebure
//Company:      Solvation
//Description:  Postbank aandelenfonds
package goedegep.finan.abnamrobank.aaeffrek;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class AAEffRekTransaction extends PgTransaction {
  // Transaction types.
  public static final short TT_AANDELEN_TRANSACTIE      =  1;
  public static final short TT_AFSCHRIJVING             =  2;
  public static final short TT_BELASTING_OVERZICHT      =  3;
  public static final short TT_BEWAARLOON               =  4;
  public static final short TT_BIJSCHRIJVING            =  5;
//  public static final short TT_BONUS_AANDELEN           =  4;
//  public static final short TT_CLAIM_RIGHTS_RECEIVED    =  5;
//  public static final short TT_CLAIM_RIGHTS_TRANSACTION =  6;
//  public static final short TT_CORRECTIE                =  7;
  public static final short TT_DIVIDEND                 =  6;
//  public static final short TT_FRACTIEVERR_NWE_WAARDEN  =  9;
  public static final short TT_KWARTAAL_OVERZICHT       =  7;
  public static final short TT_OPTIE_EXPIRATIE          =  8;
  public static final short TT_OPTIE_TRANSACTIE         =  9;
//  public static final short TT_OVERNAME                 = 13;
//  public static final short TT_REDENOMINATIE            = 15;
  public static final short TT_RENTEVERREKENING         = 10;
  public static final short TT_AANPASSING_DEKKINGSVERPLICHTING = 11;
//  public static final short TT_STOCK_DIVIDEND           = 17;
//  public static final short TT_STOCK_DIV_TRANSACTIE     = 18;
//  public static final short TT_TERUGBETALING            = 19;
//  public static final short TT_TERUGBETALING_RECHTEN    = 20;
//  public static final short TT_VERW_CLAIM_RIGHTS        = 21;
//  public static final short TT_VERW_STOCK_DIVIDEND      = 22;

  public AAEffRekTransaction(PgAccount  account) {
    super(account);
  }

  public static boolean transactionInEuros(Date trDate) {
    final   GregorianCalendar    euroIntroCalendarDate = new GregorianCalendar(1999, Calendar.JANUARY, 1);
    final   Date                 euroIntroDate = euroIntroCalendarDate.getTime();
    boolean inEuro;

    if (trDate.before(euroIntroDate)) {
      inEuro = false;
    }
    else {
      inEuro = true;
    }

    return inEuro;
  }
}
