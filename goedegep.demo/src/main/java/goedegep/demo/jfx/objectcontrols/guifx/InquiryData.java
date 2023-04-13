package goedegep.demo.jfx.objectcontrols.guifx;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import goedegep.emfsample.model.Gender;
import goedegep.util.datetime.FlexDate;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InquiryData implements Observable {
  private String name;
  private boolean happy;
  private City birthPlace;
  private Gender gender;
  private Integer age;
  private PgCurrency priceLastHoliday;
  private TravelerType travelerType;
  private FixedPointValue lastTravelRating;
  private LocalDate lastTravelDate;
  private File travelReportFile;
  private FlexDate nextTravelDate;
  private File picturesFolder;
  private File imageFile;
  private String notes;
  private String details;
  
  private static ObservableList<InquiryData> inquiryDataList;
  private List<InvalidationListener> invalidationListeners = new ArrayList<>();
  
  static {
    inquiryDataList = FXCollections.observableArrayList(InquiryData::extractor);
    
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
    notifyListeners();
  }
  
  public boolean isHappy() {
    return happy;
  }
  
  public void setHappy(boolean happy) {
    this.happy = happy;
    notifyListeners();
  }
  
  public City getBirthPlace() {
    return birthPlace;
  }
  
  public void setBirthPlace(City birthPlace) {
    this.birthPlace = birthPlace;
    notifyListeners();
  }
  
  public Gender getGender() {
    return gender;
  }
  
  public void setGender(Gender gender) {
    this.gender = gender;
    notifyListeners();
  }
  
  public Integer getAge() {
    return age;
  }
  
  public void setAge(Integer age) {
    this.age = age;
    notifyListeners();
  }
  
  public PgCurrency getPriceLastHoliday() {
    return priceLastHoliday;
  }

  public void setPriceLastHoliday(PgCurrency priceLastHoliday) {
    this.priceLastHoliday = priceLastHoliday;
    notifyListeners();
  }

  public TravelerType getTravelerType() {
    return travelerType;
  }
  
  public void setTravelerType(TravelerType travelerType) {
    this.travelerType = travelerType;
    notifyListeners();
  }
  
  public FixedPointValue getLastTravelRating() {
    return lastTravelRating;
  }
  
  public void setLastTravelRating(FixedPointValue lastTravelRating) {
    this.lastTravelRating = lastTravelRating;
    notifyListeners();
  }
  
  public LocalDate getLastTravelDate() {
    return lastTravelDate;
  }
  
  public void setLastTravelDate(LocalDate lastTravelDate) {
    this.lastTravelDate = lastTravelDate;
    notifyListeners();
  }
  
  public File getTravelReportFile() {
    return travelReportFile;
  }
  
  public void setTravelReportFile(File travelReportFilename) {
    this.travelReportFile = travelReportFilename;
    notifyListeners();
  }
  
  public FlexDate getNextTravelDate() {
    return nextTravelDate;
  }
  
  public void setNextTravelDate(FlexDate nextTravelDate) {
    this.nextTravelDate = nextTravelDate;
    notifyListeners();
  }
  
  public File getPicturesFolder() {
    return picturesFolder;
  }
  
  public void setPicturesFolder(File picturesFolder) {
    this.picturesFolder = picturesFolder;
    notifyListeners();
  }
  
  public File getImageFile() {
    return imageFile;
  }
  
  public void setImageFile(File imageFile) {
    this.imageFile = imageFile;
    notifyListeners();
  }
  
  public String getNotes() {
    return notes;
  }
  
  public void setNotes(String notes) {
    this.notes = notes;
    notifyListeners();
  }
  
  public String getDetails() {
    return details;
  }
  
  public void setDetails(String details) {
    this.details = details;
    notifyListeners();
  }

  public static ObservableList<InquiryData> getInquiryDataList() {
    return inquiryDataList;
  }
  
  public static Observable[] extractor(InquiryData inquiryData) {
    return new Observable[] {inquiryData};
  }

  @Override
  public void addListener(InvalidationListener listener) {
    invalidationListeners.add(listener);    
  }

  @Override
  public void removeListener(InvalidationListener listener) {
    invalidationListeners.remove(listener);
  }
  
  private void notifyListeners() {
    for (InvalidationListener listener: invalidationListeners) {
      listener.invalidated(this);
    }
  }
}
