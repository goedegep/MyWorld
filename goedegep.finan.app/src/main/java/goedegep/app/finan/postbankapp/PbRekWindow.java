
//Title:      PostbankWindow
//Version:
//Copyright:  Copyright (c) 2001
//Author:     Peter Goedegebure
//Company:    Solvation
//Description:Postbank main window

package goedegep.app.finan.postbankapp;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import goedegep.app.finan.gen.BankAccountsOverviewPanel;
import goedegep.app.finan.gen.FinanBank;
import goedegep.appgen.swing.Customization;
import goedegep.appgen.ImageSize;
import goedegep.appgen.WindowUtil;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.appgen.swing.MenuFactory;
import goedegep.finan.postbank.pbrek.PbRek;
import goedegep.finan.postbank.pbsprek.PbSpRek;

@SuppressWarnings("serial")
public class PbRekWindow extends AppFrame {
  private final static Logger     LOGGER = Logger.getLogger(PbRekWindow.class.getName()); 
  private final static String     WINDOW_TITLE = "Postbank";

//  private GenLook        look = PbLook.getLook();
  private FinanBank      finanBank;
  private PbRek          pbRekening;
  private ComponentFactory componentFactory;

  // Statusbar
  JLabel statusBar;

  //Construct the frame
  public PbRekWindow(Customization customization, FinanBank finanBank) {
    super(WINDOW_TITLE, customization, null);
    
    LOGGER.severe("=> ");
    
    this.finanBank = finanBank;
    pbRekening = (PbRek) finanBank.getBank();
    LOGGER.severe("Number of Quarterly Reports: " + pbRekening.getEffectenRekening(false).getVerzamelDepot().getQuarterReports().size());
    componentFactory = getTheComponentFactory();

    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      init();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    
    LOGGER.severe("<=");
  }

  //Component initialization
  private void init() throws Exception  {
    this.setSize(new Dimension(900, 424));              // set window size

    setJMenuBar(createMenuBar());              // set Menu

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(createToolBar(), BorderLayout.NORTH);


    // main area
    JPanel mainPanel = componentFactory.createPanel(-1, -1, false);
    mainPanel.setLayout(new BorderLayout());
    getContentPane().add(mainPanel, BorderLayout.CENTER);

    mainPanel.add(new BankAccountsOverviewPanel(this, pbRekening), BorderLayout.SOUTH);

    // Status bar
    statusBar = componentFactory.createLabel("", SwingConstants.LEFT);
    statusBar.setText("Overzicht Postbank rekeningen");              // clear status bar text
    this.getContentPane().add(statusBar, BorderLayout.SOUTH);
  }

