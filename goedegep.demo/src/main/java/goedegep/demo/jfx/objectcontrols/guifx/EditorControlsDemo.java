package goedegep.demo.jfx.objectcontrols.guifx;

import java.util.logging.Logger;

import goedegep.emfsample.model.Gender;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.editor.controls.EditorControlAutoCompleteTextField;
import goedegep.jfx.editor.controls.EditorControlBoolean;
import goedegep.jfx.editor.controls.EditorControlCurrency;
import goedegep.jfx.editor.controls.EditorControlEnumComboBox;
import goedegep.jfx.editor.controls.EditorControlFixedPointValue;
import goedegep.jfx.editor.controls.EditorControlInteger;
import goedegep.jfx.editor.controls.EditorControlString;
import goedegep.jfx.objectcontrols.ObjectControlFileSelecter;
import goedegep.jfx.objectcontrols.ObjectControlFlexDate;
import goedegep.jfx.objectcontrols.ObjectControlFolderSelecter;
import goedegep.jfx.objectcontrols.ObjectControlHTMLString;
import goedegep.jfx.objectcontrols.ObjectControlImageFile;
import goedegep.jfx.objectcontrols.ObjectControlLocalDate;
import goedegep.jfx.objectcontrols.ObjectControlMultiLineString;
import goedegep.jfx.stringconverterandchecker.StringConverterAndChecker;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * This class demonstrates the EditorControls from goedegep.jfx.editor.controls.
 * Here the controls are not shown in an editor. See the editor demo's for using the controls in an editor.
 */
public class EditorControlsDemo extends JfxStage {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(EditorControlsDemo.class.getName());
  
  /*
   * Editor controls (all implementations of EditorControl).
   */
  private EditorControlString nameControl;
  private EditorControlBoolean iAmHappyControl;
  private EditorControlAutoCompleteTextField<City> birthPlaceControl;
  private EditorControlEnumComboBox<Gender> genderControl;
  private EditorControlInteger ageControl;
  private EditorControlCurrency priceLastHolidayControl;
  private EditorControlEnumComboBox<TravelerType> travelerTypeControl;
  private EditorControlFixedPointValue lastTravelRatingObjectControlFixedPointValue;
  private ObjectControlLocalDate lastTravelDateObjectControlLocalDate;
  private ObjectControlFileSelecter travelReportFileObjectControlFileSelecter;
  private ObjectControlFlexDate nextTravelDateObjectControlFlexDate;
  private ObjectControlFolderSelecter picturesFolderObjectControlFolderSelecter;
  private ObjectControlImageFile imageFileObjectControlImageFile;
  private ObjectControlMultiLineString notesObjectControlMultiLineString;
  private ObjectControlHTMLString detailsObjectControlHTMLString;
  
  
  /**
   * Constructor.
   * 
   * @param customization The GUI customization.
   */
  public EditorControlsDemo(CustomizationFx customization) {
    super(customization, "EditorControls demo");
        
    createControls();
    createGUI();
    show();
  }
  
