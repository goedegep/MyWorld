package goedegep.util.img;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.common.ImageMetadata.ImageMetadataItem;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffDirectory;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata.Directory;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata.TiffMetadataItem;
import org.apache.commons.imaging.formats.tiff.constants.GpsTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.MicrosoftTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;

import goedegep.geo.WGS84Coordinates;
import goedegep.util.file.FileUtils;
import goedegep.util.string.StringUtil;

/**
 * This class provides meta information about photo files (i.e. jpeg or tiff files).
 * <p>
 * When creating an instance of this class, the meta data is read from the file.
 * After this the different get methods provide access to the following:
 * <ul>
 * <li>title - see {@link #getTitle()}.</li>
 * <li>the creation date/time from a photo file (i.e. the date/time the photo was taken) - see {@link #getCreationDateTime()}</li>
 * <li>the creation date/time of a photo file (i.e. the date/time the photo was taken), from the item 'DateTimeOriginal' from the meta data - see {@link #getDateTimeOriginalFromMetaData()}</li>
 * <li>the creation date/time of a photo file (i.e. the date/time the photo was taken), from the filename - see {@link #getCreationDateTimeFromFileName()}</li>
 * <li>the last edit date/time of a photo file - see {@link #getEditDateTime()</li>
 * <li>the <code>WGS84Coordinates</code> from the photo file - see {@link #getGeoLocation()}</li>
 * <li>a single Tiff item from the meta data - see {@link #getTiffItemValue(int, String)}</li>
 * </ul>
 * 
 * If you only need one piece of meta data, you can use any of the following static methods:
 * <ul>
 * <li>the <code>WGS84Coordinates</code> from the photo file - see getGeoLocation(String)</li>
 * </ul>
 * 
 * The coordinates and meta data of a photo file can be updated via the method {@link #writeGeoLocationAndTitle()}.<br/>
 * The 'Orientation' of a photo in a photo file can be updated via the method {@link #writeOrientation(File, int) THIS METHOD ISN'T TESTED.
 * 
 * <p>
 * General information about creation date/time.
 * <ul>
 *   <li>Exif: DateTimeOriginal<br/>
 *   Seems to be the right value so far, but may not always be available. 
 *   </li>
 *   <li>Exif: DateTimeDigitized<br/>
 *   Also seems to be the right value so far, but may not always be available. 
 *   </li>
 *   <li>File name<br/>
 *   Often the filename includes the time the phote was taken. 
 *   </li>
 *   <li>Gps: GPSDateStamp + GPSTimeStamp<br/>
 *   Not sure about this. Might be the right time, but GMT time. 
 *   </li>
 *   <li>Root: DateTime<br/>
 *   This is NOT the right value, it is the date/time the file was created. This is not useful if the file was edited.
 *   </li>
 *   <li>The date/time of the file<br/>
 *   This is the date/time the file was created. This is not useful if the file was edited.
 *   </li>
 * </ul>
 *
 */
public final class PhotoFileMetaDataHandler {
  private static final Logger LOGGER = Logger.getLogger(PhotoFileMetaDataHandler.class.getName());
  
  private static final String GPS_PROCESSING_METHOD_MANUAL = "MANUAL";

  private static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");   // For parsing
  private static DateTimeFormatter DTF_WITH_SPACE = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm: s");   // For parsing
  private static DateTimeFormatter DTFP = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  // For printing
  
  private File file = null;
  private JpegImageMetadata jpegMetadata = null;
  
  /**
   * Constructor.
   * 
   * @param file the photo file to handle.
   * @throws IOException 
   * @throws ImageReadException 
   */
  public PhotoFileMetaDataHandler(final File file) throws ImageReadException, IOException {
    this.file = file;

    if (FileUtils.isWebpFile(file)) {
      return;
    }
    
    // get all metadata stored in EXIF format (ie. from JPEG or TIFF).
    ImageMetadata metadata = Imaging.getMetadata(file);

    if (metadata instanceof JpegImageMetadata) {
      jpegMetadata = (JpegImageMetadata) metadata;

      LOGGER.info("Metadata: " + jpegMetadata.toString());      
    }
  }

