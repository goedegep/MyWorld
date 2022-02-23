package goedegep.app.finan.lynxapp.transactioncheck;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.logging.Logger;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import goedegep.finan.basic.PgTransaction;

@SuppressWarnings("serial")
public class LynxTransactionDifferencesTableCellRenderer extends DefaultTableCellRenderer {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(LynxTransactionDifferencesTableCellRenderer.class.getName());
  private static final SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
  
  private static Color lighterRed = new Color(255, 20, 20);
  private static Color darkerRed = new Color(220, 0, 0);
  private static Color lighterGreen = new Color(20, 255, 20);
  private static Color darkerGreen = new Color(0, 220, 0);
  private static Color lighterOrange = new Color(255, 205, 0);
  private static Color darkerOrange = new Color(230, 185, 0);
  
  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    
    assert value instanceof LynxTransactionCheckResultInfo;
    LynxTransactionCheckResultInfo lynxTransactionCheckResultInfo = (LynxTransactionCheckResultInfo) value;
    
    if (lynxTransactionCheckResultInfo.getRowNr() == 0) {
      handleFirstRow(lynxTransactionCheckResultInfo);
    } else {
      handleSecondRow(lynxTransactionCheckResultInfo);
    }
    
    int oddOrEven = lynxTransactionCheckResultInfo.getEntryNr() % 2;
    switch (lynxTransactionCheckResultInfo.getResultType()) {
    case FOUND_IN_BOTH:
      if (oddOrEven == 0) {
        setBackground(darkerGreen);
      } else {
        setBackground(lighterGreen);
      }
      break;
      
    case FOUND_SIMILAR:
    case FOUND_MATCHING_PARTIAL_EXECUTIONS:
      if (oddOrEven == 0) {
        setBackground(darkerOrange);
      } else {
        setBackground(lighterOrange);
      }
      break;
      
    case ONLY_IN_FINAN:
    case ONLY_IN_ACTIVITY_STATEMENT:
      if (oddOrEven == 0) {
        setBackground(darkerRed);
      } else {
        setBackground(lighterRed);
      }
      break;
    }
    return this;
  }

  private void handleFirstRow(LynxTransactionCheckResultInfo lynxTransactionCheckResultInfo) {
    switch (lynxTransactionCheckResultInfo.getColumn()) {
    case RENTE_DATUM:
      handleFirstRowRenteDatum(lynxTransactionCheckResultInfo);
      break;
      
    case TRANSACTIE:
      handleFirstRowTransactie(lynxTransactionCheckResultInfo);
      break;
      
    case UITV_DATUM:
      handleFirstRowUitvoeringsDatum(lynxTransactionCheckResultInfo);
      break;
      
    case OPMERKINGEN:
      handleFirstRowOpmerkingen(lynxTransactionCheckResultInfo);
      break;
      
    case CHECK_RESULT:
      handleFirstRowCheckResult(lynxTransactionCheckResultInfo);
      break;
    }
  }

  private void handleFirstRowRenteDatum(LynxTransactionCheckResultInfo lynxTransactionCheckResultInfo) {
    String text = "";
    PgTransaction transaction = lynxTransactionCheckResultInfo.getTransactionStoredInFinan();
    if (transaction != null) {
      LocalDate renteDatum = transaction.getBookingDate();
      if (renteDatum != null) {
        text = DF.format(renteDatum);
      }
    }
    setText(text);
  }

  private void handleFirstRowTransactie(LynxTransactionCheckResultInfo lynxTransactionCheckResultInfo) {
    String text = "";
    PgTransaction transaction = lynxTransactionCheckResultInfo.getTransactionStoredInFinan();
    if (transaction != null) {
      text = transaction.getDescription();
    }
    setText(text);
  }

  private void handleFirstRowUitvoeringsDatum(LynxTransactionCheckResultInfo lynxTransactionCheckResultInfo) {
    String text = "";
    PgTransaction transaction = lynxTransactionCheckResultInfo.getTransactionStoredInFinan();
    if (transaction != null) {
      LocalDate uitvoeringsDatum = transaction.getExecutionDate();
      if (uitvoeringsDatum != null) {
        text = DF.format(uitvoeringsDatum);
      }
    }
    setText(text);
  }

  private void handleFirstRowOpmerkingen(LynxTransactionCheckResultInfo lynxTransactionCheckResultInfo) {
    String text = "";
    PgTransaction transaction = lynxTransactionCheckResultInfo.getTransactionStoredInFinan();
    if (transaction != null) {
      text = transaction.getComment();
    }
    setText(text);
  }

  private void handleFirstRowCheckResult(LynxTransactionCheckResultInfo lynxTransactionCheckResultInfo) {
    switch (lynxTransactionCheckResultInfo.getResultType()) {
    case FOUND_IN_BOTH:
      setText("");
      break;
      
    case FOUND_SIMILAR:
      setText("Gelijkwaardig");
      break;
      
    case FOUND_MATCHING_PARTIAL_EXECUTIONS:
      setText("Deeluitvoeringen gevonden");
      break;
      
    case ONLY_IN_FINAN:
      setText("Alleen in Finan");
      break;
      
    case ONLY_IN_ACTIVITY_STATEMENT:
      setText("Alleen in Activity Statement");
      break;
    }
  }

  private void handleSecondRow(LynxTransactionCheckResultInfo lynxTransactionCheckResultInfo) {
    switch (lynxTransactionCheckResultInfo.getColumn()) {
    case RENTE_DATUM:
      handleSecondRowRenteDatum(lynxTransactionCheckResultInfo);
      break;
      
    case TRANSACTIE:
      handleSecondRowTransactie(lynxTransactionCheckResultInfo);
      break;
      
    case UITV_DATUM:
      handleSecondRowUitvoeringsDatum(lynxTransactionCheckResultInfo);
      break;
      
    case OPMERKINGEN:
      handleSecondRowOpmerkingen(lynxTransactionCheckResultInfo);
      break;
      
    case CHECK_RESULT:
      handleSecondRowCheckResult(lynxTransactionCheckResultInfo);
      break;
    }
  }

  private void handleSecondRowRenteDatum(LynxTransactionCheckResultInfo lynxTransactionCheckResultInfo) {
    String text = "";
    PgTransaction transaction = lynxTransactionCheckResultInfo.getTransactionsFromActivityStatement().get(lynxTransactionCheckResultInfo.getRowNr() - 1);
    if (transaction != null) {
      LocalDate renteDatum = transaction.getBookingDate();
      if (renteDatum != null) {
        text = DF.format(renteDatum);
      }
    }
    setText(text);
  }

  private void handleSecondRowTransactie(LynxTransactionCheckResultInfo lynxTransactionCheckResultInfo) {
    String text = "";
    PgTransaction transaction = lynxTransactionCheckResultInfo.getTransactionsFromActivityStatement().get(lynxTransactionCheckResultInfo.getRowNr() - 1);
    if (transaction != null) {
      text = transaction.getDescription();
    }
    setText(text);
  }

  private void handleSecondRowUitvoeringsDatum(LynxTransactionCheckResultInfo lynxTransactionCheckResultInfo) {
    String text = "";
    PgTransaction transaction = lynxTransactionCheckResultInfo.getTransactionsFromActivityStatement().get(lynxTransactionCheckResultInfo.getRowNr() - 1);
    if (transaction != null) {
      LocalDate uitvoeringsDatum = transaction.getExecutionDate();
      if (uitvoeringsDatum != null) {
        text = DF.format(uitvoeringsDatum);
      }
    }
    setText(text);
  }

  private void handleSecondRowOpmerkingen(LynxTransactionCheckResultInfo lynxTransactionCheckResultInfo) {
    String text = "";
    PgTransaction transaction = lynxTransactionCheckResultInfo.getTransactionsFromActivityStatement().get(lynxTransactionCheckResultInfo.getRowNr() - 1);
    if (transaction != null) {
      text = transaction.getComment();
    }
    setText(text);
  }

  private void handleSecondRowCheckResult(LynxTransactionCheckResultInfo lynxTransactionCheckResultInfo) {
    switch (lynxTransactionCheckResultInfo.getResultType()) {
    case FOUND_IN_BOTH:
      setText("");
      break;
      
    case FOUND_SIMILAR:
      setText("");
      break;
      
    case FOUND_MATCHING_PARTIAL_EXECUTIONS:
      setText("");
      break;
      
    case ONLY_IN_FINAN:
      setText("");
      break;
      
    case ONLY_IN_ACTIVITY_STATEMENT:
      setText("");
      break;
    }
  }
}
