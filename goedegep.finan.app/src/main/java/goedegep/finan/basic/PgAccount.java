package goedegep.finan.basic;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import goedegep.util.money.PgCurrency;


/**
 * Basis class voor een (bank)rekening.
 * Een rekening heeft een naam, kan een saldo en/of een waarde hebben.<br>
 * Betalingsrekeningen en spaarrekeningen hebben een saldo, maar geen waarde. Rekeningen van
 * beleggingsfondsen hebben een waarde maar geen saldo. Een effectenrekening heeft zowel een
 * saldo als een waarde.<br>
 * De waarde is voorlopig alleen een geschatte waarde. Een echte waarde is bijna
 * nooit te bepalen en altijd maar heel kort geldig (meestal is de waarde van
 * een dagkoers afhankelijk, die momenteel niet voorhanden is).
 * Op een rekening worden transacties (PgTransaction) uitgevoerd.
 * Daarom heeft een rekening ook een lijst van transacties.
 */
public abstract class PgAccount implements DataDump {
  /**
   * Naam van de rekening.
   */
  private String        name = null;

  /**
   * Geeft aan of deze rekening een saldo heeft (true) of niet (false).
   */
  private boolean       hasBalance;

  /**
   * Het saldo van de rekening (indien van toepassing, zie hasBalance).
   */
  private PgCurrency    balance = null;       // current balance when hasBalance is true
  
  /**
   * Netto storting. Wat naar de rekening overgemaakt is, minus hetgeen opgenomen is.
   */
  PgCurrency            nettoStorting = null;

  /**
   * Geeft aan of deze rekening een waarde (zoals waarde van effecten) heeft (true) of niet (false).
   */
  private boolean       hasValue;

  /**
   * Lijst met transacties.
   */
  private LinkedList<PgTransaction>    transactions = new LinkedList<>();

  /**
   * Listeners to changes on this account.
   */
  private ArrayList<PgAccountListener>  accountListeners = new ArrayList<>(10);

  /**
   * Maakt een rekening aan, zonder een initieel saldo en een naam te zetten.
   *
   * @param hasBalance   Geeft aan of deze rekening een saldo heeft (true) of niet (false).
   * @param hasValue   Geeft aan of deze rekening een waarde heeft (true) of niet (false).
   */
  public PgAccount(boolean hasBalance, boolean hasValue) {
    this(hasBalance, hasValue, null);
  }

  /**
   * Maakt een rekening aan, zonder een initieel saldo te zetten, maar wel een naam.
   *
   * @param hasBalance   Geeft aan of deze rekening een saldo heeft (true) of niet (false).
   * @param hasValue   Geeft aan of deze rekening een waarde heeft (true) of niet (false).
   * @param name Naam van de rekening.
   */
  public PgAccount(boolean hasBalance, boolean hasValue, String name) {
    this.hasBalance = hasBalance;
    this.hasValue = hasValue;
    this.name = name;
    clear();
  }

  /**
   * Maakt een rekening aan, met een initieel saldo.
   *
   * @param hasBalance   Geeft aan of deze rekening een saldo heeft (true) of niet (false).
   * @param currency     Munteenheid van het saldo.
   * @param amount       Saldo.
   */
  public PgAccount(boolean hasBalance, int currency, long amount, boolean hasValue) {
    this(hasBalance, hasValue, null);
    if (hasBalance) {
      balance = new PgCurrency(currency, amount);
    }
    nettoStorting = new PgCurrency(currency, 0L);
  }

  /**
   * Geeft de rekening een (nieuwe) naam.
   *
   * @param name    Naam van de rekening
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Verkrijg de naam van de rekening.
   *
   * @return De naam van de rekening.
   */
  public String getName() {
    return name;
  }

  public void certifyCurrency(int currency) {
    if (hasBalance) {
      balance = balance.certifyCurrency(currency);
      nettoStorting = nettoStorting.certifyCurrency(currency);
    }
  }
  
  
  /*
   * Saldo
   */
  
  /**
   * Vraag op of deze rekening een saldo heeft.
   */
  public boolean hasBalance() {
    return hasBalance;
  }

