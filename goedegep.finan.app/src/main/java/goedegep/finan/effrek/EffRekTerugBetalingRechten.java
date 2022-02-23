package goedegep.finan.effrek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.finan.stocks.Share;
import goedegep.util.money.PgCurrency;

public abstract class EffRekTerugBetalingRechten extends EffRekTransactie {
  private Share           effect = null;
  private PgCurrency      betalingPerEffect = null;
  private Integer         aantalRechten = null;

  public EffRekTerugBetalingRechten(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_TERUGBETALING_RECHTEN;
  }

  public Share getEffect() {
    return effect;
  }

  public void setEffect(Share effect) {
    this.effect = effect;
  }

  public PgCurrency getBetalingPerEffect() {
    return betalingPerEffect;
  }

  public void setBetalingPerEffect(PgCurrency betalingPerEffect) {
    this.betalingPerEffect = betalingPerEffect;
  }

  public Integer getAantalRechten() {
    return aantalRechten;
  }

  public void setAantalRechten(Integer aantalRechten) {
    this.aantalRechten = aantalRechten;
  }

  public String getDescription() {
    StringBuilder      output = new StringBuilder();

    output.append("Ontvangen:  ");
    output.append(aantalRechten);
    output.append(" ged. terugbetalingsrechten ");
    output.append(effect.getName());

    return output.toString();
  }

  public String toString() {
    return getDescription();
  }

  @Override
  public void handle(List<TransactionError> errors) {
    EffRek  effectenRekening = (EffRek) this.getAccount();

    effectenRekening.getVerzamelDepot().terugBetalingsRechtenToevoegen(effect, betalingPerEffect, aantalRechten);
    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }
}
