package goedegep.markdowneditor.gui;

import goedegep.jfx.AbstractAppResourcesFx;
import goedegep.resources.ImageResource;
import goedegep.resources.ImageSize;
import javafx.scene.image.Image;

/**
 * This class provides the application resources for the Markdown editor.
 */
public class MarkdownEditorResources extends AbstractAppResourcesFx {

  @Override
  protected void readResources() {
    Image[] applicationImages = new Image[4];
    Image picture = null;
    
    try {
      applicationImages[0] = ImageResource.MARKDOWN.getImage(ImageSize.SIZE_0);
      applicationImages[1] = ImageResource.MARKDOWN.getImage(ImageSize.SIZE_0);
      applicationImages[2] = ImageResource.MARKDOWN.getImage(ImageSize.SIZE_0);
      applicationImages[3] = ImageResource.MARKDOWN.getImage();
      
      picture = applicationImages[3];
      
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    
    setRawImages(null, null, null, applicationImages);
    setPicture(picture);
  }
  
}
