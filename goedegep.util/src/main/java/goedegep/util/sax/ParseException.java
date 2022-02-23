package goedegep.util.sax;

@SuppressWarnings("serial")
public class ParseException extends RuntimeException {
  int lineNumber;
  int columnNumber;
  String fileName;
  String message;
  
  public ParseException(String fileName, int lineNumber, int columnNumber,
      String message) {
    super();
    this.lineNumber = lineNumber;
    this.columnNumber = columnNumber;
    this.fileName = fileName;
    this.message = message;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public int getColumnNumber() {
    return columnNumber;
  }

  public String getFileName() {
    return fileName;
  }

  public String getMessage() {
    return message;
  }
}
