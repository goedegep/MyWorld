package goedegep.demo.jfx.objectcontrols.guifx;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlAutoCompleteTextField;
import goedegep.jfx.objectcontrols.ObjectControlBoolean;
import goedegep.jfx.objectcontrols.ObjectControlCurrency;
import goedegep.jfx.objectcontrols.ObjectControlEnumComboBox;
import goedegep.jfx.objectcontrols.ObjectControlFileSelecter;
import goedegep.jfx.objectcontrols.ObjectControlFixedPointValue;
import goedegep.jfx.objectcontrols.ObjectControlFlexDate;
import goedegep.jfx.objectcontrols.ObjectControlFolderSelecter;
import goedegep.jfx.objectcontrols.ObjectControlGroup;
import goedegep.jfx.objectcontrols.ObjectControlHTMLString;
import goedegep.jfx.objectcontrols.ObjectControlImageFile;
import goedegep.jfx.objectcontrols.ObjectControlInteger;
import goedegep.jfx.objectcontrols.ObjectControlLocalDate;
import goedegep.jfx.objectcontrols.ObjectControlMultiLineString;
import goedegep.jfx.objectcontrols.ObjectControlTextField;
import goedegep.jfx.objecteditor.EditMode;
import goedegep.jfx.stringconverters.StringConverterAndChecker;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * This class provides an editor (in the form of a panel that can be included in an application window) for InquiryData.
 */
public class EditorPanel extends VBox {
  /*
   * Editor controls
   */
  private ObjectControlTextField<String> nameObjectControlTextField;
  private ObjectControlBoolean happyObjectControlBoolean;
  private ObjectControlAutoCompleteTextField<City> birthPlaceObjectControlAutoCompleteTextField;
  private ObjectControlInteger ageObjectControlInteger;
  private ObjectControlCurrency priceLastHolidayObjectControlCurrency;
  private ObjectControlEnumComboBox<TravelerType> travelerTypeObjectControlEnumComboBox;
  private ObjectControlFixedPointValue lastTravelRatingObjectControlFixedPointValue;
  private ObjectControlLocalDate lastTravelDateObjectControlLocalDate;
  private ObjectControlFileSelecter travelReportFilenameObjectControlFileSelecter;
  private ObjectControlFlexDate nextTravelDateObjectControlFlexDate;
  private ObjectControlFolderSelecter picturesFolderObjectControlFolderSelecter;
  private ObjectControlImageFile imageFileObjectControlImageFile;
  private ObjectControlMultiLineString notesObjectControlMultiLineString;
  private ObjectControlHTMLString detailsObjectControlHTMLString;
  
  private ObjectControlGroup objectControlGroup;
  
  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;
  
  /**
   * Indicates whether we're creating new InquiryData, or editing existing InquiryData.
   */
  private EditMode editMode = EditMode.NEW;
  
  /**
   * Panel for the add/update and cancel buttons.
   */
  private HBox addUpdateAndCancelButtonsPanel;
  
  /**
   * Add or Update button (depends on editMode)
   */
  private Button addOrUpdateButton;
  
  /**
   * The InquiryData currently being edited.
   */
  private InquiryData inquiryData;
  
  /**
   * Constructor.
   */
  public EditorPanel(CustomizationFx customization) {
    this.customization = customization;
    componentFactory = customization.getComponentFactoryFx();
    
    createControls();
    createGUI();
  }
  
