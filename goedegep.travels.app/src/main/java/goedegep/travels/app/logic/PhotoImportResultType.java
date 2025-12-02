package goedegep.travels.app.logic;

public enum PhotoImportResultType {
  /**
   * A folder to be skipped is skipped. PhotoImportResult.text is the name of the skipped folder.
   */
  SKIP_FOLDER_SKIPPED,

  /**
   * A photo is skipped because it is already part of the vacation. PhotoImportResult.text is the filename of the skipped file.
   */
  EXISTING_PHOTO_SKIPPED,


  /**
   * A file is skipped because it is not a jpeg file. PhotoImportResult.text is the filename of the skipped file.
   */
  NON_JPEG_FILE_SKIPPED,

  /**
   * A photo is skipped because it doesn't have coordinates. PhotoImportResult.text is the filename of the skipped photo.
   */
  PHOTO_WITHOUT_COORDINATES_SKIPPED,
  
  /**
   * The photo is added, based on a match on a location for the day the photo was taken.
   */
  ADDED_TO_DAY_PLUS_LOCATION,
  
  /**
   * The photo is added, based on a match on a location.
   */
  ADDED_TO_LOCATION,
  
  /**
   * The photo is added, based on a match on a day.
   */
  ADDED_TO_DAY,
  
  /**
   * The photo is added at vacation level, because there was no match.
   */
  ADDED_TO_VACATION
  
}
