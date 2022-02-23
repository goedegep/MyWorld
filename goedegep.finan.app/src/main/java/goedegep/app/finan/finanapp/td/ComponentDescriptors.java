package goedegep.app.finan.finanapp.td;

import goedegep.app.finan.td.CD;
import goedegep.app.finan.td.CDCheckBox;
import goedegep.app.finan.td.CDClaimEmissionField;
import goedegep.app.finan.td.CDComboBox;
import goedegep.app.finan.td.CDCommaValueField;
import goedegep.app.finan.td.CDCurrencyField;
import goedegep.app.finan.td.CDDateField;
import goedegep.app.finan.td.CDLabel;
import goedegep.app.finan.td.CDLookAheadTextField;
import goedegep.app.finan.td.CDNumberField;
import goedegep.app.finan.td.CDShareDividendField;
import goedegep.app.finan.td.CDTextField;
import goedegep.app.finan.td.EffectType;
import goedegep.app.finan.td.UitvoeringsType;
import goedegep.finan.stocks.OptionType;

public class ComponentDescriptors {
  // algemeen
  private static final CDLabel              AANDEEL_NAAM_LABEL = new CDLabel("AandeelNaamLabel", "Aandeel naam:");
  private static final CDAandeelNaamField   AANDEEL_NAAM_FIELD = new CDAandeelNaamField("AandeelNaamField", 20, "kies effect");
  private static final CDLabel              AANTAL_LABEL = new CDLabel("AantalAandelenLabel", "Aantal aandelen:");
  private static final CDNumberField        AANTAL_FIELD = new CDNumberField("AantalAandelenField", 20, "aantal aandelen", 1, null);
  private static final CDLabel              AANTAL_RECHTEN_LABEL = new CDLabel("AantalRechtenLabel", "Aantal rechten:");
  private static final CDNumberField        AANTAL_RECHTEN_FIELD = new CDNumberField("AantalRechtenField", 20, "aantal rechten", 1, null);
  private static final CDLabel              BEDRAG_LABEL = new CDLabel("BedragLabel", "Bedrag:");
  private static final CDCurrencyField      BEDRAG_FIELD = new CDCurrencyField("BedragField", 20, "bedrag");
  private static final CDLabel              CLAIM_ID_LABEL = new CDLabel("ClaimIdLabel", "Claim Id:");
  private static final CDClaimEmissionField CLAIM_ID_FIELD = new CDClaimEmissionField("ClaimIdField", 30, "kies claim id", "AandeelNaamField");
  private static final CDLabel              CREDIT_RENTE_LABEL = new CDLabel("CreditRenteLabel", "Credit rente:");
  private static final CDCurrencyField      CREDIT_RENTE_FIELD = new CDCurrencyField("CreditRenteField", 20, "vul ontvangen credit rente in");
  private static final CDLabel              DEBET_RENTE_LABEL = new CDLabel("DebetRenteLabel", "Debet rente:");
  private static final CDCurrencyField      DEBET_RENTE_FIELD = new CDCurrencyField("DebetRenteField", 20, "vul betaalde debet rente in");
  private static final CDLabel              DIVIDEND_LABEL = new CDLabel("DividendLabel", "Dividend:");
  private static final CDShareDividendField DIVIDEND_FIELD = new CDShareDividendField("DividendField", 20, "selecteer het dividend", "AandeelNaamField");
  private static final CDLabel              EFFECT_TYPE_LABEL = new CDLabel("EffectTypeLabel", "Soort effect:");
  public static final  CDLookAheadTextField EFFECT_TYPE_FIELD = new CDLookAheadTextField("EffectTypeField", EffectType.getNames(), 20, "kies effect type");
  private static final CDLabel              FRACTIE_LABEL = new CDLabel("FractieLabel", "Fractie:");
  private static final CDCommaValueField    FRACTIE_FIELD = new CDCommaValueField("FractieField", 20, "fractie", 100, 1l, null);
  private static final CDLabel              JAAR_LABEL = new CDLabel("JaarLabel", "Jaar:");
  private static final CDNumberField        JAAR_FIELD = new CDNumberField("JaarField", 8, "het jaartal", 1900, 2200);
  private static final CDLabel              KOERS_LABEL = new CDLabel("KoersLabel", "Koers:");
  private static final CDCurrencyField      KOERS_FIELD = new CDCurrencyField("KoersField", 20, "aankoop of verkoop koers");
  private static final CDLabel              KWARTAAL_LABEL = new CDLabel("KwartaalLabel", "Kwartaal:");
  private static final CDNumberField        KWARTAAL_FIELD = new CDNumberField("KwartaalField", 8, "het kwartaal (1 - 4)", 1, 4);
  private static final CDLabel              MAAND_LABEL = new CDLabel("MaandLabel", "Maand:");
  private static final CDNumberField        MAAND_FIELD = new CDNumberField("MaandField", 8, "de maand", 1, 12);
  private static final CDLabel              NAAR_AANDEEL_NAAM_LABEL = new CDLabel("NaarAandeelNaamLabel", "Verkregen aandeel naam:");
  private static final CDAandeelNaamField   NAAR_AANDEEL_NAAM_FIELD = new CDAandeelNaamField("NaarAandeelNaamField", 30, "kies verkregen effect");
  private static final CDLabel              NAAR_AANTAL_LABEL = new CDLabel("NaarAantalAandelenLabel", "Verkregen aantal aandelen:");
  private static final CDNumberField        NAAR_AANTAL_FIELD = new CDNumberField("NaarAantalAandelenField", 20, "aantal verkregen aandelen", 1, null);
  private static final CDLabel              NW_RENTE_PERCENTAGE_LABEL = new CDLabel("NwRentePercentageLabel", "Nieuwe Rente:");
  private static final CDCommaValueField    NW_RENTE_PERCENTAGE_FIELD = new CDCommaValueField("NwRentePercentageField", 20, "fractie", 100, 0l, null, true);
  private static final CDLabel              OPMERKINGEN_LABEL = new CDLabel("OpmerkingenLabel", "Opmerkingen:");
  private static final CDTextField          OPMERKINGEN_FIELD = new CDTextField("OpmerkingenField", 20, "vul eventuele opmerkingen in");
  private static final CDLabel              OPTIE_TYPE_LABEL = new CDLabel("OptieTypeLabel", "Optie type:");
  private static final CDComboBox           OPTIE_TYPE_COMBOBOX = new CDComboBox("OptieType", OptionType.getTexts(), -1, "Selecteer een uitvoeringstype");
  private static final CDLabel              ORDER_KOSTEN_LABEL = new CDLabel("OrderKostenLabel", "Order kosten:");
  private static final CDCurrencyField      ORDER_KOSTEN_FIELD = new CDCurrencyField("OrderKostenField", 20, "totale order kosten");
  private static final CDCurrencyField      ORDER_KOSTEN_OPT_FIELD = new CDCurrencyField("OrderKostenField", 20, "totale order kosten", true);
  private static final CDLabel              TOT_DATUM_LABEL = new CDLabel("TotDatumLabel", "tot en met:");
  private static final CDDateField          TOT_DATUM_FIELD = new CDDateField("TotDatumField", "vul 'tot en met' datum in");
  private static final CDLabel              UITVOERINGS_KOERS_LABEL = new CDLabel("UitoefeningsKoersLabel", "Uitvoerings koers:");
  private static final CDCurrencyField      UITVOERINGS_KOERS_FIELD = new CDCurrencyField("UitoefeningsKoersField", 20, "uitvoeringskoers van de optie");
  private static final CDLabel              UITVOERINGS_DATUM_LABEL = new CDLabel("UitvoeringsDatumLabel", "Uitvoerings Datum:");
  private static final CDDateField          UITVOERINGS_DATUM_FIELD = new CDDateField("UitvoeringsDatumField", "vul uitvoerings datum in");
  private static final CDLabel              UITVOERINGS_TYPE_LABEL = new CDLabel("UitvoeringsTypeLabel", "Uitvoerings Type:");
  private static final CDComboBox           UITVOERINGS_TYPE_COMBOBOX = new CDComboBox("UitvoeringsType", UitvoeringsType.getTexts(), -1, "Selecteer een uitvoeringstype");
  private static final CDLabel              VAN_AANDEEL_NAAM_LABEL = new CDLabel("VanAandeelNaamLabel", "Overgenomen aandeel naam:");
  private static final CDAandeelNaamField   VAN_AANDEEL_NAAM_FIELD = new CDAandeelNaamField("VanAandeelNaamField", 30, "kies overgenomen effect");
  private static final CDLabel              VAN_AANTAL_LABEL = new CDLabel("VanAantalAandelenLabel", "Overgenomen aantal aandelen:");
  private static final CDNumberField        VAN_AANTAL_FIELD = new CDNumberField("VanAantalAandelenField", 20, "aantal overgenomen aandelen", 1, null);
  private static final CDLabel              VAN_DATUM_LABEL = new CDLabel("VanDatumLabel", "van:");
  private static final CDDateField          VAN_DATUM_FIELD = new CDDateField("VanDatumField", "vul 'van' datum in");
  private static final CDLabel              ZONDER_KOSTEN_LABEL = new CDLabel("ZonderKostenLabel", "Zonder kosten:");
  private static final CDCheckBox           ZONDER_KOSTEN_CHECKBOX = new CDCheckBox("ZonderKosten", false);

