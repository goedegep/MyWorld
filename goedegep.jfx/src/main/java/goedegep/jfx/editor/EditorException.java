package goedegep.jfx.editor;

import java.util.List;

/**
 * An exception with a list messages about what is wrong in the provided object information.
 */
@SuppressWarnings("serial")
public class EditorException extends Exception {
  private List<String> problems;
  
  public EditorException(List<String> problems) {
    this.problems = problems;
  }
  
  public List<String> getProblems() {
    return problems;
  }
}