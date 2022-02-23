package goedegep.finan.stocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a company. A company can have Funds.
 * 
 * @author Peter
 *
 */
public class Company {
  private static ArrayList<CompaniesListener> companiesListeners = new ArrayList<CompaniesListener>(10);  // Listeners to changes in the companies.

  private String      name;    // company name
  private List<Fund>  funds;   // company funds

  public Company(String name) {
    this.name = name;
    funds = new LinkedList<Fund>();
  }
  
  public String getName() {
    return name;
  }

  public List<Fund> getFunds() {
    return funds;
  }

  public void setFunds(LinkedList<Fund> funds) {
    this.funds = funds;
    notifyListenersOnFundAdded(this);
  }

  public void addFund(Fund fund) {
    funds.add(fund);
    notifyListenersOnFundAdded(this);
  }

  public Fund getFund(String name) {
    Iterator<Fund>  it = funds.iterator();

    while (it.hasNext()) {
      Fund fund = it.next();
      if (fund.getName().compareTo(name) == 0) {
        return fund;
      }
    }

    return null;
  }

  public static void addCompaniesListener(CompaniesListener listener) {
    companiesListeners.add(listener);
  }

  public static void removeCompaniesListener(CompaniesListener listener) {
    companiesListeners.remove(listener);
  }

  private static void notifyListenersOnFundAdded(Company company) {
    for (CompaniesListener listener: companiesListeners) {
      listener.CompanyFundAdded(company);
    }
  }
}
