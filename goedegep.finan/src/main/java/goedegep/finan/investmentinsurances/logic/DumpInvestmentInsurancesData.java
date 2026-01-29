package goedegep.finan.investmentinsurances.logic;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;

import goedegep.finan.investmentinsurance.model.AnnualStatement;
import goedegep.finan.investmentinsurance.model.ExtraInvestment;
import goedegep.finan.investmentinsurance.model.FundChange;
import goedegep.finan.investmentinsurance.model.InsuranceCompany;
import goedegep.finan.investmentinsurance.model.InvestmentFund;
import goedegep.finan.investmentinsurance.model.InvestmentInsurance;
import goedegep.finan.investmentinsurance.model.InvestmentInsurancesData;
import goedegep.finan.investmentinsurance.model.InvestmentPart;
import goedegep.finan.investmentinsurance.model.Participation;
import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.City;
import goedegep.rolodex.model.Gender;
import goedegep.rolodex.model.Person;
import goedegep.rolodex.model.PhoneNumber;
import goedegep.types.model.Event;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;
import goedegep.util.text.TextWriter;

public class DumpInvestmentInsurancesData {
  private static final Logger LOGGER = Logger.getLogger(DumpInvestmentInsurancesData.class.getName());

  private static final PgCurrencyFormat CF = new PgCurrencyFormat(11);
  private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private static final FlexDateFormat FDF = new FlexDateFormat();
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();
  
  // Kolommen voor Fondswijziging
  private static final int FONDSWIJZIGING_PARTICIPATIES_POS = 32;
  private static final int FONDSWIJZIGING_KOERS_POS = 54;
  private static final int FONDSWIJZIGING_WAARDE_POS = 68;
  private static final int FONDSWIJZIGING_RENDEMENT_POS = 82;
  private static final int FONDSWIJZIGING_TER_POS = 110;
  
  // Kolommen voor Afsluitgegevens
  private static final int AFSLUITING_GEGEVENS_POS = 14;
  
  // Kolommen voor Aanwending beleggingsdeel
  private static final int VERDELINGS_PERCENTAGE_POS = 24;
  
  // Kolommen voor Waardeontwikkeling
  private static final int WAARDEONTWIKKELING_KOSTENSOORT_POS = 18;
  private static final int WAARDEONTWIKKELING_KOSTEN_POS = 48;
  private static final int WAARDEONTWIKKELING_POS = 60;
  
  // Kolommen voor Participatieoverzicht
  private static final int AANTAL_PARTICIPATIES_POS = 22;
  private static final int KOERS_POS = 45;
  private static final int FONDSWAARDE_POS = 60;
  
  // Kolommen voor aangekochte- en verkochte participaties
  private static final int PARTICIPATIES_BIJ_POS = 22;
  private static final int PARTICIPATIES_AF_POS = 45;
  
  // Kolommen voor Voorbeeldkapitalen
  private static final int PERCENTAGE_POS = 38;
  private static final int VOORBEELDKAPITAAL_POS = 48;
  private static final int BEREKEND_VOORBEELDKAPITAAL_POS = 88;
  
  // Kolommen voor Total Expense Ratio's
  private static final int TER_POS = 22;
  
  private static final String[] UITLEG_VERZEKERDE_UITKERING = {
    "Verzekerde uitkering A",
    "De verzekerde uitkering is het uit te keren bedrag bij overlijden van de verzekerde en",
    "betaalbaar terstond na overlijden",
    "Indien de guldenswaarde vermeerderd met 10% daarvan, van de",
    "ten behoeve van de verzekeringnemer uitstaande participaties",
    "in de door de verzekeringnemer aangewezen fondsen op de",
    "overlijdensdatum van de verzekerde hoger is dan het in de",
    "vorige volzin genoemde bedrag, wordt dit hogere bedrag uitgekeerd."
  };

  
  public static void dumpData(InvestmentInsurancesData investmentInsurancesData, TextWriter out) throws IOException {
    out.writeString(0, "BELEGGINGSVERZEKERINGEN", 2);
    for (InvestmentInsurance investmentInsurance: investmentInsurancesData.getInvestmentInsurances()) {
      dumpInvestmentInsurance(out, investmentInsurance);
    }
    
    PgCurrency lastKnownTotalValue = new PgCurrency(0l);
    for (InvestmentInsurance investmentInsurance: investmentInsurancesData.getInvestmentInsurances()) {
      AnnualStatement annualStatement = investmentInsurance.getLastAnnualStatement();
      if (annualStatement != null) {
        PgCurrency valueAtYearStart = annualStatement.valueAtYearStart();
        if (valueAtYearStart != null) {
          lastKnownTotalValue = lastKnownTotalValue.add(annualStatement.valueAtYearStart());
        }
      }
    }
    out.newLine();
    out.newLine();
    out.write("Totale waarde: ");
    out.write(CF.format(lastKnownTotalValue));
  }

  private static void dumpInvestmentInsurance(TextWriter out, InvestmentInsurance investmentInsurance) throws IOException {
    LOGGER.info("=> investmentInsurance=" + investmentInsurance.getPolicyNumber());
    out.write("VERZEKERING ");
    out.write(investmentInsurance.getPolicyNumber());
    out.newLine();
    out.newLine();
    dumpClosingData(out, investmentInsurance);
    out.newLine();
    dumpEvents(out, investmentInsurance);
  }

