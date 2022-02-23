/*
 * Stocks are: Shares (class Share), Dividends (class Dividend), ...
 */

package goedegep.finan.stocks;

public class Stock {
  /*
   * Stocks are related to a Fund or Company.
   */

  /*
   * Each Stock has a unique Id.
   */

  // Stock ID's
  // Just for practical reasons the ID's are grouped by shares and dividends

  /*
  // Shares
  public static final short   STOCK_ID_MIN                            = 50;
  public static final short   STOCK_ABN_AMRO_HOLDING_F0125            = 50;
  public static final short   STOCK_AHOLD_KON_F0050                   = 51;
  public static final short   STOCK_AHOLD_KON_0025                    = 52;
  public static final short   STOCK_ASML_0002                         = 53;
  public static final short   STOCK_GETRONICS_0004                    = 54;
  */
  public static final short   STOCK_HAGEMEYER                         = 55;
  /*
  public static final short   STOCK_HAGEMEYER_0120                    = 56;
  public static final short   STOCK_HAGEMEYER_F0250                   = 57;
  public static final short   STOCK_KPN_KON_0024                      = 58;
  public static final short   STOCK_LIBERTEL_F0064                    = 59;
  */
  public static final short   STOCK_NUTRECO_HOLDING_0024              = 60;
  /*
  public static final short   STOCK_PHILIPS_ELECTRONICS_KON_0020      = 61;
  public static final short   STOCK_POSTBANK_AANDELENFONDS_500_1      = 62;
  public static final short   STOCK_POSTBANK_AANDELENFONDS_F1000      = 63;
  public static final short   STOCK_POSTBANK_BELEGGINGSFONDS          = 64;
  public static final short   STOCK_POSTBANK_FINACIELE_WERELDFONDS    = 65;
  public static final short   STOCK_POSTBANK_MULTIMEDIAFONDS_0500     = 66;
  public static final short   STOCK_POSTBANK_WERELDMERKENFONDS_0080   = 67;
  public static final short   STOCK_POSTBANK_WERELDMERKENFONDS_F1000  = 68;
  public static final short   STOCK_POSTBANK_WERELDMERKENFONDS_E0008  = 69;
  public static final short   STOCK_TISCALI_ITL10_NL                  = 70;
  public static final short   STOCK_UNITED_PAN_EUR_COM_0100           = 71;
  public static final short   STOCK_WORLD_ONLINE_INTL_0040            = 72;
  public static final short   STOCK_ID_MAX                            = 73;
  */
  // namen van dividenden zitten voorlopig nog hier
  // als alleen nog XML als invoer gebruikt wordt, dan voor dividenden:
  // naam van het aandeel en bijv. div. jaar, interim, etc.
  // Dividends
  public static final short   STOCK_ABN_AMRO_HOLDING_DIV01            = 501;
  public static final short   STOCK_ABN_AMRO_HOLDING_DIV01_INTERIM    = 502;
  public static final short   STOCK_GETRONICS_DIV01                   = 503;
  public static final short   STOCK_HAGEMEYER_DIV97                   = 504;
  public static final short   STOCK_HAGEMEYER_DIV98                   = 505;
  public static final short   STOCK_HAGEMEYER_DIV01                   = 506;
  public static final short   STOCK_KON_KPN_INTERIM2000               = 507;
  public static final short   STOCK_KON_KPN_DIV01                     = 508;
  public static final short   STOCK_NUTRECO_HOLDING_DIV01             = 509;
  public static final short   STOCK_HAGEMEYER_DIV02                   = 510;
  public static final short   STOCK_ABN_AMRO_HOLDING_DIV02            = 511;
  public static final short   STOCK_AHOLD_DIV02                       = 512;
  public static final short   STOCK_NUTRECO_HOLDING_DIV02             = 513;
  public static final short   STOCK_ABN_AMRO_HOLDING_INTERIM_DIV02    = 514;
  public static final short   STOCK_AHOLD_DIV02_1_100E                = 515;

  /*
  static short[][] stockIdToFundIdTable = {
    {STOCK_ABN_AMRO_HOLDING_F0125,           Fund.FUND_ABN_AMRO_HOLDING},
    {STOCK_AHOLD_KON_F0050,                  Fund.FUND_AHOLD_KON},
    {STOCK_AHOLD_KON_0025,                   Fund.FUND_AHOLD_KON},
    {STOCK_ASML_0002,                        Fund.FUND_ASML_HOLDING},
    {STOCK_GETRONICS_0004,                   Fund.FUND_GETRONICS},
    {STOCK_HAGEMEYER,                        Fund.FUND_HAGEMEYER},
    {STOCK_HAGEMEYER_0120,                   Fund.FUND_HAGEMEYER},
    {STOCK_HAGEMEYER_F0250,                  Fund.FUND_HAGEMEYER},
    {STOCK_KPN_KON_0024,                     Fund.FUND_KPN_KON},
    {STOCK_LIBERTEL_F0064,                   Fund.FUND_LIBERTEL},
    {STOCK_NUTRECO_HOLDING_0024,             Fund.FUND_NUTRECO_HOLDING},
    {STOCK_PHILIPS_ELECTRONICS_KON_0020,     Fund.FUND_PHILIPS_ELECTRONICS_KON},
    {STOCK_POSTBANK_AANDELENFONDS_500_1,     Fund.FUND_POSTBANK_AANDELENFONDS},
    {STOCK_POSTBANK_AANDELENFONDS_F1000,     Fund.FUND_POSTBANK_AANDELENFONDS},
    {STOCK_POSTBANK_BELEGGINGSFONDS,         Fund.FUND_POSTBANK_BELEGGINGSFONDS},
    {STOCK_POSTBANK_FINACIELE_WERELDFONDS,   Fund.FUND_POSTBANK_FINANCIELE_WERELDFONDS},
    {STOCK_POSTBANK_MULTIMEDIAFONDS_0500,    Fund.FUND_POSTBANK_MULTIMEDIAFONDS},
    {STOCK_POSTBANK_WERELDMERKENFONDS_0080,  Fund.FUND_POSTBANK_WERELDMERKENFONDS},
    {STOCK_POSTBANK_WERELDMERKENFONDS_F1000, Fund.FUND_POSTBANK_WERELDMERKENFONDS},
    {STOCK_UNITED_PAN_EUR_COM_0100,          Fund.FUND_UNITED_PAN_EUR_COM},
    {STOCK_WORLD_ONLINE_INTL_0040,           Fund.FUND_WORLD_ONLINE_INTL}
  };

  static LinkedList _stocks = new LinkedList();

  short     _fundId;
  */
  //short     _stockId;
  /*
  public Stock(short stockId) {
    _stockId = stockId;
    _fundId = getFundIdForStockId(stockId);
  }
  */

  /*
  public short getId() {
    return _stockId;
  }
  */

  /*
  public static short getFundIdForStockId(short stockId) {
    for (int i = 0; i < stockIdToFundIdTable.length; i++) {
      if (stockIdToFundIdTable[i][0] == stockId) {
        return stockIdToFundIdTable[i][1];
      }
    }

    return -1;
  }
  */
}