  /**
   * Create the GUI controls
   */
  private void createControls() {
    
    nameControl = new EditorControlString.Builder("name")
        .setCustomization(customization)
        .setWidth(300d)
        .setLabelBaseText("_Name")
        .setToolTipText("Enter your name\nThis is an example of an EditorControlString")
        .setErrorTextSupplier(() -> "The name is not filled in")
        .build();
    
    iAmHappyControl = componentFactory.createEditorControlBoolean("iAmHappy", "I'm happy", "Uncheck if you're not happy\nThis is an example of an EditorControlBoolean");
    iAmHappyControl.setObject(true);
    
    birthPlaceControl = EditorControlAutoCompleteTextField.newInstance(customization, new StringConverterAndChecker<City>() {

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
    300.0, false, "Select the city where you were born\\nThis is an example of an EditorControlAutoCompleteTextField");
    birthPlaceControl.setLabelBaseText("Birthplace");
    birthPlaceControl.setOptions(City.getCities());
    
    genderControl = new EditorControlEnumComboBox.Builder<Gender>(Gender.FEMALE, "gender")
        .setCustomization(customization)
        .setLabelBaseText("Gender")
        .setToolTipText("What is your gender?\nThis is an example of en EditorControlEnumComboBox")
        .build();
    
    ageControl = new EditorControlInteger.IntegerBuilder("age")
        .setCustomization(customization)
        .setOptional(false)
        .setWidth(300.0)
        .setLabelBaseText("Age")
        .setToolTipText("Enter your age\nThis is an example of en EditorControlInteger")
        .build();
    
    priceLastHolidayControl = new EditorControlCurrency.CurrencyBuilder("priceLastHoliday")
        .setCustomization(customization)
        .setOptional(false)
        .setWidth(300.0)
        .setLabelBaseText("Price last holiday")
        .setToolTipText("How much did your last travel cost\nThis is an example of en EditorControlCurrency")
        .build();
    
    travelerTypeControl = new EditorControlEnumComboBox.Builder<TravelerType>(TravelerType.REGULAR, "travelerType")
        .setCustomization(customization)
        .setLabelBaseText("Traveler type:")
        .setToolTipText("What kind of traveler are you?\nThis is an example of en EditorControlEnumComboBox")
        .build();
    
//    lastTravelRatingObjectControlFixedPointValue = componentFactory.createObjectControlFixedPointValue(null, 150.0, false, "How do you rate your last travel (scale 0 to 10, with 2 decimal digits)");
//    lastTravelRatingObjectControlFixedPointValue.setValidFactorRange(100, 100);
    
    lastTravelDateObjectControlLocalDate = componentFactory.createObjectControlLocalDate(null, 300.0, false, "When was your last travel?");
    travelReportFileObjectControlFileSelecter = componentFactory.createFileSelecterObjectControl(300, "The file with you're travel report", "Select file", "Select travel report", "Select the file with your travel report", false);
    nextTravelDateObjectControlFlexDate = componentFactory.createObjectControlFlexDate(null, 300.0, false, "When do you expect to travel again?");
    picturesFolderObjectControlFolderSelecter = componentFactory.createFolderSelecter(300, "The folder with pictures", "Select folder", "Select pictures folder", "Select the folder with the pictures", false);
    picturesFolderObjectControlFolderSelecter.setInitialFolderProvider(() -> "C:\\Users");
    imageFileObjectControlImageFile = componentFactory.createObjectControlImageFile(false);
    notesObjectControlMultiLineString = componentFactory.createObjectControlMultiLineString(null, false);
    detailsObjectControlHTMLString = componentFactory.createObjectControlHTMLString(null, false);    
  }
  
  /**
   * Create the GUI.
   * Left: list InquiryData (with Edit button). Shows also previous value.
   * Right: EditorPanel (with New, Add/Update buttons)
   */
  private void createGUI() {
    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0);
    gridPane.setPadding(new Insets(12.0));
    
    int row = 0;
    Label label;
    
    gridPane.addRow(row, nameControl.getLabel(), nameControl.getControl(), nameControl.getStatusIndicator());
    
    row++;
    
    gridPane.addRow(row, iAmHappyControl.getLabel(), iAmHappyControl.getControl(), iAmHappyControl.getStatusIndicator());
    
    row++;
    
    gridPane.addRow(row, birthPlaceControl.getLabel(), birthPlaceControl.getControl(), birthPlaceControl.getStatusIndicator());
    
    row++;
    
    gridPane.addRow(row, genderControl.getLabel(), genderControl.getControl(), genderControl.getStatusIndicator());
    
    row++;
    
    gridPane.addRow(row, ageControl.getLabel(), ageControl.getControl(), ageControl.getStatusIndicator());
    
    row++;
    
    gridPane.addRow(row, priceLastHolidayControl.getLabel(), priceLastHolidayControl.getControl(), priceLastHolidayControl.getStatusIndicator());
    
    row++;
    
    gridPane.addRow(row, travelerTypeControl.getLabel(), travelerTypeControl.getControl(), travelerTypeControl.getStatusIndicator());
    
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
    
    
//    getChildren().add(gridPane);
    

    setScene(new Scene(gridPane));
  }
}
