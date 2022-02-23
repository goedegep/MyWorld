package goedegep.finan.postbank.pbsprek;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrencyFormat;

public class PbSpRekRente extends PbSpRekTransaction {
  private static final DateTimeFormatter  DF =  DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private static final PgCurrencyFormat  CF =  new PgCurrencyFormat(0, false, false, true);
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();

  private LocalDate vanDatum = null;      // begin van de periode waarover verrekening plaats vindt
  private LocalDate totDatum = null;      // einde van de periode waarover verrekening plaats vindt
  
  public PbSpRekRente(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_RENTE;
  }

  public void setVanDatum(LocalDate vanDatum) {
    this.vanDatum = vanDatum;
  }

  public LocalDate getVanDatum() {
    return vanDatum;
  }

  public void setTotDatum(LocalDate totDatum) {
    this.totDatum = totDatum;
  }

  public LocalDate getTotDatum() {
    return totDatum;
  }
  
  public String getDescription() {
    PbSpRek       account = (PbSpRek) getAccount();
    StringBuilder output = new StringBuilder();

    // bedrag.
    output.append("bij ");
    output.append(CF.format(getTransactionAmount()));

    // rentedatum
    output.append(" rente ");
    if (vanDatum != null  &&  totDatum != null) {
      output.append("over de periode ");
      output.append(DF.format(vanDatum));
      output.append(" t/m ");
      output.append(DF.format(totDatum));
    } else {
      output.append("t/m " + DF.format(getBookingDate()));
    }
    
    if (account.hasVrijOpneembaarBedrag()) {
      output.append(", zonder korting opneembaar t/m ");
      LocalDate opneembaarTotDatum = totDatum.plusYears(1);
      output.append(opneembaarTotDatum.format(DF));
    }
    
    output.append(", huidig rentepercentage ");
    output.append(FPVF.format(this.rentePercentage));   // rentepercentage
    output.append(" %");
    
    return output.toString();
  }

  public String toString() {
    StringBuilder output = new StringBuilder();
    output.append("Rente");
    output.append("\t");
    output.append(CF.format(getTransactionAmount()));
    output.append("\t");
    output.append(FPVF.format(this.rentePercentage));
    if (vanDatum != null) {
      output.append("\tvan");
      output.append(DF.format(vanDatum));
    }
    if (totDatum != null) {
      output.append("\tt/m");
      output.append(DF.format(totDatum));
    }
    
    return output.toString();
  }

  @Override
  public void handle(List<TransactionError> errors) {
    PbSpRek        account = (PbSpRek) this.getAccount();

    handleFirst();

    // verhoog saldo met de rente, de investering blijft gelijk.
    account.increaseBalance(getTransactionAmount());

    handleLast();
  }
}
