/**
 * 
 */
package goedegep.finan.logic;

import java.io.File;
import java.lang.reflect.Field;
import java.util.logging.Logger;

import goedegep.myworld.common.Registry;

/**
 * This class contains settings which are shared throughout the Finan application.
 *
 */
public class FinanRegistry extends Registry {
  private static final Logger         LOGGER = Logger.getLogger(FinanRegistry.class.getName());
  
  /**
   * Name of the file with a job appointment.
   */
  private static String jobAppointmentFile = null;

  /**
   * Name of the file with all beleggingsverzekeringen.
   */
  private static String investmentInsurancesFileName = null;
  
  /**
   * Name of the file with all information on claim emissions.
   */
  private static String claimEmissionsFile = "ClaimEmissions.xml";
  
  /**
   * Name of the file with all information on companies.
   */
  private static String companiesFile = "Companies.xml";
  
  /**
   * Name of the file with all information on company funds.
   */
  private static String companyFundsFile = "CompanyFunds.xml";
  
  /**
   * Name of the file with Configuration data.
   */
  private static String configurationFile = null;
  
  /**
   * Directory where all user data files are stored.
   */
  private static String dataDirectory = null;
  
  /**
   * Name of the bank which is selected by default.
   */
  private static String defaultBank = null;
  
  /**
   * Identification of the morgage which is selected by default.
   */
  private static String defaultHypotheekId = null;
  
  /**
   * Name of the file describing the financial units.
   */
  private static String financieleEenhedenFile = null;
  
  /**
   * Name of the file with all shares issued for a fund.
   */
  private static String fundSharesFile = "FundShares.xml";
  
  /**
   * File with share mappings from Lynx to Finan.
   */
  private static String lynxShareIdToFinanShareNamesFile = null;
  
  /**
   * Name of the file containing information on mortgages.
   */
  private static String mortgagesFileName = null;
  
  /**
   * Name of the file with all option series.
   */
  private static String optionSeriesFile = "OptionSeries.xml";
  
  /**
   * Default account for the 'Postbank'.
   */
  private static String postbankDefaultRekening = null;
  
  /**
   * Name of the file with all dividends per share.
   */
  private static String shareDividendsFile = "ShareDividends.xml";
  
  /**
   * Name of the file with all tax rates per share.
   */
  private static String shareTaxRatesFile = "BelastingKoersen.xml";
  
  /**
   * File with all financial transactions.
   */
  private static File transactionsFile = null;
  
  /**
   * Name of the file with all financial transactions.
   */
  private static String transactionsFileName = null;
  
  /*
   * If transactions are handled and there's any change in the transactions,
   * the transactions are rewind. A manual 'handle transactions' is needed to handle them again.
   */
  private static boolean transactionsHandled = false;
  
  
  /**
   * Singleton instance of the FinanRegistry.
   */
  private static FinanRegistry instance = null;

  /**
   * Get the singleton instance of the FinanRegistry.
   * 
   * @return the singleton instance of the FinanRegistry.
   */
  public static FinanRegistry getInstance() {
    if (instance == null) {
      instance = new FinanRegistry();
    }
    
    return instance;
  }
  
  /**
   * Gets the name of the file with job appointments.
   * 
   * @return the name of the file with job appointments.
   */
  public String getJobAppointmentFile() {
    return jobAppointmentFile;
  }

  /**
   * Sets the name of the file with job appointments.
   * 
   * @param jobAppointmentFile the name of the file with job appointments.
   */
  public void setJobAppointmentFile(String jobAppointmentFile) {
    FinanRegistry.jobAppointmentFile = jobAppointmentFile;
  }

  /**
   * Gets the name of the file with all investment insurances.
   * 
   * @return the name of the file with all investment insurances.
   */
  public String getInvestmentInsurancesFileName() {
    return investmentInsurancesFileName;
  }

  /**
   * Set the name of the file with all investment insurances.
   * 
   * @param investmentInsurancesFileName the name of the file with all investment insurances.
   */
  public void setInvestmentInsurancesFileName(String investmentInsurancesFileName) {
    FinanRegistry.investmentInsurancesFileName = investmentInsurancesFileName;
  }

  /**
   * Gets the name of the file with all claim emissions.
   * 
   * @return the name of the file with all claim emissions.
   */
  public String getClaimEmissionsFile() {
    return claimEmissionsFile;
  }

  /**
   * Sets the name of the file with all claim emissions.
   * 
   * @param claimEmissionsFile the name of the file with all claim emissions.
   */
  public void setClaimEmissionsFile(String claimEmissionsFile) {
    FinanRegistry.claimEmissionsFile = claimEmissionsFile;
  }

  /**
   * Gets the name of the file with all companies.
   * 
   * @return the name of the file with all companies.
   */
  public String getCompaniesFile() {
    return companiesFile;
  }

  /**
   * Sets the name of the file with all companies.
   * 
   * @param companiesFile the name of the file with all companies.
   */
  public void setCompaniesFile(String companiesFile) {
    FinanRegistry.companiesFile = companiesFile;
  }

