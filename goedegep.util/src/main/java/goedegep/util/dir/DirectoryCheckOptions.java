package goedegep.util.dir;

import java.util.ArrayList;
import java.util.List;

public class DirectoryCheckOptions {
  private boolean recurseIntoSubDirectories = false;
  private boolean checkWhetherFileIsMovedIntoSubDirectory = false;
  private List<String> ignoreDirectoryNames = new ArrayList<>();
  
  public boolean isRecurseIntoSubDirectories() {
    return recurseIntoSubDirectories;
  }
  
  public void setRecurseIntoSubDirectories(boolean recurseIntoSubDirectories) {
    this.recurseIntoSubDirectories = recurseIntoSubDirectories;
  }
  
  public boolean isCheckWhetherFileIsMovedIntoSubDirectory() {
    return checkWhetherFileIsMovedIntoSubDirectory;
  }
  
  public void setCheckWhetherFileIsMovedIntoSubDirectory(boolean checkWhetherFileIsMovedIntoSubDirectory) {
    this.checkWhetherFileIsMovedIntoSubDirectory = checkWhetherFileIsMovedIntoSubDirectory;
  }

  public List<String> getIgnoreDirectoryNames() {
    return ignoreDirectoryNames;
  }
}
