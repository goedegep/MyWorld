package goedegep.util.projectinfo;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class Finder extends SimpleFileVisitor<Path> {

  private ProjectInfo projectInfo;
  private final PathMatcher matcher;

  Finder(ProjectInfo projectInfo, String pattern) {
    this.projectInfo = projectInfo;
    matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
  }

  void find(Path file) {
    Path name = file.getFileName();
    if (name != null && matcher.matches(name)) {
      projectInfo.incrementNumberOfClasses();
      try {
        int cnt = countLines(file.toString());
        projectInfo.incrementNumberOfLines(cnt);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  // Invoke the pattern matching
  // method on each file.
  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
    find(file);
    return CONTINUE;
  }

  // Invoke the pattern matching
  // method on each directory.
  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
    find(dir);
    return CONTINUE;
  }

  @Override
  public FileVisitResult visitFileFailed(Path file, IOException exc) {
    System.err.println(exc);
    return CONTINUE;
  }
  
  public int countLines(String filename) throws IOException {
    int cnt = 0;
    try (LineNumberReader reader  = new LineNumberReader(new FileReader(filename))) {
      String line;
      do {
        line = reader.readLine();
      } while (line != null);

      cnt = reader.getLineNumber(); 
      reader.close();
    }
    return cnt;
  }
}
