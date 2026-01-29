package goedegep.media.common;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.Track;
import goedegep.util.Result;
import goedegep.util.emf.EMFResource;

public interface IMediaDbService {

  MediaDb getMediaDb();

  EMFResource<MediaDb> getMediaDbResource();

  /**
   * Add a new {@code Album} to the media database.
   */
  void addAlbumToMediaDatabase(Album album);

  /**
   * Add a new {@code Artist} to the media database.
   */
  void addArtistToMediaDatabase(Artist artist);

  /**
   * Add a new {@code Track} to the media database.
   */
  void addTrackToMediaDatabase(Track track);

  /**
   * Check and save the media database.
   */
  Result checkAndSaveMediaDb();

  /**
   * Save the media database to the related file.
   */
  Result saveMediaDb();

}