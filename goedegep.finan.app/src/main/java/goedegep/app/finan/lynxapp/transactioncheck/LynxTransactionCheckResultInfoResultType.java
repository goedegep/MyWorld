package goedegep.app.finan.lynxapp.transactioncheck;

public enum LynxTransactionCheckResultInfoResultType {
  FOUND_IN_BOTH,    // During rendering of the table, differences are determined.
  FOUND_SIMILAR,
  FOUND_MATCHING_PARTIAL_EXECUTIONS,
  ONLY_IN_FINAN,
  ONLY_IN_ACTIVITY_STATEMENT
}
