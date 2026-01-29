package goedegep.travels.gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Logger;

import org.apache.commons.imaging.ImageReadException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.gluonhq.maps.MapPoint;

import goedegep.gpxeditor.svc.GPXService;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttreeview.EObjectTreeItem;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassListReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemForObject;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.jfx.eobjecttreeview.NodeOperationDescriptorCustom;
import goedegep.jfx.eobjecttreeview.NodeOperationDescriptorDelete;
import goedegep.jfx.eobjecttreeview.NodeOperationDescriptorNew;
import goedegep.jfx.eobjecttreeview.NodeOperationDescriptorNewAfter;
import goedegep.jfx.eobjecttreeview.NodeOperationDescriptorNewBefore;
import goedegep.jfx.eobjecttreeview.NodeOperationDescriptorOpen;
import goedegep.jfx.eobjecttreeview.PresentationType;
import goedegep.poi.app.LocationCategory;
import goedegep.resources.ImageResource;
import goedegep.resources.ImageSize;
import goedegep.travels.logic.EnumStringConverterForLocationCategory;
import goedegep.travels.model.BoundingBox;
import goedegep.travels.model.Day;
import goedegep.travels.model.DayTrip;
import goedegep.travels.model.Document;
import goedegep.travels.model.GPXTrack;
import goedegep.travels.model.Location;
import goedegep.travels.model.MapImage;
import goedegep.travels.model.Picture;
import goedegep.travels.model.Text;
import goedegep.travels.model.Vacation;
import goedegep.travels.svc.TravelsService;
import goedegep.travels.model.TravelCategory;
import goedegep.travels.model.TravelsFactory;
import goedegep.travels.model.TravelsPackage;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesFactory;
import goedegep.types.model.TypesPackage;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.emf.EmfUtil;
import goedegep.util.file.FileUtils;
import goedegep.util.img.PhotoFileMetaDataHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;

/**
 * This class provides a <code>TreeView</code> for a <code>Vacations</code> data structure.
 */
public class TravelsTreeViewCreator {
  private static final Logger LOGGER = Logger.getLogger(TravelsTreeViewCreator.class.getName());
  private static final SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
  private static final FlexDateFormat FDF = new FlexDateFormat();
  private static final TravelsPackage TRAVELS_PACKAGE = TravelsPackage.eINSTANCE;
  private final TypesPackage typesPackage = TypesPackage.eINSTANCE;
  
  /**
   * The GUI customization (mandatory)
   */
  private CustomizationFx customization = null;
  
  /**
   * A new {@code EObject} initialization function (optional).
   */
  private BiConsumer<EObject, EObjectTreeItem> newEObjectInitializationFunction = null;
  
  /**
   * A {@code Predicate} to check whether a menu shall be enabled or not.
   */
  private Predicate<EObjectTreeItem> isMenuToBeEnabledPredicate;
  
  /**
   * A reference to the {@code TravelMapView}
   */
  private TravelMapView travelMapView;
  
  /**
   * Function to update a map image file.
   */
  private Consumer<EObjectTreeItem> updateMapImageFileFunction;

  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   */
  public TravelsTreeViewCreator(CustomizationFx customization) {
    this.customization = customization;
  }
  
  /**
   * Set the new {@code EObject} initialization function.
   * <p>
   * This method is called after the creation of a {@code EObject}.
   * It is used to perform further initialization.
   * 
   * @param newEObjectInitializationFunction the new {@code EObject} initialization function.
   * @return this
   */
  public TravelsTreeViewCreator setNewEObjectInitializationFunction(BiConsumer<EObject, EObjectTreeItem> newEObjectInitializationFunction) {
    this.newEObjectInitializationFunction = newEObjectInitializationFunction;
    
    return this;
  }
  
  /**
   * Set the {@code Predicate} to check whether a menu shall be enabled or not.
   * 
   * @param isMenuToBeEnabledPredicate the {@code Predicate} to check whether a menu shall be enabled or not.
   * @return this
   */
  public TravelsTreeViewCreator setMenuToBeEnabledPredicate(Predicate<EObjectTreeItem> isMenuToBeEnabledPredicate) {
    this.isMenuToBeEnabledPredicate = isMenuToBeEnabledPredicate;
    
    return this;
  }
  
  /**
   * Set the {@code TravelMapView}.
   * 
   * @param travelMapView the {@code TravelMapView}.
   * @return this
   */
  public TravelsTreeViewCreator setTravelMapView(TravelMapView travelMapView) {
    this.travelMapView = travelMapView;
    
    return this;
  }
  
  /**
   * Set the function to update a map image file.
   * 
   * @param updateMapImageFileFunction the function to update a map image file.
   * @return this
   */
  public TravelsTreeViewCreator setUpdateMapImageFileFunction(Consumer<EObjectTreeItem> updateMapImageFileFunction) {
    this.updateMapImageFileFunction = updateMapImageFileFunction;
    
    return this;
  }
   
  /**
   * Create the {@code EObjectTreeView} for {@code Vacations}.
   * 
   * @return the {@code EObjectTreeView} for {@code Vacations}.
   */
  public EObjectTreeView createVacationsTreeView() {
    EObjectTreeView eObjectTreeView = new EObjectTreeView()
        .setCustomization(customization)
        .addEClassDescriptor(TRAVELS_PACKAGE.getTravels(), createDescriptorForTravels())
        .addEClassDescriptor(TRAVELS_PACKAGE.getVacation(), createDescriptorForVacation())
        .addEClassDescriptor(TRAVELS_PACKAGE.getDayTrip(), createDescriptorForDayTrip())
        .addEClassDescriptor(TRAVELS_PACKAGE.getTravelCategory(), createDescriptorForTravelCategory())
        .addEClassDescriptor(TRAVELS_PACKAGE.getDay(), createDescriptorForDay())
        .addEClassDescriptor(TRAVELS_PACKAGE.getDocument(), createDescriptorForDocument())
        .addEClassDescriptor(TRAVELS_PACKAGE.getText(), createDescriptorForText())
        .addEClassDescriptor(TRAVELS_PACKAGE.getLocation(), createDescriptorForLocation())
        .addEClassDescriptor(TRAVELS_PACKAGE.getGPXTrack(), createDescriptorForGPXTrack())
        .addEClassDescriptor(TRAVELS_PACKAGE.getPicture(), createDescriptorForPicture())
        .addEClassDescriptor(TRAVELS_PACKAGE.getMapImage(), createDescriptorForMapImage())
        .addEClassDescriptor(TRAVELS_PACKAGE.getBoundingBox(), createDescriptorForBoundingBox())
        .addEClassDescriptor(typesPackage.getFileReference(), createDescriptorForFileReference())
        .addEnumStringConverter(TRAVELS_PACKAGE.getELocationCategory().getInstanceClass(), EnumStringConverterForLocationCategory.getInstance())
        .setIsDropPossibleFunction(this::isDropPossible)
        .setHandleDropFunction(this::handleDrop);
//        .setNewEObjectInitializationFunction(newEObjectInitializationFunction);

    return eObjectTreeView;
  }
    
