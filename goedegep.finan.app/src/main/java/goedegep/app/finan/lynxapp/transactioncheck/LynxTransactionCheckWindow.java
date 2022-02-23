package goedegep.app.finan.lynxapp.transactioncheck;

import java.awt.BorderLayout;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import goedegep.appgen.LookAheadComboBoxWithSpinner;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.appgen.swing.Customization;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.lynx.lynxeffrek.LynxEffRek;
import goedegep.finan.lynx.lynxeffrek.LynxMonthlyActivityStatement;
import goedegep.finan.lynx.lynxeffrek.LynxMonthlyActivityStatements;
import goedegep.finan.lynx2finan.model.LynxToFinanShareIdList;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;

@SuppressWarnings("serial")
public class LynxTransactionCheckWindow extends AppFrame {
  private static final Logger LOGGER = Logger.getLogger(LynxTransactionCheckWindow.class.getName());
  private final static String WINDOW_TITLE = "Lynx Transactie controle";
  
  private static final int        LEFT_MARGIN = 23;    // is also first column
  private static final int        SECOND_COLUMN = 210;
  private static final int        THIRD_COLUMN = 320;
  private static final int        FOURTH_COLUMN = 400;
  private static final int        TOP_MARGIN = 12;
  private static final int        LINE_SPACING = 24;
  
  private static final String     JAAR_TOOLTIP = "Kies het jaar waarvoor het overzicht weergegeven moet worden.";
  private static final String     MAAND_TOOLTIP = "Kies de maand waarvoor het overzicht weergegeven moet worden.";
  
  private static final FlexDateFormat FDF = new FlexDateFormat();
  private static final DateTimeFormatter MF = DateTimeFormatter.ofPattern("MM-yyyy");

  private LynxEffRek                          effectenRekening;
  private LynxToFinanShareIdList              lynxToFinanShareIdList;
  private ComponentFactory                    componentFactory;
  private LynxMonthlyActivityStatements       lynxMonthlyActivityStatements;
  
  private LookAheadComboBoxWithSpinner<Integer> yearComboBox;
  private LookAheadComboBoxWithSpinner<Integer> monthComboxBox;
  private LynxTransactionCheckResultPanel       lynxTransactionCheckResultPanel;
  
  // Geselecteerd Jaar + maand
  private Integer     year = null;
  private Integer     month = null;  // 1 t/m 12

  public LynxTransactionCheckWindow(Customization customization, LynxEffRek effectenRekening, LynxToFinanShareIdList lynxToFinanShareIdList) {
    super(WINDOW_TITLE, customization);
    
    this.effectenRekening = effectenRekening;
    this.lynxToFinanShareIdList = lynxToFinanShareIdList;
    componentFactory = getTheComponentFactory();
    
    init();
    
    pack();
  }

