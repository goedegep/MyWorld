package goedegep.media.mediadb.app.derivealbuminfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ealvatag.audio.AudioFile;
import ealvatag.audio.AudioFileIO;
import ealvatag.audio.exceptions.CannotReadException;
import ealvatag.audio.exceptions.InvalidAudioFrameException;
import ealvatag.tag.NullTag;
import ealvatag.tag.Tag;
import ealvatag.tag.TagException;
import ealvatag.tag.TagField;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.file.FileUtils;

public class DeriveAlbumInfo {
  private static final Logger LOGGER = Logger.getLogger(DeriveAlbumInfo.class.getName());
  
  
  /**
   * Derive the information of an album from a folder (containing the tracks of an album).
   * 
   * @param sourceFolderName the folder from which the information is to be derived.
   */
  public static AlbumInfo deriveAlbumDetails(String albumFolderName, String imagesFolderName) {
    LOGGER.severe("=> sourceFolderName" + albumFolderName);
        
    Path sourceFolder = Paths.get(albumFolderName);
    AlbumInfo albumInfo = null;

    // read all MP3 and FLAC files in the folder
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceFolder)) {
      List<Path> flacFiles = null;
      
      for (Path path: stream) {
        String fileName = path.getFileName().toString();
        LOGGER.severe("Handling path: " + fileName);
        if (!Files.isDirectory(path)) {
          String extension = FileUtils.getFileExtension(path);
          LOGGER.severe("extension: " + extension);
          
          switch (extension) {
          case ".mp3":
//            deriveInfoFromMp3File(album, path);
            break;
            
          case ".flac":
            if (flacFiles == null) {
              flacFiles = new ArrayList<>();
            }
            flacFiles.add(path);
            break;
            
          default:
            break;
          }
          
        } else {
          LOGGER.severe("Skipping folder: " + fileName);
        }
      }
      
      if (flacFiles != null) {
        albumInfo = deriveInfoFromFlacFiles(flacFiles, true);
      }
    } catch (IOException | DirectoryIteratorException x) {
      // IOException can never be thrown by the iteration.
      System.err.println(x);
    }
    
    return albumInfo;
    
