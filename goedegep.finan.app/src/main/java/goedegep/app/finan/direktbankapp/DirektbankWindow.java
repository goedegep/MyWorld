package goedegep.app.finan.direktbankapp;

import goedegep.app.finan.gen.BankAccountsOverviewPanel;
import goedegep.appgen.swing.Customization;
import goedegep.appgen.WindowUtil;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.appgen.swing.MenuFactory;
import goedegep.finan.direktbank.Direktbank;
import goedegep.resources.ImageSize;

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
public class DirektbankWindow extends AppFrame {
  private static final String     WINDOW_TITLE = "Direktbank";

//  private GenLook        look = DirektbankLook.getLook();
  private Direktbank     direktbank;
  private ComponentFactory componentFactory;

  //Statusbar
  JLabel statusBar;

  //Construct the frame
  public DirektbankWindow(Customization customization, Direktbank direktbank) {
    super(WINDOW_TITLE, customization, null);
    this.direktbank = direktbank;
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

    mainPanel.add(new BankAccountsOverviewPanel(this, direktbank), BorderLayout.SOUTH);

    // Status bar
    statusBar = componentFactory.createLabel("", SwingConstants.LEFT);
    statusBar.setText("Overzicht Direktbank rekeningen");
    this.getContentPane().add(statusBar, BorderLayout.SOUTH);
  }

  private JMenuBar createMenuBar() {
    JMenuBar    menuBar = new JMenuBar();
    JMenu       menu;

    // Menu bar
    // For each menu: set the text, "handle menu items" and add menu to menu bar.
    // "handle menu items" means: set the text, add an ActionListener and add the
    // item to the menu.

    // Spaarrekeningen
    menu = new JMenu("Spaarrekeningen");

    // Spaarrekeningen: Spaarrekeningen window
    MenuFactory.addMenuItem(menu, "Direktspaarrekening", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        direktspaarrekeningWindow_actionPerformed(e);
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

    // Direktspaarrekening
    tbb = componentFactory.createButton(null, getResources().getApplicationIcon(ImageSize.SIZE_0), "Direktspaarrekening");
    tbb.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
//        BeleggersGiroWindow_actionPerformed(e);
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

  void direktspaarrekeningWindow_actionPerformed(ActionEvent e) {
    statusBar.setText("Spaarrekeningen Window");

    DirektSpRekTransactionsWindow direktspaarrekeningWindow = new DirektSpRekTransactionsWindow(getCustomization(), direktbank.getDirektspaarrekening());
    WindowUtil.showFrameCenteredOnScreen(direktspaarrekeningWindow);

    this.repaint();
  }
}