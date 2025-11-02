package goedegep.app.finan.finanapp;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

//import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.SAXException;

import goedegep.app.finan.abnamrobank.AbnAmroBankTransactionContentHandler;
import goedegep.app.finan.abnamrobank.AbnAmroBankWindow;
import goedegep.app.finan.direktbankapp.DirektBank;
import goedegep.app.finan.direktbankapp.DirektbankTransactionContentHandler;
import goedegep.app.finan.direktbankapp.DirektbankWindow;
import goedegep.app.finan.gen.AppModules;
import goedegep.app.finan.gen.FinanBank;
import goedegep.app.finan.gen.GenResources;
import goedegep.app.finan.overzichten.VermogensontwikkelingWindow;
import goedegep.app.finan.postbankapp.PbEffRekTestWindow;
import goedegep.app.finan.postbankapp.PbRekWindow;
import goedegep.app.finan.postbankapp.PbResources;
import goedegep.app.finan.postbankapp.PbTransactionContentHandler;
import goedegep.app.finan.postbankapp.Postbank;
import goedegep.app.finan.registry.FinanRegistry;
import goedegep.app.finan.stocksapp.CompanyWindow;
import goedegep.appgen.MessageDialogType;
import goedegep.appgen.PgFileFilter;
import goedegep.appgen.PgJFileChooser;
import goedegep.appgen.PgMenuItem;
import goedegep.appgen.WindowUtil;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppResources;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.appgen.swing.Customization;
import goedegep.appgen.swing.Customizations;
import goedegep.appgen.swing.MenuFactory;
import goedegep.appgen.swing.OptionDialog;
import goedegep.finan.abnamrobank.AbnAmroBank;
import goedegep.finan.basic.Bank;
import goedegep.finan.basic.FinanTransaction;
import goedegep.finan.basic.FinancieleEenheid;
import goedegep.finan.basic.FinancieleEenheidHandler;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.basic.SumAccount;
import goedegep.finan.basic.SumAccountListener;
import goedegep.finan.basic.TransactionError;
import goedegep.finan.direktbank.Direktbank;
import goedegep.finan.effrek.EffRekAandelenTransactie;
import goedegep.finan.effrek.EffRekOptieTransactie;
import goedegep.finan.postbank.pbeffrek.PbEffRek;
import goedegep.finan.postbank.pbrek.PbRek;
import goedegep.finan.stocks.ClaimEmissionsContentHandler;
import goedegep.finan.stocks.CompaniesContentHandler;
import goedegep.finan.stocks.CompanyFundsContentHandler;
import goedegep.finan.stocks.CompanyPool;
import goedegep.finan.stocks.FundSharesContentHandler;
import goedegep.finan.stocks.OptionSerie;
import goedegep.finan.stocks.OptionSeriesContentHandler;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.ShareDividendsContentHandler;
import goedegep.finan.stocks.ShareTaxRatesContentHandler;
import goedegep.finan.stocks.StockDepot;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.jfx.PropertyDescriptorsEditorFx;
import goedegep.properties.app.guifx.PropertiesEditor;
import goedegep.resources.ImageSize;
import goedegep.rolodex.model.Rolodex;
import goedegep.util.sax.ParseException;
import goedegep.util.text.TextWriter;

/**
 * Finan application main window.
 */

@SuppressWarnings("serial")
public class FinanMainWindow extends AppFrame implements SumAccountListener {
  private static final Logger         LOGGER = Logger.getLogger(FinanMainWindow.class.getName());
  
  /**
   * Base of the window title.
   */
  private static final String         WINDOW_TITLE = "Finance";

  private List<BankInfo> bankInfoList = new ArrayList<BankInfo>();
  
  // bank Identificatie Strings
  protected static String              bankNameAbnAmroString;
  protected static String              bankNameDirektbankString;
  protected static String              bankNamePostbankString;

  // For popup to ask whether transactions shall be handled before dumping data
  private static final String CANCEL = "CANCEL";
  private static final String TRANSACTIES_VERWERKEN_EN_DOORGAAN = "TRANSACTIES_VERWERKEN_EN_DOORGAAN";
  private static final String TRANSACTIES_NIET_VERWERKEN_EN_DOORGAAN = "TRANSACTIES_NIET_VERWERKEN_EN_DOORGAAN";
  private static final String DEFAULT_DATA_DUMP_OPTION = TRANSACTIES_VERWERKEN_EN_DOORGAAN;
  private static final String[] DATA_DUMP_OPTIONS = {
      CANCEL, TRANSACTIES_VERWERKEN_EN_DOORGAAN, TRANSACTIES_NIET_VERWERKEN_EN_DOORGAAN
  };
  
  private boolean                      isMainWindow;
  private ComponentFactory             componentFactory;
  
  private FinanRegistry finanRegistry;
  