  private static void dumpClosingData(TextWriter out, InvestmentInsurance investmentInsurance) throws IOException {
    out.write("AFSLUITING VERZEKERING");
    out.newLine();
    
    out.writeString(0, "Polisnummer:");
    out.writeString(AFSLUITING_GEGEVENS_POS, investmentInsurance.getPolicyNumber(), 1);
    
    out.writeString(0, "Ingangsdatum:");
    out.writeString(AFSLUITING_GEGEVENS_POS, investmentInsurance.getStartingDate().format(DF), 1);
    
    out.writeString(0, "Maatschappij:");
    out.writeString(AFSLUITING_GEGEVENS_POS, investmentInsurance.getInsuranceCompany().getName(), 1);
    
    if (investmentInsurance.isSetInsuredBenefitOnDeath()) {
      out.newLine();
      out.writeTextBlock(UITLEG_VERZEKERDE_UITKERING, false);
      PgCurrency insuredCapitalOnDeath = investmentInsurance.getInsuredBenefitOnDeath();
      out.writeString(0, CF.format(insuredCapitalOnDeath));
      
      if (insuredCapitalOnDeath.getCurrency() != PgCurrency.EURO) {
        insuredCapitalOnDeath = insuredCapitalOnDeath.changeCurrency(PgCurrency.EURO);
        out.writeStringOffset(5, "(" + CF.format(insuredCapitalOnDeath) + ")");
      }
      out.newLine();
    }
    
    if (investmentInsurance.isSetPremium()) {
      out.newLine();
      out.write("Premie");
      out.newLine();
      PgCurrency premie = investmentInsurance.getPremium();
      out.writeString(0, CF.format(premie));
      
      if (premie.getCurrency() != PgCurrency.EURO) {
        premie = premie.changeCurrency(PgCurrency.EURO);
        out.writeStringOffset(5, "(" + CF.format(premie) + ")");
      }
      out.newLine();
    }
    
    out.newLine();
    out.write("Aanwending beleggingsdeel");
    out.newLine();
    out.write("Het beleggingsdeel zal als volgt worden aangewend.");
    out.newLine();
    out.writeString(0, "Fondsen");
    out.writeString(VERDELINGS_PERCENTAGE_POS, "Verdeling", 1);
    for (InvestmentPart investmentPart: investmentInsurance.getInvestmentParts()) {
      out.writeString(0, investmentPart.getInvestmentFund().getName());
      out.writeString(VERDELINGS_PERCENTAGE_POS, FPVF.format(investmentPart.getPercentage()) + "%", 1);
    }
  }

  private static void dumpEvents(TextWriter out, InvestmentInsurance investmentInsurance) throws IOException {
    out.write("JAAROPGAVEN, EXTRA INLEGGINGEN EN FONDSWIJZIGINGEN");
    out.newLine();
    
    for (Event event: investmentInsurance.getEvents()) {
      if (event instanceof FundChange) {
        dumpFundChange(out, investmentInsurance, (FundChange) event);
      } else if (event instanceof AnnualStatement) {
        dumpAnnualStatement(out, investmentInsurance, (AnnualStatement) event);
      } else if (event instanceof ExtraInvestment) {
        dumpExtraInvestment(out, investmentInsurance, (ExtraInvestment) event);
      } else {
        throw new IllegalArgumentException("Unknown event type: " + event.getClass().getName());
      }
    }
  }

  private static void dumpFundChange(TextWriter out, InvestmentInsurance investmentInsurance, FundChange fundChange) throws IOException {
    out.newLine();
    out.writeString(0, "FONDS WIJZIGING", 1);
    
    out.writeString(0, "Datum aanpassing:");
    if (fundChange.isSetDate()) {
      out.writeStringOffset(2, FDF.format(fundChange.getDate()), 1);
    }
    if (fundChange.isSetNotes()) {
      out.writeString(0, fundChange.getNotes(), 1);
    }
    
    out.newLine();
    out.writeString(0, "Situatie voor de wijziging", 1);
    
    // Header
    out.writeString(0, "Beleggingsfonds");
    out.writeString(FONDSWIJZIGING_PARTICIPATIES_POS, "Aantal participaties");
    out.writeString(FONDSWIJZIGING_KOERS_POS, "Koers");
    out.writeString(FONDSWIJZIGING_WAARDE_POS, "Fonds waarde");
    out.writeString(FONDSWIJZIGING_RENDEMENT_POS, "Gem. hist. rendement %");
    out.writeString(FONDSWIJZIGING_TER_POS, "TER %", 1);
    
    // 'Van' info
    out.writeString(0, fundChange.getFromFund().getName());
    out.writeString(FONDSWIJZIGING_PARTICIPATIES_POS, FPVF.format(fundChange.getFromNumberOfParticipations()));
    if (fundChange.isSetFromStockPrice()) {
      out.writeString(FONDSWIJZIGING_KOERS_POS, CF.format(fundChange.getFromStockPrice()));
      out.writeString(FONDSWIJZIGING_WAARDE_POS, CF.format(fundChange.getFromStockPrice().multiply(fundChange.getFromNumberOfParticipations().doubleValue()).changeFactor(100)));
    }
    if (fundChange.isSetFromAverageHistoricReturnOnInvestment()) {
      out.writeString(FONDSWIJZIGING_RENDEMENT_POS, FPVF.format(fundChange.getFromAverageHistoricReturnOnInvestment()));
    }
    if (fundChange.isSetFromTER()) {
      out.writeString(FONDSWIJZIGING_TER_POS, FPVF.format(fundChange.getFromTER()));
    }
    out.newLine();
    
    out.newLine();
    out.writeString(0, "Situatie na de wijziging", 1);
    
    // Header
    out.writeString(0, "Beleggingsfonds");
    out.writeString(FONDSWIJZIGING_PARTICIPATIES_POS, "Aantal participaties");
    out.writeString(FONDSWIJZIGING_KOERS_POS, "Koers");
    out.writeString(FONDSWIJZIGING_WAARDE_POS, "Fonds waarde");
    out.writeString(FONDSWIJZIGING_RENDEMENT_POS, "Gem. hist. rendement %");
    out.writeString(FONDSWIJZIGING_TER_POS, "TER %", 1);
    
    // 'Naar' info
    out.writeString(0, fundChange.getToFund().getName());
    out.writeString(FONDSWIJZIGING_PARTICIPATIES_POS, FPVF.format(fundChange.getToNumberOfParticipations()));
    if (fundChange.isSetToStockPrice()) {
      out.writeString(FONDSWIJZIGING_KOERS_POS, CF.format(fundChange.getToStockPrice()));
      out.writeString(FONDSWIJZIGING_WAARDE_POS, CF.format(fundChange.getToStockPrice().multiply(fundChange.getToNumberOfParticipations().doubleValue()).changeFactor(100)));
    }
    if (fundChange.isSetToAverageHistoricReturnOnInvestment()) {
      out.writeString(FONDSWIJZIGING_RENDEMENT_POS, FPVF.format(fundChange.getToAverageHistoricReturnOnInvestment()));
    }
    if (fundChange.isSetToTER()) {
      out.writeString(FONDSWIJZIGING_TER_POS, FPVF.format(fundChange.getToTER()));
    }
    out.newLine();
    out.newLine();
  }

