package goedegep.util.objectselector;

public class ObjectSelectorUtil {
  
  public static <T> void link(ObjectSelector<T> objectSelector1, ObjectSelector<T> objectSelector2) {
    objectSelector1.addObjectSelectionListener((_, object) -> {
      objectSelector2.selectObject(object);
    });
    objectSelector2.addObjectSelectionListener((_, object) -> {
      objectSelector1.selectObject(object);
    });
  }
  
}
