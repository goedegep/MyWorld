package goedegep.app.finan.lynxapp;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import goedegep.app.finan.effrek.EffRekWindow;
import goedegep.app.finan.gen.FinanBank;
import goedegep.app.finan.lynxapp.transactioncheck.LynxTransactionCheckWindow;
import goedegep.appgen.MessageDialogType;
import goedegep.appgen.WindowUtil;
import goedegep.appgen.swing.Customization;
import goedegep.appgen.swing.MenuFactory;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.lynx.lynxeffrek.LynxEffRek;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekAandelenTransactie;
import goedegep.finan.lynx.lynxeffrek.Ofx2Finan;
import goedegep.finan.lynx2finan.model.LynxToFinanShareIdList;
import goedegep.finan.stocks.StockDepot;
import goedegep.finan.stocks.StockDepotPeriodicReport;

@SuppressWarnings("serial")
public class LynxEffRekWindow extends EffRekWindow {
  private static final Logger LOGGER = Logger.getLogger(LynxEffRekWindow.class.getName());
  private static final DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
  private static final String     WINDOW_TITLE = "Lynx Effectenrekening";
  private static final int        MENU_OVERZICTEN_INDEX = 2;
  
  private LynxEffRek effectenRekening;
  private LynxToFinanShareIdList lynxToFinanShareIdList;
  private Ofx2Finan ofx2Finan;
  
  //Construct the frame
  public LynxEffRekWindow(Customization customization, FinanBank finanBank, LynxEffRek effectenRekening,
      LynxToFinanShareIdList lynxToFinanShareIdList) {
    this(customization, finanBank, effectenRekening, lynxToFinanShareIdList, LocalDate.now());
  }
  
  public LynxEffRekWindow(Customization customization, FinanBank finanBank, LynxEffRek effectenRekening,
      LynxToFinanShareIdList lynxToFinanShareIdList, LocalDate statusDate) {
    super(WINDOW_TITLE, customization, new Dimension(800, 600), finanBank, effectenRekening);

    this.effectenRekening = effectenRekening;
    this.lynxToFinanShareIdList = lynxToFinanShareIdList;
    ofx2Finan = new Ofx2Finan(lynxToFinanShareIdList.getEntries());
    
//    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
//    try {
//      init();
//    }
//    catch(Exception e) {
//      e.printStackTrace();
//    }
    addMenuBarExtensions();
  }

