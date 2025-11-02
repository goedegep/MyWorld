package goedegep.util.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;


/**
 * This class is a java.util.logging.Formatter which logs information in my favorite format.
 * <p>
 * Logging statements have the following format:<br/>
 * &lt;class-name&gt;.&lt;method-name&gt: &lt;message&gt(&lt;source-file-name&gt;.java:&lt;line-number&gt;)
 * <p/>
 * Example:<br/>
 * SomeClass.initMethod: Illegal argument(SomeClass.java:38)
 * <p>
 * When used as a Formatter for java.util.logging.ConsoleHandler within Eclipse, the example looks as follows:<br/>
 * <font color="red">SomeClass.initMethod: Illegal argument (</font><font color="blue"><u>SomeClass.java:38</u></font><font color="red">)</font>
 * <p>
 * The main part is red, because java.util.logging.ConsoleHandler writes to System.err.<br/>
 * The Eclipse console automatically creates a link to the source code for the part &lt;source-file-name&gt;.java:&lt;line-number&gt;, so this part is blue and underlined.
 */
public class MyLoggingFormatter extends SimpleFormatter {
  private final static String LOGGER = "java.util.logging.Logger";
  private static final String NEW_LINE = System.getProperty("line.separator");

  @Override
  public String format(LogRecord record) {
    String fileName = null;
    String className = null;
    String methodName = null;
    int lineNumber = -1;
    
    boolean loggerFound = false;
    for ( StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace() ) {
      className = stackTraceElement.getClassName();
      
      if (!loggerFound  &&  className.equals(LOGGER)) {
        loggerFound = true;
        continue;
      }
      
      
      if (loggerFound && !className.equals(LOGGER)) {
        className = className.substring(className.lastIndexOf(".") + 1);
        fileName = stackTraceElement.getFileName();
        methodName = stackTraceElement.getMethodName();
        lineNumber = stackTraceElement.getLineNumber();
//        System.out.println("className: " + className + ", methodName: " + methodName + ", lineNumber: " + lineNumber);
        break;
      }
    }
        
    StringBuilder buf = new StringBuilder();
    buf.append(className);
    buf.append(".");
    buf.append(methodName);
    buf.append(": ");
    buf.append(formatMessage(record));
    
    if (record.getThrown() != null) {
      StringWriter sw = new StringWriter();
      try (PrintWriter pw = new PrintWriter(sw)) {
        pw.println();
        record.getThrown().printStackTrace(pw);
      }
      buf.append(sw.toString());
    }
    
    buf.append(" (");
    buf.append(fileName);
    buf.append(":");
    buf.append(lineNumber);
    buf.append(")");
    buf.append(NEW_LINE);
        
    return buf.toString();
  }

}
