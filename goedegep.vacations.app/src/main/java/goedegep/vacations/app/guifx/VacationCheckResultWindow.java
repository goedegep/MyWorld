package goedegep.vacations.app.guifx;

import java.util.Objects;
import java.util.Set;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * This class is a window for displaying the results of checking a vacation.
 */
public class VacationCheckResultWindow extends JfxStage {
    
  private VBox mainPanel = null;

  /**
   * Constructor.
   * <p>
   * A builder is used to provide the parameters.
   * 
   * @param builder the {@code Builder} with the parameters for constructing the window.
   */
  public VacationCheckResultWindow(VacationCheckResultWindowBuilder builder) {
    super(builder.customization, "Results for checking vacation: " + builder.vacationTitle);

    createGUI();
    
    /*
     * Report about FileReferences without the 'file' attribute set.
     * If {@code builder.fileReferencesNotSet} is null, the check isn't performed and nothing is shown.
     * If there are such references, show them in a panel. Else show a message that all references have their 'file' attribute set.
     */
    if (builder.fileReferencesNotSet != null) {
      if (!builder.fileReferencesNotSet.isEmpty()) {
        addReferencesNotSetPanel(builder.fileReferencesNotSet);
      } else {
        mainPanel.getChildren().add(createOKMessagePanel("All reference have their 'file' attribute set."));
      }
    }
    
    /*
     * Report about files that are referred to by FileReferences but that don't exist.
     * If {@code builder.nonExistingReferences} is null, the check isn't performed and nothing is shown.
     * If there are references to non-existing files, show them in a panel. Else show a message that all referred files exist.
     */
    if (builder.nonExistingReferences != null) {
      if (!builder.nonExistingReferences.isEmpty()) {
        addNonExistingReferencesPanel(builder.nonExistingReferences);
      } else {
        mainPanel.getChildren().add(createOKMessagePanel("All files referred to exist."));
      }
    }
    
    /*
     * Report about files in the vacation folder which aren't referred to.
     * If {@code builder.filesNotReferredTo} is null, the check isn't performed and nothing is shown.
     * If there are such files, show them in a panel. Else show a message that all files are referred to.
     */
    if (builder.filesNotReferredTo != null) {
      if  (!builder.filesNotReferredTo.isEmpty()) {
        addFilesNotReferredToPanel(builder.filesNotReferredTo);
      } else {
        mainPanel.getChildren().add(createOKMessagePanel("The vacation folder doesn't contain files which aren't referred to."));
      }
    }
    
    /*
     * Report about problems with the photos folder.
     * If {@code builder.photosFolderProblem} is null, the check isn't performed and nothing is shown.
     * If there is a problem, show it in a panel. Else show a message that there is no problem.
     */
    if (builder.photosFolderProblem != null) {
      if (!builder.photosFolderProblem.isEmpty()) {
        VBox vBox = componentFactory.createVBox(6.0, 6.0);
        Label label = componentFactory.createStrongLabel("! There is a problem with the photos folder: " + builder.photosFolderProblem);
        vBox.getChildren().add(label);
        mainPanel.getChildren().add(vBox);
      } else {
        mainPanel.getChildren().add(createOKMessagePanel("There is no problem with the photos folder."));
      }
    }
    
    /*
     * Report about photos in the vacation photo folder which aren't referred to by the vacation.
     * If {@code builder.photosNotReferredTo} is null, the check isn't performed and nothing is shown.
     * If there are such photos, show them in a panel. Else show a message that all photos are referred to.
     */
    if (builder.photosNotReferredTo != null) {
      if (!builder.photosNotReferredTo.isEmpty()) {
        addPhotosNotReferredToPanel(builder.photosNotReferredTo);
      } else {
        mainPanel.getChildren().add(createOKMessagePanel("There are no photos which aren't referred to."));
      }
    }
    
    show();
  }
  
  /**
   * Create a panel with a message indicating that everything is OK.
   * 
   * @param message the message to be shown.
   * @return the created panel.
   */
  private VBox createOKMessagePanel(String message) {
    VBox vBox = componentFactory.createVBox(6.0, 6.0);
    
    Label label = componentFactory.createStrongLabel("✓ " + message);
    vBox.getChildren().add(label);
    
    return vBox;
  }

