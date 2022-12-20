package goedegep.media.fotomapview.guifx;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.media.app.base.MediaAppResourcesFx;
import goedegep.media.photo.IPhotoMetaData;
import goedegep.media.photo.IPhotoMetaDataWithImage;
import goedegep.media.photo.PhotoMetaData;
import goedegep.media.photo.PhotoMetaDataWithImage;
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
public class PhotoListCell extends ListCell<IPhotoMetaData> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(PhotoListCell.class.getName());

  private static CustomizationFx customization;
  private static MediaAppResourcesFx mediaAppResourcesFx;
  
  private IPhotoMetaData previousPhotoMetaData = null;
  private ChangeListener<IPhotoMetaData> changeListener;
  
  /**
   * Set the GUI customization for these cells.
   * 
   * @param customization the GUI customization.
   */
  public static void setCustomization(CustomizationFx customization) {
    PhotoListCell.customization = customization;
    PhotoListCell.mediaAppResourcesFx = (MediaAppResourcesFx) customization.getResources();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void updateItem(final IPhotoMetaData photoMetaData, final boolean empty) {
    super.updateItem(photoMetaData, empty);
    
    if (previousPhotoMetaData != null) {
      previousPhotoMetaData.removeListener(changeListener);
    }
    
    if (empty) {
      setText(null);
      setGraphic(null);
    } else {
      updateGraphicAndText(photoMetaData);
      
      setOnDragDetected((event) -> {
        Dragboard dragboard = startDragAndDrop(TransferMode.COPY);

        ClipboardContent content = new ClipboardContent();
        List<File> files = new ArrayList<>();
        File aFile = new File(photoMetaData.getFileName());
        files.add(aFile);
        content.putFiles(files);
        dragboard.setContent(content);

        event.consume();
      });
      
      setOnMouseClicked(e -> handleMouseEventOnPhotoIcon(e, photoMetaData));
      
      changeListener = (observable, oldValue, newValue) -> updateGraphicAndText(newValue);
          
      photoMetaData.addListener(changeListener);
      previousPhotoMetaData = photoMetaData;
    }
  }
  
  private void updateGraphicAndText(final IPhotoMetaData photoMetaData) {
    
    /*
     * The Graphic is a StackPane: the photo, with on top icons for title and coordinates.
     */          
    StackPane stackPane = new StackPane();
    
    if (photoMetaData instanceof IPhotoMetaDataWithImage photoMetaDataWithImage) {

      ImageView imageView = new ImageView();
      imageView.setFitHeight(150);
      imageView.setPreserveRatio(true);
      imageView.setImage(photoMetaDataWithImage.getImage());
      stackPane.getChildren().add(imageView);
    }

    boolean hasTitle = (photoMetaData.getTitle() != null);
    boolean hasCoordinates = (photoMetaData.getCoordinates() != null);

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
    File aFile = new File(photoMetaData.getFileName());
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
  private void handleMouseEventOnPhotoIcon(MouseEvent mouseEvent, IPhotoMetaData photoInfo) {
    if (mouseEvent.getClickCount() > 1) {
      new PhotoMetaDataEditor(customization, photoInfo);
    } else {
    }
  }
  

}
