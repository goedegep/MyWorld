package goedegep.rolodex.app.guifx;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.JfxUtil;
import goedegep.jfx.MenuUtil;
import goedegep.jfx.PropertyDescriptorsEditorFx;
import goedegep.properties.app.guifx.PropertiesEditor;
import goedegep.resources.ImageSize;
import goedegep.rolodex.app.RolodexRegistry;
import goedegep.rolodex.app.logic.AddressBookExporter;
import goedegep.rolodex.model.Employee;
import goedegep.rolodex.model.Family;
import goedegep.rolodex.model.Institution;
import goedegep.rolodex.model.Person;
import goedegep.rolodex.model.Phone;
import goedegep.rolodex.model.PhoneAddressBookEntry;
import goedegep.rolodex.model.PhoneNumber;
import goedegep.rolodex.model.Rolodex;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class RolodexMenuWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(RolodexMenuWindow.class.getName());
  private static final String WINDOW_TITLE   = "Rolodex";
  private static final String NEWLINE = System.getProperty("line.separator");
  
  private AdminResourcesFx appResources;
  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;
  private Rolodex rolodex;

  public RolodexMenuWindow(CustomizationFx customization) {
    super(customization, WINDOW_TITLE);
    
    this.customization = customization;
    
    componentFactory = getComponentFactory();
    appResources = (AdminResourcesFx) getResources();
    rolodex = RolodexRegistry.rolodexResource.getEObject();

    createGUI();
    
    setOnCloseRequest(event -> {
      closeWindowEvent(event);
    });
    
    RolodexRegistry.rolodexResource.dirtyProperty().addListener(new ChangeListener<Boolean>() {

      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        LOGGER.info("=>");
        updateWindowTitle();
      }
      
    });    
  }

  
  /**
   * Create the GUI.
   * <p>
   * The root layout is a BorderPane.<br/>
   * The center is a background image, with the application buttons on top.
   */
  private void createGUI() {
    BorderPane rootLayout = new BorderPane();

    rootLayout.setTop(createMenuBar());

    StackPane mainLayout = new StackPane();
    
    // Add the background image
    ImageView backGroundImageView = new ImageView(appResources.getPicture());
    backGroundImageView.setPreserveRatio(true);
    backGroundImageView.setFitWidth(700);
    backGroundImageView.setSmooth(true);
    mainLayout.getChildren().add(backGroundImageView);
        
    // Add the buttons
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(50, 50, 50, 50));
    
    Button applicationButton;
    
    applicationButton = createToolButton("Address book", appResources.getLargeAddressBookImage());
    applicationButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        showAddressesWindow();
      }
      
    });
    grid.add(applicationButton, 0, 0);
    
    applicationButton = createToolButton("Phone book", appResources.getLargePhoneBookImage());
    applicationButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        showPhoneNumbersWindow();
      }
      
    });
    grid.add(applicationButton, 1, 0);
    
    applicationButton = createToolButton("Birthdays", appResources.getLargeBirthdayImage());
    applicationButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        showBirthdaysWindow();
      }
      
    });
    grid.add(applicationButton, 2, 0);
    
    applicationButton = createToolButton("Phones", appResources.getLargePhonesImage());
    applicationButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        showPhonesWindow();
      }
      
    });
    grid.add(applicationButton, 3, 0);
    
    mainLayout.getChildren().add(grid);
        
    rootLayout.setCenter(mainLayout);

    setScene(new Scene(rootLayout));
  }

  private MenuBar createMenuBar() {
    MenuBar menuBar = componentFactory.createMenuBar();
    Menu menu;

    // File menu
    menu = componentFactory.createMenu("File");
    
    // File: Save Rolodex
    MenuItem menuItem = componentFactory.createMenuItem("Save Rolodex");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        saveRolodex();
      }
    });
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
    menu.getItems().add(menuItem);
    
    // File: Export to PDF
    MenuUtil.addMenuItem(menu, "Export to PDF", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        exportToPdf();
      }
    });
    
    // File: Check Rolodex
    MenuUtil.addMenuItem(menu, "Check Rolodex", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        checkRolodex();
      }
    });
    
    // File: Rolodex Check Window
    MenuUtil.addMenuItem(menu, "Rolodex Check Window", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        checkRolodexWindow();
      }
    });

    // Bestand: property descriptors bewerken
    if (RolodexRegistry.developmentMode) {
      MenuUtil.addMenuItem(menu, "Edit Property Descriptors", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          showPropertyDescriptorsEditor();
        }
      });
    }

    // Bestand: Gebruikers instellingen bewerken
    MenuUtil.addMenuItem(menu, "Edit user settings", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        showPropertiesEditor();
      }
    });

    menuBar.getMenus().add(menu);


    // Tabellen menu
    menu = componentFactory.createMenu("Tables");
    
    // Tables: Countries
    MenuUtil.addMenuItem(menu, "Countries", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        showCountriesWindow();
      }
    });
    
    // Tables: Cities
    MenuUtil.addMenuItem(menu, "Cities", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        showCitiesWindow();
      }
    });
    
    // Tables: Addresses
    MenuUtil.addMenuItem(menu, "Addresses", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        adressenTabel_actionPerformed();
      }
    });
    
    // Tables: Persons
    MenuUtil.addMenuItem(menu, "Persons", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        personenTabel_actionPerformed();
      }
    });
    
    // Tables: Families
    MenuUtil.addMenuItem(menu, "Families", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        familiesTabel_actionPerformed();
      }
    });
    
    // Tables: Institutions
    MenuUtil.addMenuItem(menu, "Institutions", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        instellingenTabel_actionPerformed();
      }
    });
    
    // Tables: Employees
    MenuUtil.addMenuItem(menu, "Employees", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        medewerkersTabel_actionPerformed();
      }
    });
    
    // Tables: Phonenumbers
    MenuUtil.addMenuItem(menu, "Phonenumbers", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        telefoonnummersTabel_actionPerformed();
      }
    });
    
    // Tables: Phone addressbooks
    MenuUtil.addMenuItem(menu, "Phone addressbooks", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        telefoonAdresboekenTabel_actionPerformed();
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

    menuBar.getMenus().add(menu);

    return menuBar;
  }

  private Button createToolButton(String text, Image image) {
    double buttonSize = 175;
    Button button = new Button(text);
    button.setMaxWidth(buttonSize);
    button.setMinWidth(buttonSize);
    button.setMinHeight(buttonSize);
    button.wrapTextProperty().setValue(true);
    
    String hex = JfxUtil.colorToCssString(getLook().getPanelBackgroundColor());
    button.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold; -fx-background-color: " + hex + ";");
    
    if (image != null) {
      ImageView buttonImageView = new ImageView(image);
      buttonImageView.setFitWidth(buttonSize - 35);
      buttonImageView.setPreserveRatio(true);
      buttonImageView.setSmooth(true);
      button.setGraphic(buttonImageView);
      button.setContentDisplay(ContentDisplay.TOP);
    }

    ColorAdjust colorAdjust = new ColorAdjust();
    final double normalBrightnessAdjust = -0.1;
    colorAdjust.setBrightness(normalBrightnessAdjust);

    button.setEffect(colorAdjust);
    button.setOnMouseEntered(e -> {

      Timeline highlightTimeline = new Timeline(
          new KeyFrame(Duration.seconds(0.2), new KeyValue(colorAdjust.brightnessProperty(), 0.4, Interpolator.LINEAR)));
      highlightTimeline.play();

    });
    button.setOnMouseExited(e -> {

      Timeline backToNormalTimeline = new Timeline(
          new KeyFrame(Duration.seconds(1), new KeyValue(colorAdjust.brightnessProperty(), normalBrightnessAdjust, Interpolator.LINEAR)
              ));
      backToNormalTimeline.play();

    });

    return button;
  }
  
  public void showAddressesWindow() {
    new AddressBookWindowFx(customization, rolodex);
  }
  
  public void showPhoneNumbersWindow() {
    new PhoneBookWindow(customization, rolodex);
  }
  
  public void showBirthdaysWindow() {
    new BirthdaysWindow(customization, rolodex);
  }
  
  public void showPhonesWindow() {
    new PhoneMemoriesWindow(customization, rolodex);
  }

  /**
   * Save the rolodex to file.
   */
  private void saveRolodex() {
    // Save the contents of the resource to the file system.
    try {
      RolodexRegistry.rolodexResource.save();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    updateWindowTitle();
  }
  
  private void exportToPdf() {
    AddressBookExporter adressBookExporter = new AddressBookExporter(customization, rolodex);
    
    Path pdfFilePath = Paths.get("C:\\Users\\Peter\\Downloads\\Adreslijst" + ".pdf");

    adressBookExporter.exportAddressBookToPdf(pdfFilePath);
  }

  /**
   * Perform checks on the Rolodex database.
   * *Check whether there are Countries which aren't referred to.
   *   Country is only used in City.
   * *Check whether there are Cities which aren't referred to.
   *   City is only used in Address.
   * *Check whether there are Adresses which aren't referred to.
   *  Address is used in Person, Family and Institution.
   * *Check whether there are PhoneNumbers which aren't referred to.
   *  PhoneNumber is used in Person, Family and Institution, and in PhoneAddressBookEntry.
   * @return
   */
  private void checkRolodex() {    
        
    Set<PhoneNumber> usedPhoneNumbers = new HashSet<>();
    for (Person person: rolodex.getPersonList().getPersons()) {
      for (PhoneNumber phoneNumber: person.getPhoneNumbers()) {
        usedPhoneNumbers.add(phoneNumber);
      }
    }
    for (Family family: rolodex.getFamilyList().getFamilies()) {
      for (PhoneNumber phoneNumber: family.getPhoneNumbers()) {
        usedPhoneNumbers.add(phoneNumber);
      }
    }
    for (Institution institution: rolodex.getInstitutionList().getInstitutions()) {
      for (PhoneNumber phoneNumber: institution.getPhoneNumbers()) {
        usedPhoneNumbers.add(phoneNumber);
      }
    }
    for (Employee employee: rolodex.getEmployeeList().getEmployees()) {
      for (PhoneNumber phoneNumber: employee.getPhoneNumbers()) {
        usedPhoneNumbers.add(phoneNumber);
      }
    }
    for (Phone phone: rolodex.getPhoneList().getPhones()){
      for (PhoneAddressBookEntry phoneAddressBookEntry: phone.getPhoneAddressBook().getEntries()) {
        usedPhoneNumbers.add(phoneAddressBookEntry.getPhoneNumber());
      }
    }
    for (PhoneNumber phoneNumber: rolodex.getPhoneNumberList().getPhoneNumbers()) {
      if (!usedPhoneNumbers.contains(phoneNumber)) {
        System.out.println("Telefoonnummer \'" + phoneNumber.toString() + "\' wordt niet gebruikt (dus kan verwijderd worden)");
      }
    }
    
  }
  
  private void checkRolodexWindow() {
    new RolodexCheckWindow(customization, rolodex);
  }

  private void showPropertyDescriptorsEditor() {
    new PropertyDescriptorsEditorFx(customization, RolodexRegistry.propertyDescriptorsResource);
  }

  private void showPropertiesEditor() {
    new PropertiesEditor("Rolodex properties", customization, RolodexRegistry.propertyDescriptorsResource, RolodexRegistry.customPropertiesFile);
  }
  
  protected void telefoonAdresboekenTabel_actionPerformed() {
    new PhonesWindowFx(customization, rolodex);
  }

  protected void telefoonnummersTabel_actionPerformed() {
    new PhoneNumbersWindowFx(customization, rolodex);
  }

  protected void instellingenTabel_actionPerformed() {
    new InstitutionsWindowFx(customization, rolodex);
  }

  protected void medewerkersTabel_actionPerformed() {
    new EmployeesWindowFx(customization, rolodex);
  }

  protected void familiesTabel_actionPerformed() {
    new FamiliesWindowFx(customization, rolodex);
  }

  protected void personenTabel_actionPerformed() {
    new PersonsWindowFx(customization, rolodex);
  }

  protected void adressenTabel_actionPerformed() {
    new AddressesWindowFx(customization, rolodex);
  }

  protected void showCountriesWindow() {
    new CountriesWindowFx(customization, rolodex);
  }

  protected void showCitiesWindow() {
    new CitiesWindowFx(customization, rolodex);
  }
  
  /**
   * Show the dialog with information about this application.
   */
  private void showHelpAboutDialog() {
    componentFactory.createApplicationInformationDialog(
        "About the Rolodex application",
        appResources.getApplicationImage(ImageSize.SIZE_3),
        null, 
        RolodexRegistry.shortProductInfo + NEWLINE +
        "Versie: " + RolodexRegistry.version + NEWLINE +
        RolodexRegistry.copyrightMessage + NEWLINE +
        "Auteur: " + RolodexRegistry.author)
        .showAndWait();
  }

  /**
   * This method handles a 'close window' event.
   * <p>
   * If there are no unsaved changes in the Rolodex, just exit.<br/>
   * If there are unsaved changes, a dialog is shown where the user can choose between:
   * <ul>
   * <li>Saving the changes and then exit.</li>
   * <li>Exit without saving</li>
   * <li>No action (don't exit)</li>
   * </ul>
   * @param event a <code>WindowEvent</code>
   */
  private void closeWindowEvent(WindowEvent event) {
    LOGGER.info("Window close request ...");

    if(RolodexRegistry.rolodexResource.isDirty()) {
      Alert alert = componentFactory.createOkCancelConfirmationDialog("Close Rolodex?", null, "There are unsaved changes in the Rolodex. Do you want to save changes before closing?");
      alert.getButtonTypes().remove(ButtonType.OK);
      alert.getButtonTypes().add(ButtonType.YES);
      alert.getButtonTypes().add(ButtonType.NO);
      Optional<ButtonType> res = alert.showAndWait();

      if(res.isPresent()) {
        ButtonType buttonType = res.get();
        if(buttonType.equals(ButtonType.CANCEL)) {
          event.consume();
        } else if (buttonType.equals(ButtonType.NO)) {  // NOPMD
          // no action
        } else if (buttonType.equals(ButtonType.YES)) {
          saveRolodex();
        }
      }
    }
  }
  
  /**
   * Update the window title.
   * <p>
   * The window title starts with a '*' if the rolodex has been modified since it was last saved. 
   */
  private void updateWindowTitle() {
    if (RolodexRegistry.rolodexResource.isDirty()) {
      setTitle("*" + WINDOW_TITLE);
    } else {
      setTitle(WINDOW_TITLE);
    }
  }
}
