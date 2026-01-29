package goedegep.media.photo.photoshow.guifx;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.WrappedException;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.editor.controls.EditorControlFileSelecter;
import goedegep.jfx.editor.controls.EditorControlFolderSelecter;
import goedegep.media.common.MediaRegistry;
import goedegep.media.photo.photoshow.logic.PhotoshowCommons;
import goedegep.media.photoshow.model.PhotoShowFactory;
import goedegep.media.photoshow.model.PhotoShowPackage;
import goedegep.media.photoshow.model.PhotoShowSpecification;
import goedegep.util.emf.EMFResource;
import goedegep.util.file.FileUtils;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * This class is the main window for the Photo show player.
 * <p>
 * The list of photos can either be defined by a Photoshow specification, it can be all files in a folder or it can be provided as a list of files.<br/>
 * On the first photo an optional title is shown.
 *
 */
public class PhotoViewerMenuWindow extends JfxStage {
  private final static Logger LOGGER = Logger.getLogger(PhotoViewerMenuWindow.class.getName());
  
  private final static String WINDOW_TITLE = "Photoshow viewer";
  
  /**
   * The current window showing the photos.
   */
  private PhotoWindow currentPhotoWindow = null;
  
  /**
   * Constructor
   * 
   * @param customization The GUI customization
   */
  public PhotoViewerMenuWindow(CustomizationFx customization) {
    super(customization, WINDOW_TITLE);
    
    createGUI();

    show();
    
//    File file = new File("D:\\Photo\\Vakanties\\2025-08-30 Zuid Duitsland\\Pixel 7\\PXL_20250831_111347745.PHOTOSPHERE.jpg");
//    new Panorama360Viewer(file);
  }
  
  /**
   * Create the GUI.
   */
  private void createGUI() {
    VBox topLevelVBox = componentFactory.createVBox(12.0, 12.0);
    
    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0);
    Label label;
    int row = 0;
    
    // First row: "Run from a Photoshow specification:" <File text field> <open file chooser button> <show all checkbox>
    label = componentFactory.createLabel("Run from a Photoshow specification:");
    
    String initiallySelectedFolder = MediaRegistry.getInstance().getPhotosFolder();    
    EditorControlFileSelecter fileSelecter = componentFactory.createEditorControlFileSelecter(
        300,
        "Enter Photoshow specification file name",
        "Select Photoshow specification",
        "Click to select a Photoshow specification via a file selecter",
        "Select a Photoshow specification", false);
    fileSelecter.addFileType(PhotoshowCommons.DEFAULT_PHOTOSHOW_SPECIFICATION_FILE_EXTENSION, "Photoshow specification file", true);
    fileSelecter.addFileType(".*", "Any file", false);
    fileSelecter.setInitialFolderProvider(() -> initiallySelectedFolder);
    fileSelecter.addValueAndOrStatusChangeListener((_, _) -> {
      File photoshowSpecificationFile = fileSelecter.getValue();
      if (photoshowSpecificationFile.exists()  &&
          photoshowSpecificationFile.isFile()  &&
          ".psw".equals(FileUtils.getFileExtension(photoshowSpecificationFile))) {
        startPhotoshowFromSpecification(photoshowSpecificationFile);
      } else {
        LOGGER.severe("Skipping");
      }
    });
    gridPane.addRow(row, label, fileSelecter.getControl(), fileSelecter.getFileChooserButton());
    
    row++;
    
    // Second row: "Run from files from a folder:" <Folder text field> <open folder chooser button>
    label = componentFactory.createLabel("Run from files from a folder");
    
    EditorControlFolderSelecter folderSelecter = componentFactory.createEditorControlFolderSelecter(
        300,
        "Enter folder that contains the photos to show",
        "Select photos folder",
        "Click to select a photos folder via a folder selecter",
        "Select a photos folder",
        false);
    folderSelecter.setInitialFolderProvider(() -> initiallySelectedFolder);
    folderSelecter.addValueAndOrStatusChangeListener((_, _) -> {
      File photosFolder = folderSelecter.getValue();
      if (photosFolder.exists()  &&
          photosFolder.isDirectory()) {
        startPhotoshowForFolder(photosFolder);
      } else {
        LOGGER.severe("Skipping");
      }
    });
    gridPane.addRow(row, label, folderSelecter.getControl(), folderSelecter.getFolderChooserButton());
    
    topLevelVBox.getChildren().add(gridPane);
    
    Scene scene = new Scene(topLevelVBox);
    setScene(scene);
  }
  
  /**
   * Start a Photoshow from a {@code PhotoShowSpecification}.
   * <p>
   * If there is a show running, this will be closed.<br/>
   * The {@code photoshowSpecificationFile} will be opened and a {@code PhotoWindow} is opened with the photos to show and the title.
   * 
   * @param photoshowSpecificationFile The Photoshow specification file
   */
  private void startPhotoshowFromSpecification(File photoshowSpecificationFile) {
    if (currentPhotoWindow != null) {
      currentPhotoWindow.close();
      currentPhotoWindow = null;
    }
    
    try {
      EMFResource<PhotoShowSpecification> emfResource = new EMFResource<PhotoShowSpecification>(
          PhotoShowPackage.eINSTANCE,
          () -> PhotoShowFactory.eINSTANCE.createPhotoShowSpecification(),
          ".xmi");
      PhotoShowSpecification photoShowSpecification = emfResource.load(photoshowSpecificationFile.toURI());
      List<String> photosToShow = photoShowSpecification.getPhotosToShow();
      currentPhotoWindow = new PhotoWindow(customization, photosToShow, photoShowSpecification.getTitle(), null);
    } catch (IOException e) {
      e.printStackTrace();  // should not occur as we already checked existence.
    } catch (WrappedException wrappedException) {
      componentFactory.createExceptionDialog("An exception occurred while reading the file: '" + photoshowSpecificationFile.getAbsolutePath() + "'.", wrappedException).show();
    }
  }
  
  /**
   * Start a Photoshow with the files from the {@code currentPhotosFolder}.
   * <p>
   * If there is a show running, this will be closed.<br/>
   * The {@code currentPhotosFolder} is scanned for image files and a {@code PhotoWindow} is opened with this list of files. There is no title in this case.
   */
  private void startPhotoshowForFolder(File photosFolder) {
    if (currentPhotoWindow != null) {
      currentPhotoWindow.close();
      currentPhotoWindow = null;
    }
    
    List<String> photosToShow = new ArrayList<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(photosFolder.toPath())) {

      for (Path path: stream) {
        if (!Files.isDirectory(path)) {
          String filename = path.toString();
          if (FileUtils.isPictureFile(filename)) {
            photosToShow.add(filename);
          }
        }
      }
      
      currentPhotoWindow = new PhotoWindow(customization, photosToShow, null, null);
      
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    
  }
}
