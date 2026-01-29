package goedegep.finan.gui;

import java.util.logging.Logger;

import goedegep.finan.investmentinsurances.gui.InvestmentInsurancesOverviewWindow;
import goedegep.finan.jobappointment.gui.JobAppointmentWindow;
import goedegep.finan.jobappointment.svc.JobAppointmentService;
import goedegep.finan.logic.FinanRegistry;
import goedegep.finan.mortgage.gui.MortgagesWindow;
import goedegep.finan.mortgage.svc.MortgageService;
import goedegep.finan.svc.FinanService;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.MenuUtil;
import goedegep.resources.ImageSize;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class FinanMenuWindow extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(FinanMenuWindow.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final String WINDOW_TITLE   = "Finan";
  
  private CustomizationFx customization;
  private FinanResourcesFx appResources;
  private FinanRegistry finanRegistry;
  private FinanService finanService;

  /**
   * Constructor.
   * 
   * @param customization GUI customization.
   * @param finan the financial information.
   */
  public FinanMenuWindow(CustomizationFx customization) {
    super(customization, WINDOW_TITLE);
    this.customization = customization;
    finanRegistry = FinanRegistry.getInstance();
    finanService = FinanService.getInstance();
        
    componentFactory = getComponentFactory();
    appResources = (FinanResourcesFx) getResources();
    
    createGUI();
  }

  /**
   * Create the GUI.
   * <p>
   * The root layout is a BorderPane.<br/>
   * Top is the menu bar.
   * The center is a background image, with the application buttons on top.
   */
  private void createGUI() {
    BorderPane rootLayout = new BorderPane();
    
    rootLayout.setTop(createMenuBar());
    
    StackPane mainLayout = new StackPane();

    // Add the background image
    ImageView backGroundImageView = new ImageView(appResources.getPicture());
    backGroundImageView.setPreserveRatio(true);
    backGroundImageView.setSmooth(true);
    mainLayout.getChildren().add(backGroundImageView);

    // Add the buttons
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(50, 50, 50, 50));

    Button applicationButton;

    AppResourcesFx appResourcesHypotheek = MortgageService.getInstance().getCustomization().getResources();
    applicationButton = componentFactory.createToolButton("Mortgages", appResourcesHypotheek.getApplicationImage(ImageSize.SIZE_0), "Mortgages");
    applicationButton.setOnAction(_ -> new MortgagesWindow(MortgageService.getInstance().getCustomization()));
    grid.add(applicationButton, 1, 0);

    applicationButton = componentFactory.createToolButton("Investment Insurances", appResources.getApplicationImage(ImageSize.SIZE_0), "Investment Insurances");
    applicationButton.setOnAction(_ -> new InvestmentInsurancesOverviewWindow(customization));
    grid.add(applicationButton, 2, 0);

    AppResourcesFx appResourcesAanstelling = JobAppointmentService.getInstance().getCustomization().getResources();
    applicationButton = componentFactory.createToolButton("Job Appointment", appResourcesAanstelling.getApplicationImage(ImageSize.SIZE_0), "Start the 'Job Appointment' app");
    applicationButton.setOnAction(_ -> {
        new JobAppointmentWindow(JobAppointmentService.getInstance().getCustomization());
    });
    grid.add(applicationButton, 3, 0);

    mainLayout.getChildren().add(grid);

    rootLayout.setCenter(mainLayout);

    setScene(new Scene(rootLayout));
  }

  /**
   * Create the menu bar for this window.
   * 
   * @return the menu bar for this window.
   */
  private MenuBar createMenuBar() {
    MenuBar menuBar = new MenuBar();
    Menu menu;

    // File menu
    menu = new Menu("File");
    
    // Bestand: property descriptors bewerken
    if (finanRegistry.isDevelopmentMode()) {
      MenuUtil.addMenuItem(menu, "Edit Property Descriptors", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          finanService.showPropertyDescriptorsEditor();
        }
      });
    }
      
    MenuUtil.addMenuItem(menu, "Edit Properties", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        finanService.showPropertiesEditor();
      }
    });
    
    menuBar.getMenus().add(menu);

    // Help menu
    menu = new Menu("Help");

    // Help: About
    MenuUtil.addMenuItem(menu, "About", _ -> showHelpAboutDialog());
    
    menuBar.getMenus().add(menu);
    
    return menuBar;
  }

  /**
   * Show a dialog with information about this application.
   */
  private void showHelpAboutDialog() {
    componentFactory.createApplicationInformationDialog(
        "About " + finanRegistry.getApplicationName(),
        appResources.getApplicationImage(ImageSize.SIZE_3),
        null, 
        finanRegistry.getShortProductInfo() + NEWLINE +
        "Version: " + finanRegistry.getVersion() + NEWLINE +
        finanRegistry.getCopyrightMessage() + NEWLINE +
        "Author: " + finanRegistry.getAuthor() + 
        (finanRegistry.isDevelopmentMode() ? (NEWLINE + NEWLINE + "Running in development mode!") : "")
        )
        .showAndWait();
  }
}