package goedegep.properties.app.guifx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EContentAdapter;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttreeview.EObjectTreeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassListReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.jfx.eobjecttreeview.PresentationType;
import goedegep.properties.model.PropertiesFactory;
import goedegep.properties.model.PropertiesPackage;
import goedegep.properties.model.Property;
import goedegep.properties.model.PropertyDescriptor;
import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.properties.model.PropertyGroup;
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


/**
 * This class provides an editor for properties defined via a {@link PropertyDescriptorGroup}.
 */
public class PropertiesEditor extends JfxStage {
  /**
   * Strategy:
   * The actual editor is an EObjectTreeView.
   * The EMF model for this view is dynamically generated upon first instantiation of this class.
   */
  private static final Logger         LOGGER = Logger.getLogger(PropertiesEditor.class.getName());
  
  /**
   * The PropertyDescriptorGroup describing the properties to be edited.
   */
  private PropertyDescriptorGroup propertyDescriptorGroup;
  private EObjectTreeView editablePropertiesTreeView = null;
  
  /*
   * The created EditablePropertiesGroup items.
   */
  private static EPackage editablePropertiesPackage = null;
  private static EClass editablePropertyGroup = null;
  private static EAttribute editablePropertyGroup_name = null;
  private static EReference editablePropertyGroup_editablePropertyGroups = null;
  private static EReference editablePropertyGroup_editableProperties = null;
  
  /*
   * The created EditableProperty items
   */
  private static EClass editableProperty = null;
  private static EAttribute editableProperty_name = null;
  private static EAttribute editableProperty_displayName = null;
  private static EAttribute editableProperty_description = null;
  private static EAttribute editableProperty_defaultValue = null;
  private static EAttribute editableProperty_value = null;
  
  private EObject editableProperties;

  private String windowTitle = null;
  private ResourceBundle resourceBundle;
  private EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource;
  private EMFResource<PropertyGroup> propertiesResource;
  private String propertiesFileName;
  private ComponentFactoryFx componentFactory;
  private EContentAdapter eContentAdapter = null;  // to detect changes in the resource.
  private boolean isDirty = false;
  
  /**
   * Create the EMF data model for editable properties.
   */
  static {
    createEditablePropertyClasses();
  }
  

  /**
   * Constructor
   * <p>
   * This constructor creates and shows the editor.
   * 
   * @param windowTitle the window title
   * @param customization the GUI customization
   * @param propertyDescriptorsResource an EMFResource for a PropertyDescriptorGroup.
   * @param propertiesFileName name of the Properties file to be edited.
   */
  public PropertiesEditor(String windowTitle, CustomizationFx customization,
      EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource, String propertiesFileName) {
    this(windowTitle, customization, null, propertyDescriptorsResource, propertiesFileName);
  }

  /**
   * Constructor
   * <p>
   * This constructor creates and shows the editor.
   * 
   * @param windowTitle the window title
   * @param customization the GUI customization
   * @param resourceBundle translations
   * @param propertyDescriptorsResource an EMFResource for a PropertyDescriptorGroup.
   * @param propertiesFileName name of the Properties file to be edited.
   */
  public PropertiesEditor(String windowTitle, CustomizationFx customization, ResourceBundle resourceBundle,
      EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource, String propertiesFileName) {
    super(null, customization);
    
    if (propertyDescriptorsResource == null) {
      throw new IllegalArgumentException("Argument propertyDescriptorsResource may not be null");
    }
    
    if (propertiesFileName == null) {
      throw new IllegalArgumentException("Argument propertiesFileName may not be null");
    }
    
    this.windowTitle = windowTitle;
    this.resourceBundle = resourceBundle;
    this.propertyDescriptorsResource = propertyDescriptorsResource;
    
    propertyDescriptorGroup = propertyDescriptorsResource.getEObject();
    componentFactory = getComponentFactory();
    
    propertiesResource = new EMFResource<PropertyGroup>(PropertiesPackage.eINSTANCE, () -> PropertiesFactory.eINSTANCE.createPropertyGroup(), ".xmi");
    
    editableProperties = createEditableProperties();
    
    File propertiesFile = new File(propertiesFileName);
    this.propertiesFileName = propertiesFile.getAbsolutePath();
    
    if (propertiesFile.exists()) {
      System.out.println("File exists");
      fillEditablePropertiesFromPropertiesFile(editableProperties);
    } else {
      componentFactory.createInformationDialog(
          "User Settings file doesn't exist yet",
          "The file with the User Settings (" + propertiesFileName + ") doesn't exist yet",
          "When you save changes it will be created").showAndWait();
    }
    
    // Setup for indicating in the window title that something is changed.
    eContentAdapter = new EContentAdapter() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void notifyChanged(Notification notification) {
        super.notifyChanged(notification);
        isDirty = true;
        updateTitle();
      }

    };
    
