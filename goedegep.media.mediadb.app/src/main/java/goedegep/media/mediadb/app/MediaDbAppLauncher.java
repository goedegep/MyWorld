package goedegep.media.mediadb.app;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.PropertyDescriptorsEditorFx;
import goedegep.media.common.MediaRegistry;
import goedegep.media.mediadb.app.guifx.AlbumDetailsWindow;
import goedegep.media.mediadb.app.guifx.AlbumsTable;
import goedegep.media.mediadb.app.guifx.MediaDbWindow;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.Track;
import goedegep.properties.app.guifx.PropertiesEditor;
import goedegep.util.emf.EMFResource;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class MediaDbAppLauncher {
  private static final String NEWLINE = System.getProperty("line.separator");
  
  private static MediaRegistry mediaRegistry = MediaRegistry.getInstance();
  
  public static void launchMediaApplication(CustomizationFx customization) {
    if (!checkThatMediaDbFileNameIsSetInRegistry(customization)) {
      return;
    }
    
    EMFResource<MediaDb> mediaDbResource = getMediaDbResource(customization);
    if (mediaDbResource == null) {
      return;
    }
    
    MediaDbService mediaDbService = new MediaDbService(mediaDbResource);
    Stage stage = new MediaDbWindow(customization, mediaDbService);
    stage.show();
  }
  
  public static AlbumDetailsWindow openAlbumDetailsWindow(CustomizationFx customization, MediaDbService mediaDbService, Map<Track, Path> trackDiscLocationMap, AlbumsTable albumsTable) {
    AlbumDetailsWindow albumDetailsWindow = new AlbumDetailsWindow(customization, mediaDbService, trackDiscLocationMap, albumsTable);
    
    return albumDetailsWindow;
  }

  /**
   * Open the PropertyDescriptors editor.
   */
  public static void showPropertyDescriptorsEditor(CustomizationFx customization) {
    new PropertyDescriptorsEditorFx(customization, mediaRegistry.getPropertyDescriptorsFileURI());
  }

  /**
   * Open the User Settings editor.
   */
  public static void showUserSettingsEditor(CustomizationFx customization) {
    PropertiesEditor propertiesEditor = new PropertiesEditor("Edit Media settings", customization,
        mediaRegistry.getPropertyDescriptorsFileURI(), mediaRegistry.getUserPropertiesFileName());
    propertiesEditor.show();
  }  

  /**
   * Check that the file name for the media database is set in the {@link MediaRegistry}.
   * <p>
   * If the file name is not set, a dialog is shown to inform the user about this and to tell him what to do.
   * 
   * @return true if the file name is set, false otherwise.
   */
  private static boolean checkThatMediaDbFileNameIsSetInRegistry(CustomizationFx customization) {
    if (mediaRegistry.getMediaDbFile() != null) {
      return true;
    } else {
      Alert alert = customization.getComponentFactoryFx().createErrorDialog(
          "There's no filename configured for the file with the media database",
          """
          Configure the filename (e.g. via the 'Edit User Settings' below) and restart the application.
          A restart is needed, because the settings are only read at startup.""");

      ButtonType editorButtonType = new ButtonType("Edit User Settings");
      alert.getButtonTypes().add(editorButtonType);

      alert.showAndWait().ifPresent(response -> {
        if (response == editorButtonType) {
          showUserSettingsEditor(customization);
        }
      });

      return false;
    }
  }  
  
  /**
   * Try to get (load) the MediaDb resource.
   * <p>
   * The media database is loaded from the file specified in the registry.<br/>
   * If the file doesn't exist, there are two options; either the file name in the registry is incorrect, or the file hasn't been created yet.
   * Therefore a dialog is shown asking the user whether this file shall be created or not. In the latter case the user has to correct the file name in the registry.
   * 
   * @return true if the resource could be opened, false otherwise.
   */
  private static EMFResource<MediaDb> getMediaDbResource(CustomizationFx customization) {
    boolean returnValue = false;

    EMFResource<MediaDb> mediaDbResource = new EMFResource<>(MediadbPackage.eINSTANCE, () -> MediadbFactory.eINSTANCE.createMediaDb(), ".xmi", true);

    try {
      mediaDbResource.load(mediaRegistry.getMediaDbFile());
      returnValue = true;
    } catch (IOException e) {
      Alert alert = customization.getComponentFactoryFx().createYesNoConfirmationDialog(
          null,
          "The file with media information (" + mediaRegistry.getMediaDbFile() + ") doesn't exist yet.",
          "Do you want to create this file now?" + NEWLINE +
          "If you choose \"No\" the mediaDb application will not be started.");
      Optional<ButtonType> response = alert.showAndWait();
      if (response.isPresent()  &&  response.get() == ButtonType.YES) {
        mediaDbResource.newEObject();
        try {
          mediaDbResource.save(mediaRegistry.getMediaDbFile());
          returnValue = true;
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }

    return returnValue ? mediaDbResource : null;
  }

}
