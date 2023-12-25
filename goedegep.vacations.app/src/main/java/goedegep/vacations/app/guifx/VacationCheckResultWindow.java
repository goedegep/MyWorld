package goedegep.vacations.app.guifx;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.types.model.FileReference;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class VacationCheckResultWindow extends JfxStage {
  
  /**
   * GUI customization
   */
  private CustomizationFx customization;
  
  private ComponentFactoryFx componentFactory;
  
  private VBox mainPanel = null;

  public VacationCheckResultWindow(VacationCheckResultWindowBuilder builder) {
    super("Results for checking vacation " + builder.vacationTitle,
        builder.customization);
    
    customization = builder.customization;
    componentFactory = customization.getComponentFactoryFx();

    createGUI();
    
    if (builder.fileReferencesNotSet != null) {
      addReferencesNotSetPanel(builder.fileReferencesNotSet);
    }
    
    if (builder.nonExistingReferences != null) {
      addNonExistingReferencesPanel(builder.nonExistingReferences);
    }
    
    if (builder.filesNotReferredTo != null) {
      addFilesNotReferredToPanel(builder.filesNotReferredTo);
    }
    
    show();
  }

  private void createGUI() {
    mainPanel = componentFactory.createVBox(12.0, 12.0);
    
    setScene(new Scene(mainPanel, 1700, 900));
  }

  private void addReferencesNotSetPanel(List<FileReference> fileReferencesNotSet) {
    VBox vBox = componentFactory.createVBox(6.0, 6.0);
    
    Label label;
    
    label = componentFactory.createStrongLabel("References for which the 'file' attribute isn't set:");
    vBox.getChildren().add(label);
    
    for (FileReference fileReference: fileReferencesNotSet) {
      label = componentFactory.createLabel("FileReference not set: " + fileReference.toString());
      vBox.getChildren().add(label);
    }
    
    mainPanel.getChildren().add(vBox);
  }

  private void addNonExistingReferencesPanel(List<FileReference> nonExistingReferences) {
    VBox vBox = componentFactory.createVBox(6.0, 6.0);
    
    for (FileReference fileReference: nonExistingReferences) {
      Label label = componentFactory.createLabel("FileReference to non existing file: " + fileReference.toString());
      vBox.getChildren().add(label);
    }
    
    mainPanel.getChildren().add(vBox);
  }

  private void addFilesNotReferredToPanel(Set<String> filesNotReferredTo) {
    VBox vBox = componentFactory.createVBox(6.0, 6.0);
    
    Label label;
    
    label = componentFactory.createStrongLabel("Files not referred to:");
    vBox.getChildren().add(label);
    
    for (String fileName: filesNotReferredTo) {
      label = componentFactory.createLabel(fileName);
      vBox.getChildren().add(label);
    }
    
    mainPanel.getChildren().add(vBox);
  }

  
  public static class VacationCheckResultWindowBuilder {
    /*
     * Required parameters
     */
    
    /**
     * GUI customization
     */
    private CustomizationFx customization;
    
    /**
     * The title of the checked vacation (to be shown as the window title).
     */
    private String vacationTitle;
    
    
    /*
     * Optional parameters
     */
    
    /**
     * References for which the 'file' attribute isn't set.
     */
    private List<FileReference> fileReferencesNotSet = null;
    
    /**
     * References to non-existing files.
     */
    private List<FileReference> nonExistingReferences = null;
    
    /**
     * Files in the vacation folder which aren't referred to.
     */
    private Set<String> filesNotReferredTo = null;
    
    
    /**
     * Constructor providing required parameters.
     * 
     * @param customization the GUI customization.
     */
    public VacationCheckResultWindowBuilder(CustomizationFx customization, String vacationTitle) {
      Objects.requireNonNull(customization, "'customization' may not be null");
      Objects.requireNonNull(vacationTitle, "'vacationTitle' may not be null");

      this.customization = customization;
      this.vacationTitle = vacationTitle;
    }

    /**
     * Set the list of file references for which the 'file' attribute isn't set.
     * 
     * @param fileReferencesNotSet the list of file references for which the 'file' attribute isn't set.
     */
    public void setReferencesNotSet(List<FileReference> fileReferencesNotSet) {
      this.fileReferencesNotSet = fileReferencesNotSet;
    }

    /**
     * Set the list of non-existing file references.
     * 
     * @param nonExistingReferences a list of non-existing file references.
     */
    public void setNonExistingReferences(List<FileReference> nonExistingReferences) {
      this.nonExistingReferences = nonExistingReferences;
    }

    /**
     * Set the list of files which aren't referred to.
     * 
     * @param nonExistingReferences a list of non-existing file references.
     */
    public void setFilesNotReferredTo(Set<String> filesNotReferredTo) {
      this.filesNotReferredTo = filesNotReferredTo;
    }    
    
    /**
     * Construct the {@code VacationCheckResultWindow}.
     * 
     * @return the created {@code VacationCheckResultWindow}.
     */
    public VacationCheckResultWindow build() {
      return new VacationCheckResultWindow(this);
    }
  }
}
