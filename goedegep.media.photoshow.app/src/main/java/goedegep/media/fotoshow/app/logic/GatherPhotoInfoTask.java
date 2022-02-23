package goedegep.media.fotoshow.app.logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import org.apache.commons.imaging.ImageReadException;

import goedegep.media.fotoshow.app.guifx.PhotoInfo;
import goedegep.util.Tuplet;
import goedegep.util.file.FileUtils;
import goedegep.util.img.PhotoFileMetaDataHandler;
import javafx.concurrent.Task;
import javafx.scene.image.Image;

/**
 * This class provides a Task which gathers information on photos in folders.
 * <p>
 * Upon creation two parameters are required
 * <ul>
 * <li>
 * a request queue</br>
 * Each request is a the name of a folder for which the photo information is to be gathered.
 * </li>
 * <li>
 * a list of supported file types (specified as file extensions)</br>
 * Only files with one of these extensions are considered to be a photo.
 * </li>
 * </ul>
 * Once started, this task never ends.<br/>
 * It waits till a new request appears in the request queue, if so is will gather the photo information for this folder.</br>
 * A request is handled as follows:
 * <ul>
 * <li>
 * a message is sent to indicate the start of handling a folder.
 * </li>
 * <li>
 * the total number of photos in the folder is counted. This number is used to provide a reliable progress indication.
 * </li>
 * <li>
 *  a message is sent with the number of photos and indicating that information gathering is in progress.
 * </li>
 * <li>
 * progress information is sent, indicating 'no progress'.
 * </li>
 * <li>
 * while gathering the information, actual progress is reported after handling each photo.</br>
 * When all photos are handled, progress is set to 'undefined'.
 * </li>
 * <li>
 * after gathering the information, a message and the results are sent.
 * </li>
 * </ul>
 */
public class GatherPhotoInfoTask extends Task<Tuplet<String, List<PhotoInfo>>> {
  private final static Logger LOGGER = Logger.getLogger(GatherPhotoInfoTask.class.getName());
  
  private BlockingQueue<String> photoFoldersToHandle;     // the request queue
  private List<String> supportedFileTypes;                // file extensions of files which are considered to be photos.
  private long totalNumberOfPhotos;                       // number of photos in the folder which is being handled.
  private long photosWithoutCoordinates;                  // number of photos without coordinates.
  private long numberOfPhotosHandled;                     // the number of photos handled so far.
  private int imageSize = -1; 
  
  /**
   * Constructor
   * 
   * @param photoFoldersToHandle the request queue. This value may not be null.
   * @param supportedFileTypes file extensions of files which are considered to be photos. This value may not be null.
   */
  public GatherPhotoInfoTask(BlockingQueue<String> photoFoldersToHandle, List<String> supportedFileTypes, int imageSize) {
    this.photoFoldersToHandle = photoFoldersToHandle;
    this.supportedFileTypes = supportedFileTypes;
    this.imageSize = imageSize;
  }

  /**
   * Handle requests from the request queue.
   */
  @Override
  protected Tuplet<String, List<PhotoInfo>> call() throws Exception {
    LOGGER.info("=>");
    
    while (true) {
      String photoFolderName = photoFoldersToHandle.take();
      
      updateMessage("Gathering photo information from folder: " + photoFolderName);
      
      // Convert the folder name to a Path
      Path photoFolder = Paths.get(photoFolderName);
     
      // Count the number of photos in the folders.
      totalNumberOfPhotos = getNumberOfPhotosInFolder(photoFolder);
      updateMessage("Gathering information on " + totalNumberOfPhotos + " photos from folder " + photoFolderName);

      
      // Get information about all photos in the folders
      List<PhotoInfo> photoInfoList = getInfoOfPhotosInAFolder(photoFolder);
      
      // Report the information back to the application.
      Tuplet<String, List<PhotoInfo>> photoFolderInfo = new Tuplet<>(photoFolderName, photoInfoList);
      updateMessage("Information gathered for " + totalNumberOfPhotos + " photos from folder " + photoFolderName);
      updateValue(photoFolderInfo);
      LOGGER.info("Finished handling folder: " + photoFolderName + ", photos found: " + totalNumberOfPhotos + ", photosWithoutCoordinates: " + photosWithoutCoordinates);
      Thread.sleep(100);
    }

  }
  
