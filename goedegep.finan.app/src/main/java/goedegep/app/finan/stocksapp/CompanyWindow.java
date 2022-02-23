//Title:       CompanyWindow
//Version:
//Copyright:   Copyright (c) 2002
//Author:      Peter Goedegebure
//Description: User Interface for companies
// TODO B Functionaliteit toevoegen om claim emissies te tonen en toe te voegen.
package goedegep.app.finan.stocksapp;

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import javax.swing.*;

import goedegep.appgen.swing.Customization;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.appgen.swing.MenuFactory;
import goedegep.finan.stocks.CompaniesContentHandler;
import goedegep.finan.stocks.Company;
import goedegep.finan.stocks.CompaniesListener;
import goedegep.finan.stocks.CompanyFundsContentHandler;
import goedegep.finan.stocks.CompanyPool;
import goedegep.finan.stocks.Fund;
import goedegep.finan.stocks.FundListener;
import goedegep.finan.stocks.FundSharesContentHandler;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.ShareDividend;
import goedegep.finan.stocks.ShareDividendsContentHandler;
import goedegep.finan.stocks.ShareListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class CompanyWindow extends AppFrame implements CompaniesListener, FundListener, ShareListener {
  private static final long          serialVersionUID = 1L;
  
  private static final String    WINDOW_TITLE = "Bedrijven";
  private static final String[] EMPTY_LIST = {"<geen>"};
  private static final String[] NO_LIST = {};

  private JLabel statusBar;
  
  private CompanyPool   companyPool;
  
  private JList<String> companiesList;
  private JList<String> companyFundsList;
  private JList<String> fundSharesList;
  private JList<String> dividendsList;

  private Company       selectedCompany = null;
  private Fund          selectedFund = null;
  private Share         selectedShare = null;

  //Construct the frame
  public CompanyWindow(Customization customization, CompanyPool companyPool) {
    super(WINDOW_TITLE, customization, null);
    this.companyPool = companyPool;
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      init();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    pack();
  }

  //Component initialization
  private void init() throws Exception  {
    this.setSize(new Dimension(400, 300));     // set window size
    ComponentFactory componentFactory = getTheComponentFactory();

    setJMenuBar(createMenuBar());              // set Menu


    // Main window layout:
    //  mainLayout is a BorderLayout:
    //    north is toolbar
    //    center is centerPanel with GridBagLayout,
    //    south is statusBar.

    // Main area layout
    Color contentPaneColor = new Color(100, 100, 0);
    getContentPane().setBackground(contentPaneColor);

    //BorderLayout  mainLayout = new BorderLayout();
    getContentPane().setLayout(new BorderLayout());      // install the borderLayout

    // main part of main area
    JPanel        centerPanel = new JPanel();
    //GridBagLayout centerLayout = new GridBagLayout();

    centerPanel.setBackground(contentPaneColor);
    centerPanel.setLayout(new GridBagLayout());
    getContentPane().add(centerPanel, BorderLayout.CENTER);

    int nameWidth = 220;

    companiesList = componentFactory.createList("selecteer een bedrijfnaam");
    JScrollPane companiesPane = componentFactory.createListPane(companiesList, 170, 300);

    companyFundsList = componentFactory.createList("selecteer een fonds");
    JScrollPane companyFundsPane = componentFactory.createListPane(companyFundsList, 170, 300);

    fundSharesList = componentFactory.createList("selecteer een aandeel");
    JScrollPane fundSharesPane = componentFactory.createListPane(fundSharesList, 170, 300);

    dividendsList = componentFactory.createList("selecteer een dividend");
    JScrollPane dividendsPane = componentFactory.createListPane(dividendsList, 170, 300);

    centerPanel.add(componentFactory.createLabelPanel("Bedrijfsnaam", SwingConstants.CENTER, nameWidth, 20), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(0, 1, 0, 0), 0, 0));
    centerPanel.add(componentFactory.createLabelPanel("Fondsen", SwingConstants.CENTER, 170, 20), new GridBagConstraints(GridBagConstraints.RELATIVE, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
    centerPanel.add(componentFactory.createLabelPanel("Aandelen", SwingConstants.CENTER, 170, 20), new GridBagConstraints(GridBagConstraints.RELATIVE, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
    centerPanel.add(componentFactory.createLabelPanel("Dividenden", SwingConstants.CENTER, 170, 20), new GridBagConstraints(GridBagConstraints.RELATIVE, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
    centerPanel.add(companiesPane, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 43, 0));
    centerPanel.add(companyFundsPane, new GridBagConstraints(GridBagConstraints.RELATIVE, 1, 1, 10, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    centerPanel.add(fundSharesPane, new GridBagConstraints(GridBagConstraints.RELATIVE, 1, 1, 10, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    centerPanel.add(dividendsPane, new GridBagConstraints(GridBagConstraints.RELATIVE, 1, 1, 10, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

    // status bar (bottom)
    statusBar = new JLabel();
    statusBar.setBorder(BorderFactory.createEtchedBorder());
    getContentPane().add(statusBar, BorderLayout.SOUTH);

    companiesList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        //System.out.println("companiesList->valueChanged");
        selectedCompanyChanged();
      }
    });

    companyFundsList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        selectedFundChanged();
      }
    });

    fundSharesList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        selectedShareChanged();
      }
    });

    Company.addCompaniesListener(this);
    Fund.addFundListener(this);
    Share.addShareListener(this);

    companiesChanged();

    // Status bar
    statusBar.setText("Totaal aantal bedrijven: " + companyPool.getNumberOfCompanies());                             // clear status bar text
  }


  private JMenuBar createMenuBar() {
    JMenuBar    menuBar = new JMenuBar();
    JMenu       menu;
    
    // Menu bar
    // For each menu: set the text, "handle menu items" and add menu to menu bar.
    // "handle menu items" means: set the text, add an ActionListener and add the
    // item to the menu.

    // Menu: Bestand
    menu = new JMenu("Bestand");
    
    // Bestand: Bedrijven informatie opslaan
    MenuFactory.addMenuItem(menu, "Bedrijven informatie opslaan", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        bedrijvenInformatieOpslaan_actionPerformed(e);
      }
    });

    menuBar.add(menu);

    
    // Menu:Bedrijven
    menu = new JMenu("Bedrijven");

    // Bedrijven: Bedrijven toevoegen
    MenuFactory.addMenuItem(menu, "Bedrijven toevoegen", new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        companiesAdd_actionPerformed(e);
      }
    });

    // Bedrijven: Fondsen aan bedrijven toevoegen
    MenuFactory.addMenuItem(menu, "Fondsen aan bedrijven toevoegen", new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        companyAddFunds_actionPerformed(e);
      }
    });

    menuBar.add(menu);

    // Menu: Fondsen
    menu = new JMenu("Fondsen");

    // Fondsen: Aandelen aan fondsen toevoegen
    MenuFactory.addMenuItem(menu, "Aandelen aan fondsen toevoegen", new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fundAddShares_actionPerformed(e);
      }
    });

    menuBar.add(menu);

    // Menu:Aandelen
    menu = new JMenu("Aandelen");

    // Aandelen: Dividenden aan aandelen toevoegen
    MenuFactory.addMenuItem(menu, "Dividenden aan aandelen toevoegen", new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        shareAddDividends_actionPerformed(e);
      }
    });

    // Aandelen: Koersinformatie
    MenuFactory.addMenuItem(menu, "Koersinformatie", new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        koersInformatie_actionPerformed(e);
      }
    });

    menuBar.add(menu);
    
    // Menu:Opties
    menu = new JMenu("Opties");
    
    // Opties: Koersinformatie
    MenuFactory.addMenuItem(menu, "Koersinformatie", new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        koersInformatieOpties_actionPerformed(e);
      }
    });

    menuBar.add(menu);

    return menuBar;
  }

  //File | Exit action performed
  public void fileExit_actionPerformed(ActionEvent e) {
    // System.exit(0);
  }

  // Bestand | Bedrijven informatie opslaan action performed
  public void bedrijvenInformatieOpslaan_actionPerformed(ActionEvent e) {
    (new CompaniesContentHandler(companyPool)).write();
    (new CompanyFundsContentHandler(companyPool)).write();
    FundSharesContentHandler.write();
    ShareDividendsContentHandler.write();

    statusBar.setText("Informatie over bedrijven opgeslagen.");

    //repaints menu after item is selected
    this.repaint();
  }

  //Bedrijven | Toevoegen action performed
  public void companiesAdd_actionPerformed(ActionEvent e) {
    CompanyAddCompaniesDialog dlg = new CompanyAddCompaniesDialog(this, companyPool);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.setVisible(true);

    //repaints menu after item is selected
    this.repaint();
  }


  //Bedrijven | Fondsen toevoegen action performed
  public void companyAddFunds_actionPerformed(ActionEvent e) {
    CompanyAddFundsDialog dlg = new CompanyAddFundsDialog(this, companyPool, selectedCompany);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.setVisible(true);

    //repaints menu after item is selected
    this.repaint();
  }

  //Fondsen | Aandelen toevoegen action performed
  public void fundAddShares_actionPerformed(ActionEvent e) {
    FundAddSharesDialog dlg = new FundAddSharesDialog(this, selectedFund);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.setVisible(true);

    //repaints menu after item is selected
    this.repaint();
  }

  //Aandelen | Divedenden toevoegen action performed
  public void shareAddDividends_actionPerformed(ActionEvent e) {
    ShareAddDividendsDialog dlg = new ShareAddDividendsDialog(this, selectedShare);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.setVisible(true);

    //repaints menu after item is selected
    this.repaint();
  }

  //Aandelen | Koersinformatie action performed
  public void koersInformatie_actionPerformed(ActionEvent e) {
    ShareRatesDialog dlg = new ShareRatesDialog(this, selectedFund, selectedShare);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(false);
    dlg.setVisible(true);

    //repaints menu after item is selected
    this.repaint();
  }

  //Opties | Koersinformatie action performed
  public void koersInformatieOpties_actionPerformed(ActionEvent e) {
    OptionRatesDialog dlg = new OptionRatesDialog(this, selectedShare);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(false);
    dlg.setVisible(true);

    //repaints menu after item is selected
    this.repaint();
  }

  //Overridden so we can exit on System Close
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if(e.getID() == WindowEvent.WINDOW_CLOSING) {
      fileExit_actionPerformed(null);
      Company.removeCompaniesListener(this);
      Fund.removeFundListener(this);
      Share.removeShareListener(this);
    }
  }

  public void companiesChanged() {
    // create a list of all existing company names.
    int numberOfCompanies = companyPool.getNumberOfCompanies();
    if (numberOfCompanies == 0) {
      companiesList.setListData(EMPTY_LIST);
    } else {
      String[] companyNames = new String[companyPool.getNumberOfCompanies()];
      int index = 0;
      for (Company company: companyPool.getCompanies()) {
        companyNames[index++] = company.getName();
      }
      companiesList.setListData(companyNames);
    }

    // if 'selectedCompany' was not null, try to select it again.
    if (selectedCompany != null) {
      companiesList.setSelectedValue(selectedCompany.getName(), true);
      // This selection will result in calling selectedCompanyChanged().
    }
  }

  public void selectedCompanyChanged() {
    String companyName = (String) companiesList.getSelectedValue();
    Company previouslySelectedCompany = selectedCompany;
    if (companyName == null) {
      selectedCompany = null;
    } else {
      selectedCompany = companyPool.getCompany(companyName);
    }
    if ((selectedCompany != null)  &&  (selectedCompany == previouslySelectedCompany)) {
      return;
    } else {
      makeNewFundsList();
    }
  }

  public void makeNewFundsList(){
    if (selectedCompany == null) {
      companyFundsList.setListData(NO_LIST);
    } else {
      java.util.List<Fund> funds = selectedCompany.getFunds();
      if (funds.size() == 0) {
        companyFundsList.setListData(EMPTY_LIST);
      } else {
        Iterator<Fund> iterator = funds.iterator();
        Fund fund;
        int index = 0;
        String[] names = new String[funds.size()];

        while (iterator.hasNext()) {
          fund = iterator.next();
          names[index++] = fund.getName();
        }

        companyFundsList.setListData(names);
      }
      companyFundsList.setSelectedIndex(0);
    }
  }

  public void selectedFundChanged() {
    String fundName = (String) companyFundsList.getSelectedValue();
    Fund previouslySelectedFund = selectedFund;
    if (fundName == null  ||  fundName.compareTo(EMPTY_LIST[0]) == 0) {
      selectedFund = null;
    } else {
      selectedFund = selectedCompany.getFund(fundName);
    }

    if ((selectedFund != null)  &&  (selectedFund == previouslySelectedFund)) {
      return;
    }

    makeNewSharesList();
  }

  public void makeNewSharesList() {
    // Make new list of shares
    if (selectedFund == null) {
      fundSharesList.setListData(NO_LIST);
    } else {
      java.util.List<Share> shares = selectedFund.getShares();
      if (shares.size() == 0) {
        fundSharesList.setListData(EMPTY_LIST);
      } else {
        Iterator<Share> iterator = shares.iterator();
        Share share;
        int index = 0;
        String[] names = new String[shares.size()];

        while (iterator.hasNext()) {
          share = iterator.next();
          names[index++] = share.getName();
        }

        fundSharesList.setListData(names);
      }
      fundSharesList.setSelectedIndex(0);
    }
  }

  private void selectedShareChanged() {
    String shareName = (String) fundSharesList.getSelectedValue();
    Share previouslySelectedShare = selectedShare;
    if (shareName == null) {
      selectedShare = null;
    } else {
      selectedShare = selectedFund.getShare(shareName);
    }
    if ((selectedShare != null)  &&  (selectedShare == previouslySelectedShare)) {
      return;
    }

    makeNewDividendsList();
  }

  private void makeNewDividendsList() {
    if (selectedShare == null) {
      dividendsList.setListData(NO_LIST);
      return;
    }

    java.util.List<ShareDividend> dividends = selectedShare.getDividends();
    Iterator<ShareDividend> iterator = dividends.iterator();
    ShareDividend dividend;
    int index = 0;
    String[] names = new String[dividends.size()];

    while (iterator.hasNext()) {
      dividend = iterator.next();
      names[index++] = dividend.getReferenceString();
    }

    if (names.length > 0) {
      dividendsList.setListData(names);
      dividendsList.setSelectedIndex(0);
    } else {
      dividendsList.setListData(EMPTY_LIST);
    }
  }

  // Implementation of CompanyListener Interface
  public void CompaniesUpdated() {
    companiesChanged();
  }

  public void CompanyFundAdded(Company company) {
    makeNewFundsList();
  }

  // Implementation of FundListener Interface
  public void FundsUpdated() {
    makeNewFundsList();
  }

  public void FundShareAdded(Fund fund) {
    makeNewSharesList();
  }

  // Implementation of FundListener Interface
  public void SharesUpdated() {

  }

  public void ShareDividendAdded(Share share) {
    //System.out.println("ShareDividendAdded event received");
    makeNewSharesList();
  }
}