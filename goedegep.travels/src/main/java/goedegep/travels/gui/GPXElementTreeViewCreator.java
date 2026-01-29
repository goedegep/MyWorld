package goedegep.travels.gui;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.travels.model.GPXTrack;
import goedegep.travels.model.TravelsPackage;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesPackage;

/**
 * This class creates an {@link EObjectTreeView} for a {@link GPXTrack} vacation element.
 */
public class GPXElementTreeViewCreator {
  private final TravelsPackage vacationsPackage = TravelsPackage.eINSTANCE;
  private final TypesPackage typesPackage = TypesPackage.eINSTANCE;
    
  /**
   * Create an {@link EObjectTreeView} for a {@link GPXTrack} vacation element.
   * 
   * @param customization the GUI customization
   * @return an {@code EObjectTreeView} for a {@code GPXTrack} vacation element.
   */
  public EObjectTreeView createGPXElementTreeView(CustomizationFx customization) {

    EObjectTreeView eObjectTreeView = new EObjectTreeView()
        .setCustomization(customization)
        .addEClassDescriptor(typesPackage.getFileReference(), createEObjectTreeDescriptorForFileReference())
        .addEClassDescriptor(vacationsPackage.getGPXTrack(), createEObjectTreeDescriptorForGPXTrack());
    
    return eObjectTreeView;
  }
  
  /**
   * Create the descriptor for the EClass goedegep.types.model.FileReference.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the FileReference descriptor is to be added.
   */
  private EObjectTreeItemClassDescriptor createEObjectTreeDescriptorForFileReference() {

    // FileReference
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> {
          FileReference fileReference = (FileReference) eObject;
          if (fileReference != null) {
            return fileReference.getTitle();
          } else {
            return "<no file reference>";
          }
        })
        .setExpandOnCreation(true);
    
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;
    
    // FileReference.title
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(typesPackage.getFileReference_Title())
        .setLabelText("Titel");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // FileReference.file
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(typesPackage.getFileReference_File())
        .setLabelText("File")
        .setOpenDialog(false);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.VacationElementGPX.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the VacationElementGPX descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private EObjectTreeItemClassDescriptor createEObjectTreeDescriptorForGPXTrack() {
    
    // VacationElementGPX (extends VacationElement)
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setExpandOnCreation(true);
    
    // VacationElementGPX.trackReference
    EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(vacationsPackage.getGPXTrack_TrackReference())
        .setNodeTextFunction(eObject -> "GPX track reference")
        .setExpandOnCreation(true);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);
    
    // VacationElementGPX.children not applicable here
    
    return eObjectTreeItemClassDescriptor;
  }

}
