package goedegep.app.finan.stocksapp;

//Title:      Financien
//Version:
//Copyright:  Copyright (c) 2002
//Author:     Peter Goedegebure
//Company:
//Description:Dialog to add shares to a fund

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;

import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.finan.stocks.Fund;
import goedegep.finan.stocks.Share;

public class FundAddSharesDialog extends StocksHelperDialog {
  private static final long          serialVersionUID = 1L;
  
  private static final String    _windowTitle = "Aandelen aan fondsen toevoegen";
  static String helpText =
      "Kies een fonds en voer de naam in van een nieuw aandeel voor dat fonds.";
  JComboBox<String>   fundBox;            // 1e rij, fonds
  JList<String>       currentSharesList;  // 2e rij, huidige aandelen
  JTextField          newShareName;       // 3e rij, naam nieuw aandeel

  public FundAddSharesDialog(AppFrame parent, Fund selectedFund) {
    super(parent, _windowTitle, helpText, 3);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try  {
      jbInit(selectedFund);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    pack();
  }

  private void jbInit(Fund selectedFund) throws Exception  {
    ComponentFactory componentFactory = getTheComponentFactory();
    JPanel panel;

    /*
     * Center panel
     */

    // 1e rij, label "Bedrijf"
    panel = componentFactory.createLabelPanel("Fonds", SwingConstants.LEFT, 150, 20);
    _centerPanel.add(panel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                    ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 1, 0, 0), 0, 0));

    // 1e rij, fonds
    fundBox = componentFactory.createComboBox(10, "selecteer fonds");
    for (Fund fund: Fund.getFunds()) {
      fundBox.addItem(fund.getName());
    }
    if (selectedFund != null) {
      fundBox.setSelectedItem(selectedFund.getName());
    }
    panel = componentFactory.createPanel(250, 20, false);
    panel.add(fundBox, BorderLayout.CENTER);
    _centerPanel.add(panel, new GridBagConstraints(GridBagConstraints.RELATIVE, 1, 1, 1, 0.0, 0.0
              ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

    // 2e rij, label "Huidige aandelen"
    panel = componentFactory.createLabelPanel("Huidige aandelen", SwingConstants.LEFT, 150, 110);
    _centerPanel.add(panel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                    ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 1, 0, 0), 0, 0));

    // 2e rij, huidige aandelen
    currentSharesList = componentFactory.createList(null);
    currentSharesList.setFocusable(false);
    JScrollPane currentSharesPane = new JScrollPane(currentSharesList);
    updateCurrentSharesList();
    _centerPanel.add(currentSharesPane, new GridBagConstraints(GridBagConstraints.RELATIVE, 2, 1, 1, 0.0, 0.0
                    ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

    // 3e rij, label "Nieuw aandeel"
    panel = componentFactory.createLabelPanel("Nieuw aandeel", SwingConstants.LEFT, 150, 20);
    _centerPanel.add(panel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                    ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 1, 0, 0), 0, 0));

    // 3e rij, naam van nieuw aandeel
    newShareName = componentFactory.createTextField(32, "Typ naam van een nieuw aandeel");
    newShareName.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addNewShare();
      }
    });
    panel = componentFactory.createPanel(-1, -1, false);
    panel.add(newShareName, BorderLayout.CENTER);
    _centerPanel.add(panel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                    ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 1, 0, 0), 0, 0));

    /* buttons panel */

    // "toevoegen" button
    JButton button = componentFactory.createButton("toevoegen", null);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addNewShare();
      }
    });
    panel = componentFactory.createPanel(-1, -1, false);
    panel.add(button, null);
    _buttonsPanel.add(panel, BorderLayout.WEST);

    // "klaar" button
    button = componentFactory.createButton("klaar", null);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancel();
      }
    });
    panel = componentFactory.createPanel(-1, -1, false);
    panel.add(button);
    _buttonsPanel.add(panel, BorderLayout.EAST);

    // Add action listeners.
    fundBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        updateCurrentSharesList();
      }
    });
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

  /* Called when the selected fund has changed, or when a Share has been added.
   * Fills the 'currentSharesList' with the names of the shares of the selected fund.
   */
  public void updateCurrentSharesList() {
    String fundName = (String) fundBox.getSelectedItem();
    Fund fund = Fund.getFund(fundName);
    List<Share> shares = fund.getShares();
    Iterator<Share> iterator = shares.iterator();
    Share share;
    int index = 0;
    String[] names = new String[shares.size()];

    while (iterator.hasNext()) {
      share = iterator.next();
      names[index++] = share.getName();
    }

    currentSharesList.setListData(names);
  }

  /* Add a new share to the selected fund. */
  public void addNewShare() {
    String  fundName = (String) fundBox.getSelectedItem();
    Fund fund = Fund.getFund(fundName);

    String  shareName = newShareName.getText().trim();
    if (shareName.length() == 0) {
      _statusBar.setText("Ongeldige naam voor een aandeel");
    } else {
      Share share = new Share(shareName, fund);
      fund.addShare(share);
      _statusBar.setText("Aandeel " + share.getName() + " toegevoegd aan fonds " + fundName + ".");
      updateCurrentSharesList();
    }
    newShareName.setText("");
  }
}