  /**
   * Get the title of the photo.
   * <p>
   * This is the title as shown by a Windows properties window,
   * which is the value of the 'XPTitle' attribute in the EXIF info.
   * 
   * @return the title of the photo, or null if there is no title.
   */
  public String getTitle() throws ImageReadException {
    String title = null;
    
    if (jpegMetadata != null) {
      final TiffImageMetadata exifMetadata = jpegMetadata.getExif();
      if (null != exifMetadata) {
        Object titleAsObject = exifMetadata.getFieldValue(MicrosoftTagConstants.EXIF_TAG_XPTITLE);
        if (titleAsObject != null) {
          title = (String) titleAsObject;
        }
      }
    }
    
    return title;
  }
  
  /**
   * Get the creation date/time from a photo file (i.e. the date/time the photo was taken).
   * <p>
   * It is attempted to get this value from two sources:
   * <ul>
   * <li>Exif: DateTimeOriginal</li>
   * <li>The file name</li>
   * </ul>
   * If only one value is available, this is returned.
   * If both values are available, they are compared, and an exception is thrown if they aren't equal.
   * If none of the values is available, null is returned.
   * 
   * @return the creation date/time of the photo, or null if not available.
   * @throws ImageReadException if the meta data can not be read from the file
   * @throws IOException if the file cannot be read
   * @throws ParseException if a problem occurs while reading the date/time information from the meta data.
   */
  public LocalDateTime getCreationDateTime() {
    LocalDateTime dateTimeOriginal = getDateTimeOriginalFromMetaData();
    
    LocalDateTime dateTimeFromFileName = getCreationDateTimeFromFileName();
    
    if (dateTimeOriginal != null  &&  dateTimeFromFileName != null) {
      Duration timeDifference = Duration.between(dateTimeFromFileName, dateTimeOriginal);
      long timeDifferenceInSeconds = timeDifference.abs().toSeconds();
      if (timeDifferenceInSeconds > 60) {
        LOGGER.severe("Two different creation times found: DateTimeOriginal=" + dateTimeOriginal.format(DTF) + ", Date/Time from filename=" + dateTimeFromFileName.format(DTF));
        throw new RuntimeException("Two different creation times found: DateTimeOriginal=" + dateTimeOriginal.format(DTF) + ", Date/Time from filename=" + dateTimeFromFileName.format(DTF));
      }      
    }
    
    if (dateTimeOriginal != null) {
      return dateTimeOriginal;
    } else {
      return dateTimeFromFileName;
    }
  }

  /**
   * Get the creation date/time of a photo file (i.e. the date/time the photo was taken), from the item 'DateTimeOriginal' from the meta data.
   * 
   * @return the creation date/time of the photo (as value of 'DateTimeOriginal'), or null if not available.
   * @throws ImageReadException if the meta data can not be read from the file.
   * @throws ParseException if a problem occurs while reading the date/time information from the meta data.
   */
  public LocalDateTime getDateTimeOriginalFromMetaData() {
    LocalDateTime dateTime = null;
    String dateTimeText = getTiffItemValue(TiffDirectoryConstants.DIRECTORY_TYPE_EXIF, "DateTimeOriginal");

    if (dateTimeText != null) {
      dateTimeText = dateTimeText.substring(1, dateTimeText.length() - 1);  // Remove quotes
      try {
      dateTime = LocalDateTime.parse(dateTimeText.subSequence(0, dateTimeText.length()), DTF);
      } catch (DateTimeParseException e) {
        LOGGER.severe("Parsing DateTimeOriginal failed");
      }
      if (dateTime == null) {
        try {
          dateTime = LocalDateTime.parse(dateTimeText.subSequence(0, dateTimeText.length()), DTF_WITH_SPACE);
          } catch (DateTimeParseException e) {
            LOGGER.severe("Parsing DateTimeOriginal failed");
          }
      }
    }

    LOGGER.fine("dateTime=" + (dateTime != null ? dateTime.format(DTFP) : "(null)"));
    return dateTime;
  }
  
  /**
   * Get the creation date/time of a photo file (i.e. the date/time the photo was taken), from the filename.
   * 
   * @return the creation date/time of the photo (parsed from the filename), or null if not possible.
   */
  public LocalDateTime getCreationDateTimeFromFileName() {
    String fileName = FileUtils.getFileNameWithoutExtension(file);
    return FileUtils.parseYyyyMmDd_HhMmSs(fileName);
  }

