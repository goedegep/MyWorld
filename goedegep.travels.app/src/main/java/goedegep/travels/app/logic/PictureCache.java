package goedegep.travels.app.logic;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

/**
 * This class is currently not in use. I'm now using thumbnails, which seems to perform well enough.
 * TODO: Remove this class if not needed anymore.
 */
public class PictureCache {
  
  private static PictureCache instance;
  private Map<String, Path> pictureToThumbnailMap = new HashMap<>();
  private FileSystem imfs;
  private Path thumbnailsDirPath;

  public static PictureCache getInstance() {
    if (instance == null) {
      instance = new PictureCache();
    }
    return instance;
  }
  
  /**
   * Constructor for PictureCache class.
   */
  private PictureCache() {
    
    // Create the in-memory file system for the PDF generation task.
    imfs = Jimfs.newFileSystem(Configuration.unix());
    thumbnailsDirPath = imfs.getPath("/thumbnails");
    try {
      Files.createDirectory(thumbnailsDirPath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public void createThumbnails(Path picturePath) {
    List<String> pictures = new ArrayList<>();
    VacationsChecker.getPhotosInFolder(picturePath, pictures);
    
    for (String picture : pictures) {
      createThumbnail(picture);
    }
  }

  /**
   * Creates a thumbnail for the given picture and stores it in the in-memory file system.
   * @param picture The path to the original picture file.
   */
  private void createThumbnail(String picture) {
    try {
        // Read the original image
        BufferedImage originalImage = ImageIO.read(Paths.get(picture).toFile());
        if (originalImage == null) {
            return; // Not a valid image
        }
        int maxDimension = 750;
        int thumbWidth;
        int thumbHeight;
        int originalHeight = originalImage.getHeight();
        int originalWidth = originalImage.getWidth();
        if (originalHeight > originalWidth) {
            // Portrait orientation, height is max, width relative to height
          thumbHeight = maxDimension;
          thumbWidth = (int) ((double) originalWidth / originalHeight * maxDimension);
        } else {
            // Landscape orientation, width is max, height relative to width
          thumbWidth = maxDimension;
          thumbHeight = (int) ((double) originalHeight / originalWidth * maxDimension);
        }
        BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, java.awt.image.BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = thumbImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, thumbWidth, thumbHeight, null);
        g2d.dispose();

        // Create thumbnail file name
        String fileName = Paths.get(picture).getFileName().toString();
        String thumbFileName = fileName.replaceAll("\\.jpg$", "_thumb.jpg");
        Path thumbPath = thumbnailsDirPath.resolve(thumbFileName);

        // Write the thumbnail image to the in-memory file system
        try (OutputStream os = Files.newOutputStream(thumbPath)) {
            ImageIO.write(thumbImage, "jpg", os);
        }

        // Map the original picture to its thumbnail path (as string)
        pictureToThumbnailMap.put(picture, thumbPath);
    } catch (Exception e) {
        // Handle exceptions (log or ignore as needed)
        e.printStackTrace();
    }
  }
  
  public Path getThumbnailPath(String picture) {
    return pictureToThumbnailMap.get(picture);
  }
}