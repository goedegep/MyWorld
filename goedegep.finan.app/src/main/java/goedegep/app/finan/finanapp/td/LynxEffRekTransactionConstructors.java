package goedegep.app.finan.finanapp.td;


import java.time.LocalDate;

//TODO implement LynxEffRekVerwClaimRights

import goedegep.app.finan.td.TransactionDialogComponentList;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekAandelenTransactie;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekAfschrijving;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekBelastingOverzicht;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekBijschrijving;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekBonusAandelen;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekClaimRightsReceived;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekClaimRightsTransaction;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekDividend;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekFractieVerrekeningNieuweWaarden;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekKwartaalOverzicht;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekMaandOverzicht;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekOptieExpiratie;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekOvername;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekRenteverrekening;
import goedegep.finan.stocks.ClaimEmission;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.ShareDividend;
import goedegep.util.money.PgCurrency;


public class LynxEffRekTransactionConstructors {
  public static PgTransaction createLynxEffRekAandelenTransactie(FinanTransactionEntryStatus transactionEntryStatus) {
//  System.out.println("=> createLynxEffRekAandelenTransactie");
    LynxEffRekAandelenTransactie t = new LynxEffRekAandelenTransactie(transactionEntryStatus.getBankAccount());
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

    // uitvoeringsdatum.
    t.setExecutionDate((LocalDate) components.get("UitvoeringsDatumField").getValue());

//    // zonder kosten.
//    t.setZonderKosten((Boolean) components.get("ZonderKosten").getValue());
    
    // provisie
    PgCurrency orderKosten = (PgCurrency) components.get("OrderKostenField").getValue();
    if (orderKosten != null) {
      t.setOrderKosten(orderKosten);
    }

    // Uitvoeringstype (volledig of deeluitvoering).
    String uitvoeringsTypeAsString =
      (String) components.get("UitvoeringsType").getValue();
    short uitVoeringsType = 0;
    if (uitvoeringsTypeAsString.compareTo("volledig") == 0) {
      uitVoeringsType = LynxEffRekAandelenTransactie.UT_VOLLEDIG;
    } else if (uitvoeringsTypeAsString.compareTo("deeluitvoering (eerste)") == 0) {
      uitVoeringsType = LynxEffRekAandelenTransactie.UT_DEELUITVOERING_EERSTE;
    } else if (uitvoeringsTypeAsString.compareTo("deeluitvoering (laatste)") == 0) {
      uitVoeringsType = LynxEffRekAandelenTransactie.UT_DEELUITVOERING_LAATSTE;
    } else if (uitvoeringsTypeAsString.compareTo("deeluitvoering") == 0) {
      uitVoeringsType = LynxEffRekAandelenTransactie.UT_DEELUITVOERING;
    } else {
      System.out.println("ERROR: ongeldig uitvoeringstype: " + uitvoeringsTypeAsString);
    }
    t.setUitvoeringsType(uitVoeringsType);

    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//  System.out.println("<= createLynxEffRekAandelenTransactie, transaction = " + t);

    return t;
  }

  public static PgTransaction createLynxEffRekAfschrijving(FinanTransactionEntryStatus transactionEntryStatus) {
    LynxEffRekAfschrijving t = new LynxEffRekAfschrijving(transactionEntryStatus.getBankAccount());
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
//  System.out.println("<= LynxEffRekAfschrijving, transaction = " + t);

    return t;
  }

