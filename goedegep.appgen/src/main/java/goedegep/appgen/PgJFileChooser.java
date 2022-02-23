package goedegep.appgen;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

@SuppressWarnings("serial")
public class PgJFileChooser extends JFileChooser {
  
  boolean   appendExtensionIfNotSpecified = false;

  /**
   * Returns the selected file. This can be set either by the
   * programmer via <code>setFile</code> or by a user action, such as
   * either typing the filename into the UI or selecting the
   * file from a list in the UI.
   *
   * @see #setSelectedFile
   * @return the selected file
   */
  public File getSelectedFile() {
    File file = super.getSelectedFile();

    if (appendExtensionIfNotSpecified  &&  file != null) {
      FileFilter usedFilter = getFileFilter();
      if (usedFilter instanceof PgFileFilter) {
        PgFileFilter f = (PgFileFilter) usedFilter;
        if (f.getExtension(file) == null) {
          String ext = f.getUniqueFilterExtension();
          if (ext != null) {
            file = new File(file.getPath() + "." + ext);
          }
        }
      }
      //System.out.println("PgJFileChooser: getSelectedFile -> " + file.getPath());
    }

    return file;
  }

  public void setAppendExtensionIfNotSpecified(boolean b) {
    appendExtensionIfNotSpecified = b;
  }
}