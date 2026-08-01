package goedegep.media.mediadb.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.media.common.IAlbumDetailsWindow;
import goedegep.media.common.IMediaDbService;
import goedegep.media.common.MediaRegistry;
import goedegep.media.mediadb.gui.AlbumDetailsWindow;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.Track;
import goedegep.util.Result;
import goedegep.util.Result.ResultType;
import goedegep.util.emf.EMFResource;
import goedegep.util.emf.EmfUtil;

/**
 * This class is a service on top of a media database.
 * <p>
 * Before getting the service instance via {@link #getInstance()} you have to set:
 * <ul>
 * <li>the GUI customization by calling {@link #setGUICustomization()}.</li>
 * <li>the EMFResource for the media database by calling {@link #setEMFResource()}.</li>
 * </ul>
 * The service provides methods to:
 * <ul>
 * <li>add an album, artist or track to the database</li>
 * <li>get the media database  or its EMFResource</li>
 * <li>open the album details window</li>
 * <li>save the media database<br/>
 * Althoug clients have access to the EMFResource, saving the database should only be done via this method</li>
 * </ul>
 */
public class MediaDbService implements IMediaDbService {
  
  /**
   * The {@code MediaRegistry}
   */
  private MediaRegistry mediaRegistry = MediaRegistry.getInstance();
  
  /**
   * The singleton instance of the MediaDbService.
   */
  private static MediaDbService instance;
  
  /**
   * The GUI customization.
   */
  private static CustomizationFx customization;
  
  /**
   * The {@link EMFResource} for loading and storing the media database.
   */
  private static EMFResource<MediaDb> mediaDbResource;
  
  /**
   * The media database.
   */
  private MediaDb mediaDb;
  
  /**
   * Set the GUI customization.
   * 
   * @param customization The GUI customization.
   */
  public static void setGUICustomization(CustomizationFx customization) {
    MediaDbService.customization = customization;
  }
  
  /**
   * Set the {@code EMFResource} for the music database.
   * 
   * @param mediaDbResource the {@link EMFResource} for loading and storing the media database.
   */
  public static void setEMFResource(EMFResource<MediaDb> mediaDbResource) {
    MediaDbService.mediaDbResource = mediaDbResource;
  }
  
  /**
   * Get the singleton instance of the MediaDbService.
   * 
   * @return the singleton instance of the MediaDbService.
   */
  public static MediaDbService getInstance() {
    if (instance == null) {
      instance = new MediaDbService();
    }
    
    return instance;
  }

  /**
   * Private constructor as this is a Singleton.
   */
  private MediaDbService() {
    mediaRegistry = MediaRegistry.getInstance();
    mediaDb = mediaDbResource.getEObject();
  }
  
  @Override
  public MediaDb getMediaDb() {
    return mediaDb;
  }

  @Override
  public EMFResource<MediaDb> getMediaDbResource() {
    return mediaDbResource;
  }
  
  /**
   * Add a new {@code Album} to the media database.
   */
  @Override
  public void addAlbumToMediaDatabase(Album album) {
    mediaDb.getAlbums().add(album);
  }
    
  /**
   * Add a new {@code Artist} to the media database.
   */
  @Override
  public void addArtistToMediaDatabase(Artist artist) {
    mediaDb.getArtists().add(artist);
  }

  /**
   * Add a new {@code Track} to the media database.
   */
  @Override
  public void addTrackToMediaDatabase(Track track) {
    mediaDb.getTracks().add(track);
  }
  
  /**
   * Open an {@code AlbumDetailsWindow}.
   * 
   * @param trackDiscLocationMap A map that maps tracks to their location on hard disk.
   * @param albumsTable the albums table
   * @return the interface (an {@code IAlbumDetailsWindow}) of the opened window.
   */
  public IAlbumDetailsWindow openAlbumDetailsWindow(Map<Track, Path> trackDiscLocationMap, EObjectTable<Album> albumsTable) {
    AlbumDetailsWindow albumDetailsWindow = new AlbumDetailsWindow(customization, this, trackDiscLocationMap, albumsTable);
    albumDetailsWindow.show();
    return albumDetailsWindow;
  }


  /**
   * Check and save the media database.
   */
  @Override
  public Result checkAndSaveMediaDb() {
    List<Object> errors = new ArrayList<>();
    MediaDbChecker.checkMediaDb(mediaDb, errors);
    return saveMediaDb();
  }

  /**
   * Save the media database to the related file.
   */
  @Override
  public Result saveMediaDb() { 
    EmfUtil.checkCompleteContainment(mediaDb);
    Result result = new Result();
    
    try {
      mediaDbResource.save(mediaRegistry.getMediaDbFile());
      result.setResultType(ResultType.OK);
    } catch (IOException e) {
      result.setResultType(ResultType.FAILED);
      result.setMessage("System error message: "  + e.getMessage());
    }
    
    return result;
  }
}