  private JMenuBar createMenuBar() {
    JMenuBar    menuBar = new JMenuBar();
    JMenu       menu;

    // Menu bar
    // For each menu: set the text, "handle menu items" and add menu to menu bar.
    // "handle menu items" means: set the text, add an ActionListener and add the
    // item to the menu.

    // Menu:Algemeen
    menu = new JMenu("Algemeen");

    // Algemeen: Venster sluiten
    MenuFactory.addMenuItem(menu, "Venster Sluiten", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        vensterSluiten_actionPerformed(e);
      }
    });

    menuBar.add(menu);

    // Spaarrekeningen
    menu = new JMenu("Spaarrekeningen");

    // Spaarrekeningen: Spaarrekeningen window
    MenuFactory.addMenuItem(menu, "Spaarrekeningen window", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        SpaarrekeningenWindow_actionPerformed(e);
      }
    });
    
    // Spaarrekeningen: <alle bestaande spaarrekeningen>
    for (final PbSpRek spRek: pbRekening.getOpenSpaarRekeningen()) {
      MenuFactory.addMenuItem(menu, spRek.getName(), new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          transactiesPerSpaarrekening_actionPerformed(e, spRek);
        }
      });
    }
    menuBar.add(menu);

    // Menu:Beleggingsfondsen
    menu = new JMenu("Beleggingsfondsen");

    // Beleggingsfondsen: BeleggersGiro window
    MenuFactory.addMenuItem(menu, "BeleggersGiro window", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        BeleggersGiroWindow_actionPerformed(e);
      }
    });

    // Beleggingsfondsen - Aandelenfonds koersinfo
    MenuFactory.addMenuItem(menu, "Aandelenfonds koersinfo", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        menuAandFondsKoersInfo_actionPerformed(e);
      }
    });

    menuBar.add(menu);

    // Menu:Effectenrekening
    menu = new JMenu("Effecten");

    // Effectenrekening - Effectenrekening window
    MenuFactory.addMenuItem(menu, "Effectenrekening window", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        EffectenrekeningWindow_actionPerformed(e);
      }
    });

    menuBar.add(menu);

    return menuBar;
  }  

  /**
   * Create the toolbar for this screen.
   * 
   * @return
   *     The toolbar.
   */
  private JToolBar createToolBar() {
    /* For each toolbar button: create icon from file, install icon on the button,
     * set the ToolTipText and add the button to the toolbar.
     */
    JToolBar    toolBar = new JToolBar();
    JButton     tbb;

    // Effecten Rekening
    tbb = componentFactory.createButton(null, PbResources.getPostbankEffRekIcon(), "Effecten Rekening");
    tbb.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        EffectenrekeningWindow_actionPerformed(e);
      }
    });
    toolBar.add(tbb);

    // BeleggersGiro
    tbb = componentFactory.createButton(null, getResources().getApplicationIcon(ImageSize.SIZE_0), "BeleggersGiro");
    tbb.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        BeleggersGiroWindow_actionPerformed(e);
      }
    });
    toolBar.add(tbb);
    
    return toolBar;
  }

  //File | Exit action performed
  public void vensterSluiten_actionPerformed(ActionEvent e) {
    dispose();
  }

  //Overridden so we can exit on System Close
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if(e.getID() == WindowEvent.WINDOW_CLOSING) {
      vensterSluiten_actionPerformed(null);
    }
  }

  void menuAandFondsKoersInfo_actionPerformed(ActionEvent e) {
    // set the string into the JTextArea.
    //MainTextWindow.setText(pbAandelenFondsInfo.getOpeningRatesAsString()); TODO C Hier iets doen
    // Display the name of the opened directory+file in the statusBar.
    statusBar.setText("Postbank aandelenfonds");
    //repaints menu after item is selected
    this.repaint();
  }

  void SpaarrekeningenWindow_actionPerformed(ActionEvent e) {
    statusBar.setText("Spaarrekeningen Window");

    PbSpRekWindow spaarrekeningenWindow = new PbSpRekWindow(getCustomization(), pbRekening.getOpenSpaarRekeningen());
    WindowUtil.showFrameCenteredOnScreen(spaarrekeningenWindow);

    this.repaint();
  }

  // Spaarrekeningen | <spaarrekening> action performed
  void transactiesPerSpaarrekening_actionPerformed(ActionEvent e, PbSpRek account) {
    PbSpRekTransactionsWindow   window = new PbSpRekTransactionsWindow(getCustomization(), "Postbank " + account.getName(), account);
    WindowUtil.showFrameCenteredOnScreen(window, 22, 0);
  }

  void BeleggersGiroWindow_actionPerformed(ActionEvent e) {
    statusBar.setText("BeleggersGiro Window");

    PbBelGiroWindow beleggersGiroWindow = new PbBelGiroWindow(getCustomization(), pbRekening.getFondsen());
    WindowUtil.showFrameCenteredOnScreen(beleggersGiroWindow, 44, 44);
  }

  void EffectenrekeningWindow_actionPerformed(ActionEvent e) {
    statusBar.setText("Effectenrekening Window");

    PbEffRekWindow effectenrekeningWindow = new PbEffRekWindow(getCustomization(), finanBank, pbRekening.getEffectenRekening(true));
    WindowUtil.showFrameCenteredOnScreen(effectenrekeningWindow, 44, 44);
  }
}