package goedegep.vacations.app.guifx;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.jfx.eobjecttreeview.PresentationType;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesPackage;
import goedegep.vacations.model.GPXTrack;
import goedegep.vacations.model.VacationsPackage;

/**
 * This class creates an {@link EObjectTreeView} for a {@link GPXTrack} vacation element.
 */
public class GPXElementTreeViewCreator {
  private final VacationsPackage vacationsPackage = VacationsPackage.eINSTANCE;
  private final TypesPackage typesPackage = TypesPackage.eINSTANCE;
    
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
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(
          eObject -> {
            FileReference fileReference = (FileReference) eObject;
            if (fileReference != null) {
              return fileReference.getTitle();
            } else {
              return "<no file reference>";
            }
          },
          true, null);
    
    // FileReference.title
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(typesPackage.getFileReference_Title(), "Titel", null));
    
    // FileReference.file
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(typesPackage.getFileReference_File(), "File", PresentationType.FILE, null);
    eObjectTreeItemAttributeDescriptor.setOpenDialog(false);
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
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(null, true, null, null);
    
    // VacationElementGPX.trackReference
    TypesPackage typesPackage = TypesPackage.eINSTANCE;
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(vacationsPackage.getGPXTrack_TrackReference(), typesPackage.getFileReference(), (eObject) -> "GPX track reference", true, null));
    
    // VacationElementGPX.children not applicable here
    
    return eObjectTreeItemClassDescriptor;
  }

}