  private void init() {
    lynxMonthlyActivityStatements = new LynxMonthlyActivityStatements();
    JPanel controlPanel = createControlPanel();
    getContentPane().add(controlPanel, BorderLayout.CENTER);  
    lynxTransactionCheckResultPanel = new LynxTransactionCheckResultPanel(this, effectenRekening, lynxToFinanShareIdList, lynxMonthlyActivityStatements);
    getContentPane().add(lynxTransactionCheckResultPanel, BorderLayout.SOUTH);

    yearComboBox.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {
        @SuppressWarnings("unchecked")
        LookAheadComboBoxWithSpinner<Integer> yearComboBox = (LookAheadComboBoxWithSpinner<Integer>) e.getSource();
        year = (Integer) yearComboBox.getValue();
        
        updateMonthComboBox();
      }      
    });
    
    monthComboxBox.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {
        @SuppressWarnings("unchecked")
        LookAheadComboBoxWithSpinner<Integer> field = (LookAheadComboBoxWithSpinner<Integer>) e.getSource();
        month = (Integer) field.getValue();
        
        updateTransactionCheckResultPanel();

      }      
    });
    
    // Select the latest year. Do this here, as now the monthComboBox will automatically be updated.
    if (yearComboBox.getItemCount() != 0) {
      yearComboBox.setSelectedIndex(yearComboBox.getItemCount() - 1);
    }
  }
  
  private JPanel createControlPanel() {
    int verticalOffset = TOP_MARGIN;
    
    JPanel controlPanel = componentFactory.createPanel(800, 200, true);
    SpringLayout layout = new SpringLayout();
    controlPanel.setLayout(layout);
    
    // Provide status information on from which month till which month there are transactions in Finan.
    // "Transacties in Finan lopen van:" <maand> "tot en met:" <maand>
    JLabel transactionsFromDateLabel = componentFactory.createLabel("Transacties in Finan lopen van:", SwingConstants.LEFT);
    controlPanel.add(transactionsFromDateLabel);
    layout.putConstraint(SpringLayout.WEST, transactionsFromDateLabel,
        LEFT_MARGIN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, transactionsFromDateLabel,
        verticalOffset + 4,
        SpringLayout.NORTH, controlPanel);
    PgTransaction firstTransaction = effectenRekening.getFirstTransaction();
    JTextField transactionsFromDateField = componentFactory.createTextField(8, null);
    transactionsFromDateField.setHorizontalAlignment(SwingConstants.RIGHT);
    transactionsFromDateField.setEditable(false);
    if (firstTransaction != null) {
      LocalDate firstTransactionDate = firstTransaction.getExecutionDate();
      if (firstTransactionDate == null) {
        firstTransactionDate = firstTransaction.getBookingDate();
      }
      if (firstTransactionDate != null) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LOGGER.severe("First transaction date: " + firstTransactionDate.format(df));
        FlexDate firstTransactionFlexDate = new FlexDate(firstTransactionDate.getMonthValue(), firstTransactionDate.getYear());
        transactionsFromDateField.setText(FDF.format(firstTransactionFlexDate));
      } else {
        transactionsFromDateField.setText("eerste transactie heeft geen datum");
      }
    } else {
      transactionsFromDateField.setText("geen transacties");
    }
    controlPanel.add(transactionsFromDateField);
    layout.putConstraint(SpringLayout.WEST, transactionsFromDateField,
        SECOND_COLUMN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, transactionsFromDateField,
        verticalOffset,
        SpringLayout.NORTH, controlPanel);
    JLabel transactionsTillDateLabel = componentFactory.createLabel("tot en met:", SwingConstants.LEFT);
    controlPanel.add(transactionsTillDateLabel);
    layout.putConstraint(SpringLayout.WEST, transactionsTillDateLabel,
        THIRD_COLUMN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, transactionsTillDateLabel,
        verticalOffset + 4,
        SpringLayout.NORTH, controlPanel);
    PgTransaction lastTransaction = effectenRekening.getLastTransaction();
    JTextField transactionsTillDateField = componentFactory.createTextField(8, null);
    transactionsTillDateField.setHorizontalAlignment(SwingConstants.RIGHT);
    transactionsTillDateField.setEditable(false);
    if (lastTransaction != null) {
      LocalDate lastTransactionDate = lastTransaction.getExecutionDate();
      if (lastTransactionDate == null) {
        lastTransactionDate = lastTransaction.getBookingDate();
      }
      if (lastTransactionDate != null) {
        FlexDate lastTransactionFlexDate = new FlexDate(lastTransactionDate.getMonthValue(), lastTransactionDate.getYear());
        transactionsTillDateField.setText(FDF.format(lastTransactionFlexDate));
      } else {
        transactionsTillDateField.setText("laatste transactie heeft geen datum");
      }
    } else {
      transactionsTillDateField.setText("geen transacties");
    }
    controlPanel.add(transactionsTillDateField);
    layout.putConstraint(SpringLayout.WEST, transactionsTillDateField,
        FOURTH_COLUMN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, transactionsTillDateField,
        verticalOffset,
        SpringLayout.NORTH, controlPanel);
    
    verticalOffset += LINE_SPACING;
    
    // Provide status information on from which month till which month there are Lynx Activity Statements.
    // "Maandelijkse Activity Statements lopen van:" <maand> "tot en met:" <maand>
    JLabel activityStatementsFromDateLabel = componentFactory.createLabel("Activity Statements lopen van:", SwingConstants.LEFT);
    controlPanel.add(activityStatementsFromDateLabel);
    layout.putConstraint(SpringLayout.WEST, activityStatementsFromDateLabel,
        LEFT_MARGIN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, activityStatementsFromDateLabel,
        verticalOffset + 4,
        SpringLayout.NORTH, controlPanel);
    LynxMonthlyActivityStatement firstMonthlyActivityStatement = lynxMonthlyActivityStatements.getFirstMonthlyActivityStatement();
    JTextField firstMonthlyActivityStatementField = componentFactory.createTextField(8, null);
    firstMonthlyActivityStatementField.setHorizontalAlignment(SwingConstants.RIGHT);
    firstMonthlyActivityStatementField.setEditable(false);
    if (firstMonthlyActivityStatement != null) {
      YearMonth firstMonthlyActivityStatementDate = firstMonthlyActivityStatement.getMonth();
      firstMonthlyActivityStatementField.setText(MF.format(firstMonthlyActivityStatementDate));
    } else {
      firstMonthlyActivityStatementField.setText("geen activity statements");
    }
    controlPanel.add(firstMonthlyActivityStatementField);
    layout.putConstraint(SpringLayout.WEST, firstMonthlyActivityStatementField,
        SECOND_COLUMN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, firstMonthlyActivityStatementField,
        verticalOffset,
        SpringLayout.NORTH, controlPanel);
    JLabel activityStatementsTillDateLabel = componentFactory.createLabel("tot en met:", SwingConstants.LEFT);
    controlPanel.add(activityStatementsTillDateLabel);
    layout.putConstraint(SpringLayout.WEST, activityStatementsTillDateLabel,
        THIRD_COLUMN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, activityStatementsTillDateLabel,
        verticalOffset + 4,
        SpringLayout.NORTH, controlPanel);
    LynxMonthlyActivityStatement lastMonthlyActivityStatement = lynxMonthlyActivityStatements.getLastMonthlyActivityStatement();
    JTextField lastMonthlyActivityStatementField = componentFactory.createTextField(8, null);
    lastMonthlyActivityStatementField.setHorizontalAlignment(SwingConstants.RIGHT);
    lastMonthlyActivityStatementField.setEditable(false);
    if (lastMonthlyActivityStatement != null) {
      YearMonth lastMonthlyActivityStatementDate = lastMonthlyActivityStatement.getMonth();
      lastMonthlyActivityStatementField.setText(lastMonthlyActivityStatementDate.format(MF));
    } else {
      lastMonthlyActivityStatementField.setText("geen activity statements");
    }
    controlPanel.add(lastMonthlyActivityStatementField);
    layout.putConstraint(SpringLayout.WEST, lastMonthlyActivityStatementField,
        FOURTH_COLUMN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, lastMonthlyActivityStatementField,
        verticalOffset,
        SpringLayout.NORTH, controlPanel);
    
    verticalOffset += LINE_SPACING;
    
    // label and textfield for selecting a year.
    JLabel yearSelectionLabel = componentFactory.createLabel("Jaar:", SwingConstants.LEFT);
    controlPanel.add(yearSelectionLabel);
    layout.putConstraint(SpringLayout.WEST, yearSelectionLabel,
        LEFT_MARGIN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, yearSelectionLabel,
        verticalOffset + 4,
        SpringLayout.NORTH, controlPanel);
    
    List<Integer> years = lynxMonthlyActivityStatements.getYearsForWhichStatementsAreAvailable();
    yearComboBox = componentFactory.createLookAheadComboBoxWithSpinner(years, false, 12, 20, JAAR_TOOLTIP);
    monthComboxBox = componentFactory.createLookAheadComboBoxWithSpinner(null, false, 12, 20, MAAND_TOOLTIP);
    
    controlPanel.add(yearComboBox);
    layout.putConstraint(SpringLayout.WEST, yearComboBox,
        SECOND_COLUMN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, yearComboBox,
        verticalOffset,
        SpringLayout.NORTH, controlPanel);

    // label and textfield for selecting a month.
    JLabel monthSelectionLabel = componentFactory.createLabel("Maand:", SwingConstants.LEFT);
    controlPanel.add(monthSelectionLabel);
    layout.putConstraint(SpringLayout.WEST, monthSelectionLabel,
        THIRD_COLUMN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, monthSelectionLabel,
        verticalOffset + 4,
        SpringLayout.NORTH, controlPanel);
    
    controlPanel.add(monthComboxBox);
    layout.putConstraint(SpringLayout.WEST, monthComboxBox,
        FOURTH_COLUMN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, monthComboxBox,
        verticalOffset,
        SpringLayout.NORTH, controlPanel);
    
    controlPanel.revalidate();
    controlPanel.doLayout();
    return controlPanel;
  }

  private void updateMonthComboBox() {
//    monthComboxBox.removeAllItems();

    if (year != null) {
      List<Integer> months = lynxMonthlyActivityStatements.getMonthsForWhichStatementsAreAvailable(year);
      if (months.size() != 0) {
        monthComboxBox.setValues(months);
        monthComboxBox.setSelectedIndex(monthComboxBox.getItemCount() - 1);
      }
    }
    updateTransactionCheckResultPanel();
  }
  
  private void updateTransactionCheckResultPanel() {
    LOGGER.severe("=> year=" + year + ", month=" + month);
    if (year != null  &&  month != null) {
      lynxTransactionCheckResultPanel.setMonth(YearMonth.of(year, month));
    } else {
      lynxTransactionCheckResultPanel.setMonth(null);
    }
    LOGGER.severe("<=");
  }
}
