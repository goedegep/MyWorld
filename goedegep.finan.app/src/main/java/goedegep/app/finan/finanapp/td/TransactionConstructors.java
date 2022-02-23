package goedegep.app.finan.finanapp.td;

import java.time.LocalDate;

// TODO implement PbEffRekVerwClaimRights
// TODO implement PbEffRekStockDividend

import goedegep.app.finan.td.TransactionDialogComponentList;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.postbank.pbeffrek.PbEffRekAandelenTransactie;
import goedegep.finan.postbank.pbeffrek.PbEffRekAfschrijving;
import goedegep.finan.postbank.pbeffrek.PbEffRekBelastingOverzicht;
import goedegep.finan.postbank.pbeffrek.PbEffRekBewaarloon;
import goedegep.finan.postbank.pbeffrek.PbEffRekBijschrijving;
import goedegep.finan.postbank.pbeffrek.PbEffRekBonusAandelen;
import goedegep.finan.postbank.pbeffrek.PbEffRekClaimRightsReceived;
import goedegep.finan.postbank.pbeffrek.PbEffRekClaimRightsTransaction;
import goedegep.finan.postbank.pbeffrek.PbEffRekDividend;
import goedegep.finan.postbank.pbeffrek.PbEffRekFractieVerrekeningNieuweWaarden;
import goedegep.finan.postbank.pbeffrek.PbEffRekKwartaalOverzicht;
import goedegep.finan.postbank.pbeffrek.PbEffRekOptieExpiratie;
import goedegep.finan.postbank.pbeffrek.PbEffRekOvername;
import goedegep.finan.postbank.pbeffrek.PbEffRekRenteverrekening;
import goedegep.finan.stocks.ClaimEmission;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.ShareDividend;
import goedegep.util.money.PgCurrency;

