package goedegep.app.finan.lynxapp.transactioncheck;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.app.finan.lynxapp.LynxEffRekWindow;
import goedegep.appgen.swing.AppGenAbstractTableModel;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.basic.PgTransactionWithFixedDescription;
import goedegep.finan.effrek.EffRekAandelenTransactie;
import goedegep.finan.lynx.lynxeffrek.LynxEffRek;
import goedegep.finan.lynx.lynxeffrek.LynxMonthlyActivityStatement;
import goedegep.finan.lynx.lynxeffrek.LynxMonthlyActivityStatements;
import goedegep.finan.lynx.lynxeffrek.Ofx2Finan;
import goedegep.finan.lynx2finan.model.LynxToFinanShareIdList;
import goedegep.util.money.PgCurrency;

@SuppressWarnings("serial")
public class LynxTranactionDifferencesTableModel extends AppGenAbstractTableModel {
  private static final Logger     LOGGER = Logger.getLogger(LynxTranactionDifferencesTableModel.class.getName());
  private static final DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");

  private static final String[] columnNames = {
      "Rente Datum",
      "Transactie",
      "Uitv. Datum",
      "Opmerkingen",
      "Verschillen"
  };
  
  private static PgTransactionWithFixedDescription transaction = new PgTransactionWithFixedDescription("long, long description text for this transaction, yes, even longer than long and moren, yes, even longer than long and more");
  private static final Object[] longValues = {
      new LynxTransactionCheckResultInfo(transaction, transaction, 0, 0, LynxTransactionCheckResultInfoColumn.RENTE_DATUM, LynxTransactionCheckResultInfoResultType.FOUND_MATCHING_PARTIAL_EXECUTIONS),
      new LynxTransactionCheckResultInfo(transaction, transaction, 0, 0, LynxTransactionCheckResultInfoColumn.TRANSACTIE, LynxTransactionCheckResultInfoResultType.FOUND_MATCHING_PARTIAL_EXECUTIONS),
      new LynxTransactionCheckResultInfo(transaction, transaction, 0, 0, LynxTransactionCheckResultInfoColumn.UITV_DATUM, LynxTransactionCheckResultInfoResultType.FOUND_MATCHING_PARTIAL_EXECUTIONS),
      new LynxTransactionCheckResultInfo(transaction, transaction, 0, 0, LynxTransactionCheckResultInfoColumn.OPMERKINGEN, LynxTransactionCheckResultInfoResultType.FOUND_MATCHING_PARTIAL_EXECUTIONS),
      new LynxTransactionCheckResultInfo(transaction, transaction, 0, 0, LynxTransactionCheckResultInfoColumn.CHECK_RESULT, LynxTransactionCheckResultInfoResultType.FOUND_MATCHING_PARTIAL_EXECUTIONS),
  };
  
  private List<LynxTransactionCheckResultInfo[]> data = new ArrayList<LynxTransactionCheckResultInfo[]>();
  private LynxEffRek                          effectenRekening;
  private LynxMonthlyActivityStatements       lynxMonthlyActivityStatements;
  private Ofx2Finan                           ofx2Finan;
    
  public LynxTranactionDifferencesTableModel(LynxEffRek effectenRekening, LynxToFinanShareIdList lynxToFinanShareIdList, LynxMonthlyActivityStatements lynxMonthlyActivityStatements) {
    super(columnNames, longValues);
    this.effectenRekening = effectenRekening;
    this.lynxMonthlyActivityStatements = lynxMonthlyActivityStatements;
    ofx2Finan = new Ofx2Finan(lynxToFinanShareIdList.getEntries());
  }

  public int getRowCount() {
    if (data == null) {
      return 0;
    } else {
      return data.size();
    }
  }

  public Object getValueAt(int row, int col) {
    return data.get(row)[col];
  }
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Class getColumnClass(int col) {
    return LynxTransactionCheckResultInfo.class;
  }