    editableProperties.eAdapters().add(eContentAdapter);
    
    createGUI();
    
    updateTitle();
  }
  
  /**
   * Create the classes for editing in the EObjectTreeView.
   * <p>
   * The following are created:
   * <ul>
   * <li>
   * EPackage editablePropertiesPackage
   * </li>
   * <li>
   * EClass editableProperty<br/>
   * An editableProperty has the following attributes:
   * <ul>
   * <li>displayName - the display name, which will be filled from the displayName from the PropertyDescriptor</li>
   * <li>description - the description, which will be filled from the description from the PropertyDescriptor</li>
   * <li>defaultValue - the initial value, which will be filled from the initialValue from the PropertyDescriptor</li>
   * <li>value - the user defined value (initally empty). This is the value to be saved to the Properties file</li>
   * </ul>
   * </li>
   * <li>
   * EClass editablePropertyGroup<br/>
   * An editablePropertyGroup has the following structural features:
   * <ul>
   * <li>name - the name of the group, filled from the PropertyDescriptorGroup and PropertyGroup</li>
   * <li>a list of child editablePropertyGroups</li>
   * <li>a list of editableProperties</li>
   * </ul>
   * </li>
   * </ul>
   * All attribute values are of type String.
   */
  private static void createEditablePropertyClasses() {
    // Create the EPackage editablePropertiesPackage
    editablePropertiesPackage = EcoreFactory.eINSTANCE.createEPackage();
    
    // Create the EClass editableProperty
    editableProperty = EcoreFactory.eINSTANCE.createEClass();
    editableProperty.setName("EditableProperty");
    EList<EStructuralFeature> structuralFeatures = editableProperty.getEStructuralFeatures();
    
    editableProperty_name = EcoreFactory.eINSTANCE.createEAttribute();
    editableProperty_name.setName("name");
    editableProperty_name.setEType(EcorePackage.eINSTANCE.getEString());
    structuralFeatures.add(editableProperty_name);
    
    editableProperty_displayName = EcoreFactory.eINSTANCE.createEAttribute();
    editableProperty_displayName.setName("displayName");
    editableProperty_displayName.setEType(EcorePackage.eINSTANCE.getEString());
    structuralFeatures.add(editableProperty_displayName);
    
    editableProperty_description = EcoreFactory.eINSTANCE.createEAttribute();
    editableProperty_description.setName("description");
    editableProperty_description.setEType(EcorePackage.eINSTANCE.getEString());
    structuralFeatures.add(editableProperty_description);
    
    editableProperty_defaultValue = EcoreFactory.eINSTANCE.createEAttribute();
    editableProperty_defaultValue.setName("defaultValue");
    editableProperty_defaultValue.setEType(EcorePackage.eINSTANCE.getEString());
    structuralFeatures.add(editableProperty_defaultValue);
    
    editableProperty_value = EcoreFactory.eINSTANCE.createEAttribute();
    editableProperty_value.setName("value");
    editableProperty_value.setEType(EcorePackage.eINSTANCE.getEString());
    structuralFeatures.add(editableProperty_value);
    
    editablePropertiesPackage.getEClassifiers().add(editableProperty);
    
    // Create the EClass editablePropertyGroup
    editablePropertyGroup = EcoreFactory.eINSTANCE.createEClass();
    editablePropertyGroup.setName("EditablePropertyGroup");
    structuralFeatures = editablePropertyGroup.getEStructuralFeatures();
   
    editablePropertyGroup_name = EcoreFactory.eINSTANCE.createEAttribute();
    editablePropertyGroup_name.setName("name");
    editablePropertyGroup_name.setEType(EcorePackage.eINSTANCE.getEString());
    structuralFeatures.add(editablePropertyGroup_name);
    
    editablePropertyGroup_editablePropertyGroups = EcoreFactory.eINSTANCE.createEReference();
    editablePropertyGroup_editablePropertyGroups.setName("editablePropertyGroups");
    editablePropertyGroup_editablePropertyGroups.setUpperBound(-1);
    editablePropertyGroup_editablePropertyGroups.setContainment(true);
    editablePropertyGroup_editablePropertyGroups.setEType(editablePropertyGroup);
    structuralFeatures.add(editablePropertyGroup_editablePropertyGroups);
    
    editablePropertyGroup_editableProperties = EcoreFactory.eINSTANCE.createEReference();
    editablePropertyGroup_editableProperties.setName("editableProperties");
    editablePropertyGroup_editableProperties.setUpperBound(-1);
    editablePropertyGroup_editableProperties.setContainment(true);
    editablePropertyGroup_editableProperties.setEType(editableProperty);
    structuralFeatures.add(editablePropertyGroup_editableProperties);
    
    editablePropertiesPackage.getEClassifiers().add(editablePropertyGroup);
  }

  /**
   * Create the main window of the application.
   * <p>
   * Layout:
   * Top level is VBox:
   *   Top is a Tree view of the properties
   *   Bottom is the buttons panel: cancel and save.
   *   
   */
  private void createGUI() {
    BorderPane mainPane = new BorderPane();
    
    EObjectTreeDescriptor eObjectTreeDescriptor = createEObjectTreeDescriptor();
    editablePropertiesTreeView = new EObjectTreeView(editableProperties, eObjectTreeDescriptor, true);
    editablePropertiesTreeView.setMinWidth(800);
    editablePropertiesTreeView.setStyle("-fx-border-style: solid inside;");
    editablePropertiesTreeView.setEditMode(true);
    mainPane.setCenter(editablePropertiesTreeView);
    
    mainPane.setBottom(createButtonsPane());
    
    Scene scene = new Scene(mainPane, 1200, 950);
    setScene(scene);
    show();
  }

  /**
   * Create the descriptor for the EObjectTree.
   * 
   * @return the created EObjectTreeDescriptor.
   */
  private EObjectTreeDescriptor createEObjectTreeDescriptor() {
    LOGGER.info("=>");
    
    EmfPackageHelper propertiesPackageHelper = new EmfPackageHelper(editablePropertiesPackage);
    EObjectTreeDescriptor eObjectTreeDescriptor = new EObjectTreeDescriptor();

    createAndAddEObjectTreeDescriptorForEditablePropertyGroup(eObjectTreeDescriptor, propertiesPackageHelper);
    createAndAddEObjectTreeDescriptorForEditableProperty(eObjectTreeDescriptor, propertiesPackageHelper);
    
    LOGGER.info("<=");
    return eObjectTreeDescriptor;
  }
  
  /**
   * Create an <code>EObjectTreeItemClassDescriptor</code> for the <code>editablePropertyGroup</code> and add it to an <code>EObjectTreeDescriptor</code>.
   * 
   * @param eObjectTreeDescriptor the <code>EObjectTreeDescriptor</code> to add the descriptor to.
   * @param propertiesPackageHelper an <code>EmfPackageHelper</code> for the <code>editablePropertiesPackage</code>.
   */
  private void createAndAddEObjectTreeDescriptorForEditablePropertyGroup(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper propertiesPackageHelper) {
    EClass eClass = editablePropertyGroup;
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor((eObject) -> "Group of user changeable properties", true, null);
    
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(editablePropertyGroup_name, "Name", null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(editablePropertyGroup_editableProperties, "User changeable properties", true, null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(editablePropertyGroup_editablePropertyGroups, "Sub groups", false, null));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }
    
  /**
   * Create an <code>EObjectTreeItemClassDescriptor</code> for the <code>editableProperty</code> and add it to an <code>EObjectTreeDescriptor</code>.
   * 
   * @param eObjectTreeDescriptor the <code>EObjectTreeDescriptor</code> to add the descriptor to.
   * @param propertiesPackageHelper an <code>EmfPackageHelper</code> for the <code>editablePropertiesPackage</code>.
   */
  private void createAndAddEObjectTreeDescriptorForEditableProperty(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper propertiesPackageHelper) {
    EClass eClass = editableProperty;
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor((eObject) -> (String) eObject.eGet(editableProperty_displayName), true, null);
    
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(editableProperty_description, "Description", PresentationType.MULTI_LINE_TEXT, null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(editableProperty_defaultValue, "Default value", null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(editableProperty_value, "User value", null));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }
  
  /**
   * Create the EditableProperties structure to show in the EObjectTreeView.
   * <p>
   * The structure is derived from the content of the code>propertyDescriptorGroup</code>.
   * 
   * @return an <code>EObject</code> of type <code>editablePropertyGroup</code> created from the code>propertyDescriptorGroup</code>.
   */
  private EObject createEditableProperties() {
    EFactory editablePropertiesFactory = editablePropertiesPackage.getEFactoryInstance();
    
    EObject editablePropertyGroupEObject = editablePropertiesFactory.create(editablePropertyGroup);
    editablePropertyGroupEObject.eSet(editablePropertyGroup_name, propertyDescriptorGroup.getName());
    
    @SuppressWarnings("unchecked")
    EList<EObject> editableProperties = (EList<EObject>) editablePropertyGroupEObject.eGet(editablePropertyGroup_editableProperties);
    for (PropertyDescriptor propertyDescriptor: propertyDescriptorGroup.getPropertyDescriptors()) {
      if (propertyDescriptor.isUserSettable()) {
        EObject ep = editablePropertiesFactory.create(editableProperty);
        ep.eSet(editableProperty_name, propertyDescriptor.getName());
        ep.eSet(editableProperty_displayName, propertyDescriptor.getDisplayName());
        String description = null;
        if (resourceBundle != null) {
          description = resourceBundle.getString(getQualifiedGroupName(propertyDescriptorGroup) + "." + propertyDescriptor.getName() + ".description");
        }
        if (description == null) {
          description = propertyDescriptor.getDescription();
        }
        ep.eSet(editableProperty_description, description);
        ep.eSet(editableProperty_defaultValue, propertyDescriptor.getInitialValue());
        //      private static EAttribute editableProperty_value = null;
        editableProperties.add(ep);
      }
    }
    
    return editablePropertyGroupEObject;
  }
  
  private static String getQualifiedGroupName(PropertyDescriptorGroup propertyDescriptorGroup) {
    String qualifiedGroupName = propertyDescriptorGroup.getName();
    
    while (propertyDescriptorGroup.eContainer() != null) {
      propertyDescriptorGroup = (PropertyDescriptorGroup) propertyDescriptorGroup.eContainer();
      qualifiedGroupName = propertyDescriptorGroup.getName() + "." + qualifiedGroupName;
    }
    
    return qualifiedGroupName;
  }
  
  private void fillEditablePropertiesFromPropertiesFile(EObject editablePropertyGroup) {
    try {
      PropertyGroup propertyGroup = propertiesResource.load(propertiesFileName);

      String propertyGroupName = propertyGroup.getName();
      String editablePropertyGroupName = (String) editablePropertyGroup.eGet(editablePropertyGroup_name);
      if (!propertyGroupName.contentEquals(editablePropertyGroupName)) {
        LOGGER.severe("Group names don't match");
        LOGGER.severe("propertyGroupName=" + propertyGroupName + ", editablePropertyGroupName=" + editablePropertyGroupName);

        return;
      }
      fillEditablePropertiesGroupFromPropertyGroup(editablePropertyGroup, propertyGroup);

    } catch (FileNotFoundException e) {
      LOGGER.severe("FileNotFoundException");
      e.printStackTrace();
    }
  }
  
  /**
   * Fill in the values from a PropertyGroup (of a Properties file) into an EditablePropertyGroup (of the editableProperties).
   * 
   * @param editablePropertyGroup the EditablePropertyGroup in which the property values will be filled in.
   * @param propertyGroup the PropertyGroup from which the values are filled in in the <code>editablePropertyGroup</code>.
   */
  private void fillEditablePropertiesGroupFromPropertyGroup(EObject editablePropertyGroup, PropertyGroup propertyGroup) {
    LOGGER.info("=> propertyGroup=" + propertyGroup.getName());
    
    String editablePropertyGroupName = (String) editablePropertyGroup.eGet(editablePropertyGroup_name);
    assert(editablePropertyGroupName.equals(propertyGroup.getName()));
    
    @SuppressWarnings("unchecked")
    EList<EObject> editableProperties = (EList<EObject>) editablePropertyGroup.eGet(editablePropertyGroup_editableProperties);
    
    // Fill in the property values
    for (Property property: propertyGroup.getProperties()) {
      LOGGER.info("Handling: " + property.getName() + " - " + property.getValue());
      
      EObject editablePropertyForProperty = null;
      for (EObject editableProperty: editableProperties) {
        String editablePropertyName = (String) editableProperty.eGet(editableProperty_name);
        if (editablePropertyName.equals(property.getName())) {
          editablePropertyForProperty = editableProperty;
          LOGGER.info("Match found for property: " + editablePropertyName);
          break;
        }
      }
      
      if (editablePropertyForProperty != null) {
        editablePropertyForProperty.eSet(editableProperty_value, property.getValue());
      } else {
        LOGGER.severe("No match found for property: " + property.getName());
      }
    }
    
    // Handle any sub groups
    @SuppressWarnings("unchecked")
    EList <EObject> editablePropertySubGroups = (EList<EObject>) editablePropertyGroup.eGet(editablePropertyGroup_editablePropertyGroups);
    for (PropertyGroup subPropertyGroup: propertyGroup.getPropertyGroups()) {
      for (EObject editablePropertySubGroup: editablePropertySubGroups) {
        String editablePropertySubGroupName = (String) editablePropertySubGroup.eGet(editablePropertyGroup_name);
        if (editablePropertySubGroupName.equals(subPropertyGroup.getName())) {
          fillEditablePropertiesGroupFromPropertyGroup(editablePropertySubGroup, subPropertyGroup);
        }
      }
    }
    
    LOGGER.info("<=");
  }
  
  /**
   * Save the current editor values to the Properties file.
   */
  private void savePropertiesFile() {
    System.out.println("=>");
    
    // Copies all user values from the editable properties structure to the properties of the resource
    copyEditablePropertyValuesToPropertiesResource();
    
    // save changes
    try {
      System.out.println("trying");

      if (propertiesResource.getFileName() != null) {
        System.out.println("saving to file: " + propertiesResource.getFileName());

        propertiesResource.save();
        
        System.out.println("saved");

      } else {
        System.out.println("saving2");
        propertiesResource.save(propertiesFileName);
        System.out.println("saved2");
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("IOException: " + e.getMessage());
    }
    
    // notify that there aren't any changes
    isDirty = false;
    System.out.println("updateTitle");
    updateTitle();

    System.out.println("=>");
  }
  
  /**
   * Copy the values of the <code>editableProperties</code> to the <code>propertiesResource</code>.
   */
  private void copyEditablePropertyValuesToPropertiesResource() {
    LOGGER.severe("=>");
    
    PropertyGroup propertyGroup = propertiesResource.getEObject();
    if (propertyGroup == null) {
      propertyGroup = propertiesResource.newEObject();
    }
    copyEditablePropertyGroupValuesToCustomProperties(editableProperties, propertyGroup);
    
    LOGGER.severe("<=");
  }
  
  /**
   * Copy the values of a <code>editablePropertyGroup</code> to a <code>PropertyGroup</code>.
   * 
   * @param editablePropertyGroup the <code>editablePropertyGroup</code> from which to copy the property values.
   * @param propertyGroup the <code>PropertyGroup</code> to copy the values into.
   */
  private void copyEditablePropertyGroupValuesToCustomProperties(EObject editablePropertyGroup, PropertyGroup propertyGroup) {
    LOGGER.severe("=> propertyGroup.getName()=" + propertyGroup.getName());
    
    // Copy the values of the editable properties to the corresponding Properties.
    // Some properties may have to be added, other may have to be cleared/removed.
    // Therefore the simplest way is to remove all existing properties and add necessary values.
    @SuppressWarnings("unchecked")
    EList<EObject> editableProperties = (EList<EObject>) editablePropertyGroup.eGet(editablePropertyGroup_editableProperties);
    EList<Property> properties = propertyGroup.getProperties();
    properties.clear();
    for (EObject editableProperty: editableProperties) {
      String name = (String) editableProperty.eGet(editableProperty_name);
      LOGGER.severe("Handling property: " + name);
      String value = (String) editableProperty.eGet(editableProperty_value);
      if (value == null  ||  value.isEmpty()) {
        LOGGER.severe("Skipping empty value, name: " + name);
        continue;
      }
      
      Property property = PropertiesFactory.eINSTANCE.createProperty();
      property.setName(name);
      property.setValue(value);
      properties.add(property);      
    }
    
    // Handle the sub groups
    @SuppressWarnings("unchecked")
    EList<EObject> editablePropertyGroups = (EList<EObject>) editablePropertyGroup.eGet(editablePropertyGroup_editablePropertyGroups);
    EList<PropertyGroup> propertyGroups = propertyGroup.getPropertyGroups();
    for (EObject editablePropertySubGroup: editablePropertyGroups) {
      PropertyGroup propertySubGroupToHandle = null;
      for (PropertyGroup propertySubGroup: propertyGroups) {
        String name = (String) editablePropertySubGroup.eGet(editablePropertyGroup_name);
        if (propertySubGroup.getName().equals(name)) {
          propertySubGroupToHandle = propertySubGroup;
          break;
        }
        
        if (propertySubGroupToHandle != null) {
          copyEditablePropertyGroupValuesToCustomProperties(editablePropertySubGroup, propertySubGroupToHandle);
        } else {
          LOGGER.severe("No corresponding group found");
        }
      }
    }
    
    LOGGER.severe("<=");
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
        savePropertiesFile();
      }
      
    });
    buttonPane.getChildren().add(button);

    return buttonPane;
  }
  
  private void closeWindow() {
    this.close();
  }

  /**
   * Update the window title.
   * <p>
   * The format of the title is: &lt;title&gt; - &lt;dirty&gt;&lt;file-name&gt;<br/>
   * Where:<br/>
   * &lt;title&gt; is the window title<br/>
   * &lt;dirty&gt; is a '*' symbol if the properties file is dirty, empty otherwise<br/>
   * &lt;file-name&gt; is the name of the properties file.
   * 
   */
  private void updateTitle() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(windowTitle);
    buf.append(" - ");
    if (isDirty) {
      buf.append("*");
    }
    buf.append(propertyDescriptorsResource.getFileName());
    
    setTitle(buf.toString());
  }
}
