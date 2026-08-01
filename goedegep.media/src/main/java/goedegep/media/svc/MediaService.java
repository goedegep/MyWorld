package goedegep.media.svc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import goedegep.configuration.model.Look;
import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.JfxApplication;
import goedegep.media.common.IMediaService;
import goedegep.media.common.MediaAppResourcesFx;
import goedegep.media.common.MediaRegistry;
import goedegep.media.dune.gui.DuneWindow;
import goedegep.media.gui.MediaMenuWindow;
import goedegep.media.mediadb.gui.MediaDbWindow;
import goedegep.media.mediadb.logic.MediaDbService;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.musicfolder.gui.MusicFolderWindow;
import goedegep.media.photo.photomapview.guifx.PhotoMapView;
import goedegep.media.photo.photoshow.guifx.PhotoShowBuilder;
import goedegep.media.photo.photoshow.guifx.PhotoViewerMenuWindow;
import goedegep.media.videodb.gui.VideoDbWindow;
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
 * application-wide resources and customization.<br/>
 * It also has methods to start the media components.
 */
public class MediaService extends Service implements IMediaService {
  private static final String NEWLINE = System.getProperty("line.separator");

  /**
   * The singleton instance of the MediaService.
   */
  private static MediaService instance = null;
  
  /**
   * The {@code MediaRegistry}
   */
  private MediaRegistry mediaRegistry;
  
  private static MediaDbService mediaDbService;
  
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
  
  private void getMediaDbService() {
    if (mediaDbService == null) {
      if (!checkThatMediaDbFileNameIsSetInRegistry()) {
        return;
      }

      EMFResource<MediaDb> mediaDbResource = getMediaDbResource();
      if (mediaDbResource == null) {
        return;
      }

      MediaDbService.setGUICustomization(customization);
      MediaDbService.setEMFResource(mediaDbResource);
      mediaDbService = MediaDbService.getInstance();
    }
  }
    
  @Override
  public void showMediaMenuWindow() {
    getMediaDbService();
    Stage stage = new MediaMenuWindow(customization, this, mediaDbService.getMediaDb());
    stage.show();
  }
  
  @Override
  public void showMediaDbWindow() {
    getMediaDbService();
    new MediaDbWindow(customization, this, mediaDbService).show();
  }
  
  @Override
  public void showMusicFolderWindow() {
    Stage stage = new MusicFolderWindow(customization);
    stage.show();
  }
  
  @Override
  public void showPhotoShowBuilder() {
    new PhotoShowBuilder(customization);
  }
  
  @Override
  public void showPhotoShowWindow() {
    new PhotoViewerMenuWindow(customization);
  }
  
  @Override
  public void showPhotoMapView() {
    new PhotoMapView(customization);
  }

  @Override
  public void showVideoDbWindow() {
    new VideoDbWindow(customization, this).show();;
  }
  
  @Override
  public void showDuneWindow() {
    new DuneWindow(customization).show();
  }
  
  /**
   * Private constructor to ensure that the application is a singleton.
   */
  private MediaService() {
    mediaRegistry = MediaRegistry.getInstance();
  }
  
  @Override
  protected void handleApplicationProperties() {
    Properties props = new Properties();
    try (InputStream in = getClass().getResourceAsStream("MediaApplication.properties")) {
        props.load(in);
        
        mediaRegistry.setVersion(props.getProperty("media.version"));
        mediaRegistry.setApplicationName(props.getProperty("media.name"));
        mediaRegistry.setAuthor(props.getProperty("media.author"));
        mediaRegistry.setCopyrightMessage(props.getProperty("media.copyright"));
        mediaRegistry.setShortProductInfo(props.getProperty("media.description"));
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
