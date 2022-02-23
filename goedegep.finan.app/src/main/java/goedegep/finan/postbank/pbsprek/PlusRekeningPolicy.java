package goedegep.finan.postbank.pbsprek;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.util.money.PgCurrency;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.Month;

/**
 * De 'policies' in deze class zijn allemaal alleen gebaseerd
 * op mijn transacties. Ofwel, de echte ingangsdata en algoritmen
 * kunnen geheel anders zijn.
 */
public class PlusRekeningPolicy extends PbSpRekPolicy {
  DateVrijOpneembaarPolicyCombo test;
    
  // De policies moeten in deze lijst staan van nieuw naar oud.
  DateVrijOpneembaarPolicyCombo[] vrijOpneembaarPolicies = {
      new DateVrijOpneembaarPolicyCombo(LocalDate.of(1982, Month.DECEMBER, 1),
          "getVrijOpneembaarBedragVoor19821101", "getVrijOpneembaarBedragNa19821101")
  };
  
  @Override
  public PgCurrency getOpnameKosten(PbSpRekOverschrijving transaction) {
    return new PgCurrency(transaction.getTransactionAmount().getCurrency(), 0L);  // TODO implement
  }
  
  @Override
  public PgCurrency getVrijOpneembaarBedragVoor(PbSpRekTransaction transaction) {
    for (DateVrijOpneembaarPolicyCombo combo: vrijOpneembaarPolicies) {
      if (!transaction.getExecutionDate().isBefore(combo.date)) {
        try {
          Method vrijOpneembaarBedragMethod = this.getClass().getMethod(combo.policyVoorMethodName, PbSpRekTransaction.class);
          return (PgCurrency) vrijOpneembaarBedragMethod.invoke(this, transaction);
        } catch (SecurityException e) {  // should never happen.
          e.printStackTrace();
        } catch (NoSuchMethodException e) {  // should never happen.
          e.printStackTrace();
        } catch (IllegalArgumentException e) {  // should never happen.
          e.printStackTrace();
        } catch (IllegalAccessException e) {  // should never happen.
          e.printStackTrace();
        } catch (InvocationTargetException e) {  // should never happen.
          e.printStackTrace();
        }
      }
    }
    
    return getVrijOpneembaarBedragVoorDefault(transaction);
  }
  
  @Override
  public PgCurrency getVrijOpneembaarBedragNa(PbSpRekTransaction transaction) {
    for (DateVrijOpneembaarPolicyCombo combo: vrijOpneembaarPolicies) {
      if (!transaction.getExecutionDate().isBefore(combo.date)) {      
        try {
          Method vrijOpneembaarBedragMethod = this.getClass().getMethod(combo.policyNaMethodName, PbSpRekTransaction.class);
          return (PgCurrency) vrijOpneembaarBedragMethod.invoke(this, transaction);
        } catch (SecurityException e) {  // should never happen.
          e.printStackTrace();
        } catch (NoSuchMethodException e) {  // should never happen.
          e.printStackTrace();
        } catch (IllegalArgumentException e) {  // should never happen.
          e.printStackTrace();
        } catch (IllegalAccessException e) {  // should never happen.
          e.printStackTrace();
        } catch (InvocationTargetException e) {  // should never happen.
          e.printStackTrace();
        }
      }
    }
    
    return getVrijOpneembaarBedragNaDefault(transaction);
  }
  
  private PgCurrency getVrijOpneembaarBedragNaDefault(PbSpRekTransaction transaction) {
    return getVrijOpneembaarBedragVoorDefault(transaction);
  }

