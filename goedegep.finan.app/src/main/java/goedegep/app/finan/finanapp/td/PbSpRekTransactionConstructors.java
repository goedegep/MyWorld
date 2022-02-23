package goedegep.app.finan.finanapp.td;

import java.time.LocalDate;

import goedegep.app.finan.td.TransactionDialogComponentList;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.postbank.pbsprek.PbSpRekGeschenkInleg;
import goedegep.finan.postbank.pbsprek.PbSpRekOpheffing;
import goedegep.finan.postbank.pbsprek.PbSpRekOverschrijving;
import goedegep.finan.postbank.pbsprek.PbSpRekRente;
import goedegep.finan.postbank.pbsprek.PbSpRekRenteAanpassing;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;

public class PbSpRekTransactionConstructors {

  public static PgTransaction createPbSpRekOverschrijving(FinanTransactionEntryStatus transactionEntryStatus) {
    PbSpRekOverschrijving t = new PbSpRekOverschrijving(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Boekingsdatum (Rentedatum)
    t.setBookingDate((LocalDate) components.get("RenteDatumField").getValue());
    
    // Uitvoeringsdatum
    t.setExecutionDate((LocalDate) components.get("UitvoeringsDatumField").getValue());
    
    // Bedrag.
    t.setTransactionAmount((PgCurrency) components.get("BedragField").getValue());

    // Van of naar giro.
    if (((String) components.get("TransactieTypeField").getValue()).equals("bij")) {
      t.setBijschrijving(true);
//      t.setTransactionType(PbSpRekTransaction.VAN_GIRO);
    } else {
      t.setBijschrijving(false);
//      t.setTransactionType(PbSpRekTransaction.NAAR_GIRO);
      t.setZonderKosten((Boolean) components.get("ZonderKosten").getValue());
    }
    
    // Nieuw rente percentage
    if (components.get("NwRentePercentageField").isFilledIn()) {
      Long renteAsLong = (Long) components.get("NwRentePercentageField").getValue();
      FixedPointValue rentePercentage = new FixedPointValue(renteAsLong, 100);
      t.setRentePercentage(rentePercentage);
    }
    
    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//    System.out.println("<= createPbSpRekOverschrijving, transaction = " + t);

    return t;
  }

  public static PgTransaction createPbSpRekRente(FinanTransactionEntryStatus transactionEntryStatus) {
    PbSpRekRente t = new PbSpRekRente(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Boekingsdatum + Rentedatum
    t.setBookingDate((LocalDate) components.get("RenteDatumField").getValue());
    t.setExecutionDate((LocalDate) components.get("RenteDatumField").getValue());
    
    // Bedrag.
    PgCurrency bedrag = (PgCurrency) components.get("BedragField").getValue();
    bedrag = bedrag.certifyFactor(100);
    t.setTransactionAmount(bedrag);
    
    // Nieuw rente percentage
    if (components.get("NwRentePercentageField").isFilledIn()) {
      Long renteAsLong = (Long) components.get("NwRentePercentageField").getValue();
      FixedPointValue rentePercentage = new FixedPointValue(renteAsLong, 100);
      t.setRentePercentage(rentePercentage);
    }
    
    // 'van'datum.
    if (components.get("VanDatumField").isFilledIn()) {
      t.setVanDatum((LocalDate) components.get("VanDatumField").getValue());
    }

    // 'tot'datum.
    if (components.get("TotDatumField").isFilledIn()) {
      t.setTotDatum((LocalDate) components.get("TotDatumField").getValue());
    }
    
    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//    System.out.println("<= createPbSpRekRente, transaction = " + t);

    return t;
  }

  public static PgTransaction createPbSpRekRenteAanpassing(FinanTransactionEntryStatus transactionEntryStatus) {
    PbSpRekRenteAanpassing t = new PbSpRekRenteAanpassing(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Uitvoerings datum
    t.setExecutionDate((LocalDate) components.get("UitvoeringsDatumField").getValue());
    
    // Nieuw rente percentage
    if (components.get("NwRentePercentageField").isFilledIn()) {
      Long renteAsLong = (Long) components.get("NwRentePercentageField").getValue();
      FixedPointValue rentePercentage = new FixedPointValue(renteAsLong, 100);
      t.setRentePercentage(rentePercentage);
    }
    
    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//    System.out.println("<= createPbSpRekRenteAanpassing, transaction = " + t);

    return t;
  }

  public static PgTransaction createPbSpRekGeschenkInleg(FinanTransactionEntryStatus transactionEntryStatus) {
    PbSpRekGeschenkInleg t = new PbSpRekGeschenkInleg(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Boekingsdatum (Rentedatum)
    t.setBookingDate((LocalDate) components.get("RenteDatumField").getValue());
    
    // Uitvoeringsdatum
    t.setExecutionDate((LocalDate) components.get("UitvoeringsDatumField").getValue());
    
    // Bedrag.
    PgCurrency bedrag = (PgCurrency) components.get("BedragField").getValue();
    bedrag = bedrag.certifyFactor(100);  // TODO add parameter to Currency Component, for minimum factor.
    t.setTransactionAmount(bedrag);

    // Nieuw rente percentage
    if (components.get("NwRentePercentageField").isFilledIn()) {
      Long renteAsLong = (Long) components.get("NwRentePercentageField").getValue();
      FixedPointValue rentePercentage = new FixedPointValue(renteAsLong, 100);
      t.setRentePercentage(rentePercentage);
    }
    
    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//    System.out.println("<= createPbSpRekRenteAanpassing, transaction = " + t);

    return t;
  }

  public static PgTransaction createPbSpRekOpheffing(FinanTransactionEntryStatus transactionEntryStatus) {
    PbSpRekOpheffing t = new PbSpRekOpheffing(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Boekingsdatum (Rentedatum)
    t.setBookingDate((LocalDate) components.get("RenteDatumField").getValue());
    
    // Uitvoeringsdatum
    t.setExecutionDate((LocalDate) components.get("UitvoeringsDatumField").getValue());
    
    // Bedrag.
    t.setTransactionAmount((PgCurrency) components.get("BedragField").getValue());

    // Nieuw rente percentage
    if (components.get("NwRentePercentageField").isFilledIn()) {
      Long renteAsLong = (Long) components.get("NwRentePercentageField").getValue();
      FixedPointValue rentePercentage = new FixedPointValue(renteAsLong, 100);
      t.setRentePercentage(rentePercentage);
    }
    
    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
    System.out.println("<= createPbSpRekOpheffing, transaction = " + t);

    return t;
  }
}
