package goedegep.markdown.exe;

/*
 * This class is the entyry point for the Markdown executable.
 * <p>
 * This wrapper is needed because if the main class extends from javafx.application.Application the executable doesn't work.
 */
public class MarkdownWrapper {
  public static void main(String[] args) {
    
    MarkdownApplication.main(args);
  }
}
