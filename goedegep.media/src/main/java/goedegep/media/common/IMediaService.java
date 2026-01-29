package goedegep.media.common;

import java.nio.file.Path;
import java.util.Map;

import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Track;
import goedegep.myworld.common.IService;

public interface IMediaService extends IService {

  /**
   * Show the main media menu window.
   */
  void showMediaMenuWindow();

  void showMediaDbWindow();
  
  IAlbumDetailsWindow openAlbumDetailsWindow(IMediaDbService iMediaDbService, Map<Track, Path> trackDiscLocationMap, EObjectTable<Album> albumsTable);
}