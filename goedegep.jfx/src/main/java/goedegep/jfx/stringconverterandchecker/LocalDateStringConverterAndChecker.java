package goedegep.jfx.stringconverterandchecker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * This class is a {@link StringConverterAndChecker} for a {@link LocalDate}.
 */
public class LocalDateStringConverterAndChecker extends StringConverterAndChecker<LocalDate> {
  private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("d-M-yyyy");
  private static final DateTimeFormatter DTF2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  
  private static LocalDateStringConverterAndChecker defaultFormatInstance = null;

  /**
   * Get the instance for the default local date format.
   * 
   * @return the  instance for the default local date format.
   */
  public static LocalDateStringConverterAndChecker getDefaultFormatInstance() {
    if (defaultFormatInstance == null) {
      defaultFormatInstance = new LocalDateStringConverterAndChecker();
    }
    
    return defaultFormatInstance;
  }
  
  /**
   * Private constructor.
   */
  private LocalDateStringConverterAndChecker() {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString(LocalDate localDate) {
    if (localDate != null) {
      return DTF2.format(localDate);
    } else {
      return null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LocalDate fromString(String string) {
    // If the specified value is null or zero-length, return null
    if (string == null) {
      return null;
    }

    string = string.trim();

    if (string.isEmpty()) {
      return null;
    }
    
      // Perform the requested parsing
    LocalDate localDate = null;
    
    try {
      localDate = LocalDate.parse(string, DTF);
    } catch (DateTimeParseException e) {
      // no action
    }
    
    return localDate;
  }

  @Override
  public boolean isValid(String string) {
    if (string == null  ||  string.isEmpty()) {
      return true;
    }

    return LocalDate.parse(string, DTF) != null;
  }
  
}
