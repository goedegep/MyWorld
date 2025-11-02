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

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.objectcontrols.ObjectControlFileSelecter;
import goedegep.jfx.objectcontrols.ObjectControlFolderSelecter;
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
 * The list of photos can either be defined by a Photoshow specification or it can be all files in a folder.
 *
 */
public class PhotoShowViewer extends JfxStage {
  private final static Logger LOGGER = Logger.getLogger(PhotoShowViewer.class.getName());
  
  private final static String WINDOW_TITLE = "Photoshow builder";
  
  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;
  
  private File currentPhotospecificationFile = null;
  private File currentPhotosFolder = null;
  private PhotoWindow currentPhotoWindow = null;
  
  /**
   * Constructor
   * 
   * @param customization The GUI customization
   */
  public PhotoShowViewer(CustomizationFx customization) {
    super(customization, WINDOW_TITLE);
    
    this.customization = customization;
    componentFactory = customization.getComponentFactoryFx();
    
    createGUI();

    show();
    
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
    gridPane.add(label, 0, row);
    
    String initiallySelectedFolder = MediaRegistry.getInstance().getPhotosFolder();
    ObjectControlFileSelecter fileSelecter = componentFactory.createFileSelecterObjectControl(
        300,
        "Enter Photoshow specification file name",
        "Select Photoshow specification",
        "Click to select a Photoshow specification via a file selecter",
        "Select a Photoshow specification", false);
    fileSelecter.addFileType(PhotoshowCommons.DEFAULT_PHOTOSHOW_SPECIFICATION_FILE_EXTENSION, "Photoshow specification file", true);
    fileSelecter.addFileType(".*", "Any file", false);
    fileSelecter.setInitialFolderProvider(() -> initiallySelectedFolder);
    gridPane.add(fileSelecter.getControl(), 1, row);
    gridPane.add(fileSelecter.getFileChooserButton(), 2, row);
    fileSelecter.addListener((_) -> {
      File photoshowSpecificationFile = fileSelecter.getValue();
      LOGGER.severe("photoshowSpecificationFile: " + photoshowSpecificationFile);
      if (photoshowSpecificationFile.exists()  &&
          photoshowSpecificationFile.isFile()  &&
          ".psw".equals(FileUtils.getFileExtension(photoshowSpecificationFile))) {
        LOGGER.severe("Opening show");
        currentPhotospecificationFile = photoshowSpecificationFile;
        startPhotoshowFromSpecification();
      } else {
        LOGGER.severe("Skipping");
      }
    });
    
    row++;
    
    // Second row: "Run from files from a folder:" <Folder text field> <open folder chooser button>
    label = componentFactory.createLabel("Run from files from a folder");
    gridPane.add(label, 0, row);
    
    ObjectControlFolderSelecter folderSelecter = componentFactory.createFolderSelecter(
        300,
        "Enter folder that contains the photos to show",
        "Select photos folder",
        "Click to select a photos folder via a folder selecter",
        "Select a photos folder",
        false);
    folderSelecter.setInitialFolderProvider(() -> initiallySelectedFolder);
    gridPane.add(folderSelecter.getControl(), 1, row);
    gridPane.add(folderSelecter.getFolderChooserButton(), 2, row);
    folderSelecter.addListener((_) -> {
      File photosFolder = folderSelecter.getValue();
      LOGGER.severe("photosFolder: " + photosFolder);
      if (photosFolder.exists()  &&
          photosFolder.isDirectory()) {
        LOGGER.severe("Opening show");
        currentPhotosFolder = photosFolder;
        startPhotoshowForFolder();
      } else {
        LOGGER.severe("Skipping");
      }
    });
    
    topLevelVBox.getChildren().add(gridPane);
    
    Scene scene = new Scene(topLevelVBox, 1300, 950);
    setScene(scene);
  }
  
  /**
   * Start a Photoshow.
   * <p>
   * If there is a show running, this will be closed.<br/>
   * The {@code currentPhotospecificationFile} will be opened and a {@code PhotoWindow} is opened with the photos to show and the title.
   */
  private void startPhotoshowFromSpecification() {
    if (currentPhotoWindow != null) {
      currentPhotoWindow.close();
    }
    
    try {
      EMFResource<PhotoShowSpecification> emfResource = new EMFResource<PhotoShowSpecification>(
          PhotoShowPackage.eINSTANCE,
          () -> PhotoShowFactory.eINSTANCE.createPhotoShowSpecification(),
          ".xmi");
      PhotoShowSpecification photoShowSpecification = emfResource.load(currentPhotospecificationFile.getAbsolutePath());
      List<String> photosToShow = photoShowSpecification.getPhotosToShow();
      currentPhotoWindow = new PhotoWindow(customization, photosToShow, photoShowSpecification.getTitle());
    } catch (IOException e) {
      e.printStackTrace();
    } catch (WrappedException wrappedException) {
      componentFactory.createExceptionDialog("An exception occurred while reading the file: '" + currentPhotospecificationFile.getAbsolutePath() + "'.", wrappedException).show();
    }
  }
  
  /**
   * Start a Photoshow with the files from the {@code currentPhotosFolder}.
   * <p>
   * If there is a show running, this will be closed.<br/>
   * The {@code currentPhotosFolder} is scanned for image files and a {@code PhotoWindow} is opened with this list of files. There is no title in this case.
   */
  private void startPhotoshowForFolder() {
    List<String> photosToShow = new ArrayList<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentPhotosFolder.toPath())) {

      for (Path path: stream) {
        if (!Files.isDirectory(path)) {
          String filename = path.toString();
          if (FileUtils.isPictureFile(filename)) {
            photosToShow.add(filename);
          }
        }
      }
      
      currentPhotoWindow = new PhotoWindow(customization, photosToShow, null);
      
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    
  }
}
