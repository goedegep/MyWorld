//Title:        Financien
//Version:
//Copyright:    Copyright (c) 2001
//Author:       Peter Goedegebure
//Company:      Solvation
//Description:  StockBuySellCombo
//              Bevat een koop/verkoop combinatie


package goedegep.finan.stocks;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public class StockBuySellCombo {
  private static final PgCurrencyFormat  cf = new PgCurrencyFormat();
  private static final SimpleDateFormat  df =  new SimpleDateFormat("dd-MM-yyyy");
  
  private boolean     integerAmount; // Indicates whether amount is actually an int or a float.
  private float       amount;        // aantal aandelen
  private LocalDate   buyDate;       // datum van eerste aankoop (de verkochte aandelen kunnen van verschillende aankopen zijn).
  private PgCurrency  buyRate;       // aankoop koers
  private LocalDate   sellDate;      // datum van sluitings verkoop
  private PgCurrency  sellRate;      // verkoop koers
  private PgCurrency  profit;        // winst (of verlies) op de combinatie

  public StockBuySellCombo(float amount, LocalDate buyDate, PgCurrency buyRate, LocalDate sellDate, PgCurrency sellRate, PgCurrency profit) {
    integerAmount = false;
    this.amount  = amount;
    this.buyDate  = buyDate;
    this.buyRate  = buyRate;
    this.sellDate  = sellDate;
    this.sellRate = sellRate;
    this.profit  = profit;
  }

  public StockBuySellCombo(int amount, LocalDate buyDate, PgCurrency buyRate, LocalDate sellDate, PgCurrency sellRate, PgCurrency profit) {
    integerAmount = true;
    this.amount  = amount;
    this.buyDate  = buyDate;
    this.buyRate  = buyRate;
    this.sellDate  = sellDate;
    this.sellRate = sellRate;
    this.profit  = profit;
  }

  public boolean isIntegerAmount() {
    return integerAmount;
  }

  public void setIntegerAmount(boolean integerAmount) {
    this.integerAmount = integerAmount;
  }

  public float getAmount() {
    return amount;
  }

  public int getIntAmount() {
    return (int) Math.round(amount);
  }

  public LocalDate getBuyDate() {
    return buyDate;
  }

  public PgCurrency getBuyRate() {
    return buyRate;
  }

  public LocalDate getSellDate() {
    return sellDate;
  }

  public PgCurrency getSellRate() {
    return sellRate;
  }

  public PgCurrency getProfit() {
    return profit;
  }
  
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    if (integerAmount) {
      buf.append("Aantal (int): " + getIntAmount());
    } else {
      buf.append("Aantal (float): " + getAmount());     
    }
    buf.append(", Koopdatum: " + df.format(buyDate));
    buf.append(", Koopkoers: ");
    if (buyRate != null) {
      buf.append(cf.format(buyRate));
    } else {
      buf.append("-");
    }
    buf.append(", Verkoopdatum: " + df.format(sellDate));
    buf.append(", Verkoopkoers: " + cf.format(sellRate));
    buf.append(", Winst: " + cf.format(profit));

    return buf.toString();
  }
}