  /*
   * ABN AMRO effecten rekening
   */
  // aankoop, verkoop
  public static final CD[] AA_EFF_REK_AANKOOP_VERKOOP_COMPONENTS = {
    EFFECT_TYPE_LABEL, EFFECT_TYPE_FIELD};
  // aandelen
  public static final CD[] AA_EFF_REK_AANKOOP_VERKOOP_AANDELEN_COMPONENTS = {
    EFFECT_TYPE_LABEL, EFFECT_TYPE_FIELD, AANDEEL_NAAM_LABEL, AANDEEL_NAAM_FIELD,
    AANTAL_LABEL, AANTAL_FIELD, KOERS_LABEL, KOERS_FIELD,
    ORDER_KOSTEN_LABEL, ORDER_KOSTEN_FIELD,
    UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD, UITVOERINGS_TYPE_LABEL, UITVOERINGS_TYPE_COMBOBOX,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
//  // claim rechten
//  public static final CD[] AA_EFF_REK_AANKOOP_VERKOOP_CLAIM_RIGHTS_COMPONENTS = {
//    EFFECT_TYPE_LABEL, EFFECT_TYPE_FIELD, AANDEEL_NAAM_LABEL, AANDEEL_NAAM_FIELD,
//    CLAIM_ID_LABEL, CLAIM_ID_FIELD,
//    AANTAL_LABEL, AANTAL_FIELD,
//    UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD,
//    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  // aankoop, verkoop opties
  public static final CD[] AA_EFF_REK_AANKOOP_VERKOOP_OPTIES_COMPONENTS = {
    AANDEEL_NAAM_LABEL, AANDEEL_NAAM_FIELD, OPTIE_TYPE_LABEL, OPTIE_TYPE_COMBOBOX,
    JAAR_LABEL, JAAR_FIELD, MAAND_LABEL, MAAND_FIELD,
    UITVOERINGS_KOERS_LABEL, UITVOERINGS_KOERS_FIELD,
    AANTAL_LABEL, AANTAL_FIELD, KOERS_LABEL, KOERS_FIELD,
    ORDER_KOSTEN_LABEL, ORDER_KOSTEN_FIELD,
    UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  // af- en bijschrijving
  public static final CD[] AA_EFF_REK_AFSCHRIJVING_COMPONENTS = {
    BEDRAG_LABEL, BEDRAG_FIELD, OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  public static final CD[] AA_EFF_REK_BIJSCHRIJVING_COMPONENTS = {
    BEDRAG_LABEL, BEDRAG_FIELD, OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  // naar- en van dekkingsrekening
  public static final CD[] AA_EFF_REK_DEKKINGS_REKENING_COMPONENTS = {
    BEDRAG_LABEL, BEDRAG_FIELD, OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  // dividend
  public static final CD[] AA_EFF_REK_DIVIDEND_COMPONENTS = {
    AANDEEL_NAAM_LABEL, AANDEEL_NAAM_FIELD, DIVIDEND_LABEL, DIVIDEND_FIELD,
    AANTAL_LABEL, AANTAL_FIELD,
    UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD, ZONDER_KOSTEN_LABEL, ZONDER_KOSTEN_CHECKBOX};
  
  // bewaarloon
  public static final CD[] AA_EFF_REK_BEWAARLOON_COMPONENTS = {
    BEDRAG_LABEL, BEDRAG_FIELD, OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
//  // kwartaal overzicht
//  public static final CD[] AA_EFF_REK_KWARTAAL_OVERZICHT_COMPONENTS = {
//    JAAR_LABEL, JAAR_FIELD, KWARTAAL_LABEL, KWARTAAL_FIELD,
//    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
    
  // belasting overzicht
  public static final CD[] AA_EFF_REK_BELASTING_OVERZICHT_COMPONENTS = {
    JAAR_LABEL, JAAR_FIELD, OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
//  // bonus aandelen
//  public static final CD[] AA_EFF_REK_BONUS_AANDELEN_COMPONENTS = {
//    AANDEEL_NAAM_LABEL, AANDEEL_NAAM_FIELD, AANTAL_LABEL, AANTAL_FIELD, 
//    UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD, OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
//  // Claim Rights Received.
//  public static final CD[] AA_EFF_REK_CLAIM_RIGHTS_RECEIVED_COMPONENTS = {
//    AANDEEL_NAAM_LABEL, AANDEEL_NAAM_FIELD, CLAIM_ID_LABEL, CLAIM_ID_FIELD,
//    AANTAL_RECHTEN_LABEL, AANTAL_RECHTEN_FIELD, UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD,
//    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  // Renteverrekening.
  public static final CD[] AA_EFF_REK_RENTEVERREKENING_COMPONENTS = {
    VAN_DATUM_LABEL, VAN_DATUM_FIELD, TOT_DATUM_LABEL, TOT_DATUM_FIELD, 
    CREDIT_RENTE_LABEL, CREDIT_RENTE_FIELD, DEBET_RENTE_LABEL, DEBET_RENTE_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};

//  // Overname.
//  public static final CD[] AA_EFF_REK_OVERNAME_COMPONENTS = {
//    VAN_AANDEEL_NAAM_LABEL, VAN_AANDEEL_NAAM_FIELD, VAN_AANTAL_LABEL, VAN_AANTAL_FIELD, 
//    NAAR_AANDEEL_NAAM_LABEL, NAAR_AANDEEL_NAAM_FIELD, NAAR_AANTAL_LABEL, NAAR_AANTAL_FIELD,
//    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};

  // Optie Expiratie.
  public static final CD[] AA_EFF_REK_OPTIE_EXPIRATIE_COMPONENTS = {
    MAAND_LABEL, MAAND_FIELD, JAAR_LABEL, JAAR_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
//  // Fractieverrekening nieuwe waarden.
//  public static final CD[] AA_EFF_REK_FRACTIEVERREKENING_NIEUWE_WAARDEN_COMPONENTS = {
//    AANDEEL_NAAM_LABEL, AANDEEL_NAAM_FIELD, FRACTIE_LABEL, FRACTIE_FIELD,
//    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
 
  /*
   * Direktbank direktspaarrekening
   */
  // afschrijving
  public static final CD[] DIREKT_SP_REK_AFSCHRIJVING_COMPONENTS = {
    BEDRAG_LABEL, BEDRAG_FIELD, NW_RENTE_PERCENTAGE_LABEL, NW_RENTE_PERCENTAGE_FIELD,
    UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  // bijschrijving
  public static final CD[] DIREKT_SP_REK_BIJSCHRIJVING_COMPONENTS = {
    BEDRAG_LABEL, BEDRAG_FIELD, NW_RENTE_PERCENTAGE_LABEL, NW_RENTE_PERCENTAGE_FIELD,
    UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  // rente  
  public static final CD[] DIREKT_SP_REK_RENTE_COMPONENTS = {
    BEDRAG_LABEL, BEDRAG_FIELD, NW_RENTE_PERCENTAGE_LABEL, NW_RENTE_PERCENTAGE_FIELD,
    VAN_DATUM_LABEL, VAN_DATUM_FIELD, TOT_DATUM_LABEL, TOT_DATUM_FIELD,
    UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  /*
   * Lynx effecten rekening
   */
  // aankoop, verkoop
  public static final CD[] LYNX_EFF_REK_AANKOOP_VERKOOP_COMPONENTS = {
    EFFECT_TYPE_LABEL, EFFECT_TYPE_FIELD};
  // aandelen
  public static final CD[] LYNX_EFF_REK_AANKOOP_VERKOOP_AANDELEN_COMPONENTS = {
    EFFECT_TYPE_LABEL, EFFECT_TYPE_FIELD, AANDEEL_NAAM_LABEL, AANDEEL_NAAM_FIELD,
    AANTAL_LABEL, AANTAL_FIELD, KOERS_LABEL, KOERS_FIELD,
    ORDER_KOSTEN_LABEL, ORDER_KOSTEN_OPT_FIELD,
    UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD, UITVOERINGS_TYPE_LABEL, UITVOERINGS_TYPE_COMBOBOX,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD, ZONDER_KOSTEN_LABEL, ZONDER_KOSTEN_CHECKBOX};
  // claim rechten
  public static final CD[] LYNX_EFF_REK_AANKOOP_VERKOOP_CLAIM_RIGHTS_COMPONENTS = {
    EFFECT_TYPE_LABEL, EFFECT_TYPE_FIELD, AANDEEL_NAAM_LABEL, AANDEEL_NAAM_FIELD,
    CLAIM_ID_LABEL, CLAIM_ID_FIELD,
    AANTAL_LABEL, AANTAL_FIELD,
    UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  // af- en bijschrijving
  public static final CD[] LYNX_EFF_REK_AFSCHRIJVING_COMPONENTS = {
    BEDRAG_LABEL, BEDRAG_FIELD, OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  public static final CD[] LYNX_EFF_REK_BIJSCHRIJVING_COMPONENTS = {
    BEDRAG_LABEL, BEDRAG_FIELD, OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  // dividend
  public static final CD[]                  LYNX_EFF_REK_DIVIDEND_COMPONENTS = {
    AANDEEL_NAAM_LABEL, AANDEEL_NAAM_FIELD, DIVIDEND_LABEL, DIVIDEND_FIELD,
    AANTAL_LABEL, AANTAL_FIELD,
    UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD, ZONDER_KOSTEN_LABEL, ZONDER_KOSTEN_CHECKBOX};
  
  // maand overzicht
  public static final CD[] LYNX_EFF_REK_MAAND_OVERZICHT_COMPONENTS = {
    JAAR_LABEL, JAAR_FIELD, MAAND_LABEL, MAAND_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  // kwartaal overzicht
  public static final CD[] LYNX_EFF_REK_KWARTAAL_OVERZICHT_COMPONENTS = {
    JAAR_LABEL, JAAR_FIELD, KWARTAAL_LABEL, KWARTAAL_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
    
  // belasting overzicht
  public static final CD[] LYNX_EFF_REK_BELASTING_OVERZICHT_COMPONENTS = {
    JAAR_LABEL, JAAR_FIELD, OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  // bonus aandelen
  public static final CD[] LYNX_EFF_REK_BONUS_AANDELEN_COMPONENTS = {
    AANDEEL_NAAM_LABEL, AANDEEL_NAAM_FIELD, AANTAL_LABEL, AANTAL_FIELD, 
    UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD, OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  // Claim Rights Received.
  public static final CD[] LYNX_EFF_REK_CLAIM_RIGHTS_RECEIVED_COMPONENTS = {
    AANDEEL_NAAM_LABEL, AANDEEL_NAAM_FIELD, CLAIM_ID_LABEL, CLAIM_ID_FIELD,
    AANTAL_RECHTEN_LABEL, AANTAL_RECHTEN_FIELD, UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  // Renteverrekening.
  public static final CD[] LYNX_EFF_REK_RENTEVERREKENING_COMPONENTS = {
    VAN_DATUM_LABEL, VAN_DATUM_FIELD, TOT_DATUM_LABEL, TOT_DATUM_FIELD, 
    CREDIT_RENTE_LABEL, CREDIT_RENTE_FIELD, DEBET_RENTE_LABEL, DEBET_RENTE_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};

  // Overname.
  public static final CD[] LYNX_EFF_REK_OVERNAME_COMPONENTS = {
    VAN_AANDEEL_NAAM_LABEL, VAN_AANDEEL_NAAM_FIELD, VAN_AANTAL_LABEL, VAN_AANTAL_FIELD, 
    NAAR_AANDEEL_NAAM_LABEL, NAAR_AANDEEL_NAAM_FIELD, NAAR_AANTAL_LABEL, NAAR_AANTAL_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};

  // Optie Expiratie.
  public static final CD[] LYNX_EFF_REK_OPTIE_EXPIRATIE_COMPONENTS = {
    MAAND_LABEL, MAAND_FIELD, JAAR_LABEL, JAAR_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  // Fractieverrekening nieuwe waarden.
  public static final CD[] LYNX_EFF_REK_FRACTIEVERREKENING_NIEUWE_WAARDEN_COMPONENTS = {
    AANDEEL_NAAM_LABEL, AANDEEL_NAAM_FIELD, FRACTIE_LABEL, FRACTIE_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  

  /*
   * Postbank effecten rekening
   */
  // aankoop, verkoop
  public static final CD[] PB_EFF_REK_AANKOOP_VERKOOP_COMPONENTS = {
    EFFECT_TYPE_LABEL, EFFECT_TYPE_FIELD};
  // aandelen  PRIO1 Orderkosten optioneel maken.
  public static final CD[] PB_EFF_REK_AANKOOP_VERKOOP_AANDELEN_COMPONENTS = {
    EFFECT_TYPE_LABEL, EFFECT_TYPE_FIELD, AANDEEL_NAAM_LABEL, AANDEEL_NAAM_FIELD,
    AANTAL_LABEL, AANTAL_FIELD, KOERS_LABEL, KOERS_FIELD,
    ORDER_KOSTEN_LABEL, ORDER_KOSTEN_OPT_FIELD,
    UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD, UITVOERINGS_TYPE_LABEL, UITVOERINGS_TYPE_COMBOBOX,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD, ZONDER_KOSTEN_LABEL, ZONDER_KOSTEN_CHECKBOX};
  // claim rechten
  public static final CD[] PB_EFF_REK_AANKOOP_VERKOOP_CLAIM_RIGHTS_COMPONENTS = {
    EFFECT_TYPE_LABEL, EFFECT_TYPE_FIELD, AANDEEL_NAAM_LABEL, AANDEEL_NAAM_FIELD,
    CLAIM_ID_LABEL, CLAIM_ID_FIELD,
    AANTAL_LABEL, AANTAL_FIELD,
    UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  // van giro, naar giro
  public static final CD[] PB_EFF_REK_VAN_GIRO_COMPONENTS = {
    BEDRAG_LABEL, BEDRAG_FIELD, OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  public static final CD[] PB_EFF_REK_NAAR_GIRO_COMPONENTS = {
    BEDRAG_LABEL, BEDRAG_FIELD, OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  // dividend
  public static final CD[]                  PB_EFF_REK_DIVIDEND_COMPONENTS = {
    AANDEEL_NAAM_LABEL, AANDEEL_NAAM_FIELD, DIVIDEND_LABEL, DIVIDEND_FIELD,
    AANTAL_LABEL, AANTAL_FIELD,
    UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD, ZONDER_KOSTEN_LABEL, ZONDER_KOSTEN_CHECKBOX};
  
  // bewaarloon
  public static final CD[] PB_EFF_REK_BEWAARLOON_COMPONENTS = {
    BEDRAG_LABEL, BEDRAG_FIELD, OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  // kwartaal overzicht
  public static final CD[] PB_EFF_REK_KWARTAAL_OVERZICHT_COMPONENTS = {
    JAAR_LABEL, JAAR_FIELD, KWARTAAL_LABEL, KWARTAAL_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
    
  // belasting overzicht
  public static final CD[] PB_EFF_REK_BELASTING_OVERZICHT_COMPONENTS = {
    JAAR_LABEL, JAAR_FIELD, OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  // bonus aandelen
  public static final CD[] PB_EFF_REK_BONUS_AANDELEN_COMPONENTS = {
    AANDEEL_NAAM_LABEL, AANDEEL_NAAM_FIELD, AANTAL_LABEL, AANTAL_FIELD, 
    UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD, OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  // Claim Rights Received.
  public static final CD[] PB_EFF_REK_CLAIM_RIGHTS_RECEIVED_COMPONENTS = {
    AANDEEL_NAAM_LABEL, AANDEEL_NAAM_FIELD, CLAIM_ID_LABEL, CLAIM_ID_FIELD,
    AANTAL_RECHTEN_LABEL, AANTAL_RECHTEN_FIELD, UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  // Renteverrekening.
  public static final CD[] PB_EFF_REK_RENTEVERREKENING_COMPONENTS = {
    VAN_DATUM_LABEL, VAN_DATUM_FIELD, TOT_DATUM_LABEL, TOT_DATUM_FIELD, 
    CREDIT_RENTE_LABEL, CREDIT_RENTE_FIELD, DEBET_RENTE_LABEL, DEBET_RENTE_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};

  // Overname.
  public static final CD[] PB_EFF_REK_OVERNAME_COMPONENTS = {
    VAN_AANDEEL_NAAM_LABEL, VAN_AANDEEL_NAAM_FIELD, VAN_AANTAL_LABEL, VAN_AANTAL_FIELD, 
    NAAR_AANDEEL_NAAM_LABEL, NAAR_AANDEEL_NAAM_FIELD, NAAR_AANTAL_LABEL, NAAR_AANTAL_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};

  // Optie Expiratie.
  public static final CD[] PB_EFF_REK_OPTIE_EXPIRATIE_COMPONENTS = {
    MAAND_LABEL, MAAND_FIELD, JAAR_LABEL, JAAR_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  // Fractieverrekening nieuwe waarden.
  public static final CD[] PB_EFF_REK_FRACTIEVERREKENING_NIEUWE_WAARDEN_COMPONENTS = {
    AANDEEL_NAAM_LABEL, AANDEEL_NAAM_FIELD, FRACTIE_LABEL, FRACTIE_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  
  /*
   * Postbank spaarrekening
   */
  // overschrijving van giro, naar giro
  public static final CD[] PB_RENTE_REK_OVERSCHRIJVING_COMPONENTS = {
    BEDRAG_LABEL, BEDRAG_FIELD, NW_RENTE_PERCENTAGE_LABEL, NW_RENTE_PERCENTAGE_FIELD,
    UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  public static final CD[] PB_RENTE_REK_RENTE_COMPONENTS = {
    BEDRAG_LABEL, BEDRAG_FIELD, NW_RENTE_PERCENTAGE_LABEL, NW_RENTE_PERCENTAGE_FIELD,
    VAN_DATUM_LABEL, VAN_DATUM_FIELD, TOT_DATUM_LABEL, TOT_DATUM_FIELD, 
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  public static final CD[] PB_RENTE_REK_RENTE_AANPASSING_COMPONENTS = {
    UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD,
    NW_RENTE_PERCENTAGE_LABEL, NW_RENTE_PERCENTAGE_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  public static final CD[] PB_RENTE_REK_GESCHENK_INLEG_COMPONENTS = {
    BEDRAG_LABEL, BEDRAG_FIELD, NW_RENTE_PERCENTAGE_LABEL, NW_RENTE_PERCENTAGE_FIELD,
    UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD};
  public static final CD[] PB_PLUS_REK_OVERSCHRIJVING_COMPONENTS = {
    BEDRAG_LABEL, BEDRAG_FIELD, NW_RENTE_PERCENTAGE_LABEL, NW_RENTE_PERCENTAGE_FIELD,
    UITVOERINGS_DATUM_LABEL, UITVOERINGS_DATUM_FIELD,
    OPMERKINGEN_LABEL, OPMERKINGEN_FIELD,
    ZONDER_KOSTEN_LABEL, ZONDER_KOSTEN_CHECKBOX};
}
