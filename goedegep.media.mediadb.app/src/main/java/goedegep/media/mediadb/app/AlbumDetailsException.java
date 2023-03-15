package goedegep.media.mediadb.app;

import java.util.List;

/**
 * An exception with a list messages about what is wrong in the provided album information.
 */
@SuppressWarnings("serial")
public class AlbumDetailsException extends Exception {
  private List<String> problems;
  
  public AlbumDetailsException(List<String> problems) {
    this.problems = problems;
  }
  
  public List<String> getProblems() {
    return problems;
  }
}
