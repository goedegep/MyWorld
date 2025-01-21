package goedegep.media.mediadb.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import goedegep.media.app.MediaRegistry;
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
 */
public class MediaDbService {
  
  /**
   * The {@link EMFResource} for loading and storing the media database.
   */
  private EMFResource<MediaDb> mediaDbResource;
  
  /**
   * The media database.
   */
  private MediaDb mediaDb;

  /**
   * Constructor (typically only to be called by a launcher).
   * 
   * @param mediaDbResource the {@link EMFResource} for loading and storing the media database.
   */
  public MediaDbService(EMFResource<MediaDb> mediaDbResource) {
    this.mediaDbResource = mediaDbResource;
    
    mediaDb = mediaDbResource.getEObject();
  }
  
  public MediaDb getMediaDb() {
    return mediaDb;
  }



  public EMFResource<MediaDb> getMediaDbResource() {
    return mediaDbResource;
  }
  
  /**
   * Add a new {@code Album} to the media database.
   */
  public void addAlbumToMediaDatabase(Album album) {
    mediaDb.getAlbums().add(album);
  }
    
  /**
   * Add a new {@code Artist} to the media database.
   */
  public void addArtistToMediaDatabase(Artist artist) {
    mediaDb.getArtists().add(artist);
  }

  /**
   * Add a new {@code Track} to the media database.
   */
  public void addTrackToMediaDatabase(Track track) {
    mediaDb.getTracks().add(track);
  }


  /**
   * Check and save the media database.
   */
  public Result checkAndSaveMediaDb() {
    List<Object> errors = new ArrayList<>();
    MediaDbChecker.checkMediaDb(mediaDb, errors);
    return saveMediaDb();
  }

  /**
   * Save the media database to the related file.
   */
  public Result saveMediaDb() { 
    EmfUtil.checkCompleteContainment(mediaDb);
    Result result = new Result();
    
    try {
      mediaDbResource.save(MediaRegistry.mediaDbFile);
      result.setResultType(ResultType.OK);
    } catch (IOException e) {
      result.setResultType(ResultType.FAILED);
      result.setMessage("System error message: "  + e.getMessage());
    }
    
    return result;
  }
}
