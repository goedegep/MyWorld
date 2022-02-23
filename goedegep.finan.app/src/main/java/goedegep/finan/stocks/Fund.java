//Title:        Fund
//Version:
//Copyright:    Copyright (c) 2002
//Author:       Peter Goedegebure
//Company:
//Description:  a fund issued by a company
package goedegep.finan.stocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Fund implements Comparator<Fund>, Comparable<Fund> {
  public static final String  fundPostbankAandelenfondsName           = "Postbank Aandelenfonds";
  public static final String  fundPostbankBeleggingsfondsName         = "Postbank Beleggingsfonds";
  public static final String  fundPostbankFinancieleWereldfondsName   = "Postbank Financiele Wereldfonds";
  public static final String  fundPostbankMultimediafondsName         = "Postbank Multimediafonds";
  public static final String  fundPostbankWereldmerkenfondsName       = "Postbank WereldMerkenfonds";

  // List of funds, the indexes are equal to the ID's.
  // This is guarantied by the way the list is created.
  private static Map<String, Fund>    funds = new HashMap<String, Fund>();
  private static List<FundListener>   fundsListeners = new ArrayList<FundListener>(10);

  String       name;
  Company      company;
  List<Share>  shares;

  public Fund(String fundName, Company company) {
    if (funds.get(fundName) != null) {
      throw new IllegalArgumentException("fundName \"" + fundName + "\" already exists.");
    }
    name = fundName;
    this.company = company;
    shares = new LinkedList<Share>();
    funds.put(fundName, this);

    notifyListenersOnFundsUpdated();
  }

  public static int getNumberOfFunds() {
    return funds.size();
  }
  
  public static Collection<Fund> getFunds() {
    List<Fund> fundsList = new ArrayList<Fund>(funds.values());
    if (fundsList.size() != 0) {
      Collections.sort(fundsList);
    }
    return fundsList;
  }

  public static Fund getFund(String name) {
    return funds.get(name);
  }
  
  public static void resetFunds() {
    funds = new HashMap<String, Fund>();
  }

  public String getName() {
    return name;
  }

  public Company getCompany() {
    return company;
  }

  public List<Share> getShares() {
    return shares;
  }

  public void setShares(LinkedList<Share> shares) {
    this.shares = shares;

    notifyListenersOnShareAdded(this);
  }

  public void addShare(Share share) {
    shares.add(share);

    notifyListenersOnShareAdded(this);
  }

  public Share getShare(String name) {
    Iterator<Share>  it = shares.iterator();

    while (it.hasNext()) {
      Share share = it.next();
      if (share.getName().compareTo(name) == 0) {
        return share;
      }
    }

    return null;
  }

//  public static ListIterator<Fund> listIterator() {
//    return funds.listIterator();
//  }

  public static void addFundListener(FundListener listener) {
    fundsListeners.add(listener);
  }

  public static void removeFundListener(FundListener listener) {
    fundsListeners.remove(listener);
  }

  private static void notifyListenersOnFundsUpdated() {
    Iterator<FundListener> it = fundsListeners.iterator();
    while (it.hasNext()) {
      FundListener listener = it.next();
      listener.FundsUpdated();
    }
  }

  private static void notifyListenersOnShareAdded(Fund fund) {
    Iterator<FundListener> it = fundsListeners.iterator();
    while (it.hasNext()) {
      FundListener listener = it.next();
      listener.FundShareAdded(fund);
    }
  }

  public int compare(Fund fund1, Fund fund2) {
    return fund1.getName().compareTo(fund2.getName());
  }

  public int compareTo(Fund fund) {
    return this.getName().compareTo(fund.getName());
  }
}
