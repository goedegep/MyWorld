package goedegep.vacations.app.guifx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;

import goedegep.appgen.EMFResource;
import goedegep.appgen.TableRowOperation;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttreeview.EObjectTreeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassListReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.jfx.eobjecttreeview.NodeOperationDescriptor;
import goedegep.util.emf.EmfPackageHelper;
import goedegep.vacations.checklist.model.VacationChecklist;
import goedegep.vacations.checklist.model.VacationChecklistCategory;
import goedegep.vacations.checklist.model.VacationChecklistItem;
import goedegep.vacations.checklist.model.VacationChecklistLabel;
import goedegep.vacations.checklist.model.VacationChecklistPackage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class VacationChecklistEditor extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(VacationChecklistEditor.class.getName());
  private static final String WINDOW_TITLE = "Vacation checklist editor";

  private EMFResource<VacationChecklist> vacationChecklistResource;
  private ComponentFactoryFx componentFactory;
  private Label statusLabel = new Label("");
  
  /**
   * Constructor.
   * 
   * @param customization The GUI customization.
   * @param vacations the vacations structure.
   */
  public VacationChecklistEditor(CustomizationFx customization, EMFResource<VacationChecklist> vacationChecklistResource) {
    super(WINDOW_TITLE, customization);
    
    this.vacationChecklistResource = vacationChecklistResource;
    componentFactory = customization.getComponentFactoryFx();
    
    createGUI();
    updateTitle();
    
    vacationChecklistResource.dirtyProperty().addListener(new ChangeListener<Boolean>() {

      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        updateTitle();        
      }
      
    });
    
    vacationChecklistResource.fileNameProperty().addListener(new ChangeListener<String>() {

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        updateTitle();
      }
      
    });
    
    show();
  }

  private void createGUI() {
    VBox mainPanel = componentFactory.createVBox(10.0, 10.0);
    
    VacationChecklist vacationChecklist = vacationChecklistResource.getEObject();
    
    // For now there is one TreeView. I think it's better to have separate trees for label and categories, but this is currently not possible
    // because the table expects that the root of the table is a top level class (problem is in support with the XTree path).
    EObjectTreeDescriptor checklistTreeViewDescriptor = createChecklistTreeViewDescriptor();
    EObjectTreeView checklistTreeView = new EObjectTreeView(vacationChecklist, checklistTreeViewDescriptor, true);    
    mainPanel.getChildren().addAll(checklistTreeView);
    
    // Save and cancel buttons
    mainPanel.getChildren().addAll(createButtonsPanel());
    
    // Status label
    mainPanel.getChildren().addAll(statusLabel);
    
    setScene(new Scene(mainPanel, 1700, 900));
  }

  private Node createButtonsPanel() {
    HBox buttonsBox = componentFactory.createHBox(12.0, 12.0);
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    buttonsBox.getChildren().add(spacer);
    
    Button cancelButton = componentFactory.createButton("Cancel", "Close this window without saving any changes");
    cancelButton.setOnAction(e -> close());
    buttonsBox.getChildren().add(cancelButton);
    
    Button saveButton = componentFactory.createButton("Save", "Save changes");
    saveButton.setOnAction(e -> saveVacationChecklist());
    buttonsBox.getChildren().add(saveButton);
        
    return  buttonsBox;
  }


  /**
   * Create the EObjectTreeDescriptor for the VacationChecklist tree view.
   * 
   * @return the EObjectTreeDescriptor for the VacationChecklist tree view
   */
  private EObjectTreeDescriptor createChecklistTreeViewDescriptor() {
    EObjectTreeDescriptor eObjectTreeDescriptor = new EObjectTreeDescriptor();
    EmfPackageHelper vacationChecklistPackageHelper = new EmfPackageHelper(VacationChecklistPackage.eINSTANCE);
    EClass eClass;
    List<NodeOperationDescriptor> nodeOperationDescriptors;
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor;
    
    /*
     *  VacationChecklist
     */
    eClass = vacationChecklistPackageHelper.getEClass("goedegep.vacations.checklist.model.VacationChecklist");
    eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> "Vacation checklist labels and categories", true, null);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(
        VacationChecklistPackage.eINSTANCE.getVacationChecklist_VacationChecklistLabelsList(),
        vacationChecklistPackageHelper.getEClass("goedegep.vacations.checklist.model.VacationChecklistLabelsList"), (eObject) -> "Label list", true, null));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(
        VacationChecklistPackage.eINSTANCE.getVacationChecklist_VacationChecklistCategoriesList(),
        vacationChecklistPackageHelper.getEClass("goedegep.vacations.checklist.model.VacationChecklistCategoriesList"), (eObject) -> "Categories list", true, null));
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
    
    /*
     *  VacationChecklistLabelsList
     */
    eClass = vacationChecklistPackageHelper.getEClass("goedegep.vacations.checklist.model.VacationChecklistLabelsList");
    eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> "Label list", true, null);
    
    // VacationChecklistLabelsList.vacationChecklistLabels
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New label"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VacationChecklistPackage.eINSTANCE.getVacationChecklistLabelsList_VacationChecklistLabels(), "Labels", true, nodeOperationDescriptors));

    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
    
    /*
     * VacationChecklistLabel
     */
    eClass = vacationChecklistPackageHelper.getEClass("goedegep.vacations.checklist.model.VacationChecklistLabel");
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New label before this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New label after this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Move label up"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Move label down"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete this label"));
    eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> ((VacationChecklistLabel)eObject).getName(), true, nodeOperationDescriptors);
    
    // VacationChecklistLabel.name
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VacationChecklistPackage.eINSTANCE.getVacationChecklistLabel_Name(), "name", null));

    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
    
    /*
     *  VacationChecklistCategoriesList
     */
    eClass = vacationChecklistPackageHelper.getEClass("goedegep.vacations.checklist.model.VacationChecklistCategoriesList");
    eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> "Categories list", true, null);
    
    // VacationChecklistCategoriesList.vacationChecklistCategories
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New category"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VacationChecklistPackage.eINSTANCE.getVacationChecklistCategoriesList_VacationChecklistCategories(), "Categories", true, nodeOperationDescriptors));

    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
    
    /*
     * VacationChecklistCategory
     */
    eClass = vacationChecklistPackageHelper.getEClass("goedegep.vacations.checklist.model.VacationChecklistCategory");
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New category before this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New category after this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Move category up"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Move category down"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete this category"));
    eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> ((VacationChecklistCategory)eObject).getName(), true, nodeOperationDescriptors);
    
    // VacationChecklistCategory.name
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VacationChecklistPackage.eINSTANCE.getVacationChecklistCategory_Name(), "name", null));
    
    // VacationChecklistCategory.vacationChecklistItems
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New item"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VacationChecklistPackage.eINSTANCE.getVacationChecklistCategory_VacationChecklistItems(), "Items", true, nodeOperationDescriptors));

    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
    
    /*
     * VacationChecklistItem
     */
    eClass = vacationChecklistPackageHelper.getEClass("goedegep.vacations.checklist.model.VacationChecklistItem");
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New item before this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New item after this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Move item up"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Move item down"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete this item"));
    eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> ((VacationChecklistItem)eObject).getName(), true, nodeOperationDescriptors);
    
    // VacationChecklistItem.name
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VacationChecklistPackage.eINSTANCE.getVacationChecklistItem_Name(), "name", null));
    
    // VacationChecklistItem.vacationChecklistLabels
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Add label"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VacationChecklistPackage.eINSTANCE.getVacationChecklistItem_VacationChecklistLabels(), "Labels", true, nodeOperationDescriptors));

    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);

    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
      
    return eObjectTreeDescriptor;
  }

  /**
   * Save the vacation checklist information to the related file.
   */
  private void saveVacationChecklist() {
    if (vacationChecklistResource != null) {
      try {
        vacationChecklistResource.save();
        statusLabel.setText("Vacation checklist saved to '" + vacationChecklistResource.getFileName() + "'");
      } catch (IOException e) {        
        componentFactory.createErrorDialog(
            "Saving the vacation checklist has failed.",
            "System error message: "  + e.getMessage()
            ).showAndWait();
      }
    }
  }

  /**
   * Update the window title.
   * <p>
   * The format of the title is: &lt;title&gt; - &lt;dirty&gt;&lt;file-name&gt;<br/>
   * Where:<br/>
   * &lt;title&gt; is the language specific window title<br/>
   * &lt;dirty&gt; is a '*' symbol if the vacations file is dirty, empty otherwise<br/>
   * &lt;file-name&gt; is the name of the vacations file.
   * 
   */
  private void updateTitle() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(WINDOW_TITLE);
    buf.append(" - ");
    if (vacationChecklistResource.isDirty()) {
      buf.append("*");
    }
    String fileName = vacationChecklistResource.getFileName();
    if (fileName.equals("")) {
      fileName = "<NoName>";
    }
    buf.append(fileName);
    
    setTitle(buf.toString());
  }
}
