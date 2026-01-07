package goedegep.finan.investmentinsurances.guifx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

import goedegep.finan.app.FinanRegistry;
import goedegep.finan.app.FinanService;
import goedegep.finan.investmentinsurance.model.InvestmentInsuranceFactory;
import goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage;
import goedegep.finan.investmentinsurance.model.InvestmentInsurancesData;
import goedegep.finan.investmentinsurances.DumpInvestmentInsurancesData;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.MenuUtil;
import goedegep.util.desktop.DesktopUtil;
import goedegep.util.emf.EMFResource;
import goedegep.util.text.TextWriter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * This class provides an overview window for investment insurances.
 */
public class InvestmentInsurancesOverviewWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(InvestmentInsurancesOverviewWindow.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");

  /**
   * Base of the window title.
   */
  private final static String WINDOW_TITLE = "Investment Insurances";
  
  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;
  private FinanRegistry finanRegistry;
  private FinanService finanService;
  
  private EMFResource<InvestmentInsurancesData> investmentInsurancesResource = null;
  private InvestmentInsurancesData investmentInsurancesData;
  private Label statusText;
  private File dataDumpFile;

  public InvestmentInsurancesOverviewWindow(CustomizationFx customization) {
    super(customization, WINDOW_TITLE);
    
    this.customization = customization;
    componentFactory = customization.getComponentFactoryFx();
    finanRegistry = FinanRegistry.getInstance();
    finanService = FinanService.getInstance();
    
    if (finanRegistry.getInvestmentInsurancesFileName() == null) {

      Alert alert = componentFactory.createErrorDialog(
          "No investment insurances filename configured",
          "There's no filename configured for the file with investment insurances" + NEWLINE +
          "Configure the filename (e.g. via the 'Edit User Settings' below) and restart the application." + NEWLINE +
          "A restart is needed, because the settings are only read at startup."
      );
            
      ButtonType editorButtonType = new ButtonType("Edit User Settings");
      alert.getButtonTypes().add(editorButtonType);
      
      alert.showAndWait().filter(response -> response == editorButtonType).ifPresent(_ ->  finanService.showPropertiesEditor());
      
      return;
    }

    investmentInsurancesResource = new EMFResource<>(
        InvestmentInsurancePackage.eINSTANCE, 
        () -> InvestmentInsuranceFactory.eINSTANCE.createInvestmentInsurancesData(), ".xmi");

    File investmentInsurancesFile = new File(finanRegistry.getDataDirectory(), finanRegistry.getInvestmentInsurancesFileName());
    try {
      investmentInsurancesData = investmentInsurancesResource.load(investmentInsurancesFile.getAbsolutePath());
    } catch (IOException e) {
      LOGGER.severe("File not found: " + e.getMessage());
      
      Alert alert = componentFactory.createYesNoConfirmationDialog(
          "Vacations file not found",
          "The file with investment insurances '" + investmentInsurancesFile.getAbsolutePath() + "' doesn't exist yet.",
          "Do you want to create this file now?" + NEWLINE +
          "If you choose \"No\" you can't do anything in this screen.");
      alert.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(_ -> {
        LOGGER.severe("yes, create file");
        investmentInsurancesData = investmentInsurancesResource.newEObject();
        try {
          investmentInsurancesResource.save(finanRegistry.getInvestmentInsurancesFileName());
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      });
 
    }
    
    CreateGUI();
    updateTitle();
    
    investmentInsurancesResource.dirtyProperty().addListener(new ChangeListener<Boolean>() {

      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        updateTitle();        
      }
      
    });
    
    investmentInsurancesResource.uriProperty().addListener((_ , _, _) -> updateTitle());
    
    show();
  }
  
  private void CreateGUI() {
    BorderPane mainPane = new BorderPane();
    
    // Top: menu bar
    mainPane.setTop(createMenuBar());
    
    // Center
    VBox vBox = componentFactory.createVBox(12.0, 12.0);
    
    // InvestmentInsurancesOverviewTable
    InvestmentInsurancesOverviewTable investmentInsurancesOverviewTable = new InvestmentInsurancesOverviewTable(investmentInsurancesData);
    vBox.getChildren().add(investmentInsurancesOverviewTable);
    
    InvestmentFundStockPriceDevelopmentPanel investmentFundStockPriceDevelopmentPanel = new InvestmentFundStockPriceDevelopmentPanel(customization, investmentInsurancesData.getInsuranceCompanies());
    vBox.getChildren().add(investmentFundStockPriceDevelopmentPanel);
    
    statusText = componentFactory.createLabel("Nothing to report");
    vBox.getChildren().add(statusText);    
    
    mainPane.setCenter(vBox);
    
    setScene(new Scene(mainPane, 1200, 900));
  }

  /**
   * Create the menu bar for this window.
   * 
   * @return the menu bar for this window.
   */
  private MenuBar createMenuBar() {
    MenuBar menuBar = componentFactory.createMenuBar();
    Menu menu;
    MenuItem menuItem;
    
    // File menu
    menu = componentFactory.createMenu("File");

    // File: Save Investment Insurances
    menuItem = componentFactory.createMenuItem("Save Investment Insurances");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        saveInvestmentInsurances();
      }
    });
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
    menu.getItems().add(menuItem);
    
    // File: Dump Data
    if (finanRegistry.isDevelopmentMode()) {
      menuItem = componentFactory.createMenuItem("Dump data");
      menuItem.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
          dumpData();
        }
      });
      menu.getItems().add(menuItem);
    }
    
    menuBar.getMenus().add(menu);
    
    // Investment insurance menu
    menu = componentFactory.createMenu("Investment insurance");

    // Investment insurance: Investment insurance information
    menuItem = componentFactory.createMenuItem("Investment insurance information");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        showInvestmentInsuranceInformation();
      }
    });
    menu.getItems().add(menuItem);
    
    // Investment insurance: New Annual Statement
    menuItem = componentFactory.createMenuItem("New Annual Statement");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        showAnnualStatementEditor(true);
      }
    });
    menu.getItems().add(menuItem);
    
    // Investment insurance: Edit Annual Statement
    menuItem = componentFactory.createMenuItem("Edit Annual Statement");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        showAnnualStatementEditor(false);
      }
    });
    menu.getItems().add(menuItem);
    
    menuBar.getMenus().add(menu);


    // Help menu
    menu = componentFactory.createMenu("Help");

    // Help: Investment Insurances Information
    MenuUtil.addMenuItem(menu, "Investment Insurances Information", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        DesktopUtil.open("http://mydigitallife.rf.gd/myworld-user-manual/finan/beleggingsverzekeringen/");
//        new BrowserWindow("Beleggingsverzekeringen informatie", customization, "http://mydigitallife.rf.gd/myworld-user-manual/finan/beleggingsverzekeringen/");
      }
    });

    menuBar.getMenus().add(menu);
    
    return menuBar;
  }

  /**
   * Save the Investment Insurances to file.
   */
  protected void saveInvestmentInsurances() {
    if (investmentInsurancesResource != null) {
      try {
        investmentInsurancesResource.save();
        statusText.setText("Investment insurances saved to file: '" + investmentInsurancesResource.getFileName() + "'");
      } catch (IOException e) {
        componentFactory.createErrorDialog("Saving investment insurances failed", "System message: " + e.getMessage()).showAndWait();
      }
    }  
  }
  
  private void showInvestmentInsuranceInformation() {
    new InvestmentInsuranceWindow(customization, investmentInsurancesData);
  }
  
  private void showAnnualStatementEditor(boolean newStatementMode) {
    new AnnualStatementEditor(customization, investmentInsurancesData, newStatementMode);
  }

  protected void dumpData() {
    FileChooser fileChooser = componentFactory.createFileChooser("Dump data to file");
    ExtensionFilter extensionFilter = new ExtensionFilter("Text files", ".txt");
    fileChooser.setSelectedExtensionFilter(extensionFilter);
    if (dataDumpFile != null) {
      fileChooser.setInitialFileName(dataDumpFile.getAbsolutePath());
    } else {
      fileChooser.setInitialDirectory(new File(finanRegistry.getDataDirectory()));
    }
    dataDumpFile = fileChooser.showSaveDialog(this);
    dumpDataToFile(dataDumpFile);
  }

  protected void dumpDataToFile(File dataDumpFile) {
    LOGGER.severe("Dumping data to: " + dataDumpFile);
    try {
      TextWriter textWriter = new TextWriter(new OutputStreamWriter(new FileOutputStream(dataDumpFile)));
      
      DumpInvestmentInsurancesData.dumpData(investmentInsurancesData, textWriter);
      textWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Update the window title.
   * <p>
   * The format of the title is: {@link #WINDOW_TITLE} - &lt;dirty&gt;&lt;file-name&gt;<br/>
   * Where:<br/>
   * &lt;dirty&gt; is a '*' symbol if the data file is dirty, empty otherwise.<br/>
   * &lt;file-name&gt; is the name of the file being operated on, or '&lt;NoName&gt' if there's no file name available.
   */
  private void updateTitle() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(WINDOW_TITLE);
    buf.append(" - ");
    if (investmentInsurancesResource.isDirty()) {
      buf.append("*");
    }
    String fileName = investmentInsurancesResource.getFileName();
    if (fileName.equals("")) {
      fileName = "<NoName>";
    }
    buf.append(fileName);
    
    setTitle(buf.toString());
  }
  
}
