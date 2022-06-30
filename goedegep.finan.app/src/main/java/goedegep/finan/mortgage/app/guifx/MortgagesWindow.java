package goedegep.finan.mortgage.app.guifx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EReference;

import goedegep.app.finan.registry.FinanRegistry;
import goedegep.finan.mortgage.InterestCompensationMortgageCalculator;
import goedegep.finan.mortgage.MortgageCalculator;
import goedegep.finan.mortgage.MortgageReportsGenerator;
import goedegep.finan.mortgage.model.InterestCompensationMortgage;
import goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview;
import goedegep.finan.mortgage.model.Mortgage;
import goedegep.finan.mortgage.model.MortgageFactory;
import goedegep.finan.mortgage.model.MortgagePackage;
import goedegep.finan.mortgage.model.MortgageYearlyOverview;
import goedegep.finan.mortgage.model.Mortgages;
import goedegep.finan.mortgage.model.util.InterestCompensationMortgageUtil;
import goedegep.finan.mortgage.model.util.MortgageUtil;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.properties.app.guifx.PropertiesEditor;
import goedegep.rolodex.model.Rolodex;
import goedegep.util.emf.EMFNotificationListener;
import goedegep.util.emf.EMFResource;
import goedegep.util.i18n.TranslationFormatter;
import goedegep.util.text.TextWriter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MortgagesWindow extends JfxStage implements EMFNotificationListener {
  private static final Logger LOGGER = Logger.getLogger(MortgagesWindow.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final ResourceBundle TRANSLATIONS = getBundle(MortgagesWindow.class, "MortgagesResource");

  private static final String ZWITSERLEVEN_HYPOTHEEK_ID = "ZwitserLeven Hypotheek van Betere Huize 01-03-1996";

  private CustomizationFx customization;
  private TranslationFormatter translationFormatter = new TranslationFormatter(TRANSLATIONS);
  private EMFResource<Mortgages> mortgagesResource;
  private Mortgages mortgages;
  private ComponentFactoryFx componentFactory;
  private MenuItem compensationPaymentsMenuItem;
  private MenuItem interestRateDevelopmentChartMenuItem;
  private Mortgage mortgage = null;
  private MortgageCalculator mortgageCalculator = null;
  private ComboBox<MortgageWrapper> mortgagesComboBox;
  private MortgageEventsTable mortgageEventsTable = null;
  @SuppressWarnings("rawtypes")
  private EObjectTable mortgageYearlyOverviewsTable = null;
  private VBox mortgageYearlyOverviewsTableBox = null;
  private Label statusLabel = new Label("");
  
  private MortgageInfoPanel hypotheekInfoPanel;

  public MortgagesWindow(CustomizationFx customization, Rolodex rolodex) {
    super(null, customization);
    
    if (FinanRegistry.mortgagesFileName == null) {
      statusLabel.setText(TRANSLATIONS.getString("MortgagesWindow.statusLabel.noMortgagesFileNameMsg"));
      Alert alert = componentFactory.createErrorDialog(TRANSLATIONS.getString("MortgagesWindow.alertNoMortgagesFileName.header"), TRANSLATIONS.getString("MortgagesWindow.alertNoMortgagesFileName.content"));
      
      ButtonType editorButtonType = new ButtonType(TRANSLATIONS.getString("MortgagesWindow.alertNoMortgagesFileName.editorButton"));
      alert.getButtonTypes().add(editorButtonType);
      
      alert.showAndWait().filter(response -> response == editorButtonType).ifPresent(response -> {
        showPropertiesEditor();
      });
      
      return;
    }

    this.customization = customization;
    componentFactory = customization.getComponentFactoryFx();
    createGUI();
    
    mortgagesResource = new EMFResource<>(
        MortgagePackage.eINSTANCE, 
        () -> MortgageFactory.eINSTANCE.createMortgages());
    
    File mortgagesFile = new File(FinanRegistry.dataDirectory, FinanRegistry.mortgagesFileName);
    String mortgagesFileName = mortgagesFile.getAbsolutePath();
    try {
      mortgages = mortgagesResource.load(mortgagesFileName);
    } catch (FileNotFoundException e) {
      LOGGER.severe("File not found: " + e.getMessage());
      
      Alert alert = componentFactory.createYesNoConfirmationDialog(
          TRANSLATIONS.getString("MortgagesWindow.alertMortgagesFileNotFound.title"),
          translationFormatter.formatText("MortgagesWindow.alertMortgagesFileNotFound.header", mortgagesFileName),
          TRANSLATIONS.getString("MortgagesWindow.alertMortgagesFileNotFound.content"));
      alert.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
        mortgages = mortgagesResource.newEObject();
        try {
          mortgagesResource.save(mortgagesFileName);
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      });
            
    }
    
    mortgagesResource.addNotificationListener(this);
    
    handleMortgagesChanged();
    
    handleNewHypotheekSelected();
    updateTitle();

    show();
  }

  /**
   * Get a {@link ResourceBundle} for the default locale.
   * 
   * @param clazz a class from the package in which the ResourceBundle is located.
   * @param bundle basename of the resource bundle
   * @return the requested ResourceBundle
   */
  public static ResourceBundle getBundle(Class<? extends Object> clazz, String bundle) {
      String bundlePath = clazz.getPackage().getName() + "." + bundle;
      LOGGER.info("bundlePath: " + bundlePath);
      Locale locale = Locale.getDefault();
      ClassLoader classLoader = clazz.getClassLoader();
      LOGGER.info("classLoader: " + classLoader.getName());
      return ResourceBundle.getBundle(bundlePath, locale, classLoader);
  }

  /**
   * Create the GUI.
   */
  private void createGUI() {
    /*
     * Main pane is a BorderPane.
     * North is the menu bar
     * Center is a ...
     * Bottom is a ...
     */

    BorderPane mainPane = new BorderPane();

    mainPane.setTop(createMenuBar());
    
    VBox vBox = componentFactory.createVBox();
    
    // Hypotheek selectie
    HBox hBox = componentFactory.createHBox(12.0, 12.0);
    Label label = componentFactory.createLabel("Mortgage");
    hBox.getChildren().add(label);
    mortgagesComboBox = componentFactory.createComboBox(null);
    mortgagesComboBox.setOnAction((event) -> {
      MortgageWrapper mortgageWrapper = mortgagesComboBox.getValue();
      if (mortgageWrapper != null) {
        mortgage = mortgageWrapper.getMortgage();
        if (mortgage instanceof InterestCompensationMortgage) {
          mortgageCalculator = new InterestCompensationMortgageCalculator((InterestCompensationMortgage) mortgage);
        } else {
          mortgageCalculator = new MortgageCalculator(mortgage);
        }
        mortgageCalculator.handleAndGenerateEvents();
      } else {
        mortgage = null;
      }
      handleNewHypotheekSelected();
    });
    
    hBox.getChildren().add(mortgagesComboBox);
    vBox.getChildren().add(hBox);
    
    // Mortgage information + Controls panel
    hBox = componentFactory.createHBox(12.0);
    // Hypotheek informatie
    hypotheekInfoPanel = new MortgageInfoPanel(customization, null);
    hBox.getChildren().add(hypotheekInfoPanel);
    
    Node controlsPanel = createControlsPanel();
    hBox.getChildren().add(controlsPanel);
    
    vBox.getChildren().add(hBox);
    
    // Mortgage Events
    mortgageEventsTable = new MortgageEventsTable(customization, null, null);
    vBox.getChildren().add(mortgageEventsTable);
    
    // Mortgage yearly info
    mortgageYearlyOverviewsTableBox = componentFactory.createVBox();
        
    vBox.getChildren().add(mortgageYearlyOverviewsTableBox);
    
    mainPane.setCenter(vBox);
    
    mainPane.setBottom(statusLabel);

    setScene(new Scene(mainPane, 1700, 900));
  }

  /**
   * Create the menu bar for this window.
   * @param developmentMode 
   * 
   * @return the menu bar for this window.
   */
  private MenuBar createMenuBar() {
    MenuBar menuBar = componentFactory.createMenuBar();
    Menu menu;
    MenuItem menuItem;
    
    // File menu
    menu = new Menu("File");

    // File: Save mortgages
    menuItem = componentFactory.createMenuItem("Save mortgages");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        saveMortgages();
      }
    });
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
    menu.getItems().add(menuItem);
    
    // File: Dump Data
    if (FinanRegistry.developmentMode) {
      menuItem = componentFactory.createMenuItem("Dump data");
      menuItem.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
          dumpDataToFile();
        }
      });
      menu.getItems().add(menuItem);
    }
    
    menuBar.getMenus().add(menu);
    
    // Reports menu
    menu = new Menu("Reports");

    // Reports: Generate pdf yearly report
    menuItem = componentFactory.createMenuItem("Generate yearly report (pdf)");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        generateYearlyReport();
      }
    });
    menu.getItems().add(menuItem);

    // Reports: Compensation payments
    compensationPaymentsMenuItem = componentFactory.createMenuItem("Compensation payments");
    compensationPaymentsMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        showCompensationPaymentsWindow();
      }
    });
    menu.getItems().add(compensationPaymentsMenuItem);

    if (mortgage instanceof InterestCompensationMortgage) {
      compensationPaymentsMenuItem.setDisable(false);
    } else {
      compensationPaymentsMenuItem.setDisable(true);
    }

    // Reports: Interest rates chart
    interestRateDevelopmentChartMenuItem = componentFactory.createMenuItem("Interest rates chart");
    interestRateDevelopmentChartMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        showInterestRateDevelopmentChart();
      }
    });
    menu.getItems().add(interestRateDevelopmentChartMenuItem);
    if ((mortgage != null)  &&  mortgage.getId().equals("ZwitserLeven Hypotheek van Betere Huize 01-03-1996")) {
      interestRateDevelopmentChartMenuItem.setDisable(false);
    } else {
      interestRateDevelopmentChartMenuItem.setDisable(true);
    }

    // Reports: Generate redemption invoice
    menuItem = componentFactory.createMenuItem("Generate redemption invoice");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        generateRedemptionInvoice();
      }
    });
    menu.getItems().add(menuItem);
    
    menuBar.getMenus().add(menu);
    
    // Mortgage menu
    menu = new Menu("Mortgage");

    // Mortgage: New mortgage
    menuItem = componentFactory.createMenuItem("New mortgage");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        newMortgage();
      }
    });
    menu.getItems().add(menuItem);

    // Mortgage: Edit current mortgage
    menuItem = componentFactory.createMenuItem("Edit current mortgage");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        editMortgage();
      }
    });
    menu.getItems().add(menuItem);
    
    menuBar.getMenus().add(menu);

    return menuBar;
  }
  
  private Node createControlsPanel() {
    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    
    // First row: Add events label and buttons
    Label label = componentFactory.createLabel("Add: ");
    gridPane.add(label, 0, 0);

    Button button = componentFactory.createButton("New interest rate", "Add a new interest rate event");
    button.setOnAction((e) -> addNewInterestRateEvent());
    gridPane.add(button, 1, 0);

    button = componentFactory.createButton("Final payment", "Add a new final payment event");
    button.setOnAction((e) -> addNewFinalPaymentEvent());
    gridPane.add(button, 2, 0);
    
    // Second row: Show fixed events or all events
    label = componentFactory.createLabel("Show: ");
    gridPane.add(label, 0, 1);
    
    final ToggleGroup radioButtonGroup = new ToggleGroup();

    RadioButton radioButton = new RadioButton("Fixed events");
    radioButton.setOnAction((e) -> showFixedEvents());
    radioButton.setToggleGroup(radioButtonGroup);
    gridPane.add(radioButton, 1, 1);

    radioButton = new RadioButton("All events");
    radioButton.setOnAction((e) -> showAllEvents());
    radioButton.setToggleGroup(radioButtonGroup);
    radioButton.setSelected(true);
    gridPane.add(radioButton, 2, 1);

    return gridPane;
  }

  /**
   * Handle changes in the list of mortgages.
   */
  private void handleMortgagesChanged() {
    List<MortgageWrapper> hypothekenWrapped = new ArrayList<>();
    for (Mortgage h: mortgages.getMortgages()) {
      hypothekenWrapped.add(new MortgageWrapper(h));
    }
    
    mortgagesComboBox.getItems().clear();
    mortgagesComboBox.getItems().addAll(hypothekenWrapped);
    
    if (!hypothekenWrapped.isEmpty()) {
      MortgageWrapper hypotheekWrapper  = hypothekenWrapped.get(0);
      mortgagesComboBox.setValue(hypotheekWrapper);
      mortgage = hypotheekWrapper.getMortgage();
      
      if (mortgage instanceof InterestCompensationMortgage) {
        mortgageCalculator = new InterestCompensationMortgageCalculator((InterestCompensationMortgage) mortgage);
      } else {
        mortgageCalculator = new MortgageCalculator(mortgage);
      }
      mortgageCalculator.handleAndGenerateEvents();
    }
  }
  
  @SuppressWarnings("unchecked")
  private void handleNewHypotheekSelected() {
    hypotheekInfoPanel.setHypotheek(mortgageCalculator);
    mortgageEventsTable.setMortgageEvents(null, mortgageCalculator.getCalculatedMortgageEvents());
    
    if (mortgage instanceof InterestCompensationMortgage) {
      List<InterestCompensationMortgageYearlyOverview> yearlyOverviews = getInterestCompensationYearlyOverviews();
      if (mortgageYearlyOverviewsTable == null  ||  mortgageYearlyOverviewsTable instanceof MortgageYearlyOverviewsTable) {
        mortgageYearlyOverviewsTable = new InterestCompensationMortgageYearlyOverviewsTable(customization, yearlyOverviews);
        mortgageYearlyOverviewsTableBox.getChildren().clear();
        mortgageYearlyOverviewsTableBox.getChildren().add(mortgageYearlyOverviewsTable);
      } else {
        mortgageYearlyOverviewsTable.setObjects(null, yearlyOverviews);
      }
    } else {
      List<MortgageYearlyOverview> yearlyOverviews = getYearlyOverviews();
      if ((mortgageYearlyOverviewsTable == null)  ||  mortgageYearlyOverviewsTable instanceof InterestCompensationMortgageYearlyOverviewsTable) {
        mortgageYearlyOverviewsTable = new MortgageYearlyOverviewsTable(customization, yearlyOverviews);
        mortgageYearlyOverviewsTableBox.getChildren().clear();
        mortgageYearlyOverviewsTableBox.getChildren().add(mortgageYearlyOverviewsTable);
      } else {
        mortgageYearlyOverviewsTable.setObjects(null, yearlyOverviews);
      }
    }
    
    if ((mortgage != null)  &&  mortgage.getId().equals(ZWITSERLEVEN_HYPOTHEEK_ID)) {
      interestRateDevelopmentChartMenuItem.setDisable(false);
    } else {
      interestRateDevelopmentChartMenuItem.setDisable(true);
    }
    
    if (mortgage instanceof InterestCompensationMortgage) {
      compensationPaymentsMenuItem.setDisable(false);
    } else {
      compensationPaymentsMenuItem.setDisable(true);
    }
  }
  
  private List<MortgageYearlyOverview> getYearlyOverviews() {
    List<MortgageYearlyOverview> yearlyOverviews = new ArrayList<>();
    
    int startYear = mortgageCalculator.getStartYear();
    int endYear = mortgageCalculator.getEndYear();
    for (int year = startYear; year <= endYear; year++) {
      MortgageYearlyOverview mortgageYearlyOverview = mortgageCalculator.getYearlyOverview(year);
      if (mortgageYearlyOverview != null) {
        yearlyOverviews.add(mortgageYearlyOverview);
      }
    }
    
    return yearlyOverviews;
  }
  
  private List<InterestCompensationMortgageYearlyOverview> getInterestCompensationYearlyOverviews() {
    List<InterestCompensationMortgageYearlyOverview> yearlyOverviews = new ArrayList<>();
    
    int startYear = mortgageCalculator.getStartYear();
    int endYear = mortgageCalculator.getActualEndYear();
    InterestCompensationMortgageCalculator interestCompensationMortgageCalculator = (InterestCompensationMortgageCalculator) mortgageCalculator;
    for (int year = startYear; year <= endYear; year++) {
      InterestCompensationMortgageYearlyOverview mortgageYearlyOverview = interestCompensationMortgageCalculator.getYearlyOverview(year);
      if (mortgageYearlyOverview != null) {
        yearlyOverviews.add(mortgageYearlyOverview);
      }
    }
    
    return yearlyOverviews;
  }
  
  private void saveMortgages() {
    if (mortgagesResource != null) {
      try {
        mortgagesResource.save();
        statusLabel.setText("Mortgages saved to '" + mortgagesResource.getFileName() + "'");
      } catch (IOException e) {
        componentFactory.createErrorDialog(
            "Saving the mortgages has failed.",
            "An error occurred while writing the mortgages to the file." + NEWLINE + "Error: " + e.getMessage())
            .showAndWait();
      }
    }
  }


  protected void generateYearlyReport() {
    new YearlyReportOptionsWindow(customization, mortgageCalculator);
  }

  protected void showCompensationPaymentsWindow() {
    if (mortgageCalculator instanceof InterestCompensationMortgageCalculator) {
      new CompensationPaymentsWindow(customization, (InterestCompensationMortgageCalculator) mortgageCalculator);
    }

  }

  protected void showInterestRateDevelopmentChart() {
    new MortgageInterestRateDevelopmentWindow(customization, mortgage, mortgages.getInterestRateSets());
  }
  
  private void dumpDataToFile() {
    FileChooser fileChooser = componentFactory.createFileChooser("Select file to dump the data to");
    ExtensionFilter extensionFilter = new ExtensionFilter("Text", "*.txt");
    fileChooser.getExtensionFilters().add(extensionFilter);
    File file = fileChooser.showSaveDialog(this);
    if (file != null) {
      dumpDataToFile(file);
//      statusBar.setText("Aflos nota gegenereerd naar: " + file.getAbsolutePath());    
    }
  }
  
  private void dumpDataToFile(File file) {
    try {
      TextWriter textWriter = new TextWriter(new OutputStreamWriter(new FileOutputStream(file)));
      for (Mortgage mortgage: mortgages.getMortgages()) {
        if (mortgage instanceof InterestCompensationMortgage) {
          InterestCompensationMortgageUtil.dumpData((InterestCompensationMortgage) mortgage, textWriter);
        } else {
          MortgageUtil.dumpData(mortgage, textWriter);
        }
      }

      textWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  protected void generateRedemptionInvoice() {
    FileChooser fileChooser = componentFactory.createFileChooser("Select file to save the redemption invoice to");
    ExtensionFilter extensionFilter = new ExtensionFilter("Portable Data Format", "*.pdf");
    fileChooser.getExtensionFilters().add(extensionFilter);
    File file = fileChooser.showSaveDialog(this);
    if (file != null) {
      MortgageReportsGenerator.generateRedemptionInvoice(mortgageCalculator, file);
//      statusBar.setText("Aflos nota gegenereerd naar: " + file.getAbsolutePath());    
    }
  }
  
  private void newMortgage() {
    new MortgageClosingDataEditor(customization, mortgages.getMortgages());
  }
  
  private void editMortgage() {
    new MortgageClosingDataEditor(customization, mortgage);
  }

  private void showPropertiesEditor() {
    new PropertiesEditor("Finan properties", customization, FinanRegistry.propertyDescriptorsResource, FinanRegistry.customPropertiesFile);
  }
  
  private void addNewInterestRateEvent() {
    LOGGER.severe("=>");
  }
  
  private void addNewFinalPaymentEvent() {
    LOGGER.severe("=>");
  }
  
  private void showFixedEvents() {
    LOGGER.severe("=>");
    mortgageEventsTable.setMortgageEvents(null, mortgage.getMortgageEvents());
  }
  
  private void showAllEvents() {
    LOGGER.severe("=>");
    mortgageEventsTable.setMortgageEvents(null, mortgageCalculator.getCalculatedMortgageEvents());
  }
  
  /**
   * Update the window title.
   * <p>
   * The format of the title is: &lt;window-title&gt; - &lt;dirty&gt;&lt;file-name&gt;<br/>
   * Where:<br/>
   * &lt;window-title&gt; is the base title obtained via from the translations, via the key 'MortgagesWindow.windowTitle'<br/>
   * &lt;dirty&gt; is a '*' symbol if the data file is dirty, empty otherwise.<br/>
   * &lt;file-name&gt; is the name of the file being operated on, or '&lt;NoName&gt' if there's no file name available.
   */
  private void updateTitle() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(TRANSLATIONS.getString("MortgagesWindow.windowTitle"));
    buf.append(" - ");
    if (mortgagesResource.isDirty()) {
      buf.append("*");
    }
    String fileName = mortgagesResource.getFileName();
    if (fileName.equals("")) {
      fileName = "<NoName>";
    }
    buf.append(fileName);
    
    setTitle(buf.toString());
  }

  @Override
  public void notifyChanged(Notification notification) {
    LOGGER.info("=>");
    
    // React to changes in the list of mortgages.
    Object feature = notification.getFeature();
    if (feature instanceof EReference) {
      EReference eReference = (EReference) feature;
      
      if (eReference.equals(MortgagePackage.eINSTANCE.getMortgages_Mortgages())) {
        handleMortgagesChanged();        
      }
    }
    
    // React to changes in the current mortgage
    Object notifier = notification.getNotifier();
    if (notifier.equals(mortgage)) {
      handleNewHypotheekSelected();
    }
    
    LOGGER.info("<=");
  }
}

/**
 * This wrapper class is only adding a toString to a Hypotheek.
 */
class MortgageWrapper {
  Mortgage mortgage;

  public MortgageWrapper(Mortgage mortgage) {
    this.mortgage = mortgage;
  }
  
  protected Mortgage getMortgage() {
    return mortgage;
  }

  public String toString() {
    return mortgage.getId();
  }
}
