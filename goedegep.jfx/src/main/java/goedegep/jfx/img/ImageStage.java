package goedegep.jfx.img;

import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ImageStage extends JfxStage {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(ImageStage.class.getName());
  private final static int MAX_HEIGHT = 1400;

  public ImageStage(CustomizationFx customization, String pictureFilename) {
    super(customization, pictureFilename);
    
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    
    Image image = new Image("file:" + pictureFilename);
    ImageView imageView = new ImageView(image);

    if (imageView.maxHeight(-1) > MAX_HEIGHT) {
      imageView.setFitHeight(MAX_HEIGHT);
    }
    imageView.setPreserveRatio(true);
    imageView.setOnMouseClicked((e) -> close());

    VBox vBox = componentFactory.createVBox();
    vBox.getChildren().add(imageView);
    
    setScene(new Scene(vBox));
    
    
    show();
  }
}