  public void setMonth(YearMonth month) {
    LOGGER.severe("=>");
    data.clear();
    if (month != null) {
      // read Lynx Activity statement for the month, and create a list of FinanTransactions for all Lynx Transactions
      LynxMonthlyActivityStatement lynxMonthlyActivityStatement = lynxMonthlyActivityStatements.getMonthlyActivityStatement(month);
      List<PgTransaction> transactionsFromActivityStatement = ofx2Finan.getFinanTransactionsFromActivityStatement(lynxMonthlyActivityStatement, null);
      LOGGER.severe("Number of transactions in activity statement: " + transactionsFromActivityStatement.size());
      
      // Make a shallow copy of the list of transaction derived from the Activity statements.
      List<PgTransaction> transactionsFromActivityStatementsCopy = new ArrayList<>();
      for (PgTransaction pgTransaction: transactionsFromActivityStatement) {
        LOGGER.fine("Copying: " + DF.format(pgTransaction.getExecutionDate()) + " " + pgTransaction.getDescription());
        transactionsFromActivityStatementsCopy.add(pgTransaction);
      }

      int entryNr = 0;
      int matches = 0;
      int partialTransactionMatches = 0;
      int effRekOnly = 0;
      int actStatementOnly = 0;
      PgCurrency koersMargin = new PgCurrency(PgCurrency.EURO, 9, 1000);
      
      // For each transaction on the LynxEffRek:
      for (PgTransaction effRekTransaction: effectenRekening.getTransactionsOfOneMonth(month)) {
        LOGGER.fine("Checking: " + ((effRekTransaction.getExecutionDate() != null) ? DF.format(effRekTransaction.getExecutionDate()) : "--") + " " + effRekTransaction.getDescription());
        // First try to find an exact match.
        PgTransaction activityStatementTransaction = LynxEffRekWindow.findTransactionInList(effRekTransaction, transactionsFromActivityStatementsCopy);
        if (activityStatementTransaction != null) {   // exact match found
          transactionsFromActivityStatementsCopy.remove(activityStatementTransaction);
          addTransactionRows(entryNr++, effRekTransaction, activityStatementTransaction, LynxTransactionCheckResultInfoResultType.FOUND_IN_BOTH);
          matches++;
        } else {
          // No exact match found. Try to find partial transactions, which together match this transaction.
          List<PgTransaction> activityStatementPartialTransactions = LynxEffRekWindow.findPartialTransactionsInList(effRekTransaction, transactionsFromActivityStatementsCopy);
          if (activityStatementPartialTransactions != null) {
            for (PgTransaction partialTransaction: activityStatementPartialTransactions) {
              transactionsFromActivityStatementsCopy.remove(partialTransaction);
              partialTransactionMatches++;
            }
            addTransactionRows(entryNr++, effRekTransaction, activityStatementPartialTransactions, LynxTransactionCheckResultInfoResultType.FOUND_MATCHING_PARTIAL_EXECUTIONS);
            matches++;
          } else {
            // Also no matching partial transactions found, try to find a match with a small difference in the rate.
            activityStatementTransaction = findSimilarTransactionInList(effRekTransaction, transactionsFromActivityStatementsCopy, koersMargin);
            if (activityStatementTransaction != null) {  // Similar transaction found
              transactionsFromActivityStatementsCopy.remove(activityStatementTransaction);
              LOGGER.severe("Similar transaction found");
              LOGGER.severe(((effRekTransaction.getExecutionDate() != null) ? DF.format(effRekTransaction.getExecutionDate()) : "--") + " " + effRekTransaction.getDescription());
              addTransactionRows(entryNr++, effRekTransaction, activityStatementTransaction, LynxTransactionCheckResultInfoResultType.FOUND_SIMILAR);
            } else {
              LOGGER.severe("Transaction on LynxEffectenRekening, but not in activity statements");
              LOGGER.severe(((effRekTransaction.getExecutionDate() != null) ? DF.format(effRekTransaction.getExecutionDate()) : "--") + " " + effRekTransaction.getDescription());
              addTransactionRows(entryNr++, effRekTransaction, (PgTransaction) null, LynxTransactionCheckResultInfoResultType.ONLY_IN_FINAN);
              effRekOnly++;
            }
          }
        }
      }

      LOGGER.severe("Transactions in activity statements, but not on LynxEffectenRekening:");
      for (PgTransaction activityStatementTransaction: transactionsFromActivityStatementsCopy) {
        LOGGER.severe(((activityStatementTransaction.getExecutionDate() != null) ? DF.format(activityStatementTransaction.getExecutionDate()) : "--") + " " + activityStatementTransaction.getDescription());
        actStatementOnly++;
        addTransactionRows(entryNr++, null, activityStatementTransaction, LynxTransactionCheckResultInfoResultType.ONLY_IN_ACTIVITY_STATEMENT);
      }

      LOGGER.severe("matches=" + matches + ", partialTransactionMatches=" + partialTransactionMatches + ", effRekOnly=" + effRekOnly + ", actStatementOnly=" + actStatementOnly);

      // - if it is not in the list from the activity statements, report an error
      // - if it is in the list from the activity statements, remove it from this list
      //   This is done because there may be 2 identical transactions on the same day (I don't store the transaction time)
      // Report all remaining transactions in the list from the activity statements as errors.
    }

    fireTableDataChanged();

    LOGGER.severe("<=");
  }
  
