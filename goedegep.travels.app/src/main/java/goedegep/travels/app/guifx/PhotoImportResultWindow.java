package goedegep.travels.app.guifx;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttreeview.EObjectTreeItem;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.poi.app.LocationCategory;
import goedegep.travels.app.logic.PhotoImportResult;
import goedegep.travels.app.logic.VacationsUtils;
import goedegep.types.model.FileReference;
import goedegep.vacations.model.Day;
import goedegep.vacations.model.GPXTrack;
import goedegep.vacations.model.Location;
import goedegep.vacations.model.Picture;
import goedegep.vacations.model.VacationElement;
import goedegep.vacations.model.VacationsPackage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class PhotoImportResultWindow extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(PhotoImportResultWindow.class.getName());
  private final static String NEWLINE = System.getProperty("line.separator");
  private static final String WINDOW_TITLE = "Photos import results";
  
//  private CustomizationFx customization;
  private EObjectTreeView vacationsTreeView;
  private List<PhotoImportResult> photoImportResults;
  private ComponentFactoryFx componentFactory;

  public PhotoImportResultWindow(CustomizationFx customization, EObjectTreeView vacationsTreeView, List<PhotoImportResult> photoImportResults) {
    super(customization, WINDOW_TITLE);
    
//    this.customization = customization;
    this .vacationsTreeView = vacationsTreeView;
    this.photoImportResults = photoImportResults;
    
    componentFactory = customization.getComponentFactoryFx();
    
    createGUI();
    
    show();
  }

  private void createGUI() {
    ObservableList<PhotoImportResult> observablePhotoImportResults = FXCollections.observableList(photoImportResults);
    ListView<PhotoImportResult> listView = componentFactory.createListView(observablePhotoImportResults);
    
    listView.setCellFactory(new Callback<ListView<PhotoImportResult>, ListCell<PhotoImportResult>>() {

      @Override
      public ListCell<PhotoImportResult> call(ListView<PhotoImportResult> listView) {
        
        ListCell<PhotoImportResult> listCell = new ListCell<PhotoImportResult>() {
 
          @Override
          public void updateItem(PhotoImportResult photoImportResult, boolean empty) {
            super.updateItem(photoImportResult, empty);
            if (empty) {
              setText(null);
              setGraphic(null);
            } else {
              setGraphic(createPhotoImportResultNode(photoImportResult));
              
            }
          }
        };
        return listCell;
      }
    });
    
    ChangeListener<? super PhotoImportResult> cl = new ChangeListener<>() {

      @Override
      public void changed(ObservableValue<? extends PhotoImportResult> observable, PhotoImportResult oldValue, PhotoImportResult newValue) {
        if (newValue != null) {
          EObject vacationElement = newValue.getVacationElement();
          if (vacationElement != null) {
            vacationsTreeView.getSelectionModel().select(null);
            EObjectTreeItem treeItem = vacationsTreeView.findTreeItem(vacationElement);
            vacationsTreeView.getSelectionModel().select(treeItem);
            vacationsTreeView.scrollTo(vacationsTreeView.getSelectionModel().getSelectedIndex());
          }
        }
        
      }
      
    };
    listView.getSelectionModel().selectedItemProperty().addListener(cl);
    
    setScene(new Scene(listView, 600, 900));
  }

  private Node createPhotoImportResultNode(PhotoImportResult photoImportResult) {
    HBox hBox = componentFactory.createHBox(12.0);
    hBox.setAlignment(Pos.CENTER_LEFT);
    
    String photoFileName = photoImportResult.getPhotoFilename();
    if (photoFileName != null) {
      VBox photoBox = componentFactory.createVBox();
      Image image = new Image("file:" + photoFileName, 250, 250, true, true);
      ImageView imageView = new ImageView(image);
      photoBox.getChildren().add(imageView);
      File file = new File(photoFileName);
      Label label = componentFactory.createLabel(file.getName());
      photoBox.getChildren().add(label);
      hBox.getChildren().add(photoBox);
    }
    
    StringBuilder buf = new StringBuilder();
    VacationElement vacationElement = photoImportResult.getVacationElement();
    VacationElement newVacationElement = photoImportResult.getVacationNewElement();
    Day day = null;
    if (vacationElement instanceof Day) {
      day = (Day) vacationElement;
    } else {
      day = VacationsUtils.getAncestorOfType(vacationElement, Day.class);
    }
    
    switch(photoImportResult.getPhotoImportResultType()) {
    case ADDED_TO_DAY_PLUS_LOCATION:
      buf.append("Photo is added to an element for the day that the photo was taken.").append(NEWLINE);
      buf.append("Day: ").append(getDayText(day)).append(NEWLINE);
      buf.append("Element: ").append(getElementText(vacationElement)).append(NEWLINE);
      break;
      
    case ADDED_TO_LOCATION:
      buf.append("Photo is added to an element.").append(NEWLINE);
      buf.append("Element: ").append(getElementText(vacationElement)).append(NEWLINE);
      break;
      
    case ADDED_TO_DAY:
      buf.append("Photo is added to the day that the photo was taken.").append(NEWLINE);
      buf.append("Day: ").append(getDayText(day)).append(NEWLINE);
      buf.append("Element: ").append(getElementText(vacationElement)).append(NEWLINE);
      break;
      
    case ADDED_TO_VACATION:
      buf.append("Photo is added as top level element of the vacation.").append(NEWLINE);
      break;
            
    case EXISTING_PHOTO_SKIPPED:
      buf.append("Photo is not added because it is already part of the vacation.").append(NEWLINE);
      buf.append("Element: ").append(getElementText(vacationElement)).append(NEWLINE);
      if (newVacationElement != null) {
        buf.append("Warning: it would now be added to element: ").append(getElementText(newVacationElement)).append(NEWLINE);
      }
      break;
      
    case NON_JPEG_FILE_SKIPPED:
      buf.append("This file was skipped because it's not a jpeg file.");
      break;
      
    case PHOTO_WITHOUT_COORDINATES_SKIPPED:
      buf.append("This photo was skipped because it doesn't have coordinates.");
      break;
      
    case SKIP_FOLDER_SKIPPED:
      buf.append("This folder was skipped because it is listed as a folder to skip.");
      break;
      
    default:
      throw new RuntimeException("Unknown PhotoImportResultType: " + photoImportResult.getPhotoImportResultType());
    }
    Label label = componentFactory.createLabel(buf.toString());
    hBox.getChildren().add(label);    
    
    return hBox;
  }
  
  private String getElementText(EObject eObject) {
    StringBuilder buf = new StringBuilder();
    
    if (eObject instanceof VacationElement vacationElement) {
      switch(vacationElement.eClass().getClassifierID()) {
      case VacationsPackage.DAY:
        // A day has no location, so the photo can't be added here, so no text needed.
        break;

      case VacationsPackage.LOCATION:
        Location location = (Location) vacationElement;
        buf.append(LocationCategory.getDisplayName(location.getLocationCategory())).append(" - ");
        if (location.getLocationCategory().equals(LocationCategory.CITY)) {
          buf.append(location.getCity());
        } else {
          buf.append(location.getName());
        }
        break;

      case VacationsPackage.TEXT:
        // A text has no location, so the photo can't be added here, so no text needed.
        break;

      case VacationsPackage.PICTURE:
        Picture picture = (Picture) vacationElement;
        FileReference fileReference = picture.getPictureReference();
        buf.append("Photo: ").append(fileReference.getFile());
        break;

      case VacationsPackage.GPX_TRACK:
        GPXTrack gpxTrack = (GPXTrack) vacationElement;
        FileReference gpxFileReference = gpxTrack.getTrackReference();
        buf.append("GPX track: ").append(gpxFileReference.getFile());
        break;
      }
    } else {
      throw new RuntimeException("Type not supported: " + eObject);  
    }
    
    return buf.toString();
  }

  private String getDayText(Day day) {
    return day.getTitle();
  }
}
