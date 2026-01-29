package goedegep.travels.logic;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import goedegep.travels.svc.TravelsService;
import goedegep.util.file.FileUtils;

/**
 * This class manages photo thumbnails for vacation photos. It is meant to be used by the TravelsApplication.<br/>
 * In the constructor you provide the following parameters:
 * <ul>
 * <li>the starting folder where the vacation photos are stored</li>
 * <li>a list of folder names to ignore (e.g. ".thumbnails")</li>
 * </ul>
 * In the starting folder a folder per vacation is expected, and in each vacation folder a single thumbnails file is maintained.<br/>
 * The thumbnails are stored in a zip file named ".thumbnails.zip" in the vacation folder.<br/>
 * The photos may be in the vacation folder and/or in subfolders. Subfolders with names in the ignore list are skipped.<br/>
 * <p>
 * The status of the thumbnails is maintained in a meta data file (see META_DATA_FILE_NAME constant).<br/>
 * When started, the meta data file is read, and then all thumbnails are updated if needed.<br/>
 * After that, it listens to changes in the vacation folder set by setVacationFolder.
 */
public class PhotoThumbnailManager implements Runnable {
  private static final Logger LOGGER = Logger.getLogger(PhotoThumbnailManager.class.getName());
  
  private static final String META_DATA_FILE_NAME = "D:\\Photo\\Vakanties\\.thumbnails.meta";
  
  /**
   * 
   */
  private final List<String> ignoreFolders;
  
  /**
   * The folder below which thumbnails will be maintained.
   */
  private final Path startingFolder;
  
  /**
   * The zip file system for the current thumbnails zip file.
   */
  private FileSystem zipfs;
  
  /**
   * Map of photo file paths to their last modified timestamp (in milliseconds).
   */
  private Map<String, Long> photoMetaData = new HashMap<>();
  
  /**
   * Constructor.
   * 
   * @param startingFolder the folder below which thumbnails will be maintained.
   * @param ignoreFolders list of folder names to ignore (e.g. ".thumbnails").
   */
  public PhotoThumbnailManager(Path startingFolder, List<String> ignoreFolders) {
    this.startingFolder = startingFolder;
    this.ignoreFolders = ignoreFolders;
  }

  /**
   * The main method of the PhotoThumbnailManager. It reads the meta data file, updates all thumbnails, and then listens to changes in the photo directory.
   */
  @Override
  public void run() {
    // Read the meta data file.
    readMetaDataFile();
    
    // register to changes in the relavant directories.
    TravelsService.getInstance().addDirectoryChangesListener(event -> {
      LOGGER.severe("Directory change event: type=" + event.kind() + ", path=" + event.context().toString());
    });
    
    // update all thumbnail zip files
    updateThumbnails();
    
    // This should actually be done when the application is closing. But I often just kill the application, so doing it here for now.
    writeMetaDataFile();
    
    // react to changes in the photo directory
  }

  /**
   * Reads the meta data file that contains information about existing thumbnails.
   * Line format: <photo file path>|<last modified timestamp>
   */
  private void readMetaDataFile() {
    Path metaDataFilePath = Paths.get(META_DATA_FILE_NAME);
    if (!Files.exists(metaDataFilePath)) {
      return;
    }
    
    try {
      Files.lines(metaDataFilePath).forEach(line -> {
        String[] parts = line.split("\\|");
          String photoPath = parts[0];
          String lastModified = parts[1];
          Long lastModifiedLong = Long.valueOf(lastModified);
          photoMetaData.put(photoPath, lastModifiedLong);
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }

  /**
   * Writes the meta data file that contains information about existing thumbnails.
   */
  private void writeMetaDataFile() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(META_DATA_FILE_NAME, false))) {
      for (Map.Entry<String, Long> entry : photoMetaData.entrySet()) {
        String line = entry.getKey() + "|" + entry.getValue().toString();
        writer.write(line);
        writer.newLine();
      }
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }    
  }

