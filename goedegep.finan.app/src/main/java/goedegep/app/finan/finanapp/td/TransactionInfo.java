package goedegep.app.finan.finanapp.td;

import java.util.logging.Logger;

import goedegep.app.finan.td.CD;
import goedegep.app.finan.td.EffectType;
import goedegep.app.finan.td.Expression;
import goedegep.app.finan.td.ExpressionDyadic;
import goedegep.app.finan.td.OperatorDyadic;
import goedegep.app.finan.td.TransactionDialogComponentList;
import goedegep.finan.stocks.OptieTransactieType;

public enum TransactionInfo {

  // ABN AMRO, effectenrekening
  // Aankoop aandelen.
  AA_EFF_REK_AANKOOP_AANDELEN("ABN AMRO", "Effectenrekening", "aankoop",
      ComponentDescriptors.AA_EFF_REK_AANKOOP_VERKOOP_AANDELEN_COMPONENTS,
      AAEffRekTransactionConstructors.class, "createAAEffRekAandelenTransactie",
      (Expression) (new ExpressionDyadic(ComponentDescriptors.EFFECT_TYPE_FIELD, OperatorDyadic.EQUALS, EffectType.AANDELEN.getName()))),
//  // Aankoop claim rights.
//  AA_EFF_REK_AANKOOP_CLAIM_RIGHTS("ABN AMRO", "Effectenrekening", "aankoop",
//      ComponentDescriptors.AA_EFF_REK_AANKOOP_VERKOOP_CLAIM_RIGHTS_COMPONENTS,
//      AAEffRekTransactionConstructors.class, "createAAEffRekClaimRightsTransaction",
//      (Expression) (new ExpressionDyadic(ComponentDescriptors.EFFECT_TYPE_FIELD, OperatorDyadic.EQUALS, EffectType.CLAIM_RIGHTS.getName()))),
  // Aankoop ...
  AA_EFF_REK_AANKOOP("ABN AMRO", "Effectenrekening", "aankoop",
      ComponentDescriptors.AA_EFF_REK_AANKOOP_VERKOOP_COMPONENTS,
      null, null),
  // Verkoop aandelen of opties.
  AA_EFF_REK_VERKOOP_AANDELEN("ABN AMRO", "Effectenrekening", "verkoop",
      ComponentDescriptors.AA_EFF_REK_AANKOOP_VERKOOP_AANDELEN_COMPONENTS,
      AAEffRekTransactionConstructors.class, "createAAEffRekAandelenTransactie",
      (Expression) (new ExpressionDyadic(ComponentDescriptors.EFFECT_TYPE_FIELD, OperatorDyadic.EQUALS, EffectType.AANDELEN.getName()))),
//  // Verkoop claim rights.
//  AA_EFF_REK_VERKOOP_CLAIM_RIGHTS("ABN AMRO", "Effectenrekening", "verkoop",
//      ComponentDescriptors.AA_EFF_REK_AANKOOP_VERKOOP_CLAIM_RIGHTS_COMPONENTS,
//      AAEffRekTransactionConstructors.class, "createAAEffRekClaimRightsTransaction",
//      (Expression) (new ExpressionDyadic(ComponentDescriptors.EFFECT_TYPE_FIELD, OperatorDyadic.EQUALS, EffectType.CLAIM_RIGHTS.getName()))),
  // Verkoop ...
  AA_EFF_REK_VERKOOP("ABN AMRO", "Effectenrekening", "verkoop",
      ComponentDescriptors.AA_EFF_REK_AANKOOP_VERKOOP_COMPONENTS,
      null, null),
  
