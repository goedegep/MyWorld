package goedegep.pctools.filescontrolled.guifx;

import goedegep.appgen.TableRowOperation;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassListReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.jfx.eobjecttreeview.NodeOperationDescriptor;
import goedegep.jfx.eobjecttreeview.PresentationType;
import goedegep.pctools.filescontrolled.model.DescribedItem;
import goedegep.pctools.filescontrolled.model.DirectorySpecification;
import goedegep.pctools.filescontrolled.model.PCToolsPackage;

public class DiscStructureSpecificationTreeViewCreator {

  private final PCToolsPackage PC_TOOLS_PACKAGE = PCToolsPackage.eINSTANCE;

  public EObjectTreeView createDiscStructureSpecificationTreeView() {

    EObjectTreeView eObjectTreeView = new EObjectTreeView()
        .addEClassDescriptor(PC_TOOLS_PACKAGE.getDiscStructureSpecification(), createDescriptorForDiscStructureSpecification())
        .addEClassDescriptor(PC_TOOLS_PACKAGE.getDirectorySpecification(), createDescriptorForDirectorySpecification())
        .addEClassDescriptor(PC_TOOLS_PACKAGE.getDescribedItem(), createDescriptorForDescribedItem());

    return eObjectTreeView;
  }

  /**
   * Create the descriptor for the EClass goedegep.pctools.filescontrolled.model.DiscStructureSpecification.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForDiscStructureSpecification() {
    // DiscStructureSpecification (root node)
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> "Disc Structure Specification")
        .setExpandOnCreation(true);

    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor;

    // DiscStructureSpecification.directorySpecifications
    eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(PC_TOOLS_PACKAGE.getDiscStructureSpecification_DirectorySpecifications())
        .setLabelText("Directory specifications")
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Create Directory Specification"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);

    // DiscStructureSpecification.filesToIgnoreCompletely
    eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(PC_TOOLS_PACKAGE.getDiscStructureSpecification_FilesToIgnoreCompletely())
        .setLabelText("Files to ignore completely")
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New file to ignore completely"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);

    // DiscStructureSpecification.directoriesToIgnoreCompletely
    eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(PC_TOOLS_PACKAGE.getDiscStructureSpecification_DirectoriesToIgnoreCompletely())
        .setLabelText("Directories to ignore completely")
        .setExpandOnCreation(true)
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New directory to ignore completely"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);

    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.pctools.filescontrolled.model.DirectorySpecification.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForDirectorySpecification() {
    // DirectorySpecification
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> {
          StringBuilder buf = new StringBuilder();
          DirectorySpecification directorySpecification = (DirectorySpecification) eObject;
          if (directorySpecification.isSetDirectoryPath()) {
            buf.append(directorySpecification.getDirectoryPath());
          } else {
            buf.append("<name not specified>");
          }
          buf.append(": ");
          if (directorySpecification.isControlled()) {
            buf.append("(controlled)");
          } else {
            buf.append("(not controlled)");
          }

          return buf.toString();
        })
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New Directory Specification before this one ..."))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New Directory Specification after this one ..."))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete Directory Specification"));

    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;

    // DirectorySpecification.directoryPath
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PC_TOOLS_PACKAGE.getDirectorySpecification_DirectoryPath())
        .setLabelText("Directory path")
        .setPresentationType(PresentationType.FOLDER);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // DirectorySpecification.description
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PC_TOOLS_PACKAGE.getDirectorySpecification_Description())
        .setLabelText("Description")
        .setPresentationType(PresentationType.MULTI_LINE_TEXT);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // DirectorySpecification.synchronizationSpecification
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PC_TOOLS_PACKAGE.getDirectorySpecification_SynchronizationSpecification())
        .setLabelText("Synchronization Specification");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // DirectorySpecification.sourceControlSpecification
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PC_TOOLS_PACKAGE.getDirectorySpecification_SourceControlSpecification())
        .setLabelText("Source Control Specification");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // DirectorySpecification.toBeChecked
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PC_TOOLS_PACKAGE.getDirectorySpecification_ToBeChecked())
        .setLabelText("To be checked")
        .setPresentationType(PresentationType.BOOLEAN);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.pctools.filescontrolled.model.DescribedItem.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForDescribedItem() {
    // DescribedItem
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> {
          DescribedItem describedItem = (DescribedItem) eObject;

          if (describedItem.isSetItem()) {
            return describedItem.getItem();
          }
          else {
            return "<not specified>";
          }
        })
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New item before this one ..."))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New item after this one ..."))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete item"));

    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;

    // DescribedItem.item
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PC_TOOLS_PACKAGE.getDescribedItem_Item())
        .setLabelText("Item");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // DescribedItem.description
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PC_TOOLS_PACKAGE.getDescribedItem_Description())
        .setLabelText("Description")
        .setPresentationType(PresentationType.MULTI_LINE_TEXT);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    return eObjectTreeItemClassDescriptor;
  }

}
