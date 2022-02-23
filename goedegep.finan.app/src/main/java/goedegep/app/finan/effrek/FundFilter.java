package goedegep.app.finan.effrek;

import goedegep.finan.basic.PgTransaction;
import goedegep.finan.basic.TransactionFilter;
import goedegep.finan.effrek.EffRekAandelenTransactie;
import goedegep.finan.effrek.EffRekBonusAandelen;
import goedegep.finan.effrek.EffRekClaimRightsReceived;
import goedegep.finan.effrek.EffRekClaimRightsTransaction;
import goedegep.finan.effrek.EffRekDividend;
import goedegep.finan.effrek.EffRekFractieVerrekeningNieuweWaarden;
import goedegep.finan.effrek.EffRekOptieTransactie;
import goedegep.finan.effrek.EffRekOvername;
import goedegep.finan.effrek.EffRekRedenominatie;
import goedegep.finan.effrek.EffRekStockDivTransactie;
import goedegep.finan.effrek.EffRekStockDividend;
import goedegep.finan.effrek.EffRekTerugBetaling;
import goedegep.finan.effrek.EffRekTerugBetalingRechten;
import goedegep.finan.effrek.EffRekTransactie;
import goedegep.finan.effrek.EffRekVerwClaimRights;
import goedegep.finan.effrek.EffRekVerwStockDiv;
import goedegep.finan.stocks.Fund;
import goedegep.finan.stocks.Share;

public class FundFilter implements TransactionFilter {
  private Fund fund;
  private Share share;
  
  public FundFilter(Fund fund) {
    this.fund = fund;
  }
  
  public FundFilter(Share share) {
    this.share = share;
    fund = share.getFund();
  }
  
  public boolean Filter(PgTransaction transaction) {
    switch (transaction.getTransactionType()) {
    case EffRekTransactie.TT_AANDELEN_TRANSACTIE:
      return isRequestedShare(((EffRekAandelenTransactie) transaction).getEffect());
      
    case EffRekTransactie.TT_BONUS_AANDELEN:
      return isRequestedShare(((EffRekBonusAandelen) transaction).getEffect());
      
    case EffRekTransactie.TT_CLAIM_RIGHTS_RECEIVED:
      return isRequestedShare(((EffRekClaimRightsReceived) transaction).getClaimEmission().getShare());
      
    case EffRekTransactie.TT_CLAIM_RIGHTS_TRANSACTION:
      return isRequestedShare(((EffRekClaimRightsTransaction) transaction).getClaimEmission().getShare());
      
    case EffRekTransactie.TT_DIVIDEND:
      return isRequestedShare(((EffRekDividend) transaction).getShareDividend().getShare());
      
    case EffRekTransactie.TT_FRACTIEVERR_NWE_WAARDEN:
      return isRequestedShare(((EffRekFractieVerrekeningNieuweWaarden) transaction).getEffect());
      
    case EffRekTransactie.TT_OPTIE_TRANSACTIE:
      return isRequestedShare(((EffRekOptieTransactie) transaction).getShare());
      
    case EffRekTransactie.TT_OVERNAME:
      return isRequestedShare(((EffRekOvername) transaction).getVanEffect())  ||
      isRequestedShare(((EffRekOvername) transaction).getNaarEffect());
      
    case EffRekTransactie.TT_REDENOMINATIE:
      return isRequestedShare(((EffRekRedenominatie) transaction).getNaarEffect())  ||
      isRequestedShare(((EffRekRedenominatie) transaction).getNaarEffect().getRedenominationFrom().getShare());
      
    case EffRekTransactie.TT_STOCK_DIV_TRANSACTIE:
      return isRequestedShare(((EffRekStockDivTransactie) transaction).getEffect());
      
    case EffRekTransactie.TT_STOCK_DIVIDEND:
      return isRequestedShare(((EffRekStockDividend) transaction).getEffect());
      
    case EffRekTransactie.TT_TERUGBETALING:
      return isRequestedShare(((EffRekTerugBetaling) transaction).getEffect());
      
    case EffRekTransactie.TT_TERUGBETALING_RECHTEN:
      return isRequestedShare(((EffRekTerugBetalingRechten) transaction).getEffect());
      
    case EffRekTransactie.TT_VERW_CLAIM_RIGHTS:
      return isRequestedShare(((EffRekVerwClaimRights) transaction).getClaimEmission().getShare());
      
    case EffRekTransactie.TT_VERW_STOCK_DIVIDEND:
      return isRequestedShare(((EffRekVerwStockDiv) transaction).getEffect());
      
    case EffRekTransactie.TT_AANPASSING_DEKKINGSVERPLICHTING:
    case EffRekTransactie.TT_AFSCHRIJVING:
    case EffRekTransactie.TT_BELASTING_OVERZICHT:
    case EffRekTransactie.TT_BEWAARLOON:
    case EffRekTransactie.TT_BIJSCHRIJVING:
    case EffRekTransactie.TT_CORRECTIE:
    case EffRekTransactie.TT_KWARTAAL_OVERZICHT:
    case EffRekTransactie.TT_OPTIE_EXPIRATIE:
    case EffRekTransactie.TT_RENTEVERREKENING:
      return false;
      
    default:
      throw new IllegalArgumentException("Onbekend transactie type");
    }
  }
  
  private boolean isRequestedShare(Share share) {
    if (this.share != null  &&  !this.share.equals(share)) {
      return false;
    }
    if (fund != null  && !fund.equals(share.getFund())) {
      return false;
    }
    
    return true;
  }

  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("Filter instelling: ");
    if (fund == null) {
      buf.append("alle fondsen.");
    } else {
      buf.append("fonds is ");
      buf.append(fund.getName());
      buf.append(", ");
      if (share == null) {
        buf.append("alle aandelen.");
      } else {
        buf.append("aandeel is ");
        buf.append(share.getName());
        buf.append(".");
      }
    }
    
    return buf.toString();
  }
}