  // aan- en verkoop opties
  // Openingskoop opties.
  AA_EFF_REK_OPENINGSKOOP_OPTIES("ABN AMRO", "Effectenrekening", OptieTransactieType.OPENINGSKOOP.getDescription(),
      ComponentDescriptors.AA_EFF_REK_AANKOOP_VERKOOP_OPTIES_COMPONENTS,
      AAEffRekTransactionConstructors.class, "createAAEffRekOptieTransactie"),
  // Openingsverkoop opties.
  AA_EFF_REK_OPENINGSVERKOOP_OPTIES("ABN AMRO", "Effectenrekening", OptieTransactieType.OPENINGSVERKOOP.getDescription(),
      ComponentDescriptors.AA_EFF_REK_AANKOOP_VERKOOP_OPTIES_COMPONENTS,
      AAEffRekTransactionConstructors.class, "createAAEffRekOptieTransactie"),
  // Sluitingskoop opties.
  AA_EFF_REK_SLUITINGSKOOP_OPTIES("ABN AMRO", "Effectenrekening", OptieTransactieType.SLUITINGSKOOP.getDescription(),
      ComponentDescriptors.AA_EFF_REK_AANKOOP_VERKOOP_OPTIES_COMPONENTS,
      AAEffRekTransactionConstructors.class, "createAAEffRekOptieTransactie"),
  // Sluitingsverkoop opties.
  AA_EFF_REK_SLUITINGSVERKOOP_OPTIES("ABN AMRO", "Effectenrekening", OptieTransactieType.SLUITINGSVERKOOP.getDescription(),
      ComponentDescriptors.AA_EFF_REK_AANKOOP_VERKOOP_OPTIES_COMPONENTS,
      AAEffRekTransactionConstructors.class, "createAAEffRekOptieTransactie"),
  // af- en bijschrijving
  AA_EFF_REK_AFSCHRIJVING("ABN AMRO", "Effectenrekening", "af",
      ComponentDescriptors.AA_EFF_REK_AFSCHRIJVING_COMPONENTS,
      AAEffRekTransactionConstructors.class, "createAAEffRekAfschrijving"),
  AA_EFF_REK_BIJSCHRIJVING("ABN AMRO", "Effectenrekening", "bij",
      ComponentDescriptors.AA_EFF_REK_BIJSCHRIJVING_COMPONENTS,
      AAEffRekTransactionConstructors.class, "createAAEffRekBijschrijving"),
  // af- en bijschrijving
  AA_EFF_REK_NAAR_DEKKINGSREKENING("ABN AMRO", "Effectenrekening", "naar dekkingsrekening",
      ComponentDescriptors.AA_EFF_REK_DEKKINGS_REKENING_COMPONENTS,
      AAEffRekTransactionConstructors.class, "createAAEffRekAanpassingDekkingsVerplichting"),
  AA_EFF_REK_VAN_DEKKINGSREKENING("ABN AMRO", "Effectenrekening", "van dekkingsrekening",
      ComponentDescriptors.AA_EFF_REK_DEKKINGS_REKENING_COMPONENTS,
      AAEffRekTransactionConstructors.class, "createAAEffRekAanpassingDekkingsVerplichting"),
  // Dividend
  AA_EFF_REK_DIVIDEND("ABN AMRO", "Effectenrekening", "dividend",
      ComponentDescriptors.AA_EFF_REK_DIVIDEND_COMPONENTS,
      AAEffRekTransactionConstructors.class, "createAAEffRekDividend"),
  // Renteverrekening
  AA_EFF_REK_RENTEVERREKENING("ABN AMRO", "Effectenrekening", "renteverrekening",
      ComponentDescriptors.AA_EFF_REK_RENTEVERREKENING_COMPONENTS,
      AAEffRekTransactionConstructors.class, "createAAEffRekRenteverrekening"),
  // Bewaarloon.
  AA_EFF_REK_BEWAARLOON("ABN AMRO", "Effectenrekening", "bewaarloon",
      ComponentDescriptors.AA_EFF_REK_BEWAARLOON_COMPONENTS,
      AAEffRekTransactionConstructors.class, "createAAEffRekBewaarloon"),
//  // Bonus aandelen.
//  AA_EFF_REK_BONUS_AANDELEN("ABN AMRO", "Effectenrekening", "bonusaandelen",
//      ComponentDescriptors.AA_EFF_REK_BONUS_AANDELEN_COMPONENTS,
//      AAEffRekTransactionConstructors.class, "createAAEffRekBonusAandelen"),
//  // Claimrechten ontvangen.
//  AA_EFF_REK_CLAIM_RIGHTS_RECEIVED("ABN AMRO", "Effectenrekening", "claimrechten ontvangen",
//      ComponentDescriptors.AA_EFF_REK_CLAIM_RIGHTS_RECEIVED_COMPONENTS,
//      AAEffRekTransactionConstructors.class, "createAAEffRekClaimRightsReceived"),
//  // Overname.
//  AA_EFF_REK_OVERNAME("ABN AMRO", "Effectenrekening", "overname",
//      ComponentDescriptors.AA_EFF_REK_OVERNAME_COMPONENTS,
//      AAEffRekTransactionConstructors.class, "createAAEffRekOvername"),
  // Optie expiratie.
  AA_EFF_REK_OPTIE_EXPIRATIE("ABN AMRO", "Effectenrekening", "optie expiratie",
      ComponentDescriptors.AA_EFF_REK_OPTIE_EXPIRATIE_COMPONENTS,
      AAEffRekTransactionConstructors.class, "createAAEffRekOptieExpiratie"),
//  // Fractieverrekening nieuwe waarde.
//  AA_EFF_REK_AANKOOP_AANDEEL_FRACTIE("ABN AMRO", "Effectenrekening", "fractie aankoop",
//      ComponentDescriptors.AA_EFF_REK_FRACTIEVERREKENING_NIEUWE_WAARDEN_COMPONENTS,
//      AAEffRekTransactionConstructors.class, "createAAEffRekFractieVerrekeningNieuweWaarden"),
//  // Kwartaal overzicht.
//  AA_EFF_REK_KWARTAAL_OVERZICHT("ABN AMRO", "Effectenrekening", "kwartaaloverzicht",
//      ComponentDescriptors.AA_EFF_REK_KWARTAAL_OVERZICHT_COMPONENTS,
//      AAEffRekTransactionConstructors.class, "createAAEffRekKwartaalOverzicht"),
  // Belasting overzicht.
  AA_EFF_REK_BELASTINGOVERZICHT("ABN AMRO", "Effectenrekening", "belastingoverzicht",
      ComponentDescriptors.AA_EFF_REK_BELASTING_OVERZICHT_COMPONENTS,
      AAEffRekTransactionConstructors.class, "createAAEffRekBelastingOverzicht"),
      
