package goedegep.types.model.util;

import goedegep.types.model.FileReference;
import goedegep.util.PgUtilities;

public class TypesUtil {
  
  public static boolean areEventsEqual(FileReference fileReference1, FileReference fileReference2) {
    if (fileReference1 == null  &&  fileReference2 != null) {
      return false;
    } else if (fileReference1 != null  &&  fileReference2 == null) {
      return false;
    } else if (fileReference1 == null  &&  fileReference2 == null) {
      return true;
    } else {
      if (!PgUtilities.equals(fileReference1.getTitle(), fileReference2.getTitle())) {
        return false;
      }
      return PgUtilities.equals(fileReference1.getFile(), fileReference2.getFile());
    }
  }

}
