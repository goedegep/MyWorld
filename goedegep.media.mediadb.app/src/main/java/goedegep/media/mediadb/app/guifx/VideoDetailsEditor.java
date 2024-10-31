package goedegep.media.mediadb.app.guifx;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.util.EcoreUtil;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.objectcontrols.ObjectControlFileSelecter;
import goedegep.jfx.objecteditor.ObjectEditorException;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.Subject;
import goedegep.media.mediadb.model.Video;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VideoDetailsEditor extends JfxStage {
  
  /*
   * Strategy:
   * This editor can be used to edit video details, or to create a new video.
   * If the field 'video' is null (by default), the editor is in the 'new video' mode. Otherwise it is in 'edit video' mode.
   * 
   * All information of a video is 'stored' in the GUI controls and a number of lists. Together these are referred to as the GUI controls.
   * When a new video is set (which may be null), the information from the video is stored in the controls. If the album is null,
   * all information is cleared.
   * Any user changes are only stored in the GUI controls.
   * Upon 'add vidao' an video is created from the controls, 'video' is set to this value and the 'video' is added to the database.
   * Upon 'update', the 'video' is cleared and filled from the controls.
   */
  
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(VideoDetailsEditor.class.getName());
  private static final String NEW_LINE = System.getProperty("line.separator");
  private static final MediadbFactory MEDIA_DB_FACTORY = MediadbFactory.eINSTANCE;
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
  private static final FlexDateFormat FDF = new FlexDateFormat();
  
  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;

  /**
   * The Media Database.
   */
  private MediaDb mediaDb;
  
  /**
   * Control for the video title.
   * The title is plain text.
   */
  private TextField videoTitleTextField;
  
  /**
   * Control for the video date.
   * A <code>FlexDateFormat</code> is used to fill and parse this value.
   */
  private TextField videoDateTextField;
  
  /**
   * Control for the video image.
   * The video image is plain text; an absolute filename.
   */
  private ObjectControlFileSelecter videoImageFileSelecter;
  
  /**
   * Shows the video image, which is stored in the <code>videoImageTextField</code>.
   */
  private HBox videoImagePanel;
  
  /**
   * Shows the subjects
   */
  private VBox subjectsVBox;
  
  /**
   * The subjects 
   */
  private EObjectTable<Subject> subjectsTable;
  
  private List<Subject> subjects;
 

  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   * @param mediaDb the media database.
   */
  public VideoDetailsEditor(CustomizationFx customization, MediaDb mediaDb) {
    super(customization, "New video");
    
    this.customization = customization;
    this.mediaDb = mediaDb;
    
    componentFactory = customization.getComponentFactoryFx();

    createGUI();
    
    updateControlsForVideo(null);
  }

  /**
   * Change the <code>Video</code> for which the information is shown and which can be modified.
   * 
   * @param video the <code>Film</code> for which the information is shown in this window.
   */
  public void setVideo(Video video) {
    updateControlsForVideo(video);
  }
  
  /**
   * Create the GUI.
   */
  private void createGUI() {

    /*
     * Main pane is a BorderPane.
     * Center is a VBox with all album details
     */

    BorderPane mainPane = new BorderPane();
    
    
    /*
     * Center: album details
     */
    VBox centerPane = componentFactory.createVBox(18);
    
    /*
     * General information
     */
    GridPane gridPane = componentFactory.createGridPane(10.0, 10.0, 10.0);
    
    
    // First row: 'Date: <video-date>'
    Label label = componentFactory.createLabel("Date:");
    gridPane.add(label, 0, 0);
    
    videoDateTextField = componentFactory.createTextField(600, null);
    gridPane.add(videoDateTextField, 1, 0);
    
    // Second row: 'Title: <video-title>'
    label = componentFactory.createLabel("Title:");
    gridPane.add(label, 0, 1);
    
    videoTitleTextField = componentFactory.createTextField(600, null);
    gridPane.add(videoTitleTextField, 1, 1);

    // Third row: 'Image: <video-image>'
    label = componentFactory.createLabel("Image file:");
    gridPane.add(label, 0, 2);
    
    videoImageFileSelecter = componentFactory.createFileSelecterObjectControl(300, "Select an image file", "Open file selecter", "Select this image file", "Select image", false);
    videoImageFileSelecter.setInitialFolderProvider(() -> "D:\\Photo");
    gridPane.add(videoImageFileSelecter.getControl(), 1, 2);
    gridPane.add(videoImageFileSelecter.getFileChooserButton(), 2, 2);
    
    centerPane.getChildren().add(gridPane);
    
    /*
     * Image
     */
    videoImagePanel = componentFactory.createHBox(10.0, 50.0);
    centerPane.getChildren().add(videoImagePanel);
    
    
    /*
     * Subjects tables.
     */
    subjectsVBox = componentFactory.createVBox(10.0, 10.0);
    label = componentFactory.createStrongLabel("Subjects:");
    subjectsVBox.getChildren().add(label);
    subjectsTable = new SubjectsTable(customization);
    subjectsVBox.getChildren().add(subjectsTable);
    
    centerPane.getChildren().add(subjectsVBox);
    
    mainPane.setCenter(centerPane);
    

    /*
     * Controls panel on the bottom
     */
    mainPane.setBottom(createOkOrCancelPanel());
    
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(mainPane);

    Scene scene = new Scene(scrollPane, 1200, 1200);
    setScene(scene);
    show();
  }
    
  /**
   * Update all GUI controls for the details of a video.
   * <p>
   * If the video is null, all controls are cleared.
   * 
   * @param video the <code>Film</code> for which the GUI controls are updated. This value may be null, in which case all controls are cleared.
   */
  private void updateControlsForVideo(Video video) {
    if (video != null) {
      if (video.isSetTitle()) {
        videoTitleTextField.setText(video.getTitle());
      } else {
        videoTitleTextField.setText("");
      }
      
      if (video.isSetDate()) {
        videoDateTextField.setText(FDF.format(video.getDate()));
      } else {
        videoDateTextField.setText("");
      }
      
      if (video.isSetImage()) {
        videoImageFileSelecter.setValue(new File(video.getImage()));
      } else {
        videoImageFileSelecter.setValue(null);
      }
      
      videoImagePanel.getChildren().clear();
      if (video.isSetImage()) {
        String  imageFileName = video.getImage();
        Image image = new Image("file:" + imageFileName, -1, 300,  true, true);
        ImageView imageView = new ImageView(image);
        videoImagePanel.getChildren().add(imageView);
      }
          
      subjects = (List<Subject>) EcoreUtil.copyAll(video.getSubjects());
      subjectsTable.setObjects(video, MediadbPackage.eINSTANCE.getVideo_Subjects());
    } else {
      videoTitleTextField.setText("");
      videoDateTextField.setText("");
      videoImageFileSelecter.setValue(null);
    }
  }
  
  
  
//  
//  private void updateImagesPanel(HBox imagesBox, List<File> imageFiles) {
//    LOGGER.info("=>");
//    
//    imagesBox.getChildren().clear();
//    
//    for (File file: imageFiles) {
//      Image image = new Image("file:" + file.getAbsolutePath(), 0.0, 200.0, true, true);
//      ImageView imageView = new ImageView(image);
//      imageView.setOnMouseEntered(e -> showLargePicture("file:" + file.getAbsolutePath()));
//      imageView.setOnMouseExited(e -> {
//        if (currentLargePictureStage != null) {
//          currentLargePictureStage.close();
//        }
//      });
//      imagesBox.getChildren().add(imageView);
//    }
//    
//    LOGGER.info("<=");
//  }
    
    
  /**
   * Create a panel with 'Add album' and a 'Cancel' buttons.
   * <p>
   * The action for the 'Add album' button is {@link #addVideo()}.
   * The action for the 'Cancel' button is {@link #closeWindow()}.
   * 
   * @return
   */
  private Node createOkOrCancelPanel() {
    HBox hBox = componentFactory.createHBox(10.0, 12.0);
   
    // Two buttons: "Add video", "Cancel".
    Button button;
    
    button = componentFactory.createButton("Add video", "Add the video to the media database");
    button.setOnAction(e -> addVideo());
    hBox.getChildren().add(button);
    
    button = componentFactory.createButton("Cancel", "Exit window without saving");
    button.setOnAction(e -> closeWindow());
    hBox.getChildren().add(button);
    
    return hBox;
  }
  
  
  
  /**
   * Create a Video based on the provided information and add it to the media database.
   * <p>
   * If the provided information is correct, the Video is created and added to the media database.
   * Otherwise, an Alert is shown with information about what is wrong.
   */
  protected void addVideo() {
    try {
    Video video = createVideoFromControls();
    mediaDb.getVideos().add(video);
    } catch (ObjectEditorException e) {
      StringBuilder buf = new StringBuilder();
      for (String problem: e.getProblems()) {
        buf.append(problem);
        buf.append(NEW_LINE);
      }
      componentFactory.createErrorDialog("Problem in video details", buf.toString()).showAndWait();
    }
  }
  
  /**
   * Create an Album based on the provided information.
   * <p>
   * If the provided information isn't correct, an {@link AlbumDetailsException} is throw with a list error messages.
   * 
   * @return the new Album
   * @throws AlbumtDetailsException if the provided information isn't correct.
   */
  private Video createVideoFromControls() throws ObjectEditorException {
    Video video;
    
    video = MEDIA_DB_FACTORY.createVideo();
    
    List<String> problems = new ArrayList<>();
    
    // Film title
    String titleText = videoTitleTextField.getText().trim();
    if (titleText.isEmpty()) {
      problems.add("Film title may not be empty");
    } else {
      video.setTitle(titleText);
    }

    // Film date
    FlexDate date;
    String dateText = videoDateTextField.getText().trim();
    if (!dateText.isEmpty()) {
      try {
        date = FDF.parse(dateText);
        video.setDate(date);
      } catch (ParseException e) {
        problems.add("Date is not valid: " + dateText);
      }
    }
    
    // Video image
    String imageText = videoImageFileSelecter.getAbsolutePath().trim();
    if (!imageText.isEmpty()) {
      video.setImage(imageText);
    }
    
    video.getSubjects().addAll(subjects);
        
    return video;
  }

  private void closeWindow() {
    System.out.println("AlbumDetailsEditor: closeWindow");
    this.close();
  }
}
