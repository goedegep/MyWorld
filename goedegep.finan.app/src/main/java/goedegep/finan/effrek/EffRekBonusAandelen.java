package goedegep.finan.effrek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.finan.stocks.Share;

public abstract class EffRekBonusAandelen extends EffRekTransactie {
  private Share   effect;                  // effect
  private int     aantalEffecten;          // aantal effecten

  public EffRekBonusAandelen(PgAccount  account) {
    super(account);
  }

  public short getTransactionType() {
    return TT_BONUS_AANDELEN;
  }

  public void setEffect(Share effect) {
    this.effect = effect;
  }

  public Share getEffect() {
    return effect;
  }

  public void setAantalEffecten(int aantalEffecten) {
    this.aantalEffecten = aantalEffecten;
  }

  public int getAantalEffecten() {
    return aantalEffecten;
  }

  public String getDescription() {
    StringBuilder      output = new StringBuilder();
    
    output.append("Ontvangen ");
    output.append(aantalEffecten);
    output.append(" bonus ");
    if (aantalEffecten == 1) {
      output.append(" aandeel ");
    } else {
      output.append(" aandelen ");
    }
    output.append(effect.getName());

    return output.toString();
  }
  
  public String toString() {
    return getDescription();
  }

  @Override
  public void handle(List<TransactionError> errors) {
    EffRek        effectenRekening = (EffRek) getAccount();

    // voeg de aandelen toe aan het verzameldepot
    effectenRekening.getVerzamelDepot().effectenToevoegen(getBookingDate(), effect, aantalEffecten, null, null);

    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }
}
