package goedegep.app.finan.abnamrobank;

import goedegep.app.finan.gen.BankAccountsOverviewPanel;
import goedegep.app.finan.gen.FinanBank;
import goedegep.appgen.swing.Customization;
import goedegep.appgen.ImageSize;
import goedegep.appgen.WindowUtil;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.appgen.swing.MenuFactory;
import goedegep.finan.abnamrobank.AbnAmroBank;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class AbnAmroBankWindow extends AppFrame {
  private static final String     WINDOW_TITLE = "ABN AMRO Bank";

  private ComponentFactory componentFactory;
  private FinanBank      finanBank;
  private AbnAmroBank    abnAmroBank;

  //Statusbar
  private JLabel statusBar;

  //Construct the frame
  public AbnAmroBankWindow(Customization customization, FinanBank finanBank) {
    super(WINDOW_TITLE, customization, null);
    this.finanBank = finanBank;
    abnAmroBank = (AbnAmroBank) finanBank.getBank();
    componentFactory = getTheComponentFactory();

    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      init();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  //Component initialization
  private void init() throws Exception  {
    this.setSize(new Dimension(900, 424));

    setJMenuBar(createMenuBar());       // set Menu

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(createToolBar(), BorderLayout.NORTH);

    
    // main area
    JPanel mainPanel = componentFactory.createPanel(-1, -1, false);
    mainPanel.setLayout(new BorderLayout());
    getContentPane().add(mainPanel, BorderLayout.CENTER);

    mainPanel.add(new BankAccountsOverviewPanel(this, abnAmroBank), BorderLayout.SOUTH);

    // Status bar
    statusBar = componentFactory.createLabel("", SwingConstants.LEFT);
    statusBar.setText("Overzicht ABN AMRO Bank rekeningen");
    this.getContentPane().add(statusBar, BorderLayout.SOUTH);
  }

  private JMenuBar createMenuBar() {
    JMenuBar    menuBar = new JMenuBar();
    JMenu       menu;

    // Menu bar
    // For each menu: set the text, "handle menu items" and add menu to menu bar.
    // "handle menu items" means: set the text, add an ActionListener and add the
    // item to the menu.

    // Rekeningen
    menu = new JMenu("Rekeningen");

    // Rekeningen: Effectenrekening window
    MenuFactory.addMenuItem(menu, "Effectenrekening window", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        effectenrekeningWindow_actionPerformed(e);
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

    // Effectenrekening
    tbb = componentFactory.createButton(null, getResources().getApplicationIcon(ImageSize.SIZE_0), "Effectenrekening");
    tbb.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        effectenrekeningWindow_actionPerformed(e);
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

  void effectenrekeningWindow_actionPerformed(ActionEvent e) {
    statusBar.setText("Effectenrekening Window");

    AAEffRekWindow lynxEffRekWindow = new AAEffRekWindow(getCustomization(), finanBank, abnAmroBank.getEffectenrekening());
    WindowUtil.showFrameCenteredOnScreen(lynxEffRekWindow);

    this.repaint();
  }
}