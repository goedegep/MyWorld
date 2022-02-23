
//Title:      Financien
//Version:
//Copyright:  Copyright (c) 2002
//Author:     Peter Goedegebure
//Company:
//Description:Dialog to add companies

package goedegep.app.finan.stocksapp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.finan.stocks.Company;
import goedegep.finan.stocks.CompanyPool;

public class CompanyAddCompaniesDialog extends StocksHelperDialog {
  private static final long          serialVersionUID = 1L;
  
  private static final String      _windowTitle = "Bedrijven toevoegen";
  private static final String      _helpText =
      "Vul de gegevens van het bedrijf in en druk op \"toevoegen\" of ENTER om het bedrijf " +
      "toe te voegen aan de lijst van bedrijven.";

  CompanyPool companyPool;
  JTextField  companyName;

  public CompanyAddCompaniesDialog(AppFrame parent, CompanyPool companyPool) {
    super(parent, _windowTitle, _helpText, 1);
    this.companyPool = companyPool;
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    pack();
  }

  private void jbInit() throws Exception  {
    setResizable(true);
    ComponentFactory componentFactory = getTheComponentFactory();

    JPanel panel;

    /*
     * Center panel
     */

    // 1e rij, label "Naam"
    panel = componentFactory.createLabelPanel("Naam:", SwingConstants.LEFT, -1, -1);
    _centerPanel.add(panel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 1, 0, 0), 0, 0));

    // 1e rij, naam van het nieuwe bedrijf.
    companyName = componentFactory.createTextField(32, "Typ naam van een nieuw bedrijf");
    companyName.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addCompany();
      }
    });
    panel = componentFactory.createPanel(250, 20, false);
    panel.add(companyName, BorderLayout.CENTER);
    _centerPanel.add(panel, new GridBagConstraints(GridBagConstraints.RELATIVE, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 1, 0, 0), 0, 0));

    /* buttons panel */

    // "toevoegen" button
    JButton button = componentFactory.createButton("toevoegen", null);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addCompany();
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
    panel.add(button, null);
    _buttonsPanel.add(panel, BorderLayout.EAST);
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

  public void addCompany() {
    String name = companyName.getText().trim();
    if (name.length() == 0)
    {
      _statusBar.setText("Ongeldige bedrijfsnaam");
    } else {
      Company newCompany = new Company(name);
      companyPool.addCompany(newCompany);
      _statusBar.setText("Bedrijf " + newCompany.getName() + " toegevoegd.");
    }
    companyName.setText("");
  }
}