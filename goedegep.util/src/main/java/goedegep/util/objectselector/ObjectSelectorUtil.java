package goedegep.util.objectselector;

public class ObjectSelectorUtil {
  
  public static <T> void link(ObjectSelector<T> objectSelector1, ObjectSelector<T> objectSelector2) {
    objectSelector1.addObjectSelectionListener((source, object) -> {
      objectSelector2.selectObject(object);
    });
    objectSelector2.addObjectSelectionListener((source, object) -> {
      objectSelector1.selectObject(object);
    });
  }
  
}
