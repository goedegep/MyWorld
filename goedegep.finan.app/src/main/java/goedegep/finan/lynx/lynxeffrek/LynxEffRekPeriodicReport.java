package goedegep.finan.lynx.lynxeffrek;

import java.io.File;

import goedegep.util.datetime.PeriodicReport;

public class LynxEffRekPeriodicReport<T extends Comparable<T>> extends PeriodicReport<T>  {
  File file;
  
  public LynxEffRekPeriodicReport() {
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }
  
}
