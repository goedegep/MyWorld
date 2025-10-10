package goedegep.media.photo;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.imaging.ImageReadException;

import goedegep.util.Tuplet;
import goedegep.util.file.FileUtils;
import goedegep.util.img.PhotoFileMetaDataHandler;
import javafx.concurrent.Task;

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
public class GatherPhotoMetaDataTask extends Task<Tuplet<String, List<PhotoMetaData>>> {
  private final static Logger LOGGER = Logger.getLogger(GatherPhotoMetaDataTask.class.getName());
  
  private String photosFolder;                            // the top level photos folder
  private List<String> skipFolders;                       // folder names to skip.
  private List<String> supportedFileTypes;                // file extensions of files which are considered to be photos.
  private long totalNumberOfPhotos;                       // number of photos in the folder which is being handled.
  private long photosWithoutCoordinates;                  // number of photos without coordinates.
  private long numberOfPhotosHandled;                     // the number of photos handled so far.
  
  /**
   * Constructor
   * 
   * @param photosFolder the top level photos folder.
   * @param skipFolders folder names to skip
   * @param supportedFileTypes file extensions of files which are considered to be photos. This value may not be null.
   */
  public GatherPhotoMetaDataTask(String photosFolder, List<String> skipFolders, List<String> supportedFileTypes) {
    this.photosFolder = photosFolder;
    this.skipFolders = skipFolders;
    this.supportedFileTypes = supportedFileTypes;
  }

  /**
   * Handle requests from the request queue.
   */
  @Override
  protected Tuplet<String, List<PhotoMetaData>> call() throws Exception {
    handleFolder(Paths.get(photosFolder));
    
    return null;
  }
  
  private void handleFolder(Path photoFolder) throws Exception {
    String photoFolderName = photoFolder.toString();
    updateMessage("Gathering photo information from folder: " + photoFolderName);

    // Get information about all photos in the folder
    List<PhotoMetaData> photoInfoList = getMetaDataOfPhotosInAFolder(photoFolder);
    
    // Report the information back to the application.
    Tuplet<String, List<PhotoMetaData>> photoFolderInfo = new Tuplet<>(photoFolderName, photoInfoList);
    updateMessage("Information gathered for " + totalNumberOfPhotos + " photos from folder " + photoFolderName + ". (" + photosWithoutCoordinates + " photos without coordinates");
    updateValue(photoFolderInfo);
    
    // Next recurse into all subfolders
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(photoFolder)) {

      for (Path checkPath: stream) {
        if (Files.isDirectory(checkPath)) {
          String foldername = checkPath.getFileName().toString();
          LOGGER.severe("foldername: " + foldername);
          if (!skipFolders.contains(foldername)) {
            LOGGER.severe("going down into folder: " + foldername);
            if (!"Visual fun".equals(foldername)) {
              handleFolder(checkPath);
            } else {
              LOGGER.severe("Skipping folder: " + foldername);  // TODO Find out why it hangs on this folder
            }
          } else {
            LOGGER.severe("skipping folder: " + foldername);
          }
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      x.printStackTrace();
    }
    
  }
  
  /**
   * Gather all information needed for each photo in a specific folder.
   * <p>
   * Progress is reported after handling each photo.
   * 
   * @param photoFolder the folder to handle
   * @return a list of PhotoInfo, the information about the photos in the {@code photoFolder}.
   */
  private List<PhotoMetaData> getMetaDataOfPhotosInAFolder(Path photoFolder) {
    List<PhotoMetaData> photoMetaDataList = new ArrayList<>();
    numberOfPhotosHandled = 0;
    photosWithoutCoordinates = 0;
    updateProgress(0, totalNumberOfPhotos);
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(photoFolder)) {

      for (Path checkDirectory: stream) {
        if (!Files.isDirectory(checkDirectory)) {
          String fileExtension = FileUtils.getFileExtension(checkDirectory);
           if (supportedFileTypes.contains(fileExtension.toLowerCase())) {
            // it is a supported picture file.
            PhotoMetaData photoMetaData = createPhotoMetaData(checkDirectory.toAbsolutePath().toString());
            if (photoMetaData.getCoordinates() == null) {
              photosWithoutCoordinates++;
            }
            photoMetaDataList.add(photoMetaData);
            
            numberOfPhotosHandled++;
            updateProgress(numberOfPhotosHandled, totalNumberOfPhotos);
          }
        }
      }

    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    
    updateProgress(-1, 0);
    
    return photoMetaDataList;    
  }

  /**
   * Gather the meta data of a photo.
   * <p>
   * The gathered information is stored in a {@link PhotoMetaData} object.
   * 
   * @param fileName the file name of the photo
   * @return all needed information about the photo
   */
  public static PhotoMetaData createPhotoMetaData(String fileName) {
    PhotoMetaData photoMetaData = new PhotoMetaData();

    photoMetaData.setFileName(fileName);

    try {
      PhotoFileMetaDataHandler photoFileMetaDataHandler = new PhotoFileMetaDataHandler(new File(fileName));
      photoMetaData.setTitle(photoFileMetaDataHandler.getTitle());
      photoMetaData.setDeviceSpecificPhotoTakenTime(photoFileMetaDataHandler.getCreationDateTime());
      photoMetaData.setCoordinates(photoFileMetaDataHandler.getGeoLocation());
      photoMetaData.setApproximateGPScoordinates(photoFileMetaDataHandler.hasApproximateCoordinates());
    } catch (ImageReadException e) {
      LOGGER.severe("ImageReadException while reading file " + fileName + ", message: " + e.getMessage());
    } catch (IOException e) {
      LOGGER.severe("IOException while reading file " + fileName + ", message: " + e.getMessage());
    }
    photoMetaData.setModificationDateTime(FileUtils.getLastModificationDate(new File(fileName)));

    return photoMetaData;
  }
}
