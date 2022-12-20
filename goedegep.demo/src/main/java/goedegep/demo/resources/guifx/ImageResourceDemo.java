package goedegep.demo.resources.guifx;

import java.util.logging.Logger;

import goedegep.jfx.DefaultCustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.resources.ImageFileInfo;
import goedegep.resources.ImageResource;
import goedegep.resources.ImageSize;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ImageResourceDemo extends JfxStage {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(ImageResourceDemo.class.getName());
  
  private final static String WINDOW_TITLE = "Demo";
  
//  private ComponentFactoryFx componentFactory;
  

  public ImageResourceDemo() {
    super(WINDOW_TITLE, DefaultCustomizationFx.getInstance());
    
//    componentFactory = customization.getComponentFactoryFx();
    
    createGUI();
    
    show();
  }
  
  private void createGUI() {
    VBox vBox = new VBox();
    
    vBox.getChildren().add(createImagesOverviewPanel());
    vBox.getChildren().add(createDifferentSizeImagePanel());
    
    Scene scene = new Scene(vBox, 1500, 900);
    setScene(scene);
  }

  private Node createImagesOverviewPanel() {
    FlowPane flowPane = new FlowPane();
    final ScrollPane scrollPane = new ScrollPane();

    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Horizontal scroll bar
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Vertical scroll bar
    scrollPane.setContent(flowPane);
    scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
        @Override
        public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
            flowPane.setPrefWidth(bounds.getWidth());
            flowPane.setPrefHeight(bounds.getHeight());
        }
    });    
    
    for (ImageResource imageResource: ImageResource.values()) {
      flowPane.getChildren().add(createImagePanel(imageResource));
    }
    
    return scrollPane;
  }

  private Node createImagePanel(ImageResource imageResource) {
    VBox vBox = new VBox();
    
    vBox.setPadding(new Insets(12.0));
    
    Image image = imageResource.getImage(ImageSize.SIZE_2);
    ImageView imageView = new ImageView(image);
    vBox.getChildren().add(imageView);
    
    for (ImageFileInfo imageFileInfo: imageResource.getImageFilesInfo()) {
      Label label = new Label(imageFileInfo.filename() + ": " + imageFileInfo.width() + " x " + imageFileInfo.height());
      vBox.getChildren().add(label);
    }
    Label description = new Label(imageResource.getDescription() != null ? imageResource.getDescription() : "<no description>");
    Label credits = new Label(imageResource.getCredits() != null ? imageResource.getCredits() : "<no credits>");
    vBox.getChildren().addAll(description, credits);
    
    return vBox;
  }
  
  private Node createDifferentSizeImagePanel() {
    ObservableList<ImageResourceWrapper> imageResourceWrappers = FXCollections.observableArrayList();
    
    for (ImageResource imageResource: ImageResource.values()) {
      imageResourceWrappers.add(new ImageResourceWrapper(imageResource));
    }
    
    ComboBox<ImageResourceWrapper> comboBox = new ComboBox<>(imageResourceWrappers);
    
    HBox hBox = new HBox();
    hBox.setPadding(new Insets(12.0));
    
    hBox.getChildren().add(comboBox);
    
    HBox imageHBox = new HBox();
    imageHBox.setPadding(new Insets(12.0));
    hBox.getChildren().add(imageHBox);
    
    comboBox.setOnAction((e) -> {
      imageHBox.getChildren().clear();
      ImageResourceWrapper imageResourceWrapper = comboBox.getSelectionModel().getSelectedItem();
      if (imageResourceWrapper != null) {
        
        ImageResource imageResource = imageResourceWrapper.getImageResource();
        Image image = imageResource.getImage();
        imageHBox.getChildren().add(createBoxedImageView(image));
        
        for (ImageSize imageSize: ImageSize.values()) {
          image = imageResource.getImage(imageSize);
          imageHBox.getChildren().add(createBoxedImageView(image));
        }
      }
    });
    
    if (!imageResourceWrappers.isEmpty()) {
      comboBox.getSelectionModel().select(0);
    }
    
    return hBox;
  }
  
  private Node createBoxedImageView(Image image) {
    HBox hBox = new HBox();
    hBox.setPadding(new Insets(12.0));
    
    ImageView imageView = new ImageView(image);
    hBox.getChildren().add(imageView);
    
    return hBox;
  }
}

class ImageResourceWrapper {
  private ImageResource imageResource;
  
  ImageResourceWrapper(ImageResource imageResource) {
    this.imageResource = imageResource;
  }
  
  public String toString() {
    return imageResource.name();
  }

  public ImageResource getImageResource() {
    return imageResource;
  }
    
}
