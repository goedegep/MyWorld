package goedegep.media.fotomapview.guifx;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import goedegep.geo.WGS84Coordinates;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.media.photo.IPhotoMetaData;
import goedegep.resources.ImageSize;
import javafx.collections.ObservableSet;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * This class provides an editor/viewer for the meta data of a photo.
 *
 */
public class PhotoMetaDataEditor extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(PhotoMetaDataEditor.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
  
  /**
   * A set of modified photos.
   * Whenever anything on a photo is modified it is added to this set.
   */
  private static ObservableSet<IPhotoMetaData> modifiedPhotos;
  
  private CustomizationFx customization;
  private IPhotoMetaData photoMetaData;
  private ComponentFactoryFx componentFactory;
  
  private TextField titleTextField;
  private CheckBox approximateGPScoordinatesCheckBox;

  public PhotoMetaDataEditor(CustomizationFx customization, IPhotoMetaData photoMetaData) {
    super("Details for " + photoMetaData.getFileName() , customization);
    
    this.customization = customization;
    this.photoMetaData = photoMetaData;
    componentFactory = customization.getComponentFactoryFx();
    
    createGUI();
    
    show();
  }
  
  /**
   * Set the set of modified photos.
   * 
   * @param modifiedPhotos the set of modified photos.
   */
  public static void setModifiedPhotos(ObservableSet<IPhotoMetaData> modifiedPhotos) {
    PhotoMetaDataEditor.modifiedPhotos = modifiedPhotos;
  }
  
  /**
   * Create the GUI.
   * <p>
   * Top part shows (editable) meta data.<br/>
   * Center part shows the photo.<br/>
   * Bottom provides the action buttons.
   */
  private void createGUI() {
    VBox mainLayout = componentFactory.createVBox();
    
    mainLayout.getChildren().add(createMetaDataPanel());
    mainLayout.getChildren().add(createImagePanel());
    mainLayout.getChildren().add(createButtonsPanel());

    setScene(new Scene(mainLayout));
    titleTextField.requestFocus();
  }

  /**
   * Create the meta data panel.
   * <p>
   * 
   * @return the created meta data panel.
   */
  private Node createMetaDataPanel() {
    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0);
    gridPane.setPadding(new Insets(12.0));
    
    Label label;
    TextField textField;
    int row = 0;
    
    // row: Folder: 'folder-name'
    File file = new File(photoMetaData.getFileName());
    String folder = file.getParent();
    label = componentFactory.createLabel("Folder:");
    gridPane.add(label, 0, row);
    textField = componentFactory.createTextField(folder, 300, null);
    textField.setEditable(false);
    gridPane.add(textField, 1, row);
    
    row++;
    
    // row: File: 'filename'
    String fileName = file.getName();
    label = componentFactory.createLabel("File:");
    gridPane.add(label, 0, row);
    textField = componentFactory.createTextField(fileName, 300, null);
    textField.setEditable(false);
    gridPane.add(textField, 1, row);
    
    row++;
    
    // row: Title: 'title'
    label = componentFactory.createLabel("Title:");
    gridPane.add(label, 0, row);
    titleTextField = componentFactory.createTextField(300, null);
    if (photoMetaData.getTitle() != null) {
      titleTextField.setText(photoMetaData.getTitle());
    }
    gridPane.add(titleTextField, 1, row);
    
    row++;
    
    // row: Coordinates: 'latitude', 'longitude'
    label = componentFactory.createLabel("Coordinates:");
    gridPane.add(label, 0, row);
    textField = componentFactory.createTextField(300, null);
    textField.setEditable(false);
    WGS84Coordinates coordinates = photoMetaData.getCoordinates();
    if (coordinates != null) {
      textField.setText(coordinates.getLatitude() + ", " + coordinates.getLongitude());
    }
    gridPane.add(textField, 1, row);
    
    row++;
    
    // row: Approximate coordinates: checkbox' 'info button'
    label = componentFactory.createLabel("Approximate coordinates:");
    gridPane.add(label, 0, row);
    approximateGPScoordinatesCheckBox = componentFactory.createCheckBox(null, false);
    if (photoMetaData.isApproximateGPScoordinates()) {
      approximateGPScoordinatesCheckBox.setSelected(true);
    }
    gridPane.add(approximateGPScoordinatesCheckBox, 1, row);
    Button infoButton = componentFactory.createInfoButton(
        "information on approximate coordinates",
        "About approximate coordinates",
        customization.getResources().getApplicationImage(ImageSize.SIZE_2), PhotoMetaDataEditor::getApproximateCoordinatesExplanation);
    gridPane.add(infoButton, 2, row);
    
    row++;
    
    // row: Sorting date/time: 'sorting date/time'
    label = componentFactory.createLabel("Sorting date/time:");
    gridPane.add(label, 0, row);
    textField = componentFactory.createTextField(300, null);
    textField.setEditable(false);
    LocalDateTime sortingDateTime = photoMetaData.getSortingDateTime();
    if (sortingDateTime != null) {
      textField.setText(DTF.format(sortingDateTime));
    }
    gridPane.add(textField, 1, row);
    
    return gridPane;
  }
  
  private static String getApproximateCoordinatesExplanation() {
    return "Select this checkbox if the new location isn't precise (where precise is up to your own interpretation.   " + NEWLINE
         + "If selected, a 'UserComment' entry with the value 'Approximate GPS coordinates' is added to the meta data of the file.   " + NEWLINE
         + "This application uses this information to show such photos with a different icon color.";
  }

  private Node createImagePanel() {
    Image image = new Image("file:" + photoMetaData.getFileName());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(600);
    imageView.setFitHeight(400);
    
    return imageView;
  }

  private Node createButtonsPanel() {
    HBox buttonsBox = componentFactory.createHBox(12.0, 12.0);
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    buttonsBox.getChildren().add(spacer);
    
    Button cancelButton = componentFactory.createButton("Cancel", "Close this window without making changes");
    cancelButton.setOnAction(e -> cleanupAndClose());
    buttonsBox.getChildren().add(cancelButton);
    
    Button okButton = componentFactory.createButton("OK", "Save changes");
    okButton.setOnAction(e -> saveChanges());
    buttonsBox.getChildren().add(okButton);
        
    return  buttonsBox;
  }

  private void saveChanges() {
    String title = titleTextField.getText();
    if (title != null && title.isEmpty()) {
      title = null;
    }
    
    photoMetaData.setTitle(title);
    
    photoMetaData.setApproximateGPScoordinates(approximateGPScoordinatesCheckBox.isSelected());
    
    modifiedPhotos.add(photoMetaData);
    
    cleanupAndClose();
  }

  private void cleanupAndClose() {
    close();
  }
}