  private SAXParser                    parser;
  private TransactionContentHandler    contentHandler;
  private CompanyPool                  companyPool = new CompanyPool();
  
  // State information.
  private boolean         transactionsFileDirty = false;    // True means transaction data changed since last save.
  private File            dataDumpFile = null;              // File to which data has been dumped.
  private SumAccount      sumAccount = new SumAccount();
  private StockDepot      sumStockDepot;
  
  // Voor de personen
  private Rolodex rolodex;
  
  // Accounts overview table
  private BankTable       bankTable;
//  private FinanBanksTable finanBanksTable;
  
  // 'All transactions'table
  private FinanTransactionsTable finanTransactionsTable;
  
  // Financiele eenheden (under development)
  private List<FinancieleEenheid> financieleEenheden = null;
  private FinancieleEenheid financieleEenheid = null;
  
  //
  // GUI elements
  //
    
  // Menu items which can be enabled or disabled.
  private JMenuItem      handleTransactionsMenuItem;
  private PgMenuItem     transactiesOpslaanMenuItem;

  // Statusbar
  private JLabel         statusBar = null;

  // file chooser for open and save as
  private PgJFileChooser fileChooser = new PgJFileChooser();
  
  
  public FinanMainWindow(Customization customization, boolean isMainWindow, boolean userPropertiesFileInstalled, Rolodex rolodex) {
    super(WINDOW_TITLE, customization);
    LOGGER.setLevel(Level.SEVERE);
    this.isMainWindow = isMainWindow;
    componentFactory = getTheComponentFactory();
    
    finanRegistry = FinanRegistry.getInstance();
    
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      initGUI();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    
    pack();
    
    initFinance(userPropertiesFileInstalled, rolodex);
    
    setVisible(true);
  }

  private void initGUI() throws Exception {
    statusBar = componentFactory.createLabel("", SwingConstants.LEFT);
    
    // General window initialization
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

    fileChooser.setAppendExtensionIfNotSpecified(true);

    setSize(new Dimension(1100, 600));
    
    setJMenuBar(createMenuBar());
    
    sumStockDepot = new StockDepot();
    
    // TODO Open de bekende bankrekeningen, dit moet eigenlijk anders.
    Bank bank;
    FinanBank finanBank;
    AppResources appResources;
    bankInfoList = new ArrayList<BankInfo>();
    
    bank = new PbRek(sumStockDepot);
    bankNamePostbankString = bank.getName();
    appResources = Postbank.getAppResources();
    finanBank = new FinanBank(bank, appResources.getApplicationIcon(ImageSize.SIZE_0), appResources.getSmallIconDisabled());
    sumAccount.addBank(finanBank);
    bankInfoList.add(new BankInfo(bank.getName(), PbTransactionContentHandler.class));
    
    bank = new AbnAmroBank(sumStockDepot);
    bankNameAbnAmroString = bank.getName();
    appResources = AbnAmroBank.getAppResources();
    finanBank = new FinanBank(bank, appResources.getApplicationIcon(ImageSize.SIZE_0), appResources.getSmallIconDisabled());
    sumAccount.addBank(finanBank);
    bankInfoList.add(new BankInfo(bank.getName(), AbnAmroBankTransactionContentHandler.class));
    
    bank = new Direktbank();
    bankNameDirektbankString = bank.getName();
    appResources = DirektBank.getAppResources();
    finanBank = new FinanBank(bank, appResources.getApplicationIcon(ImageSize.SIZE_0), appResources.getSmallIconDisabled());
    sumAccount.addBank(finanBank);
    bankInfoList.add(new BankInfo(bank.getName(), DirektbankTransactionContentHandler.class));
    
    getContentPane().add(createMainPanel(), BorderLayout.CENTER);
    
    statusBar.setText("Welkom bij Finan");
    getContentPane().add(statusBar, BorderLayout.SOUTH);
    
    initXmlSerialization();
  }
  
