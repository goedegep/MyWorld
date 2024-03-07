package goedegep.vacations.app.guifx;

import goedegep.appgen.TableRowOperation;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassListReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.jfx.eobjecttreeview.NodeOperationDescriptor;
import goedegep.vacations.checklist.model.VacationChecklist;
import goedegep.vacations.checklist.model.VacationChecklistCategory;
import goedegep.vacations.checklist.model.VacationChecklistItem;
import goedegep.vacations.checklist.model.VacationChecklistLabel;
import goedegep.vacations.checklist.model.VacationChecklistPackage;

/**
 * This class creates an {@link EObjectTreeView} for a {@link VacationChecklist}.
 */
public class VacationChecklistTreeViewCreator {
  private final VacationChecklistPackage VACATION_CHECKLIST_PACKAGE = VacationChecklistPackage.eINSTANCE;

  /**
   * Create an {@link EObjectTreeView} for a {@link VacationChecklist}.
   * 
   * @param customization the GUI customization
   * @return an {@link EObjectTreeView} for a {@link VacationChecklist}.
   */
  public EObjectTreeView createVacationChecklistTreeView(CustomizationFx customization) {

    EObjectTreeView eObjectTreeView = new EObjectTreeView()
        .setCustomization(customization)
        .addEClassDescriptor(VACATION_CHECKLIST_PACKAGE.getVacationChecklist(), createDescriptorForVacationChecklist())
        .addEClassDescriptor(VACATION_CHECKLIST_PACKAGE.getVacationChecklistLabelsList(), createDescriptorForVacationChecklistLabelsList())
        .addEClassDescriptor(VACATION_CHECKLIST_PACKAGE.getVacationChecklistLabel(), createDescriptorForVacationChecklistLabel())
        .addEClassDescriptor(VACATION_CHECKLIST_PACKAGE.getVacationChecklistCategoriesList(), createDescriptorForVacationChecklistCategoriesList())
        .addEClassDescriptor(VACATION_CHECKLIST_PACKAGE.getVacationChecklistCategory(), createDescriptorForVacationChecklistCategory())
        .addEClassDescriptor(VACATION_CHECKLIST_PACKAGE.getVacationChecklistItem(), createDescriptorForVacationChecklistItem());

    return eObjectTreeView;
  }
  
  /**
   * Create an {@code eObjectTreeItemClassDescriptor} for a {@code VacationChecklist}.
   * 
   * @return an {@code eObjectTreeItemClassDescriptor} for a {@code VacationChecklist}.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForVacationChecklist() {
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> "Vacation checklist labels and categories")
        .setExpandOnCreation(true);
    EObjectTreeItemClassReferenceDescriptor eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(VACATION_CHECKLIST_PACKAGE.getVacationChecklist_VacationChecklistLabelsList())
        .setNodeTextFunction(eObject -> "Label list")
        .setExpandOnCreation(true);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);
    eObjectTreeItemClassReferenceDescriptor = new EObjectTreeItemClassReferenceDescriptor(VACATION_CHECKLIST_PACKAGE.getVacationChecklist_VacationChecklistCategoriesList())
        .setNodeTextFunction(eObject -> "Categories list")
        .setExpandOnCreation(true);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassReferenceDescriptor);
    return eObjectTreeItemClassDescriptor;
  }
  
  /**
   * Create an {@code eObjectTreeItemClassDescriptor} for a {@code VacationChecklistLabelsList}.
   * 
   * @return an {@code eObjectTreeItemClassDescriptor} for a {@code VacationChecklistLabelsList}.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForVacationChecklistLabelsList() {
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> "Label list")
        .setExpandOnCreation(true);
    
    // VacationChecklistLabelsList.vacationChecklistLabels
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(VACATION_CHECKLIST_PACKAGE.getVacationChecklistLabelsList_VacationChecklistLabels())
        .setLabelText("Labels")
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New label"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);

    return eObjectTreeItemClassDescriptor;
  }
  
  /**
   * Create an {@code eObjectTreeItemClassDescriptor} for a {@code VacationChecklistLabel}.
   * 
   * @return an {@code eObjectTreeItemClassDescriptor} for a {@code VacationChecklistLabel}.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForVacationChecklistLabel() {
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> ((VacationChecklistLabel)eObject).getName())
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New label before this one ..."))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New label after this one ..."))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete this label"));
    
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;
    
    // VacationChecklistLabel.name
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(VACATION_CHECKLIST_PACKAGE.getVacationChecklistLabel_Name())
        .setLabelText("name");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    return eObjectTreeItemClassDescriptor;
  }
  
  /**
   * Create an {@code eObjectTreeItemClassDescriptor} for a {@code VacationChecklistCategoriesList}.
   * 
   * @return an {@code eObjectTreeItemClassDescriptor} for a {@code VacationChecklistCategoriesList}.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForVacationChecklistCategoriesList() {
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> "Categories list")
        .setExpandOnCreation(true);
    
    // VacationChecklistCategoriesList.vacationChecklistCategories
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(VACATION_CHECKLIST_PACKAGE.getVacationChecklistCategoriesList_VacationChecklistCategories())
        .setLabelText("Categories")
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New category"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);

    return eObjectTreeItemClassDescriptor;
  }
  
  /**
   * Create an {@code eObjectTreeItemClassDescriptor} for a {@code VacationChecklistCategory}.
   * 
   * @return an {@code eObjectTreeItemClassDescriptor} for a {@code VacationChecklistCategory}.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForVacationChecklistCategory() {
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> ((VacationChecklistCategory)eObject).getName())
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New category before this one ..."))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New category after this one ..."))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete this category"));
    
    // VacationChecklistCategory.name
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(VACATION_CHECKLIST_PACKAGE.getVacationChecklistCategory_Name())
        .setLabelText("name");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // VacationChecklistCategory.vacationChecklistItems
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(VACATION_CHECKLIST_PACKAGE.getVacationChecklistCategory_VacationChecklistItems())
        .setLabelText("Items")
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New item"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);

    return eObjectTreeItemClassDescriptor;
  }
  
  /**
   * Create an {@code eObjectTreeItemClassDescriptor} for a {@code VacationChecklistItem}.
   * 
   * @return an {@code eObjectTreeItemClassDescriptor} for a {@code VacationChecklistItem}.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForVacationChecklistItem() {
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> ((VacationChecklistItem)eObject).getName())
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New item before this one ..."))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New item after this one ..."))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete this item"));
    
    // VacationChecklistItem.name
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(VACATION_CHECKLIST_PACKAGE.getVacationChecklistItem_Name())
        .setLabelText("name");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // VacationChecklistItem.vacationChecklistLabels
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(VACATION_CHECKLIST_PACKAGE.getVacationChecklistItem_VacationChecklistLabels())
        .setLabelText("Labels")
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Add label"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);

    return eObjectTreeItemClassDescriptor;
  }
}
