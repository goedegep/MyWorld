package goedegep.media.common;

import goedegep.media.mediadb.model.Album;

public interface IAlbumDetailsWindow {

  /**
   * Change the {@code Album} for which the information is shown.
   * 
   * @param album the {@code Album} for which the information is shown in this window.
   */
  void setAlbum(Album album);

}