  private void createControls() {
    nameObjectControlTextField = componentFactory.createObjectControlTextField(null, null, 300.0, false, "Enter your name");
    happyObjectControlBoolean = componentFactory.createObjectControlBoolean(null, true, true, "Uncheck if you're not happy");
    birthPlaceObjectControlAutoCompleteTextField = componentFactory.createObjectControlAutoCompleteTextField(new StringConverterAndChecker<City>() {

      @Override
      public boolean isValid(String string) {
        return (string == null)  ||  City.getCityForName(string) != null;
      }

      @Override
      public String toString(City city) {
        if (city != null) {
          return city.getName();
        } else {
          return null;
        }
      }

      @Override
      public City fromString(String cityName) {
        return City.getCityForName(cityName);
      }
      
    }, 
    null, 300.0, false, "Select the city where you were born");
    ageObjectControlInteger = componentFactory.createObjectControlInteger(null, 300.0, true, "Enter your age");
    priceLastHolidayObjectControlCurrency = componentFactory.createObjectControlCurrency(null, 300.0, true, "How much did your last travel cost");
    travelerTypeObjectControlEnumComboBox = componentFactory.createObjectControlEnumComboBox(TravelerType.REGULAR, null, false, "What kind of traveler are you?");
    lastTravelRatingObjectControlFixedPointValue = componentFactory.createObjectControlFixedPointValue(null, 150.0, true, "How do you rate your last travel (scale 0 to 10, with 2 decimal digits)");
    lastTravelRatingObjectControlFixedPointValue.setValidFactorRange(100, 100);
    lastTravelDateObjectControlLocalDate = componentFactory.createObjectControlLocalDate(null, 300.0, true, "When was your last travel?");
    travelReportFilenameObjectControlFileSelecter = componentFactory.createFileSelecter("C:\\Users", 300, "The file with you're travel report", "Select file", "Select travel report", "Select the file with you're travel report");
    nextTravelDateObjectControlFlexDate = componentFactory.createObjectControlFlexDate(null, 300.0, true, "When do you expect to travel again?");
    picturesFolderObjectControlFolderSelecter = componentFactory.createFolderSelecter("C:\\Users", 300, "The folder with pictures", "Select folder", "Select pictures folder", "Select the folder with the pictures");
    imageFileObjectControlImageFile = new ObjectControlImageFile(customization);
    notesObjectControlMultiLineString = componentFactory.createObjectControlMultiLineString(null, 300.0, true, "Enter your notes");
    detailsObjectControlHTMLString = componentFactory.createObjectControlHTMLString(null, 300.0, true, "Enter details of your travel");
    
    objectControlGroup = new ObjectControlGroup();
    objectControlGroup.addObjectControls(nameObjectControlTextField, happyObjectControlBoolean, birthPlaceObjectControlAutoCompleteTextField,
        ageObjectControlInteger, priceLastHolidayObjectControlCurrency, travelerTypeObjectControlEnumComboBox, lastTravelRatingObjectControlFixedPointValue,
        lastTravelDateObjectControlLocalDate, travelReportFilenameObjectControlFileSelecter, nextTravelDateObjectControlFlexDate, picturesFolderObjectControlFolderSelecter,
        imageFileObjectControlImageFile, notesObjectControlMultiLineString, detailsObjectControlHTMLString);
    
    addUpdateAndCancelButtonsPanel = componentFactory.createHBox();
  }
  
  private void createGUI() {
    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0);
    int row = 0;
    Label label;
    
    label = componentFactory.createLabel("Name:");
    gridPane.add(label, 0, row);
    gridPane.add(nameObjectControlTextField, 1, row);
    
    row++;
    
    label = componentFactory.createLabel("I'm happy:");
    gridPane.add(label, 0, row);
    gridPane.add(happyObjectControlBoolean, 1, row);
    
    row++;
    
    label = componentFactory.createLabel("Birthplace:");
    gridPane.add(label, 0, row);
    gridPane.add(birthPlaceObjectControlAutoCompleteTextField, 1, row);
    
    row++;
    
    label = componentFactory.createLabel("Age:");
    gridPane.add(label, 0, row);
    gridPane.add(ageObjectControlInteger, 1, row);
    
    row++;
    
    label = componentFactory.createLabel("Price last holiday:");
    gridPane.add(label, 0, row);
    gridPane.add(priceLastHolidayObjectControlCurrency, 1, row);
    
    row++;
    
    label = componentFactory.createLabel("Traveler type:");
    gridPane.add(label, 0, row);
    gridPane.add(travelerTypeObjectControlEnumComboBox, 1, row);
    
    row++;
    
    label = componentFactory.createLabel("Last travel rating:");
    gridPane.add(label, 0, row);
    gridPane.add(lastTravelRatingObjectControlFixedPointValue, 1, row);
    
    row++;
    
    label = componentFactory.createLabel("Last travel date:");
    gridPane.add(label, 0, row);
    gridPane.add(lastTravelDateObjectControlLocalDate, 1, row);
    
    row++;
    
