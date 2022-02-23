package goedegep.finan.stocks;

class CompanyBasics {
  // definitions used in the XML file
  public static final String CompanyTag = "Company";
  public static final String companiesTag = "Companies";
  public static final String companyNameTag = "CompanyName";
  public static final String companyFundsTag = "CompanyFunds";
  public static final String companyFundListTag = "CompanyFundList";
  public static final String fundTag = "Fund";
  public static final String fundNameTag = "FundName";
  public static final String fundSharesTag = "FundShares";
  public static final String fundSharesListTag = "FundShareList";
  public static final String shareTag = "Share";
  public static final String shareNameTag = "ShareName";
  public static final String shareDividendsTag = "ShareDividends";
  public static final String shareDividendsListTag = "ShareDividendsList";
  public static final String REDENOMINATION_FROM = "RedenominationFrom";
  public static final String FROM_AMOUNT = "FromAmount";
  public static final String TO_AMOUNT = "ToAmount";
  public static final String dividendTag = "Dividend";
  public static final String dividendTypeTag = "DividendType";
  public static final String dividendTypeContant = "Contant"; // for DividendType
  public static final String dividendTypeStock = "Stock";     // for DividendType
  public static final String dividendTypeContantOfStock = "ContantOfStock"; // for DividendType
  public static final String dividendTypeDrip = "Drip";     // for DividendType
  public static final String dividendNameTag = "DividendName";
  public static final String dividendYearTag = "DividendYear";
  public static final String dividendAmountTag = "DividendAmount";
  public static final String stockDividendTag = "StockDividend";
  public static final String dividendsPerShareTag = "DividendsPerShare";
  public static final String koersTag = "Koers";
  public static final String DRIP_TAG = "Drip";
  public static final String TAX_PERCENTAGE_TAG = "BelastingPercentage";
  public static final String TERUGBETALING_TAG = "TerugBetaling";
  public static final String OBSOLETE_TAG = "Obsolete";
  
  // definitions for Belasting Koersen.
  public static final String BELASTING_KOERSEN_TAG = "BelastingKoersen";
  public static final String KOERSEN_PER_KWARTAAL_TAG = "KoersenPerKwartaal";
  public static final String MAAND_TAG = "Maand";
  public static final String JAAR_TAG = "Jaar";
  public static final String KWARTAAL_TAG = "Kwartaal";
  public static final String KOERSEN_TAG = "Koersen";
  public static final String AANDEEL_KOERS_INFO_TAG = "AandeelKoersInfo";
  public static final String STOCKDIVIDEND_KOERS_INFO_TAG = "StockDividendKoersInfo";
  public static final String OPTIE_KOERS_INFO_TAG = "OptieKoersInfo";
  public static final String FONDS_NAAM_TAG = "FondsNaam";  
  public static final String TYPE_TAG = "Type";  
  public static final String AANDEEL_TAG = "Aandeel";  
  public static final String UITOEFENINGS_KOERS_TAG = "UitoefeningsKoers";
  public static final String DIVIDEND_REFERENTIE_TAG = "DividendReferentie";
  
  // definitions for Claim Emissions
  public static final String CLAIM_EMISSIONS_TAG = "ClaimEmissions";
  public static final String CLAIM_EMISSION_TAG = "ClaimEmission";
  public static final String CLAIM_ID_TAG = "ClaimId";
  public static final String PRICE_PER_SHARE_TAG = "PricePerShare";
  public static final String RIGHT_RATE_TAG = "RightRate";
  
  // definitions for option series
  public static final String OPTION_SERIES_TAG = "OptionSeries";
  public static final String OPTION_SERIE_TAG = "OptionSerie";
}
