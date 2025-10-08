package goedegep.myworld.app.guifx;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.CustomizationsFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.JfxUtil;
import goedegep.jfx.MenuUtil;
import goedegep.jfx.PropertyDescriptorsEditorFx;
import goedegep.media.app.guifx.MediaMenuWindow;
import goedegep.myworld.app.MyWorldAppModule;
import goedegep.myworld.app.MyWorldRegistry;
import goedegep.pctools.app.guifx.PCToolsMenuWindow;
import goedegep.properties.app.guifx.PropertiesEditor;
import goedegep.properties.model.PropertyDescriptor;
import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.resources.ImageSize;
import goedegep.unitconverter.app.guifx.UnitConverterWindow;
import goedegep.util.emf.EMFResource;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MyWorldMenuWindowFx extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(MyWorldMenuWindowFx.class.getName());
  private final static String NEWLINE = System.getProperty("line.separator");
  private static final String WINDOW_TITLE   = "MyWorld main menu";
  
  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;
  private MyWorldAppResourcesFx appResources;

  public MyWorldMenuWindowFx(CustomizationFx customization) {
    super(customization, WINDOW_TITLE + "\"getting version\"");
    LOGGER.setLevel(Level.SEVERE);
    
    this.customization = customization;
    
    componentFactory = getComponentFactory();
    appResources = (MyWorldAppResourcesFx) getResources();
    
    createGUI();

    setTitleWithVersionInfo();
  }
  
  /**
   * Create the GUI.
   * <p>
   * Root layout is a BorderPane.<br/>
   * <ul>
   * <li>
   * Top is the MenuBar.
   * </li>
   * <li>
   * Center is a background image, with the application buttons on top.
   * </li>
   * </ul>
   * 
   */
  private void createGUI() {

    BorderPane rootLayout = new BorderPane();

    /*
     * Top
     */
    rootLayout.setTop(createMenuBar());
    
    /*
     * Center
     */
    StackPane mainLayout = new StackPane();
    
    // Add the background image
    ImageView backGroundImageView = new ImageView(appResources.getPicture());
    backGroundImageView.setFitWidth(1200);
    backGroundImageView.setPreserveRatio(true);
    backGroundImageView.setSmooth(true);
    mainLayout.getChildren().add(backGroundImageView);
    
    // Add the buttons
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(50, 200, 100, 600));
    
    Button applicationButton;
    
    // Media
    applicationButton = createModuleButton(
        MyWorldAppModule.MEDIA.getModuleName(),
        CustomizationsFx.getCustomization(MyWorldAppModule.MEDIA.name()).getResources().getApplicationImage(ImageSize.SIZE_3));
    applicationButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        Stage stage = new MediaMenuWindow(CustomizationsFx.getCustomization(MyWorldAppModule.MEDIA.name()));
        stage.show();
      }
      
    });
    grid.add(applicationButton, 2, 0);
    
    // UnitConverter
    applicationButton = createModuleButton(
        MyWorldAppModule.UNIT_CONVERTER.getModuleName(),
        CustomizationsFx.getCustomization(MyWorldAppModule.UNIT_CONVERTER.name()).getResources().getApplicationImage(ImageSize.SIZE_3));
    applicationButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        Stage stage = new UnitConverterWindow(CustomizationsFx.getCustomization(MyWorldAppModule.UNIT_CONVERTER.name()));
        stage.show();
      }
      
    });
    grid.add(applicationButton, 1, 1);
        
    // PC Tools
    applicationButton = createModuleButton(
        MyWorldAppModule.PCTOOLS.getModuleName(),
        CustomizationsFx.getCustomization(MyWorldAppModule.PCTOOLS.name()).getResources().getApplicationImage(ImageSize.SIZE_3));
    applicationButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        Stage stage = new PCToolsMenuWindow(CustomizationsFx.getCustomization(MyWorldAppModule.PCTOOLS.name()), null);
        stage.centerOnScreen();
        stage.show();
      }
      
    });
    grid.add(applicationButton, 2, 2);
   
    mainLayout.getChildren().add(grid);
    
    rootLayout.setCenter(mainLayout);
    
    /*
     *  Create and set funny cursor.
     */
    Image image = appResources.getEmileImage();
    Cursor cursor = new ImageCursor(image, 0d, 0d);
    rootLayout.setCursor(cursor);

    setScene(new Scene(rootLayout));
  }

  /**
   * Set the title, which contains the version information.
   * <p>
   * The version information is obtained from the 'version' property in the PropertyDescriptorGroup of this application.
   */
  private void setTitleWithVersionInfo() {
    String versionInfo = "";

    try {
      EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource = MyWorldRegistry.propertyDescriptorsResource;
      PropertyDescriptorGroup propertyDescriptorGroup = propertyDescriptorsResource.getEObject();
      PropertyDescriptor versionPropertyDescriptor = propertyDescriptorGroup.getPropertyDescriptor("version");
      if (versionPropertyDescriptor != null) {
        String version = versionPropertyDescriptor.getInitialValue();
        versionInfo = " (" + version + ")";
      } else {
        versionInfo = " - property descriptor for \"version\" not found";
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }

    setTitle(WINDOW_TITLE + versionInfo);
  }

  private MenuBar createMenuBar() {
    MenuBar menuBar = componentFactory.createMenuBar();
    Menu menu;

    // Bestand menu
    menu = componentFactory.createMenu("File");

    if (MyWorldRegistry.developmentMode) {
      // File: Edit Property Descriptors
      MenuUtil.addMenuItem(menu, "Edit Property Descriptors", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          showPropertyDescriptorsEditor();
        }
      });
    }

    // File: Edit User Settings
    MenuUtil.addMenuItem(menu, "Edit User Settings", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        showUserSettingsEditor();
      }
    });

    menuBar.getMenus().add(menu);


    // Help menu
    menu = componentFactory.createMenu("Help");

    // Help: About
    MenuUtil.addMenuItem(menu, "About", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        showHelpAboutDialog();
      }
    });

    // Help: MyWorld Online Manual
    MenuUtil.addMenuItem(menu, "MyWorld Online Manual", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        showMyWorldOnlineManual();
      }
    });

    menuBar.getMenus().add(menu);

    return menuBar;
  }
  
  private Button createModuleButton(String text, Image image) {
    Button button = new Button(text);
    button.setMaxWidth(100);
    button.setMinWidth(100);
    button.setMinHeight(100);
    button.wrapTextProperty().setValue(true);
    
    String hex = JfxUtil.colorToCssString(getLook().getPanelBackgroundColor());
    button.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold; -fx-background-color: " + hex + ";");
    
    ImageView buttonImageView = new ImageView(image);
    buttonImageView.setFitWidth(48);
    buttonImageView.setPreserveRatio(true);
    buttonImageView.setSmooth(true);
    button.setGraphic(buttonImageView);
    button.setContentDisplay(ContentDisplay.TOP);

    ColorAdjust colorAdjust = new ColorAdjust();
    final double normalBrightnessAdjust = -0.1;
    colorAdjust.setBrightness(normalBrightnessAdjust);

    buttonImageView.setEffect(colorAdjust);
    button.setOnMouseEntered(_ -> {

      Timeline highlightTimeline = new Timeline(
          new KeyFrame(Duration.seconds(0.2), new KeyValue(colorAdjust.brightnessProperty(), 0.4, Interpolator.LINEAR)));
      highlightTimeline.play();

    });
    button.setOnMouseExited(_ -> {

      Timeline backToNormalTimeline = new Timeline(
          new KeyFrame(Duration.seconds(1), new KeyValue(colorAdjust.brightnessProperty(), normalBrightnessAdjust, Interpolator.LINEAR)
              ));
      backToNormalTimeline.play();

    });

    return button;
  }

  private void showPropertyDescriptorsEditor() {
    new PropertyDescriptorsEditorFx(customization, MyWorldRegistry.propertyDescriptorsResource);
  }
  
  private void showUserSettingsEditor() {
    PropertiesEditor propertiesEditor = new PropertiesEditor("Edit MyWorld settings", customization,
        MyWorldRegistry.propertyDescriptorsResource, MyWorldRegistry.customPropertiesFile);
    propertiesEditor.show();
  }
  
  /**
   * Show the dialog with information about this application.
   */
  private void showHelpAboutDialog() {
    componentFactory.createApplicationInformationDialog(
        "About MyWorld" + " running on " + getComputerName(),
        appResources.getApplicationImage(ImageSize.SIZE_3),
        null, 
        MyWorldRegistry.shortProductInfo + NEWLINE +
        "Version: " + MyWorldRegistry.version + NEWLINE +
        MyWorldRegistry.copyrightMessage + NEWLINE +
        "Author: " + MyWorldRegistry.author)
        .showAndWait();
  }
  
  /**
   * Open a browser with the online manual.
   */
  private void showMyWorldOnlineManual() {
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
      try {
        Desktop.getDesktop().browse(new URI("https://petersdigitallife.nl/myworld-user-manual/myworld-overview/"));
      } catch (IOException e) {
        e.printStackTrace();
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
    }
  }

  private String getComputerName()
  {
      Map<String, String> env = System.getenv();
      if (env.containsKey("COMPUTERNAME"))
          return env.get("COMPUTERNAME");
      else if (env.containsKey("HOSTNAME"))
          return env.get("HOSTNAME");
      else
          return "Unknown Computer";
  }
}
