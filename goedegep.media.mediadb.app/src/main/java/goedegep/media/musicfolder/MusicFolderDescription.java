package goedegep.media.musicfolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class provides a description of the top level of the Music Folder.
 */
public class MusicFolderDescription {
  private static final String NEWLINE = System.getProperty("line.separator");

  /**
   * Name of the folder for artists whose name starts with a number.
   */
  public static final String NUMERIC_NAME_INDEX_FOLDER = "0-9";
  
  /**
   * Main Index folder names.
   */
  private static final String[] MAIN_INDEX_FOLDERS = {
      NUMERIC_NAME_INDEX_FOLDER, "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
      "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

  /**
   * Specification of the Track Folders (as array).
   */
  private static final ItemDescription[] TRACK_FOLDER_DESCRIPTIONS = {
      new ItemDescription("zTracks Easy Listening", "Easy listening tracks."),
      new ItemDescription("zTracks Franstalig", "French tracks."),
      new ItemDescription("zTracks Klassiek", "Classical music."),
      new ItemDescription("zTracks Nederlandstalig", "Dutch tracks."),
      new ItemDescription("zTracks Pop", "Pop songs."),
      new ItemDescription("zTracks Punk", "Punk music."),
      new ItemDescription("zTracks Rock", "Rock music.")
  };
  
  /**
   * Specification of the Special Music Folders (as array).
   */
  private static final ItemDescription[] SPECIAL_MUSIC_FOLDERS_DESCRIPTIONS = {
      new ItemDescription("zzCheck", "Music that needs further investigation."),
      new ItemDescription("zzEigen Opnamen", "My own recordings."),
      new ItemDescription("zzGuitar Book", "Tracks from the book 'Guitar Book'."),
      new ItemDescription("zzKaraoke", "Karaoke video files."),
      new ItemDescription("zzMuziek Spelen Nummers", "All files related to my own music playing."),
      new ItemDescription("zzSamples", "Sample songs from artists in the Artist Information 'database'."),
      new ItemDescription("zzToDo", "Music still to be decided about'."),
      new ItemDescription("zzTracks Film Backing Tracks", "Tracks that can be used as backing track on my own movies.")
  };

  /**
   * Specification of folders to be ignored.
   */
  private static final ItemDescription[] FOLDERS_TO_BE_IGNORED_DESCRIPTIONS = {
      new ItemDescription(".SynologyWorkingDirectory", "Owned by Synology Cloud Station Drive, to synchronize with the NAS")
  };

  /**
   * Specification of files to be ignored.
   */
  private static final ItemDescription[] FILES_TO_BE_IGNORED_DESCRIPTIONS = {
      new ItemDescription("desktop.ini", "A Microsoft Windows file")
  };

  /**
   * A textual description of the Music Folder structure (in MarkDown format).
   */
  private static String musicFolderDescription;
  
  
  /**
   * The list of Main Index folder names.
   */
  private static List<String> mainIndexFolders = Arrays.asList(MAIN_INDEX_FOLDERS);
  
  /**
   * The list of Track Folder names.
   */
  private static List<String> trackFolders = new ArrayList<>();
  
  /**
   * Track Folder descriptions (as list)
   */
  private static List<ItemDescription> trackFolderDescriptions = Arrays.asList(TRACK_FOLDER_DESCRIPTIONS);
  
  /**
   * Name of the Soundtracks folder.
   */
  private static String SOUNDTRACKS_FOLDER = "zSoundtracks";
  
  /**
   * Description of the Soundtracks folder.
   */
  private static ItemDescription soundtrackFolderDescription = new ItemDescription(SOUNDTRACKS_FOLDER, "Movie sound tracks.");
  
  /**
   * The list of Special Music Folders.
   */
  private static List<String> specialMusicFolders = new ArrayList<>();
  
  /**
   * Music Folder descriptions (as list).
   */
  private static List<ItemDescription> specialMusicFolderDescriptions = Arrays.asList(SPECIAL_MUSIC_FOLDERS_DESCRIPTIONS);
  
  /**
   * The list of folders to be ignored.
   */
  private static List<String> foldersToBeIgnored = new ArrayList<>();
  
  /**
   * Descriptions of the folders to be ignored.
   */
  private static List<ItemDescription> foldersToBeIgnoredDescriptions = Arrays.asList(FOLDERS_TO_BE_IGNORED_DESCRIPTIONS);
  
  /**
   * The list of files to be ignored.
   */
  private static List<String> filesToBeIgnored = new ArrayList<>();
  
  /**
   * Descriptions of the files to be ignored.
   */
  private static List<ItemDescription> filesToBeIgnoredDescriptions = Arrays.asList(FILES_TO_BE_IGNORED_DESCRIPTIONS);
  
  static {    
    for (ItemDescription folderInfo: TRACK_FOLDER_DESCRIPTIONS) {
      trackFolders.add(folderInfo.getName());
    }
    
    for (ItemDescription folderInfo: SPECIAL_MUSIC_FOLDERS_DESCRIPTIONS) {
      specialMusicFolders.add(folderInfo.getName());
    }
    
    for (ItemDescription folderInfo: FOLDERS_TO_BE_IGNORED_DESCRIPTIONS) {
      foldersToBeIgnored.add(folderInfo.getName());
    }
    
    for (ItemDescription fileInfo: FILES_TO_BE_IGNORED_DESCRIPTIONS) {
      filesToBeIgnored.add(fileInfo.getName());
    }
    
    StringBuilder buf = new StringBuilder();
    buf.append("A Music Folder contains the following:").append(NEWLINE);
    buf.append("* **Main Index folders**   ").append(NEWLINE);
    buf.append("These folders are named '0..9' for artists whose name start with a number, and 'A' .. 'Z' each for the artists whose name start with that character.").append(NEWLINE);
    buf.append("Each **Main Index Folder** contains folders which have the name of an artist. These folders are referred to as **Artist Folders**.").append(NEWLINE);
    buf.append("  * **Artist Folder**  ").append(NEWLINE);
    buf.append("An **Artist Folder** contains Albums of that Artist.").append(NEWLINE);
    buf.append("    * **Album Folder**   ").append(NEWLINE);
    buf.append("An **Album Folder** contains the Tracks of an album.   ").append(NEWLINE);
    buf.append("The naming convention for an **Album Folder** is: \\<year-plus-optional-index\\> - \\<artist-name\\> - \\<album-title\\> - \\<cd-nr\\>").append(NEWLINE);

    buf.append("* **Track Folders**   ").append(NEWLINE);
    buf.append("These folders each contain tracks of a specific style.").append(NEWLINE);
    buf.append("The names of the track folders start with a 'z', so that in a folder listing they appear after the **Main Index Folders**.").append(NEWLINE);
    for (ItemDescription folderDescription: trackFolderDescriptions) {
      buf.append("    * **").append(folderDescription.getName()).append("**   ").append(NEWLINE);
      buf.append(folderDescription.getDescription()).append(NEWLINE);
    }

    buf.append("* **Special Music Folders**   ").append(NEWLINE);
    buf.append("These folders each contain each contain music for dedicated purposes.").append(NEWLINE);
    buf.append("The names of these folders start with 'zz', so that in a folder listing they appear after the **Track Folders**.").append(NEWLINE);
    for (ItemDescription folderDescription: specialMusicFolderDescriptions) {
      buf.append("    * **").append(folderDescription.getName()).append("**   ").append(NEWLINE);
      buf.append(folderDescription.getDescription()).append(NEWLINE);
    }
    
    buf.append("* Known but irrelevant folders   ").append(NEWLINE);
    buf.append("Some folders are just there, while they are not part of the structure. Usually they cannot be removed.").append(NEWLINE);
    for (ItemDescription folderDescription: foldersToBeIgnoredDescriptions) {
      buf.append("    * ").append(folderDescription.getName()).append("   ").append(NEWLINE);
      buf.append(folderDescription.getDescription()).append(NEWLINE);
    }
    buf.append("* Known but irrelevant files   ").append(NEWLINE);
    buf.append("Some files are just there, while they are not part of the structure. Usually they cannot be removed.").append(NEWLINE);
    for (ItemDescription fileDescription: filesToBeIgnoredDescriptions) {
      buf.append("    * ").append(fileDescription.getName()).append("   ").append(NEWLINE);
      buf.append(fileDescription.getDescription()).append(NEWLINE);
    }
    
    musicFolderDescription = buf.toString();
  }

  /**
   * Get a description of the Music Folder (in MarkDown format).
   * 
   * @return a description of the Music Folder (in MarkDown format).
   */
  public static String getMusicFolderDescription() {
    return musicFolderDescription;
  }
  
  /**
   * Get the list of Main Index folder names.
   * 
   * @return the list of Main Index folder names.
   */
  public static List<String> getMainIndexFolders() {
    return mainIndexFolders;
  }

  /**
   * Get the list of Track Folder names.
   * 
   * @return the list of Track Folder names.
   */
  public static List<String> getTrackFolders() {
    return trackFolders;
  }

  /**
   * Get the list of Track Folder descriptions.
   * 
   * @return the list of Track Folder descriptions.
   */
  public static List<ItemDescription> getTrackFolderDescriptions() {
    return trackFolderDescriptions;
  }

  /**
   * Get the name of the Soundtrack folder.
   * 
   * @return the name of the Soundtrack folder.
   */
  public static String getSoundtracksFolder() {
    return SOUNDTRACKS_FOLDER;
  }

  /**
   * Get the Soundtrack folder description.
   * 
   * @return the Soundtrack folder description.
   */
  public static ItemDescription getSoundtrackFolderDescription() {
    return soundtrackFolderDescription;
  }

  /**
   * Get the list of Special Music Folder names.
   * 
   * @return the list of Special Music Folder names.
   */
  public static List<String> getSpecialMusicFolders() {
    return specialMusicFolders;
  }

  /**
   * Get the list of Special Music Folder descriptions.
   * 
   * @return the list of Special Music Folder descriptions.
   */
  public static List<ItemDescription> getSpecialMusicFolderDescriptions() {
    return specialMusicFolderDescriptions;
  }

  /**
   * Get the list of the names of the folders to be ignored.
   * 
   * @return the list of the names of the folders to be ignored.
   */
  public static List<String> getFoldersToBeIgnored() {
    return foldersToBeIgnored;
  }

  /**
   * Get the list of descriptions of the folders to be ignored.
   * 
   * @return the list of descriptions of the folders to be ignored.
   */
  public static List<ItemDescription> getFoldersToBeIgnoredDescriptioons() {
    return foldersToBeIgnoredDescriptions;
  }

  /**
   * Get the list of names of the files to be ignored.
   * 
   * @return the list of names of the files to be ignored.
   */
  public static List<String> getFilesToBeIgnored() {
    return filesToBeIgnored;
  }

  /**
   * Get the list of descriptions of the files to be ignored.
   * @return the list of descriptions of the files to be ignored.
   */
  public static List<ItemDescription> getFilesToBeIgnoredDescriptions() {
    return filesToBeIgnoredDescriptions;
  }
  
}
