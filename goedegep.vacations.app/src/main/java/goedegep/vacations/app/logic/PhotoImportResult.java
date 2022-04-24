package goedegep.vacations.app.logic;

import goedegep.vacations.model.VacationElement;

public class PhotoImportResult {
  private String photoFilename;
  private PhotoImportResultType photoImportResultType;
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
