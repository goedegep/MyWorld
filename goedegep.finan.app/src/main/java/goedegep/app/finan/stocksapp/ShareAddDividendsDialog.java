package goedegep.app.finan.stocksapp;

import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.finan.stocks.Share;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class ShareAddDividendsDialog extends StocksHelperDialog {
  private static final String      WINDOW_TITLE = "Dividenden aan aandelen toevoegen";
  private static final String      NEWLINE = System.getProperty("line.separator");
  private static final String      HELP_TEXT =
      "Kies een aandeel en voer de gegevens in van een nieuw dividend voor dat aandeel." +
      NEWLINE +
      "Dividend informatie:" + NEWLINE +
      "Aan een dividend wordt gerefereerd via een combinatie van naam en jaar," +
      "daarom moet minstens een van deze twee ingevuld worden.";

  private JComboBox<String>       shareBox;             // 1e rij, aandeel
  private Share                   currentShare = null;  // share selected in shareBox
  private DividendsTable          dividendsTable;

  public ShareAddDividendsDialog(AppFrame parent, Share selectedShare) {
    super(parent, WINDOW_TITLE, HELP_TEXT, 3);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try  {
      jbInit(selectedShare);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    pack();
  }

  private void jbInit(Share selectedShare) throws Exception  {
    JPanel panel;
    ComponentFactory componentFactory = getTheComponentFactory();

    /*
     * Center panel
     */

    // 1e rij, label "Aandeel"
    panel = componentFactory.createLabelPanel("Aandeel", SwingConstants.LEFT, 150, 20);
    _centerPanel.add(panel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                    ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 1, 0, 0), 0, 0));

    // 1e rij, aandeel
    shareBox = componentFactory.createComboBox(10, "selecteer aandeel");
    for (Share share: Share.getShares()) {
      shareBox.addItem(share.getName());
    }
    if (selectedShare != null) {
      shareBox.setSelectedItem(selectedShare.getName());
    }
    panel = componentFactory.createPanel(250, 20, false);
    panel.add(shareBox, BorderLayout.CENTER);
    _centerPanel.add(panel, new GridBagConstraints(GridBagConstraints.RELATIVE, 1, 1, 1, 0.0, 0.0
              ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

    // 2e rij, label "Huidige dividenden"
    panel = componentFactory.createLabelPanel("Huidige dividenden", SwingConstants.LEFT, 150, 110);
    _centerPanel.add(panel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                    ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 1, 0, 0), 0, 0));

    // 2e rij, huidige dividenden
    dividendsTable = new DividendsTable(getOwner());
    _centerPanel.add(dividendsTable, new GridBagConstraints(GridBagConstraints.RELATIVE, 2, 1, 1, 0.0, 0.0
                    ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

    /* buttons panel */

    // "klaar" button
    JButton button = componentFactory.createButton("klaar", null);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancel();
      }
    });
    panel = componentFactory.createPanel(-1, -1, false);
    panel.add(button);
    _buttonsPanel.add(panel, BorderLayout.EAST);

    // Add action listeners.
    shareBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        updateCurrentDividendsList();
      }
    });

    updateCurrentDividendsList();
  }

  protected void processWindowEvent(WindowEvent e) {
    if(e.getID() == WindowEvent.WINDOW_CLOSING) {
      cancel();
    }
    super.processWindowEvent(e);
  }

  void cancel() {
    dispose();
  }

  /* Called when the selected share has changed, or when a dividend has been added.
   * Fills the 'currentDividendsList' with the names of the dividends of the selected share.
   */
  public void updateCurrentDividendsList() {
    String shareName = (String) shareBox.getSelectedItem();
    currentShare = Share.getShare(shareName);
    dividendsTable.setShare(currentShare);
  }
}