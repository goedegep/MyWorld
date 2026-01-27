package goedegep.demo.jfx.objectcontrols.gui;

import java.io.File;
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
import goedegep.jfx.stringconverterandchecker.StringConverterAndChecker;
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
    
    genderObjectControlEnumComboBox = componentFactory.createObjectControlEnumComboBox(Gender.FEMALE, false, "What is your gender?");
    ageObjectControlInteger = componentFactory.createObjectControlInteger(null, 300.0, false, "Enter your age");
    priceLastHolidayObjectControlCurrency = componentFactory.createObjectControlCurrency(null, 300.0, false, "How much did your last travel cost");
    travelerTypeObjectControlEnumComboBox = componentFactory.createObjectControlEnumComboBox(TravelerType.REGULAR, false, "What kind of traveler are you?");
    
    lastTravelRatingObjectControlFixedPointValue = componentFactory.createObjectControlFixedPointValue(null, 150.0, false, "How do you rate your last travel (scale 0 to 10, with 2 decimal digits)");
    lastTravelRatingObjectControlFixedPointValue.setValidFactorRange(100, 100);
    
    lastTravelDateObjectControlLocalDate = componentFactory.createObjectControlLocalDate(null, 300.0, false, "When was your last travel?");
    travelReportFileObjectControlFileSelecter = componentFactory.createFileSelecterObjectControl(300, "The file with you're travel report", "Select file", "Select travel report", "Select the file with your travel report", false);
    nextTravelDateObjectControlFlexDate = componentFactory.createObjectControlFlexDate(null, 300.0, false, "When do you expect to travel again?");
    picturesFolderObjectControlFolderSelecter = componentFactory.createFolderSelecter(300, "The folder with pictures", "Select folder", "Select pictures folder", "Select the folder with the pictures", false);
    picturesFolderObjectControlFolderSelecter.setInitialFolderProvider(() -> "C:\\Users");
    imageFileObjectControlImageFile = componentFactory.createObjectControlImageFile(false);
    notesObjectControlMultiLineString = componentFactory.createObjectControlMultiLineString(null, false);
    detailsObjectControlHTMLString = componentFactory.createObjectControlHTMLString(null, false);
    
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
    gridPane.add(nameObjectControlTextField.getControl(), 1, row);
    gridPane.add(nameObjectControlTextField.getStatusIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("I'm happy:");
    gridPane.add(label, 0, row);
    gridPane.add(happyObjectControlBoolean.getControl(), 1, row);
    gridPane.add(happyObjectControlBoolean.getStatusIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Birthplace:");
    gridPane.add(label, 0, row);
    gridPane.add(birthPlaceObjectControlAutoCompleteTextField.getControl(), 1, row);
    gridPane.add(birthPlaceObjectControlAutoCompleteTextField.getStatusIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Gender:");
    gridPane.add(label, 0, row);
    gridPane.add(genderObjectControlEnumComboBox.getControl(), 1, row);
    gridPane.add(genderObjectControlEnumComboBox.getStatusIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Age:");
    gridPane.add(label, 0, row);
    gridPane.add(ageObjectControlInteger.getControl(), 1, row);
    gridPane.add(ageObjectControlInteger.getStatusIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Price last holiday:");
    gridPane.add(label, 0, row);
    gridPane.add(priceLastHolidayObjectControlCurrency.getControl(), 1, row);
    gridPane.add(priceLastHolidayObjectControlCurrency.getStatusIndicator(), 2, row);
    
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
    gridPane.add(travelerTypeObjectControlEnumComboBox.getControl(), 1, row);
    gridPane.add(travelerTypeObjectControlEnumComboBox.getStatusIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Last travel rating:");
    gridPane.add(label, 0, row);
    gridPane.add(lastTravelRatingObjectControlFixedPointValue.getControl(), 1, row);
    gridPane.add(lastTravelRatingObjectControlFixedPointValue.getStatusIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Last travel date:");
    gridPane.add(label, 0, row);
    gridPane.add(lastTravelDateObjectControlLocalDate.getControl(), 1, row);
    gridPane.add(lastTravelDateObjectControlLocalDate.getStatusIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Traval report:");
    gridPane.add(label, 0, row);
    gridPane.add(travelReportFileObjectControlFileSelecter.getControl(), 1, row);
    gridPane.add(travelReportFileObjectControlFileSelecter.getStatusIndicator(), 2, row);
    gridPane.add(travelReportFileObjectControlFileSelecter.getFileChooserButton(), 3, row);
    
    row++;
    
    label = componentFactory.createLabel("Next travel date:");
    gridPane.add(label, 0, row);
    gridPane.add(nextTravelDateObjectControlFlexDate.getControl(), 1, row);
    gridPane.add(nextTravelDateObjectControlFlexDate.getStatusIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Pictures folder:");
    gridPane.add(label, 0, row);
    gridPane.add(picturesFolderObjectControlFolderSelecter.getControl(), 1, row);
    gridPane.add(picturesFolderObjectControlFolderSelecter.getStatusIndicator(), 2, row);
    gridPane.add(picturesFolderObjectControlFolderSelecter.getFolderChooserButton(), 3, row);
    
    row++;
    
    label = componentFactory.createLabel("Favorite picture:");
    gridPane.add(label, 0, row);
    gridPane.add(imageFileObjectControlImageFile.getControl(), 1, row);
    gridPane.add(imageFileObjectControlImageFile.getStatusIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Notes:");
    gridPane.add(label, 0, row);
    notesObjectControlMultiLineString.getControl().setMaxHeight(80.0);
    gridPane.add(notesObjectControlMultiLineString.getControl(), 1, row);
    gridPane.add(notesObjectControlMultiLineString.getStatusIndicator(), 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Details:");
    gridPane.add(label, 0, row);
    detailsObjectControlHTMLString.getControl().setMaxHeight(200.0);
    gridPane.add(detailsObjectControlHTMLString.getControl(), 1, row);
    gridPane.add(detailsObjectControlHTMLString.getStatusIndicator(), 2, row);
    
    
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
    
    nameObjectControlTextField.setObject(inquiryData.getName());
    happyObjectControlBoolean.setObject(inquiryData.isHappy());
    birthPlaceObjectControlAutoCompleteTextField.setObject(inquiryData.getBirthPlace());
    genderObjectControlEnumComboBox.setObject(inquiryData.getGender());
    ageObjectControlInteger.setObject(inquiryData.getAge());
    priceLastHolidayObjectControlCurrency.setObject(inquiryData.getPriceLastHoliday());
    travelerTypeObjectControlEnumComboBox.setObject(inquiryData.getTravelerType());
    lastTravelRatingObjectControlFixedPointValue.setObject(inquiryData.getLastTravelRating());
    lastTravelDateObjectControlLocalDate.setObject(inquiryData.getLastTravelDate());
    travelReportFileObjectControlFileSelecter.setObject(inquiryData.getTravelReportFile());
    nextTravelDateObjectControlFlexDate.setObject(inquiryData.getNextTravelDate());
    picturesFolderObjectControlFolderSelecter.setObject(inquiryData.getPicturesFolder());
    imageFileObjectControlImageFile.setObject(inquiryData.getImageFile());
    notesObjectControlMultiLineString.setObject(inquiryData.getNotes());
    detailsObjectControlHTMLString.setObject(inquiryData.getDetails());
  }
  
  /**
   * Set the controls to their default values.
   */
  private void setControlsToDefaultValues() {
    nameObjectControlTextField.setObject(null);
    happyObjectControlBoolean.setObject(false);
    birthPlaceObjectControlAutoCompleteTextField.setObject(null);
    genderObjectControlEnumComboBox.setObject(null);
    ageObjectControlInteger.setObject(null);
    priceLastHolidayObjectControlCurrency.setObject(null);
    travelerTypeObjectControlEnumComboBox.setObject(TravelerType.REGULAR);
    lastTravelRatingObjectControlFixedPointValue.setObject(null);
    lastTravelDateObjectControlLocalDate.setObject(null);
    travelReportFileObjectControlFileSelecter.setObject(new File("C:\\Users\\Peter\\Downloads\\Gebouw 464 BIC.jpg"));
    nextTravelDateObjectControlFlexDate.setObject(null);
    picturesFolderObjectControlFolderSelecter.setObject(null);
    imageFileObjectControlImageFile.setObject(null);
    notesObjectControlMultiLineString.setObject(null);
    detailsObjectControlHTMLString.setObject(null);
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
    inquiryData.setName(nameObjectControlTextField.getValue());
    inquiryData.setHappy(happyObjectControlBoolean.getValue());
    inquiryData.setBirthPlace(birthPlaceObjectControlAutoCompleteTextField.getValue());
    inquiryData.setGender(genderObjectControlEnumComboBox.getValue());
    inquiryData.setAge(ageObjectControlInteger.getValue());
    inquiryData.setPriceLastHoliday(priceLastHolidayObjectControlCurrency.getValue());
    inquiryData.setTravelerType(travelerTypeObjectControlEnumComboBox.getValue());
    inquiryData.setLastTravelRating(lastTravelRatingObjectControlFixedPointValue.getValue());
    inquiryData.setLastTravelDate(lastTravelDateObjectControlLocalDate.getValue());
    inquiryData.setTravelReportFile(travelReportFileObjectControlFileSelecter.getValue());
    inquiryData.setNextTravelDate(nextTravelDateObjectControlFlexDate.getValue());
    inquiryData.setPicturesFolder(picturesFolderObjectControlFolderSelecter.getValue());
    inquiryData.setImageFile(imageFileObjectControlImageFile.getValue());
    inquiryData.setNotes(notesObjectControlMultiLineString.getValue());
    inquiryData.setDetails(detailsObjectControlHTMLString.getValue());
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
      if (!objectControlGroup.isValid()) {
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
    if (objectControlGroup.isValid()) {
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
      if (!Objects.equals(nameObjectControlTextField.getValue(), inquiryData.getName())  ||
          !Objects.equals(happyObjectControlBoolean.getValue(), inquiryData.isHappy())  ||
          !Objects.equals(birthPlaceObjectControlAutoCompleteTextField.getValue(), inquiryData.getBirthPlace())  ||
          !Objects.equals(genderObjectControlEnumComboBox.getValue(), inquiryData.getGender())  ||
          !Objects.equals(ageObjectControlInteger.getValue(), inquiryData.getAge())  ||
          !Objects.equals(priceLastHolidayObjectControlCurrency.getValue(), inquiryData.getPriceLastHoliday())  ||
          !Objects.equals(travelerTypeObjectControlEnumComboBox.getValue(), inquiryData.getTravelerType())  ||
          !Objects.equals(lastTravelRatingObjectControlFixedPointValue.getValue(), inquiryData.getLastTravelRating())  ||
          !Objects.equals(lastTravelDateObjectControlLocalDate.getValue(), inquiryData.getLastTravelDate())  ||
          !Objects.equals(travelReportFileObjectControlFileSelecter.getValue(), inquiryData.getTravelReportFile())  ||
          !Objects.equals(nextTravelDateObjectControlFlexDate.getValue(), inquiryData.getNextTravelDate())  ||
          !Objects.equals(picturesFolderObjectControlFolderSelecter.getValue(), inquiryData.getPicturesFolder())  ||
          Objects.compare(imageFileObjectControlImageFile.getValue(), inquiryData.getImageFile(), FileUtils.getComparator()) != 0  ||
          !Objects.equals(notesObjectControlMultiLineString.getValue(), inquiryData.getNotes())  ||
          !Objects.equals(detailsObjectControlHTMLString.getValue(), inquiryData.getDetails())) {
        return true;
      } else {
        return false;
      }
    } else {  // editMode = NEW
      return objectControlGroup.isAnyObjectChanged();
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
