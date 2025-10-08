package goedegep.finan.stocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

import goedegep.app.finan.stocksapp.StocksResources;
import goedegep.appgen.swing.AppResources;

/**
 * This class maintains a collection of companies.
 * 
 * @author Peter
 *
 */
public class CompanyPool {
  private SortedMap<String, Company> companies = new TreeMap<String, Company>();   // Maps a company name to a company
  private ArrayList<CompanyPoolListener> companiesPoolListeners = new ArrayList<CompanyPoolListener>();  // Listeners to changes in the companies.
  
  private static AppResources appResources = null;
  
  public static AppResources getAppResources() {
    if (appResources == null) {
      appResources = new StocksResources();
    }
    
    return appResources;
  }

  public void addCompany(Company company) {
    companies.put(company.getName(), company);
    notifyCompaniesListeners();
  }
  
  public int getNumberOfCompanies() {
    return companies.size();
  }

  public Collection<Company> getCompanies() {
    return companies.values();
  }

  public Company getCompany(String name) {
    return companies.get(name);
  }

  public void addCompaniesListener(CompanyPoolListener listener) {
    companiesPoolListeners.add(listener);
  }

  public void removeCompaniesListener(CompanyPoolListener listener) {
    companiesPoolListeners.remove(listener);
  }

  private void notifyCompaniesListeners() {
    for (CompanyPoolListener listener: companiesPoolListeners) {
      listener.companiesUpdated();
    }
  }
}