  /**
   * Verkrijg het saldo van de rekening.
   *
   * @return
   *     Het saldo van de rekening. Indien de rekening geen saldo heeft, of als het saldo
   *     nog niet gezet is, wordt null geretourneerd.
   */
  public PgCurrency getBalance() {
    return balance;
  }

  /**
   * Zet het saldo.<br>
   * Het zetten van het saldo voor een rekening die geen saldo heeft, leidt tot een
   * IllegalArgumentException.
   * @param balance
   */
  public void setBalance(PgCurrency balance) {
    if (hasBalance) {
      this.balance = balance;
    } else {
      throw new IllegalArgumentException("Poging om het saldo te zetten van een rekening die geen saldo heeft");
    }
  }

  /**
   * Verhoog het saldo.<br>
   * Het verhogen van het saldo voor een rekening die geen saldo heeft, leidt tot een
   * IllegalArgumentException.
   * @param balance
   */
  public void increaseBalance(PgCurrency amount) {
    if (hasBalance) {
      if (balance == null) {
        balance = amount;
      } else {
        balance = balance.certifyCurrency(amount.getCurrency());
        balance = balance.add(amount);
      }
    } else {
      throw new IllegalArgumentException("Poging om het saldo te verhogen van een rekening die geen saldo heeft");
    }
  }

  /**
   * Verlaag het saldo.<br>
   * Het verlagen van het saldo voor een rekening die geen saldo heeft, leidt tot een
   * IllegalArgumentException.
   * @param balance
   */
  public void decreaseBalance(PgCurrency amount) {
    if (hasBalance) {
      if (balance == null) {
        throw new IllegalArgumentException("Poging om het saldo te verlagen, terwijl er geen saldo is");
      } else {
        balance = balance.subtract(amount);
      }
    } else {
      throw new IllegalArgumentException("Poging om het saldo te verlagen van een rekening die geen saldo heeft");
    }
  }
  
  /*
   * Netto storting
   */

  public PgCurrency getNettoStorting() {
    return nettoStorting;
  }

  public void increaseNettoStorting(PgCurrency amount) {
    if (nettoStorting == null) {
      nettoStorting = amount;
    } else {
      nettoStorting = nettoStorting.certifyCurrency(amount.getCurrency());
      nettoStorting = nettoStorting.add(amount);
    }
  }

  public void decreaseNettoStorting(PgCurrency amount) {
    if (nettoStorting == null) {
      throw new IllegalArgumentException("Poging om de netto storting te verlagen, terwijl er geen netto storting is");
    } else {
      nettoStorting = nettoStorting.subtract(amount);
    }
  }


  /*
   * Waarde
   */
  
  /**
   * Vraag op of deze rekening een waarde heeft.
   */
  public boolean hasValue() {
    return hasValue;
  }


  /**
   * Verkrijg de geschatte waarde. Een account dat een waarde heeft moet
   * deze method overschrijven.
   *
   * @return
   *    De geschatte waarde, of null indien de rekening geen 'waarde' heeft.
   */
  public PgCurrency getEstimatedValue() {
    return null;
  }

  public static void init() {
  }
  
  public void clear() {
    nettoStorting = null;
    balance = null;
  }

  /*
   * Transactions
   */

  /**
   * Voeg een transactie toe, aan het eind van de lijst met transacties.
   *
   * @param transaction   De toe te voegen transactie.
   */
  public void addTransaction(PgTransaction transaction) {
    transactions.addLast(transaction);
    notifyListenersOnTransactionAdded(transaction);
  }

  /**
   * Voeg een transactie toe, aan het eind van de lijst met transacties.
   *
   * @param transaction   De toe te voegen transactie.
   */
  public void addFirstTransaction(PgTransaction transaction) {
    transactions.addFirst(transaction);
    notifyListenersOnTransactionAdded(transaction);
  }
  
  /**
   * Voeg een transactie, voor of na een specifieke transactie, toe.
   *
   * @param transaction   De toe te voegen transactie.
   * @param insertLocation  Transactie waar de nieuwe transactie voor of achter ingevoegd moet worden.
   * @param before    Geeft aan of de transactie voor of na de insertLocation ingevoegd moet worden.
   */
  public void addTransaction(PgTransaction transaction, PgTransaction insertLocation, boolean before) {
    int insertIndex = transactions.indexOf(insertLocation);
    if (insertIndex == -1) {
      throw(new IllegalArgumentException());
    }

    if (before) {
      transactions.add(insertIndex, transaction);
    } else {
      insertIndex++;
      if (insertIndex >= transactions.size()) {
        transactions.addLast(transaction);
      } else {
        transactions.add(insertIndex, transaction);
      }
    }

    notifyListenersOnTransactionAdded(transaction);
  }