  private void addTransactionRows(int entryNr, PgTransaction finanTransaction, PgTransaction activityStatementTransaction, LynxTransactionCheckResultInfoResultType resultType) {
    List<PgTransaction> activityStatementTransactions = new ArrayList<>();
    activityStatementTransactions.add(activityStatementTransaction);
    addTransactionRows(entryNr, finanTransaction, activityStatementTransactions, resultType);
  }
  
  private void addTransactionRows(int entryNr, PgTransaction finanTransaction, List<PgTransaction> activityStatementTransactions, LynxTransactionCheckResultInfoResultType resultType) {
    LynxTransactionCheckResultInfo[] row;
    
    // First row is the Finan transaction.
    row = new LynxTransactionCheckResultInfo[5];
    row[0] = new LynxTransactionCheckResultInfo(finanTransaction, activityStatementTransactions, entryNr, 0, LynxTransactionCheckResultInfoColumn.RENTE_DATUM, resultType);
    row[1] = new LynxTransactionCheckResultInfo(finanTransaction, activityStatementTransactions, entryNr, 0, LynxTransactionCheckResultInfoColumn.TRANSACTIE, resultType);
    row[2] = new LynxTransactionCheckResultInfo(finanTransaction, activityStatementTransactions, entryNr, 0, LynxTransactionCheckResultInfoColumn.UITV_DATUM, resultType);
    row[3] = new LynxTransactionCheckResultInfo(finanTransaction, activityStatementTransactions, entryNr, 0, LynxTransactionCheckResultInfoColumn.OPMERKINGEN, resultType);
    row[4] = new LynxTransactionCheckResultInfo(finanTransaction, activityStatementTransactions, entryNr, 0, LynxTransactionCheckResultInfoColumn.CHECK_RESULT, resultType);
    data.add(row);
    // Second row is the Activity Statement transaction, which isn't there in this case.
    for (int rowNr = 1; rowNr <= activityStatementTransactions.size(); rowNr++) {
      row = new LynxTransactionCheckResultInfo[5];
      row[0] = new LynxTransactionCheckResultInfo(finanTransaction, activityStatementTransactions, entryNr, rowNr, LynxTransactionCheckResultInfoColumn.RENTE_DATUM, resultType);
      row[1] = new LynxTransactionCheckResultInfo(finanTransaction, activityStatementTransactions, entryNr, rowNr, LynxTransactionCheckResultInfoColumn.TRANSACTIE, resultType);
      row[2] = new LynxTransactionCheckResultInfo(finanTransaction, activityStatementTransactions, entryNr, rowNr, LynxTransactionCheckResultInfoColumn.UITV_DATUM, resultType);
      row[3] = new LynxTransactionCheckResultInfo(finanTransaction, activityStatementTransactions, entryNr, rowNr, LynxTransactionCheckResultInfoColumn.OPMERKINGEN, resultType);
      row[4] = new LynxTransactionCheckResultInfo(finanTransaction, activityStatementTransactions, entryNr, rowNr, LynxTransactionCheckResultInfoColumn.CHECK_RESULT, resultType);
      data.add(row);
    }
  }

