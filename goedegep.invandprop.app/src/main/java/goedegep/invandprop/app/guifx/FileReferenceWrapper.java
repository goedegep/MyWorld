package goedegep.invandprop.app.guifx;

import java.io.File;
import java.util.List;

import goedegep.types.model.FileReference;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FileReferenceWrapper {
  private FileReference fileReference;
    
  public FileReferenceWrapper(FileReference fileReference) {
    this.fileReference = fileReference;
  }
  
  public String toString() {
    String string = fileReference.getTitle();
    if ((string == null)  &&  (fileReference.getFile() != null)) {
      File file = new File(fileReference.getFile());
      string = file.getName();
    }
    
    return string;
  }
  
  public FileReference getFileReference() {
    return fileReference;
  }
  
  public static ObservableList<FileReferenceWrapper> createFileReferenceWrapperList(List<FileReference> fileReferences) {
    ObservableList<FileReferenceWrapper> fileReferenceWrappers = FXCollections.observableArrayList();
    
    for (FileReference fileReference: fileReferences) {
      fileReferenceWrappers.add(new FileReferenceWrapper(fileReference));
    }
    
    return fileReferenceWrappers;
  }

}
