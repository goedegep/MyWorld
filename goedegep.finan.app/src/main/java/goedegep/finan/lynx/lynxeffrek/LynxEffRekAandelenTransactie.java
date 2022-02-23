package goedegep.finan.lynx.lynxeffrek;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.finan.effrek.EffRekAandelenTransactie;
import goedegep.util.money.PgCurrency;

import java.time.LocalDate;
import java.util.List;

public class LynxEffRekAandelenTransactie extends EffRekAandelenTransactie {

  public LynxEffRekAandelenTransactie(PgAccount  account) {
    super(account);
  }

  public String getDescription() {
    StringBuilder      output = new StringBuilder();

    if (getKoers() == null) {
      throw new RuntimeException("Koers ontbreekt in Lynx Effectenrekening Aandelen Transactie: " + toString());
    }
    PgCurrency effectenPrijs = getKoers().multiply(getAantalEffecten()).certifyFactor(100);
    PgCurrency totaalBedrag = effectenPrijs;

    // transactie kosten: alleen als uitvoering ongedeeld is, of laatste deel van deeluitvoering en niet 'zonder kosten'.
    short uitvoeringsType = getUitvoeringsType();
    PgCurrency orderKosten = null;
    if (!isZonderKosten()  &&
        (uitvoeringsType == LynxEffRekAandelenTransactie.UT_VOLLEDIG  || uitvoeringsType == LynxEffRekAandelenTransactie.UT_DEELUITVOERING_LAATSTE)) {
        orderKosten = LynxEffRekAandelenTransactie.orderKosten(totaleOrderWaarde(), getExecutionDate());
    }

    if (isAankoop()) {
      output.append("af  ");
      if (orderKosten != null) {
        totaalBedrag = totaalBedrag.add(orderKosten);
      }
    } else {
      output.append("bij ");
      if (orderKosten != null) {
        totaalBedrag = totaalBedrag.subtract(orderKosten);
      }
    }
    output.append(CF2.format(totaalBedrag) + " ");

    if (isAankoop()) {
      output.append("aankoop ");
    } else {
      output.append("verkoop ");
    }

    if (isZonderKosten()) {
      output.append("zonder kosten ");
    }

    output.append("aand: " + getEffect().getName() + ",  ");

    output.append(getAantalEffecten());
    if (getAantalEffecten() == 1) {
      output.append(" stuk, ");
    } else {
      output.append(" stuks, ");
    }

    output.append("koers: " + CF.format(getKoers()) + ", ");

    if (isAankoop()) {
      output.append("af:  ");
    } else {
      output.append("bij: ");
    }
    output.append(CF.format(effectenPrijs) + " ");

    if (orderKosten != null) {
      output.append(" provisie af: " + CF.format(orderKosten));
    }

    if (uitvoeringsType != LynxEffRekAandelenTransactie.UT_VOLLEDIG) {
      output.append(", dit betreft een deeluitvoering");
    }

    return output.toString();
  }

  
  public String toString() {
    StringBuffer      output = new StringBuffer(80);

    // transaction type
    if (isAankoop()) {
      output.append(AANKOOP_STRING);
    } else {
      output.append(VERKOOP_STRING);
    }

    // zonder kosten
    if (isZonderKosten()) {
      output.append(" zonder kosten");
    }

    switch (getUitvoeringsType()) {
    case UT_VOLLEDIG:
      // geen extra toevoeging
      break;

    case UT_DEELUITVOERING:
      output.append(" " + DEELUITVOERING_STRING);
      break;

    case UT_DEELUITVOERING_EERSTE:
      output.append(" " + DEELUITVOERING_EERSTE_STRING);
      break;

    case UT_DEELUITVOERING_LAATSTE:
      output.append(" " + DEELUITVOERING_LAATSTE_STRING);
      break;

    default:
      // kan niet voorkomen
      break;
    }

    output.append(" " + AANDEEL_STRING);
    if (getEffect() != null) {
      output.append(" " + getEffect().getName());
    } else {
      output.append(" " + "<ERROR: aandeel ontbreekt>");
    }
    output.append(" " + String.valueOf(getAantalEffecten()));
    if (getKoers() != null) {
      output.append(" " + CF.format(getKoers()));
    } else {
      output.append(" " + "<ERROR: koers ontbreekt>");
    }

    return output.toString();
  }

  @Override
  public void handle(List<TransactionError> errors) {
    LynxEffRek      effectenRekening = (LynxEffRek) this.getAccount();
    PgCurrency      effectenPrijs;  // totale prijs van de effecten
    PgCurrency      totaalBedrag;   // totaalbedrag inclusief kosten

    // prijs van de aandelen
    effectenPrijs = getKoers().multiply(getAantalEffecten()).certifyFactor(100);

    if (isAankoop()) {
      // transactie kosten: alleen als aankoop ongedeeld is, of laatste deel van deeluitvoering en geen emissie
      if (!isZonderKosten()  &&  (getUitvoeringsType() == UT_VOLLEDIG  || getUitvoeringsType() == UT_DEELUITVOERING_LAATSTE)) {
        totaalBedrag = effectenPrijs.add(orderKosten(totaleOrderWaarde(), getExecutionDate()));
      } else {
        totaalBedrag = effectenPrijs;
      }

      // verminder het saldo met het totaalbedrag
      effectenRekening.decreaseBalance(totaalBedrag);

      // voeg de aandelen toe aan het verzameldepot
      effectenRekening.getVerzamelDepot().effectenToevoegen(getBookingDate(), getEffect(), getAantalEffecten(), getKoers(), totaalBedrag);
    } else {
      // verkoop
      // transactie kosten: alleen als verkoop ongedeeld is, of laatste deel van deeluitvoering
      if (!isZonderKosten()  &&  (getUitvoeringsType() == UT_VOLLEDIG  || getUitvoeringsType() == UT_DEELUITVOERING_LAATSTE)) {
        totaalBedrag = effectenPrijs.subtract(orderKosten(totaleOrderWaarde(), this.getExecutionDate()));
      } else {
        totaalBedrag = effectenPrijs;
      }

      // verhoog het saldo met het totaalbedrag
      effectenRekening.increaseBalance(totaalBedrag);

      // verwijder de aandelen uit het verzameldepot
      effectenRekening.getVerzamelDepot().effectenVerwijderen(getBookingDate(), getEffect(), getAantalEffecten(), getKoers(), totaalBedrag);
    }

    effectenRekening.getVerzamelDepot().addTransactionToMonthlyReport(this);
    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }

  public static PgCurrency orderKosten(PgCurrency orderBedrag, LocalDate date) {
    double PROVISIE_PERCENTAGE = 0.14 / 100;
    PgCurrency orderKosten = orderBedrag.multiply(PROVISIE_PERCENTAGE);

    return orderKosten;
  }
}