  public static PgTransaction findSimilarTransactionInList(PgTransaction transaction, List<PgTransaction> transactions, PgCurrency koersMargin) {
    int numberOfHits = 0;
    PgTransaction transactionInList = null;
    for (PgTransaction currentTransaction: transactions) {
      if (isSimilarTransaction(currentTransaction, transaction, koersMargin)) {
        numberOfHits++;
        transactionInList = currentTransaction;
      }
    }
    if (numberOfHits != 1) {
      LOGGER.severe("numberOfHits=" + numberOfHits);
    }
    return transactionInList;
  }
  
  public static boolean isSimilarTransaction(PgTransaction transactionOne, PgTransaction transactionTwo, PgCurrency koersMargin) {
    if (!(transactionOne instanceof EffRekAandelenTransactie)) {
      return false;
    }
    if (!(transactionTwo instanceof EffRekAandelenTransactie)) {
      return false;
    }
    if ((transactionOne.getExecutionDate() != null)  &&  (transactionTwo.getExecutionDate() == null)) {
      LOGGER.fine("No executionDate for 'transactionOne', but there's one for 'transactionTwo'.");
      return false;
    }
    if ((transactionOne.getExecutionDate() == null)  &&  (transactionTwo.getExecutionDate() != null)) {
      LOGGER.fine("No executionDate for 'transactionTwo', but there's one for 'transactionOne'.");
      return false;
    }
    if ((transactionOne.getExecutionDate() != null)  &&  (transactionTwo.getExecutionDate() != null)) {
      if (!transactionOne.getExecutionDate().equals(transactionTwo.getExecutionDate())) {
        LOGGER.fine("ExecutionDates differ: transactionOne:" + DF.format(transactionOne.getExecutionDate()) + ", transactionTwo:" + DF.format(transactionTwo.getExecutionDate()));
        return false;
      }
    }
    
    EffRekAandelenTransactie effRekAandelenTransactieOne = (EffRekAandelenTransactie) transactionOne;
    EffRekAandelenTransactie effRekAandelenTransactieTwo = (EffRekAandelenTransactie) transactionTwo;
    if (effRekAandelenTransactieOne.isAankoop() != effRekAandelenTransactieTwo.isAankoop()) {
      return false;
    }
    
    if (effRekAandelenTransactieOne.getAantalEffecten() != effRekAandelenTransactieTwo.getAantalEffecten()) {
      return false;
    }
    
    // The effect should be filled in.
    if ((effRekAandelenTransactieOne.getEffect() == null)  ||  (effRekAandelenTransactieTwo.getEffect() == null)  ||
        !effRekAandelenTransactieOne.getEffect().equals(effRekAandelenTransactieTwo.getEffect())) {
      return false;
    }
    
    // The koers should be filled in.
    if ((effRekAandelenTransactieOne.getKoers() == null)  ||  (effRekAandelenTransactieTwo.getKoers() == null)) {
      return false;
    }
    
    // Compare the rate with some margin.
    int factor = Math.max(effRekAandelenTransactieOne.getKoers().getFactor(), effRekAandelenTransactieTwo.getKoers().getFactor());
    factor = Math.max(factor, koersMargin.getFactor());
    PgCurrency koersOne = effRekAandelenTransactieOne.getKoers().certifyFactor(factor);
    PgCurrency koersTwo = effRekAandelenTransactieTwo.getKoers().certifyFactor(factor);
    koersMargin = koersMargin.certifyFactor(factor);
    if (koersOne.isGreaterThan(koersTwo.add(koersMargin)) ||
        koersOne.isLessThan(koersTwo.subtract(koersMargin))) {
      return false;
    }
    
    if (effRekAandelenTransactieOne.getTransactionType() != effRekAandelenTransactieTwo.getTransactionType()) {
      return false;
    }
    
    return true;
  }
}
