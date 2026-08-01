package goedegep.media.common;

import goedegep.myworld.common.IService;

public interface IMediaService extends IService {

  /**
   * Show the main media menu window.
   */
  void showMediaMenuWindow();

  /**
   * Show the music database window.
   * <p>
   * Before starting the music database window the following checks are performed and actions are taken:
   * <ul>
   * <li>The file name for the music database is set in the registry</li>
   * <li>Opening the music database (an {@code EMFResource<MediaDb>})</li>
   * <li>Set this {@code EMFResource<MediaDb>} on the {@code MediaDbService}</li>
   * <li>Get the instance of the {@code MediaDbService} and use is as an argument on calling {@code new MediaDbWindow(customization, this, mediaDbService).show()}.
   * </ul>
   */
  void showMediaDbWindow();
  
  
  /**
   * Show the Music Folder window.
   */
  void showMusicFolderWindow();
  
  /**
   * Show the photoshow builder.
   */
  void showPhotoShowBuilder();
    
  /**
   * Show the photo show window.
   */
  void showPhotoShowWindow();
  
  /**
   * Show the photo map view window.
   */
  void showPhotoMapView();
  
  /**
   * Show the video (film) database window.
   */
  void showVideoDbWindow();
  
  /**
   * Show the Dune window.
   */
  void showDuneWindow() ;
}