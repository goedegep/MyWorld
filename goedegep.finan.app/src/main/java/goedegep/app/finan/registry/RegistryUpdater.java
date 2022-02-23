package goedegep.app.finan.registry;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RegistryUpdater {
  public static Map<String, String> propertyToRegistryEntryMap = new HashMap<String, String>();
  
  static {
    // Map properties to field names in the Registry.
    // Keep this list alphabetically sorted, just for easy maintenance.
    propertyToRegistryEntryMap.put("aanstellingFile", "aanstellingFile");
    propertyToRegistryEntryMap.put("aanstellingFileOrig", "aanstellingFileOrig");
    propertyToRegistryEntryMap.put("claimEmissionsFile", "claimEmissionsFile");
    propertyToRegistryEntryMap.put("claimEmissionsFileOrig", "claimEmissionsFileOrig");
    propertyToRegistryEntryMap.put("companiesFile", "companiesFile");
    propertyToRegistryEntryMap.put("companiesFileOrig", "companiesFileOrig");
    propertyToRegistryEntryMap.put("companyFundsFile", "companyFundsFile");
    propertyToRegistryEntryMap.put("companyFundsFileOrig", "companyFundsFileOrig");
    propertyToRegistryEntryMap.put("configurationFile", "configurationFile");
    propertyToRegistryEntryMap.put("configurationFileOrig", "configurationFileNameOrig");
    propertyToRegistryEntryMap.put("dataDirectory", "dataDirectory");
    propertyToRegistryEntryMap.put("defaultBank", "defaultBank");
    propertyToRegistryEntryMap.put("eigendommen.documentenMap", "eigendommenDocumentenMap");
    propertyToRegistryEntryMap.put("financieleEenhedenFile", "financieleEenhedenFile");
    propertyToRegistryEntryMap.put("financieleEenhedenFileOrig", "financieleEenhedenFileOrig");
    propertyToRegistryEntryMap.put("fundSharesFile", "fundSharesFile");
    propertyToRegistryEntryMap.put("fundSharesFileOrig", "fundSharesFileOrig");
    propertyToRegistryEntryMap.put("hypothekenFile", "hypothekenFile");
    propertyToRegistryEntryMap.put("hypothekenFileOrig", "hypothekenFileOrig");
    propertyToRegistryEntryMap.put("hypotheken.defaultHypotheek", "defaultHypotheekId");
    propertyToRegistryEntryMap.put("notasFile", "notasFileName");
    propertyToRegistryEntryMap.put("notasFileOrig", "notasFileNameOrig");
    propertyToRegistryEntryMap.put("optionSeriesFile", "optionSeriesFile");
    propertyToRegistryEntryMap.put("optionSeriesFileOrig", "optionSeriesFileOrig");
    propertyToRegistryEntryMap.put("postbank.defaultRekening", "postbankDefaultRekening");
    propertyToRegistryEntryMap.put("rolodexFile", "rolodexFileName");
    propertyToRegistryEntryMap.put("rolodexFileOrig", "rolodexFileNameOrig");
    propertyToRegistryEntryMap.put("shareDividendsFile", "shareDividendsFile");
    propertyToRegistryEntryMap.put("shareDividendsFileOrig", "shareDividendsFileOrig");
    propertyToRegistryEntryMap.put("shareTaxRatesFile", "shareTaxRatesFile");
    propertyToRegistryEntryMap.put("shareTaxRatesFileOrig", "shareTaxRatesFileOrig");
    propertyToRegistryEntryMap.put("transactionsFile", "transactionsFileName");
    propertyToRegistryEntryMap.put("transactionsFileOrig", "transactionsFileNameOrig");
  }
  
  public static void update(Properties properties) {
    for (Field field: FinanRegistry.class.getFields()) {
      System.out.println("Field: " + field.getName());
    }
    for (Object key : properties.keySet()) {
      String registryEntry = propertyToRegistryEntryMap.get(key);
      if (registryEntry != null) {
        try {
          Field field = FinanRegistry.class.getField(registryEntry);
          field.set(null, properties.get(key));
        } catch (SecurityException e) {
          // This should never happen, print stack and exit.
          e.printStackTrace();
          System.exit(-1);
        } catch (NoSuchFieldException e) {
          // This should never happen, print error and exit.
          System.err.println("Registry field error: property = " +
              properties.get(key) + ", field = " + registryEntry);
          System.exit(-1);
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
    }
  }
}
