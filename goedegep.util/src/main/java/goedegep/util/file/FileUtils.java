package goedegep.util.file;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

/**
 * This is a utility class with methods which operate on files.
 * 
 * Files can be identified by a file name (a String), a path (which represents a file), or a File.
 *
 */
public class FileUtils {
  private static final Logger LOGGER = Logger.getLogger(FileUtils.class.getName());
  private static final SimpleDateFormat BACKUP_FILENAME_TIMESTAMP =  new SimpleDateFormat("yyyyMMdd_HHmmss");
  
  /**
   * Name of the backup folder.
   */
  private static final String BACKUP_FOLDER_NAME = "backup";
  
  /**
   * File name extensions of known audio files.
   */
  private final static Set<String> audioFileExtensions = new HashSet<>(Arrays.asList(
      ".dsf", ".flac", ".m2ts", ".m4a", ".mp3", ".wav", ".wma"
      ));

  /**
   * File name extensions for picture files.
   */
  private static final String DEFAULT_JPEG_EXTENSION = ".jpg";
  private static final String ALT_JPEG_EXTENSION = ".jpeg";
  private static final String PNG_EXTENSION = ".png";
  private static final String WEBP_EXTENSION = ".webp";
  
  /**
   * File name extensions for jpeg files.
   */
  private static final List<String> JPEG_EXTENSIONS = new ArrayList<>(Arrays.asList(DEFAULT_JPEG_EXTENSION, ALT_JPEG_EXTENSION));
  
  /**
   * File name extensions of known pictures files.
   */
  private static List<String> pictureFileExtensions = null;
  
  /**
   * File name extension for GPX files.
   */
  private static final String GPX_EXTENSION = ".gpx";
  
  static {
    pictureFileExtensions = new ArrayList<>(JPEG_EXTENSIONS);
    pictureFileExtensions.add(PNG_EXTENSION);
    pictureFileExtensions.add(WEBP_EXTENSION);
  }
  
  
  /**
   * Private constructor, so this class cannot be instantiated.
   */
  private FileUtils() {
    
  }

  /**
   * Get the filename (without extension) and the extension from a <code>Path</code>.
   * <p>
   * The <code>path</code> may contain anything, from an absolute path to just the filename.
   * 
   * @param path the <code>Path</code> to derive the information from.
   * @return a array of Strings, with the first element being the filename and the second element being the extension.
   */
  public static String[] getFileNameAndExtension(Path path) {
    String fileName = path.getFileName().toString();

    return getFileNameAndExtension(fileName);
  }

  /**
   * Get the filename (without extension) and the extension from a filename (including extension).
   * <p>
   * The <code>fileName</code> shall be just a filename (i.e. not containing any directory part).
   * 
   * @param fileName the filename to derive the information from.
   * @return a array of Strings, with the first element being the filename and the second element being the extension. Null is returned if no extension could be detected.
   */
  public static String[] getFileNameAndExtension(String fileName) {
    int dotIndex = fileName.lastIndexOf(".");
    if (dotIndex != -1) {
      String[] nameAndExtension = new String[2];
      nameAndExtension[0] = fileName.substring(0, dotIndex);
      nameAndExtension[1] = fileName.substring(dotIndex + 1);

      return nameAndExtension;
    } else {
      return null;
    }
  }

  /**
   * Get the filename (without extension) from a filename (including extension).
   * <p>
   * The <code>fileName</code> shall be just a filename (i.e. not containing any directory part).
   * 
   * @param fileName the filename to derive the information from.
   * @return the filename (without extension), or null if no extension could be detected.
   */
  public static String getFileNameWithoutExtension(String fileName) {
    int dotIndex = fileName.lastIndexOf(".");
    if (dotIndex != -1) {
      return fileName.substring(0, dotIndex);
    } else {
      return null;
    }
  }

  /**
   * Get the filename (without extension) from a <code>File</code>.
   * 
   * @param file the <code>File</code> to derive the information from.
   * @return the filename (without extension), or null if no extension could be detected.
   */
  public static String getFileNameWithoutExtension(File file) {
    return getFileNameWithoutExtension(file.getAbsolutePath());
  }

