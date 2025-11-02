package goedegep.media.mediadb.app.guifx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.media.common.MediaRegistry;
import goedegep.media.mediadb.model.Album;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ImportAlbumPicturesWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(ImportAlbumPicturesWindow.class.getName());

  private static final String WINDOW_TITLE = "Import album pictures";

  private Album album;
  private ComponentFactoryFx componentFactory;
  private String picturesFolderName;
  private GridPane gridPane;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   */
  public ImportAlbumPicturesWindow(CustomizationFx customization, Album album) {
    super(customization, WINDOW_TITLE);
        
    this.album = album;
    componentFactory = customization.getComponentFactoryFx();
    
    picturesFolderName = MediaRegistry.getInstance().getMusicDataDirectory() + "\\" + "Pictures";

    createGUI();
  }
  
  /**
   * Create the GUI
   */
  private void createGUI() {
    /*
     * Main pane is a BorderPane.
     * Top is explanation
     * Center is a GridPane with the artist details; Bottom is a control panel with 'add' and 'cancel' buttons.
     * Front Pictures:
     *                             from file
     * copy checkbox  Picture      to file
     * 
     * same for inside, back and label
     * 
     * OK and cancel buttons
     */

    BorderPane mainPane = new BorderPane();
    
    HBox messageBox = componentFactory.createHBox(10.0);
    TextArea messageArea = componentFactory.createTextArea();
    messageArea.setText("Deselect the pictures you don't want to copy and then press 'copy'");
    messageBox.getChildren().add(messageArea);
    mainPane.setTop(messageBox);
        
    gridPane = componentFactory.createGridPane(10.0, 10.0, 10.0);
    
    int row = 0;
    
    // 'Front pictures' label
    Label label = componentFactory.createLabel("Front pictures:");
    gridPane.add(label, 0, row++);
    
    // Add panel for each front picture
    for (String imageFileName: album.getImagesFront()) {
      Node node = createImagePanel(imageFileName, "front");
      gridPane.add(node, 0, row++);
    }
    
    // 'Front Inside pictures' label
    label = componentFactory.createLabel("Front Inside pictures:");
    gridPane.add(label, 0, row++);
    
    // Add panel for each front inside picture
    for (String imageFileName: album.getImagesFrontInside()) {
      Node node = createImagePanel(imageFileName, "inside");
      gridPane.add(node, 0, row++);
    }
    
    // 'Back pictures' label
    label = componentFactory.createLabel("Back pictures:");
    gridPane.add(label, 0, row++);
    
    // Add panel for each back picture
    for (String imageFileName: album.getImagesBack()) {
      Node node = createImagePanel(imageFileName, "back");
      gridPane.add(node, 0, row++);
    }
    
    // 'Label pictures' label
    label = componentFactory.createLabel("Label pictures:");
    gridPane.add(label, 0, row++);
    
    // Add panel for each label picture
    for (String imageFileName: album.getImagesLabel()) {
      Node node = createImagePanel(imageFileName, "label");
      gridPane.add(node, 0, row++);
    }

    mainPane.setCenter(gridPane);
    

    /*
     * Controls panel on the bottom
     */
    mainPane.setBottom(createOkOrCancelPanel());


    Scene scene = new Scene(mainPane);
    setScene(scene);
  }
  
  private Node createImagePanel(String imageFileName, String type) {
    HBox hBox = componentFactory.createHBox(12.0);
    
    hBox.setId("imagePanel");
    
    CheckBox checkBox = componentFactory.createCheckBox(null, true);
    hBox.getChildren().add(checkBox);
    
    Image image = new Image("file:" + imageFileName, 0.0, 200.0, true, true);
    ImageView imageView = new ImageView(image);
    hBox.getChildren().add(imageView);
    
    GridPane fileInfoPane = componentFactory.createGridPane();
    fileInfoPane.setHgap(12.0);
    
    Label label = componentFactory.createLabel("From file:");
    fileInfoPane.add(label, 0, 0);
    
    TextField fromFileNameTextField = componentFactory.createTextField(800, null);
    fromFileNameTextField.setText(imageFileName);
    fromFileNameTextField.setId("fromFile");
    fileInfoPane.add(fromFileNameTextField, 1, 0);
    
    label = componentFactory.createLabel("To file:");
    fileInfoPane.add(label, 0, 1);
    
    TextField toFileNameTextField = componentFactory.createTextField(800, null);
    String toFileName = picturesFolderName + "\\" + album.getArtist().getName() + " - " + album.getTitle() + " - " + type + ".jpg";
    toFileNameTextField.setText(toFileName);
    toFileNameTextField.setId("toFile");
    fileInfoPane.add(toFileNameTextField, 1, 1);
    
    hBox.getChildren().add(fileInfoPane);
    
    return hBox;
  }
  
  /**
   * Create a panel with 'Copy pictures' and 'Cancel' buttons.
   * <p>
   * The action for the 'Add artist' button is {@link #addArtist()}.
   * The action for the 'Cancel' button is {@link #closeWindow()}.
   * 
   * @return
   */
  private Node createOkOrCancelPanel() {
    HBox hBox = componentFactory.createHBox(10.0, 12.0);
   
    // Two buttons: "Copy pictures", "Cancel".
    Button button;
    
    button = componentFactory.createButton("Copy pictures", "Copy images to Pictures folder");
    button.setOnAction(_ -> copyPictures());
    hBox.getChildren().add(button);
    
    button = componentFactory.createButton("Cancel", "Exit window without copying");
    button.setOnAction(_ -> closeWindow());
    hBox.getChildren().add(button);
    
    return hBox;
  }
  
  private void copyPictures() {
    for (Node node: gridPane.getChildren()) {
      if ((node.getId() != null) &&  node.getId().equals("imagePanel")) {
        HBox hBox = (HBox) node;
        
        boolean copy = false;
        String fromFileName = null;
        String toFileName = null;

        for (Node childNode: hBox.getChildren()) {
          if (childNode instanceof CheckBox) {
            CheckBox checkBox = (CheckBox) childNode;
            copy = checkBox.isSelected();
            LOGGER.severe("copy: " + copy);
          }
          
          if (childNode instanceof GridPane) {
            GridPane gridPane = (GridPane) childNode;
            for (Node filesNode: gridPane.getChildren()) {
              if ((filesNode.getId() != null) &&  filesNode.getId().equals("fromFile")) {
                fromFileName = ((TextField) filesNode).getText();
                LOGGER.severe("from: " + fromFileName);
              }
              
              if ((filesNode.getId() != null) &&  filesNode.getId().equals("toFile")) {
                toFileName = ((TextField) filesNode).getText();
                LOGGER.severe("to: " + toFileName);
              }
            }
            
            if (copy && (fromFileName != null)  &&  (toFileName != null)) {
              Path fromPath = Paths.get(fromFileName);
              Path toPath = Paths.get(toFileName);
              
              if (Files.exists(toPath)) {
                LOGGER.severe("Skipping existing file: " + toPath.toString());
              } else {
                try {
                  Files.copy(fromPath, toPath);
                  replaceFileName(fromFileName, toFileName);
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            }
          }
        }
      }
    }
  }
  
  private void replaceFileName(String fromFileName, String toFileName) {
    replaceFileName(fromFileName, toFileName, album.getImagesFront());
    replaceFileName(fromFileName, toFileName, album.getImagesFrontInside());
    replaceFileName(fromFileName, toFileName, album.getImagesBack());
    replaceFileName(fromFileName, toFileName, album.getImagesLabel());
  }
  
  private void replaceFileName(String fromFileName, String toFileName, List<String> strings) {
    int index = 0;
    for (String fileName: strings) {
      if (fileName.equals(fromFileName)) {
        strings.remove(index);
        strings.add(index, toFileName);
        index++;
      }
    }
  }
    

  private void closeWindow() {
    System.out.println("AlbumDetailsEditor: closeWindow");
    this.close();
  }
}
