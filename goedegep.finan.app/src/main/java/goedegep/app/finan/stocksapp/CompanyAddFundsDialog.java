
//Title:      Financien
//Version:
//Copyright:  Copyright (c) 2002
//Author:     Peter Goedegebure
//Company:
//Description:Dialog to add funds to a company

package goedegep.app.finan.stocksapp;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import goedegep.appgen.LookAheadComboBox;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.finan.stocks.Company;
import goedegep.finan.stocks.CompanyPool;
import goedegep.finan.stocks.Fund;

public class CompanyAddFundsDialog extends StocksHelperDialog {
  private static final long          serialVersionUID = 1L;
  
  private static final String   _windowTitle = "Fondsen aan bedrijven toevoegen";
  private static final String   _helpText =
      "Kies een bedrijf en voer de naam in van een nieuw fonds voor dat bedrijf.";
  
  CompanyPool               companyPool;
  LookAheadComboBox<String> companyBox;         // 1e rij, bedrijf
  JList<String>             currentFundsList;   // 2e rij, huidige fondsen
  JTextField                newFundName;        // 3e rij, naam nieuw fonds

  public CompanyAddFundsDialog(AppFrame parent, CompanyPool companyPool, Company selectedCompany) {
    super(parent, _windowTitle, _helpText, 3);
    this.companyPool = companyPool;
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try  {
      jbInit(selectedCompany);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    pack();
  }

  private void jbInit(Company selectedCompany) throws Exception  {
    ComponentFactory componentFactory = getTheComponentFactory();
    JPanel panel;

    /*
     * Center panel
     */

    // 1e rij, label "Bedrijf"
    panel = componentFactory.createLabelPanel("Bedrijf", SwingConstants.LEFT, 150, 20);
    _centerPanel.add(panel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                    ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 1, 0, 0), 0, 0));

    // 1e rij, bedrijf
    List<String> companies = new ArrayList<>();
    for (Company company: companyPool.getCompanies()) {
      companies.add(company.getName());
    }
    companyBox = componentFactory.createLookAheadComboBox(companies, false, 10, 20, "selecteer bedrijf");
    if (selectedCompany != null) {
      companyBox.setSelectedItem(selectedCompany.getName());
    }
    panel = componentFactory.createPanel(250, 20, false);
    panel.add(companyBox, BorderLayout.CENTER);
    _centerPanel.add(panel, new GridBagConstraints(GridBagConstraints.RELATIVE, 1, 1, 1, 0.0, 0.0
              ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

    // 2e rij, label "Huidige fondsen"
    panel = componentFactory.createLabelPanel("Huidige fondsen", SwingConstants.LEFT, 150, 110);
    _centerPanel.add(panel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                    ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 1, 0, 0), 0, 0));

    // 2e rij, huidige fondsen
    currentFundsList = componentFactory.createList(null);
    currentFundsList.setFocusable(false);
    JScrollPane currentFundsPane = new JScrollPane(currentFundsList);
    updateCurrentFundsList();
    _centerPanel.add(currentFundsPane, new GridBagConstraints(GridBagConstraints.RELATIVE, 2, 1, 1, 0.0, 0.0
                    ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

    // 3e rij, label "Nieuw fonds"
    panel = componentFactory.createLabelPanel("Nieuw fonds", SwingConstants.LEFT, 150, 20);
    _centerPanel.add(panel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                    ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 1, 0, 0), 0, 0));

    // 3e rij, naam van nieuw fonds
    newFundName = componentFactory.createTextField(32, "Typ naam van een nieuw fonds");
    newFundName.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addNewFund();
      }
    });
    panel = componentFactory.createPanel(-1, -1, false);
    panel.add(newFundName, BorderLayout.CENTER);
    _centerPanel.add(panel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                    ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 1, 0, 0), 0, 0));

    /* buttons panel */

    // "toevoegen" button
    JButton button = componentFactory.createButton("toevoegen", null);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addNewFund();
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
    companyBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        updateCurrentFundsList();
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

  /* Called when the selected company has changed, or when a Fund has been added.
   * Fills the 'currentFundsList' with the names of the funds of the selected company.
   */
  public void updateCurrentFundsList() {
    String companyName = (String) companyBox.getSelectedItem();
    Company company = companyPool.getCompany(companyName);
    List<Fund> funds = company.getFunds();
    Iterator<Fund> iterator = funds.iterator();
    Fund fund;
    int index = 0;
    String[] names = new String[funds.size()];

    while (iterator.hasNext()) {
      fund = iterator.next();
      names[index++] = fund.getName();
    }

    currentFundsList.setListData(names);
  }

  /* Add a new fund to the selected company. */
  public void addNewFund() {
    String  companyName = (String) companyBox.getSelectedItem();
    Company company = companyPool.getCompany(companyName);

    String  fundName = newFundName.getText().trim();
    if (fundName.length() == 0) {
      _statusBar.setText("Ongeldige fondsnaam");
    } else {
      Fund fund = new Fund(fundName, company);
      company.addFund(fund);
      _statusBar.setText("Fonds " + fund.getName() + " toegevoegd aan bedrijf " + companyName + ".");
      updateCurrentFundsList();
    }
    newFundName.setText("");
  }
}