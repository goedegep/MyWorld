package goedegep.app.finan.effrek;

import goedegep.app.finan.gen.FinanBank;
import goedegep.app.finan.gen.TransactionsWindow;
import goedegep.appgen.swing.Customization;
import goedegep.appgen.WindowUtil;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.MenuFactory;
import goedegep.finan.effrek.EffRek;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.StockDepot;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * This class provides an "Effectenrekening" window.
 * <p>
 * This window consist of the following parts:
 * <ul>
 * <li>
 * Status information</br>
 * List general numbers like balance, profit, investment, etc.
 * </li>
 * <li>
 * A table with the current share positions
 * </li>
 * <li>
 * A table with the current option positions
 * </li>
 * <li>
 * A table with the current stock dividends
 * </li>
 * </ul>
 * 
 */
@SuppressWarnings("serial")
public abstract class EffRekWindow extends AppFrame {
  private static final String     TRANSACTIONS_WINDOW_TITLE = "Effectenrekening Transacties";  // Used when creating a TransactionsWindow.
  
  private FinanBank         finanBank;
  private EffRek            effRek;

  private LocalDate         statusDate = null; // Date for which rates are shown. If null, today.
  private StockDepot        depot = null;

  private EffRekStatusPanel statusPanel = null;

  // Statusbar
  private JLabel statusBar = new JLabel();

  // Layout: mainLayout is BorderLayout
  //   Center is centerPanel is centerLayout is BorderLayout
  //     North is statusPanel is statusLayout is GridBagLayout
  //   South is statusBar
  private BorderLayout centerLayout = new BorderLayout();
  private JPanel centerPanel = new JPanel();
  private JPanel statusBarPanel = new JPanel();
  
  public EffRekWindow(String title, Customization customization, Dimension size, FinanBank finanBank, EffRek effRek) {
    this(title, customization, size, finanBank, effRek, LocalDate.now());    
  }
  