  // Direktbank, direktspaarrekening
  // Afschrijving
  DIREKT_SP_REK_AF("Direktbank", "Direktspaarrekening", "af",
      ComponentDescriptors.DIREKT_SP_REK_AFSCHRIJVING_COMPONENTS,
      DirektSpRekTransactionConstructors.class, "createDirektSpRekAfschrijving"),
  // Bijschrijving
  DIREKT_SP_REK_BIJ("Direktbank", "Direktspaarrekening", "bij",
      ComponentDescriptors.DIREKT_SP_REK_BIJSCHRIJVING_COMPONENTS,
      DirektSpRekTransactionConstructors.class, "createDirektSpRekBijschrijving"),
  // Opheffing
  DIREKT_SP_REK_OPHEFFING("Direktbank", "Direktspaarrekening", "opheffing",
      ComponentDescriptors.DIREKT_SP_REK_AFSCHRIJVING_COMPONENTS,
      DirektSpRekTransactionConstructors.class, "createDirektSpRekOpheffing"),
  // Rente.
  DIREKT_SP_REK_RENTE("Direktbank", "Direktspaarrekening", "rente",
      ComponentDescriptors.DIREKT_SP_REK_RENTE_COMPONENTS,
      DirektSpRekTransactionConstructors.class, "createDirektSpRekRente"),
          