  public static PgTransaction createLynxEffRekBelastingOverzicht(FinanTransactionEntryStatus transactionEntryStatus) {
    LynxEffRekBelastingOverzicht t = new LynxEffRekBelastingOverzicht(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Jaar.
    t.setJaar((Integer) components.get("JaarField").getValue());

    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//  System.out.println("<= createLynxEffRekBelastingOverzicht, transaction = " + t);

    return t;
  }

  public static PgTransaction createLynxEffRekBijschrijving(FinanTransactionEntryStatus transactionEntryStatus) {
    LynxEffRekBijschrijving t = new LynxEffRekBijschrijving(transactionEntryStatus.getBankAccount());
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
//  System.out.println("<= LynxEffRekBijschrijving, transaction = " + t);

    return t;
  }

  public static PgTransaction createLynxEffRekBonusAandelen(FinanTransactionEntryStatus transactionEntryStatus) {
    LynxEffRekBonusAandelen t = new LynxEffRekBonusAandelen(transactionEntryStatus.getBankAccount());
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
//  System.out.println("<= createLynxEffRekBonusAandelen, transaction = " + t);

    return t;
  }

  public static PgTransaction createLynxEffRekClaimRightsReceived(FinanTransactionEntryStatus transactionEntryStatus) {
    LynxEffRekClaimRightsReceived t = new LynxEffRekClaimRightsReceived(transactionEntryStatus.getBankAccount());
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
//  System.out.println("<= LynxEffRekClaimRightsReceived, transaction = " + t);

    return t;
  }

  public static PgTransaction createLynxEffRekClaimRightsTransaction(FinanTransactionEntryStatus transactionEntryStatus) {
//  System.out.println("=> createLynxEffRekClaimRightsTransaction");
    LynxEffRekClaimRightsTransaction t = new LynxEffRekClaimRightsTransaction(transactionEntryStatus.getBankAccount());
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
//  System.out.println("<= createLynxEffRekClaimRightsTransaction, transaction = " + t);

    return t;
  }

  public static PgTransaction createLynxEffRekDividend(FinanTransactionEntryStatus transactionEntryStatus) {
//  System.out.println("=> createLynxEffRekDividend");

    LynxEffRekDividend t = new LynxEffRekDividend(transactionEntryStatus.getBankAccount());
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

//  System.out.println("<= createLynxEffRekDividend, transaction = " + t);

    return t;
  }

  public static void createLynxEffRekFractieVerrekeningNieuweWaarden(FinanTransactionEntryStatus transactionEntryStatus) {
    LynxEffRekFractieVerrekeningNieuweWaarden t = new LynxEffRekFractieVerrekeningNieuweWaarden(transactionEntryStatus.getBankAccount());
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
//  System.out.println("<= LynxEffRekFractieVerrekeningNieuweWaarden, transaction = " + t);
  }

  public static PgTransaction createLynxEffRekMaandOverzicht(FinanTransactionEntryStatus transactionEntryStatus) {
    LynxEffRekMaandOverzicht t = new LynxEffRekMaandOverzicht(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Jaar.
    t.setJaar((Integer) components.get("JaarField").getValue());

    // Kwartaal.
    t.setMaand((Integer) components.get("MaandField").getValue());

    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//  System.out.println("<= createLynxEffRekMaandOverzicht, transaction = " + t);

    return t;
  }

  public static PgTransaction createLynxEffRekKwartaalOverzicht(FinanTransactionEntryStatus transactionEntryStatus) {
    LynxEffRekKwartaalOverzicht t = new LynxEffRekKwartaalOverzicht(transactionEntryStatus.getBankAccount());
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
//  System.out.println("<= createLynxEffRekKwartaalOverzicht, transaction = " + t);

    return t;
  }

  public static PgTransaction createLynxEffRekOptieExpiratie(FinanTransactionEntryStatus transactionEntryStatus) {
    LynxEffRekOptieExpiratie t = new LynxEffRekOptieExpiratie(transactionEntryStatus.getBankAccount());
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
//  System.out.println("<= createLynxEffRekOptieExpiratie, transaction = " + t);

    return t;
  }

  public static PgTransaction createLynxEffRekOvername(FinanTransactionEntryStatus transactionEntryStatus) {
//  System.out.println("=> createLynxEffRekOvername");
    LynxEffRekOvername t = new LynxEffRekOvername(transactionEntryStatus.getBankAccount());
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
//  System.out.println("<= createLynxEffRekOvername, transaction = " + t);

    return t;
  }

  public static PgTransaction createLynxEffRekRenteverrekening(FinanTransactionEntryStatus transactionEntryStatus) {
    LynxEffRekRenteverrekening t = new LynxEffRekRenteverrekening(transactionEntryStatus.getBankAccount());
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
//  System.out.println("<= LynxEffRekRenteverrekening, transaction = " + t);

    return t;
  }
}