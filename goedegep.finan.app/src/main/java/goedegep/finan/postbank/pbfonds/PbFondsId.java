package goedegep.finan.postbank.pbfonds;

import goedegep.finan.stocks.Fund;


public enum PbFondsId {
  AANDELENFONDS("Postbank Aandelenfonds", Fund.fundPostbankAandelenfondsName),
  BELEGGINGSFONDS("Postbank Beleggingsfonds", Fund.fundPostbankBeleggingsfondsName),
  WERELDMERKENFONDS("Postbank Financiele Wereldfonds", Fund.fundPostbankFinancieleWereldfondsName),
  FINANCIELE_WERELDFONDS("Postbank WereldMerkenfonds", Fund.fundPostbankWereldmerkenfondsName);

  private String pbFundName; // Naam van het Postbank fonds.
  private String fundName;   // Naam van het fonds behorend bij het aandeel.
  
  
  private PbFondsId(String pbFundName, String fundName) {
    this.pbFundName = pbFundName;
    this.fundName = fundName;
  }
  
  public String getPbFundName() {
    return pbFundName;
  }
  
  public String getFundName() {
    return fundName;
  }
  
  public static PbFondsId getIdForPbFundName(String name) {
    for (PbFondsId id: values()) {
      if (id.pbFundName.equals(name)) {
        return id;
      }
    }
    
    return null;
  }
}
