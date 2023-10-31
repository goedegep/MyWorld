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

  private Path folderPath;
  private WatchService watchService;
  
  public DirectoryChangesMonitoringTask(String folderName) {
    try {
      watchService = FileSystems.getDefault().newWatchService();
      folderPath = Paths.get(folderName);
      LOGGER.severe("Going to register folder: " + folderPath.toString());
      WatchKey key = folderPath.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
      LOGGER.severe("Obtained WatchKey: " + key);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }

  @Override
  protected WatchEvent<Path> call() throws Exception {
    while (true) {

      // wait for key to be signalled
      WatchKey key;
      try {
        key = watchService.take();
      } catch (InterruptedException x) {
        return null;
      }

      for (WatchEvent<?> event: key.pollEvents()) {
        WatchEvent.Kind<?> kind = event.kind();
        LOGGER.severe("event: " + watchEventToString(event));

        // TBD - provide example of how OVERFLOW event is handled
        if (kind == OVERFLOW) {
          continue;
        }

        // Context for directory entry event is the file name of entry
        WatchEvent<Path> watchEvent = (WatchEvent<Path>) event;
        Path name = watchEvent.context();
        Path child = folderPath.resolve(name);
        LOGGER.info("child: " + child.toString());
        updateValue(watchEvent);
      }
      
      // Cancel this task if the directory no longer accessible
      boolean valid = key.reset();
      LOGGER.severe("valid: " + valid);
      if (!valid) {
        cancel();
      }
    }
    
  }

  public static String watchEventToString(WatchEvent<?> watchEvent) {
    StringBuilder buf = new StringBuilder();
    
    WatchEvent.Kind<?> kind = watchEvent.kind();
    buf.append("kind: ").append(kind.name()).append(NEW_LINE);
    buf.append("Context: ").append(watchEvent.context().toString()).append(NEW_LINE);
    
    WatchEvent<Path> pathWatchEvent = (WatchEvent<Path>) watchEvent;
    Path path = pathWatchEvent.context();
    buf.append("Path: ").append(path.toString()).append(NEW_LINE);
    
    return buf.toString();
  }
}