  public EffRekWindow(String title, Customization customization, Dimension size, FinanBank finanBank, EffRek effRek, LocalDate statusDate) {
    super(title, customization, size);
    
    this.finanBank = finanBank;
    this.effRek = effRek;
    this.statusDate = statusDate;
    
    try {
      init();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  public FinanBank getFinanBank() {
    return finanBank;
  }  
  
  public EffRek getEffRek() {
    return effRek;
  }

  private void init() throws Exception  {

    setJMenuBar(createMenuBar());              // set Menu
    statusPanel = new EffRekStatusPanel(this, effRek, statusDate);

    // Main area
//    getContentPane().setBackground(getLook().getBackgroundColor());

    // Center panel
    centerPanel.setLayout(centerLayout);
//    centerPanel.setBackground(getLook().getBackgroundColor());
    centerPanel.add(statusPanel, BorderLayout.NORTH);

    depot = effRek.getVerzamelDepot();

    JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new OptionPositionsTable(this, depot), new StockDividendsOwnedTable(this, depot));
    splitPane1.setDividerLocation(80);
    JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new SharesOwnedTable(this, depot, statusDate), splitPane1);
    splitPane2.setDividerLocation(200);
    centerPanel.add(splitPane2, BorderLayout.CENTER);

    getContentPane().add(centerPanel, BorderLayout.CENTER);
    getContentPane().add(statusBarPanel, BorderLayout.SOUTH);


    // Status area
    statusPanel.update();

    // Status bar
    statusBar.setText("- Status -");                             // clear status bar text
    statusBarPanel.add(statusBar, null);

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

    // Transacties: Alle
    MenuFactory.addMenuItem(menu, "Alle", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        transactiesAlle_actionPerformed(e);
      }
    });

    // Transacties: Per aandeel
    MenuFactory.addMenuItem(menu, "Per aandeel", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        transactiesPerAandeel_actionPerformed(e);
      }
    });

    menuBar.add(menu);

    // Menu: Modellen
    menu = new JMenu("Modellen");

    // Modellen: V Model
    MenuFactory.addMenuItem(menu, "V Model", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        modellenVModel_actionPerformed(e);
      }
    });

    // Modellen: Test Window
    MenuFactory.addMenuItem(menu, "Test Window", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        testWindow_actionPerformed(e);
      }
    });

    menuBar.add(menu);

    // Menu: Overzichten
    menu = new JMenu("Overzichten");

    // Overzichten: Maandoverzicht
    MenuFactory.addMenuItem(menu, "Maandoverzicht", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        maandInformatie_actionPerformed(e);
      }
    });

    // Overzichten: Kwartaaloverzicht
    MenuFactory.addMenuItem(menu, "Kwartaaloverzicht", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        kwartaalInformatie_actionPerformed(e);
      }
    });

    // Overzichten: Belasting informatie
    MenuFactory.addMenuItem(menu, "Belasting informatie", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        belastingInformatie_actionPerformed(e);
      }
    });

    menuBar.add(menu);
    
    return menuBar;
  }

  // Modellen | V Model action performed
  private void modellenVModel_actionPerformed(ActionEvent e) {
    showVModelWindow(null);
  }

  // Transacties | Test Window
  protected abstract void testWindow_actionPerformed(ActionEvent e);
  
  private void maandInformatie_actionPerformed(ActionEvent e) {
    showMaandoverzichtWindow();
  }

  private void showMaandoverzichtWindow() {
    boolean packFrame = false;
    EffRekMaandoverzichtWindow  window = new EffRekMaandoverzichtWindow(getCustomization(), finanBank, depot);

    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame)
      window.pack();
    else
      window.validate();
    
    WindowUtil.showFrameCenteredOnScreen(window, 22, 22);
  }
  
  private void kwartaalInformatie_actionPerformed(ActionEvent e) {
    showKwartaaloverzichtWindow();
  }

  private void showKwartaaloverzichtWindow() {
    boolean packFrame = false;
    EffRekKwartaaloverzichtWindow  window = new EffRekKwartaaloverzichtWindow(getCustomization(), finanBank, depot);

    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame)
      window.pack();
    else
      window.validate();
    
    WindowUtil.showFrameCenteredOnScreen(window, 22, 22);
  }

  private void belastingInformatie_actionPerformed(ActionEvent e) {
    showFiscaalJaaroverzichtWindow();
  }
  
  private void showFiscaalJaaroverzichtWindow() {
    boolean                 packFrame = false;
    EffRekFiscaalJaaroverzichtWindow  window = new EffRekFiscaalJaaroverzichtWindow(getCustomization(), finanBank, depot);

    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame)
      window.pack();
    else
      window.validate();
    WindowUtil.showFrameCenteredOnScreen(window, 22, 22);
  }

  public void showVModelWindow(Share share) {
    String title = finanBank.getBank().getName() + " " +
                   effRek.getName() + " Effectendepot V Model";
    JFrame effRekVModelWindow =
      new EffRekVModelWindow(title, getCustomization(), effRek.getVerzamelDepot(), share);
    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    effRekVModelWindow.validate();
    WindowUtil.showFrameCenteredOnScreen(effRekVModelWindow, 22, 22);
  }
  
  // Transacties | Alle action performed
  protected void transactiesAlle_actionPerformed(ActionEvent e) {
    boolean packFrame = false;
    String windowTitle = finanBank.getBank().getName() + " " + TRANSACTIONS_WINDOW_TITLE;
    
    TransactionsWindow w1 = new TransactionsWindow(windowTitle, getCustomization(),
        new Dimension(1300, 600), effRek);
    WindowUtil.showFrameCenteredOnScreen(w1, 22, 22);
    if (packFrame)
      w1.pack();
    else
      w1.validate();
  }

  // Transacties | Per aandeel action performed
  protected void transactiesPerAandeel_actionPerformed(ActionEvent e) {
    boolean                     packFrame = false;
    String windowTitle = finanBank.getBank().getName() + " " + TRANSACTIONS_WINDOW_TITLE;
    
    TransactionsPerShareWindow w1 = new TransactionsPerShareWindow(windowTitle, getCustomization(),
        new Dimension(1300, 600), effRek);
    WindowUtil.showFrameCenteredOnScreen(w1, 22, 22);
    if (packFrame)
      w1.pack();
    else
      w1.validate();
  }
}
