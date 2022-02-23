package goedegep.vacations.app.guifx;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import goedegep.jfx.eobjecttreeview.EObjectTreeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItem;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemContent;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesFactory;
import goedegep.util.emf.EmfUtil;
import goedegep.util.file.FileUtils;
import goedegep.vacations.model.Picture;
import goedegep.vacations.model.VacationsFactory;
import goedegep.vacations.model.VacationsPackage;
import javafx.scene.input.Dragboard;

/**
 * This class provides a <code>TreeView</code> for a <code>Vacations</code> data structure.
 */
public class VacationsTreeView extends EObjectTreeView {
  private static final Logger LOGGER = Logger.getLogger(VacationsTreeView.class.getName());
  
  public VacationsTreeView(EObjectTreeDescriptor eObjectTreeDescriptor, boolean editMode) {
    super(null, eObjectTreeDescriptor, editMode);
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

    EObjectTreeItemContent eObjectTreeItemContent = treeItem.getValue();
    EStructuralFeature eStructuralFeature = eObjectTreeItemContent.getEStructuralFeature();
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
   * Here we check for photo files, which can be dropped on lists of VacationElement, or children of lists of VacationElement.
   * 
   * @param eObjectTreeItem the tree item.
   * @param dragboard the {@code Dragboard} information.
   * @return true if the {@code dragboard} information can be dropped on {@code eObjectTreeItem}, false otherwise.
   */
  public boolean isDropPossible(EObjectTreeItem eObjectTreeItem, Dragboard dragboard) {    
    LOGGER.info("=>");
    
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
      if (!FileUtils.isPictureFile(file)) {
        LOGGER.info("<= false (one of the files is not a picture file)");
        return false;
      }
    }
    
    // Check whether the item is a list of VacationElement
    EObjectTreeItemContent eObjectTreeItemContent = eObjectTreeItem.getValue();
    EStructuralFeature contentStructuralFeature = eObjectTreeItemContent.getEStructuralFeature();
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
    EObjectTreeItemContent parentEObjectTreeItemContent = parentEObjectTreeItem.getValue();
    contentStructuralFeature = parentEObjectTreeItemContent.getEStructuralFeature();
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
   * 
   * @param eObjectTreeItem the tree item on which the information is dropped.
   * @param dragboard the {@code Dragboard} information.
   * @return if the drop was successful, false otherwise.
   */
  public boolean handleDrop(EObjectTreeItem eObjectTreeItem, Dragboard dragboard) {
    LOGGER.info("=>");
    
    if (!isDropPossible(eObjectTreeItem, dragboard)) {
      return false;
//      event.setDropCompleted(success);
//      event.consume();
    }
    
    List<File> files = dragboard.getFiles();
    Collections.sort(files);

    // Check whether the item is a list of VacationElement, if so add the picture to the end of the list
    EObjectTreeItemContent eObjectTreeItemContent = eObjectTreeItem.getValue();
    Object object = eObjectTreeItemContent.getObject();
    EStructuralFeature contentStructuralFeature = eObjectTreeItemContent.getEStructuralFeature();
    if (contentStructuralFeature != null) {
      LOGGER.info("contentStructuralFeature=" + contentStructuralFeature.toString());
      if (contentStructuralFeature instanceof EReference contentEReference) {
        EClass contentReferenceType = contentEReference.getEReferenceType();
        VacationsPackage vacationsPackage = VacationsPackage.eINSTANCE;
        if (contentEReference.isMany()  &&  contentReferenceType.equals(vacationsPackage.getVacationElement())) {
          LOGGER.info("Yes it is a VacationElement reference");
          @SuppressWarnings("unchecked")
          EList<Object> list = (EList<Object>) object;
          for (File file: files) {
            list.add(createPicture(file));
          }
          return true;
        }
      }
    }

    
    // Check whether the parent of the item is a list of VacationElement, if so add the picture before the item.
    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();
    EObjectTreeItemContent parentEObjectTreeItemContent = parentEObjectTreeItem.getValue();
    contentStructuralFeature = parentEObjectTreeItemContent.getEStructuralFeature();
    if (contentStructuralFeature != null) {
      LOGGER.info("contentStructuralFeature=" + contentStructuralFeature.toString());
      if (contentStructuralFeature instanceof EReference contentEReference) {
        LOGGER.info("contentEReference=" + contentEReference.toString());
        EClass contentReferenceType = contentEReference.getEReferenceType();
        LOGGER.info("contentReferenceType=" + contentReferenceType.toString());
        VacationsPackage vacationsPackage = VacationsPackage.eINSTANCE;
        if (contentEReference.isMany()  &&  contentReferenceType.equals(vacationsPackage.getVacationElement())) {
          LOGGER.info("<= true (parent is a list of VacationElement reference)");
          @SuppressWarnings("unchecked")
          EList<Object> list = (EList<Object>) parentEObjectTreeItemContent.getObject();
          for (File file: files) {
            list.add(list.indexOf(object), createPicture(file));
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
    VacationsFactory vacationsFactory = VacationsFactory.eINSTANCE;
    Picture picture = vacationsFactory.createPicture();
    TypesFactory typesFactory = TypesFactory.eINSTANCE;
    FileReference fileReference = typesFactory.createFileReference();
    fileReference.setFile(file.getAbsolutePath());
    picture.setPictureReference(fileReference);
    
    return picture;
  }
}