  // Postbank, effectenrekening
  // Aankoop aandelen.
  PB_EFF_REK_AANKOOP_AANDELEN("Postbank", "Effectenrekening", "aankoop",
      ComponentDescriptors.PB_EFF_REK_AANKOOP_VERKOOP_AANDELEN_COMPONENTS,
      TransactionConstructors.class, "createPbEffRekAandelenTransactie",
      (Expression) (new ExpressionDyadic(ComponentDescriptors.EFFECT_TYPE_FIELD, OperatorDyadic.EQUALS, EffectType.AANDELEN.getName()))),
  // Aankoop claim rights.
  PB_EFF_REK_AANKOOP_CLAIM_RIGHTS("Postbank", "Effectenrekening", "aankoop",
      ComponentDescriptors.PB_EFF_REK_AANKOOP_VERKOOP_CLAIM_RIGHTS_COMPONENTS,
      TransactionConstructors.class, "createPbEffRekClaimRightsTransaction",
      (Expression) (new ExpressionDyadic(ComponentDescriptors.EFFECT_TYPE_FIELD, OperatorDyadic.EQUALS, EffectType.CLAIM_RIGHTS.getName()))),
  // Aankoop ...
  PB_EFF_REK_AANKOOP("Postbank", "Effectenrekening", "aankoop",
      ComponentDescriptors.PB_EFF_REK_AANKOOP_VERKOOP_COMPONENTS,
      null, null),
  // Verkoop aandelen of opties.
  PB_EFF_REK_VERKOOP_AANDELEN("Postbank", "Effectenrekening", "verkoop",
      ComponentDescriptors.PB_EFF_REK_AANKOOP_VERKOOP_AANDELEN_COMPONENTS,
      TransactionConstructors.class, "createPbEffRekAandelenTransactie",
      (Expression) (new ExpressionDyadic(ComponentDescriptors.EFFECT_TYPE_FIELD, OperatorDyadic.EQUALS, EffectType.AANDELEN.getName()))),
  // Verkoop claim rights.
  PB_EFF_REK_VERKOOP_CLAIM_RIGHTS("Postbank", "Effectenrekening", "verkoop",
      ComponentDescriptors.PB_EFF_REK_AANKOOP_VERKOOP_CLAIM_RIGHTS_COMPONENTS,
      TransactionConstructors.class, "createPbEffRekClaimRightsTransaction",
      (Expression) (new ExpressionDyadic(ComponentDescriptors.EFFECT_TYPE_FIELD, OperatorDyadic.EQUALS, EffectType.CLAIM_RIGHTS.getName()))),
  // Verkoop ...
  PB_EFF_REK_VERKOOP("Postbank", "Effectenrekening", "verkoop",
      ComponentDescriptors.PB_EFF_REK_AANKOOP_VERKOOP_COMPONENTS,
      null, null),
  // Overschrijving
  PB_EFF_REK_VAN_GIRO("Postbank", "Effectenrekening", "van giro",
      ComponentDescriptors.PB_EFF_REK_VAN_GIRO_COMPONENTS,
      TransactionConstructors.class, "createPbEffRekOverschrijving"),
  PB_EFF_REK_NAAR_GIRO("Postbank", "Effectenrekening", "naar giro",
      ComponentDescriptors.PB_EFF_REK_NAAR_GIRO_COMPONENTS,
      TransactionConstructors.class, "createPbEffRekOverschrijving"),
  // Dividend
  PB_EFF_REK_DIVIDEND("Postbank", "Effectenrekening", "dividend",
      ComponentDescriptors.PB_EFF_REK_DIVIDEND_COMPONENTS,
      TransactionConstructors.class, "createPbEffRekDividend"),
  // Renteverrekening
  PB_EFF_REK_RENTEVERREKENING("Postbank", "Effectenrekening", "renteverrekening",
      ComponentDescriptors.PB_EFF_REK_RENTEVERREKENING_COMPONENTS,
      TransactionConstructors.class, "createPbEffRekRenteverrekening"),
  // Bewaarloon.
  PB_EFF_REK_BEWAARLOON("Postbank", "Effectenrekening", "bewaarloon",
      ComponentDescriptors.PB_EFF_REK_BEWAARLOON_COMPONENTS,
      TransactionConstructors.class, "createPbEffRekBewaarloon"),
  // Bonus aandelen.
  PB_EFF_REK_BONUS_AANDELEN("Postbank", "Effectenrekening", "bonusaandelen",
      ComponentDescriptors.PB_EFF_REK_BONUS_AANDELEN_COMPONENTS,
      TransactionConstructors.class, "createPbEffRekBonusAandelen"),
  // Claimrechten ontvangen.
  PB_EFF_REK_CLAIM_RIGHTS_RECEIVED("Postbank", "Effectenrekening", "claimrechten ontvangen",
      ComponentDescriptors.PB_EFF_REK_CLAIM_RIGHTS_RECEIVED_COMPONENTS,
      TransactionConstructors.class, "createPbEffRekClaimRightsReceived"),
  // Overname.
  PB_EFF_REK_OVERNAME("Postbank", "Effectenrekening", "overname",
      ComponentDescriptors.PB_EFF_REK_OVERNAME_COMPONENTS,
      TransactionConstructors.class, "createPbEffRekOvername"),
  // Optie expiratie.
  PB_EFF_REK_OPTIE_EXPIRATIE("Postbank", "Effectenrekening", "optie expiratie",
      ComponentDescriptors.PB_EFF_REK_OPTIE_EXPIRATIE_COMPONENTS,
      TransactionConstructors.class, "createPbEffRekOptieExpiratie"),
  // Fractieverrekening nieuwe waarde.
  PB_EFF_REK_AANKOOP_AANDEEL_FRACTIE("Postbank", "Effectenrekening", "fractie aankoop",
      ComponentDescriptors.PB_EFF_REK_FRACTIEVERREKENING_NIEUWE_WAARDEN_COMPONENTS,
      TransactionConstructors.class, "createPbEffRekFractieVerrekeningNieuweWaarden"),
  // Kwartaal overzicht.
  PB_EFF_REK_KWARTAAL_OVERZICHT("Postbank", "Effectenrekening", "kwartaaloverzicht",
      ComponentDescriptors.PB_EFF_REK_KWARTAAL_OVERZICHT_COMPONENTS,
      TransactionConstructors.class, "createPbEffRekKwartaalOverzicht"),
  // Belasting overzicht.
  PB_EFF_REK_BELASTINGOVERZICHT("Postbank", "Effectenrekening", "belastingoverzicht",
      ComponentDescriptors.PB_EFF_REK_BELASTING_OVERZICHT_COMPONENTS,
      TransactionConstructors.class, "createPbEffRekBelastingOverzicht"),
      
