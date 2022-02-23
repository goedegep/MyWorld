package goedegep.pctools.filefinder.logic;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class FileFinder {
  public static void findFrameMakerFiles(Path directory) {
    try {
      Files.walkFileTree(directory, new FileVisitor<Path>() {

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
//          System.out.println("preVisitDirectory: dir=" + dir.toAbsolutePath().toString());
          return CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//          System.out.println("visitFile: file=" + file.toAbsolutePath().toString());
          if (isFramemakerFile(file.toAbsolutePath().toString(), attrs)) {
            System.out.println(file.toAbsolutePath().toString());
          }
          return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
//          System.out.println("visitFileFailed: file=" + file.toAbsolutePath().toString());
          return CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
//          System.out.println("postVisitDirectory: dir=" + dir.toAbsolutePath().toString());
          return CONTINUE;
        }
        
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public static boolean isFramemakerFile(String fileName, BasicFileAttributes attrs) throws IOException {
    if (attrs.size() < 16) {
      return false;
    }
    
    byte[] framemakerMarker = {
        '<', 'M', 'a', 'k', 'e', 'r', 'F', 'i', 'l', 'e', ' '
    };
    
      FileInputStream fis = new FileInputStream(fileName);

      byte[] dataBytes = new byte[framemakerMarker.length];
      fis.read(dataBytes);
      fis.close();
      
      for (int i = 0; i < framemakerMarker.length; i++) {
        if (framemakerMarker[i] != dataBytes[i]) {
          return false;
        }
      }
    
    return true;
  }

  public static void main(String[] args) {
    Path directory = Paths.get("D:\\Peter\\Doc");
    
    FileFinder.findFrameMakerFiles(directory);
  }
}
