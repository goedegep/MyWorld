package goedegep.properties.app.guifx;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
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

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
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

  private static EPackage editablePropertiesPackage = null;

  /*
   * The created EditablePropertiesGroup items.
   */
  private static EClass editablePropertyGroup = null;
  private static EAttribute editablePropertyGroup_name = null;
  private static EReference editablePropertyGroup_editableProperties = null;
  
  /*
   * The created EditableProperty items
   */
  private static EClass editableProperty = null;
  private static EAttribute editableProperty_name = null;
  private static EAttribute editableProperty_displayName = null;
  private static EAttribute editableProperty_description = null;
  private static EAttribute editableProperty_value = null;
  
  private EObject editableProperties;

  private String windowTitle = null;
  private ResourceBundle resourceBundle;
  private EMFResource<PropertyDescriptorGroup> propertyDescriptorsResource;
  private EMFResource<PropertyGroup> propertiesResource;
  private Path propertiesFilePath;
  private EContentAdapter eContentAdapter = null;  // to detect changes in the resource.
  private boolean isDirty = false;
  
  /**
   * Create the EMF data model for editable properties.
   */
  static {
    createEditablePropertyClasses();
  }
  
  /**
   * Constructor for NOT using a resource bundle.
   * <p>
   * This constructor creates and shows the editor.
   * 
   * @param windowTitle the window title
   * @param customization the GUI customization
   * @param propertyDescriptorsURI the URI to the PropertyDescriptors file.
   * @param propertiesFilePath name of the Properties file to be edited.
   */
  public PropertiesEditor(String windowTitle, CustomizationFx customization, URI propertyDescriptorsURI, Path propertiesFilePath) {
    this(windowTitle, customization, null, propertyDescriptorsURI, propertiesFilePath);
  }
  
  /**
   * Constructor for using a resource bundle
   * <p>
   * This constructor creates and shows the editor.
   * 
   * @param windowTitle the window title
   * @param customization the GUI customization
   * @param resourceBundle translations
   * @param propertyDescriptorsURI the URI to the PropertyDescriptors file.
   * @param propertiesFilePath name of the Properties file to be edited.
   */
  public PropertiesEditor(String windowTitle, CustomizationFx customization, ResourceBundle resourceBundle, URI propertyDescriptorsURI, Path propertiesFilePath) {
    Objects.requireNonNull(windowTitle, "Argument windowTitle may not be null");
    Objects.requireNonNull(propertyDescriptorsURI, "Argument propertyDescriptorsURI may not be null");
    Objects.requireNonNull(propertiesFilePath, "Argument propertiesFileName may not be null");

    super(customization, null);  // window title is filled/updated via method updateTitle()
    
    
    this.windowTitle = windowTitle;
    this.resourceBundle = resourceBundle;
    
    propertyDescriptorsResource = new EMFResource<PropertyDescriptorGroup>(PropertiesPackage.eINSTANCE, () -> PropertiesFactory.eINSTANCE.createPropertyDescriptorGroup(), ".xmi");
    try {
      propertyDescriptorsResource.load(propertyDescriptorsURI);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    propertyDescriptorGroup = propertyDescriptorsResource.getEObject();
    
    propertiesResource = new EMFResource<PropertyGroup>(PropertiesPackage.eINSTANCE, () -> PropertiesFactory.eINSTANCE.createPropertyGroup(), ".xmi");
    
    editableProperties = createEditableProperties();
    
    this.propertiesFilePath = propertiesFilePath;
    
    if (Files.exists(propertiesFilePath)) {
      fillEditablePropertiesFromPropertiesFile(editableProperties);
    } else {
      componentFactory.createInformationDialog(
          "User Settings file doesn't exist yet",
          "The file with the User Settings (" + propertiesFilePath + ") doesn't exist yet",
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
    
    EMFResource<EObject> emfResource = new EMFResource<EObject>(editablePropertiesPackage, null, ".xmi");
    emfResource.setEObject(editableProperties);
    editablePropertiesTreeView = createPropertiesTreeView(customization).setEObject(editableProperties);
    editablePropertiesTreeView.setMinWidth(800);
    editablePropertiesTreeView.setStyle("-fx-border-style: solid inside;");
    mainPane.setCenter(editablePropertiesTreeView);
    
    mainPane.setBottom(createButtonsPane());
    
    Scene scene = new Scene(mainPane, 1200, 950);
    setScene(scene);
    show();
  }
  
  private EObjectTreeView createPropertiesTreeView(CustomizationFx customization) {

    EObjectTreeView eObjectTreeView = new EObjectTreeView()
        .setCustomization(customization)
        .addEClassDescriptor(editablePropertyGroup, createDescriptorForEditablePropertyGroup())
        .addEClassDescriptor(editableProperty, createDescriptorForEditableProperty());

    return eObjectTreeView;
  }
  
  /**
   * Create an <code>EObjectTreeItemClassDescriptor</code> for the <code>editablePropertyGroup</code> and add it to an <code>EObjectTreeDescriptor</code>.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForEditablePropertyGroup() {
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(_ -> "User settings")
        .setExpandOnCreation(true);
    
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;
    EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor;
    
    // Name
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(editablePropertyGroup_name)
        .setLabelText("Name");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // Editable properties
    eObjectTreeItemClassListReferenceDescriptor = new EObjectTreeItemClassListReferenceDescriptor(editablePropertyGroup_editableProperties)
        .setLabelText("Settings");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemClassListReferenceDescriptor);
        
    return eObjectTreeItemClassDescriptor;
  }
    
  /**
   * Create an <code>EObjectTreeItemClassDescriptor</code> for the <code>editableProperty</code> and add it to an <code>EObjectTreeDescriptor</code>.
   */
  private EObjectTreeItemClassDescriptor createDescriptorForEditableProperty() {
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor()
        .setNodeTextFunction(eObject -> (String) eObject.eGet(editableProperty_displayName))
        .setExpandOnCreation(true);
    
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor;
    
    // Description
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(editableProperty_description)
        .setLabelText("Description")
        .setPresentationType(PresentationType.MULTI_LINE_TEXT);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    // User value
    eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(editableProperty_value)
        .setLabelText("User value");
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
    
    return eObjectTreeItemClassDescriptor;
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
      EObject ep = editablePropertiesFactory.create(editableProperty);
      ep.eSet(editableProperty_name, propertyDescriptor.getName());
      ep.eSet(editableProperty_displayName, propertyDescriptor.getDisplayName());
      String description = null;
      if (resourceBundle != null) {
        try {
          description = resourceBundle.getString(getQualifiedGroupName(propertyDescriptorGroup) + "." + propertyDescriptor.getName() + ".description");
        } catch (Exception e) {
          // ignore
        }
      }
      if (description == null) {
        description = propertyDescriptor.getDescription();
      }
      ep.eSet(editableProperty_description, description);
      editableProperties.add(ep);
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
      PropertyGroup propertyGroup = propertiesResource.load(propertiesFilePath.toString());

      fillEditablePropertiesGroupFromPropertyGroup(editablePropertyGroup, propertyGroup);

    } catch (IOException e) {
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
    assert editablePropertyGroupName.equals(propertyGroup.getName());
    
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
 
      if (propertiesResource.getFileName() != null) {
        propertiesResource.save();
      } else {
        propertiesResource.save(propertiesFilePath.toString());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    // notify that there aren't any changes
    isDirty = false;
    updateTitle();
  }
  
  /**
   * Copy the values of the <code>editableProperties</code> to the <code>propertiesResource</code>.
   */
  private void copyEditablePropertyValuesToPropertiesResource() {
    PropertyGroup propertyGroup = propertiesResource.getEObject();
    if (propertyGroup == null) {
      propertyGroup = propertiesResource.newEObject();
    }
    copyEditablePropertyGroupValuesToCustomProperties(editableProperties, propertyGroup);
  }
  
  /**
   * Copy the values of a <code>editablePropertyGroup</code> to a <code>PropertyGroup</code>.
   * 
   * @param editablePropertyGroup the <code>editablePropertyGroup</code> from which to copy the property values.
   * @param propertyGroup the <code>PropertyGroup</code> to copy the values into.
   */
  private void copyEditablePropertyGroupValuesToCustomProperties(EObject editablePropertyGroup, PropertyGroup propertyGroup) {
    // Copy the values of the editable properties to the corresponding Properties.
    // Some properties may have to be added, other may have to be cleared/removed.
    // Therefore the simplest way is to remove all existing properties and add necessary values.
    @SuppressWarnings("unchecked")
    EList<EObject> editableProperties = (EList<EObject>) editablePropertyGroup.eGet(editablePropertyGroup_editableProperties);
    EList<Property> properties = propertyGroup.getProperties();
    properties.clear();
    for (EObject editableProperty: editableProperties) {
      String name = (String) editableProperty.eGet(editableProperty_name);
      String value = (String) editableProperty.eGet(editableProperty_value);
      if (value == null  ||  value.isEmpty()) {
        continue;
      }
      
      Property property = PropertiesFactory.eINSTANCE.createProperty();
      property.setName(name);
      property.setValue(value);
      properties.add(property);      
    }
    
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
    buf.append(propertiesResource.getFileName());
    
    setTitle(buf.toString());
  }
}
