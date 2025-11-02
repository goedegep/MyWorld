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

import com.google.common.base.Optional;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import ealvatag.audio.AudioFile;
import ealvatag.audio.AudioFileIO;
import ealvatag.audio.exceptions.CannotReadException;
import ealvatag.audio.exceptions.InvalidAudioFrameException;
import ealvatag.tag.FieldKey;
import ealvatag.tag.NullTag;
import ealvatag.tag.Tag;
import ealvatag.tag.TagException;
import ealvatag.tag.TagField;
import goedegep.media.common.MediaRegistry;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.file.FileUtils;

/**
 * This class provides a method to derive album information from a folder with audio tracks.
 * <p>
 * There are different ways to derive the information, but all files should have the same kind of information.
 */
public class DeriveAlbumInfo {
  private static final Logger LOGGER = Logger.getLogger(DeriveAlbumInfo.class.getName());
  private static final MediadbFactory MEDIA_DB_FACTORY = MediadbFactory.eINSTANCE;
  
  private final FlexDateFormat flexDateFormat = new FlexDateFormat();
  
  private MediaDb mediaDb;
  private MediaRegistry mediaRegistry;
  private InformationType informationType;
  
  public DeriveAlbumInfo(MediaDb mediaDb) {
    this.mediaDb = mediaDb;
    mediaRegistry = MediaRegistry.getInstance();
  }
  
  /**
   * Derive the information of an album from a folder (containing the tracks of an album).
   * <p>
   * Important: the returned {@code Album} is not part of the media database and does not reference any items in the media database.
   * So the returned information is only to be used to fill the controls of the AlbumEditor.<br/>
   * This method only handles single disc albums. All tracks found are added to a single disc.
   * 
   * @param albumFolderName the folder from which the information is to be derived.
   * @param imagesFolderName the folder where images of the album may be found
   * @return an {@code Album} with the derived information, or null if no useful information could be derived.
   */
  public MediaDb deriveAlbumDetails(String albumFolderName, String imagesFolderName) {
    LOGGER.severe("=> sourceFolderName" + albumFolderName);
    
    Album album = MEDIA_DB_FACTORY.createAlbum();
    
    MyInfo myInfo = MEDIA_DB_FACTORY.createMyInfo();
    album.setMyInfo(myInfo);
    
    mediaDb.getAlbums().add(album);
        
    Path sourceFolder = Paths.get(albumFolderName);
    
    informationType = determineInformationType(sourceFolder);
    
    if (informationType == null) {
      LOGGER.severe("Folder cannot be handled");
      return mediaDb;
    }
    
    switch (informationType) {
    case FLAC_META_DATA:
      deriveAlbumDetailsFromFlacMetaData(album, sourceFolder);
      handleSingleArtistAlbum(album);
      break;
      
    default:
      throw new RuntimeException();
    }

//
//    // read all MP3 and FLAC files in the folder
//    try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceFolder)) {
////      List<Path> flacFiles = null;
//      
//      for (Path path: stream) {
//        String fileName = path.getFileName().toString();
//        LOGGER.severe("Handling path: " + fileName);
//        if (!Files.isDirectory(path)) {
//          String extension = FileUtils.getFileExtension(path);
//          LOGGER.severe("extension: " + extension);
//          
//          switch (extension) {
//          case ".mp3":
//            deriveInfoFromMp3File(album, path);
//            break;
//            
//          case ".flac":
//            deriveInfoFromFlacFile(album, path);
////            if (flacFiles == null) {
////              flacFiles = new ArrayList<>();
////            }
////            flacFiles.add(path);
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
//      
////      if (flacFiles != null) {
////        deriveInfoFromFlacFiles(flacFiles, true, album);
////      }
//    } catch (IOException | DirectoryIteratorException x) {
//      // IOException can never be thrown by the iteration.
//      System.err.println(x);
//    }
    
    // Try to find picture files.
    
    // First check whether there are already images in the images folder.
    // Look for files with the name '<artist> - <album-title><anything> - front.jpg'
    // The <anything> is for extensions like 'single', 'lp' or whatever.
    // This can of course only be done if we have album artist and title.
    
    if (album.getTitle() != null  &&  album.getArtist() != null) {
      Path imagesFolder = Paths.get(imagesFolderName);
      Pattern pattern = Pattern.compile(album.getArtist().getName() + " - " + album.getTitle() + ".* - front.jpg");
      LOGGER.severe("pattern: " + pattern.pattern());

      try (DirectoryStream<Path> stream = Files.newDirectoryStream(imagesFolder)) {
        for (Path path: stream) {
          String fileName = path.getFileName().toString();
          Matcher matcher = pattern.matcher(fileName);
          boolean b = matcher.matches();
          if (b) {
            LOGGER.severe("Matching file: " + fileName);
            String relativeFileName = FileUtils.getPathRelativeToFolder(mediaRegistry.getMusicDataDirectory() + "\\", path.toString());
             album.getImagesFront().add(relativeFileName);
          }
        }
      } catch (IOException | DirectoryIteratorException x) {
        // IOException can never be thrown by the iteration.
        System.err.println(x);
      }
    }
    
    
    LOGGER.severe("<= \n" + album);
    return mediaDb;
    
    
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
   * Handle a single artist album.
   * <p>
   * If all tracks of the album have the same artist, this artist is set at album level
   * and the artist is removed from the tracks.
   */
  private void handleSingleArtistAlbum(Album album) {
    Artist artist = null;
    
    for (Disc disc: album.getDiscs()) {
      for (TrackReference trackReference: disc.getTrackReferences()) {
        Artist trackArtist = trackReference.getTrack().getArtist();
        if (trackArtist == null) {
          return;
        }
        
        if (artist == null) {
          artist = trackArtist;
        } else {
          if (trackArtist != artist) {
            return;
          }
        }
      }
    }
    
    // If we get here, all tracks have the same artist
    
    album.setArtist(artist);
    
    for (Disc disc: album.getDiscs()) {
      for (TrackReference trackReference: disc.getTrackReferences()) {
        trackReference.getTrack().setArtist(null);
      }
    }
    
    
  }

  /**
   * Derive album information from the meta data in flac files.
   * <p>
   * First a list of the meta data of all flac files is gathered. This list is then sorted based on the track number.
   * After this the tracks are handled one by one, in the right order.
   */
  private void deriveAlbumDetailsFromFlacMetaData(Album album, Path sourceFolder) {

    // create a list of the meta data of all flac files.
    List<AudioFile> flacFilesMetaData = new ArrayList<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceFolder)) {

      for (Path path: stream) {
        if (!Files.isDirectory(path)  &&  FileUtils.isFlacFile(path)) {
          try {
            AudioFile audioFile = AudioFileIO.read(path.toFile());
            flacFilesMetaData.add(audioFile);
          } catch (CannotReadException | TagException | InvalidAudioFrameException e) {
            e.printStackTrace();
          }
        }
      }

    } catch (IOException | DirectoryIteratorException x) {
      // IOException can never be thrown by the iteration.
      x.printStackTrace();
    }