  /**
   * Get the last edit date/time of a photo file.
   * <p>
   * This date/time is read from the meta data of the file. It is the date/time the photo was last edited in an editor, which is usually
   * some time before the Last Modification Date (the moment it was saved to a file).
   * 
   * @param file the file from which the date/time will be read.
   * @return
   * @throws ImageReadException if the meta data can not be read from the file
   * @throws IOException if the file cannot be read
   * @throws ParseException if a problem occurs while reading the date/time information from the meta data.
   */  
  public LocalDateTime getEditDateTime() throws ImageReadException {
    LocalDateTime dateTime = null;
    String dateTimeText = null;

    if (jpegMetadata != null) {

      final TiffField field = jpegMetadata.findEXIFValueWithExactMatch(TiffTagConstants.TIFF_TAG_DATE_TIME);
      if (field == null) {
        LOGGER.severe("Date/time not found for photo: " + file.getName());
      } else {
        dateTimeText = field.getStringValue();
        LOGGER.fine("DateTime: "  + dateTimeText);
      }
    }

    if (dateTimeText != null) {
      dateTime = LocalDateTime.parse(dateTimeText.subSequence(0, dateTimeText.length()), DTF);
      LOGGER.fine("dateTime=" + dateTime.format(DTF));
    }

    return dateTime;
  }
  
  /**
   * Get the width of the image.
   * 
   * @return the width of the image, or null if this cannot be determined.
   */
  public Integer getWidth() {
    Integer width = null;
    
    if (jpegMetadata != null) {

      final TiffField field = jpegMetadata.findEXIFValueWithExactMatch(TiffTagConstants.TIFF_TAG_IMAGE_WIDTH);
      if (field == null) {
        LOGGER.severe("Width not found for photo: " + file.getName());
      } else {
        try {
          width = field.getIntValue();
        } catch (ImageReadException e) {
          e.printStackTrace();
        }
        LOGGER.fine("Width: "  + width);
      }
    }
    
    return width;
  }
  
  /**
   * Get the height of the image.
   * 
   * @return the height of the image, or null if this cannot be determined.
   */
  public Integer getHeight() {
    Integer height = null;
    
    if (jpegMetadata != null) {

      final TiffField field = jpegMetadata.findEXIFValueWithExactMatch(TiffTagConstants.TIFF_TAG_IMAGE_LENGTH);
      if (field == null) {
        LOGGER.severe("Length not found for photo: " + file.getName());
      } else {
        try {
          height = field.getIntValue();
        } catch (ImageReadException e) {
          e.printStackTrace();
        }
        LOGGER.fine("Height: "  + height);
      }
    }
    
    return height;
  }
  
  /**
   * Get the <code>WGS84Coordinates</code> from the photo file.
   * 
   * @return the coordinates of the photo file, or null if these aren't available.
   * @throws ImageReadException
   */
  public WGS84Coordinates getGeoLocation() throws ImageReadException {
    if (jpegMetadata != null) {
      // simple interface to GPS data
      final TiffImageMetadata exifMetadata = jpegMetadata.getExif();
      if (null != exifMetadata) {
        final TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
        if (null != gpsInfo) {
          final double longitude = gpsInfo.getLongitudeAsDegreesEast();
          final double latitude = gpsInfo.getLatitudeAsDegreesNorth();

          LOGGER.fine("GPS Longitude (Degrees East): " + longitude);
          LOGGER.fine("GPS Latitude (Degrees North): " + latitude);
          
          WGS84Coordinates coordinates = new WGS84Coordinates(latitude, longitude);
          return coordinates;
        }
      }
    }
    
    return null;
  }
  
  /**
   * Check whether a photo has approximate coordinates.
   * <p>
   * I use the value GPS_PROCESSING_METHOD_MANUAL for the TIFF item GPS_TAG_GPS_PROCESSING_METHOD.name to indicate approximate coordinates.
   * 
   * @return true if the photo has approximate coordinates.
   * @throws ImageReadException
   */
  public boolean hasApproximateCoordinates() throws ImageReadException {
    LOGGER.info("=>");
    
    return jpegMetadata != null ? metadataHasApproximateCoordinatesIndication(jpegMetadata) : false;
  }  
  
