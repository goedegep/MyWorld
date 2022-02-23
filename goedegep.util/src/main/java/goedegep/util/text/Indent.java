package goedegep.util.text;

public class Indent {
  int size;
  int level = 0;
  
  public Indent(int size) {
    this.size = size;
  }

  public void increment() {
    level++;
  }
  
  public void decrement() {
    if (level < 1) {
      throw new IllegalArgumentException();
    }
    level--;
  }
  
  public String toString() {
    StringBuffer buf = new StringBuffer();
    for (int currentLevel = 0; currentLevel < level; currentLevel++) {
      for (int currentSpace = 0; currentSpace < size; currentSpace++) {
        buf.append(" "); 
      }
    }
    
    return buf.toString();
  }
}
