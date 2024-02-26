package goedegep.vacations.app.guifx;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.gluonhq.maps.MapPoint;

import goedegep.appgen.TableRowOperation;
import goedegep.gpx.app.GPXWindow;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttreeview.EObjectTreeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItem;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassListReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemForObject;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.jfx.eobjecttreeview.ExtendedNodeOperationDescriptor;
import goedegep.jfx.eobjecttreeview.NodeOperationDescriptor;
import goedegep.jfx.eobjecttreeview.PresentationType;
import goedegep.poi.app.guifx.POIIcons;
import goedegep.poi.model.POICategoryId;
import goedegep.poi.model.POIPackage;
import goedegep.resources.ImageResource;
import goedegep.resources.ImageSize;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesFactory;
import goedegep.types.model.TypesPackage;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.emf.EmfPackageHelper;
import goedegep.util.emf.EmfUtil;
import goedegep.util.file.FileUtils;
import goedegep.util.img.PhotoFileMetaDataHandler;
import goedegep.vacations.model.BoundingBox;
import goedegep.vacations.model.Day;
import goedegep.vacations.model.DayTrip;
import goedegep.vacations.model.Document;
import goedegep.vacations.model.GPXTrack;
import goedegep.vacations.model.Location;
import goedegep.vacations.model.MapImage;
import goedegep.vacations.model.Picture;
import goedegep.vacations.model.Text;
import goedegep.vacations.model.Vacation;
import goedegep.vacations.model.VacationsFactory;
import goedegep.vacations.model.VacationsPackage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;

/**
 * This class provides a <code>TreeView</code> for a <code>Vacations</code> data structure.
 */
public class VacationsTreeView extends EObjectTreeView {
  private static final Logger LOGGER = Logger.getLogger(VacationsTreeView.class.getName());
  private static final SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
  private static final FlexDateFormat FDF = new FlexDateFormat();
  private static final VacationsPackage VACATIONS_PACKAGE = VacationsPackage.eINSTANCE;
  private static final POIPackage POI_PACKAGE = POIPackage.eINSTANCE; 
  
  private static CustomizationFx customization;  // TODO should not be static 
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param biConsumer TODO
   * @param updateMapImageFileMethod method for updating a map image file
   * @param poiIcons POI icons
   * @param isMenuToBeEnabledMethod TODO
   * @param reduceBoundariesSizesMethod method for reducing the number of point of the boundary of a location.
   * @param editMode if true, the tree is shown in 'edit mode', otherwise the tree is shown in 'view mode'.
   */
  public VacationsTreeView(CustomizationFx customization,
      BiConsumer<EObject, EObjectTreeItem> biConsumer,
      Consumer<EObjectTreeItem> updateMapImageFileMethod,
      POIIcons poiIcons,
      Predicate<EObjectTreeItem> isMenuToBeEnabledMethod,
      Consumer<EObjectTreeItem> reduceBoundariesSizesMethod, TravelMapView travelMapView, boolean editMode) {
    super(null, createEObjectTreeDescriptor(customization, biConsumer, updateMapImageFileMethod, poiIcons, isMenuToBeEnabledMethod, reduceBoundariesSizesMethod, travelMapView), editMode);
    VacationsTreeView.customization = customization;
    setIsDropPossibleFunction(this::isDropPossible);
    setHandleDropFunction(this::handleDrop);
  }
  