  /**
   * Verkrijg een transactie voor een bepaalde index.
   *
   * @param index   Geeft aan welke transactie (de hoeveelste) gevraagd wordt.
   *                De eerste transactie heeft index 0.
   * @return
   *     De transactie voor de gespecificeerde index.
   *
   * @throws IndexOutOfBoundsException als de transactie voor de gevraagde index
   *         niet bestaat (<tt>index &lt; 0 || index &gt;= "aantal transacties"</tt>).
   */
  public PgTransaction getTransaction(int index) {
    return transactions.get(index - 1);
  }
  
  /**
   * Verkrijg de eerste transactie.
   * 
   * @return
   *     De eerste transactie, of null als de lijst leeg is.
   */
  public PgTransaction getFirstTransaction() {
    if (transactions.isEmpty()) {
      return null;
    } else {
      return transactions.get(0);
    }
  }
  
  /**
   * Verkrijg de laatste transactie.
   * 
   * @return
   *     De laatste transactie, of null als de lijst leeg is.
   */
  public PgTransaction getLastTransaction() {
    if (transactions.isEmpty()) {
      return null;
    } else {
      return transactions.get(transactions.size() - 1);
    }
  }
  
  /**
   * Verkrijg de hele transactie lijst.
   * 
   * @return
   *     De hele transactie lijst. De lijst bestaat altijd, maar kan leeg zijn.
   */
  public List<PgTransaction> getTransactions() {
    return transactions;
  }
  
  /**
   * Verkrijg de transacties voor een specifieke maand.
   * 
   * @parameter month The month for which the transactions are requested.
   * @return
   *     De hele transactie lijst. De lijst bestaat altijd, maar kan leeg zijn.
   */
  public List<PgTransaction> getTransactionsOfOneMonth(YearMonth month) {
    List<PgTransaction> transactionsInMonth = new ArrayList<>();
    int year = month.getYear();
    int monthInYear = month.getMonthValue() - 1;
    boolean beforeMonth = true;
    
    for (PgTransaction transaction: transactions) {
      LocalDate executionDate = transaction.getExecutionDate();
      if (executionDate != null) {
        if (beforeMonth) {
          // Check whether we are now in month
          if (executionDate != null) {
            if ((executionDate.getYear() == year)  &&
                (executionDate.getMonthValue() == monthInYear)) {
              beforeMonth = false;
            }
          }
        }

        // break out if we're after month
        if (((executionDate.getYear() == year)  &&
             (executionDate.getMonthValue() > monthInYear))  ||
            (executionDate.getYear() > year)) {
          break;
        }
      }


      if (!beforeMonth) {
        transactionsInMonth.add(transaction);
      }
    }
    
    return transactionsInMonth;
  }
  
  
  /**
   * Verkrijg het aantal transacties.
   *
   * @return  Het aantal transacties.
   */
  public int numberOfTransactions() {
    return transactions.size();
  }

  public void addAccountListener(PgAccountListener listener) {
    accountListeners.add(listener);
  }

  public void removeAccountListener(PgAccountListener listener) {
    accountListeners.remove(listener);
  }

  public abstract String toString(PgTransaction transaction);

  private void notifyListenersOnTransactionAdded(PgTransaction transaction) {
    Iterator<PgAccountListener> it = accountListeners.iterator();
    while (it.hasNext()) {
      PgAccountListener listener = it.next();
      listener.transactionAdded(transaction);
    }
  }
  
  // TODO make abstract
  public List<QuarterlyData> getQuarterlyData() {
    return null;
  }

  public PgTransaction getFirstTransactionExecutedOnOrAfterDate(LocalDate date) {
    for (PgTransaction transaction: transactions) {
      if (!transaction.getExecutionDate().isBefore(date)) {
        return transaction;
      }
    }
    
    return null;
  }
}