  /**
   * Create the GUI.
   * 
   * This method only creates the main panel and sets the scene.
   * Based on the check results panels will be added to the main panel.
   */
  private void createGUI() {
    mainPanel = componentFactory.createVBox(12.0, 12.0);
    ScrollPane scrollPane = new ScrollPane(mainPanel);
    
    setScene(new Scene(scrollPane));
  }

  /**
   * Add a panel reporting about file references for which the 'file' attribute isn't set.
   * <p>
   * The panel is added to the {@code mainPanel}.
   * 
   * @param fileReferencesNotSet the set of file references for which the 'file' attribute isn't set.
   */
  private void addReferencesNotSetPanel(Set<String> fileReferencesNotSet) {
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

  /**
   * Add a panel reporting about file references to non-existing files.
   * <p>
   * The panel is added to the {@code mainPanel}.
   * 
   * @param nonExistingReferences the set of file references to non-existing files.
   */
  private void addNonExistingReferencesPanel(Set<String> nonExistingReferences) {
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
  
  /**
   * Add a panel reporting about files in the vacation folder which aren't referred to.
   * <p>
   * The panel is added to the {@code mainPanel}.
   * 
   * @param filesNotReferredTo the set of files in the vacation folder which aren't referred to.
   */
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

  /**
   * Add a panel reporting about photos in the vacation photo folder which aren't referred to by the vacation.
   * <p>
   * The panel is added to the {@code mainPanel}.
   * 
   * @param photosNotReferredTo the list of photos in the vacation photo folder which aren't referred to by the vacation.
   */
  private void addPhotosNotReferredToPanel(Set<String> photosNotReferredTo) {
    VBox vBox = componentFactory.createVBox(6.0, 6.0);
    
    Label label;
    
    label = componentFactory.createStrongLabel("! Photos not referred to:");
    vBox.getChildren().add(label);
    
    TextArea textArea = componentFactory.createTextArea();
    for (String fileName: photosNotReferredTo) {
      textArea.appendText(fileName + System.lineSeparator());
    }
    vBox.getChildren().add(textArea);
    
    mainPanel.getChildren().add(vBox);
  }

  
  /**
   * This builder class is used to provide parameters for constructing a {@code VacationCheckResultWindow}.
   */
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
     * If this value is null, the check isn't performed.
     */
    private Set<String> fileReferencesNotSet = null;
    
    /**
     * References to non-existing files.
     * If this value is null, the check isn't performed.
     */
    private Set<String> nonExistingReferences = null;
    
    /**
     * Files in the vacation folder which aren't referred to.
     * If this value is null, the check isn't performed.
     */
    private Set<String> filesNotReferredTo = null;
    
    /**
     * Problem with the photos folder (name).
     * If this value is null, the check isn't performed. If it is an empty string, there is no problem.
     */
    private String photosFolderProblem = null;
    
    /**
     * Photos in the vacation photo folder which aren't referred to by the vacation.
     * If this value is null, the check isn't performed.
     */
    private Set<String> photosNotReferredTo = null;
    
    
    /**
     * Constructor providing required parameters.
     * 
     * @param customization the GUI customization.
     * @param vacationTitle the title of the checked vacation (to be shown as the window title).
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
    public void setReferencesNotSet(Set<String> fileReferencesNotSet) {
      this.fileReferencesNotSet = fileReferencesNotSet;
    }

    /**
     * Set the list of non-existing file references.
     * 
     * @param nonExistingReferences a list of non-existing file references.
     */
    public void setNonExistingReferences(Set<String> nonExistingReferences) {
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
     * Set the problem with the photos folder (name).
     * 
     * @param photosFolderProblem the problem with the photos folder (name).
     */
    public void setPhotosFolderProblem(String photosFolderProblem) {
      this.photosFolderProblem = photosFolderProblem;
      
    }
    
    /**
     * Set the list of photos in the vacation photo folder which aren't referred to by the vacation.
     * 
     * @param photosNotReferredTo the list of photos in the vacation photo folder which aren't referred to by the vacation
     */
    public void setPhotosNotReferredTo(Set<String> photosNotReferredTo) {
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
