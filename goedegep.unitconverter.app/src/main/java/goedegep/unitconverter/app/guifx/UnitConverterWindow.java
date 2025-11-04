package goedegep.unitconverter.app.guifx;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Duration;
import java.util.Locale;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.MenuUtil;
import goedegep.resources.ImageSize;
import goedegep.unitconverter.app.UnitConverterRegistry;
import goedegep.unitconverter.app.UnitConverterService;
//import goedegep.util.datetime.ClockTime;
import goedegep.util.datetime.DurationFormat;
import goedegep.util.unit.UnitUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * This class provides a window for (distance) unit conversions.
 * <p>
 * It provides:
 * <ul>
 * <li>
 * Conversions from km to miles and vice versa
 * </li>
 * <li>
 * Calculations: from the 3 values distance, time and speed/tempo always one is calculated from last two entered values<br/>
 * E.g. if you enter speed and time, the distance is calculated.
 * </li>
 * <li>
 * Checkboxes, which cannot be set by the user, show the last two entered values (the ones used as input for the calculations).
 * </li>
 * <li>
 * A table with split times for a (half) marathon.
 * </li>
 * </ul>
 */
public class UnitConverterWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(UnitConverterWindow.class.getName());
  private final static String NEWLINE = System.getProperty("line.separator");
  private static final String WINDOW_TITLE   = "Eenheden calculator";

  private static final String     KM_FIELD_TOOLTIP = "vul het aantal kilometers in";
  private static final String     MILE_FIELD_TOOLTIP = "vul het aantal mijlen in";
  private static final String     HOURS_FIELD_TOOLTIP = "vul het aantal uren in";
  private static final String     MINUTES_FIELD_TOOLTIP = "vul het aantal minuten in";
  private static final String     SECONDS_FIELD_TOOLTIP = "vul het aantal seconden in";
  private static final String     SPEED_FIELD_TOOLTIP = "vul de snelheid in";

  private static final NumberFormat NF = NumberFormat.getInstance(Locale.GERMANY);
  private static final String OK_TEXT_STYLE = "-fx-text-fill: black;";
  private static final String ERROR_TEXT_STYLE = "-fx-text-fill: red;";
  
  private UnitConverterRegistry unitConverterRegistry;
  private UnitConverterAppResourcesFx appResources;
  
  private Node errorComponent;  // There can only be one error component, the last entered value. Others are overwritten with calculated or null values.
  private CheckBox afstandInKmInputCheckBox;
  private TextField afstandInKmTextField;
  private String afstandInKmTextFieldLastValue = "";
  private Double afstandInKm = null;
  private CheckBox afstandInMijlInputCheckBox;
  private TextField afstandInMijlTextField;
  private String afstandInMijlTextFieldLastValue = "";
  private Double afstandInMijl = null;
  private CheckBox tijdInputCheckBox;
  private TextField tijdUrenTextField;
  private String tijdUrenTextFieldLastValue = "";
  private Integer uren = null;
  private TextField tijdMinutenTextField;
  private String tijdMinutenTextFieldLastValue = "";
  private Integer minuten = null;
  private TextField tijdSecondenTextField;
  private String tijdSecondenTextFieldLastValue = "";
  private Integer seconden = null;
  private Duration duration = null;
  private CheckBox snelheidInKmhInputCheckBox;
  private TextField snelheidInKmhTextField;
  private String snelheidInKmhTextFieldLastValue = "";
  private Double snelheidInKmh = null;
  private CheckBox snelheidInMijlhInputCheckBox;
  private TextField snelheidInMijlhTextField;
  private String snelheidInMijlhTextFieldLastValue = "";
  private Double snelheidInMijlh = null;
  private CheckBox tempoInMinPerKmInputCheckBox;
  private TextField tempoInMinPerKmMinutenTextField;
  private String tempoInMinPerKmMinutenTextFieldLastValue = "";
  private TextField tempoInMinPerKmSecondenTextField;
  private String tempoInMinPerKmSecondenTextFieldLastValue = "";
  private CheckBox tempoInMinPerMijlInputCheckBox;
  private TextField tempoInMinPerMijlMinutenTextField;
  private String tempoInMinPerMijlMinutenTextFieldLastValue = "";
  private TextField tempoInMinPerMijlSecondenTextField;
  private String tempoInMinPerMijlSecondenTextFieldLastValue = "";
  private Integer tempoInMinPerKmMinuten = null;
  private Integer tempoInMinPerKmSeconden = null;
  private Duration tempoInMinPerKm = null;
  private Integer tempoInMinPerMijlMinuten = null;
  private Integer tempoInMinPerMijlSeconden = null;
  private Duration tempoInMinPerMijl = null;
  
  private InputType laatstIngevoerd = null;
  private InputType voorLaatstIngevoerd = null;
  
  private ObservableList<SplitTime> splitTimes = null;
  private TableView<SplitTime> splitTimesTableView = null;

  public UnitConverterWindow(CustomizationFx customization) {
    super(customization, WINDOW_TITLE);
    
    unitConverterRegistry = UnitConverterRegistry.getInstance();
    appResources = (UnitConverterAppResourcesFx) getResources();
    
    createGUI();
  }

  /**
   * Create the GUI.
   * 
   */
  private void createGUI() {
    VBox rootPane = new VBox();
    rootPane.getChildren().add(createMenuBar());
    
    GridPane mainLayout = new GridPane();
    mainLayout.setPadding(new Insets(12, 12, 12, 12));
    mainLayout.setHgap(12);
    mainLayout.setVgap(8);
    
    ColumnConstraints columnConstraints = new ColumnConstraints();
    mainLayout.getColumnConstraints().add(columnConstraints);
    columnConstraints = new ColumnConstraints();
    columnConstraints.setMaxWidth(200);
    mainLayout.getColumnConstraints().add(columnConstraints);
    columnConstraints = new ColumnConstraints();
    columnConstraints.setMaxWidth(200);
    mainLayout.getColumnConstraints().add(columnConstraints);
    
    Label label;
    FlowPane flowPane;
    
    /*
     *  First row:  Afstand:   [v] ...   km       [v] ... mijl
     */
    
    // Label: "Afstand:"
    label = componentFactory.createLabel("Afstand:");
    mainLayout.add(label, 0, 0);
    
    // FlowPane for:  [v] ...   km
    flowPane = new FlowPane();
    flowPane.setHgap(10);
    
    // Checkbox: "afstand in km".
    afstandInKmInputCheckBox = componentFactory.createCheckBox(null, false);
    afstandInKmInputCheckBox.setDisable(true);
    flowPane.getChildren().add(afstandInKmInputCheckBox);
    
    // TextField: "afstand in km"
    afstandInKmTextField = componentFactory.createTextField(60, KM_FIELD_TOOLTIP);
    afstandInKmTextField.setOnAction(_ -> handleNewValueAfstandInKmTextField());
    afstandInKmTextField.focusedProperty().addListener((_, _, newVal) -> {
      if (newVal) {
        afstandInKmTextField.selectAll();
      } else {
        handleNewValueAfstandInKmTextField();
      }
    });
    flowPane.getChildren().add(afstandInKmTextField);
    
    // Label: "km"
    label = componentFactory.createLabel("km");
    flowPane.getChildren().add(label);
    mainLayout.add(flowPane, 1, 0);
    
    // FlowPane for:  [v] ...   mijl
    flowPane = new FlowPane();
    flowPane.setHgap(10);
    
    // Checkbox: "afstand in mijl".
    afstandInMijlInputCheckBox = componentFactory.createCheckBox(null, false);
    afstandInMijlInputCheckBox.setDisable(true);
    flowPane.getChildren().add(afstandInMijlInputCheckBox);

    // TextField: "afstand in mijl"
    afstandInMijlTextField = componentFactory.createTextField(60, MILE_FIELD_TOOLTIP);
    afstandInMijlTextField.setOnAction(_ -> handleNewValueAfstandInMijlTextField());
    afstandInMijlTextField.focusedProperty().addListener((_, _, newVal) -> {
      if (newVal) {
        afstandInMijlTextField.selectAll();
      } else {
        handleNewValueAfstandInMijlTextField();
      }
    });
    flowPane.getChildren().add(afstandInMijlTextField);
    
    // Label: "mijl"
    label = componentFactory.createLabel("mijl");
    flowPane.getChildren().add(label);
    mainLayout.add(flowPane, 2, 0);
    
    
    /*
     *  Second row:  Tijd:      [v] ..:..:..       hh:mm:ss
     */
    
    // Label: "Tijd"
    label = componentFactory.createLabel("Tijd:");
    mainLayout.add(label, 0, 1);
    
    // FlowPane for: [v] ..:..:..       hh:mm:ss
    flowPane = new FlowPane();
    flowPane.setHgap(10);
    
    // Checkbox: "tijd".
    tijdInputCheckBox = componentFactory.createCheckBox(null, false);
    tijdInputCheckBox.setDisable(true);
    flowPane.getChildren().add(tijdInputCheckBox);
    
    // TextField: "hh"
    tijdUrenTextField = componentFactory.createTextField(30, HOURS_FIELD_TOOLTIP);
    tijdUrenTextField.setOnAction(_ -> handleNewValueTijdUrenTextField());
    tijdUrenTextField.focusedProperty().addListener((_, _, newVal) -> {
      if (newVal) {
        tijdUrenTextField.selectAll();
      } else {
        handleNewValueTijdUrenTextField();
      }
    });
    flowPane.getChildren().add(tijdUrenTextField);
    
    // Label: ":"
    label = componentFactory.createLabel(":");
    flowPane.getChildren().add(label);
   
    // TextField: "mm"
    tijdMinutenTextField = componentFactory.createTextField(30, MINUTES_FIELD_TOOLTIP);
    tijdMinutenTextField.setOnAction(_ -> handleNewValueTijdMinutenTextField());
    tijdMinutenTextField.focusedProperty().addListener((_, _, newVal) -> {
      if (newVal) {
        tijdMinutenTextField.selectAll();
      } else {
        handleNewValueTijdMinutenTextField();
      }
    });
    flowPane.getChildren().add(tijdMinutenTextField);
    
    // Label: ":"
    label = componentFactory.createLabel(":");
    flowPane.getChildren().add(label);

    // TextField: "ss"
    tijdSecondenTextField = componentFactory.createTextField(30, SECONDS_FIELD_TOOLTIP);
    tijdSecondenTextField.setOnAction(_ -> handleNewValueTijdSecondenTextField());
    tijdSecondenTextField.focusedProperty().addListener((_, _, newVal) -> {
      if (newVal) {
        tijdSecondenTextField.selectAll();
      } else {
        handleNewValueTijdSecondenTextField();
      }
    });
    flowPane.getChildren().add(tijdSecondenTextField);
    
    // Label: "hh:mm:ss"
    label = componentFactory.createLabel("hh:mm:ss");
    flowPane.getChildren().add(label);

    mainLayout.add(flowPane, 1, 1, 2, 1);

    
    /*
     *  Third row:  Snelheid:  [v] ...   km/h     [v] ... mijl/h
     */

    // Label: "Snelheid:"
    label = componentFactory.createLabel("Snelheid:");
    mainLayout.add(label, 0, 2);
    
    // FlowPane for: [v] ...   km/h
    flowPane = new FlowPane();
    flowPane.setHgap(10);
    
    // Checkbox: "snelheid in km/h".
    snelheidInKmhInputCheckBox = componentFactory.createCheckBox(null, false);
    snelheidInKmhInputCheckBox.setDisable(true);
    flowPane.getChildren().add(snelheidInKmhInputCheckBox);
    
    // TextField: "snelheid in km/h"
    snelheidInKmhTextField = componentFactory.createTextField(60, SPEED_FIELD_TOOLTIP);
    snelheidInKmhTextField.setOnAction(_ -> handleNewValueSnelheidInKmhTextField());
    snelheidInKmhTextField.focusedProperty().addListener((_, _, newVal) -> {
      if (newVal) {
        snelheidInKmhTextField.selectAll();
      } else {
        handleNewValueSnelheidInKmhTextField();
      }
    });
    flowPane.getChildren().add(snelheidInKmhTextField);
    
    // Label: "km/h:"
    label = componentFactory.createLabel("km/h");
    flowPane.getChildren().add(label);
    
    mainLayout.add(flowPane, 1, 2);
    
    // FlowPane for: [v] ... mijl/h
    flowPane = new FlowPane();
    flowPane.setHgap(10);
    
    // Checkbox: "snelheid in mijl/h".
    snelheidInMijlhInputCheckBox = componentFactory.createCheckBox(null, false);
    snelheidInMijlhInputCheckBox.setDisable(true);
    flowPane.getChildren().add(snelheidInMijlhInputCheckBox);
    
    // TextField: "snelheid in mijl/h"
    snelheidInMijlhTextField = componentFactory.createTextField(60, SPEED_FIELD_TOOLTIP);
    snelheidInMijlhTextField.setOnAction(_ -> handleNewValueSnelheidInMijlhTextField());
    snelheidInMijlhTextField.focusedProperty().addListener((_, _, newVal) -> {
      if (newVal) {
        snelheidInMijlhTextField.selectAll();
      } else {
        handleNewValueSnelheidInMijlhTextField();
      }
    });
    flowPane.getChildren().add(snelheidInMijlhTextField);

    // Label: "mijl/h:"
    label = componentFactory.createLabel("mijl/h");
    flowPane.getChildren().add(label);
    
    mainLayout.add(flowPane, 2, 2);
    
    /*
     *  Fourth row:  Tempo:     [v] ..:.. mm:ss/km [v] ..:.. mm:ss/mijl
     */
    
    // Label: "Tempo:"
    label = componentFactory.createLabel("Tempo:");
    mainLayout.add(label, 0, 3);
    
    // FlowPane for: [v] ..:.. mm:ss/km
    flowPane = new FlowPane();
    flowPane.setHgap(10);
    
    // Checkbox: "tempo in min/km".
    tempoInMinPerKmInputCheckBox = componentFactory.createCheckBox(null, false);
    tempoInMinPerKmInputCheckBox.setDisable(true);
    flowPane.getChildren().add(tempoInMinPerKmInputCheckBox);

    // TextField: "minuten"
    tempoInMinPerKmMinutenTextField = componentFactory.createTextField(30, MINUTES_FIELD_TOOLTIP);
    tempoInMinPerKmMinutenTextField.setOnAction(_ -> handleNewValueTempoInMinPerKmMinutenTextField());
    tempoInMinPerKmMinutenTextField.focusedProperty().addListener((_, _, newVal) -> {
      if (newVal) {
        tempoInMinPerKmMinutenTextField.selectAll();
      } else {
        handleNewValueTempoInMinPerKmMinutenTextField();
      }
    });
    flowPane.getChildren().add(tempoInMinPerKmMinutenTextField);

    // Label: ":"
    label = componentFactory.createLabel(":");
    flowPane.getChildren().add(label);
    
    // TextField: "seconden"
    tempoInMinPerKmSecondenTextField = componentFactory.createTextField(30, SECONDS_FIELD_TOOLTIP);
    tempoInMinPerKmSecondenTextField.setOnAction(_ -> handleNewValueTempoInMinPerKmSecondenTextField());
    tempoInMinPerKmSecondenTextField.focusedProperty().addListener((_, _, newVal) -> {
      if (newVal) {
        tempoInMinPerKmSecondenTextField.selectAll();
      } else {
        handleNewValueTempoInMinPerKmSecondenTextField();
      }
    });
    flowPane.getChildren().add(tempoInMinPerKmSecondenTextField);

    // Label: "mm:ss/km"
    label = componentFactory.createLabel("mm:ss/km");
    flowPane.getChildren().add(label);
    
    mainLayout.add(flowPane, 1, 3);

    // FlowPane for: [v] ..:.. mm:ss/mijl
    flowPane = new FlowPane();
    flowPane.setHgap(10);
    
    // Checkbox: "tempo in min/mijl".
    tempoInMinPerMijlInputCheckBox = componentFactory.createCheckBox(null, false);
    tempoInMinPerMijlInputCheckBox.setDisable(true);
    flowPane.getChildren().add(tempoInMinPerMijlInputCheckBox);
    
    // TextField: "minuten"
    tempoInMinPerMijlMinutenTextField = componentFactory.createTextField(30, MINUTES_FIELD_TOOLTIP);
    tempoInMinPerMijlMinutenTextField.setOnAction(_ -> handleNewValueTempoInMinPerMijlMinutenTextField());
    tempoInMinPerMijlMinutenTextField.focusedProperty().addListener((_, _, newVal) -> {
      if (newVal) {
        tempoInMinPerMijlMinutenTextField.selectAll();
      } else {
        handleNewValueTempoInMinPerMijlMinutenTextField();
      }
    });
    flowPane.getChildren().add(tempoInMinPerMijlMinutenTextField);
    
    // Label: ":"
    label = componentFactory.createLabel(":");
    flowPane.getChildren().add(label);
    
    // TextField: "seconden"
    tempoInMinPerMijlSecondenTextField = componentFactory.createTextField(30, SECONDS_FIELD_TOOLTIP);
    tempoInMinPerMijlSecondenTextField.setOnAction(_ -> handleNewValueTempoInMinPerMijlSecondenTextField());
    tempoInMinPerMijlSecondenTextField.focusedProperty().addListener((_, _, newVal) -> {
      if (newVal) {
        tempoInMinPerMijlSecondenTextField.selectAll();
      } else {
        handleNewValueTempoInMinPerMijlSecondenTextField();
      }
    });
    flowPane.getChildren().add(tempoInMinPerMijlSecondenTextField);
    
    // Label: "mm:ss/mijl"
    label = componentFactory.createLabel("mm:ss/mijl");
    flowPane.getChildren().add(label);

    mainLayout.add(flowPane, 2, 3);
    rootPane.getChildren().add(mainLayout);
    
    // Add split times table below converter panel
    rootPane.getChildren().add(createSplitTimesTable());

    setScene(new Scene(rootPane));
  }
  
  private Node createSplitTimesTable() {
    final Label label = new Label("(Halve) Marathon doorkomsttijden");

    splitTimesTableView = new TableView<>();

    TableColumn<SplitTime, String> distanceColumn = new TableColumn<>("Afstand");
    TableColumn<SplitTime, String> passThroughTime = new TableColumn<>("Doorkomsttijd");

    splitTimesTableView.getColumns().add(distanceColumn);
    splitTimesTableView.getColumns().add(passThroughTime);

    splitTimes = FXCollections.observableArrayList();
    for (double distance: SplitTime.DISTANCES) {
      splitTimes.add(new SplitTime(distance));
    }

    distanceColumn.setCellValueFactory(
        new PropertyValueFactory<SplitTime, String>("distance")
        );
    passThroughTime.setCellValueFactory(
        new PropertyValueFactory<SplitTime, String>("splitTime")
        );

    splitTimesTableView.setItems(splitTimes);
    splitTimesTableView.setEditable(true);
    
    final VBox vbox = new VBox();
    vbox.setSpacing(5);
    vbox.setPadding(new Insets(10, 0, 0, 10));
    vbox.getChildren().addAll(label, splitTimesTableView);

    return vbox;
  }

  /**
   * Create the menu bar.
   * 
   * @return the menu bar.
   */
  private MenuBar createMenuBar() {
    MenuBar    menuBar = new MenuBar();
    Menu       menu;
    
    if (unitConverterRegistry.isDevelopmentMode()) {
      // Bestand menu
      menu = componentFactory.createMenu("Bestand");

      // Bestand: property descriptors bewerken
      MenuUtil.addMenuItem(menu, "Property Descriptors bewerken", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          UnitConverterService.getInstance().showPropertyDescriptorsEditor();
        }
      });

      menuBar.getMenus().add(menu);
    }

    
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
  
  /**
   * Show the Help - About dialog.
   */
  private void showHelpAboutDialog() {    
    componentFactory.createApplicationInformationDialog(
        "About " + unitConverterRegistry.getApplicationName(),
        appResources.getApplicationImage(ImageSize.SIZE_3),
        null, 
        unitConverterRegistry.getShortProductInfo() + NEWLINE +
        "Version: " + unitConverterRegistry.getVersion() + NEWLINE +
        unitConverterRegistry.getCopyrightMessage() + NEWLINE +
        "Author: " + unitConverterRegistry.getAuthor())
        .showAndWait();
  }
  
  /**
   * Handle a new value in the field {@link #afstandInKmTextField}.
   */
  private void handleNewValueAfstandInKmTextField() {
    String text = afstandInKmTextField.getText().trim();
    if (!text.equals(afstandInKmTextFieldLastValue)) {
      if (text.isEmpty()) {
        afstandInKm = null;
        if (afstandInKmTextField.equals(errorComponent)) {
          errorComponent = null;
        }
        updateAfstandInMijlen();
        updateHistoryValuesAndGUI(InputType.AFSTAND);
      } else {
        try {
          Number number = NF.parse(text);
          afstandInKm = number.doubleValue();
          if (afstandInKmTextField.equals(errorComponent)) {
            errorComponent = null;
          }
          afstandInKmInputCheckBox.setSelected(true);
          afstandInMijlInputCheckBox.setSelected(false);
          updateAfstandInMijlen();
          updateHistoryValuesAndGUI(InputType.AFSTAND);
        } catch (ParseException e1) {
          afstandInKmTextField.setStyle(ERROR_TEXT_STYLE);
          errorComponent = afstandInKmTextField;
          afstandInKm = null;
          afstandInKmTextField.requestFocus();
          afstandInKmTextField.selectAll();
        }
      }
      
      afstandInKmTextFieldLastValue = text;
    }
  }
  
  /**
   * Handle a new value in the field {@link #afstandInMijlTextField}.
   */
  private void handleNewValueAfstandInMijlTextField() {
    String text = afstandInMijlTextField.getText().trim();
    if (!text.equals(afstandInMijlTextFieldLastValue)) {
      if (text.isEmpty()) {
        afstandInMijl = null;
        if (afstandInMijlTextField.equals(errorComponent)) {
          errorComponent = null;
        }
        updateAfstandInKm();
        updateHistoryValuesAndGUI(InputType.AFSTAND);
      } else {
        try {
          Number number = NF.parse(text);
          afstandInMijl = number.doubleValue();
          if (afstandInMijlTextField.equals(errorComponent)) {
            errorComponent = null;
          }
          afstandInKmInputCheckBox.setSelected(false);
          afstandInMijlInputCheckBox.setSelected(true);
          updateAfstandInKm();
          updateHistoryValuesAndGUI(InputType.AFSTAND);
        } catch (ParseException e1) {
          afstandInMijlTextField.setStyle(ERROR_TEXT_STYLE);
          errorComponent = afstandInMijlTextField;
          afstandInMijl = null;
          afstandInMijlTextField.requestFocus();
          afstandInMijlTextField.selectAll();
        }
      }
      
      afstandInMijlTextFieldLastValue = text;
    }
  }

  /**
   * Handle a new value in the field {@link #tijdUrenTextField}.
   */
  private void handleNewValueTijdUrenTextField() {
    String text = tijdUrenTextField.getText().trim();
    if (!text.equals(tijdUrenTextFieldLastValue)) {
      if (text.isEmpty()) {
        uren = null;
        if (tijdUrenTextField.equals(errorComponent)) {
          errorComponent = null;
        }
      } else {
        try {
          Number number = NF.parse(text);
          uren = number.intValue();
          if (tijdUrenTextField.equals(errorComponent)) {
            errorComponent = null;
          }
          tijdInputCheckBox.setSelected(true);
          updateTijd();
          updateHistoryValuesAndGUI(InputType.TIJD);
        } catch (ParseException e1) {
          tijdUrenTextField.setStyle(ERROR_TEXT_STYLE);
          errorComponent = tijdUrenTextField;
          uren = null;
          tijdUrenTextField.requestFocus();
          tijdUrenTextField.selectAll();
        }
      }
    }
    
    tijdUrenTextFieldLastValue = text;
  }
  
  /**
   * Handle a new value in the field {@link #tijdMinutenTextField}.
   */
  private void handleNewValueTijdMinutenTextField() {
    String text = tijdMinutenTextField.getText().trim();
    if (!text.equals(tijdMinutenTextFieldLastValue)) {
      if (text.isEmpty()) {
        minuten = null;
        if (tijdMinutenTextField.equals(errorComponent)) {
          errorComponent = null;
        }
      } else {
        try {
          Number number = NF.parse(text);
          minuten = number.intValue();
          if (tijdMinutenTextField.equals(errorComponent)) {
            errorComponent = null;
          }
          tijdInputCheckBox.setSelected(true);
          updateTijd();
          updateHistoryValuesAndGUI(InputType.TIJD);
        } catch (ParseException e1) {
          tijdMinutenTextField.setStyle(ERROR_TEXT_STYLE);
          errorComponent = tijdMinutenTextField;
          minuten = null;
          tijdMinutenTextField.requestFocus();
          tijdMinutenTextField.selectAll();
        }
      }
      
      tijdMinutenTextFieldLastValue = text;
    }
  }

  /**
   * Handle a new value in the field {@link #tijdSecondenTextField}.
   */
  private void handleNewValueTijdSecondenTextField() {
    String text = tijdSecondenTextField.getText().trim();
    if (!text.equals(tijdSecondenTextFieldLastValue)) {
      if (text.isEmpty()) {
        seconden = null;
        if (tijdSecondenTextField.equals(errorComponent)) {
          errorComponent = null;
        }
      } else {
        try {
          Number number = NF.parse(text);
          seconden = number.intValue();
          if (tijdSecondenTextField.equals(errorComponent)) {
            errorComponent = null;
          }
          tijdInputCheckBox.setSelected(true);
          updateTijd();
          updateHistoryValuesAndGUI(InputType.TIJD);
        } catch (ParseException e1) {
          tijdSecondenTextField.setStyle(ERROR_TEXT_STYLE);
          errorComponent = tijdSecondenTextField;
          seconden = null;
          tijdSecondenTextField.requestFocus();
          tijdSecondenTextField.selectAll();
        }
      }
      
      tijdSecondenTextFieldLastValue = text;
    }
  }
  
  /**
   * Handle a new value in the field {@link #snelheidInKmhTextField}.
   */
  private void handleNewValueSnelheidInKmhTextField() {
    String text = snelheidInKmhTextField.getText().trim();
    if (!text.equals(snelheidInKmhTextFieldLastValue)) {
      if (text.isEmpty()) {
        snelheidInKmh = null;
        if (snelheidInKmhTextField.equals(errorComponent)) {
          errorComponent = null;
        }
      } else {
        try {
          Number number = NF.parse(text);
          snelheidInKmh = number.doubleValue();
          if (snelheidInKmhTextField.equals(errorComponent)) {
            errorComponent = null;
          }
          snelheidInKmhInputCheckBox.setSelected(true);
          snelheidInMijlhInputCheckBox.setSelected(false);
          tempoInMinPerKmInputCheckBox.setSelected(false);
          tempoInMinPerMijlInputCheckBox.setSelected(false);
          calculateSnelheidInMijlh();
          calculateTempoInMinPerKm();
          calculateTempoInMinPerMijl();
          updateSplitTimes();
          updateHistoryValuesAndGUI(InputType.SNELHEID);
        } catch (ParseException e1) {
          snelheidInKmhTextField.setStyle(ERROR_TEXT_STYLE);
          errorComponent = snelheidInKmhTextField;
          snelheidInKmh = null;
          snelheidInKmhTextField.requestFocus();
          snelheidInKmhTextField.selectAll();
        }
      }
      
      snelheidInKmhTextFieldLastValue = text;
    }
  }

  /**
   * Handle a new value in the field {@link #snelheidInMijlhTextField}.
   */
  private void handleNewValueSnelheidInMijlhTextField() {
    String text = snelheidInMijlhTextField.getText().trim();
    if (!text.equals(snelheidInMijlhTextFieldLastValue)) {
      if (text.isEmpty()) {
        snelheidInMijlh = null;
        if (snelheidInMijlhTextField.equals(errorComponent)) {
          errorComponent = null;
        }
      } else {
        try {
          Number number = NF.parse(text);
          snelheidInMijlh = number.doubleValue();
          if (snelheidInMijlhTextField.equals(errorComponent)) {
            errorComponent = null;
          }
          snelheidInKmhInputCheckBox.setSelected(false);
          snelheidInMijlhInputCheckBox.setSelected(true);
          tempoInMinPerKmInputCheckBox.setSelected(false);
          tempoInMinPerMijlInputCheckBox.setSelected(false);
          calculateSnelheidInKmh();
          calculateTempoInMinPerKm();
          calculateTempoInMinPerMijl();
          updateSplitTimes();
          updateHistoryValuesAndGUI(InputType.SNELHEID);
        } catch (ParseException e1) {
          snelheidInMijlhTextField.setStyle(ERROR_TEXT_STYLE);
          errorComponent = snelheidInMijlhTextField;
          snelheidInMijlh = null;
          snelheidInMijlhTextField.requestFocus();
          snelheidInMijlhTextField.selectAll();
        }
      }
      
      snelheidInMijlhTextFieldLastValue = text;
    }
  }
  
  /**
   * Handle a new value in the field {@link #tempoInMinPerKmMinutenTextField}.
   */
  private void handleNewValueTempoInMinPerKmMinutenTextField() {
    String text = tempoInMinPerKmMinutenTextField.getText().trim();
    if (!text.equals(tempoInMinPerKmMinutenTextFieldLastValue)) {
      if (text.isEmpty()) {
        tempoInMinPerKmMinuten = null;
        if (tempoInMinPerKmMinutenTextField.equals(errorComponent)) {
          errorComponent = null;
        }
      } else {
        try {
          Number number = NF.parse(text);
          tempoInMinPerKmMinuten = number.intValue();
          if (tempoInMinPerKmMinutenTextField.equals(errorComponent)) {
            errorComponent = null;
          }
          snelheidInKmhInputCheckBox.setSelected(false);
          snelheidInMijlhInputCheckBox.setSelected(false);
          tempoInMinPerKmInputCheckBox.setSelected(true);
          tempoInMinPerMijlInputCheckBox.setSelected(false);
          updateTempoInMinPerKm();
          calculateSnelheidInKmh();
          calculateSnelheidInMijlh();
          calculateTempoInMinPerMijl();
          updateHistoryValuesAndGUI(InputType.SNELHEID);
        } catch (ParseException e1) {
          tempoInMinPerKmMinutenTextField.setStyle(ERROR_TEXT_STYLE);
          errorComponent = tempoInMinPerKmMinutenTextField;
          tempoInMinPerKmMinuten = null;
          tempoInMinPerKmMinutenTextField.requestFocus();
          tempoInMinPerKmMinutenTextField.selectAll();
        }
      }
      
      tempoInMinPerKmMinutenTextFieldLastValue = text;
    }
  }
  
  /**
   * Handle a new value in the field {@link #tempoInMinPerKmSecondenTextField}.
   */
  private void handleNewValueTempoInMinPerKmSecondenTextField() {
    String text = tempoInMinPerKmSecondenTextField.getText();
    if (!text.equals(tempoInMinPerKmSecondenTextFieldLastValue)) {
      if (text.isEmpty()) {
        tempoInMinPerKmSeconden = null;
        if (tempoInMinPerKmSecondenTextField.equals(errorComponent)) {
          errorComponent = null;
        }
      } else {
        try {
          Number number = NF.parse(text);
          tempoInMinPerKmSeconden = number.intValue();
          if (tempoInMinPerKmSecondenTextField.equals(errorComponent)) {
            errorComponent = null;
          }
          snelheidInKmhInputCheckBox.setSelected(false);
          snelheidInMijlhInputCheckBox.setSelected(false);
          tempoInMinPerKmInputCheckBox.setSelected(true);
          tempoInMinPerMijlInputCheckBox.setSelected(false);
          updateTempoInMinPerKm();
          calculateSnelheidInKmh();
          calculateSnelheidInMijlh();
          calculateTempoInMinPerMijl();
          updateHistoryValuesAndGUI(InputType.SNELHEID);
        } catch (ParseException e1) {
          tempoInMinPerKmSecondenTextField.setStyle(ERROR_TEXT_STYLE);
          errorComponent = tempoInMinPerKmSecondenTextField;
          tempoInMinPerKmSeconden = null;
          tempoInMinPerKmSecondenTextField.requestFocus();
          tempoInMinPerKmSecondenTextField.selectAll();
        }
      }
      
      tempoInMinPerKmSecondenTextFieldLastValue = text;
    }
  }
  
  /**
   * Handle a new value in the field {@link #tempoInMinPerMijlMinutenTextField}.
   */
  private void handleNewValueTempoInMinPerMijlMinutenTextField() {
    String text = tempoInMinPerMijlMinutenTextField.getText().trim();
    if (!text.equals(tempoInMinPerMijlMinutenTextFieldLastValue)) {
      if (text.isEmpty()) {
        tempoInMinPerMijlMinuten = null;
        if (tempoInMinPerMijlMinutenTextField.equals(errorComponent)) {
          errorComponent = null;
        }
      } else {
        try {
          Number number = NF.parse(text);
          tempoInMinPerMijlMinuten = number.intValue();
          errorComponent = null;
          snelheidInKmhInputCheckBox.setSelected(false);
          snelheidInMijlhInputCheckBox.setSelected(false);
          tempoInMinPerKmInputCheckBox.setSelected(false);
          tempoInMinPerMijlInputCheckBox.setSelected(true);
          updateTempoInMinPerMijl();
          calculateSnelheidInKmh();
          calculateSnelheidInMijlh();
          calculateTempoInMinPerKm();
          updateHistoryValuesAndGUI(InputType.SNELHEID);
        } catch (ParseException e1) {
          tempoInMinPerMijlMinutenTextField.setStyle(ERROR_TEXT_STYLE);
          errorComponent = tempoInMinPerMijlMinutenTextField;
          tempoInMinPerMijlMinuten = null;
          tempoInMinPerMijlMinutenTextField.requestFocus();
          tempoInMinPerMijlMinutenTextField.selectAll();
        }
      }
      
      tempoInMinPerMijlMinutenTextFieldLastValue = text;
    }
  }
  
  /**
   * Handle a new value in the field {@link #tempoInMinPerMijlSecondenTextField}.
   */
  private void handleNewValueTempoInMinPerMijlSecondenTextField() {
    String text = tempoInMinPerMijlSecondenTextField.getText().trim();
    if (!text.equals(tempoInMinPerMijlSecondenTextFieldLastValue)) {
      if (text.isEmpty()) {
        tempoInMinPerMijlSeconden = null;
        if (tempoInMinPerMijlSecondenTextField.equals(errorComponent)) {
          errorComponent = null;
        }
      } else {
        try {
          Number number = NF.parse(text);
          tempoInMinPerMijlSeconden = number.intValue();
          if (tempoInMinPerMijlSecondenTextField.equals(errorComponent)) {
            errorComponent = null;
          }
          snelheidInKmhInputCheckBox.setSelected(false);
          snelheidInMijlhInputCheckBox.setSelected(false);
          tempoInMinPerKmInputCheckBox.setSelected(false);
          tempoInMinPerMijlInputCheckBox.setSelected(true);
          updateTempoInMinPerMijl();
          calculateSnelheidInKmh();
          calculateSnelheidInMijlh();
          calculateTempoInMinPerKm();
          updateHistoryValuesAndGUI(InputType.SNELHEID);
        } catch (ParseException e1) {
          tempoInMinPerMijlSecondenTextField.setStyle(ERROR_TEXT_STYLE);
          errorComponent = tempoInMinPerMijlSecondenTextField;
          tempoInMinPerMijlSeconden = null;
          tempoInMinPerMijlSecondenTextField.requestFocus();
          tempoInMinPerMijlSecondenTextField.selectAll();
        }
      }
      
      tempoInMinPerMijlSecondenTextFieldLastValue = text;
    }
  }

  /**
   * Update the history (last entered input types), values and the GUI.
   */
  private void updateHistoryValuesAndGUI(InputType inputType) {
    if (laatstIngevoerd == null) {
      // No history, so start it.
      laatstIngevoerd = inputType;
    } else {
      if (laatstIngevoerd == inputType) {  // NOPMD
        // no action
      } else {
        if (voorLaatstIngevoerd == null) {  // NOPMD
          // no extra action
        } else if (voorLaatstIngevoerd == inputType) {  // NOPMD
          // also no extra action, the last two types are just switching place.
        } else {
          // unlock oldest input fields
          switch (voorLaatstIngevoerd) {
          case AFSTAND:
            afstandInKmInputCheckBox.setSelected(false);
            afstandInMijlInputCheckBox.setSelected(false);
            break;

          case TIJD:
            tijdInputCheckBox.setSelected(false);
            break;

          case SNELHEID:
            snelheidInKmhInputCheckBox.setSelected(false);
            snelheidInMijlhInputCheckBox.setSelected(false);
            tempoInMinPerKmInputCheckBox.setSelected(false);
            tempoInMinPerMijlInputCheckBox.setSelected(false);
            break;
          }
        }
        voorLaatstIngevoerd = laatstIngevoerd;
        laatstIngevoerd = inputType;
      }
    }

    updateValuesAndGUI();
  }
  
  private void updateSplitTimes() {
    for (SplitTime splitTime: splitTimes) {
      Number number;
      try {
        number = NF.parse(splitTime.getDistance());
        double distance = number.doubleValue();
        
        Duration clockTime = Duration.ofSeconds((long) (distance / snelheidInKmh * 3600 + 0.5));
        splitTime.setSplitTime(clockTime);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
 
    splitTimesTableView.refresh();
  }
  
  /**
   * Update the values and the GUI.
   */
  private void updateValuesAndGUI() {
    if (voorLaatstIngevoerd != null) {
      // 2 out of 3 values entered.

      if (laatstIngevoerd != InputType.AFSTAND  &&  voorLaatstIngevoerd != InputType.AFSTAND) {
        // bereken afstand waardes.
        if (snelheidInKmh != null  &&  snelheidInMijlh != null  &&  duration != null) {
          afstandInKm = snelheidInKmh * duration.getSeconds() / 3600;
          afstandInMijl = snelheidInMijlh * duration.getSeconds() / 3600;
        }
      } else if (laatstIngevoerd != InputType.TIJD  &&  voorLaatstIngevoerd != InputType.TIJD) {
        // bereken tijd.
        if (afstandInKm != null  &&  snelheidInKmh != null) {
          duration = Duration.ofSeconds((long) (afstandInKm / snelheidInKmh * 3600 + 0.5));
//          tijd = new ClockTime((long) (afstandInKm / snelheidInKmh * 3600 + 0.5));
          uren = (int) duration.toHours();
          minuten = duration.toMinutesPart();
          seconden = duration.toSecondsPart();
        }
      } else {
        // bereken snelheid
        if (afstandInKm != null  &&  duration != null) {
          snelheidInKmh = 3600 * afstandInKm / duration.getSeconds();

          snelheidInMijlh = UnitUtils.kmToMiles(snelheidInKmh);

          tempoInMinPerKm = UnitUtils.speedToTempo(snelheidInKmh);
          tempoInMinPerKmMinuten = tempoInMinPerKm.toMinutesPart();
          tempoInMinPerKmSeconden = tempoInMinPerKm.toSecondsPart();

          tempoInMinPerMijl = UnitUtils.speedToTempo(UnitUtils.kmToMiles(snelheidInKmh));
          tempoInMinPerMijlMinuten = tempoInMinPerMijl.toMinutesPart();
          tempoInMinPerMijlSeconden = tempoInMinPerMijl.toSecondsPart();
        }
      }
    }

    updateGUI();
  }

  /**
   * Update the GUI.
   */
  private void updateGUI() {
    if (!afstandInKmTextField.equals(errorComponent)) {
      afstandInKmTextField.setStyle(OK_TEXT_STYLE);
      
      if (afstandInKm != null) {
        afstandInKmTextField.setText(NF.format(afstandInKm));
      } else {
        afstandInKmTextField.setText("");
      }
      afstandInKmTextFieldLastValue = afstandInKmTextField.getText();
    }
    
    if (!afstandInMijlTextField.equals(errorComponent)) {
      afstandInMijlTextField.setStyle(OK_TEXT_STYLE);
      
      if (afstandInMijl != null) {
        afstandInMijlTextField.setText(NF.format(afstandInMijl));
      } else {
        afstandInMijlTextField.setText("");
      }
      afstandInMijlTextFieldLastValue = afstandInMijlTextField.getText();
    }
    
    if (!tijdUrenTextField.equals(errorComponent)) {
      tijdUrenTextField.setStyle(OK_TEXT_STYLE);
      
      if (uren != null) {
        tijdUrenTextField.setText(NF.format(uren));
      } else {
        tijdUrenTextField.setText("");
      }
      tijdUrenTextFieldLastValue = tijdUrenTextField.getText();
    }
    
    if (!tijdMinutenTextField.equals(errorComponent)) {
      tijdMinutenTextField.setStyle(OK_TEXT_STYLE);
      
      if (minuten != null) {
        tijdMinutenTextField.setText(NF.format(minuten));
      } else {
        tijdMinutenTextField.setText("");
      }
      tijdMinutenTextFieldLastValue = tijdMinutenTextField.getText();
    }
    
    if (!tijdSecondenTextField.equals(errorComponent)) {
      tijdSecondenTextField.setStyle(OK_TEXT_STYLE);
      
      if (minuten != null) {
        tijdSecondenTextField.setText(NF.format(seconden));
      } else {
        tijdSecondenTextField.setText("");
      }
      tijdSecondenTextFieldLastValue = tijdSecondenTextField.getText();
    }
    
    if (!snelheidInKmhTextField.equals(errorComponent)) {
      snelheidInKmhTextField.setStyle(OK_TEXT_STYLE);
      
      if (snelheidInKmh != null) {
        LOGGER.info("Snelheid: " + snelheidInKmh);
        snelheidInKmhTextField.setText(NF.format(snelheidInKmh));
      } else {
        snelheidInKmhTextField.setText("");
      }
      snelheidInKmhTextFieldLastValue = snelheidInKmhTextField.getText();
    }
    
    if (!snelheidInMijlhTextField.equals(errorComponent)) {
      snelheidInMijlhTextField.setStyle(OK_TEXT_STYLE);
      
      if (snelheidInMijlh != null) {
        snelheidInMijlhTextField.setText(NF.format(snelheidInMijlh));
      } else {
        snelheidInMijlhTextField.setText("");
      }
      snelheidInMijlhTextFieldLastValue = snelheidInMijlhTextField.getText();
    }
    
    if (!tempoInMinPerKmMinutenTextField.equals(errorComponent)) {
      tempoInMinPerKmMinutenTextField.setStyle(OK_TEXT_STYLE);
      
      if (tempoInMinPerKmMinuten != null) {
        tempoInMinPerKmMinutenTextField.setText(NF.format(tempoInMinPerKmMinuten));
      } else {
        tempoInMinPerKmMinutenTextField.setText("");
      }
      tempoInMinPerKmMinutenTextFieldLastValue = tempoInMinPerKmMinutenTextField.getText();
    }
    
    if (!tempoInMinPerKmSecondenTextField.equals(errorComponent)) {
      tempoInMinPerKmSecondenTextField.setStyle(OK_TEXT_STYLE);
      
      if (tempoInMinPerKmSeconden != null) {
        tempoInMinPerKmSecondenTextField.setText(NF.format(tempoInMinPerKmSeconden));
      } else {
        tempoInMinPerKmSecondenTextField.setText("");
      }
      tempoInMinPerKmSecondenTextFieldLastValue = tempoInMinPerKmSecondenTextField.getText();
    }
    
    if (!tempoInMinPerMijlMinutenTextField.equals(errorComponent)) {
      tempoInMinPerMijlMinutenTextField.setStyle(OK_TEXT_STYLE);
      
      if (tempoInMinPerMijlMinuten != null) {
        tempoInMinPerMijlMinutenTextField.setText(NF.format(tempoInMinPerMijlMinuten));
      } else {
        tempoInMinPerMijlMinutenTextField.setText("");
      }
      tempoInMinPerMijlMinutenTextFieldLastValue = tempoInMinPerMijlMinutenTextField.getText();
    }
    
    if (!tempoInMinPerMijlSecondenTextField.equals(errorComponent)) {
      tempoInMinPerMijlSecondenTextField.setStyle(OK_TEXT_STYLE);
      
      if (tempoInMinPerMijlSeconden != null) {
        tempoInMinPerMijlSecondenTextField.setText(NF.format(tempoInMinPerMijlSeconden));
      } else {
        tempoInMinPerMijlSecondenTextField.setText("");
      }
      tempoInMinPerMijlSecondenTextFieldLastValue = tempoInMinPerMijlSecondenTextField.getText();
    }
  }
  
  /**
   * Calculate the speed in km/h.
   */
  protected void calculateSnelheidInKmh() {
    if (snelheidInMijlhInputCheckBox.isSelected()) {
      snelheidInKmh = UnitUtils.milesToKm(snelheidInMijlh);
    } else if (tempoInMinPerKmInputCheckBox.isSelected()) {
      snelheidInKmh = UnitUtils.tempoToSpeed(tempoInMinPerKm);
    } else if (tempoInMinPerMijlInputCheckBox.isSelected()) {
      snelheidInKmh = UnitUtils.milesToKm(UnitUtils.tempoToSpeed(tempoInMinPerMijl));
    }
    updateSplitTimes();
  }

  /**
   * Calculate the speed in mile/h.
   */
  protected void calculateSnelheidInMijlh() {
    if (snelheidInKmhInputCheckBox.isSelected()) {
      snelheidInMijlh = UnitUtils.kmToMiles(snelheidInKmh);
    } else if (tempoInMinPerKmInputCheckBox.isSelected()) {
      snelheidInMijlh = UnitUtils.kmToMiles(UnitUtils.tempoToSpeed(tempoInMinPerKm));
    } else if (tempoInMinPerMijlInputCheckBox.isSelected()) {
      snelheidInMijlh = UnitUtils.tempoToSpeed(tempoInMinPerMijl);
    }
  }

  /**
   * Calculate the tempo in time/km.
   */
  protected void calculateTempoInMinPerKm() {
    if (snelheidInKmhInputCheckBox.isSelected()) {
      tempoInMinPerKm = UnitUtils.speedToTempo(snelheidInKmh);
    } else if (snelheidInMijlhInputCheckBox.isSelected()) {
      tempoInMinPerKm = UnitUtils.speedToTempo(UnitUtils.milesToKm(snelheidInMijlh));
    } else if (tempoInMinPerMijlInputCheckBox.isSelected()) {
      tempoInMinPerKm = Duration.ofSeconds((long) (tempoInMinPerMijl.getSeconds() / UnitUtils.KM_TO_MILES_FACTOR + 0.5));
    }
    tempoInMinPerKmMinuten = tempoInMinPerKm.toMinutesPart();
    tempoInMinPerKmSeconden = tempoInMinPerKm.toSecondsPart();
  }

  /**
   * Calculate the tempo in time/mile.
   */
  protected void calculateTempoInMinPerMijl() {
    if (snelheidInKmhInputCheckBox.isSelected()) {
      tempoInMinPerMijl = UnitUtils.speedToTempo(UnitUtils.kmToMiles(snelheidInKmh));
    } else if (snelheidInMijlhInputCheckBox.isSelected()) {
      tempoInMinPerMijl = UnitUtils.speedToTempo(snelheidInMijlh);
    } else if (tempoInMinPerKmInputCheckBox.isSelected()) {
      tempoInMinPerMijl = Duration.ofSeconds((long) (tempoInMinPerKm.getSeconds() * UnitUtils.KM_TO_MILES_FACTOR + 0.5));
    }
    tempoInMinPerMijlMinuten = tempoInMinPerMijl.toMinutesPart();
    tempoInMinPerMijlSeconden = tempoInMinPerMijl.toSecondsPart();
  }

  /**
   * Update the distance in km value.
   */
  private void updateAfstandInKm() {
    if (afstandInMijl != null) {
      afstandInKm = UnitUtils.milesToKm(afstandInMijl);
    } else {
      afstandInKm = null;
    }
  }

  /**
   * Update the distance in miles value.
   */
  private void updateAfstandInMijlen() {
    if (afstandInKm != null) {
      afstandInMijl = UnitUtils.kmToMiles(afstandInKm);
    } else {
      afstandInMijl = null;
    }
  }
  
  /**
   * Update the time values.
   */
  private void updateTijd() {
    if (uren == null) {
      uren = 0;
    }
    if (minuten == null) {
      minuten = 0;
    }
    if (seconden == null) {
      seconden = 0;
    }
    duration = Duration.ofSeconds(uren * 3600 + minuten * 60 + seconden);
    uren = (int) duration.toHours();
    minuten = duration.toMinutesPart();
    seconden = duration.toSecondsPart();
  }
 
  /**
   * Update the tempo time/km values
   */
  protected void updateTempoInMinPerKm() {
    if (tempoInMinPerKmMinuten == null) {
      tempoInMinPerKmMinuten = 0;
    }
    if (tempoInMinPerKmSeconden == null) {
      tempoInMinPerKmSeconden = 0;
    }
    tempoInMinPerKm = Duration.ofSeconds(tempoInMinPerKmMinuten * 60 + tempoInMinPerKmSeconden);
    tempoInMinPerKmMinuten = tempoInMinPerKm.toMinutesPart();
    tempoInMinPerKmSeconden = tempoInMinPerKm.toSecondsPart();
  }
  
  /**
   * Update the tempo time/mile values
   */
  protected void updateTempoInMinPerMijl() {
    if (tempoInMinPerMijlMinuten == null) {
      tempoInMinPerMijlMinuten = 0;
    }
    if (tempoInMinPerMijlSeconden == null) {
      tempoInMinPerMijlSeconden = 0;
    }
    tempoInMinPerMijl = Duration.ofSeconds(tempoInMinPerMijlMinuten * 60 + tempoInMinPerMijlSeconden);
    tempoInMinPerMijlMinuten = tempoInMinPerMijl.toMinutesPart();
    tempoInMinPerMijlSeconden = tempoInMinPerMijl.toSecondsPart();
  }


  /**
   * A class which represents a Split Time as a row in a table.
   */
  public static class SplitTime {
    public static double[] DISTANCES = {5, 10, 15, 20, 21.1, 25, 30, 35, 40, 42.2};

    private static DurationFormat CTF = new DurationFormat(true);

    private SimpleStringProperty distance;
    private SimpleStringProperty splitTime = new SimpleStringProperty(String.valueOf("--:--:--"));

    public SplitTime(double distance) {
     this.distance = new SimpleStringProperty(NF.format(distance));
    }

    public String getSplitTime() {
      return splitTime.get();
    }

    public void setSplitTime(Duration splitTime) {
      this.splitTime.set(CTF.format(splitTime));
    }

    public String getDistance() {
      return distance.get();
    }
  }

}

/**
 * Constants for the different input types.
 */
enum InputType {
  AFSTAND,
  TIJD,
  SNELHEID
}




