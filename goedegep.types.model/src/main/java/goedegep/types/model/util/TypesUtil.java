package goedegep.types.model.util;

import java.util.Objects;

import goedegep.types.model.FileReference;

public class TypesUtil {
  
  public static boolean areEventsEqual(FileReference fileReference1, FileReference fileReference2) {
    if (fileReference1 == null  &&  fileReference2 != null) {
      return false;
    } else if (fileReference1 != null  &&  fileReference2 == null) {
      return false;
    } else if (fileReference1 == null  &&  fileReference2 == null) {
      return true;
    } else {
      if (!Objects.equals(fileReference1.getTitle(), fileReference2.getTitle())) {
        return false;
      }
      return Objects.equals(fileReference1.getFile(), fileReference2.getFile());
    }
  }

}
