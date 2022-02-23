package goedegep.app.finan.finanapp;

import java.awt.Dimension;
import java.awt.Window;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import goedegep.appgen.swing.Customization;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.basic.TransactionError;

/**
 * A window which lists the problems in media information.
 * 
 */
@SuppressWarnings("serial")
public class TransactionProblemsWindow extends AppFrame {
  private static final Logger     LOGGER = Logger.getLogger(TransactionProblemsWindow.class.getName());

  private static final String     WINDOW_TITLE = "Transactie problemen";
  private static final String     NEWLINE = System.getProperty("line.separator");
  private static final DateTimeFormatter DTF =  DateTimeFormatter.ofPattern("dd-MM-yyyy");
  
  private ComponentFactory componentFactory;

  /**
   * Constructor.
   * 
   * @param customization GUI customization
   * @param parent owner of this window
   * @param mediaDb media database, used to propose solutions for problems
   * @param errors the problems to be listed
   */
  public TransactionProblemsWindow(Customization customization, Window parent, List<TransactionError> errors) {
    super(WINDOW_TITLE, customization, new Dimension(1400, 720));
    
    initGUI(errors);
  }
  
  /**
   * Create the GUI.
   * 
   * The problems are listed as a list of error panels, using a BoxLayout.
   * 
   * @param errors the problems to be listed
   */
  private void initGUI(List<TransactionError> errors) {
    componentFactory = getCustomization().getComponentFactory();
    
    JPanel mainPanel = componentFactory.createPanel(900, 700, false);
    JPanel errorListPanel = componentFactory.createPanel(900, 700, false);
    errorListPanel.setLayout(new BoxLayout(errorListPanel, BoxLayout.PAGE_AXIS));
    
    int errorCount = 0;
    for (TransactionError error: errors) {
      JPanel errorPanel = componentFactory.createPanel(-1, -1, true);
      errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.PAGE_AXIS));
      fillErrorPanel(errorPanel, error);
      errorListPanel.add(errorPanel);
      
      if (errorCount++ == 6) {
        LOGGER.severe("ERROR LIST TRUNCATED");
        break;
      }
    }
    
    JScrollPane scrollPane = new JScrollPane(errorListPanel);
    mainPanel.add(scrollPane);
    getContentPane().add(mainPanel);
    
    pack();
  }

  /**
   * Fill a panel to report a problem.
   * <p>
   * The panel shall at least describe the problem.
   * 
   * @param the panel to be filled
   * @param error the problem to be reported
   */
  private void fillErrorPanel(JPanel errorPanel, TransactionError error) {
    StringBuilder buf = new StringBuilder();
    PgTransaction transaction = error.getTransaction();
    
    buf.append("Fout bij transactie afhandeling: ");
    buf.append(error.getErrorMessage());
    
    LocalDate date = transaction.getExecutionDate();
    if (date != null) {
      buf.append(NEWLINE);
      buf.append("Uitvoeringsdatum: ");
      buf.append(date.format(DTF));
    }

    date = transaction.getBookingDate();
    if (date != null) {
      buf.append(NEWLINE);
      buf.append("Rentedatum: ");
      buf.append(date.format(DTF));
    }

    buf.append(NEWLINE);
    buf.append("Transactie: ");
    buf.append(transaction.getDescription());
    
    JPanel textPanel = createErrorTextPanel(buf.toString());
    
    errorPanel.add(textPanel);
  }

  private JPanel createErrorTextPanel(String text) {
    JPanel errorTextPanel = componentFactory.createPanel(700, 10, false);
    JTextArea textArea = componentFactory.createTextArea(text, false);
    JScrollPane scrollPane = new JScrollPane(textArea);
    errorTextPanel.add(scrollPane);
    
    return errorTextPanel;
  }
}