  private PgCurrency getVrijOpneembaarBedragVoorDefault(PbSpRekTransaction transaction) {
    // start 1 jaar terug, rente komt bij vrij opneembaar,
    // opname (behalve i.g.v. zonder kosten) gaat er af.
    LocalDate currentDate = transaction.getExecutionDate();
    LocalDate jaarTerugDatum = currentDate.minusYears(1);
    
    PgAccount account = transaction.getAccount();
    PgCurrency vrijOpneembaarBedrag = null;
    
    boolean searchingStart = true;
    for (PgTransaction pgTransaction: account.getTransactions()) {
      PbSpRekTransaction checkTransaction = (PbSpRekTransaction) pgTransaction;
      
      if (checkTransaction.equals(transaction)) {
        // reached our transaction, so return the result.
        if (vrijOpneembaarBedrag == null) {
          // Er zijn kennelijk geen eerdere transacties die het  vrij opneembare
          // bedrag beinvloeden (zoals ontvangen rente).
          vrijOpneembaarBedrag = new PgCurrency(PgCurrency.GUILDER, 0L);
        }
        return vrijOpneembaarBedrag;
      }
      
      if (searchingStart) {
        if (!checkTransaction.getExecutionDate().isBefore(jaarTerugDatum)) {
          searchingStart = false;
        }
      }
      
      if (!searchingStart) {
        switch (checkTransaction.getTransactionType()) {
//        case PbSpRekTransaction.NAAR_GIRO:
        case PbSpRekTransaction.TT_OVERSCHRIJVING:
          PbSpRekOverschrijving overschrijving = (PbSpRekOverschrijving) checkTransaction;
          if (!overschrijving.isBijschrijving()  &&
              !overschrijving.isZonderKosten()) {
            if (vrijOpneembaarBedrag != null  &&
                !vrijOpneembaarBedrag.isLessThan(overschrijving.getTransactionAmount())) {
              vrijOpneembaarBedrag = vrijOpneembaarBedrag.subtract(overschrijving.getTransactionAmount());
            }
          }
          break;
          
        case PbSpRekTransaction.TT_RENTE:
          if (vrijOpneembaarBedrag == null) {
            vrijOpneembaarBedrag = checkTransaction.getTransactionAmount();
          } else {
            vrijOpneembaarBedrag = vrijOpneembaarBedrag.add(checkTransaction.getTransactionAmount());
          }
          break;
        }
      }
    }
    
    throw new RuntimeException("Unexpected location reached.");
  }
  
