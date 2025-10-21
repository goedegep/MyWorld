/**
 * 
 */
package goedegep.app.finan.registry;

import java.io.File;
import java.lang.reflect.Field;
import java.util.logging.Logger;

import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.util.emf.EMFResource;

/**
 * This class contains settings which are shared throughout the Finan application.
 *
 */
public class FinanRegistry {
  private static final Logger         LOGGER = Logger.getLogger(FinanRegistry.class.getName());
  
  /**
   * The name of the application.
   */
  public static String applicationName;
  
  /**
   * Name of the file with a job appointment.
   */
  public static String jobAppointmentFile = null;
  
  /**
   * Name of the author of the application.
   */
  public static String author = "Peter Goedegebure";

  /**
   * Name of the file with all beleggingsverzekeringen.
   */
  public static String investmentInsurancesFileName = null;
  
  /**
   * Name of the file with all information on claim emissions.
   */
  public static String claimEmissionsFile = null;
  
  /**
   * Name of the file with all information on companies.
   */
  public static String companiesFile = null;
  
  /**
   * Name of the file with all information on company funds.
   */
  public static String companyFundsFile = null;
  
  /**
   * Name of the file with Configuration data.
   */
  public static String configurationFile = null;
  
  /**
   * Copyright message for the application.
   */
  public static String copyrightMessage = "Copyright (c) 2001-2025";
  
  /**
   * Name of the file with the property descriptors.
   */
  public static String propertyDescriptorsFile = "FinanPropertyDescriptors.xmi";
  
  /**
   * Name of the file with custom properties (settings).
   */
  public static String customPropertiesFile = null;   // Name of the file with custom properties.
  
  /**
   * Directory where all user data files are stored.
   */
  public static String dataDirectory = null;
  
  /**
   * Name of the bank which is selected by default.
   */
  public static String defaultBank = null;
  
  /**
   * Identification of the morgage which is selected by default.
   */
  public static String defaultHypotheekId = null;
  
  /**
   * If true, the application runs in development mode. In this mode e.g. extra menu items related to development may be available.
   */
  public static boolean developmentMode = false;
  
  /**
   * Name of the directory where pictures and documents related to properties (products) are stored.
   */
  public static String eigendommenDocumentenMap = null;
  
  /**
   * Name of the file describing the financial units.
   */
  public static String financieleEenhedenFile = null;
  
  /**
   * Name of the file with all shares issued for a fund.
   */
  public static String fundSharesFile = null;
  
  /**
   * File with share mappings from Lynx to Finan.
   */
  public static String lynxShareIdToFinanShareNamesFile = null;
  
  /**
   * Name of the file containing information on mortgages.
   */
  public static String mortgagesFileName = null;
  
  /**
   * Name of the file with all option series.
   */
  public static String optionSeriesFile = null;
  
  /**
   * Default account for the 'Postbank'.
   */
  public static String postbankDefaultRekening = null;
  
  /**
   * Used in development mode to find e.g. the customPropertiesFile.
   */
  public static String projectPath = null;
  
  /**
   * The Property Descriptors EMF resource.
   */
  public static EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource = null;
  
  /**
   * Name of the file with all dividends per share.
   */
  public static String shareDividendsFile = null;
  
  /**
   * Name of the file with all tax rates per share.
   */
  public static String shareTaxRatesFile = null;
  
  /**
   * Short description of this application.
   */
  public static String shortProductInfo = "Financial program";
  
  /**
   * File with all financial transactions.
   */
  public static File transactionsFile = null;
  
  /**
   * Name of the file with all financial transactions.
   */
  public static String transactionsFileName = null;
  
  /*
   * If transactions are handled and there's any change in the transactions,
   * the transactions are rewind. A manual 'handle transactions' is needed to handle them again.
   */
  public static boolean transactionsHandled = false;
  
  /**
   * Current software version of this application.
   */
  public static String version = null;
   
  

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
}
