package goedegep.app.finan.finanapp.td;

import java.time.LocalDate;

import goedegep.app.finan.td.TransactionDialogComponentList;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.direktbank.direktsprek.DirektSpRekAfschrijving;
import goedegep.finan.direktbank.direktsprek.DirektSpRekBijschrijving;
import goedegep.finan.direktbank.direktsprek.DirektSpRekOpheffing;
import goedegep.finan.direktbank.direktsprek.DirektSpRekRente;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;


public class DirektSpRekTransactionConstructors {

  public static PgTransaction createDirektSpRekAfschrijving(FinanTransactionEntryStatus transactionEntryStatus) {
    DirektSpRekAfschrijving t = new DirektSpRekAfschrijving(transactionEntryStatus.getBankAccount());
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
      t.setRentePercentage(new FixedPointValue(renteAsLong, 100));
    }
    
    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }

    return t;
  }

  public static PgTransaction createDirektSpRekBijschrijving(FinanTransactionEntryStatus transactionEntryStatus) {
    DirektSpRekBijschrijving t = new DirektSpRekBijschrijving(transactionEntryStatus.getBankAccount());
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
      t.setRentePercentage(new FixedPointValue(renteAsLong, 100));
    }
    
    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }

    return t;
  }

  public static PgTransaction createDirektSpRekOpheffing(FinanTransactionEntryStatus transactionEntryStatus) {
    DirektSpRekOpheffing t = new DirektSpRekOpheffing(transactionEntryStatus.getBankAccount());
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
      t.setRentePercentage(new FixedPointValue(renteAsLong, 100));
    }
    
    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }

    return t;
  }

  public static PgTransaction createDirektSpRekRente(FinanTransactionEntryStatus transactionEntryStatus) {
    DirektSpRekRente t = new DirektSpRekRente(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Rentedatum (Boekingsdatum)
    t.setBookingDate((LocalDate) components.get("RenteDatumField").getValue());
    
    // Bedrag.
    PgCurrency bedrag = (PgCurrency) components.get("BedragField").getValue();
    bedrag = bedrag.certifyFactor(100);
    t.setTransactionAmount(bedrag);
    
    // Nieuw rente percentage
    if (components.get("NwRentePercentageField").isFilledIn()) {
      Long renteAsLong = (Long) components.get("NwRentePercentageField").getValue();
      t.setRentePercentage(new FixedPointValue(renteAsLong, 100));
    }
    
    // 'van'datum.
    if (components.get("VanDatumField").isFilledIn()) {
      t.setVanDatum((LocalDate) components.get("VanDatumField").getValue());
    }

    // 'tot'datum.
    if (components.get("TotDatumField").isFilledIn()) {
      t.setTotDatum((LocalDate) components.get("TotDatumField").getValue());
    }
    
    // Uitvoeringsdatum
    t.setExecutionDate((LocalDate) components.get("UitvoeringsDatumField").getValue());
    
    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//    System.out.println("<= createPbSpRekRente, transaction = " + t);

    return t;
  }
}