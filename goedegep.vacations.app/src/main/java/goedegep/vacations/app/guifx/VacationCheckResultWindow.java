package goedegep.vacations.app.guifx;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * This class is a window displaying the results of checking a vacation.
 */
public class VacationCheckResultWindow extends JfxStage {
    
  private VBox mainPanel = null;

  public VacationCheckResultWindow(VacationCheckResultWindowBuilder builder) {
    super(builder.customization, "Results for checking vacation " + builder.vacationTitle);

    createGUI();
    
    if (builder.fileReferencesNotSet != null  &&  !builder.fileReferencesNotSet.isEmpty()) {
      addReferencesNotSetPanel(builder.fileReferencesNotSet);
    } else {
      VBox vBox = componentFactory.createVBox(6.0, 6.0);
      Label label = componentFactory.createStrongLabel("✓ All reference have their 'file' attribute set.");
      vBox.getChildren().add(label);
      mainPanel.getChildren().add(vBox);
    }
    
    if (builder.nonExistingReferences != null  &&  !builder.nonExistingReferences.isEmpty()) {
      addNonExistingReferencesPanel(builder.nonExistingReferences);
    } else {
      VBox vBox = componentFactory.createVBox(6.0, 6.0);
      Label label = componentFactory.createStrongLabel("✓ All files referred to exist.");
      vBox.getChildren().add(label);
      mainPanel.getChildren().add(vBox);
    }
    
    if (builder.filesNotReferredTo != null  &&  !builder.filesNotReferredTo.isEmpty()) {
      addFilesNotReferredToPanel(builder.filesNotReferredTo);
    } else {
      VBox vBox = componentFactory.createVBox(6.0, 6.0);
      Label label = componentFactory.createStrongLabel("✓ The vacation folder doesn't contain files which aren't referred to.");
      vBox.getChildren().add(label);
      mainPanel.getChildren().add(vBox);
    }
    
    if (builder.photosFolderProblem != null) {
      VBox vBox = componentFactory.createVBox(6.0, 6.0);
      Label label = componentFactory.createStrongLabel("! There is a problem with the photos folder: " + builder.photosFolderProblem);
      vBox.getChildren().add(label);
      mainPanel.getChildren().add(vBox);
    }
    
    if (builder.photosNotReferredTo != null  &&  !builder.photosNotReferredTo.isEmpty()) {
      addPhotosNotReferredToPanel(builder.photosNotReferredTo);
    } else {
      VBox vBox = componentFactory.createVBox(6.0, 6.0);
      Label label = componentFactory.createStrongLabel("✓ There are no photos which aren't referred to.");
      vBox.getChildren().add(label);
      mainPanel.getChildren().add(vBox);
    }
    
    show();
  }

  private void createGUI() {
    mainPanel = componentFactory.createVBox(12.0, 12.0);
    
    setScene(new Scene(mainPanel, 1700, 900));
  }

  private void addReferencesNotSetPanel(List<String> fileReferencesNotSet) {
    VBox vBox = componentFactory.createVBox(6.0, 6.0);
    
    Label label;
    
    label = componentFactory.createStrongLabel("! References for which the 'file' attribute isn't set:");
    vBox.getChildren().add(label);
    
    for (String fileReference: fileReferencesNotSet) {
      label = componentFactory.createLabel("FileReference not set: " + fileReference);
      vBox.getChildren().add(label);
    }
    
    mainPanel.getChildren().add(vBox);
  }

  private void addNonExistingReferencesPanel(List<String> nonExistingReferences) {
    VBox vBox = componentFactory.createVBox(6.0, 6.0);
    
    Label label;
    
    label = componentFactory.createStrongLabel("References for which the referred file doesn't exist:");
    vBox.getChildren().add(label);
    
    for (String fileReference: nonExistingReferences) {
      label = componentFactory.createLabel("! FileReference to non existing file: " + fileReference);
      vBox.getChildren().add(label);
    }
    
    mainPanel.getChildren().add(vBox);
  }

  private void addFilesNotReferredToPanel(Set<String> filesNotReferredTo) {
    VBox vBox = componentFactory.createVBox(6.0, 6.0);
    
    Label label;
    
    label = componentFactory.createStrongLabel("! Files not referred to:");
    vBox.getChildren().add(label);
    
    for (String fileName: filesNotReferredTo) {
      label = componentFactory.createLabel(fileName);
      vBox.getChildren().add(label);
    }
    
    mainPanel.getChildren().add(vBox);
  }

  private void addPhotosNotReferredToPanel(List<String> photosNotReferredTo) {
    VBox vBox = componentFactory.createVBox(6.0, 6.0);
    
    Label label;
    
    label = componentFactory.createStrongLabel("! Photos not referred to:");
    vBox.getChildren().add(label);
    
    for (String fileName: photosNotReferredTo) {
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
    private List<String> fileReferencesNotSet = null;
    
    /**
     * References to non-existing files.
     */
    private List<String> nonExistingReferences = null;
    
    /**
     * Files in the vacation folder which aren't referred to.
     */
    private Set<String> filesNotReferredTo = null;
    
    /**
     * Problem with the photos folder (name)
     */
    private String photosFolderProblem = null;
    
    /**
     * Photos in the vacation photo folder which aren't referred to by the vacation.
     */
    private List<String> photosNotReferredTo = null;
    
    
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
    public void setReferencesNotSet(List<String> fileReferencesNotSet) {
      this.fileReferencesNotSet = fileReferencesNotSet;
    }

    /**
     * Set the list of non-existing file references.
     * 
     * @param nonExistingReferences a list of non-existing file references.
     */
    public void setNonExistingReferences(List<String> nonExistingReferences) {
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

    public void setPhotosFolderProblem(String photosFolderProblem) {
      this.photosFolderProblem = photosFolderProblem;
      
    }
    
    /**
     * Set the list of photos in the vacation photo folder which aren't referred to by the vacation.
     * 
     * @param photosNotReferredTo the list of photos in the vacation photo folder which aren't referred to by the vacation
     */
    public void setPhotosNotReferredTo(List<String> photosNotReferredTo) {
      this.photosNotReferredTo = photosNotReferredTo;      
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