  private void initXmlSerialization() {
    // Create instance of a parser.
    SAXParserFactory spf = SAXParserFactory.newInstance();
    spf.setNamespaceAware(true);
    SAXParser saxParser = null;
    try {
      saxParser = spf.newSAXParser();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    }
    parser = saxParser;

    contentHandler = new TransactionContentHandler();
    contentHandler.setAccounts(sumAccount);
    for (BankInfo bankInfo: bankInfoList) {
      contentHandler.addBankHandler(bankInfo.getName(), bankInfo.getContentHandler());
    }
  }
  
//  private void checkRequiredDataFiles() {
//    // The following two arrays have to be in sync. (file + original).
//    String[] requiredFiles = {
//        FinanRegistry.aanstellingFile, FinanRegistry.claimEmissionsFile,
//        FinanRegistry.companiesFile, FinanRegistry.companyFundsFile,
//        FinanRegistry.financieleEenhedenFile,
//        FinanRegistry.fundSharesFile, FinanRegistry.hypothekenFile,
//        FinanRegistry.optionSeriesFile,
//        FinanRegistry.shareDividendsFile,
//        FinanRegistry.shareTaxRatesFile, FinanRegistry.transactionsFileName
//    };
//    String[] originalFiles = {
//        FinanRegistry.aanstellingFileOrig, FinanRegistry.claimEmissionsFileOrig,
//        FinanRegistry.companiesFileOrig, FinanRegistry.companyFundsFileOrig,
//        FinanRegistry.financieleEenhedenFileOrig,
//        FinanRegistry.fundSharesFileOrig, FinanRegistry.hypothekenFileOrig,
//        FinanRegistry.optionSeriesFileOrig,
//        FinanRegistry.shareDividendsFileOrig,
//        FinanRegistry.shareTaxRatesFileOrig, FinanRegistry.transactionsFileNameOrig
//    };
//    
//    // Remove the existing files from the list of requiredFiles.
//    // Set a flag if at least one file is missing.
//    boolean fileMissing = false;
//    for (int i = 0; i < requiredFiles.length; i++) {
//      String dataDirectory = FinanRegistry.dataDirectory;
//      String requiredFile = requiredFiles[i];
//      File f = new File(dataDirectory, requiredFile);
//      LOGGER.info("Checking required file: " + f.getAbsolutePath());
//      if (f.exists()) {
//        LOGGER.fine("exists");
//        requiredFiles[i] = null;
//      } else {
//        LOGGER.fine("missing");
//        fileMissing = true;
//      }
//    }
//    
//    // If one or more files are missing, show a warning (re)install the files.
//    if (fileMissing) {
//      // Create and show the message.
//      StringBuilder sb = new StringBuilder();
//      sb.append("De volgende bestanden bestaan nog niet in de gebruikers data directory.").append(NEWLINE);
//      for (String s : requiredFiles) {
//        if (s != null) {
//          sb.append(s).append(NEWLINE);
//        }
//      }
//      sb.append("Deze worden nu aangemaakt.");     
//      showMessageDialog(MessageDialogType.WARNING, sb.toString());
//      
//      // Copy the missing files from the install directory (using the original
//      // name) to the data directory (using the custom name).
//      for (int i = 0; i < requiredFiles.length; i++) {
//        Path srcPath;
//        Path dstPath;
////        File src;
////        File dst;
//        if (requiredFiles[i] != null) {
//          LOGGER.severe("originalFile: " + originalFiles[i]);
//          srcPath = Paths.get(originalFiles[i]);
//          LOGGER.severe("requiredFile: " + requiredFiles[i]);
//          dstPath = Paths.get(FinanRegistry.dataDirectory, requiredFiles[i]);
////          src = new File(originalFiles[i]);
////          dst = new File(FinanRegistry.dataDirectory, requiredFiles[i]);
//          try {
////            org.codehaus.plexus.util.FileUtils.copyFile(src, dst);
//            Files.copy(srcPath, dstPath);
//          } catch (IOException e) {
//            sb = new StringBuilder();
//            sb.append("Een van de benodigde bestanden kon niet aangemaakt worden.").append(NEWLINE);
////            sb.append("Naam van het bronbestand: ").append(src.getAbsolutePath()).append(NEWLINE);
////            sb.append("Naam van het bestemmingsbestand: ").append(dst.getAbsolutePath()).append(NEWLINE);
//            sb.append("Naam van het bronbestand: ").append(srcPath.toAbsolutePath().toString()).append(NEWLINE);
//            sb.append("Naam van het bestemmingsbestand: ").append(dstPath.toAbsolutePath().toString()).append(NEWLINE);
//            sb.append("Systeem foutmelding: ").append(e.getMessage());
//            showMessageDialog(MessageDialogType.ERROR, sb.toString());
//            System.exit(-1);
//          }
//        }
//      }
//    }
//  }
  
  private void showPropertyDescriptorsEditor() {
    new PropertyDescriptorsEditorFx(DefaultCustomizationFx.getInstance(), finanRegistry.getPropertyDescriptorsFileURI());
  }

  private void showUserSettingsEditor() {
    PropertiesEditor propertiesEditor = new PropertiesEditor("Edit Finan settings", DefaultCustomizationFx.getInstance(),
        finanRegistry.getPropertyDescriptorsFileURI(), finanRegistry.getUserPropertiesFileName());
    propertiesEditor.show();
//    JFrame propertiesWindow = new PropertiesWindow(getCustomization(), FinanRegistry.propertyDescriptorsResource);
//    WindowUtil.showFrameCenteredOnScreen(propertiesWindow, -50, -50);
  }

