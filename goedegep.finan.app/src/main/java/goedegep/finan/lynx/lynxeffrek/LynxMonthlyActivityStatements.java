package goedegep.finan.lynx.lynxeffrek;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import goedegep.app.finan.registry.FinanRegistry;
import goedegep.util.file.FileUtils;

public class LynxMonthlyActivityStatements {
  private final static Logger           LOGGER = Logger.getLogger(LynxMonthlyActivityStatements.class.getName()); 


  /**
   * Get a Lynx Activity Statement for a specific Month.
   * 
   * @param month The Month (year + month combination) for which the statement is requested. This value may not be null.
   * @return
   *   The statement for the specified Month, or null if this doesn't exist.
   */
  public LynxMonthlyActivityStatement getMonthlyActivityStatement(YearMonth month) {
    assert month != null;
    
    String monthlyActivityStatementFileName = LynxMonthlyActivityStatement.getFileNameForMonth(month);
    Path monthlyActivityStatementPath = Paths.get(FinanRegistry.dataDirectory, "lynx", "maandoverzichten",
        String.valueOf(month.getYear()), monthlyActivityStatementFileName);
    if (Files.exists(monthlyActivityStatementPath)) {
      return new LynxMonthlyActivityStatement(monthlyActivityStatementPath);
    } else {
      return null;
    }
  }
  
  /**
   * Get the first Lynx Activity Statement.
   * 
   * @return
   *   The first statement, or null if there are no statements.
   */
  public LynxMonthlyActivityStatement getFirstMonthlyActivityStatement() {
    List<Path> yearFolders = getYearFoldersSortedByYear();
    if (yearFolders.size() == 0) {
      // No monthly activity statements available.
      return null;
    }
    Path firstYearFolder = yearFolders.get(0);


    LOGGER.info("First year folder: " + firstYearFolder.getFileName().toString());
    DirectoryStream<Path> maandOverzichtenStream;
    try {
      maandOverzichtenStream = Files.newDirectoryStream(firstYearFolder);
      List<Path> maandOverzichtPaths = new ArrayList<>();
      for (Path maandOverzichtPath: maandOverzichtenStream) {
        maandOverzichtPaths.add(maandOverzichtPath);
      }
      Collections.sort(maandOverzichtPaths);
      // The first monthly report is the first .ofx file.
      for (Path maandOverzichtPath: maandOverzichtPaths) {
        String fileExtension = FileUtils.getFileExtension(maandOverzichtPath);
        if (fileExtension.equals(".ofx")) {
          LOGGER.info("Handling maandoverzicht: " + maandOverzichtPath.getFileName().toString());
          return new LynxMonthlyActivityStatement(maandOverzichtPath);
        } else {
          LOGGER.severe("Skipping file: " + maandOverzichtPath.getFileName().toString());
        }
      }
      return null;
    } catch (IOException e) {
      throw new RuntimeException("Maandoverzichten kunnen niet gelezen worden.");
    }
  }
  
  /**
   * Get the last Lynx Activity Statement.
   * 
   * @return
   *   The last statement, or null if there are no statements.
   */
  public LynxMonthlyActivityStatement getLastMonthlyActivityStatement() {
    List<Path> yearFolders = getYearFoldersSortedByYear();
    if (yearFolders.size() == 0) {
      // No monthly activity statements available.
      return null;
    }
    Path lastYearFolder = yearFolders.get(yearFolders.size() - 1);


    LOGGER.info("Last year folder: " + lastYearFolder.getFileName().toString());
    DirectoryStream<Path> maandOverzichtenStream;
    try {
      maandOverzichtenStream = Files.newDirectoryStream(lastYearFolder);
      List<Path> maandOverzichtPaths = new ArrayList<>();
      for (Path maandOverzichtPath: maandOverzichtenStream) {
        maandOverzichtPaths.add(maandOverzichtPath);
      }
      Collections.sort(maandOverzichtPaths);
      Collections.reverse(maandOverzichtPaths);
      // The first monthly report is the first .ofx file.
      for (Path maandOverzichtPath: maandOverzichtPaths) {
        String fileExtension = FileUtils.getFileExtension(maandOverzichtPath);
        if (fileExtension.equals(".ofx")) {
          LOGGER.info("Handling maandoverzicht: " + maandOverzichtPath.getFileName().toString());
          return new LynxMonthlyActivityStatement(maandOverzichtPath);
        } else {
          LOGGER.severe("Skipping file: " + maandOverzichtPath.getFileName().toString());
        }
      }
      return null;
    } catch (IOException e) {
      throw new RuntimeException("Maandoverzichten kunnen niet gelezen worden.");
    }
  }

  /**
   * Get a list of years for which Lynx Activity Statements are available.
   * 
   * @return
   *   The (probably empty) list of years for which statements are available.
   */
  public List<Integer> getYearsForWhichStatementsAreAvailable() {
    List<Path> yearFoldersSortedByYear = getYearFoldersSortedByYear();
    List<Integer> years = new ArrayList<>();
    for (Path yearFolder: yearFoldersSortedByYear) {
      String filename = yearFolder.toString();
      filename = filename.substring(filename.lastIndexOf(File.separator) + 1);
      Integer year = Integer.parseInt(filename);
      years.add(year);
    }
    return years;
  }
  
  /**
   * For a specific year, get the list of months for which Lynx Activity Statements are available.
   * 
   * @param year The year for which the list is to be provided.
   * @return
   *   The (probably empty) list of months for which statements are available.
   */
  public List<Integer> getMonthsForWhichStatementsAreAvailable(int year) {
    List<Path> monthlyStatements = getMonthlyStatementsSortedByMonth(year);
    List<Integer> months = new ArrayList<>();
    for (Path monthlyStatement: monthlyStatements) {
      LynxMonthlyActivityStatement lynxMonthlyActivityStatement = new LynxMonthlyActivityStatement(monthlyStatement);
      int month = lynxMonthlyActivityStatement.getMonth().getMonthValue();
      months.add(month);
    }
    return months;
  }
  
  private List<Path> getYearFoldersSortedByYear() {
    Path maandOverzichtenPath = Paths.get(FinanRegistry.dataDirectory, "lynx", "maandoverzichten");
    List<Path> yearFolders = new ArrayList<>();
    
    try (DirectoryStream<Path> jarenStream = Files.newDirectoryStream(maandOverzichtenPath)) {
      for (Path yearFolderPath: jarenStream) {
        yearFolders.add(yearFolderPath);
      }
      Collections.sort(yearFolders);
      return yearFolders;
    } catch (IOException | DirectoryIteratorException x) {
      throw new RuntimeException("Maandoverzichten mappen kunnen niet gelezen worden.");
    }
  }
  
  public List<Path> getMonthlyStatementsSortedByMonth(int year) {
    Path maandOverzichtenVoorJaarPath = Paths.get(FinanRegistry.dataDirectory, "lynx", "maandoverzichten", String.valueOf(year));
    List<Path> maandStatements = new ArrayList<>();
    
    try (DirectoryStream<Path> maandStatementsStream = Files.newDirectoryStream(maandOverzichtenVoorJaarPath)) {
      for (Path maandStatementPath: maandStatementsStream) {
        maandStatements.add(maandStatementPath);
      }
      Collections.sort(maandStatements);
      return maandStatements;
    } catch (IOException | DirectoryIteratorException x) {
      throw new RuntimeException("Maandoverzichten mappen kunnen niet gelezen worden.");
    }
  }
}
