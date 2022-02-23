//Title:      PbBelGiroWindow
//Version:
//Copyright:  Copyright (c) 2001
//Author:     Peter Goedegebure
//Company:    Solvation
//Description:Postbank BeleggersGiro window

package goedegep.app.finan.postbankapp;

import goedegep.appgen.swing.Customization;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.MenuFactory;
import goedegep.finan.postbank.pbfonds.PbFonds;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrencyFormat;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PbBelGiroWindow extends AppFrame {
  private static final Logger LOGGER = Logger.getLogger(PbBelGiroWindow.class.getName());
  
  private static final String          _windowTitle = "Postbank BeleggersGiro";
  List<PbFonds>      _fondsen;

  // Layout: borderLayout
  BorderLayout borderLayout = new BorderLayout();

  // Items in main area
  JPanel        mainPanel = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel      labelFondsnaam          = new JLabel();     // "Fondsnaam" label (1e kolom)
  JLabel      labelAantal             = new JLabel();     // "Aantal" label (2e kolom)
  JLabel      labelKoers              = new JLabel();     // "Koers" label (3e kolom)
  JLabel      labelWaarde             = new JLabel();     // "Waarde" label (4e kolom)
  JLabel      labelInvestering        = new JLabel();     // "Investering" label (5e kolom)

  // Statusbar
  JPanel statusbarPanel = new JPanel();
  JLabel statusBar = new JLabel();


  //Construct the frame
  public PbBelGiroWindow(Customization customization, List<PbFonds> fondsen) {
    super(_windowTitle, customization, null);
    _fondsen = fondsen;

    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  //Component initialization
  private void jbInit() throws Exception  {
    // General window initialization
    this.setSize(new Dimension(700, 409));              // set window size

    this.setJMenuBar(createMenuBar());


    // Main area
    this.getContentPane().setLayout(borderLayout);      // install the borderLayout

    // main part of main area
    PgCurrencyFormat  cf = new PgCurrencyFormat();

    mainPanel.setLayout(gridBagLayout1);
    labelFondsnaam.setText("Fondsnaam");
    labelAantal.setText("Aantal");
    labelKoers.setText("Koers");
    labelWaarde.setText("Waarde");
    labelInvestering.setText("Investering");

    // Status bar
    statusBar.setText(" ");                             // clear status bar text

    this.getContentPane().add(statusbarPanel, BorderLayout.SOUTH);
    statusbarPanel.add(statusBar, null);
    this.getContentPane().add(mainPanel, BorderLayout.CENTER);

    // First line: labels
    mainPanel.add(labelFondsnaam, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
    mainPanel.add(labelAantal, new GridBagConstraints(GridBagConstraints.RELATIVE, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    mainPanel.add(labelKoers, new GridBagConstraints(GridBagConstraints.RELATIVE, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
    mainPanel.add(labelWaarde, new GridBagConstraints(GridBagConstraints.RELATIVE, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    mainPanel.add(labelInvestering, new GridBagConstraints(GridBagConstraints.RELATIVE, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

    NumberFormat  nf = DecimalFormat.getInstance();
    FixedPointValueFormat fpvf = new FixedPointValueFormat();
    nf.setMinimumFractionDigits(4);
    nf.setMaximumFractionDigits(4);
    JTextField    textFieldFondsnaam;
    JTextField    textFieldAantal;
    JTextField    textFieldKoers;
    JTextField    textFieldWaarde;
    JTextField    textFieldInvestering;
    short         line = 1;

    for (PbFonds fonds: _fondsen) {
      LOGGER.severe("fonds: " + fonds.getName());
      
      textFieldFondsnaam = new JTextField(fonds.getName());
      //textFieldFondsnaam.setText(fonds.getName());
      mainPanel.add(textFieldFondsnaam, new GridBagConstraints(0, line, 1, 1, 0.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));

      textFieldAantal = new JTextField(fpvf.format(fonds.getNumberOfShares()));
      // textFieldAantal.setText(nf.format((double) _fondsen[index].getNumberOfShares()/10000));
      //textFieldAantal.setText(nf.format((double) 987654/10000));
      mainPanel.add(textFieldAantal, new GridBagConstraints(GridBagConstraints.RELATIVE, line, 1, 1, 0.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));

//      DateRateTuplet bestRateTuplet = fonds.getBestRate();
      textFieldKoers = new JTextField(cf.format(fonds.getBestRate().getRate()));
      //textFieldKoers.setText("----");
      mainPanel.add(textFieldKoers, new GridBagConstraints(GridBagConstraints.RELATIVE, line, 1, 1, 0.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

      textFieldWaarde = new JTextField(cf.format(fonds.getEstimatedValue()));
      //textFieldWaarde.setText("----");
      mainPanel.add(textFieldWaarde, new GridBagConstraints(GridBagConstraints.RELATIVE, line, 1, 1, 0.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));

      textFieldInvestering = new JTextField(cf.format(fonds.getInvestment()));
      mainPanel.add(textFieldInvestering, new GridBagConstraints(GridBagConstraints.RELATIVE, line, 1, 1, 0.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));


      line++;
    }

    if (_fondsen.size() == 0) {
      statusBar.setText("Er zijn geen beleggingsfondsen");
    }
  }
  
  private JMenuBar createMenuBar() {
    JMenuBar    menuBar = new JMenuBar();
    JMenu       menu;

    // Menu bar
    // For each menu: set the text, "handle menu items" and add menu to menu bar.
    // "handle menu items" means: set the text, add an ActionListener and add the
    // item to the menu.

    // Menu: Transacties
    menu = new JMenu("Transacties");

    // Voeg voor ieder bestaand fonds een menu item toe.
    for (final PbFonds fonds: _fondsen) {
      MenuFactory.addMenuItem(menu, fonds.getName(), new ActionListener()  {
        public void actionPerformed(ActionEvent e) {
          transactiesPerFonds_actionPerformed(e, fonds);
        }
      });
    }

    menuBar.add(menu);

    return menuBar;
  }
  
  //File | Exit action performed
  public void fileExit_actionPerformed(ActionEvent e) {
    // System.exit(0);
  }

  // Transacties | Per aandeel action performed
  void transactiesPerFonds_actionPerformed(ActionEvent e, PbFonds fonds) {
    boolean                     packFrame = false;
    PbFondsTransactionsWindow   window = new PbFondsTransactionsWindow(getCustomization(), "Postbank " + fonds.getName(), fonds);

    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame)
      window.pack();
    else
      window.validate();
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = window.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    window.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2 + 22);
    window.setVisible(true);
  }

  //Overridden so we can exit on System Close
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if(e.getID() == WindowEvent.WINDOW_CLOSING) {
      fileExit_actionPerformed(null);
    }
  }
}