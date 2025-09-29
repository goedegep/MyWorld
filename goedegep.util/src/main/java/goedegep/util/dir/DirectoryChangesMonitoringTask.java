package goedegep.util.dir;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.logging.Logger;

import javafx.concurrent.Task;

/**
 * This class provides a Task which monitors changes in a directory.
 */
public class DirectoryChangesMonitoringTask extends Task<WatchEvent<Path>> {
  private static final String NEW_LINE = System.getProperty("line.separator");
  private static final Logger LOGGER = Logger.getLogger(DirectoryChangesMonitoringTask.class.getName());

//  private Path folderPath;
  private WatchService watchService;
  
  public DirectoryChangesMonitoringTask(String... folderNames) {
    try {
      watchService = FileSystems.getDefault().newWatchService();
      for (String folderName : folderNames) {
        Path folderPath = Paths.get(folderName);
        LOGGER.info("Going to register folder: " + folderPath.toString());
        folderPath.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }
  
  public void addDirectoryToMonitor(String folderName) {
    try {
      Path folderPath = Paths.get(folderName);
      LOGGER.info("Going to register folder: " + folderPath.toString());
      folderPath.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public void removeDirectoryToMonitor(String folderName) {
    // Not implemented yet.
  }

  @Override
  protected WatchEvent<Path> call() throws Exception {
    while (true) {

      // wait for key to be signaled
      WatchKey key;
      try {
        key = watchService.take();
      } catch (InterruptedException x) {
        continue;
      }

      for (WatchEvent<?> event: key.pollEvents()) {
        WatchEvent.Kind<?> kind = event.kind();
        LOGGER.severe("event: " + watchEventToString(event));

        if (kind == OVERFLOW) {
          throw new RuntimeException("Overflow event occurred in DirectoryChangesMonitoringTask.");
        }

        // Context for directory entry event is the file name of entry
        @SuppressWarnings("unchecked")
        WatchEvent<Path> watchEvent = (WatchEvent<Path>) event;
        Path eventPath = watchEvent.context();
        LOGGER.severe("eventPath: " + eventPath.toString());
        updateValue(watchEvent);
      }
      
      // No action if the key is no longer valid. This shall not happen, and we may be monitoring multiple directories.
      key.reset();
    }
    
  }

  public static String watchEventToString(WatchEvent<?> watchEvent) {
    StringBuilder buf = new StringBuilder();
    
    WatchEvent.Kind<?> kind = watchEvent.kind();
    buf.append("kind: ").append(kind.name()).append(NEW_LINE);
    buf.append("Context: ").append(watchEvent.context().toString()).append(NEW_LINE);
    
    @SuppressWarnings("unchecked")
    WatchEvent<Path> pathWatchEvent = (WatchEvent<Path>) watchEvent;
    Path path = pathWatchEvent.context();
    buf.append("Path: ").append(path.toString()).append(NEW_LINE);
    
    return buf.toString();
  }
}