  public static void dumpAnnualStatement(TextWriter out, InvestmentInsurance investmentInsurance, AnnualStatement annualStatement) throws IOException {
    LOGGER.info("=> year=" + FDF.format(annualStatement.getDate()));
    out.newLine();
    InsuranceCompany insuranceCompany = investmentInsurance.getInsuranceCompany();
    out.writeString(0, "JAAROPGAVE " + annualStatement.getPeriodUntil().getYear());
    out.writeString(WAARDEONTWIKKELING_POS, insuranceCompany.getName(), 2);
    
    dumpInsuranceCompanyInfo(out, investmentInsurance);
    
    out.newLine();
    out.newLine();
    
    dumpPolicyHolderInfo(out, investmentInsurance);
    out.newLine();
    
    String dateString = null;
    FlexDate date = annualStatement.getDate();
    if (date != null) {
      dateString = FDF.format(date);
    }
    if (dateString == null) {
      dateString = "<geen datum>";
    }
    out.write("datum: " + dateString);
    out.newLine();
    
    out.write("polisnummer: " + investmentInsurance.getPolicyNumber());
    out.newLine();
    String startingDateString = null;
    LocalDate ingangsDatum = investmentInsurance.getStartingDate();
    if (ingangsDatum != null) {
      startingDateString = ingangsDatum.format(DF);
    }
    if (startingDateString == null) {
      startingDateString = "<geen ingangsdatum>";
    }
    out.write("ingangsdatum verzekering: " + startingDateString);
    out.newLine();
    out.newLine();
    
    dumpInvestmentFundInfo(out, annualStatement);

    dumpAnnualStatementValueDevelopment(out, annualStatement);
    out.newLine();
    dumpAnnualStatementParticipationOverview(out, annualStatement.getPeriodFrom(), annualStatement.getPeriodUntil(), annualStatement.getParticipations());
    out.newLine();
    dumpAnnualStatementParticipationMutationOverview(out, annualStatement);
    out.newLine();
    dumpJaarOpgaveVoorbeeldWaarden(out, annualStatement);
    out.newLine();
    dumpAnnualStatementTotalExpenseRatios(out, annualStatement);
  }

  private static void dumpInsuranceCompanyInfo(TextWriter out, InvestmentInsurance investmentInsurance) throws IOException {
    InsuranceCompany insuranceCompany = investmentInsurance.getInsuranceCompany();
    
    String department;
    if (insuranceCompany.isSetDepartment()) {
      department = insuranceCompany.getDepartment();
    } else {
      department = "<geen afdeling bekend>";
    }
    out.write(department);
    out.newLine();
    
    Address address = insuranceCompany.getAddress();
    String poBox = null;
    String postalCode = null;
    City city = null;
    String cityName = null;
    if (address != null) {
       poBox = address.getPOBox();
       postalCode = address.getPostalCode();
       city = address.getCity();
    }
    if (poBox == null) {
      poBox = "<geen postbus bekend>";
    }
    if (postalCode == null) {
      postalCode = "<geen postcode bekend>";
    }
    if (city != null) {
      cityName = city.getCityName();
      LOGGER.fine("cityName = " + cityName);
    }
    if (cityName == null) {
      cityName = "<geen plaatsnaam bekend>";
    }
    out.write("Postbus " + poBox);
    
    out.newLine();
    out.write(postalCode + "  "  + cityName);
    out.newLine();
    out.write(insuranceCompany.getWebsite());
    out.newLine();
    String phoneNumber = null;
    EList<PhoneNumber> phoneNumbers = insuranceCompany.getPhoneNumbers();
    if (phoneNumbers.size() != 0) {
      phoneNumber = phoneNumbers.get(0).getPhoneNumber();
    }
    if (phoneNumber == null) {
      phoneNumber = "<geen telefoonnummer>";
    }
    out.write("tel.: " + phoneNumber);
  }