    label = componentFactory.createLabel("Traval report:");
    gridPane.add(label, 0, row);
    gridPane.add(travelReportFilenameObjectControlFileSelecter.getPathTextField(), 1, row);
    gridPane.add(travelReportFilenameObjectControlFileSelecter.getFileChooserButton(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Next travel date:");
    gridPane.add(label, 0, row);
    gridPane.add(nextTravelDateObjectControlFlexDate, 1, row);
    
    row++;
    
    label = componentFactory.createLabel("Pictures folder:");
    gridPane.add(label, 0, row);
    gridPane.add(picturesFolderObjectControlFolderSelecter.getPathTextField(), 1, row);
    gridPane.add(picturesFolderObjectControlFolderSelecter.getFolderChooserButton(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Favorite picture:");
    gridPane.add(label, 0, row);
    gridPane.add(imageFileObjectControlImageFile.getStackPane(), 1, row);
    
    row++;
    
    label = componentFactory.createLabel("Notes:");
    gridPane.add(label, 0, row);
    gridPane.add(notesObjectControlMultiLineString, 1, row);
    
    row++;
    
    label = componentFactory.createLabel("Details:");
    gridPane.add(label, 0, row);
    gridPane.add(detailsObjectControlHTMLString, 1, row);
    
    
    getChildren().add(gridPane);
    
    getChildren().add(addUpdateAndCancelButtonsPanel);
  }
  
  private void newInquiryData() {
    
  }
  
  public void setInquiryData(InquiryData inquiryData) {
    this.inquiryData = inquiryData;
    
    fillControlsFromInquiryData();
  }
  
  private void fillControlsFromInquiryData() {
    clearControls();
    
    if (inquiryData == null) {
      return;
    }
    
    nameObjectControlTextField.ociSetValue(inquiryData.getName());
    happyObjectControlBoolean.ociSetValue(inquiryData.isHappy());
    birthPlaceObjectControlAutoCompleteTextField.ociSetValue(inquiryData.getBirthPlace());
    ageObjectControlInteger.ociSetValue(inquiryData.getAge());
    priceLastHolidayObjectControlCurrency.ociSetValue(inquiryData.getPriceLastHoliday());
    travelerTypeObjectControlEnumComboBox.ociSetValue(inquiryData.getTravelerType());
    lastTravelRatingObjectControlFixedPointValue.ociSetValue(inquiryData.getLastTravelRating());
    lastTravelDateObjectControlLocalDate.ociSetValue(inquiryData.getLastTravelDate());
    travelReportFilenameObjectControlFileSelecter.ociSetValue(inquiryData.getTravelReportFilename() != null ? inquiryData.getTravelReportFilename().getAbsolutePath() : null);
    nextTravelDateObjectControlFlexDate.ociSetValue(inquiryData.getNextTravelDate());
    picturesFolderObjectControlFolderSelecter.ociSetValue(inquiryData.getPicturesFolder() != null ? inquiryData.getPicturesFolder().getAbsolutePath() : null);
    imageFileObjectControlImageFile.ociSetValue(inquiryData.getImageFile() != null ? inquiryData.getImageFile().getAbsolutePath() : null);
    notesObjectControlMultiLineString.ociSetValue(inquiryData.getNotes());
    detailsObjectControlHTMLString.ociSetValue(inquiryData.getDetails());
  }
  
  private void clearControls() {
    
  }
  
  private void addInquiryData() {
    
  }
  
  private void updateInquiryData() {
    
  }

  
  /**
   * Update the panel with the Add/Update and Cancel buttons.
   */
  private void updateAddUpdateAndCancelButtonsPanel() {
    addUpdateAndCancelButtonsPanel.getChildren().clear();

    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    addUpdateAndCancelButtonsPanel.getChildren().add(spacer);
    
    Button button;
    
    if (editMode == EditMode.NEW) {
      addOrUpdateButton = componentFactory.createButton("Add InquiryData", "Add the InquiryData to the list");
      addOrUpdateButton.setOnAction(e -> addInquiryData());
    } else {
      addOrUpdateButton = componentFactory.createButton("Update InquiryData", "Update the album in the list");
      addOrUpdateButton.setOnAction(e -> updateInquiryData());
    }
    addUpdateAndCancelButtonsPanel.getChildren().add(addOrUpdateButton);
    
    button = componentFactory.createButton("New", "Clear controls to start creating new InquiryData");
    button.setOnAction(e -> newInquiryData());
    addUpdateAndCancelButtonsPanel.getChildren().add(button);
  }
}