//    // Try to find picture files.
//    
//    // First check whether there are already images in the images folder.
//    // look for files with the name '<artist> - <album-title><anything> - front.jpg' 
//    
//    Path imagesFolder = Paths.get(imagesFolderName);
//    Pattern pattern = Pattern.compile("Alquin - Marks.* - front.jpg");  
//    
//    // read all MP3 and FLAC files in the folder
//    try (DirectoryStream<Path> stream = Files.newDirectoryStream(imagesFolder)) {
//      for (Path path: stream) {
//        String fileName = path.getFileName().toString();
//        Matcher matcher = pattern.matcher(fileName);
//        boolean b = matcher.matches();
//        if (b) {
//          LOGGER.severe("Matching file: " + fileName);
//        }
//      }
//    } catch (IOException | DirectoryIteratorException x) {
//      // IOException can never be thrown by the iteration.
//      System.err.println(x);
//    }
//
//    
//    
//    try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceFolder)) {
//      for (Path path: stream) {
//        String fileName = path.getFileName().toString();
//        LOGGER.severe("Handling path: " + fileName);
//        if (!Files.isDirectory(path)) {
//          String[] fileNameAndExtension = FileUtils.getFileNameAndExtension(path);
//          LOGGER.severe("filename: " + fileNameAndExtension[0] + ", extension: " + fileNameAndExtension[1]);
//          
//          switch (fileNameAndExtension[1]) {
//          case "jpg":
//            if (fileNameAndExtension[0].equalsIgnoreCase("front")) {
////              album.getImagesFront().add(path.toAbsolutePath().toString());
//            }
//            if (fileNameAndExtension[0].equalsIgnoreCase("back")) {
////              album.getImagesBack().add(path.toAbsolutePath().toString());
//            }
//            if (fileNameAndExtension[0].equalsIgnoreCase("inside")) {
////              album.getImagesFrontInside().add(path.toAbsolutePath().toString());
//            }
//            break;
//            
//          default:
//            break;
//          }
//          
//        } else {
//          LOGGER.severe("Skipping folder: " + fileName);
//        }
//      }
//    } catch (IOException | DirectoryIteratorException x) {
//      // IOException can never be thrown by the iteration.
//      System.err.println(x);
//    }
//    
//    
////    if (!album.getImagesFront().isEmpty()  ||  !album.getImagesFrontInside().isEmpty()  ||
////        !album.getImagesBack().isEmpty()  ||  !album.getImagesLabel().isEmpty()) {
////      ImportAlbumPicturesWindow importAlbumPicturesWindow = new ImportAlbumPicturesWindow(getCustomization(), album);
////      importAlbumPicturesWindow.showAndWait();
////    }
//    
////    Album existingAlbum = mediaDb.getAlbum(album.getReleaseDate(), album.getArtist(), album.getTitle());
////    if (existingAlbum != null) {
////      LOGGER.severe("Album already exists in the media database");
////    }
    
  }
    
  /**
   * Derive ...
   * 
   * @param album
   * @param flacFilePaths
   */
  public static AlbumInfo deriveInfoFromFlacFiles(List<Path> flacFilePaths, boolean ignoreDiscNumbers) {
    LOGGER.severe("=>");
    
    String albumArtist = null;
    String albumTitle = null;
    String albumDate = null;
    String discNumber = null;
    
    List<TrackInfo> tracks = new ArrayList<>();
    
    // Gather information from the tracks.
    for (Path flacFilePath: flacFilePaths) {
      LOGGER.severe("file: " + flacFilePath.toString());
      File inputFile = new File(flacFilePath.toString());
      
      String trackNumber = null;
      String trackTitle = null;
      String trackArtist = null;
      
      try {
        AudioFile audioFile = AudioFileIO.read(inputFile);
        Tag tag = audioFile.getTag().or(NullTag.INSTANCE);

        // Normal way to get the information would be by calling tag.getValue(FieldKey.<key>). But FieldKey doesn't have e.g. TRACKNUMBER.
        // Therefor we iterate over the keys and use the values ...
        Iterator<TagField> it = tag.getFields();
        while (it.hasNext()) {
          TagField field = (TagField)it.next();

          // skip empty fields
          if (field.isEmpty()) {
            continue;
          }

          switch (field.getId()) {
          case "ALBUM":  // Album title shall be the same on all tracks.
            String thisAlbumTitle = field.toString();
            if (albumTitle == null) {
              albumTitle = thisAlbumTitle;
            } else {
              if (!thisAlbumTitle.equals(albumTitle)) {
                LOGGER.severe("Different values for album title found");
              }
            }
            break;

          case "ALBUM ARTIST":  // Album artist shall be the same on all tracks.
            String thisAlbumArtist = field.toString();
            if (albumArtist == null) {
              albumArtist = thisAlbumArtist;
            } else {
              if (!thisAlbumArtist.equals(albumArtist)) {
                LOGGER.severe("Different values for album artist found");
              }
            }
            break;

          case "ARTIST":
            trackArtist = field.toString();
            break;

          case "DATE":  // Album date shall be the same on all tracks.
            String thisAlbumDate = field.toString();
            if (albumDate == null) {
              albumDate = thisAlbumDate;
            } else {
              if (!thisAlbumDate.equals(albumDate)) {
                LOGGER.severe("Different values for album date found");
              }
            }
            break;

          case "DISCNUMBER":  // Disc number, shall (for now) be the same on all tracks.
            String thisDiscNumber = field.toString();
            if (discNumber == null) {
              discNumber = thisDiscNumber;
            } else {
              if (!ignoreDiscNumbers  &&  !thisDiscNumber.equals(discNumber)) {
                LOGGER.severe("Different values for disc number found");
              }
            }
            break;

          case "TITLE":
            trackTitle = field.toString();
            break;

          case "TRACKNUMBER":
            trackNumber = field.toString();
            break;
          }
        }
      } catch (CannotReadException | IOException | TagException | InvalidAudioFrameException e) {
        e.printStackTrace();
      }
      
      TrackInfo trackDetails = new TrackInfo(trackNumber, trackTitle, trackArtist);
      tracks.add(trackDetails);
    }
    
    FlexDateFormat flexDateFormat = new FlexDateFormat();
    FlexDateFormat flexDateFormatReversedDate = new FlexDateFormat(true, true);
    FlexDate date = null;
    
    // First try for a normal FlexDate
    try {
      date = flexDateFormat.parse(albumDate);
    } catch (ParseException e) {
      // no action
    }
    
    // Then try for a reversed FlexDate
    if (date == null) {
      try {
        date = flexDateFormatReversedDate.parse(albumDate);
      } catch (ParseException e) {
        // no action
      }
    }
    
    // Then check whether it starts with 4 digits. If so we might at least have the release year.
    if (date == null) {
      Pattern pattern = Pattern.compile("\\d\\d\\d\\d.*");
      Matcher matcher = pattern.matcher(albumDate);  
      boolean match = matcher.matches();
      if (match) {
        albumDate = albumDate.substring(0, 4);
        try {
          date = flexDateFormat.parse(albumDate);
        } catch (ParseException e) {
          // no action
        }        
      }
    }
    
    sortTracks(tracks);
    
    // Print gathered information
    LOGGER.severe("Gathered information:");
    LOGGER.severe("Disc number: " + discNumber);
    LOGGER.severe("Album title: " + albumTitle);
    LOGGER.severe("Album artist: " + albumArtist);
    LOGGER.severe("Album date: " + albumDate);
    for (TrackInfo track: tracks) {
      LOGGER.severe("Track number=" + track.trackNr() + ", Title=" + track.trackTitle() + ", Artist=" + track.trackArtist());
    }
    
    List<DiscInfo> discInfos = new ArrayList<>();
    DiscInfo discInfo = new DiscInfo(tracks);
    discInfos.add(discInfo);
    AlbumInfo albumInfo = new AlbumInfo(albumTitle, albumArtist, date, discInfos);

    LOGGER.info("<=");
    
    return albumInfo;
   
    
//    Disc disc = album.getDiscs().get(0);
    
    /*
     *  Get the information from the tracks.
     */
//    List<Tag> tags = new ArrayList<>();
//    for (Path flacFilePath: flacFilePaths) {
//      LOGGER.severe("file: " + flacFilePath.toString());
//      File inputFile = new File(flacFilePath.toString());
//      try {
//        AudioFile audioFile = AudioFileIO.read(inputFile);
//        Tag tag = audioFile.getTag().or(NullTag.INSTANCE);
//        
//        // Normal way to get the information would be by calling tag.getValue(FieldKey.<key>). But FieldKey doesn't have e.g. TRACKNUMBER.
//        // Therefor we iterate over the keys and use the values ...
//        Iterator<TagField> it = tag.getFields();
//        while (it.hasNext()) {
//          TagField field = (TagField)it.next();
//          LOGGER.severe(field.getId() + ":" + field.toString());
//        }
//        
//        
//        
//        LOGGER.severe("tag: " + tag.toString());
//        tags.add(tag);
//        
//        // Debugging only. Print all FieldKey values. TODO except the ones which aren't supported by the library.
//        for (FieldKey fieldKey: FieldKey.values()) {
////          LOGGER.severe("fieldKey: " + fieldKey.name());
//          if (fieldKey == FieldKey.COVER_ART  ||
//              fieldKey == FieldKey.ITUNES_GROUPING) {
//            continue;
//          }
//          
//          Optional<String> optionalTag = tag.getValue(fieldKey);
//          if (optionalTag.isPresent()) {
//            LOGGER.severe("fieldKey:" + fieldKey.name() + "=" + optionalTag.get());
//          }
//        }
//      } catch (CannotReadException | IOException | TagException | InvalidAudioFrameException e) {
//        e.printStackTrace();
//      }
//    }
    
    /*
     *  Sort the tracks. This is based on TRACK. If there's at least one track for which this isn't set, get it from the filename.
     */
    
    /*
     *  Determine the album artist name.
     *  This is the ALBUM_ARTIST. This shall be the same for all tracks., or, if this isn't available, the ARTIST (if this is the same for all tracks).
     *  If the ALBUM_ARTIST isn't available, and the ARTIST is the same for all tracks, then this is the album artist name.
     */
//    String albumArtistName = null;
//    for (Tag tag: tags) {
//      Optional<String> optionalTag = tag.getValue(FieldKey.ALBUM_ARTIST);
//      if (optionalTag.isPresent()) {
//        String currentAlbumArtistName = optionalTag.get();
//        if (albumArtistName == null) {
//          albumArtistName = currentAlbumArtistName;
//        } else {
//          if (!albumArtistName.equals(currentAlbumArtistName)) {
//            LOGGER.severe("Different album artist names detected.");
//          }
//        }
//      }
//    }
    
//    if (albumArtistName == null) {
//      for (Tag tag: tags) {
//        Optional<String> optionalTag = tag.getValue(FieldKey.ARTIST);
//        if (optionalTag.isPresent()) {
//          String currentArtistName = optionalTag.get();
//          if (albumArtistName == null) {
//            albumArtistName = currentArtistName;
//          } else {
//            if (!albumArtistName.equals(currentArtistName)) {
//              LOGGER.severe("Different artist names detected.");
//            }
//          }
//        }
//      }
//    }
    
    /*
     *  If the artist isn't known in de database, ask the user whether it should be added.
     */
//    Artist artist = null;
//    if (albumArtistName != null) {
//      artist = mediaDb.getArtist(albumArtistName);
//      if (artist == null) {
//        ArtistDetailsEditor artistDetailsEditor = new ArtistDetailsEditor(DefaultCustomizationFx.getInstance(), "New artist", mediaDb, "The artist doesn't exist in the database yet. Do you want to add it?", albumArtistName);
////        artistDetailsEditor.initModality(Modality.APPLICATION_MODAL);
//        artistDetailsEditor.showAndWait();
//        // retry to get the artist, should succeed if it has been added.
//        artist = mediaDb.getArtist(albumArtistName);
//      }
//      if (artist != null) {
////        album.setArtist(artist);
//      }
//    }
    
//    // Determine the album title. This is the ALBUM. This shall be the same for all tracks.
//    for (Tag tag: tags) {
//      Optional<String> optionalTag = tag.getValue(FieldKey.ALBUM);
//      if (optionalTag.isPresent()) {
////        String albumTitle = optionalTag.get();
//        if (!album.isSetTitle()) {
//          album.setTitle(albumTitle);
//        } else {
//          if (!album.getTitle().equals(albumTitle)) {
//            LOGGER.severe("Different album titles detected.");
//          }
//        }
//      }
//    }
    
//    // Determine the year of issue. This is the YEAR. This shall be the same for all tracks.
//    Integer issueYear = null;
//    for (Tag tag: tags) {
//      Optional<String> optionalTag = tag.getValue(FieldKey.YEAR);
//      if (optionalTag.isPresent()) {
//        try {
//          Integer currentIssueYear = Integer.valueOf(optionalTag.get());
//          if (issueYear == null) {
//            issueYear = currentIssueYear;
//          } else {
//            if (!issueYear.equals(currentIssueYear)) {
//              LOGGER.severe("Different issue years detected.");
//            }
//          }
//        } catch (NumberFormatException e) {
//          // no action
//        }
//      }
//    }
//    if (issueYear != null) {
//      album.setReleaseDate(new FlexDate(issueYear));
//    }
    
//    // Determine the track title. This is the TITLE. Create and add track with this title
//    for (Tag tag: tags) {
//      
//      
//      String trackTitle = null;
//      Optional<String> optionalTag = tag.getValue(FieldKey.TITLE);
//      if (optionalTag.isPresent()) {
//        trackTitle = optionalTag.get();
//      }
//      
////      track.setOriginalAlbum(album);
//      
////      mediaDb.getTracks().add(track);
////      Track track = MediaDbUtil.getOrAddTrack(mediaDb, artist, trackTitle, disc);
//      
////      TrackReference trackReference = MEDIA_DB_FACTORY.createTrackReference();
////      trackReference.setTrack(track);
////      
////      disc.getTrackReferences().add(trackReference);
//    }
      
  }

  private static void sortTracks(List<TrackInfo> tracks) {
    Collections.sort(tracks, new Comparator<TrackInfo>() {

      @Override
      public int compare(TrackInfo o1, TrackInfo o2) {
        if (o1 == null  &&  o2 == null) {
          return 0;
        }
        if (o1 == null  &&  o2 != null) {
          return -1;
        }
        if (o1 != null  &&  o2 == null) {
          return 1;
        }
        
        int result = o1.trackNr().compareTo(o2.trackNr());
        return result;
      }
      
    });
    
  }

}

