package goedegep.app.finan.finanapp.td;

import java.time.LocalDate;

import goedegep.app.finan.td.TransactionDialogComponentList;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRekAandelenTransactie;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRekAanpassingDekkingsVerplichting;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRekAfschrijving;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRekBelastingOverzicht;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRekBewaarloon;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRekBijschrijving;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRekDividend;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRekOptieExpiratie;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRekOptieTransactie;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRekRenteverrekening;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.stocks.OptieTransactieType;
import goedegep.finan.stocks.OptionType;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.ShareDividend;
import goedegep.util.money.PgCurrency;

public class AAEffRekTransactionConstructors {
  public static PgTransaction createAAEffRekAandelenTransactie(FinanTransactionEntryStatus transactionEntryStatus) {
//  System.out.println("=> createAAEffRekOverschrijving");
    AAEffRekAandelenTransactie t = new AAEffRekAandelenTransactie(transactionEntryStatus.getBankAccount());
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

    // Uitvoeringstype (volledig of deeluitvoering).
    String uitvoeringsTypeAsString =
      (String) components.get("UitvoeringsType").getValue();
    short uitVoeringsType = 0;
    if (uitvoeringsTypeAsString.compareTo("volledig") == 0) {
      uitVoeringsType = AAEffRekAandelenTransactie.UT_VOLLEDIG;
    } else if (uitvoeringsTypeAsString.compareTo("deeluitvoering (eerste)") == 0) {
      uitVoeringsType = AAEffRekAandelenTransactie.UT_DEELUITVOERING_EERSTE;
    } else if (uitvoeringsTypeAsString.compareTo("deeluitvoering (laatste)") == 0) {
      uitVoeringsType = AAEffRekAandelenTransactie.UT_DEELUITVOERING_LAATSTE;
    } else if (uitvoeringsTypeAsString.compareTo("deeluitvoering") == 0) {
      uitVoeringsType = AAEffRekAandelenTransactie.UT_DEELUITVOERING;
    } else {
      System.out.println("ERROR: ongeldig uitvoeringstype: " + uitvoeringsTypeAsString);
    }
    t.setUitvoeringsType(uitVoeringsType);

    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//  System.out.println("<= AAEffRekAandelenTransactie, transaction = " + t);

    return t;
  }

