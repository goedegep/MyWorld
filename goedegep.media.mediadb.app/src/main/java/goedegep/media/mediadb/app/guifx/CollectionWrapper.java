package goedegep.media.mediadb.app.guifx;


import java.util.ArrayList;
import java.util.List;

import goedegep.media.mediadb.model.Collection;

public class CollectionWrapper {
  private static List<CollectionWrapper> values;

  private Collection collection;
  
  static {
    values = new ArrayList<>();
    for (Collection collection: Collection.values()) {
      values.add(new CollectionWrapper(collection));
    }
  }
  
  public CollectionWrapper(Collection collection) {
    this.collection = collection;
  }
  
  public String toString() {
    return collection.getLiteral();
  }
  
  public static List<CollectionWrapper> values() {
    return values;
  }
  
  public static CollectionWrapper forCollection(Collection collection) {
    for (CollectionWrapper collectionWrapper: values) {
      if (collectionWrapper.collection.equals(collection)) {
        return collectionWrapper;
      }
    }
    
    return null;
  }
  
  public Collection getCollection() {
    return collection;
  }
}
