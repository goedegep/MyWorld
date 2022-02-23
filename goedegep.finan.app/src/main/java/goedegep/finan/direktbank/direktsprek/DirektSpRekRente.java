package goedegep.finan.direktbank.direktsprek;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrencyFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DirektSpRekRente extends DirektSpRekTransaction {
  private static final DateTimeFormatter     DF =  DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private static final PgCurrencyFormat      CF =  new PgCurrencyFormat(0, false, false, true);
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();

  private LocalDate vanDatum = null;      // begin van de periode waarover verrekening plaats vindt
  private LocalDate totDatum = null;      // einde van de periode waarover verrekening plaats vindt
  
  public DirektSpRekRente(PgAccount  account) {
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
    StringBuilder output = new StringBuilder();

    // bedrag.
    output.append("bij ");
    output.append(CF.format(getTransactionAmount()));

    // rentedatum
    output.append(" rente ");
    if (vanDatum != null  &&  totDatum != null) {
      output.append("over de periode ");
      output.append(vanDatum.format(DF));
      output.append(" t/m ");
      output.append(totDatum.format(DF));
    } else {
      output.append("t/m " + getBookingDate().format(DF));
    }
    
    output.append(", huidig rentepercentage ");
    output.append(FPVF.format(this.getRentePercentage()));   // rentepercentage
    output.append(" %");
    
    return output.toString();
  }

  public String toString() {
    StringBuilder output = new StringBuilder();
    output.append("Rente");
    output.append("\t");
    output.append(CF.format(getTransactionAmount()));
    output.append("\t");
    output.append(FPVF.format(getRentePercentage()));
    if (vanDatum != null) {
      output.append("\tvan");
      output.append(vanDatum.format(DF));
    }
    if (totDatum != null) {
      output.append("\tt/m");
      output.append(totDatum.format(DF));
    }
    
    return output.toString();
  }

  @Override
  public void handle(List<TransactionError> errors) {
    DirektSpRek        account = (DirektSpRek) this.getAccount();

    handleFirst();

    // verhoog saldo met de rente, de investering blijft gelijk.
    account.increaseBalance(getTransactionAmount());

    handleLast();
  }
}