  /**
   * Check whether a tree item is a list of (supertypes of) Locations.
   * 
   * @param treeItem the tree item to be checked
   * @return true if <code>treeItem</code> is a list of (supertypes of) Locations, false otherwise.
   */
  public static boolean treeItemIsLocationsList(EObjectTreeItem treeItem) {
    if (treeItem == null) {
      return false;
    }
    
    EClass locationEClass = TravelsPackage.eINSTANCE.getLocation();

    EStructuralFeature eStructuralFeature = treeItem.getEStructuralFeature();
    if (eStructuralFeature instanceof EReference) {
      EReference eReference = (EReference) eStructuralFeature;
      EClass listTypeEClass = eReference.getEReferenceType();

      return EmfUtil.isInstanceof(locationEClass, listTypeEClass)  &&  eReference.isMany();
    } else {
      return false;
    }
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.Vacations.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Vacations descriptor is to be added.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForTravels() {
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor;
        
    // Travels (root node)
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(_ -> "Travel information")
        .setStrongText(true)
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("Add a Travel Category", null, null))
        .setNodeIconFunction(_ -> TravelImageResource.TRAVELS.getIcon(ImageSize.SIZE_1));
    
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;

    // Travels.tips
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getTravels_Tips())
        .setLabelText("Tips")
        .setPresentationType(PresentationType.MULTI_LINE_TEXT);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // Travels.home
    EObjectTreeItemClassReferenceDescriptor homeDescriptor = new EObjectTreeItemClassReferenceDescriptor(TRAVELS_PACKAGE.getTravels_Home())
        .setNodeTextFunction(_ -> "Home location")
        .setStrongText(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("Create home location", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorDelete("Delete home location", null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(homeDescriptor);
    
    // Travels.vacations
    eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(TRAVELS_PACKAGE.getTravels_Vacations())
        .setLabelText("Vacations")
        .setStrongText(true)
        .setNodeIconFunction(_ -> TravelImageResource.VACATIONS.getIcon(ImageSize.SIZE_1))
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("New vacation", null, null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    // Travels.dayTrips
    eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(TRAVELS_PACKAGE.getTravels_DayTrips())
        .setLabelText("Day trips")
        .setStrongText(true)
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("New day trip", null, null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    // Travels.travelCategories
    eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(TRAVELS_PACKAGE.getTravels_TravelCategories())
        .setLabelText("Other travel categories")
        .setStrongText(true)
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("New travel category", null, null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.Vacation.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForVacation() {
    TypesPackage typesPackage = TypesPackage.eINSTANCE;
        
    // Vacation
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> {
          if (eObject instanceof Vacation vacation) {
            return vacation.getId();
          } else {
            LOGGER.severe("Wrong type, Vacation expected, but is: " + eObject.getClass().getSimpleName());
            return "Not a vacation";
          }
        })
        .setStrongText(true)
        .setNodeIconFunction(_ -> TravelImageResource.VACATION.getIcon(ImageSize.SIZE_1))
        .setExpandChildrenOnExpand(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("New ...", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewBefore("New vacation before this one", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewAfter("New vacation after this one", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorDelete("Delete vacation", null));
    
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor;
    
    // Vacation.title
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getTravel_Title())
        .setLabelText("Title");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Vacation.date (startDate)
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(typesPackage.getEvent_Date())
        .setLabelText("From")
        .setFormat(FDF);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // Vacation.endDate
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getVacation_EndDate())
        .setLabelText("Until")
        .setFormat(FDF);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // Vacation.notes
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(typesPackage.getEvent_Notes())
        .setLabelText("General")
        .setPresentationType(PresentationType.MULTI_LINE_TEXT);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Vacation.documents
    eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(TRAVELS_PACKAGE.getTravel_Documents())
        .setLabelText("Documents")
        .setNodeIconFunction(_ -> ImageResource.DOCUMENTS.getImage(ImageSize.SIZE_1))
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("New document", null, null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    // Vacation.pictures
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getTravel_Pictures())
        .setLabelText("Photos")
        .setPresentationType(PresentationType.FOLDER)
        .addNodeOperationDescriptor(new NodeOperationDescriptorOpen("Open photos folder", null, null))
        .setInitialDirectoryNameFunction(TravelsWindow::getInitialPhotoFolderName);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // Vacation.elements
    eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(TRAVELS_PACKAGE.getTravel_Elements())
        .setLabelText("Elements")
        .setNodeIconFunction(_ -> EObjectTreeView.getListIcon())
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("New element", null, newEObjectInitializationFunction));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.DayTrip.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForDayTrip() {
    TypesPackage typesPackage = TypesPackage.eINSTANCE;
        
    // DayTrip
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> {
          DayTrip dayTrip = (DayTrip) eObject;
          return dayTrip.getId();
        })
        .setNodeIconFunction(_ -> TravelImageResource.DAY_TRIP.getIcon(ImageSize.SIZE_0))
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("New ...", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewBefore("New day trip before this one", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewAfter("New day trip after this one", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorDelete("Delete day trip", null));
    
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;
    
    // DayTrip.title
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getTravel_Title())
        .setLabelText("Title");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // DayTrip.date
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(typesPackage.getEvent_Date())
        .setLabelText("Date")
        .setFormat(FDF);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // Vacation.elements
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(TRAVELS_PACKAGE.getTravel_Elements())
        .setLabelText("Elements")
        .setNodeIconFunction(_ -> EObjectTreeView.getListIcon())
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("New element", null, newEObjectInitializationFunction));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.TravelCategory.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForTravelCategory() {
    // TravelCategory
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> ((TravelCategory) eObject).getTravelCategoryName())
        .setNodeIconFunction(_ -> TravelImageResource.TRAVEL_CATEGORY.getIcon(ImageSize.SIZE_0))
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewBefore("New Travel Category before this one ...", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewAfter("New  Travel Category after this one ...", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorDelete("Delete  Travel Category", null));
    
    // TravelCategory.travelCategoryName
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getTravelCategory_TravelCategoryName())
        .setLabelText("Category name");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // TravelCategory.children
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(TRAVELS_PACKAGE.getTravelCategory_Travels())
        .setLabelText("Travels")
        .setNodeIconFunction(_ -> EObjectTreeView.getListIcon())
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("New travel", null, newEObjectInitializationFunction));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);

    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.VacationElementDay.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForDay() {
    // Day (extends VacationElement)
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> {
          Day day = (Day) eObject;
          StringBuilder buf = new StringBuilder();
          buf.append("Day: ");
          Integer dayNr = day.getDayNr();
          if (dayNr != null) {
            buf.append(dayNr.toString());
          }
          Date date = day.getDate();
          if (date != null) {
            buf.append(" - ");
            buf.append(DF.format(date));
          }
          if (day.isSetTitle()) {
            buf.append(" - ");
            buf.append(day.getTitle());
          }
          return buf.toString();
        })
        .setNodeIconFunction(_ -> TravelImageResource.DAY.getIcon(ImageSize.SIZE_0))
        .setExpandChildrenOnExpand(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewBefore("New element before this one ...", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewAfter("New element after this one ...", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorDelete("Delete element", null));
    
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;
    
    // Day.title
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getDay_Title())
        .setLabelText("Title");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Day.days
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getDay_Days())
        .setLabelText("Days");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // Day.children
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(TRAVELS_PACKAGE.getVacationElement_Children())
        .setLabelText("Elements")
        .setNodeIconFunction(_ -> EObjectTreeView.getListIcon())
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("New element", null, newEObjectInitializationFunction));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.VacationElementText.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForText() {
    // VacationElementText (extends VacationElement)
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> {
          Text text = (Text) eObject;
          if (text.isSetText()) {
            int maxTextLength = 80;
            String displayText = text.getText();
            if (displayText.length() > maxTextLength) {
              displayText = displayText.substring(0, maxTextLength - 4) + "...";
            }
            return displayText;
          } else {
            return "Text";
          }
        })
        .setNodeIconFunction(_ -> TravelImageResource.TEXT.getIcon(ImageSize.SIZE_0))
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewBefore("New element before this one ...", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewAfter("New element after this one ...", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorDelete("Delete element", null));
    
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;
    
    // VacationElementText.text
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getText_Text())
        .setLabelText("Text")
        .setPresentationType(PresentationType.MULTI_LINE_TEXT);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // VacationElementText.children
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(TRAVELS_PACKAGE.getVacationElement_Children())
        .setLabelText("Elements")
        .setNodeIconFunction(_ -> EObjectTreeView.getListIcon())
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("New element", null, newEObjectInitializationFunction));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }
  
  /**
   * Create the descriptor for the EClass goedegep.model.vacations.Location.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForLocation() {
    // VacationElementLocation
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> {
          StringBuilder buf = new StringBuilder();
          boolean spaceNeeded = false;
          Location location = (Location) eObject;
          if (location.isSetLocationCategory()) {
            spaceNeeded = true;
          }
          if (location.isSetName()) {
            if (spaceNeeded) {
              buf.append(" ");
            }
            buf.append(location.getName());
          } else if (location.isSetCity()) {
            if (spaceNeeded) {
              buf.append(" ");
            }
            buf.append(location.getCity());
          }
          
          if (buf.length() == 0) {
            buf.append("Location");
          }
          return buf.toString();
        })
        .setNodeIconFunction(object -> {
          Location location = (Location) object;
          LocationCategory locationCategory = location.getLocationCategory();
          if (locationCategory == null) {
            locationCategory = LocationCategory.DEFAULT_POI;
          }
          return locationCategory.getIcon(ImageSize.SIZE_0);
        })
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewBefore("New element before ...", null, TravelsTreeViewCreator::initNewObject))
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewAfter("New element after ...", null, TravelsTreeViewCreator::initNewObject))
        .addNodeOperationDescriptor(new NodeOperationDescriptorDelete("Delete element", null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorCustom("Show on map", isMenuToBeEnabledPredicate, eObject -> {
          Object value = eObject.getValue();
          if (value instanceof Location location) {
            if (location.getLatitude() != null  &&  location.getLongitude() != null) {
              MapPoint mapPoint = new MapPoint(location.getLatitude(), location.getLongitude());
              travelMapView.flyTo(0.0, mapPoint, 2.0);
            }
          }
        }));
    
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;

    // Location.stayedAtThisLocation
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getLocation_StayedAtThisLocation())
        .setLabelText("Is stayed at location")
        .setPresentationType(PresentationType.BOOLEAN);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // VacationElement.startDate
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getLocation_StartDate())
        .setLabelText("From")
        .setFormat(FDF);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // VacationElement.endDate
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getLocation_EndDate())
        .setLabelText("Until")
        .setFormat(FDF);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Location.duration
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getLocation_Duration())
        .setLabelText("Duration of stay");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Location.description
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getLocation_Description())
        .setLabelText("Description")
        .setPresentationType(PresentationType.MULTI_LINE_TEXT);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // Location.name
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getLocation_Name())
        .setLabelText("Name");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Location.locationCategory
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getLocation_LocationCategory())
        .setLabelText("Location category");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Location.country
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getLocation_Country())
        .setLabelText("Country");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // Location.city
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getLocation_City())
        .setLabelText("City");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // Location.street
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getLocation_Street())
        .setLabelText("Street");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // Location.houseNumber
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getLocation_HouseNumber())
        .setLabelText("Housenumber");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // Location.webSite
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getLocation_WebSite())
        .setLabelText("Website")
        .addNodeOperationDescriptor(new NodeOperationDescriptorOpen("Open document", null, null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // Location.referenceOnly
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getLocation_ReferenceOnly())
        .setLabelText("Reference only")
        .setPresentationType(PresentationType.BOOLEAN);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // Location.latitude
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getLocation_Latitude())
        .setLabelText("Latitude");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // Location.longitude
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getLocation_Longitude())
        .setLabelText("Longitude");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // Location.boundingBox
    EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(TRAVELS_PACKAGE.getLocation_Boundingbox())
        .setNodeTextFunction(_ -> "Bounding box")
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("Create bounding box", null, TravelsTreeViewCreator::initNewObject))
        .addNodeOperationDescriptor(new NodeOperationDescriptorDelete("Delete Bounding Box", null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorCustom("Obtain bounding box", null, new BoundingBoxObtainer()));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);

    // Location.boundaries
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(TRAVELS_PACKAGE.getLocation_Boundaries())
        .setLabelText("Boundaries")
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorCustom("Delete boundaries", null, this::deleteBoundaries))
        .addNodeOperationDescriptor(new NodeOperationDescriptorCustom("Reduce number of points", null, this::showBoundariesPointsReductionWindow));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);

    // Location.children
    eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(TRAVELS_PACKAGE.getVacationElement_Children())
        .setLabelText("Elements")
        .setNodeIconFunction(_ -> EObjectTreeView.getListIcon())
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("New element", null, newEObjectInitializationFunction));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }
  
  private void deleteBoundaries(EObjectTreeItem treeItem) {
    Object value = treeItem.getValue();
    // TODO implement deletion of boundaries
  }
  
  /**
   * Show the boundaries points reduction window.
   * <p>
   * If the provided tree item isn't part of a Location, an IllegalArgumentException is thrown.
   * 
   * @param treeItem the tree item representing the boundaries list.
   */
  private void showBoundariesPointsReductionWindow(EObjectTreeItem treeItem) {
    EObjectTreeItem parentTreeItem = (EObjectTreeItem) treeItem.getParent();
    if (parentTreeItem == null) {
      throw new IllegalArgumentException("treeItem must not be the root item");
    }    
    
    Object parentValue = parentTreeItem.getValue();
    if (!(parentValue instanceof Location)) {
      throw new IllegalArgumentException("treeItem's parent must be a Location");
    }
    Location location = (Location) parentValue;
    
    TravelsService.getInstance().showBoundariesPointsReductionWindow(location);
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.VacationElementGPX.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForGPXTrack() {
    // VacationElementGPX (extends VacationElement)
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(TravelsWindow::generateTextForGpxTrack)
        .setNodeIconFunction(TravelsWindow::generateIconForGpxTrack)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewBefore("New element before this one ...", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewAfter("New element after this one ...", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorCustom("View/edit GPX file", this::canGPXFileBeOpened, this::openGPXFile))
        .addNodeOperationDescriptor(new NodeOperationDescriptorDelete("Delete element", null));
    
    // VacationElementGPX.trackReference
    EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(TRAVELS_PACKAGE.getGPXTrack_TrackReference())
        .setNodeTextFunction(_ -> "GPX track reference")
        .setExpandOnCreation(true);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);
    
    // VacationElementGPX.children
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(TRAVELS_PACKAGE.getVacationElement_Children())
        .setLabelText("Elements")
        .setNodeIconFunction(_ -> EObjectTreeView.getListIcon())
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("New element", null, newEObjectInitializationFunction));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.Document.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForDocument() {
    // Document (extends VacationElement)
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> {
          Document document = (Document) eObject;
          String text = "...";  // default value
          if (document.getDocumentReference() != null) {
            FileReference fileReference = document.getDocumentReference();
            text = fileReference.getTitle();  // first preference; the title set in the FileReference

            if (text == null  ||  text.isEmpty()) {
              String fileName = fileReference.getFile();
              if (fileName != null) {
                text = fileName;
              }

            }
          }

          return text;
        })
        .setNodeIconFunction(TravelsTreeViewCreator::getIconForDocumentFilename)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewBefore("New element before this one ...", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewAfter("New element after this one ...", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorOpen("Open document", TravelsTreeViewCreator::canDocumentBeOpened, TravelsTreeViewCreator::openDocument))
        .addNodeOperationDescriptor(new NodeOperationDescriptorDelete("Delete element", null));

    // Document.documentReference
    EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(TRAVELS_PACKAGE.getDocument_DocumentReference())
        .setNodeTextFunction(_ -> "Document reference")
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("Create document reference", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorDelete("Delete document reference", null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);
    
    //Document.children
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(TRAVELS_PACKAGE.getVacationElement_Children())
        .setLabelText("Elements")
        .setNodeIconFunction(_ -> EObjectTreeView.getListIcon())
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("New element", null, newEObjectInitializationFunction));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

  private static Image getIconForDocumentFilename(Object object) {
    Image image = null;
    if (object instanceof Document document) {
      FileReference fileReference = document.getDocumentReference();
      if (fileReference != null) {
        String fileName = fileReference.getFile();
        LOGGER.info("Creating image for: " + fileName);
        if (fileName != null) {
          if (FileUtils.isPDFFile(fileName)) {
            image = ImageResource.PDF.getImage();
          } else if (FileUtils.isODTFile(fileName)) {
            image = ImageResource.ODT.getImage();
          } else if (FileUtils.isMSWordFile(fileName)) {
            image = ImageResource.MS_WORD.getImage();
          } else if (FileUtils.isTextFile(fileName)) {
            image = ImageResource.TEXT_FILE.getImage();
          } else if (FileUtils.isMarkDownFile(fileName)) {
            image = ImageResource.MARKDOWN.getImage();
          } else {
            image = ImageResource.DOCUMENT.getImage();
          }
        }
        LOGGER.info("image = " + image);
      }
    }

    return image;
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.Picture.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForPicture() {
    // VacationElementPicture (extends VacationElement)
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> {
          Picture picture = (Picture) eObject;
          String text = "...";  // default value
          File file = null;
          if (picture.isSetPictureReference()) {
            FileReference bestandReferentie = picture.getPictureReference();
            text = bestandReferentie.getTitle();  // first preference; the title set in the FileReference
            
            if (text == null  ||  text.isEmpty()) {
              String fileName = bestandReferentie.getFile();
              if (fileName != null) {
                try {
                  file = new File(fileName);
                  PhotoFileMetaDataHandler photoFileMetaDataHandler = new PhotoFileMetaDataHandler(file);
                  text = photoFileMetaDataHandler.getTitle();   // second preference; title set in the photo
                } catch (ImageReadException | IOException e) {
                  text = "NOT FOUND: " + fileName;
//                  e.printStackTrace();
                }
              }
              
              if (text == null  ||  text.isEmpty()) {
                if (file != null) {
                  text = file.getName();
                }
              }
              return text;
            }
          } else {
            return "Picture";
          }
          
          return text;
        })
        .setNodeIconFunction(object -> {
          if (object instanceof Picture picture) {
            FileReference fileReference = picture.getPictureReference();
            Image image = null;
            if (fileReference != null) {
              LOGGER.info("Creating image for: " + picture.getPictureReference().getFile());
              image = new Image("file:" + picture.getPictureReference().getFile(), 150, 150, true, true);
              LOGGER.info("image = " + image);
            }
            return image;
          } else {
            return ImageResource.CAMERA_BLACK.getImage(ImageSize.SIZE_0);
          }
        })
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewBefore("New element before this one ...", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewAfter("New element after this one ...", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorDelete("Delete element", null));

    // VacationElementPicture.pictureReference
    EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(TRAVELS_PACKAGE.getPicture_PictureReference())
        .setNodeTextFunction(_ -> "Photo reference")
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("Create photo reference", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorDelete("Delete photo reference", null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);
    
    // VacationElementText.children
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(TRAVELS_PACKAGE.getVacationElement_Children())
        .setLabelText("Elements")
        .setNodeIconFunction(_ -> EObjectTreeView.getListIcon())
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("New element", null, newEObjectInitializationFunction));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }
  
  /**
   * Create the descriptor for the EClass goedegep.model.vacations.MapImage.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForMapImage() {
    // MapImage (extends VacationElement)
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> {
          MapImage picture = (MapImage) eObject;
          String title = picture.getTitle();
          if (title != null  &&  !title.isEmpty()) {
            return title;
          } else {
            return "MapImage";
          }
        })
        .setNodeIconFunction(_ -> TravelImageResource.MAP_IMAGE.getIcon(ImageSize.SIZE_0))
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewBefore("New element before this one ...", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewAfter("New element after this one ...", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorDelete("Delete element", null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorCustom("Update  image file", (_) -> true, updateMapImageFileFunction));
    
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;

    // MapImage.title
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getMapImage_Title())
        .setLabelText("Title");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // MapImage.imageWidth
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getMapImage_ImageWidth())
        .setLabelText("Image width");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // MapImage.imageHeight
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getMapImage_ImageHeight())
        .setLabelText("Image height");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // MapImage.zoom
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getMapImage_Zoom())
        .setLabelText("Zoom level");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // MapImage.centerLatitude
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getMapImage_CenterLatitude())
        .setLabelText("Center latitude");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // MapImage.centerLongitude
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getMapImage_CenterLongitude())
        .setLabelText("Center longitude");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // MapImage.fileName
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getMapImage_FileName())
        .setLabelText("Filename")
        .setPresentationType(PresentationType.FILE);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // MapImage.children
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(TRAVELS_PACKAGE.getVacationElement_Children())
        .setLabelText("Elements")
        .setNodeIconFunction(_ -> EObjectTreeView.getListIcon())
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptorNew("New element", null, newEObjectInitializationFunction));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }
  
  /**
   * Create the descriptor for the EClass goedegep.model.vacations.BoundingBox.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForBoundingBox() {
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;
    
    // BoundingBox
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> {
          BoundingBox boundingBox = (BoundingBox) eObject;
          if (boundingBox != null) {
            StringBuilder buf = new StringBuilder();
            if (boundingBox.isSetWest()) {
              buf.append(boundingBox.getWest());
            } else {
              buf.append("..");
            }
            buf.append(", ");
            if (boundingBox.isSetNorth()) {
              buf.append(boundingBox.getNorth());
            } else {
              buf.append("..");
            }
            buf.append(", ");
            if (boundingBox.isSetEast()) {
              buf.append(boundingBox.getEast());
            } else {
              buf.append("..");
            }
            buf.append(", ");
            if (boundingBox.isSetSouth()) {
              buf.append(boundingBox.getSouth());
            } else {
              buf.append("..");
            }
            return buf.toString();
          } else {
            return null;
          }
        })
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewBefore("New element before ...", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewAfter("New element after ...", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorDelete("Delete element", null));
    
    // BoundingBox.west
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getBoundingBox_West())
        .setLabelText("West");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    // BoundingBox.north
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getBoundingBox_North())
        .setLabelText("North");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    // BoundingBox.east
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getBoundingBox_East())
        .setLabelText("East");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    // BoundingBox.south
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(TRAVELS_PACKAGE.getBoundingBox_South())
        .setLabelText("South");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }
  
  /**
   * Create the descriptor for the EClass goedegep.types.model.FileReference.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForFileReference() {
    // FileReference
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> {
          FileReference bestandReferentie = (FileReference) eObject;
          if (bestandReferentie != null) {
            return bestandReferentie.getTitle();
          } else {
            return "<no file reference>";
          }
        })
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewBefore("New document before this one", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorNewAfter("New document after this one", null, null))
        .addNodeOperationDescriptor(new NodeOperationDescriptorOpen("Open document", TravelsTreeViewCreator::canDocumentBeOpened, TravelsTreeViewCreator::openDocument))
        .addNodeOperationDescriptor(new NodeOperationDescriptorDelete("Delete document", null));
    
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;
    
    // FileReference.title
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(typesPackage.getFileReference_Title())
        .setLabelText("Titel");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // FileReference.file
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(typesPackage.getFileReference_File())
        .setLabelText("File")
        .setPresentationType(PresentationType.FILE)
        .setInitialDirectoryNameFunction(TravelsWindow::getReferredFilesFolder)
        .addNodeOperationDescriptor(new NodeOperationDescriptorOpen("Open document", null, null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
        
    return eObjectTreeItemClassDescriptor;
  }
  
  /**
   * Check whether Dragboard information can be dropped on the specified tree item.
   * <p>
   * Dropping of EObjects is already part of the general EObjectTreeView functionality, so this isn't checked here.
   * Here we check for photo and GPX files, which can be dropped on lists of VacationElement, or children of lists of VacationElement.<br/>
   * A drop is not possible if any of the file specified in the dragboard cannot be dropped.
   * 
   * @param eObjectTreeItem the tree item.
   * @param dragboard the {@code Dragboard} information.
   * @return true if the {@code dragboard} information can be dropped on {@code eObjectTreeItem}, false otherwise.
   */
  private boolean isDropPossible(EObjectTreeItem eObjectTreeItem, Dragboard dragboard) {
    LOGGER.info("=>");
    
    // If the dragboard has no files, there's nothing to drop.
    if (!dragboard.hasFiles()) {
      LOGGER.info("<= false (no files)");
      return false;
    }
    
    List<File> files = dragboard.getFiles();
    if (files == null  ||  files.isEmpty()) {
      LOGGER.info("<= false (file list null or empty)");
      return false;
    }
    
    for (File file: files) {
      if (!FileUtils.isPictureFile(file)  && !FileUtils.isGpxFile(file)  &&  !FileUtils.isPDFFile(file)) {
        LOGGER.info("<= false (one of the files is not a picture, GPX or PDF file)");
        return false;
      }
    }
    
    // Check whether the item is a list of VacationElement, in which case all supported files can be dropped.
    EStructuralFeature contentStructuralFeature = eObjectTreeItem.getEStructuralFeature();
    if (contentStructuralFeature != null) {
      LOGGER.info("contentStructuralFeature=" + contentStructuralFeature.toString());
      if (contentStructuralFeature instanceof EReference contentEReference) {
        LOGGER.info("contentEReference=" + contentEReference.toString());
        EClass contentReferenceType = contentEReference.getEReferenceType();
        LOGGER.info("contentReferenceType=" + contentReferenceType.toString());
        TravelsPackage vacationsPackage = TravelsPackage.eINSTANCE;
        if (contentEReference.isMany()  &&  contentReferenceType.equals(vacationsPackage.getVacationElement())) {
          LOGGER.info("<= true (it is a list of VacationElement reference)");
          return true;
        }
      }
    }
    
    // Check whether the parent of the item is a list of VacationElement, in which case all supported files can be dropped
    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();
    contentStructuralFeature = parentEObjectTreeItem.getEStructuralFeature();
    if (contentStructuralFeature != null) {
      LOGGER.info("contentStructuralFeature=" + contentStructuralFeature.toString());
      if (contentStructuralFeature instanceof EReference contentEReference) {
        LOGGER.info("contentEReference=" + contentEReference.toString());
        EClass contentReferenceType = contentEReference.getEReferenceType();
        LOGGER.info("contentReferenceType=" + contentReferenceType.toString());
        TravelsPackage vacationsPackage = TravelsPackage.eINSTANCE;
        if (contentEReference.isMany()  &&  contentReferenceType.equals(vacationsPackage.getVacationElement())) {
          LOGGER.info("<= true (parent is a list of VacationElement reference)");
          return true;
        }
      }
    }
    
    
    boolean allFilesAreDocuments = true;
    for (File file: files) {
      if (!FileUtils.isPDFFile(file)) {
        LOGGER.info("<= false (one of the files is not a PDF file)");
        allFilesAreDocuments = false;
        break;
      }
    }

    // Check whether the item is the list of Vacation/Documents, in which case only documents can be dropped.
    if (allFilesAreDocuments) {
      contentStructuralFeature = eObjectTreeItem.getEStructuralFeature();
      if (contentStructuralFeature != null) {
        LOGGER.severe("contentStructuralFeature=" + contentStructuralFeature.toString());
        if (contentStructuralFeature instanceof EReference contentEReference) {
          LOGGER.severe("contentEReference=" + contentEReference.toString());
          EClass contentReferenceType = contentEReference.getEReferenceType();
          LOGGER.severe("contentReferenceType=" + contentReferenceType.toString());
          TravelsPackage vacationsPackage = TravelsPackage.eINSTANCE;
          if (contentEReference.equals(vacationsPackage.getTravel_Documents())) {
            LOGGER.severe("<= true (it is the list of Vacation/Documents)");
            return true;
          }
        }
      }
    }
    // Check whether the parent of the item is the list of Vacation/Documents, in which case only documents can be dropped.
    if (allFilesAreDocuments) {
      parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();
      contentStructuralFeature = parentEObjectTreeItem.getEStructuralFeature();
      if (contentStructuralFeature != null) {
        LOGGER.severe("contentStructuralFeature=" + contentStructuralFeature.toString());
        if (contentStructuralFeature instanceof EReference contentEReference) {
          LOGGER.severe("contentEReference=" + contentEReference.toString());
          EClass contentReferenceType = contentEReference.getEReferenceType();
          LOGGER.severe("contentReferenceType=" + contentReferenceType.toString());
          TravelsPackage vacationsPackage = TravelsPackage.eINSTANCE;
          if (contentEReference.equals(vacationsPackage.getTravel_Documents())) {
            LOGGER.severe("<= true (parent is the list of Vacation/Documents)");
            return true;
          }
        }
      }
    }
    
    LOGGER.info("<= false (not a VacationElement reference)");
    return false;
  }
  
  /**
   * Handle the dropping of {@code Dragboard} information on a tree item.
   * <p>
   * For picture files, a Picture element is created.<br/>
   * For GPX files, a 
   * 
   * @param eObjectTreeItem the tree item on which the information is dropped.
   * @param dragboard the {@code Dragboard} information.
   * @return true if the drop was successful, false otherwise.
   */
  private boolean handleDrop(EObjectTreeItem eObjectTreeItem, Dragboard dragboard) {
    LOGGER.info("=>");
    
    if (!isDropPossible(eObjectTreeItem, dragboard)) {
      return false;
    }
    
    List<File> files = dragboard.getFiles();
    
    // Sort the files, so that they are added in the same way as they appear in an explorer window.
    Collections.sort(files);
    
    // Check whether the item is a list of VacationElement, if so add the new element to the end of the list
    Object object = eObjectTreeItem.getValue();
    EStructuralFeature contentStructuralFeature = eObjectTreeItem.getEStructuralFeature();
    if (contentStructuralFeature != null) {
      LOGGER.info("contentStructuralFeature=" + contentStructuralFeature.toString());
      if (contentStructuralFeature instanceof EReference contentEReference) {
        EClass contentReferenceType = contentEReference.getEReferenceType();
        TravelsPackage vacationsPackage = TravelsPackage.eINSTANCE;
        if (contentEReference.isMany()  &&  contentReferenceType.equals(vacationsPackage.getVacationElement())) {
          @SuppressWarnings("unchecked")
          EList<Object> list = (EList<Object>) object;
          for (File file: files) {
            if (FileUtils.isPictureFile(file)) {
              Picture picture = createPicture(file);
              if (picture != null) {
                list.add(picture);
              }
            } else if (FileUtils.isGpxFile(file)) {
              list.add(createGPXTrack(file));
            } else if (FileUtils.isPDFFile(file)) {
              list.add(createDocument(file));
            }
          }
          return true;
        }
      }
    }

    
    // Check whether the parent of the item is a list of VacationElement, if so add the new element before the item.
    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();
    Object parentEObjectTreeItemContent = parentEObjectTreeItem.getValue();
    contentStructuralFeature = parentEObjectTreeItem.getEStructuralFeature();
    if (contentStructuralFeature != null) {
      LOGGER.info("contentStructuralFeature=" + contentStructuralFeature.toString());
      if (contentStructuralFeature instanceof EReference contentEReference) {
        LOGGER.info("contentEReference=" + contentEReference.toString());
        EClass contentReferenceType = contentEReference.getEReferenceType();
        LOGGER.info("contentReferenceType=" + contentReferenceType.toString());
        TravelsPackage vacationsPackage = TravelsPackage.eINSTANCE;
        if (contentEReference.isMany()  &&  contentReferenceType.equals(vacationsPackage.getVacationElement())) {
          LOGGER.severe("<= true (parent is a list of VacationElement reference)");
          @SuppressWarnings("unchecked")
          EList<Object> list = (EList<Object>) parentEObjectTreeItemContent;
          for (File file: files) {
            if (FileUtils.isPictureFile(file)) {
              Picture picture = createPicture(file);
              if (picture != null) {
                list.add(list.indexOf(object), picture);
              }
            } else if (FileUtils.isGpxFile(file)) {
              list.add(list.indexOf(object), createGPXTrack(file));
            } else if (FileUtils.isPDFFile(file)) {
              list.add(list.indexOf(object), createDocument(file));
            }
          }
          return true;
        }
      }
    }
    
    // Check whether the item is the list of Vacation/Documents, if so add the document to the end of the list
    object = eObjectTreeItem.getValue();
    contentStructuralFeature = eObjectTreeItem.getEStructuralFeature();
    if (contentStructuralFeature != null) {
      LOGGER.info("contentStructuralFeature=" + contentStructuralFeature.toString());
      if (contentStructuralFeature instanceof EReference contentEReference) {
        TravelsPackage vacationsPackage = TravelsPackage.eINSTANCE;
        if (contentEReference.equals(vacationsPackage.getTravel_Documents())) {
          LOGGER.severe("Yes it is the list of Vacation/Documents");
          @SuppressWarnings("unchecked")
          EList<Object> list = (EList<Object>) object;
          for (File file: files) {
            if (FileUtils.isPDFFile(file)) {
              FileReference fileReference = TypesFactory.eINSTANCE.createFileReference();
              fileReference.setFile(file.getAbsolutePath());
              list.add(fileReference);
            }
          }
          return true;
        }
      }
    }
    
    // Check whether the parent of the item is the list of Vacation/Documents, if so add the document before the item.
    parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();
    parentEObjectTreeItemContent = parentEObjectTreeItem.getValue();
    contentStructuralFeature = parentEObjectTreeItem.getEStructuralFeature();
    if (contentStructuralFeature != null) {
      LOGGER.info("contentStructuralFeature=" + contentStructuralFeature.toString());
      if (contentStructuralFeature instanceof EReference contentEReference) {
        LOGGER.info("contentEReference=" + contentEReference.toString());
        EClass contentReferenceType = contentEReference.getEReferenceType();
        LOGGER.info("contentReferenceType=" + contentReferenceType.toString());
        TravelsPackage vacationsPackage = TravelsPackage.eINSTANCE;
        if (contentEReference.equals(vacationsPackage.getTravel_Documents())) {
          LOGGER.severe("<= true (parent is the list of Vacation/Documents)");
          @SuppressWarnings("unchecked")
          EList<Object> list = (EList<Object>) parentEObjectTreeItemContent;
          for (File file: files) {
            if (FileUtils.isPDFFile(file)) {
              FileReference fileReference = TypesFactory.eINSTANCE.createFileReference();
              fileReference.setFile(file.getAbsolutePath());
              list.add(list.indexOf(object), fileReference);
            }
          }
          return true;
        }
      }
    }
    
    return false;
  }
  
  /**
   * Initialize a newly created object.
   * 
   * @param eObject
   * @param eObjectTreeItem
   */
  private static void initNewObject(EObject eObject, EObjectTreeItem eObjectTreeItem) {
    LOGGER.severe("=> eObject=" + eObject + ", eObjectTreeItem" + eObjectTreeItem);
  }
  
  /**
   * Create a {@code Picture} object for a specific file.
   * 
   * @param file the picture {@code File}.
   * @return
   */
  private Picture createPicture(File file) {
    if (FileUtils.isWebpFile(file)) {
      Alert alert = customization.getComponentFactoryFx().createYesNoConfirmationDialog(
          "Webp files are not supported",
          "You are trying to drop a webp file, but webp files are not supported.", "Do you want to convert the file to a jpeg file (which implies the webp file will be removed)");
      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent() && result.get() == ButtonType.YES) {
        LOGGER.severe("yes, convert file");
        try {
          file = FileUtils.convertWebpFileToJpegFile(file);
        } catch (IOException e1) {
          e1.printStackTrace();
          return null;
        }
      } else {
        LOGGER.severe("no, don't convert file");
        return null;
      }
    }
    
    TravelsFactory vacationsFactory = TravelsFactory.eINSTANCE;
    Picture picture = vacationsFactory.createPicture();
    TypesFactory typesFactory = TypesFactory.eINSTANCE;
    FileReference fileReference = typesFactory.createFileReference();
    fileReference.setFile(file.getAbsolutePath());
    picture.setPictureReference(fileReference);
    
    return picture;
  }
  
  /**
   * Create a {@code GPXTrack} object for a specific file.
   * 
   * @param file the GPX {@code File}.
   * @return a GPXTrack for the specified {@code file}.
   */
  private GPXTrack createGPXTrack(File file) {
    TravelsFactory vacationsFactory = TravelsFactory.eINSTANCE;
    GPXTrack gpxTrack = vacationsFactory.createGPXTrack();
    TypesFactory typesFactory = TypesFactory.eINSTANCE;
    FileReference fileReference = typesFactory.createFileReference();
    fileReference.setFile(file.getAbsolutePath());
    gpxTrack.setTrackReference(fileReference);
    
    return gpxTrack;
  }
  
  /**
   * Create a {@code Document} object for a specific file.
   * 
   * @param file the document {@code File}.
   * @return a Document for the specified {@code file}.
   */
  private Document createDocument(File file) {
    TravelsFactory vacationsFactory = TravelsFactory.eINSTANCE;
    Document document = vacationsFactory.createDocument();
    TypesFactory typesFactory = TypesFactory.eINSTANCE;
    FileReference fileReference = typesFactory.createFileReference();
    fileReference.setFile(file.getAbsolutePath());
    document.setDocumentReference(fileReference);
    
    return document;
  }
  

  
//  /**
//   * Create the descriptor for the EClass goedegep.model.vacations.VacationElement.
//   * 
//   * @param eObjectTreeDescriptor the tree descriptor to which the VacationElement descriptor is to be added.
//   */
//  private static void createAndAddEObjectTreeDescriptorForVacationElement(EObjectTreeDescriptor eObjectTreeDescriptor) {
//    EClass eClass = VACATIONS_PACKAGE.getVacationElement();
//        
//    // VacationElement
//    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
//        .setNodeTextFunction(eObject -> {
//          return "Vacation element";
//        })
//        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New element ..."));
//        
//    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
//  }
  
  private static boolean canDocumentBeOpened(EObjectTreeItem treeItem) {
    LOGGER.info("=> treeItem=" + treeItem);
    
    if (treeItem == null  ||  !(treeItem instanceof EObjectTreeItemForObject)) {
      return false;
    }
    
    EObjectTreeItemForObject eObjectTreeItemForObject = (EObjectTreeItemForObject) treeItem;
    Object value = eObjectTreeItemForObject.getValue();
    
    if (value == null  ||  !(value instanceof Document)) {
      return false;
    }
    
    Document document = (Document) value;
    FileReference documentReference = document.getDocumentReference();
    if (documentReference == null) {
      return false;
    }
    
    String filename = documentReference.getFile();
    if (filename != null) {
      filename = filename.trim();
    }
    
    File file = new File(filename);
    if (!file.exists()) {
      return false;
    }
    
    return true;
  }
  
  /**
   * Open the file related to an {@code EObjectTreeItem}
   * <p>
   * The {@code treeItem} shall be an {@code EObjectTreeItemForObject}.<br/>
   * Objects of the following types are supported:
   * <ul>
   * <li>FileReference<br/>
   * If the file of the FileReference is set, this file is opened.
   * </li>
   * <li>Document<br/>
   * If the documentReference is set, this FileReference is handled as above.
   * </li>
   * </ul>
   * 
   * @param treeItem the {@code EObjectTreeItem} for which the file is to be opened.
   */
  private static void openDocument(EObjectTreeItem treeItem) {
    LOGGER.info("=> treeItem=" + treeItem);

    if (treeItem instanceof EObjectTreeItemForObject eObjectTreeItemForObject) {
      FileReference fileReference = null;
      Object value = eObjectTreeItemForObject.getValue();
      
      if (value instanceof Document document) {
        fileReference = document.getDocumentReference();
      } else if (value instanceof FileReference fr) {
        fileReference = fr;
      } else {
        throw new IllegalArgumentException("Unsupported value: " + value);
      }
      
      if (fileReference == null) {
        return;
      }
      
      String filename = fileReference.getFile().trim();
      if (filename == null) {
        return;
      }

      LOGGER.info("Going to open: " + filename);

      File file = new File(filename);
      try {
        Desktop.getDesktop().open(file);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      throw new IllegalArgumentException("treeItem shall be an EObjectTreeItemForObject but is a " + treeItem.getClass().getName());
    }
  }
  
  // Probably has to go with next below
  private boolean canGPXFileBeOpened(EObjectTreeItem treeItem) {
    LOGGER.info("=> treeItem=" + treeItem);
    
    if (treeItem == null  ||  !(treeItem instanceof EObjectTreeItemForObject)) {
      return false;
    }
    
    EObjectTreeItemForObject eObjectTreeItemForObject = (EObjectTreeItemForObject) treeItem;
    Object value = eObjectTreeItemForObject.getValue();
    
    if (value == null  ||  !(value instanceof GPXTrack)) {
      return false;
    }
    
    GPXTrack gpxTrack = (GPXTrack) value;
    FileReference trackReference = gpxTrack.getTrackReference();
    if (trackReference == null) {
      return false;
    }
    
    String filename = trackReference.getFile();
    if (filename != null) {
      filename = filename.trim();
    } else {
      return false;
    }
    
    File file = new File(filename);
    if (!file.exists()) {
      return false;
    }
    
    return true;
  }
  
  // MOVE TO VACATIONSWINDOW
  private void openGPXFile(EObjectTreeItem treeItem) {
    LOGGER.info("=> treeItem=" + treeItem);
    
    EObjectTreeItemForObject eObjectTreeItemForObject = (EObjectTreeItemForObject) treeItem;
    GPXTrack gpxTrack = (GPXTrack) eObjectTreeItemForObject.getValue();
    FileReference trackReference = gpxTrack.getTrackReference();
    String filename = trackReference.getFile().trim();
    
    LOGGER.info("Going to open: " + filename);
    
    GPXService.getInstance().showGPXWindow(filename);
  }
  
}