  /**
   * Check whether a tree item is a list of (supertypes of) Locations.
   * 
   * @param treeItem the tree item to be checked
   * @return true if <code>treeItem</code> is a list of (supertypes of) Locations, false otherwise.
   */
  public boolean treeItemIsLocationsList(EObjectTreeItem treeItem) {
    if (treeItem == null) {
      return false;
    }
    
    EClass locationEClass = VacationsPackage.eINSTANCE.getLocation();

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
  public boolean isDropPossible(EObjectTreeItem eObjectTreeItem, Dragboard dragboard) {
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
        VacationsPackage vacationsPackage = VacationsPackage.eINSTANCE;
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
        VacationsPackage vacationsPackage = VacationsPackage.eINSTANCE;
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
          VacationsPackage vacationsPackage = VacationsPackage.eINSTANCE;
          if (contentEReference.equals(vacationsPackage.getVacation_Documents())) {
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
          VacationsPackage vacationsPackage = VacationsPackage.eINSTANCE;
          if (contentEReference.equals(vacationsPackage.getVacation_Documents())) {
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
  public boolean handleDrop(EObjectTreeItem eObjectTreeItem, Dragboard dragboard) {
    LOGGER.severe("=>");
    
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
        VacationsPackage vacationsPackage = VacationsPackage.eINSTANCE;
        if (contentEReference.isMany()  &&  contentReferenceType.equals(vacationsPackage.getVacationElement())) {
          LOGGER.severe("Yes it is a VacationElement reference");
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
        VacationsPackage vacationsPackage = VacationsPackage.eINSTANCE;
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
        VacationsPackage vacationsPackage = VacationsPackage.eINSTANCE;
        if (contentEReference.equals(vacationsPackage.getVacation_Documents())) {
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
        VacationsPackage vacationsPackage = VacationsPackage.eINSTANCE;
        if (contentEReference.equals(vacationsPackage.getVacation_Documents())) {
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
    
    VacationsFactory vacationsFactory = VacationsFactory.eINSTANCE;
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
    VacationsFactory vacationsFactory = VacationsFactory.eINSTANCE;
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
    VacationsFactory vacationsFactory = VacationsFactory.eINSTANCE;
    Document document = vacationsFactory.createDocument();
    TypesFactory typesFactory = TypesFactory.eINSTANCE;
    FileReference fileReference = typesFactory.createFileReference();
    fileReference.setFile(file.getAbsolutePath());
    document.setDocumentReference(fileReference);
    
    return document;
  }

  public void selectTreeItem(Object object) {
    // TODO Auto-generated method stub
    
  }
  

  /**
   * Create the EObjectTreeDescriptor for the Vacations tree view.
   * 
   * @return the EObjectTreeDescriptor for the Vacations tree view
   */
  private static EObjectTreeDescriptor createEObjectTreeDescriptor(
      CustomizationFx customization,
      BiConsumer<EObject,
      EObjectTreeItem> biConsumer,
      Consumer<EObjectTreeItem> updateMapImageFileMethod,
      POIIcons poiIcons,
      Predicate<EObjectTreeItem> isMenuToBeEnabledMethod,
      Consumer<EObjectTreeItem> reduceBoundariesSizesMethod,
      TravelMapView travelMapView) {
    EmfPackageHelper vakantiesPackageHelper = new EmfPackageHelper(VACATIONS_PACKAGE);
    EmfPackageHelper poiPackageHelper = new EmfPackageHelper(POI_PACKAGE);
    EObjectTreeDescriptor eObjectTreeDescriptor = new EObjectTreeDescriptor();
    
    createAndAddEObjectTreeDescriptorForVacations(eObjectTreeDescriptor, vakantiesPackageHelper);
    createAndAddEObjectTreeDescriptorForBoundingBox(eObjectTreeDescriptor, vakantiesPackageHelper);
    createAndAddEObjectTreeDescriptorForLocation(eObjectTreeDescriptor, vakantiesPackageHelper, biConsumer, poiIcons, isMenuToBeEnabledMethod, reduceBoundariesSizesMethod, travelMapView);
    createAndAddEObjectTreeDescriptorForVacation(eObjectTreeDescriptor, vakantiesPackageHelper, customization, biConsumer);
    createAndAddEObjectTreeDescriptorForDayTrip(eObjectTreeDescriptor, vakantiesPackageHelper, biConsumer);
    createAndAddEObjectTreeDescriptorForVacationElement(eObjectTreeDescriptor, vakantiesPackageHelper);
    createAndAddEObjectTreeDescriptorForFileReference(eObjectTreeDescriptor);
    createAndAddEObjectTreeDescriptorForDay(eObjectTreeDescriptor, vakantiesPackageHelper, biConsumer);
    createAndAddEObjectTreeDescriptorForText(eObjectTreeDescriptor, vakantiesPackageHelper, biConsumer);
    createAndAddEObjectTreeDescriptorForPicture(eObjectTreeDescriptor, vakantiesPackageHelper, biConsumer);
    createAndAddEObjectTreeDescriptorForDocument(eObjectTreeDescriptor, vakantiesPackageHelper, biConsumer);
    createAndAddEObjectTreeDescriptorForGPXTrack(eObjectTreeDescriptor, vakantiesPackageHelper, biConsumer);
    createAndAddEObjectTreeDescriptorForMapImage(eObjectTreeDescriptor, vakantiesPackageHelper, biConsumer, updateMapImageFileMethod);
    
    createAndAddEEnumEditorDescriptorForLocationType(eObjectTreeDescriptor, poiPackageHelper);
  
    return eObjectTreeDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.Vacations.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Vacations descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private static void createAndAddEObjectTreeDescriptorForVacations(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.Vacations");
        
    // Vacations = "Vakanties informatie" (root node)
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor((eObject) -> "Travel information", true, null,
        eObject -> TravelImageResource.TRAVEL.getIcon(ImageSize.SIZE_1));
    eObjectTreeItemClassDescriptor.setStrongText(true);

    // Vacations.tips
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getVacations_Tips(), "Tips", PresentationType.MULTI_LINE_TEXT, null));

    // Vacations.home
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Create home location"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete home location"));
    EObjectTreeItemClassReferenceDescriptor homeDescriptor = new EObjectTreeItemClassReferenceDescriptor(VACATIONS_PACKAGE.getVacations_Home(), vakantiesPackageHelper.getEClass("goedegep.vacations.model.Location"), (eObject) -> "Home location", false, nodeOperationDescriptors);
    homeDescriptor.setStrongText(true);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(homeDescriptor);
    
    // Vacations.vacations
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New vacation"));
    EObjectTreeItemClassListReferenceDescriptor vacationsDescriptor = new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacations_Vacations(), "Vacations", true, nodeOperationDescriptors,
        eObject -> TravelImageResource.VACATIONS.getIcon(ImageSize.SIZE_1));
    vacationsDescriptor.setStrongText(true);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(vacationsDescriptor);
    
    // Vacations.dayTrips
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New day trip"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacations_DayTrips(), "Day trips", true, nodeOperationDescriptors));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }
  
  /**
   * Create the descriptor for the EClass goedegep.model.vacations.BoundingBox.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the BoundingBox descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private static void createAndAddEObjectTreeDescriptorForBoundingBox(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.BoundingBox");
    
    // BoundingBox
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New element before ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New element after ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete element"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(
        eObject -> {
          StringBuilder buf = new StringBuilder();
          BoundingBox boundingBox = (BoundingBox) eObject;
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
        }, false, nodeOperationDescriptors);
    
    // BoundingBox.west
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getBoundingBox_West(), "West", null));
    // BoundingBox.north
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getBoundingBox_North(), "North", null));
    // BoundingBox.east
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getBoundingBox_East(), "East", null));
    // BoundingBox.south
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getBoundingBox_South(), "South", null));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }
  
  /**
   * Create the descriptor for the EClass goedegep.model.vacations.Location.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Location descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private static void createAndAddEObjectTreeDescriptorForLocation(
      EObjectTreeDescriptor eObjectTreeDescriptor,
      EmfPackageHelper vakantiesPackageHelper,
      BiConsumer<EObject, EObjectTreeItem> biConsumer,
      POIIcons poiIcons,
      Predicate<EObjectTreeItem> isMenuToBeEnabledMethod,
      Consumer<EObjectTreeItem> reduceBoundariesSizesMethod,
      TravelMapView travelMapView) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.Location");
    
    // VacationElementLocation
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New element before ...", VacationsTreeView::initNewObject));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New element after ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete element"));
    nodeOperationDescriptors.add(new ExtendedNodeOperationDescriptor("Show on map", isMenuToBeEnabledMethod, (eObject) -> {
      LOGGER.severe("eObject type:" + eObject.getClass().getName());
      Object value = eObject.getValue();
      LOGGER.severe("value type:" + value.getClass().getName());
      if (value instanceof Location location) {
        if (location.getLatitude() != null  &&  location.getLongitude() != null) {
          MapPoint mapPoint = new MapPoint(location.getLatitude(), location.getLongitude());
          travelMapView.flyTo(0.0, mapPoint, 2.0);
        }
      }
    }));
    nodeOperationDescriptors.add(new ExtendedNodeOperationDescriptor("Reduce boundaries sizes", isMenuToBeEnabledMethod, reduceBoundariesSizesMethod));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(
        eObject -> {
          StringBuilder buf = new StringBuilder();
          boolean spaceNeeded = false;
          Location location = (Location) eObject;
          if (location.isSetLocationType()) {
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
        }, false, nodeOperationDescriptors,
        object -> {
          Location location = (Location) object;
          POICategoryId poiCategoryId = location.getLocationType();
          return poiIcons.getIcon(poiCategoryId, 16, 16);
        });
    
    // Location.stayedAtThisLocation
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_StayedAtThisLocation(), "Is stayed at location", PresentationType.BOOLEAN, null));
    // VacationElement.startDate
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_StartDate(), "From", FDF, null));
    // VacationElement.endDate
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_EndDate(), "Until", FDF, null));
    // Location.duration
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Duration(), "Duration of stay", null));
    // Location.description
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Description(), "Description", PresentationType.MULTI_LINE_TEXT, null));

    // Location.name
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Name(), "Name", null));
    // Location.locationType
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_LocationType(), "Location type", null));
    // Location.country
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Country(), "Country", null));
    // Location.city
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_City(), "City", null));
    // Location.street
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Street(), "Street", null));
    // Location.houseNumber
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_HouseNumber(), "Housenumber", null));
    // Location.webSite
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.OPEN, "Open document"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_WebSite(), "Website", nodeOperationDescriptors));
    // Location.referenceOnly
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_ReferenceOnly(), "Reference only", PresentationType.BOOLEAN, null));
    // Location.latitude
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Latitude(), "Latitude", null));
    // Location.longitude
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Longitude(), "Longitude", null));
    // Location.boundingBox
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Create bounding box", VacationsTreeView::initNewObject));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete Bounding Box"));
    nodeOperationDescriptors.add(new ExtendedNodeOperationDescriptor("Obtain bounding box", null, new BoundingBoxObtainer()));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(VACATIONS_PACKAGE.getLocation_Boundingbox(), vakantiesPackageHelper.getEClass("goedegep.vacations.model.BoundingBox"), (eObject) -> "Bounding box", true, nodeOperationDescriptors));

    // Location.children
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Nieuw element", biConsumer));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacationElement_Children(), "Elements", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.Vacation.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Vacation descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private static void createAndAddEObjectTreeDescriptorForVacation(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper, CustomizationFx customization, BiConsumer<EObject, EObjectTreeItem> biConsumer) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.Vacation");
    TypesPackage typesPackage = TypesPackage.eINSTANCE;
        
    // Vacation
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New vacation before this one"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New vacation after this one"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete vacation"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(
        eObject -> {
          if (!(eObject instanceof Vacation)) {
            LOGGER.severe("Wrong type, Vacation expected, but is: " + eObject.getClass().getSimpleName());
          }
          Vacation vacation = (Vacation) eObject;
          return vacation.getId();
        }, false, nodeOperationDescriptors,
        eObject -> {
          return customization.getResources().getApplicationImage(ImageSize.SIZE_0);
        });
    eObjectTreeItemClassDescriptor.setStrongText(true);
    
    // Vacation.title
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getVacation_Title(), "Title", null));
    // Vacation.date (startDate)
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(typesPackage.getEvent_Date(), "From", FDF, null));
    // Vacation.endDate
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getVacation_EndDate(), "Until", FDF, null));
    // Vacation.notes
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(typesPackage.getEvent_Notes(), "General", PresentationType.MULTI_LINE_TEXT, null));
    
    // Vacation.documents
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New document"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacation_Documents(), "Documents", false, nodeOperationDescriptors));
    
    // Vacation.pictures
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.OPEN, "Open photos folder"));
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getVacation_Pictures(), "Photos", PresentationType.FOLDER, nodeOperationDescriptors);
    eObjectTreeItemAttributeDescriptor.setInitialDirectoryNameFunction(VacationsWindow::getInitialPhotoFolderName);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // Vacation.elements
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New element", biConsumer));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacation_Elements(), "Elements", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.DayTrip.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the DayTrip descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private static void createAndAddEObjectTreeDescriptorForDayTrip(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper, BiConsumer<EObject, EObjectTreeItem> biConsumer) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.DayTrip");
    TypesPackage typesPackage = TypesPackage.eINSTANCE;
        
    // DayTrip
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New day trip before this one"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New day trip after this one"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete day trip"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(
        eObject -> {
            DayTrip dayTrip = (DayTrip) eObject;
            return dayTrip.getId();
          }, false, nodeOperationDescriptors,
        eObject -> {
            return TravelImageResource.DAY_TRIP.getIcon(ImageSize.SIZE_0);
        });
    
    // DayTrip.title
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getDayTrip_Title(), "Title", null));
    // DayTrip.date
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(typesPackage.getEvent_Date(), "Date", FDF, null));

    // Vacation.elements
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New element", biConsumer));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getDayTrip_Elements(), "Elements", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }
  /**
   * Create the descriptor for the EClass goedegep.model.vacations.VacationElement.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the VacationElement descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private static void createAndAddEObjectTreeDescriptorForVacationElement(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.VacationElement");
        
    // VacationElement
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New element ..."));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(
        eObject -> {
            return "Vacation element";
          }, false, nodeOperationDescriptors);
        
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.VacationElementDay.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the VacationElementDay descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private static void createAndAddEObjectTreeDescriptorForDay(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper, BiConsumer<EObject, EObjectTreeItem> biConsumer) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.Day");
        
    // Day (extends VacationElement)
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New element before this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New element after this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete element"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(
        eObject -> {
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
          }, false, nodeOperationDescriptors, object -> TravelImageResource.DAY.getIcon(ImageSize.SIZE_0));
    
    // Day.title
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getDay_Title(), "Title", null));
    
    // Day.days
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getDay_Days(), "Days", null));

    // Day.children
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New element", biConsumer));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacationElement_Children(), "Elements", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }
  
  /**
   * Create the descriptor for the EClass goedegep.types.model.FileReference.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the FileReference descriptor is to be added.
   */
  private static void createAndAddEObjectTreeDescriptorForFileReference(EObjectTreeDescriptor eObjectTreeDescriptor) {
    TypesPackage typesPackage = TypesPackage.eINSTANCE;
    EmfPackageHelper typesPackageHelper = new EmfPackageHelper(typesPackage);
    EClass eClass = typesPackageHelper.getEClass("goedegep.types.model.FileReference");

    // FileReference
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New document before this one"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New document after this one"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete document"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(
          eObject -> {
            FileReference bestandReferentie = (FileReference) eObject;
            if (bestandReferentie != null) {
              return bestandReferentie.getTitle();
            } else {
              return "<no file reference>";
            }
          },
          false, nodeOperationDescriptors);
    
    // FileReference.title
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(typesPackage.getFileReference_Title(), "Titel", null));
    
    // FileReference.file
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.OPEN, "Open document"));
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(typesPackage.getFileReference_File(), "File", PresentationType.FILE, nodeOperationDescriptors);
    eObjectTreeItemAttributeDescriptor.setInitialDirectoryNameFunction(VacationsWindow::getReferredFilesFolder);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
        
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.VacationElementText.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the VacationElementText descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private static void createAndAddEObjectTreeDescriptorForText(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper, BiConsumer<EObject, EObjectTreeItem> biConsumer) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.Text");
        
    // VacationElementText (extends VacationElement)
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New element before this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New element after this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete element"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(
        eObject -> {
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
          }, false, nodeOperationDescriptors, object -> ImageResource.TEXT.getImage(ImageSize.SIZE_0));
    