  // Postbank, renterekening
  // Overschrijving: bij.
  PB_RENTE_REK_BIJ("Postbank", "Renterekening", "bij",
      ComponentDescriptors.PB_RENTE_REK_OVERSCHRIJVING_COMPONENTS,
      PbSpRekTransactionConstructors.class, "createPbSpRekOverschrijving"),
  // Overschrijving: af.
  PB_RENTE_REK_AF("Postbank", "Renterekening", "af",
      ComponentDescriptors.PB_RENTE_REK_OVERSCHRIJVING_COMPONENTS,
      PbSpRekTransactionConstructors.class, "createPbSpRekOverschrijving"),
  // Rente.
  PB_RENTE_REK_RENTE("Postbank", "Renterekening", "rente",
      ComponentDescriptors.PB_RENTE_REK_RENTE_COMPONENTS,
      PbSpRekTransactionConstructors.class, "createPbSpRekRente"),
  // Rente aanpassing (nieuw rentepercentage).
  PB_RENTE_REK_RENTE_AANPASSING("Postbank", "Renterekening", "rente aanpassing",
      ComponentDescriptors.PB_RENTE_REK_RENTE_AANPASSING_COMPONENTS,
      PbSpRekTransactionConstructors.class, "createPbSpRekRenteAanpassing"),
  // Geschenkinleg.
  PB_RENTE_REK_GESCHENK_INLEG("Postbank", "Renterekening", "geschenkinleg",
      ComponentDescriptors.PB_RENTE_REK_GESCHENK_INLEG_COMPONENTS,
      PbSpRekTransactionConstructors.class, "createPbSpRekGeschenkInleg"),
  // Opheffing van de rekening.
  PB_RENTE_REK_OPHEFFING("Postbank", "Renterekening", "opheffing",
      ComponentDescriptors.PB_RENTE_REK_OVERSCHRIJVING_COMPONENTS,
      PbSpRekTransactionConstructors.class, "createPbSpRekOpheffing"),
  // Postbank, plusrekening
  // Overschrijving: bij.
  PB_PLUS_REK_BIJ("Postbank", "Plusrekening", "bij",
      ComponentDescriptors.PB_RENTE_REK_OVERSCHRIJVING_COMPONENTS,
      PbSpRekTransactionConstructors.class, "createPbSpRekOverschrijving"),
  // Overschrijving: af.
  PB_PLUS_REK_AF("Postbank", "Plusrekening", "af",
      ComponentDescriptors.PB_PLUS_REK_OVERSCHRIJVING_COMPONENTS,
      PbSpRekTransactionConstructors.class, "createPbSpRekOverschrijving"),
  // Rente.
  PB_PLUS_REK_RENTE("Postbank", "Plusrekening", "rente",
      ComponentDescriptors.PB_RENTE_REK_RENTE_COMPONENTS,
      PbSpRekTransactionConstructors.class, "createPbSpRekRente"),
  // Rente aanpassing (nieuw rentepercentage).
  PB_PLUS_REK_RENTE_AANPASSING("Postbank", "Plusrekening", "rente aanpassing",
      ComponentDescriptors.PB_RENTE_REK_RENTE_AANPASSING_COMPONENTS,
      PbSpRekTransactionConstructors.class, "createPbSpRekRenteAanpassing"),
  // Geschenkinleg.
  PB_PLUS_REK_GESCHENK_INLEG("Postbank", "Plusrekening", "geschenkinleg",
      ComponentDescriptors.PB_RENTE_REK_GESCHENK_INLEG_COMPONENTS,
      PbSpRekTransactionConstructors.class, "createPbSpRekGeschenkInleg"),
  // Opheffing van de rekening.
  PB_PLUS_REK_OPHEFFING("Postbank", "Plusrekening", "opheffing",
      ComponentDescriptors.PB_RENTE_REK_OVERSCHRIJVING_COMPONENTS,
      PbSpRekTransactionConstructors.class, "createPbSpRekOpheffing");
  
