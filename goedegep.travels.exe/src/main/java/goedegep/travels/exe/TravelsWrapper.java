package goedegep.travels.exe;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;

public class TravelsWrapper {
  public static void main(String[] args) {
    try {
      Files.write(Paths.get("D:\\SoulSeek\\output.txt"), Collections.singletonList("Your text here"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    } catch (IOException e) {
      e.printStackTrace();
    }

    
    TravelsApplicationLauncher.main(args);
  }

}