  public static PgTransaction createAAEffRekAanpassingDekkingsVerplichting(FinanTransactionEntryStatus transactionEntryStatus) {
    AAEffRekAanpassingDekkingsVerplichting t = new AAEffRekAanpassingDekkingsVerplichting(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Boekingsdatum
    t.setBookingDate((LocalDate) components.get("RenteDatumField").getValue());

    // aankoop of verkoop.
    PgCurrency bedrag = (PgCurrency) components.get("BedragField").getValue();
    if (((String) components.get("TransactieTypeField").getValue()).equals("naar dekkingsrekening")) {
      t.setTransactionAmount(bedrag);
    } else {
      t.setTransactionAmount(bedrag.changeSign());
    }

    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//  System.out.println("<= AAEffRekAanpassingDekkingsVerplichting, transaction = " + t);

    return t;
  }

  public static PgTransaction createAAEffRekAfschrijving(FinanTransactionEntryStatus transactionEntryStatus) {
    AAEffRekAfschrijving t = new AAEffRekAfschrijving(transactionEntryStatus.getBankAccount());
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

  public static PgTransaction createAAEffRekBelastingOverzicht(FinanTransactionEntryStatus transactionEntryStatus) {
    AAEffRekBelastingOverzicht t = new AAEffRekBelastingOverzicht(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Jaar.
    t.setJaar((Integer) components.get("JaarField").getValue());

    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//  System.out.println("<= createAAEffRekBelastingOverzicht, transaction = " + t);

    return t;
  }

  public static PgTransaction createAAEffRekBewaarloon(FinanTransactionEntryStatus transactionEntryStatus) {
    AAEffRekBewaarloon t = new AAEffRekBewaarloon(transactionEntryStatus.getBankAccount());
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
//  System.out.println("<= createAAEffRekBewaarloon, transaction = " + t);

    return t;
  }

  public static PgTransaction createAAEffRekBijschrijving(FinanTransactionEntryStatus transactionEntryStatus) {
    AAEffRekBijschrijving t = new AAEffRekBijschrijving(transactionEntryStatus.getBankAccount());
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

//public static PgTransaction createAAEffRekBonusAandelen(FinanTransactionEntryStatus transactionEntryStatus) {
//  AAEffRekBonusAandelen t = new AAEffRekBonusAandelen(transactionEntryStatus.getBankAccount());
//  TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();
//
//  // Boekingsdatum
//  t.setBookingDate((Date) components.get("RenteDatumField").getValue());
//
//  // het aandeel.
//  t.setEffect((Share) components.get("AandeelNaamField").getValue());
//
//  // aantal aandelen.
//  t.setAantalEffecten((Integer) components.get("AantalAandelenField").getValue());
//
//  // uitvoeringsdatum.
//  t.setExecutionDate((Date) components.get("UitvoeringsDatumField").getValue());
//
//  // Comments
//  String comments = (String) components.get("OpmerkingenField").getValue();
//  if (comments.length() > 0) {
//    t.setComment(comments);
//  }
////System.out.println("<= createAAEffRekBonusAandelen, transaction = " + t);
//
//  return t;
//}

//public static PgTransaction createAAEffRekClaimRightsReceived(FinanTransactionEntryStatus transactionEntryStatus) {
//  AAEffRekClaimRightsReceived t = new AAEffRekClaimRightsReceived(transactionEntryStatus.getBankAccount());
//  TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();
//
//  // Boekingsdatum
//  t.setBookingDate((Date) components.get("RenteDatumField").getValue());
//
//  // de claim rechten.
//  t.setClaimEmission((ClaimEmission) components.get("ClaimIdField").getValue());
//
//  // aantal rechten.
//  t.setNumberOfRights((Integer) components.get("AantalRechtenField").getValue());
//
//  // uitvoeringsdatum.
//  t.setExecutionDate((Date) components.get("UitvoeringsDatumField").getValue());
//
//  // Comments
//  String comments = (String) components.get("OpmerkingenField").getValue();
//  if (comments.length() > 0) {
//    t.setComment(comments);
//  }
////System.out.println("<= AAEffRekClaimRightsReceived, transaction = " + t);
//
//  return t;
//}

//public static PgTransaction createAAEffRekClaimRightsTransaction(FinanTransactionEntryStatus transactionEntryStatus) {
////System.out.println("=> createAAEffRekClaimRightsTransaction");
//  AAEffRekClaimRightsTransaction t = new AAEffRekClaimRightsTransaction(transactionEntryStatus.getBankAccount());
//  TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();
//
//  // Boekingsdatum
//  t.setBookingDate((Date) components.get("RenteDatumField").getValue());
//
//  // aankoop of verkoop.
//  if (((String) components.get("TransactieTypeField").getValue()).equals("aankoop")) {
//    t.setBuy(true);
//  } else {
//    t.setBuy(false);
//  }
//
//  // de claimemissie.
//  t.setClaimEmission((ClaimEmission) components.get("ClaimIdField").getValue());
//
//  // aantal rechten.
//  t.setNumberOfRights((Integer) components.get("AantalAandelenField").getValue());
//
//  // uitvoeringsdatum.
//  t.setExecutionDate((Date) components.get("UitvoeringsDatumField").getValue());
//
//  // Comments
//  String comments = (String) components.get("OpmerkingenField").getValue();
//  if (comments.length() > 0) {
//    t.setComment(comments);
//  }
////System.out.println("<= createAAEffRekClaimRightsTransaction, transaction = " + t);
//
//  return t;
//}

  public static PgTransaction createAAEffRekDividend(FinanTransactionEntryStatus transactionEntryStatus) {
//  System.out.println("=> createAAEffRekDividend");

    AAEffRekDividend t = new AAEffRekDividend(transactionEntryStatus.getBankAccount());
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

//  System.out.println("<= createAAEffRekDividend, transaction = " + t);

    return t;
  }

//public static void createAAEffRekFractieVerrekeningNieuweWaarden(FinanTransactionEntryStatus transactionEntryStatus) {
//  AAEffRekFractieVerrekeningNieuweWaarden t = new AAEffRekFractieVerrekeningNieuweWaarden(transactionEntryStatus.getBankAccount());
//  TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();
//
//  // Boekingsdatum
//  t.setBookingDate((Date) components.get("RenteDatumField").getValue());
//
//  // aankoop of verkoop.
//  if (((String) components.get("TransactieTypeField").getValue()).equals("aankoop")) {
//    t.setAankoop(true);
//  } else {
//    t.setAankoop(false);
//  }
//
//  // het aandeel.
//  t.setEffect((Share) components.get("AandeelNaamField").getValue());
//
//  // Comments
//  String comments = (String) components.get("OpmerkingenField").getValue();
//  if (comments.length() > 0) {
//    t.setComment(comments);
//  }
////System.out.println("<= AAEffRekClaimRightsReceived, transaction = " + t);
//}

//  public static PgTransaction createAAEffRekKwartaalOverzicht(FinanTransactionEntryStatus transactionEntryStatus) {
//    AAEffRekKwartaalOverzicht t = new AAEffRekKwartaalOverzicht(transactionEntryStatus.getBankAccount());
//    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();
//
//    // Jaar.
//    t.setJaar((Integer) components.get("JaarField").getValue());
//
//    // Kwartaal.
//    t.setKwartaal((Integer) components.get("KwartaalField").getValue());
//
//    // Comments
//    String comments = (String) components.get("OpmerkingenField").getValue();
//    if (comments.length() > 0) {
//      t.setComment(comments);
//    }
////  System.out.println("<= createAAEffRekKwartaalOverzicht, transaction = " + t);
//
//    return t;
//  }

  public static PgTransaction createAAEffRekOptieExpiratie(FinanTransactionEntryStatus transactionEntryStatus) {
    AAEffRekOptieExpiratie t = new AAEffRekOptieExpiratie(transactionEntryStatus.getBankAccount());
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
//  System.out.println("<= createAAEffRekBelastingOverzicht, transaction = " + t);

    return t;
  }

  public static PgTransaction createAAEffRekOptieTransactie(FinanTransactionEntryStatus transactionEntryStatus) {
//  System.out.println("=> createAAEffRekOptieTransactie");
    AAEffRekOptieTransactie t = new AAEffRekOptieTransactie(transactionEntryStatus.getBankAccount());
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();

    // Boekingsdatum
    t.setBookingDate((LocalDate) components.get("RenteDatumField").getValue());

    // aankoop of verkoop.
    String description = (String) components.get("TransactieTypeField").getValue();
    t.setOptieTransactieType(OptieTransactieType.getOptieTransactieTypeVoorDescription(description));

    // het aandeel.
    String optionType = (String) components.get("OptieType").getValue();
    t.setOptionType(OptionType.getOptionTypeForText(optionType));
    t.setShare((Share) components.get("AandeelNaamField").getValue());

    // expiratie jaar + maand, en uitoefeningskoers.
    t.setExpirationMonth((Integer) components.get("MaandField").getValue());
    t.setExpirationYear((Integer) components.get("JaarField").getValue());
    t.setUitoefeningsKoers((PgCurrency) components.get("UitoefeningsKoersField").getValue());

    // aantal aandelen.
    t.setAantalContracten((Integer) components.get("AantalAandelenField").getValue());

    // koers.
    t.setKoers((PgCurrency) components.get("KoersField").getValue());

    // orderkosten
    PgCurrency orderKosten = (PgCurrency) components.get("OrderKostenField").getValue();
    if (orderKosten != null) {
      t.setProvisie(orderKosten);
    }
    
    // uitvoeringsdatum.
    t.setExecutionDate((LocalDate) components.get("UitvoeringsDatumField").getValue());

    // Comments
    String comments = (String) components.get("OpmerkingenField").getValue();
    if (comments.length() > 0) {
      t.setComment(comments);
    }
//  System.out.println("<= AAEffRekOptieTransactie, transaction = " + t);

    return t;
  }

//  public static PgTransaction createAAEffRekOvername(FinanTransactionEntryStatus transactionEntryStatus) {
////  System.out.println("=> createAAEffRekOvername");
//    AAEffRekOvername t = new AAEffRekOvername(transactionEntryStatus.getBankAccount());
//    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();
//
//    // Boekingsdatum
//    t.setBookingDate((Date) components.get("RenteDatumField").getValue());
//
//    // van aandeel.
//    t.setVanEffect((Share) components.get("VanAandeelNaamField").getValue());
//
//    // van aantal aandelen.
//    t.setVanAantal((Integer) components.get("VanAantalAandelenField").getValue());
//
//    // naar aandeel.
//    t.setNaarEffect((Share) components.get("NaarAandeelNaamField").getValue());
//
//    // naar aantal aandelen.
//    t.setNaarAantal((Integer) components.get("NaarAantalAandelenField").getValue());
//
//    // Comments
//    String comments = (String) components.get("OpmerkingenField").getValue();
//    if (comments.length() > 0) {
//      t.setComment(comments);
//    }
////  System.out.println("<= createAAEffRekOvername, transaction = " + t);
//
//    return t;
//  }

  public static PgTransaction createAAEffRekRenteverrekening(FinanTransactionEntryStatus transactionEntryStatus) {
    AAEffRekRenteverrekening t = new AAEffRekRenteverrekening(transactionEntryStatus.getBankAccount());
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
//  System.out.println("<= AAEffRekClaimRightsReceived, transaction = " + t);

    return t;
  }
}
