package goedegep.jfx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EContentAdapter;

import goedegep.appgen.TableRowOperation;
import goedegep.jfx.eobjecttreeview.EObjectTreeDescriptor;
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
import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.util.emf.EMFResource;
import goedegep.util.emf.EmfPackageHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class PropertyDescriptorsEditorFx extends JfxStage {
  private static final Logger         LOGGER = Logger.getLogger(PropertyDescriptorsEditorFx.class.getName());
  private static final String WINDOW_TITLE = "Edit Property Desciptors";
  private static final PropertiesPackage PROPERTIES_PACKAGE = PropertiesPackage.eINSTANCE;
  
  private EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource;
  private PropertyDescriptorGroup propertyDescriptorGroup;
  private ComponentFactoryFx componentFactory;
  private EObjectTreeView treeView = null;
  private boolean isDirty = false;
      
  public PropertyDescriptorsEditorFx(CustomizationFx customization, EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource) {
    super(WINDOW_TITLE, customization);
    
    this.propertyDescriptorsResource = propertyDescriptorsResource;
    propertyDescriptorGroup = propertyDescriptorsResource.getEObject();
    EContentAdapter eContentAdapter = new EContentAdapter() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void notifyChanged(
          org.eclipse.emf.common.notify.Notification notification) {
        super.notifyChanged(notification);
        isDirty = true;
        updateTitle();

      }

    };
    propertyDescriptorGroup.eAdapters().add(eContentAdapter);
    componentFactory = getComponentFactory();
    
    setX(10);
    setY(20);
    
    /*
     * Main pane is a BorderPane.
     * Center is TreeView for the PropertyDescriptors.
     * Bottom is a pane with cancel and save buttons.
     */
    BorderPane mainPane = new BorderPane();
    
    EObjectTreeDescriptor eObjectTreeDescriptor = createEObjectTreeDescriptor();
    treeView = new EObjectTreeView(propertyDescriptorGroup, eObjectTreeDescriptor, true);
    treeView.setEditable(true);
    treeView.setMinWidth(600);
    
    mainPane.setCenter(treeView);
    
    mainPane.setBottom(createButtonsPane());

    setScene(new Scene(mainPane, 1600, 600));

    show();
    
    updateTitle();
    
    LOGGER.fine("<=");
  }

  private EObjectTreeDescriptor createEObjectTreeDescriptor() {
    LOGGER.info("=>");
        
    EmfPackageHelper propertiesPackageHelper = new EmfPackageHelper(PROPERTIES_PACKAGE);
    EObjectTreeDescriptor eObjectTreeDescriptor = new EObjectTreeDescriptor();
    
    createAndAddEObjectTreeDescriptorForPropertyDescriptorGroup(eObjectTreeDescriptor, propertiesPackageHelper);
    createAndAddEObjectTreeDescriptorForPropertyDescriptor(eObjectTreeDescriptor, propertiesPackageHelper);
    createAndAddEObjectTreeDescriptorForFilePropertyDescriptor(eObjectTreeDescriptor, propertiesPackageHelper);
    
    LOGGER.info("<=");
    return eObjectTreeDescriptor;
  }

  private void createAndAddEObjectTreeDescriptorForPropertyDescriptorGroup(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper propertiesPackageHelper) {
    EClass eClass = propertiesPackageHelper.getEClass("goedegep.properties.model.PropertyDescriptorGroup");
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> "Group of Property Descriptors", true, null);
    List<NodeOperationDescriptor> nodeOperationDescriptors;
    
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptorGroup_Name(), "Name", null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptorGroup_Description(), "Description", PresentationType.MULTI_LINE_TEXT, null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptorGroup_PackageName(), "PackageName", null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptorGroup_RegistryClassName(), "Registry class", null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptorGroup_PropertyDescriptors(), "Property Descriptors", false, null));
    
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New Property Descriptors group"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptorGroup_PropertyDescriptorGroups(), "Property Descriptor groups", false, nodeOperationDescriptors));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  private void createAndAddEObjectTreeDescriptorForPropertyDescriptor(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper propertiesPackageHelper) {
    EClass eClass = propertiesPackageHelper.getEClass("goedegep.properties.model.PropertyDescriptor");
    List<NodeOperationDescriptor> nodeOperationDescriptors;
   
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New Property Descriptor before"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New Property Descriptor after"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Move Property Descriptor up"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Move Property Descriptor down"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Remove PropertyDescriptor"));
    
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(propertiesPackageHelper.getEClass("goedegep.properties.model.PropertyDescriptor"),
        eObject -> {
          StringBuilder buf = new StringBuilder();
          PropertyDescriptor propertyDescriptor = (PropertyDescriptor) eObject;
          buf.append(propertyDescriptor.getName());
          buf.append(" - ");
          buf.append(propertyDescriptor.getInitialValue());
          return buf.toString();},
        false, nodeOperationDescriptors);
    
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_Type(), "Type", null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_Name(), "Name", null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_Description(), "Description", PresentationType.MULTI_LINE_TEXT, null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_DisplayName(), "Display name", null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_RegistryName(), "Registry name", null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_InitialValue(), "Initial value", null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_UserSettable(), "Changeable", null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_InstallInitialValue(), "Store in Registry", null));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  private void createAndAddEObjectTreeDescriptorForFilePropertyDescriptor(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper propertiesPackageHelper) {
    EClass eClass = propertiesPackageHelper.getEClass("goedegep.properties.model.FilePropertyDescriptor");
    List<NodeOperationDescriptor> nodeOperationDescriptors;

    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New Property Descriptor before"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New Property Descriptor after"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Move Property Descriptor up"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Move Property Descriptor down"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Remove PropertyDescriptor"));
   
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(propertiesPackageHelper.getEClass("goedegep.properties.model.FilePropertyDescriptor"),
        eObject -> {
          StringBuilder buf = new StringBuilder();
          PropertyDescriptor propertyDescriptor = (PropertyDescriptor) eObject;
          buf.append(propertyDescriptor.getName());
          buf.append(" - ");
          buf.append(propertyDescriptor.getInitialValue());
          return buf.toString();},
        false, nodeOperationDescriptors);
    
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_Type(), "Type", null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_Name(), "Name", null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_Description(), "Description", null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_DisplayName(), "Display name", null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_RegistryName(), "Registry name", null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_InitialValue(), "Initial value", null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_UserSettable(), "Changeable", null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(PROPERTIES_PACKAGE.getPropertyDescriptor_InstallInitialValue(), "Store in Registry", null));
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New file extension before"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New file extension after"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Move file extension up"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Move file extension down"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Remove file extension"));
    EObjectTreeItemAttributeListValueDescriptor fileExtensionDescriptor = new EObjectTreeItemAttributeListValueDescriptor(false, nodeOperationDescriptors);
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New file extension"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeListDescriptor(PROPERTIES_PACKAGE.getFilePropertyDescriptor_FileExtensions(), "File extensions", false, nodeOperationDescriptors,
        fileExtensionDescriptor));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the pane with the 'Cancel' and 'Save changes' buttons.
   * 
   * @return the created buttons pane.
   */
  private Node createButtonsPane() {
    HBox buttonPane = new HBox(20d);
    buttonPane.setPadding(new Insets(10d, 15d, 10d, 15d));
    
    Button button = componentFactory.createButton("Cancel", "Close window without saving changes");
    button.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        closeWindow();
      }
      
    });
    buttonPane.getChildren().add(button);
    
    button = componentFactory.createButton("Save changes", "Changes are effective only after restarting the application");
    button.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        try {
          propertyDescriptorsResource.save();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        isDirty = false;
        updateTitle();
      }
      
    });
    buttonPane.getChildren().add(button);

    return buttonPane;
  }
  
  private void closeWindow() {
    this.close();
  }

  private void updateTitle() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(WINDOW_TITLE);
    buf.append(" - ");
    if (isDirty) {
      buf.append("*");
    }
    buf.append(propertyDescriptorsResource.getFileName());
    
    setTitle(buf.toString());
  }
}