  /**
   * Get the extension of a filename (including the '.').
   * 
   * @param fileName the filename to derive the information from.
   * @return the extension of the filename, or null if no extension could be detected.
   */
  public static String getFileExtension(String fileName) {
    int dotIndex = fileName.lastIndexOf(".");
    if (dotIndex != -1) {
      return fileName.substring(dotIndex);
    } else {
      return null;
    }
  }

  /**
   * Get the extension of a <code>File</code> (including the '.').
   * 
   * @param file the <code>File</code> to derive the information from.
   * @return the extension of the name of the <code>file</code>, or null if no extension could be detected.
   */
  public static String getFileExtension(File file) {
    return getFileExtension(file.getName());
  }

  /**
   * Get the extension of a <code>Path</code> (including the '.').
   * 
   * @param path the <code>Path</code> to derive the information from.
   * @return the extension of the name of the <code>Path</code>, or null if no extension could be detected.
   */
  public static String getFileExtension(Path path) {
    return getFileExtension(path.getFileName().toString());
  }

  /**
   * Check whether two files have the same contents.
   * 
   * @param fileName1 Pathname of the first file.
   * @param fileName2 Pathname of the second file.
   * @return true, if the two files have the same contents, false otherwise.
   * @throws IOException if reading the files failed.
   */
  public static boolean contentEquals(String fileName1, String fileName2) throws IOException {
    return contentEquals(Paths.get(fileName1), Paths.get(fileName2));
  }

  /**
   * Check whether two files have the same contents.
   * 
   * @param file1 First <code>File</code>.
   * @param file2 Second <code>File</code>.
   * @return true, if the two files have the same contents, false otherwise.
   * @throws IOException if reading the files failed.
   */
  public static boolean contentEquals(File file1, File file2) throws IOException {
    return contentEquals(file1.toPath(), file2.toPath());
  }

  /**
   * Check whether two files have the same contents.
   * 
   * @param file1 <code>Path</code> for the first file.
   * @param file2 <code>Path</code> for the second file.
   * @return true, if the two files have the same contents, false otherwise.
   * @throws IOException if reading the files failed.
   */
  public static boolean contentEquals(Path file1, Path file2) throws IOException {
    
    final long size = Files.size(file1);
    if (size != Files.size(file2))
      return false;

    if (size < 4096)
      return Arrays.equals(Files.readAllBytes(file1), Files.readAllBytes(file2));

//    Long startTime = System.currentTimeMillis();
    try (InputStream is1 = Files.newInputStream(file1);
        InputStream is2 = Files.newInputStream(file2)) {
      
      // Compare byte-by-byte.
      // Note that this can be sped up drastically by reading large chunks
      // (e.g. 16 KBs) but care must be taken as InputStream.read(byte[])
      // does not neccessarily read a whole array!
      int data;
      while ((data = is1.read()) != -1)
        if (data != is2.read())
          return false;
    }

//    Long endTime = System.currentTimeMillis();
//    Long duration = endTime - startTime;
//    LOGGER.severe("Comparison (for equal files) took: " + duration + "ms");
    return true;
  }
  
  /**
   * Copy a file to a directory.
   * 
   * @param file the <code>File</code> to be copied.
   * @param directory the directory to copy the file to.
   * @throws IOException if copy cannot be created.
   */
  public static void copyFileToDirectory(File file, File directory) throws IOException {
    Path filePath = file.toPath();
    Path directoryPath = directory.toPath();
    Files.copy(filePath, directoryPath.resolve(filePath.getFileName()));
  }

