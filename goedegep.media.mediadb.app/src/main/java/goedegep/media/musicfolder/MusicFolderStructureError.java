package goedegep.media.musicfolder;

public enum MusicFolderStructureError {
  /**
   * Found a file which isn't an audio file.
   */
  FOUND_NON_AUDIO_FILE,
  
  /**
   * Found an audio file with a mixed or upper case extension.
   */
  AUDIO_FILE_WITH_MIXED_OR_UPPER_CASE_EXTENSION,
  
  /**
   * Found a Windows Media Player file.
   */
  FOUND_WINDOWS_MEDIA_PLAYER_FILE,
  
  /**
   * None of the Main Index Folders was found.
   */
  NO_INDEX_FOLDER_FOUND,
  
  /**
   * File found at a location where it isn't expected.
   */
  UNEXPECTED_FILE,
  
  /**
   * Folder found with a name or location where it isn't expected.
   */
  UNEXPECTED_FOLDER,
  
  /**
   * Wrong folder name.
   */
  WRONG_FOLDER_NAME,
  
  /**
   * Wrong format for a track name.
   */
  WRONG_TRACK_NAME_FORMAT,
  
  /**
   * Wav file found.
   */
  WAV_FILE_FOUND,
  
  /**
   * Mpeg 4 audio file found
   */
  MPEG_4_AUDIO_FILE_FOUND,
  
  /**
   * Windows Media Audio file found.
   */
  WINDOWS_MEDIA_AUDIO_FILE_FOUND,
  
  /**
   * Encoding a Wav file to flac failed.
   */
  ENCODING_WAV_TO_FLAC_FAILED
}