  /**
   * Gets the name of the file with all company funds.
   * 
   * @return the name of the file with all company funds.
   */
  public String getCompanyFundsFile() {
    return companyFundsFile;
  }

  /**
   * Sets the name of the file with all company funds.
   * 
   * @param companyFundsFile the name of the file with all company funds.
   */
  public void setCompanyFundsFile(String companyFundsFile) {
    FinanRegistry.companyFundsFile = companyFundsFile;
  }

  /**
   * Gets the name of the file with Configuration data.
   * 
   * @return the name of the file with Configuration data.
   */
  public String getConfigurationFile() {
    return configurationFile;
  }

  /**
   * Sets the name of the file with Configuration data.
   * 
   * @param configurationFile the name of the file with Configuration data.
   */
  public void setConfigurationFile(String configurationFile) {
    FinanRegistry.configurationFile = configurationFile;
  }

  /**
   * Gets the directory where all user data files are stored.
   * 
   * @return the directory where all user data files are stored.
   */
  public String getDataDirectory() {
    return dataDirectory;
  }

  /**
   * Sets the directory where all user data files are stored.
   * 
   * @param dataDirectory the directory where all user data files are stored.
   */
  public void setDataDirectory(String dataDirectory) {
    FinanRegistry.dataDirectory = dataDirectory;
  }

  /**
   * Gets the name of the default bank.
   * 
   * @return the name of the defautl bank.
   */
  public String getDefaultBank() {
    return defaultBank;
  }

  /**
   * Sets the name of the default bank.
   * 
   * @param defaultBank the name of the default bank.
   */
  public void setDefaultBank(String defaultBank) {
    FinanRegistry.defaultBank = defaultBank;
  }

  /**
   * Gets the identification of the default mortgage.
   * 
   * @return the identification of the default mortgage.
   */
  public String getDefaultHypotheekId() {
    return defaultHypotheekId;
  }

  /**
   * Sets the identification of the default mortgage.
   * 
   * @param defaultHypotheekId the identification of the default mortgage.
   */
  public void setDefaultHypotheekId(String defaultHypotheekId) {
    FinanRegistry.defaultHypotheekId = defaultHypotheekId;
  }

  /**
   * Gets the name of the file with the financial units.
   * 
   * @return the name of the file with the financial units.
   */
  public String getFinancieleEenhedenFile() {
    return financieleEenhedenFile;
  }

  /**
   * Sets the name of the file with the financial units.
   * 
   * @param financieleEenhedenFile the name of the file with the financial units.
   */
  public void setFinancieleEenhedenFile(String financieleEenhedenFile) {
    FinanRegistry.financieleEenhedenFile = financieleEenhedenFile;
  }

  /**
   * Gets the name of the file with all shares issued for a fund.
   * 
   * @return the name of the file with all shares issued for a fund.
   */
  public String getFundSharesFile() {
    return fundSharesFile;
  }

  /**
   * Sets the name of the file with all shares issued for a fund.
   * 
   * @param fundSharesFile the name of the file with all shares issued for a fund.
   */
  public void setFundSharesFile(String fundSharesFile) {
    FinanRegistry.fundSharesFile = fundSharesFile;
  }

  /**
   * Gets the file with share mappings from Lynx to Finan.
   * 
   * @return the file with share mappings from Lynx to Finan.
   */
  public String getLynxShareIdToFinanShareNamesFile() {
    return lynxShareIdToFinanShareNamesFile;
  }

  /**
   * Sets the file with share mappings from Lynx to Finan.
   * 
   * @param lynxShareIdToFinanShareNamesFile the file with share mappings from Lynx to Finan.
   */
  public void setLynxShareIdToFinanShareNamesFile(String lynxShareIdToFinanShareNamesFile) {
    FinanRegistry.lynxShareIdToFinanShareNamesFile = lynxShareIdToFinanShareNamesFile;
  }

  /**
   * Gets the name of the file containing information on mortgages.
   * 
   * @return the name of the file containing information on mortgages.
   */
  public String getMortgagesFileName() {
    return mortgagesFileName;
  }

  /**
   * Sets the name of the file containing information on mortgages.
   * 
   * @param mortgagesFileName the name of the file containing information on mortgages.
   */
  public void setMortgagesFileName(String mortgagesFileName) {
    FinanRegistry.mortgagesFileName = mortgagesFileName;
  }

  /**
   * Gets the name of the file with all option series.
   * 
   * @return the name of the file with all option series.
   */
  public String getOptionSeriesFile() {
    return optionSeriesFile;
  }

   /**
    * Sets the name of the file with all option series.
    * 
    * @param optionSeriesFile the name of the file with all option series.
    */
  public void setOptionSeriesFile(String optionSeriesFile) {
    FinanRegistry.optionSeriesFile = optionSeriesFile;
  }