  private static void dumpPolicyHolderInfo(TextWriter out, InvestmentInsurance investmentInsurance) throws IOException {
    String title = null;
    String initials = null;
    String surname = null;
    Person policyHolder = null;
    if (investmentInsurance.isSetPolicyHolder()) {
      policyHolder = investmentInsurance.getPolicyHolder();
      Gender gender = policyHolder.getGender();
      if (gender != null) {
        switch (gender) {
        case FEMALE:
          title = "Mevrouw";
          break;
          
        case MALE:
          title = "De heer";
          break;
          
        default:
          throw new RuntimeException("Unsupported gender");
        }
      }
      initials = policyHolder.getInitials();
      surname = policyHolder.getSurname();
    }
    if (title == null) {
      title = "<geen title>";
    }
    if (initials == null) {
      initials = "<geen initialen bekend>";
    }
    if (surname == null) {
      surname = "<geen achternaam bekend>";
    }    
    out.write(title + " " + initials + " " + surname);
    out.newLine();
    
    String streetName = null;
    String houseNumber = null;
    String postalCode = null;
    String cityName = null;
    
    if (policyHolder != null) {
      Address policyHolderAddress = policyHolder.getAddress();
      if (policyHolderAddress != null) {
        streetName = policyHolderAddress.getStreetName();
        houseNumber = String.valueOf(policyHolderAddress.getHouseNumber());
        postalCode = policyHolderAddress.getPostalCode();
        City policyHolderCity = policyHolderAddress.getCity();
        if (policyHolderCity != null) {
          cityName = policyHolderCity.getCityName();
        }
      }
    }
    if (streetName == null) {
      streetName = "<geen straatnaam>";
    }
    if (houseNumber == null) {
      houseNumber = "<geen huisnummer>";
    }
    if (postalCode == null) {
      postalCode = "<geen postcode>";
    }
    if (cityName == null) {
      cityName = "<geen plaatsnaam>";
    }
    out.write(streetName + " " + houseNumber);
    out.newLine();
    out.write(postalCode + " " + cityName);
    out.newLine();
  }

  private static void dumpInvestmentFundInfo(TextWriter out, AnnualStatement annualStatement) throws IOException {
    out.write("FONDSINFORMATIE");
    out.newLine();
    
    for (Participation participation: annualStatement.getParticipations()) {
      InvestmentFund investmentFund = participation.getInvestmentFund();
      out.write("Fonds: " + investmentFund.getName());
      out.newLine();
      
      if (investmentFund.isSetFundInformation()) {
        out.write(investmentFund.getFundInformation());
        out.newLine();
      }
    }
  }

