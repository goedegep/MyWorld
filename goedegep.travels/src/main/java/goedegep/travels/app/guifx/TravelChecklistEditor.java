package goedegep.travels.app.guifx;

import java.io.IOException;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.util.emf.EMFResource;
import goedegep.vacations.checklist.model.VacationChecklist;
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

public class TravelChecklistEditor extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(TravelChecklistEditor.class.getName());
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
  public TravelChecklistEditor(CustomizationFx customization, EMFResource<VacationChecklist> vacationChecklistResource) {
    super(customization, WINDOW_TITLE);
    
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
    
    vacationChecklistResource.uriProperty().addListener((_, _, _) -> updateTitle());
    
    show();
  }

  private void createGUI() {
    VBox mainPanel = componentFactory.createVBox(10.0, 10.0);
    
    VacationChecklist vacationChecklist = vacationChecklistResource.getEObject();
    
    // For now there is one TreeView. I think it's better to have separate trees for label and categories, but this is currently not possible
    // because the table expects that the root of the table is a top level class (problem is in support with the XTree path).
    EObjectTreeView checklistTreeView = new TravelChecklistTreeViewCreator().createVacationChecklistTreeView(customization);
    checklistTreeView.setEObject(vacationChecklist);
    checklistTreeView.setEditMode(true);
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
    cancelButton.setOnAction((_) -> close());
    buttonsBox.getChildren().add(cancelButton);
    
    Button saveButton = componentFactory.createButton("Save", "Save changes");
    saveButton.setOnAction((_) -> saveVacationChecklist());
    buttonsBox.getChildren().add(saveButton);
        
    return  buttonsBox;
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
