package goedegep.demo.jfx.objectcontrols.guifx;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.editor.controls.EditorControlAutoCompleteTextField;
import goedegep.jfx.editor.controls.EditorControlBoolean;
import goedegep.jfx.editor.controls.EditorControlCurrency;
import goedegep.jfx.editor.controls.EditorControlEnumComboBox;
import goedegep.jfx.editor.controls.EditorControlFileSelecter;
import goedegep.jfx.editor.controls.EditorControlFixedPointValue;
import goedegep.jfx.editor.controls.EditorControlFlexDate;
import goedegep.jfx.editor.controls.EditorControlFolderSelecter;
import goedegep.jfx.editor.controls.EditorControlHTMLString;
import goedegep.jfx.editor.controls.EditorControlImageFile;
import goedegep.jfx.editor.controls.EditorControlInteger;
import goedegep.jfx.editor.controls.EditorControlLocalDate;
import goedegep.jfx.editor.controls.EditorControlMultiLineString;
import goedegep.jfx.editor.controls.EditorControlString;
import goedegep.jfx.stringconverterandchecker.StringConverterAndChecker;
import javafx.geometry.Insets;
import javafx.scene.Scene;
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
  private EditorControlInteger ageControl;
  private EditorControlCurrency priceLastHolidayControl;
  private EditorControlEnumComboBox<TravelerType> travelerTypeControl;
  private EditorControlFixedPointValue lastTravelRatingControl;
  private EditorControlLocalDate lastTravelDateControl;
  private EditorControlFileSelecter travelReportFileControl;
  private EditorControlFlexDate nextTravelDateControl;
  private EditorControlFolderSelecter picturesFolderControl;
  private EditorControlImageFile favoritePictureControl;
  private EditorControlMultiLineString notesControl;
  private EditorControlHTMLString detailsControl;
  
  
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
    
    iAmHappyControl = new EditorControlBoolean.Builder("iAmHappy")
        .setCustomization(customization)
        .setLabelBaseText("I'm _happy")
        .setToolTipText("Uncheck if you're not happy\nThis is an example of an EditorControlBoolean")
        .build();
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
        .setLabelBaseText("Traveler type")
        .setToolTipText("What kind of traveler are you?\nThis is an example of en EditorControlEnumComboBox")
        .build();
    
    lastTravelRatingControl = new EditorControlFixedPointValue.FixedPointValueBuilder("lastTravelRating")
        .setCustomization(customization)
        .setLabelBaseText("Last travel rating")
        .setToolTipText("How do you rate your last travel (scale 0 to 10, with 2 decimal digits)")
        .setValidFactorRange(100, 100)
        .build();
        
    lastTravelDateControl = new EditorControlLocalDate.LocalDateBuilder("lastTravelDate")
        .setCustomization(customization)
        .setLabelBaseText("Last travel date")
        .setToolTipText("When was your last travel?)")
        .build();
        
    travelReportFileControl = EditorControlFileSelecter.newInstance(customization, 300, "The file with you're travel report",
        "Select file", "Select travel report", "Select the file with your travel report", false);
    travelReportFileControl.setLabelBaseText("Travel report");

    nextTravelDateControl = new EditorControlFlexDate.FlexDateBuilder("nextTravelDate")
        .setCustomization(customization)
        .setLabelBaseText("Next travel date")
        .setToolTipText("When do you expect to travel again?")
        .build();
    
    
    picturesFolderControl = EditorControlFolderSelecter.newInstance(customization, 300, "The folder with pictures",
        "Select folder", "Select pictures folder", "Select the folder with the pictures", false);
    picturesFolderControl.setLabelBaseText("Pictures folder");
    picturesFolderControl.setInitialFolderProvider(() -> "C:\\Users");
   
    favoritePictureControl = new EditorControlImageFile.Builder("favoritePicture")
        .setCustomization(customization)
        .setLabelBaseText("Favorite picture")
        .build();
    
    notesControl = EditorControlMultiLineString.newInstance(customization, false);
    notesControl.setLabelBaseText("Notes");
    
    detailsControl = EditorControlHTMLString.newInstance(customization, false);
    detailsControl.setLabelBaseText("Details");
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
    
    gridPane.addRow(row++, nameControl.getLabel(), nameControl.getControl(), nameControl.getStatusIndicator());
    
    gridPane.addRow(row++, iAmHappyControl.getLabel(), iAmHappyControl.getControl(), iAmHappyControl.getStatusIndicator());
    
    gridPane.addRow(row++, birthPlaceControl.getLabel(), birthPlaceControl.getControl(), birthPlaceControl.getStatusIndicator());
    
    gridPane.addRow(row++, ageControl.getLabel(), ageControl.getControl(), ageControl.getStatusIndicator());
    
    gridPane.addRow(row++, priceLastHolidayControl.getLabel(), priceLastHolidayControl.getControl(), priceLastHolidayControl.getStatusIndicator());
    
    gridPane.addRow(row++, travelerTypeControl.getLabel(), travelerTypeControl.getControl(), travelerTypeControl.getStatusIndicator());
    
    gridPane.addRow(row++, lastTravelRatingControl.getLabel(), lastTravelRatingControl.getControl(), lastTravelRatingControl.getStatusIndicator());
    
    gridPane.addRow(row++, lastTravelDateControl.getLabel(), lastTravelDateControl.getControl(), lastTravelDateControl.getStatusIndicator());
    
    gridPane.addRow(row++, travelReportFileControl.getLabel(), travelReportFileControl.getControl(), travelReportFileControl.getStatusIndicator(), travelReportFileControl.getFileChooserButton());
    
    gridPane.addRow(row++, nextTravelDateControl.getLabel(), nextTravelDateControl.getControl(), nextTravelDateControl.getStatusIndicator());
    
    gridPane.addRow(row++, picturesFolderControl.getLabel(), picturesFolderControl.getControl(), picturesFolderControl.getStatusIndicator(), picturesFolderControl.getFolderChooserButton());
    
    gridPane.addRow(row++, favoritePictureControl.getLabel(), favoritePictureControl.getControl(), favoritePictureControl.getStatusIndicator());
    
    notesControl.getControl().setMaxHeight(80.0);
    gridPane.addRow(row++, notesControl.getLabel(), notesControl.getControl(), notesControl.getStatusIndicator());
    
    detailsControl.getControl().setMaxHeight(200.0);
    gridPane.addRow(row++, detailsControl.getLabel(), detailsControl.getControl(), detailsControl.getStatusIndicator());    

    setScene(new Scene(gridPane));
  }
}
