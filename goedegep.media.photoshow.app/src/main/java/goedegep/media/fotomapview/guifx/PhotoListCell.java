package goedegep.media.fotomapview.guifx;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.media.app.base.MediaAppResourcesFx;
import goedegep.media.fotoshow.app.guifx.PhotoInfo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * This class provides a {@link ListCell} which shows a photo thumbnail with indicators for whether the title and coordinates are set.
 */
public class PhotoListCell extends ListCell<PhotoInfo> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(PhotoListCell.class.getName());

  private static CustomizationFx customization;
  private static MediaAppResourcesFx mediaAppResourcesFx;
  
  private PhotoInfo previousPhotoInfo = null;
  private ChangeListener<? super PhotoInfo> changeListener;
  
  public static void setCustomization(CustomizationFx customization) {
    PhotoListCell.customization = customization;
    PhotoListCell.mediaAppResourcesFx = (MediaAppResourcesFx) customization.getResources();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void updateItem(final PhotoInfo photoInfo, final boolean empty) {
    super.updateItem(photoInfo, empty);
    
    if (previousPhotoInfo != null) {
      previousPhotoInfo.removeListener(changeListener);
    }
    
    if (empty) {
      setText(null);
      setGraphic(null);
    } else {
      updateGraphicAndText(photoInfo);
      
      setOnDragDetected((event) -> {
        Dragboard dragboard = startDragAndDrop(TransferMode.COPY);

        ClipboardContent content = new ClipboardContent();
        List<File> files = new ArrayList<>();
        File aFile = new File(photoInfo.getFileName());
        files.add(aFile);
        content.putFiles(files);
        dragboard.setContent(content);

        event.consume();
      });
      
      setOnMouseClicked(e -> handleMouseEventOnPhotoIcon(e, photoInfo));
      
      changeListener = new ChangeListener<>() {

        @Override
        public void changed(ObservableValue<? extends PhotoInfo> observable, PhotoInfo oldValue, PhotoInfo newValue) {
          updateGraphicAndText(newValue);
          
        }
        
      };
      photoInfo.addListener(changeListener);
      previousPhotoInfo = photoInfo;
    }
  }
  
  private void updateGraphicAndText(final PhotoInfo photoInfo) {
    
    /*
     * The Graphic is a StackPane: the photo, with on top icons for title and coordinates.
     */          
    StackPane stackPane = new StackPane();

    ImageView imageView = new ImageView();
    imageView.setFitHeight(150);
    
    imageView.setPreserveRatio(true);
    imageView.setImage(photoInfo.getImage());
    stackPane.getChildren().add(imageView);

    boolean hasTitle = (photoInfo.getTitle() != null);
    boolean hasCoordinates = (photoInfo.getCoordinates() != null);

    if (hasTitle  ||  hasCoordinates) {
      HBox hBox = new HBox();
      hBox.setSpacing(10);
      hBox.setPadding(new Insets(8));
      
      if (hasTitle) {
        Image titleImage = mediaAppResourcesFx.getTitleIcon();
        ImageView titleImageView = new ImageView(titleImage);
        hBox.getChildren().add(titleImageView);
      }
      
      if (hasCoordinates) {
        Image coordinatesImage = mediaAppResourcesFx.getCoordinatesIcon();
        ImageView coordinatesImageView = new ImageView(coordinatesImage);
        hBox.getChildren().add(coordinatesImageView);
      }
      
      stackPane.getChildren().add(hBox);
    }
    setGraphic(stackPane);

    // Get the photo file name from the full path name
    File aFile = new File(photoInfo.getFileName());
    setText(aFile.getName());
  }
  

  /**
   * Show the single current photo.
   * <p>
   * If there is a current photo, is will be removed.<br/>
   * After this the current photo is shown, limiting the dimensions to MAX_IMAGE_WIDTH and MAX_IMAGE_HEIGHT.
   * @param mouseEvent 
   * 
   * @param coordinates the location where the photo is shown.
   * @param text An optional title for the photo. 
   * @param fileName the file name of the photo to be shown.
   */
  private void handleMouseEventOnPhotoIcon(MouseEvent mouseEvent, PhotoInfo photoInfo) {
    if (mouseEvent.getClickCount() > 1) {
      new PhotoMetaDataEditor(customization, photoInfo);
    } else {
    }
  }
  

}