  public PgCurrency getVrijOpneembaarBedragVoor19821101(PbSpRekTransaction transaction) {
    System.out.println("in getVrijOpneembaarBedrag19821101");
    
    // start 1 maand terug, met f 10.000,- vrij opneembaar per maand,
    // opname (behalve i.g.v. zonder kosten) gaat er af.
    LocalDate currentDate = transaction.getExecutionDate();
    LocalDate maandTerugDatum = currentDate.minusMonths(-1);
    
    PgAccount account = transaction.getAccount();
    PgCurrency vorigTegoed = new PgCurrency(PgCurrency.GUILDER, 1000000L);
    PgCurrency vrijOpneembaarBedrag = new PgCurrency(PgCurrency.GUILDER, 1000000L);
    
    boolean searchingStart = true;
    for (PgTransaction pgTransaction: account.getTransactions()) {
      PbSpRekTransaction checkTransaction = (PbSpRekTransaction) pgTransaction;
      
      if (checkTransaction.equals(transaction)) {
        // reached our transaction, so return the result.
        if (vrijOpneembaarBedrag == null) {
          // Er zijn kennelijk geen eerdere transacties die het  vrij opneembare
          // bedrag beinvloeden (zoals ontvangen rente).
          vrijOpneembaarBedrag = createZeroMoney(transaction);
        }
        
        // Het vrij opneembare bedrag is nooit hoger dan het vorige tegoed.
        if (vrijOpneembaarBedrag.isGreaterThan(vorigTegoed)) {
          vrijOpneembaarBedrag = vorigTegoed;
        }
        return vrijOpneembaarBedrag;
      }
      
      if (searchingStart) {
        if (!checkTransaction.getExecutionDate().isBefore(maandTerugDatum)) {
          searchingStart = false;
        }
      }
      
      if (!searchingStart) {
        switch (checkTransaction.getTransactionType()) {
//        case PbSpRekTransaction.NAAR_GIRO:
        case PbSpRekTransaction.TT_OVERSCHRIJVING:
          PbSpRekOverschrijving overschrijving = (PbSpRekOverschrijving) checkTransaction;
          if (!overschrijving.isBijschrijving()  &&
              !overschrijving.isZonderKosten()) {
            if (vrijOpneembaarBedrag != null  &&
                !vrijOpneembaarBedrag.isLessThan(overschrijving.getTransactionAmount())) {
              vrijOpneembaarBedrag = vrijOpneembaarBedrag.subtract(overschrijving.getTransactionAmount());
            }
          }
          break;
        }
      }
      
      vorigTegoed = checkTransaction.getNieuwTegoed();
    }
    
    throw new RuntimeException("Unexpected location reached.");
  }
  
  
  public PgCurrency getVrijOpneembaarBedragNa19821101(PbSpRekTransaction transaction) {
    System.out.println("in getVrijOpneembaarBedrag19821101");
    
    // start 1 maand terug, met f 10.000,- vrij opneembaar per maand,
    // opname (behalve i.g.v. zonder kosten) gaat er af.
    LocalDate currentDate = transaction.getExecutionDate();
    LocalDate maandTerugDatum = currentDate.minusMonths(-1);
    
    PgAccount account = transaction.getAccount();
    PgCurrency vrijOpneembaarBedrag = new PgCurrency(PgCurrency.GUILDER, 1000000L);
    
    boolean searchingStart = true;
    for (PgTransaction pgTransaction: account.getTransactions()) {
      PbSpRekTransaction checkTransaction = (PbSpRekTransaction) pgTransaction;
      
      if (checkTransaction.equals(transaction)) {
        // reached our transaction, so return the result.
        if (vrijOpneembaarBedrag == null) {
          // Er zijn kennelijk geen eerdere transacties die het  vrij opneembare
          // bedrag beinvloeden (zoals ontvangen rente).
          vrijOpneembaarBedrag = createZeroMoney(transaction);
        }
        
        // Het vrij opneembare bedrag is nooit hoger dan het vorige tegoed.
        if (vrijOpneembaarBedrag.isGreaterThan(transaction.getNieuwTegoed())) {
          vrijOpneembaarBedrag = transaction.getNieuwTegoed();
        }
        return vrijOpneembaarBedrag;
      }
      
      if (searchingStart) {
        if (!checkTransaction.getExecutionDate().isBefore(maandTerugDatum)) {
          searchingStart = false;
        }
      }
      
      if (!searchingStart) {
        switch (checkTransaction.getTransactionType()) {
//        case PbSpRekTransaction.NAAR_GIRO:
        case PbSpRekTransaction.TT_OVERSCHRIJVING:
          PbSpRekOverschrijving overschrijving = (PbSpRekOverschrijving) checkTransaction;
          if (!overschrijving.isBijschrijving()  &&
              !overschrijving.isZonderKosten()) {
            if (vrijOpneembaarBedrag != null  &&
                !vrijOpneembaarBedrag.isLessThan(overschrijving.getTransactionAmount())) {
              vrijOpneembaarBedrag = vrijOpneembaarBedrag.subtract(overschrijving.getTransactionAmount());
            }
          }
          break;
        }
      }
    }
    
    throw new RuntimeException("Unexpected location reached.");
  }
  
  private PgCurrency createZeroMoney(PbSpRekTransaction transaction) {
    int currency;
    if (PbSpRekTransaction.transactionInEuros(transaction.getExecutionDate())) {
      currency = PgCurrency.EURO;
    } else {
      currency = PgCurrency.GUILDER;
    }
    return new PgCurrency(currency, 0L);
  }
  
  private class DateVrijOpneembaarPolicyCombo {
    LocalDate date;
    String policyVoorMethodName;
    String policyNaMethodName;
    
    public DateVrijOpneembaarPolicyCombo(LocalDate date,
        String policyVoorMethodName, String policyNaMethodName) {
      this.date = date;
      this.policyVoorMethodName = policyVoorMethodName;
      this.policyNaMethodName = policyNaMethodName;
    }
  }

}
