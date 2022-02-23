package goedegep.app.finan.stocksapp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import goedegep.appgen.swing.AppDialog;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;

/**
 * <p>Title: FinanDialog</p>
 * <p>Description: A JDialog for Stock Dialogs that add items.<br/>
 *    All 'Add'dialogs related to stocks have the following layout:<br/>
 *      mainLayout is a GridBagLayout:<br/>
 *        First row is the help text area.<br/>
 *        A panel with buttons.<br/>
 *        Status bar.<br/>
 * </p>
 * <p>@author Peter Goedegebure</p>
 */

@SuppressWarnings("serial")
public class StocksHelperDialog extends AppDialog {
  
  protected Container _centerPanel;
  protected JPanel    _buttonsPanel;
  protected JLabel    _statusBar = new JLabel("");

  public StocksHelperDialog(AppFrame owner, String title, String helpText, int centerRows) {
    super(owner, title);
//    Look look = getLook();
    ComponentFactory componentFactory = getTheComponentFactory();

    _centerPanel = getContentPane();
//    _centerPanel.setBackground(look.getPanelBackgroundColor());  // TODO this shouldn't be needed here.

    GridBagLayout gbl = new GridBagLayout();
    _centerPanel.setLayout(gbl);


    // set help text as first row.
    JTextArea   helpTextArea = componentFactory.createTextArea(helpText, true);
    //helpTextArea.setMinimumSize();
    _centerPanel.add(helpTextArea, new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER, 1, 0.0, 0.0
             ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 1, 0, 0), 0, 0));

    /* bottom panel with buttons */
    _buttonsPanel = componentFactory.createPanel(-1, -1, true);
    _centerPanel.add(_buttonsPanel, new GridBagConstraints(0, 1 + centerRows, GridBagConstraints.REMAINDER, 1, 0.0, 0.0
             ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 1, 0, 0), 0, 0));


    /* status bar */
    JPanel panel = componentFactory.createPanel(150, 25, true);
    panel.add(_statusBar, BorderLayout.CENTER);
    _centerPanel.add(panel, new GridBagConstraints(0, 2 + centerRows, GridBagConstraints.REMAINDER, 1, 0.0, 0.0
             ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 1, 0, 0), 0, 0));

    setMinRowHeight(gbl, 0, 100);
  }

  public void setMinRowHeight(GridBagLayout gbl, int row, int height) {
    int[] rowHeights = gbl.rowHeights;

    if (rowHeights == null) {
      rowHeights = new int[row + 1];
    } else if (rowHeights.length < row + 1) {
      rowHeights = new int[row + 1];
      System.arraycopy(gbl.rowHeights, 0, rowHeights, 0, gbl.rowHeights.length);
    }
    rowHeights[row] = height;
    gbl.rowHeights = rowHeights;
  }
}
