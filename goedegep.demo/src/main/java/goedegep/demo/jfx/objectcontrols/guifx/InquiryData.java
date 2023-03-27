package goedegep.demo.jfx.objectcontrols.guifx;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import goedegep.util.datetime.FlexDate;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InquiryData {
  private String name;
  private boolean happy;
  private City birthPlace;
  private Integer age;
  private PgCurrency priceLastHoliday;
  private TravelerType travelerType;
  private FixedPointValue lastTravelRating;
  private LocalDate lastTravelDate;
  private File travelReportFilename;
  private FlexDate nextTravelDate;
  private File picturesFolder;
  private File imageFile;
  private String notes;
  private String details;
  
  private static ObservableList<InquiryData> inquiryDataList = FXCollections.observableArrayList();
  
  static {
    InquiryData inquiryData = new InquiryData();
    inquiryData.name = "Paul";
    inquiryDataList.add(inquiryData);
    
    inquiryData = new InquiryData();
    inquiryData.name = "Joan";
    inquiryDataList.add(inquiryData);
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public boolean isHappy() {
    return happy;
  }
  
  public void setHappy(boolean happy) {
    this.happy = happy;
  }
  
  public City getBirthPlace() {
    return birthPlace;
  }
  
  public void setBirthPlace(City birthPlace) {
    this.birthPlace = birthPlace;
  }
  
  public Integer getAge() {
    return age;
  }
  
  public void setAge(Integer age) {
    this.age = age;
  }
  
  public PgCurrency getPriceLastHoliday() {
    return priceLastHoliday;
  }

  public void setPriceLastHoliday(PgCurrency priceLastHoliday) {
    this.priceLastHoliday = priceLastHoliday;
  }

  public TravelerType getTravelerType() {
    return travelerType;
  }
  
  public void setTravelerType(TravelerType travelerType) {
    this.travelerType = travelerType;
  }
  
  public FixedPointValue getLastTravelRating() {
    return lastTravelRating;
  }
  
  public void setLastTravelRating(FixedPointValue lastTravelRating) {
    this.lastTravelRating = lastTravelRating;
  }
  
  public LocalDate getLastTravelDate() {
    return lastTravelDate;
  }
  
  public void setLastTravelDate(LocalDate lastTravelDate) {
    this.lastTravelDate = lastTravelDate;
  }
  
  public File getTravelReportFilename() {
    return travelReportFilename;
  }
  
  public void setTravelReportFilename(File travelReportFilename) {
    this.travelReportFilename = travelReportFilename;
  }
  
  public FlexDate getNextTravelDate() {
    return nextTravelDate;
  }
  
  public void setNextTravelDate(FlexDate nextTravelDate) {
    this.nextTravelDate = nextTravelDate;
  }
  
  public File getPicturesFolder() {
    return picturesFolder;
  }
  
  public void setPicturesFolder(File picturesFolder) {
    this.picturesFolder = picturesFolder;
  }
  
  public File getImageFile() {
    return imageFile;
  }
  
  public void setImageFile(File imageFile) {
    this.imageFile = imageFile;
  }
  
  public String getNotes() {
    return notes;
  }
  
  public void setNotes(String notes) {
    this.notes = notes;
  }
  
  public String getDetails() {
    return details;
  }
  
  public void setDetails(String details) {
    this.details = details;
  }

  public static ObservableList<InquiryData> getInquiryDataList() {
    return inquiryDataList;
  }
}