  /**
   * Get a single Tiff item from the meta data.
   * 
   * @param directoryType the Tiff directory type (see {@link TiffDirectoryConstants} for known values.
   * @param itemName the name of the item to be obtained.
   * @return the value of requested item, or null if not available.
   */
  public String getTiffItemValue(int directoryType, String itemName) {
    if (jpegMetadata != null) {
      return getTiffItemValue(jpegMetadata, directoryType, itemName);
    } else {
      return null;
    }
  }
  
  
  
  /**
   * Write a geo location and title to a photo file.
   *
   * @param jpegImageFile the photo file for which the information is to be updated.
   * @param geoLocation the coordinates of where the photo was taken.
   * @param approximateGPScoordinates if true, this indicates that the <code>geoLocation</code> isn't accurate.
   * @param the title of the photo.
   * @throws IOException
   * @throws ImageReadException
   * @throws ImageWriteException
   */
  public static void writeGeoLocationAndTitle(final File jpegImageFile, WGS84Coordinates geoLocation, boolean approximateGPScoordinates, String title)
      throws IOException, ImageReadException, ImageWriteException {
    if (!FileUtils.isJpegFile(jpegImageFile)) {
      throw new IllegalArgumentException("File type is not supported: " + jpegImageFile);
    }
    // move the original file to a temporary name: filename-<timestamp>.ext
    String backupFileName = FileUtils.createBackupFileName(jpegImageFile.getAbsolutePath());
    File backupFile = new File(backupFileName);
    Path backupPath = Paths.get(backupFileName);
    Path sourcePath = Paths.get(jpegImageFile.getAbsolutePath());
    Files.move(sourcePath, backupPath);
    
    // read temporary file, update it and write to original name
    try (FileOutputStream fos = new FileOutputStream(jpegImageFile);
         OutputStream os = new BufferedOutputStream(fos)) {
      TiffOutputSet outputSet = null;

      // note that metadata might be null if no metadata is found.
      final ImageMetadata metadata = Imaging.getMetadata(backupFile);
      final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
      if (null != jpegMetadata) {
        // note that exif might be null if no Exif metadata is found.
        final TiffImageMetadata exif = jpegMetadata.getExif();

        if (null != exif) {
          // TiffImageMetadata class is immutable (read-only).
          // TiffOutputSet class represents the Exif data to write.
          //
          // Usually, we want to update existing Exif metadata by
          // changing
          // the values of a few fields, or adding a field.
          // In these cases, it is easiest to use getOutputSet() to
          // start with a "copy" of the fields read from the image.
          outputSet = exif.getOutputSet();
        }
      }

      // if file does not contain any exif metadata, we create an empty
      // set of exif metadata. Otherwise, we keep all of the other
      // existing tags.
      if (null == outputSet) {
        outputSet = new TiffOutputSet();
      }
      
      if (approximateGPScoordinates  &&  !metadataHasApproximateCoordinatesIndication(jpegMetadata)) {
        final TiffOutputDirectory gpsDir = outputSet.getOrCreateGPSDirectory();
        gpsDir.removeField(GpsTagConstants.GPS_TAG_GPS_PROCESSING_METHOD);
        gpsDir.add(GpsTagConstants.GPS_TAG_GPS_PROCESSING_METHOD, "MANUAL");
      } else if (!approximateGPScoordinates  &&  metadataHasApproximateCoordinatesIndication(jpegMetadata)) {
        final TiffOutputDirectory gpsDir = outputSet.getOrCreateGPSDirectory();
        gpsDir.removeField(GpsTagConstants.GPS_TAG_GPS_PROCESSING_METHOD);
      }
      outputSet.setGPSInDegrees(geoLocation.getLongitude(), geoLocation.getLatitude());
      
      final TiffOutputDirectory rootDir = outputSet.getOrCreateRootDirectory();
      rootDir.removeField(MicrosoftTagConstants.EXIF_TAG_XPTITLE);
      if (title != null) {
        rootDir.add(MicrosoftTagConstants.EXIF_TAG_XPTITLE, title);
      }
      

      new ExifRewriter().updateExifMetadataLossless(backupFile, os, outputSet);
      
      // if successful, delete the temporary file.
      Files.delete(backupPath);
    }
  }
  
  
  /**
   * Write the 'Orientation' to a photo file.
   * <p>
   * THIS METHOD ISN'T TESTED
   *
   * @param jpegImageFile A source photo file.
   * @param orientation the new 'Root.Orientation'. Where <code>orientation</code> shall have on of the following values:
   * <ul>
   * <li>ORIENTATION_VALUE_HORIZONTAL_NORMAL = 1 (normal horizontal orientation)</li>
   * <li>ORIENTATION_VALUE_MIRROR_HORIZONTAL = 2 (mirrorred horizontal orientation)</li>
   * <li>ORIENTATION_VALUE_ROTATE_180 = 3 (rotated 180 degrees)</li>
   * <li>ORIENTATION_VALUE_MIRROR_VERTICAL = 4 (mirrorred vertically)</li>
   * <li>ORIENTATION_VALUE_MIRROR_HORIZONTAL_AND_ROTATE_270_CW = 5 (mirrorred horizontally and rotated 270 degrees)</li>
   * <li>ORIENTATION_VALUE_ROTATE_90_CW = 6 (rotated 90 degrees)</li>
   * <li>ORIENTATION_VALUE_MIRROR_HORIZONTAL_AND_ROTATE_90_CW = 7 (mirrorred horizontally and rotated 90 degrees)</li>
   * <li>ORIENTATION_VALUE_ROTATE_270_CW = 8 (rotated 270 degrees)</li>
   * </ul>
   * @throws IOException
   * @throws ImageReadException
   * @throws ImageWriteException
   */
  public static void writeOrientation(final File jpegImageFile, int orientation) throws IOException, ImageReadException, ImageWriteException {
    if (!FileUtils.isJpegFile(jpegImageFile)) {
      throw new IllegalArgumentException("File type is not supported: " + jpegImageFile);
    }

    // move the original file to a temporary name: filename-<timestamp>.ext
    String backupFileName = FileUtils.createBackupFileName(jpegImageFile.getAbsolutePath());
    File backupFile = new File(backupFileName);
    Path backupPath = Paths.get(backupFileName);
    Path sourcePath = Paths.get(jpegImageFile.getAbsolutePath());
    Files.move(sourcePath, backupPath);
    
    // read temporary file, update it and write to original name
    try (FileOutputStream fos = new FileOutputStream(jpegImageFile);
         OutputStream os = new BufferedOutputStream(fos)) {
      TiffOutputSet outputSet = null;

      // note that metadata might be null if no metadata is found.
      final ImageMetadata metadata = Imaging.getMetadata(backupFile);
      final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
      if (null != jpegMetadata) {
        // note that exif might be null if no Exif metadata is found.
        final TiffImageMetadata exif = jpegMetadata.getExif();

        if (null != exif) {
          // TiffImageMetadata class is immutable (read-only).
          // TiffOutputSet class represents the Exif data to write.
          //
          // Usually, we want to update existing Exif metadata by
          // changing
          // the values of a few fields, or adding a field.
          // In these cases, it is easiest to use getOutputSet() to
          // start with a "copy" of the fields read from the image.
          outputSet = exif.getOutputSet();
        }
      }

      // if file does not contain any exif metadata, we create an empty
      // set of exif metadata. Otherwise, we keep all of the other
      // existing tags.
      if (null == outputSet) {
        outputSet = new TiffOutputSet();
      }
      
      final TiffOutputDirectory rootDir = outputSet.getOrCreateRootDirectory();
      rootDir.removeField(TiffTagConstants.TIFF_TAG_ORIENTATION);
      rootDir.add(TiffTagConstants.TIFF_TAG_ORIENTATION, (short) orientation);

      new ExifRewriter().updateExifMetadataLossless(backupFile, os, outputSet);
      
      // if successful, delete the temporary file.
      Files.delete(backupPath);
    }
  }
  