public class TransactionConstructors {
  public static PgTransaction createPbEffRekAandelenTransactie(FinanTransactionEntryStatus transactionEntryStatus) {
//    System.out.println("=> createPbEffRekOverschrijving");
    PbEffRekAandelenTransactie t = new PbEffRekAandelenTransactie(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Boekingsdatum
    t.setBookingDate((LocalDate) components.get("RenteDatumField").getValue());

    // aankoop of verkoop.
    if (((String) components.get("TransactieTypeField").getValue()).equals("aankoop")) {
      t.setAankoop(true);
    } else {
      t.setAankoop(false);
    }

    // het aandeel.
    t.setEffect((Share) components.get("AandeelNaamField").getValue());

    // aantal aandelen.
    t.setAantalEffecten((Integer) components.get("AantalAandelenField").getValue());

    // koers.
    t.setKoers((PgCurrency) components.get("KoersField").getValue());

    // orderkosten
    PgCurrency orderKosten = (PgCurrency) components.get("OrderKostenField").getValue();
    if (orderKosten != null) {
      t.setOrderKosten(orderKosten);
    }

    // uitvoeringsdatum.
    t.setExecutionDate((LocalDate) components.get("UitvoeringsDatumField").getValue());

//    // zonder kosten.
//    t.setZonderKosten((Boolean) components.get("ZonderKosten").getValue());

    // provisie
    t.setOrderKosten((PgCurrency) components.get("OrderKostenField").getValue());

    // Uitvoeringstype (volledig of deeluitvoering).
    String uitvoeringsTypeAsString =
      (String) components.get("UitvoeringsType").getValue();
    short uitVoeringsType = 0;
    if (uitvoeringsTypeAsString.compareTo("volledig") == 0) {
      uitVoeringsType = PbEffRekAandelenTransactie.UT_VOLLEDIG;
    } else if (uitvoeringsTypeAsString.compareTo("deeluitvoering (eerste)") == 0) {
      uitVoeringsType = PbEffRekAandelenTransactie.UT_DEELUITVOERING_EERSTE;
    } else if (uitvoeringsTypeAsString.compareTo("deeluitvoering (laatste)") == 0) {
      uitVoeringsType = PbEffRekAandelenTransactie.UT_DEELUITVOERING_LAATSTE;
    } else if (uitvoeringsTypeAsString.compareTo("deeluitvoering") == 0) {
      uitVoeringsType = PbEffRekAandelenTransactie.UT_DEELUITVOERING;
    } else {
      System.out.println("ERROR: ongeldig uitvoeringstype: " + uitvoeringsTypeAsString);
    }
    t.setUitvoeringsType(uitVoeringsType);
    
    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//    System.out.println("<= createPbEffRekOverschrijving, transaction = " + t);

    return t;
  }

  public static PgTransaction createPbEffRekKwartaalOverzicht(FinanTransactionEntryStatus transactionEntryStatus) {
    PbEffRekKwartaalOverzicht t = new PbEffRekKwartaalOverzicht(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Jaar.
    t.setJaar((Integer) components.get("JaarField").getValue());
    
    // Kwartaal.
    t.setKwartaal((Integer) components.get("KwartaalField").getValue());
    
    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//    System.out.println("<= createPbEffRekKwartaalOverzicht, transaction = " + t);

    return t;
  }

  public static PgTransaction createPbEffRekBelastingOverzicht(FinanTransactionEntryStatus transactionEntryStatus) {
    PbEffRekBelastingOverzicht t = new PbEffRekBelastingOverzicht(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Jaar.
    t.setJaar((Integer) components.get("JaarField").getValue());
    
    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//    System.out.println("<= createPbEffRekBelastingOverzicht, transaction = " + t);

    return t;
  }

  public static PgTransaction createPbEffRekBewaarloon(FinanTransactionEntryStatus transactionEntryStatus) {
    PbEffRekBewaarloon t = new PbEffRekBewaarloon(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Boekingsdatum
    t.setBookingDate((LocalDate) components.get("RenteDatumField").getValue());

    // Bedrag.
    t.setTransactionAmount((PgCurrency) components.get("BedragField").getValue());
    
    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//    System.out.println("<= createPbEffRekBewaarloon, transaction = " + t);

    return t;
  }

  public static PgTransaction createPbEffRekOptieExpiratie(FinanTransactionEntryStatus transactionEntryStatus) {
    PbEffRekOptieExpiratie t = new PbEffRekOptieExpiratie(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Jaar.
    t.setExpirationMonth((Integer) components.get("MaandField").getValue());
    t.setExpirationYear((Integer) components.get("JaarField").getValue());

    // Boekingsdatum is expiratiedatum
    t.setBookingDate((LocalDate) components.get("RenteDatumField").getValue());
    
    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//    System.out.println("<= createPbEffRekBelastingOverzicht, transaction = " + t);

    return t;
  }
  
  public static PgTransaction createPbEffRekBonusAandelen(FinanTransactionEntryStatus transactionEntryStatus) {
    PbEffRekBonusAandelen t = new PbEffRekBonusAandelen(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Boekingsdatum
    t.setBookingDate((LocalDate) components.get("RenteDatumField").getValue());

    // het aandeel.
    t.setEffect((Share) components.get("AandeelNaamField").getValue());

    // aantal aandelen.
    t.setAantalEffecten((Integer) components.get("AantalAandelenField").getValue());

    // uitvoeringsdatum.
    t.setExecutionDate((LocalDate) components.get("UitvoeringsDatumField").getValue());

    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//    System.out.println("<= createPbEffRekBonusAandelen, transaction = " + t);

    return t;
  }

  public static PgTransaction createPbEffRekClaimRightsReceived(FinanTransactionEntryStatus transactionEntryStatus) {
    PbEffRekClaimRightsReceived t = new PbEffRekClaimRightsReceived(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Boekingsdatum
    t.setBookingDate((LocalDate) components.get("RenteDatumField").getValue());

    // de claim rechten.
    t.setClaimEmission((ClaimEmission) components.get("ClaimIdField").getValue());

    // aantal rechten.
    t.setNumberOfRights((Integer) components.get("AantalRechtenField").getValue());

    // uitvoeringsdatum.
    t.setExecutionDate((LocalDate) components.get("UitvoeringsDatumField").getValue());

    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//    System.out.println("<= PbEffRekClaimRightsReceived, transaction = " + t);

    return t;
  }
  
  public static PgTransaction createPbEffRekClaimRightsTransaction(FinanTransactionEntryStatus transactionEntryStatus) {
//    System.out.println("=> createPbEffRekClaimRightsTransaction");
    PbEffRekClaimRightsTransaction t = new PbEffRekClaimRightsTransaction(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Boekingsdatum
    t.setBookingDate((LocalDate) components.get("RenteDatumField").getValue());

    // aankoop of verkoop.
    if (((String) components.get("TransactieTypeField").getValue()).equals("aankoop")) {
      t.setBuy(true);
    } else {
      t.setBuy(false);
    }

    // de claimemissie.
    t.setClaimEmission((ClaimEmission) components.get("ClaimIdField").getValue());

    // aantal rechten.
    t.setNumberOfRights((Integer) components.get("AantalAandelenField").getValue());

    // uitvoeringsdatum.
    t.setExecutionDate((LocalDate) components.get("UitvoeringsDatumField").getValue());

    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//    System.out.println("<= createPbEffRekClaimRightsTransaction, transaction = " + t);

    return t;
  }

  public static PgTransaction createPbEffRekOvername(FinanTransactionEntryStatus transactionEntryStatus) {
//    System.out.println("=> createPbEffRekOvername");
    PbEffRekOvername t = new PbEffRekOvername(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Boekingsdatum
    t.setBookingDate((LocalDate) components.get("RenteDatumField").getValue());

    // van aandeel.
    t.setVanEffect((Share) components.get("VanAandeelNaamField").getValue());

    // van aantal aandelen.
    t.setVanAantal((Integer) components.get("VanAantalAandelenField").getValue());

    // naar aandeel.
    t.setNaarEffect((Share) components.get("NaarAandeelNaamField").getValue());

    // naar aantal aandelen.
    t.setNaarAantal((Integer) components.get("NaarAantalAandelenField").getValue());
    
    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//    System.out.println("<= createPbEffRekOvername, transaction = " + t);

    return t;
  }

  public static PgTransaction createPbEffRekOverschrijving(FinanTransactionEntryStatus transactionEntryStatus) {
//    PbEffRekOverschrijving t = new PbEffRekOverschrijving(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();
//
//    // Boekingsdatum
//    t.setBookingDate((Date) components.get("RenteDatumField").getValue());
//    
//    // Bedrag.
//    t.setTransactionAmount((PgCurrency) components.get("BedragField").getValue());

    // Van of naar giro.
    if (((String) components.get("TransactieTypeField").getValue()).equals("van giro")) {
      return createPbEffRekBijschrijving(transactionEntryStatus);
//      t.setVanGiro(true);
    } else {
      return createPbEffRekAfschrijving(transactionEntryStatus);
//      t.setVanGiro(false);
    }
    
//    // Comments
//    String comments = (String) components.get("OpmerkingenField").getValue();
//    if (comments.length() > 0) {
//      t.setComment(comments);
//    }
////    System.out.println("<= PbEffRekClaimRightsReceived, transaction = " + t);
//
//    return t;
  }

  public static PgTransaction createPbEffRekAfschrijving(FinanTransactionEntryStatus transactionEntryStatus) {
    PbEffRekAfschrijving t = new PbEffRekAfschrijving(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Boekingsdatum
    t.setBookingDate((LocalDate) components.get("RenteDatumField").getValue());
    
    // Bedrag.
    t.setTransactionAmount((PgCurrency) components.get("BedragField").getValue());

    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//    System.out.println("<= PbEffRekAfschrijving, transaction = " + t);

    return t;
  }

  public static PgTransaction createPbEffRekBijschrijving(FinanTransactionEntryStatus transactionEntryStatus) {
    PbEffRekBijschrijving t = new PbEffRekBijschrijving(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Boekingsdatum
    t.setBookingDate((LocalDate) components.get("RenteDatumField").getValue());
    
    // Bedrag.
    t.setTransactionAmount((PgCurrency) components.get("BedragField").getValue());

    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//    System.out.println("<= PbEffRekAfschrijving, transaction = " + t);

    return t;
  }

  public static PgTransaction createPbEffRekRenteverrekening(FinanTransactionEntryStatus transactionEntryStatus) {
    PbEffRekRenteverrekening t = new PbEffRekRenteverrekening(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Boekingsdatum
    t.setBookingDate((LocalDate) components.get("RenteDatumField").getValue());

    // 'van'datum.
    t.setVanDatum((LocalDate) components.get("VanDatumField").getValue());

    // 'tot'datum.
    t.setTotDatum((LocalDate) components.get("TotDatumField").getValue());

    // Credit rente.
    t.setCreditRente((PgCurrency) components.get("CreditRenteField").getValue());

    // Debet rente.
    t.setDebetRente((PgCurrency) components.get("DebetRenteField").getValue());

    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//    System.out.println("<= PbEffRekClaimRightsReceived, transaction = " + t);

    return t;
  }
  
  public static void createPbEffRekFractieVerrekeningNieuweWaarden(FinanTransactionEntryStatus transactionEntryStatus) {
    PbEffRekFractieVerrekeningNieuweWaarden t = new PbEffRekFractieVerrekeningNieuweWaarden(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Boekingsdatum
    t.setBookingDate((LocalDate) components.get("RenteDatumField").getValue());

    // aankoop of verkoop.
    if (((String) components.get("TransactieTypeField").getValue()).equals("aankoop")) {
      t.setAankoop(true);
    } else {
      t.setAankoop(false);
    }
    
    // het aandeel.
    t.setEffect((Share) components.get("AandeelNaamField").getValue());

    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//    System.out.println("<= PbEffRekClaimRightsReceived, transaction = " + t);
  }
  
  public static PgTransaction createPbEffRekDividend(FinanTransactionEntryStatus transactionEntryStatus) {
//    System.out.println("=> createPbEffRekDividend");
    
    PbEffRekDividend t = new PbEffRekDividend(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Boekingsdatum
    t.setBookingDate((LocalDate) components.get("RenteDatumField").getValue());

    // Het dividend.
    t.setShareDividend((ShareDividend) components.get("DividendField").getValue());
    
    // Aantal.
    t.setAantalEffecten((Integer) components.get("AantalAandelenField").getValue());
    
    // Dividend datum
    t.setExecutionDate((LocalDate) components.get("UitvoeringsDatumField").getValue());

    // zonder kosten.
    t.setZonderKosten((Boolean) components.get("ZonderKosten").getValue());

    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
    
//    System.out.println("<= createPbEffRekDividend, transaction = " + t);

    return t;
  }
}
