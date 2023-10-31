package goedegep.jfx.objecteditor;

import java.util.List;

/**
 * An exception with a list messages about what is wrong in the provided object information.
 */
@SuppressWarnings("serial")
public class ObjectEditorException extends Exception {
  private List<String> problems;
  
  public ObjectEditorException(List<String> problems) {
    this.problems = problems;
  }
  
  public List<String> getProblems() {
    return problems;
  }
}