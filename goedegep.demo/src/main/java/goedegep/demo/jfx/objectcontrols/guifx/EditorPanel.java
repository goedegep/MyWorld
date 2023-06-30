package goedegep.demo.jfx.objectcontrols.guifx;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import goedegep.emfsample.model.Gender;
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
import goedegep.util.PgUtilities;
import goedegep.util.file.FileUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
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
   * Editor controls (all implementations of ObjectControl).
   */
  private ObjectControlTextField<String> nameObjectControlTextField;
  private ObjectControlBoolean happyObjectControlBoolean;
  private ObjectControlAutoCompleteTextField<City> birthPlaceObjectControlAutoCompleteTextField;
  private ObjectControlEnumComboBox<Gender> genderObjectControlEnumComboBox;
  private ObjectControlInteger ageObjectControlInteger;
  private ObjectControlCurrency priceLastHolidayObjectControlCurrency;
  private ObjectControlEnumComboBox<TravelerType> travelerTypeObjectControlEnumComboBox;
  private ObjectControlFixedPointValue lastTravelRatingObjectControlFixedPointValue;
  private ObjectControlLocalDate lastTravelDateObjectControlLocalDate;
  private ObjectControlFileSelecter travelReportFileObjectControlFileSelecter;
  private ObjectControlFlexDate nextTravelDateObjectControlFlexDate;
  private ObjectControlFolderSelecter picturesFolderObjectControlFolderSelecter;
  private ObjectControlImageFile imageFileObjectControlImageFile;
  private ObjectControlMultiLineString notesObjectControlMultiLineString;
  private ObjectControlHTMLString detailsObjectControlHTMLString;
  
  private ObjectControlGroup objectControlGroup;
  
  private ComponentFactoryFx componentFactory;
  
  /**
   * Indicates whether we're creating new InquiryData, or editing existing InquiryData.
   */
  private EditMode editMode = EditMode.NEW;
  
  /**
   * Panel for the add/update and new buttons.
   */
  private HBox addUpdateAndNewButtonsPanel;
    
  /**
   * The list of InquiryData to which new items will be added.
   */
  private List<InquiryData> inquiryDataList;
  
  /**
   * The InquiryData currently being edited. If null we're creating a new InquiryData.
   */
  private InquiryData inquiryData = null;
  
  /**
   * Constructor.
   * 
   * @param customization The GUI customization
   * @param inquiryDataList The list of InquiryData to which new items will be added.
   */
  public EditorPanel(CustomizationFx customization, List<InquiryData> inquiryDataList) {
    this.inquiryDataList = inquiryDataList;
    
    componentFactory = customization.getComponentFactoryFx();
    
    createControls();
    createGUI();
    
    objectControlGroup.addListener((observable) -> updateAddUpdateAndNewButtonsPanel());
    
    setInquiryData(null, false);
  }
  
  /**
   * Create the GUI controls
   */
  private void createControls() {
    nameObjectControlTextField = componentFactory.createObjectControlTextField(null, null, 300.0, false, "Enter your name");
    happyObjectControlBoolean = componentFactory.createObjectControlBoolean(null, true, false, "Uncheck if you're not happy");
    
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
    birthPlaceObjectControlAutoCompleteTextField.setOptions(City.getCities());
    
    genderObjectControlEnumComboBox = componentFactory.createObjectControlEnumComboBox(Gender.FEMALE, null, false, "What is your gender?");
    ageObjectControlInteger = componentFactory.createObjectControlInteger(null, 300.0, false, "Enter your age");
    priceLastHolidayObjectControlCurrency = componentFactory.createObjectControlCurrency(null, 300.0, false, "How much did your last travel cost");
    travelerTypeObjectControlEnumComboBox = componentFactory.createObjectControlEnumComboBox(TravelerType.REGULAR, null, false, "What kind of traveler are you?");
    
    lastTravelRatingObjectControlFixedPointValue = componentFactory.createObjectControlFixedPointValue(null, 150.0, false, "How do you rate your last travel (scale 0 to 10, with 2 decimal digits)");
    lastTravelRatingObjectControlFixedPointValue.setValidFactorRange(100, 100);
    
    lastTravelDateObjectControlLocalDate = componentFactory.createObjectControlLocalDate(null, 300.0, false, "When was your last travel?");
    travelReportFileObjectControlFileSelecter = componentFactory.createFileSelecter("C:\\Users\\Peter\\Downloads\\Gebouw 464 BIC.jpg", 300, "The file with you're travel report", "Select file", "Select travel report", "Select the file with your travel report");
    nextTravelDateObjectControlFlexDate = componentFactory.createObjectControlFlexDate(null, 300.0, false, "When do you expect to travel again?");
    picturesFolderObjectControlFolderSelecter = componentFactory.createFolderSelecter("C:\\Users", 300, "The folder with pictures", "Select folder", "Select pictures folder", "Select the folder with the pictures");
    imageFileObjectControlImageFile = componentFactory.createObjectControlImageFile();
    notesObjectControlMultiLineString = componentFactory.createObjectControlMultiLineString(null, 300.0, false, "Enter your notes");
    detailsObjectControlHTMLString = componentFactory.createObjectControlHTMLString(null, 300.0, false, "Enter details of your travel");
    
    objectControlGroup = new ObjectControlGroup();
    objectControlGroup.addObjectControls(nameObjectControlTextField, happyObjectControlBoolean, birthPlaceObjectControlAutoCompleteTextField, genderObjectControlEnumComboBox,
        ageObjectControlInteger, priceLastHolidayObjectControlCurrency, travelerTypeObjectControlEnumComboBox, lastTravelRatingObjectControlFixedPointValue,
        lastTravelDateObjectControlLocalDate, travelReportFileObjectControlFileSelecter, nextTravelDateObjectControlFlexDate, picturesFolderObjectControlFolderSelecter,
        imageFileObjectControlImageFile, notesObjectControlMultiLineString, detailsObjectControlHTMLString);
    
    addUpdateAndNewButtonsPanel = componentFactory.createHBox();
  }
  
  /**
   * Create the GUI.
   */
  private void createGUI() {
    setPadding(new Insets(12.0));
    
    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0);
    int row = 0;
    Label label;
    
    label = componentFactory.createLabel("Name:");
    gridPane.add(label, 0, row);
    gridPane.add(nameObjectControlTextField.ocGetControl(), 1, row);
    gridPane.add(nameObjectControlTextField.ocGetValidIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("I'm happy:");
    gridPane.add(label, 0, row);
    gridPane.add(happyObjectControlBoolean.ocGetControl(), 1, row);
    gridPane.add(happyObjectControlBoolean.ocGetValidIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Birthplace:");
    gridPane.add(label, 0, row);
    gridPane.add(birthPlaceObjectControlAutoCompleteTextField.ocGetControl(), 1, row);
    gridPane.add(birthPlaceObjectControlAutoCompleteTextField.ocGetValidIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Gender:");
    gridPane.add(label, 0, row);
    gridPane.add(genderObjectControlEnumComboBox.ocGetControl(), 1, row);
    gridPane.add(genderObjectControlEnumComboBox.ocGetValidIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Age:");
    gridPane.add(label, 0, row);
    gridPane.add(ageObjectControlInteger.ocGetControl(), 1, row);
    gridPane.add(ageObjectControlInteger.ocGetValidIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Price last holiday:");
    gridPane.add(label, 0, row);
    gridPane.add(priceLastHolidayObjectControlCurrency.ocGetControl(), 1, row);
    gridPane.add(priceLastHolidayObjectControlCurrency.ocGetValidIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Traveler type:");
    gridPane.add(label, 0, row);
//    StringConverter<TravelerType> stringConverter = new StringConverter<>() {
//
//      @Override
//      public String toString(TravelerType travelerType) {
//        return (travelerType != null ? travelerType.toString() + "!!" : "<null>");
//      }
//
//      @Override
//      public TravelerType fromString(String string) {
//        for (TravelerType travelerType: TravelerType.values()) {
//          if (travelerType.toString().equals(string)) {
//            return travelerType;
//          }
//        }
//        return null;
//      }
//    };
//    travelerTypeObjectControlEnumComboBox.setConverter(stringConverter);
    gridPane.add(travelerTypeObjectControlEnumComboBox.ocGetControl(), 1, row);
    gridPane.add(travelerTypeObjectControlEnumComboBox.ocGetValidIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Last travel rating:");
    gridPane.add(label, 0, row);
    gridPane.add(lastTravelRatingObjectControlFixedPointValue.ocGetControl(), 1, row);
    gridPane.add(lastTravelRatingObjectControlFixedPointValue.ocGetValidIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Last travel date:");
    gridPane.add(label, 0, row);
    gridPane.add(lastTravelDateObjectControlLocalDate.ocGetControl(), 1, row);
    gridPane.add(lastTravelDateObjectControlLocalDate.ocGetValidIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Traval report:");
    gridPane.add(label, 0, row);
    gridPane.add(travelReportFileObjectControlFileSelecter.ocGetControl(), 1, row);
    gridPane.add(travelReportFileObjectControlFileSelecter.ocGetValidIndicator(), 2, row);
    gridPane.add(travelReportFileObjectControlFileSelecter.getFileChooserButton(), 3, row);
    
    row++;
    
    label = componentFactory.createLabel("Next travel date:");
    gridPane.add(label, 0, row);
    gridPane.add(nextTravelDateObjectControlFlexDate.ocGetControl(), 1, row);
    gridPane.add(nextTravelDateObjectControlFlexDate.ocGetValidIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Pictures folder:");
    gridPane.add(label, 0, row);
    gridPane.add(picturesFolderObjectControlFolderSelecter.ocGetControl(), 1, row);
    gridPane.add(picturesFolderObjectControlFolderSelecter.ocGetValidIndicator(), 2, row);
    gridPane.add(picturesFolderObjectControlFolderSelecter.getFolderChooserButton(), 3, row);
    
    row++;
    
    label = componentFactory.createLabel("Favorite picture:");
    gridPane.add(label, 0, row);
    gridPane.add(imageFileObjectControlImageFile.ocGetControl(), 1, row);
    gridPane.add(imageFileObjectControlImageFile.ocGetValidIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Notes:");
    gridPane.add(label, 0, row);
    notesObjectControlMultiLineString.ocGetControl().setMaxHeight(80.0);
    gridPane.add(notesObjectControlMultiLineString.ocGetControl(), 1, row);
    gridPane.add(notesObjectControlMultiLineString.ocGetValidIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Details:");
    gridPane.add(label, 0, row);
    detailsObjectControlHTMLString.ocGetControl().setMaxHeight(200.0);
    gridPane.add(detailsObjectControlHTMLString.ocGetControl(), 1, row);
    gridPane.add(detailsObjectControlHTMLString.ocGetValidIndicator(), 2, row);
    
    
    getChildren().add(gridPane);
    
    addUpdateAndNewButtonsPanel.setSpacing(30.0);
    addUpdateAndNewButtonsPanel.setPadding(new Insets(12.0, 0, 0, 0));
    getChildren().add(addUpdateAndNewButtonsPanel);
  }
  
  /**
   * If there are unsaved changes, show a dialog informing the user about this and ask for a confirmation.
   * 
   * @return true if there are no unsaved changes, or the user has confirmed to continue and discard the changes. False otherwise.
   */
  private boolean getUserConfirmationInCaseOfUnsavedChanges() {
    if (changesInInput()) {
      Optional<ButtonType> userChoice = componentFactory.createOkCancelConfirmationDialog("Unsaved changes", "There are unsaved changes in the editor, these will be lost if you continue.", "Do you want to continue?")
          .showAndWait();
      if (userChoice.isEmpty()  ||
          !userChoice.get().equals(ButtonType.OK)) {
            return false;
          }
    }
    
    return true;
  }
    
  /**
   * Start editing an InquiryData object.
   * <p>
   * If there are any unsaved changes, show a dialog informing the user about this and ask for a confirmation.
   * {@code inquiryData} is set to the specified value.
   * All the controls are cleared then filled with the information from the {@code inquiryData}.
   * 
   * @param inquiryData the value to be edited.
   */
  public void setInquiryData(InquiryData inquiryData) {
    setInquiryData(inquiryData, true);
  }
  
  /**
   * Start editing an InquiryData object.
   * <p>
   * If there are any unsaved changes while checkOnUnsavedChanges is set, show a dialog informing the user about this and ask for a confirmation.
   * {@code inquiryData} is set to the specified value.
   * All the controls are cleared then filled with the information from the {@code inquiryData}.
   * 
   * @param inquiryData the value to be edited.
   * @param checkOnUnsavedChanges if true and if there are any unsaved changes, show a dialog informing the user about this and ask for a confirmation.
   */
  private void setInquiryData(InquiryData inquiryData, boolean checkOnUnsavedChanges) {
    if (checkOnUnsavedChanges  &&  !getUserConfirmationInCaseOfUnsavedChanges()) {
      return;
    }
    
    this.inquiryData = inquiryData;
    
    fillControlsFromInquiryData();
    
    editMode = inquiryData != null ? EditMode.EDIT : EditMode.NEW;
    updateAddUpdateAndNewButtonsPanel();
  }
  
  /**
   * Clear the controls and  fill them with the information from the {@code inquiryData}.
   */
  private void fillControlsFromInquiryData() {
    setControlsToDefaultValues();
    
    if (inquiryData == null) {
      return;
    }
    
    nameObjectControlTextField.ocSetValue(inquiryData.getName());
    happyObjectControlBoolean.ocSetValue(inquiryData.isHappy());
    birthPlaceObjectControlAutoCompleteTextField.ocSetValue(inquiryData.getBirthPlace());
    genderObjectControlEnumComboBox.ocSetValue(inquiryData.getGender());
    ageObjectControlInteger.ocSetValue(inquiryData.getAge());
    priceLastHolidayObjectControlCurrency.ocSetValue(inquiryData.getPriceLastHoliday());
    travelerTypeObjectControlEnumComboBox.ocSetValue(inquiryData.getTravelerType());
    lastTravelRatingObjectControlFixedPointValue.ocSetValue(inquiryData.getLastTravelRating());
    lastTravelDateObjectControlLocalDate.ocSetValue(inquiryData.getLastTravelDate());
    travelReportFileObjectControlFileSelecter.ocSetValue(inquiryData.getTravelReportFile());
    nextTravelDateObjectControlFlexDate.ocSetValue(inquiryData.getNextTravelDate());
    picturesFolderObjectControlFolderSelecter.ocSetValue(inquiryData.getPicturesFolder());
    imageFileObjectControlImageFile.ocSetValue(inquiryData.getImageFile());
    notesObjectControlMultiLineString.ocSetValue(inquiryData.getNotes());
    detailsObjectControlHTMLString.ocSetValue(inquiryData.getDetails());
  }
  
  /**
   * Set the controls to their default values.
   */
  private void setControlsToDefaultValues() {
    nameObjectControlTextField.ocSetValue(null);
    happyObjectControlBoolean.ocSetValue(false);
    birthPlaceObjectControlAutoCompleteTextField.ocSetValue(null);
    genderObjectControlEnumComboBox.ocSetValue(null);
    ageObjectControlInteger.ocSetValue(null);
    priceLastHolidayObjectControlCurrency.ocSetValue(null);
    travelerTypeObjectControlEnumComboBox.ocSetValue(TravelerType.REGULAR);
    lastTravelRatingObjectControlFixedPointValue.ocSetValue(null);
    lastTravelDateObjectControlLocalDate.ocSetValue(null);
    travelReportFileObjectControlFileSelecter.ocSetFilename("C:\\Users\\Peter\\Downloads\\Gebouw 464 BIC.jpg");
    nextTravelDateObjectControlFlexDate.ocSetValue(null);
    picturesFolderObjectControlFolderSelecter.ocSetValue(null);
    imageFileObjectControlImageFile.ocSetValue(null);
    notesObjectControlMultiLineString.ocSetValue(null);
    detailsObjectControlHTMLString.ocSetValue(null);
  }
  
  /**
   * Add a new InquiryData to the InquiryData list.
   * <p>
   * A new InquiryData is created and filled with the values of the controls.
   * This new InquiryData is then added to the list.
   * {@code inquiryData} is set to this new InquiryData and the editor switches to Edit mode.
   */
  private void addInquiryData() {
    inquiryData = new InquiryData();
    
    updateInquiryDataFromControls();
    
    inquiryDataList.add(inquiryData);
    
    setInquiryData(inquiryData, false);
  }
  
  /**
   * Update {@code inquiryData} with the values of the controls.
   */
  private void updateInquiryData() {
    updateInquiryDataFromControls();
    
    setInquiryData(inquiryData, false);
  }
  
  /**
   * Fill (update) {@code inquiryData} with the values of the controls.
   */
  private void updateInquiryDataFromControls() {
    inquiryData.setName(nameObjectControlTextField.ocGetValue());
    inquiryData.setHappy(happyObjectControlBoolean.ocGetValue());
    inquiryData.setBirthPlace(birthPlaceObjectControlAutoCompleteTextField.ocGetValue());
    inquiryData.setGender(genderObjectControlEnumComboBox.ocGetValue());
    inquiryData.setAge(ageObjectControlInteger.ocGetValue());
    inquiryData.setPriceLastHoliday(priceLastHolidayObjectControlCurrency.ocGetValue());
    inquiryData.setTravelerType(travelerTypeObjectControlEnumComboBox.ocGetValue());
    inquiryData.setLastTravelRating(lastTravelRatingObjectControlFixedPointValue.ocGetValue());
    inquiryData.setLastTravelDate(lastTravelDateObjectControlLocalDate.ocGetValue());
    inquiryData.setTravelReportFile(travelReportFileObjectControlFileSelecter.ocGetValue());
    inquiryData.setNextTravelDate(nextTravelDateObjectControlFlexDate.ocGetValue());
    inquiryData.setPicturesFolder(picturesFolderObjectControlFolderSelecter.ocGetValue());
    inquiryData.setImageFile(imageFileObjectControlImageFile.ocGetValue());
    inquiryData.setNotes(notesObjectControlMultiLineString.ocGetValue());
    inquiryData.setDetails(detailsObjectControlHTMLString.ocGetValue());
  }

  
  /**
   * Update the panel with the Add/Update and Cancel buttons.
   */
  private void updateAddUpdateAndNewButtonsPanel() {
    addUpdateAndNewButtonsPanel.getChildren().clear();

    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    addUpdateAndNewButtonsPanel.getChildren().add(spacer);
    
    Button button;
    
    EditStatus editStatus = determineEditStatus();
    Label editStatusLabel = componentFactory.createLabel(editStatus.getStatusIndicator());
    if (editStatus == EditStatus.INVALID) {
      editStatusLabel.setTooltip(new Tooltip("At least one of the controls has an invalid value"));
    }
    addUpdateAndNewButtonsPanel.getChildren().add(editStatusLabel);
    
    Button addOrUpdateButton;
    if (editMode == EditMode.NEW) {
      addOrUpdateButton = componentFactory.createButton("Add InquiryData", "Add the InquiryData to the list");
      addOrUpdateButton.setOnAction(e -> addInquiryData());
      if (!objectControlGroup.getIsValid()) {
        addOrUpdateButton.setDisable(true);
      }
    } else {
      addOrUpdateButton = componentFactory.createButton("Update InquiryData", "Update the album in the list");
      addOrUpdateButton.setOnAction(e -> updateInquiryData());
      if (editStatus != EditStatus.CHANGES) {
        addOrUpdateButton.setDisable(true);
      }
    }
    addUpdateAndNewButtonsPanel.getChildren().add(addOrUpdateButton);
    
    button = componentFactory.createButton("New", "Clear controls to start creating new InquiryData");
    button.setOnAction(e -> setInquiryData(null, true));
    addUpdateAndNewButtonsPanel.getChildren().add(button);
  }
  
  /**
   * Determine the edit status.
   * <p>
   * If any of the controls is invalid, the status is INVALID.
   * Otherwise, the status is CHANGES if there are any changes in the controls, or NO_CHANGES.
   * 
   * @return the newly determined EditStatus.
   */
  private EditStatus determineEditStatus() {
    if (objectControlGroup.isValid().getValue()) {
      if (editMode == EditMode.NEW) {
        return EditStatus.ADD;
      } else {
        if (changesInInput()) {
          return EditStatus.CHANGES;
        } else {
          return EditStatus.NO_CHANGES;
        }
      }
    } else {
      return EditStatus.INVALID;
    }
  }
  
  /**
   * Check whether there are any changes in the controls.
   * <p>
   * In EDIT mode there are changes if any control has a different value than the current value of {@code inquiryData}.
   * In NEW mode there are changes if any control is filled in.
   * 
   * @return
   */
  private boolean changesInInput() {
    if (editMode == EditMode.EDIT) {
      if (!PgUtilities.equals(nameObjectControlTextField.ocGetValue(), inquiryData.getName())  ||
          !PgUtilities.equals(happyObjectControlBoolean.ocGetValue(), inquiryData.isHappy())  ||
          !PgUtilities.equals(birthPlaceObjectControlAutoCompleteTextField.ocGetValue(), inquiryData.getBirthPlace())  ||
          !PgUtilities.equals(genderObjectControlEnumComboBox.ocGetValue(), inquiryData.getGender())  ||
          !PgUtilities.equals(ageObjectControlInteger.ocGetValue(), inquiryData.getAge())  ||
          !PgUtilities.equals(priceLastHolidayObjectControlCurrency.ocGetValue(), inquiryData.getPriceLastHoliday())  ||
          !PgUtilities.equals(travelerTypeObjectControlEnumComboBox.ocGetValue(), inquiryData.getTravelerType())  ||
          !PgUtilities.equals(lastTravelRatingObjectControlFixedPointValue.ocGetValue(), inquiryData.getLastTravelRating())  ||
          !PgUtilities.equals(lastTravelDateObjectControlLocalDate.ocGetValue(), inquiryData.getLastTravelDate())  ||
          !PgUtilities.equals(travelReportFileObjectControlFileSelecter.ocGetValue(), inquiryData.getTravelReportFile())  ||
          !PgUtilities.equals(nextTravelDateObjectControlFlexDate.ocGetValue(), inquiryData.getNextTravelDate())  ||
          !PgUtilities.equals(picturesFolderObjectControlFolderSelecter.ocGetValue(), inquiryData.getPicturesFolder())  ||
          Objects.compare(imageFileObjectControlImageFile.ocGetValue(), inquiryData.getImageFile(), FileUtils.getComparator()) != 0  ||
          !PgUtilities.equals(notesObjectControlMultiLineString.ocGetValue(), inquiryData.getNotes())  ||
          !PgUtilities.equals(detailsObjectControlHTMLString.ocGetValue(), inquiryData.getDetails())) {
        return true;
      } else {
        return false;
      }
    } else {  // editMode = NEW
      return objectControlGroup.isAnyObjectControlChanged();
    }
  }
  
}

enum EditStatus {
  ADD("+"),         // Valid in NEW mode, so object can be added.    
  INVALID("!"),     // Value of controls is invalid - !
  NO_CHANGES("="),  // Valid, but none of the values has changed - =
  CHANGES("*");     // Valid and there are changes - *
  
  private String statusIndicator;
  
  EditStatus(String statusIndicator) {
    this.statusIndicator = statusIndicator;
  }

  public String getStatusIndicator() {
    return statusIndicator;
  }
    
}