  /**
   * Count the number of photos in a folder.
   * <p>
   * This method counts the number of files in the specified folder, which have a supported file extension.
   * 
   * @param photoFolder the folder in which to count the photos
   * @return the number of photos in folder {@code photoFolder}.
   */
  private int getNumberOfPhotosInFolder(Path photoFolder) {
    int numberOfPhotos = 0;
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(photoFolder)) {

      for (Path checkDirectory: stream) {
        if (!Files.isDirectory(checkDirectory)) {
          String fileExtension = FileUtils.getFileExtension(checkDirectory);
          LOGGER.fine("extensie: " + fileExtension);
          if (supportedFileTypes.contains(fileExtension.toLowerCase())) {
            // it is a supported picture file.
            numberOfPhotos++;
          }
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    
    return numberOfPhotos;
  }
  
  /**
   * Gather all information needed for each photo in a specific folder.
   * <p>
   * Progress is reported after handling each photo.
   * 
   * @param photoFolder the folder to handle
   * @return a list of PhotoInfo, the information about the photos in the {@code photoFolder}.
   */
  private List<PhotoInfo> getInfoOfPhotosInAFolder(Path photoFolder) {
    List<PhotoInfo> photoInfoList = new ArrayList<>();
    numberOfPhotosHandled = 0;
    photosWithoutCoordinates = 0;
    updateProgress(0, totalNumberOfPhotos);
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(photoFolder)) {

      for (Path checkDirectory: stream) {
        if (!Files.isDirectory(checkDirectory)) {
          String fileExtension = FileUtils.getFileExtension(checkDirectory);
          LOGGER.fine("extensie: " + fileExtension);
          if (supportedFileTypes.contains(fileExtension.toLowerCase())) {
            // it is a supported picture file.
            PhotoInfo photoInfo = createPhotoInfo(checkDirectory.toAbsolutePath().toString(), imageSize);
            if (photoInfo.getCoordinates() == null) {
              photosWithoutCoordinates++;
            }
            photoInfoList.add(photoInfo);
            
            numberOfPhotosHandled++;
            updateProgress(numberOfPhotosHandled, totalNumberOfPhotos);
          }
        }
      }

    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    
    updateProgress(-1, 0);
    
    return photoInfoList;    
  }

  /**
   * Gather the information of a photo.
   * <p>
   * The gathered information is stored in a {@link PhotoInfo} object.
   * 
   * @param fileName the file name of the photo
   * @return all needed information about the photo
   */
  public static PhotoInfo createPhotoInfo(String fileName, int imageSize) {
    LOGGER.info("=> fileName=" + fileName);
    
    PhotoInfo photoInfo = new PhotoInfo();

    photoInfo.setFileName(fileName);

    if (imageSize != -1) {
      Image image = new Image("file:" + fileName, imageSize, imageSize, true, true);
      photoInfo.setImage(image);
    }

    try {
      PhotoFileMetaDataHandler photoFileMetaDataHandler = new PhotoFileMetaDataHandler(new File(fileName));
      photoInfo.setTitle(photoFileMetaDataHandler.getTitle());
      photoInfo.setDeviceSpecificPhotoTakenTime(photoFileMetaDataHandler.getCreationDateTime());
      photoInfo.setCoordinates(photoFileMetaDataHandler.getGeoLocation());
      photoInfo.setApproximateGPScoordinates(photoFileMetaDataHandler.metadataHasApproximateCoordinatesIndication());
    } catch (ImageReadException e) {
      LOGGER.severe("ImageReadException while reading file " + fileName + ", message: " + e.getMessage());
//      e.printStackTrace();
    } catch (IOException e) {
      LOGGER.severe("IOException while reading file " + fileName + ", message: " + e.getMessage());
//      e.printStackTrace();
    }
    photoInfo.setModificationDateTime(FileUtils.getLastModificationDate(new File(fileName)));

    LOGGER.info("<=");
    return photoInfo;
  }
}