  private static void dumpAnnualStatementValueDevelopment(TextWriter out, AnnualStatement annualStatement) throws IOException {
    out.newLine();
    out.write("WAARDEONTWIKKELING");
    out.newLine();
   
    if (!annualStatement.isSetPeriodFrom()) {
      out.write("----");
      out.newLine();
      
      return;
    }

    out.write("Periode: ");
    out.write(annualStatement.getPeriodFrom().format(DF));
    out.write(" tot ");
    out.write(annualStatement.getPeriodUntil().format(DF));
    out.newLine();
    
    out.writeString(0, "Saldo per " + annualStatement.getPeriodFrom().format(DF));
    PgCurrency valueAtYearStart = annualStatement.valueAtYearStart();
    out.writeString(WAARDEONTWIKKELING_POS, CF.format(valueAtYearStart), 1);
    
    out.writeString(0, "Betaalde premie");
    PgCurrency premiumPaid;
    if (annualStatement.isSetPremiumPaid()) {
      premiumPaid = annualStatement.getPremiumPaid();
    } else {
      premiumPaid = new PgCurrency(0);
    }
    out.writeString(WAARDEONTWIKKELING_POS, CF.format(premiumPaid), 1);
    
    PgCurrency valueAtYearStartPlusPremiumPaid = valueAtYearStart;
    if (valueAtYearStartPlusPremiumPaid != null  &&  annualStatement.isSetPremiumPaid()) {
      valueAtYearStartPlusPremiumPaid = valueAtYearStartPlusPremiumPaid.add(annualStatement.getPremiumPaid());
    }
    out.writeString(0, "Saldo plus inleg");
    out.writeString(WAARDEONTWIKKELING_POS, CF.format(valueAtYearStartPlusPremiumPaid), 1);
    
    out.newLine();
    out.write("Kosten:");
    out.newLine();
    out.write("Premies voor een overlijdensrisicodekking");
    out.newLine();
    out.write("Voor het overlijdensrisico wordt er maandelijks een koopsom voor de dekking van het aanwezige");
    out.newLine();
    out.write("overlijdensrisico verrekend met de waarde van de voor de verzekering aangehouden participaties.");
    out.newLine();
    out.write("Het overlijdensrisico is het verschil tussen het daadwerkelijk uit te keren bedrag bij overlijden");
    out.newLine();
    out.write("en de waarde van de participaties aan het begin van de maand. De koopsom voor de dekking van het");
    out.newLine();
    out.write("overlijdensrisico is daarnaast afhankelijk van bijvoorbeeld het aantal en de leeftijd van de verzekerden.");
    out.newLine();
    out.write("Naarmate het verschil tussen de uitkering bij overlijden en de waarde van de participaties groter is,");
    out.newLine();
    out.write("is de invloed van de koopsom voor de uitkering bij overlijden op de waarde-ontwikkeling van uw verzekering groter.");
    out.newLine();
    out.write("Doorlopende kosten");
    out.newLine();
    out.write("Deze kosten bestaan uit doorlopende administratiekosten.");
    out.newLine();
    out.write("Deze kosten worden gedurende de gehele looptijd van de verzekering verrekend.");
    out.newLine();
    out.write("Beheerskosten (ook wel Kosten Vermogensbeslag)");
    out.newLine();
    out.write("Maandelijks worden er beheerskosten berekend.");
    out.newLine();
    out.write("Deze bedragen gemiddeld 0,50% van de fondswaarde per jaar, maandelijks vast te stellen.");
    out.newLine();
    out.newLine();
    
    if (annualStatement.isSetPremiumDeathRiskCoverage()) {
      out.writeString(0, "Premies overlijdensrisicodekking");
      out.writeString(WAARDEONTWIKKELING_KOSTEN_POS, CF.format(annualStatement.getPremiumDeathRiskCoverage()), 1);
    }
    
    if (annualStatement.isSetPremiumOccupationalDisabilityRiskCoverage()) {
      out.writeString(0, "Premies arbeidsongeschiktheidsrisicodekking");
      out.writeString(WAARDEONTWIKKELING_KOSTEN_POS, CF.format(annualStatement.getPremiumOccupationalDisabilityRiskCoverage()), 1);
    }
    
    if (annualStatement.isSetUpkeepPremium()) {
      out.writeString(0, "Premie verzorging");
      out.writeString(WAARDEONTWIKKELING_KOSTEN_POS, CF.format(annualStatement.getUpkeepPremium()), 1);
    }
    
    if (annualStatement.isSetFirstCostsInsuranceCompany()  ||
        annualStatement.isSetContinuingCostsInsuranceCompany()) {
      out.write("Kosten verzekeringsmaatschappij:");
      out.newLine();
      
      if (annualStatement.isSetFirstCostsInsuranceCompany()) {
        out.writeString(WAARDEONTWIKKELING_KOSTENSOORT_POS, "Eerste kosten");
        out.writeString(WAARDEONTWIKKELING_KOSTEN_POS, CF.format(annualStatement.getFirstCostsInsuranceCompany()), 1);
      }
      
      if (annualStatement.isSetContinuingCostsInsuranceCompany()) {
        out.writeString(WAARDEONTWIKKELING_KOSTENSOORT_POS, "Doorlopende kosten");
        out.writeString(WAARDEONTWIKKELING_KOSTEN_POS, CF.format(annualStatement.getContinuingCostsInsuranceCompany()), 1);
      }
    }
    
    if (annualStatement.isSetManagementCosts()) {
      out.writeString(0, "Beheerskosten");
      out.writeString(WAARDEONTWIKKELING_KOSTEN_POS, CF.format(annualStatement.getManagementCosts()), 1);
    }
    
    if (annualStatement.isSetBuyAndSellCosts()) {
      out.writeString(0, "Aan- en verkoopkosten");
      out.writeString(WAARDEONTWIKKELING_KOSTEN_POS, CF.format(annualStatement.getBuyAndSellCosts()), 1);
    }
    
    if (annualStatement.isSetMutationCosts()) {
      out.writeString(0, "Mutatiekosten");
      out.writeString(WAARDEONTWIKKELING_KOSTEN_POS, CF.format(annualStatement.getMutationCosts()), 1);
    }
    
    if (annualStatement.isSetCorrections()) {
      out.writeString(0, "Correcties");
      out.writeString(WAARDEONTWIKKELING_KOSTEN_POS, CF.format(annualStatement.getCorrections()), 1);
    }
    
    PgCurrency totalCosts = annualStatement.totalCosts();
    out.writeString(0, "Totaal aan kosten");
    out.writeString(WAARDEONTWIKKELING_POS, CF.format(totalCosts), 1);
    
    
    PgCurrency valueAfterCosts = null;
    if (valueAtYearStartPlusPremiumPaid != null) {
      valueAfterCosts = valueAtYearStartPlusPremiumPaid.subtract(totalCosts);
    }
    out.newLine();
    out.writeString(0, "Saldo na aftrek kosten");
    out.writeString(WAARDEONTWIKKELING_POS, CF.format(valueAfterCosts), 1);

    out.writeString(0, "Verdiend op de participaties (resultaat)");
    out.writeString(WAARDEONTWIKKELING_POS, CF.format(annualStatement.getEarnedOnTheParticipations()), 1);
    
    if (annualStatement.isSetCostsRestitution()) {
      out.writeString(0, "Teruggave kosten");
      out.writeString(WAARDEONTWIKKELING_POS, CF.format(annualStatement.getCostsRestitution()), 1);
    }
    
    out.writeString(0, "Saldo per " + annualStatement.getPeriodUntil().format(DF));
    PgCurrency newValue = null;
    if (valueAfterCosts != null  &&  annualStatement.isSetEarnedOnTheParticipations()) {
      newValue = valueAfterCosts.add(annualStatement.getEarnedOnTheParticipations());
    }
    if (newValue != null  &&  annualStatement.isSetCostsRestitution()) {
      newValue = newValue.add(annualStatement.getCostsRestitution());
      
    }
    out.writeString(WAARDEONTWIKKELING_POS, CF.format(newValue), 1);
  }

