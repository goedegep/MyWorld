package goedegep.travels;

import goedegep.myworld.common.IService;
import goedegep.util.dir.DirectoryChangesListener;

public interface ITravelsService extends IService {
  
  /**
   * Add a listener for changes in the travels folder and the travels pictures folder.
   * <p>
   * When the first listener is added, the monitoring of these directories is started.
   * 
   * @param listener the {@link DirectoryChangesListener} to add.
   */
  public void addDirectoryChangesListener(DirectoryChangesListener listener);

  /**
   * Exit the travels application.
   */
  void exitApplication();
  

}
