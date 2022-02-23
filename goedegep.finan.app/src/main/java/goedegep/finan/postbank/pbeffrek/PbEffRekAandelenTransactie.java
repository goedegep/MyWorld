package goedegep.finan.postbank.pbeffrek;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.finan.effrek.EffRekAandelenTransactie;
import goedegep.util.money.PgCurrency;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class PbEffRekAandelenTransactie extends EffRekAandelenTransactie {
  private boolean                    viaBeursorderlijn = false;     // transactie via de beursorderlijn (i.p.v. via internet)
  private boolean                    betalingViaGiro = false;      // betaling via giro

  public PbEffRekAandelenTransactie(PgAccount  account) {
    super(account);
  }

  public boolean isViaBeursorderlijn() {
    return viaBeursorderlijn;
  }

  public void setViaBeursorderlijn(boolean viaBeursorderlijn) {
    this.viaBeursorderlijn = viaBeursorderlijn;
  }

  public void setVerrekeningViaGiro(boolean verrekeningViaGiro) {
    betalingViaGiro = verrekeningViaGiro;
  }

  public boolean verrekeningViaGiro() {
    return betalingViaGiro;
  }

  public String getDescription() {
    StringBuilder      output = new StringBuilder();

    PgCurrency effectenPrijs = getKoers().multiply(getAantalEffecten()).certifyFactor(100);
    PgCurrency totaalBedrag = effectenPrijs;

    // transactie kosten: alleen als aankoop ongedeeld is, of laatste deel van deeluitvoering en niet 'zonder kosten'.
    short uitvoeringsType = getUitvoeringsType();
    PgCurrency orderKosten = null;
    if (!isZonderKosten()  &&
        (uitvoeringsType == PbEffRekAandelenTransactie.UT_VOLLEDIG  || uitvoeringsType == PbEffRekAandelenTransactie.UT_DEELUITVOERING_LAATSTE)) {
        orderKosten = berekenOrderKosten();
    }

    if (!verrekeningViaGiro()) {
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
    }

    if (isAankoop()) {
      output.append("aankoop ");
    } else {
      output.append("verkoop ");
    }

    if (isViaBeursorderlijn()) {
      output.append("via beursorderlijn ");
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

    // betaling via giro
    if (verrekeningViaGiro()) {
      output.append(" verrekening via girorekening");
    }

    if (uitvoeringsType != PbEffRekAandelenTransactie.UT_VOLLEDIG) {
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

    if (isViaBeursorderlijn()) {
      output.append(" via beursorderlijn");
    }
      
    // zonder kosten
    if (isZonderKosten()) {
      output.append(" zonder kosten");
    }

    // betaling via giro
    if (betalingViaGiro) {
      output.append(" (giro)");
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
    PbEffRek        effectenRekening = (PbEffRek) this.getAccount();
    PgCurrency      effectenPrijs;  // totale prijs van de effecten
    PgCurrency      totaalBedrag;   // totaalbedrag inclusief kosten

    // converteer het saldo bij introductie van de euro
    if (transactionInEuros(getBookingDate())  &&  (effectenRekening.getBalance().getCurrency() != PgCurrency.EURO)) {
      effectenRekening.certifyCurrency(PgCurrency.EURO);
      effectenRekening.getVerzamelDepot().convertToEuros();
    }

    // prijs van de aandelen
    effectenPrijs = getKoers().multiply(getAantalEffecten()).certifyFactor(100);

    if (isAankoop()) {
      // transactie kosten: alleen als aankoop ongedeeld is, of laatste deel van deeluitvoering en geen emissie
      if (!isZonderKosten()  &&  (getUitvoeringsType() == UT_VOLLEDIG  || getUitvoeringsType() == UT_DEELUITVOERING_LAATSTE)) {
        totaalBedrag = effectenPrijs.add(berekenOrderKosten());
      } else {
        totaalBedrag = effectenPrijs;
      }

      // verminder het saldo met het totaalbedrag, behalve bij betaling via giro
      if (!betalingViaGiro) {
        effectenRekening.decreaseBalance(totaalBedrag);
      } else {
        // verhoog nettoStorting
        effectenRekening.increaseNettoStorting(totaalBedrag);
      }

      // voeg de aandelen toe aan het verzameldepot
      effectenRekening.getVerzamelDepot().effectenToevoegen(getBookingDate(), getEffect(), getAantalEffecten(), getKoers(), totaalBedrag);
    } else {
      // verkoop
      // transactie kosten: alleen als verkoop ongedeeld is, of laatste deel van deeluitvoering
      if (!isZonderKosten()  &&  (getUitvoeringsType() == UT_VOLLEDIG  || getUitvoeringsType() == UT_DEELUITVOERING_LAATSTE)) {
        totaalBedrag = effectenPrijs.subtract(berekenOrderKosten());
      } else {
        totaalBedrag = effectenPrijs;
      }

      // verhoog het saldo met het totaalbedrag
      effectenRekening.increaseBalance(totaalBedrag);

      // verwijder de aandelen uit het verzameldepot
      effectenRekening.getVerzamelDepot().effectenVerwijderen(getBookingDate(), getEffect(), getAantalEffecten(), getKoers(), totaalBedrag);
    }

    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }

  public PgCurrency berekenOrderKosten() {
    // Er zijn alleen transactiekosten als aankooop/verkoop ongedeeld is, of als het het laatste deel van deeluitvoering is.
    if ((getUitvoeringsType() != UT_VOLLEDIG)  &&  (getUitvoeringsType() != UT_DEELUITVOERING_LAATSTE)) {
      return new PgCurrency(getCurrency(), 0L);
    }
    
    // Als de orderkosten gegeven zijn, gaat dat voor.
    if (getOrderKosten() != null) {
      return getOrderKosten();
    }
    
    return berekenKosten(KostenStructuur.getKostenStructuur(getExecutionDate(), isViaBeursorderlijn()));
  }
    
  PgCurrency berekenKosten(KostenStructuur kostenStructuur) {
    PgCurrency bedragEersteSchijf = kostenStructuur.getBedragEersteSchijf();
    Long percentageEersteSchijf = kostenStructuur.getPercentageEersteSchijf();
    Long percentageRestant = kostenStructuur.getPercentageRestant();
    PgCurrency maximumKosten = kostenStructuur.getMaximumKosten();
    Long maximumKostenPercentage = kostenStructuur.getMaximumKostenPercentage();
    PgCurrency bedrag = totaleOrderWaarde();
    PgCurrency restantBedrag = bedrag;

    PgCurrency kosten = kostenStructuur.getVasteKosten();
    PgCurrency procentueleKosten = null;  // Wordt gebruikt om meer dan een keer afronden te voorkomen.

    if (bedragEersteSchijf != null) {
      if (restantBedrag.isGreaterThan(bedragEersteSchijf)) {
        restantBedrag = restantBedrag.subtract(bedragEersteSchijf);
        procentueleKosten = bedragEersteSchijf.multiply(percentageEersteSchijf);
      } else {
        procentueleKosten = restantBedrag.multiply(percentageEersteSchijf);
        restantBedrag = new PgCurrency(getCurrency(), 0L);
      }
    } else {
      procentueleKosten = new PgCurrency(getCurrency(), 0L);
    }
    
    if (percentageRestant != null  &&  !restantBedrag.isZero()) {
      procentueleKosten = procentueleKosten.add(restantBedrag.multiply(percentageRestant));
    }
    
    kosten = kosten.add(procentueleKosten.divide(10000L));
    
    if (maximumKosten != null) {
      kosten = PgCurrency.min(kosten, maximumKosten);
    }
    
    if (maximumKostenPercentage != null) {
      kosten = PgCurrency.min(kosten, bedrag.multiply(maximumKostenPercentage).divide(10000));
    }
    
    return kosten;
  }
  
  private enum KostenStructuur {
    KOSTEN_GULDENS(null, LocalDate.of(1998, Month.DECEMBER, 31), false,
        new PgCurrency(PgCurrency.GUILDER, 2000L), new PgCurrency(PgCurrency.GUILDER, 1500000L), 60L,
        35L, null, 5000L),
    KOSTEN_VANAF_JAN1999(LocalDate.of(1999, Month.JANUARY, 1),  LocalDate.of(2001, Month.NOVEMBER, 30), false,
        new PgCurrency(PgCurrency.EURO, 600L), new PgCurrency(PgCurrency.EURO, 675000L), 45L,
        35L, null, 5000L),
    KOSTEN_VANAF_DEC2001(LocalDate.of(2001, Month.DECEMBER , 1), LocalDate.of(2005, Month.MARCH, 31), false,
        new PgCurrency(PgCurrency.EURO, 800L), new PgCurrency(PgCurrency.EURO, 6750000L), 40L,
        35L, null, 5000L),
    KOSTEN_VANAF_APR2005(LocalDate.of(2005, Month.APRIL, 1), null, false,
        new PgCurrency(PgCurrency.EURO, 1195L), null, null,
        null, null, 5000L),
    KOSTEN_BEURSORDERLIJN_EUROS(LocalDate.of(1999, Month.JANUARY, 1), null, true,
        new PgCurrency(PgCurrency.EURO, 1500L), null, null,
        40L, new PgCurrency(PgCurrency.EURO, 40000L), 5000L);

    private LocalDate ingangsDatum;              // Datum vanaf wanneer de structuur geldig is.
    private LocalDate vervalDatum;               // Datum vanaf wanneer de structuur niet meer geldig is.
    private boolean telefonisch;            // Structuur voor telefonische orders (i.p.v. via internet).
    private PgCurrency vasteKosten;         // Vaste kosten, bedrag is 0 als er geen vaste kosten zijn.
    private PgCurrency bedragEersteSchijf;  // Hoogte van de eerste schijf. null geeft aan dat er geen schijven zijn
                                            // (in dit geval is alleen percentageRestant van toepassing).
    private Long percentageEersteSchijf;    // Percentage kosten (in 0.01%) over de eerste schijf.
                                            // Als bedragEersteSchijf niet null is, is ook percentageEersteSchijf niet null.
    private Long percentageRestant;         // Percentage kosten (in 0.01%) over alles boven de eerste schijf.
                                            // null indien niet van toepassing.
    private PgCurrency maximumKosten;       // De maximale kosten. null geeft aan dat er geen maximum is.
    private Long maximumKostenPercentage;   // Maximum percentage (in 0.01%) van het totaalbedrag, dat aan kosten berekend wordt.
                                            // null indien niet van toepassing.
    
    KostenStructuur(PgCurrency vasteKosten) {
      this.vasteKosten = vasteKosten;
    }
    
    private KostenStructuur(LocalDate ingangsDatum, LocalDate vervalDatum, boolean telefonisch,
        PgCurrency vasteKosten, PgCurrency bedragEersteSchijf, Long percentageEersteSchijf,
        Long percentageRestant, PgCurrency maximumKosten,
        Long maximumKostenPercentage) {
      this.ingangsDatum = ingangsDatum;
      this.vervalDatum = vervalDatum;
      this.telefonisch = telefonisch;
      this.vasteKosten = vasteKosten;
      this.bedragEersteSchijf = bedragEersteSchijf;
      this.percentageEersteSchijf = percentageEersteSchijf;
      this.percentageRestant = percentageRestant;
      this.maximumKosten = maximumKosten;
      this.maximumKostenPercentage = maximumKostenPercentage;
    }

    public PgCurrency getVasteKosten() {
      return vasteKosten;
    }

    public PgCurrency getBedragEersteSchijf() {
      return bedragEersteSchijf;
    }

    public Long getPercentageEersteSchijf() {
      return percentageEersteSchijf;
    }

    public Long getPercentageRestant() {
      return percentageRestant;
    }

    public PgCurrency getMaximumKosten() {
      return maximumKosten;
    }

    public Long getMaximumKostenPercentage() {
      return maximumKostenPercentage;
    }
    
    public static KostenStructuur getKostenStructuur(LocalDate transactionDate, boolean telefonisch) {
      for (KostenStructuur kostenStructuur: values()) {
        if (kostenStructuur.telefonisch == telefonisch  &&
            (kostenStructuur.ingangsDatum == null  ||  !transactionDate.isBefore(kostenStructuur.ingangsDatum)) &&
            (kostenStructuur.vervalDatum == null  ||  !transactionDate.isAfter(kostenStructuur.vervalDatum))) {
          return kostenStructuur;
        }
      }
      
      throw new RuntimeException("Geen kostenstructuur voor opgegeven datum.");
    }
  }
}