    // VacationElementText.text
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getText_Text(), "Text", PresentationType.MULTI_LINE_TEXT, null));

    // VacationElementText.children
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New element", biConsumer));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacationElement_Children(), "Elements", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.Picture.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the VacationElementPicture descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private static void createAndAddEObjectTreeDescriptorForPicture(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper, BiConsumer<EObject, EObjectTreeItem> biConsumer) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.Picture");
        
    // VacationElementPicture (extends VacationElement)
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New element before this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New element after this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete element"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(
        eObject -> {
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
        }, false, nodeOperationDescriptors, (object) -> {
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
        });

    // VacationElementPicture.pictureReference
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Create photo reference"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete photo reference"));
    TypesPackage typesPackage = TypesPackage.eINSTANCE;
    EmfPackageHelper typesPackageHelper = new EmfPackageHelper(typesPackage);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(VACATIONS_PACKAGE.getPicture_PictureReference(), typesPackageHelper.getEClass("goedegep.types.model.FileReference"), (eObject) -> "Photo reference", true, nodeOperationDescriptors));
    
    // VacationElementText.children
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New element", biConsumer));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacationElement_Children(), "Elements", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.Picture.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the VacationElementPicture descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private static void createAndAddEObjectTreeDescriptorForDocument(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper, BiConsumer<EObject, EObjectTreeItem> biConsumer) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.Document");
        
    // Document (extends VacationElement)
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New element before this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New element after this one ..."));
    nodeOperationDescriptors.add(new ExtendedNodeOperationDescriptor("Open document", VacationsTreeView::canDocumentBeOpened, VacationsTreeView::openDocument));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete element"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(
        eObject -> {
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
        }, false, nodeOperationDescriptors, (object) -> {
          Image image = null;
          if (object instanceof Document document) {
            FileReference fileReference = document.getDocumentReference();
            if (fileReference != null) {
              String fileName = fileReference.getFile();
              LOGGER.info("Creating image for: " + fileName);
              if (fileName != null  &&  FileUtils.isPDFFile(fileName)) {
                image = ImageResource.PDF.getImage();
              }
              LOGGER.info("image = " + image);
            }
          }

          return image;
        });

    // Document.documentReference
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Create document reference"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete document reference"));
    TypesPackage typesPackage = TypesPackage.eINSTANCE;
    EmfPackageHelper typesPackageHelper = new EmfPackageHelper(typesPackage);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(VACATIONS_PACKAGE.getDocument_DocumentReference(), typesPackageHelper.getEClass("goedegep.types.model.FileReference"), (eObject) -> "Document reference", true, nodeOperationDescriptors));
    
    //Document.children
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New element", biConsumer));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacationElement_Children(), "Elements", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }
  
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
  
  private static void openDocument(EObjectTreeItem treeItem) {
    LOGGER.info("=> treeItem=" + treeItem);
    
    EObjectTreeItemForObject eObjectTreeItemForObject = (EObjectTreeItemForObject) treeItem;
    Document document = (Document) eObjectTreeItemForObject.getValue();
    FileReference documentReference = document.getDocumentReference();
    String filename = documentReference.getFile().trim();
    
    LOGGER.info("Going to open: " + filename);
    
    File file = new File(filename);
    try {
      Desktop.getDesktop().open(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.VacationElementGPX.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the VacationElementGPX descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private static void createAndAddEObjectTreeDescriptorForGPXTrack(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper, BiConsumer<EObject, EObjectTreeItem> biConsumer) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.GPXTrack");
        
    // VacationElementGPX (extends VacationElement)
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New element before this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New element after this one ..."));
    nodeOperationDescriptors.add(new ExtendedNodeOperationDescriptor("View/edit GPX file", VacationsTreeView::canGPXFileBeOpened, VacationsTreeView::openGPXFile));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete element"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(
        VacationsWindow::generateTextForGpxTrack, false, nodeOperationDescriptors, VacationsWindow::generateIconForGpxTrack);
    
    // VacationElementGPX.trackReference
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Create GPX track reference"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete GPX track reference"));
    TypesPackage typesPackage = TypesPackage.eINSTANCE;
    EmfPackageHelper typesPackageHelper = new EmfPackageHelper(typesPackage);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(VACATIONS_PACKAGE.getGPXTrack_TrackReference(), typesPackageHelper.getEClass("goedegep.types.model.FileReference"), (eObject) -> "GPX track reference", true, nodeOperationDescriptors));
    
    // VacationElementGPX.children
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New element", biConsumer));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacationElement_Children(), "Elements", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }
  
  private static boolean canGPXFileBeOpened(EObjectTreeItem treeItem) {
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
    }
    
    File file = new File(filename);
    if (!file.exists()) {
      return false;
    }
    
    return true;
  }
  
  private static void openGPXFile(EObjectTreeItem treeItem) {
    LOGGER.info("=> treeItem=" + treeItem);
    
    EObjectTreeItemForObject eObjectTreeItemForObject = (EObjectTreeItemForObject) treeItem;
    GPXTrack gpxTrack = (GPXTrack) eObjectTreeItemForObject.getValue();
    FileReference trackReference = gpxTrack.getTrackReference();
    String filename = trackReference.getFile().trim();
    
    LOGGER.info("Going to open: " + filename);
    
    new GPXWindow(customization, filename);
  }
  
  /**
   * Create the descriptor for the EClass goedegep.model.vacations.MapImage.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the MapImage descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private static void createAndAddEObjectTreeDescriptorForMapImage(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper, BiConsumer<EObject, EObjectTreeItem> biConsumer, Consumer<EObjectTreeItem> updateMapImageFileMethod) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.MapImage");
        
    // MapImage (extends VacationElement)
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New element before this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New element after this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete element"));
    nodeOperationDescriptors.add(new ExtendedNodeOperationDescriptor("Update  image file", (eObjectTreeItem) -> true, updateMapImageFileMethod));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(
        eObject -> {
            MapImage picture = (MapImage) eObject;
            String title = picture.getTitle();
            if (title != null  &&  !title.isEmpty()) {
              return title;
            } else {
              return "MapImage";
            }
          }, false, nodeOperationDescriptors, object -> ImageResource.MAP.getImage());

    // MapImage.title
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getMapImage_Title(), "Title", null));

    // MapImage.imageWidth
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getMapImage_ImageWidth(), "Image width", null));

    // MapImage.imageHeight
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getMapImage_ImageHeight(), "Image height", null));

    // MapImage.zoom
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getMapImage_Zoom(), "Zoom level", null));

    // MapImage.centerLatitude
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getMapImage_CenterLatitude(), "Center latitude", null));

    // MapImage.centerLongitude
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getMapImage_CenterLongitude(), "Center longitude", null));

    // MapImage.fileName
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getMapImage_FileName(), "Filename", PresentationType.FILE, null));
    
    // MapImage.children
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New element", biConsumer));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacationElement_Children(), "Elements", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }
  
  private static void createAndAddEEnumEditorDescriptorForLocationType(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper poiPackageHelper) {
    EEnum eEnum = poiPackageHelper.getEEnum("goedegep.poi.model.POICategoryId");
    
    eObjectTreeDescriptor.addEEnumEditorDescriptor(eEnum, EEnumEditorDescriptorForPOIs.getInstance());
  }
  
}