  /**
   * Updates all thumbnails by walking the file tree starting from the starting folder.
   * For each photo file found, it checks if a thumbnail needs to be created or updated.<br/>
   * Information about photos no longer present is removed from the meta data.
   */
  private void updateThumbnails() {
    Set<String> existingPhotoPaths = new HashSet<>();
    
    try {Files.newDirectoryStream(startingFolder).forEach(p -> {
      if (!Files.isDirectory(p)) {
        return;
      }
      try {
        prepareForPhotoFolder(p);

        Files.walkFileTree(p, new FileVisitor<Path>() {

          @Override
          public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            String dirName = dir.getFileName().toString();
            if (ignoreFolders.contains(dirName)) {
              return FileVisitResult.SKIP_SUBTREE;
            }

            return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
            if (FileUtils.isJpegFile(filePath)) {
              addPhotoFile(p, filePath);
              existingPhotoPaths.add(filePath.toString());
            }
            return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            exc.printStackTrace();
            return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
          }
        });

        finishPhotoFolder(p);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    } catch (IOException e1) {
      e1.printStackTrace();
    }
        
    photoMetaData.entrySet().removeIf(e-> !existingPhotoPaths.contains(e.getKey()));
  }

  /**
   * Prepares for processing a vacation photo folder by opening (or creating) the thumbnails zip file.
   * 
   * @param dir the vacation photo folder
   * @throws IOException if an I/O error occurs
   */
  protected void prepareForPhotoFolder(Path dir) throws IOException {
    Path zipFilePath = dir.resolve(".thumbnails.zip");
    URI zipFileUri = zipFilePath.toUri();  // Extra step needed because URI.create(String) requires *encoded* string.
    URI uri = URI.create("jar:file:" + zipFileUri.getRawPath());
    
    Map<String, String> env = new HashMap<>();
    env.put("create", "true");  // Create the zip file if it doesn't exist

    zipfs = FileSystems.newFileSystem(uri, env);
  }

  protected void addPhotoFile(Path vacationFolder, Path filePath) {
    try {
      FileTime lastModifiedTime = Files.getLastModifiedTime(filePath);
      Long photoLastModified = lastModifiedTime.toMillis();
      
      Long thumbnailLastModified = photoMetaData.get(filePath.toString());
      if (thumbnailLastModified != null  &&  thumbnailLastModified >= photoLastModified) {
        // No need to update the thumbnail, it's already up-to-date.
        return;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    try {
      // Read the original image
      BufferedImage originalImage = ImageIO.read(filePath.toFile());
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
      BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
      Graphics2D g2d = thumbImage.createGraphics();
      g2d.drawImage(originalImage, 0, 0, thumbWidth, thumbHeight, null);
      g2d.dispose();

      // Create thumbnail file name
      String fileName = FileUtils.getPathRelativeToFolder(vacationFolder.toString(), filePath.toString());
//      String fileName = filePath.getFileName().toString();
      String thumbFileName = fileName.replaceAll("\\.jpg$", "_thumb.jpg");

      // Write the thumbnail image to the in-memory file system
      Path pathInZipfile = zipfs.getPath(thumbFileName);

      if (Files.exists(pathInZipfile)) {
        Files.delete(pathInZipfile);
      }
      Path parentDir = pathInZipfile.getParent();
      if (parentDir != null && !Files.exists(parentDir)) {
        Files.createDirectories(parentDir);
      }
      try (OutputStream os = Files.newOutputStream(pathInZipfile)) {
        ImageIO.write(thumbImage, "jpg", os);
      }

      FileTime fileTime = Files.getLastModifiedTime(pathInZipfile);
      Long thumbnailTimeLong = fileTime.toMillis();
      photoMetaData.put(filePath.toString(), thumbnailTimeLong);
    } catch (Exception e) {
      // Handle exceptions (log or ignore as needed)
      e.printStackTrace();
    }
  }

  protected void finishPhotoFolder(Path dir) throws IOException {
    zipfs.close();    
  }

}