  private static void dumpAnnualStatementParticipationOverview(TextWriter out, LocalDate periodFromDate, LocalDate periodUntilDate, List<Participation> participations) throws IOException {
    out.write("PARTICIPATIEOVERZICHT");
    out.newLine();
    out.newLine();
    
    if (periodFromDate != null) {
      out.write("Status per ");
      out.write(periodFromDate.format(DF));
      out.newLine();
      out.writeString(0, "Fonds");
      out.writeString(AANTAL_PARTICIPATIES_POS, "Aantal Participaties");
      out.writeString(KOERS_POS, "Koers");
      out.writeString(FONDSWAARDE_POS, "Fondswaarde", 1);
      PgCurrency totalEndValue = null;
      for (Participation participation: participations) {
        InvestmentFund investmentFund = participation.getInvestmentFund();
        out.writeString(0, investmentFund.getName());
        FixedPointValue numberOfParticipations = participation.numberOfParticipationsStart();
        if (numberOfParticipations != null) {
          out.writeString(AANTAL_PARTICIPATIES_POS, FPVF.format(numberOfParticipations));
        } else {
          out.writeString(AANTAL_PARTICIPATIES_POS, "----");
        }
        PgCurrency stockPrice = investmentFund.getStockPrice(periodFromDate);
        out.writeString(KOERS_POS, CF.format(stockPrice));
        PgCurrency fundValue = null;
        if (stockPrice != null  &&  numberOfParticipations != null) {
          fundValue = stockPrice.multiply(numberOfParticipations.doubleValue()).certifyFactor(100);
          if (stockPrice.getFactor() < 1000) {
            stockPrice = stockPrice.certifyFactor(1000);
          }
          PgCurrency fundValueExact = stockPrice.multiply(numberOfParticipations.doubleValue());
          if (totalEndValue == null) {
            totalEndValue = fundValueExact;
          } else {
            totalEndValue = totalEndValue.add(fundValueExact);
          }
        }
        out.writeString(FONDSWAARDE_POS, CF.format(fundValue), 1);
      }
      if (totalEndValue != null) {
        totalEndValue = totalEndValue.certifyFactor(100);
      }
      out.writeString(FONDSWAARDE_POS, CF.format(totalEndValue), 1);
    }
    
    out.write("Status per ");
    out.write(periodUntilDate.format(DF));
    out.newLine();
    out.writeString(0, "Fonds");
    out.writeString(AANTAL_PARTICIPATIES_POS, "Aantal Participaties");
    out.writeString(KOERS_POS, "Koers");
    out.writeString(FONDSWAARDE_POS, "Fondswaarde", 1);
    PgCurrency totalEndValue = null;
    for (Participation participation: participations) {
      InvestmentFund investmentFund = participation.getInvestmentFund();
      out.writeString(0, investmentFund.getName());
      FixedPointValue numberOfParticipations = participation.getNumberOfParticipationsEnd();
      if (numberOfParticipations != null) {
        out.writeString(AANTAL_PARTICIPATIES_POS, FPVF.format(numberOfParticipations));
      } else {
        out.writeString(AANTAL_PARTICIPATIES_POS, "----");
      }
      PgCurrency stockPrice = investmentFund.getStockPrice(periodUntilDate);
      out.writeString(KOERS_POS, CF.format(stockPrice));
      PgCurrency fundValue = null;
      if (stockPrice != null  && numberOfParticipations != null) {
        fundValue = stockPrice.multiply(numberOfParticipations.doubleValue()).certifyFactor(100);
        if (stockPrice.getFactor() < 1000) {
          stockPrice = stockPrice.certifyFactor(1000);
        }
        PgCurrency fundValueExact = stockPrice.multiply(numberOfParticipations.doubleValue());
        if (totalEndValue == null) {
          totalEndValue = fundValueExact;
        } else {
          totalEndValue = totalEndValue.add(fundValueExact);
        }
      }
      out.writeString(FONDSWAARDE_POS, CF.format(fundValue), 1);
    }
    if (totalEndValue != null) {
      totalEndValue = totalEndValue.certifyFactor(100);
    }
    out.writeString(FONDSWAARDE_POS, CF.format(totalEndValue), 1);
  }

  private static void dumpAnnualStatementParticipationMutationOverview(TextWriter out, AnnualStatement annualStatement) throws IOException {
    out.write("OVERZIICHT VAN AANGEKOCHTE EN VERKOCHTE PARTICIPATIES");
    out.newLine();
    out.write("Er worden participaties verkocht om de kosten te voldoen.");
    out.newLine();
    out.write("Het is niet bekend op welke datum dit gebeurt en tegen welke koers.");
    out.newLine();
    out.write("Daarom is het niet mogenlijk om het aantal verkochte participaties te berekenen.");
    out.newLine();
    out.newLine();
    
    out.writeString(0, "Fonds");
    out.writeString(PARTICIPATIES_BIJ_POS, "Participaties bij");
    out.writeString(PARTICIPATIES_AF_POS, "Participaties af", 1);
    for (Participation participation: annualStatement.getParticipations()) {
      InvestmentFund investmentFund = participation.getInvestmentFund();
      for (FixedPointValue mutation: participation.getParticipationMutations()) {
        out.writeString(0, investmentFund.getName());
        if (mutation.isNegative()) {
          mutation = mutation.changeSign();
          out.writeString(PARTICIPATIES_AF_POS, FPVF.format(mutation));
        } else {
          out.writeString(PARTICIPATIES_BIJ_POS, FPVF.format(mutation));
        }
        out.newLine();
      }
    }
    out.newLine();
  }

  private static void dumpJaarOpgaveVoorbeeldWaarden(TextWriter out, AnnualStatement annualStatement) throws IOException {
    out.write("VOORBEELDKAPITALEN");
    out.newLine();
    out.newLine();
    
    if (dumpAnnualStatementExampleCapital(out, annualStatement, ExampleCapitalType.NETTO_HISTORISCH)) {
      out.newLine();
    }
    
    if (dumpAnnualStatementExampleCapital(out, annualStatement, ExampleCapitalType.NETTO_HISTORISCH_NA_AFSLAG)) {
      out.newLine();
    }

    if (dumpAnnualStatementExampleCapital(out, annualStatement, ExampleCapitalType.STANDAARD)) {
      out.newLine();
    }

    if (dumpAnnualStatementExampleCapital(out, annualStatement, ExampleCapitalType.VIER_PROCENT_BRUTO)) {
      out.newLine();
    }
    
    if (dumpAnnualStatementExampleCapital(out, annualStatement, ExampleCapitalType.BRUTO_EIGEN)) {
      out.newLine();
    }

    if (dumpAnnualStatementExampleCapital(out, annualStatement, ExampleCapitalType.PESSIMISTISCH)) {
      out.newLine();
    }
  }

