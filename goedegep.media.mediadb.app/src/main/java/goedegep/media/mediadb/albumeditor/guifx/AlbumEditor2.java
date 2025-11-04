package goedegep.media.mediadb.albumeditor.guifx;

import java.util.Objects;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditPanel;
import goedegep.jfx.editor.EditorTemplate;
import goedegep.media.common.IMediaDbService;
import goedegep.media.mediadb.app.MediaDbService;
import goedegep.media.mediadb.model.Album;


/**
 * This class provides a window to show and edit an album (of type {@link Album}).
 */
public class AlbumEditor2 extends EditorTemplate<Album> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(AlbumEditor2.class.getName());
  private static final String WINDOW_TITLE = "Album editor";
  
  
  private IMediaDbService mediaDbService;
  
  /**
   * Main {@code EditPanel}.
   */
  private AlbumEditPanel albumEditPanel;
  
  
  /**
   * Factory method to obtain a new instance of an {@code AlbumEditor2}.
   * 
   * @param customization the GUI customization.
   * @param mediaDbService the media database service.
   * @return a newly created {@code AlbumEditor2}.
   */
  public static AlbumEditor2 newInstance(CustomizationFx customization, MediaDbService mediaDbService) {
    Objects.requireNonNull(mediaDbService, "mediaDbService may not be null");
    
    AlbumEditor2 albumEditor = new AlbumEditor2(customization, mediaDbService);
    albumEditor.performInitialization();
     
    return albumEditor;
  }
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param mediaDbService the media database service.
   */
  private AlbumEditor2(CustomizationFx customization, IMediaDbService mediaDbService) {
    super(customization, WINDOW_TITLE, mediaDbService::addAlbumToMediaDatabase);
    
    this.mediaDbService = mediaDbService;
  }
  
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void configureEditor() {
    setAddObjectTexts("Add album", "Add the album to the media database");
    setUpdateObjectTexts("Update album", "Update the current album");
    setNewObjectTexts("New", "Clear the controls to start entering new Album data");
  }


  /**
   * {@inheritDoc}
   * 
   * In this case an EventEditPanel is created and returned.
   */
  @Override
  protected EditPanel<Album> getMainEditPanel() {
    albumEditPanel = AlbumEditPanel.newInstance(customization, mediaDbService);

    return albumEditPanel;
  }
}