  private static final Logger         LOGGER = Logger.getLogger(TransactionInfo.class.getName());

  private String                bankName;
  private String                accountName;
  private String                transactionType;
  private CD[]                  componentDescriptors;
  @SuppressWarnings({ "rawtypes" })
  private Class                 transactionCreationClass;
  private String                transactionCreationMethod;
  private Expression[]          conditions;
  
  TransactionInfo(String bankName, String accountName, String transactionType,
      CD[] componentDescriptors, @SuppressWarnings("rawtypes") Class transactionCreationClass, String transactionCreationMethod, Expression ... conditions) {
    this.bankName = bankName;
    this.accountName = accountName;
    this.transactionType = transactionType;
    this.componentDescriptors = componentDescriptors;
    this.conditions = conditions;
    this.transactionCreationClass = transactionCreationClass;
    this.transactionCreationMethod = transactionCreationMethod;
  }

  protected String getBankName() {
    return bankName;
  }

  protected String getAccountName() {
    return accountName;
  }

  protected String getTransactionType() {
    return transactionType;
  }
  
  public CD[] getComponentDescriptors() {
    return componentDescriptors;
  }

  @SuppressWarnings({ "rawtypes" })
  public Class getTransactionCreationClass() {
    return transactionCreationClass;
  }
  
  public String getTransactionCreationMethod() {
    return transactionCreationMethod;
  }

  public static TransactionInfo getTransactionInfo(String bankName,
      String accountName, String transactionTypeText,
      TransactionDialogComponentList transactionComponents) {
    for (TransactionInfo transactionInfo: TransactionInfo.values()) {
      if (transactionInfo.getBankName().equals(bankName)  &&
          transactionInfo.getAccountName().equals(accountName)  &&
          transactionInfo.getTransactionType().equalsIgnoreCase(transactionTypeText)) {
        if (!expressionsOK(transactionInfo, transactionComponents)) {
          continue;
        }
        LOGGER.info("<= " + transactionInfo);
        return transactionInfo;
      }
    }
    
    LOGGER.info("<= null");
    return null;
  }
  
  /**
   * Check whether all conditions are met.
   * @param transactionInfo
   * @param transactionComponents
   * @return true if all conditions are met, false otherwise.
   */
  private static boolean expressionsOK(TransactionInfo transactionInfo, 
      TransactionDialogComponentList transactionComponents) {
    for (Expression expression: transactionInfo.conditions) {
      Boolean result = (Boolean) expression.evaluate(transactionComponents);
      LOGGER.info("result = " + result);
      if (!result) {
        return false;
      }
    }
    
    return true;
  }
}