    // Sort the files (meta data)
    sortAudioFileList(flacFilesMetaData);

    // Handle the files (meta data)
    for (AudioFile audioFile: flacFilesMetaData) {
      deriveInfoFromFlacFileMetaData(album, audioFile);
    }
  }

  /**
   * Determine how to derive the information.
   * <p>
   * 
   * 
   * @param sourceFolder the folder containing the audio files.
   * @return the {@code InformationType} for handling the information, which may be null.
   */
  private InformationType determineInformationType(Path sourceFolder) {
    InformationType informationType = null;
    
    // read all MP3 and FLAC files in the folder
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceFolder)) {
      
      for (Path path: stream) {
        InformationType fileInformationType = null;
        
        if (!Files.isDirectory(path)) {
          String extension = FileUtils.getFileExtension(path);
          LOGGER.severe("extension: " + extension);
          
          switch (extension) {
          case ".mp3":
            // TODO
            break;
            
          case ".flac":
            AudioFile audioFile;
            try {
              audioFile = AudioFileIO.read(path.toFile());
              Tag tag = audioFile.getTag().or(NullTag.INSTANCE);
              if (tag.getValue(FieldKey.TITLE) != null) {
                fileInformationType = InformationType.FLAC_META_DATA;
              }
            } catch (CannotReadException | TagException | InvalidAudioFrameException e) {
              e.printStackTrace();
            }
            
            if (fileInformationType == null) {
              // TODO
            }
            break;
            
          default:
            break;
          }
          
        }
        
        if (informationType == null) {
          informationType = fileInformationType;
        } else if (fileInformationType != null  &&  fileInformationType != informationType) {
          return null;
        }
      }
      
    } catch (IOException | DirectoryIteratorException x) {
      // IOException can never be thrown by the iteration.
      x.printStackTrace();
    }
    
    return informationType;
  }

  /**
   * Derive track and album information from an .mp3 file.
   * 
   * @param album The {@link Album} to add the derived information to.
   * @param mp3FilePath the {@code Path} of the .mp3 file.
   */
  private void deriveInfoFromMp3File(Album album, Path mp3FilePath) {
    LOGGER.severe("=>");
//
////    Disc disc = album.getDiscs().get(0);
//
//    // First try to get the information from the mp3 file meta data.
//    if (deriveInfoFromMp3FileMetaData(album, mp3FilePath)) {
//      return;
//    }
//    
//     try {
////      Mp3File mp3file = new Mp3File(mp3FilePath);
//      
//      long lengthInSeconds = mp3file.getLengthInSeconds();
//      LOGGER.severe("Length of this mp3 is: " + lengthInSeconds + " seconds");
//      track.setDuration((int) lengthInSeconds);
//      //      System.out.println("Bitrate: " + mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)"));
//      //      System.out.println("Sample rate: " + mp3file.getSampleRate() + " Hz");
//      //      System.out.println("Has ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
//      //      System.out.println("Has ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
//      //      System.out.println("Has custom tag?: " + (mp3file.hasCustomTag() ? "YES" : "NO"));
//
//      if (mp3file.hasId3v1Tag()) {
//        ID3v1 id3v1Tag = mp3file.getId3v1Tag();
//
//        System.out.println("Track: " + id3v1Tag.getTrack());
//        System.out.println("Artist: " + id3v1Tag.getArtist());
//        String artistName = id3v1Tag.getArtist();
//        Artist artist = MEDIA_DB_FACTORY.createArtist();
//        artist.setName(artistName);
//// TODo move to import screen
////        if (artist == null) {
////          ArtistDetailsEditor artistDetailsEditor = new ArtistDetailsEditor(getCustomization(), "New artist", mediaDb, "The artist doesn't exist in the database yet. Do you want to add it?", artistName);
////          artistDetailsEditor.initModality(Modality.APPLICATION_MODAL);
////          artistDetailsEditor.showAndWait();
////          // retry to get the artist, should succeed if it has been added.
////          artist = mediaDb.getArtist(id3v1Tag.getArtist());
////        }
//        album.setArtist(artist);
//        System.out.println("Title: " + id3v1Tag.getTitle());
//        System.out.println("Album: " + id3v1Tag.getAlbum());
//        album.setTitle(id3v1Tag.getAlbum());
////        track = MediaDbUtil.getOrAddTrack(mediaDb, artist, id3v1Tag.getTitle(), disc);
//        System.out.println("Year: " + id3v1Tag.getYear());
//        Integer year = Integer.valueOf(id3v1Tag.getYear());
//        FlexDate releaseDate = new FlexDate(year);
//        album.setReleaseDate(releaseDate);
//        System.out.println("Genre: " + id3v1Tag.getGenre() + " (" + id3v1Tag.getGenreDescription() + ")");
//        System.out.println("Comment: " + id3v1Tag.getComment());
//        System.out.println("Version: " + id3v1Tag.getVersion());
//        System.out.println("Year: " + id3v1Tag.getYear());
//        TrackReference trackReference = MEDIA_DB_FACTORY.createTrackReference();
//        trackReference.setTrack(track);
//        disc.getTrackReferences().add(trackReference);
//      }
//    } catch (UnsupportedTagException | InvalidDataException | IOException e) {
//      e.printStackTrace();
//    }
  }
  
  /**
   * Derive track and album information from the meta data of an .mp3 file.
   * If at least the track title is available, we use the information from this meta data.
   * 
   * @param album The {@link Album} to add the derived information to.
   * @param mp3FilePath the {@code Path} of the .mp3 file.
   */
  private boolean deriveInfoFromMp3FileMetaData(Album album, Path mp3FilePath) {
    try {
      Mp3File mp3file = new Mp3File(mp3FilePath);
      
      if (!mp3file.hasId3v1Tag()) {
        return false;
      }
      
      ID3v1 id3v1Tag = mp3file.getId3v1Tag();
      if (id3v1Tag.getTitle() == null) {
        return false;
      }
      
      Disc disc = album.getDiscs().get(0);

      // Assume that each file is a unique track (so to be added to the {@code mediaDb} and the disc).
      Track track = MEDIA_DB_FACTORY.createTrack();
      track.setTitle(id3v1Tag.getTitle());
      mediaDb.getTracks().add(track);
      
      TrackReference trackReference = MEDIA_DB_FACTORY.createTrackReference();
      trackReference.setTrack(track);
      disc.getTrackReferences().add(trackReference);
      
      return true;
    } catch (UnsupportedTagException | InvalidDataException | IOException e) {
      e.printStackTrace();
      return false;
    }
  }
  
  /**
   * Derive track and album information from an .flac file.
   * 
   * @param album The {@link Album} to add the derived information to.
   * @param flacFilePath the {@code Path} of the .flac file.
   */
  public void deriveInfoFromFlacFile(Album album, Path flacFilePath) {
    LOGGER.severe("flacFilePath: " + flacFilePath.toString());
    try {
      AudioFile audioFile = AudioFileIO.read(flacFilePath.toFile());
      Tag tag = audioFile.getTag().or(NullTag.INSTANCE);
      if (tag.getValue(FieldKey.TITLE) != null) {
        deriveInfoFromFlacFileMetaData(album, audioFile);
      }
      
    } catch (CannotReadException | IOException | TagException | InvalidAudioFrameException e) {  // NOPMD
      // No action if meta data cannot be read.
    }
  }
  
  /**
   * Derive track and album information from an .flac file its {@code AudioFile} information.
   * 
   * @param album The {@link Album} to add the derived information to.
   * @param flacFilePath the {@code Path} of the .flac file.
   */
  public void deriveInfoFromFlacFileMetaData(Album album, AudioFile audioFile) {
    LOGGER.severe("=>");

    // Album level info
    String albumArtistName = null;
    String albumTitle = null;
    String albumDateText = null;
    
    // Disc level info
    String discNumber = null;

    // Track level info
    String trackTitle = null;
    String trackArtistName = null;
    String trackStyle = null;
    String trackComposer = null;
    

    Tag tag = audioFile.getTag().or(NullTag.INSTANCE);
    debugPrintFieldKeyValues(tag);

    // Normal way to get the information would be by calling tag.getValue(FieldKey.<key>). But FieldKey doesn't have e.g. TRACKNUMBER.
    // Therefore we iterate over the keys and use the values ...
    Iterator<TagField> it = tag.getFields();
    while (it.hasNext()) {
      TagField field = (TagField) it.next();

      // skip empty fields
      if (field.isEmpty()) {
        continue;
      }

      switch (field.getId()) {
      case "ALBUM":  // Album title shall be the same on all tracks.
        albumTitle = field.toString();
        break;

      case "ALBUM ARTIST":  // Album artist shall be the same on all tracks.
        albumArtistName = field.toString();
        break;

      case "ARTIST":
        trackArtistName = field.toString();
        break;
        
      case "COMPOSER":
        trackComposer = field.toString();
        break;

      case "DATE":  // Album date shall be the same on all tracks.
        albumDateText = field.toString();
        break;

      case "DISCNUMBER":  // Disc number, shall (for now) be the same on all tracks.
        discNumber = field.toString();
        break;
        
      case "GENRE":
        trackStyle = field.toString();
        break;

      case "TITLE":
        trackTitle = field.toString();
        break;

      case "TRACKNUMBER":
        // not needed as the AudioFile list is already sorted.
        break;
        
      // Id's not relevant  
      case "VENDOR":
      case "MAJOR_BRAND":
      case "MINOR_VERSION":
      case "COMPATIBLE_BRANDS":
      case "ITUNES_CDDB_TRACKNUMBER":
      case "DESCRIPTION":
      case "ITUNES_CDDB_1":
      case "ENCODER":
        break;
        
      default:
        LOGGER.severe("Skipping field Id: " + field.getId());
        break;
      }
      
    }
    
    // Handle album title
    if (albumTitle != null) {
      if (album.getTitle() == null) {
        album.setTitle(albumTitle);
      } else {
        if (!albumTitle.equals(album.getTitle())) {
          LOGGER.severe("different album titles found: " + albumTitle + ", " + album.getTitle());
        }
      }
    }
    
    // Handle album artist
    Artist albumArtist = null;
    if (albumArtistName != null) {
      albumArtist = getOrAddArtistByName(albumArtistName);
      
      if (album.getArtist() == null) {
        album.setArtist(albumArtist);
      } else {
        if (!albumArtist.equals(album.getArtist())) {
          LOGGER.severe("different album artists found: " + albumArtist.getName() + ", " + album.getArtist().getName());
        }
      }
    }
    
    // Handle album date
    FlexDate albumDate = null;
    if (albumDateText != null) {
      albumDate = parseFlexDate(albumDateText);
      
      if (albumDate != null) {
        if (album.getReleaseDate() == null) {
          album.setReleaseDate(albumDate);
        } else {
          if (!album.getReleaseDate().equals(albumDate)) {
            LOGGER.severe("different album release dates found: " + flexDateFormat.format(albumDate) + ", " + flexDateFormat.format(album.getReleaseDate()));
          }
        }
      }
    }
    
    // Handle disc number
    Disc disc = getDiscForDiscNumber(album, discNumber);
    
    // Handle track artist and title
    Track track = null;
    if (trackArtistName != null) {
      Artist trackArtist = getOrAddArtistByName(trackArtistName);
      
      track = mediaDb.getTrack(trackArtist, trackTitle);
      // As we are handling a single album in our mediaDb, there shouldn't be a track with this title and artist yet.
      if (track == null) {
        track = MEDIA_DB_FACTORY.createTrack();
        track.setTitle(trackTitle);
        if (albumArtist == null  ||  !albumArtist.equals(trackArtist)) {
          track.setArtist(trackArtist);
        }
        mediaDb.getTracks().add(track);
      } else {
        LOGGER.severe("Track already exists in media database: " + track.toString());
      }
    }
    
    // Handle artist style (genre)
    if (trackStyle != null) {
      if (track != null) {
        if (track.getArtist() != null) {
          if (track.getArtist().getStyle() != null  &&  !track.getArtist().getStyle().equals(trackStyle)) {
            LOGGER.severe("Different style for artist found");
          }
          track.getArtist().setStyle(trackStyle);
        } else if (album.getArtist() != null) {
          if (album.getArtist().getStyle() != null  &&  !album.getArtist().getStyle().equals(trackStyle)) {
            LOGGER.severe("Different style for artist found");
          }
          album.getArtist().setStyle(trackStyle);
        }
      }
    }
    
    // Handle track authors TODO I don't want all track authors in the database, so adding them should be optional
//    if (trackComposer != null) {
//      Artist author = getOrAddArtistByName(trackComposer);
//      track.getAuthors().add(author);
//    }
    
    
    TrackReference trackReference = MEDIA_DB_FACTORY.createTrackReference();
    trackReference.setTrack(track);
    trackReference.setOriginalAlbumTrackReference(true); // assuming this is a normal album
    
    disc.getTrackReferences().add(trackReference);

    // Print gathered information
    LOGGER.severe("Gathered information:");
    LOGGER.severe("Album title: " + albumTitle);
    LOGGER.severe("Album artist: " + albumArtist);
    LOGGER.severe("Album date: " + albumDate);
    LOGGER.severe("Disc number: " + discNumber);
    LOGGER.severe("Track: " + track);

    LOGGER.info("<=");

    return;


    /*
     *  If the artist isn't known in de database, ask the user whether it should be added.
     */
    //  Artist artist = null;
    //  if (albumArtistName != null) {
    //    artist = mediaDb.getArtist(albumArtistName);
    //    if (artist == null) {
    //      ArtistDetailsEditor artistDetailsEditor = new ArtistDetailsEditor(DefaultCustomizationFx.getInstance(), "New artist", mediaDb, "The artist doesn't exist in the database yet. Do you want to add it?", albumArtistName);
    ////      artistDetailsEditor.initModality(Modality.APPLICATION_MODAL);
    //      artistDetailsEditor.showAndWait();
    //      // retry to get the artist, should succeed if it has been added.
    //      artist = mediaDb.getArtist(albumArtistName);
    //    }
    //    if (artist != null) {
    ////      album.setArtist(artist);
    //    }
    //  }

    //  // Determine the album title. This is the ALBUM. This shall be the same for all tracks.
    //  for (Tag tag: tags) {
    //    Optional<String> optionalTag = tag.getValue(FieldKey.ALBUM);
    //    if (optionalTag.isPresent()) {
    ////      String albumTitle = optionalTag.get();
    //      if (!album.isSetTitle()) {
    //        album.setTitle(albumTitle);
    //      } else {
    //        if (!album.getTitle().equals(albumTitle)) {
    //          LOGGER.severe("Different album titles detected.");
    //        }
    //      }
    //    }
    //  }

    //  // Determine the year of issue. This is the YEAR. This shall be the same for all tracks.
    //  Integer issueYear = null;
    //  for (Tag tag: tags) {
    //    Optional<String> optionalTag = tag.getValue(FieldKey.YEAR);
    //    if (optionalTag.isPresent()) {
    //      try {
    //        Integer currentIssueYear = Integer.valueOf(optionalTag.get());
    //        if (issueYear == null) {
    //          issueYear = currentIssueYear;
    //        } else {
    //          if (!issueYear.equals(currentIssueYear)) {
    //            LOGGER.severe("Different issue years detected.");
    //          }
    //        }
    //      } catch (NumberFormatException e) {
    //        // no action
    //      }
    //    }
    //  }
    //  if (issueYear != null) {
    //    album.setReleaseDate(new FlexDate(issueYear));
    //  }

    //  // Determine the track title. This is the TITLE. Create and add track with this title
    //  for (Tag tag: tags) {
    //    
    //    
    //    String trackTitle = null;
    //    Optional<String> optionalTag = tag.getValue(FieldKey.TITLE);
    //    if (optionalTag.isPresent()) {
    //      trackTitle = optionalTag.get();
    //    }
    //    
    ////    track.setOriginalAlbum(album);
    //    
    ////    mediaDb.getTracks().add(track);
    ////    Track track = MediaDbUtil.getOrAddTrack(mediaDb, artist, trackTitle, disc);
    //    
    ////    TrackReference trackReference = MEDIA_DB_FACTORY.createTrackReference();
    ////    trackReference.setTrack(track);
    ////    
    ////    disc.getTrackReferences().add(trackReference);
    //  }

  }
  
  /**
   * Get or create the disc for a specific disc number.
   * 
   * @param discNumber identification of the disc
   * @return the {@code Disc} for {@code discNumber}.
   */
  private Disc getDiscForDiscNumber(Album album, String discNumber) {
    Disc disc = null;
    
    if (discNumber != null) {
      for (Disc aDisc: album.getDiscs()) {
        if (discNumber.equals(aDisc.getTitle())) {
          disc = aDisc;
          break;
        }
      }
      
      if (disc == null) {
        disc = MEDIA_DB_FACTORY.createDisc();
        disc.setTitle(discNumber);
        album.getDiscs().add(disc);
      }
    } else {
      // assume single disc album
      if (album.getDiscs().size() > 1) {
        LOGGER.severe("Track without disc number in multi disc album, adding track to first disc");
        disc = album.getDiscs().get(0);
      } else if  (album.getDiscs().isEmpty()) {
        disc = MEDIA_DB_FACTORY.createDisc();
        album.getDiscs().add(disc);
      } else {
        disc = album.getDiscs().get(0);
      }
    }
    
    return disc;
  }

  /**
   * Get an {@code Artist} by name from the media database.
   * <p>
   * If an artist by that name already exists, this is returned.
   * Else a new artist with that name is added to the media database and that artist is returned.
   * 
   * @param artistName
   * @return the {@code Artist} with the specified {@code artistName}.
   */
  private Artist getOrAddArtistByName(String artistName) {
    Artist artist = mediaDb.getArtist(artistName);
    if (artist == null) {
      artist = MEDIA_DB_FACTORY.createArtist();
      artist.setName(artistName);
      mediaDb.getArtists().add(artist);
    }
    
    return artist;
  }
  
  /**
   * Try to parse a string to a {@code FlexDate}.
   * 
   * @param dateString The string to be parsed.
   * @return the {@code FlexDate} parsed from the {@code dateString}, or null if parsing failed.
   */
  private FlexDate parseFlexDate(String dateString) {
    FlexDateFormat flexDateFormatReversedDate = new FlexDateFormat(true, true);
    FlexDate date = null;

    // First try for a normal FlexDate
    try {
      date = flexDateFormat.parse(dateString);
    } catch (ParseException e) {  // NOPMD
      // no action
    }

    // Then try for a reversed FlexDate
    if (date == null) {
      try {
        date = flexDateFormatReversedDate.parse(dateString);
      } catch (ParseException e) {  // NOPMD
        // no action
      }
    }

    // Then check whether it starts with 4 digits. If so we might at least have the release year.
    if (date == null) {
      Pattern pattern = Pattern.compile("\\d\\d\\d\\d.*");
      Matcher matcher = pattern.matcher(dateString);  
      boolean match = matcher.matches();
      if (match) {
        String yearString = dateString.substring(0, 4);
        try {
          date = flexDateFormat.parse(yearString);
        } catch (ParseException e) {  // NOPMD
          // no action
        }        
      }
    }
    
    return date;
  }


  /**
   * Derive album information from the flac files in a folder.
   * 
   * @param flacFilePaths the flac files
   * @param ignoreDiscNumbers ToDo
   * @param album the {@code Album} to add the information to.
   */
  public static void deriveInfoFromFlacFiles(List<Path> flacFilePaths, boolean ignoreDiscNumbers, Album album) {
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
    
//    sortAudioFileList(tracks);
    
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

    LOGGER.info("<=");
    
    return;
   
    
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

  /**
   * Sort a list of {@code AudioFile} based on their track numbers.
   * <p>
   * This method first checks that all {@code audioFiles} have a track number.
   * Only if this is the case, sorting takes place.
   * 
   * @param audioFiles the list to be sorted.
   */
  private static void sortAudioFileList(List<AudioFile> audioFiles) {
    boolean canBeSorted = true;
    for (AudioFile audioFile: audioFiles) {
      if (getTrackNumber(audioFile) == null) {
        canBeSorted = false;
        break;
      }
    }
    
    if (!canBeSorted) {
      return;
    }
    
    Collections.sort(audioFiles, new Comparator<AudioFile>() {

      @Override
      public int compare(AudioFile o1, AudioFile o2) {
        Integer trackNumber1 = getTrackNumber(o1);
        Integer trackNumber2 = getTrackNumber(o2);
        
        int result = trackNumber1.compareTo(trackNumber2);
        return result;
      }

    });

  }
  
  /**
   * Get the track number from {@code AudioFile} meta data.
   * 
   * @param audioFile the {@code AudioFile} to get the track number from.
   * @return the track number, or null if it isn't available.
   */
  private static Integer getTrackNumber(AudioFile audioFile) {
    String trackNumberText = null;
    Integer trackNumber = null;
    Tag tag = audioFile.getTag().or(NullTag.INSTANCE);

    // Normal way to get the information would be by calling tag.getValue(FieldKey.<key>). But FieldKey doesn't have TRACKNUMBER.
    // Therefore we iterate over the keys and check the Id.
    Iterator<TagField> it = tag.getFields();
    while (it.hasNext()) {
      TagField field = (TagField) it.next();

      // skip empty fields
      if (field.isEmpty()) {
        continue;
      }

      if ("TRACKNUMBER".equals(field.getId())) {
        trackNumberText = field.toString();
        break;
      }
      
    }
    
    if (trackNumberText != null) {
      int slashIndex = trackNumberText.indexOf("/");
      if (slashIndex > 0) {
        trackNumberText = trackNumberText.substring(0, slashIndex);
        trackNumber = Integer.valueOf(trackNumberText);
      }
    }
    
    return trackNumber;
  }

  /**
   * Print all the supported field key values (for debugging only)
   */
  private static void debugPrintFieldKeyValues(Tag tag) {
    for (FieldKey fieldKey: FieldKey.values()) {
      if (fieldKey == FieldKey.COVER_ART  ||
          fieldKey == FieldKey.ITUNES_GROUPING) {
        continue;
      }

      Optional<String> optionalTag = tag.getValue(fieldKey);
      if (optionalTag.isPresent()) {
        LOGGER.severe("fieldKey:" + fieldKey.name() + "=" + optionalTag.get());
      }
    }
  }
}

enum InformationType {
  FLAC_META_DATA  // Use the meta data from .flac files
}