  // Transacties | Test Window
  protected void testWindow_actionPerformed(ActionEvent e) {
    boolean             packFrame = false;
    LynxEffRekTestWindow  window = new LynxEffRekTestWindow(getCustomization(), getFinanBank(), (LynxEffRek) getEffRek());

    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame)
      window.pack();
    else
      window.validate();
    WindowUtil.showFrameCenteredOnScreen(window, 22, 22);
  }

  private void addMenuBarExtensions() {
    JMenuBar menuBar = getJMenuBar();
    JMenu menu = menuBar.getMenu(MENU_OVERZICTEN_INDEX);
    
    // Overzichten: Controle tegen Activity Statements.
    MenuFactory.addMenuItem(menu, "Controle tegen Activity Statements", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        controleTegenActivityStatements_actionPerformed(e);
      }
    });
    
    // Overzichten: Controleer alle transacties met Lynx overzichten.
    MenuFactory.addMenuItem(menu, "Controleer alle transacties met Lynx overzichten", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        controleerTransacties_actionPerformed(e);
      }
    });
    
    // Overzichten: Controleer overzichten met Lynx overzichten
    MenuFactory.addMenuItem(menu, "Controleer overzichten met Lynx overzichten", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        controleerOverzichten_actionPerformed(e);
      }
    });
  }
  
  private void controleTegenActivityStatements_actionPerformed(ActionEvent e) {
    LynxTransactionCheckWindow  window = new LynxTransactionCheckWindow(getCustomization(), effectenRekening, lynxToFinanShareIdList);

    WindowUtil.showFrameCenteredOnScreen(window, 22, 22);
  }

  private void controleerTransacties_actionPerformed(ActionEvent e) {
    LOGGER.severe("=>");
    // read all Lynx Activity statements, and create a list of FinanTransactions for all Lynx Transactions
    List<PgTransaction> transactionsFromActivityStatements = ofx2Finan.getFinanTransactionsFromActivityStatements();
    // Make a shallow copy of the list of transaction derived from the Activity statements.
    List<PgTransaction> transactionsFromActivityStatementsCopy = new ArrayList<>();
    for (PgTransaction pgTransaction: transactionsFromActivityStatements) {
      LOGGER.fine("Copying: " + DF.format(pgTransaction.getExecutionDate()) + " " + pgTransaction.getDescription());
      transactionsFromActivityStatementsCopy.add(pgTransaction);
    }
    
    int matches = 0;
    int partialTransactionMatches = 0;
    int effRekOnly = 0;
    int actStatementOnly = 0;
    // For each transaction on the LynxEffRek:
    for (PgTransaction effRekTransaction: effectenRekening.getTransactions()) {
      LOGGER.fine("Checking: " + ((effRekTransaction.getExecutionDate() != null) ? DF.format(effRekTransaction.getExecutionDate()) : "--") + " " + effRekTransaction.getDescription());
      PgTransaction activityStatementTransaction = findTransactionInList(effRekTransaction, transactionsFromActivityStatementsCopy);
      if (activityStatementTransaction != null) {
        transactionsFromActivityStatementsCopy.remove(activityStatementTransaction);
        matches++;
      } else {
        List<PgTransaction> activityStatementPartialTransactions = findPartialTransactionsInList(effRekTransaction, transactionsFromActivityStatementsCopy);
        if (activityStatementPartialTransactions == null) {
          LOGGER.severe("Transaction on LynxEffectenRekening, but not in activity statements");
          LOGGER.severe(((effRekTransaction.getExecutionDate() != null) ? DF.format(effRekTransaction.getExecutionDate()) : "--") + " " + effRekTransaction.getDescription());
          effRekOnly++;
        } else {
          for (PgTransaction partialTransaction: activityStatementPartialTransactions) {
            transactionsFromActivityStatementsCopy.remove(partialTransaction);
            partialTransactionMatches++;
          }
          matches++;
        }
      }
    }
    
    LOGGER.severe("Transactions in activity statements, but not on LynxEffectenRekening:");
    for (PgTransaction activityStatementTransaction: transactionsFromActivityStatementsCopy) {
      LOGGER.severe(((activityStatementTransaction.getExecutionDate() != null) ? DF.format(activityStatementTransaction.getExecutionDate()) : "--") + " " + activityStatementTransaction.getDescription());
      actStatementOnly++;
    }
    
    LOGGER.severe("matches=" + matches + ", partialTransactionMatches=" + partialTransactionMatches + ", effRekOnly=" + effRekOnly + ", actStatementOnly=" + actStatementOnly);
    
    // - if it is not in the list from the activity statements, report an error
    // - if it is in the list from the activity statements, remove it from this list
    //   This is done because there may be 2 identical transactions on the same day (I don't store the transaction time)
    // Report all remaining transactions in the list from the activity statements as errors.
    
    
    LOGGER.severe("<=");
  }

  public static PgTransaction findTransactionInList(PgTransaction transaction, List<PgTransaction> transactions) {
    int numberOfHits = 0;
    PgTransaction transactionInList = null;
    for (PgTransaction currentTransaction: transactions) {
      if (currentTransaction.isSameTransaction(transaction)) {
        numberOfHits++;
        transactionInList = currentTransaction;
      }
    }
    if (numberOfHits != 1) {
      LOGGER.severe("numberOfHits=" + numberOfHits);
    }
    return transactionInList;
  }
  
  public static List<PgTransaction> findPartialTransactionsInList(PgTransaction effRekTransaction, List<PgTransaction> transactions) {
    if (!(effRekTransaction instanceof LynxEffRekAandelenTransactie)) {
      return null;
    }
    
    LynxEffRekAandelenTransactie lynxEffRekAandelenTransactie = (LynxEffRekAandelenTransactie) effRekTransaction;
    
    List<LynxEffRekAandelenTransactie> partialTransactionCandidates = findPartialTransactionCandidates(lynxEffRekAandelenTransactie, transactions);
    if (partialTransactionCandidates.size() < 2) {
      return null;
    }
    
    if (lynxEffRekAandelenTransactie.getAantalEffecten() > sumAantalEffecten(partialTransactionCandidates)) {
      return null;
    }
    
    List<PgTransaction> partialTransactions = new ArrayList<>();
    int aantalLeft = lynxEffRekAandelenTransactie.getAantalEffecten();
    while (aantalLeft > 0) {
      LynxEffRekAandelenTransactie candidate = partialTransactionCandidates.remove(0);
      aantalLeft -= candidate.getAantalEffecten();
      partialTransactions.add(candidate);
    }

//    if (lynxEffRekAandelenTransactie.getAantalEffecten() != sumAantalEffecten(partialTransactionCandidates)) {
//      LOGGER.severe("AantalEffecten in transactie: " + lynxEffRekAandelenTransactie.getAantalEffecten());
//      for (LynxEffRekAandelenTransactie candidate: partialTransactionCandidates) {
//        LOGGER.severe("AantalEffecten in candidate: " + candidate.getAantalEffecten());
//      }
//      throw new RuntimeException("Te veel effecten in deeluitvoeringen.");
//    }
//    
//    List<LynxEffRekAandelenTransactie> partialTransactions = new ArrayList<>(partialTransactionCandidates);
    
    
    return partialTransactions;
  }
  
  public static int sumAantalEffecten(List<LynxEffRekAandelenTransactie> transactions) {
    int aantalEffecten = 0;
    
    for (LynxEffRekAandelenTransactie transaction: transactions) {
      aantalEffecten += transaction.getAantalEffecten();
    }
    
    return aantalEffecten;
  }

  public static List<LynxEffRekAandelenTransactie> findPartialTransactionCandidates(LynxEffRekAandelenTransactie lynxEffRekAandelenTransactie, List<PgTransaction> transactions) {
    List<LynxEffRekAandelenTransactie> partialTransactionCandidates = new ArrayList<>();
    
    for (PgTransaction currentTransaction: transactions) {
      if (isPartialTransactionCandidate(lynxEffRekAandelenTransactie, currentTransaction)) {
        partialTransactionCandidates.add((LynxEffRekAandelenTransactie) currentTransaction);
      }
    }
   
    return partialTransactionCandidates;
  }

  public static boolean isPartialTransactionCandidate(LynxEffRekAandelenTransactie combinedTransaction, PgTransaction partialCandidateTransaction) {
    if (!(partialCandidateTransaction instanceof LynxEffRekAandelenTransactie)) {
      return false;
    }
    
    LynxEffRekAandelenTransactie lynxEffRekAandelenTransactie = (LynxEffRekAandelenTransactie) partialCandidateTransaction;
    
    if ((combinedTransaction.getExecutionDate() == null)  ||  (lynxEffRekAandelenTransactie.getExecutionDate() == null)) {
      LOGGER.fine("No executionDate.");
      return false;
    }
    
    if (!combinedTransaction.getExecutionDate().equals(lynxEffRekAandelenTransactie.getExecutionDate())) {
      LOGGER.fine("ExecutionDates differ: combinedTransaction:" + DF.format(combinedTransaction.getExecutionDate()) + ", lynxEffRekAandelenTransactie:" + DF.format(lynxEffRekAandelenTransactie.getExecutionDate()));
      return false;
    }
    
    if (combinedTransaction.isAankoop() != lynxEffRekAandelenTransactie.isAankoop()) {
      return false;
    }
    
    if (combinedTransaction.getAantalEffecten() < lynxEffRekAandelenTransactie.getAantalEffecten()) {
      return false;
    }
    
    // The effect should be filled in.
    if ((combinedTransaction.getEffect() == null)  ||  (lynxEffRekAandelenTransactie.getEffect() == null)  ||  !combinedTransaction.getEffect().equals(lynxEffRekAandelenTransactie.getEffect())) {
      return false;
    }
    
    // The koers should be filled in.
    if ((combinedTransaction.getKoers() == null)  ||  (lynxEffRekAandelenTransactie.getKoers() == null)) {
      return false;
    }
        
    return true;
  }

  protected void controleerOverzichten_actionPerformed(ActionEvent e) {
    LOGGER.severe("=>");
    
    // bepaal periode: start en eind datum
    // start datum is datum van eerste Lynx transactie
    PgTransaction firstTransactionOnAccount = effectenRekening.getTransaction(1);
    if (firstTransactionOnAccount == null) {
      showMessageDialog(MessageDialogType.WARNING, "Geen transacties, dus geen controle mogenlijk");
      return;
    }
    LocalDate startDate = firstTransactionOnAccount.getExecutionDate();
    if (startDate == null) {
      startDate = firstTransactionOnAccount.getBookingDate();
    }
    LOGGER.severe("start datum: " + startDate);
    
    // eind datum is vandaag.
    LocalDate endDate = LocalDate.now();
    LOGGER.severe("eind datum: " + endDate);
    
    // bepaal eerte en laatste Maand
    DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM-yyyy");
    YearMonth startMonth = YearMonth.from(startDate);
    LOGGER.severe("start maand: " + startMonth.format(monthFormatter));
    YearMonth endMonth = YearMonth.from(endDate);
    LOGGER.severe("eind maand: " + endMonth.format(monthFormatter));
    endMonth = endMonth.plusMonths(-1);
    LOGGER.severe("eind maand: " + monthFormatter.format(endMonth));
    
    // controleer of voor deze maanden de Finan raporten bestaan (en er ook geen andere rapporten zijn)
    StockDepot stockDepot = effectenRekening.getVerzamelDepot();
    List<StockDepotPeriodicReport<YearMonth>> depotReports = stockDepot.getMonthlyReports();
    boolean first = true;
    YearMonth month = null;
    for (StockDepotPeriodicReport<YearMonth> report: depotReports) {
      if (first) {
        month = report.getPeriod();
        if (!month.equals(startMonth)) {
          LOGGER.severe("Eerste rapport is niet van de juiste maand, eerste maand moet zijn: " + monthFormatter.format(startMonth) + ", maar is: " + monthFormatter.format(month));
          first = false;
        }
      } else {
        month = month.plusMonths(1);
        if (!report.getPeriod().equals(month)) {
          LOGGER.severe("Een of meer ontbrekende maandrapporten, van: " + monthFormatter.format(month) + ", tot: " + monthFormatter.format(report.getPeriod()));
          month = report.getPeriod();
        }
      }
    }
    if (first) {
      showMessageDialog(MessageDialogType.ERROR, "Er zijn geen maandrapporten, dus er valt niets te controleren.");
      return;
    }
    if (!month.equals(endMonth)) {
      LOGGER.severe("Laatste rapport is niet van de juiste maand, laatste maand moet zijn: " + monthFormatter.format(endMonth) + ", maar is: " + monthFormatter.format(month));
      first = false;
    }
    
    // controleer of voor deze maanden de Lynx raporten bestaan
    // TODO Hier verder: kijk of dit vervangen wordt door ...transactioncheck
//    List<LynxEffRekPeriodicReport<Month>> lynxMonthlyReports = effectenRekening.getLynxMonthlyReports();
    
    // vergelijk de rapporten per maand.
    // belangrijkste informatie:
    // saldo
    // effecten in bezit
    // overig
    // alle transacties
  }
}