  private JMenuBar createMenuBar() {
    JMenuBar     menuBar = new JMenuBar();
    JMenu        menu;
    AppResources appResources;
    
    // Menu bar
    // For each menu: set the text, "handle menu items" and add menu to menu bar.
    // "handle menu items" means: set the text, add an ActionListener and add the
    // item to the menu.

    // Bestand menu
    menu = new JMenu("Bestand");

    // Bestand: Transacties verwerken
    handleTransactionsMenuItem = MenuFactory.addMenuItem(menu, "Transacties verwerken",
        GenResources.getHandleTransactionsIcon(),
        GenResources.getHandleTransactionsDisabledIcon(),
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handleTransactions();
      }
    });

    // Bestand: Transacties opslaan
    transactiesOpslaanMenuItem = new PgMenuItem("Transacties opslaan");
    transactiesOpslaanMenuItem.setEnabled(false);
    transactiesOpslaanMenuItem.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        transactiesOpslaan_actionPerformed();
      }
    });
    menu.add(transactiesOpslaanMenuItem);

    menu.addSeparator();


    // Bestand: property descriptors bewerken
    if (finanRegistry.isDevelopmentMode()) {
      MenuFactory.addMenuItem(menu, "Property Descriptors bewerken", new ActionListener()  {
        public void actionPerformed(ActionEvent e) {
          showPropertyDescriptorsEditor();
        }
      });
    }
    
    // Bestand: Gebruikers instellingen bewerken
    MenuFactory.addMenuItem(menu, "Gebruikers instellingen bewerken", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        showUserSettingsEditor();
      }
    });
    
    // Bestand: Uiterlijk voorbeeld scherm
    JMenu vbMenu = new JMenu("Uiterlijk voorbeeld scherm");

    PgMenuItem subMenuItem;
    for (final AppModules module: AppModules.values()) {
      subMenuItem = new PgMenuItem(module.name());
      subMenuItem.addActionListener(new ActionListener()  {
        public void actionPerformed(ActionEvent e) {
          uiterlijkVoorbeeldScherm_actionPerformed(module);
        }
      });
      vbMenu.add(subMenuItem);
    }
    menu.add(vbMenu);

    menu.addSeparator();

    
    // Bestand: Dump data
    MenuFactory.addMenuItem(menu, "Dump data", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        dumpData_actionPerformed(e);
      }
    });

    // Bestand: Exit
    MenuFactory.addMenuItem(menu, "Exit", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        fileExit_actionPerformed(e);
      }
    });

    menuBar.add(menu);

    
    
    // Menu: Banken
    menu = new JMenu("Banken");

    // Banken: AbnAmro bank
    appResources = AbnAmroBank.getAppResources();
    MenuFactory.addMenuItem(menu, "ABN AMRO Bank", appResources.getApplicationIcon(ImageSize.SIZE_0),
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        abnAmroBank_actionPerformed(e);
      }
    });

    // Banken: Direktbank
    appResources = DirektBank.getAppResources();
    MenuFactory.addMenuItem(menu, "Direktbank", appResources.getApplicationIcon(ImageSize.SIZE_0),
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        direktbank_actionPerformed(e);
      }
    });

    // Banken: Postbank
    MenuFactory.addMenuItem(menu, "Postbank", PbResources.getPostbankLogoIcon(),
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        postbank_actionPerformed(e);
      }
    });

    menuBar.add(menu);

    
    // Effecten menu
    menu = new JMenu("Effecten");

    // Effecten: Bedrijven window
    appResources = CompanyPool.getAppResources();
    MenuFactory.addMenuItem(menu, "Bedrijven ...", appResources.getApplicationIcon(ImageSize.SIZE_0), new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        algemeenBedrijven_actionPerformed(e);
      }
    });

    menuBar.add(menu);
    
    
    // Overzichten menu
    menu = new JMenu("Overzichten");

    // Overzichten: Vermogensontwikkeling
    MenuFactory.addMenuItem(menu, "Vermogensontwikkeling",
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menuVermogensontwikkeling_actionPerformed(e);
      }
    });

    menuBar.add(menu);
    
    return menuBar;
  }
  
  /**
   * Create the main panel. Top part is a table with accounts,
   * bottom part is a table that lists all transactions.
   * @return
   */
  private JComponent createMainPanel() {
    // Split pane with Bank table on top and Transactions Table below.
//    finanBanksTable = new FinanBanksTable(sumAccount.getBanks());
    bankTable = new BankTable(this, sumAccount);
    finanTransactionsTable = new FinanTransactionsTable(this, companyPool, sumAccount);
    JSplitPane splitPane =  new JSplitPane(JSplitPane.VERTICAL_SPLIT,
        bankTable, finanTransactionsTable);
//    JSplitPane splitPane =  new JSplitPane(JSplitPane.VERTICAL_SPLIT,
//        finanBanksTable, finanTransactionsTable);
    splitPane.setDividerLocation(110);
    return splitPane;
  }
  
  public void initFinance(boolean userPropertiesFileInstalled, Rolodex rolodex) {
    if (userPropertiesFileInstalled) {
      // Show message to user.
      showMessageDialog(MessageDialogType.WARNING,
          "Het bestand met gebruiker specifieke eigenschappen bestond nog niet." +
          System.getProperty("line.separator") +
          "Er is een bestand aangemaakt dat u zelf aan kunt passen." +
          "Het scherm hiervoor wordt nu eenmalig gestart." +
          "Dit kan je later altijd opnieuw starten via \"Bestand/Gebruikersinstellingen bewerken\"");

      showPropertyDescriptorsEditor();
      
//      // re-read properties file.
//      try {
//        File propertiesFile = new File(FinanRegistry.dataDirectory, FinanRegistry.customPropertiesFile);
////        readAndProcessPropertiesFile(propertiesFile, true);
//      } catch (Exception e) {
//        // Should never happen.
//        e.printStackTrace();
//        System.exit(-1);
//      }
    }
    
    File dataDirectory = new File(finanRegistry.getDataDirectory());
//    checkRequiredDataFiles();           // Check that the data files exist.
    

    // Initialize financiele eenheden, companies, hypotheken.
    File file = null;
    String fileName = null;
    try {
      // Initialize financiele eenheden.
      fileName = finanRegistry.getFinancieleEenhedenFile();
      file = new File(dataDirectory, fileName);
      if (rolodex != null) {
        FinancieleEenheidHandler financieleEenheidHandler = new FinancieleEenheidHandler(rolodex);
        LOGGER.fine("Going to read Financiele Eenheden file: " + file.getAbsolutePath());
        financieleEenheidHandler.read(file.getPath());
        financieleEenheden = financieleEenheidHandler.getFinancieleEenheden();
        financieleEenheid = financieleEenheden.get(0);
      }
      
      // Initialize companies.
      fileName = finanRegistry.getCompaniesFile();
      file = new File(dataDirectory, fileName);
      (new CompaniesContentHandler(companyPool)).read(file.getPath());
      
      // Initialize company funds.
      fileName = finanRegistry.getCompanyFundsFile();
      file = new File(dataDirectory, fileName);
      (new CompanyFundsContentHandler(companyPool)).read(file.getPath());
      
      // Initialize fund shares.
      fileName = finanRegistry.getFundSharesFile();
      file = new File(dataDirectory, fileName);
      (new FundSharesContentHandler()).read(file.getPath());
      
      // Initialize share dividends.
      fileName = finanRegistry.getShareDividendsFile();
      file = new File(dataDirectory, fileName);
      (new ShareDividendsContentHandler()).read(parser, file.getPath());
      
      // Initialize option series.
      fileName = finanRegistry.getOptionSeriesFile();
      file = new File(dataDirectory, fileName);
      (new OptionSeriesContentHandler()).read(parser, file.getPath());
      
      // Initialize share and stock dividend tax rates.
      fileName = finanRegistry.getShareTaxRatesFile();
      file = new File(dataDirectory, fileName);
      (new ShareTaxRatesContentHandler()).read(parser, file.getPath());
      
      // Initialize claim emissions.
      fileName = finanRegistry.getClaimEmissionsFile();
      file = new File(dataDirectory, fileName);
      (new ClaimEmissionsContentHandler()).read(parser, file.getPath());
            
    } catch (ParseException e) {
      StringBuilder buf = new StringBuilder();
      buf.append("Bestand '" + e.getFileName() + "' kan niet gelezen worden.");
      buf.append(System.getProperty("line.separator"));
      buf.append("Fout: ").append(e.getMessage()).append(System.getProperty("line.separator"));
      buf.append("Locatie: regel ").append(e.getLineNumber());
      buf.append(", kolom ").append(e.getColumnNumber());
      showMessageDialog(MessageDialogType.ERROR, buf.toString());
      System.exit(-1);
    }

    // Open de transactie file.
    finanRegistry.setTransactionsFile(new File(dataDirectory, finanRegistry.getTransactionsFileName()));
    loadTransactions();
    
    revalidate();
//    showTestWindow();
  }

  void showTestWindow() {
    FinanBank finanBank = sumAccount.getBankForBankName(bankNamePostbankString);
    PbEffRek  pbEffRek = ((PbRek) finanBank.getBank()).getEffectenRekening(false);
    
    PbEffRekTestWindow pbEffRekTestWindow = new PbEffRekTestWindow(Customizations.getCustomization(AppModules.FINAN_POSTBANK.name()), finanBank, pbEffRek);
    
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = pbEffRekTestWindow.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    
    pbEffRekTestWindow.setLocation((screenSize.width - frameSize.width) / 2,
        (screenSize.height - frameSize.height - 20));
    pbEffRekTestWindow.setVisible(true);
  }
  
  public void loadTransactions() {
    readTransactionsFromFile(finanRegistry.getTransactionsFile());

    updateTitle();
    extractRatesFromTransactions();
//    handleTransactions();
    bankTable.updateData(true);  // ignore errors, as we know that transactions aren't handled yet.

    this.repaint();
    sumAccount.addAccountListener(this);
  }

  public void readTransactionsFromFile(File transactionFile) {

    try {
      contentHandler.read(parser, transactionFile.getPath());
    } catch (SAXException e) {
      showMessageDialog(MessageDialogType.ERROR, e.getMessage());
      System.exit(-1);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }

    Integer tr_size = Integer.valueOf(sumAccount.numberOfTransactions());
    statusBar.setText("Bestand " + transactionFile.getPath() + " geopend, " + tr_size.toString() + " transacties");
  }

  boolean saveTransactions() {
    File transactionsFile = finanRegistry.getTransactionsFile();
    Path transactionsFilePath = transactionsFile.toPath();
    // If we are saving to an existing file, rename it first (with date/time extension)
    LOGGER.severe("saveCurrentFile: currentFile = " + transactionsFilePath.toAbsolutePath().toString());
    if (transactionsFile.exists()) {
      Date date = new Date();
      SimpleDateFormat     df =  new SimpleDateFormat("yyyyMMdd_HHmmss");
//      File backupFile = new File(transactionsFile.getPath() + "_" + df.format(date));
      String directoryPath = transactionsFilePath.getParent().toString();
      String transactionFileName = transactionsFilePath.getFileName().toString();
      String backupFileName = transactionFileName + "_" + df.format(date);
      Path backupPath = Paths.get(directoryPath, backupFileName);
      LOGGER.info("saveCurrentFile: file exists, renaming to " + backupPath.toAbsolutePath().toString());
      try {
//        org.codehaus.plexus.util.FileUtils.rename(transactionsFile, backupFile);
        Files.move(transactionsFilePath, backupPath);
      } catch (IOException e) {
        showMessageDialog(MessageDialogType.ERROR, "FOUT: Kan file niet hernoemen");
        return false;
      }
    }

    return writeTransactionsToFile();
  }

  boolean writeTransactionsToFile() {
    try {
      contentHandler.write();

      statusBar.setText("Opgeslagen in " + finanRegistry.getTransactionsFile().getPath());

      transactionsFileDirty = false;
      transactiesOpslaanMenuItem.setEnabled(true);
      updateTitle();

      return true;
    } catch (Exception e) {
      showMessageDialog(MessageDialogType.ERROR, e.getMessage());
      return false;
    }
  }

  void handleTransactions() {
    List<TransactionError> errors = sumAccount.handleTransactions();
    if (errors.size() > 0) {
      TransactionProblemsWindow transactionProblemsWindow = new TransactionProblemsWindow(getCustomization(), this, errors);
      WindowUtil.showFrameCenteredOnScreen(transactionProblemsWindow);
    }
    
    finanRegistry.setTransactionsHandled(true);
    statusBar.setText("Transacties zijn verwerkt");
    handleTransactionsMenuItem.setEnabled(false);

    try {
      bankTable.updateData(false);  // Transactions are handled, so errors shouldn't occur.
    } catch (RuntimeException e) {
      showMessageDialog(MessageDialogType.ERROR, e.getMessage());
    }

    this.repaint();
  }
  
  private void rewindTransactions() {
    sumAccount.unhandleTransactions();
    
    // Clear all accounts.
    sumStockDepot.clear();
    for (FinanBank account: sumAccount.getBanks()) {
      account.getBank().clear();
    }
    
    // Clear the any rates that were obtained from the transactions.
    Share.clearAllAnyRates();
    
    bankTable.updateData(true);  // Ignore errors, as account have no 'values' after clearing them.

    this.repaint();
  }

  // Check if file is dirty.
  // If so get user to make a "Save? yes/no/cancel" decision.
  boolean okToAbandon() {
    if (!transactionsFileDirty) {
      return true;
    }

    int value =  JOptionPane.showConfirmDialog(this, "Wijzigingen opslaan?",
                                         "Financien", JOptionPane.YES_NO_CANCEL_OPTION) ;
    switch (value) {
       case JOptionPane.YES_OPTION:
         // yes, please save changes
         // Handle the case where we don't have a file name yet.
         return saveTransactions();
       case JOptionPane.NO_OPTION:
         // no, abandon edits
         // i.e. return true without saving
         return true;
       case JOptionPane.CANCEL_OPTION:
       default:
         // cancel
         return false;
    }
  }

  /**
   * Update the window title.
   * <p>
   * The format of the title is: {@link #WINDOW_TITLE} - &lt;financial-unit&gt; - &lt;dirty&gt;&lt;file-name&gt;<br/>
   * Where:<br/>
   * &lt;financial-unit&gt; is the name of the financial unit. This part is only there if a financial unit is set.<br/>
   * &lt;dirty&gt; is a '*' symbol if the data file is dirty, empty otherwise.<br/>
   * &lt;file-name&gt; is the name of the file being operated on.
   */
  private void updateTitle() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(WINDOW_TITLE);
    buf.append(" - ");
    
    if (financieleEenheid != null) {
      buf.append(financieleEenheid.getTeNaamStelling());
      buf.append(" - ");
    }
    
    if (transactionsFileDirty) {
      buf.append("*");
    }
    buf.append(finanRegistry.getTransactionsFile().getName());
    
    setTitle(buf.toString());
  }

  public void clearFinance() {
    sumAccount = new SumAccount();
    sumStockDepot = new StockDepot();
    
    // TODO Open de bekende bankrekeningen, dit moet eigenlijk anders.
    Bank         bank;
    FinanBank    finanBank;
    AppResources appResources;
    
    bank = new PbRek(sumStockDepot);
    appResources = Customizations.getCustomization(AppModules.FINAN_DIREKTBANK.name()).getResources();
    finanBank = new FinanBank(bank, appResources.getApplicationIcon(ImageSize.SIZE_0), appResources.getSmallIconDisabled());
    sumAccount.addBank(finanBank);
    
    bank = new AbnAmroBank(sumStockDepot);
    appResources = Customizations.getCustomization(AppModules.FINAN_ABNAMRO_BANK.name()).getResources();
    finanBank = new FinanBank(bank, appResources.getApplicationIcon(ImageSize.SIZE_0), appResources.getSmallIconDisabled());
    sumAccount.addBank(finanBank);
    
    bank = new Direktbank();
    appResources = Customizations.getCustomization(AppModules.FINAN_DIREKTBANK.name()).getResources();
    finanBank = new FinanBank(bank, appResources.getApplicationIcon(ImageSize.SIZE_0), appResources.getSmallIconDisabled());
    sumAccount.addBank(finanBank);
    
  }

  void showVModelWindow() {
    SumVModelWindow  window =
      new SumVModelWindow(Customizations.getCustomization(AppModules.FINAN.name()),
          sumStockDepot, null);
    WindowUtil.showFrameCenteredOnScreen(window, 22, 22);
  }

  // Bestand | Opslaan action performed
  public void transactiesOpslaan_actionPerformed() {
    saveTransactions();

    //repaints menu after item is selected
    this.repaint();
  }
  
  private void uiterlijkVoorbeeldScherm_actionPerformed(AppModules appModule) {
    Customization customization = Customizations.getCustomization(appModule.name());
    JFrame frame = new CustomizationExampleFrame(this, appModule.name(), customization);
    WindowUtil.showFrameCenteredOnScreen(frame);
  }
  
  public void dumpData_actionPerformed(ActionEvent e) {
    // Dump Data only works if the transactions are handled.
    if (finanRegistry.areTransactionsHandled() == false) {
      Image image = getCustomization().getResources().getAttentionImage(ImageSize.SIZE_3);
      OptionDialog optionDialog = new OptionDialog(
          this,
          "Hoe verder?",
          image,
          "De transacties zijn nog niet verwerkt. Aangezien alleen verwerkte transacties weggeschreven worden, " +
          "is het het beste om eerst de transacties te verwerken.",
          DATA_DUMP_OPTIONS,
          DEFAULT_DATA_DUMP_OPTION);
      WindowUtil.showDialogCenteredOnParent(this, optionDialog);
      String selectedOption = optionDialog.getSelectedOption();
      LOGGER.info("Selection = " + selectedOption);
      switch (selectedOption) {
      case CANCEL:
        return;

      case TRANSACTIES_VERWERKEN_EN_DOORGAAN:
        handleTransactions();
        break;

      case TRANSACTIES_NIET_VERWERKEN_EN_DOORGAAN:
        // No action
        break;
      }
    }
    
    fileChooser.setDialogTitle("Dump data");
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    if (dataDumpFile != null) {
      fileChooser.setSelectedFile(dataDumpFile);
    } else {
      fileChooser.setCurrentDirectory(new File(finanRegistry.getDataDirectory()));
    }
    fileChooser.addChoosableFileFilter(new PgFileFilter("txt", "Tekstbestanden"));
    fileChooser.setApproveButtonToolTipText("Data naar het gekozen bestand dumpen");

    // Use the standard dialog, setting the 'APPROVE_OPTION' to "Opslaan".
    // Note: the SAVE version with setting setApproveButtonText doesn't work.
    if (JFileChooser.APPROVE_OPTION == fileChooser.showDialog(this, "Opslaan")) {
      // Set the current file to the user's selection,
      // then do a regular saveCurrentFile
      dataDumpFile = fileChooser.getSelectedFile();

      dumpDataToFile(dataDumpFile);
      statusBar.setText("Data gedumpt naar " + dataDumpFile.getAbsolutePath() + ".");
    }

    //repaints menu after item is selected
    this.repaint();
  }
  
  
  // Bestand | Exit action performed
  public void fileExit_actionPerformed(ActionEvent e) {
    if (okToAbandon()) {
      dispose();
      if (isMainWindow) {
        System.exit(0);
      }
    }
  }


  // Algemeen | Bedrijven action performed
  void algemeenBedrijven_actionPerformed(ActionEvent e) {
    WindowUtil.showFrameCenteredOnScreen(new CompanyWindow(Customizations.getCustomization(AppModules.FINAN_COMPANIES.name()), companyPool),
        -22, -22);
  }

  // Overzichten | Vermogensontwikkeling
  void menuVermogensontwikkeling_actionPerformed(ActionEvent e) {
    WindowUtil.showFrameCenteredOnScreen(new VermogensontwikkelingWindow(getCustomization(), sumAccount),
        44, 44);
    //repaints menu after item is selected
    this.repaint();
  }

  void tbbCloseFile_actionPerformed(ActionEvent e) {
    transactiesOpslaan_actionPerformed();
    fileExit_actionPerformed(e);
  }

  void abnAmroBank_actionPerformed(ActionEvent e) {
    FinanBank finanBank = sumAccount.getBankForBankName(bankNameAbnAmroString);
    WindowUtil.showFrameCenteredOnScreen(new AbnAmroBankWindow(Customizations.getCustomization(AppModules.FINAN_ABNAMRO_BANK.name()), finanBank), 22, 22);
  }

  void direktbank_actionPerformed(ActionEvent e) {
    Direktbank direktbank = (Direktbank) sumAccount.getBankForBankName(bankNameDirektbankString).getBank();
    WindowUtil.showFrameCenteredOnScreen(new DirektbankWindow(Customizations.getCustomization(AppModules.FINAN_DIREKTBANK.name()), direktbank), 22, 22);
  }

  void postbank_actionPerformed(ActionEvent e) {
    FinanBank finanBank = sumAccount.getBankForBankName(bankNamePostbankString);
    WindowUtil.showFrameCenteredOnScreen(new PbRekWindow(Customizations.getCustomization(AppModules.FINAN_POSTBANK.name()), finanBank), 22, 22);
  }

  void postbankEffRekVModel_actionPerformed(ActionEvent e) {
    showVModelWindow();
  }

  //Overridden so we can exit on System Close
  @Override
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if(e.getID() == WindowEvent.WINDOW_CLOSING) {
      fileExit_actionPerformed(null);
    }
  }
  
  public void transactionAdded(FinanTransaction transaction) {
    transactionDataChanged();
  }

  public void transactionRemoved(FinanTransaction transaction) {
    transactionDataChanged();
  }
  
  private void transactionDataChanged() {
    if (!transactionsFileDirty) {
      transactionsFileDirty = true;
      transactiesOpslaanMenuItem.setEnabled(true);
      updateTitle();
    }
    if (finanRegistry.areTransactionsHandled()) {
      rewindTransactions();
      finanRegistry.setTransactionsHandled(false);
      statusBar.setText("Transacties zijn niet verwerkt.");
      handleTransactionsMenuItem.setEnabled(true);
    }
    bankTable.updateData(true);  // Ignore errors as transactions aren't handled anymore.
  }

  void extractRatesFromTransactions() {
    PgTransaction         currentTransaction;

    for (FinanTransaction finanTransaction: sumAccount.getTransactions()) {
      currentTransaction = finanTransaction.getTransaction();
      if (currentTransaction instanceof EffRekAandelenTransactie) {
        EffRekAandelenTransactie tr = (EffRekAandelenTransactie) currentTransaction;
        Share share = tr.getEffect();
        share.addAnyRate(tr.getBookingDate(), tr.getKoers().cloneCurrency());
      }
      
      if (currentTransaction instanceof EffRekOptieTransactie) {
        EffRekOptieTransactie tr = (EffRekOptieTransactie) currentTransaction;
        OptionSerie optionSerie = tr.getOptionSerie();
        optionSerie.addAnyRate(tr.getBookingDate(), tr.getKoers().cloneCurrency());
      }
    }
  }
  
  private void dumpDataToFile(File file) {
    try {
      TextWriter textWriter = new TextWriter(new OutputStreamWriter(new FileOutputStream(file)));
      for (FinanBank account: sumAccount.getBanks()) {
        account.getBank().dumpData(textWriter);
      }
            
      textWriter.newLine();
      textWriter.newLine();

      textWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  class BankInfo {
    String name;
    Class<?> contentHandler;
    
    protected BankInfo(String name, Class<?> contentHandler) {
      this.name = name;
      this.contentHandler = contentHandler;
    }

    protected String getName() {
      return name;
    }

    protected Class<?> getContentHandler() {
      return contentHandler;
    } 
  }
}