  /**
   * Move a file to a backup folder (see {@link #BACKUP_FOLDER_NAME}.
   * If filename exists in directory, it is moved to the subfolder "backup".
   * For a file with the name 'name.ext', the filename is changed to 'nameyyyyMMdd_HHmmss.ext'.
   * 
   * @param directory the folder where the file to be moved is located.
   * @param fileName the name of the file to be moved.
   * @param createBackupFolder If the backup folder doesn't exist yet, and fileName does exist, the backup folder is created if this parameter is true.
   * @throws IOException if the file couldn't be moved.
   */
  public static void moveFileToBackupFolder(String directory, String fileName, boolean createBackupFolder) throws IOException {
    Path source = Paths.get(directory, fileName);

    LOGGER.fine("source = " + source.toFile().getAbsolutePath());
    if (Files.exists(source)) {
      // create backup folder if needed.
      Path backupFolder = Paths.get(directory, BACKUP_FOLDER_NAME);
      if (createBackupFolder  &&  !Files.exists(backupFolder)) {
        Files.createDirectory(backupFolder);
        LOGGER.fine("backup folder created");
      }

      String[] fileNameAndExtension = getFileNameAndExtension(fileName);
      Date date = new Date();
      String backupFileName = fileNameAndExtension[0] + BACKUP_FILENAME_TIMESTAMP.format(date) + "." + fileNameAndExtension[1];
      Path target = Paths.get(directory, BACKUP_FOLDER_NAME, backupFileName);

      LOGGER.fine("file exists, renaming to " + target.toFile().getAbsolutePath());
      Files.move(source, target, REPLACE_EXISTING);
    }
  }
  
  /**
   * Create a backup filename.
   * <p>
   * For a filename: &lt;name&gt;.&lt;extension&gt;<br/>
   * The backup filename is: &lt;name&gt;&lt;timestamp&gt;.&lt;extension&gt;<br/>
   * Where &lt;timestamp&gt; is the current date/time in the format 'yyyyMMdd_HHmmss'.
   * @param fileName the filename for which a backup filename is to be created.
   * @return a backup filename generated for <code>fileName</code>.
   */
  public static String createBackupFileName(String fileName) {
    String[] fileNameAndExtension = getFileNameAndExtension(fileName);
    Date date = new Date();
    String backupFileName = fileNameAndExtension[0] + "_" + BACKUP_FILENAME_TIMESTAMP.format(date) + "." + fileNameAndExtension[1];
    
    return backupFileName;
  }

  public static String getFullPathName(Path path, String pathSeparator) {
    return path.getParent().toString() + pathSeparator + path.getFileName().toString();
  }
  