  /**
   * Dump de uitleg en voorbeeldwaarden voor een bepaald type.
   * Het hele blok wordt alleen gedumpt als er waarden beschikbaar zijn en/of waarden berekend
   * kunnen worden.
   * Waarden zijn beschikbaar als er een rendement en/of voorbeeld eindwaarde beschikbaar is.
   * Waarden kunnen berekend worden als er minimaal een huidige waarde en een rendement beschikbaar is
   * voor iedere participatie. Voor iedere participatie, omdat anders de kosten niet evenredig verdeeld kunnen
   * worden.
   */
  private static boolean dumpAnnualStatementExampleCapital(TextWriter out, AnnualStatement annualStatement, ExampleCapitalType voorbeeldKapitaalType) throws IOException {
    // Collect all information for own calculation
    LocalDate calculationStartDate = annualStatement.getPeriodUntil();
    List<ExampleCapitalCalculationInput> voorbeelKapitaalInputs = new ArrayList<>();
    for (Participation participation: annualStatement.getParticipations()) {
      ExampleCapitalCalculationInput voorbeeldKapitaalInput = new ExampleCapitalCalculationInput();
      
      voorbeeldKapitaalInput.startingValue = participation.endValue();
      LOGGER.fine("added startKapitaal: " + CF.format(voorbeeldKapitaalInput.startingValue));
        
      FixedPointValue exampleReturnOnInvestment = null;
      FixedPointValue totalExpenseRatio = null;
      switch (voorbeeldKapitaalType) {
      case NETTO_HISTORISCH:
        exampleReturnOnInvestment = participation.getExampleReturnOnInvestmentNetHistoric();
        totalExpenseRatio = new FixedPointValue(0, 100);
        break;
        
      case NETTO_HISTORISCH_NA_AFSLAG:
        if (participation.isSetExampleReturnOnInvestmentNetHistoric()  &&
            participation.isSetReturnOnInvestmentReductionNetHistoric()) {
          exampleReturnOnInvestment = participation.getExampleReturnOnInvestmentNetHistoric().multiply(1 - participation.getReturnOnInvestmentReductionNetHistoric().doubleValue() / 100);
          totalExpenseRatio = new FixedPointValue(0, 100);
        }
        break;
        
      case STANDAARD:
        exampleReturnOnInvestment = participation.getStandardFundReturnOnInvestment();
        totalExpenseRatio = participation.getTotalExpenseRatio();
        break;
        
      case VIER_PROCENT_BRUTO:
        exampleReturnOnInvestment = participation.getExampleReturnOnInvestmentGross();
        totalExpenseRatio = participation.getTotalExpenseRatio();
        break;
        
      case BRUTO_EIGEN:
        exampleReturnOnInvestment = participation.getExampleReturnOnInvestmentGrossCompanyOwn();
        totalExpenseRatio = participation.getTotalExpenseRatio();
        break;
        
      case PESSIMISTISCH:
        exampleReturnOnInvestment = participation.getExampleReturnOnInvestmentPessimistic();
        totalExpenseRatio = participation.getTotalExpenseRatio();
        break;        
      }
      if (exampleReturnOnInvestment == null) {
        LOGGER.fine("no example return on investment for " + participation.getInvestmentFund().getName());
      }
      voorbeeldKapitaalInput.returnOnInvestment = exampleReturnOnInvestment;
      LOGGER.fine("added example return on investment: " + FPVF.format(exampleReturnOnInvestment));
      voorbeeldKapitaalInput.totalExpenseRatio = totalExpenseRatio;
      LOGGER.fine("added totalExpenseRatio: " + FPVF.format(totalExpenseRatio));
      
      voorbeelKapitaalInputs.add(voorbeeldKapitaalInput);
    }
    
    // Make own calculation
    ExampleCapitalCalculationResult VoorbeeldKapitaalBerekeningResultaat = InvestmentInsurancesCalculator.calculateExampleCapital(
        calculationStartDate, annualStatement.getExampleCapitalOnEndDate(), voorbeelKapitaalInputs,
        annualStatement.fixedCosts(), annualStatement.percentageCosts(), annualStatement.getExpectedYearlyCostsIncrease());
    
    // Only dump information if it is available
    if (!InvestmentInsurancesCalculator.isMinimalInformationExampleCapitalAvailable(annualStatement, voorbeeldKapitaalType)  &&
        !VoorbeeldKapitaalBerekeningResultaat.isCalculationOk()) {
      return false;
    }
    
    // Explanation
    out.writeTextBlock(voorbeeldKapitaalType.getDescription(), true);
    
    // Header
    out.writeString(0, "Voorbeeldwaarde per " + annualStatement.getExampleCapitalOnEndDate().format(DF));
    out.writeString(PERCENTAGE_POS, "%");
    out.writeString(VOORBEELDKAPITAAL_POS, voorbeeldKapitaalType.getName());
    out.writeString(BEREKEND_VOORBEELDKAPITAAL_POS, "eigen berekening", 1);    

    PgCurrency total = null;
    PgCurrency totalCalculated = null;
    PgCurrency[] calculatedExampleCapitals = VoorbeeldKapitaalBerekeningResultaat.calculatedExampleCapitals;
    int participationIndex = 0;
    for (Participation participation: annualStatement.getParticipations()) {
      out.writeString(0, participation.getInvestmentFund().getName());
      FixedPointValue exampleReturnOnInvestment = null;
      PgCurrency exampleCapital = null;
      switch (voorbeeldKapitaalType) {
      case NETTO_HISTORISCH:
        exampleReturnOnInvestment = participation.getExampleReturnOnInvestmentNetHistoric();
        exampleCapital = participation.getExampleCapitalNetHistoric();
        break;
        
      case NETTO_HISTORISCH_NA_AFSLAG:
        if (participation.isSetExampleReturnOnInvestmentNetHistoric()  &&
            participation.isSetReturnOnInvestmentReductionNetHistoric()) {
          exampleReturnOnInvestment = participation.getExampleReturnOnInvestmentNetHistoric().multiply(1 - participation.getReturnOnInvestmentReductionNetHistoric().doubleValue() / 100);
        }
        exampleCapital = participation.getExampleCapitalAfterReduction();
        break;
        
      case STANDAARD:
        exampleReturnOnInvestment = participation.getStandardFundReturnOnInvestment();
        exampleCapital = participation.getExampleCapitalStandardFundReturnOnInvestment();
        break;
        
      case VIER_PROCENT_BRUTO:
        exampleReturnOnInvestment = participation.getExampleReturnOnInvestmentGross();
        exampleCapital = participation.getExampleCapitalGross();
        break;
        
      case BRUTO_EIGEN:
        exampleReturnOnInvestment = participation.getExampleReturnOnInvestmentGrossCompanyOwn();
        exampleCapital = participation.getExampleCapitalGrossCompanyOwn();
        break;
        
      case PESSIMISTISCH:
        exampleReturnOnInvestment = participation.getExampleReturnOnInvestmentPessimistic();
        exampleCapital = participation.getExampleCapitalPessimistic();
        break;     
      }
      out.writeString(PERCENTAGE_POS, FPVF.format(exampleReturnOnInvestment));
      out.writeString(VOORBEELDKAPITAAL_POS, CF.format(exampleCapital));
      
      PgCurrency calculatedExampleCapital = null;
      if (calculatedExampleCapitals != null) {
        calculatedExampleCapital = calculatedExampleCapitals[participationIndex];
        if (calculatedExampleCapital != null) {
          double errorFactor = exampleCapital.getDoubleValue() / calculatedExampleCapital.getDoubleValue();
//          double errorFactor = voorbeeldKapitaal.divide(berekendVoorbeeldKapitaal).doubleValue();
          if (errorFactor < 0.955  ||  errorFactor > 1.03) {
            LOGGER.fine("Too large error in own calculation: " + errorFactor);
          }
        }
      }
      out.writeString(BEREKEND_VOORBEELDKAPITAAL_POS, CF.format(calculatedExampleCapital));
      if (VoorbeeldKapitaalBerekeningResultaat.isOneOrMoreEstimationsUsed()) {
        out.writeStringOffset(2, "~");
        for (int i = 1; i < VoorbeeldKapitaalBerekeningResultaat.numberOfEstimationsUsed(); i++) {
          out.writeStringOffset(0, "~");
        }
      }
      out.newLine();

      if (exampleCapital != null) {
        if (total == null) {
          total = exampleCapital;
        } else {
          total = total.add(exampleCapital);
        }
      }
      if (calculatedExampleCapitals != null) {
        if (calculatedExampleCapitals[participationIndex] != null) {
          if (totalCalculated == null) {
            totalCalculated = calculatedExampleCapitals[participationIndex];
          } else {
            totalCalculated = totalCalculated.add(calculatedExampleCapitals[participationIndex]);
          }
        }
      }
      participationIndex++;
    }
    out.writeString(0, "Totaal");
    out.writeString(VOORBEELDKAPITAAL_POS, CF.format(total));
    out.writeString(BEREKEND_VOORBEELDKAPITAAL_POS, CF.format(totalCalculated), 1);
    
    return true;
  }

