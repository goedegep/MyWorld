package goedegep.media.app;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import goedegep.configuration.model.Look;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.JfxApplication;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.media.app.guifx.MediaMenuWindow;
import goedegep.media.common.IAlbumDetailsWindow;
import goedegep.media.common.IMediaDbService;
import goedegep.media.common.IMediaService;
import goedegep.media.common.MediaAppResourcesFx;
import goedegep.media.common.MediaRegistry;
import goedegep.media.mediadb.app.MediaDbService;
import goedegep.media.mediadb.app.guifx.AlbumDetailsWindow;
import goedegep.media.mediadb.app.guifx.MediaDbWindow;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.Track;
import goedegep.myworld.common.Registry;
import goedegep.myworld.common.Service;
import goedegep.util.emf.EMFResource;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * The MediaService class is the main class for the Media application.
 * <p>
 * It provides methods to show the main media menu window and manages
 * application-wide resources and customization.
 */
public class MediaService extends Service implements IMediaService {
  private static final String NEWLINE = System.getProperty("line.separator");

  /**
   * The singleton instance of the MediaService.
   */
  private static MediaService instance = null;
  
  private MediaRegistry mediaRegistry;
  
  /**
   * Get the singleton instance of the MediaService.
   * 
   * @return the singleton instance of MediaService.
   */
  public static MediaService getInstance() {
    if (instance == null) {
      instance = new MediaService();
      instance.initialize();      
    }
    
    return instance;
  }
  
  
  /**
   * Show the main media menu window.
   */
  @Override
  public void showMediaMenuWindow() {
    Stage stage = new MediaMenuWindow(customization, this);
    stage.show();
  }
  
  public void showMediaDbWindow() {
    if (!checkThatMediaDbFileNameIsSetInRegistry()) {
      return;
    }
    
    EMFResource<MediaDb> mediaDbResource = getMediaDbResource();
    if (mediaDbResource == null) {
      return;
    }
    
    MediaDbService mediaDbService = new MediaDbService(mediaDbResource);
    Stage stage = new MediaDbWindow(customization, this, mediaDbService);
    stage.show();
  }
  
  public IAlbumDetailsWindow openAlbumDetailsWindow(IMediaDbService iMediaDbService, Map<Track, Path> trackDiscLocationMap, EObjectTable<Album> albumsTable) {
    AlbumDetailsWindow albumDetailsWindow = new AlbumDetailsWindow(customization, iMediaDbService, trackDiscLocationMap, albumsTable);
    albumDetailsWindow.show();
    return albumDetailsWindow;
  }

  
  /**
   * Private constructor to ensure that the application is a singleton.
   */
  private MediaService() {
    mediaRegistry = MediaRegistry.getInstance();
  }
  
  @Override
  protected void readApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("MediaApplication.properties")) {
        props.load(in);
        
        mediaRegistry.setVersion(props.getProperty("media.app.version"));
        mediaRegistry.setApplicationName(props.getProperty("media.app.name"));
    } catch (Exception e) {
      JfxApplication.reportException(null, e);
      System.exit(1);
    }
  }
  
  @Override
  protected void fillLook(Look look) {
    look.setBackgroundColor(Color.rgb(140,140,170));
    look.setButtonBackgroundColor(Color.rgb(100,100,130));
    look.setPanelBackgroundColor(Color.rgb(130,130,190));
    look.setListBackgroundColor(Color.rgb(135,135,180));
    look.setLabelBackgroundColor(Color.rgb(135,135,180));
    look.setBoxBackgroundColor(Color.rgb(165,165,195));
    look.setTextFieldBackgroundColor(Color.rgb(155,155,200));
  }
  
  @Override
  protected AppResourcesFx getAppResourcesFxClass() {
    return new MediaAppResourcesFx();
  }
  
  @Override
  protected Registry getRegistry() {
    return mediaRegistry;
  }

  /**
   * Check that the file name for the media database is set in the {@link MediaRegistry}.
   * <p>
   * If the file name is not set, a dialog is shown to inform the user about this and to tell him what to do.
   * 
   * @return true if the file name is set, false otherwise.
   */
  private boolean checkThatMediaDbFileNameIsSetInRegistry() {
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
          showPropertiesEditor();
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
  
  private EMFResource<MediaDb> getMediaDbResource() {
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
