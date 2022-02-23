package goedegep.ov2;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 
 *
 */
public class Ov2DecodeFile {

  public static Stream<Ov2Item> ov2Items(String path) throws FileNotFoundException {

    Ov2DecodeFileIterator iterator = new Ov2DecodeFileIterator(path);

    Iterable<Ov2Item> iterable = new Iterable<Ov2Item>() {
      @Override
      public Iterator<Ov2Item> iterator() {
        return iterator;
      }
    };

    return StreamSupport.stream(iterable.spliterator(), false);
  }

  public static Stream<Ov2Item> ov2Items(URL url) throws FileNotFoundException {
    return ov2Items(url.getFile());
  }

}
