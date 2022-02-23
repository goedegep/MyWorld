package goedegep.app.finan.lynxapp.transactioncheck;

import java.util.ArrayList;
import java.util.List;

import goedegep.finan.basic.PgTransaction;

public class LynxTransactionCheckResultInfo {
  private PgTransaction transactionStoredInFinan;
  private List<PgTransaction> transactionsFromActivityStatement;
  
  private int entryNr;
  private int rowNr;
  private LynxTransactionCheckResultInfoColumn column;
  private LynxTransactionCheckResultInfoResultType resultType;
  
  public LynxTransactionCheckResultInfo(PgTransaction transactionStoredInFinan,
      PgTransaction transactionFromActivityStatement, int entryNr, int rowNr,
      LynxTransactionCheckResultInfoColumn column, LynxTransactionCheckResultInfoResultType resultType) {
    this(transactionStoredInFinan, (List<PgTransaction>) null, entryNr, rowNr, column, resultType);
    transactionsFromActivityStatement = new ArrayList<>();
    transactionsFromActivityStatement.add(transactionFromActivityStatement);
  }
  
  public LynxTransactionCheckResultInfo(PgTransaction transactionStoredInFinan,
      List<PgTransaction> transactionsFromActivityStatement, int entryNr, int rowNr,
      LynxTransactionCheckResultInfoColumn column, LynxTransactionCheckResultInfoResultType resultType) {
    super();
    this.transactionStoredInFinan = transactionStoredInFinan;
    this.transactionsFromActivityStatement = transactionsFromActivityStatement;
    this.entryNr = entryNr;
    this.rowNr = rowNr;
    this.column = column;
    this.resultType = resultType;
  }

  public PgTransaction getTransactionStoredInFinan() {
    return transactionStoredInFinan;
  }

  public void setTransactionStoredInFinan(PgTransaction transactionStoredInFinan) {
    this.transactionStoredInFinan = transactionStoredInFinan;
  }

  public List<PgTransaction> getTransactionsFromActivityStatement() {
    return transactionsFromActivityStatement;
  }

  public LynxTransactionCheckResultInfoColumn getColumn() {
    return column;
  }

  public int getEntryNr() {
    return entryNr;
  }

  public int getRowNr() {
    return rowNr;
  }

  public void setColumn(LynxTransactionCheckResultInfoColumn column) {
    this.column = column;
  }

  public LynxTransactionCheckResultInfoResultType getResultType() {
    return resultType;
  }

  public void setResultType(LynxTransactionCheckResultInfoResultType resultType) {
    this.resultType = resultType;
  }
}
