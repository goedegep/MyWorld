package goedegep.jfx;

import java.util.ArrayList;
import java.util.List;

import goedegep.appgen.TableRowOperation;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeListDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeListValueDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassListReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.jfx.eobjecttreeview.NodeOperationDescriptor;
import goedegep.jfx.eobjecttreeview.PresentationType;
import goedegep.properties.model.PropertiesPackage;
import goedegep.properties.model.PropertyDescriptor;

/**
 * This class creates an {@link EObjectTreeView} for a property descriptors.
 */
public class PropertyDescriptorsTreeViewCreator {
  private static final PropertiesPackage PROPERTIES_PACKAGE = PropertiesPackage.eINSTANCE;

  /**
   * Create an {@link EObjectTreeView} for property descriptors.
   * 
   * @param customization the GUI customization
   * @return an {@code EObjectTreeView} for property descriptors.
   */
  public EObjectTreeView createPropertyDescriptorsTreeView(CustomizationFx customization) {

    EObjectTreeView eObjectTreeView = new EObjectTreeView()
        .setCustomization(customization)
        .addEClassDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptorGroup(), createDescriptorForPropertyDescriptorGroup())
        .addEClassDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor(), createDescriptorForPropertyDescriptor())
        .addEClassDescriptor(PROPERTIES_PACKAGE.getFilePropertyDescriptor(), createDescriptorForFilePropertyDescriptor());

    return eObjectTreeView;
  }

  /**
   * Create a {@code EObjectTreeItemClassDescriptor} for a {@code PropertyDescriptorGroup}.
   * 
   * @return a {@code EObjectTreeItemClassDescriptor} for a {@code PropertyDescriptorGroup}.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForPropertyDescriptorGroup() {
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> "Group of Property Descriptors")
        .setExpandOnCreation(true);
    
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor;
    
    // Name
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptorGroup_Name())
        .setLabelText("Name");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Description
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptorGroup_Description())
        .setLabelText("Description")
        .setPresentationType(PresentationType.MULTI_LINE_TEXT);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // PackageName
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptorGroup_PackageName())
        .setLabelText("PackageName");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Registry class
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptorGroup_RegistryClassName())
        .setLabelText("Registry class");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Property Descriptors
    eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptorGroup_PropertyDescriptors())
        .setLabelText("Property Descriptors");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptorGroup_PropertyDescriptorGroups())
        .setLabelText("Property Descriptor groups")
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New Property Descriptors group"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create a {@code EObjectTreeItemClassDescriptor} for a {@code PropertyDescriptor}.
   * 
   * @return a {@code EObjectTreeItemClassDescriptor} for a {@code PropertyDescriptor}.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForPropertyDescriptor() {
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> {
          StringBuilder buf = new StringBuilder();
          PropertyDescriptor propertyDescriptor = (PropertyDescriptor) eObject;
          buf.append(propertyDescriptor.getName());
          buf.append(" - ");
          buf.append(propertyDescriptor.getInitialValue());
          return buf.toString();
        })
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New Property Descriptor before"))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New Property Descriptor after"))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Remove PropertyDescriptor"));
    
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;
    
    // Type
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_Type())
        .setLabelText("Type");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Name
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_Name())
        .setLabelText("Name");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Description
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_Description())
        .setLabelText("Description")
        .setPresentationType(PresentationType.MULTI_LINE_TEXT);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Display name
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_DisplayName())
        .setLabelText("Display name");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Registry name
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_RegistryName())
        .setLabelText("Registry name");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Initial value
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_InitialValue())
        .setLabelText("Initial value");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Changeable
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_UserSettable())
        .setLabelText("Changeable");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Store in Registry
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_InstallInitialValue())
        .setLabelText("Store in Registry");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

  /**
   * Create a {@code EObjectTreeItemClassDescriptor} for a {@code FilePropertyDescriptor}.
   * 
   * @return a {@code EObjectTreeItemClassDescriptor} for a {@code FilePropertyDescriptor}.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForFilePropertyDescriptor() {
    List<NodeOperationDescriptor> nodeOperationDescriptors;

    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> {
          StringBuilder buf = new StringBuilder();
          PropertyDescriptor propertyDescriptor = (PropertyDescriptor) eObject;
          buf.append(propertyDescriptor.getName());
          buf.append(" - ");
          buf.append(propertyDescriptor.getInitialValue());
          return buf.toString();
        })
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New Property Descriptor before"))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New Property Descriptor after"))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Remove PropertyDescriptor"));
    
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;
    
    // Type
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_Type())
        .setLabelText("Type");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Name
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_Name())
        .setLabelText("Name");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Description
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_Description())
        .setLabelText("Description");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Display name
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_DisplayName())
        .setLabelText("Display name");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Registry name
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_RegistryName())
        .setLabelText("Registry name");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Initial value
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_InitialValue())
        .setLabelText("Initial value");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Changeable
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_UserSettable())
        .setLabelText("Changeable");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Store in Registry
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_InstallInitialValue())
        .setLabelText("Store in Registry");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // File extensions
    EObjectTreeItemAttributeListValueDescriptor fileExtensionDescriptor = new EObjectTreeItemAttributeListValueDescriptor()
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New file extension before"))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New file extension after"))
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Remove file extension"));
    
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New file extension"));
    EObjectTreeItemAttributeListDescriptor eObjectTreeItemAttributeListDescriptor = new EObjectTreeItemAttributeListDescriptor(PROPERTIES_PACKAGE.getFilePropertyDescriptor_FileExtensions())
        .setLabelText("File extensions")
        .addNodeOperationDescriptor(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New file extension"))
        .setListValuesDescriptor(fileExtensionDescriptor);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeListDescriptor);
    
    return eObjectTreeItemClassDescriptor;
  }

}