  /**
   * Generate an MD5 hash string for the file with the specified name.
   * 
   * @param fileName the name of the file for which the MD5 hash string is to be generated.
   * @return the MD5 string for the specified file
   * @throws IOException in case the file cannot be read
   */
  public static String generateMD5String(String fileName) throws IOException {
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("MD5");

      try (FileInputStream fis = new FileInputStream(fileName)) {

        byte[] dataBytes = new byte[1024];

        int nread = 0;
        while ((nread = fis.read(dataBytes)) != -1) {
          md.update(dataBytes, 0, nread);
        }
        byte[] mdbytes = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        fis.close();

        return sb.toString();
      }
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * Check whether a file extension belongs to an audio file.
   * 
   * @param fileExtension a file extension (including the '.'). This value may not be null.
   * @return true is the extension belongs to an audio file, false otherwise.
   */
  public static boolean isAudioFileExtension(String fileExtension) {
    return audioFileExtensions.contains(fileExtension.toLowerCase());
  }
  
  /**
   * Check whether a file is an m2ts file.
   * 
   * @param file 
   * @param ignoreCase
   * @return
   */
  public static boolean isM2tsFile(Path file, boolean ignoreCase) {
    String fileExtension = getFileExtension(file);
    
    if (fileExtension == null) {
      return false;
    }
    
    fileExtension = fileExtension.toLowerCase();
    
    if (fileExtension.equals(".m2ts")) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean isWindowsMediaAudioFile(Path file) {
    String fileExtension = getFileExtension(file);
    fileExtension = fileExtension.toLowerCase();
    return ".wma".equals(fileExtension);
  }

  public static boolean isMpeg4AudioFile(Path file) {
    String fileExtension = getFileExtension(file);
    fileExtension = fileExtension.toLowerCase();
    return ".m4a".equals(fileExtension);
  }

  /**
   * Check whether the file is an audio WAV file, based on the filename extension.
   * 
   * @param file the file to be checked.
   * @return true if the file is a WAV file, false otherwise
   */
  public static boolean isWavFile(Path file) {
    String fileExtension = getFileExtension(file);
    fileExtension = fileExtension.toLowerCase();
    return ".wav".equals(fileExtension);
  }

  /**
   * Check whether the file is an audio MP3 file, based on the filename extension.
   * 
   * @param file the file to be checked.
   * @return true if the file is an MP3 file, false otherwise
   */
  public static boolean isMP3File(Path file) {
    String fileExtension = getFileExtension(file);
    fileExtension = fileExtension.toLowerCase();
    return ".mp3".equals(fileExtension);
  }

  /**
   * Check whether the file is an audio flac file, based on the filename extension.
   * 
   * @param file the file to be checked.
   * @return true if the file is a flac file, false otherwise
   */
  public static boolean isFlacFile(Path file) {
    String fileExtension = getFileExtension(file);
    fileExtension = fileExtension.toLowerCase();
    return ".flac".equals(fileExtension);
  }

  /**
   * Check whether a file is an audio file (based on its extension).
   * 
   * @param fileName a filename. This value may not be null.
   * @return true is the file is an audio file, false otherwise.
   */
  public static boolean isAudioFile(String fileName) {
    String fileExtension = getFileExtension(fileName);
    if (fileExtension != null) {
      return audioFileExtensions.contains(fileExtension);
    } else {
      return false;
    }
  }

  /**
   * Check whether a file is an audio file (based on its extension).
   * 
   * @param filePath a Path for a file. This value may not be null.
   * @return true is the file is an audio file, false otherwise.
   */
  public static boolean isAudioFile(Path filePath) {
    String fileName = filePath.getFileName().toString();
    boolean returnValue = isAudioFile(fileName);
    
    LOGGER.fine("fileName=" + fileName + ", returnValue=" + returnValue);
    return returnValue;
  }

  /**
   * Check that the extension of a file is in lower case.
   * 
   * @param fileName the file name to be checked.
   * @return true if <code>fileName</code> has a lower case extension, false otherwise (also in case there is no extension).
   */
  public static boolean hasLowerCaseExtension(String fileName) {
    String extension = getFileExtension(fileName);
    if (extension == null) {
      return false;   // No extension is also no lower case extension.
    }
    String lowerCaseExtension = extension.toLowerCase();
    return lowerCaseExtension.equals(extension);
  }

  /**
   * Check that the extension of a file is in lower case.
   * 
   * @param filePath the Path of a file, for which the extension is to be checked.
   * @return true if <code>filePath</code> has a lower case extension, false otherwise (also in case there is no extension).
   */
  public static boolean hasLowerCaseExtension(Path filePath) {
    String fileName = filePath.getFileName().toString();
    return hasLowerCaseExtension(fileName);
  }

  /**
   * Check whether a file extension belongs to a picture file.
   * 
   * @param fileExtension a file extension (including the '.'). This value may not be null.
   * @return true is the extension belongs to a picture file, false otherwise.
   */
  public static boolean isPictureFileExtension(String fileExtension) {
    return pictureFileExtensions.contains(fileExtension.toLowerCase());
  }

  /**
   * Check whether a file is a picture file (based on its extension).
   * 
   * @param file a File. This value may not be null.
   * @return true is the file is a picture file, false otherwise.
   */
  public static boolean isPictureFile(File file) {
    return isPictureFile(file.getAbsolutePath());
  }

  /**
   * Check whether a file is a picture file (based on its extension).
   * 
   * @param fileName a filename. This value may not be null.
   * @return true is the file is a picture file, false otherwise.
   */
  public static boolean isPictureFile(String fileName) {
    String fileExtension = getFileExtension(fileName);
    if (fileExtension != null) {
      return isPictureFileExtension(fileExtension);
    } else {
      return false;
    }
  }

  /**
   * Check whether a file is a jpeg file (based on its extension).
   * 
   * @param file a File. This value may not be null.
   * @return true is the file is a jpeg file, false otherwise.
   */
  public static boolean isJpegFile(File file) {
    return isJpegFile(file.getAbsolutePath());
  }
  
  /**
   * Check whether a file is a jpeg file.
   * 
   * @param fileName a filename. This value may not be null.
   * @return true is the file is a jpeg file, false otherwise.
   */
  public static boolean isJpegFile(String fileName) {
    String fileExtension = getFileExtension(fileName);
    return JPEG_EXTENSIONS.contains(fileExtension);
  }

  /**
   * Check whether a file is a webp file (based on its extension).
   * 
   * @param file a File. This value may not be null.
   * @return true is the file is a webp file, false otherwise.
   */
  public static boolean isWebpFile(File file) {
    return isWebpFile(file.getAbsolutePath());
  }
  
  /**
   * Check whether a file is a webp file.
   * 
   * @param fileName a filename. This value may not be null.
   * @return true is the file is a webp file, false otherwise.
   */
  public static boolean isWebpFile(String fileName) {
    String fileExtension = getFileExtension(fileName);
    return WEBP_EXTENSION .equals(fileExtension);
  }
  
  /**
   * Check whether a file is a PDF file (based on its extension).
   * 
   * @param file a File. This value may not be null.
   * @return true is the file is a PDF file, false otherwise.
   */
  public static boolean isPDFFile(File file) {
    return isPDFFile(file.getAbsolutePath());
  }

  /**
   * Check whether a file is a PDF file (based on its extension).
   * 
   * @param fileName a filename. This value may not be null.
   * @return true is the file is a PDF file, false otherwise.
   */
  public static boolean isPDFFile(String fileName) {
    String fileExtension = getFileExtension(fileName);
    if (fileExtension != null) {
      return fileExtension.equalsIgnoreCase(".pdf");
    } else {
      return false;
    }
  }

  /**
   * Check whether a file is a text file (based on its extension).
   * 
   * @param fileName a filename. This value may not be null.
   * @return true is the file is a text file, false otherwise.
   */
  public static boolean isTextFile(String fileName) {
    String fileExtension = getFileExtension(fileName);
    if (fileExtension != null) {
      return fileExtension.equalsIgnoreCase(".txt");
    } else {
      return false;
    }
  }

  /**
   * Check whether a file is a Microsoft Word file (based on its extension).
   * 
   * @param fileName a filename. This value may not be null.
   * @return true is the file is a Microsoft Word file, false otherwise.
   */
  public static boolean isMSWordFile(String fileName) {
    String fileExtension = getFileExtension(fileName);
    if (fileExtension != null) {
      return fileExtension.equalsIgnoreCase(".doc")  ||  fileExtension.equalsIgnoreCase(".docx");
    } else {
      return false;
    }
  }

  /**
   * Check whether a file is an OpenDocument Text file (based on its extension).
   * 
   * @param fileName a filename. This value may not be null.
   * @return true is the file is a OpenDocument Text file, false otherwise.
   */
  public static boolean isODTFile(String fileName) {
    String fileExtension = getFileExtension(fileName);
    if (fileExtension != null) {
      return fileExtension.equalsIgnoreCase(".odt");
    } else {
      return false;
    }
  }
  
  /**
   * Check whether a file is a GPX file (based on its extension).
   * 
   * @param file a File. This value may not be null.
   * @return true is the file is a GPX file, false otherwise.
   */
  public static boolean isGpxFile(File file) {
    return isGpxFile(file.getAbsolutePath());
  }
  
  /**
   * Check whether a file is a GPX file (based on its extension).
   * 
   * @param fileName a fileName. This value may not be null.
   * @return true is the file is a GPX file, false otherwise.
   */
  public static boolean isGpxFile(String fileName) {
    String fileExtension = getFileExtension(fileName);
    return GPX_EXTENSION .equals(fileExtension);
  }
  
  /**
   * Create a path name from a prefix and filename.
   * <p>
   * Basically this calls {@code new File(prefix, fileName)} and returns the absolute path.
   * However if the {@code fileName} is null, it just returns null.
   * If the {@code fileName} is an absolute path, a runtime exception is thrown.
   * 
   * @param prefix the prefix to be prepended to the {@code fileName} (may not be null and shall not end with a '/' or '\').
   * @param fileName a relative file name to which the {@code prefix} will be prepended.
   * @return an absolute path constructed from {@code prefix} and {@code fileName}.
   */
  public static String createPathNameFromPrefixAndFileName(String prefix, String fileName) {
    Objects.requireNonNull(prefix, "prefix may not be null");
    
    if (fileName == null) {
      return null;
    }
    
    if (new File(fileName).isAbsolute()) {
      throw new RuntimeException("fileName may not be an absolute filename: " + fileName);
    }
    
    File file = new File(prefix, fileName);
    return file.getAbsolutePath();
  }

  /**
   * Get the path (as String) of a file or folder relative to some base folder.<br/>
   * Example:<br/>
   * referenceFolder: "C:\aFolder\SomeSubfolder"<br/>
   * fileName: "C:\aFolder\SomeSubfolder\myDir\myFile.ext"<br/>
   * returns: "myDir\myFile.ext"<br/>
   * 
   * If {@code fileName} is null, null is returned.
   * 
   * @param referenceFolder the base folder (may not be null and shall not end with a '/' or '\').
   * @param fileName the folder for which the relative path is to be returned
   * @return the path of {@code fileName} relative to the {@code referenceFolder}, or {@code fileName} if that doesn't start with the {@code referenceFolder}.
   */
  public static String getPathRelativeToFolder(String referenceFolder, String fileName) {
    Objects.requireNonNull(referenceFolder, "referenceFolder may not be null");
    
    LOGGER.info("=> referenceFolder=" + referenceFolder + ", file" + fileName);
    if (referenceFolder.endsWith("/")  ||  referenceFolder.endsWith("\\")) {
      throw new RuntimeException("referenceFolder may not end with a '/' or '\\'. referenceFolder is: '" + referenceFolder + "'");
    }
    
    if (fileName == null) {
      return null;
    }
    
    if (fileName.startsWith(referenceFolder)  &&  fileName.length() >= referenceFolder.length() + 1) {
      String strippedName = fileName.substring(referenceFolder.length() + 1);
      LOGGER.info("<= " + strippedName);
      return strippedName;
    } else {
      LOGGER.info("<= (folder)");
      return fileName;
    }
  }
  
  /**
   * Get the last modification date/time of the file, derived from the file attributes.
   * 
   * @return the last modification date/time of the file.
   */
  public static LocalDateTime getLastModificationDate(File file) {
    long millis = file.lastModified();
    
    LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(millis / 1000, 0, ZoneOffset.ofHours(1));
    return localDateTime;
  }
  
  /**
   * Get a list of audio file names in a folder
   * @param folder the folder for which the information is to be gathered.
   * @return a list of audio file names in <code>folder</code>.
   */
  public static List<String> getAudioFileNamesInFolder(String folder) {
    List<String> audioFileNames = new ArrayList<>();
    Path sourceFolderPath = Paths.get(folder);
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceFolderPath)) {
      for (Path file: stream) {
        LOGGER.severe("Handling path: " + file.getFileName().toString());
        if (Files.isDirectory(file)) {
          LOGGER.severe("Skipping directory");
        } else {
          if (isAudioFile(file)) {
            LOGGER.severe("Audio file");            
            String fileName = file.getFileName().toString();
            LOGGER.fine("fileName: " + fileName);
            audioFileNames.add(fileName);
          }
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    
    Collections.sort(audioFileNames);
    
    return audioFileNames;
  }
  
  /**
   * Parse a filename of the format 'yyyyMMdd_HHmmss' to a LocalDateTime.
   * <p>
   * Some applications (like cameras) generate filenames in this format, so the time that the file was originally created can be derived from the filename.
   * 
   * @param fileName the fileName to be parsed.
   * @return the LocalDateTime, parsed from <code>fileName</code>, or null if the parsing failed.
   */
  public static LocalDateTime parseYyyyMmDd_HhMmSs(String fileName) {
    DateTimeFormatter fileNameDateTimeFormat = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    
    LocalDateTime dateTime = null;
    
    try {
      dateTime = LocalDateTime.parse(fileName, fileNameDateTimeFormat);
    } catch (DateTimeParseException e) {
      return null;
    }
    return dateTime;
  }
  
  public static Comparator<File> getComparator() {
    return new Comparator<>() {

      @Override
      public int compare(File o1, File o2) {
        if (o1 == null) {
          if (o2 == null) {
            return -1;
          } else {
            return 0;
          }
        }
        
        if (o2 == null) {
          return 1;
        }
        
        return o1.getAbsolutePath().compareTo(o2.getAbsolutePath());
      }
      
    };
  }

  /**
   * Convert a webp file to a jpeg file.
   * 
   * @param file the file to be converted (may not be null)
   */
  public static File convertWebpFileToJpegFile(File file) throws IOException {
    LOGGER.severe("Going to convert: " + file);
    
    // webp file are often namen name.jpg.webp
    // In that case only the .webp extension is removed.
    String convertedFileName = getFileNameWithoutExtension(file);
    String extension = getFileExtension(convertedFileName);
    if (!DEFAULT_JPEG_EXTENSION.equals(extension)) {
      convertedFileName = convertedFileName + DEFAULT_JPEG_EXTENSION;
    }
    LOGGER.severe("To file: " + convertedFileName);
    
    List<String> commandArguments = new ArrayList<>();
    String irfanviewExecutable = "C:\\Program Files\\IrfanView\\i_view64.exe";
    commandArguments.add(irfanviewExecutable);
    commandArguments.add("\"" + file.getAbsolutePath() + "\"");
    File convertedFile = new File(convertedFileName);
    commandArguments.add("/convert=" + "\"" + convertedFile.getName() + "\"");
    LOGGER.severe("commandArguments: " + commandArguments.toString());
    
    try {
      Process process = new ProcessBuilder(commandArguments).start();
      
      while (process.isAlive()) {
        LOGGER.severe("Waiting for irfanView to end.");
        Thread.sleep(100);
      }
      
      if (convertedFile.exists()) {
        file.delete();
      }
    } catch (IOException e1) {
      e1.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    return convertedFile;
  }
  
  /**
   * Create a zip file for a all files in a folder.
   * 
   * @param zipFile the zip file to be created.
   * @param folderToZip a folder of which all files are to be zipped to {@code zipFile}.
   */
  public static void createZipFileForFolder(Path zipFile, Path folderToZip) throws IOException {
    LOGGER.info("=> zipFile=" + zipFile + ", folderToZip=" + folderToZip);

    Map<String, String> env = new HashMap<>();
    // Create the zip file if it doesn't exist
    env.put("create", "true");

    URI zipFileUri = zipFile.toUri();  // Extra step needed because URI.create(String) requires *encoded* string.
    URI uri = URI.create("jar:file:" + zipFileUri.getRawPath());

    if (Files.exists(zipFile)) {
      LOGGER.severe("Zip file " + zipFile + " already exists.");
      return;
    }
    
    try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
      try {
        Files.walkFileTree(folderToZip, new SimpleFileVisitor<Path>() {
          @Override
          public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
            if (Files.isDirectory(path)) {
              LOGGER.info("Directory: " + path.toString());
            } else {
              LOGGER.info("File: " + path.toString());
            }
            String relativePathName = getPathRelativeToFolder(folderToZip.toString(), path.toString());
            LOGGER.info("relativePathName: " + relativePathName);
            Path pathInZipfile = zipfs.getPath(relativePathName);
            
            // Create directory if it doesn't exist
            Path destinationFolder = pathInZipfile.getParent();
            LOGGER.info("destinationFolder: " + destinationFolder);
            if (destinationFolder != null  &&  !Files.exists(pathInZipfile)) {
              LOGGER.info("Going to create destination folder");
              Files.createDirectories(destinationFolder);
            }
            
            Files.copy(path, pathInZipfile);

            return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            // Handle exception
            return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
          }
        });
      } catch (IOException e) {
        e.printStackTrace();
      }      
    }
  }
}
