package goedegep.vacations.app.logic;

import goedegep.vacations.model.VacationElement;

/**
 * Represents the result of a photo import operation.
 * Contains details about the photo, the result type, associated vacation elements, and any relevant messages.
 * 
 */
public class PhotoImportResult {
  /**
   * The filename of the photo.
   */
  private String photoFilename;
  
  /**
   * The type of result from the photo import operation.
   */
  private PhotoImportResultType photoImportResultType;
  
  /**
   * The vacation element associated with the photo, if applicable.
   */
  private VacationElement vacationElement;
  private String text;
  private VacationElement newVacationElement;
  
  public PhotoImportResult(String photoFilename, PhotoImportResultType photoImportResultType, String text) {
    this.photoFilename = photoFilename;
    this.photoImportResultType = photoImportResultType;
    this.text = text;
  }
  
  public PhotoImportResult(String photoFilename, PhotoImportResultType photoImportResultType, VacationElement vacationElement, String text) {
    this.photoFilename = photoFilename;
    this.photoImportResultType = photoImportResultType;
    this.vacationElement = vacationElement;
    this.text = text;
  }
  
  public PhotoImportResult(String photoFilename, PhotoImportResultType photoImportResultType, VacationElement vacationElement, String text, VacationElement newVacationElement) {
    this.photoFilename = photoFilename;
    this.photoImportResultType = photoImportResultType;
    this.vacationElement = vacationElement;
    this.text = text;
    this.newVacationElement = newVacationElement;
  }

  public String getPhotoFilename() {
    return photoFilename;
  }

  public PhotoImportResultType getPhotoImportResultType() {
    return photoImportResultType;
  }

  public VacationElement getVacationElement() {
    return vacationElement;
  }

  public String getText() {
    return text;
  }

  public VacationElement getVacationNewElement() {
    return newVacationElement;
  }
  
}
