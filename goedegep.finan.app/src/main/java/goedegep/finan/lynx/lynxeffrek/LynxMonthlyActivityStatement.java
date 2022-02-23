package goedegep.finan.lynx.lynxeffrek;

import java.io.File;
import java.nio.file.Path;
import java.time.YearMonth;


public class LynxMonthlyActivityStatement {
  private final static String ACCOUNT_NAME = "U874486";
  
  private Path monthlyActivityStatementPath;

  /**
   * Create a Lynx Monthly Activity Statement. This statement is based on the java.nio.file.Path to the statement.
   * 
   * @param monthlyActivityStatementPath - Path to the statement, which may not be null.
   */
  public LynxMonthlyActivityStatement(Path monthlyActivityStatementPath) {
    assert monthlyActivityStatementPath != null;
    
    this.monthlyActivityStatementPath = monthlyActivityStatementPath;
  }
  
  /**
   * Get the java.nio.file.Path to the statement.
   * 
   * @return
   *   The Path to the statement (which is never null).
   */
  public Path getPath() {
    return monthlyActivityStatementPath;
  }

  /**
   * Get the Month (year + month combination) to which the statement applies.
   * 
   * @return
   *   The Month to which the statement applies.
   */
  public YearMonth getMonth() {
    // The month is derived from the file name.
    // The file name has the format; ACCOUNT_NAME_yyyymm_yyyymm.ofx
    // As the report is for a single month, the 2 date parts are always the same.
    // We use the first one.
    String filename = monthlyActivityStatementPath.toString();
    filename = filename.substring(filename.lastIndexOf(File.separator) + 1);
    filename = filename.substring(filename.indexOf("_") + 1, filename.lastIndexOf("_"));
    String yearString = filename.substring(0, 4);
    String monthString = filename.substring(4);
    
    YearMonth month = YearMonth.of(Integer.parseInt(yearString), Integer.parseInt(monthString));
    
    return month;
  }

  /**
   * Get the filename for the statement of a specific month.
   * Note that this method doesn't check whether the statement actually exists.
   * 
   * @param month The Month for which the filename is requested. This parameter may not be null.
   * @return
   *   The filename of the statement.
   */
  public static String getFileNameForMonth(YearMonth month) {
    assert month != null;
    
    String yearString = String.valueOf(month.getYear());
    String monthString = String.valueOf(month.getMonth());  // TODO use number formatting.
    if (monthString.length() == 1) {
      monthString = "0" + monthString;  
    }
    String yearPlusMonthString = yearString + monthString;
    
    return ACCOUNT_NAME + "_" + yearPlusMonthString + "_" + yearPlusMonthString + ".ofx";
  }
}
