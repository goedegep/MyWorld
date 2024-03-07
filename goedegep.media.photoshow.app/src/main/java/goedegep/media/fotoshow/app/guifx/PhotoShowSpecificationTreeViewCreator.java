package goedegep.media.fotoshow.app.guifx;

import goedegep.appgen.TableRowOperation;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeListDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeListValueDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassListReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.jfx.eobjecttreeview.NodeOperationDescriptor;
import goedegep.media.photoshow.model.FolderTimeOffsetSpecification;
import goedegep.media.photoshow.model.PhotoShowPackage;
import goedegep.media.photoshow.model.PhotoShowSpecification;

/**
 * This class creates an {@link EObjectTreeView} for a {@link PhotoShowSpecification}.
 */
public class PhotoShowSpecificationTreeViewCreator {
  
  private final PhotoShowPackage PHOTO_SHOW_PACKAGE = PhotoShowPackage.eINSTANCE;

  /**
   * Create an {@link EObjectTreeView} for a {@link PhotoShowSpecification}.
   * 
   * @param customization the GUI customization
   * @return an {@code EObjectTreeView} for a {@code GPXTrack} vacation element.
   */
  public EObjectTreeView createPhotoShowSpecificationTreeView(CustomizationFx customization) {

    EObjectTreeView eObjectTreeView = new EObjectTreeView()
        .setCustomization(customization)
        .addEClassDescriptor(PHOTO_SHOW_PACKAGE.getPhotoShowSpecification(), createDescriptorForPhotoShowSpecification())
        .addEClassDescriptor(PHOTO_SHOW_PACKAGE.getFolderTimeOffsetSpecification(), createDescriptorForFolderTimeOffsetSpecification());

    return eObjectTreeView;
  }

  /**
   * Create a descriptor for a {@code PhotoShowSpecification}.
   * 
   * @return a descriptor for a {@code PhotoShowSpecification}.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForPhotoShowSpecification() {
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> "Photo show specification")
        .setExpandOnCreation(true);

    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;
    EObjectTreeItemAttributeListDescriptor eObjectTreeItemAttributeListDescriptor;

    // PhotoShowSpecification.title
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PHOTO_SHOW_PACKAGE.getPhotoShowSpecification_Title())
        .setLabelText("Title");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // PhotoShowSpecification.photoFolders
    EObjectTreeItemAttributeListValueDescriptor photoFolderDescriptor = new EObjectTreeItemAttributeListValueDescriptor()
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New photo folder before"))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New photo folder after"))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Remove photo folder"));

    eObjectTreeItemAttributeListDescriptor = new EObjectTreeItemAttributeListDescriptor(PHOTO_SHOW_PACKAGE.getPhotoShowSpecification_PhotoFolders())
        .setLabelText("Photo folders")
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New photo folder"))
        .setListValuesDescriptor(photoFolderDescriptor);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeListDescriptor);

    // PhotoShowSpecification.foldertimeoffsetspecifications
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(PHOTO_SHOW_PACKAGE.getPhotoShowSpecification_FolderTimeOffsetSpecifications())
        .setLabelText("Folder time offsets")
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New time offset"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);

    // PhotoShowSpecification.photosToShow
    EObjectTreeItemAttributeListValueDescriptor photoToShowDescriptor = new EObjectTreeItemAttributeListValueDescriptor()
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New photo before"))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New photo after"))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Remove photo"));

    eObjectTreeItemAttributeListDescriptor = new EObjectTreeItemAttributeListDescriptor(PHOTO_SHOW_PACKAGE.getPhotoShowSpecification_PhotosToShow())
        .setLabelText("Photos to show")
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New photo to show"))
        .setListValuesDescriptor(photoToShowDescriptor);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeListDescriptor);

   return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create a descriptor for a {@code FolderTimeOffsetSpecification}.
   * 
   * @return a descriptor for a {@code FolderTimeOffsetSpecification}.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForFolderTimeOffsetSpecification() {
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> {
          StringBuilder buf = new StringBuilder();
          FolderTimeOffsetSpecification folderTimeOffsetSpecification = (FolderTimeOffsetSpecification) eObject;
          String folderName = folderTimeOffsetSpecification.getFolderName();
          if ((folderName != null)  &&  !folderName.isEmpty()) {
            buf.append(folderName);
          } else {
            buf.append("(no folder specified)");
          }
          buf.append(" - ");
          String timeOffsetAsString = folderTimeOffsetSpecification.getTimeOffset();
          if ((timeOffsetAsString != null)  &&  !timeOffsetAsString.isEmpty()) {
            buf.append(timeOffsetAsString);
          } else {
            buf.append("(no time offset specified)");
          }

          return buf.toString();
        })
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New time offset before this one"))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New time offset after this one"))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete this time offset"));

    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;

    // PhotoShowSpecification.foldertimeoffsetspecifications.FolderTimeOffsetSpecification.folderName
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PHOTO_SHOW_PACKAGE.getFolderTimeOffsetSpecification_FolderName())
        .setLabelText("Folder");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // PhotoShowSpecification.foldertimeoffsetspecifications.FolderTimeOffsetSpecification.timeOffset
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PHOTO_SHOW_PACKAGE.getFolderTimeOffsetSpecification_TimeOffset())
        .setLabelText("Time offset");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    return eObjectTreeItemClassDescriptor;
  }

}