  /**
   * Gets the default account for the 'Postbank'.
   * 
   * @return the default account for the 'Postbank'.
   */
  public String getPostbankDefaultRekening() {
    return postbankDefaultRekening;
  }

  /**
   * Sets the default account for the 'Postbank'.
   * 
   * @param postbankDefaultRekening the default account for the 'Postbank'.
   */
  public void setPostbankDefaultRekening(String postbankDefaultRekening) {
    FinanRegistry.postbankDefaultRekening = postbankDefaultRekening;
  }

  /**
   * Gets the name of the file with all dividends per share.
   */
  public String getShareDividendsFile() {
    return shareDividendsFile;
  }

  /**
   * Sets the name of the file with all dividends per share.
   */
  public void setShareDividendsFile(String shareDividendsFile) {
    FinanRegistry.shareDividendsFile = shareDividendsFile;
  }

  /**
   * Gets the name of the file with all tax rates per share.
   */
  public String getShareTaxRatesFile() {
    return shareTaxRatesFile;
  }

  /**
   * Sets the name of the file with all tax rates per share.
   */
  public void setShareTaxRatesFile(String shareTaxRatesFile) {
    FinanRegistry.shareTaxRatesFile = shareTaxRatesFile;
  }

  /**
   * Gets the file with all financial transactions.
   */
  public File getTransactionsFile() {
    return transactionsFile;
  }

  /**
   * Sets the file with all financial transactions.
   */
  public void setTransactionsFile(File transactionsFile) {
    FinanRegistry.transactionsFile = transactionsFile;
  }

  /**
   * Gets the name of the file with all financial transactions.
   */
  public String getTransactionsFileName() {
    return transactionsFileName;
  }

  /**
   * Sets the name of the file with all financial transactions.
   */
  public void setTransactionsFileName(String transactionsFileName) {
    FinanRegistry.transactionsFileName = transactionsFileName;
  }

  /**
   * Checks if the transactions are handled.
   */
  public boolean areTransactionsHandled() {
    return transactionsHandled;
  }

  /**
   * Sets if the transactions are handled.
   */
  public void setTransactionsHandled(boolean transactionsHandled) {
    FinanRegistry.transactionsHandled = transactionsHandled;
  }


  /**
   * Gets the default account for a specific Bank.
   * The value is obtained by returning the value of the field with
   * the name "<bank>DefaultRekening".
   * @param bank Name of the bank for which the default account shall be returned.
   * @return The default account for the specified bank,
   *         or null if the related field doesn't exist.
   */
  public static String defaultAccountForBank(String bank) {
    Field field;
    try {
      field = FinanRegistry.class.getField(bank.toLowerCase() + "DefaultRekening");
      return (String) field.get(null);
    } catch (SecurityException e) {
      // This should never happen, print stack and exit.
      e.printStackTrace();
      System.exit(-1);
    } catch (IllegalAccessException e) {
      // This should never happen, print stack and exit.
      e.printStackTrace();
      System.exit(-1);
    } catch (NoSuchFieldException e) {
      // It seems we don't have a default account for this bank, return null.
    }

    LOGGER.fine(bank);
    return bank;
  }
  
  
  /**
   * A static version of toString. It lists the values of all fields.
   * @return The values of all fields as a String.
   */
  public static String toStringStatic() {
	StringBuffer sb = new StringBuffer();
	sb.append("Registry contents:").append(System.getProperty("line.separator"));
    for (Field field: FinanRegistry.class.getFields()) {
      try {
        sb.append(field.getName());
        sb.append(": ");
        Object fieldValue = field.get(null);
        if (fieldValue == null) {
          sb.append("null");
        } else if (fieldValue instanceof String) {
          sb.append((String) fieldValue);
        } else {
          sb.append(fieldValue.toString());
        }
          
        sb.append(System.getProperty("line.separator"));
      } catch (IllegalArgumentException e) {
        // This should never happen, print stack and exit.
        e.printStackTrace();
        System.exit(-1);
      } catch (IllegalAccessException e) {
        // This should never happen, print stack and exit.
        e.printStackTrace();
        System.exit(-1);
      }
    }
		
	return sb.toString();
  }
  
  /**
   * Private constructor for the singleton FinanRegistry.
   */
  private FinanRegistry() {
    super();
    
    setAuthor("Peter Goedegebure");
    setShortProductInfo("Financial program");
    setPropertyDescriptorsFileName("FinanPropertyDescriptors.xmi");
    setUserPropertiesFileName("FinanUserPreferences.xmi");
  }

  @Override
  public boolean setValue(String name, String value) {
    if (super.setValue(name, value)) {
      return true;
    }

    boolean known = true;
    switch (name) {
    case "dataDirectory" -> dataDirectory = value;
    case "jobAppointmentFile" -> jobAppointmentFile = value;
    case "transactionsFile" -> transactionsFileName = value;
    case "investmentInsurancesFileName" -> investmentInsurancesFileName = value;
    case "mortgagesFileName" -> mortgagesFileName = value;
    default -> known = false;
    }

    return known;
  }
}
