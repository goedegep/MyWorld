package goedegep.app.finan.guifx;

import java.util.logging.Logger;

import goedegep.app.finan.finanapp.FinanMainWindow;
import goedegep.app.finan.finanapp.guifx.FinanResourcesFx;
import goedegep.app.finan.gen.AppModules;
import goedegep.app.finan.registry.FinanRegistry;
import goedegep.appgen.ImageSize;
import goedegep.appgen.swing.Customizations;
import goedegep.finan.Finan;
import goedegep.finan.investmentinsurances.app.guifx.InvestmentInsurancesOverviewWindow;
import goedegep.finan.jobappointment.guifx.JobAppointmentWindow;
import goedegep.finan.mortgage.app.guifx.MortgagesWindow;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.CustomizationsFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.MenuUtil;
import goedegep.jfx.PropertyDescriptorsEditorFx;
import goedegep.properties.app.guifx.PropertiesEditor;
import goedegep.rolodex.app.RolodexRegistry;
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
  private static final String WINDOW_TITLE   = "Finan";
  
  private Finan finan;
  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;
  private FinanResourcesFx appResources;

  /**
   * Constructor.
   * 
   * @param customization GUI customization.
   * @param finan the financial information.
   */
  public FinanMenuWindow(CustomizationFx customization, Finan finan) {
    super(WINDOW_TITLE, customization);
    
    this.customization = customization;
    this.finan = finan;
//    PropertyDescriptorGroup propertyDescriptorGroup = FinanRegistry.propertyDescriptorsResource.getEObject();
//    String customPropertiesFileName = null;
    if (FinanRegistry.projectPath != null) {
//      Path filePath = Paths.get(FinanRegistry.projectPath, FinanRegistry.customPropertiesFile);
//      customPropertiesFileName = filePath.toAbsolutePath().toString();
    } else {
//      customPropertiesFileName = FinanRegistry.customPropertiesFile;
    }
    
//    EMFResource<PropertyGroup> propertiesResource = new EMFResource<>(
//        PropertiesPackage.eINSTANCE,
//        () -> PropertiesFactory.eINSTANCE.createPropertyGroup());
//    PropertyGroup propertyGroup;
//    try {
//      propertyGroup = propertiesResource.load(customPropertiesFileName);
//      LOGGER.severe(propertyGroup.toString());
//    } catch (FileNotFoundException e) {
//      e.printStackTrace();
//      System.exit(-1);
//    }
    
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

    applicationButton = componentFactory.createToolButton("Finan Classic", appResources.getApplicationImage(ImageSize.SIZE_0), "Start the 'old' Finan app");
    applicationButton.setOnAction(event -> new FinanMainWindow(Customizations.getCustomization("FINAN"), false, false, RolodexRegistry.rolodexResource));
    grid.add(applicationButton, 0, 0);

    AppResourcesFx appResourcesHypotheek = CustomizationsFx.getCustomization(AppModules.FINAN_HYPOTHEEK.name()).getResources();
    applicationButton = componentFactory.createToolButton("Mortgages", appResourcesHypotheek.getApplicationImage(ImageSize.SIZE_0), "Mortgages");
    applicationButton.setOnAction(event -> new MortgagesWindow(CustomizationsFx.getCustomization(AppModules.FINAN_HYPOTHEEK.name()), finan.getRolodex()));
    grid.add(applicationButton, 1, 0);

    applicationButton = componentFactory.createToolButton("Investment Insurances", appResources.getApplicationImage(ImageSize.SIZE_0), "Investment Insurances");
    applicationButton.setOnAction(event -> new InvestmentInsurancesOverviewWindow(CustomizationsFx.getCustomization(AppModules.FINAN.name())));
    grid.add(applicationButton, 2, 0);

    AppResourcesFx appResourcesAanstelling = CustomizationsFx.getCustomization(AppModules.FINAN_AANSTELLING.name()).getResources();
    applicationButton = componentFactory.createToolButton("Job Appointment", appResourcesAanstelling.getApplicationImage(ImageSize.SIZE_0), "Start the 'Job Appointment' app");
    applicationButton.setOnAction(event -> {
        new JobAppointmentWindow(CustomizationsFx.getCustomization(AppModules.FINAN_AANSTELLING.name()));
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
    if (FinanRegistry.developmentMode) {
      MenuUtil.addMenuItem(menu, "Edit Property Descriptors", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          showPropertyDescriptorsEditor();
        }
      });
      
      MenuUtil.addMenuItem(menu, "Edit Properties", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          showPropertiesEditor();
        }
      });
      
    }
    
    menuBar.getMenus().add(menu);
    
    return menuBar;
  }
  
  private void showPropertyDescriptorsEditor() {
    new PropertyDescriptorsEditorFx(customization, FinanRegistry.propertyDescriptorsResource);
  }
  
  private void showPropertiesEditor() {
    new PropertiesEditor("Vacation properties", customization, FinanRegistry.propertyDescriptorsResource, FinanRegistry.customPropertiesFile);
  }
}