  private static void dumpAnnualStatementTotalExpenseRatios(TextWriter out, AnnualStatement annualStatement) throws IOException {
    out.write("TOTAL EXPENSE RATIO'S");
    out.newLine();
    out.write("Beleggingskosten");
    out.newLine();
    out.write("Voor het beheren van de beleggingsfondsen wordt per fonds een percentage op jaarbasis verrekend,");
    out.newLine();
    out.write("de zogenaamde Total Expense Ratio (TER). De TER is het totaal van provisies en kosten van een beleggingsfonds");
    out.newLine();
    out.write("(zoals de beheersvergoeding, de overige bedrijfskosten en de rentelasten) als percentage van het totaal van het belegd vermogen.");
    out.newLine();
    out.write("Het percentage is fondsafhankelijk en wordt periodiek opnieuw vastgesteld.");
    out.newLine();
    out.write("Onderstaand wordt per fonds het percentage vermeld.");
    out.newLine();
    out.newLine();

    out.writeString(0, "Fonds");
    out.writeString(TER_POS, "TER", 1);
    for (Participation participation: annualStatement.getParticipations()) {
      InvestmentFund investmentFund = participation.getInvestmentFund();
      out.writeString(0, investmentFund.getName());
      out.writeString(TER_POS, FPVF.format(participation.getTotalExpenseRatio()) + " %", 1);
    }
    out.newLine();
  }

  private static void dumpExtraInvestment(TextWriter out, InvestmentInsurance investmentInsurance, ExtraInvestment extraInvestment) throws IOException {
    out.newLine();
    out.writeString(0, "EXTRA INLEG", 2);
    
    out.writeString(0, "Datum inleg (datum per wanneer de inleg belegd is volgens onderstaande beleggingsmix):");
    if (extraInvestment.isSetDepositDate()) {
      out.writeStringOffset(2, extraInvestment.getDepositDate().format(DF), 1);
    }
    out.writeString(0, "Premie (extra inleg):");
    if (extraInvestment.isSetPremium()) {
      out.writeStringOffset(2, CF.format(extraInvestment.getPremium()), 1);
    }

    out.newLine();
    out.write("Aanwending beleggingsdeel");
    out.newLine();
    out.write("Het beleggingsdeel zal als volgt worden aangewend.");
    out.newLine();
    out.writeString(0, "Fondsen");
    out.writeString(VERDELINGS_PERCENTAGE_POS, "Verdeling", 1);
    for (InvestmentPart investmentPart: extraInvestment.getInvestmentParts()) {
      out.writeString(0, investmentPart.getInvestmentFund().getName());
      out.writeString(VERDELINGS_PERCENTAGE_POS, FPVF.format(investmentPart.getPercentage()) + "%", 1);
    }

    out.newLine();
    dumpAnnualStatementParticipationOverview(out, null, extraInvestment.getOverviewDate(), extraInvestment.getParticipations());

    out.newLine();
    out.newLine();
  }

}
