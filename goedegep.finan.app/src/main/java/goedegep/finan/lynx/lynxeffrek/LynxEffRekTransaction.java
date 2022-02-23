package goedegep.finan.lynx.lynxeffrek;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;

public abstract class LynxEffRekTransaction extends PgTransaction {
  //Transaction types.
  public static final short TT_AANDELEN_TRANSACTIE      =  1;
  public static final short TT_AFSCHRIJVING             =  2;
  public static final short TT_BELASTING_OVERZICHT      =  3;
  public static final short TT_BIJSCHRIJVING            =  4;
  public static final short TT_BONUS_AANDELEN           =  5;
  public static final short TT_CLAIM_RIGHTS_RECEIVED    =  6;
  public static final short TT_CLAIM_RIGHTS_TRANSACTION =  7;
  public static final short TT_CORRECTIE                =  8;
  public static final short TT_DIVIDEND                 =  9;
  public static final short TT_FRACTIEVERR_NWE_WAARDEN  = 10;
  public static final short TT_KWARTAAL_OVERZICHT       = 11;
  public static final short TT_OPTIE_EXPIRATIE          = 12;
  public static final short TT_OPTIE_TRANSACTIE         = 13;
  public static final short TT_OVERNAME                 = 14;
  public static final short TT_REDENOMINATIE            = 15;
  public static final short TT_RENTEVERREKENING         = 16;
  public static final short TT_STOCK_DIVIDEND           = 17;
  public static final short TT_STOCK_DIV_TRANSACTIE     = 18;
  public static final short TT_TERUGBETALING            = 19;
  public static final short TT_TERUGBETALING_RECHTEN    = 20;
  public static final short TT_VERW_CLAIM_RIGHTS        = 21;
  public static final short TT_VERW_STOCK_DIVIDEND      = 22;

  public LynxEffRekTransaction(PgAccount  account) {
    super(account);
  }
}