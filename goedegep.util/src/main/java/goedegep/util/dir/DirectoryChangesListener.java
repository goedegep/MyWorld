package goedegep.util.dir;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

public interface DirectoryChangesListener {
  void directoryChange(WatchEvent<Path> watchEvent);
}