  /**
   * Check whether the approximate coordinates indication is set in <code>JpegImageMetadata</code>.
   * <p>
   * I use the value GPS_PROCESSING_METHOD_MANUAL for the TIFF item GPS_TAG_GPS_PROCESSING_METHOD.name to indicate approximate coordinates.
   * 
   * @param jpegMetadata the <code>JpegImageMetadata</code> to check.
   * @return true if approximate coordinates is set, false otherwise.
   */
  private static boolean metadataHasApproximateCoordinatesIndication(JpegImageMetadata jpegMetadata) {
    String gpsProcessingMethodText = getTiffItemValue(jpegMetadata, TiffDirectoryConstants.DIRECTORY_TYPE_GPS, GpsTagConstants.GPS_TAG_GPS_PROCESSING_METHOD.name);
    gpsProcessingMethodText = StringUtil.removeQuotes(gpsProcessingMethodText);
    if (gpsProcessingMethodText != null) {
      if (gpsProcessingMethodText.equals(GPS_PROCESSING_METHOD_MANUAL)) {
        LOGGER.info("<= true GPS Processing Method MANUAL found");
        return true;
      }
    }

    LOGGER.info("<= false");
    return false;
  }
  
  /**
   * Get a single Tiff item from the meta data.
   * 
   * @param directoryType the Tiff directory type (see {@link TiffDirectoryConstants} for known values.
   * @param itemName the name of the item to be obtained.
   * @return the value of requested item, or null if not available.
   */
  private static String getTiffItemValue(JpegImageMetadata jpegMetadata, int directoryType, String itemName) {
    LOGGER.info("=> directoryType= " + directoryType + "(" + TiffDirectory.description(directoryType) + "), itemName=" + itemName);
    if (jpegMetadata != null) {
      TiffImageMetadata exif = jpegMetadata.getExif();
      if (exif != null) {
        List<ImageMetadataItem> dirs = (List<ImageMetadataItem>) exif.getDirectories();
        LOGGER.severe("Value of getDirectories: " + dirs);
//        for (Directory directory: exif.getDirectories()) {
//          if (directory.type == directoryType) {
//            for (ImageMetadataItem item: directory.getItems()) {
//              TiffMetadataItem tiffMetadataItem = (TiffMetadataItem) item;
//              if (tiffMetadataItem.getKeyword().equals(itemName)) {
//                return tiffMetadataItem.getText();
//              }
//            }
//          }
//        }
      }
    }

    return null;
  }
    
