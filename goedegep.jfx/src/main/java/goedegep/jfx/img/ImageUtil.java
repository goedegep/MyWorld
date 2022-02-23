package goedegep.jfx.img;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class ImageUtil {
  private final static Logger LOGGER = Logger.getLogger(ImageUtil.class.getName());

  /**
   * Create a thumbnail for an image file.
   * <p>
   * An image file with a size of 1000 x 1000 is created for a given (larger) image.
   * 
   * @param imageFilename filename of the picture for which a thumbnail is to be created.
   * @param thumbnailFolder the folder in which the thumbnail will be created.
   * @throws IOException if the thumbnail file cannot be written.
   */
  public void createThumbnail(String imageFilename, File thumbnailFolder) throws IOException {
    LOGGER.info("=> fileName=" + imageFilename + ", showFolder=" + thumbnailFolder.getAbsolutePath());
    Image image = new Image("file:" + imageFilename, 1000.0, 1000.0, true, true);
    File imageFile = new File(imageFilename);
    String imageFileName = imageFile.getName();
    Path thumbnailPath = Paths.get(thumbnailFolder.getAbsolutePath(), imageFileName);
    File file = thumbnailPath.toFile();
    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "jpg", file);
  }

}
