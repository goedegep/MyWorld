package goedegep.finan.effrek;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;

import java.time.LocalDate;
import java.time.Month;

public abstract class EffRekTransactie extends PgTransaction {
  // Transaction types.
  public static final short TT_AANDELEN_TRANSACTIE             =  1;
  public static final short TT_AANPASSING_DEKKINGSVERPLICHTING =  2;
  public static final short TT_AFSCHRIJVING                    =  3;
  public static final short TT_BELASTING_OVERZICHT             =  4;
  public static final short TT_BEWAARLOON                      =  5;
  public static final short TT_BIJSCHRIJVING                   =  6;
  public static final short TT_BONUS_AANDELEN                  =  7;
  public static final short TT_CLAIM_RIGHTS_RECEIVED           =  8;
  public static final short TT_CLAIM_RIGHTS_TRANSACTION        =  9;
  public static final short TT_CORRECTIE                       = 10;
  public static final short TT_DIVIDEND                        = 11;
  public static final short TT_FRACTIEVERR_NWE_WAARDEN         = 12;
  public static final short TT_KWARTAAL_OVERZICHT              = 13;
  public static final short TT_OPTIE_EXPIRATIE                 = 14;
  public static final short TT_OPTIE_TRANSACTIE                = 15;
  public static final short TT_OVERNAME                        = 16;
  public static final short TT_REDENOMINATIE                   = 17;
  public static final short TT_RENTEVERREKENING                = 18;
  public static final short TT_STOCK_DIVIDEND                  = 19;
  public static final short TT_STOCK_DIV_TRANSACTIE            = 20;
  public static final short TT_TERUGBETALING                   = 21;
  public static final short TT_TERUGBETALING_RECHTEN           = 22;
  public static final short TT_VERW_CLAIM_RIGHTS               = 23;
  public static final short TT_VERW_STOCK_DIVIDEND             = 24;
  public static final short TT_MAAND_OVERZICHT                 = 25;

  public EffRekTransactie(PgAccount  account) {
    super(account);
  }

  public static boolean transactionInEuros(LocalDate trDate) {
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