  public static void main(String[] args) {
    try {
      for (String arg: args) {
        System.out.println("File: " + arg);
        File fileOrDirectory = new File(arg);

        if (fileOrDirectory.isFile())
        {
          PhotoFileMetaDataHandler photoFileMetaDataHandler = new PhotoFileMetaDataHandler(fileOrDirectory);
                    
          LocalDateTime creationDate = photoFileMetaDataHandler.getCreationDateTime();
          if (creationDate != null) {
            System.out.println("Creation date: " + creationDate.format(DTFP));
          } else {
            System.out.println("No creation date available");
          }
          
          LocalDateTime editDate = photoFileMetaDataHandler.getEditDateTime();
          if (editDate != null) {
            System.out.println("Modification date: " + editDate.format(DTFP));
          } else {
            System.out.println("No modification date available");
          }
          
          LocalDateTime modificationDate = FileUtils.getLastModificationDate(fileOrDirectory);
          if (modificationDate != null) {
            System.out.println("Modification date: " + modificationDate.format(DTFP));
          } else {
            System.out.println("No modification date available");
          }
          
          WGS84Coordinates coordinates = photoFileMetaDataHandler.getGeoLocation();
          if (coordinates != null) {
            System.out.println("Coordinates: " + coordinates.toString());            
          } else {
            System.out.println("Coordinates not available");            
          }
          
          WGS84Coordinates geoLocation = new WGS84Coordinates(41.249866, 9.184371);
          try {
            PhotoFileMetaDataHandler.writeGeoLocationAndTitle(fileOrDirectory, geoLocation, true, "Nice Photo");
          } catch (ImageWriteException e) {
            e.printStackTrace();
          }

          photoFileMetaDataHandler = new PhotoFileMetaDataHandler(fileOrDirectory);
          coordinates = photoFileMetaDataHandler.getGeoLocation();
          if (coordinates != null) {
            System.out.println("New Coordinates: " + coordinates.toString());            
          } else {
            System.out.println("New Coordinates not available");            
          }
        }
        else
        {
          System.out.println("Skipping directory: " + fileOrDirectory);
        }
        
        System.out.println();
      }

      System.out.println("\nDone");
    }
    catch (IOException e)
    {
      e.printStackTrace();
    } catch (ImageReadException e) {
      e.printStackTrace();
    }
  }
}
