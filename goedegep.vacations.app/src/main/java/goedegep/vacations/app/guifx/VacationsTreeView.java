package goedegep.vacations.app.guifx;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttreeview.EObjectTreeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItem;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesFactory;
import goedegep.util.emf.EmfUtil;
import goedegep.util.file.FileUtils;
import goedegep.vacations.model.Document;
import goedegep.vacations.model.GPXTrack;
import goedegep.vacations.model.Picture;
import goedegep.vacations.model.VacationsFactory;
import goedegep.vacations.model.VacationsPackage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.Dragboard;

/**
 * This class provides a <code>TreeView</code> for a <code>Vacations</code> data structure.
 */
public class VacationsTreeView extends EObjectTreeView {
  private static final Logger LOGGER = Logger.getLogger(VacationsTreeView.class.getName());
  
  private CustomizationFx customization;
  
  public VacationsTreeView(CustomizationFx customization, EObjectTreeDescriptor eObjectTreeDescriptor, boolean editMode) {
    super(null, eObjectTreeDescriptor, editMode);
    this.customization = customization;
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
    
    // Check whether the item is a list of VacationElement
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
    
    // Check whether the parent of the item is a list of VacationElement
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
//      event.setDropCompleted(success);
//      event.consume();
    }
    
    List<File> files = dragboard.getFiles();
    
    // Sort the files, so that they are added in the same way as they appear in an explorer window.
    Collections.sort(files);
    
    // Check whether the item is a list of VacationElement, if so add the picture to the end of the list
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

    
    // Check whether the parent of the item is a list of VacationElement, if so add the picture before the item.
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
    
    return false;
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
}
